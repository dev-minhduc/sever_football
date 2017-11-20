package com.sever.football.web.rest;

import com.sever.football.SvFootballApp;

import com.sever.football.domain.Ns_Highlight;
import com.sever.football.repository.Ns_HighlightRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.sever.football.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Ns_HighlightResource REST controller.
 *
 * @see Ns_HighlightResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SvFootballApp.class)
public class Ns_HighlightResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private Ns_HighlightRepository ns_HighlightRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNs_HighlightMockMvc;

    private Ns_Highlight ns_Highlight;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Ns_HighlightResource ns_HighlightResource = new Ns_HighlightResource(ns_HighlightRepository);
        this.restNs_HighlightMockMvc = MockMvcBuilders.standaloneSetup(ns_HighlightResource)
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
    public static Ns_Highlight createEntity(EntityManager em) {
        Ns_Highlight ns_Highlight = new Ns_Highlight()
            .title(DEFAULT_TITLE)
            .img(DEFAULT_IMG)
            .link(DEFAULT_LINK)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS);
        return ns_Highlight;
    }

    @Before
    public void initTest() {
        ns_Highlight = createEntity(em);
    }

    @Test
    @Transactional
    public void createNs_Highlight() throws Exception {
        int databaseSizeBeforeCreate = ns_HighlightRepository.findAll().size();

        // Create the Ns_Highlight
        restNs_HighlightMockMvc.perform(post("/api/ns-highlights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Highlight)))
            .andExpect(status().isCreated());

        // Validate the Ns_Highlight in the database
        List<Ns_Highlight> ns_HighlightList = ns_HighlightRepository.findAll();
        assertThat(ns_HighlightList).hasSize(databaseSizeBeforeCreate + 1);
        Ns_Highlight testNs_Highlight = ns_HighlightList.get(ns_HighlightList.size() - 1);
        assertThat(testNs_Highlight.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNs_Highlight.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testNs_Highlight.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testNs_Highlight.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNs_Highlight.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createNs_HighlightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ns_HighlightRepository.findAll().size();

        // Create the Ns_Highlight with an existing ID
        ns_Highlight.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNs_HighlightMockMvc.perform(post("/api/ns-highlights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Highlight)))
            .andExpect(status().isBadRequest());

        // Validate the Ns_Highlight in the database
        List<Ns_Highlight> ns_HighlightList = ns_HighlightRepository.findAll();
        assertThat(ns_HighlightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = ns_HighlightRepository.findAll().size();
        // set the field null
        ns_Highlight.setTitle(null);

        // Create the Ns_Highlight, which fails.

        restNs_HighlightMockMvc.perform(post("/api/ns-highlights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Highlight)))
            .andExpect(status().isBadRequest());

        List<Ns_Highlight> ns_HighlightList = ns_HighlightRepository.findAll();
        assertThat(ns_HighlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ns_HighlightRepository.findAll().size();
        // set the field null
        ns_Highlight.setDate(null);

        // Create the Ns_Highlight, which fails.

        restNs_HighlightMockMvc.perform(post("/api/ns-highlights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Highlight)))
            .andExpect(status().isBadRequest());

        List<Ns_Highlight> ns_HighlightList = ns_HighlightRepository.findAll();
        assertThat(ns_HighlightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNs_Highlights() throws Exception {
        // Initialize the database
        ns_HighlightRepository.saveAndFlush(ns_Highlight);

        // Get all the ns_HighlightList
        restNs_HighlightMockMvc.perform(get("/api/ns-highlights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ns_Highlight.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getNs_Highlight() throws Exception {
        // Initialize the database
        ns_HighlightRepository.saveAndFlush(ns_Highlight);

        // Get the ns_Highlight
        restNs_HighlightMockMvc.perform(get("/api/ns-highlights/{id}", ns_Highlight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ns_Highlight.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.img").value(DEFAULT_IMG.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNs_Highlight() throws Exception {
        // Get the ns_Highlight
        restNs_HighlightMockMvc.perform(get("/api/ns-highlights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNs_Highlight() throws Exception {
        // Initialize the database
        ns_HighlightRepository.saveAndFlush(ns_Highlight);
        int databaseSizeBeforeUpdate = ns_HighlightRepository.findAll().size();

        // Update the ns_Highlight
        Ns_Highlight updatedNs_Highlight = ns_HighlightRepository.findOne(ns_Highlight.getId());
        updatedNs_Highlight
            .title(UPDATED_TITLE)
            .img(UPDATED_IMG)
            .link(UPDATED_LINK)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS);

        restNs_HighlightMockMvc.perform(put("/api/ns-highlights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNs_Highlight)))
            .andExpect(status().isOk());

        // Validate the Ns_Highlight in the database
        List<Ns_Highlight> ns_HighlightList = ns_HighlightRepository.findAll();
        assertThat(ns_HighlightList).hasSize(databaseSizeBeforeUpdate);
        Ns_Highlight testNs_Highlight = ns_HighlightList.get(ns_HighlightList.size() - 1);
        assertThat(testNs_Highlight.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNs_Highlight.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testNs_Highlight.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testNs_Highlight.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNs_Highlight.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingNs_Highlight() throws Exception {
        int databaseSizeBeforeUpdate = ns_HighlightRepository.findAll().size();

        // Create the Ns_Highlight

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNs_HighlightMockMvc.perform(put("/api/ns-highlights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ns_Highlight)))
            .andExpect(status().isCreated());

        // Validate the Ns_Highlight in the database
        List<Ns_Highlight> ns_HighlightList = ns_HighlightRepository.findAll();
        assertThat(ns_HighlightList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNs_Highlight() throws Exception {
        // Initialize the database
        ns_HighlightRepository.saveAndFlush(ns_Highlight);
        int databaseSizeBeforeDelete = ns_HighlightRepository.findAll().size();

        // Get the ns_Highlight
        restNs_HighlightMockMvc.perform(delete("/api/ns-highlights/{id}", ns_Highlight.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ns_Highlight> ns_HighlightList = ns_HighlightRepository.findAll();
        assertThat(ns_HighlightList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ns_Highlight.class);
        Ns_Highlight ns_Highlight1 = new Ns_Highlight();
        ns_Highlight1.setId(1L);
        Ns_Highlight ns_Highlight2 = new Ns_Highlight();
        ns_Highlight2.setId(ns_Highlight1.getId());
        assertThat(ns_Highlight1).isEqualTo(ns_Highlight2);
        ns_Highlight2.setId(2L);
        assertThat(ns_Highlight1).isNotEqualTo(ns_Highlight2);
        ns_Highlight1.setId(null);
        assertThat(ns_Highlight1).isNotEqualTo(ns_Highlight2);
    }
}
