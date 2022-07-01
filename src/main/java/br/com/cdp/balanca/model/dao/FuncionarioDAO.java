package br.com.cdp.balanca.model.dao;

import br.com.cdp.balanca.model.entities.Funcionario;

import java.util.List;

public interface FuncionarioDAO {

    void insert(Funcionario func);

    void update(Funcionario func);

    Funcionario findById(int id);

    Funcionario findByLogin(String loginRede);

    List<Funcionario> findAll();

    List<Funcionario> find(String pesquisa);

    Funcionario findLoginByCpf(String cpf);
}
