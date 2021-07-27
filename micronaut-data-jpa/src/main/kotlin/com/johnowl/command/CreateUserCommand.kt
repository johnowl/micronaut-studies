package com.johnowl.command

import io.micronaut.core.annotation.Introspected

@Introspected
data class CreateUserCommand(
    val name: String,
    val email: String
)