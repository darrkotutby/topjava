package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;
    private final SimpleJdbcInsert insertRole;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.insertRole = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_roles");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());

            for(Role role: user.getRoles()) {
                jdbcTemplate.update("INSERT INTO user_roles (user_id, role) values (?, ?)", user.getId(), role.toString());
            }

        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users a, user_roles b WHERE a.id=b.user_id and id=?", new UserRowMapper(), id);
        return DataAccessUtils.singleResult(users);




    }

    @Override
    public User getByEmail(String email) {

        List<User> users = jdbcTemplate.query("SELECT * FROM users a, user_roles b WHERE a.id=b.user_id and a.email=?", new UserRowMapper(), email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users a, user_roles b WHERE a.id=b.user_id ORDER BY name, email", new UserRowMapper());
    }


    class UserRowMapper implements RowMapper<User> {

        Map<Integer, User> users = new HashMap<>();

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = users.get(rs.getInt("id"));
            if(user == null){
                user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"),  Role.valueOf(rs.getString("role")));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                user.setRegistered( rs.getDate("registered"));
                user.setEnabled(rs.getBoolean("enabled"));
                users.put(user.getId(), user);
                return user;
            }
            Role role = Role.valueOf(rs.getString("role"));
            user.getRoles().add(role);
            return user;
            /*return (new BeanPropertyRowMapper<>(User.class)).mapRow(rs,rowNum);*/
        }
    }




   /* public A mapRow(ResultSet rs, int num){
        A a = aMap.get(rs.getInt("id"));
        if(a == null){
            a = new A();
            a.setId(rs.getInt("a_id"));
            a.setName(rs.getString("a_name"));
            aMap.put(a.getId(), a);
        }
        B b = new B();
        b.setId(rs.getInt("b_id");
        b.setName(rs.getString("b_name"));
        a.addB(b);
    }*/


}
