package org.silvachristian.searchfilms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Reference;

@Getter
@Setter
@Entity(name = "favorites")
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long movieId;

    @NonNull
    private Long userId;
}
