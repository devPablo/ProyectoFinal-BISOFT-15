package com.cenfotec.taskly.web;

import com.cenfotec.taskly.domain.Task;
import com.cenfotec.taskly.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/tasks")
    public String tasks(Model model) {
        return "tasks";
    }

    @PostMapping("/api/task")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        taskService.registerTask(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

}
