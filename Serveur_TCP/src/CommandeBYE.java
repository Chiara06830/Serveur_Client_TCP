import java.io.PrintStream;

public class CommandeBYE extends Commande {
	
	public CommandeBYE(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() {
		unClient.setUserOk(false);
		unClient.setPwOk(false);
		unClient.setEmplacement(".");
		ps.println("0 Vous avez été déconnecté");
	}

}
