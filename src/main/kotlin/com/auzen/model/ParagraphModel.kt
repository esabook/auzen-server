package com.auzen.model

class ParagraphModel {
    var id: Int? = hashCode()
    var news_id: Int? = null
    var paragraph_previous_id: Int? = null
    var paragraph_next_id: Int? = null
    var content: String? = null
    var access_first_timestamp: Long = 0;
    var access_last_timestamp: Long = 0;
}
