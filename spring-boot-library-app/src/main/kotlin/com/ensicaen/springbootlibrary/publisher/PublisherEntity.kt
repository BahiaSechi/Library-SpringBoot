package com.ensicaen.springbootlibrary.publisher

import com.ensicaen.springbootlibrary.entity.AbstractJpaPersistable
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "publisher")
class PublisherEntity(var name: String) : AbstractJpaPersistable()