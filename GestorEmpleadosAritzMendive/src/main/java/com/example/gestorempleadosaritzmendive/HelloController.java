package com.example.gestorempleadosaritzmendive;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

     @FXML
     private ComboBox<String> ComboSalario;

    @FXML
    private TextField Nombre;

    @FXML
    private TextField Salario;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboSalario.getItems().removeAll(ComboSalario.getItems());
        ComboSalario.getItems().addAll("Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst Programmer", "Junior Programmer");
    }

    @FXML
    protected void onHelloButtonClick() {

    }
}