package ru.javawebinar.topjava.repository.memoryrepository;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.repository.exception.ExistsException;
import ru.javawebinar.topjava.repository.exception.NotExistsException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserMemoryRepository implements Repository<User> {

    private static UserMemoryRepository repository;

    private List<User> items = new CopyOnWriteArrayList<>();
    private AtomicInteger sequence = new AtomicInteger(0);

    private UserMemoryRepository() {
    }

    public static UserMemoryRepository getRepository() {
        if (repository == null) {
            synchronized (UserMemoryRepository.class) {
                if (repository == null) {
                    repository = new UserMemoryRepository();
                }
            }
        }
        return repository;
    }

    @Override
    public int count() {
        return items.size();
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public User add(User user) {

        if (exists(user)) {
            throw new ExistsException("User " + user.getFullName() + "already exists");
        }

        User temp = cloneUser(user);
        temp.setId(getSequenceNextVal());
        items.add(temp);
        return temp;
    }

    @Override
    public void update(User user) {
        delete(user);
        items.add(user);
    }

    @Override
    public void delete(User user) {
        if (!exists(user)) {
            throw new NotExistsException("User " + user.getFullName() + "does not exist");
        }
        items.remove(user);
    }

    @Override
    public boolean exists(User user) {
        return items.contains(user);
    }

    @Override
    public List<User> query(Predicate<User> predicate) {
        return items.stream().filter(predicate).map(this::cloneUser).collect(Collectors.toList());
    }

    @Override
    public User getById(int id) {
        List<User> users = query(m -> m.getId() == id);
        if (users.size() > 0) {
            return users.get(0);
        }
        throw new NotExistsException("User with id=" + id + " does not exist");
    }

    @Override
    public User getByPk(User user) {

        List<User> users = repository.query(u -> u.getLogin().equals(user.getLogin()));
        if (users.size() > 0) {
            return users.get(0);
        }
        throw new NotExistsException("User with login=" + user.getLogin() + " does not exist");
    }

    private int getSequenceNextVal() {
        return sequence.addAndGet(1);
    }

    private User cloneUser(User user) {
        return new User(user.getLogin(), user.getFullName(), user.getCaloriesPerDate(), user.getId());
    }

}
