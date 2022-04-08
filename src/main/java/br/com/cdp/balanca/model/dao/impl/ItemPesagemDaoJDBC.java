package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.db.DB;
import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.dao.ItemPesagemDAO;
import br.com.cdp.balanca.model.entities.ItemPesagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @Override
    public ItemPesagem findByAutorizacao(int idAutorizacao) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
         st = conn.prepareStatement("SELECT * FROM item_pesagem WHERE nsu_autorizacao_e_s = ?");
         st.setInt(1,idAutorizacao);
         rs = st.executeQuery();

         if(rs.next()) {
            ItemPesagem itemPesagem = new ItemPesagem();
            itemPesagem = instantiateItemPesagem(rs);
            return itemPesagem;
         }
         return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private ItemPesagem instantiateItemPesagem(ResultSet rs) throws SQLException {
        ItemPesagem itemPesagem = new ItemPesagem();
        itemPesagem.setIdAutorizacaoEntradaSaida(rs.getInt("nsu_autorizacao_e_s"));
        itemPesagem.setIdItemIO(rs.getInt("nsu_item_io"));
        itemPesagem.setIdSubitemIO(rs.getInt("nsu_subitem_io"));
        itemPesagem.setPesoLiquido(rs.getFloat("no_peso_real"));
        return itemPesagem;
    }
}
