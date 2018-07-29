package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(profiles = {"jpa", "postgres"})
public class JpaMealServiceTest extends AbstractMealServiceTest {
    public JpaMealServiceTest() {
        Profiles.setRepositoryImplementation(Profiles.JPA);
    }
}