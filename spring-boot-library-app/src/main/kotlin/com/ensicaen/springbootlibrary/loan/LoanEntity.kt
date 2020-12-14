package com.ensicaen.springbootlibrary.loan

import com.ensicaen.springbootlibrary.book.BookEntity
import com.ensicaen.springbootlibrary.entity.AbstractJpaPersistable
import java.time.Duration
import java.time.OffsetDateTime
import java.time.OffsetDateTime.now
import javax.persistence.CascadeType.PERSIST
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "loan")
class LoanEntity(
    var clientId: String,
    @ManyToOne(cascade = [PERSIST])
    @JoinColumn(name = "book_id", nullable = false)
    var book: BookEntity,
    durationString: String? = null,
    var returnDatetime: OffsetDateTime? = null
) : AbstractJpaPersistable() {

    var startDatetime: OffsetDateTime = now()
    var endDatetime: OffsetDateTime =
        durationString
            ?.let { startDatetime.plus(Duration.parse(it)) }
            ?: startDatetime.plusDays(15)
}