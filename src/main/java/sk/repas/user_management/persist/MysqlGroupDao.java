package sk.repas.user_management.persist;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class MysqlGroupDao implements GroupDao {
	
	private JdbcTemplate jdbcTemplate;

	public MysqlGroupDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public List<Group> getAll() {
		String sql = "SELECT id, name, value " +
				"FROM `group` " +
				"LEFT JOIN permission ON `group`.id = permission.group_id " + 
				"ORDER BY id";
		return jdbcTemplate.query(sql, new GroupResultSetExtractor());
		
	}
}
