package com.example.tasks.controller;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.exception.GlobalExceptionHandler;
import com.example.tasks.exception.TaskNotFoundException;
import com.example.tasks.model.Task;
import com.example.tasks.model.TaskStatus;
import com.example.tasks.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService service;

    @InjectMocks
    private TaskController controller;

    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private Task task;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        task = new Task();
        task.setId(1L);
        task.setTitle("Tarefa Teste");
        task.setDescription("Descrição");
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void create_deveRetornar201() throws Exception {
        TaskDTO dto = new TaskDTO();
        dto.setTitle("Tarefa Teste");
        dto.setDescription("Descrição");

        when(service.create(any(TaskDTO.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content(mapper.writeValueAsString(dto)))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.title").value("Tarefa Teste"));
    }

    @Test
    void create_comTituloInvalido_deveRetornar400() throws Exception {
        TaskDTO dto = new TaskDTO();
        dto.setTitle("ab");

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content(mapper.writeValueAsString(dto)))

                .andExpect(status().isBadRequest());
    }

    @Test
    void findAll_deveRetornar200() throws Exception {
        when(service.findAll()).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())


                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void findById_deveRetornar200_quandoExiste() throws Exception {
        when(service.findById(1L)).thenReturn(task);

        mockMvc.perform(get("/tasks/1"))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void findById_deveRetornar404_quandoNaoExiste() throws Exception {
        when(service.findById(99L)).thenThrow(new TaskNotFoundException(99L));




        mockMvc.perform(get("/tasks/99"))

                .andExpect(status().isNotFound());
    }

    @Test
    void update_deveRetornar200() throws Exception {

        TaskDTO dto = new TaskDTO();
        dto.setTitle("Título Atualizado");


        dto.setDescription("Nova descrição");

        task.setTitle("Título Atualizado");
        when(service.update(eq(1L), any(TaskDTO.class))).thenReturn(task);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.title").value("Título Atualizado"));

    }

    @Test
    void updateStatus_deveRetornar200() throws Exception {
        task.setStatus(TaskStatus.DONE);
        when(service.updateStatus(1L, TaskStatus.DONE)).thenReturn(task);

        mockMvc.perform(patch("/tasks/1/status")
                        .param("status", "DONE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void delete_deveRetornar204() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/tasks/1"))


                .andExpect(status().isNoContent());

    }

    @Test
    void delete_deveRetornar404_quandoNaoExiste() throws Exception {
        doThrow(new TaskNotFoundException(99L)).when(service).delete(99L);


        mockMvc.perform(delete("/tasks/99"))
                .andExpect(status().isNotFound());

    }

    @Test
    void findByStatus_deveRetornar200() throws Exception {


        when(service.findByStatus("PENDING")).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks/filter").param("status", "PENDING"))

                .andExpect(status().isOk())


                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void findByStatus_comStatusInvalido_deveRetornar400() throws Exception {
        when(service.findByStatus("INVALIDO"))
                .thenThrow(new IllegalArgumentException("Status inválido: INVALIDO"));

        mockMvc.perform(get("/tasks/filter").param("status", "INVALIDO"))

                .andExpect(status().isBadRequest());

    }
}
