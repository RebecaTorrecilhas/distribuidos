package servidor;

import org.json.*;
import java.io.*;
import java.net.*;
import model.Usuario;
import dao.UsuarioDAO;

public class ServidorTCP extends Thread {

    protected Socket clienteSocket;
    protected Usuario usuario;
    protected view.Servidor main;
    protected DataOutputStream out;
    protected DataInputStream in;

    public ServidorTCP(Socket socket, view.Servidor main) {
        this.main = main;
        this.clienteSocket = socket;

        try {
            out = new DataOutputStream(clienteSocket.getOutputStream()); // prepara para enviar os dados
            in = new DataInputStream(this.clienteSocket.getInputStream()); // prepara para receber os dado
        } catch (IOException e) {
            System.out.println("[SERVIDOR]: " + e.getMessage());
        }
    }

    @Override
    public void run() { // Essa thead fica rodando até o servidor ser fechado
        System.out.println("[SERVIDOR]: Novo cliente iniciado. Aguardando o login...");

        try {
            String recebe;
            boolean close = false;

            while ((recebe = in.readUTF()) != null) { // ler dados vindo do ClienteTCP
                JSONObject receive = new JSONObject(recebe);

                System.out.println("[SERVIDOR]: Recebendo o JSON -> " + receive);

                switch (receive.getInt("protocol")) { // Verifica qual é o protocolo
                    case 100:
                        this.login(receive);
                        break;
                    case 199:
                        this.desconectar();
                        close = true;

                        break;
                    case 700:
                        this.cadastrar(receive);
                        break;
                    case 710:
                        this.get(receive);
                        break;
                    case 720:
                        this.editar(receive);
                        break;
                }

                if (close) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("[SERVIDOR]: Conexão perdida com o Usuário: " + this.usuario.getUsername());

            try {
                this.desconectar();
            } catch (IOException ex) {
                System.out.println("[SERVIDOR]: Erro ao remover usuário com conexão perdida");
            }
        } catch (JSONException ex) {
            System.out.println("Exception no protocolo: " + ex.getMessage());
        }
    }

    public void login(JSONObject receive) throws IOException, JSONException {
        JSONObject response = new JSONObject();
        JSONObject message = new JSONObject();
        JSONArray required = new JSONArray();

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            usuario = usuarioDAO.get((receive.getJSONObject("message")).getString("username"));

            if (usuario == null) {
                response.put("protocol", 102);

                message.put("result", false);
                message.put("reason", "Usuário não existe.");

                required.put("result");

                response.put("message", message);
                response.put("required", required);
            } else {
                if (usuario.getPassword().equals((receive.getJSONObject("message")).getString("password"))) {
                    response.put("protocol", 101);

                    message.put("result", true);

                    required.put("result");

                    response.put("message", message);
                    response.put("required", required);
                } else {
                    response.put("protocol", 102);

                    message.put("result", false);
                    message.put("reason", "Usuário e senha incorretos.");

                    required.put("result");

                    response.put("message", message);
                    response.put("required", required);
                }
            }

            System.out.println("[SERVIDOR]: Enviando o JSON -> : " + response.toString());
            out.writeUTF(response.toString());
        } catch (JSONException ex) {
            response.put("protocol", 102);

            message.put("result", false);
            message.put("reason", "Dados obrigatórios faltantes.");

            required.put("result");

            response.put("message", message);
            response.put("required", required);

            System.out.println("[SERVIDOR]: Enviando o JSON -> : " + response.toString());
            out.writeUTF(response.toString());
        }
    }

    public void desconectar() throws IOException {
        out.close();
        in.close();
        clienteSocket.close();

        System.out.println("[SERVIDOR]: Cliente desconectado -> " + this.usuario.getName());
    }

    public void cadastrar(JSONObject receive) throws IOException {
        JSONObject response = new JSONObject();
        JSONObject message = new JSONObject();
        JSONArray required = new JSONArray();

        try {
            JSONObject dados = receive.getJSONObject("message");

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            usuario = new Usuario(dados.getString("username"), dados.getString("name"), dados.getString("city"), dados.getString("state"), dados.getString("password"));

            int id = usuarioDAO.insert(usuario);

            if (id == -1) {
                response.put("protocol", 702);

                message.put("result", false);
                message.put("reason", "Ocorreu algum erro ao inserir os dados.");

                required.put("result");
                required.put("reason");

                response.put("message", message);
                response.put("required", required);
            } else {
                response.put("protocol", 701);

                message.put("result", true);

                required.put("result");

                response.put("message", message);
                response.put("required", required);
            }

            System.out.println("[SERVIDOR]: Enviando o JSON -> : " + response.toString());
            out.writeUTF(response.toString());
        } catch (JSONException ex) {
            System.out.println("Exception no protocolo: " + ex.getMessage());

            response.put("protocol", 702);

            message.put("result", false);
            message.put("reason", "Dados obrigatórios faltantes.");

            required.put("result");
            required.put("reason");

            response.put("message", message);
            response.put("required", required);

            System.out.println("[SERVIDOR]: Enviando o JSON -> : " + response.toString());
            out.writeUTF(response.toString());
        }
    }

    public void editar(JSONObject receive) throws IOException {
        JSONObject response = new JSONObject();
        JSONObject message = new JSONObject();
        JSONArray required = new JSONArray();

        try {
            JSONObject dados = receive.getJSONObject("message");

            usuario.setName(dados.getString("name"));
            usuario.setCity(dados.getString("city"));
            usuario.setState(dados.getString("state"));
            usuario.setPassword(dados.getString("password"));
            usuario.setUser_type(dados.getInt("receptor"));

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            boolean responseUpdate = usuarioDAO.update(usuario.getUsername(), usuario);

            if (responseUpdate) {
                response.put("protocol", 721);

                message.put("result", true);
                message.put("reason", "Usuário atualizado com sucesso.");

                required.put("result");
                required.put("reason");

                response.put("message", message);
                response.put("required", required);

            } else {
                response.put("protocol", 722);

                message.put("result", false);
                message.put("reason", "Ocorreu um erro ao atualizar o usuário.");

                required.put("result");
                required.put("reason");

                response.put("message", message);
                response.put("required", required);
            }

            System.out.println("[SERVIDOR]: Enviando o JSON -> : " + response.toString());
            out.writeUTF(response.toString());
        } catch (JSONException ex) {
            System.out.println("Exception no protocolo: " + ex.getMessage());

            response.put("protocol", 722);

            message.put("result", false);
            message.put("reason", "Dados obrigatórios faltantes para atualizar o usuário.");

            required.put("result");
            required.put("reason");

            response.put("message", message);
            response.put("required", required);

            System.out.println("[SERVIDOR]: Enviando o JSON -> : " + response.toString());
            out.writeUTF(response.toString());
        }
    }

    public void get(JSONObject receive) throws IOException {
        JSONObject response = new JSONObject();
        JSONObject message = new JSONObject();
        JSONArray required = new JSONArray();

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            usuario = usuarioDAO.get((receive.getJSONObject("message")).getString("username"));

            if (usuario == null) {
                response.put("protocol", 712);

                message.put("result", false);
                message.put("reason", "Usuário não existe.");

                required.put("result");
                required.put("reason");

                response.put("message", message);
                response.put("required", required);
            } else {
                response.put("protocol", 711);

                message.put("result", true);
                message.put("username", usuario.getUsername());
                message.put("name", usuario.getName());
                message.put("city", usuario.getCity());
                message.put("state", usuario.getState());
                message.put("password", usuario.getPassword());
                message.put("receptor", usuario.getUser_type());

                required.put("result");
                required.put("username");
                required.put("name");
                required.put("city");
                required.put("state");
                required.put("password");
                required.put("receptor");

                response.put("message", message);
                response.put("required", required);
            }

            System.out.println("[SERVIDOR]: Enviando o JSON -> : " + response.toString());
            out.writeUTF(response.toString());
        } catch (JSONException ex) {
            System.out.println("Exception no protocolo: " + ex.getMessage());

            response.put("protocol", 712);

            message.put("result", false);
            message.put("reason", "Dados obrigatórios faltantes.");

            required.put("result");
            required.put("reason");

            response.put("message", message);
            response.put("required", required);

            System.out.println("[SERVIDOR]: Enviando o JSON -> : " + response.toString());
            out.writeUTF(response.toString());
        }
    }
}
