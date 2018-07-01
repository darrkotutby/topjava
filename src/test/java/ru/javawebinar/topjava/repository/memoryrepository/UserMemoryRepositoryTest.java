package ru.javawebinar.topjava.repository.memoryrepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.exception.ExistsException;
import ru.javawebinar.topjava.repository.exception.NotExistsException;
import ru.javawebinar.topjava.util.FillUtil;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMemoryRepositoryTest {

    private UserMemoryRepository repository = UserMemoryRepository.getRepository();
    private User user1 = FillUtil.getUserList().get(0);

    @BeforeEach
    void init() {
        FillUtil.getUserList().forEach(user -> repository.add(user));
    }

    @Test
    void count() {
        assertEquals(2, repository.count());
    }

    @Test
    void clear() {
        repository.clear();
        assertEquals(0, repository.count());
    }

    @Test
    void add() {
        User test = new User("Zul", "ZulDarr", 1500);
        User test1 = repository.add(test);
        assertEquals(test, test1);
        assertEquals(3, repository.count());
        assertTrue(repository.exists(test));
        List<User> users = repository.query(m -> m.getLogin().equalsIgnoreCase(test.getLogin()));
        assertEquals(1, users.size());
        assertEquals(test1, users.get(0));
        assertNotEquals(0, test1.getId());
    }

    @Test
    void addExisting() {
        assertThrows(ExistsException.class, () -> repository.add(user1));
        assertEquals(2, repository.count());
    }

    @Test
    void update() {
        User user = repository.query((User m) -> m.getLogin().equals("AWP")).get(0);
        user.setFullName("AWP_TEST");
        user.setCaloriesPerDate(10000);
        repository.update(user);
        assertEquals(2, repository.count());
        List<User> users = repository.query(m -> m.getLogin().equalsIgnoreCase(user.getLogin()));
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
        assertEquals(user.getId(), users.get(0).getId());
        assertEquals(user.getFullName(), users.get(0).getFullName());
        assertEquals(user.getCaloriesPerDate(), users.get(0).getCaloriesPerDate());
    }

    @Test
    void updateNotExisting() {
        User test = new User("Test", "Test Test", 3000);
        assertThrows(NotExistsException.class, () -> repository.update(test));
        assertEquals(2, repository.count());
    }


    @Test
    void delete() {
        repository.delete(user1);
        assertEquals(1, repository.count());
        assertFalse(repository.exists(user1));
    }

    @Test
    void deleteNotExisting() {
        User test = new User("Test", "Test Test", 3000);
        assertThrows(NotExistsException.class, () -> repository.delete(test));
        assertEquals(2, repository.count());
    }

    @Test
    void query() {
        List<User> expectedUsers = Collections.singletonList(user1);
        List<User> queriedUsers = repository.query((User m) -> m.getLogin().equalsIgnoreCase(user1.getLogin()));
        assertEquals(1, queriedUsers.size());
        assertEquals(expectedUsers, queriedUsers);
    }

    @Test
    void getById() {
        User expectedUser = repository.getByPk(user1);
        User queriedUser = repository.getById(expectedUser.getId());
        assertEquals(expectedUser, queriedUser);
        assertEquals(expectedUser.getFullName(), queriedUser.getFullName());
        assertEquals(expectedUser.getCaloriesPerDate(), queriedUser.getCaloriesPerDate());
    }

    @Test
    void getByIdNotFound() {
        assertThrows(NotExistsException.class, () -> repository.getById(-1));
    }

    @Test
    void getByPk() {
        User queriedUser = repository.getByPk(user1);
        assertEquals(user1, queriedUser);
        assertEquals(user1.getFullName(), queriedUser.getFullName());
        assertEquals(user1.getCaloriesPerDate(), queriedUser.getCaloriesPerDate());
    }

    @Test
    void getByPkNotFound() {
        User test = new User("Test", "Test Test", 3000);
        assertThrows(NotExistsException.class, () -> repository.getByPk(test));
    }

    @AfterEach
    void tearDown() {
        repository.clear();
    }
}