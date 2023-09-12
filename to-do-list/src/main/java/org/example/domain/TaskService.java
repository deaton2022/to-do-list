package org.example.domain;

import org.example.data.TaskRepository;
import org.example.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {return repository.findAll();}
    public List<Task>
}
