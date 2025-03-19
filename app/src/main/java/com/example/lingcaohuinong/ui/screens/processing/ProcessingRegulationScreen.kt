package com.example.lingcaohuinong.ui.screens.processing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lingcaohuinong.ui.components.AppTopBar
import com.example.lingcaohuinong.ui.navigation.Screen
import com.example.lingcaohuinong.ui.theme.GreenLight
import com.example.lingcaohuinong.ui.theme.GreenPrimary

data class ProcessingRegulation(
    val id: Int,
    val name: String,
    val crop: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcessingRegulationScreen(navController: NavController) {
    val searchText = remember { mutableStateOf("") }
    val regulations = remember {
        listOf(
            ProcessingRegulation(
                1,
                "蔬菜清洗与分级",
                "蔬菜类",
                "适用于各类叶菜、果菜的清洗、分级和包装流程"
            ),
            ProcessingRegulation(
                2,
                "水果采后处理",
                "水果类",
                "适用于各类水果的采后保鲜、分级和包装流程"
            ),
            ProcessingRegulation(
                3,
                "谷物烘干与储存",
                "谷物类",
                "适用于各类谷物的烘干、储存和初加工流程"
            ),
            ProcessingRegulation(
                4,
                "食用菌加工",
                "食用菌类",
                "适用于各类食用菌的清洗、分级和包装流程"
            ),
            ProcessingRegulation(
                5,
                "中药材初加工",
                "中药材类",
                "适用于各类中药材的采收后清洗、烘干和储存流程"
            )
        )
    }
    
    Scaffold(
        topBar = {
            AppTopBar(
                title = "产地加工规程",
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
                .padding(16.dp)
        ) {
            TextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("搜索加工规程...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "搜索"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "加工规程列表",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(regulations) { regulation ->
                    RegulationCard(
                        regulation = regulation,
                        onClick = {
                            navController.navigate(Screen.ProcessingRegulationDetail.route)
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun RegulationCard(
    regulation: ProcessingRegulation,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(GreenPrimary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Inventory,
                    contentDescription = null,
                    tint = GreenPrimary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = regulation.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "适用作物: ${regulation.crop}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = regulation.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "查看详情",
                tint = GreenPrimary
            )
        }
    }
} 