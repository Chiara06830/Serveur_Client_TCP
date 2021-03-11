import java.io.PrintStream;

public class CommandExecutor {
	
	public static void executeCommande(PrintStream ps, String commande, GestionUnClient unClient) {
		if(unClient.isUserOk() && unClient.isPwOk()) {
			// Changer de repertoire. Un (..) permet de revenir au repertoire superieur
			if(commande.split(" ")[0].equals("cd")) (new CommandeCD(ps, commande, unClient)).execute();
	
			// Telecharger un fichier
			if(commande.split(" ")[0].equals("get")) (new CommandeGET(ps, commande, unClient)).execute();
			
			// Afficher la liste des fichiers et des dossiers du repertoire courant
			if(commande.split(" ")[0].equals("ls")) (new CommandeLS(ps, commande, unClient)).execute();
		
			// Afficher le repertoire courant
			if(commande.split(" ")[0].equals("pwd")) (new CommandePWD(ps, commande, unClient)).execute();
			
			// Envoyer (uploader) un fichier
			if(commande.split(" ")[0].equals("stor")) (new CommandeSTOR(ps, commande, unClient)).execute();
			
			// D�connexion
			if(commande.split(" ")[0].equals("bye")) (new CommandeBYE(ps, commande, unClient)).execute();
			
			// Supprimer un fichier
			if(commande.split(" ")[0].equals("delete")) (new CommandeDELETE(ps, commande, unClient)).execute();
			
			// Supprimer un repertoire
			if(commande.split(" ")[0].equals("deletedir")) (new CommandeDELETEDIR(ps, commande, unClient)).execute();
			
			// D�placer un fichier
			if(commande.split(" ")[0].equals("mv")) (new CommandeMV(ps, commande, unClient)).execute();
		}
		else {
			if(commande.split(" ")[0].equals("pass") || commande.split(" ")[0].equals("user")) {
				// Le mot de passe pour l'authentification
				if(commande.split(" ")[0].equals("pass")) (new CommandePASS(ps, commande, unClient)).execute();
	
				// Le login pour l'authentification
				if(commande.split(" ")[0].equals("user")) (new CommandeUSER(ps, commande, unClient)).execute();
			}
			else
				ps.println("2 Vous n'êtes pas connecté !");
		}
	}

}
