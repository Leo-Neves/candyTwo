package io.candy.web.rest;

import io.candy.CandyShopApplicationApp;

import io.candy.domain.Pedido;
import io.candy.repository.PedidoRepository;
import io.candy.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static io.candy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PedidoResource REST controller.
 *
 * @see PedidoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CandyShopApplicationApp.class)
public class PedidoResourceIntTest {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final Double DEFAULT_AREA = 1D;
    private static final Double UPDATED_AREA = 2D;

    private static final String DEFAULT_GEOMETRIA = "AAAAAAAAAA";
    private static final String UPDATED_GEOMETRIA = "BBBBBBBBBB";

    @Autowired
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoRepository pedidoRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPedidoMockMvc;

    private Pedido pedido;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PedidoResource pedidoResource = new PedidoResource(pedidoRepository);
        this.restPedidoMockMvc = MockMvcBuilders.standaloneSetup(pedidoResource)
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
    public static Pedido createEntity() {
        Pedido pedido = new Pedido()
            .data(DEFAULT_DATA)
            .municipio(DEFAULT_MUNICIPIO)
            .area(DEFAULT_AREA)
            .geometria(DEFAULT_GEOMETRIA);
        return pedido;
    }

    @Before
    public void initTest() {
        pedidoRepository.deleteAll();
        pedido = createEntity();
    }

    @Test
    public void createPedido() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedido)))
            .andExpect(status().isCreated());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate + 1);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPedido.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testPedido.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testPedido.getGeometria()).isEqualTo(DEFAULT_GEOMETRIA);
    }

    @Test
    public void createPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pedidoRepository.findAll().size();

        // Create the Pedido with an existing ID
        pedido.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidoMockMvc.perform(post("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedido)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPedidos() throws Exception {
        // Initialize the database
        pedidoRepository.save(pedido);

        // Get all the pedidoList
        restPedidoMockMvc.perform(get("/api/pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedido.getId())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].geometria").value(hasItem(DEFAULT_GEOMETRIA.toString())));
    }
    
    public void getAllPedidosWithEagerRelationshipsIsEnabled() throws Exception {
        PedidoResource pedidoResource = new PedidoResource(pedidoRepositoryMock);
        when(pedidoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPedidoMockMvc = MockMvcBuilders.standaloneSetup(pedidoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPedidoMockMvc.perform(get("/api/pedidos?eagerload=true"))
        .andExpect(status().isOk());

        verify(pedidoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllPedidosWithEagerRelationshipsIsNotEnabled() throws Exception {
        PedidoResource pedidoResource = new PedidoResource(pedidoRepositoryMock);
            when(pedidoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPedidoMockMvc = MockMvcBuilders.standaloneSetup(pedidoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPedidoMockMvc.perform(get("/api/pedidos?eagerload=true"))
        .andExpect(status().isOk());

            verify(pedidoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getPedido() throws Exception {
        // Initialize the database
        pedidoRepository.save(pedido);

        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", pedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pedido.getId()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.doubleValue()))
            .andExpect(jsonPath("$.geometria").value(DEFAULT_GEOMETRIA.toString()));
    }

    @Test
    public void getNonExistingPedido() throws Exception {
        // Get the pedido
        restPedidoMockMvc.perform(get("/api/pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePedido() throws Exception {
        // Initialize the database
        pedidoRepository.save(pedido);

        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Update the pedido
        Pedido updatedPedido = pedidoRepository.findById(pedido.getId()).get();
        updatedPedido
            .data(UPDATED_DATA)
            .municipio(UPDATED_MUNICIPIO)
            .area(UPDATED_AREA)
            .geometria(UPDATED_GEOMETRIA);

        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPedido)))
            .andExpect(status().isOk());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
        Pedido testPedido = pedidoList.get(pedidoList.size() - 1);
        assertThat(testPedido.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPedido.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testPedido.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testPedido.getGeometria()).isEqualTo(UPDATED_GEOMETRIA);
    }

    @Test
    public void updateNonExistingPedido() throws Exception {
        int databaseSizeBeforeUpdate = pedidoRepository.findAll().size();

        // Create the Pedido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidoMockMvc.perform(put("/api/pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pedido)))
            .andExpect(status().isBadRequest());

        // Validate the Pedido in the database
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePedido() throws Exception {
        // Initialize the database
        pedidoRepository.save(pedido);

        int databaseSizeBeforeDelete = pedidoRepository.findAll().size();

        // Get the pedido
        restPedidoMockMvc.perform(delete("/api/pedidos/{id}", pedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pedido> pedidoList = pedidoRepository.findAll();
        assertThat(pedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pedido.class);
        Pedido pedido1 = new Pedido();
        pedido1.setId("id1");
        Pedido pedido2 = new Pedido();
        pedido2.setId(pedido1.getId());
        assertThat(pedido1).isEqualTo(pedido2);
        pedido2.setId("id2");
        assertThat(pedido1).isNotEqualTo(pedido2);
        pedido1.setId(null);
        assertThat(pedido1).isNotEqualTo(pedido2);
    }
}
