/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.admindatas;

import admin.GUIListener;
import common.Protocol;

/**
 *
 * @author vicle
 */
public class ConnectFrame extends javax.swing.JFrame {

	
	private GUIListener listener;
	
    /**
     * Creates new form ConnectFrame
     */
    public ConnectFrame(GUIListener listener) {
        initComponents();
        this.listener=listener;
    }

    private void initComponents() {

        ConnectPanel = new javax.swing.JPanel();
        connectLabel = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        username = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        username1 = new javax.swing.JTextField();
        status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ConnectPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        connectLabel.setText("Connexion");

        connectButton.setText("Se connecter");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Utilisateur");

        jLabel2.setText("@IP Serveur");

        status.setText(" ");

        javax.swing.GroupLayout ConnectPanelLayout = new javax.swing.GroupLayout(ConnectPanel);
        ConnectPanel.setLayout(ConnectPanelLayout);
        ConnectPanelLayout.setHorizontalGroup(
            ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(username1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(182, 182, 182))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConnectPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(connectButton)
                .addGap(42, 42, 42))
            .addGroup(ConnectPanelLayout.createSequentialGroup()
                .addComponent(connectLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(status)
        );
        ConnectPanelLayout.setVerticalGroup(
            ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnectPanelLayout.createSequentialGroup()
                .addComponent(connectLabel)
                .addGap(29, 29, 29)
                .addGroup(ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(1, 1, 1)
                .addComponent(connectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ConnectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ConnectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
    
    
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt)
    {
    	if (listener != null) {
			final String name = username.getText();
			Protocol.IPSERV=username1.getText();
			new Thread () {
				public void run () 
				{
					listener.requestConnection(name);
				}
			}.start();
		}
    }   
    
    public void updateStatus(String log)
    {
    	status.setText(log);
    }
   
    // Variables declaration - do not modify                     
    private javax.swing.JPanel ConnectPanel;
    private javax.swing.JButton connectButton;
    private javax.swing.JLabel connectLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel status;
    private javax.swing.JTextField username;
    private javax.swing.JTextField username1;
    // End of variables declaration                   
}
