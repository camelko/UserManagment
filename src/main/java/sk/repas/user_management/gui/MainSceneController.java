package sk.repas.user_management.gui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sk.repas.user_management.persist.User;
import sk.repas.user_management.persist.UserDao;
import sk.repas.user_management.persist.DaoFactory;

public class MainSceneController {

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
//	private UsersManager usersManager = ManagementFactory.INSTANCE.getUsersManager();
	private UserFxModel currentUserModel = new UserFxModel();
	private ObservableList<User> usersModel;
	private User selectedUser;
	
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button addUserButton;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private Button deleteUserButton;
    @FXML
    void initialize() {
    	nameTextField.textProperty().bindBidirectional(
    			currentUserModel.nameProperty());
    	emailTextField.textProperty().bindBidirectional(
    			currentUserModel.emailProperty());
    	addUserButton.disableProperty().bind(
    			currentUserModel.invalidUserProperty());
    	
    	List<User> users = userDao.getAll();
//    	List<User> users = usersManager.getTodayUsers();
    	usersModel = FXCollections.observableList(users);
   	
    	TableColumn<User, Long> idCol = new TableColumn<>("ID");
    	idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    	usersTableView.getColumns().add(idCol);
    	
    	TableColumn<User, String> nameCol = new TableColumn<>("Meno");
    	nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    	usersTableView.getColumns().add(nameCol);

    	TableColumn<User, String> emailCol = new TableColumn<>("E-mail");
    	emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    	usersTableView.getColumns().add(emailCol);

    	TableColumn<User, LocalDateTime> lastLoginCol = 
    			new TableColumn<>("Posledné prihlásenie");
    	lastLoginCol.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
    	lastLoginCol.setCellFactory(
    			new Callback<TableColumn<User,LocalDateTime>, TableCell<User,LocalDateTime>>() {
			
			@Override
			public TableCell<User, LocalDateTime> call(TableColumn<User, LocalDateTime> param) {
				return new TableCell<User, LocalDateTime>() {
					
					@Override
					protected void updateItem(LocalDateTime lastLogin, boolean empty) {
						super.updateItem(lastLogin, empty);
						DateTimeFormatter formatter = 
								DateTimeFormatter.ofPattern("d.M.yyyy HH:mm:ss");
						if (lastLogin != null) {
							setText(formatter.format(lastLogin));
						}
					}
				};
			}
		});
    	usersTableView.getColumns().add(lastLoginCol);	

    	TableColumn<User, LocalDateTime> lastLoginCol2 = 
    			new TableColumn<>("Posledné prihlásenie");
    	lastLoginCol2.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
    	lastLoginCol2.setCellFactory(
    			TextFieldTableCell.forTableColumn(
    					new StringConverter<LocalDateTime>() {

			@Override
			public String toString(LocalDateTime lastLogin) {
				DateTimeFormatter formatter = 
						DateTimeFormatter.ofPattern("d.M.yyyy HH:mm:ss");
				if (lastLogin != null) {
					return formatter.format(lastLogin);
				}
				return "";
			}

			@Override
			public LocalDateTime fromString(String string) {
				return null;
			}
		}));
    	usersTableView.getColumns().add(lastLoginCol2);	
    	usersTableView.setItems(usersModel);
    	
    	usersTableView.getSelectionModel().selectedItemProperty()
    		.addListener(new ChangeListener<User>() {

				@Override
				public void changed(
						ObservableValue<? extends User> observable, 
						User oldValue, 
						User newValue) {
					selectedUser = newValue;
					if (newValue == null) {
						deleteUserButton.setDisable(true);
					} else {
						deleteUserButton.setDisable(false);
					}
				}
		});
    	
    }
    
    @FXML
    void addUserButtonClick(ActionEvent event) {
		userDao.add(currentUserModel.getUser());
		usersModel.setAll(userDao.getAll());   	
    }
    
    @FXML
    void deleteUserButtonClick(ActionEvent event) {
    	userDao.delete(selectedUser.getId());
    	usersModel.setAll(userDao.getAll());  
    }
    
    @FXML
    void usersTableViewMouseClick(MouseEvent event) {
    	try {
			if (event.getClickCount() == 2) {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("UserEdit.fxml"));
				fxmlLoader.setController(new UserEditController(selectedUser));
				Parent rootPane = fxmlLoader.load();
				Scene scene = new Scene(rootPane);
				Stage stage = new Stage();
				stage.setTitle("Editácia používateľa");
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
				// dalsie sa spusti az po zatvoreni okna:
				usersModel.setAll(userDao.getAll());
			}
		} catch (IOException e) {
			System.err.println("Subor UserEdit.fxml sa nenasiel");
		}
    }
}
