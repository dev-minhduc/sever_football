package com.sever.football.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sever.football.domain.Ns_Livestream;

import com.sever.football.repository.Ns_LivestreamRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ns_Livestream.
 */
@RestController
@RequestMapping("/api")
public class Ns_LivestreamResource {

    private final Logger log = LoggerFactory.getLogger(Ns_LivestreamResource.class);

    private static final String ENTITY_NAME = "ns_Livestream";

    private final Ns_LivestreamRepository ns_LivestreamRepository;

    public Ns_LivestreamResource(Ns_LivestreamRepository ns_LivestreamRepository) {
        this.ns_LivestreamRepository = ns_LivestreamRepository;
    }

    /**
     * POST  /ns-livestreams : Create a new ns_Livestream.
     *
     * @param ns_Livestream the ns_Livestream to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ns_Livestream, or with status 400 (Bad Request) if the ns_Livestream has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ns-livestreams")
    @Timed
    public ResponseEntity<Ns_Livestream> createNs_Livestream(@Valid @RequestBody Ns_Livestream ns_Livestream) throws URISyntaxException {
        log.debug("REST request to save Ns_Livestream : {}", ns_Livestream);
        if (ns_Livestream.getId() != null) {
            throw new BadRequestAlertException("A new ns_Livestream cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ns_Livestream result = ns_LivestreamRepository.save(ns_Livestream);
        return ResponseEntity.created(new URI("/api/ns-livestreams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ns-livestreams : Updates an existing ns_Livestream.
     *
     * @param ns_Livestream the ns_Livestream to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ns_Livestream,
     * or with status 400 (Bad Request) if the ns_Livestream is not valid,
     * or with status 500 (Internal Server Error) if the ns_Livestream couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ns-livestreams")
    @Timed
    public ResponseEntity<Ns_Livestream> updateNs_Livestream(@Valid @RequestBody Ns_Livestream ns_Livestream) throws URISyntaxException {
        log.debug("REST request to update Ns_Livestream : {}", ns_Livestream);
        if (ns_Livestream.getId() == null) {
            return createNs_Livestream(ns_Livestream);
        }
        Ns_Livestream result = ns_LivestreamRepository.save(ns_Livestream);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ns_Livestream.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ns-livestreams : get all the ns_Livestreams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ns_Livestreams in body
     */
    @GetMapping("/ns-livestreams")
    @Timed
    public ResponseEntity<List<Ns_Livestream>> getAllNs_Livestreams(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ns_Livestreams");
        Page<Ns_Livestream> page = ns_LivestreamRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ns-livestreams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ns-livestreams/:id : get the "id" ns_Livestream.
     *
     * @param id the id of the ns_Livestream to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ns_Livestream, or with status 404 (Not Found)
     */
    @GetMapping("/ns-livestreams/{id}")
    @Timed
    public ResponseEntity<Ns_Livestream> getNs_Livestream(@PathVariable Long id) {
        log.debug("REST request to get Ns_Livestream : {}", id);
        Ns_Livestream ns_Livestream = ns_LivestreamRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ns_Livestream));
    }

    /**
     * DELETE  /ns-livestreams/:id : delete the "id" ns_Livestream.
     *
     * @param id the id of the ns_Livestream to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ns-livestreams/{id}")
    @Timed
    public ResponseEntity<Void> deleteNs_Livestream(@PathVariable Long id) {
        log.debug("REST request to delete Ns_Livestream : {}", id);
        ns_LivestreamRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
