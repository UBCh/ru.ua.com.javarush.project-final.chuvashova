--changeset CREATE TABLE


insert into USER_BELONG (id, OBJECT_ID, OBJECT_TYPE, USER_ID, USER_TYPE_CODE, STARTPOINT, ENDPOINT)
values (1, 1, 2, 2, 'task_developer', '2023-06-14 08:35:10', '2023-06-14 08:55:00'),
       (2, 1, 2, 2, 'task_reviewer', '2023-06-14 09:35:10', null),
       (3, 1, 2, 1, 'task_developer', '2023-06-12 11:40:00', '2023-06-12 12:35:00'),
       (4, 1, 2, 1, 'task_developer', '2023-06-13 12:35:00', null),
       (5, 1, 2, 1, 'task_tester', '2023-06-14 15:20:00', null),
       (6, 2, 2, 2, 'task_developer', '2023-06-08 07:10:00', null),
       (7, 2, 2, 1, 'task_developer', '2023-06-09 14:48:00', null),
       (8, 2, 2, 1, 'task_tester', '2023-06-10 16:37:00', null);
