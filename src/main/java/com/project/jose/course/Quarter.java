package com.project.jose.course;

public enum Quarter {
    FALL("FALL"), WINTER("WINTER"), SPRING("SPRING"), SUMMER("SUMMER");

    public final String value;

    Quarter(String value) {
        this.value = value;
    }

    public static Quarter valueOfIgnoreCase(String name) {
        for (Quarter quarter : values()) {
            if (quarter.name().equalsIgnoreCase(name)) {
                return quarter;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
