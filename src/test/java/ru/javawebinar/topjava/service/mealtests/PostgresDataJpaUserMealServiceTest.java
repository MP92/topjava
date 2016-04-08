package ru.javawebinar.topjava.service.mealtests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.POSTGRES, Profiles.DATA_JPA})
public class PostgresDataJpaUserMealServiceTest extends AbstractUserMealServiceTest {
}
