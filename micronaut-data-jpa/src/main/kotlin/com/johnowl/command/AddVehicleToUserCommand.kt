package com.johnowl.command

import io.micronaut.core.annotation.Introspected

@Introspected
class AddVehicleToUserCommand(
    val brand: String,
    val model: String,
    val year: Int,
    val color: String
)