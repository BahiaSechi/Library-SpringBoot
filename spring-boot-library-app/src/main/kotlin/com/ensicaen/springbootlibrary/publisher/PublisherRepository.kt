package com.ensicaen.springbootlibrary.publisher

import org.springframework.data.jpa.repository.JpaRepository

interface PublisherRepository : JpaRepository<PublisherEntity, Long>