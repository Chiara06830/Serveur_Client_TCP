import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File f = new File (commandeArgs[0]).getAbsoluteFile();
		if (f.isDirectory()) {
			ps.println("0 Le repertoire existe");
			//System.setProperty("user.dir", f.getAbsolutePath());
		}else {
			ps.println("2 Le repertoire n'existe pas");
		}
		/*File directory = new File(commandeArgs[0]).getAbsoluteFile();
		if (directory.exists() || directory.mkdirs()) {
			boolean ret = System.setProperty("user.dir", directory.getAbsolutePath()) != null;
			if (ret) {
				String s = directory.getAbsoluteFile().toString();
				ps.println("0 " + s);
			} else {
				ps.println("2 Impossible de changer de répertoire courant");
			}*/
		/*} else {
			ps.println("2 Le repertoire n'existe pas");
		}*/
	}

}
