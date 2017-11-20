package com.sever.football.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sever.football.domain.Ns_Highlight;

import com.sever.football.repository.Ns_HighlightRepository;
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
 * REST controller for managing Ns_Highlight.
 */
@RestController
@RequestMapping("/api")
public class Ns_HighlightResource {

    private final Logger log = LoggerFactory.getLogger(Ns_HighlightResource.class);

    private static final String ENTITY_NAME = "ns_Highlight";

    private final Ns_HighlightRepository ns_HighlightRepository;

    public Ns_HighlightResource(Ns_HighlightRepository ns_HighlightRepository) {
        this.ns_HighlightRepository = ns_HighlightRepository;
    }

    /**
     * POST  /ns-highlights : Create a new ns_Highlight.
     *
     * @param ns_Highlight the ns_Highlight to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ns_Highlight, or with status 400 (Bad Request) if the ns_Highlight has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ns-highlights")
    @Timed
    public ResponseEntity<Ns_Highlight> createNs_Highlight(@Valid @RequestBody Ns_Highlight ns_Highlight) throws URISyntaxException {
        log.debug("REST request to save Ns_Highlight : {}", ns_Highlight);
        if (ns_Highlight.getId() != null) {
            throw new BadRequestAlertException("A new ns_Highlight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ns_Highlight result = ns_HighlightRepository.save(ns_Highlight);
        return ResponseEntity.created(new URI("/api/ns-highlights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ns-highlights : Updates an existing ns_Highlight.
     *
     * @param ns_Highlight the ns_Highlight to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ns_Highlight,
     * or with status 400 (Bad Request) if the ns_Highlight is not valid,
     * or with status 500 (Internal Server Error) if the ns_Highlight couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ns-highlights")
    @Timed
    public ResponseEntity<Ns_Highlight> updateNs_Highlight(@Valid @RequestBody Ns_Highlight ns_Highlight) throws URISyntaxException {
        log.debug("REST request to update Ns_Highlight : {}", ns_Highlight);
        if (ns_Highlight.getId() == null) {
            return createNs_Highlight(ns_Highlight);
        }
        Ns_Highlight result = ns_HighlightRepository.save(ns_Highlight);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ns_Highlight.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ns-highlights : get all the ns_Highlights.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ns_Highlights in body
     */
    @GetMapping("/ns-highlights")
    @Timed
    public ResponseEntity<List<Ns_Highlight>> getAllNs_Highlights(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ns_Highlights");
        Page<Ns_Highlight> page = ns_HighlightRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ns-highlights");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ns-highlights/:id : get the "id" ns_Highlight.
     *
     * @param id the id of the ns_Highlight to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ns_Highlight, or with status 404 (Not Found)
     */
    @GetMapping("/ns-highlights/{id}")
    @Timed
    public ResponseEntity<Ns_Highlight> getNs_Highlight(@PathVariable Long id) {
        log.debug("REST request to get Ns_Highlight : {}", id);
        Ns_Highlight ns_Highlight = ns_HighlightRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ns_Highlight));
    }

    /**
     * DELETE  /ns-highlights/:id : delete the "id" ns_Highlight.
     *
     * @param id the id of the ns_Highlight to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ns-highlights/{id}")
    @Timed
    public ResponseEntity<Void> deleteNs_Highlight(@PathVariable Long id) {
        log.debug("REST request to delete Ns_Highlight : {}", id);
        ns_HighlightRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
