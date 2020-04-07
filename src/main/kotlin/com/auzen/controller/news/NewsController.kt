package com.auzen.controller.news

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("news")
class NewsController {

    @GetMapping("{newsId}")
    fun getInfo(@PathVariable("newsId") newsId: String): NewsResponseModel {
        val ret = NewsResponseModel()
//        ret.id = newsId.hashCode().toString()
//        ret.url = newsId
        return ret
    }

    @PostMapping("")
    fun postNews(@RequestBody newsInfo: NewsRequestModel): NewsResponseModel {
        return getInfo(newsInfo.url.orEmpty())
    }
}