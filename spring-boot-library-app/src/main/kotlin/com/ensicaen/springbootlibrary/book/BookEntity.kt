package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookState
import com.ensicaen.springbootlibrary.entity.AbstractJpaPersistable
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "book")
class BookEntity(
    var title: String,
    @Enumerated(STRING) var state: BookState
) : AbstractJpaPersistable()