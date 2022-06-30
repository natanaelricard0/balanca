package br.com.cdp.balanca.db;

import br.com.cdp.balanca.model.entities.Funcionario;

import javax.naming.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Hashtable;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection(){
        if(conn == null){
            try {
                Properties prop = loadProperties("/br/com/cdp/balanca/properties/db.properties");
                String url = prop.getProperty("dburl");
                conn = DriverManager.getConnection(url, prop);
            } catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }
    }

    public static void closeStatment(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static Properties loadProperties(String fl) {
        try (InputStream fs = DB.class.getResourceAsStream(fl)) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static boolean atenticacaoUsuarioAd(Funcionario func) {
        if (func.getLoginRede() != "" && func.getSenha() != "") {

            Properties prop = loadProperties("/br/com/cdp/balanca/properties/ad.properties");
            String url = prop.getProperty("adurl");
            String dominio = prop.getProperty("dominio");

            Hashtable<String, String> environment = new Hashtable<>();
            String put = environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            environment.put(Context.PROVIDER_URL, url);
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");
            environment.put(Context.SECURITY_PRINCIPAL, func.getLoginRede() + dominio);
            environment.put(Context.SECURITY_CREDENTIALS, func.getSenha());

            try {
                InitialContext ic = new InitialContext(environment);
                System.out.println(ic.getNameInNamespace());
                return true;
            } catch (AuthenticationNotSupportedException exception) {
                return false;
            } catch (AuthenticationException exception) {
                return false;
            } catch (NamingException exception) {
                return false;
            }
        }
        return false;
    }
}
