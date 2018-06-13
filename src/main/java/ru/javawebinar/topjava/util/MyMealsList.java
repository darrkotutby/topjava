package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.util.*;

public class MyMealsList implements List<UserMeal> {
    private List<UserMeal> list = new ArrayList<>();
    private Map<LocalDate, Integer> map = new HashMap<>();

    Map<LocalDate, Integer> getMap() {
        return map;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<UserMeal> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(UserMeal userMeal) {
        AddCalories(userMeal);
        return list.add(userMeal);
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof UserMeal)) {
            return false;
        }
        UserMeal userMeal = (UserMeal) o;
        DeleteCalories(userMeal);
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends UserMeal> c) {
        for (UserMeal userMeal : c) {
            AddCalories(userMeal);
            list.add(userMeal);
        }
        return true;
    }

    private void AddCalories(UserMeal userMeal) {
        LocalDate localDate = userMeal.getDateTime().toLocalDate();
        map.put(localDate, map.getOrDefault(localDate, 0) + userMeal.getCalories());
    }

    @Override
    public boolean addAll(int index, Collection<? extends UserMeal> c) {
        for (UserMeal userMeal : c) {
            AddCalories(userMeal);
            list.add(index++, userMeal);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = list.retainAll(c);
        if (flag) {
            map.clear();
            for (Object o : c) {
                UserMeal userMeal = (UserMeal) o;
                AddCalories(userMeal);
            }
        }
        return flag;
    }

    @Override
    public void clear() {
        map.clear();
        list.clear();
    }

    @Override
    public UserMeal get(int index) {
        return list.get(index);
    }

    @Override
    public UserMeal set(int index, UserMeal element) {
        UserMeal userMeal = list.set(index, element);
        DeleteCalories(userMeal);
        AddCalories(element);
        return userMeal;
    }

    @Override
    public void add(int index, UserMeal element) {
        AddCalories(element);
        list.add(index, element);
    }

    @Override
    public UserMeal remove(int index) {
        UserMeal userMeal = list.get(index);
        DeleteCalories(userMeal);
        list.remove(index);
        return userMeal;
    }

    private void DeleteCalories(UserMeal userMeal) {
        LocalDate localDate = userMeal.getDateTime().toLocalDate();
        map.put(localDate, map.getOrDefault(localDate, 0) - userMeal.getCalories());
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<UserMeal> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<UserMeal> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<UserMeal> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
