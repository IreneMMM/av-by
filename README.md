# AV.BY — автоматизированное тестирование

Набор UI- и API-автотестов для белорусского автомобильного портала [av.by](https://av.by).

Проект написан на Java с использованием Selenium WebDriver (UI) и Rest Assured (API). Архитектура построена на паттерне **Page Object Model**, с разделением page objects, сервисов, тестовых данных и assertion-классов.

## Содержание

- [Что тестируется](#что-тестируется)
- [Технологии](#технологии)
- [Структура проекта](#структура-проекта)
- [Архитектура](#архитектура)
- [Требования](#требования)
- [Учётные данные](#учётные-данные)
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
| Java | 21 | Язык разработки |
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

- **JDK 21** (версия из `pom.xml`)
- **Maven 3.6+**
- **Google Chrome** (браузер по умолчанию) или Firefox / Edge

## Учётные данные

Тест `LoginPageTest.testLoginWithValidCredentials` читает логин и пароль через `EnvConfig`:
сначала из **переменных окружения**, затем из файла **`.env`** (локально).

| Переменная | Назначение |
|------------|------------|
| `VALID_LOGIN` | Email или логин зарегистрированного пользователя av.by |
| `VALID_PASSWORD` | Пароль этого пользователя |

### Локально

1. Скопируйте шаблон:
   ```bash
   cp .env.example .env
   ```
   На Windows (PowerShell):
   ```powershell
   Copy-Item .env.example .env
   ```
2. Откройте `.env` и подставьте реальные значения.
3. Файл `.env` в git не коммитится (см. `.gitignore`).

### Jenkins

На CI файла `.env` нет — секреты хранятся в **Jenkins Credentials** и пробрасываются как переменные окружения с теми же именами: `VALID_LOGIN`, `VALID_PASSWORD`.

#### Шаг 1. Создать credentials

1. **Manage Jenkins** → **Credentials**.
2. Выберите домен (обычно `(global)`).
3. **Add Credentials** → тип **Secret text**.
4. Создайте две записи:

   | ID (важно для pipeline) | Secret | Description |
   |-------------------------|--------|-------------|
   | `av-by-valid-login` | email пользователя | AV.BY test login |
   | `av-by-valid-password` | пароль | AV.BY test password |

   ID можно задать свой — тогда измените его в `Jenkinsfile`.

#### Шаг 2а. Pipeline job (рекомендуется)

1. **New Item** → имя, например `av-by-tests` → тип **Pipeline** → OK.
2. В **Pipeline** → **Definition**: *Pipeline script from SCM* (если Jenkinsfile в репозитории)  
   или вставьте скрипт из [`Jenkinsfile.example`](Jenkinsfile.example).
3. Убедитесь, что в Jenkins настроены tool aliases `jdk-21` и `Maven-3.9`  
   (**Manage Jenkins** → **Tools**), либо замените блок `tools` на явные пути.
4. Сохраните и запустите **Build Now**.

Минимальный фрагмент pipeline:

```groovy
environment {
    VALID_LOGIN    = credentials('av-by-valid-login')
    VALID_PASSWORD = credentials('av-by-valid-password')
}

stages {
    stage('Test') {
        steps {
            script {
                if (isUnix()) {
                    sh 'mvn test'
                } else {
                    bat 'mvn test'
                }
            }
        }
    }
}
```

Jenkins подставит значения credentials в переменные окружения агента. `EnvConfig` подхватит их через `System.getenv()`.

#### Шаг 2б. Freestyle job

1. **New Item** → **Freestyle project**.
2. **Build Environment** → включить **Use secret text(s) or file(s)** (плагин *Credentials Binding*).
3. **Add** → **Secret text**:
   - Variable: `VALID_LOGIN` → выбрать credential с логином
   - Variable: `VALID_PASSWORD` → выбрать credential с паролем
4. **Build** → **Invoke top-level Maven targets**: `test`
5. Сохранить и собрать.

#### Проверка

В логе сборки **не должно** быть строк с паролем. Если тест падает с:

```
Missing required value: VALID_LOGIN. Set environment variable or add it to .env file.
```

— credentials не привязаны или ID в pipeline не совпадает с ID в Jenkins.

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


## Известные ограничения

- **reCAPTCHA при регистрации**: сценарий регистрации может блокироваться капчей. В этом случае тест пропускается через `Assumptions`.
- **Rate limit при логине**: если пароль вводится слишком часто с одной учёткой, сайт может вернуть ошибку “Слишком часто вводится неверный пароль…”. Тогда тест логина может пропускаться.
- **Случайные параметры фильтра**: тест `testCombinedFiltersWithBynCurrency` использует random brand/model/year. Если после `MAX_COMBINED_FILTER_ATTEMPTS` объявлений не нашлось, тест помечается как `SKIPPED`, чтобы не падать на “неудачном” наборе данных.
- **Flaky UI из-за динамики**: av.by меняется асинхронно (drawer, dropdown, loader). Для стабилизации используются явные ожидания и `jsClick`, но полностью исключить флаки невозможно при работе с реальным сайтом.

## Автор

Irina Menshova
