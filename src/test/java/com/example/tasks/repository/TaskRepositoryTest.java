package com.example.tasks.repository;

import com.example.tasks.model.Task;
import com.example.tasks.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    @Test
    void findByStatus_deveRetornarTarefasComStatusCorreto() {
        Task task = new Task();
        task.setTitle("Tarefa 1");
        task.setStatus(TaskStatus.PENDING);
        repository.save(task);

        List<Task> result = repository.findByStatus(TaskStatus.PENDING);

        assertThat(result).hasSize(1);


        assertThat(result.get(0).getStatus()).isEqualTo(TaskStatus.PENDING);
    }

    @Test
    void create_deveSalvarTarefaNoBanco() {
        Task task = new Task();
        task.setTitle("Tarefa 2");
        task.setStatus(TaskStatus.IN_PROGRESS);

        Task savedTask = repository.save(task);

        assertThat(savedTask.getId()).isNotNull();

        assertThat(savedTask.getTitle()).isEqualTo("Tarefa 2");

        assertThat(savedTask.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

    @Test
    void delete_deveRemoverTarefaDoBanco() {
        Task task = new Task();
        task.setTitle("Tarefa 3");


        task.setStatus(TaskStatus.DONE);
        Task savedTask = repository.save(task);
        repository.deleteById(savedTask.getId());

        assertThat(repository.findById(savedTask.getId())).isEmpty();


    }

    @Test
    void findAll_deveRetornarTodasAsTarefas() {
        Task task1 = new Task();

        task1.setTitle("Tarefa 1");


        task1.setStatus(TaskStatus.PENDING);
        repository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Tarefa 2");

        task2.setStatus(TaskStatus.IN_PROGRESS);
        repository.save(task2);

        List<Task> result = repository.findAll();

        assertThat(result).hasSizeGreaterThanOrEqualTo(2);

        assertThat(result).extracting(Task::getTitle)
                .contains("Tarefa 1", "Tarefa 2");
    }

    @Test
    void findById_deveRetornarTarefaCorreta() {
        Task task = new Task();
        task.setTitle("Tarefa Teste");
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = repository.save(task);

        var result = repository.findById(savedTask.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Tarefa Teste");
        assertThat(result.get().getId()).isEqualTo(savedTask.getId());
    }

    @Test
    void findById_deveRetornarEmpty_quandoNaoExiste() {
        var result = repository.findById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    void existsById_deveRetornarTrue_quandoExiste() {
        Task task = new Task();
        task.setTitle("Tarefa Teste");
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = repository.save(task);

        boolean exists = repository.existsById(savedTask.getId());

        assertThat(exists).isTrue();


    }

    @Test
    void existsById_deveRetornarFalse_quandoNaoExiste() {
        boolean exists = repository.existsById(999L);

        assertThat(exists).isFalse();


    }

    @Test
    void count_deveRetornarNumeroCorretoDeTarefas() {

        long initialCount = repository.count();

        Task task1 = new Task();
        task1.setTitle("Tarefa 1");
        task1.setStatus(TaskStatus.PENDING);
        repository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Tarefa 2");
        task2.setStatus(TaskStatus.IN_PROGRESS);

        repository.save(task2);


        long finalCount = repository.count();

        assertThat(finalCount).isEqualTo(initialCount + 2);
    }

    @Test
    void findByStatus_deveRetornarListaVazia_quandoNaoHaTarefasComStatus() {

        List<Task> result = repository.findByStatus(TaskStatus.DONE);

        assertThat(result).isEmpty();
    }

    @Test
    void findByStatus_deveRetornarMultiplasTarefasComMesmoStatus() {

        Task task1 = new Task();
        task1.setTitle("Tarefa 1");
        task1.setStatus(TaskStatus.PENDING);
        repository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Tarefa 2");
        task2.setStatus(TaskStatus.PENDING);
        repository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Tarefa 3");
        task3.setStatus(TaskStatus.IN_PROGRESS);
        repository.save(task3);



        List<Task> pendingTasks = repository.findByStatus(TaskStatus.PENDING);
        List<Task> inProgressTasks = repository.findByStatus(TaskStatus.IN_PROGRESS);

        assertThat(pendingTasks).hasSize(2);
        assertThat(inProgressTasks).hasSize(1);


        assertThat(pendingTasks).allMatch(task -> task.getStatus() == TaskStatus.PENDING);


        assertThat(inProgressTasks).allMatch(task -> task.getStatus() == TaskStatus.IN_PROGRESS);

    }
}
