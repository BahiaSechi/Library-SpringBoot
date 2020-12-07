package com.ensicaen.springbootlibrary.publisher

import com.ensicaen.openapi.springbootlibrary.api.PublisherDto
import com.ensicaen.springbootlibrary.publisher.exception.PublisherNotFoundException
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

internal class PublisherServiceImplTest {

    private val publisherRepositoryMock: PublisherRepository = mockk(relaxed = true)
    private val publisherService = PublisherServiceImpl(
        publisherRepository = publisherRepositoryMock,
        publisherMapper = PublisherMapperImpl()
    )

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun create() {
        // given
        val publisherDtoToCreate = PublisherDto(name = "PublisherName")
        val publisherId = "1"
        val publisherEntityCaptor = slot<PublisherEntity>()
        every { publisherRepositoryMock.findByIdOrNull(publisherId.toLong()) } returns null
        every { publisherRepositoryMock.save(capture(publisherEntityCaptor)) } answers { publisherEntityCaptor.captured }

        // when
        val createdPublisherDto = publisherService.create(publisherDtoToCreate)

        // then
        verify(exactly = 1) { publisherRepositoryMock.save(capture(publisherEntityCaptor)) }

        assertThat(createdPublisherDto.name).isEqualTo("PublisherName")
    }

    @Test
    fun read() {
        // given
        val publisherId = "1"
        every { publisherRepositoryMock.findByIdOrNull(publisherId.toLong()) } returns PublisherEntity(name = "PublisherName")

        // when
        val publisherDto = publisherService.read(publisherId)

        // then
        assertThat(publisherDto.name).isEqualTo("PublisherName")
    }

    @Test
    fun `read with publisherId not found`() {
        // given
        val publisherId = "1"
        every { publisherRepositoryMock.findByIdOrNull(publisherId.toLong()) } returns null

        // when and then
        assertThatExceptionOfType(PublisherNotFoundException::class.java)
            .isThrownBy { publisherService.read(publisherId) }
            .withMessage("Publisher with id 1 not found")
    }

    @Test
    fun readAll() {
        // given
        val publisherEntity1 = PublisherEntity(name = "publisher1")
        val publisherEntity2 = PublisherEntity(name = "publisher2")
        val expectedPublisherDto1 = PublisherDto(name = "publisher1")
        val expectedPublisherDto2 = PublisherDto(name = "publisher2")
        every { publisherRepositoryMock.findAll() } returns listOf(publisherEntity1, publisherEntity2)

        // when
        val publishersDto = publisherService.readAll()

        // then
        assertThat(publishersDto).containsExactlyInAnyOrder(expectedPublisherDto1, expectedPublisherDto2)
    }

    @Test
    fun delete() {
        // given
        val publisherId = "1"
        val publisherEntity = PublisherEntity(name = "PublisherName")
        every { publisherRepositoryMock.findByIdOrNull(publisherId.toLong()) } returns publisherEntity

        // when
        publisherService.delete(publisherId)

        // then
        verify(exactly = 1) { publisherRepositoryMock.delete(publisherEntity) }
    }

    @Test
    fun `delete with publisherId not found`() {
        // given
        val publisherId = "1"
        every { publisherRepositoryMock.findByIdOrNull(publisherId.toLong()) } returns null

        // when and then
        assertThatExceptionOfType(PublisherNotFoundException::class.java)
            .isThrownBy { publisherService.delete(publisherId) }
            .withMessage("Publisher with id 1 not found")
    }
}