package br.com.cdp.balanca.model.services;

import br.com.cdp.balanca.model.dao.DaoFactory;
import br.com.cdp.balanca.model.dao.VeiculoDAO;
import br.com.cdp.balanca.model.entities.Veiculo;

import java.util.List;

public class VeiculoServices {
    VeiculoDAO service = DaoFactory.createVeiculoDao();

    public  Veiculo findById(int id){
        return service.findById(id);
    }

    public Veiculo findByPlca(String placa){
        return service.findByPlaca(placa);
    }

    public void updateTara(Veiculo veiculo){ service.updateTara(veiculo); }

    public List findAll(){ return service.findAll(); }
}
