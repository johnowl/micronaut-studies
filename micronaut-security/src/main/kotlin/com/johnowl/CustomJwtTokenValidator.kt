package com.johnowl

import io.micronaut.context.annotation.Replaces
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.token.jwt.validator.JwtTokenValidator
import io.micronaut.security.token.validator.TokenValidator
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
@Replaces(TokenValidator::class)
class CustomJwtTokenValidator : TokenValidator {

    companion object {
        private val logger = LoggerFactory.getLogger(CustomJwtTokenValidator::class.java)
    }

    override fun validateToken(token: String?): Publisher<Authentication> {
        logger.info("CustomJwtTokenValidator->Validate")
        // do validation
        return Flowable.just(CustomAuthentication())
    }
}