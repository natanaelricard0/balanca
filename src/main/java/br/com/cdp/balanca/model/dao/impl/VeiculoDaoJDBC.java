package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.application.Main;
import br.com.cdp.balanca.db.DB;
import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.dao.VeiculoDAO;
import br.com.cdp.balanca.model.entities.Veiculo;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VeiculoDaoJDBC implements VeiculoDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Connection conn;

    public VeiculoDaoJDBC(Connection conn){ this.conn = conn; }

    @Override
    public Veiculo findById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("EXECUTE pr_BalancaBuscarTaraVeiculo ?");
            st.setInt(1,id);

            rs = st.executeQuery();

            if(rs.next()){
                Veiculo veiculo = instatiateVeiculo(rs);
                return veiculo;
            }
            return null;

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            DB.closeStatment(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void insertTara(Veiculo veiculo) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("EXECUTE pr_Balanca_InserirVeiculoPesagemImportacao ?,?,?,?,?,?,?");
            st.setInt(1,findLastIdIncrement());
            st.setInt(2, veiculo.getIdVeiculo());
            st.setFloat(3,veiculo.getPesoTara());
            st.setInt(4,1);
            st.setTimestamp(5, veiculo.getDataPesagem());
            st.setString(6, Main.getDataUser().getLoginScap());
            st.setString(7, Main.getDataUser().getNome().toUpperCase());

            st.execute();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public List<Veiculo> findAll() {
        return null;
    }

    @Override
    public List<Veiculo> findPesagemById(int id) {
        return null;
    }

    @Override
    public Veiculo findVeiculo(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("SELECT * FROM veiculo WHERE nsu_veiculo = ?");
            st.setInt(1,id);

            rs = st.executeQuery();

            if(rs.next()){
                Veiculo veiculo = new Veiculo();
                veiculo.setIdVeiculo(rs.getInt("nsu_veiculo"));
                veiculo.setPlacaVeiculo(rs.getString("nm_placa").replace(" ",""));
                return veiculo;
            }
            return null;
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatment(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Integer findLastIdIncrement() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT TOP 1 nsu_pesagem + 1 as nsu_pesagem FROM veiculo_pesagem_importacao ORDER BY nsu_pesagem DESC");

            rs = st.executeQuery();

            if(rs.next()){
                return rs.getInt("nsu_pesagem");
            }

            return null;
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private Veiculo instatiateVeiculo(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(rs.getInt("nsu_veiculo"));
        veiculo.setIdPessoa(rs.getInt("id_pessoa"));
        veiculo.setPlacaVeiculo(rs.getString("nm_placa").replace(" ", ""));
        veiculo.setPesoTara(rs.getFloat("peso_tara"));
        veiculo.setDataPesagem(rs.getTimestamp("dt_pesagem1"));
        veiculo.setUsuario(rs.getString("nm_usuario1"));
        veiculo.setNomeUsuario(rs.getString("nm_operador1"));
        return veiculo;
    }
}
