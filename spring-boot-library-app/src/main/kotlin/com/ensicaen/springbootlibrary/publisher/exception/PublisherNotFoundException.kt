package com.ensicaen.springbootlibrary.publisher.exception

import com.ensicaen.openapi.springbootlibrary.api.ErrorCode._02
import com.ensicaen.springbootlibrary.exception.SpringBootLibraryException
import org.springframework.http.HttpStatus.NOT_FOUND

class PublisherNotFoundException(message: String) : SpringBootLibraryException(
    message = message,
    httpStatus = NOT_FOUND,
    errorCode = _02
)

