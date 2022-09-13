package com.dmdev.app.integration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationIT {

    @Test
    public void checkBooleanForTruth() {
        assertTrue(true);
    }

    @Test
    public void checkBooleanForFalse() {
        assertFalse(true);
    }

}
