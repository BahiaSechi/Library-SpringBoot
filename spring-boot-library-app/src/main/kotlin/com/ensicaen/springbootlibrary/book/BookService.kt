package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookDto

interface BookService {
    fun create(bookDto: BookDto): BookDto
    fun read(bookId: String): BookDto
    fun readAll(): List<BookDto>
    fun delete(bookId: String)
}