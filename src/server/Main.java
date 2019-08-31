package server;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import server.Exception.CloseException;
import server.Exception.FXMLException;

@SuppressWarnings("unused")
public class Main extends Application {
	
	private Stage window;
	private MainController main = new MainController();

	public void start(Stage primaryStage){
		try {
		window = primaryStage;
		windowConfig();
		main=main.display(window);
		window.show();
		}catch(FXMLException e) {
			System.out.println("Main FXML loaded failure");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void windowConfig() {
		window.setTitle("Dictionary Server");
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
			if(main.getServer() != null) {
				main.getServer().terminate();
			}
			window.close();
		}
		}catch(FXMLException e) {
			main.getWrong().setText(e.getMessage());
		}catch(CloseException g) {
			main.getWrong().setText(g.getMessage());
		}
	}


}
