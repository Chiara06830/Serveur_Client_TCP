import java.io.File;
import java.io.PrintStream;

public class CommandeDELETEDIR extends Commande {
	
	public CommandeDELETEDIR(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		File file = new File(unClient.getEmplacement() + "\\" + commandeArgs[0]).getAbsoluteFile();
		// C'est un repertoire ?
		if (file.exists() && file.isDirectory()) {
			if(file.delete()) {
				ps.println("0 Le repertoire a �t� supprim�");
			} else {
			    ps.println("2 La suppression a �chouer. Veuillez v�rifi� que le repertoire est vide");
			}
		} else {
			ps.println("2 Le repertoire est introuvable");
		}
	}

}
