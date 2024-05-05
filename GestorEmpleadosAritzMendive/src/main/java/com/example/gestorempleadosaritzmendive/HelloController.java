package com.example.gestorempleadosaritzmendive;


import Modelo.Trabajador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TextField txtfldNombre;
    @FXML
    public TextField txtfldPuesto;
    @FXML
    public TextField txtfldSalario;
    @FXML
    private ComboBox<String> ComboPuesto;

    @FXML
    private TextField Nombre;

    @FXML
    private TextField Salario;

    @FXML
    private ListView<Trabajador> listViewDatos;

    private ObservableList<Trabajador> listaEmpleados = FXCollections.observableArrayList();

    LocalDate fechaActual = LocalDate.now();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboPuesto.getItems().removeAll(ComboPuesto.getItems());
        ComboPuesto.getItems().addAll("Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst Programmer", "Junior Programmer");
        listViewDatos.setItems(listaEmpleados);
    }

    @FXML
    protected void Insertar() {
        String nombre = Nombre.getText();
        String puesto = ComboPuesto.getValue();
        String salarioText = Salario.getText();

        if (nombre.isEmpty() || puesto == null || salarioText.isEmpty()) {
            mostrarError("Todos los campos deben estar rellenados");
        } else {
            try {
                int salario = Integer.parseInt(salarioText);


                if (insertarEmpleado(nombre, puesto, salario, fechaActual)) {
                    mostrarMensaje("Empleado " + nombre + " introducido en la base de datos satisfactoriamente.");
                    Nombre.clear();
                    Salario.clear();
                } else {
                    mostrarError("Error al insertar alimento en la base de datos.");
                }
            } catch (NumberFormatException e) {
                mostrarError("El salario debe ser un número entero.");
            }
        }
    }


    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public boolean insertarEmpleado(String nombre, String puesto, int salario, LocalDate fecha) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmpleadosBDAritz", "root", "root")) {
            String insertSQL = "INSERT INTO EMPLEADO (nombre, puesto, salario, fecha) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setString(1, nombre);
                statement.setString(2, puesto);
                statement.setInt(3, salario);
                statement.setDate(4, java.sql.Date.valueOf(fecha));
                int filasInsertadas = statement.executeUpdate();
                return filasInsertadas > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Hubo un error durante la inserción en la base de datos", e);
        }
    }

    @FXML
    protected void agregarDesdeArchivo() {
        String archivo = "src/main/Texto/trabajadores.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(";");
                if (datos.length == 3) {
                    String nombre = datos[0];
                    String puesto = datos[1];
                    int salario = Integer.parseInt(datos[2]);
                    insertarEmpleado(nombre, puesto, salario, fechaActual);
                }
            }
            mostrarMensaje("Datos agregados desde el archivo " + archivo);
        } catch (IOException e) {
            mostrarError("Error al leer el archivo " + archivo);
        } catch (NumberFormatException e) {
            mostrarError("Error al convertir el salario a un número entero");
        }
    }


    @FXML
    protected void refrescarLista() {
        listaEmpleados.clear();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmpleadosBDAritz", "root", "root")) {
            String selectSQL = "SELECT * FROM EMPLEADO";
            try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String puesto = resultSet.getString("puesto");
                    int salario = resultSet.getInt("salario");
                    int id = resultSet.getInt("id");
                    listaEmpleados.add(new Trabajador(nombre, puesto, salario, fechaActual, id));
                }
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar los datos de los empleados desde la base de datos.");
            e.printStackTrace();
        }
    }


    public void mostrarInfoEmpleado(javafx.scene.input.MouseEvent mouseEvent) {
        Trabajador trabajadorSeleccionado = listViewDatos.getSelectionModel().getSelectedItem();
        txtfldNombre.setText(trabajadorSeleccionado.getNombre());
        txtfldPuesto.setText(trabajadorSeleccionado.getPuesto());
        txtfldSalario.setText(String.valueOf(trabajadorSeleccionado.getSalario()));

    }

    public void eliminarEmpleado(ActionEvent event) {
        Trabajador empleadoSeleccionado = listViewDatos.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmpleadosBDAritz", "root", "root")) {
                String deleteSQL = "DELETE FROM EMPLEADO WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
                    statement.setInt(1, empleadoSeleccionado.getId());
                    int filasEliminadas = statement.executeUpdate();
                    if (filasEliminadas > 0) {
                        mostrarMensaje("Empleado eliminado correctamente.");
                        listaEmpleados.remove(empleadoSeleccionado);
                    } else {
                        mostrarError("No se pudo eliminar el empleado.");
                    }
                }
            } catch (SQLException e) {
                mostrarError("Error al eliminar el empleado.");
                e.printStackTrace();
            }
        } else {
            mostrarError("Por favor, seleccione un empleado de la lista.");
        }
    }
}