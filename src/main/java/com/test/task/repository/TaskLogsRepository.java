package com.test.task.repository;

import com.test.task.model.Task;
import com.test.task.model.TaskLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskLogsRepository extends JpaRepository<TaskLogs, Long> {
    List<TaskLogs> findByTaskIn(List<Task> tasks);
}
