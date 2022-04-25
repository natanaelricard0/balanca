package br.com.cdp.balanca.model.entities;

public class ItemAutorizacao {
    private Integer idAutorizacaoEntradaSaida;
    private Integer idItemEmbarqueDesembarque;
    private Integer idSubitemEmbarqueDesembarque;
    private String descricao;
    private Float peso;

    public ItemAutorizacao(){

    }

    public Integer getIdAutorizacaoEntradaSaida() {
        return idAutorizacaoEntradaSaida;
    }

    public void setIdAutorizacaoEntradaSaida(Integer idAutorizacaoEntradaSaida) {
        this.idAutorizacaoEntradaSaida = idAutorizacaoEntradaSaida;
    }

    public Integer getIdItemEmbarqueDesembarque() {
        return idItemEmbarqueDesembarque;
    }

    public void setIdItemEmbarqueDesembarque(Integer idItemEmbarqueDesembarque) {
        this.idItemEmbarqueDesembarque = idItemEmbarqueDesembarque;
    }

    public Integer getIdSubitemEmbarqueDesembarque() {
        return idSubitemEmbarqueDesembarque;
    }

    public void setIdSubitemEmbarqueDesembarque(Integer idSubitemEmbarqueDesembarque) {
        this.idSubitemEmbarqueDesembarque = idSubitemEmbarqueDesembarque;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Float getPeso() {
        return peso;
    }
    public void setPeso(Float peso) {
        this.peso = peso;
    }
}
