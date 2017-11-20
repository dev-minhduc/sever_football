package com.sever.football.repository;

import com.sever.football.domain.Ns_Match_Schedule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ns_Match_Schedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Ns_Match_ScheduleRepository extends JpaRepository<Ns_Match_Schedule, Long> {

}
