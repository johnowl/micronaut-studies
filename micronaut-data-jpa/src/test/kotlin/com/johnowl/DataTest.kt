package com.johnowl
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class DataTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Inject
    lateinit var userRepository: UserRepository

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

    @Test
    fun testing() {
        val user1 = User(
            name = "João Paulo",
            email = "johnowl@gmail.com"
        )

        userRepository.save(user1)
    }

}
