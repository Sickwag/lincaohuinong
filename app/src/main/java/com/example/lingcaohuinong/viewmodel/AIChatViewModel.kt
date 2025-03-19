package com.example.lingcaohuinong.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lingcaohuinong.api.DeepSeekService
import com.example.lingcaohuinong.model.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * AI聊天页面的ViewModel
 */
class AIChatViewModel : ViewModel() {
    private val deepSeekService = DeepSeekService()
    
    // 聊天消息列表
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    
    // 输入框文本
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()
    
    // 是否正在加载
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // API密钥
    private val _apiKey = MutableStateFlow("")
    val apiKey: StateFlow<String> = _apiKey.asStateFlow()
    
    /**
     * 更新输入框文本
     */
    fun updateInputText(text: String) {
        _inputText.value = text
    }
    
    /**
     * 设置API密钥
     */
    fun setApiKey(key: String) {
        _apiKey.value = key
        deepSeekService.setApiKey(key)
    }
    
    /**
     * 发送消息
     */
    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isEmpty()) return
        
        // 检查API密钥是否已设置
        if (deepSeekService.getApiKey().isEmpty()) {
            _messages.update { currentMessages ->
                currentMessages + ChatMessage(
                    content = "请先设置API密钥",
                    isFromUser = false
                )
            }
            return
        }
        
        // 添加用户消息
        val userMessage = ChatMessage(
            content = text,
            isFromUser = true
        )
        _messages.update { it + userMessage }
        
        // 清空输入框
        _inputText.value = ""
        
        // 设置加载状态
        _isLoading.value = true
        
        // 发送请求
        viewModelScope.launch {
            try {
                // 获取所有历史消息
                val historyMessages = _messages.value
                
                // 添加一个空的AI回复消息作为占位符
                val tempAiMessage = ChatMessage(
                    content = "",
                    isFromUser = false,
                    isComplete = false
                )
                _messages.update { it + tempAiMessage }
                
                // 发送请求并获取流式响应
                deepSeekService.sendMessage(historyMessages).collect { response ->
                    // 更新最后一条AI消息
                    _messages.update { currentMessages ->
                        val mutableMessages = currentMessages.toMutableList()
                        // 替换最后一条消息
                        if (mutableMessages.isNotEmpty() && !mutableMessages.last().isFromUser) {
                            mutableMessages[mutableMessages.size - 1] = response
                        } else {
                            mutableMessages.add(response)
                        }
                        mutableMessages
                    }
                }
            } catch (e: Exception) {
                // 添加错误消息
                _messages.update { currentMessages ->
                    currentMessages + ChatMessage(
                        content = "发送消息失败: ${e.message}",
                        isFromUser = false
                    )
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * 清空聊天记录
     */
    fun clearMessages() {
        _messages.value = emptyList()
    }
} 