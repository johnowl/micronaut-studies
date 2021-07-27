package com.johnowl.command

import io.micronaut.core.annotation.Introspected

@Introspected
data class UpdateUserPartiallyCommand(
    val name: String?,
    val email: String?
) {
    fun allFieldsAreNull(): Boolean {
        return this.name == null && this.email == null
    }
}