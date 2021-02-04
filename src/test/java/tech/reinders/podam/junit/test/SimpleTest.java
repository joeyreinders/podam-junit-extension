package tech.reinders.podam.junit.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.reinders.podam.junit.PodamExtension;
import tech.reinders.podam.junit.annotation.Podam;
import tech.reinders.podam.junit.model.SimpleModel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(PodamExtension.class)
public class SimpleTest {
    @Podam
    private SimpleModel simpleModel;

    @Test
    void doTest() {
        assertNotNull(this.simpleModel);
        assertFalse(this.simpleModel.getStringProperty() == null || this.simpleModel.getStringProperty().isEmpty());
        assertNotNull(this.simpleModel.getDoubleProperty());
        assertNotNull(this.simpleModel.getIntegerProperty());
    }

    SimpleModel getSimpleModel() {
        return this.simpleModel;
    }
}
