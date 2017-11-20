package com.sever.football.repository;

import com.sever.football.domain.Ns_Rank;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ns_Rank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Ns_RankRepository extends JpaRepository<Ns_Rank, Long> {

}
