package br.com.cdp.balanca.model.services;

import br.com.cdp.balanca.model.dao.AutorizacaoEntradaSaidaDAO;
import br.com.cdp.balanca.model.dao.DaoFactory;
import br.com.cdp.balanca.model.entities.AutorizacaoEntradaSaida;

public class AutorizacaoEntradaSaidaServices {

    AutorizacaoEntradaSaidaDAO service = DaoFactory.createAutorizacaoEntradaSaidaDao();

    public AutorizacaoEntradaSaida findById(int id) {
        return service.findById(id);
    }
}
