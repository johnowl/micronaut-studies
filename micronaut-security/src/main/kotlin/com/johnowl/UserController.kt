package com.johnowl

import io.micronaut.http.annotation.*

@Controller("/v1/users")
class UserController {

    @Get("/")
    @Permission("user:list")
    fun list(): List<String> {
        return listOf("user1", "user2", "user3")
    }

    @Get("/{username}")
    @Permission("user:read")
    fun get(username: String): String {
        return username
    }

    @Post("/")
    @Permission("user:create")
    fun add(): String {
        return "user"
    }

    @Patch("/{username}")
    @Permission("user:update")
    fun update(username: String): String {
        return username
    }

    @Put("/{username}")
    @Permission("user:update")
    fun replace(username: String): String {
        return username
    }
}