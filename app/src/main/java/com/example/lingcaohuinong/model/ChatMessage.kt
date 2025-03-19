package com.example.lingcaohuinong.model

import java.util.Date

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Date = Date(),
    val isComplete: Boolean = true // 根据文档设置的流失传输，但不知道为什么就是不行lol
) 