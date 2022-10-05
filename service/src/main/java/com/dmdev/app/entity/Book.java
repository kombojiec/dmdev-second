package com.dmdev.app.entity;

import com.dmdev.app.enums.Genre;
import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Builder
@Check(constraints = "page_size >= 1")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    private LocalDate publishDate;

    private Integer pageSize;

    private String name;

    private boolean isIssued;

}
