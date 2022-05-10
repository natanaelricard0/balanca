package br.com.cdp.balanca.model.dao;

import br.com.cdp.balanca.model.entities.Pesagem;

import java.sql.Timestamp;
import java.util.List;

public interface PesagemDAO {

    void insert(Pesagem pesagem);

    void insertPesagemPendente(Pesagem pesagem);

    void updatePesagemPendente(Pesagem pesagem);

    List<Pesagem> findAll();

    List<Pesagem> pesagensPendentes();

    List<Pesagem> buscarPesagensPorPlaca(String valor);

    List<Pesagem> buscaPorParametro(String usuarioScap, String placa, Timestamp dataInicial, Timestamp dataFinal, char tipo);

    Pesagem findById(int id);
}
