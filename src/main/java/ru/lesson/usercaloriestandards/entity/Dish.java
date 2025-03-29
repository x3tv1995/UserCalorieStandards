package ru.lesson.usercaloriestandards.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;           // Название блюда
    private int caloriesPerServing; // Количество калорий на порцию
    private double protein;        // Белки в граммах на порцию
    private double fat;            // Жиры в граммах на порцию
    private double carbohydrates;  // Углеводы в граммах на порцию
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
