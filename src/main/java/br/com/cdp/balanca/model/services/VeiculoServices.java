package br.com.cdp.balanca.model.services;

import br.com.cdp.balanca.model.dao.DaoFactory;
import br.com.cdp.balanca.model.dao.VeiculoDAO;
import br.com.cdp.balanca.model.entities.Veiculo;

import java.util.List;

public class VeiculoServices {
    VeiculoDAO service = DaoFactory.createVeiculoDao();

    public void insert(Veiculo veiculo){
        service.insertTara(veiculo);
    }

    public List<Veiculo> findAll(){
        return service.findAll();
    }

    public List<Veiculo> findPesagenById(int id){
        return service.findPesagemById(id);
    }

    public  Veiculo findById(int id){
        return service.findById(id);
    }

    public Veiculo findPlacaVeiculo(int id){
        return service.findVeiculo(id);
    }
}
