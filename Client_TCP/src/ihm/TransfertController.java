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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idClient.setText(ConnexionController.idClient);

		// Initilisation des chemin
		try {
			//this.cheminClient.setText(ConnexionController.graphique.pwd());
			this.cheminServer.setText(ConnexionController.graphique.pwd());

			// Affichage des fichiers et dossiers
			this.remplirListe(ConnexionController.graphique.ls(), fichierServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void bye(MouseEvent mouseEvent) throws IOException { // deconnexion
		//deconnecter le client
		ConnexionController.graphique.deconnexion();

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
	public void selectionnerClient() {
		HBox selection = this.fichierClient.getSelectionModel().getSelectedItem();
		if(selection != null) {
			Label fichier = (Label) selection.getChildren().get(1);
			this.nomFichierClient = fichier.getText();
		}
	}
	
	@FXML //Récuperer le nom du fichier selectionner cote server
	public void selectionnerServer() {
		HBox selection = this.fichierServer.getSelectionModel().getSelectedItem();
		if(selection != null) {
			Label fichier = (Label) selection.getChildren().get(1);
			this.nomFichierServer = fichier.getText();
		}
	}

	@FXML //supprime un fichier ou repertoire cote server
	public void delete() {
		if(this.nomFichierServer != null) {
			
		}
	}

	@FXML //tranfere un fichier ou repertoire server -> client
	public void get() {
		if(this.nomFichierServer != null) {
			
		}
	}

	@FXML //deplace un fichier ou dossier cote server
	public void mv() {
		if(this.nomFichierServer != null) {
			
		}
	}

	@FXML //tranfere un fichier ou repertoire client -> server
	public void stor() {
		if(this.nomFichierClient != null) {
			
		}
	}
}
