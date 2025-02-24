module Lab2 {
    requires javafx.fxml;
    requires javafx.controls;
    requires jdk.jdi;

    exports view.gui to javafx.graphics, javafx.fxml;
    opens view.gui to javafx.fxml;
}