package com.example.tasks.service;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.exception.TaskNotFoundException;
import com.example.tasks.model.Task;
import com.example.tasks.model.TaskStatus;
import com.example.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    private Task task;
    private TaskDTO dto;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Tarefa Teste");
        task.setDescription("Descrição");
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());

        dto = new TaskDTO();
        dto.setTitle("Tarefa Teste");
        dto.setDescription("Descrição");
    }

    @Test
    void create_deveRetornarTarefaCriada() {
        when(repository.save(any(Task.class))).thenReturn(task);

        Task result = service.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Tarefa Teste");

        assertThat(result.getStatus()).isEqualTo(TaskStatus.PENDING);


        verify(repository, times(1)).save(any(Task.class));


    }

    @Test
    void create_comStatusDefinido_deveUsarStatusInformado() {
        dto.setStatus(TaskStatus.IN_PROGRESS);
        task.setStatus(TaskStatus.IN_PROGRESS);
        when(repository.save(any(Task.class))).thenReturn(task);

        Task result = service.create(dto);

        assertThat(result.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

    @Test
    void findAll_deveRetornarListaDeTarefas() {
        when(repository.findAll()).thenReturn(List.of(task));

        List<Task> result = service.findAll();

        assertThat(result).hasSize(1);
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_deveRetornarTarefa_quandoExiste() {


        when(repository.findById(1L)).thenReturn(Optional.of(task));


        Task result = service.findById(1L);

        assertThat(result).isNotNull();

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findById_deveLancarExcecao_quandoNaoExiste() {

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("99");


    }

    @Test
    void update_deveAtualizarTarefa_quandoExiste() {
        dto.setTitle("Título Atualizado");
        task.setTitle("Título Atualizado");

        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.save(any(Task.class))).thenReturn(task);

        Task result = service.update(1L, dto);

        assertThat(result.getTitle()).isEqualTo("Título Atualizado");
        verify(repository, times(1)).save(any(Task.class));



    }

    @Test
    void update_deveLancarExcecao_quandoTarefaNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, dto))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void updateStatus_deveAtualizarStatus() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        task.setStatus(TaskStatus.DONE);
        when(repository.save(any(Task.class))).thenReturn(task);

        Task result = service.updateStatus(1L, TaskStatus.DONE);

        assertThat(result.getStatus()).isEqualTo(TaskStatus.DONE);


    }

    @Test
    void delete_deveDeletarTarefa_quandoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(repository).deleteById(1L);

        assertThatCode(() -> service.delete(1L)).doesNotThrowAnyException();


        verify(repository, times(1)).deleteById(1L);


    }

    @Test
    void delete_deveLancarExcecao_quandoNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99L))

                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void findByStatus_deveRetornarTarefasFiltradas() {
        when(repository.findByStatus(TaskStatus.PENDING)).thenReturn(List.of(task));

        List<Task> result = service.findByStatus("PENDING");

        assertThat(result).hasSize(1);


    }

}
