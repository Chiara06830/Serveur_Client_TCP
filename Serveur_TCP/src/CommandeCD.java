import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File f = new File (unClient.getEmplacement() + "\\" + commandeArgs[0]);
		String [] chemin = unClient.getEmplacement().split("\\\\");
		String [] arguments = commandeArgs[0].split("\\\\");
		//Si il veut sortir de son répértoire
		System.out.println(arguments[0]);
		if(arguments[0].equals("..") && chemin[chemin.length-1].equals(unClient.getNom())) {
			ps.println("2 Vous ne pouvez pas sortir de votre dossier");
		}else if (f.isDirectory()) { //si le dossier existe
			if(commandeArgs[0].equals("..")) {//si on remonte d'un dossier
				unClient.setEmplacement(unClient.getEmplacement().substring(0, (unClient.getEmplacement().length() - chemin[chemin.length-1].length())-1));
			}else {
				unClient.setEmplacement(f.getAbsolutePath());
			}
			ps.println("0 L'emplacement courant à bien été modifié");
		}else {
			ps.println("2 Le repertoire n'éxiste pas");
		}
	}

}
