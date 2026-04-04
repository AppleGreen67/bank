# Микросервисное приложение «Банк» с использованием Spring Boot, интеграций Spring Cloud и паттернов микросервисной архитектуры
### Yandex Practicum. Java-Middle. Sprint 9


## Запуск приложения
0. Установить docker
1. Установить Rancher Desktop
1. Перейти в корневой каталог репозитория (содержит docker-compose.yaml)
2. Запустить в терминале 'docker compose build' для сборки образов
4. В терминале выполнить команду 'helm install ingress-nginx ingress-nginx/ingress-nginx   --namespace ingress-nginx --create-namespace'
5. В терминале выполнить команду 'kubectl apply -f .\accounts\postgres.yaml'
6. В терминале выполнить команду 'helm install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak -f keycloak/keycloak-values.yaml'
7. В терминале выполнить команду 'kubectl apply -f .\keycloak\keycloak-ingress.yaml'
8. Добавьте строчки в etc/hosts:
   * 127.0.0.1 keycloak
   * 127.0.0.1 bank.local
9. Перейти в админку keycloak http://keycloak/ admin/admin
10. Создать bank-realm и сделать его активным
11. Выполнить импорт realm keycloak/bank-realm.json с импортом User, Client, Realm Roles, Client roles. При импорте выбрать режим Overwrite
11. Перейти в Clients. Клиент transfer-service. Установить Client Secret на вкладке Credentials
12. Прописать созданный Client Secret в bank-umbrella-chart/values.yaml
13. Перейти в терминале в папку bank-umbrella-chart. Выполните команду 'helm dependency update'
14. Перейти в корневой каталог репозитория (содержит docker-compose.yaml)
15. Для запуска приложения bank используйте команду 'helm install bank ./bank-umbrella-chart'
16. Для завершения работы приложения используйте команду ''

## Для работы с приложением
1. Перейдите в браузере по адресу http://bank.local/account
2. Для работы доступно два пользователя ivanov/ivanov1 и petrov/petrov1

