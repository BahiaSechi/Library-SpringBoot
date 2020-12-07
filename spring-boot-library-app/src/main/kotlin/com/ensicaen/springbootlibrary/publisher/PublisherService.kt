package com.ensicaen.springbootlibrary.publisher

import com.ensicaen.openapi.springbootlibrary.api.PublisherDto

interface PublisherService {
    fun create(publisherDto: PublisherDto): PublisherDto
    fun read(publisherId: String): PublisherDto
    fun readAll(): List<PublisherDto>
    fun delete(publisherId: String)
}