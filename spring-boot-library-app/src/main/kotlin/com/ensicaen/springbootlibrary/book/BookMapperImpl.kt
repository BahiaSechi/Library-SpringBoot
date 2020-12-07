package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookDto
import com.ensicaen.springbootlibrary.publisher.PublisherEntity
import org.springframework.stereotype.Service

@Service
class BookMapperImpl : BookMapper {

    override fun fromDto(bookDto: BookDto, publisherEntity: PublisherEntity) =
        BookEntity(
            title = bookDto.title,
            publisher = publisherEntity,
            state = bookDto.state
        )

    override fun fromEntity(bookEntity: BookEntity) =
        BookDto(
            id = bookEntity.id?.toBigDecimal(),
            title = bookEntity.title,
            publisherId = bookEntity.publisher.id!!.toBigDecimal(),
            state = bookEntity.state
        )
}