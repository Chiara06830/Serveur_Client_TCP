import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommandeSTOR extends Commande {
	
	public CommandeSTOR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		ps.println("1 Le fichier est prêt à être reçu. Port de transfert :");
		ps.println("0 " + CommandExecutor.prochainPort);
		try {
			ServerSocket serveurFTP = new ServerSocket(CommandExecutor.prochainPort);
			Socket socket = serveurFTP.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream psTransfert = new PrintStream(socket.getOutputStream());
			List<String> lignes = new ArrayList<String>();
			
			boolean onContinue = true;
			while (onContinue) {
				String ligne = br.readLine();
				if ((ligne.charAt(0)) == '2') {
					ps.println("2 Erreur lors de la lecture de la socket");
					onContinue = false;
				}
				if ((ligne.charAt(0)) == '0') {
					Path fichier = Paths.get(commandeArgs[0]);
					Files.write(fichier, lignes, Charset.forName("UTF-8"));
					onContinue = false;
				}
				lignes.add(ligne.substring(2));
			}
			
			br.close();
			psTransfert.close();
			serveurFTP.close();
			socket.close();
			CommandExecutor.prochainPort += 1;
		} catch (IOException e) {
			ps.println("2 Erreur lors de l'écriture du fichier");
			e.printStackTrace();
		}
	}

}
