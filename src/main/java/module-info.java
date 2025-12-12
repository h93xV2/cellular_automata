module cellular_automata {
    requires transitive javafx.controls;
    requires javafx.fxml;

    requires com.google.common;
    requires com.fasterxml.jackson.databind;

    requires transitive java.desktop;

    exports cellular_automata;
    exports cellular_automata.graphics.board;
    exports cellular_automata.cells;
    exports cellular_automata.cells.rules;
    exports cellular_automata.filemanagement.data;

    opens cellular_automata.controllers to javafx.fxml;
    opens cellular_automata.graphics to javafx.fxml;
    opens cellular_automata.graphics.board to javafx.fxml;
    opens cellular_automata.filemanagement.data to com.fasterxml.jackson.databind;
}
