package com.example.todoApp.service;

import com.example.todoApp.model.Todo;
import com.example.todoApp.model.Todo.Status;
import com.example.todoApp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repo;

    private final Sort sortByCreatedDesc = Sort.by(Sort.Direction.DESC, "createdAt");

    public List<Todo> findAll() {
        return repo.findAll(sortByCreatedDesc);
    }

    public List<Todo> findByStatus(Status status) {
        return repo.findByStatus(status, sortByCreatedDesc);
    }

    public Optional<Todo> findById(Long id) {
        return repo.findById(id);
    }

    public Todo save(Todo todo) {
        return repo.save(todo);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public Todo markAsDone(Long id) {
        Todo t = repo.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        t.setStatus(Status.DONE);
        return repo.save(t);
    }
}

