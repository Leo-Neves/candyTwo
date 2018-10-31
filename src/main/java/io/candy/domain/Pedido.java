package io.candy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Pedido.
 */
@Document(collection = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("data")
    private LocalDate data;

    @Field("municipio")
    private String municipio;

    @Field("area")
    private Double area;

    @Field("geometria")
    private String geometria;

    @DBRef
    @Field("produtos")
    private Set<Produto> produtos = new HashSet<>();

    @DBRef
    @Field("pedido")
    @JsonIgnoreProperties("pedidos")
    private Usuario pedido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public Pedido data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Pedido municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Double getArea() {
        return area;
    }

    public Pedido area(Double area) {
        this.area = area;
        return this;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getGeometria() {
        return geometria;
    }

    public Pedido geometria(String geometria) {
        this.geometria = geometria;
        return this;
    }

    public void setGeometria(String geometria) {
        this.geometria = geometria;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Pedido produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Pedido addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.getPedidos().add(this);
        return this;
    }

    public Pedido removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.getPedidos().remove(this);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Usuario getPedido() {
        return pedido;
    }

    public Pedido pedido(Usuario usuario) {
        this.pedido = usuario;
        return this;
    }

    public void setPedido(Usuario usuario) {
        this.pedido = usuario;
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
        Pedido pedido = (Pedido) o;
        if (pedido.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", area=" + getArea() +
            ", geometria='" + getGeometria() + "'" +
            "}";
    }
}
