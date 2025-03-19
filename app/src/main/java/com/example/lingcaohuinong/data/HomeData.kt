package com.example.lingcaohuinong.data

import com.example.lingcaohuinong.model.CarouselItem
import com.example.lingcaohuinong.model.HerbItem
import com.example.lingcaohuinong.model.NewsItem
import com.example.temporal_use.R
import java.util.Date
import java.util.concurrent.TimeUnit


object HomeData {
    
    
    fun getCarouselItems(): List<CarouselItem> {
        return listOf(
            CarouselItem(
                id = 1,
                title = "GAP基地认证申请开放",
                imageRes = R.drawable.ic_launcher_foreground,
                url = "https://www.baidu.com/s?wd=GAP%E5%9F%BA%E5%9C%B0%E8%AE%A4%E8%AF%81"
            ),
            CarouselItem(
                id = 2,
                title = "优质种苗预售",
                imageRes = R.drawable.ic_launcher_foreground,
                url = "https://www.baidu.com/s?wd=%E4%B8%AD%E8%8D%AF%E6%9D%90%E7%A7%8D%E8%8B%97"
            ),
            CarouselItem(
                id = 3,
                title = "有机肥料特惠活动",
                imageRes = R.drawable.ic_launcher_foreground,
                url = "https://www.baidu.com/s?wd=%E6%9C%89%E6%9C%BA%E8%82%A5%E6%96%99"
            )
        )
    }

    
    fun getPopularHerbs(): List<HerbItem> {
        return listOf(
            HerbItem(
                id = 1,
                name = "人参",
                imageRes = R.drawable.ic_launcher_foreground,
                description = "补气养血，安神益智",
                url = "https://baike.baidu.com/item/%E4%BA%BA%E5%8F%82/13276"
            ),
            HerbItem(
                id = 2,
                name = "当归",
                imageRes = R.drawable.ic_launcher_foreground,
                description = "补血调经，活血止痛",
                url = "https://baike.baidu.com/item/%E5%BD%93%E5%BD%92/1127"
            ),
            HerbItem(
                id = 3,
                name = "黄芪",
                imageRes = R.drawable.ic_launcher_foreground,
                description = "补气固表，利水消肿",
                url = "https://baike.baidu.com/item/%E9%BB%84%E8%8A%AA/753453"
            ),
            HerbItem(
                id = 4,
                name = "枸杞",
                imageRes = R.drawable.ic_launcher_foreground,
                description = "滋补肝肾，明目",
                url = "https://baike.baidu.com/item/%E6%9E%B8%E6%9D%9E/2748"
            ),
            HerbItem(
                id = 5,
                name = "灵芝",
                imageRes = R.drawable.ic_launcher_foreground,
                description = "扶正固本，增强免疫力",
                url = "https://baike.baidu.com/item/%E7%81%B5%E8%8A%9D/396123"
            ),
            HerbItem(
                id = 6,
                name = "西洋参",
                imageRes = R.drawable.ic_launcher_foreground,
                description = "清热生津，滋阴降火",
                url = "https://baike.baidu.com/item/%E8%A5%BF%E6%B4%8B%E5%8F%82/1035"
            )
        )
    }

    
    fun getMarketNews(): List<NewsItem> {
        val currentTime = Date()

        return listOf(
            NewsItem(
                id = 1,
                title = "中药材市场行情分析：人参价格稳步上涨",
                summary = "近期人参市场需求旺盛，价格呈现稳步上涨趋势，专家预测后市仍有上涨空间。",
                imageRes = R.drawable.ic_launcher_foreground,
                publishTime = Date(currentTime.time - TimeUnit.HOURS.toMillis(2)),
                url = "https://www.baidu.com/s?wd=%E4%B8%AD%E8%8D%AF%E6%9D%90%E5%B8%82%E5%9C%BA%E8%A1%8C%E6%83%85%E5%88%86%E6%9E%90"
            ),
            NewsItem(
                id = 2,
                title = "有机种植技术研讨会在京召开",
                summary = "全国中药材有机种植技术研讨会在北京召开，多位专家分享了最新的有机种植技术和经验。",
                imageRes = R.drawable.ic_launcher_foreground,
                publishTime = Date(currentTime.time - TimeUnit.DAYS.toMillis(1)),
                url = "https://www.baidu.com/s?wd=%E6%9C%89%E6%9C%BA%E7%A7%8D%E6%A4%8D%E6%8A%80%E6%9C%AF%E7%A0%94%E8%AE%A8%E4%BC%9A"
            ),
            NewsItem(
                id = 3,
                title = "新型农业补贴政策出台，中药材种植户受益",
                summary = "国家发布新型农业补贴政策，中药材种植户将获得更多支持，促进中药材产业健康发展。",
                imageRes = R.drawable.ic_launcher_foreground,
                publishTime = Date(currentTime.time - TimeUnit.DAYS.toMillis(3)),
                url = "https://www.baidu.com/s?wd=%E6%96%B0%E5%9E%8B%E5%86%9C%E4%B8%9A%E8%A1%A5%E8%B4%B4%E6%94%BF%E7%AD%96"
            )
        )
    }
} 