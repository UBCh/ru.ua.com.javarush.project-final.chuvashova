## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

## Тестирование

- https://habr.com/ru/articles/259055/

## Список выполненных задач:

- Удалить социальные сети: vk, yandex.
- Вынести чувствительную информацию в отдельный проперти файл (+ application-test.yaml)
- Написать тесты для всех публичных методов контроллера ProfileRestController
- Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload
- Добавить новый функционал: добавления тегов к задаче (REST API + реализация на сервисе)
- Написать Dockerfile для основного сервера
- Написать docker-compose файл для запуска контейнера сервера вместе с БД и nginx.
- Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2)

## выполнено частично

- Написать тесты для всех публичных методов контроллера ProfileRestController