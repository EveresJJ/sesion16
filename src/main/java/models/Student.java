package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name ="students")
@Getter
@Setter
@ToString
@NamedQueries(
        {
                @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
        }
)

public class Student extends BaseEntity {
    @Column(unique = true)
    private String cif;
    @Column(nullable = false)
    @Length(min = 3, max = 60)
    private String firstName;
    @Column(nullable = false)
    @Length(min = 3, max = 60)
    private String lastName;
    @Column(unique = true)
    private String email;

    @ManyToOne // Definir la relacion con la entidad City
    @JoinColumn(name = "city_id") //Define la clave foranea que conecta con la tabla City
    private City city;

}
