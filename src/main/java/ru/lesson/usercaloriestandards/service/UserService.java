package ru.lesson.usercaloriestandards.service;

import ru.lesson.usercaloriestandards.entity.Dish;
import ru.lesson.usercaloriestandards.entity.User;
import ru.lesson.usercaloriestandards.entity.enumUser.ActivityFactor;
import ru.lesson.usercaloriestandards.entity.enumUser.Goal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.lesson.usercaloriestandards.repository.DishRepository;
import ru.lesson.usercaloriestandards.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public User createUser(User user) {

        Optional<User> byUsername = userRepository.findByName(user.getName());
        if (byUsername.isPresent()) {
            return byUsername.get();
        }

        if (user.getGoal() == null) {
            user.setGoal(Goal.MAINTAIN_WEIGHT);
        } else {
            switch (user.getGoal()) {
                case LOSE_WEIGHT:
                    user.setGoal(Goal.LOSE_WEIGHT);
                    break;
                case MAINTAIN_WEIGHT:
                    user.setGoal(Goal.MAINTAIN_WEIGHT);
                    break;
                case GAIN_WEIGHT:
                    user.setGoal(Goal.GAIN_WEIGHT);
                    break;
                default:
                    user.setGoal(Goal.MAINTAIN_WEIGHT);
                    break;

            }

            if (user.getActivityFactor() == null) {
                user.setActivityFactor(ActivityFactor.MEDIUM);
            }
            switch (user.getActivityFactor()) {
                case INACTIVE:
                    user.setActivityFactor(ActivityFactor.INACTIVE);
                    break;
                case MEDIUM:
                    user.setActivityFactor(ActivityFactor.MEDIUM);
                    break;
                case ACTIVE:
                    user.setActivityFactor(ActivityFactor.ACTIVE);
                    break;
                default:
                    user.setActivityFactor(ActivityFactor.MEDIUM);
                    break;

            }
        }

        user.setDishList(user.getDishList());
        return userRepository.save(user);
    }

    public ResponseEntity<Dish> createDish(Dish dish, long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        if (user != null) {
            dish.setUser(user);
        }
        return ResponseEntity.ok(dishRepository.save(dish));
    }

    public ResponseEntity<String> ReportForTheDay(LocalDate localDate, Long idUser) {
        Double v = calculateDailyCalories(idUser);
        List<Dish> dishes = dishRepository.findByUserIdAndDate(idUser, localDate);
        int sumDishes = dishes.stream()
                .mapToInt(Dish::getCaloriesPerServing)
                .sum();
        double difference = v - sumDishes;
        if (sumDishes == 0) {
            return ResponseEntity.ok().body("Вы ничего не съели.");
        }
        if (sumDishes > v) {
            return ResponseEntity.ok().body("Вы съели больше нормы: " + (-difference) + " ккал");
        }
        if (sumDishes < v) {
            return ResponseEntity.ok().body("До нормы калорий вам не хватает: " + difference + " ккал");
        }
        return ResponseEntity.status(404).build();
    }


    public ResponseEntity<List<Dish>> historyByDayDish(Long idUser, LocalDate localDate) {

        List<Dish> byUserIdAndDate = dishRepository.findByUserIdAndDate(idUser, localDate);
        if (!byUserIdAndDate.isEmpty()) {
            return ResponseEntity.ok(byUserIdAndDate);
        }
        return ResponseEntity.status(404).build();
    }

    public Double calculateDailyCalories(Long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        //  базовый уровень метаболизма
        double bmr = 88.362 + (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge());

        // Определяю коэффициент активности
        double activityFactor;
        switch (user.getActivityFactor()) {
            case INACTIVE:
                activityFactor = 1.2;
                break;
            case MEDIUM:
                activityFactor = 1.55;
                break;
            case ACTIVE:
                activityFactor = 1.725;
                break;
            default:
                activityFactor = 1.4;
                break;
        }


        double dailyCalories = bmr * activityFactor;


        double adjustedCalories;
        switch (user.getGoal()) {
            case LOSE_WEIGHT:
                adjustedCalories = dailyCalories * 0.85; // Снижение на 15% для похудения
                break;
            case MAINTAIN_WEIGHT:
                adjustedCalories = dailyCalories;        // Поддержание веса
                break;
            case GAIN_WEIGHT:
                adjustedCalories = dailyCalories * 1.15; // Увеличение на 15% для набора массы
                break;
            default:
                adjustedCalories = dailyCalories;
                break;
        }


        return adjustedCalories = Math.round(adjustedCalories);


    }


}
