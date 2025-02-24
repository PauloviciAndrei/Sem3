package view.gui;

import controller.Controller;
import exceptions.ExpressionException;
import exceptions.StatementException;
import hardcoded.FileCodedPrograms;
import hardcoded.HardcodedPrograms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.adt.MyMap;
import model.expressions.*;
import model.state.ProgramState;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramSelect {
    private ProgramExecutor programExecutor;

    public void setProgramExecutor(ProgramExecutor programExecutor) {
        this.programExecutor = programExecutor;
    }
    @FXML
    private ListView<IStmt> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    private void initialize() {
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent) {
        IStmt selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typecheck(new MyMap<>());
                IRepository repository = new Repository("log" + (id + 1) + ".txt");
                Controller controller = new Controller(repository);
                controller.addProgram(selectedStatement);
                programExecutor.setController(controller);
            } catch (StatementException | ExpressionException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<IStmt> getAllStatements() {

        FileCodedPrograms fileCodedPrograms = new FileCodedPrograms();
        return FXCollections.observableArrayList(fileCodedPrograms.getFileCodedPrograms());
    }
}