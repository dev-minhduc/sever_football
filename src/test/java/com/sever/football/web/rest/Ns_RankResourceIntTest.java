package com.sever.football.web.rest;

import com.sever.football.SvFootballApp;

import com.sever.football.domain.Ns_Rank;
import com.sever.football.repository.Ns_RankRepository;
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
 * Test class for the Ns_RankResource REST controller.
 *
 * @see Ns_RankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SvFootballApp.class)
public class Ns_RankResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_BATTLE = 1;
    private static final Integer UPDATED_BATTLE = 2;

    private static final Integer DEFAULT_WIN = 1;
    private static final Integer UPDATED_WIN = 2;

    private static final Integer DEFAULT_DRAW = 1;
    private static final Integer UPDATED_DRAW = 2;

    private static final Integer DEFAULT_LOSE = 1;
    private static final Integer UPDATED_LOSE = 2;

    private static final String DEFAULT_DIFF = "AAAAAAAAAA";
    private static final String UPDATED_DIFF = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINT = 1;
    private static final Integer UPDATED_POINT = 2;

    private static final Boolean DEFAULT_SATUS = false;
    private static final Boolean UPDATED_SATUS = true;

    @Autowired
    private Ns_RankRepository ns_RankRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNs_RankMockMvc;

    private Ns_Rank ns_Rank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Ns_RankResource ns_RankResource = new Ns_RankResource(ns_RankRepository);
        this.restNs_RankMockMvc = MockMvcBuilders.standaloneSetup(ns_RankResource)
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
    public static Ns_Rank createEntity(EntityManager em) {
        Ns_Rank ns_Rank = new Ns_Rank()
            .name(DEFAULT_NAME)
            .thumbnail(DEFAULT_THUMBNAIL)
            .battle(DEFAULT_BATTLE)
            .win(DEFAULT_WIN)
            .draw(DEFAULT_DRAW)
            .lose(DEFAULT_LOSE)
            .diff(DEFAULT_DIFF)
            .point(DEFAULT_POINT)
            .satus(DEFAULT_SATUS);
        return ns_Rank;
    }

    @Before
    public void initTest() {
        ns_Rank = createEntity(em);
    }

    @Test
    @Transactional
    public void createNs_Rank() throws Exception {
        int databaseSizeBeforeCreate = ns_RankRepository.findAll().size();

        // Create the Ns_Rank
        restNs_RankMockMvc.perform(post("/api/ns-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Rank)))
            .andExpect(status().isCreated());

        // Validate the Ns_Rank in the database
        List<Ns_Rank> ns_RankList = ns_RankRepository.findAll();
        assertThat(ns_RankList).hasSize(databaseSizeBeforeCreate + 1);
        Ns_Rank testNs_Rank = ns_RankList.get(ns_RankList.size() - 1);
        assertThat(testNs_Rank.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNs_Rank.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testNs_Rank.getBattle()).isEqualTo(DEFAULT_BATTLE);
        assertThat(testNs_Rank.getWin()).isEqualTo(DEFAULT_WIN);
        assertThat(testNs_Rank.getDraw()).isEqualTo(DEFAULT_DRAW);
        assertThat(testNs_Rank.getLose()).isEqualTo(DEFAULT_LOSE);
        assertThat(testNs_Rank.getDiff()).isEqualTo(DEFAULT_DIFF);
        assertThat(testNs_Rank.getPoint()).isEqualTo(DEFAULT_POINT);
        assertThat(testNs_Rank.isSatus()).isEqualTo(DEFAULT_SATUS);
    }

    @Test
    @Transactional
    public void createNs_RankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ns_RankRepository.findAll().size();

        // Create the Ns_Rank with an existing ID
        ns_Rank.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNs_RankMockMvc.perform(post("/api/ns-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Rank)))
            .andExpect(status().isBadRequest());

        // Validate the Ns_Rank in the database
        List<Ns_Rank> ns_RankList = ns_RankRepository.findAll();
        assertThat(ns_RankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNs_Ranks() throws Exception {
        // Initialize the database
        ns_RankRepository.saveAndFlush(ns_Rank);

        // Get all the ns_RankList
        restNs_RankMockMvc.perform(get("/api/ns-ranks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ns_Rank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL.toString())))
            .andExpect(jsonPath("$.[*].battle").value(hasItem(DEFAULT_BATTLE)))
            .andExpect(jsonPath("$.[*].win").value(hasItem(DEFAULT_WIN)))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW)))
            .andExpect(jsonPath("$.[*].lose").value(hasItem(DEFAULT_LOSE)))
            .andExpect(jsonPath("$.[*].diff").value(hasItem(DEFAULT_DIFF.toString())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT)))
            .andExpect(jsonPath("$.[*].satus").value(hasItem(DEFAULT_SATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getNs_Rank() throws Exception {
        // Initialize the database
        ns_RankRepository.saveAndFlush(ns_Rank);

        // Get the ns_Rank
        restNs_RankMockMvc.perform(get("/api/ns-ranks/{id}", ns_Rank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ns_Rank.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL.toString()))
            .andExpect(jsonPath("$.battle").value(DEFAULT_BATTLE))
            .andExpect(jsonPath("$.win").value(DEFAULT_WIN))
            .andExpect(jsonPath("$.draw").value(DEFAULT_DRAW))
            .andExpect(jsonPath("$.lose").value(DEFAULT_LOSE))
            .andExpect(jsonPath("$.diff").value(DEFAULT_DIFF.toString()))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT))
            .andExpect(jsonPath("$.satus").value(DEFAULT_SATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNs_Rank() throws Exception {
        // Get the ns_Rank
        restNs_RankMockMvc.perform(get("/api/ns-ranks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNs_Rank() throws Exception {
        // Initialize the database
        ns_RankRepository.saveAndFlush(ns_Rank);
        int databaseSizeBeforeUpdate = ns_RankRepository.findAll().size();

        // Update the ns_Rank
        Ns_Rank updatedNs_Rank = ns_RankRepository.findOne(ns_Rank.getId());
        updatedNs_Rank
            .name(UPDATED_NAME)
            .thumbnail(UPDATED_THUMBNAIL)
            .battle(UPDATED_BATTLE)
            .win(UPDATED_WIN)
            .draw(UPDATED_DRAW)
            .lose(UPDATED_LOSE)
            .diff(UPDATED_DIFF)
            .point(UPDATED_POINT)
            .satus(UPDATED_SATUS);

        restNs_RankMockMvc.perform(put("/api/ns-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNs_Rank)))
            .andExpect(status().isOk());

        // Validate the Ns_Rank in the database
        List<Ns_Rank> ns_RankList = ns_RankRepository.findAll();
        assertThat(ns_RankList).hasSize(databaseSizeBeforeUpdate);
        Ns_Rank testNs_Rank = ns_RankList.get(ns_RankList.size() - 1);
        assertThat(testNs_Rank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNs_Rank.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testNs_Rank.getBattle()).isEqualTo(UPDATED_BATTLE);
        assertThat(testNs_Rank.getWin()).isEqualTo(UPDATED_WIN);
        assertThat(testNs_Rank.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testNs_Rank.getLose()).isEqualTo(UPDATED_LOSE);
        assertThat(testNs_Rank.getDiff()).isEqualTo(UPDATED_DIFF);
        assertThat(testNs_Rank.getPoint()).isEqualTo(UPDATED_POINT);
        assertThat(testNs_Rank.isSatus()).isEqualTo(UPDATED_SATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingNs_Rank() throws Exception {
        int databaseSizeBeforeUpdate = ns_RankRepository.findAll().size();

        // Create the Ns_Rank

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNs_RankMockMvc.perform(put("/api/ns-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Rank)))
            .andExpect(status().isCreated());

        // Validate the Ns_Rank in the database
        List<Ns_Rank> ns_RankList = ns_RankRepository.findAll();
        assertThat(ns_RankList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNs_Rank() throws Exception {
        // Initialize the database
        ns_RankRepository.saveAndFlush(ns_Rank);
        int databaseSizeBeforeDelete = ns_RankRepository.findAll().size();

        // Get the ns_Rank
        restNs_RankMockMvc.perform(delete("/api/ns-ranks/{id}", ns_Rank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ns_Rank> ns_RankList = ns_RankRepository.findAll();
        assertThat(ns_RankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ns_Rank.class);
        Ns_Rank ns_Rank1 = new Ns_Rank();
        ns_Rank1.setId(1L);
        Ns_Rank ns_Rank2 = new Ns_Rank();
        ns_Rank2.setId(ns_Rank1.getId());
        assertThat(ns_Rank1).isEqualTo(ns_Rank2);
        ns_Rank2.setId(2L);
        assertThat(ns_Rank1).isNotEqualTo(ns_Rank2);
        ns_Rank1.setId(null);
        assertThat(ns_Rank1).isNotEqualTo(ns_Rank2);
    }
}
