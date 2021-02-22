import java.io.File;
import java.io.PrintStream;

public class CommandeMV extends Commande {
	
	public CommandeMV(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File sourceFile = new File(CommandExecutor.emplacement + "\\" + commandeArgs[0]);
	    File destinationFolder = new File(CommandExecutor.emplacement + "\\" + commandeArgs[1]);
		// C'est un fichier ? && Ce n'est pas un repertoire ?
		if (sourceFile.exists() && !sourceFile.isDirectory()) {
			if (!destinationFolder.exists()) {
				if (!destinationFolder.mkdirs()) {
					ps.println("2 Le repertoire destination est introuvable et n'a pas pu être crée");
				}
			}
			System.out.println(destinationFolder + "\\" + commandeArgs[0]);
			if(sourceFile.renameTo(new File(destinationFolder + "\\" + commandeArgs[0]))) {
				ps.println("0 Le fichier a été déplacer avec succès");
			} else {
			    ps.println("2 Le fichier n'a pas pu déplacer");
			}
		// C'est un repertoire ?
		} else if (sourceFile.exists() && sourceFile.isDirectory()) {
			if (!destinationFolder.exists()) {
				if (!destinationFolder.mkdirs()) {
					ps.println("2 Le repertoire destination est introuvable et n'a pas pu être crée");
				}
			}
			File[] listOfFiles = sourceFile.listFiles();
			boolean bonFonctionnement = true;
			if (listOfFiles != null) {
	            for (File child : listOfFiles) {
	                if(!child.renameTo(new File(destinationFolder + "\\" + child.getName()))) {
	                	bonFonctionnement = false;
	                }
	            }
			}
			
			if (bonFonctionnement) {
				sourceFile.delete();
				ps.println("0 Le repertoire a été déplacer avec succès");
			} else {
				ps.println("2 Le repertoire n'a pas pu être déplacer");
			}
		} else {
			ps.println("2 Le fichier source est introuvable");
		}
	}

}
