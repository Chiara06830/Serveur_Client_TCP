package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ConnexionController implements Initializable{
	@FXML
	private TextField identifiant;
	@FXML
	private TextField password; 
	@FXML 
	private Label erreure;
	
	public static String idClient = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	
	@FXML
	public void connexion(MouseEvent mouseEvent) throws IOException {
		System.out.println("Connexion");
		
		//attribution de l'id du client
		idClient = "personne";
		
		//ou si il y a une erreure 
		erreure.setVisible(true);
		
		//Changement de page 
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("./transfert.fxml")));
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        appStage.setScene(scene);
	}
}
