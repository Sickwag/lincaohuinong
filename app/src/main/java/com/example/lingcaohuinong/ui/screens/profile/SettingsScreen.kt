package com.example.lingcaohuinong.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lingcaohuinong.ui.components.AppTopBar
import com.example.lingcaohuinong.ui.theme.GreenLight
import com.example.lingcaohuinong.ui.theme.GreenPrimary
import com.example.lingcaohuinong.ui.theme.ThemeManager
import com.example.lingcaohuinong.ui.theme.ThemeMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "设置",
                navController = navController,
                showBackButton = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(GreenLight)
        ) {
            // 主题设置卡片
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "主题设置",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimary
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    ThemeSelector()
                }
            }
            
            // 其他设置卡片可以在这里添加
        }
    }
}

@Composable
fun ThemeSelector() {
    var expanded by remember { mutableStateOf(false) }
    val currentThemeMode = ThemeManager.themeMode.value
    
    val themeModeText = when (currentThemeMode) {
        ThemeMode.LIGHT -> "浅色模式"
        ThemeMode.DARK -> "深色模式"
        ThemeMode.SYSTEM -> "跟随系统"
    }
    
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (currentThemeMode) {
                    ThemeMode.LIGHT -> Icons.Default.LightMode
                    ThemeMode.DARK -> Icons.Default.DarkMode
                    ThemeMode.SYSTEM -> Icons.Default.Settings
                },
                contentDescription = "主题模式",
                tint = GreenPrimary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "主题模式",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = themeModeText,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            androidx.compose.material3.TextButton(
                onClick = { expanded = true }
            ) {
                Text(
                    text = "更改",
                    color = GreenPrimary
                )
            }
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("浅色模式") },
                    onClick = {
                        ThemeManager.switchThemeMode(ThemeMode.LIGHT)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.LightMode,
                            contentDescription = "浅色模式"
                        )
                    }
                )
                
                DropdownMenuItem(
                    text = { Text("深色模式") },
                    onClick = {
                        ThemeManager.switchThemeMode(ThemeMode.DARK)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.DarkMode,
                            contentDescription = "深色模式"
                        )
                    }
                )
                
                DropdownMenuItem(
                    text = { Text("跟随系统") },
                    onClick = {
                        ThemeManager.switchThemeMode(ThemeMode.SYSTEM)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "跟随系统"
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            content()
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    subtitle: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = GreenPrimary
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun ThemeSettings(
    currentTheme: ThemeMode,
    onThemeChanged: (ThemeMode) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "主题设置",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // 浅色模式选项
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LightMode,
                contentDescription = "浅色模式",
                tint = GreenPrimary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = "浅色模式",
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            
            Switch(
                checked = currentTheme == ThemeMode.LIGHT,
                onCheckedChange = { if (it) onThemeChanged(ThemeMode.LIGHT) }
            )
        }
        
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        
        // 深色模式选项
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = "深色模式",
                tint = GreenPrimary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = "深色模式",
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            
            Switch(
                checked = currentTheme == ThemeMode.DARK,
                onCheckedChange = { if (it) onThemeChanged(ThemeMode.DARK) }
            )
        }
        
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        
        // 跟随系统选项
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "跟随系统",
                tint = GreenPrimary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = "跟随系统",
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            
            Switch(
                checked = currentTheme == ThemeMode.SYSTEM,
                onCheckedChange = { if (it) onThemeChanged(ThemeMode.SYSTEM) }
            )
        }
    }
}

// 修改Preview函数
@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsSectionPreview() {
    MaterialTheme {
        SettingsSection(title = "主题设置") {
            Text("这是主题设置内容", modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemPreview() {
    MaterialTheme {
        SettingsItem(
            title = "深色模式",
            icon = Icons.Default.DarkMode,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ThemeSettingsPreview() {
    MaterialTheme {
        ThemeSettings(
            currentTheme = ThemeMode.SYSTEM,
            onThemeChanged = {}
        )
    }
} 