package com.auzen.config

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate


@Configuration
class MongoConfig {
    @Bean
    fun mongo(): MongoClient {
//        val mongoUri = "mongodb+srv://auzen-egit:<password>@auzen-db-a6pl0.mongodb.net/auzen?retryWrites=true&w=majority"
        val mongoUri = "mongodb://localhost:27017/"
        val mongoClientURI = MongoClientURI(mongoUri)
        return MongoClient(mongoClientURI)
    }

    @Bean
    @Throws(Exception::class)
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(mongo(), "test")
    }

}