import java.io.File;
import java.io.PrintStream;

public class CommandeCREATEDIR extends Commande {

	public CommandeCREATEDIR(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		String testPath[] = commandeArgs[0].split("\\"+ File.separator);
		if(!(commandeArgs[0].equals(File.separator) && !(commandeArgs[0].equals("~")))) {
			if(	!((unClient.getEmplacement().equals("personne")) && (testPath[0].equals(".."))) ) {
				File newFile = new File(unClient.getEmplacement()+ File.separator + commandeArgs[0]);
				
				if(!(newFile.exists())) {				
					if(newFile.mkdirs()) {
						ps.println("0 Le répertoire "+newFile+" a été créé");			
						
					}
					else {
						ps.println("2 La syntaxe est incorrecte");
					}			
				}
				else {
					ps.println("2 Le répertoire existe dèjà ou le nom n'est pas disponible");
					
				}
				
				
			}
			else {
				ps.println("2 Vous ne pouvez pas créer de répertoire ");
			}
			
			
			
			
			
			
			
		}
		else {
			ps.println("2 Ce nom de répertoire n'est pas autorisé");
			
			
		}
		

		
		
			

	}

}
