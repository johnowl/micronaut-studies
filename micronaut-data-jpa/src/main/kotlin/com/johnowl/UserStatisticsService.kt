package com.johnowl

import com.johnowl.event.UserCreatedEvent
import com.johnowl.event.UserDeletedEvent
import com.johnowl.event.UserUpdatedEvent
import io.micronaut.runtime.event.annotation.EventListener
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Singleton

@Singleton
class UserStatisticsService {

    private val totalUsers = AtomicLong(0)
    private val totalUpdates = AtomicLong(0)
    private val totalDeletes = AtomicLong(0)
    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventListener
    fun on(userCreatedEvent: UserCreatedEvent) {
        val total = totalUsers.incrementAndGet()
        logger.info("Total users added is $total")
    }

    @EventListener
    fun on(userUpdatedEvent: UserUpdatedEvent) {
        val total = totalUpdates.incrementAndGet()
        logger.info("Total users updated is $total")
    }

    @EventListener
    fun on(userDeletedEvent: UserDeletedEvent) {
        val total = totalDeletes.incrementAndGet()
        logger.info("Total users deleted is $total")
    }
}