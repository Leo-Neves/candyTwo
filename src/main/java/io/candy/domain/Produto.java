package io.candy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Produto.
 */
@Document(collection = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nome")
    private String nome;

    @Field("preco")
    private String preco;

    @Field("estoque")
    private Integer estoque;

    @DBRef
    @Field("categoria")
    @JsonIgnoreProperties("produtos")
    private Categoria categoria;

    @DBRef
    @Field("pedidos")
    @JsonIgnore
    private Set<Pedido> pedidos = new HashSet<>();

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

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public Produto preco(String preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public Produto estoque(Integer estoque) {
        this.estoque = estoque;
        return this;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Produto categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public Produto pedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        return this;
    }

    public Produto addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.getProdutos().add(this);
        return this;
    }

    public Produto removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.getProdutos().remove(this);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
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
        Produto produto = (Produto) o;
        if (produto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", preco='" + getPreco() + "'" +
            ", estoque=" + getEstoque() +
            "}";
    }
}
