package ru.lesson.usercaloriestandards.controller;

import jakarta.validation.Valid;
import ru.lesson.usercaloriestandards.entity.Dish;
import ru.lesson.usercaloriestandards.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lesson.usercaloriestandards.service.UserService;
import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @PostMapping("/create")
    public ResponseEntity<?> createUser( @RequestBody @Valid User user) {
        User createdUser = userService.createUser(user);

        if (createdUser != null) {
            return ResponseEntity.ok(createdUser);
        } else {
            return ResponseEntity.noContent().build();
        }

    }
    @PostMapping("/createDish/{idUser}")
    public ResponseEntity<Dish>createDish(@RequestBody Dish dish, @PathVariable Long idUser) {
        return  userService.createDish(dish,idUser);
    }

    @GetMapping("/report/{localDate}/{idUser}")
    public ResponseEntity<String> ReportForTheDay(@PathVariable LocalDate localDate,@PathVariable  Long idUser) {
    return  userService.ReportForTheDay(localDate,idUser);
    }
    @GetMapping("/history/{localDate}/{idUser}")
    public ResponseEntity<List<Dish>> historyByDayDish(@PathVariable Long idUser, @PathVariable LocalDate localDate) {
        return  userService.historyByDayDish(idUser,localDate);
    }
}
