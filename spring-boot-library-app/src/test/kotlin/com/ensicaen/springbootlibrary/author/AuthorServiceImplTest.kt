package com.ensicaen.springbootlibrary.author

import com.ensicaen.openapi.springbootlibrary.api.AuthorDto
import com.ensicaen.springbootlibrary.author.exception.AuthorNotFoundException
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

internal class AuthorServiceImplTest {

    private val authorRepositoryMock: AuthorRepository = mockk(relaxed = true)
    private val authorService = AuthorServiceImpl(
        authorRepository = authorRepositoryMock,
        authorMapper = AuthorMapperImpl()
    )

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun create() {
        // given
        val authorDtoToCreate = AuthorDto(name = "AuthorName")
        val authorId = "1"
        val authorEntityCaptor = slot<AuthorEntity>()
        every { authorRepositoryMock.findByIdOrNull(authorId.toLong()) } returns null
        every { authorRepositoryMock.save(capture(authorEntityCaptor)) } answers { authorEntityCaptor.captured }

        // when
        val createdAuthorDto = authorService.create(authorDtoToCreate)

        // then
        verify(exactly = 1) { authorRepositoryMock.save(capture(authorEntityCaptor)) }

        assertThat(createdAuthorDto.name).isEqualTo("AuthorName")
    }

    @Test
    fun read() {
        // given
        val authorId = "1"
        every { authorRepositoryMock.findByIdOrNull(authorId.toLong()) } returns AuthorEntity(name = "AuthorName")

        // when
        val authorDto = authorService.read(authorId)

        // then
        assertThat(authorDto.name).isEqualTo("AuthorName")
    }

    @Test
    fun `read with authorId not found`() {
        // given
        val authorId = "1"
        every { authorRepositoryMock.findByIdOrNull(authorId.toLong()) } returns null

        // when and then
        assertThatExceptionOfType(AuthorNotFoundException::class.java)
            .isThrownBy { authorService.read(authorId) }
            .withMessage("Author with id 1 not found")
    }

    @Test
    fun readAll() {
        // given
        val authorEntity1 = AuthorEntity(name = "author1")
        val authorEntity2 = AuthorEntity(name = "author2")
        val expectedAuthorDto1 = AuthorDto(name = "author1")
        val expectedAuthorDto2 = AuthorDto(name = "author2")
        every { authorRepositoryMock.findAll() } returns listOf(authorEntity1, authorEntity2)

        // when
        val authorsDto = authorService.readAll()

        // then
        assertThat(authorsDto).containsExactlyInAnyOrder(expectedAuthorDto1, expectedAuthorDto2)
    }

    @Test
    fun delete() {
        // given
        val authorId = "1"
        val authorEntity = AuthorEntity(name = "AuthorName")
        every { authorRepositoryMock.findByIdOrNull(authorId.toLong()) } returns authorEntity

        // when
        authorService.delete(authorId)

        // then
        verify(exactly = 1) { authorRepositoryMock.delete(authorEntity) }
    }

    @Test
    fun `delete with authorId not found`() {
        // given
        val authorId = "1"
        every { authorRepositoryMock.findByIdOrNull(authorId.toLong()) } returns null

        // when and then
        assertThatExceptionOfType(AuthorNotFoundException::class.java)
            .isThrownBy { authorService.delete(authorId) }
            .withMessage("Author with id 1 not found")
    }
}