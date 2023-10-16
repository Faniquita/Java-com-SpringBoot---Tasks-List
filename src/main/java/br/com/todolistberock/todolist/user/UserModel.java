package br.com.todolistberock.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/*
 * @Data = Getting and Setting
 * @Setter = Setting
 * @Gettet = Gettering
 */

// TABELA odbc
@Entity(name = "tb_users")

@Data
public class UserModel {

    @Id // @ID do JakartA
    @GeneratedValue(generator = "UUID")
    private UUID id_user;

    @Column(name = "tbus_username", unique = true)
    private String username;

    @Column(name = "tbus_name")
    private String name;

    @Column(name = "tbus_password")
    private String password;   
   
    @CreationTimestamp
    @Column(name = "tbus_data")
    private LocalDateTime createdAt;
    
}
