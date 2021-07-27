package com.johnowl

import com.johnowl.command.AddVehicleToUserCommand
import com.johnowl.command.CreateUserCommand
import com.johnowl.command.UpdateUserCommand
import com.johnowl.command.UpdateUserPartiallyCommand
import com.johnowl.event.UserCreatedEvent
import com.johnowl.event.UserDeletedEvent
import com.johnowl.event.UserUpdatedEvent
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.time.Clock
import java.time.LocalDateTime
import javax.transaction.Transactional

@Controller("/v1/users")
open class UserController(
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Get("/")
    fun listAll(): List<User> {
        return userRepository.findAll().toList()
    }

    @Post("/")
    fun createUser(createUserCommand: CreateUserCommand): HttpResponse<User> {

        val user = User(
            name = createUserCommand.name,
            email = createUserCommand.email
        )
        userRepository.save(user)
        applicationEventPublisher.publishEvent(UserCreatedEvent(user = user))

        return HttpResponse.created(user).header("Location", "/v1/users/${user.id}")
    }

    @Put("/{userId}")
    fun updateUser(@PathVariable userId: Long, @Body updateUserCommand: UpdateUserCommand): User {

        val dbUser = userRepository.findById(userId)
        if (dbUser.isEmpty)
            throw UserNotFoundException()

        val originalUser = dbUser.get()
        val updatedUser = originalUser.copy(
            name = updateUserCommand.name,
            email = updateUserCommand.email,
            updatedAt = LocalDateTime.now(Clock.systemUTC())
        )

        userRepository.update(updatedUser)
        applicationEventPublisher.publishEvent(UserUpdatedEvent(user = updatedUser))

        return updatedUser
    }


    @Put("/{userId}/vehicles")
    @Transactional
    open fun addVehicle(@PathVariable userId: Long, @Body command: AddVehicleToUserCommand): HttpResponse<Vehicle> {

        val user = userRepository.findById(userId).orElse(null) ?: throw UserNotFoundException()

        user.addVehicle(Vehicle(
            brand = command.brand,
            color = command.color,
            model = command.model,
            year = command.year
        ))

        userRepository.update(user)

        val vehicleAdded = user.findLastAddedVehicle() ?: throw VehicleNotFoundError()

        return HttpResponse
            .created(vehicleAdded)
            .header("Location", "/v1/users/${userId}/vehicles/${vehicleAdded.id}")
    }

    @Patch("/{userId}")
    fun updateUserPartially(@PathVariable userId: Long, @Body updateUserCommand: UpdateUserPartiallyCommand): User {

        val user = userRepository.findById(userId).orElse(null) ?: throw UserNotFoundException()
        
        if (updateUserCommand.allFieldsAreNull())
            return user

        val updatedUser = user.copy(
            name = updateUserCommand.name ?: user.name,
            email = updateUserCommand.email ?: user.email,
            updatedAt = LocalDateTime.now(Clock.systemUTC())
        )

        userRepository.update(updatedUser)
        applicationEventPublisher.publishEvent(UserUpdatedEvent(user = updatedUser))

        return updatedUser
    }

    @Delete("/{userId}")
    fun deleteUser(@PathVariable userId: Long): HttpResponse<Any> {
        val dbUser = userRepository.findById(userId)
        if (dbUser.isPresent) {
            userRepository.deleteById(userId)
            applicationEventPublisher.publishEvent(UserDeletedEvent(user = dbUser.get()))
        }

        return HttpResponse.noContent()
    }
}




