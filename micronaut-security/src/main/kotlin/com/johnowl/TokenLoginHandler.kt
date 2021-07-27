package com.johnowl

import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import io.micronaut.security.token.jwt.bearer.AccessRefreshTokenLoginHandler
import io.micronaut.security.token.jwt.generator.AccessRefreshTokenGenerator
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
@Replaces(AccessRefreshTokenLoginHandler::class)
class TokenLoginHandler(tokenGenerator: AccessRefreshTokenGenerator) : AccessRefreshTokenLoginHandler(tokenGenerator) {

    private val logger = LoggerFactory.getLogger(this::class.java)


    // se o único motivo da reescrita for para saber os eventos que aconteceram, é melhor escutar os eventos
    // https://micronaut-projects.github.io/micronaut-security/latest/guide/#securityEvents
    override fun loginSuccess(userDetails: UserDetails?, request: HttpRequest<*>?): MutableHttpResponse<*> {
        // Registra login com sucesso
        logger.info("Sucesso!")
        return super.loginSuccess(userDetails, request)
    }

    override fun loginRefresh(userDetails: UserDetails?, refreshToken: String?, request: HttpRequest<*>?): MutableHttpResponse<*> {
        // Registra login com sucesso
        logger.info("Sucesso!")
        return super.loginRefresh(userDetails, refreshToken, request)
    }

    override fun loginFailed(authenticationResponse: AuthenticationResponse?, request: HttpRequest<*>?): MutableHttpResponse<*> {
        // incrementa contador de falhas
        logger.warn("Deu ruim!")
        return super.loginFailed(authenticationResponse, request)
    }
}