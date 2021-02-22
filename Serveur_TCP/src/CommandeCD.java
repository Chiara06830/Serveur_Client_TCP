import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File f = new File (unClient.getEmplacement() + "\\" + commandeArgs[0]);
		if (f.isDirectory()) {
			unClient.setEmplacement(f.getAbsolutePath());
			System.out.println("a " + unClient.getEmplacement());
			ps.println("0 L'emplacement courant à bien été modifié");
		}else {
			ps.println("2 Le repertoire n'existe pas");
		}
	}

}
