package com.johnowl

import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory

@Filter("/**")
class MyFilter : HttpServerFilter {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun doFilter(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {

        logger.info(request.attributes.toString())
        return chain.proceed(request)
    }
}