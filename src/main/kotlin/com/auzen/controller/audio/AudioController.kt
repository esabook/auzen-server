package com.auzen.controller.audio

import com.auzen.controller.news.NewsRequestModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("audio")
class AudioController {
    @GetMapping("{paragrafHash}")
    fun getAudio(@PathVariable("paragrafHash") paragrafHash: String): String {
        return paragrafHash;
    }

    @PostMapping()
    fun postParagraf(@RequestBody newsInfo: NewsRequestModel): String {
        return "ResponseEntity(newsInfo, HttpStatus.OK)"
    }
}