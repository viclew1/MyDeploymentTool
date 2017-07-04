package admin.admindatas;

import javax.swing.JPanel;
import static common.MDTUtils.*;

public class ClientUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1883797100330269744L;
	
	
	private Client client;

	public ClientUI(Client c) {
		client=c;
		initComponents();
		name.setText(stringRaccourci(c.getName(), 15));
		name.setToolTipText(c.getName());
		addr.setText(c.getAddress());
	}

	private void initComponents() {

		name = new javax.swing.JLabel();
		addr = new javax.swing.JLabel();
		jCheckBox1 = new javax.swing.JCheckBox();

		name.setText("label1");

		addr.setText("label2");

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
						.addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
						.addComponent(addr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(43, 43, 43)
						.addComponent(jCheckBox1)
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(addr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jCheckBox1)
				);
	}

	private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		if (jCheckBox1.isSelected())
			client.setSelected(true);
		else
			client.setSelected(false);
	}                                          


	// Variables declaration - do not modify                     
	private javax.swing.JLabel addr;
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JLabel name;
	// End of variables declaration                   
}