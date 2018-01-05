package admin.admindatas;

import static common.MDTUtils.stringRaccourci;
import static admin.admindatas.Constantes.*;

import javax.swing.JPanel;

public class ClientUI extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1883797100330269744L;

	private Client client;

	public ClientUI(Client c) 
	{
		client=c;
		initComponents();
		name.setText(stringRaccourci(c.getName(), 15));
		name.setToolTipText(c.getName());
		addr.setText(c.getAddress());
		if (!c.isConnected())
			state.setIcon(Icons.DISCONNECTED_ICON);
		else
			if (c.isBusy())
				state.setIcon(Icons.BUSY_ICON);
			else
				state.setIcon(Icons.AVAILABLE_ICON);
	}

	private void initComponents() 
	{

        name = new javax.swing.JLabel();
        addr = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        progress = new javax.swing.JLabel();
        state = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(441, 21));
        setMinimumSize(new java.awt.Dimension(441, 21));

        name.setText("jLabel1");

        addr.setText("jLabel2");

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, IMG_W, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(addr, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(addr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jCheckBox1))
            .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, IMG_H, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }

	private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) 
	{                                           
		client.setSelected(!client.isSelected());
	}                                          


	// Variables declaration - do not modify                     
	private javax.swing.JLabel addr;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel name;
    private javax.swing.JLabel progress;
    private javax.swing.JLabel state;
	// End of variables declaration                   
}