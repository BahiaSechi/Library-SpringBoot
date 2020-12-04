package com.ensicaen.springbootlibrary.author.exception

import com.ensicaen.openapi.springbootlibrary.api.ErrorCode._01
import com.ensicaen.springbootlibrary.exception.SpringBootLibraryException
import org.springframework.http.HttpStatus.NOT_FOUND

class AuthorNotFoundException(message: String) : SpringBootLibraryException(
    message = message,
    httpStatus = NOT_FOUND,
    errorCode = _01
)

