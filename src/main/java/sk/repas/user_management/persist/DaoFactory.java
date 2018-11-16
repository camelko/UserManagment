package sk.repas.user_management.persist;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;
	
	private GroupDao groupDao;
	private UserDao userDao; 
	private JdbcTemplate jdbcTemplate;
	
	public UserDao getUserDao() {
		if (userDao == null) {
			userDao = new MysqlUserDao(getJdbcTemplate());
		}
		return userDao;
	}
	
	public GroupDao getGroupDao() {
		if (groupDao == null) {
			groupDao = new MysqlGroupDao(getJdbcTemplate());
		}
		return groupDao;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUrl("jdbc:mysql://localhost/user_management?serverTimezone=Europe/Bratislava");
			dataSource.setUser("user_management");
			dataSource.setPassword("elct");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
