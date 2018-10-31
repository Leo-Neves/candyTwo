package io.candy.web.rest;

import io.candy.CandyShopApplicationApp;

import io.candy.domain.Usuario;
import io.candy.repository.UsuarioRepository;
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

import java.util.List;


import static io.candy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UsuarioResource REST controller.
 *
 * @see UsuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CandyShopApplicationApp.class)
public class UsuarioResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuarioResource usuarioResource = new UsuarioResource(usuarioRepository);
        this.restUsuarioMockMvc = MockMvcBuilders.standaloneSetup(usuarioResource)
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
    public static Usuario createEntity() {
        Usuario usuario = new Usuario()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .token(DEFAULT_TOKEN);
        return usuario;
    }

    @Before
    public void initTest() {
        usuarioRepository.deleteAll();
        usuario = createEntity();
    }

    @Test
    public void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testUsuario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsuario.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    public void createUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario with an existing ID
        usuario.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setNome(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setEmail(null);

        // Create the Usuario, which fails.

        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.save(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }
    
    @Test
    public void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.save(usuario);

        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    public void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.save(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
        updatedUsuario
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .token(UPDATED_TOKEN);

        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuario)))
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUsuario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsuario.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    public void updateNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Create the Usuario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.save(usuario);

        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Get the usuario
        restUsuarioMockMvc.perform(delete("/api/usuarios/{id}", usuario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuario.class);
        Usuario usuario1 = new Usuario();
        usuario1.setId("id1");
        Usuario usuario2 = new Usuario();
        usuario2.setId(usuario1.getId());
        assertThat(usuario1).isEqualTo(usuario2);
        usuario2.setId("id2");
        assertThat(usuario1).isNotEqualTo(usuario2);
        usuario1.setId(null);
        assertThat(usuario1).isNotEqualTo(usuario2);
    }
}
