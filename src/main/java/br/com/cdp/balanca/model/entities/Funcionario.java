package br.com.cdp.balanca.model.entities;

public class Funcionario {

    private Long id;
    private String loginRede;
    private String senha;
    private String loginScap;
    private String nome;
    private Boolean administrador;
    private Boolean ativo;

    public Funcionario(){

    }

    public Funcionario(Long id, String loginRede, String senha, String loginScap, String nome, Boolean administrador, Boolean ativo) {
        this.id = id;
        this.loginRede = loginRede;
        this.senha = senha;
        this.loginScap = loginScap;
        this.nome = nome;
        this.administrador = administrador;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginRede() {
        return loginRede;
    }

    public void setLoginRede(String loginRede) {
        this.loginRede = loginRede;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLoginScap() {
        return loginScap;
    }

    public void setLoginScap(String loginScap) {
        this.loginScap = loginScap;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
