package com.test.task.controller;

import com.test.task.model.Task;
import com.test.task.model.TaskLogs;
import com.test.task.model.User;
import com.test.task.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {
    private final AppService appService;

    @Autowired
    public MainController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/")
    public String getUserList(Model model) {
        List<User> people = appService.getAllUsers();

        model.addAttribute("people", people);
        model.addAttribute("countUsers", people.size());
        return "userList";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@Valid User user, Model model) {
        appService.addNewUser(user);
        return getUserList(model);
    }

    @GetMapping("getUserTaskList/{user}")
    public String getUserTaskList(@PathVariable(name = "user") Long id, Model model) {
        User user = appService.getUser(id);

        List<Task> tasks = appService.getAllUserTasks(user);
        List<TaskLogs> logs = appService.getAllTasksLogs(tasks);

        model.addAttribute("logs", logs);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        model.addAttribute("countTask", tasks.size());
        return "taskList";
    }

    @PostMapping("addNewTask/{user}")
    public String addNewTask(@PathVariable(name = "user") Long id, @RequestParam(name = "taskName") String taskName, Model model) {
        User user = appService.getUser(id);
        appService.addNewTask(taskName, user);
        return getUserTaskList(id, model);
    }

    @PostMapping("addLogsTask/{task}")
    public String addLogsTask(@PathVariable(name = "task") Long id, @RequestParam(name = "comment") String comment, Model model) {
        Task task = appService.getTask(id);
        appService.addLogsTask(task, comment);
        return getUserTaskList(task.getUser().getId(), model);
    }

    @PostMapping("userEdit/{userEdit}")
    public String userEdit(@Valid User user, @PathVariable(name = "userEdit") Long idMainUser, Model model) {
        appService.userEdit(user, idMainUser);
        return getUserList(model);
    }
}