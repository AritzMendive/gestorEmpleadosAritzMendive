module com.example.gestorempleadosaritzmendive {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestorempleadosaritzmendive to javafx.fxml;
    exports com.example.gestorempleadosaritzmendive;
}