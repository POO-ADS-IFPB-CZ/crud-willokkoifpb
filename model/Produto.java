package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa um produto com código, descrição e preço.
 * Implementa Serializable para permitir que objetos desta classe sejam gravados em arquivo.
 */
public class Produto implements Serializable {

    // Garante compatibilidade entre diferentes versões da classe ao serializar.
    private static final long serialVersionUID = 1L;

    private int codigo;
    private String descricao;
    private double preco;

    // Construtor padrão (necessário para algumas operações)
    public Produto() {
    }

    // Construtor com todos os campos
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

    /**
     * O método hashCode é baseado apenas no 'codigo',
     * pois ele é o identificador único do produto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    /**
     * O método equals compara dois produtos com base apenas no 'codigo'.
     * Dois produtos são considerados iguais se tiverem o mesmo código.
     */
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