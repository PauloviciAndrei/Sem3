package view.gui;

import controller.Controller;
import exceptions.ExpressionException;
import exceptions.StatementException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import model.adt.ADTPair;
import model.adt.BarrierTableRow;
import model.adt.MyList;
import model.adt.MyMap;
import model.state.ProgramState;
import model.state.barrierTable.BarrierTable;
import model.state.heap.Heap;
import model.state.symTable.SymTable;
import model.statements.IStmt;
import model.types.IType;
import model.values.IValue;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ProgramExecutor {
    private Controller controller;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private TableView<ADTPair<Integer, IValue>> heapTableView;

    @FXML
    private TableColumn<ADTPair<Integer, IValue>, Integer> addressColumn;

    @FXML
    private TableColumn<ADTPair<Integer, IValue>, String> valueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private TableView<ADTPair<String, IValue>> symbolTableView;

    @FXML
    private TableColumn<ADTPair<String, IValue>, String> variableNameColumn;

    @FXML
    private TableColumn<ADTPair<String, IValue>, String> variableValueColumn;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableView<Pair<Pair<Integer, Integer>, String>> barrierTableView;

    @FXML
    private TableColumn<Pair<Pair<Integer, Integer>, String>, Integer> indexColumn;
    @FXML
    private TableColumn<Pair<Pair<Integer, Integer>, String>, Integer> barrierValueColumn;
    @FXML
    private TableColumn<Pair<Pair<Integer, Integer>, String>, String> listColumn;

    @FXML
    private Button runOneStepButton;

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize() {
        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSecond().toString()));
        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getFirst()));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSecond().toString()));
        indexColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey().getKey()));
        barrierValueColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey().getValue()));
        listColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValue()));
    }

    private ProgramState getCurrentProgramState() {
        if (controller.getRepository().getProgramsList().size() == 0)
            return null;
        else {
            int currentId = programStateIdentifiersListView.getSelectionModel().getSelectedIndex();
            System.out.println("CURRENT ID: " + currentId);
            if (currentId == -1)
                return controller.getRepository().getProgramsList().get(0);
            else
                return controller.getRepository().getProgramsList().get(currentId);
        }
    }

    private void populate() {
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateProgramStateIdentifiersListView();
        populateSymbolTableView();
        populateExecutionStackListView();

    }

    @FXML
    private void changeProgramState(MouseEvent event) {
        populateExecutionStackListView();
        populateSymbolTableView();
    }

    private void populateNumberOfProgramStatesTextField() {
        List<ProgramState> programStates = controller.getRepository().getProgramsList();
        numberOfProgramStatesTextField.setText(String.valueOf(programStates.size()));
    }

    private void populateHeapTableView() {
        ProgramState programState = getCurrentProgramState();
        Heap heap = Objects.requireNonNull(programState).getHeap();
        ArrayList<ADTPair<Integer, IValue>> heapEntries = new ArrayList<>();
        for(Map.Entry<Integer, IValue> entry: heap.getContent().entrySet()) {
            heapEntries.add(new ADTPair<>(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(heapEntries));
    }

    private void populateOutputListView() {
        ProgramState programState = getCurrentProgramState();
        List<String> output = new ArrayList<>();
        MyList<String> outputList = Objects.requireNonNull(programState).getOut().getOutputList();
        int index;
        for (index = 0; index < outputList.getAll().size(); index++){
            output.add(outputList.get(index));
        }
        outputListView.setItems(FXCollections.observableArrayList(output));
    }

    private void populateFileTableListView() {
        ProgramState programState = getCurrentProgramState();
        List<String> files = new ArrayList<>(Objects.requireNonNull(programState).getFileTable().getContent().keySet());
        fileTableListView.setItems(FXCollections.observableList(files));
    }

    private void populateProgramStateIdentifiersListView() {
        List<ProgramState> programStates = controller.getRepository().getProgramsList();
        List<Integer> idList = programStates.stream().map(ProgramState::getId).collect(Collectors.toList());
        programStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        populateNumberOfProgramStatesTextField();
    }

    private void populateSymbolTableView() {
        ProgramState programState = getCurrentProgramState();
        SymTable symbolTable = Objects.requireNonNull(programState).getSymTable();
        ArrayList<ADTPair<String, IValue>> symbolTableEntries = new ArrayList<>();
        for (Map.Entry<String, IValue> entry: symbolTable.getContent().entrySet()) {
            symbolTableEntries.add(new ADTPair<>(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    private void populateExecutionStackListView() {
        ProgramState programState = getCurrentProgramState();
        List<String> executionStackToString = new ArrayList<>();
        if (programState != null)
            for (IStmt statement : programState.getExeStack().getContent().getReversed())
                executionStackToString.add(statement.toString());

        executionStackListView.setItems(FXCollections.observableList(executionStackToString));
    }

    private void populateBarrierTableView() {
        ProgramState programState = getCurrentProgramState();
        if (programState == null) {
            barrierTableView.setItems(FXCollections.observableArrayList());
            return;
        }

        BarrierTable barrierTable = programState.getBarrierTable();
        List<Pair<Pair<Integer, Integer>, String>> tableData = new ArrayList<>();

        for (Integer key : barrierTable.getContent().keySet()) {
            Pair<Integer, List<Integer>> value = barrierTable.getContent().get(key);
            Pair<Integer, Integer> innerPair = new Pair<>(key, value.getKey());
            tableData.add(new Pair<>(innerPair, value.getValue().toString()));
        }

        barrierTableView.setItems(FXCollections.observableArrayList(tableData));
    }


    @FXML
    private void runOneStep(MouseEvent mouseEvent) {
        if (controller != null) {

                List<ProgramState> programStates = Objects.requireNonNull(controller.getRepository().getProgramsList());
                System.out.println("LIST" + controller.getRepository().getProgramsList());
                //programStates =controller.removeCompletedPrograms(programStates);
                if (programStates.size() > 0) {
                    try
                    {
                        controller.executeOneStepForAll(controller.getRepository().getProgramsList());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    populate();
                    controller.getRepository().setProgramsList(programStates);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("An error has occured!");
                    alert.setContentText("There is nothing left to execute!");
                    alert.showAndWait();
                }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error has occured!");
            alert.setContentText("No program selected!");
            alert.showAndWait();
        }
    }
}
