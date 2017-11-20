package com.sever.football.web.rest;

import com.sever.football.SvFootballApp;

import com.sever.football.domain.Ns_Match_Schedule;
import com.sever.football.repository.Ns_Match_ScheduleRepository;
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
 * Test class for the Ns_Match_ScheduleResource REST controller.
 *
 * @see Ns_Match_ScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SvFootballApp.class)
public class Ns_Match_ScheduleResourceIntTest {

    private static final String DEFAULT_TEAM_1 = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_2 = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_2 = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SATUS = false;
    private static final Boolean UPDATED_SATUS = true;

    @Autowired
    private Ns_Match_ScheduleRepository ns_Match_ScheduleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNs_Match_ScheduleMockMvc;

    private Ns_Match_Schedule ns_Match_Schedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Ns_Match_ScheduleResource ns_Match_ScheduleResource = new Ns_Match_ScheduleResource(ns_Match_ScheduleRepository);
        this.restNs_Match_ScheduleMockMvc = MockMvcBuilders.standaloneSetup(ns_Match_ScheduleResource)
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
    public static Ns_Match_Schedule createEntity(EntityManager em) {
        Ns_Match_Schedule ns_Match_Schedule = new Ns_Match_Schedule()
            .team1(DEFAULT_TEAM_1)
            .team2(DEFAULT_TEAM_2)
            .thumbnail1(DEFAULT_THUMBNAIL_1)
            .thumbnail2(DEFAULT_THUMBNAIL_2)
            .satus(DEFAULT_SATUS);
        return ns_Match_Schedule;
    }

    @Before
    public void initTest() {
        ns_Match_Schedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createNs_Match_Schedule() throws Exception {
        int databaseSizeBeforeCreate = ns_Match_ScheduleRepository.findAll().size();

        // Create the Ns_Match_Schedule
        restNs_Match_ScheduleMockMvc.perform(post("/api/ns-match-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Match_Schedule)))
            .andExpect(status().isCreated());

        // Validate the Ns_Match_Schedule in the database
        List<Ns_Match_Schedule> ns_Match_ScheduleList = ns_Match_ScheduleRepository.findAll();
        assertThat(ns_Match_ScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        Ns_Match_Schedule testNs_Match_Schedule = ns_Match_ScheduleList.get(ns_Match_ScheduleList.size() - 1);
        assertThat(testNs_Match_Schedule.getTeam1()).isEqualTo(DEFAULT_TEAM_1);
        assertThat(testNs_Match_Schedule.getTeam2()).isEqualTo(DEFAULT_TEAM_2);
        assertThat(testNs_Match_Schedule.getThumbnail1()).isEqualTo(DEFAULT_THUMBNAIL_1);
        assertThat(testNs_Match_Schedule.getThumbnail2()).isEqualTo(DEFAULT_THUMBNAIL_2);
        assertThat(testNs_Match_Schedule.isSatus()).isEqualTo(DEFAULT_SATUS);
    }

    @Test
    @Transactional
    public void createNs_Match_ScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ns_Match_ScheduleRepository.findAll().size();

        // Create the Ns_Match_Schedule with an existing ID
        ns_Match_Schedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNs_Match_ScheduleMockMvc.perform(post("/api/ns-match-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Match_Schedule)))
            .andExpect(status().isBadRequest());

        // Validate the Ns_Match_Schedule in the database
        List<Ns_Match_Schedule> ns_Match_ScheduleList = ns_Match_ScheduleRepository.findAll();
        assertThat(ns_Match_ScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNs_Match_Schedules() throws Exception {
        // Initialize the database
        ns_Match_ScheduleRepository.saveAndFlush(ns_Match_Schedule);

        // Get all the ns_Match_ScheduleList
        restNs_Match_ScheduleMockMvc.perform(get("/api/ns-match-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ns_Match_Schedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].team1").value(hasItem(DEFAULT_TEAM_1.toString())))
            .andExpect(jsonPath("$.[*].team2").value(hasItem(DEFAULT_TEAM_2.toString())))
            .andExpect(jsonPath("$.[*].thumbnail1").value(hasItem(DEFAULT_THUMBNAIL_1.toString())))
            .andExpect(jsonPath("$.[*].thumbnail2").value(hasItem(DEFAULT_THUMBNAIL_2.toString())))
            .andExpect(jsonPath("$.[*].satus").value(hasItem(DEFAULT_SATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getNs_Match_Schedule() throws Exception {
        // Initialize the database
        ns_Match_ScheduleRepository.saveAndFlush(ns_Match_Schedule);

        // Get the ns_Match_Schedule
        restNs_Match_ScheduleMockMvc.perform(get("/api/ns-match-schedules/{id}", ns_Match_Schedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ns_Match_Schedule.getId().intValue()))
            .andExpect(jsonPath("$.team1").value(DEFAULT_TEAM_1.toString()))
            .andExpect(jsonPath("$.team2").value(DEFAULT_TEAM_2.toString()))
            .andExpect(jsonPath("$.thumbnail1").value(DEFAULT_THUMBNAIL_1.toString()))
            .andExpect(jsonPath("$.thumbnail2").value(DEFAULT_THUMBNAIL_2.toString()))
            .andExpect(jsonPath("$.satus").value(DEFAULT_SATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNs_Match_Schedule() throws Exception {
        // Get the ns_Match_Schedule
        restNs_Match_ScheduleMockMvc.perform(get("/api/ns-match-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNs_Match_Schedule() throws Exception {
        // Initialize the database
        ns_Match_ScheduleRepository.saveAndFlush(ns_Match_Schedule);
        int databaseSizeBeforeUpdate = ns_Match_ScheduleRepository.findAll().size();

        // Update the ns_Match_Schedule
        Ns_Match_Schedule updatedNs_Match_Schedule = ns_Match_ScheduleRepository.findOne(ns_Match_Schedule.getId());
        updatedNs_Match_Schedule
            .team1(UPDATED_TEAM_1)
            .team2(UPDATED_TEAM_2)
            .thumbnail1(UPDATED_THUMBNAIL_1)
            .thumbnail2(UPDATED_THUMBNAIL_2)
            .satus(UPDATED_SATUS);

        restNs_Match_ScheduleMockMvc.perform(put("/api/ns-match-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNs_Match_Schedule)))
            .andExpect(status().isOk());

        // Validate the Ns_Match_Schedule in the database
        List<Ns_Match_Schedule> ns_Match_ScheduleList = ns_Match_ScheduleRepository.findAll();
        assertThat(ns_Match_ScheduleList).hasSize(databaseSizeBeforeUpdate);
        Ns_Match_Schedule testNs_Match_Schedule = ns_Match_ScheduleList.get(ns_Match_ScheduleList.size() - 1);
        assertThat(testNs_Match_Schedule.getTeam1()).isEqualTo(UPDATED_TEAM_1);
        assertThat(testNs_Match_Schedule.getTeam2()).isEqualTo(UPDATED_TEAM_2);
        assertThat(testNs_Match_Schedule.getThumbnail1()).isEqualTo(UPDATED_THUMBNAIL_1);
        assertThat(testNs_Match_Schedule.getThumbnail2()).isEqualTo(UPDATED_THUMBNAIL_2);
        assertThat(testNs_Match_Schedule.isSatus()).isEqualTo(UPDATED_SATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingNs_Match_Schedule() throws Exception {
        int databaseSizeBeforeUpdate = ns_Match_ScheduleRepository.findAll().size();

        // Create the Ns_Match_Schedule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNs_Match_ScheduleMockMvc.perform(put("/api/ns-match-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Match_Schedule)))
            .andExpect(status().isCreated());

        // Validate the Ns_Match_Schedule in the database
        List<Ns_Match_Schedule> ns_Match_ScheduleList = ns_Match_ScheduleRepository.findAll();
        assertThat(ns_Match_ScheduleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNs_Match_Schedule() throws Exception {
        // Initialize the database
        ns_Match_ScheduleRepository.saveAndFlush(ns_Match_Schedule);
        int databaseSizeBeforeDelete = ns_Match_ScheduleRepository.findAll().size();

        // Get the ns_Match_Schedule
        restNs_Match_ScheduleMockMvc.perform(delete("/api/ns-match-schedules/{id}", ns_Match_Schedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ns_Match_Schedule> ns_Match_ScheduleList = ns_Match_ScheduleRepository.findAll();
        assertThat(ns_Match_ScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ns_Match_Schedule.class);
        Ns_Match_Schedule ns_Match_Schedule1 = new Ns_Match_Schedule();
        ns_Match_Schedule1.setId(1L);
        Ns_Match_Schedule ns_Match_Schedule2 = new Ns_Match_Schedule();
        ns_Match_Schedule2.setId(ns_Match_Schedule1.getId());
        assertThat(ns_Match_Schedule1).isEqualTo(ns_Match_Schedule2);
        ns_Match_Schedule2.setId(2L);
        assertThat(ns_Match_Schedule1).isNotEqualTo(ns_Match_Schedule2);
        ns_Match_Schedule1.setId(null);
        assertThat(ns_Match_Schedule1).isNotEqualTo(ns_Match_Schedule2);
    }
}
