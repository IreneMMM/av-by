# AV.BY — автоматизированное тестирование

Набор UI- и API-автотестов для белорусского автомобильного портала [av.by](https://av.by).

Проект написан на Java с использованием Selenium WebDriver (UI) и Rest Assured (API). Архитектура построена на паттерне **Page Object Model**, с разделением page objects, сервисов, тестовых данных и assertion-классов.

## Содержание

- [Что тестируется](#что-тестируется)
- [Технологии](#технологии)
- [Структура проекта](#структура-проекта)
- [Архитектура](#архитектура)
- [Требования](#требования)
- [Запуск тестов](#запуск-тестов)
- [Отчёты Allure](#отчёты-allure)
- [Известные ограничения](#известные-ограничения)

## Что тестируется

| Модуль | Описание |
|--------|----------|
| **Главная страница** | Навигация, копирайт в футере, переключение тем (dark / light / auto), сохранение темы после refresh, переход на проверку VIN, слайдер входа для неавторизованного пользователя |
| **Авторизация (UI)** | Вход по email/логину, валидация полей, сообщения об ошибках, восстановление пароля |
| **Регистрация** | Регистрация по email, валидация полей формы, переход к подтверждению email |
| **Проверка VIN** | Ввод VIN, валидация длины (17 символов), переход к pre-report, модальное окно «Где найти VIN», пример отчёта |
| **Фильтр поиска** | Марка, модель, год, цена, валюта (USD/BYN), комбинированные фильтры; для USD-порога цена в BYN берётся со страницы [av.by/currency](https://av.by/currency) |
| **API авторизации** | негативные сценарии (невалидные, пустые и неполные credentials) |


## Технологии

| Инструмент | Версия | Назначение |
|------------|--------|------------|
| Java | 25 | Язык разработки |
| Maven | — | Сборка и зависимости |
| JUnit 5 | 5.11.0 | Фреймворк тестирования |
| Selenium | 4.21.0 | UI-автоматизация |
| Rest Assured | 5.5.0 | API-тестирование |
| Jackson | 2.18.2 | JSON-сериализация тела запросов (Rest Assured) |
| DataFaker | 2.4.2 | Генерация тестовых данных |
| Log4j2 | 2.24.3 | Логирование |
| Allure | 2.35.2 | Отчёты и шаги 

## Структура проекта

```
av-by/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/by/av/
    │   │   ├── api/
    │   │   │   ├── BaseApiService.java
    │   │   │   └── auth/
    │   │   │       └── AuthApiService.java
    │   │   ├── domain/
    │   │   │   └── User.java              # record для тела sign-in
    │   │   └── ui/
    │   │       ├── data/
    │   │       │   └── TestData.java      # email, имена, пароли, VIN, цены
    │   │       ├── driver/
    │   │       │   ├── Driver.java        # Singleton WebDriver
    │   │       │   └── DriverFactory.java # Chrome, Firefox, Edge
    │   │       ├── page/
    │   │       │   ├── BasePage.java
    │   │       │   ├── HomePage.java
    │   │       │   ├── LoginPage.java
    │   │       │   ├── RegistrationPage.java
    │   │       │   ├── CheckVinPage.java
    │   │       │   └── SearchFilterPage.java
    │   │       └── service/
    │   │           └── CurrencyRateProvider.java
    │   └── resources/
    │       └── log4j2.xml
    └── test/java/by/av/
        ├── api/
        │   ├── BaseApiTest.java
        │   ├── AuthApiTest.java
        │   ├── assertions/
        │   │   └── AuthApiAssertions.java
        │   └── data/
        │       └── AuthApiDataProvider.java
        └── ui/
            ├── BaseTest.java
            ├── HomePageTest.java
            ├── LoginPageTest.java
            ├── RegistrationPageTest.java
            ├── CheckVinPageTest.java
            ├── SearchFilterPageTest.java
            └── assertions/
                └── SearchFilterAssertions.java
```

### Используемые паттерны

- **Page Object Model (POM)** — локаторы и действия страниц в отдельных классах
- **Singleton Driver** — один экземпляр WebDriver на тест, закрытие в `@AfterEach`
- **Service Layer** — `AuthApiService`, `CurrencyRateProvider` отделяют HTTP/UI-логику от тестов
- **Assertions** — проверки вынесены в отдельные классы
- **@Step (Allure)** — шаги в page objects и assertions для отчётов
- **@DisplayName** — читаемые имена тестов в JUnit

## Требования

- **JDK 25** (версия из `pom.xml`)
- **Maven 3.6+**
- **Google Chrome** (браузер по умолчанию) или Firefox / Edge

## Запуск тестов

### Все тесты

```bash
mvn test
```

### Выбор браузера

По умолчанию — Chrome. Другой браузер:

```bash
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

## Отчёты Allure

Результаты сохраняются в `target/allure-results`. Локальный отчёт:

```bash
mvn allure:serve
```


## Автор

Irina Menshova
