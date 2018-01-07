package server.serverdatas;

import java.io.File;
import java.util.prefs.Preferences;


public class Constantes
{

	public static Preferences pref =  Preferences.userNodeForPackage( Constantes.class );
	
	public static String SERVER_PATH="D:/Bibliothèque/Desktop/Serveur/";
	public static File SRV_FILE=new File(SERVER_PATH);

	public static boolean DEBUG = false;
}
