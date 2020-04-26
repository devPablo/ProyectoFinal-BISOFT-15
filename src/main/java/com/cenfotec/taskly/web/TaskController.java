package com.cenfotec.taskly.web;

import com.cenfotec.taskly.domain.Task;
import com.cenfotec.taskly.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Task newTask = new Task(task.getUserId(), task.getName(), task.getDescription(), task.getDueDate());
        taskService.registerTask(newTask);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @RequestMapping(path="/api/task/{userId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> getTasksByUserID(@PathVariable("userId") String userId) {
        List<Task> taskList = taskService.findAllByUserId(userId);
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @PutMapping(path="/api/task/{uuid}")
    public ResponseEntity<Task> updateTaskByUUID(@PathVariable("uuid") String uuid, @RequestBody Task task) {
        Task updatedEspecie = taskService.updateByUuid(uuid, task);
        return new ResponseEntity<>(updatedEspecie, HttpStatus.OK);
    }

    @DeleteMapping(path="/api/task/{uuid}")
    public ResponseEntity<String> deleteTaskByUUID(@PathVariable("uuid") String uuid) {
        taskService.deleteByUuid(uuid);
        return new ResponseEntity<>(uuid, HttpStatus.OK);
    }
}
