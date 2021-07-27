package com.johnowl

import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import javax.inject.Singleton

@Singleton
@InterceptorBean(RequireScope::class)
class RequireScopeInterceptor(
    private val tokenService: TokenService
) : MethodInterceptor<Any, Any> {

    override fun intercept(context: MethodInvocationContext<Any, Any>): Any {

        val annotation = context
            .annotationMetadata
            .getAnnotation(RequireScope::class.java)

        val scope = annotation.values["scope"] as String
        if (scope.isNotBlank()) {
            tokenService.validate(scope)
        }

        return context.proceed()
    }


}