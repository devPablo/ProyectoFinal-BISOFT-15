package com.cenfotec.taskly.repository;

import com.cenfotec.taskly.domain.Task;
import com.microsoft.azure.spring.data.cosmosdb.repository.CosmosRepository;

import java.util.List;

public interface TaskRepository extends CosmosRepository<Task, String> {

    List<Task> findAllByUserId(String userId);

    List<Task> findByUuid(String uuid);

    void deleteTaskByUuid(String uuid);
}
