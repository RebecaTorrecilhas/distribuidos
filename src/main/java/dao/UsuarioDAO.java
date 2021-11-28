/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

/**
 *
 * @author ana.teixeira
 */
public class UsuarioDAO {

    public int insert(Usuario usuario) {
        try {
            ConexaoSQLite conexao = new ConexaoSQLite();

            Connection connection = conexao.connect();
            Statement statement = connection.createStatement();

            statement.execute("INSERT INTO usuarios(user_type, username, name, city, state, recep_validated, password) VALUES (99, '" + usuario.getUsername() + "', '" + usuario.getName() + "', '" + usuario.getCity() + "', '" + usuario.getState() + "', 0, '" + usuario.getPassword() + "')");

            System.out.println("[SERVIDOR]: Usuário inserido com sucesso.");

            connection.close();

            return 1;
        } catch (SecurityException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return -1;
        } catch (SQLException ex) {
            System.out.println("[SERVIDOR]: Erro ao inserir o usuário.");

            return -1;
        }
    }

    public boolean update(String username, Usuario usuario) {
        try {
            ConexaoSQLite conexao = new ConexaoSQLite();

            Connection connection = conexao.connect();
            Statement statement = connection.createStatement();

            statement.execute("UPDATE usuarios SET user_type = " + usuario.getUser_type() + ", name = '" + usuario.getName() + "', city = '" + usuario.getCity() + "', state = '" + usuario.getState() + "', password =  '" + usuario.getPassword() + "' WHERE username = '" + usuario.getUsername() + "';");

            System.out.println("[SERVIDOR]: Usuário atualizado com sucesso.");

            connection.close();

            return true;
        } catch (SecurityException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return false;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    public Usuario get(String username) {
        try {
            ConexaoSQLite conexao = new ConexaoSQLite();

            Connection connection = conexao.connect();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM usuarios WHERE username = '" + username + "';");

            ResultSet response = stmt.executeQuery();

            Usuario usuario = new Usuario();

            while (response.next()) {
                if (response.getString("username").equals(username)) {
                    usuario.setId(response.getInt("id"));
                    usuario.setRecep_validated(response.getInt("recep_validated"));
                    usuario.setUser_type(response.getInt("user_type"));
                    usuario.setUsername(response.getString("username"));
                    usuario.setName(response.getString("name"));
                    usuario.setCity(response.getString("city"));
                    usuario.setState(response.getString("state"));
                    usuario.setPassword(response.getString("password"));

                    connection.close();

                    return usuario;
                }
            }

            connection.close();
            return null;
        } catch (SecurityException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        } catch (SQLException ex) {
            System.out.println("[SERVIDOR]: Erro ao buscar o usuário.");

            return null;
        }
    }
}
