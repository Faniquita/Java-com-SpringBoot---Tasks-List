package br.com.todolistberock.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


//Parametros do JpaRepository = classe representada, tipo da id que a classe tem
public interface IUserRepository extends JpaRepository <UserModel, UUID> {
    UserModel findByUsername(String username);
}
