package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookDto
import org.mapstruct.InjectionStrategy.CONSTRUCTOR
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
interface BookMapper {
    fun fromDto(bookDto: BookDto): BookEntity
    fun fromEntity(bookEntity: BookEntity): BookDto
}