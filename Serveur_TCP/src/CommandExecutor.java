import java.io.PrintStream;

public class CommandExecutor {
	
	public static String emplacement = ".";
	public static boolean userOk = false ;
	public static boolean pwOk = false ;
	public static int prochainPort = 4501;
	
	public static void executeCommande(PrintStream ps, String commande) {
		if(userOk && pwOk) {
			// Changer de repertoire. Un (..) permet de revenir au repertoire superieur
			if(commande.split(" ")[0].equals("cd")) (new CommandeCD(ps, commande)).execute();
	
			// Telecharger un fichier
			if(commande.split(" ")[0].equals("get")) (new CommandeGET(ps, commande)).execute();
			
			// Afficher la liste des fichiers et des dossiers du repertoire courant
			if(commande.split(" ")[0].equals("ls")) (new CommandeLS(ps, commande)).execute();
		
			// Afficher le repertoire courant
			if(commande.split(" ")[0].equals("pwd")) (new CommandePWD(ps, commande)).execute();
			
			// Envoyer (uploader) un fichier
			if(commande.split(" ")[0].equals("stor")) (new CommandeSTOR(ps, commande)).execute();
			
			// Déconnexion
			if(commande.split(" ")[0].equals("bye")) (new CommandeBYE(ps, commande)).execute();
			
			// Supprimer un fichier
			if(commande.split(" ")[0].equals("delete")) (new CommandeDELETE(ps, commande)).execute();
			
			// Supprimer un repertoire
			if(commande.split(" ")[0].equals("deletedir")) (new CommandeDELETEDIR(ps, commande)).execute();
			
			// Déplacer un fichier
			if(commande.split(" ")[0].equals("mv")) (new CommandeMV(ps, commande)).execute();
		}
		else {
			if(commande.split(" ")[0].equals("pass") || commande.split(" ")[0].equals("user")) {
				// Le mot de passe pour l'authentification
				if(commande.split(" ")[0].equals("pass")) (new CommandePASS(ps, commande)).execute();
	
				// Le login pour l'authentification
				if(commande.split(" ")[0].equals("user")) (new CommandeUSER(ps, commande)).execute();
			}
			else
				ps.println("2 Vous n'êtes pas connecté !");
		}
	}

}
