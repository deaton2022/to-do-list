package org.example.domain;

import org.example.data.TaskRepository;
import org.example.model.Importance;
import org.example.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {return repository.findAll();}
    public List<Task> findByDueDate(LocalDate date) {return repository.findByDueDate(date);}
    public Task findById(int id){return repository.findById(id);}

    public Result<Task> add(Task task){
        Result<Task> result = validate(task);

        if(!result.isSuccess()){
            return result;
        }
        if (task.getTask_id() != 0) {
            result.addErrorMessage("Task Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        task = repository.add(task);
        result.setPayload(task);

        return result;
    }
    public Result<Task> update(Task task){
        Result<Task> result = validate(task);

        if(!result.isSuccess()){
            return result;
        }
        if(!repository.update(task)){
            String msg = String.format("Update Failed for task with ID %s.",task.getTask_id());
            result.addErrorMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }
    public boolean delete(int taskId){
        return repository.delete(taskId);
    }
    private Result<Task> validate(Task task) {
        Result<Task> result = new Result<>();
        if (task == null) {
            result.addErrorMessage("Task cannot be null.", ResultType.INVALID);
            return result;
        }

        if (task.getName() == null || task.getName().isBlank()) {
            result.addErrorMessage("Name is required", ResultType.INVALID);
        }
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            result.addErrorMessage("Description is required", ResultType.INVALID);
        }
        if (task.getDueDate() == null) {
            result.addErrorMessage("Due Date is required.", ResultType.INVALID);
        }
        if (task.getImportance_id() < 1 || task.getImportance_id() > 3) {
            result.addErrorMessage("Choose the correct importance.", ResultType.NOT_FOUND);
        }
        if(task.getDueDate().isBefore(LocalDate.now())){
            result.addErrorMessage("Due Date must be in the future.",ResultType.INVALID);
        }

        return result;
    }

}
