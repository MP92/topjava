package ru.javawebinar.topjava.service.mealtests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.HSQLDB, Profiles.DATA_JPA})
public class HsqldbDataJpaUserMealServiceTest extends AbstractUserMealServiceTest {
}
