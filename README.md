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

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

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

## выполнено не до конца

- Написать тесты для всех публичных методов контроллера ProfileRestController
- Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2) 