package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.db.DB;
import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.dao.AutorizacaoEntradaSaidaDAO;
import br.com.cdp.balanca.model.entities.AutorizacaoEntradaSaida;

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
            st = conn.prepareStatement("SELECT * FROM autorizacao_entrada_saida WHERE nsu_autorizacao_e_s = ?");
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

    @Override
    public void alterarAutorizacao(AutorizacaoEntradaSaida autorizacaoEntradaSaida) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            if (autorizacaoEntradaSaida.autorizacaIsValid()){
                st = conn.prepareStatement("UPDATE autorizacao_entrada_saida SET nsu_veiculo = ?, " +
                        "nsu_pessoa = ?, " +
                        "dt_uso = ?, " +
                        "WHERE = nsu_autorizacao_e_s = ?");

                st.setInt(1, autorizacaoEntradaSaida.getIdVeiculo());
                st.setInt(2, autorizacaoEntradaSaida.getIdPessoa());
                st.setTimestamp(3, autorizacaoEntradaSaida.getDataUso());
                st.setInt(4, autorizacaoEntradaSaida.getIdAutorizacaoEntradaSaida());

                st.executeUpdate();
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatment(st);
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

        return autorizacaoEntradaSaida;
    }
}
