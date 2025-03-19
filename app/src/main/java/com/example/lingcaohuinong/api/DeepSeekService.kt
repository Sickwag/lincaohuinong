package com.example.lingcaohuinong.api

import com.example.lingcaohuinong.model.ChatMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class DeepSeekService {
    companion object {
        private const val API_URL = "https://api.deepseek.com/v1/chat/completions"
        private var API_KEY = "none"
    }
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    
    fun setApiKey(apiKey: String) {
        API_KEY = apiKey
    }
    
    
    fun getApiKey(): String {
        return API_KEY
    }
    
    
    fun sendMessage(messages: List<ChatMessage>): Flow<ChatMessage> = flow {
        if (API_KEY.isEmpty()) {
            throw IllegalStateException("API密钥未设置")
        }
        
        // 构建请求体
        val jsonMessages = JSONArray()
        messages.forEach { message ->
            val jsonMessage = JSONObject()
            jsonMessage.put("role", if (message.isFromUser) "user" else "assistant")
            jsonMessage.put("content", message.content)
            jsonMessages.put(jsonMessage)
        }
        
        val jsonBody = JSONObject()
        jsonBody.put("model", "deepseek-chat") // 使用DeepSeek Chat模型
        jsonBody.put("messages", jsonMessages)
        jsonBody.put("stream", true) // 启用流式传输
        
        val requestBody = jsonBody.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())
        
        val request = Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()
        
        try {
            // 使用Dispatchers.IO执行网络请求，但不在withContext中使用emit
            val responseData = withContext(Dispatchers.IO) {
                val response = client.newCall(request).execute()
                
                if (!response.isSuccessful) {
                    val errorBody = response.body?.string() ?: "未知错误"
                    throw Exception("API请求失败: ${response.code} - $errorBody")
                }
                
                val responseBody = response.body ?: throw Exception("响应体为空")
                val reader = responseBody.charStream().buffered()
                
                // 收集所有行
                val lines = mutableListOf<String>()
                reader.forEachLine { line ->
                    lines.add(line)
                }
                
                lines
            }
            
            // 在flow的上下文中处理数据并emit
            var currentMessage = ""
            val messageId = java.util.UUID.randomUUID().toString()
            
            for (line in responseData) {
                if (line.startsWith("data: ")) {
                    val data = line.substring(6)
                    if (data == "[DONE]") {
                        // 流结束
                        if (currentMessage.isNotEmpty()) {
                            emit(ChatMessage(
                                id = messageId,
                                content = currentMessage,
                                isFromUser = false,
                                isComplete = true
                            ))
                        }
                        continue
                    }
                    
                    try {
                        val jsonObject = JSONObject(data)
                        val choices = jsonObject.getJSONArray("choices")
                        if (choices.length() > 0) {
                            val choice = choices.getJSONObject(0)
                            val delta = choice.optJSONObject("delta")
                            val content = delta?.optString("content", "") ?: ""
                            
                            if (content.isNotEmpty()) {
                                currentMessage += content
                                emit(ChatMessage(
                                    id = messageId,
                                    content = currentMessage,
                                    isFromUser = false,
                                    isComplete = false
                                ))
                            }
                        }
                    } catch (e: Exception) {
                        // 解析错误，忽略
                    }
                }
            }
            
            // 确保最后一条消息被标记为完成
            if (currentMessage.isNotEmpty()) {
                emit(ChatMessage(
                    id = messageId,
                    content = currentMessage,
                    isFromUser = false,
                    isComplete = true
                ))
            }
            
        } catch (e: Exception) {
            // 发生错误时，发送错误消息
            emit(ChatMessage(
                content = "与AI通信时发生错误: ${e.message}",
                isFromUser = false
            ))
        }
    }.flowOn(Dispatchers.IO)
} 