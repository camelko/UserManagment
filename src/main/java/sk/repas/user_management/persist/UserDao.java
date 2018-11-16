package sk.repas.user_management.persist;

import java.util.List;

public interface UserDao {

	//C-Create
	void add(User user);

	//R-Read
	List<User> getAll();
	
	List<User> getByName(String name);

	//R-read
	User getById(long id);

	//U-Update
	void save(User user);

	//D-Delete
	void delete(long id);

}