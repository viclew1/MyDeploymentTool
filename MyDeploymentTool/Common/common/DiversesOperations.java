package common;

import java.io.File;

public class DiversesOperations {


	public static long tailleFichier(File f)
	{
		long size=0;
		if (f.isFile())
			size+=f.length();
		else
			for (File ff : f.listFiles())
				size+=tailleFichier(ff);
		return size;
	}


	public static String moveCommaBy(long l, int decallage) 
	{ 
		String a = ""+l+""; 
		a=a.substring(0,a.length()-decallage)+","+a.substring(a.length()-decallage,a.length()-decallage+1);
		return a; 
	} 
	
	public static String stringRaccourci(String str, int nbCharac) {
		if (str.length()<=nbCharac)
			return str;
		return str.substring(0,nbCharac)+"[...]";
	}

}
