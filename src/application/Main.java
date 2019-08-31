package application;
	
/** Client Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */


import java.io.IOException;

import application.Exception.FXMLException;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

@SuppressWarnings("unused")
public class Main extends Application {

	private Stage window;
	private SplashController splash = new SplashController();
	private MainController main = new MainController();
	
	public void start(Stage primaryStage) throws IOException, FXMLException {
		splash.display();
		window = primaryStage;
		windowConfig();
		main.display(window);
		PauseTransition splashScreenDelay = new PauseTransition(Duration.seconds(3));
		splashScreenDelay.setOnFinished(f -> {
		    primaryStage.show();
		    splash.hideWindow();
		});
		splashScreenDelay.playFromStart();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void windowConfig() {
		window.setTitle("Dictionary Client");
		window.setOnCloseRequest(e -> {
			e.consume();
			close_request();
		});
	}
	
	public void close_request(){
		
		try {
		ConfirmBoxController box = new ConfirmBoxController();
		boolean answer = box.display("QUIT", "Are you sure to quit?");
		if(answer == true) {
			window.close();
		}
		}catch(FXMLException e) {
			main.getWrong().setText(e.getMessage());
		}
	}
	
}
