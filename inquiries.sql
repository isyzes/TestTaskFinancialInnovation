-- запрос №1
use todo_app;
SELECT USER .*, COUNT(task.user_id) total_tasks
FROM USER
JOIN task ON USER .id = task.user_id
GROUP BY USER.id;

-- запрос №2
use todo_app;
SELECT USER .*
FROM USER
JOIN task ON USER .id = task.user_id
JOIN task_logs ON task.id = task_logs.task_id
GROUP BY USER.id HAVING SUM(task_logs.spent_time) > 100;

CREATE EVENT spent_time
    ON SCHEDULE every 1 SECOND
    DO
		UPDATE task_logs SET spent_time = spent_time + 0.0003125 WHERE task_id IN (SELECT id FROM task WHERE state = false);