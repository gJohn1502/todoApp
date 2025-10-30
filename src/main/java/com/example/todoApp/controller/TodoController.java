package com.example.todoApp.controller;

import com.example.todoApp.model.Todo;
import com.example.todoApp.model.Todo.Status;
import com.example.todoApp.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService service;

    // View all todos (with optional filter)
    @GetMapping
    public String list(@RequestParam(value = "filter", required = false, defaultValue = "ALL") String filter,
                       Model model) {
        model.addAttribute("filter", filter);

        if ("PENDING".equalsIgnoreCase(filter)) {
            model.addAttribute("todos", service.findByStatus(Status.PENDING));
        } else if ("DONE".equalsIgnoreCase(filter)) {
            model.addAttribute("todos", service.findByStatus(Status.DONE));
        } else {
            model.addAttribute("todos", service.findAll());
        }

        return "todos/list";
    }

    // Show add form
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "todos/form";
    }

    // Create new_todo
    @PostMapping
    public String create(@Valid @ModelAttribute("todo") Todo todo, BindingResult br) {
        if (br.hasErrors()) {
            return "todos/form";
        }
        service.save(todo);
        return "redirect:/todos";
    }

    // Edit form
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Todo todo = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo id"));
        model.addAttribute("todo", todo);
        return "todos/form";
    }

    // Update_todo
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("todo") Todo todo, BindingResult br) {
        if (br.hasErrors()) {
            return "todos/form";
        }
        todo.setId(id);
        service.save(todo);
        return "redirect:/todos";
    }

    // Delete_todo
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/todos";
    }

    // Mark as done
    @PostMapping("/{id}/done")
    public String markAsDone(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        service.markAsDone(id);
        return "redirect:/todos" + (filter != null ? "?filter=" + filter : "");
    }
}

