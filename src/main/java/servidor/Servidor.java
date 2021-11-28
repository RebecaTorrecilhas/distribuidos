package servidor;

import dao.ConexaoSQLite;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {

    protected ServerSocket server;
    protected view.Servidor main;
    protected boolean conected = true;

    public Servidor(int porta, view.Servidor main) {
        System.out.println("[SERVIDOR]: Iniciando SERVIDOR...");

        try {
            this.main = main;

            server = new ServerSocket(porta);
            System.out.println("[SERVIDOR]: SERVIDOR Iniciado com sucesso -> IP: " + server.getInetAddress() + " PORTA: " + server.getLocalPort());

            ConexaoSQLite connect = new ConexaoSQLite();
            connect.createConnection();
        } catch (IOException e) {
            System.out.println("[IOException]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Exception]: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (conected) {
                Socket socket = server.accept();

                new Thread(new ServidorTCP(socket, main)).start();
            }
        } catch (IOException e) {
            System.out.println("[ServidorThreadIOException]: " + e.getMessage());
        }
    }

    public void stop() {
        try {
            conected = false;
            server.close();

            System.out.println("[SERVIDOR]: SERVIDOR está FECHADO!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
