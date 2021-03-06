package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import application.Graphique;
import application.Terminal;

public class ChoixController implements Initializable {

	public static Graphique graphique;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	public void lancerTerminal(MouseEvent mouseEvent) {
		Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
		appStage.close();
		Terminal t = new Terminal();
		t.lancer();
	}

	@FXML
	public void lancerGraphique(MouseEvent mouseEvent) throws IOException {
		ChoixController.graphique = new Graphique();
		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("./connexion.fxml")));
		Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
		appStage.setScene(scene);

		appStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				try {
					ChoixController.graphique.quit();
					Platform.exit();
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
