import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {

	public CommandeGET(PrintStream ps, String commandeStr, GestionUnClient unClient) {
		super(ps, commandeStr, unClient);
	}

	public void execute() throws IOException {
		File file = new File(unClient.getEmplacement() + File.separator + commandeArgs[0]).getAbsoluteFile();
		
		ServerSocket serveurFTP = new ServerSocket(unClient.getProchainPort());
		System.out.println("socket...");
		Socket socket = serveurFTP.accept();
		
		// C'est un fichier ? && Ce n'est pas un repertoire ?
		if (file.exists() && !file.isDirectory()) {
			this.getOneFile(file, socket);
		}
		if (file.exists() && file.isDirectory()) {
			File[] contenu = new File(unClient.getEmplacement() + File.separator + commandeArgs[0]).getAbsoluteFile()
					.listFiles();
			for (int i = 0; i < contenu.length; i++) {
				if(!contenu[i].isDirectory()) {
					this.getOneFile(contenu[i], socket);
				}
			}
		} else {
			ps.println("2 Le fichier est introuvable");
		}
		serveurFTP.close();
		socket.close();
	}

	public void getOneFile(File file, Socket socket) {
		ps.println("1 Le fichier est prêt à être envoyé. Port de transfert :");
		ps.println("0 " + unClient.getProchainPort());
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintStream psTransfert = new PrintStream(socket.getOutputStream());
			
			String ligne;
			while ((ligne = br.readLine()) != null) {
				psTransfert.println("1 " + ligne);
			}
			psTransfert.println("0 " + file.getName() + " : Transfert terminé.");

			br.close();
			psTransfert.close();
			unClient.setProchainPort(unClient.getProchainPort() + 1);
		} catch (IOException e) {
			ps.println("2 Erreur lors de la lecture du fichier");
			e.printStackTrace();
		}
	}
}
