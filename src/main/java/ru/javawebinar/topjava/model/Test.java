package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test {

   public void testMethod(){
       LocalDateTime localDateTime = LocalDateTime.now();
       LocalDate localDate;
       for (int i=0; i<101; i++) {
           localDate = localDateTime.toLocalDate();
           System.out.println(localDate);
       }

   }

}
