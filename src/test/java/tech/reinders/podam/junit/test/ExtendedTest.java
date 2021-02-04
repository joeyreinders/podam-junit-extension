package tech.reinders.podam.junit.test;

import org.junit.jupiter.api.Test;
import tech.reinders.podam.junit.annotation.Podam;
import tech.reinders.podam.junit.model.SimpleModel;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendedTest extends SimpleTest {
    @Podam
    private final SimpleModel finalNullField = null;
    private final SimpleModel copyOfObjectToBeFilled = new SimpleModel();
    @Podam
    private final SimpleModel objectToBeFilled = copyOfObjectToBeFilled;
    @Podam
    private SimpleModel childSimpleModel;
    @Podam
    private SimpleModel simpleModel;

    @Test
    void testChildAndParent() {
        assertNotNull(this.childSimpleModel);
        assertFalse(this.childSimpleModel.getStringProperty() == null || this.childSimpleModel.getStringProperty().isEmpty());
        assertNotNull(this.childSimpleModel.getDoubleProperty());
        assertNotNull(this.childSimpleModel.getIntegerProperty());

        assertNotNull(this.getSimpleModel());
        assertFalse(this.getSimpleModel().getStringProperty() == null || this.getSimpleModel().getStringProperty().isEmpty());
        assertNotNull(this.getSimpleModel().getDoubleProperty());
        assertNotNull(this.getSimpleModel().getIntegerProperty());
    }

    @Test
    void testFieldShadowing() {
        //Testing shadow field
        assertNotNull(this.simpleModel);
        assertFalse(this.simpleModel.getStringProperty() == null || this.simpleModel.getStringProperty().isEmpty());
        assertNotNull(this.simpleModel.getDoubleProperty());
        assertNotNull(this.simpleModel.getIntegerProperty());
    }

    @Test
    void testFinalNullField() {
        //finalNullField
        assertNotNull(this.finalNullField);
        assertFalse(this.finalNullField.getStringProperty() == null || this.finalNullField.getStringProperty().isEmpty());
        assertNotNull(this.finalNullField.getDoubleProperty());
        assertNotNull(this.finalNullField.getIntegerProperty());
    }

    @Test
    void testObjectToBeFilled() {
        assertNotNull(this.objectToBeFilled);
        assertFalse(this.objectToBeFilled.getStringProperty() == null || this.objectToBeFilled.getStringProperty().isEmpty());
        assertNotNull(this.objectToBeFilled.getDoubleProperty());
        assertNotNull(this.objectToBeFilled.getIntegerProperty());

        //Instance testing
        assertEquals(copyOfObjectToBeFilled, objectToBeFilled);
    }
}
