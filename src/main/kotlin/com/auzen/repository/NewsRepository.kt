package com.auzen.repository

import com.auzen.model.NewsModel
import org.springframework.data.mongodb.repository.MongoRepository

public open interface NewsRepository : MongoRepository<NewsModel, String> {
    fun getNewsModelByIdOrUrl(id: String, url: String): NewsModel
}