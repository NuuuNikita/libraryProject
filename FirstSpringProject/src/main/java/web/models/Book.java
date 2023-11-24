package web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Название книги не должно быть путсым")
    @Size(min = 2, max = 100, message = "Название книги должно быть иметь 2 и меньше 100 символов")
    private String bookName;

    @Column(name = "author_name")
    @NotEmpty(message = "Поле автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно иметь больше 2 и меньше 100 симоволов")
    private String authorName;

    @Column(name = "year")
    @Min(value = 1500)
    @Max(value = 2023)
    private int releaseDate;

    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean overdue;

    public Book(String bookName, String authorName, int releaseDate) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.releaseDate = releaseDate;
    }
}
