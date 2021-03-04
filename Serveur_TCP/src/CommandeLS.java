import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		String[] fichiers = new File(unClient.getEmplacement()).list();
		if (fichiers != null) {
			int taille = fichiers.length;
			for(int i = 0; i < taille; i++) {
				if (i == taille - 1) ps.println("0 " + fichiers[i]);
				else ps.println("1 " + fichiers[i]);
	         }
		} else {
			ps.println("0 Aucun fichier n'est présent sur ce repertoire");
		}
	}

}
