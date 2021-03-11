import java.io.File;
import java.io.PrintStream;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File file = new File(unClient.getEmplacement() + File.separator + commandeArgs[0].toLowerCase());
		// C'est un repertoire ?
		if (file.exists() && file.isDirectory()) {
			unClient.setUserOk(true);
			unClient.setEmplacement(file.getName());
			unClient.setNom(commandeArgs[0].toLowerCase());
			ps.println("0 Commande user OK");
		} else {
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}
	}

}
