package br.com.cdp.balanca.model.services;

import br.com.cdp.balanca.model.dao.DaoFactory;
import br.com.cdp.balanca.model.dao.PesagemDAO;
import br.com.cdp.balanca.model.entities.Pesagem;

import java.util.List;

public class PesagemServices {

    PesagemDAO service = DaoFactory.createPesagemDao();

    public List<Pesagem> listarPesagensPendentes(){
        return service.pesagensPendentes();
    }

    public List<Pesagem> listarPesagensPesquisa(String valor){
        return service.buscarPesagensPorPlaca(valor);
    }

    public void insertPrimeiraPesagem(Pesagem pesagem){ service.insertPesagemPendente(pesagem);}

    public void insertSegundaPesagem(Pesagem pesagem){ service.updatePesagemPendente(pesagem);}
}
