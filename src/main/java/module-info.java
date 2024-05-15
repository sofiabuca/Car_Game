module com.example.cargame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cargame to javafx.fxml;
    exports com.example.cargame;
}