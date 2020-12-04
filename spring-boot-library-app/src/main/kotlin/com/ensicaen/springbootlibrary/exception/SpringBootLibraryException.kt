package com.ensicaen.springbootlibrary.exception

import com.ensicaen.openapi.springbootlibrary.api.ErrorDto.Code
import org.apache.http.HttpStatus

abstract class SpringBootLibraryException(
    val httpStatus: HttpStatus,
    val errorCode: Code,
    message: String
) : RuntimeException(message)