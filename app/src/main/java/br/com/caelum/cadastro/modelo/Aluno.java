package br.com.caelum.cadastro.modelo;

import java.io.Serializable;

/**
 * Created by android6920 on 19/07/17.
 */

public class Aluno implements Serializable {

    private String telefone;
    private String endereco;
    private String site;
    private Double nota;
    private Long id;
    private String nome;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /** A forma que o aluno será mostrado na listagem */
    public String toString() {
        return this.getId() + " - " + this.getNome();
    }
}
