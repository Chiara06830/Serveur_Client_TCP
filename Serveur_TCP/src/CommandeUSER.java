import java.io.File;
import java.io.PrintStream;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File file = new File(unClient.getEmplacement() + "/" + commandeArgs[0].toLowerCase());
		// C'est un repertoire ?
		System.out.println("Avant le if");
		System.out.println(file);
		System.out.println(file.exists());
		System.out.println(file.isDirectory());
		
		if (file.exists() && file.isDirectory()) {
			System.out.println("Après le if");
			unClient.setUserOk(true);
			unClient.setEmplacement(file.getName());
			unClient.setNom(commandeArgs[0].toLowerCase());
			ps.println("0 Commande user OK");
		} else {
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}
	}

}
