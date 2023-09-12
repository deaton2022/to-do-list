package org.example.data;

import org.example.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

class TaskJdbcTempRepositoryTest {
    @Autowired
    TaskRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }
    @Test
    void findAll() {
        List<Task> task = repository.findAll();
        assertNotNull(task);
        assertTrue(task.size() > 0);
    }

    @Test
    void findById() {
    Task t = repository.findById(1);
    assertEquals(1,t.getTask_id());
    assertNotNull(t);
    }

    @Test
    void findByDueDate() {
        List<Task> tasks = repository.findByDueDate(LocalDate.of(2023,9,10));
        assertEquals(1,tasks.size());
        assertEquals(1,tasks.get(0).getTask_id());
    }

    @Test
    void add() {
        Task t = new Task();
        t.setTask_id(0);
        t.setImportance_id(2);
        t.setName("Test");
        t.setDescription("Test Description");
        t.setDueDate(LocalDate.of(2023,12,6));
        t.setAppUserId(1);
        t.setCompleted(true);

        Task result = repository.add(t);
        assertEquals(3,result.getTask_id());
    }

    @Test
    void update() {
        Task t = new Task();
        t.setTask_id(3);
        t.setImportance_id(2);
        t.setName("Test");
        t.setDescription("Test Description");
        t.setDueDate(LocalDate.of(2023,12,6));
        t.setAppUserId(1);
        t.setCompleted(true);

        Task updatedTask = repository.add(t);

        Task t2 = new Task();
        t2.setTask_id(3);
        t2.setImportance_id(2);
        t2.setName("Test Task");
        t2.setDescription("Test Description");
        t2.setDueDate(LocalDate.of(2023,12,6));
        t2.setAppUserId(1);
        t2.setCompleted(true);

        assertEquals("Test Task",t2.getName());
        assertEquals(updatedTask.getTask_id(),t2.getTask_id());
        assertTrue(repository.update(t2));
    }

    @Test
    void delete() {
        Task t = new Task();
        t.setTask_id(4);
        t.setImportance_id(2);
        t.setName("Test");
        t.setDescription("Test Description");
        t.setDueDate(LocalDate.of(2023,12,6));
        t.setAppUserId(1);
        t.setCompleted(true);

        Task result = repository.add(t);
        assertTrue(repository.delete(4));
        assertFalse(repository.delete(4));

    }
}