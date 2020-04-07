package com.auzen.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class RootController {

    @RequestMapping("/help")
    fun getRoot(req: HttpServletRequest, res: HttpServletResponse) = res.sendRedirect("/swagger-ui.html")

}
