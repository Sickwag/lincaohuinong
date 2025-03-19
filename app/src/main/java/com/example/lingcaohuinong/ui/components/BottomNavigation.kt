package com.example.lingcaohuinong.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lingcaohuinong.model.CartManager
import com.example.lingcaohuinong.ui.navigation.Screen
import com.example.lingcaohuinong.ui.theme.GreenPrimary

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem("主页", Screen.Home.route, Icons.Default.Home),
        BottomNavItem("功能", Screen.Function.route, Icons.Default.ViewModule),
        BottomNavItem("购物", Screen.Shopping.route, Icons.Default.ShoppingCart),
        BottomNavItem("通知", Screen.Notification.route, Icons.Default.Notifications),
        BottomNavItem("我的", Screen.Profile.route, Icons.Default.Person)
    )
    
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        items.forEach { item ->
            NavigationBarItem(
                icon = { 
                    if (item.route == Screen.Shopping.route && CartManager.totalItems > 0) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(
                                        text = CartManager.totalItems.toString(),
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        ) {
                            Icon(
                                item.icon, 
                                contentDescription = item.name, 
                                tint = if (currentRoute == item.route) GreenPrimary else GreenPrimary.copy(alpha = 0.5f)
                            )
                        }
                    } else {
                        Icon(
                            item.icon, 
                            contentDescription = item.name, 
                            tint = if (currentRoute == item.route) GreenPrimary else GreenPrimary.copy(alpha = 0.5f)
                        )
                    }
                },
                label = { Text(text = item.name, color = if (currentRoute == item.route) GreenPrimary else GreenPrimary.copy(alpha = 0.5f)) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // 避免创建多个实例
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // 避免重复点击
                        launchSingleTop = true
                        // 恢复状态
                        restoreState = true
                    }
                }
            )
        }
    }
} 