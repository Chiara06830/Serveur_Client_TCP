package ihm;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ConnexionController implements Initializable {
	@FXML
	private TextField identifiant;
	@FXML
	private PasswordField password;
	@FXML
	private Label erreure;

	public static String idClient = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	public void connexion(MouseEvent mouseEvent) throws Exception {
		if (this.identifiant.getText() != null && !this.identifiant.getText().equals("")
				&& this.password.getText() != null && !this.password.getText().equals("")) {
			if (ChoixController.graphique.connexion(this.identifiant.getText(), this.password.getText())) {
				// attribution de l'id du client
				idClient = this.identifiant.getText();

				// Changement de page
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("./transfert.fxml")));
				Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
				appStage.setScene(scene);
			} else {
				// ou si il y a une erreure
				erreure.setVisible(true);
			}
		} else {
			// ou si il y a une erreure
			erreure.setVisible(true);
		}
	}
}
