package com.example.lingcaohuinong.ui.screens.aichat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lingcaohuinong.model.ChatMessage
import com.example.lingcaohuinong.ui.components.AppTopBar
import com.example.lingcaohuinong.ui.theme.GreenLight
import com.example.lingcaohuinong.ui.theme.GreenPrimary
import com.example.lingcaohuinong.viewmodel.AIChatViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIChatScreen(
    navController: NavController,
    viewModel: AIChatViewModel = viewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val inputText by viewModel.inputText.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val apiKey by viewModel.apiKey.collectAsState()
    
    var showApiKeyDialog by remember { mutableStateOf(false) }
    var tempApiKey by remember { mutableStateOf("") }
    
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    
    // 当消息列表更新时，滚动到底部
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    
    // 检查API密钥是否已设置
    LaunchedEffect(Unit) {
        if (apiKey.isEmpty()) {
            showApiKeyDialog = true
        }
    }
    
    // API密钥设置对话框
    if (showApiKeyDialog) {
        AlertDialog(
            onDismissRequest = { 
                if (apiKey.isNotEmpty()) {
                    showApiKeyDialog = false
                }
            },
            title = { Text("设置DeepSeek API密钥") },
            text = {
                Column {
                    Text("请输入您的DeepSeek API密钥，可以从DeepSeek官网获取。")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempApiKey,
                        onValueChange = { tempApiKey = it },
                        label = { Text("API密钥") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (tempApiKey.isNotEmpty()) {
                            viewModel.setApiKey(tempApiKey)
                            showApiKeyDialog = false
                        }
                    }
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                if (apiKey.isNotEmpty()) {
                    TextButton(onClick = { showApiKeyDialog = false }) {
                        Text("取消")
                    }
                }
            }
        )
    }
    
    Scaffold(
        topBar = {
            AppTopBar(
                title = "AI小参",
                navController = navController,
                showBackButton = true,
                actions = {
                    IconButton(onClick = { showApiKeyDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "设置API密钥",
                            tint = Color.White
                        )
                    }
                    
                    IconButton(onClick = { viewModel.clearMessages() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "清空聊天记录",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(GreenLight)
        ) {
            // 消息列表
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(messages) { message ->
                    MessageItem(message = message)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                // 如果没有消息，显示欢迎信息
                if (messages.isEmpty()) {
                    item {
                        WelcomeMessage()
                    }
                }
            }
            
            // 输入框和发送按钮
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = inputText,
                        onValueChange = { viewModel.updateInputText(it) },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("输入您的问题...") },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = GreenPrimary,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                viewModel.sendMessage()
                                focusManager.clearFocus()
                            }
                        ),
                        maxLines = 3
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // 发送按钮
                    FloatingActionButton(
                        onClick = {
                            viewModel.sendMessage()
                            focusManager.clearFocus()
                        },
                        modifier = Modifier.size(48.dp),
                        containerColor = GreenPrimary
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "发送",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: ChatMessage) {
    val isFromUser = message.isFromUser
    val backgroundColor = if (isFromUser) 
        GreenPrimary.copy(alpha = 0.1f) 
    else 
        MaterialTheme.colorScheme.surface
    
    val alignment = if (isFromUser) Alignment.End else Alignment.Start
    val shape = if (isFromUser)
        RoundedCornerShape(16.dp, 4.dp, 16.dp, 16.dp)
    else
        RoundedCornerShape(4.dp, 16.dp, 16.dp, 16.dp)
    
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val timeString = dateFormat.format(message.timestamp)
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clip(shape),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(12.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        Text(
            text = timeString,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun WelcomeMessage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "欢迎使用AI小参",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "AI小参是您的智能助手，可以回答您关于中草药、农业种植、市场行情等方面的问题。",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "您可以尝试询问：",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        SuggestionItem("人参的种植方法和注意事项？")
        SuggestionItem("中药材市场最近的行情如何？")
        SuggestionItem("如何选择适合的GAP基地？")
        SuggestionItem("有机农业和传统农业的区别？")
    }
}

@Composable
fun SuggestionItem(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreenPrimary.copy(alpha = 0.1f)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
            color = GreenPrimary
        )
    }
} 