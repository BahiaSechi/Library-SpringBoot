package com.ensicaen.springbootlibrary.exception

import com.ensicaen.openapi.springbootlibrary.api.ErrorCode
import org.springframework.http.HttpStatus


abstract class SpringBootLibraryException(
    val httpStatus: HttpStatus,
    val errorCode: ErrorCode,
    message: String
) : RuntimeException(message)