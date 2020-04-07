package com.auzen.controller.news

import com.auzen.model.NewsModel

class NewsResponseModel() {
    val total_chars: Int = 0
    val total_estimated_reading_timemilis: Long = 0
    lateinit var news: NewsModel
}