package com.johnowl

import com.fasterxml.jackson.annotation.JsonIgnore
import io.micronaut.core.annotation.Introspected
import java.time.Clock
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Introspected
data class Vehicle(
    @Id
    @GeneratedValue
    val id: Long? = null,
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "id", nullable = false)
    val user: User? = null,
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val createdAt: LocalDateTime = LocalDateTime.now(Clock.systemUTC()),
    val updatedAt: LocalDateTime = LocalDateTime.now(Clock.systemUTC())
)