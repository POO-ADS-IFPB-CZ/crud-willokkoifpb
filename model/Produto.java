package model;

import java.io.Serializable;
import java.util.Objects;

public class Produto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int codigo;
    private String descricao;
    private double preco;

    public Produto() {
    }

    public Produto(int codigo, String descricao, double preco) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
    }

    // --- Getters e Setters ---
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Produto other = (Produto) obj;
        return codigo == other.codigo;
    }
    
    @Override
    public String toString() {
        return "Produto{" + "codigo=" + codigo + ", descricao='" + descricao + '\'' + ", preco=" + preco + '}';
    }
}
