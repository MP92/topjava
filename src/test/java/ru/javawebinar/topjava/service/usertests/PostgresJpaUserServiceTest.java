package ru.javawebinar.topjava.service.usertests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JPA})
public class PostgresJpaUserServiceTest extends AbstractUserServiceTest {

}
