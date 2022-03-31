package br.com.cdp.balanca.model.dao;

import br.com.cdp.balanca.model.entities.Veiculo;

import java.util.List;

public interface VeiculoDAO {

    Veiculo findById(int id);

    void insertTara(Veiculo veiculo);

    List<Veiculo> findAll();

    List<Veiculo> findPesagemById(int id);

    Veiculo findVeiculo(int id);

    Integer findLastIdIncrement();
}
