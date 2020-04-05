package com.auzen.controller.error

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("errors")
class ErrorController {

    @GetMapping("", "{errorType}")
    fun getTroubleShootingGuide(@PathVariable("errorType") errorType: String): String {
        return "Not yet implemented, error type: ${errorType}"
    }
}