import java.io.PrintStream;

public abstract class Commande {
	
	protected PrintStream ps;
	protected String commandeNom = "";
	protected String [] commandeArgs ;
	protected GestionUnClient unClient;
	
	public Commande(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		this.ps = ps ;
		String [] args = commandeStr.split(" ");
		this.commandeNom = args[0];
		this.commandeArgs = new String[args.length-1];
		for(int i=0; i<commandeArgs.length; i++) {
			this.commandeArgs[i] = args[i+1];
		}
		this.unClient = unClient;
	}
	
	public abstract void execute();

}
