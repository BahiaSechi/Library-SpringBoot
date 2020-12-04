package com.ensicaen.springbootlibrary.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest()
                .permitAll()
                .and()
                .cors()
                .configurationSource(buildCorsConfigurationSource())
                .and()
                .csrf()
                .disable()
    }

    private fun buildCorsConfigurationSource() =
            UrlBasedCorsConfigurationSource().apply {
                registerCorsConfiguration("/**", buildCorsConfiguration())
            }


    private fun buildCorsConfiguration() =
            CorsConfiguration().apply {
                allowCredentials = true
                addAllowedHeader("*")
                addAllowedMethod("*")
                addAllowedOrigin("*")
            }

}
