package admin.admindatas;

/**
 *
 * @author vicle
 */
public class LogUI extends javax.swing.JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6019065916992211567L;


	/**
     * Creates new form ClientUI
     */
    public LogUI(String desc) {
        initComponents();
        this.desc.setText(desc);
    }

    private void initComponents() {

        desc = new javax.swing.JLabel();

        desc.setText("label1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
    }                     


    // Variables declaration - do not modify                     
    private javax.swing.JLabel desc;
    // End of variables declaration                   
}