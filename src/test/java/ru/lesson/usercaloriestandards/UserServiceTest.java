package ru.lesson.usercaloriestandards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.lesson.usercaloriestandards.entity.Dish;
import ru.lesson.usercaloriestandards.entity.User;

import ru.lesson.usercaloriestandards.repository.DishRepository;
import ru.lesson.usercaloriestandards.repository.UserRepository;
import ru.lesson.usercaloriestandards.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private DishRepository dishRepository;


    @Test
    public void createUser_NewUser_ShouldSaveUserWithDefaults() {
        User user = new User();
        String userName = "TestUser";
        user.setName(userName);
        user.setAge(20);
        user.setEmail("test@test.com");
        user.setWeight(111);
        user.setHeight(200);

        userService.createUser(user);

        Optional<User> savedUser = userRepository.findByName(userName);
        assertNotNull(savedUser);
        assertEquals(userName, savedUser.get().getName());
    }

    @Test
    public void testCreateDish() {
        // 1. Подготовка данных
        User user = new User();
        user.setName("TestUser2323");
        user.setAge(20);
        user.setEmail("test@test.com");
        user.setWeight(111);
        user.setHeight(200);
        user = userRepository.save(user);

        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setCaloriesPerServing(200);
        dish.setProtein(10.0);
        dish.setFat(5.0);
        dish.setCarbohydrates(25.0);
        dish.setDate(LocalDate.now());


        ResponseEntity<Dish> response = userService.createDish(dish, user.getId());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Dish createdDish = response.getBody();
        assertNotNull(createdDish.getId());
        assertEquals("Test Dish", createdDish.getName());


        Optional<Dish> retrievedDish = dishRepository.findById(createdDish.getId());
        assertTrue(retrievedDish.isPresent());
        assertEquals(user.getId(), retrievedDish.get().getUser().getId());

        dishRepository.deleteById(createdDish.getId());
        userRepository.deleteById(user.getId());
    }
    @Test
    public void testReportForTheDay_ExactCalories() {
        Dish dish1 = new Dish();
        dish1.setName("Test Dish");
        dish1.setCaloriesPerServing(200);
        dish1.setProtein(10.0);
        dish1.setFat(5.0);
        dish1.setCarbohydrates(25.0);
        dish1.setDate(LocalDate.now());

        Dish dish2 = new Dish();
        dish2.setName("Test Dish");
        dish2.setCaloriesPerServing(200);
        dish2.setProtein(10.0);
        dish2.setFat(5.0);
        dish2.setCarbohydrates(25.0);
        dish2.setDate(LocalDate.now());


        List<Dish> testDishes = new ArrayList<>();
        testDishes.add(dish1);
        testDishes.add(dish2);
        LocalDate testDate = LocalDate.now();
        Long testUserId = 1L;

        ResponseEntity<String> response = userService.ReportForTheDay(testDate, testUserId);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


}
