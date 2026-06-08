# AV.BY — автоматизированное тестирование

Набор UI- и API-автотестов для белорусского автомобильного портала [av.by](https://av.by).

Проект написан на Java с использованием Selenium WebDriver (UI) и Rest Assured (API). Архитектура построена на паттерне **Page Object Model** и разделении тестов, page objects и сервисов.

## Содержание

- [Что тестируется](#что-тестируется)
- [Технологии](#технологии)
- [Структура проекта](#структура-проекта)
- [Архитектура](#архитектура)
- [Требования](#требования)
- [Запуск тестов](#запуск-тестов)
- [Известные ограничения](#известные-ограничения)

## Что тестируется

| Модуль | Описание |
|--------|----------|
| **Главная страница** | Навигация, копирайт в футере, переключение тем (dark / light / auto), сохранение темы после refresh, слайдер входа для неавторизованного пользователя |
| **Авторизация (UI)** | Вход по email/логину, валидация полей, сообщения об ошибках, восстановление пароля |
| **Регистрация** | Заполнение формы и переход к подтверждению email |
| **Проверка VIN** | Ввод VIN, валидация длины (17 символов), переход к pre-report, модальное окно «Где найти VIN», пример отчёта |
| **API авторизации** | `POST https://web-api.av.by/auth/login/sign-in` — негативные сценарии (невалидные, пустые и отсутствующие credentials) |

Всего **25 тест-кейсов**: 20 UI + 5 API.

## Технологии

| Инструмент | Версия | Назначение |
|------------|--------|------------|
| Java | 25 | Язык разработки |
| Maven | — | Сборка и зависимости |
| JUnit 5 | 5.11.0 | Фреймворк тестирования |
| Selenium | 4.21.0 | UI-автоматизация |
| Rest Assured | 5.5.0 | API-тестирование |
| Jackson | 2.18.2 | JSON-сериализация тела запросов |

## Структура проекта

```
av-by/
├── pom.xml
├── README.md
└── src/
    ├── main/java/by/av/
    │   ├── api/
    │   │   ├── AuthApiService.java    # HTTP-запросы к API авторизации
    │   │   └── LoginRequest.java      # DTO тела запроса sign-in
    │   └── ui/
    │       ├── driver/
    │       │   ├── Driver.java        # Singleton WebDriver
    │       │   └── DriverFactory.java # Фабрика браузеров (Chrome, Firefox)
    │       └── page/
    │           ├── BasePage.java      # Общие wait, cookies, JS-хелперы
    │           ├── HomePage.java
    │           ├── LoginPage.java
    │           ├── CheckVinPage.java
    │           └── RegistrationPage.java
    └── test/java/by/av/
        ├── api/
        │   ├── BaseApiTest.java       # Базовый класс API-тестов
        │   └── AuthApiTest.java
        └── ui/
            ├── BaseTest.java          # Базовый класс UI-тестов
            ├── HomePageTest.java
            ├── LoginPageTest.java
            ├── CheckVinPageTest.java
            └── RegistrationPageTest.java
```

## Архитектура

```
Тестовые классы (UI / API)
        ↓ extends
BaseTest / BaseApiTest
        ↓ uses
Page Objects + AuthApiService
        ↓ uses
BasePage + Driver (Singleton) + DriverFactory
        ↓
Selenium WebDriver / Rest Assured  →  av.by / web-api.av.by
```

### Используемые паттерны

- **Page Object Model (POM)** — локаторы и действия страниц инкапсулированы в отдельных классах
- **Singleton Driver** — один экземпляр WebDriver на тест, закрытие в `@AfterEach`
- **Service Layer** — `AuthApiService` отделяет HTTP-логику от тестов
- **Наследование базовых тестов** — общий setup/teardown и assert-хелперы
- **@DisplayName** — читаемые имена тестов в отчётах JUnit

## Требования

- **JDK 25** (или совместимая версия, указанная в `pom.xml`)
- **Maven 3.6+**
- **Google Chrome** (браузер по умолчанию)


## Запуск тестов

### Все тесты

```bash
mvn test
```

### Только UI-тесты

```bash
mvn test -Dtest=by.av.ui.*
```

### Только API-тесты

```bash
mvn test -Dtest=by.av.api.*
```

### Один тестовый класс

```bash
mvn test -Dtest=HomePageTest
mvn test -Dtest=AuthApiTest
```

### Один тест

```bash
mvn test -Dtest=CheckVinPageTest#testNavigateToPreReportPageWhenVinIsValid
```

### Запуск из IDE

Откройте проект как Maven-проект в IntelliJ IDEA / VS Code и запустите нужный тестовый класс или метод через контекстное меню **Run**.

## Известные ограничения

- **CAPTCHA** — при регистрации периодически появляется капча, что может сделать `RegistrationPageTest` нестабильным
- **Зависимость от продакшн-сайта** — изменения вёрстки или API av.by могут потребовать обновления локаторов
- **UI-тесты требуют браузер** — API-тесты можно запускать без GUI
- Тесты авторизации используют **негативные сценарии** — для успешного входа нужны реальные учётные данные, которые в проект не включены

## Автор

Irina
