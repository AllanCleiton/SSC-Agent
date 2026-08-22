module sscagent.desktop {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.allancleitonppma.sscagent.desktop.application;

    opens com.allancleitonppma.sscagent.desktop.application
            to javafx.fxml;
}