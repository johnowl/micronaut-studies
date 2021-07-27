package com.johnowl

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
open class ResourceController {

    @Get("/hello")
    @RequireScope("hello:read")
    open fun hello(): String {
        return "Hello world!"
    }

}