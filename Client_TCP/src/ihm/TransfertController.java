package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TransfertController implements Initializable{
	@FXML
	private Label idClient;
	@FXML
	private TextField cheminClient;
	@FXML
	private TextField cheminServer;
	@FXML
	private ListView fichierClient;
	@FXML
	private ListView fichierServer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idClient.setText(ConnexionController.idClient);
		
		//Initilisation des chemin 
		//TODO avec pwd 
		this.cheminClient.setText("/");
		this.cheminServer.setText("/");
		
		//Affichage des fichiers et dossiers
		//TODO
	}

	@FXML
	public void bye(MouseEvent mouseEvent) throws IOException { //deconnexion
		//TODO deconnecter le client
		
		//Changement de page 
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("./connexion.fxml")));
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        appStage.setScene(scene);
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
