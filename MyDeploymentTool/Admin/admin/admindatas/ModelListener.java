/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.admindatas;

/**
 *
 * @author Hassenforder
 */
public interface ModelListener {
    
    public void updateStatus (String status);
    
    public void updateConnection (String status);

	public void updateClients();

	public void updateApps();
	
	public void updateDirs();

	public void updateInstall(String status);

	public void loading();

	public void stopLoading();

}
