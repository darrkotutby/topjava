package ru.javawebinar.topjava.repository.memoryrepository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryRepository implements Repository<Meal> {

    private final Map<Integer, Meal> items = new ConcurrentHashMap<>();
    private final AtomicInteger sequence = new AtomicInteger(0);

    @Override
    public Meal add(Meal meal) {
        Meal temp = cloneMeal(meal);
        temp.setId(getSequenceNextVal());
        items.put(temp.getId(), temp);
        return temp;
    }

    @Override
    public void update(Meal meal) {
        items.put(meal.getId(), cloneMeal(meal));
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
    public List<Meal> getAll() {
        return new ArrayList<>(items.values());
    }

    private int getSequenceNextVal() {
        return sequence.addAndGet(1);
    }

    private Meal cloneMeal(Meal meal) {
        if (meal == null) {
            return null;
        }
        return new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

}
