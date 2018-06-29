package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test1 {

    public void testMethod(){
        LocalDateTime localDateTime = LocalDateTime.now();
        for (int i=0; i<101; i++) {
            LocalDate localDate = localDateTime.toLocalDate();
            System.out.println(localDate);
        }
    }
}
