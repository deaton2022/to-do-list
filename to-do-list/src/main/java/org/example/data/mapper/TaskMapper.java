package org.example.data.mapper;


import org.example.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int i) throws SQLException{
        return new Task(
                rs.getInt("task_id"),
                rs.getInt("importance_id"),
                rs.getString("task_name"),
                rs.getString("task_description"),
                rs.getDate(("due_date")).toLocalDate(),
                rs.getInt("app_user_id"),
                rs.getBoolean("complete")
        );
    }

}
