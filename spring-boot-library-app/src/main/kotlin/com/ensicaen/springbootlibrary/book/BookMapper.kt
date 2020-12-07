package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookDto
import com.ensicaen.springbootlibrary.publisher.PublisherEntity

interface BookMapper {
    fun fromDto(bookDto: BookDto, publisherEntity: PublisherEntity): BookEntity
    fun fromEntity(bookEntity: BookEntity): BookDto
}