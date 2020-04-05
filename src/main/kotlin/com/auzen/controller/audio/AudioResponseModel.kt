package com.auzen.controller.audio

class AudioResponseModel(
        val url: String?,
        val total_chars: Int = 0,
        val total_paragraf: Int = 0,
        val data: Map<Int, String>
)