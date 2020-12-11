package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookDto
import com.ensicaen.springbootlibrary.author.AuthorEntity
import com.ensicaen.springbootlibrary.publisher.PublisherEntity

interface BookMapper {
    fun fromDto(bookDto: BookDto, publisherEntity: PublisherEntity, authorEntities: List<AuthorEntity>): BookEntity
    fun fromEntity(bookEntity: BookEntity): BookDto
}