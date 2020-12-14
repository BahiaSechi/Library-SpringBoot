package com.ensicaen.springbootlibrary.author

import com.ensicaen.openapi.springbootlibrary.api.AuthorDto
import org.mapstruct.InjectionStrategy.CONSTRUCTOR
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
interface AuthorMapper {
    fun fromDto(authorDto: AuthorDto): AuthorEntity
    fun fromEntity(authorEntity: AuthorEntity): AuthorDto
}