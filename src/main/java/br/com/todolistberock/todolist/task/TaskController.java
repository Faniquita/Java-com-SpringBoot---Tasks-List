package br.com.todolistberock.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.todolistberock.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;


    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        System.out.println("Id do usuário passado: " + idUser);
        taskModel.setIdUser((UUID) idUser);

        var currentDate =  LocalDateTime.now();
        // Verificar a data de inicio/final da tarefa antes da data atual
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicial e a data de termino da tarefa deve ser maior ou igual a data atual!");
        }

        // Verificar a data de inicio depois da data de termino da tarefa
        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inicial da tarefa não pode ser maior que a data de termino da tarefa!");
        }


        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);

    }

    // Listagem de tarefas do usuário
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        this.taskRepository.findByIdUser((UUID) idUser);
        
        return tasks;
    }

    // Alteração de dados da tarefa
    @PutMapping("/{id_task}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id_task){
        // Verificação dos dados que vão ser alterados
        var task = this.taskRepository.findById(id_task).orElse(null);
        Utils.copyNonNullProperties(taskModel, task);
        
        return this.taskRepository.save(task);
        
    }
}
