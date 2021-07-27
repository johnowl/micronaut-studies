package com.johnowl

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import org.reactivestreams.Publisher
import javax.inject.Singleton


@Singleton
class UserAuthenticationProvider : AuthenticationProvider {

    override fun authenticate(httpRequest: HttpRequest<*>?, authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse?> {
        return Maybe.create { emitter: MaybeEmitter<AuthenticationResponse?> ->
            if (authenticationRequest.identity == "user" && authenticationRequest.secret == "password") {
                emitter.onSuccess(UserDetails("user", arrayListOf("ADMIN")))
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
            }
        }.toFlowable()
    }
}
