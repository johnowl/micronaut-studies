package com.johnowl

import io.micronaut.security.authentication.Authentication

class CustomAuthentication : Authentication {

    override fun getName(): String {
        return "john.doe"
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf("roles" to listOf("ADMIN"), "sub" to "john.doe")
    }
}