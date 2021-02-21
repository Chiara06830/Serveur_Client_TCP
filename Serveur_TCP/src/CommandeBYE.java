import java.io.File;
import java.io.PrintStream;

public class CommandeBYE extends Commande {
	
	public CommandeBYE(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		CommandExecutor.userOk = false;
		CommandExecutor.pwOk = false;
		ps.println("0 Vous avez été déconnecté");
	}

}
