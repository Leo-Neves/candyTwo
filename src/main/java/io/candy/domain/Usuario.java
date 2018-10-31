package io.candy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Usuario.
 */
@Document(collection = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("nome")
    private String nome;

    @NotNull
    @Field("email")
    private String email;

    @Field("token")
    private String token;

    @DBRef
    @Field("pedido")
    private Set<Pedido> pedidos = new HashSet<>();
    @DBRef
    @Field("ponto")
    @JsonIgnoreProperties("usuarios")
    private Ponto ponto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Usuario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public Usuario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public Usuario token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public Usuario pedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        return this;
    }

    public Usuario addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setPedido(this);
        return this;
    }

    public Usuario removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setPedido(null);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Ponto getPonto() {
        return ponto;
    }

    public Usuario ponto(Ponto ponto) {
        this.ponto = ponto;
        return this;
    }

    public void setPonto(Ponto ponto) {
        this.ponto = ponto;
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
        Usuario usuario = (Usuario) o;
        if (usuario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
