package application;

/** Client Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */


import java.io.IOException;

import application.Exception.FXMLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class SplashController {
	Stage window;
	
	public void display() throws FXMLException {
		try {
		Parent root = FXMLLoader.load(getClass().getResource("FXML/Splash.fxml"));
		window = new Stage();
		window.initStyle(StageStyle.TRANSPARENT);
		window.setScene(new Scene(root,600,400));
		window.show();
		}catch(IOException e) {
			throw new FXMLException("FXML parsing error,please contact designer.");
		}
	}
	
	public void hideWindow() {
		window.hide();
	}
}
	

