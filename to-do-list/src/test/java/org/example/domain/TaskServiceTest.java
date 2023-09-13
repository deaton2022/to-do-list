package org.example.domain;

import org.example.data.TaskJdbcTempRepository;
import org.example.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    TaskService service;

    @MockBean
    TaskJdbcTempRepository repository;
    @Test
    void shouldAdd() {
    Task task = new Task();
    task.setTask_id(0);
    task.setName("Finish Project");
    task.setDescription("Complete service and controller as well as tests");
    task.setImportance_id(1);
    task.setDueDate(LocalDate.of(2023,9,30));
    task.setAppUserId(1);
    task.setCompleted(true);

    when(repository.add(task)).thenReturn(task);

    Result<Task> result = service.add(task);

    assertTrue(result.isSuccess());
    assertNotNull(result.getPayload());
    }

    @Test
    void shouldNotAddMissingName() {
        Task task = new Task();
        task.setTask_id(0);
        task.setName("");
        task.setDescription("Complete service and controller as well as tests");
        task.setImportance_id(1);
        task.setDueDate(LocalDate.of(2023,9,30));
        task.setAppUserId(1);
        task.setCompleted(true);

        Result<Task> result = service.add(task);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
    @Test
    void shouldNotAddMissingDescription() {
        Task task = new Task();
        task.setTask_id(0);
        task.setName("Finish Project");
        task.setDescription("");
        task.setImportance_id(1);
        task.setDueDate(LocalDate.of(2023,9,30));
        task.setAppUserId(1);
        task.setCompleted(true);

        Result<Task> result = service.add(task);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
    @Test
    void shouldNotAddImportanceAbove3() {
        Task task = new Task();
        task.setTask_id(0);
        task.setName("Finish Project");
        task.setDescription("Complete service and controller as well as tests");
        task.setImportance_id(4);
        task.setDueDate(LocalDate.of(2023,9,30));
        task.setAppUserId(1);
        task.setCompleted(true);

        Result<Task> result = service.add(task);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
    @Test
    void shouldNotAddImportanceBelow1() {
        Task task = new Task();
        task.setTask_id(0);
        task.setName("Finish Project");
        task.setDescription("Complete service and controller as well as tests");
        task.setImportance_id(0);
        task.setDueDate(LocalDate.of(2023,9,30));
        task.setAppUserId(1);
        task.setCompleted(true);

        Result<Task> result = service.add(task);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
    @Test
    void shouldNotAddPastDueDate() {
        Task task = new Task();
        task.setTask_id(0);
        task.setName("Finish Project");
        task.setDescription("Complete service and controller as well as tests");
        task.setImportance_id(1);
        task.setDueDate(LocalDate.of(2023,9,1));
        task.setAppUserId(1);
        task.setCompleted(true);

        Result<Task> result = service.add(task);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() {
        Task task = new Task();
        task.setTask_id(3);
        task.setName("Finish Project");
        task.setDescription("Complete service");
        task.setImportance_id(1);
        task.setDueDate(LocalDate.of(2023,9,30));
        task.setAppUserId(1);
        task.setCompleted(true);

        when(repository.update(task)).thenReturn(true);
        Result<Task> result = service.update(task);

        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotUpdateNonexistentTask() {
        Task task = new Task();
        task.setTask_id(13);
        task.setName("Finish Project");
        task.setDescription("Complete service");
        task.setImportance_id(1);
        task.setDueDate(LocalDate.of(2023,9,30));
        task.setAppUserId(1);
        task.setCompleted(true);

        Result<Task> result = service.update(task);

        assertFalse(result.isSuccess());
    }

}