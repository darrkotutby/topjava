package ru.javawebinar.topjava.repository.memoryrepository;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.Repository;

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
        User temp = cloneUser(user);
        temp.setId(getSequenceNextVal());
        items.add(temp);
        return temp;
    }

    @Override
    public void update(User user) {
        items.remove(user);
        items.add(user);
    }

    @Override
    public void delete(User user) {
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

    private int getSequenceNextVal() {
        return sequence.addAndGet(1);
    }

    private User cloneUser(User user) {
        return new User(user.getLogin(), user.getFullName(), user.getCaloriesPerDate(), user.getId());
    }

}
