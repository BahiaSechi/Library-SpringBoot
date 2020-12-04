package com.ensicaen.springbootlibrary.author

import com.ensicaen.springbootlibrary.entity.AbstractJpaPersistable
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "author")
class AuthorEntity(var name: String) : AbstractJpaPersistable()