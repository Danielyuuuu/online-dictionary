package application;

/** Client Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

import java.io.IOException;

import org.json.JSONObject;

import application.Exception.BadCommunicationException;
import application.Exception.CloseException;
import application.Exception.FXMLException;
import application.Exception.InvalidParaException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OperationController {

	@FXML
	private ToggleButton Add;
	
	@FXML
	private ToggleButton Query;
	
	@FXML
	private ToggleButton Delete;
	
	@FXML
	private Button Disconnect;
	
	@FXML
	private GridPane addPane;
	
	@FXML
	private GridPane queryPane;
	
	@FXML
	private GridPane removePane;
	
	@FXML
	private Button addConfirm;
	
	@FXML
	private Button removeConfirm;
	
	@FXML
	private Button queryConfirm;
	
	@FXML
	private VBox resultPane;
	
	@FXML
	private Label result;
	
	@FXML
	private Label description;
	
	@FXML
	private TextField queryKey;
	
	@FXML
	private TextField removeKey;
	
	@FXML
	private TextField addKey;
	
	@FXML
	private TextField addValue;
	
	@FXML
	private Label wrong;
	
	
	private Stage window;
	private DictionaryClient client;
	
	public void display(Stage primaryWindow,DictionaryClient client) throws FXMLException {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Operation.fxml"));
		Parent root = (Parent)loader.load();
		
		OperationController controller = loader.<OperationController>getController();
		controller.removePane.visibleProperty().bind(controller.Delete.selectedProperty());
		controller.queryPane.visibleProperty().bind(controller.Query.selectedProperty());
		controller.addPane.visibleProperty().bind(controller.Add.selectedProperty());
		controller.resultPane.setVisible(false);
		controller.window = primaryWindow;
		
		controller.window.setScene(new Scene(root,600,400));
		controller.client = client;
		}catch(IOException e) {
			throw new FXMLException("FXML parsing error,please contact designer.");
		}
	}
	
	public void clickDisconnect() {
		try {
		ConfirmBoxController box = new ConfirmBoxController();
		boolean answer = box.display("Disconnect", "Are you sure to disconnect?");
		if(answer == true) {
				client.terminate();
				MainController main = new MainController();
				main.display(window);
		}
		}catch(FXMLException e) {
			wrong.setText(e.getMessage());
		}catch(CloseException g) {
			wrong.setText(g.getMessage());
		}
	}
	
	
	public void clickQuery() {
		Query.setSelected(true);
		Add.setSelected(false);
		Delete.setSelected(false);
		resultPane.setVisible(false);
		wrong.setText("");
	}
	
	public void clickQueryConfirm(){
		try {
			wrong.setText("");
			JSONObject request = new JSONObject();
			if(queryKey.getText().length() == 0) {
				throw new InvalidParaException("Key can not be blank");
			}
			request.put("Task","Search");
			request.put("Key", queryKey.getText());
			JSONObject reply = client.request(request);
			queryKey.clear();
			resultPane.setVisible(true);
			result.setText(reply.getString("Result"));
			description.setText(reply.getString("Description"));
			Query.setSelected(false);
		}catch(BadCommunicationException e) {
			wrong.setText(e.getMessage());
		}catch(InvalidParaException y) {
			wrong.setText(y.getMessage());;
		}
	}
	
	public void clickAdd() {
		Query.setSelected(false);
		Add.setSelected(true);
		Delete.setSelected(false);
		resultPane.setVisible(false);
		wrong.setText("");
	}
	
	public void clickAddConfirm() {
		try {
			wrong.setText("");
			JSONObject request = new JSONObject();
			if(addKey.getText().length() == 0 || addValue.getText().length() == 0) {
				throw new InvalidParaException("Key and Meaning can not be blank");
			}
			request.put("Task","Add");
			request.put("Key", addKey.getText());
			request.put("Value", addValue.getText());
			addKey.clear();
			addValue.clear();
			JSONObject reply = client.request(request);
			resultPane.setVisible(true);
			result.setText(reply.getString("Result"));
			description.setText(reply.getString("Description"));
			Add.setSelected(false);
		}catch(BadCommunicationException e) {
			wrong.setText(e.getMessage());
		}catch(InvalidParaException y) {
			wrong.setText(y.getMessage());;
		}
	}
	
	public void clickDelete() {
		Query.setSelected(false);
		Add.setSelected(false);
		Delete.setSelected(true);
		resultPane.setVisible(false);
		wrong.setText("");
	}
	
	public void clickRemoveConfirm(){
		try{
			wrong.setText("");
			JSONObject request = new JSONObject();
			if(removeKey.getText().length() == 0) {
				throw new InvalidParaException("Key can not be blank");
			}
			request.put("Task","Remove");
			request.put("Key", removeKey.getText());
			JSONObject reply = client.request(request);
			removeKey.clear();
			resultPane.setVisible(true);
			result.setText(reply.getString("Result"));
			description.setText(reply.getString("Description"));
			Delete.setSelected(false);
		}catch(BadCommunicationException e) {
			wrong.setText(e.getMessage());
		}catch(InvalidParaException y) {
			wrong.setText(y.getMessage());;
		}
	}
	
	public OperationController() {
	}

}
