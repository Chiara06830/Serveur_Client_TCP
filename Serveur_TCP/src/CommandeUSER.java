import java.io.File;
import java.io.PrintStream;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File file = new File(CommandExecutor.emplacement + "\\" + commandeArgs[0].toLowerCase()).getAbsoluteFile();
		// C'est un repertoire ?
		if (file.exists() && file.isDirectory()) {
			CommandExecutor.userOk = true;
			CommandExecutor.emplacement = file.getName();
			ps.println("0 Commande user OK");
		} else {
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}
	}

}
