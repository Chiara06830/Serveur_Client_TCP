import java.io.File;
import java.io.PrintStream;

public class CommandeDELETE extends Commande {
	
	public CommandeDELETE(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File file = new File(CommandExecutor.emplacement + "\\" + commandeArgs[0]).getAbsoluteFile();
		System.out.println(file);
		// C'est un fichier ? && Ce n'est pas un repertoire ?
		System.out.println(file);
		if (file.exists() && !file.isDirectory()) {
			if(file.delete()) {
				ps.println("0 Le fichier a été supprimé");
			} else {
			    ps.println("2 La suppression a échouer");
			}
		} else {
			ps.println("2 Le fichier est introuvable");
		}
	}

}
