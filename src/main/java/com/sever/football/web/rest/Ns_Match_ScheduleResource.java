package com.sever.football.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sever.football.domain.Ns_Match_Schedule;

import com.sever.football.repository.Ns_Match_ScheduleRepository;
import com.sever.football.web.rest.errors.BadRequestAlertException;
import com.sever.football.web.rest.util.HeaderUtil;
import com.sever.football.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ns_Match_Schedule.
 */
@RestController
@RequestMapping("/api")
public class Ns_Match_ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(Ns_Match_ScheduleResource.class);

    private static final String ENTITY_NAME = "ns_Match_Schedule";

    private final Ns_Match_ScheduleRepository ns_Match_ScheduleRepository;

    public Ns_Match_ScheduleResource(Ns_Match_ScheduleRepository ns_Match_ScheduleRepository) {
        this.ns_Match_ScheduleRepository = ns_Match_ScheduleRepository;
    }

    /**
     * POST  /ns-match-schedules : Create a new ns_Match_Schedule.
     *
     * @param ns_Match_Schedule the ns_Match_Schedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ns_Match_Schedule, or with status 400 (Bad Request) if the ns_Match_Schedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ns-match-schedules")
    @Timed
    public ResponseEntity<Ns_Match_Schedule> createNs_Match_Schedule(@RequestBody Ns_Match_Schedule ns_Match_Schedule) throws URISyntaxException {
        log.debug("REST request to save Ns_Match_Schedule : {}", ns_Match_Schedule);
        if (ns_Match_Schedule.getId() != null) {
            throw new BadRequestAlertException("A new ns_Match_Schedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ns_Match_Schedule result = ns_Match_ScheduleRepository.save(ns_Match_Schedule);
        return ResponseEntity.created(new URI("/api/ns-match-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ns-match-schedules : Updates an existing ns_Match_Schedule.
     *
     * @param ns_Match_Schedule the ns_Match_Schedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ns_Match_Schedule,
     * or with status 400 (Bad Request) if the ns_Match_Schedule is not valid,
     * or with status 500 (Internal Server Error) if the ns_Match_Schedule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ns-match-schedules")
    @Timed
    public ResponseEntity<Ns_Match_Schedule> updateNs_Match_Schedule(@RequestBody Ns_Match_Schedule ns_Match_Schedule) throws URISyntaxException {
        log.debug("REST request to update Ns_Match_Schedule : {}", ns_Match_Schedule);
        if (ns_Match_Schedule.getId() == null) {
            return createNs_Match_Schedule(ns_Match_Schedule);
        }
        Ns_Match_Schedule result = ns_Match_ScheduleRepository.save(ns_Match_Schedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ns_Match_Schedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ns-match-schedules : get all the ns_Match_Schedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ns_Match_Schedules in body
     */
    @GetMapping("/ns-match-schedules")
    @Timed
    public ResponseEntity<List<Ns_Match_Schedule>> getAllNs_Match_Schedules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ns_Match_Schedules");
        Page<Ns_Match_Schedule> page = ns_Match_ScheduleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ns-match-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ns-match-schedules/:id : get the "id" ns_Match_Schedule.
     *
     * @param id the id of the ns_Match_Schedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ns_Match_Schedule, or with status 404 (Not Found)
     */
    @GetMapping("/ns-match-schedules/{id}")
    @Timed
    public ResponseEntity<Ns_Match_Schedule> getNs_Match_Schedule(@PathVariable Long id) {
        log.debug("REST request to get Ns_Match_Schedule : {}", id);
        Ns_Match_Schedule ns_Match_Schedule = ns_Match_ScheduleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ns_Match_Schedule));
    }

    /**
     * DELETE  /ns-match-schedules/:id : delete the "id" ns_Match_Schedule.
     *
     * @param id the id of the ns_Match_Schedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ns-match-schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteNs_Match_Schedule(@PathVariable Long id) {
        log.debug("REST request to delete Ns_Match_Schedule : {}", id);
        ns_Match_ScheduleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
