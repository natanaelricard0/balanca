package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.db.DB;
import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.dao.AutorizacaoEntradaSaidaDAO;
import br.com.cdp.balanca.model.entities.AutorizacaoEntradaSaida;
import br.com.cdp.balanca.model.entities.ItemAutorizacao;
import br.com.cdp.balanca.model.entities.ItemPesagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutorizacaoEntradaSaidaDaoJDBC implements AutorizacaoEntradaSaidaDAO {
    private Connection conn;

    public AutorizacaoEntradaSaidaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public AutorizacaoEntradaSaida findById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT aes.*, ia.nsu_item_io, ia.nsu_subitem_io, ied.ds_descricao, ia.no_peso FROM autorizacao_entrada_saida AS aes \n" +
                    "inner join item_autorizacao AS ia ON aes.nsu_autorizacao_e_s = ia.nsu_autorizacao_e_s \n" +
                    "inner join item_embarque_desembarque AS ied ON ia.nsu_item_io = ied.nsu_item_io\n" +
                    "WHERE aes.nsu_autorizacao_e_s = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                AutorizacaoEntradaSaida autorizacaoEntradaSaida = instantiateAutorizacaoEntradaSaida(rs);
                return autorizacaoEntradaSaida;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(st);
            DB.closeResultSet(rs);
        }
    }

    private AutorizacaoEntradaSaida instantiateAutorizacaoEntradaSaida(ResultSet rs) throws SQLException {
        AutorizacaoEntradaSaida autorizacaoEntradaSaida = new AutorizacaoEntradaSaida();
        autorizacaoEntradaSaida.setIdAutorizacaoEntradaSaida(rs.getInt("nsu_autorizacao_e_s"));
        autorizacaoEntradaSaida.setIdEmbarqueDesembarque(rs.getInt("nsu_embarque_desembarque"));
        autorizacaoEntradaSaida.setIdVeiculo(rs.getInt("nsu_veiculo"));
        autorizacaoEntradaSaida.setIdPessoa(rs.getInt("nsu_pessoa"));
        autorizacaoEntradaSaida.setDataPrevisao(rs.getTimestamp("dt_previsao"));
        autorizacaoEntradaSaida.setDataUso(rs.getTimestamp("dt_uso"));
        autorizacaoEntradaSaida.setTipoEntradaSaida(rs.getString("tp_entrada_saida"));

        ItemAutorizacao itemAutorizacao = new ItemAutorizacao();

        itemAutorizacao.setIdAutorizacaoEntradaSaida(rs.getInt("nsu_autorizacao_e_s"));
        itemAutorizacao.setIdItemEmbarqueDesembarque(rs.getInt("nsu_item_io"));
        itemAutorizacao.setIdSubitemEmbarqueDesembarque(rs.getInt("nsu_subitem_io"));
        itemAutorizacao.setDescricao(rs.getString("ds_descricao"));
        itemAutorizacao.setPeso(rs.getFloat("no_peso"));

        autorizacaoEntradaSaida.setItemAutorizacao(itemAutorizacao);
        return autorizacaoEntradaSaida;
    }
}
