package com.auzen.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class RootController {

    @GetMapping("/help")
    fun getRoot() = "help"

    @GetMapping("/throw")
    fun getThrow() {
        throw Exception("from getThrow()")
    }

    @GetMapping("/error")
    fun getError(req: HttpServletRequest) = "silent"

}
