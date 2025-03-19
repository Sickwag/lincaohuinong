package com.example.lingcaohuinong.ui.screens.input

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.temporal_use.R
import com.example.lingcaohuinong.model.CartManager
import com.example.lingcaohuinong.model.Product
import com.example.lingcaohuinong.ui.components.AppTopBar
import com.example.lingcaohuinong.ui.navigation.Screen
import com.example.lingcaohuinong.ui.theme.GreenPrimary
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview
import com.example.lingcaohuinong.ui.theme.Temporal_useTheme
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPurchaseScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // 将InputItem转换为Product
    val allProducts = remember {
        listOf(
            Product(
                id = "1",
                name = "有机肥料",
                description = "类型: 肥料 | 生产商: 绿色农资",
                price = 200.0,
                imageResId = R.drawable.ic_launcher_foreground,
                category = "肥料"
            ),
            Product(
                id = "2",
                name = "生物农药",
                description = "类型: 农药 | 生产商: 生物科技",
                price = 150.0,
                imageResId = R.drawable.ic_launcher_foreground,
                category = "农药"
            ),
            Product(
                id = "3",
                name = "滴灌设备",
                description = "类型: 设备 | 生产商: 灌溉科技",
                price = 2000.0,
                imageResId = R.drawable.ic_launcher_foreground,
                category = "设备"
            ),
            Product(
                id = "4",
                name = "营养液",
                description = "类型: 肥料 | 生产商: 植物营养",
                price = 80.0,
                imageResId = R.drawable.ic_launcher_foreground,
                category = "肥料"
            ),
            Product(
                id = "5",
                name = "种植容器",
                description = "类型: 工具 | 生产商: 农具制造",
                price = 50.0,
                imageResId = R.drawable.ic_launcher_foreground,
                category = "工具"
            )
        )
    }
    
    // 根据搜索文本过滤产品
    val filteredProducts = if (searchText.isEmpty()) {
        allProducts
    } else {
        allProducts.filter { product ->
            product.name.contains(searchText, ignoreCase = true) ||
            product.description.contains(searchText, ignoreCase = true) ||
            product.category.contains(searchText, ignoreCase = true)
        }
    }
    
    Scaffold(
        topBar = {
            AppTopBar(
                title = "投入品采购",
                navController = navController,
                showBackButton = true,
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Shopping.route) }) {
                        Box {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "购物车",
                                tint = Color.White
                            )
                            
                            if (CartManager.totalItems > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(Color.Red, CircleShape)
                                        .align(Alignment.TopEnd),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = CartManager.totalItems.toString(),
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("搜索投入品...") },
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
            
            if (filteredProducts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "没有找到匹配的投入品",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                Text(
                    text = "可选投入品 (${filteredProducts.size})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductItemCard(
                            product = product,
                            onItemClick = {
                                navController.navigate(Screen.InputPurchaseDetail.route)
                            },
                            onAddToCart = {
                                CartManager.addProduct(product)
                                scope.launch {
                                    snackbarHostState.showSnackbar("已添加 ${product.name} 到购物车")
                                }
                            }
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItemCard(
    product: Product,
    onItemClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
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
                    .background(Color(0xFFFFC107).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color(0xFFFFC107)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "价格: ¥${product.price}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "添加到购物车"
                )
                Text(text = "购买")
            }
        }
    }
}

// 添加Preview函数
@Preview(showBackground = true)
@Composable
fun InputPurchaseScreenPreview() {
    Temporal_useTheme {
        InputPurchaseScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemCardPreview() {
    Temporal_useTheme {
        ProductItemCard(
            product = Product(
                id = "1",
                name = "有机肥料",
                description = "类型: 肥料 | 生产商: 绿色农资",
                price = 200.0,
                imageResId = R.drawable.ic_launcher_foreground,
                category = "肥料"
            ),
            onItemClick = {},
            onAddToCart = {}
        )
    }
} 