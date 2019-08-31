package server;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.Exception.CloseException;
import server.Exception.FXMLException;

public class RunningController {

	@FXML
	private Label run;
	
	@FXML
	private Button shut;
	
	@FXML
	private Label wrong;
	
	
	private Stage window;
	private ServerWrapper server;
	
	
	public RunningController() {
		
	}
	
	public void display(Stage primaryWindow,ServerWrapper server) throws FXMLException {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Running.fxml"));
		Parent root = (Parent)loader.load();
		
		RunningController controller = loader.<RunningController>getController();
		controller.window = primaryWindow;
		controller.server = server;
		
		controller.window.setScene(new Scene(root,600,400));
		}catch(IOException e) {
			throw new FXMLException("FXML parsing error,please contact designer.");
		}
	}
	
	public void shutDown() {
		try {
			ConfirmBoxController box = new ConfirmBoxController();
			boolean answer = box.display("Shut Server", "Are you sure to shut down server?");
			if(answer == true) {
				server.terminate();
				MainController main = new MainController();
				main.display(window);
			}
			}catch(FXMLException e) {
				wrong.setText(e.getMessage());
			}catch(CloseException g) {
				wrong.setText(g.getMessage());
			}
	}
	
}
