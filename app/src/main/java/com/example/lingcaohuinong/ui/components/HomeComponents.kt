package com.example.lingcaohuinong.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.lingcaohuinong.R
import com.example.temporal_use.R
import com.example.lingcaohuinong.model.CarouselItem
import com.example.lingcaohuinong.model.HerbItem
import com.example.lingcaohuinong.model.NewsItem
import com.example.lingcaohuinong.utils.ExternalLinks
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
// md废弃api这么多那我写什么

@Composable
fun HomeSearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("搜索中草药、农资、市场行情...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "搜索"
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        keyboardActions = androidx.compose.foundation.text.KeyboardActions(
            onDone = { onSearch() }
        )
    )
}

@Composable
fun AutoCarousel(
    items: List<CarouselItem>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    
    // 轮播
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            if (items.isNotEmpty()) {
                val nextPage = (pagerState.currentPage + 1) % items.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(16.dp)
    ) {
        if (items.isNotEmpty()) {
            HorizontalPager(
                count = items.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            ) { page ->
                val item = items[page]
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            item.url?.let { url ->
                                ExternalLinks.openExternalLink(context, url)
                            }
                        }
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = item.title,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // 指示器
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun PopularHerbsList(
    herbs: List<HerbItem>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = "热门中草药/农作物",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(herbs) { herb ->
                HerbCard(
                    herb = herb,
                    onClick = {
                        herb.url?.let { url ->
                            ExternalLinks.openExternalLink(context, url)
                        }
                    }
                )
            }
        }
    }
}

/**
 * 中草药/农作物卡片
 */
@Composable
fun HerbCard(
    herb: HerbItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = herb.imageRes),
                contentDescription = herb.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = herb.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = herb.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

/**
 * 市场新闻列表
 */
@Composable
fun MarketNewsList(
    news: List<NewsItem>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = "市场新闻",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        news.forEach { newsItem ->
            NewsCard(
                news = newsItem,
                onClick = {
                    newsItem.url?.let { url ->
                        ExternalLinks.openExternalLink(context, url)
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}

/**
 * 新闻卡片
 */
@Composable
fun NewsCard(
    news: NewsItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = news.imageRes),
                contentDescription = news.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = news.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = news.summary,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Text(
                    text = dateFormat.format(news.publishTime),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

// 修改Preview函数
@Preview(showBackground = true)
@Composable
private fun HomeSearchBarPreview() {
    MaterialTheme {
        HomeSearchBar(
            searchText = "人参",
            onSearchTextChange = {},
            onSearch = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AutoCarouselPreview() {
    MaterialTheme {
        AutoCarousel(
            items = listOf(
                CarouselItem(
                    id = 1,
                    title = "GAP基地认证申请开放",
                    imageRes = R.drawable.ic_launcher_foreground,
                    url = "https://www.baidu.com"
                ),
                CarouselItem(
                    id = 2,
                    title = "优质种苗预售",
                    imageRes = R.drawable.ic_launcher_foreground,
                    url = "https://www.baidu.com"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HerbCardPreview() {
    MaterialTheme {
        HerbCard(
            herb = HerbItem(
                id = 1,
                name = "人参",
                imageRes = R.drawable.ic_launcher_foreground,
                description = "补气养血，安神益智",
                url = "https://baike.baidu.com"
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PopularHerbsListPreview() {
    MaterialTheme {
        PopularHerbsList(
            herbs = listOf(
                HerbItem(
                    id = 1,
                    name = "人参",
                    imageRes = R.drawable.ic_launcher_foreground,
                    description = "补气养血，安神益智",
                    url = "https://baike.baidu.com"
                ),
                HerbItem(
                    id = 2,
                    name = "当归",
                    imageRes = R.drawable.ic_launcher_foreground,
                    description = "补血调经，活血止痛",
                    url = "https://baike.baidu.com"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsCardPreview() {
    MaterialTheme {
        NewsCard(
            news = NewsItem(
                id = 1,
                title = "中药材市场行情分析：人参价格稳步上涨",
                summary = "近期人参市场需求旺盛，价格呈现稳步上涨趋势，专家预测后市仍有上涨空间。",
                imageRes = R.drawable.ic_launcher_foreground,
                publishTime = Date(),
                url = "https://www.baidu.com"
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MarketNewsListPreview() {
    MaterialTheme {
        MarketNewsList(
            news = listOf(
                NewsItem(
                    id = 1,
                    title = "中药材市场行情分析：人参价格稳步上涨",
                    summary = "近期人参市场需求旺盛，价格呈现稳步上涨趋势，专家预测后市仍有上涨空间。",
                    imageRes = R.drawable.ic_launcher_foreground,
                    publishTime = Date(),
                    url = "https://www.baidu.com"
                ),
                NewsItem(
                    id = 2,
                    title = "有机种植技术研讨会在京召开",
                    summary = "全国中药材有机种植技术研讨会在北京召开，多位专家分享了最新的有机种植技术和经验。",
                    imageRes = R.drawable.ic_launcher_foreground,
                    publishTime = Date(),
                    url = "https://www.baidu.com"
                )
            )
        )
    }
} 