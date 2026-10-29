module co.edu.univalle.poe.mvc {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.univalle.poe.mvc to javafx.fxml;
    exports co.edu.univalle.poe.mvc;
    exports co.edu.univalle.poe.mvc.controller;
    exports co.edu.univalle.poe.mvc.model;

    opens co.edu.univalle.poe.mvc.controller to javafx.fxml;
    opens co.edu.univalle.poe.mvc.model to org.junit.platform.commons;
}