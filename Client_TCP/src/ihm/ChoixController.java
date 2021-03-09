package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import application.Terminal;

public class ChoixController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

	@FXML
	public void lancerTerminal(MouseEvent mouseEvent) {
		Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
		appStage.close();
		Terminal t = new Terminal();
		t.lancer();
	}
	
	@FXML
	public void lancerGraphique(MouseEvent mouseEvent) throws IOException {
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("./connexion.fxml")));
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        appStage.setScene(scene);
	}
}
