package com.example.tasks.service;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.exception.TaskNotFoundException;
import com.example.tasks.model.Task;
import com.example.tasks.model.TaskStatus;
import com.example.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task create(TaskDTO dto) {

        Task task = new Task();

        task.setTitle(dto.getTitle());

        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        return repository.save(task);



    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task findById(Long id) {


        Optional<Task> task =  repository.findById(id);

        if (task.isEmpty()) {

            throw new TaskNotFoundException(id);
        }


        return task.get();



    }

    public Task update(Long id, TaskDTO dto) {

        Task task = findById(id);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        return repository.save(task);

    }

    public Task updateStatus(Long id, TaskStatus newStatus) {
        Task task = findById(id);
        task.setStatus(newStatus);


        return repository.save(task);

    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public List<Task> findByStatus(String statusStr) {

            TaskStatus status = TaskStatus.valueOf(statusStr.toUpperCase());



            return repository.findByStatus(status);
    }
}
