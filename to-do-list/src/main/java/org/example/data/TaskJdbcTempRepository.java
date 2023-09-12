package org.example.data;

import org.example.data.mapper.TaskMapper;
import org.example.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
@Repository
public class TaskJdbcTempRepository implements TaskRepository {
    public TaskJdbcTempRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Task> findAll() {
    final String sql ="SELECT task_id, importance_id, task_name, task_description, due_date " +
            "FROM task;";
        return jdbcTemplate.query(sql, new TaskMapper());
    }

    @Override
    public Task findById(int taskId) {
        final String sql ="SELECT task_id, importance_id, task_name, task_description, due_date " +
                "FROM task " +
                "WHERE task_id = ?;";
         Task task = jdbcTemplate.query(sql, new TaskMapper(),taskId).stream().findFirst().orElse(null);
         if(task == null){
             return null;
         }
         return task;
    }

    @Override
    public List<Task> findByDueDate(LocalDate date) {
        final String sql ="SELECT task_id, importance_id, task_name, task_description, due_date " +
                " FROM task " +
                " WHERE due_date = ? ;";
        return jdbcTemplate.query(sql, new TaskMapper(),date);
    }

    @Override
    public Task add(Task task) {
        final String sql = "INSERT INTO task (importance_id, task_name, task_description, due_date, app_user_id, complete) " +
                "VALUES (?,?,?,?,?,?);";
        KeyHolder keyholder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, task.getImportance_id());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setDate(4, Date.valueOf(task.getDueDate()));
            statement.setInt(5,task.getAppUserId());
            statement.setBoolean(6,task.isCompleted());
            return statement;
        }, keyholder);

        if(rowsAffected == 0) {
            return null;
        }
        task.setTask_id(keyholder.getKey().intValue());
        return task;
    }

    @Override
    public boolean update(Task task) {
        final String sql ="UPDATE task SET "  +
                "importance_id = ? ," +
                "task_name = ? , " +
                "task_description = ? ," +
                "due_date = ?," +
                "app_user_id = ?," +
                "complete = ? " +
                "WHERE task_id = ?;";
        return jdbcTemplate.update(sql,
                task.getImportance_id(),
                task.getName(),
                task.getDescription(),
                task.getDueDate(),
                task.getAppUserId(),
                task.isCompleted(),
                task.getTask_id()) > 0;
    }

    @Override
    public boolean delete(int taskId) {
        return jdbcTemplate.update("delete from task where task_id = ?;", taskId) > 0;
    }
}
