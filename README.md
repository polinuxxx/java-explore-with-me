# REST-API Explore With Me

## Сервис поиска единомышленников для совместного посещения событий
Позволяет пользователям делиться информацией об интересных событиях, находить компанию для участия в них, а также 
участвовать в событиях других пользователей. Реализовано 2 сервиса:
* **основной сервис** для работы с событиями;
* **сервис статистики** для анализа работы приложения.

## Требования
* jdk 11
* spring
* maven
* git
* docker

## Установка и запуск
### Шаг 1. Скачивание проекта
```
git clone https://github.com/polinuxxx/java-explore-with-me.git
```

### Шаг 2. Сборка проекта
```
cd java-explore-with-me
mvn install
```

### Шаг 3. Запуск проекта

```
docker-compose up
```

## Спецификация API для Swagger
* основной сервис: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html);
* сервис статистики: [http://localhost:9090/swagger-ui.html](http://localhost:9090/swagger-ui.html).

## Технологический стек
![java](https://img.shields.io/badge/java-%23ed8b00.svg?logo=openjdk&logoColor=white&style=flat)
![spring](https://img.shields.io/badge/spring-%236db33f.svg?logo=spring&logoColor=white&style=flat)
![maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=flat&logo=Apache%20Maven&logoColor=white)
![postgres](https://img.shields.io/badge/postgres-%23336791.svg?logo=postgresql&logoColor=white&style=flat)
![postgis](https://img.shields.io/badge/postgis-blue)
![hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=Hibernate&logoColor=white)
![postman](https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white)
