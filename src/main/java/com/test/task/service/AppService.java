package com.test.task.service;

import com.test.task.model.Task;
import com.test.task.model.TaskLogs;
import com.test.task.model.User;
import com.test.task.repository.TaskLogsRepository;
import com.test.task.repository.TaskRepository;
import com.test.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AppService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskLogsRepository taskLogsRepository;

    @Autowired
    public AppService(UserRepository peopleRepository, TaskRepository taskRepository, TaskLogsRepository taskLogsRepository) {
        this.userRepository = peopleRepository;
        this.taskRepository = taskRepository;
        this.taskLogsRepository = taskLogsRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public List<Task> getAllUserTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public List<TaskLogs> getAllTasksLogs(List<Task> tasks) {
        return taskLogsRepository.findByTaskIn(tasks);
    }

    public void addNewTask(String taskName, User user) {
        Task task = new Task(taskName,user);
        taskRepository.save(task);
    }

    public Task getTask(Long id) {
        return taskRepository.getOne(id);
    }

    public void addLogsTask(Task task, String comment) {
        TaskLogs taskLogs = new TaskLogs(task, comment);
        taskLogsRepository.save(taskLogs);
    }

    public void userEdit(User user, Long idMainUser) {
        User mainUser = userRepository.getOne(idMainUser);
        boolean checkEdit = false;

        if (!StringUtils.isEmpty(user.getFullName()) && !user.getFullName().equals(mainUser.getFullName())) {
            mainUser.setFullName(user.getFullName());
            checkEdit = true;
        }

        if (!StringUtils.isEmpty(user.getLogin()) && !user.getLogin().equals(mainUser.getLogin())) {
            mainUser.setLogin(user.getLogin());
            checkEdit = true;
        }

        if (!StringUtils.isEmpty(user.getPassword()) && !user.getPassword().equals(mainUser.getPassword())) {
            mainUser.setPassword(user.getPassword());
            checkEdit = true;
        }

        if (checkEdit)
            userRepository.save(mainUser);
    }
}
