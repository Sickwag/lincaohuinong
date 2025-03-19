package com.example.lingcaohuinong.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.lingcaohuinong.ui.components.BottomNavigation
import com.example.lingcaohuinong.ui.navigation.AppNavigation
import com.example.lingcaohuinong.ui.theme.GreenLight

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .background(GreenLight)
        )
    }
} 