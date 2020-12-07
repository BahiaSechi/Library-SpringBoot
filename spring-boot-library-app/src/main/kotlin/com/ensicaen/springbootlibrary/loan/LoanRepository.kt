package com.ensicaen.springbootlibrary.loan

import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository : JpaRepository<LoanEntity, Long>