package application;

/** Client Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */


import java.io.IOException;

import application.Exception.BadCommunicationException;
import application.Exception.FXMLException;
import application.Exception.ServerInvalidException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	private Button connectButton;
	
	@FXML
	private TextField serverInput;
	
	@FXML
	private TextField portInput;
	
	@FXML
	private Label serverAddress;
	
	@FXML
	private Label portNumber;
	
	@FXML
	private Label wrong;
	
	private DictionaryClient client;
	private Stage window;
	private OperationController operation = new OperationController();
	
	public void display(Stage primaryWindow) throws FXMLException {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Main.fxml"));
		Parent root = (Parent)loader.load();
		
		MainController controller = loader.<MainController>getController();
		controller.window = primaryWindow;
		
		controller.window.setScene(new Scene(root,600,400));
		}catch(IOException e) {
			throw new FXMLException("FXML parsing error,please contact designer.");
		}
		
	}
	
	public MainController() {	
		
	}
	
	public void clickConnect() {
		try {
			int port = Integer.parseInt(portInput.getText());
			client = new DictionaryClient();
			client.launch(serverInput.getText(), port);
			operation.display(window,client);
		}catch (NumberFormatException f) {
			wrong.setText("Please enter valid port number");
			portInput.clear();
		}catch (ServerInvalidException g) {
			wrong.setText(g.getMessage());
			portInput.clear();
		}catch(FXMLException h) {
			wrong.setText(h.getMessage());
		}catch(BadCommunicationException x) {
			wrong.setText(x.getMessage());
		}
	}
	
	public Label getWrong() {
		return wrong;
	}
	
}
