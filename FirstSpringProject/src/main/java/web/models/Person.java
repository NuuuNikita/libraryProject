package web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotEmpty(message = "имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "ФИО должно иметь больше 0 и меньше 100 символов")
    private String fullName;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthday;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String fullName, Date birthday) {
        this.fullName = fullName;
        this.birthday = birthday;
    }
}
