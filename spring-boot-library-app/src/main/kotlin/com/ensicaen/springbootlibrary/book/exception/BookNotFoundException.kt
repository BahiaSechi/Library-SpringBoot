package com.ensicaen.springbootlibrary.book.exception

import com.ensicaen.openapi.springbootlibrary.api.ErrorCode._04
import com.ensicaen.springbootlibrary.exception.SpringBootLibraryException
import org.springframework.http.HttpStatus.NOT_FOUND

class BookNotFoundException(message: String) : SpringBootLibraryException(
    message = message,
    httpStatus = NOT_FOUND,
    errorCode = _04
)

