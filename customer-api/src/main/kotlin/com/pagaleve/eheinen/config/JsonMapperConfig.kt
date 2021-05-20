package com.pagaleve.eheinen.config

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.stereotype.Component

@Component
class JsonMapperConfig {

    private val jsonMapper = JsonMapper()

    fun mapper(): JsonMapper {
        jsonMapper.registerModules(
            KotlinModule(),
            JavaTimeModule()
        )

        return jsonMapper
    }
}