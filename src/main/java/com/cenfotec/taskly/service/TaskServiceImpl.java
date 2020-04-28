package com.cenfotec.taskly.service;

import com.cenfotec.taskly.domain.Task;
import com.cenfotec.taskly.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public void registerTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAllByUserId(String userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public Task updateByUuid(String uuid, Task task) {
        Task existing = taskRepository.findByUuid(uuid).get(0);
        Task taskDataFrom = new Task(task.getName(), task.getDescription(), task.isComplete(), task.getDueDate());
        copyNonNullProperties(taskDataFrom, existing);
        return this.taskRepository.save(existing);
    }

    @Override
    public void deleteByUuid(String uuid) {
        taskRepository.deleteTaskByUuid(uuid);
    }

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
