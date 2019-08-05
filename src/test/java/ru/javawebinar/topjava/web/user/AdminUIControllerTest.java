package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.*;

public class AdminUIControllerTest extends AbstractControllerTest {

    private static final String AJAX_URL = AdminUIController.AJAX_URL + '/';

    @Test
    void setEnabled() throws Exception {
        User expected = new User(USER);
        expected.setEnabled(false);
        mockMvc.perform(MockMvcRequestBuilders.get(AJAX_URL + "enable")
                .param("id", String.valueOf(USER_ID))
                .param("enabled", "false"))
                .andExpect(status().isNoContent());

        assertMatch(userService.get(USER_ID), expected);
    }
}
