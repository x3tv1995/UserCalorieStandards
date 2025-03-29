package ru.lesson.usercaloriestandards.entity.enumUser;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActivityFactor {
   INACTIVE,MEDIUM,ACTIVE, DEFAULTS;


   @JsonCreator
   static ActivityFactor fromString(String value) {
      if (value == null || value.isEmpty()) {
         return DEFAULTS;
      }
      try {
         return ActivityFactor.valueOf(value.toUpperCase());
      } catch (IllegalArgumentException e) {
         return DEFAULTS;
      }
   }
}
