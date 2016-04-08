package ru.javawebinar.topjava.service.mealtests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.HSQLDB, Profiles.JDBC})
public class HsqldbJdbcUserMealServiceTest  extends AbstractUserMealServiceTest {
}
