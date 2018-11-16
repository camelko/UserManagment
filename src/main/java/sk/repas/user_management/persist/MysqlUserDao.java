package sk.repas.user_management.persist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.mysql.cj.jdbc.MysqlDataSource;

class MysqlUserDao implements UserDao {

	private JdbcTemplate jdbcTemplate;
	
	public MysqlUserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(User user) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		insert.withTableName("user");
		insert.usingColumns("name","email","password","last_login");
		insert.usingGeneratedKeyColumns("id");
		
		Map<String, Object> values = new HashMap<>();
		values.put("name", user.getName());
		values.put("email", user.getEmail());
		values.put("password", user.getPassword());
		values.put("last_login", user.getLastLogin());
		
		long id = insert.executeAndReturnKey(values).longValue();
		user.setId(id);
		for (Group group: user.getGroups()) {
			String insertSql = "INSERT INTO user_group (user_id,group_id) VALUES (?,?)";
			jdbcTemplate.update(insertSql, user.getId(), group.getId());
		}
	}

	@Override
	public List<User> getAll() {
		String sql = "SELECT id, name, email, password, last_login, group_id " + 
				"FROM `user` " + 
				"LEFT JOIN user_group ON `user`.id = user_group.user_id " + 
				"ORDER BY id";
		List<User> list = jdbcTemplate.query(sql, new UserResulSetExtractor());
		return list;
	}

	@Override
	public User getById(long id) {
		String sql = "SELECT id, name, email, password, last_login "
				+ "FROM `user` WHERE id = " + id;
		User user;
		try {
			user = jdbcTemplate.queryForObject(sql, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			//ak vráti nula riadkov ochytím výnimku a vrátim null
			return null;
		} catch (BadSqlGrammarException e) {
			System.out.println("chyba v SQL");
			return null;
		}
		return user;
	}

	@Override
	public void save(User user) {
		String sql = "UPDATE `user` SET "
				+ "name = ?, email= ?, password= ?, "
				+ "last_login= ? WHERE id = ?";
		jdbcTemplate.update(sql, user.getName(), user.getEmail(),
				user.getPassword(), user.getLastLogin(), user.getId());
		
		String deleteSql = "DELETE FROM user_group WHERE user_id = ?";
		jdbcTemplate.update(deleteSql, user.getId());
		
		for (Group group: user.getGroups()) {
			String insertSql = "INSERT INTO user_group (user_id,group_id) VALUES (?,?)";
			jdbcTemplate.update(insertSql, user.getId(), group.getId());
		}
	}

	@Override
	public void delete(long id) {
		String sql= "DELETE FROM `user` WHERE id = " + id;
		jdbcTemplate.update(sql);
	}

	@Override
	public List<User> getByName(String name) {
		String sql = "SELECT id, name, email, password, last_login "
				+ "FROM `user` WHERE name = ?";
		List<User> list 
			= jdbcTemplate.query(sql, new UserRowMapper(), name);
		return list;
	}

}
