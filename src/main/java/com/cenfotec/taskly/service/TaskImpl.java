package com.cenfotec.taskly.service;

import com.cenfotec.taskly.domain.Task;
import com.cenfotec.taskly.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public void registerTask(Task task) {
        taskRepository.save(task);
    }
}
