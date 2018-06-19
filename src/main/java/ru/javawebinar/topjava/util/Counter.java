package ru.javawebinar.topjava.util;

public class Counter {
    int i;

    public Counter(int i) {
        this.i = i;
    }

    public Counter() {
        this.i = 0;
    }

    public Boolean get() {
        return i < 0;
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
