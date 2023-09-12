package org.example.data;

import org.example.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task findById(int taskId);
    List<Task> findByDueDate(LocalDate date);
    Task add(Task task);
    boolean update(Task task);
    boolean delete(int taskId);

}
