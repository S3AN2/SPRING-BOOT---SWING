package minas.informatica.demo.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
//clase entidad en mapear es @entity
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Table(name="nombre-de-tabla")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idlibro;
    String nombreLibro;
    String autor;
    Double precio;
    Integer existencias;
}
