package view;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.json.JSONArray;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import model.Usuario;

public class Servidor extends javax.swing.JFrame {

    protected servidor.Servidor server;
    protected Thread thread;

    boolean conectado = false;
    public ArrayList<Usuario> clients = new ArrayList<Usuario>();
    public JSONArray list = new JSONArray();

    public Servidor() {
        initComponents();

        this.setLocationRelativeTo(null);
        this.btDesconectar.setEnabled(false);
    }

    public void addTexto(JTextPane tPane, String msg, Color color) {
        StyledDocument doc = tPane.getStyledDocument();
        Style style = tPane.addStyle("", null);

        try {
            StyleConstants.setForeground(style, color);

            doc.insertString(doc.getLength(), msg + "\n", style);
            tPane.setCaretPosition(tPane.getDocument().getLength());
        } catch (Exception e) {
            tPane.setText(tPane.getText() + msg);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        inputPorta = new javax.swing.JTextField();
        btConectar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        UserTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tLog = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        btDescClie = new javax.swing.JButton();
        btDesconectar = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Porta:");

        inputPorta.setText("22000");

        btConectar.setText("Conectar");
        btConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConectarActionPerformed(evt);
            }
        });

        UserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP", "Porta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(UserTable);

        jLabel2.setText("Log do sistema:");

        jScrollPane2.setAutoscrolls(true);

        tLog.setEditable(false);
        jScrollPane2.setViewportView(tLog);

        jLabel3.setText("Clientes logados:");

        btDescClie.setText("Desconectar Cliente");
        btDescClie.setVisible(false);

        btDesconectar.setText("Desconectar");
        btDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDesconectarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(inputPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btConectar)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btDesconectar))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(btDescClie))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(inputPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btConectar)
                            .addComponent(btDesconectar)
                            .addComponent(jLabel2))
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btDescClie, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConectarActionPerformed
        this.btConectar.setEnabled(false);
        this.btDesconectar.setEnabled(true);
        this.inputPorta.setEnabled(false);

        server = new servidor.Servidor(Integer.parseInt(this.inputPorta.getText()), this);
        thread = new Thread(server);

        thread.start();

        log("Servidor iniciado!");
    }//GEN-LAST:event_btConectarActionPerformed

    private void btDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDesconectarActionPerformed
        try {
            int confirma = JOptionPane.showConfirmDialog(null, "Deseja desligar o servidor?");

            if (confirma == 0) {
                log("Servidor fechado.");
                server.stop();

                btDesconectar.setEnabled(false);
                btConectar.setEnabled(true);
                inputPorta.setEnabled(true);
            }
        } catch (Exception e) {
            log("Servidor fechado.");
        }
    }//GEN-LAST:event_btDesconectarActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable UserTable;
    private javax.swing.JButton btConectar;
    private javax.swing.JButton btDescClie;
    private javax.swing.JButton btDesconectar;
    private javax.swing.JTextField inputPorta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane tLog;
    // End of variables declaration//GEN-END:variables

    synchronized private void listaUsuariosServer() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DefaultTableModel model = (DefaultTableModel) UserTable.getModel();
                model.setRowCount(0);
                try {
                    clients.forEach((n) -> {
                        model.addRow(new Object[]{n.getUsername(), n.getName()});
                    });

                } catch (Exception e) {
                    error("Error: Listagem de usuários incorreta", e.getMessage());
                }
            }
        });
    }

    private void log(String msg) {
        addTexto(tLog, "[SERVIDOR]: " + msg + "\n", Color.BLACK);
    }

    private void error(String msg, String erro) {
        addTexto(tLog, "[SERVIDOR]: " + msg + "\n" + erro, Color.RED.darker());
    }
}
