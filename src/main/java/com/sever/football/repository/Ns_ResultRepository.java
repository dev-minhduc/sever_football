package com.sever.football.repository;

import com.sever.football.domain.Ns_Result;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ns_Result entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Ns_ResultRepository extends JpaRepository<Ns_Result, Long> {

}
