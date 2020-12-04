package com.ensicaen.springbootlibrary.entity

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractJpaPersistable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other ?: return false
        id ?: return false
        if (other !is AbstractJpaPersistable) return false
        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"
}