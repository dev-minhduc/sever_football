package com.sever.football.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sever.football.domain.Ns_Result;

import com.sever.football.repository.Ns_ResultRepository;
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
 * REST controller for managing Ns_Result.
 */
@RestController
@RequestMapping("/api")
public class Ns_ResultResource {

    private final Logger log = LoggerFactory.getLogger(Ns_ResultResource.class);

    private static final String ENTITY_NAME = "ns_Result";

    private final Ns_ResultRepository ns_ResultRepository;

    public Ns_ResultResource(Ns_ResultRepository ns_ResultRepository) {
        this.ns_ResultRepository = ns_ResultRepository;
    }

    /**
     * POST  /ns-results : Create a new ns_Result.
     *
     * @param ns_Result the ns_Result to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ns_Result, or with status 400 (Bad Request) if the ns_Result has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ns-results")
    @Timed
    public ResponseEntity<Ns_Result> createNs_Result(@RequestBody Ns_Result ns_Result) throws URISyntaxException {
        log.debug("REST request to save Ns_Result : {}", ns_Result);
        if (ns_Result.getId() != null) {
            throw new BadRequestAlertException("A new ns_Result cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ns_Result result = ns_ResultRepository.save(ns_Result);
        return ResponseEntity.created(new URI("/api/ns-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ns-results : Updates an existing ns_Result.
     *
     * @param ns_Result the ns_Result to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ns_Result,
     * or with status 400 (Bad Request) if the ns_Result is not valid,
     * or with status 500 (Internal Server Error) if the ns_Result couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ns-results")
    @Timed
    public ResponseEntity<Ns_Result> updateNs_Result(@RequestBody Ns_Result ns_Result) throws URISyntaxException {
        log.debug("REST request to update Ns_Result : {}", ns_Result);
        if (ns_Result.getId() == null) {
            return createNs_Result(ns_Result);
        }
        Ns_Result result = ns_ResultRepository.save(ns_Result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ns_Result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ns-results : get all the ns_Results.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ns_Results in body
     */
    @GetMapping("/ns-results")
    @Timed
    public ResponseEntity<List<Ns_Result>> getAllNs_Results(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ns_Results");
        Page<Ns_Result> page = ns_ResultRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ns-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ns-results/:id : get the "id" ns_Result.
     *
     * @param id the id of the ns_Result to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ns_Result, or with status 404 (Not Found)
     */
    @GetMapping("/ns-results/{id}")
    @Timed
    public ResponseEntity<Ns_Result> getNs_Result(@PathVariable Long id) {
        log.debug("REST request to get Ns_Result : {}", id);
        Ns_Result ns_Result = ns_ResultRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ns_Result));
    }

    /**
     * DELETE  /ns-results/:id : delete the "id" ns_Result.
     *
     * @param id the id of the ns_Result to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ns-results/{id}")
    @Timed
    public ResponseEntity<Void> deleteNs_Result(@PathVariable Long id) {
        log.debug("REST request to delete Ns_Result : {}", id);
        ns_ResultRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
