package com.sever.football.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sever.football.domain.Ns_Rank;

import com.sever.football.repository.Ns_RankRepository;
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
 * REST controller for managing Ns_Rank.
 */
@RestController
@RequestMapping("/api")
public class Ns_RankResource {

    private final Logger log = LoggerFactory.getLogger(Ns_RankResource.class);

    private static final String ENTITY_NAME = "ns_Rank";

    private final Ns_RankRepository ns_RankRepository;

    public Ns_RankResource(Ns_RankRepository ns_RankRepository) {
        this.ns_RankRepository = ns_RankRepository;
    }

    /**
     * POST  /ns-ranks : Create a new ns_Rank.
     *
     * @param ns_Rank the ns_Rank to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ns_Rank, or with status 400 (Bad Request) if the ns_Rank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ns-ranks")
    @Timed
    public ResponseEntity<Ns_Rank> createNs_Rank(@RequestBody Ns_Rank ns_Rank) throws URISyntaxException {
        log.debug("REST request to save Ns_Rank : {}", ns_Rank);
        if (ns_Rank.getId() != null) {
            throw new BadRequestAlertException("A new ns_Rank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ns_Rank result = ns_RankRepository.save(ns_Rank);
        return ResponseEntity.created(new URI("/api/ns-ranks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ns-ranks : Updates an existing ns_Rank.
     *
     * @param ns_Rank the ns_Rank to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ns_Rank,
     * or with status 400 (Bad Request) if the ns_Rank is not valid,
     * or with status 500 (Internal Server Error) if the ns_Rank couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ns-ranks")
    @Timed
    public ResponseEntity<Ns_Rank> updateNs_Rank(@RequestBody Ns_Rank ns_Rank) throws URISyntaxException {
        log.debug("REST request to update Ns_Rank : {}", ns_Rank);
        if (ns_Rank.getId() == null) {
            return createNs_Rank(ns_Rank);
        }
        Ns_Rank result = ns_RankRepository.save(ns_Rank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ns_Rank.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ns-ranks : get all the ns_Ranks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ns_Ranks in body
     */
    @GetMapping("/ns-ranks")
    @Timed
    public ResponseEntity<List<Ns_Rank>> getAllNs_Ranks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ns_Ranks");
        Page<Ns_Rank> page = ns_RankRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ns-ranks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ns-ranks/:id : get the "id" ns_Rank.
     *
     * @param id the id of the ns_Rank to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ns_Rank, or with status 404 (Not Found)
     */
    @GetMapping("/ns-ranks/{id}")
    @Timed
    public ResponseEntity<Ns_Rank> getNs_Rank(@PathVariable Long id) {
        log.debug("REST request to get Ns_Rank : {}", id);
        Ns_Rank ns_Rank = ns_RankRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ns_Rank));
    }

    /**
     * DELETE  /ns-ranks/:id : delete the "id" ns_Rank.
     *
     * @param id the id of the ns_Rank to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ns-ranks/{id}")
    @Timed
    public ResponseEntity<Void> deleteNs_Rank(@PathVariable Long id) {
        log.debug("REST request to delete Ns_Rank : {}", id);
        ns_RankRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
