package com.example.tasks.controller;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.model.Task;
import com.example.tasks.model.TaskStatus;
import com.example.tasks.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody TaskDTO dto) {


        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));


    }

    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.ok(service.findAll());


    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));

    }

    @PutMapping("/{id}")

    public ResponseEntity<Task> update(@PathVariable Long id, @Valid @RequestBody TaskDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));

    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return ResponseEntity.ok(service.updateStatus(id, status));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();


    }

    @GetMapping("/filter")
    public ResponseEntity<List<Task>> findByStatus(@RequestParam String status) {
        return ResponseEntity.ok(service.findByStatus(status));

    }
}
