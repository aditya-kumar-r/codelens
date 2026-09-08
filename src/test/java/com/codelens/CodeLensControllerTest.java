package com.codelens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CodeLensControllerTest {

    @Test
    void controllerShouldBeCreated() {

        GroqService groqService = new GroqService();

        CodeLensController controller =
                new CodeLensController(groqService);

        assertNotNull(controller);
    }
}