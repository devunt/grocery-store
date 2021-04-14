package kr.devunt.grocerystore

import kr.devunt.grocerystore.service.CategoryService
import kr.devunt.grocerystore.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Configuration
class SecurityConfiguration(
    private val jwtService: JwtService,
    private val categoryService: CategoryService,
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http {
            cors { }
            csrf { disable() }
            logout { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }

            authorizeRequests {
                authorize("/token", permitAll)
                authorize(anyRequest, authenticated)
            }

            oauth2ResourceServer {
                jwt { }

                bearerTokenResolver = BearerTokenResolver {
                    it.getHeader("authorization")
                }

                authenticationEntryPoint = AuthenticationEntryPoint { _, response, exception ->
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.toString())
                }

                accessDeniedHandler = AccessDeniedHandler { _, response, exception ->
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.toString())
                }
            }
        }
    }

    @Bean
    fun jwtDecoder() = NimbusJwtDecoder.withPublicKey(jwtService.publicKey).build().apply {
        setJwtValidator {
            val request = (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
            if (request != null) {
                val category = categoryService.get(request.getHeader("host"))
                if (!it.audience.contains(category.slug)) {
                    return@setJwtValidator OAuth2TokenValidatorResult.failure(OAuth2Error("invalid_audience"))
                }
            }
            return@setJwtValidator OAuth2TokenValidatorResult.success()
        }
    }
}
