import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class CommandePASS extends Commande {
	
	public CommandePASS(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File file = new File(unClient.getEmplacement() + "\\pw.txt").getAbsoluteFile();
		// C'est un fichier ?
		if (file.exists() && !file.isDirectory()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				if (br.readLine().equals(commandeArgs[0].toLowerCase())) {
					unClient.setPwOk(true);
					ps.println("1 Commande pass OK");
					ps.println("0 Vous êtes bien connecté sur notre serveur");
				} else {
					ps.println("2 Le mode de passe est faux");
				}
				br.close();
			} catch (IOException e) {
				ps.println("2 Une erreur c'est produite à la lecture de pw.txt");
				e.printStackTrace();
			}
		} else {
			ps.println("2 Le fichier comportant le mot de passe est introuvable");
		}
		
	}

}
