package org.silvachristian.searchfilms.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "movies")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonProperty("Title")
    String filmTitle;

    @JsonProperty("Metascore")
    String filmRating;

    @JsonProperty("Genre")
    String filmGenre;

    @JsonProperty("Year")
    String filmYear;

    @JsonProperty("Poster")
    String posterURL;

    @JsonProperty("Country")
    String filmCountry;

    @JsonProperty("Type")
    String filmType;

    @JsonProperty("Plot")
    String filmPlot;

}

