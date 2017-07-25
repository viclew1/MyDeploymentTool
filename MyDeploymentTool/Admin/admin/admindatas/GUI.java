/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.admindatas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		
		JMenuBar jmb=new JMenuBar();
        JMenu settings=new JMenu("Fichier");
        
        JMenuItem disconnect=new JMenuItem("Déconnecter");
        disconnect.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				listener.requestConnection(model.getName());
			}
		});
        
        JMenuItem exit=new JMenuItem("Quitter");
        exit.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

        settings.add(disconnect);
        settings.add(exit);
        jmb.add(settings);
        this.setJMenuBar(jmb);
		
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
			}
		}
	}


    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        PCShowPanel = new javax.swing.JPanel();
        folderPanel = new javax.swing.JPanel();
        installInfosPanel = new javax.swing.JPanel();
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
        banButton = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        logsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        logLabel = new javax.swing.JLabel();
        cleanButton = new javax.swing.JButton();

        PCShowPanel.setLayout(new javax.swing.BoxLayout(PCShowPanel, javax.swing.BoxLayout.Y_AXIS));

        folderPanel.setLayout(new javax.swing.BoxLayout(folderPanel, javax.swing.BoxLayout.Y_AXIS));

        installInfosPanel.setLayout(new javax.swing.BoxLayout(installInfosPanel, javax.swing.BoxLayout.Y_AXIS));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ExplorerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        explorerLabel.setText("Explorateur");

        dirComboBox.setToolTipText("");
        dirComboBox.setLightWeightPopupEnabled(false);
        dirComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dirComboBoxActionPerformed(evt);
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
                        .addGap(0, 191, Short.MAX_VALUE))
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
                .addComponent(jScrollPane2)
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

        banButton.setText("Bannir");
        banButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                banButtonActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(banButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deployButton)
                    .addComponent(takeControlButton)
                    .addComponent(banButton))
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
                        .addGap(0, 185, Short.MAX_VALUE)
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
                .addComponent(ExplorerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(PCPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ExplorerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

	protected void dirComboBoxActionPerformed(ActionEvent evt) {
		if (listener != null) {
			new Thread () {
				public void run () {
					listener.requestFileNames(dirComboBox.getSelectedItem().toString());
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
	
	private void banButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
		if (listener != null) {
			new Thread () {
				public void run () {
					if (model.getSelectedClients().size()==0)
					{
						updateStatus("Cette opération nécessite de sélectionner au moins un client.");
						return;
					}
					System.out.println("BANNIR : PAS ENCORE IMPLEMENTE");
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
	private javax.swing.JPanel ExplorerPanel;
	private javax.swing.JLabel PCLabel;
	private javax.swing.JPanel PCPanel;
	private javax.swing.JPanel PCShowPanel;
	private javax.swing.JButton cleanButton;
	private javax.swing.JButton deployButton;
	private javax.swing.JButton banButton;
	private javax.swing.JLabel explorerLabel;
	private javax.swing.JPanel folderPanel;
	private javax.swing.JPanel installInfosPanel;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JLabel logLabel;
	private javax.swing.JPanel logsPanel;
	private javax.swing.JComboBox<String> dirComboBox;
	private javax.swing.JButton refreshButton;
	private javax.swing.JLabel status;
	private javax.swing.JButton takeControlButton;
	
	
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
		takeControlButton.setEnabled(enable);
		deployButton.setEnabled(enable);
	}

	@Override
	public void updateInstall(String status) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
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

	@Override
	public void exit()
	{
		setVisible(false);
	}
}
