package sk.repas.user_management.gui;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import sk.repas.user_management.persist.DaoFactory;
import sk.repas.user_management.persist.Group;
import sk.repas.user_management.persist.GroupDao;
import sk.repas.user_management.persist.User;

public class UserFxModel {
	private Long id;
	private StringProperty name = new SimpleStringProperty("");
	private StringProperty password = new SimpleStringProperty("");
	private StringProperty email = new SimpleStringProperty("");
	private LocalDateTime lastLogin;
	private BooleanProperty invalidUser = new SimpleBooleanProperty(true);
	private Map<Group, BooleanProperty> groups;

	public UserFxModel() {
		groups = new HashMap<>();
		GroupDao groupDao = DaoFactory.INSTANCE.getGroupDao();
		List<Group> allGroups = groupDao.getAll();
		for (Group g : allGroups) {
			groups.put(g, new SimpleBooleanProperty(false));
		}

		ChangeListener<String> listener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				setValidity();
			}
		};
		name.addListener(listener);
		email.addListener(listener);
	}

	public void setUser(User user) {
		setId(user.getId());
		setName(user.getName());
		setPassword(user.getPassword());
		setEmail(user.getEmail());
		setLastLogin(user.getLastLogin());
		for(Entry<Group, BooleanProperty> entry: groups.entrySet()) {
			if (user.isGroupMember(entry.getKey())) {
				entry.getValue().set(true);
			} else {
				entry.getValue().set(false);
			}
		}
	}

	
	public User getUser() {
		User user = new User();
		user.setId(getId());
		user.setName(getName());
		user.setPassword(getPassword());
		user.setEmail(getEmail());
		user.setLastLogin(getLastLogin());
		for(Entry<Group, BooleanProperty> entry: groups.entrySet()) {
			if (entry.getValue().get()) {
				user.getGroups().add(entry.getKey());
				
			}
		}
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public StringProperty passwordProperty() {
		return this.password;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public StringProperty emailProperty() {
		return email;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public BooleanProperty invalidUserProperty() {
		return invalidUser;
	}

	private void setValidity() {
		if (getName() == null || getName().trim().isEmpty()) {
			invalidUser.set(true);
			return;
		}
		if (getEmail() == null || getEmail().trim().isEmpty()) {
			invalidUser.set(true);
			return;
		}
		invalidUser.set(false);

	}

	public Map<Group, BooleanProperty> getGroups() {
		return groups;
	}
}
