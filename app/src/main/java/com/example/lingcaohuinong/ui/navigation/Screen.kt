package com.example.lingcaohuinong.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object GapBase : Screen("gap_base")
    object SeedSupply : Screen("seed_supply")
    object InputPurchase : Screen("input_purchase")
    object PlantingPlan : Screen("planting_plan")
    object HarvestProcess : Screen("harvest_process")
    object ProcessingRegulation : Screen("processing_regulation")
    object Profile : Screen("profile")
    object Shopping : Screen("shopping")
    object Notification : Screen("notification")
    object Function : Screen("function")
    object Settings : Screen("settings")
    object AIChat : Screen("ai_chat")
    
    // 详情页面
    object GapBaseDetail : Screen("gap_base_detail")
    object SeedSupplyDetail : Screen("seed_supply_detail")
    object InputPurchaseDetail : Screen("input_purchase_detail")
    object PlantingPlanDetail : Screen("planting_plan_detail")
    object HarvestProcessDetail : Screen("harvest_process_detail")
    object ProcessingRegulationDetail : Screen("processing_regulation_detail")
} 