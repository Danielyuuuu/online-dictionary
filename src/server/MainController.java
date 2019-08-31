package server;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.Exception.FXMLException;
import server.Exception.FileFormatException;

public class MainController {

	public MainController() {
		
	}
	
	@FXML
	private Button launchButton;
	
	@FXML
	private TextField pathInput;
	
	@FXML
	private TextField portInput;
	
	@FXML
	private Label filePath;
	
	@FXML
	private Label portNumber;
	
	@FXML
	private Label wrong;
	
	private DictionaryService dictionaryService;
	private ServerSocket socket;
	private Stage window;
	private RunningController running = new RunningController();
	private ServerWrapper server;
	
	public MainController display(Stage primaryWindow) throws FXMLException {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Main.fxml"));
		Parent root = (Parent)loader.load();
		
		MainController controller = loader.<MainController>getController();
		controller.window = primaryWindow;
		
		controller.window.setScene(new Scene(root,600,400));
		return controller;
		}catch(IOException x) {
			throw new FXMLException("FXML parsing error,please contact designer.");
		}
		
	}
	
	public void clickLaunch() {
		try {
			int port = Integer.parseInt(portInput.getText());
			String filePath = pathInput.getText();
			dictionaryService = new BasicDictionaryService(filePath);
			socket = new ServerSocket(port);
			server = new ServerWrapper(dictionaryService,socket);
			running.display(window,server);
			new Thread(server).start();
		}catch (NumberFormatException f) {
			wrong.setText("Please give valid integer port number");
			portInput.clear();
		}catch (FileFormatException y) {
			wrong.setText(y.getMessage());
			pathInput.setText("src/server/Resource/dictionary.txt");
		}catch(FileNotFoundException i) {
			wrong.setText(i.getMessage());
			pathInput.setText("src/server/Resource/dictionary.txt");
		}catch(FXMLException z) {
			wrong.setText(z.getMessage());
		}catch(Exception x) {
			wrong.setText("Can't build socket on given port");
			portInput.clear();
		}
	}
	
	public void changePath() {
		wrong.setText("Warning,data integrity unguranteed");
	}
	
	public ServerWrapper getServer() {
		return server;
	}
	
	public Label getWrong() {
		return wrong;
	}

}
