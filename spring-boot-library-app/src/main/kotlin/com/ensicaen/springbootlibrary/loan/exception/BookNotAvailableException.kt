package com.ensicaen.springbootlibrary.loan.exception

import com.ensicaen.openapi.springbootlibrary.api.ErrorCode._06
import com.ensicaen.springbootlibrary.exception.SpringBootLibraryException
import org.springframework.http.HttpStatus.CONFLICT

class BookNotAvailableException(message: String) : SpringBootLibraryException(
    message = message,
    httpStatus = CONFLICT,
    errorCode = _06
)