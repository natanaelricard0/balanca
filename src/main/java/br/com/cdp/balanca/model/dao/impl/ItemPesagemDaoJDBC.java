package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.db.DB;
import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.dao.ItemPesagemDAO;
import br.com.cdp.balanca.model.entities.ItemPesagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemPesagemDaoJDBC implements ItemPesagemDAO {
    private Connection conn;

    public ItemPesagemDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(ItemPesagem itemPesagem) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("EXECUTE pr_Balanca_InserirItemPesagem ?,?,?,?");
            st.setInt(1,itemPesagem.getIdAutorizacaoEntradaSaida());
            st.setInt(2, itemPesagem.getIdItemIO());
            st.setInt(3,itemPesagem.getIdSubitemIO());
            st.setFloat(4,itemPesagem.getPesoLiquido());

            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatment(st);
        }
    }
}
