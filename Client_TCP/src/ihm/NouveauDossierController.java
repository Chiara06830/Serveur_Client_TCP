package ihm;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class NouveauDossierController  implements Initializable{
	@FXML
	private TextField nom;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

	@FXML
	public void valider(MouseEvent mouseEvent) {
		TransfertController.nomDossier = nom.getText();
		
		Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
	    stage.close();
	}
}
