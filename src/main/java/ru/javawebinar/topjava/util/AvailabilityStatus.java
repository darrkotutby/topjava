package ru.javawebinar.topjava.util;

public class AvailabilityStatus {
    private boolean flag;

    public AvailabilityStatus() {
    }

    public AvailabilityStatus(boolean flag) {
        this.flag = flag;
    }

    public boolean available() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
