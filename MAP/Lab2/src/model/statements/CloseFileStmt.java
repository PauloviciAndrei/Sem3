package model.statements;

import exceptions.AppException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

public class CloseFileStmt implements IStmt{

    IExpression expression;

    public CloseFileStmt(IExpression expression){
        this.expression = expression;
    }


    public ProgramState execute(ProgramState state) throws AppException {
        IValue value;
        try
        {
            value = this.expression.evaluate(state.getSymTable(), state.getHeap());
        }
        catch (ExpressionException e)
        {
            throw new AppException(e.getMessage());
        }

        if(!(value.getType() instanceof StringType)){
            throw new AppException("Filename did not evaluate to string");
        }
        state.getFileTable().closeFile(((StringValue)value).getValue());
        return null;
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        if (!expression.typecheck(typeTable).equals(new StringType())) {
            throw new StatementException("Close Read File Error: CloseReadFile() requires a string expression.");
        }
        return typeTable;
    }

    @Override
    public String toString(){
        return "closeReadFile(" + this.expression.toString() + ")";
    }
}
