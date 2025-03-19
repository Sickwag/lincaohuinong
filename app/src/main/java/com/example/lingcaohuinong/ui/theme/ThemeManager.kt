package com.example.lingcaohuinong.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf

// 主题模式枚举
enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

// 主题管理器
object ThemeManager {
    // 当前主题模式，默认跟随系统
    val themeMode: MutableState<ThemeMode> = mutableStateOf(ThemeMode.SYSTEM)
    
    // 切换主题模式
    fun switchThemeMode(mode: ThemeMode) {
        themeMode.value = mode
    }
}

// 创建一个CompositionLocal，用于在整个应用程序中访问主题管理器
val LocalThemeManager = staticCompositionLocalOf { ThemeManager } 