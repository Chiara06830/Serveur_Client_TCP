import java.io.File;
import java.io.PrintStream;

public class CommandeDELETEDIR extends Commande {
	
	public CommandeDELETEDIR(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File file = new File(unClient.getEmplacement() + "\\" + commandeArgs[0]).getAbsoluteFile();
		String [] chemin = unClient.getEmplacement().split("\\\\");
		String [] suppression = unClient.getEmplacement().split("\\\\");
		if(suppression[suppression.length-1].equals(chemin[chemin.length-1])) {
			ps.println("2 Vous ne pouvez pas supprimé le répertoire courant");
		}else
		// C'est un repertoire ?
		if (file.exists() && file.isDirectory()) {
			if(file.delete()) {
				ps.println("0 Le repertoire a été supprimé");
			} else {
			    ps.println("2 La suppression a échouer. Veuillez vérifié que le repertoire est vide");
			}
		} else {
			ps.println("2 Le repertoire est introuvable");
		}
	}

}
