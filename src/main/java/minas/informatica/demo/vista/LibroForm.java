package minas.informatica.demo.vista;

import minas.informatica.demo.modelo.Libro;
import minas.informatica.demo.repositorio.LibroRepositorio;
import minas.informatica.demo.servicio.ILibroServicio;
import minas.informatica.demo.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//para que se use el form con spring se genera una instancia
@Component
public class LibroForm extends JFrame {

    private JPanel panel;
    private JTable tblLibros;
    private JTextField idTexto;
    private JTextField txtLibro;
    private JTextField txtAutor;
    private JTextField txtPrecio;
    private JTextField txtExistencias;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private DefaultTableModel tableModel;


    LibroServicio libroServicio;
    //para inyectar la dependencia de spring es atravez del constructor, no por atributo
    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio=libroServicio;
        iniciarForma();
        btnAgregar.addActionListener(e ->  agregarLibro()

        );
        tblLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSelec();
            }
        });
        btnModificar.addActionListener( e -> modificarLibro());
        btnEliminar.addActionListener(e-> eliminarRegistro());
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
    }

    private void agregarLibro() {
        //leer los valores del formulario
        if(txtLibro.getText().equals("")){
            mostarMensaje("ingrese nombre del libro");
            txtLibro.requestFocusInWindow();
            return;

        }
        var nombreLibro = txtLibro.getText();
        var autor = txtAutor.getText();
        var precio = Double.parseDouble(txtPrecio.getText());
        var existencias = Integer.parseInt(txtExistencias.getText());

        //crear el objeto libro
        var libro =new Libro(null,nombreLibro,autor,precio,existencias);
        //libro.setNombreLibro(nombreLibro);
        //libro.setAutor(autor);
        //libro.setPrecio(precio);
        // libro.setExistencias(existencias);
        this.libroServicio.guardarLibro(libro);
        mostarMensaje("se agrego el libro...");
        limpiarFormulario();
        listarLibros();
    }

    private  void cargarLibroSelec(){
        //los indices de las columnsa inicias en 0
        var reglon= tblLibros.getSelectedRow();
        if(reglon != -1){ //regresa -1 si no se selecciona
            String idLibro=
                    tblLibros.getModel().getValueAt(reglon,0).toString();
            idTexto.setText(idLibro);
            String nombreLibro=
                    tblLibros.getModel().getValueAt(reglon,1).toString();
                txtLibro.setText(nombreLibro);
            String autor=
                    tblLibros.getModel().getValueAt(reglon,2).toString();
            txtAutor.setText(autor);
            String precio=
                    tblLibros.getModel().getValueAt(reglon,3).toString();
            txtPrecio.setText(precio);
            String existencias=
                    tblLibros.getModel().getValueAt(reglon,4).toString();
            txtExistencias.setText(existencias);
            //
        }

    }

    private void modificarLibro(){
        if (this.idTexto.getText().equals("")){
            mostarMensaje("Debe seleccionar un registro");

        }
        else {
            //verificamos que el nombre del libro no sea nulo
            if(txtLibro.getText().equals("")){
                mostarMensaje("Proporciona el nombre del Libro...");
                txtLibro.requestFocusInWindow();
                return;

            }
            //llenamos el objeto del libro actualizar
            int idLibro = Integer.parseInt(idTexto.getText());
            var nombreLibro= txtLibro.getText();
            var autor= txtAutor.getText();
            var precio=  txtPrecio.getText();
            var existencias=txtExistencias.getText();
            var libro  =new Libro(idLibro,nombreLibro,autor,Double.parseDouble(precio),Integer.parseInt(existencias));
            libroServicio.guardarLibro(libro);
            mostarMensaje("SE MODIFICO EL LIBRO");
            limpiarFormulario();
            listarLibros();

        }

    }

    private void eliminarRegistro() {
        if (this.idTexto.getText().equals("")){
            mostarMensaje("Debe seleccionar un registro para eliminar");

        }
        else {
            //verificamos que el nombre del libro no sea nulo
            if(txtLibro.getText().equals("")){
                mostarMensaje("Proporciona el nombre del Libro...");
                txtLibro.requestFocusInWindow();
                return;

            }
            //llenamos el objeto del libro actualizar
            int idLibro = Integer.parseInt(idTexto.getText());


            libroServicio.eliminarLibro(idLibro);
            mostarMensaje("SE ELIMINO EL REGISTRO");
            limpiarFormulario();
            listarLibros();

        }

    }

    private void limpiarFormulario() {
        txtLibro.setText("");
        txtAutor.setText("");
        txtExistencias.setText("");
        txtPrecio.setText("");

    }


    private void mostarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this,mensaje);
    }


    private void createUIComponents() {
        //crear objeto Default table
        //creamos el elemento idtexto oculto
        idTexto= new JTextField("");
        idTexto.setVisible(false);
    this.tableModel  =new DefaultTableModel(0,5){
        @Override
        public  boolean isCellEditable(int row, int column ){return false;}

    };
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
            Object[] renglonLibro = {
                    libro.getIdlibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            this.tableModel.addRow(renglonLibro);
        });
    }
}
