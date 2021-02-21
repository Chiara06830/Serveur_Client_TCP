import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		String[] fichiers = new File(".").list();
		int taille = fichiers.length;
		for(int i = 0; i < taille; i++) {
			if (i == taille - 1) ps.println("0 " + fichiers[i]);
			else ps.println("1 " + fichiers[i]);
         }
	}

}
