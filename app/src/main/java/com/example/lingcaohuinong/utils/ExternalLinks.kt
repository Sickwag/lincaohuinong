package com.example.lingcaohuinong.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * 外部链接管理类
 * 用于管理应用中所有跳转到外部的链接
 */
object ExternalLinks {
    
    // 热门中草药和农作物链接
    val HERB_LINKS = mapOf(
        "人参" to "https://baike.baidu.com/item/%E4%BA%BA%E5%8F%82/13276",
        "当归" to "https://baike.baidu.com/item/%E5%BD%93%E5%BD%92/1127",
        "黄芪" to "https://baike.baidu.com/item/%E9%BB%84%E8%8A%AA/753453",
        "枸杞" to "https://baike.baidu.com/item/%E6%9E%B8%E6%9D%9E/2748",
        "灵芝" to "https://baike.baidu.com/item/%E7%81%B5%E8%8A%9D/396123",
        "西洋参" to "https://baike.baidu.com/item/%E8%A5%BF%E6%B4%8B%E5%8F%82/1035"
    )
    
    // 市场新闻链接
    val NEWS_LINKS = mapOf(
        "中药材市场行情分析：人参价格稳步上涨" to "https://www.baidu.com/s?wd=%E4%B8%AD%E8%8D%AF%E6%9D%90%E5%B8%82%E5%9C%BA%E8%A1%8C%E6%83%85%E5%88%86%E6%9E%90",
        "有机种植技术研讨会在京召开" to "https://www.baidu.com/s?wd=%E6%9C%89%E6%9C%BA%E7%A7%8D%E6%A4%8D%E6%8A%80%E6%9C%AF%E7%A0%94%E8%AE%A8%E4%BC%9A",
        "新型农业补贴政策出台，中药材种植户受益" to "https://www.baidu.com/s?wd=%E6%96%B0%E5%9E%8B%E5%86%9C%E4%B8%9A%E8%A1%A5%E8%B4%B4%E6%94%BF%E7%AD%96"
    )
    
    /**
     * 打开外部链接
     * @param context 上下文
     * @param url 链接地址
     */
    fun openExternalLink(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
    
    /**
     * 根据中草药名称获取对应链接
     * @param herbName 中草药名称
     * @return 对应的链接，如果不存在则返回百度搜索该中草药的链接
     */
    fun getHerbLink(herbName: String): String {
        return HERB_LINKS[herbName] ?: "https://www.baidu.com/s?wd=$herbName"
    }
    
    /**
     * 根据新闻标题获取对应链接
     * @param newsTitle 新闻标题
     * @return 对应的链接，如果不存在则返回百度搜索该新闻的链接
     */
    fun getNewsLink(newsTitle: String): String {
        return NEWS_LINKS[newsTitle] ?: "https://www.baidu.com/s?wd=$newsTitle"
    }
    
    /**
     * 打开中草药相关链接
     * @param context 上下文
     * @param herbName 中草药名称
     */
    fun openHerbLink(context: Context, herbName: String) {
        openExternalLink(context, getHerbLink(herbName))
    }
    
    /**
     * 打开新闻相关链接
     * @param context 上下文
     * @param newsTitle 新闻标题
     */
    fun openNewsLink(context: Context, newsTitle: String) {
        openExternalLink(context, getNewsLink(newsTitle))
    }
} 