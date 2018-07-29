package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(profiles = {"datajpa", "postgres"})
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
    public DataJpaMealServiceTest() {
        Profiles.setRepositoryImplementation(Profiles.DATAJPA);
    }
}