/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Rebeca
 */
public class ConexaoSQLite {

    public ConexaoSQLite() {

    }

    public void createConnection() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:banco.db")) {
            Statement statement = connection.createStatement();
            System.out.println("[SERVIDOR]: Conexão com SQLite realizada com sucesso.");

            statement.execute("DROP TABLE usuarios");
            statement.execute("CREATE TABLE IF NOT EXISTS usuarios(id INTEGER PRIMARY KEY, user_type INTEGER, username VARCHAR, name VARCHAR, city VARCHAR, state VARCHAR, recep_validated INTEGER, password VARCHAR)");
            System.out.println("[SERVIDOR]: Tabela usuário criada/iniciada com sucesso.");

            statement.execute("DROP TABLE doacao");
            statement.execute("CREATE TABLE IF NOT EXISTS doacao(id INTEGER PRIMARY KEY, id_donor INTEGER, id_recipient INTEGER, value INTEGER, date_donation VARCHAR, is_anon INTEGER)");
            System.out.println("[SERVIDOR]: Tabela doação criada/iniciada com sucesso.");

            statement.execute("INSERT INTO usuarios(user_type, username, name, city, state, recep_validated, password) VALUES (1, 'user', 'Usuário', 'Ponta Grossa', 'PR', 0, 'user')");
            System.out.println("[SERVIDOR]: Usuário de teste inserido com sucesso.");

            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection connect() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:banco.db");
            return connection;
        } catch (SQLException e) {
            System.out.println("[SERVIDOR]: Erro ao abrir conexão do SQLite.");
            return null;
        }
    }
}
