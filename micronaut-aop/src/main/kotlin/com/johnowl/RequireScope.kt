package com.johnowl

import io.micronaut.aop.Around

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@Around
annotation class RequireScope(val scope: String)