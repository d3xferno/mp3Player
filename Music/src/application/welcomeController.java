package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class welcomeController {
	private Scene scene;
	private Stage stage;
	
	@FXML
	ImageView myView;
	Image myImg;
	public void enterApp(ActionEvent event) throws IOException {
	Parent root = FXMLLoader.load(getClass().getResource("/application/Sample.fxml"));
	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	scene = new Scene(root);
	stage.setScene(scene);
	stage.show();		
}

}
