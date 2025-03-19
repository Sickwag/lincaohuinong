package com.example.lingcaohuinong.model

import androidx.annotation.DrawableRes
import java.util.Date

data class CarouselItem(
    val id: Int,
    val title: String,
    @DrawableRes val imageRes: Int,
    val url: String? = null
)

data class HerbItem(
    val id: Int,
    val name: String,
    @DrawableRes val imageRes: Int,
    val description: String,
    val url: String? = null
)

data class NewsItem(
    val id: Int,
    val title: String,
    val summary: String,
    @DrawableRes val imageRes: Int,
    val publishTime: Date,
    val url: String? = null
) 