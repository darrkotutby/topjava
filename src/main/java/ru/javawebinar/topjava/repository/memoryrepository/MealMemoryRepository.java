package ru.javawebinar.topjava.repository.memoryrepository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealMemoryRepository implements Repository<Meal> {

    // private static MealMemoryRepository repository;

    private final Map<Integer, Meal> items = new ConcurrentHashMap<>();
    private final AtomicInteger sequence = new AtomicInteger(0);

    /*  public static MealMemoryRepository getRepository() {
        if (repository == null) {
            synchronized (MealMemoryRepository.class) {
                if (repository == null) {
                    repository = new MealMemoryRepository();
                }
            }
        }
        return repository;
    } */

    @Override
    public Meal add(Meal meal) {
        Meal temp = cloneMeal(meal);
        temp.setId(getSequenceNextVal());
        items.put(temp.getId(), temp);
        return temp;
    }

    @Override
    public void update(Meal meal) {
        items.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        items.remove(id);
    }

    @Override
    public Meal get(int id) {
        return cloneMeal(items.get(id));
    }

    @Override
    public List<Meal> read(Predicate<Meal> p) {
        return items.values().stream().filter(p).map(this::cloneMeal).collect(Collectors.toList());
    }

    public void clear() {
        items.clear();
        sequence.set(0);
    }

    public int count() {
        return items.size();
    }

    private int getSequenceNextVal() {
        return sequence.addAndGet(1);
    }

    private Meal cloneMeal(Meal meal) {
        if (meal == null) {
            return null;
        }
        return new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId());
    }

}
