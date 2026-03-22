package org.silvachristian.searchfilms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Entity(name = "user")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Username invalid, try again")
    @Column(unique = true)
    private String username;

    @NonNull
    @NotBlank
    private String password;

    @NonNull
    @NotBlank(message = "Invalid e-mail, try again")
    @Column(unique = true)
    private String email;
}
