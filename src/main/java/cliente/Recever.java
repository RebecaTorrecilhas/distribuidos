package cliente;

import org.json.*;
import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import view.Cliente;

public class Recever extends Thread {

    private final DataInputStream in;
    private Cliente cliente;

    private boolean close;

    public void setClose() {
        this.close = true;
    }

    public Recever(DataInputStream in, Cliente cliente) {
        this.in = in;
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        String recebe;
        this.close = false;

        try {
            while ((recebe = this.in.readUTF()) != null && !this.close) { // ler dados do cliente
                JSONObject receive = new JSONObject(recebe);

                System.out.println("[CLIENTE]: Recebendo o JSON -> " + receive);

                switch (receive.getInt("protocol")) {
                    case 101:
                        JOptionPane.showMessageDialog(null, "Usuário logado com sucesso!");
                        cliente.closeLoginView();
                        break;
                    case 102:
                        JOptionPane.showMessageDialog(null, (receive.getJSONObject("message")).getString("reason"));
                        break;
                    case 701:
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        cliente.closeCadastroView();
                        break;
                    case 702:
                        JOptionPane.showMessageDialog(null, (receive.getJSONObject("message")).getString("reason"));
                        break;
                    case 711:
                        cliente.openEditView(receive);
                        break;
                    case 712:
                        JOptionPane.showMessageDialog(null, (receive.getJSONObject("message")).getString("reason"));
                        break;
                    case 721:
                        JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!");
                         cliente.closeEditView();
                        break;
                    case 722:
                        JOptionPane.showMessageDialog(null, (receive.getJSONObject("message")).getString("reason"));
                        break;
                }
            }
        } catch (IOException ex) {
            if (!close) {
             // TODO servidor fechado
            }
        } catch (JSONException ex) {
            System.out.println("Exception no protocolo: " + ex.getMessage());
        }
    }
}
