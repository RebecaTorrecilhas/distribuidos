package cliente;

import java.io.*;
import java.net.*;
import org.json.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;
import view.Cliente;

public class ClienteTCP extends Thread {

    private String ip;
    private int porta;
    private Usuario usuario;
    private Socket serverSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private String mensagem;
    private Cliente cliente;
    private Recever recever;

    public ClienteTCP(Usuario usuario, Cliente cliente) throws IOException {
        this.usuario = usuario;
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public int getPorta() {
        return porta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void conectar() throws IOException {
        System.out.println("[CLIENTE]: Tentando conexão no host: " + this.getIp() + ":" + this.getPorta());

        try {
            this.serverSocket = new Socket(this.getIp(), 22000);

            this.out = new DataOutputStream(this.serverSocket.getOutputStream());
            this.in = new DataInputStream(this.serverSocket.getInputStream());

            this.recever = new Recever(in, cliente);

            this.recever.start();
        } catch (UnknownHostException e) {
            System.out.println("[CLIENTE]: Este host não existe -> " + this.getIp());
        } catch (IOException io) {
            System.out.println(io.toString());
            System.out.println("[CLIENTE]: Não foi possível obter I/O para o server -> " + this.getIp());
        }
    }

    public void login() throws IOException {
        try {
            JSONObject json = new JSONObject();
            JSONObject message = new JSONObject();
            JSONArray required = new JSONArray();

            json.put("protocol", 100);

            message.put("username", this.usuario.getUsername());
            message.put("password", this.usuario.getPassword());

            required.put("username");
            required.put("password");

            json.put("message", message);
            json.put("required", required);

            System.out.println("[CLIENTE]: Enviando o JSON -> " + json);

            out.writeUTF(json.toString());
        } catch (JSONException ex) {
            System.out.println("[CLIENTE]: Exception no JSON -> " + ex.getMessage());
        }
    }

    public void desconectar() throws IOException {
        try {
            JSONObject json = new JSONObject();

            json.put("protocol", 199);

            System.out.println("[CLIENTE]: Enviando o JSON -> " + json);
            out.writeUTF(json.toString());

            this.recever.setClose();
            this.out.close();
            this.in.close();
            this.serverSocket.close();

            System.out.println("[CLIENTE]: Desconectado.");
        } catch (IOException ex) {
            System.out.println("[CLIENTE]: Problema para desconectar usuário. " + ex.getMessage());
        } catch (JSONException ex) {
            System.out.println("[CLIENTE]: Exception no JSON: " + ex.getMessage());
        }
    }

    public void cadastrarUsuario() throws IOException {
        try {
            JSONObject json = new JSONObject();
            JSONObject message = new JSONObject();
            JSONArray required = new JSONArray();

            json.put("protocol", 700);

            message.put("username", usuario.getUsername());
            message.put("name", usuario.getName());
            message.put("city", usuario.getCity());
            message.put("state", usuario.getState());
            message.put("password", usuario.getPassword());

            json.put("message", message);

            required.put("username");
            required.put("name");
            required.put("city");
            required.put("state");
            required.put("password");

            json.put("required", required);

            System.out.println("[CLIENTE]: Enviando o JSON -> " + json);
            out.writeUTF(json.toString());
        } catch (IOException ex) {
            System.out.println("[CLIENTE]: Problema para cadastrar usuário. " + ex.getMessage());
        } catch (JSONException ex) {
            System.out.println("Exception no JSON: " + ex.getMessage());
        }
    }

    public boolean buscarUsuario() {
        try {
            JSONObject json = new JSONObject();
            JSONObject message = new JSONObject();
            JSONArray required = new JSONArray();

            json.put("protocol", 710);

            message.put("username", usuario.getUsername());

            json.put("message", message);

            required.put("username");

            json.put("required", required);

            System.out.println("[CLIENTE]: Enviando o JSON -> " + json.toString());
            out.writeUTF(json.toString());
        } catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            System.out.println("Exception no JSON: " + ex.getMessage());
        }

        return true;
    }

    public boolean editarUsuario() {
        try {
            JSONObject json = new JSONObject();
            JSONObject message = new JSONObject();
            JSONArray required = new JSONArray();

            json.put("protocol", 720);

            message.put("name", usuario.getName());
            message.put("city", usuario.getCity());
            message.put("state", usuario.getState());
            message.put("password", usuario.getPassword());
            message.put("receptor", usuario.getUser_type());

            json.put("message", message);

            required.put("name");
            required.put("city");
            required.put("state");
            required.put("password");
            required.put("receptor");

            json.put("required", required);

            System.out.println("[CLIENTE]: Enviando o JSON -> " + json.toString());
            out.writeUTF(json.toString());
        } catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            System.out.println("Exception no JSON: " + ex.getMessage());
        }

        return true;
    }
}
