package br.com.jeffersonmoraesjunior.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//valida o idUser por uma lista de Modelo de tarefa
public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{
    List<TaskModel> findByIdUser(UUID idUser);
}
