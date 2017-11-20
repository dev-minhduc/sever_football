package com.sever.football.repository;

import com.sever.football.domain.Ns_Highlight;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ns_Highlight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Ns_HighlightRepository extends JpaRepository<Ns_Highlight, Long> {

}
