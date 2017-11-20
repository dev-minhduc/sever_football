package com.sever.football.web.rest;

import com.sever.football.SvFootballApp;

import com.sever.football.domain.Ns_Livestream;
import com.sever.football.repository.Ns_LivestreamRepository;
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
 * Test class for the Ns_LivestreamResource REST controller.
 *
 * @see Ns_LivestreamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SvFootballApp.class)
public class Ns_LivestreamResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_THUMB_1 = "AAAAAAAAAA";
    private static final String UPDATED_THUMB_1 = "BBBBBBBBBB";

    private static final String DEFAULT_THUMB_2 = "AAAAAAAAAA";
    private static final String UPDATED_THUMB_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private Ns_LivestreamRepository ns_LivestreamRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNs_LivestreamMockMvc;

    private Ns_Livestream ns_Livestream;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Ns_LivestreamResource ns_LivestreamResource = new Ns_LivestreamResource(ns_LivestreamRepository);
        this.restNs_LivestreamMockMvc = MockMvcBuilders.standaloneSetup(ns_LivestreamResource)
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
    public static Ns_Livestream createEntity(EntityManager em) {
        Ns_Livestream ns_Livestream = new Ns_Livestream()
            .title(DEFAULT_TITLE)
            .link(DEFAULT_LINK)
            .thumb1(DEFAULT_THUMB_1)
            .thumb2(DEFAULT_THUMB_2)
            .status(DEFAULT_STATUS);
        return ns_Livestream;
    }

    @Before
    public void initTest() {
        ns_Livestream = createEntity(em);
    }

    @Test
    @Transactional
    public void createNs_Livestream() throws Exception {
        int databaseSizeBeforeCreate = ns_LivestreamRepository.findAll().size();

        // Create the Ns_Livestream
        restNs_LivestreamMockMvc.perform(post("/api/ns-livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Livestream)))
            .andExpect(status().isCreated());

        // Validate the Ns_Livestream in the database
        List<Ns_Livestream> ns_LivestreamList = ns_LivestreamRepository.findAll();
        assertThat(ns_LivestreamList).hasSize(databaseSizeBeforeCreate + 1);
        Ns_Livestream testNs_Livestream = ns_LivestreamList.get(ns_LivestreamList.size() - 1);
        assertThat(testNs_Livestream.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNs_Livestream.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testNs_Livestream.getThumb1()).isEqualTo(DEFAULT_THUMB_1);
        assertThat(testNs_Livestream.getThumb2()).isEqualTo(DEFAULT_THUMB_2);
        assertThat(testNs_Livestream.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createNs_LivestreamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ns_LivestreamRepository.findAll().size();

        // Create the Ns_Livestream with an existing ID
        ns_Livestream.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNs_LivestreamMockMvc.perform(post("/api/ns-livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Livestream)))
            .andExpect(status().isBadRequest());

        // Validate the Ns_Livestream in the database
        List<Ns_Livestream> ns_LivestreamList = ns_LivestreamRepository.findAll();
        assertThat(ns_LivestreamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = ns_LivestreamRepository.findAll().size();
        // set the field null
        ns_Livestream.setTitle(null);

        // Create the Ns_Livestream, which fails.

        restNs_LivestreamMockMvc.perform(post("/api/ns-livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Livestream)))
            .andExpect(status().isBadRequest());

        List<Ns_Livestream> ns_LivestreamList = ns_LivestreamRepository.findAll();
        assertThat(ns_LivestreamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThumb1IsRequired() throws Exception {
        int databaseSizeBeforeTest = ns_LivestreamRepository.findAll().size();
        // set the field null
        ns_Livestream.setThumb1(null);

        // Create the Ns_Livestream, which fails.

        restNs_LivestreamMockMvc.perform(post("/api/ns-livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Livestream)))
            .andExpect(status().isBadRequest());

        List<Ns_Livestream> ns_LivestreamList = ns_LivestreamRepository.findAll();
        assertThat(ns_LivestreamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThumb2IsRequired() throws Exception {
        int databaseSizeBeforeTest = ns_LivestreamRepository.findAll().size();
        // set the field null
        ns_Livestream.setThumb2(null);

        // Create the Ns_Livestream, which fails.

        restNs_LivestreamMockMvc.perform(post("/api/ns-livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Livestream)))
            .andExpect(status().isBadRequest());

        List<Ns_Livestream> ns_LivestreamList = ns_LivestreamRepository.findAll();
        assertThat(ns_LivestreamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNs_Livestreams() throws Exception {
        // Initialize the database
        ns_LivestreamRepository.saveAndFlush(ns_Livestream);

        // Get all the ns_LivestreamList
        restNs_LivestreamMockMvc.perform(get("/api/ns-livestreams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ns_Livestream.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].thumb1").value(hasItem(DEFAULT_THUMB_1.toString())))
            .andExpect(jsonPath("$.[*].thumb2").value(hasItem(DEFAULT_THUMB_2.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getNs_Livestream() throws Exception {
        // Initialize the database
        ns_LivestreamRepository.saveAndFlush(ns_Livestream);

        // Get the ns_Livestream
        restNs_LivestreamMockMvc.perform(get("/api/ns-livestreams/{id}", ns_Livestream.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ns_Livestream.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.thumb1").value(DEFAULT_THUMB_1.toString()))
            .andExpect(jsonPath("$.thumb2").value(DEFAULT_THUMB_2.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNs_Livestream() throws Exception {
        // Get the ns_Livestream
        restNs_LivestreamMockMvc.perform(get("/api/ns-livestreams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNs_Livestream() throws Exception {
        // Initialize the database
        ns_LivestreamRepository.saveAndFlush(ns_Livestream);
        int databaseSizeBeforeUpdate = ns_LivestreamRepository.findAll().size();

        // Update the ns_Livestream
        Ns_Livestream updatedNs_Livestream = ns_LivestreamRepository.findOne(ns_Livestream.getId());
        updatedNs_Livestream
            .title(UPDATED_TITLE)
            .link(UPDATED_LINK)
            .thumb1(UPDATED_THUMB_1)
            .thumb2(UPDATED_THUMB_2)
            .status(UPDATED_STATUS);

        restNs_LivestreamMockMvc.perform(put("/api/ns-livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNs_Livestream)))
            .andExpect(status().isOk());

        // Validate the Ns_Livestream in the database
        List<Ns_Livestream> ns_LivestreamList = ns_LivestreamRepository.findAll();
        assertThat(ns_LivestreamList).hasSize(databaseSizeBeforeUpdate);
        Ns_Livestream testNs_Livestream = ns_LivestreamList.get(ns_LivestreamList.size() - 1);
        assertThat(testNs_Livestream.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNs_Livestream.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testNs_Livestream.getThumb1()).isEqualTo(UPDATED_THUMB_1);
        assertThat(testNs_Livestream.getThumb2()).isEqualTo(UPDATED_THUMB_2);
        assertThat(testNs_Livestream.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingNs_Livestream() throws Exception {
        int databaseSizeBeforeUpdate = ns_LivestreamRepository.findAll().size();

        // Create the Ns_Livestream

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNs_LivestreamMockMvc.perform(put("/api/ns-livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Livestream)))
            .andExpect(status().isCreated());

        // Validate the Ns_Livestream in the database
        List<Ns_Livestream> ns_LivestreamList = ns_LivestreamRepository.findAll();
        assertThat(ns_LivestreamList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNs_Livestream() throws Exception {
        // Initialize the database
        ns_LivestreamRepository.saveAndFlush(ns_Livestream);
        int databaseSizeBeforeDelete = ns_LivestreamRepository.findAll().size();

        // Get the ns_Livestream
        restNs_LivestreamMockMvc.perform(delete("/api/ns-livestreams/{id}", ns_Livestream.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ns_Livestream> ns_LivestreamList = ns_LivestreamRepository.findAll();
        assertThat(ns_LivestreamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ns_Livestream.class);
        Ns_Livestream ns_Livestream1 = new Ns_Livestream();
        ns_Livestream1.setId(1L);
        Ns_Livestream ns_Livestream2 = new Ns_Livestream();
        ns_Livestream2.setId(ns_Livestream1.getId());
        assertThat(ns_Livestream1).isEqualTo(ns_Livestream2);
        ns_Livestream2.setId(2L);
        assertThat(ns_Livestream1).isNotEqualTo(ns_Livestream2);
        ns_Livestream1.setId(null);
        assertThat(ns_Livestream1).isNotEqualTo(ns_Livestream2);
    }
}
