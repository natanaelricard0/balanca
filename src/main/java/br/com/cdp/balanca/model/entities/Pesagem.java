package br.com.cdp.balanca.model.entities;

import java.sql.Timestamp;

public class Pesagem {

    private Integer idAutorizacao;
    private Integer idPesagem;
    private Float pesoBruto;
    private Float tara;
    private String placa;
    private String notaFiscal;
    private String usuarioPrimeiraPesagem;
    private String usuarioSegundaPesagem;
    private Timestamp dataPrimeiraPesagem;
    private Timestamp dataSegundapesagem;

    public Pesagem(){

    }

    public Pesagem(Integer idAutorizacao, Integer idPesagem, Float pesoBruto, Float tara, String placa, String notaFiscal, String usuarioPrimeiraPesagem, String usuarioSegundaPesagem, Timestamp dataPrimeiraPesagem, Timestamp dataSegundapesagem) {
        this.idAutorizacao = idAutorizacao;
        this.idPesagem = idPesagem;
        this.pesoBruto = pesoBruto;
        this.tara = tara;
        this.placa = placa;
        this.notaFiscal = notaFiscal;
        this.usuarioPrimeiraPesagem = usuarioPrimeiraPesagem;
        this.usuarioSegundaPesagem = usuarioSegundaPesagem;
        this.dataPrimeiraPesagem = dataPrimeiraPesagem;
        this.dataSegundapesagem = dataSegundapesagem;
    }

    public Integer getIdAutorizacao() {
        return idAutorizacao;
    }

    public void setIdAutorizacao(Integer idAutorizacao) {
        this.idAutorizacao = idAutorizacao;
    }

    public Integer getIdPesagem() {
        return idPesagem;
    }

    public void setIdPesagem(Integer idPesagem) {
        this.idPesagem = idPesagem;
    }

    public Float getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(Float pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public Float getTara() {
        return tara;
    }

    public void setTara(Float tara) {
        this.tara = tara;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getUsuarioPrimeiraPesagem() {
        return usuarioPrimeiraPesagem;
    }

    public void setUsuarioPrimeiraPesagem(String usuarioPrimeiraPesagem) {
        this.usuarioPrimeiraPesagem = usuarioPrimeiraPesagem;
    }

    public String getUsuarioSegundaPesagem() {
        return usuarioSegundaPesagem;
    }

    public void setUsuarioSegundaPesagem(String usuarioSegundaPesagem) {
        this.usuarioSegundaPesagem = usuarioSegundaPesagem;
    }

    public Timestamp getDataPrimeiraPesagem() {
        return dataPrimeiraPesagem;
    }

    public void setDataPrimeiraPesagem(Timestamp dataPrimeiraPesagem) {
        this.dataPrimeiraPesagem = dataPrimeiraPesagem;
    }

    public Timestamp getDataSegundapesagem() {
        return dataSegundapesagem;
    }

    public void setDataSegundapesagem(Timestamp dataSegundapesagem) {
        this.dataSegundapesagem = dataSegundapesagem;
    }
}
