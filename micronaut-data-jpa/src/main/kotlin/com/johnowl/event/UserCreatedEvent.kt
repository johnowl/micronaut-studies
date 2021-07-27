package com.johnowl.event

import com.johnowl.User
import io.micronaut.core.annotation.Introspected
import java.time.Clock
import java.time.LocalDateTime

@Introspected
data class UserCreatedEvent(
    val occurredAt: LocalDateTime = LocalDateTime.now(Clock.systemUTC()),
    val user: User
)