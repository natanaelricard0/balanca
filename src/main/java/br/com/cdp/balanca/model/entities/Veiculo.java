package br.com.cdp.balanca.model.entities;

import java.sql.Timestamp;

public class Veiculo {

    private Integer nsuPesagem;
    private Integer idVeiculo;
    private Integer idPessoa;
    private String placaVeiculo;
    private Float pesoTara;
    private Timestamp dataPesagem;
    private String usuario;
    private String nomeUsuario;

    public Veiculo(){

    }

    public Veiculo(Integer idVeiculo, Integer idPessoa, String placaVeiculo, Float pesoTara, Timestamp dataPesagem, String usuario, String nomeUsuario, Integer nsuPesagem) {
        this.idVeiculo = idVeiculo;
        this.idPessoa = idPessoa;
        this.placaVeiculo = placaVeiculo;
        this.pesoTara = pesoTara;
        this.dataPesagem = dataPesagem;
        this.usuario = usuario;
        this.nomeUsuario = nomeUsuario;
        this.nsuPesagem = nsuPesagem;
    }

    public Integer getNsuPesagem() {
        return nsuPesagem;
    }

    public void setNsuPesagem(Integer nsuPesagem) {
        this.nsuPesagem = nsuPesagem;
    }

    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Integer idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Float getPesoTara() {
        return pesoTara;
    }

    public void setPesoTara(Float pesoTara) {
        this.pesoTara = pesoTara;
    }

    public Timestamp getDataPesagem() {
        return dataPesagem;
    }

    public void setDataPesagem(Timestamp dataPesagem) {
        this.dataPesagem = dataPesagem;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
