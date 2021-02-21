import java.io.File;
import java.io.PrintStream;

public class CommandeSTOR extends Commande {
	
	public CommandeSTOR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File file = new File("./commandeArgs[0]").getAbsoluteFile();
		if (file.exists()) {
			ps.println("0 Le repertoire existe");
		}else {
			ps.println("2 Le repertoire n'existe pas");
		}
	}

}
