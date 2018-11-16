package sk.repas.user_management.persist;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDao implements UserDao {

	private List<User> users = new ArrayList<>();
	private long lastId = 0;
	
	//C-Create
	@Override
	public void add(User user) {
		lastId++;
		user.setId(lastId);
		users.add(user);
	}
	
	//R-Read
	@Override
	public List<User> getAll() {
		return users;
	}
	
	//R-read
	@Override
	public User getById(long id) {
		for(User u : users) {
			if (u.getId() == id) {
				return u;
			}
		}
		return null;
	}
	
	//U-Update
	@Override
	public void save(User user) {
		if (user.getId() == null) {
			add(user);
		} else {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getId() == user.getId()) {
					users.set(i, user);
					return;
				}
			}			
		}
	}
	
	//D-Delete
	@Override
	public void delete(long id) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == id) {
				users.remove(i);
				return;
			}
		}
	}

	@Override
	public List<User> getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
