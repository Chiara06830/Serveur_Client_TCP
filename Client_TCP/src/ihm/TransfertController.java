package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class TransfertController implements Initializable {
	@FXML
	private Label idClient;
	@FXML
	private TextField cheminClient;
	@FXML
	private TextField cheminServer;
	@FXML
	private ListView<HBox> fichierClient = new ListView<HBox>();
	@FXML
	private ListView<HBox> fichierServer = new ListView<HBox>();
	
	private String nomFichierClient = null;
	private String nomFichierServer = null;
	
	private Map<String, Boolean> fichiersServer;
	private Map<String, Boolean> fichiersClient;
	private boolean move = false;
	
	public static String nomDossier = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idClient.setText(ConnexionController.idClient);

		// Initilisation des chemin
		try {
			this.affichage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void affichage() throws Exception {
		//this.cheminClient.setText(ConnexionController.graphique.pwd());
		this.cheminServer.setText(ChoixController.graphique.pwd());
		this.cheminClient.setText(ChoixController.graphique.pwdClient());

		// Affichage des fichiers et dossiers
		this.fichiersServer = ChoixController.graphique.ls();
		this.remplirListe(this.fichiersServer, fichierServer);
		this.fichiersClient = ChoixController.graphique.lsClient();
		this.remplirListe(fichiersClient, this.fichierClient);
	}

	@FXML
	public void bye(MouseEvent mouseEvent) throws IOException { // deconnexion
		//deconnecter le client
		ChoixController.graphique.deconnexion();

		// Changement de page
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("./connexion.fxml")));
		Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
		appStage.setScene(scene);
	}

	// Rempli une liste avec ses dossiers et fichiers
	public void remplirListe(Map<String, Boolean> fichier, ListView<HBox> list) {
		List<HBox> lignes = new ArrayList<HBox>();

		for (Map.Entry<String, Boolean> mapentry : fichier.entrySet()) {
			HBox hb = new HBox();
			hb.setSpacing(5);

			// creation de l'image du fichier
			ImageView icone = new ImageView(
					mapentry.getValue() ? "/ihm/icon/file-explorer-icon.png" : "/ihm/icon/th.jpeg");
			icone.setFitWidth(15);
			icone.setFitHeight(15);
			hb.getChildren().add(icone);

			// creation du nom du fichier
			hb.getChildren().add(new Label(mapentry.getKey()));

			lignes.add(hb);
		}

		// créer la liste view avec les hbox
		ObservableList<HBox> items = FXCollections.observableArrayList();
		items.addAll(lignes);
		list.setItems(items);
	}
	
	@FXML //Récuperer le nom du fichier selectionner coté client
	public void selectionnerClient(MouseEvent mouseEvent) throws Exception  {
		HBox selection = this.fichierClient.getSelectionModel().getSelectedItem();
		if(selection != null) {
			Label fichier = (Label) selection.getChildren().get(1);
			
			this.nomFichierClient = fichier.getText();
			
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){ //si il y a un double clique
	            if(mouseEvent.getClickCount() == 2){
	            	//si c'est un dossier on se déplace
	            	if(this.fichiersClient.get(this.nomFichierClient)) {
	            		ChoixController.graphique.cd(this.nomFichierClient);
		                this.affichage();
	            	}
	            }
	        }
		}
	}
	
	@FXML //Récuperer le nom du fichier selectionner cote server
	public void selectionnerServer(MouseEvent mouseEvent) throws Exception{
		HBox selection = this.fichierServer.getSelectionModel().getSelectedItem();
		if(selection != null) {
			Label fichier = (Label) selection.getChildren().get(1);
			if(this.move) { //si on clique pour déplacer un fichier
				ChoixController.graphique.mv(this.nomFichierServer, fichier.getText());
				this.move = false;
				this.affichage();
			}
			this.nomFichierServer = fichier.getText();
			
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){ //si il y a un double clique
	            if(mouseEvent.getClickCount() == 2){
	            	//si c'est un dossier on se déplace
	            	if(this.fichiersServer.get(this.nomFichierServer)) {
	            		ChoixController.graphique.envoieCommande("cd", this.nomFichierServer);
		                this.affichage();
	            	}
	            }
	        }
		}
	}

	@FXML //supprime un fichier ou repertoire cote server
	public void delete() throws Exception {
		if(this.nomFichierServer != null && !this.nomFichierServer.equals("..")) {
			if(this.fichiersServer.get(this.nomFichierServer)) { //si c'est un dossier
				if(!ChoixController.graphique.envoieCommande("deletedir", this.nomFichierServer)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Action impossible");
					alert.setHeaderText("Vous ne pouvez pas supprimer un dossier qui n'est pas vide");

					alert.showAndWait();
				}
			}else { //si c'est un fichier
				ChoixController.graphique.envoieCommande("delete",this.nomFichierServer);
			}
			this.affichage();
		}
	}

	@FXML //tranfere un fichier ou repertoire server -> client
	public void get() throws IOException {
		if(this.nomFichierServer != null && !this.nomFichierServer.equals("..")) {
			ChoixController.graphique.get(this.nomFichierServer);
		}
	}

	@FXML //deplace un fichier ou dossier cote server
	public void mv() throws IOException {
		if(this.nomFichierServer != null && !this.nomFichierServer.equals("..")) {
			this.move = !this.move; //autorise le déplacement au prochain clique
		}
	}

	@FXML //tranfere un fichier ou repertoire client -> server
	public void stor() throws Exception {
		if(this.nomFichierClient != null && !this.nomFichierClient.equals("..")) {
			ChoixController.graphique.stor(this.nomFichierClient);
			this.affichage();
		}
	}
	
	@FXML
	public void createDir(MouseEvent mouseEvent) throws Exception {
		final URL fxmlUrl = getClass().getResource("/ihm/popNouveauDossier.fxml");
	    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
	    Pane root = fxmlLoader.load();
	    
		Stage popup = new Stage();
	    popup.setTitle("Créer un dossier");
	    Scene scene = new Scene(root);
	    
	    popup.setScene(scene);
	    popup.showAndWait();
	    
	    ChoixController.graphique.envoieCommande("createdir", TransfertController.nomDossier);
	    TransfertController.nomDossier = null;
	    this.affichage();
	}
}
