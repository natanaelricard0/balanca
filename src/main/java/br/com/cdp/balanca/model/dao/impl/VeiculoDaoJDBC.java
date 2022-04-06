package br.com.cdp.balanca.model.dao.impl;

import br.com.cdp.balanca.application.Main;
import br.com.cdp.balanca.db.DB;
import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.dao.VeiculoDAO;
import br.com.cdp.balanca.model.entities.Veiculo;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class VeiculoDaoJDBC implements VeiculoDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Connection conn;

    public VeiculoDaoJDBC(Connection conn){ this.conn = conn; }

    @Override
    public Veiculo findById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM veiculo WHERE nsu_veiculo = ?");
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
    public void updateTara(Veiculo veiculo) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE veiculo SET no_tara = ? WHERE nsu_veiculo = ?");
            st.setFloat(1,veiculo.getPesoTara());
            st.setInt(2,veiculo.getIdVeiculo());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public Veiculo findByPlaca(String placa) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM veiculo WHERE nm_placa = ?");
            st.setString(1,placa);

            rs = st.executeQuery();

            if(rs.next()){
                Veiculo veiculo = instatiateVeiculo(rs);
                return veiculo;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatment(st);
            DB.closeResultSet(rs);
        }
    }


    private Veiculo instatiateVeiculo(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(rs.getInt("nsu_veiculo"));
        veiculo.setIdPessoa(rs.getInt("id_pessoa"));
        veiculo.setPlacaVeiculo(rs.getString("nm_placa").replace(" ", ""));
        veiculo.setPesoTara(rs.getFloat("no_tara"));
        return veiculo;
    }
}
