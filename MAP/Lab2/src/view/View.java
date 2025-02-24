package view;

import static hardcoded.HardcodedPrograms.hardCodedPrograms;
import controller.IController;
import java.util.Scanner;

public class View {

    IController controller;
    boolean displayFlag = true;

    public View(IController controller)
    {
        this.controller = controller;
    }

    void printMenu()
    {
        System.out.println("\n1. int v; v=2;Print(v)");
        System.out.println("2. int a;int b; a=2+3*5;b=a+1;Print(b)");
        System.out.println("3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
        System.out.println("4. int v; v=false;Print(v)");
        System.out.println("5. Set display flag true");
        System.out.println("6. Set display flag false");
        System.out.println("7. Exit");
        System.out.println("======");
    }


    public void run(String[] args) {


        while(true)
        {
            printMenu();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter command: ");
            int command = scanner.nextInt();

            if(command == 5)
                controller.setDisplayFLag(true);
            if(command == 6)
                controller.setDisplayFLag(false);
            if(command == 7)
                break;
            /*
            if(command >=1 && command <=4)
            {
                this.controller.setProgram(hardCodedPrograms.get(command - 1));
                this.controller.executeAllSteps();
            }

             */
        }

    }
}
