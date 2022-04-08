package br.com.cdp.balanca.model.services;

import br.com.cdp.balanca.model.dao.AutorizacaoEntradaSaidaDAO;
import br.com.cdp.balanca.model.dao.DaoFactory;
import br.com.cdp.balanca.model.dao.ItemPesagemDAO;
import br.com.cdp.balanca.model.entities.AutorizacaoEntradaSaida;
import br.com.cdp.balanca.model.entities.ItemPesagem;

public class AutorizacaoEntradaSaidaServices {

    AutorizacaoEntradaSaidaDAO service = DaoFactory.createAutorizacaoEntradaSaidaDao();

    ItemPesagemDAO itemServices = DaoFactory.createItemPesagemDao();

    public AutorizacaoEntradaSaida findById(int id) {
        return service.findById(id);
    }

    public boolean autorizacaoIsValid(int autorizacao) {
        ItemPesagem itemPesagem = itemServices.findByAutorizacao(autorizacao);
        if(itemPesagem == null) {
            return true;
        } else {
            return false;
        }
    }
}
