package com.johnowl

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HelloController {

    @Get("/hello")
    fun index(): String {
        return "Hello!"
    }

    @Get("/hello/world")
    fun world(): String {
        return "Hello World!"
    }

    @Get("/hello/{message}")
    fun message(message: String): String {
        return "Hello $message!"
    }

}