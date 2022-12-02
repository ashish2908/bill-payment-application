package com.online.payment.repository;

import com.online.payment.entity.Biller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillerRepository extends JpaRepository<Biller, Long> {
}
