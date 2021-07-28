package com.johnowl

import io.micronaut.core.annotation.Introspected
import java.time.Clock
import java.time.LocalDateTime
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