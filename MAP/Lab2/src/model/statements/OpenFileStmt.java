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

public class OpenFileStmt implements IStmt{
    IExpression expression;

    public OpenFileStmt(IExpression expression){
        this.expression = expression;
    }


    public ProgramState execute(ProgramState state) throws AppException {
        IValue value = null;
        try
        {
            value = this.expression.evaluate(state.getSymTable(), state.getHeap());
        } catch (ExpressionException e) {
            System.out.println(e.getMessage());
        }

        if(!(value.getType() instanceof StringType)){
            throw new AppException("Filename did not evaluate to string");
        }
        state.getFileTable().openFile(((StringValue)value).getValue());
        return null;
    }

    @Override
    public String toString(){
        return "openFile(" + this.expression.toString() + ")";
    }

    public MyMap<String, IType> typecheck(MyMap<String, IType> typeTable) throws StatementException, ExpressionException {
        if (!expression.typecheck(typeTable).equals(new StringType())) {
            throw new StatementException("Open Read File Error: OpenReadFile() requires a string expression.");
        }

        return typeTable;
    }
}
