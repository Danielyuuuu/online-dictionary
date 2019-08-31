package server;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.Exception.FXMLException;

public class ConfirmBoxController {
	
	@FXML
	private Button Yes;
	@FXML
	private Button No;
	@FXML
	private Label message;
	
	private boolean answer;
	private Stage window;
	
	public ConfirmBoxController() {
		
	}
	
	public boolean display(String title,String mess) throws FXMLException {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/ConfirmBox.fxml"));
		Parent root = (Parent)loader.load();
		
		ConfirmBoxController controller = loader.<ConfirmBoxController>getController();
		controller.initData(mess);
		
		controller.window = new Stage();
		controller.window.initModality(Modality.APPLICATION_MODAL);
		controller.window.setTitle(title);
		
		
		controller.window.setScene(new Scene(root,400,200));
		controller.window.showAndWait();
		
		return controller.answer;
		}catch(IOException r) {
			throw new FXMLException("FXML parsing error,please contact designer.");
		}
		
	}
	
	public void clickYes() {
		answer = true;
		window.close();
	}
	
	public void clickNo() {
		answer = false;
		window.close();
	}

	public void initData(String mess) {
		message.setText(mess);
	}

}
