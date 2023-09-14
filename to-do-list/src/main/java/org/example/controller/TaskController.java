package org.example.controller;

import org.example.domain.Result;
import org.example.domain.TaskService;
import org.example.model.Task;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> findAll() {
        return service.findAll();
    }

    @GetMapping("/{date}/")
    public List<Task> findByDueDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    return service.findByDueDate(date);
    }

    @GetMapping("/{taskId}")
    public Task findById(@PathVariable int taskId) {
      return  service.findById(taskId);
    }
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Task task){
        Result<Task> result = service.add(task);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Object> update(@PathVariable int taskId, @RequestBody Task task){
        if(taskId != task.getTask_id()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Result<Task> result = service.update(task);
        if(result.isSuccess()){
            return new ResponseEntity<>("Task Updated.",HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> delete(@PathVariable int taskId){
        if(service.delete(taskId)){
            return new ResponseEntity<>("Task Deleted.",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


