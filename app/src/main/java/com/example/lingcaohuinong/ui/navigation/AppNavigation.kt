package com.example.lingcaohuinong.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lingcaohuinong.ui.screens.aichat.AIChatScreen
import com.example.lingcaohuinong.ui.screens.function.FunctionScreen
import com.example.lingcaohuinong.ui.screens.gap.GapBaseDetailScreen
import com.example.lingcaohuinong.ui.screens.gap.GapBaseScreen
import com.example.lingcaohuinong.ui.screens.harvest.HarvestProcessDetailScreen
import com.example.lingcaohuinong.ui.screens.harvest.HarvestProcessScreen
import com.example.lingcaohuinong.ui.screens.home.HomeScreen
import com.example.lingcaohuinong.ui.screens.input.InputPurchaseDetailScreen
import com.example.lingcaohuinong.ui.screens.input.InputPurchaseScreen
import com.example.lingcaohuinong.ui.screens.notification.NotificationScreen
import com.example.lingcaohuinong.ui.screens.planting.PlantingPlanDetailScreen
import com.example.lingcaohuinong.ui.screens.planting.PlantingPlanScreen
import com.example.lingcaohuinong.ui.screens.processing.ProcessingRegulationDetailScreen
import com.example.lingcaohuinong.ui.screens.processing.ProcessingRegulationScreen
import com.example.lingcaohuinong.ui.screens.profile.ProfileScreen
import com.example.lingcaohuinong.ui.screens.profile.SettingsScreen
import com.example.lingcaohuinong.ui.screens.seed.SeedSupplyDetailScreen
import com.example.lingcaohuinong.ui.screens.seed.SeedSupplyScreen
import com.example.lingcaohuinong.ui.screens.shopping.ShoppingScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(Screen.GapBase.route) {
            GapBaseScreen(navController = navController)
        }
        
        composable(Screen.SeedSupply.route) {
            SeedSupplyScreen(navController = navController)
        }
        
        composable(Screen.InputPurchase.route) {
            InputPurchaseScreen(navController = navController)
        }
        
        composable(Screen.PlantingPlan.route) {
            PlantingPlanScreen(navController = navController)
        }
        
        composable(Screen.HarvestProcess.route) {
            HarvestProcessScreen(navController = navController)
        }
        
        composable(Screen.ProcessingRegulation.route) {
            ProcessingRegulationScreen(navController = navController)
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        
        composable(Screen.Shopping.route) {
            ShoppingScreen(navController = navController)
        }
        
        composable(Screen.Notification.route) {
            NotificationScreen(navController = navController)
        }
        
        composable(Screen.Function.route) {
            FunctionScreen(navController = navController)
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        
        composable(Screen.AIChat.route) {
            AIChatScreen(navController = navController)
        }
        
        // 详情页面
        composable(Screen.GapBaseDetail.route) {
            GapBaseDetailScreen(navController = navController)
        }
        
        composable(Screen.SeedSupplyDetail.route) {
            SeedSupplyDetailScreen(navController = navController)
        }
        
        composable(Screen.InputPurchaseDetail.route) {
            InputPurchaseDetailScreen(navController = navController)
        }
        
        composable(Screen.PlantingPlanDetail.route) {
            PlantingPlanDetailScreen(navController = navController)
        }
        
        composable(Screen.HarvestProcessDetail.route) {
            HarvestProcessDetailScreen(navController = navController)
        }
        
        composable(Screen.ProcessingRegulationDetail.route) {
            ProcessingRegulationDetailScreen(navController = navController)
        }
    }
} 