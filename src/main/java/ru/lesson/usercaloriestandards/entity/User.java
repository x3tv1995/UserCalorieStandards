package ru.lesson.usercaloriestandards.entity;

import lombok.EqualsAndHashCode;
import ru.lesson.usercaloriestandards.entity.enumUser.ActivityFactor;
import ru.lesson.usercaloriestandards.entity.enumUser.Goal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    @Column(unique = true)
    @NotNull(message = "Поле не может быть null")
    @NotBlank(message = "Поле не может быть пустым")
    private String name;


    @Email(message = "Почта введена неверно")
    @NotNull(message = "Поле не может быть null")
    @NotBlank(message = "Поле не может быть пустым")
    private String email;

    @Min(value = 10, message = "Возраст должен быть не менее 10 лет")
    @Max(value = 100, message = "Возраст должен быть не более 100 лет")
    private int age;

    @Min(value = 30, message = "Вес должен быть не менее 30 кг")
    @Max(value = 300, message = "Вес должен быть не более 300 кг")

    private int weight;

    @Min(value = 120, message = "Рост должен быть не менее 120 см")
    @Max(value = 220, message = "Рост должен быть не более 220 см")
    private int height;

    @Enumerated(EnumType.STRING)
    private Goal goal;


    @Enumerated(EnumType.STRING)
    private ActivityFactor activityFactor;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dishList = new ArrayList<>();
}
