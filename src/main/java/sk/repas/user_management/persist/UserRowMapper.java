package sk.repas.user_management.persist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int cisloRiadku) throws SQLException {
		User novy = new User();
		novy.setId(rs.getLong("id"));
		novy.setName(rs.getString("name"));
		novy.setEmail(rs.getString("email"));
		novy.setPassword(rs.getString("password"));
		Timestamp timestamp = rs.getTimestamp("last_login");
		if (timestamp != null) {
			novy.setLastLogin(timestamp.toLocalDateTime());
		}	
		return novy;
	}

}
