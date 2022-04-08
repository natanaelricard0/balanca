package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.dao.ItemAutorizacaoDAO;
import br.com.cdp.balanca.model.entities.ItemAutorizacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemAutorizacaoDaoJDBC implements ItemAutorizacaoDAO {

    private Connection conn;

    public ItemAutorizacaoDaoJDBC(Connection conn){
        this.conn = conn;
    }


    @Override
    public ItemAutorizacao findById(int idAutorizacao) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("select * from autorizacao_entrada_saida where nsu_autorizacao_e_s = ?");
            st.setInt(1,idAutorizacao);

            rs = st.executeQuery();

            if(rs.next()){
                ItemAutorizacao itemAutorizacao = instantiateItemAutorizacao(rs);
                return itemAutorizacao;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    private ItemAutorizacao instantiateItemAutorizacao(ResultSet rs) throws SQLException {
        ItemAutorizacao itemAutorizacao = new ItemAutorizacao();
        itemAutorizacao.setIdAutorizacaoEntradaSaida(rs.getInt("nsu_autorizacao_e_s"));
        itemAutorizacao.setIdItemEmbarqueDesembarque(rs.getInt("nsu_item_io"));
        itemAutorizacao.setIdSubitemEmbarqueDesembarque(rs.getInt("nsu_subitem_io"));
        return itemAutorizacao;
    }
}
