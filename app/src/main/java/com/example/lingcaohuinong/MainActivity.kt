package com.example.lingcaohuinong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lingcaohuinong.ui.MainScreen
import com.example.lingcaohuinong.ui.theme.Temporal_useTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Temporal_useTheme {
                MainScreen()
            }
        }
    }
}