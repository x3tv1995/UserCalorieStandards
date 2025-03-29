package ru.lesson.usercaloriestandards.entity.enumUser;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Goal {
    LOSE_WEIGHT, MAINTAIN_WEIGHT, GAIN_WEIGHT,
    DEFAULT; // Значение по умолчанию

    @JsonCreator
    static Goal fromString(String value) {
        if (value == null || value.isEmpty()) {
            return DEFAULT;
        }
        try {
            return Goal.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT;
        }
    }
}
