import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File f = new File (CommandExecutor.emplacement + "\\" + commandeArgs[0]);
		if (f.isDirectory()) {
			CommandExecutor.emplacement = f.getName();
			ps.println("0 L'emplacement courant à bien été modifié");
		}else {
			ps.println("2 Le repertoire n'existe pas");
		}
	}

}
