package io.candy.web.rest;

import io.candy.CandyShopApplicationApp;

import io.candy.domain.Ponto;
import io.candy.repository.PontoRepository;
import io.candy.web.rest.errors.ExceptionTranslator;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static io.candy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PontoResource REST controller.
 *
 * @see PontoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CandyShopApplicationApp.class)
public class PontoResourceIntTest {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final LocalDate DEFAULT_VALIDADE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDADE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PontoRepository pontoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPontoMockMvc;

    private Ponto ponto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PontoResource pontoResource = new PontoResource(pontoRepository);
        this.restPontoMockMvc = MockMvcBuilders.standaloneSetup(pontoResource)
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
    public static Ponto createEntity() {
        Ponto ponto = new Ponto()
            .quantidade(DEFAULT_QUANTIDADE)
            .validade(DEFAULT_VALIDADE);
        return ponto;
    }

    @Before
    public void initTest() {
        pontoRepository.deleteAll();
        ponto = createEntity();
    }

    @Test
    public void createPonto() throws Exception {
        int databaseSizeBeforeCreate = pontoRepository.findAll().size();

        // Create the Ponto
        restPontoMockMvc.perform(post("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponto)))
            .andExpect(status().isCreated());

        // Validate the Ponto in the database
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeCreate + 1);
        Ponto testPonto = pontoList.get(pontoList.size() - 1);
        assertThat(testPonto.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testPonto.getValidade()).isEqualTo(DEFAULT_VALIDADE);
    }

    @Test
    public void createPontoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pontoRepository.findAll().size();

        // Create the Ponto with an existing ID
        ponto.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPontoMockMvc.perform(post("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponto)))
            .andExpect(status().isBadRequest());

        // Validate the Ponto in the database
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPontos() throws Exception {
        // Initialize the database
        pontoRepository.save(ponto);

        // Get all the pontoList
        restPontoMockMvc.perform(get("/api/pontos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponto.getId())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE.toString())));
    }
    
    @Test
    public void getPonto() throws Exception {
        // Initialize the database
        pontoRepository.save(ponto);

        // Get the ponto
        restPontoMockMvc.perform(get("/api/pontos/{id}", ponto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ponto.getId()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.validade").value(DEFAULT_VALIDADE.toString()));
    }

    @Test
    public void getNonExistingPonto() throws Exception {
        // Get the ponto
        restPontoMockMvc.perform(get("/api/pontos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePonto() throws Exception {
        // Initialize the database
        pontoRepository.save(ponto);

        int databaseSizeBeforeUpdate = pontoRepository.findAll().size();

        // Update the ponto
        Ponto updatedPonto = pontoRepository.findById(ponto.getId()).get();
        updatedPonto
            .quantidade(UPDATED_QUANTIDADE)
            .validade(UPDATED_VALIDADE);

        restPontoMockMvc.perform(put("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPonto)))
            .andExpect(status().isOk());

        // Validate the Ponto in the database
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeUpdate);
        Ponto testPonto = pontoList.get(pontoList.size() - 1);
        assertThat(testPonto.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testPonto.getValidade()).isEqualTo(UPDATED_VALIDADE);
    }

    @Test
    public void updateNonExistingPonto() throws Exception {
        int databaseSizeBeforeUpdate = pontoRepository.findAll().size();

        // Create the Ponto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPontoMockMvc.perform(put("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponto)))
            .andExpect(status().isBadRequest());

        // Validate the Ponto in the database
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePonto() throws Exception {
        // Initialize the database
        pontoRepository.save(ponto);

        int databaseSizeBeforeDelete = pontoRepository.findAll().size();

        // Get the ponto
        restPontoMockMvc.perform(delete("/api/pontos/{id}", ponto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ponto.class);
        Ponto ponto1 = new Ponto();
        ponto1.setId("id1");
        Ponto ponto2 = new Ponto();
        ponto2.setId(ponto1.getId());
        assertThat(ponto1).isEqualTo(ponto2);
        ponto2.setId("id2");
        assertThat(ponto1).isNotEqualTo(ponto2);
        ponto1.setId(null);
        assertThat(ponto1).isNotEqualTo(ponto2);
    }
}
