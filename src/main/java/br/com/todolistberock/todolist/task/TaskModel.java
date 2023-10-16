package br.com.todolistberock.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    /*
     * DADOS DAS TAREFAS INCLUIDAS
     * 
     * Usuário (IS_USUA)
     * Descrição
     * Título
     * Data de Criação
     * Data de Início
     * Data de Termino
     * Prioridade
     * 
    */

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id_task;

    @Column(name = "tbta_description")
    private String description;

    @Column(name = "tbta_title", length = 50)
    private String title;

    @Column(name = "tbta_dt_start")
    private LocalDateTime startAt;

    @Column(name = "tbta_dt_end")
    private LocalDateTime endAt;

    @Column(name = "tbta_priority")
    private String priority;

    @Column(name = "tbta_fk_user")
    private UUID idUser;

    @CreationTimestamp
    @Column(name = "tbta_dt_create")
    private LocalDateTime createAd;


    // Criando validação de excessão
    public void setTitle(String title) throws Exception{
        if(title.length() > 50){
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }
        this.title = title;       

    }



    
    
}
