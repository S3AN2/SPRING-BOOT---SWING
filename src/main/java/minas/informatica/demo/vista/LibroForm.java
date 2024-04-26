package minas.informatica.demo.vista;

import minas.informatica.demo.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

//para que se use el form con spring se genera una instancia
@Component
public class LibroForm extends JFrame {



    private JPanel panel;
    private JTable tblLibros;
    private JTextField txtLibro;
    private JTextField txtAutor;
    private JTextField txtPrecio;
    private JTextField txtExistencias;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    LibroServicio libroServicio;
    private DefaultTableModel tableModel;

    //para inyectar la dependencia de spring es atravez del constructor, no por atributo
    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio=libroServicio;
        iniciarForma();
        btnAgregar.addActionListener(e -> {

        });
    }
    public  void iniciarForma(){
        setContentPane(panel);
        //para cerrar la aplicacion con exito
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900,700);
        //para obtener dimensiones de la ventana
        Toolkit toolkit = Toolkit.getDefaultToolkit();
      //obtneer el tamaño de la pantalla
        Dimension tamanio  =toolkit.getScreenSize();
        int x=(tamanio.width-getWidth()/2);
        int y=(tamanio.height=getHeight()/2);
        setLocation(x,y);
    };

    private void createUIComponents() {
        //crear objeto Default table
    this.tableModel  =new DefaultTableModel(0,5);
    //arreglo para especificar los cabeceros
    String [] cabeceros = {"id","Libro","Autor","Precio","Existencias"};
    //identificar los cabeceros
    this.tableModel.setColumnIdentifiers(cabeceros);
    //instanciar el objeto Jtable
        this.tblLibros= new JTable(tableModel);
        listarLibros();
    }

    private void listarLibros() {
        //limpiar la tabla
        tableModel.setRowCount(0);
        //obtener los libros
        var libros = libroServicio.listarLibros();
        libros.forEach((libro)-> {
            Object[] reglonLibro = {
                    libro.getIdlibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()

            };
            this.tableModel.addRow(reglonLibro);
        });
    }
}
