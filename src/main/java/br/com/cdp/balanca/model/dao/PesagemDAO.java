package br.com.cdp.balanca.model.dao;

import br.com.cdp.balanca.model.entities.Pesagem;

import java.util.List;

public interface PesagemDAO {

    void insert(Pesagem pesagem);

    void insertPesagemPendente(Pesagem pesagem);

    void updatePesagemPendente(Pesagem pesagem);

    List<Pesagem> findAll();

    List<Pesagem> pesagensPendentes();

    List<Pesagem> buscarPesagensPorPlaca(String valor);

    Pesagem findById(int id);
}
