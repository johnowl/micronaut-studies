package com.johnowl

import com.fasterxml.jackson.annotation.JsonFormat
import com.johnowl.command.CreateUserCommand
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.*

@Introspected
@Entity
data class User(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val name: String,
    val email: String,
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val vehicles: List<Vehicle> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(Clock.systemUTC()),
    val updatedAt: LocalDateTime = LocalDateTime.now(Clock.systemUTC())
) {

    fun addVehicle(vehicle: Vehicle) {
        val mutableList = this.vehicles as MutableList
        mutableList.add(vehicle.copy(user = this))
    }

    fun findLastAddedVehicle(): Vehicle? {
        return vehicles.maxByOrNull { it.createdAt }
    }
}