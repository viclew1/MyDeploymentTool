/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.admindatas;

import static common.MDTUtils.*;

/**
 *
 * @author vicle
 */
public class AppUI extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2983143594720253425L;
	
	
	private App app;

	public AppUI(App app) {
		initComponents();
		this.app=app;
		this.name.setText(stringRaccourci(app.getName(),20));
		this.name.setToolTipText(app.getName());
		long size=app.getSize();
		if (size<1000)
			this.size.setText(size+" O");
		else if (size<1000000)
			this.size.setText(moveCommaBy(size, 3)+" Ko");
		else if (size<1000000000)
			this.size.setText(moveCommaBy(size, 6)+" Mo");
		else
			this.size.setText(moveCommaBy(size, 9)+" Go");
	}



	private void initComponents() {

		name = new javax.swing.JLabel();
		jCheckBox1 = new javax.swing.JCheckBox();
		size = new javax.swing.JLabel();

		name.setMaximumSize(new java.awt.Dimension(80, 20));
		name.setText("label1");

		jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox1ActionPerformed(evt);
			}
		});

		size.setText("label1");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(41, 41, 41)
						.addComponent(jCheckBox1)
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(jCheckBox1)
				.addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				);
	}


	private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		if (jCheckBox1.isSelected())
			app.setSelected(true);
		else
			app.setSelected(false);
	}                                          


	// Variables declaration - do not modify                     
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JLabel name,size;
	// End of variables declaration                   
}
