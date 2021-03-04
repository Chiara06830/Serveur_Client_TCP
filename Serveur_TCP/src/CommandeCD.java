import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File f = new File (unClient.getEmplacement() + "\\" + commandeArgs[0]);
		String [] chemin = unClient.getEmplacement().split(" ");
		//Si il veut sortir de son répértoire
		if(commandeArgs[0].equals("..") && chemin[chemin.length-1].equals(unClient.getNom())) {
			ps.println("2 Vous ne pouvez pas sortir de votre dossier");
		}else if (f.isDirectory()) { //si le dossier existe
			unClient.setEmplacement(f.getAbsolutePath());
			System.out.println("a " + unClient.getEmplacement());
			ps.println("0 L'emplacement courant à bien été modifié");
		}else {
			ps.println("2 Le repertoire n'éxiste pas");
		}
	}

}
