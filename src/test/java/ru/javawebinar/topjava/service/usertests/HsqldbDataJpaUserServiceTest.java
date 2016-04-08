package ru.javawebinar.topjava.service.usertests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.HSQLDB, Profiles.DATA_JPA})
public class HsqldbDataJpaUserServiceTest extends AbstractUserServiceTest {
}
