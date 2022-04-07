package br.com.cdp.balanca.model.entities;

import java.sql.Timestamp;

public class AutorizacaoEntradaSaida {

    private Integer idAutorizacaoEntradaSaida;
    private Integer idEmbarqueDesembarque;
    private Integer idVeiculo;
    private Integer idPessoa;
    private Timestamp dataPrevisao;
    private Timestamp dataUso;
    private String tipoEntradaSaida;

    public AutorizacaoEntradaSaida(){

    }

    public AutorizacaoEntradaSaida(Integer idAutorizacaoEntradaSaida, Integer idEmbarqueDesembarque, Integer idVeiculo, Integer idPessoa, Timestamp dataPrevisao, Timestamp dataUso, String tipoEntradaSaida) {
        this.idAutorizacaoEntradaSaida = idAutorizacaoEntradaSaida;
        this.idEmbarqueDesembarque = idEmbarqueDesembarque;
        this.idVeiculo = idVeiculo;
        this.idPessoa = idPessoa;
        this.dataPrevisao = dataPrevisao;
        this.dataUso = dataUso;
        this.tipoEntradaSaida = tipoEntradaSaida;
    }

    public Integer getIdAutorizacaoEntradaSaida() {
        return idAutorizacaoEntradaSaida;
    }

    public void setIdAutorizacaoEntradaSaida(Integer idAutorizacaoEntradaSaida) {
        this.idAutorizacaoEntradaSaida = idAutorizacaoEntradaSaida;
    }

    public Integer getIdEmbarqueDesembarque() {
        return idEmbarqueDesembarque;
    }

    public void setIdEmbarqueDesembarque(Integer idEmbarqueDesembarque) {
        this.idEmbarqueDesembarque = idEmbarqueDesembarque;
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

    public Timestamp getDataPrevisao() {
        return dataPrevisao;
    }

    public void setDataPrevisao(Timestamp dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
    }

    public Timestamp getDataUso() {
        return dataUso;
    }

    public void setDataUso(Timestamp dataUso) {
        this.dataUso = dataUso;
    }

    public String getTipoEntradaSaida() {
        return tipoEntradaSaida;
    }

    public void setTipoEntradaSaida(String tipoEntradaSaida) {
        this.tipoEntradaSaida = tipoEntradaSaida;
    }

    public Boolean autorizacaIsValid() {
        if (idVeiculo == 0 || idPessoa == 0 || dataUso == null) {
            return true;
        } else return false;
    }
}
