package com.auzen.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@ApiIgnore
class RootController {

    @RequestMapping("/help", method = [RequestMethod.GET])
    fun getRoot(req: HttpServletRequest, res: HttpServletResponse) = res.sendRedirect("/swagger-ui.html")

}
