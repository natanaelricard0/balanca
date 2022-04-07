package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.model.dao.ItemAutorizacaoDAO;
import br.com.cdp.balanca.model.entities.ItemAutorizacao;

import java.sql.Connection;

public class ItemAutorizacaoDaoJDBC implements ItemAutorizacaoDAO {

    private Connection conn;

    public ItemAutorizacaoDaoJDBC(Connection conn){
        this.conn = conn;
    }


    @Override
    public ItemAutorizacao findById(String idAutorizacao) {
        return null;
    }
}
