package tech.reinders.podam.junit.model;

public class SimpleModel {
    private String stringProperty;
    private Double doubleProperty;
    private Integer integerProperty;
    private Boolean booleanProperty;

    public String getStringProperty() {
        return this.stringProperty;
    }

    public void setStringProperty(final String aStringProperty) {
        this.stringProperty = aStringProperty;
    }

    public Double getDoubleProperty() {
        return this.doubleProperty;
    }

    public void setDoubleProperty(final Double aDoubleProperty) {
        this.doubleProperty = aDoubleProperty;
    }

    public Integer getIntegerProperty() {
        return this.integerProperty;
    }

    public void setIntegerProperty(final Integer aIntegerProperty) {
        this.integerProperty = aIntegerProperty;
    }

    public Boolean getBooleanProperty() {
        return this.booleanProperty;
    }

    public void setBooleanProperty(final Boolean aBooleanProperty) {
        this.booleanProperty = aBooleanProperty;
    }
}
