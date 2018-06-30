package ru.javawebinar.topjava.repository.memoryrepository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealMemoryRepository implements Repository<Meal> {

    private static MealMemoryRepository repository;

    private List<Meal> items = new CopyOnWriteArrayList<>();
    private AtomicInteger sequence = new AtomicInteger(0);

    private MealMemoryRepository() {
    }

    public static MealMemoryRepository getRepository() {
        if (repository == null) {
            synchronized (MealMemoryRepository.class) {
                if (repository == null) {
                    repository = new MealMemoryRepository();
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
    public Meal add(Meal meal) {
        Meal temp = cloneMeal(meal);
        temp.setId(getSequenceNextVal());
        items.add(temp);
        return temp;
    }

    @Override
    public void update(Meal meal) {
        items.remove(meal);
        items.add(meal);
    }

    @Override
    public void delete(Meal meal) {
        items.remove(meal);
    }

    @Override
    public boolean exists(Meal meal) {
        return items.contains(meal);
    }

    @Override
    public List<Meal> query(Predicate<Meal> predicate) {
        return items.stream().filter(predicate).map(this::cloneMeal).collect(Collectors.toList());
    }

    private int getSequenceNextVal() {
        return sequence.addAndGet(1);
    }

    private Meal cloneMeal(Meal meal) {
        return new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId(), meal.getUser());
    }

}
