package com.ensicaen.springbootlibrary.publisher

import com.ensicaen.openapi.springbootlibrary.api.PublisherDto
import com.ensicaen.springbootlibrary.publisher.exception.PublisherNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PublisherServiceImpl(
    private val publisherRepository: PublisherRepository,
    private val publisherMapper: PublisherMapper
) : PublisherService {

    override fun create(publisherDto: PublisherDto): PublisherDto {
        val publisherEntityToSave = publisherMapper.fromDto(publisherDto)
        val publisherEntitySaved = publisherRepository.save(publisherEntityToSave)
        return publisherMapper.fromEntity(publisherEntitySaved)
    }

    override fun read(publisherId: String): PublisherDto =
        publisherMapper.fromEntity(getPublisherEntity(publisherId.toLong()))

    override fun readAll(): List<PublisherDto> = publisherRepository.findAll().map { publisherMapper.fromEntity(it) }

    override fun delete(publisherId: String) {
        publisherRepository.delete(getPublisherEntity(publisherId.toLong()))
    }

    private fun getPublisherEntity(publisherId: Long) =
        publisherRepository.findByIdOrNull(publisherId)
            ?: throw PublisherNotFoundException("Publisher with id $publisherId not found")
}
