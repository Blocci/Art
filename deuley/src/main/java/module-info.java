module last.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens last.project to javafx.fxml;
    exports last.project;
}
