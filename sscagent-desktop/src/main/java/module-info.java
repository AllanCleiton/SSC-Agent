module sscagent.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires sscagent.domain;
    requires com.allancleitonppma.sscagent.application;
    requires sscagent.infrastructure;
    requires javafx.base;

    exports com.allancleitonppma.sscagent.desktop.application;
    exports com.allancleitonppma.sscagent.desktop.dto;

    opens com.allancleitonppma.sscagent.desktop.application
            to javafx.fxml;

    opens com.allancleitonppma.sscagent.desktop.dto
            to javafx.base;

    opens com.allancleitonppma.sscagent.desktop.controller
            to javafx.fxml;

}