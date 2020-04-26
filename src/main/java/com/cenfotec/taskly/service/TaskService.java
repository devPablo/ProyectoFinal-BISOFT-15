package com.cenfotec.taskly.service;

import com.cenfotec.taskly.domain.Task;

import java.util.List;

public interface TaskService {
    void registerTask(Task task);

    List<Task> findAllByUserId(String userId);

    void deleteByUuid(String uuid);
}
