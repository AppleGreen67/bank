# Микросервисное приложение «Банк» с использованием Spring Boot, интеграций Spring Cloud и паттернов микросервисной архитектуры
### Yandex Practicum. Java-Middle. Sprint 9


## Запуск приложения
0. Установить docker
1. Перейти в корневой каталог репозитория (содержит docker-compose.yaml)
2. Запустить в терминале 'docker compose up keycloak -d'
3. Перейти в админку keycloak http://localhost:8080/ admin/admin
4. Выбрать bank-realm. Перейти в Clients. Клиент transfer-service. Установить Client Secret на вкладке Credentials
5. Прописать созданный Client Secret в .env в корне
6. Запустить в терминале 'docker compose up -d'
3. Для завершения работы приложения используйте команду 'docker compose down'

## Для работы с приложением
1. Перейдите в браузере по адресу http://localhost:8084/account
2. Для работы доступно два пользователя User1/User1 и User2/User2

