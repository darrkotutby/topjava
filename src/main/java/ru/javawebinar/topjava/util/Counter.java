package ru.javawebinar.topjava.util;

public class Counter {
    int i;

    public Counter(int i) {
        this.i = i;
    }

    public Counter() {
        this.i = 0;
    }

    public int get() {
        return i;
    }

    public void set(int i) {
        this.i = i;
    }

    public Counter add(int i) {
        this.i += i;
        return this;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "i=" + i +
                '}';
    }
}
