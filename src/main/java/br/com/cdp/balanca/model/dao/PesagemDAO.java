package br.com.cdp.balanca.model.dao;

import br.com.cdp.balanca.model.entities.Pesagem;

import java.util.List;

public interface PesagemDAO {

    void insert(Pesagem pesagem);

    void update(Pesagem pesagem);

    List<Pesagem> findAll();

    Pesagem findById();
}
