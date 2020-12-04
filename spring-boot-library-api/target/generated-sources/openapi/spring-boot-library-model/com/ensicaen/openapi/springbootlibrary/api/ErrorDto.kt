package com.ensicaen.openapi.springbootlibrary.api

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

/**
 * 
 * @param code * `01` - Internal error <exception message> * `05` - Message not readable * `06` - Message field(s) not valid 
 * @param datetime 
 * @param message 
 * @param details 
 */
data class ErrorDto(

    @get:NotNull 
    @JsonProperty("code") val code: ErrorDto.Code,

    @JsonProperty("datetime") val datetime: java.time.OffsetDateTime? = null,

    @JsonProperty("message") val message: kotlin.String? = null,

    @JsonProperty("details") val details: kotlin.String? = null
) {

    /**
    * * `01` - Internal error <exception message> * `05` - Message not readable * `06` - Message field(s) not valid 
    * Values: _01,_05,_06
    */
    enum class Code(val value: kotlin.String) {
    
        @JsonProperty("01") _01("01"),
    
        @JsonProperty("05") _05("05"),
    
        @JsonProperty("06") _06("06");
    
    }

}

