package com.johnowl.command

import io.micronaut.core.annotation.Introspected

@Introspected
data class UpdateUserCommand(
    val name: String,
    val email: String
)