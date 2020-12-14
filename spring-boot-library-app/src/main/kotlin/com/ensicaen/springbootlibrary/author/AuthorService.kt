package com.ensicaen.springbootlibrary.author

import com.ensicaen.openapi.springbootlibrary.api.AuthorDto

interface AuthorService {
    fun create(authorDto: AuthorDto): AuthorDto
    fun read(authorId: String): AuthorDto
    fun readAll(): List<AuthorDto>
    fun delete(authorId: String)
}