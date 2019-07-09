package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(ADMIN_ID);
        assertThat(user).isEqualToIgnoringGivenFields(ADMIN, "registered", "roles", "meals");
        assertThat(user.getMeals()).usingElementComparatorIgnoringFields("user").isEqualTo(ADMIN.getMeals());
    }
}
