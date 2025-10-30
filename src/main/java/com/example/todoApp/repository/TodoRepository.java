package com.example.todoApp.repository;

import com.example.todoApp.model.Todo;
import com.example.todoApp.model.Todo.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByStatus(Status status, Sort sort);
}

