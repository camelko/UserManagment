package sk.repas.user_management.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ExampleSceneController {

    @FXML
    private Button tlacButton;

    @FXML
    void initialize() {
    	tlacButton.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Klik z kontroléra");				
			}
		});
    }
}