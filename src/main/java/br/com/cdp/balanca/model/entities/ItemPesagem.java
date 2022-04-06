package br.com.cdp.balanca.model.entities;

public class ItemPesagem {

    private Integer idAutorizacaoEntradaSaida;
    private Integer idItemIO;
    private Integer idSubitemIO;
    private Float pesoLiquido;

    public ItemPesagem(){}

    public ItemPesagem(Integer idAutorizacaoEntradaSaida, Integer idItemIO, Integer idSubitemIO, Float pesoLiquido) {
        this.idAutorizacaoEntradaSaida = idAutorizacaoEntradaSaida;
        this.idItemIO = idItemIO;
        this.idSubitemIO = idSubitemIO;
        this.pesoLiquido = pesoLiquido;
    }

    public Integer getIdAutorizacaoEntradaSaida() {
        return idAutorizacaoEntradaSaida;
    }

    public void setIdAutorizacaoEntradaSaida(Integer idAutorizacaoEntradaSaida) {
        this.idAutorizacaoEntradaSaida = idAutorizacaoEntradaSaida;
    }

    public Integer getIdItemIO() {
        return idItemIO;
    }

    public void setIdItemIO(Integer idItemIO) {
        this.idItemIO = idItemIO;
    }

    public Integer getIdSubitemIO() {
        return idSubitemIO;
    }

    public void setIdSubitemIO(Integer idSubitemIO) {
        this.idSubitemIO = idSubitemIO;
    }

    public Float getPesoLiquido() {
        return pesoLiquido;
    }

    public void setPesoLiquido(Float pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
    }
}
