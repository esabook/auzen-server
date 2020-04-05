package com.auzen.model

open class NewsModel {
    var id: Int? = hashCode()
    var url: String? = null
    var posted_timestamp: Long = 0
    var access_first_timestamp: Long = 0
    var access_last_timestamp: Long = 0
    var total_access: Int = 0
    var paragraphs: List<ParagraphModel?> = listOf()
}