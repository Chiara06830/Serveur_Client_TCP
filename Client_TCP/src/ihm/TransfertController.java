package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idClient.setText(ConnexionController.idClient);

		// Initilisation des chemin
		// TODO avec pwd
		this.cheminClient.setText("/");
		this.cheminServer.setText("/");

		// Affichage des fichiers et dossiers
		// TODO récuperer les hashmpap a partir de ls
		Map<String, Boolean> list1 = new HashMap<String, Boolean>();
		list1.put("a", true);
		list1.put("b", true);
		list1.put("c", false);
		Map<String, Boolean> list2 = new HashMap<String, Boolean>();
		list2.put("a", true);
		list2.put("b", true);
		list2.put("c", false);
		this.remplirListe(list2, fichierClient);
		this.remplirListe(list1, fichierServer);
	}

	@FXML
	public void bye(MouseEvent mouseEvent) throws IOException { // deconnexion
		// TODO deconnecter le client

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
					mapentry.getValue() ? "/ihm/icon/file-explorer-icon.png" : "/ihm/icon/img_664.png.crdownload");
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
	
	@FXML
	public void selectionnerClient() {
		HBox selection = this.fichierClient.getSelectionModel().getSelectedItem();
		Label fichier = (Label) selection.getChildren().get(1);
		
		System.out.println(fichier.getText());
	}
	
	@FXML
	public void selectionnerServer() {
		HBox selection = this.fichierServer.getSelectionModel().getSelectedItem();
		Label fichier = (Label) selection.getChildren().get(1);
		
		System.out.println(fichier.getText());
	}

	@FXML
	public void delete() {

	}

	@FXML
	public void get() {

	}

	@FXML
	public void mv() {

	}

	@FXML
	public void stor() {

	}
}
