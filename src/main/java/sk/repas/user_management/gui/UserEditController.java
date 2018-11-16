package sk.repas.user_management.gui;

import java.util.Map.Entry;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import sk.repas.user_management.persist.User;
import sk.repas.user_management.persist.DaoFactory;
import sk.repas.user_management.persist.Group;

public class UserEditController {
	private UserFxModel userModel;
	private String originalPassword;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private FlowPane groupsFlowPane;

    public UserEditController(User user) {
		userModel = new UserFxModel();
		originalPassword = user.getPassword();
		userModel.setUser(user);
		userModel.setPassword("");
	}
    
    @FXML
    void initialize() {
    	nameTextField.textProperty().bindBidirectional(userModel.nameProperty());
    	emailTextField.textProperty().bindBidirectional(userModel.emailProperty());
    	passwordField.textProperty().bindBidirectional(userModel.passwordProperty());
    	for (Entry<Group, BooleanProperty> entry: userModel.getGroups().entrySet()) {
    		CheckBox checkBox = new CheckBox(entry.getKey().getName());
    		checkBox.selectedProperty().bindBidirectional(entry.getValue());
    		groupsFlowPane.getChildren().add(checkBox);
    	}
    }

    @FXML
    void saveButtonClick(ActionEvent event) {
    	User user = userModel.getUser();
    	if (user.getPassword().isEmpty()) {
    		user.setPassword(originalPassword);
    	} 
    		
    	DaoFactory.INSTANCE.getUserDao().save(user);
    	nameTextField.getScene().getWindow().hide();
    }
}