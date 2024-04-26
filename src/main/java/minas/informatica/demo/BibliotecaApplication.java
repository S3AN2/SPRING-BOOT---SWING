package minas.informatica.demo;

import minas.informatica.demo.modelo.Libro;
import minas.informatica.demo.vista.LibroForm;
import org.hibernate.id.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		//definir contexto de spring e instanciarlo
		ConfigurableApplicationContext context  =
				new SpringApplicationBuilder( BibliotecaApplication.class)
						//ya que no es una aplicacion web se hace lo restante
						.headless(false)
						.web(WebApplicationType.NONE)
						.run(args);
		//EJECUTAR EL CODIGO PARA CARGAR FORMULARIO
		EventQueue.invokeLater(()->{
		//obtenemos el objeto form a traves de Spring
			LibroForm libroForm = context.getBean(LibroForm.class);
			libroForm.setVisible(true);

		});
	}

}
