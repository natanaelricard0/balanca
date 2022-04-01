package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.db.DB;
import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.dao.PesagemDAO;
import br.com.cdp.balanca.model.entities.Pesagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PesagemDaoJDBC implements PesagemDAO {
    private Connection conn;

    public PesagemDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Pesagem pesagem) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("EXECUTE pr_Balanca_InserirPesagem ?,?,?,?,?,?,?,?,?,?,?,?");
            st.setInt(1, pesagem.getIdAutorizacao());
            st.setInt(2, pesagem.getIdBalanca());
            st.setInt(3, pesagem.getIdPesagem());
            st.setFloat(4, pesagem.getPesoBruto());
            st.setFloat(5, pesagem.getTara());
            st.setString(6, pesagem.getPlaca());
            st.setString(7, pesagem.getPlacaCarreta());
            st.setString(8, pesagem.getNotaFiscal());
            st.setString(9, pesagem.getUsuarioPrimeiraPesagem());
            st.setString(10, pesagem.getUsuarioSegundaPesagem());
            st.setString(11, null);
            st.setTimestamp(12, pesagem.getDataPrimeiraPesagem());
            st.setTimestamp(13, pesagem.getDataSegundapesagem());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void update(Pesagem pesagem) {

    }

    @Override
    public List<Pesagem> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Pesagem> list = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT * FROM dbo.pesagem");
            rs = st.executeQuery();

            while (rs.next()) {
                list.add(instatiatePesagem(rs));
            }

            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Pesagem findById() {
        return null;
    }

    private Pesagem instatiatePesagem(ResultSet rs) throws SQLException {
        Pesagem pesagem = new Pesagem();
        pesagem.setIdAutorizacao(rs.getInt("nsu_autorizacao_e_s"));
        pesagem.setIdBalanca(rs.getInt("nsu_balanca"));
        pesagem.setIdPesagem(rs.getInt("nsu_pesagem"));
        pesagem.setPesoBruto(rs.getFloat("no_peso_bruto"));
        pesagem.setTara(rs.getFloat("no_tara"));
        pesagem.setPlaca(rs.getString("nm_placa"));
        pesagem.setPlacaCarreta(rs.getString("nm_placa_carreta"));
        pesagem.setNotaFiscal(rs.getString("nota_fiscal"));
        pesagem.setDataPrimeiraPesagem(rs.getTimestamp("dt_pesagem1"));
        pesagem.setDataSegundapesagem(rs.getTimestamp("dt_pesagem2"));
        pesagem.setUsuarioPrimeiraPesagem(rs.getString("nm_usuario1"));
        pesagem.setUsuarioSegundaPesagem(rs.getString("nm_usuario2"));

        return pesagem;
    }
}
