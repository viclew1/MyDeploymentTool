package client.clientdatas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import client.NetworkListener;
import common.Protocol;

/**
 *
 * @author vicle
 */
public class ClientFrame extends javax.swing.JFrame {


	private NetworkListener listener;
	
	public ClientFrame(NetworkListener listener) {
    	super("My Deployment Tool");
    	this.listener=listener;

    	JMenuBar jmb=new JMenuBar();
        JMenu settings=new JMenu("Options");
        JMenuItem changeIP=new JMenuItem("Adresse serveur");
        changeIP.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String newIP=JOptionPane.showInputDialog("Adresse IP du serveur ?");
				if (newIP!=null && !newIP.equals(""))
				{
					changeIP(newIP);
				}
					
			}
		});
        
        JMenuItem changeDest=new JMenuItem("Dossier destination");
        changeDest.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Dossier de réception");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
				}
				else {
					return;
				}
				changeDest(chooser.getSelectedFile().getAbsolutePath()+"/");
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
        
        settings.add(changeIP);
        settings.add(changeDest);
        settings.add(exit);
        jmb.add(settings);
        this.setJMenuBar(jmb);
        this.setFocusable(true);
        this.setResizable(false);
        
        initComponents();
        ipLabel.setText(Protocol.IPSERV);
        destLabel.setText(Protocol.DEST_DIR);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            	listener.disconnect();
            	System.exit(0);
            }
        });
    }

	private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ipLabel = new javax.swing.JLabel();
        destLabel = new javax.swing.JLabel();
        status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("IP Serveur : ");

        jLabel2.setText("Dossier réception :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ipLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(28, 28, 28)
                        .addComponent(destLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ipLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(destLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(status))
        );

        pack();
    }                  


    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JLabel destLabel;
    private javax.swing.JLabel status;
    // End of variables declaration   
    
    
    public void updateStatus(String logs)
    {
    	status.setText(logs);
    }
    
    public void changeIP(String ip)
    {
    	new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
		    	Protocol.IPSERV=ip;
		    	ipLabel.setText(ip);
		    	listener.disconnect();
		    	try
				{
					listener.notifyConnection();
				} catch (UnknownHostException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
    }
    
    public void changeDest(String dest)
    {
    	new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
		    	Protocol.DEST_DIR=dest;
		    	ipLabel.setText(dest);
			}
		}).start();
    }
}
