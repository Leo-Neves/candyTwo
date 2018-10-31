package io.candy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ponto.
 */
@Document(collection = "ponto")
public class Ponto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("quantidade")
    private Integer quantidade;

    @Field("validade")
    private LocalDate validade;

    @DBRef
    @Field("usuario")
    private Set<Usuario> usuarios = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Ponto quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public Ponto validade(LocalDate validade) {
        this.validade = validade;
        return this;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public Ponto usuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
        return this;
    }

    public Ponto addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.setPonto(this);
        return this;
    }

    public Ponto removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.setPonto(null);
        return this;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ponto ponto = (Ponto) o;
        if (ponto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ponto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ponto{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", validade='" + getValidade() + "'" +
            "}";
    }
}
