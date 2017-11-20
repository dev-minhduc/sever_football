package com.sever.football.web.rest;

import com.sever.football.SvFootballApp;

import com.sever.football.domain.Ns_Result;
import com.sever.football.repository.Ns_ResultRepository;
import com.sever.football.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sever.football.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Ns_ResultResource REST controller.
 *
 * @see Ns_ResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SvFootballApp.class)
public class Ns_ResultResourceIntTest {

    private static final String DEFAULT_TEAM_1 = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_2 = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_2 = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SATUS = false;
    private static final Boolean UPDATED_SATUS = true;

    @Autowired
    private Ns_ResultRepository ns_ResultRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNs_ResultMockMvc;

    private Ns_Result ns_Result;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Ns_ResultResource ns_ResultResource = new Ns_ResultResource(ns_ResultRepository);
        this.restNs_ResultMockMvc = MockMvcBuilders.standaloneSetup(ns_ResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ns_Result createEntity(EntityManager em) {
        Ns_Result ns_Result = new Ns_Result()
            .team1(DEFAULT_TEAM_1)
            .team2(DEFAULT_TEAM_2)
            .result(DEFAULT_RESULT)
            .thumbnail1(DEFAULT_THUMBNAIL_1)
            .thumbnail2(DEFAULT_THUMBNAIL_2)
            .satus(DEFAULT_SATUS);
        return ns_Result;
    }

    @Before
    public void initTest() {
        ns_Result = createEntity(em);
    }

    @Test
    @Transactional
    public void createNs_Result() throws Exception {
        int databaseSizeBeforeCreate = ns_ResultRepository.findAll().size();

        // Create the Ns_Result
        restNs_ResultMockMvc.perform(post("/api/ns-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Result)))
            .andExpect(status().isCreated());

        // Validate the Ns_Result in the database
        List<Ns_Result> ns_ResultList = ns_ResultRepository.findAll();
        assertThat(ns_ResultList).hasSize(databaseSizeBeforeCreate + 1);
        Ns_Result testNs_Result = ns_ResultList.get(ns_ResultList.size() - 1);
        assertThat(testNs_Result.getTeam1()).isEqualTo(DEFAULT_TEAM_1);
        assertThat(testNs_Result.getTeam2()).isEqualTo(DEFAULT_TEAM_2);
        assertThat(testNs_Result.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testNs_Result.getThumbnail1()).isEqualTo(DEFAULT_THUMBNAIL_1);
        assertThat(testNs_Result.getThumbnail2()).isEqualTo(DEFAULT_THUMBNAIL_2);
        assertThat(testNs_Result.isSatus()).isEqualTo(DEFAULT_SATUS);
    }

    @Test
    @Transactional
    public void createNs_ResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ns_ResultRepository.findAll().size();

        // Create the Ns_Result with an existing ID
        ns_Result.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNs_ResultMockMvc.perform(post("/api/ns-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Result)))
            .andExpect(status().isBadRequest());

        // Validate the Ns_Result in the database
        List<Ns_Result> ns_ResultList = ns_ResultRepository.findAll();
        assertThat(ns_ResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNs_Results() throws Exception {
        // Initialize the database
        ns_ResultRepository.saveAndFlush(ns_Result);

        // Get all the ns_ResultList
        restNs_ResultMockMvc.perform(get("/api/ns-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ns_Result.getId().intValue())))
            .andExpect(jsonPath("$.[*].team1").value(hasItem(DEFAULT_TEAM_1.toString())))
            .andExpect(jsonPath("$.[*].team2").value(hasItem(DEFAULT_TEAM_2.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].thumbnail1").value(hasItem(DEFAULT_THUMBNAIL_1.toString())))
            .andExpect(jsonPath("$.[*].thumbnail2").value(hasItem(DEFAULT_THUMBNAIL_2.toString())))
            .andExpect(jsonPath("$.[*].satus").value(hasItem(DEFAULT_SATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getNs_Result() throws Exception {
        // Initialize the database
        ns_ResultRepository.saveAndFlush(ns_Result);

        // Get the ns_Result
        restNs_ResultMockMvc.perform(get("/api/ns-results/{id}", ns_Result.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ns_Result.getId().intValue()))
            .andExpect(jsonPath("$.team1").value(DEFAULT_TEAM_1.toString()))
            .andExpect(jsonPath("$.team2").value(DEFAULT_TEAM_2.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.thumbnail1").value(DEFAULT_THUMBNAIL_1.toString()))
            .andExpect(jsonPath("$.thumbnail2").value(DEFAULT_THUMBNAIL_2.toString()))
            .andExpect(jsonPath("$.satus").value(DEFAULT_SATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNs_Result() throws Exception {
        // Get the ns_Result
        restNs_ResultMockMvc.perform(get("/api/ns-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNs_Result() throws Exception {
        // Initialize the database
        ns_ResultRepository.saveAndFlush(ns_Result);
        int databaseSizeBeforeUpdate = ns_ResultRepository.findAll().size();

        // Update the ns_Result
        Ns_Result updatedNs_Result = ns_ResultRepository.findOne(ns_Result.getId());
        updatedNs_Result
            .team1(UPDATED_TEAM_1)
            .team2(UPDATED_TEAM_2)
            .result(UPDATED_RESULT)
            .thumbnail1(UPDATED_THUMBNAIL_1)
            .thumbnail2(UPDATED_THUMBNAIL_2)
            .satus(UPDATED_SATUS);

        restNs_ResultMockMvc.perform(put("/api/ns-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNs_Result)))
            .andExpect(status().isOk());

        // Validate the Ns_Result in the database
        List<Ns_Result> ns_ResultList = ns_ResultRepository.findAll();
        assertThat(ns_ResultList).hasSize(databaseSizeBeforeUpdate);
        Ns_Result testNs_Result = ns_ResultList.get(ns_ResultList.size() - 1);
        assertThat(testNs_Result.getTeam1()).isEqualTo(UPDATED_TEAM_1);
        assertThat(testNs_Result.getTeam2()).isEqualTo(UPDATED_TEAM_2);
        assertThat(testNs_Result.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testNs_Result.getThumbnail1()).isEqualTo(UPDATED_THUMBNAIL_1);
        assertThat(testNs_Result.getThumbnail2()).isEqualTo(UPDATED_THUMBNAIL_2);
        assertThat(testNs_Result.isSatus()).isEqualTo(UPDATED_SATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingNs_Result() throws Exception {
        int databaseSizeBeforeUpdate = ns_ResultRepository.findAll().size();

        // Create the Ns_Result

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNs_ResultMockMvc.perform(put("/api/ns-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Result)))
            .andExpect(status().isCreated());

        // Validate the Ns_Result in the database
        List<Ns_Result> ns_ResultList = ns_ResultRepository.findAll();
        assertThat(ns_ResultList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNs_Result() throws Exception {
        // Initialize the database
        ns_ResultRepository.saveAndFlush(ns_Result);
        int databaseSizeBeforeDelete = ns_ResultRepository.findAll().size();

        // Get the ns_Result
        restNs_ResultMockMvc.perform(delete("/api/ns-results/{id}", ns_Result.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ns_Result> ns_ResultList = ns_ResultRepository.findAll();
        assertThat(ns_ResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ns_Result.class);
        Ns_Result ns_Result1 = new Ns_Result();
        ns_Result1.setId(1L);
        Ns_Result ns_Result2 = new Ns_Result();
        ns_Result2.setId(ns_Result1.getId());
        assertThat(ns_Result1).isEqualTo(ns_Result2);
        ns_Result2.setId(2L);
        assertThat(ns_Result1).isNotEqualTo(ns_Result2);
        ns_Result1.setId(null);
        assertThat(ns_Result1).isNotEqualTo(ns_Result2);
    }
}
