package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookState
import com.ensicaen.springbootlibrary.entity.AbstractJpaPersistable
import com.ensicaen.springbootlibrary.publisher.PublisherEntity
import javax.persistence.*
import javax.persistence.EnumType.STRING

@Entity
@Table(name = "book")
class BookEntity(
    var title: String,
    @Enumerated(STRING) var state: BookState,
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    var publisher: PublisherEntity
) : AbstractJpaPersistable()