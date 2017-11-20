package com.sever.football.repository;

import com.sever.football.domain.Ns_Livestream;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ns_Livestream entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Ns_LivestreamRepository extends JpaRepository<Ns_Livestream, Long> {

}
