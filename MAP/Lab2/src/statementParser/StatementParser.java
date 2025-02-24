package statementParser;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class StatementParser {
    public static IStmt parseStatement(String line, BufferedReader reader, Stack<List<IStmt>> forkStack) throws IOException {
        line = line.trim();

        // Variable Declaration
        if (line.startsWith("int ") || line.startsWith("bool ") || line.startsWith("string")) {
            String[] tokens = line.split("\\s+");
            if (tokens.length < 2 || !tokens[1].endsWith(";")) {
                throw new IllegalArgumentException("Syntax Error: Invalid variable declaration. Expected format: 'type varName;'");
            }
            String varName = tokens[1].replace(";", "");
            if (line.startsWith("int ")) return new VariableDeclarationStmt(varName, new IntType());
            if (line.startsWith("bool ")) return new VariableDeclarationStmt(varName, new BoolType());
            if (line.startsWith("string ")) return new VariableDeclarationStmt(varName, new StringType());
            return new VariableDeclarationStmt(varName, new ReferenceType(new IntType()));
        }

        else if(line.startsWith("Reference ")) {
            String[] tokens = line.split("\\s+");
            String varName = tokens[1].replace(";", "");
            return new VariableDeclarationStmt(varName, new ReferenceType(new IntType()));
        }

        // Heap Allocation (new(a, 22);)
        else if (line.startsWith("new(")) {
            if (!line.endsWith(");")) {
                throw new IllegalArgumentException("Syntax Error: Heap allocation must end with ');'. Example: new(var, value);");
            }
            String[] parts = line.replace("new(", "").replace(");", "").split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Syntax Error: Invalid heap allocation syntax. Expected: new(var, value);");
            }
            return new HeapAllocationStmt(parts[0].trim(), ExpressionParser.parseExpression(parts[1].trim()));
        }

        // Assignment
        else if (line.contains("=") && !line.startsWith("if")) {
            if (!line.endsWith(";")) {
                throw new IllegalArgumentException("Syntax Error: Assignment must end with ';'. Example: var = value;");
            }
            String[] parts = line.split("=");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Syntax Error: Invalid assignment. Expected: var = expression;");
            }
            return new AssignStmt(parts[0].trim(), ExpressionParser.parseExpression(parts[1].replace(";", "").trim()));
        }

        // If-Else Statement
        else if (line.startsWith("if (")) {
            int thenIndex = line.indexOf("then");
            if (thenIndex == -1) throw new IllegalArgumentException("Syntax Error: Missing 'then' keyword in if-statement.");

            String conditionExpr = line.substring(3, thenIndex).trim().replace("(", "").replace(")", "");
            IExpression condition = ExpressionParser.parseExpression(conditionExpr);

            String thenStmt = readNextStatement(reader);
            IStmt thenStatement = parseStatement(thenStmt, reader, forkStack);

            IStmt elseStatement = null;
            reader.mark(1000);
            String nextLine = reader.readLine();

            if (nextLine != null && nextLine.trim().startsWith("else")) {
                String elseStmt = readNextStatement(reader);
                elseStatement = parseStatement(elseStmt, reader, forkStack);
            } else {
                reader.reset();
            }
            return new IfStmt(condition, thenStatement, elseStatement);
        }

        // File Open
        else if (line.startsWith("openFile(")) {
            if (!line.endsWith(");")) {
                throw new IllegalArgumentException("Syntax Error: openFile must end with ');'. Example: openFile(varName);");
            }
            String fileVar = line.replace("openFile(", "").replace(");", "").trim();
            return new OpenFileStmt(new VariableExpression(fileVar));
        }

        // File Read
        else if (line.startsWith("readFile(")) {
            if (!line.endsWith(");")) {
                throw new IllegalArgumentException("Syntax Error: readFile must end with ');'. Example: readFile(varName, varToStore);");
            }
            String[] parts = line.replace("readFile(", "").replace(");", "").split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Syntax Error: Invalid readFile syntax. Expected: readFile(fileVar, intVar);");
            }
            return new ReadFileStmt(new VariableExpression(parts[0].trim()), parts[1].trim());
        }

        // File Close
        else if (line.startsWith("closeFile(")) {
            if (!line.endsWith(");")) {
                throw new IllegalArgumentException("Syntax Error: closeFile must end with ');'. Example: closeFile(varName);");
            }
            String fileVar = line.replace("closeFile(", "").replace(");", "").trim();
            return new CloseFileStmt(new VariableExpression(fileVar));
        }

        // While Statement
        else if (line.startsWith("while (")) {
            int doIndex = line.indexOf("do");
            if (doIndex == -1) throw new IllegalArgumentException("Syntax Error: Missing 'do' keyword in while-statement.");

            String conditionExpr = line.substring(6, doIndex).trim().replace("(", "").replace(")", "");
            IExpression condition = ExpressionParser.parseExpression(conditionExpr);

            String bodyStmt = readNextStatement(reader);
            IStmt loopBody = parseStatement(bodyStmt, reader, forkStack);

            return new WhileStmt(condition, loopBody);
        }

        // Heap Write (WriteHeap(a, 30);)
        else if (line.startsWith("WriteHeap(")) {
            if (!line.endsWith(");")) {
                throw new IllegalArgumentException("Syntax Error: WriteHeap must end with ');'. Example: WriteHeap(var, value);");
            }
            String[] parts = line.replace("WriteHeap(", "").replace(");", "").split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Syntax Error: Invalid heap write syntax. Expected: WriteHeap(var, value);");
            }
            return new HeapWriteStmt(parts[0].trim(), ExpressionParser.parseExpression(parts[1].trim()));
        }

        // Fork Statement (fork(...);)
        else if (line.startsWith("fork(")) {
            List<IStmt> forkStatements = new ArrayList<>();
            String forkLine;
            while ((forkLine = reader.readLine()) != null) {
                forkLine = forkLine.trim();
                if (forkLine.equals(");")) break;
                forkStatements.add(parseStatement(forkLine, reader, forkStack));
            }
            if (forkStatements.isEmpty()) {
                throw new IllegalArgumentException("Syntax Error: Fork block cannot be empty.");
            }
            return new ForkStmt(FileParser.toCompositeStmt(forkStatements));
        }

        // Print Statement
        else if (line.startsWith("print(")) {
            if (!line.endsWith(");")) {
                throw new IllegalArgumentException("Syntax Error: print statement must end with ');'. Example: print(expression);");
            }
            return new PrintStmt(ExpressionParser.parseExpression(line.replace("print(", "").replace(");", "").trim()));
        }

        // Unrecognized Statement
        throw new IllegalArgumentException("Syntax Error: Unrecognized statement: " + line);
    }


    private static String readNextStatement(BufferedReader reader) throws IOException {
        StringBuilder statement = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                statement.append(line);
                break;
            }
        }
        return statement.toString();
    }
}