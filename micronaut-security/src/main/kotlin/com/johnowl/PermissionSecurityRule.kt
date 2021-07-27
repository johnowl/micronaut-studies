package com.johnowl

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.http.HttpRequest
import io.micronaut.security.rules.SecurityRule
import io.micronaut.security.rules.SecurityRuleResult
import io.micronaut.web.router.MethodBasedRouteMatch
import io.micronaut.web.router.RouteMatch
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class PermissionSecurityRule : SecurityRule {

    companion object {

        private val logger = LoggerFactory.getLogger(this::class.java)

        private val permissionsByRole = mapOf(
            "ADMIN" to listOf("user:read", "user:list", "user:create", "user:update"),
            "USER" to listOf("user:read", "user:list"),
        )
    }

    override fun check(request: HttpRequest<*>?, routeMatch: RouteMatch<*>?, claims: Map<String?, Any?>?): SecurityRuleResult {
        if (routeMatch is MethodBasedRouteMatch<*, *> && claims != null) {
            if (routeMatch.hasAnnotation(Permission::class.java)) {
                val permissionAnnotation: AnnotationValue<Permission> = routeMatch.getAnnotation(Permission::class.java)
                val resourcePermission = permissionAnnotation.stringValue("permission").get()
                val userRoles = claims["roles"] as List<String>

                val hasPermission = permissionsByRole
                    .filter { userRoles.contains(it.key) }
                    .filter { it.value.contains(resourcePermission) }
                    .any()

                return if (hasPermission) {
                    logger.debug("Acesso OK para usuário ${claims["sub"]}")
                    SecurityRuleResult.ALLOWED
                } else {
                    logger.debug("Acesso NOK para usuário ${claims["sub"]}")
                    SecurityRuleResult.REJECTED
                }
            }
        }
        return SecurityRuleResult.UNKNOWN
    }
}
