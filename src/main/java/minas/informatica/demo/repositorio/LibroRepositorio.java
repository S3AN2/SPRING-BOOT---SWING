package minas.informatica.demo.repositorio;

import minas.informatica.demo.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepositorio extends JpaRepository<Libro,Integer> {



}
