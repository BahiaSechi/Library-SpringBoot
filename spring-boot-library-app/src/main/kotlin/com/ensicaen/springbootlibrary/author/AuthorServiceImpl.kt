package com.ensicaen.springbootlibrary.author

import com.ensicaen.openapi.springbootlibrary.api.AuthorDto
import com.ensicaen.springbootlibrary.author.exception.AuthorNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
    private val authorMapper: AuthorMapper
) : AuthorService {

    override fun create(authorDto: AuthorDto): AuthorDto {
        val authorEntityToSave = authorMapper.fromDto(authorDto)
        val authorEntitySaved = authorRepository.save(authorEntityToSave)
        return authorMapper.fromEntity(authorEntitySaved)
    }

    override fun read(authorId: String): AuthorDto = authorMapper.fromEntity(getAuthorEntity(authorId.toLong()))

    override fun readAll(): List<AuthorDto> = authorRepository.findAll().map { authorMapper.fromEntity(it) }

    override fun delete(authorId: String) {
        authorRepository.delete(getAuthorEntity(authorId.toLong()))
    }

    private fun getAuthorEntity(authorId: Long) =
        authorRepository.findByIdOrNull(authorId)
            ?: throw AuthorNotFoundException("Author with id $authorId not found")
}
