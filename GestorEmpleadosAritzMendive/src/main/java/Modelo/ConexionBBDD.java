package Modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;

public class ConexionBBDD {

    Connection connection;
    Statement statement;

    public ConexionBBDD() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
        statement = connection.createStatement();
    }


    public void creacionBBDD() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/BBDD/CreacionBBDD.sql"));

        try {
            String st;
            ArrayList<String> lineas = new ArrayList<>();
            while ((st = br.readLine()) != null) {
                lineas.add(st);
            }


            for (String linea : lineas) {
                statement.executeUpdate(linea);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
