module baldursbones.bb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens baldursbones.bb to javafx.fxml;
    exports baldursbones.bb;
}