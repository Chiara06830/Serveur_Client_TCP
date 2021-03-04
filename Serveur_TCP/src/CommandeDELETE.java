import java.io.File;
import java.io.PrintStream;

public class CommandeDELETE extends Commande {
	
	public CommandeDELETE(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File file = new File(unClient.getEmplacement() + "\\" + commandeArgs[0]).getAbsoluteFile();
		// C'est un fichier ? && Ce n'est pas un repertoire ?
		System.out.println(file.toString());
		if (file.exists() && !file.isDirectory()) {
			if(file.delete()) {
				ps.println("0 Le fichier a été supprimé");
			} else {
			    ps.println("2 La suppression a échouer");
			}
		} else {
			ps.println("2 Le fichier est introuvable");
		}
	}

}
