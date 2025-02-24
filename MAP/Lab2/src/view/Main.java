package view;

import controller.Controller;
import hardcoded.HardcodedPrograms;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import repository.Repository;
import statementParser.FileParser;
import view.Command.*;
import javafx.application.Application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {

        Application.launch(args);

        Repository repo1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repo1);
        try
        {
            controller1.addProgram(FileParser.parseFile("ex1.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Repository repo2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repo2);
        controller2.addProgram(HardcodedPrograms.ex2);

        Repository repo3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repo3);
        controller3.addProgram(HardcodedPrograms.ex3);

        Repository repo4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repo4);
        controller4.addProgram(HardcodedPrograms.ex4);

        Repository repo5 = new Repository("log5.txt");
        Controller controller5 = new Controller(repo5);
        controller5.addProgram(HardcodedPrograms.ex5);

        Repository repo6 = new Repository("log6.txt");
        Controller controller6 = new Controller(repo6);
        controller6.addProgram(HardcodedPrograms.ex6);

        Repository repo7 = new Repository("log7.txt");
        Controller controller7 = new Controller(repo7);
        controller7.addProgram(HardcodedPrograms.ex7);

        Repository repo8 = new Repository("log8.txt");
        Controller controller8 = new Controller(repo8);
        controller8.addProgram(HardcodedPrograms.ex8);

        Repository repo9 = new Repository("log9.txt");
        Controller controller9 = new Controller(repo9);
        controller9.addProgram(HardcodedPrograms.ex9);



        Repository repo10 = new Repository("log10.txt");
        Controller controller10 = new Controller(repo10);
        controller10.addProgram(HardcodedPrograms.ex10);

        Repository repo11 = new Repository("log11.txt");
        Controller controller11 = new Controller(repo11);
        controller11.addProgram(HardcodedPrograms.ex11);

        Repository repo12 = new Repository("log12.txt");
        Controller controller12 = new Controller(repo12);
        controller12.addProgram(HardcodedPrograms.ex12);


        Repository repo13 = new Repository("log13.txt");
        Controller controller13 = new Controller(repo13);
        controller13.addProgram(HardcodedPrograms.ex13);

        TextMenu menu = new TextMenu();


        menu.addCommand(new RunExampleCommand("1", HardcodedPrograms.ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", HardcodedPrograms.ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", HardcodedPrograms.ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", HardcodedPrograms.ex4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", HardcodedPrograms.ex5.toString(), controller5));
        menu.addCommand(new RunExampleCommand("6", HardcodedPrograms.ex6.toString(), controller6));
        menu.addCommand(new RunExampleCommand("7", HardcodedPrograms.ex7.toString(), controller7));
        menu.addCommand(new RunExampleCommand("8", HardcodedPrograms.ex8.toString(), controller8));
        menu.addCommand(new RunExampleCommand("9", HardcodedPrograms.ex9.toString(), controller9));
        menu.addCommand(new RunExampleCommand("10", HardcodedPrograms.ex10.toString(), controller10));
        menu.addCommand(new RunExampleCommand("11", HardcodedPrograms.ex11.toString(), controller11));
        menu.addCommand(new RunExampleCommand("12", HardcodedPrograms.ex12.toString(), controller12));
        menu.addCommand(new RunExampleCommand("13", HardcodedPrograms.ex13.toString(), controller13));
        menu.addCommand(new ExitCommand("14", "Exit."));

        menu.show();
    }
}