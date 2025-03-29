# User Calorie Standards 

## Описание

Этот REST API сервис разработан для отслеживания дневной нормы калорий пользователей и учета съеденных ими блюд. Данные о пользователях, блюдах и приемах пищи хранятся в базе данных PostgreSQL. Сервис позволяет добавлять пользователей, блюда, фиксировать приемы пищи, а также получать отчеты о потреблении калорий за день и историю питания.

## Технологии

*   Java 17+
*   Spring Boot 3.0+
*   Spring Data JPA
*   PostgreSQL
*   Lombok
*   Validation API
*   JUnit 5
*   Gradle-Kotlin

## Функциональность

*   **Управление пользователями:**
    *   Добавление пользователей с параметрами: ID, Имя, Email, Возраст, Вес, Рост, Цель (Похудение, Поддержание, Набор массы).
    *   Автоматический расчет дневной нормы калорий на основе данных пользователя (формула Харриса-Бенедикта).
*   **Управление блюдами:**
    *   Добавление блюд с параметрами: ID, Название, Количество калорий на порцию, Белки/Жиры/Углеводы.
*   **Управление приемами пищи:**
    *   Добавление приемов пищи с указанием пользователя и списка съеденных блюд.
*   **Отчеты:**
    *   Отчет за день с суммой всех калорий и приемов пищи.
    *   Проверка, уложился ли пользователь в свою дневную норму калорий.
    *   История питания по дням.

## Архитектура

Сервис построен по классической трехуровневой архитектуре:

*   **Controller:** Обрабатывает HTTP запросы и взаимодействует с Service-слоем.
*   **Service:** Содержит бизнес-логику приложения.
*   **Repository:** Обеспечивает доступ к данным в базе данных.

## Схема базы данных

*   **users:**
    *   id (BIGINT, PRIMARY KEY)
    *   name (VARCHAR)
    *   email (VARCHAR)
    *   age (INTEGER)
    *   weight (DOUBLE PRECISION)
    *   height (INTEGER)
    *   goal (VARCHAR)
    *   activityFactor (VARCHAR)
    *   List<Dish> dishList

*   **dishes:**
    *   id (BIGINT, PRIMARY KEY)
    *   name (VARCHAR)
    *   calories (INTEGER)
    *   proteins (DOUBLE PRECISION)
    *   fats (DOUBLE PRECISION)
    *   carbohydrates (DOUBLE PRECISION)
    *   User user


## REST API Endpoints

*   **User Management:**
    *   `POST /user/create`:  Создание нового пользователя.

    ```json
    {
      "name": "John Doe",
      "email": "john.doe@example.com",
      "age": 30,
      "weight": 75.0,
      "height": 180,
      "goal": "MAINTAIN_WEIGHT",
      "activityFactor": "MEDIUM"
    }
    ```

    *   `POST /user/createDish/{idUser}`: Создание нового блюда для пользователя с указанным ID.

    ```json
    {
      "name": "Chicken Breast",
      "calories": 200,
      "proteins": 30.0,
      "fats": 5.0,
      "carbohydrates": 0.0
    }
    ```

*   **Report Management:**
    *   `GET /user/report/{localDate}/{idUser}`: Получить отчет за день для пользователя с указанным ID и датой.

    ```
    /user/report/2025-03-06/1
    ```
    *   `GET /user/history/{localDate}/{idUser}`: Получить историю питания по дням для пользователя с указанным ID и датой.
     ```
    /user/history/2025-03-06/1
    ```

## Инструкция по запуску

1.  **Предварительные требования:**
    *   Установленная Java Development Kit (JDK) версии 17 или выше.
    *   Установленный Gradle-Kotlin.
    *   Установленная и настроенная база данных PostgreSQL.

2.  **Клонирование репозитория:**

    ```bash
    git clone [URL вашего репозитория]
    cd [название папки проекта]
    ```

3.  **Настройка подключения к базе данных:**

    *   Откройте файл `src/main/resources/application.properties`.
    *   Замените значения `spring.datasource.url`, `spring.datasource.username` и `spring.datasource.password` на соответствующие вашим настройкам PostgreSQL.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```

4.  **Сборка проекта:**

    ```bash
    mvn clean install
    ```

5.  **Запуск приложения:**

    ```bash
    mvn spring-boot:run
    ```

6.  **Доступ к API:**

    После запуска приложения, API будет доступен по адресу `http://localhost:8080`.

## Юнит-тесты

Для запуска юнит-тестов выполните следующую команду:

```bash
mvn test