package com.ensicaen.springbootlibrary.publisher

import com.ensicaen.openapi.api.PublishersApi
import com.ensicaen.openapi.springbootlibrary.api.PublisherDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val LOGGER = LoggerFactory.getLogger(PublisherController::class.java)

@RestController
@RequestMapping("api/v1/")
class PublisherController(private val publisherService: PublisherService) : PublishersApi {

    override fun createPublisher(publisherDto: PublisherDto): ResponseEntity<PublisherDto> {
        LOGGER.info("Request to create publisher {}", publisherDto)
        return status(CREATED).body(publisherService.create(publisherDto))
    }

    override fun readPublishers(): ResponseEntity<List<PublisherDto>> {
        LOGGER.info("Request to read all publishers")
        return ok(publisherService.readAll())
    }

    override fun readPublisher(id: String): ResponseEntity<PublisherDto> {
        LOGGER.info("Request to read publisher with id {}", id)
        return ok(publisherService.read(id))
    }

    override fun deletePublisher(id: String): ResponseEntity<Unit> {
        LOGGER.info("Request to delete publisher with id {}", id)
        publisherService.delete(id)
        return noContent().build()
    }
}