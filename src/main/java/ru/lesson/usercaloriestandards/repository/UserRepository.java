package ru.lesson.usercaloriestandards.repository;

import ru.lesson.usercaloriestandards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByName(String username);
   Optional<User> findById(Long id);
}
