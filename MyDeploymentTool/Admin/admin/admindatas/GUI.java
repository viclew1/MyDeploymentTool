/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.admindatas;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import admin.GUIListener;

/**
 *
 * @author vicle
 */
public class GUI extends javax.swing.JFrame implements ModelListener {

	private static final long serialVersionUID = 1L;

	private GUIListener listener;
	private Model model;
	private Thread loading;
	private boolean waiting=false;
	private String currentSymbol="/ ";

	/**
	 * Creates new form GUI
	 */
	public GUI() {
		initComponents();
	}

	public GUI(GUIListener listener, Model model) {
		super("My Deployment Tools");
		initComponents();
		setListener(listener);
		this.model=model;
		addDisconnectOnClose();
		initLoadingThread();
	}

	private void initLoadingThread() {
		loading=new Thread(new Runnable() {

			@Override
			public void run() {
				while (waiting)
				{
					try {
						nextSymbol();
						String loadingString="Installation en cours "+currentSymbol;
						GUI.this.status.setText(loadingString);
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void nextSymbol()
	{
		switch (currentSymbol)
		{
		case "/ ":
			currentSymbol="--";
			break;
		case "--":
			currentSymbol="\\ ";
			break;
		case "\\ ":
			currentSymbol="| ";
			break;
		case "| ":
			currentSymbol="/ ";
			break;
		default:
			currentSymbol="/ ";
			break;
		}
	}

	private void addDisconnectOnClose()
	{
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				int result = JOptionPane.showConfirmDialog(null, "Are you sure to close this window?", "Closing?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION)
				{
					if (model.isConnected())
						listener.requestConnection(model.getName());
					System.exit(0);
				}
			}
		});
	}

	public void setListener(GUIListener listener) {
		this.listener = listener;
	}

	public void updateStatus (final String status) {
		if (status == null) {
			this.status.setText("");
		} else {
			{
				this.status.setText(status);
				installInfosPanel.add(new LogUI(status));
				jScrollPane3.getVerticalScrollBar().setValue(jScrollPane3.getVerticalScrollBar().getMaximum());
				System.out.println(status);
			}
		}
	}

	@Override
	synchronized public void updateConnection(final String status) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (model.isConnected()) connectButton.setText("Se déconnecter");
				else {
					connectButton.setText("Se connecter");
					PCShowPanel.removeAll();
					PCShowPanel.revalidate();
					PCShowPanel.repaint();
				}
				updateStatus(status);
			}
		});
	}


	private void initComponents() {

		PCShowPanel = new javax.swing.JPanel();
		folderPanel = new javax.swing.JPanel();
		installInfosPanel = new javax.swing.JPanel();
		ConnectPanel = new javax.swing.JPanel();
		connectLabel = new javax.swing.JLabel();
		connectButton = new javax.swing.JButton();
		username = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		ipserv = new javax.swing.JTextField();
		ExplorerPanel = new javax.swing.JPanel();
		explorerLabel = new javax.swing.JLabel();
		dirComboBox = new javax.swing.JComboBox<>();
		jScrollPane2 = new javax.swing.JScrollPane();
		PCPanel = new javax.swing.JPanel();
		PCLabel = new javax.swing.JLabel();
		refreshButton = new javax.swing.JButton();
		deployButton = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		takeControlButton = new javax.swing.JButton();
		photoButton = new javax.swing.JButton();
		status = new javax.swing.JLabel();
		logsPanel = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		logLabel = new javax.swing.JLabel();
		cleanButton = new javax.swing.JButton();

		PCShowPanel.setLayout(new javax.swing.BoxLayout(PCShowPanel, javax.swing.BoxLayout.Y_AXIS));

		folderPanel.setLayout(new javax.swing.BoxLayout(folderPanel, javax.swing.BoxLayout.Y_AXIS));

		installInfosPanel.setLayout(new javax.swing.BoxLayout(installInfosPanel, javax.swing.BoxLayout.Y_AXIS));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		ConnectPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		connectLabel.setText("Connexion");

		connectButton.setText("Se connecter");
		connectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				connectButtonActionPerformed(evt);
			}
		});

		jLabel1.setText("Utilisateur : ");

		jLabel2.setText("@IP Serveur : ");

		javax.swing.GroupLayout ConnectPanelLayout = new javax.swing.GroupLayout(ConnectPanel);
		ConnectPanel.setLayout(ConnectPanelLayout);
		ConnectPanelLayout.setHorizontalGroup(
				ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConnectPanelLayout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(connectButton)
						.addGap(70, 70, 70))
				.addGroup(ConnectPanelLayout.createSequentialGroup()
						.addComponent(connectLabel)
						.addGap(0, 0, Short.MAX_VALUE))
				.addGroup(ConnectPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel1)
								.addComponent(jLabel2))
						.addGap(57, 57, 57)
						.addGroup(ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
								.addComponent(ipserv))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		ConnectPanelLayout.setVerticalGroup(
				ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(ConnectPanelLayout.createSequentialGroup()
						.addComponent(connectLabel)
						.addGap(40, 40, 40)
						.addGroup(ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(ConnectPanelLayout.createSequentialGroup()
										.addComponent(jLabel2)
										.addGap(18, 18, 18)
										.addGroup(ConnectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel1)
												.addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
										.addComponent(connectButton))
								.addGroup(ConnectPanelLayout.createSequentialGroup()
										.addComponent(ipserv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap())
				);

		ExplorerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		explorerLabel.setText("Explorateur");

		dirComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
		dirComboBox.setToolTipText("");
		dirComboBox.setLightWeightPopupEnabled(false);
		dirComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				osComboBoxActionPerformed(evt);
			}
		});

		jScrollPane2.setViewportView(folderPanel);

		javax.swing.GroupLayout ExplorerPanelLayout = new javax.swing.GroupLayout(ExplorerPanel);
		ExplorerPanel.setLayout(ExplorerPanelLayout);
		ExplorerPanelLayout.setHorizontalGroup(
				ExplorerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(ExplorerPanelLayout.createSequentialGroup()
						.addComponent(explorerLabel)
						.addGap(0, 0, Short.MAX_VALUE))
				.addGroup(ExplorerPanelLayout.createSequentialGroup()
						.addGap(30, 30, 30)
						.addGroup(ExplorerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(ExplorerPanelLayout.createSequentialGroup()
										.addComponent(dirComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 215, Short.MAX_VALUE))
								.addComponent(jScrollPane2))
						.addGap(30, 30, 30))
				);
		ExplorerPanelLayout.setVerticalGroup(
				ExplorerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(ExplorerPanelLayout.createSequentialGroup()
						.addComponent(explorerLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(dirComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
						.addContainerGap())
				);

		PCPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		PCLabel.setText("PC connectés");

		refreshButton.setText("Rafraîchir");
		refreshButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				refreshButtonActionPerformed(evt);
			}
		});

		deployButton.setText("Déployer");
		deployButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deployButtonActionPerformed(evt);
			}
		});

		jScrollPane1.setViewportView(PCShowPanel);

		takeControlButton.setText("Prendre le contrôle");
		takeControlButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				takeControlButtonActionPerformed(evt);
			}
		});

		photoButton.setText("Prendre une photo");
		photoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				photoButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout PCPanelLayout = new javax.swing.GroupLayout(PCPanel);
		PCPanel.setLayout(PCPanelLayout);
		PCPanelLayout.setHorizontalGroup(
				PCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(PCPanelLayout.createSequentialGroup()
						.addGroup(PCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(PCLabel)
								.addGroup(PCPanelLayout.createSequentialGroup()
										.addGap(10, 10, 10)
										.addComponent(refreshButton)))
						.addGap(0, 0, Short.MAX_VALUE))
				.addGroup(PCPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(PCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PCPanelLayout.createSequentialGroup()
										.addComponent(takeControlButton)
										.addGap(18, 18, 18)
										.addComponent(photoButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
										.addComponent(deployButton))
								.addComponent(jScrollPane1))
						.addContainerGap())
				);
		PCPanelLayout.setVerticalGroup(
				PCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(PCPanelLayout.createSequentialGroup()
						.addComponent(PCLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(refreshButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(PCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(deployButton)
								.addComponent(takeControlButton)
								.addComponent(photoButton))
						.addContainerGap())
				);

		logsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		jScrollPane3.setViewportView(installInfosPanel);

		logLabel.setText("Logs");

		cleanButton.setText("Effacer");
		cleanButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cleanButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout logsPanelLayout = new javax.swing.GroupLayout(logsPanel);
		logsPanel.setLayout(logsPanelLayout);
		logsPanelLayout.setHorizontalGroup(
				logsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(logsPanelLayout.createSequentialGroup()
						.addComponent(logLabel)
						.addGap(0, 0, Short.MAX_VALUE))
				.addGroup(logsPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(logsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane3)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logsPanelLayout.createSequentialGroup()
										.addGap(0, 213, Short.MAX_VALUE)
										.addComponent(cleanButton)))
						.addContainerGap())
				);
		logsPanelLayout.setVerticalGroup(
				logsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logsPanelLayout.createSequentialGroup()
						.addComponent(logLabel)
						.addGap(34, 34, 34)
						.addComponent(jScrollPane3)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(cleanButton)
						.addContainerGap())
				);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(ExplorerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(ConnectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(PCPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(logsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
				.addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(ConnectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(ExplorerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(PCPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(logsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
				);

		pack();
	}

	protected void photoButtonActionPerformed(ActionEvent evt) {
		updateStatus("PAS IMPLEMENTE");
	}

	protected void takeControlButtonActionPerformed(ActionEvent evt) {
		if (listener != null) {
			new Thread () {
				public void run () {
					if (model.getSelectedClients().size()!=1)
					{
						updateStatus("Cette opération nécessite de sélectionner un client.");
						return;
					}
					listener.requestControl(model.getSelectedClients().get(0));
				}
			}.start();
		}
	}

	protected void osComboBoxActionPerformed(ActionEvent evt) {
		if (listener != null) {
			new Thread () {
				public void run () {
					listener.requestFileNames(dirComboBox.getSelectedItem().toString());
				}
			}.start();
		}
	}

	private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
		if (listener != null) {
			final String name = username.getText();
			new Thread () {
				public void run () {
					listener.requestConnection(name);
					if (model.isConnected())
					{
						listener.lookForClients();
						listener.requestDirNames();
						listener.requestFileNames(dirComboBox.getSelectedItem().toString());
					}
				}
			}.start();
		}
	}                                             

	private void deployButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
		if (listener != null) {
			new Thread () {
				public void run () {
					if (model.getSelectedApps().size()==0)
					{
						updateStatus("Cette opération nécessite de sélectionner au moins une application.");
						return;
					}
					if (model.getSelectedClients().size()==0)
					{
						updateStatus("Cette opération nécessite de sélectionner au moins une destination.");
						return;
					}
					listener.requestInstall(dirComboBox.getSelectedItem().toString(), model.getSelectedApps(), model.getSelectedClients());
				}
			}.start();
		}
	}                                            

	private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
		if (listener != null) {
			new Thread () {
				public void run () {
					listener.lookForClients();
				}
			}.start();
		}
	}      

	private void cleanButtonActionPerformed(ActionEvent evt) {

		installInfosPanel.removeAll();
	}


	// Variables declaration - do not modify                     
	private javax.swing.JPanel ConnectPanel;
	private javax.swing.JPanel ExplorerPanel;
	private javax.swing.JLabel PCLabel;
	private javax.swing.JPanel PCPanel;
	private javax.swing.JPanel PCShowPanel;
	private javax.swing.JButton cleanButton;
	private javax.swing.JButton connectButton;
	private javax.swing.JLabel connectLabel;
	private javax.swing.JButton deployButton;
	private javax.swing.JLabel explorerLabel;
	private javax.swing.JPanel folderPanel;
	private javax.swing.JPanel installInfosPanel;
	private javax.swing.JTextField ipserv;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JLabel logLabel;
	private javax.swing.JPanel logsPanel;
	private javax.swing.JComboBox<String> dirComboBox;
	private javax.swing.JButton photoButton;
	private javax.swing.JButton refreshButton;
	private javax.swing.JLabel status;
	private javax.swing.JButton takeControlButton;
	private javax.swing.JTextField username;
	// End of variables declaration                   

	@Override
	public void updateClients() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PCShowPanel.removeAll();
				for (Client c : model.getClients())
					PCShowPanel.add(new ClientUI(c));
				PCShowPanel.repaint();
				PCShowPanel.revalidate();
				PCPanel.repaint();
				PCPanel.revalidate();
				updateStatus("Liste des clients mise à jour.");
			}
		});
	}

	@Override
	public void updateApps() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				folderPanel.removeAll();
				for (App app : model.getApps())
					folderPanel.add(new AppUI(app));
				folderPanel.repaint();
				folderPanel.revalidate();
				ExplorerPanel.repaint();
				ExplorerPanel.revalidate();
				updateStatus("Liste des applications mise à jour.");
			}
		});
	}
	
	@Override
	public void updateDirs()
	{
		List<String> dirsList=model.getDirs();
		String[] dirs=new String[dirsList.size()];
		for (int i=0;i<dirsList.size();i++)
			dirs[i]=dirsList.get(i);
		dirComboBox.setModel(new DefaultComboBoxModel<>(dirs));
	}

	@Override
	public void loading()
	{
		waiting=true;
		enableAll(false);
		loading.start();
	}

	@Override
	public void stopLoading()
	{
		waiting=false;
		enableAll(true);
		initLoadingThread();
	}

	public void enableAll(boolean enable)
	{
		dirComboBox.setEnabled(enable);
		refreshButton.setEnabled(enable);
		connectButton.setEnabled(enable);
		takeControlButton.setEnabled(enable);
		photoButton.setEnabled(enable);
		username.setEnabled(enable);
		deployButton.setEnabled(enable);
		ipserv.setEnabled(enable);
	}

	@Override
	public void updateInstall(String status) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				for (Client c : model.getClients())
					c.setSelected(false);
				for (App a : model.getApps())
					a.setSelected(false);

				folderPanel.removeAll();
				for (App app : model.getApps())
					folderPanel.add(new AppUI(app));
				folderPanel.repaint();
				folderPanel.revalidate();
				ExplorerPanel.repaint();
				ExplorerPanel.revalidate();

				PCShowPanel.removeAll();
				for (Client c : model.getClients())
					PCShowPanel.add(new ClientUI(c));
				PCShowPanel.repaint();
				PCShowPanel.revalidate();
				PCPanel.repaint();
				PCPanel.revalidate();

				updateStatus(status);
			}
		});
	}
}
