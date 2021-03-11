import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File[] fichiers = new File(unClient.getEmplacement()).getAbsoluteFile().listFiles();
		if (fichiers != null && fichiers.length > 0) {
			int taille = fichiers.length;
			for(int i = 0; i < taille; i++) {
				String[] chemin = fichiers[i].toString().split("\\" + File.separator);
				if (i == taille - 1) { //si c'est le dernier
					if(fichiers[i].isDirectory()) {
						ps.println("0 # " + chemin[chemin.length-1]);
					}else {
						ps.println("0 " + chemin[chemin.length-1]);
					}
				}
				else {
					if(fichiers[i].isDirectory()) {
						ps.println("1 # " + chemin[chemin.length-1]);
					}else {
						ps.println("1 " + chemin[chemin.length-1]);
					}
				}
	         }
		} else {
			ps.println("0 Aucun fichier n'est présent sur ce repertoire");
		}
	}

}
