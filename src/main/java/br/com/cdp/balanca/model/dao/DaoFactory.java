package br.com.cdp.balanca.model.dao;

import br.com.cdp.balanca.db.DB;
import br.com.cdp.balanca.model.dao.impl.*;

public class DaoFactory {

    public static FuncionarioDAO createFuncionarioDao(){
        return new FuncionarioDaoJDBC(DB.getConnection());
    }

    public static VeiculoDAO createVeiculoDao(){
        return new VeiculoDaoJDBC(DB.getConnection());
    }

    public static PesagemDAO createPesagemDao(){
        return new PesagemDaoJDBC(DB.getConnection());
    }

    public static AutorizacaoEntradaSaidaDAO createAutorizacaoEntradaSaidaDao(){
        return new AutorizacaoEntradaSaidaDaoJDBC(DB.getConnection());
    }

    public static ItemPesagemDAO createItemPesagemDao(){
        return  new ItemPesagemDaoJDBC(DB.getConnection());
    }

    public static ItemAutorizacaoDAO createItemAutorizacaoDao(){
        return new ItemAutorizacaoDaoJDBC(DB.getConnection());
    }

}
