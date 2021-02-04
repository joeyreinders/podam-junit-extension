package tech.reinders.podam.junit.test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.reinders.podam.junit.PodamExtension;
import tech.reinders.podam.junit.PodamSupplier;
import tech.reinders.podam.junit.annotation.Podam;
import tech.reinders.podam.junit.annotation.PodamProvider;
import tech.reinders.podam.junit.model.SimpleModel;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PodamExtension.class)
@PodamProvider(CustomPodamProviderTest.CustomPodamProvider.class)
public class CustomPodamProviderTest {
    private static boolean customFactoryused = false;
    @Podam
    private SimpleModel simpleModel;

    @Test
    void doTest() {
        assertNotNull(this.simpleModel);
        assertFalse(this.simpleModel.getStringProperty() == null || this.simpleModel.getStringProperty().isEmpty());
        assertNotNull(this.simpleModel.getDoubleProperty());
        assertNotNull(this.simpleModel.getIntegerProperty());

        assertTrue(customFactoryused);
    }

    @AfterEach
    void afterEach() {
        customFactoryused = false;
    }

    static class CustomPodamProvider implements PodamSupplier {
        @NotNull
        @Override
        public PodamFactory getPodamFactory() {
            CustomPodamProviderTest.customFactoryused = true;
            return new PodamFactoryImpl();
        }
    }
}
