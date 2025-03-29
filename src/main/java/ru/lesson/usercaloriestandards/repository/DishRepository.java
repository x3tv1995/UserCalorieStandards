package ru.lesson.usercaloriestandards.repository;

import ru.lesson.usercaloriestandards.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
     List<Dish> findByUserIdAndDate(Long id, LocalDate date);
}
