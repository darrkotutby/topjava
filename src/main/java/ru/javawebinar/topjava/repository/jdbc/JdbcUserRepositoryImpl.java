package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            } else {
                namedParameterJdbcTemplate.update(
                        "DELETE FROM user_roles WHERE user_id = :id", parameterSource);
            }
        }
        List<Role> roles = new ArrayList<>(user.getRoles());
        String sql = "INSERT INTO user_roles (user_id, role) values (?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, user.getId());
                ps.setString(2, roles.get(i).toString());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        Map<Integer, User> users = new LinkedHashMap<>();

        jdbcTemplate.query("SELECT * FROM users a, user_roles b WHERE a.id=b.user_id and id=?", new UserRolesRowMapper(users), id);
        return DataAccessUtils.singleResult(users.values());
    }

    @Override
    public User getByEmail(String email) {
        Map<Integer, User> users = new LinkedHashMap<>();
        jdbcTemplate.query("SELECT * FROM users a, user_roles b WHERE a.id=b.user_id and a.email=?", new UserRolesRowMapper(users), email);
        return DataAccessUtils.singleResult(users.values());
    }

    @Override
    public List<User> getAll() {
        Map<Integer, User> users = new LinkedHashMap<>();
        jdbcTemplate.query("SELECT * FROM users a, user_roles b WHERE a.id=b.user_id ORDER BY name, email", new UserRolesRowMapper(users));
        return new ArrayList<>(users.values());
    }

    class UserRolesRowMapper implements RowMapper<Role> {

        Map<Integer, User> users;

        UserRolesRowMapper(Map<Integer, User> users) {
            this.users = users;
        }

        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = users.get(rs.getInt("id"));
            if (user == null) {
                user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), Role.valueOf(rs.getString("role")));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                user.setRegistered(rs.getDate("registered"));
                user.setEnabled(rs.getBoolean("enabled"));
                users.put(user.getId(), user);
            }
            Role role = Role.valueOf(rs.getString("role"));
            user.getRoles().add(role);
            return role;
        }
    }
}
