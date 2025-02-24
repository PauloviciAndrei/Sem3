package view;

import exceptions.ExpressionException;
import model.adt.MyMap;
import model.values.IValue;
import view.Command.Command;

import java.util.Scanner;

public class TextMenu {
    private final MyMap<String, Command> commands;

    public TextMenu() {
        commands = new MyMap<>();
    }

    public void addCommand(Command command) {
        commands.insert(command.getKey(), command);
    }

    private void printMenu() {

        for(String key : commands.keySet() )
        {
            Command command = null;

            try
            {
                command = commands.get(key);
            } catch (ExpressionException e) {
                System.out.println(e.getMessage());
            }

            String line = String.format("%s. %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }

    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Give command: ");

            String key = scanner.nextLine();

            Command command = null;
            try
            {
                command = commands.get(key);
            } catch (ExpressionException e) {
                System.out.println(e.getMessage());
            }
            command.execute();
        }
    }
}