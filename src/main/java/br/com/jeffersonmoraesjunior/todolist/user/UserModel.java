package br.com.jeffersonmoraesjunior.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //getter and setter - dependency with lombok
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    @Column(unique = true) //unique = atributo unico
    private String userName;
    private String name;
    private String password;

    @CreationTimestamp //será um relatorio de quando o BD foi criado
    private LocalDateTime createdAt;
    
}
