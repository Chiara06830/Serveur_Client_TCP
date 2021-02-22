import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {
	
	public CommandeGET(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File file = new File(commandeArgs[0]).getAbsoluteFile();
		// C'est un fichier ? && Ce n'est pas un repertoire ?
		if (file.exists() && !file.isDirectory()) {
			ps.println("1 Le fichier est prêt à être envoyé. Port de transfert :");
			ps.println("0 " + CommandExecutor.prochainPort);
			try {
				ServerSocket serveurFTP = new ServerSocket(CommandExecutor.prochainPort);
				Socket socket = serveurFTP.accept();
				BufferedReader br = new BufferedReader(new FileReader(file));
				PrintStream psTransfert = new PrintStream(socket.getOutputStream());
				
				String ligne;
				while ((ligne = br.readLine()) != null)
					psTransfert.println("1 " + ligne);
				psTransfert.println("0 " + commandeArgs[0] + " : Transfert terminé.");
				
				br.close();
				psTransfert.close();
				serveurFTP.close();
				socket.close();
				CommandExecutor.prochainPort += 1;
			} catch (IOException e) {
				ps.println("2 Erreur lors de la lecture du fichier");
				e.printStackTrace();
			}
		} else {
			ps.println("2 Le fichier est introuvable");
		}
	}

}
