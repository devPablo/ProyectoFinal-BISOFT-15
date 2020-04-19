package com.cenfotec.taskly.sqs;

import com.cenfotec.taskly.domain.Task;
import com.cenfotec.taskly.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/api/task")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        taskService.registerTask(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
