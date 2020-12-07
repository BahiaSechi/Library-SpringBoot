package com.ensicaen.springbootlibrary.publisher

import com.ensicaen.openapi.springbootlibrary.api.PublisherDto
import org.mapstruct.InjectionStrategy.CONSTRUCTOR
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
interface PublisherMapper {
    fun fromDto(publisherDto: PublisherDto): PublisherEntity
    fun fromEntity(publisherEntity: PublisherEntity): PublisherDto
}