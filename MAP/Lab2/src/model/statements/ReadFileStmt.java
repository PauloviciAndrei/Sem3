package model.statements;


import exceptions.AppException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

public class ReadFileStmt implements IStmt{

    IExpression expression;
    String name;

    public ReadFileStmt(IExpression expression, String name) {
        this.expression = expression;
        this.name = name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        IValue value = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if(!(value.getType() instanceof StringType)) {
            throw new AppException("Filename did not evaluate to string");
        }
        try
        {
            state.getSymTable().setValue(name,
                    new IntValue(state.getFileTable().readFile(((StringValue)value).getValue())));
            return null;
        }
        catch(KeyNotFoundException e)
        {
            System.out.println("Key not found!");
        }
        return null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        if (!expression.typecheck(typeTable).equals(new StringType())) {
            throw new StatementException("Read File Error: ReadFile() requires a string expression.");
        }
        if (!typeTable.get(name).equals(new IntType())) {
            throw new StatementException("Read File Error: ReadFile() requires an int as variable.");
        }

        return typeTable;
    }

    @Override
    public String toString(){
        return "readFile(" + this.expression.toString() + "," + this.name + ")";
    }

}
