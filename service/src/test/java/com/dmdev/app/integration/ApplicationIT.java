package com.dmdev.app.integration;

import com.dmdev.app.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationIT extends AbstractIntegrationTestsClass {

    @Test
    void checkDatabase() {
        try (var session = factory.openSession()) {
            var user = session.get(User.class, 1);
            Assertions.assertThat(user).isNotNull();
        }
    }

}
