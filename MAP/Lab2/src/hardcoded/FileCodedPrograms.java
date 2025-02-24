package hardcoded;

import model.statements.IStmt;
import statementParser.FileParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileCodedPrograms {

    public List<IStmt> getFileCodedPrograms() {
        IStmt ex1 = null, ex5 = null, ex9 = null, ex10 = null;
        try {
            ex1 = FileParser.parseFile("ex1.txt");
            ex5 = FileParser.parseFile("ex5.txt");
            ex9 = FileParser.parseFile("ex9.txt");
            ex10 = FileParser.parseFile("ex10.txt");
        } catch (IOException e) {
            System.err.println("Error loading files: " + e.getMessage());
        }
        List<IStmt> fileCodedPrograms = new ArrayList<>(List.of(ex1, ex5, ex9, ex10));
        return fileCodedPrograms;
    }


}
