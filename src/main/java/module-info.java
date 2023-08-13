module com.example.demofx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires jdk.compiler;

    opens com.example.demofx to javafx.fxml;
    exports com.example.demofx;
}