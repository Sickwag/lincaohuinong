package com.example.lingcaohuinong.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.example.lingcaohuinong.R
import com.example.temporal_use.R
import com.example.lingcaohuinong.ui.navigation.Screen
import com.example.lingcaohuinong.ui.theme.GreenPrimary
import kotlinx.coroutines.delay
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import com.example.lingcaohuinong.data.HomeData
import com.example.lingcaohuinong.ui.components.*
import com.example.lingcaohuinong.utils.ExternalLinks
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

data class FeatureItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)

data class HerbItem(
    val name: String,
    val imageResId: Int,
    val description: String
)

data class NewsItem(
    val title: String,
    val imageResId: Int,
    val date: String,
    val summary: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    
    // 获取数据
    val carouselItems = HomeData.getCarouselItems()
    val popularHerbs = HomeData.getPopularHerbs()
    val marketNews = HomeData.getMarketNews()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // 顶部搜索栏
        HomeSearchBar(
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            onSearch = {
                // 处理搜索操作
                if (searchText.isNotEmpty()) {
                    ExternalLinks.openExternalLink(
                        context,
                        "https://www.baidu.com/s?wd=$searchText"
                    )
                }
            }
        )
        
        // 轮播广告
        AutoCarousel(items = carouselItems)
        
        // 热门中草药/农作物
        PopularHerbsList(herbs = popularHerbs)
        
        // 市场新闻
        MarketNewsList(news = marketNews)
        
        // 底部间距
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun BannerCarousel(bannerImages: List<Int>) {
    var currentPage by remember { mutableIntStateOf(0) }
    
    // 自动轮播
    LaunchedEffect(Unit) {
        while(true) {
            delay(3000)
            currentPage = (currentPage + 1) % bannerImages.size
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painterResource(id = bannerImages[currentPage]),
                contentDescription = "Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // 指示器
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                bannerImages.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (currentPage == index) GreenPrimary
                                else GreenPrimary.copy(alpha = 0.3f)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun FeaturesSection(features: List<FeatureItem>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "主要功能",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.height(200.dp)
        ) {
            items(features) { feature ->
                FeatureCard(
                    feature = feature,
                    onClick = {
                        navController.navigate(feature.route)
                    }
                )
            }
        }
    }
}

@Composable
fun PopularHerbsSection(herbs: List<HerbItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "热门中草药和农作物",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(herbs) { herb ->
                HerbCard(herb = herb)
            }
        }
    }
}

@Composable
fun MarketNewsSection(news: List<NewsItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "市场动态",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        news.forEach { newsItem ->
            NewsCard(newsItem = newsItem)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun FeatureCard(
    feature: FeatureItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(feature.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = feature.icon,
                    contentDescription = feature.title,
                    tint = feature.color,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = feature.title,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = GreenPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun HerbCard(herb: HerbItem) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { /* TODO: 导航到详情页 */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Image(
                painter = painterResource(id = herb.imageResId),
                contentDescription = herb.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = herb.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = GreenPrimary
                )
                
                Text(
                    text = herb.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: 导航到新闻详情页 */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = newsItem.imageResId),
                contentDescription = newsItem.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = newsItem.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = newsItem.summary,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "更新时间: ${newsItem.date}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
        }
    }
}

// 添加Preview函数
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerCarouselPreview() {
    MaterialTheme {
        BannerCarousel(bannerImages = listOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground))
    }
}

@Preview(showBackground = true)
@Composable
private fun FeaturesSectionPreview() {
    MaterialTheme {
        FeaturesSection(
            features = listOf(
                FeatureItem(
                    "GAP基地选择",
                    Icons.Default.Agriculture,
                    Screen.GapBase.route,
                    GreenPrimary
                ),
                FeatureItem(
                    "种子种苗供应",
                    Icons.Default.Grass,
                    Screen.SeedSupply.route,
                    GreenPrimary
                )
            ),
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FeatureCardPreview() {
    MaterialTheme {
        FeatureCard(
            feature = FeatureItem(
                "GAP基地选择",
                Icons.Default.Agriculture,
                Screen.GapBase.route,
                GreenPrimary
            ),
            onClick = {}
        )
    }
} 