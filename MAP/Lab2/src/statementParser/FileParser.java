package statementParser;

import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.values.BoolValue;
import model.values.IntValue;

import  java.util.*;
import java.io.*;
import java.util.regex.*;

import static model.expressions.LogicalOperation.AND;
import static model.expressions.LogicalOperation.OR;

public class FileParser {

    public static IStmt parseFile(String filePath) throws IOException {
        List<IStmt> statements = new ArrayList<>();
        Stack<List<IStmt>> forkStack = new Stack<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Delegate parsing to StatementFactory
            IStmt statement = StatementParser.parseStatement(line, reader, forkStack);
            if (statement != null) {
                if (forkStack.isEmpty()) {
                    statements.add(statement);
                } else {
                    forkStack.peek().add(statement);
                }
            }
        }
        reader.close();
        return toCompositeStmt(statements);
    }

    public static IStmt toCompositeStmt(List<IStmt> statements) {
        if (statements.isEmpty()) return null;
        IStmt stmt = statements.get(statements.size() - 1);
        for (int i = statements.size() - 2; i >= 0; i--) {
            stmt = new CompositeStmt(statements.get(i), stmt);
        }
        return stmt;
    }
}
