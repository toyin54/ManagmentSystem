package com.project.jose.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDetailsRepository extends JpaRepository<CardDetails,Long> {



}
