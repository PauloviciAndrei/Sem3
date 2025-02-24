package model.statements;

import exceptions.AppException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyList;
import model.adt.MyMap;
import model.expressions.BinaryExpression;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;

import java.util.List;

public class SwitchStmt implements IStmt {

    IExpression expression;
    MyList<IExpression> expressions;
    MyList<IStmt> statements;

    public SwitchStmt(IExpression expression,
                           MyList<IExpression> expressions,
                           MyList<IStmt> statements) {
        this.expression = expression;
        this.expressions = expressions;
        this.statements = statements;
    }

    @Override
    public ProgramState execute(ProgramState state) throws AppException {
        List<IExpression> expressions = this.expressions.getAll();
        List<IStmt> statements = this.statements.getAll();
        IStmt statement = statements.get(statements.size() - 1);
        for(int i = expressions.size() - 1;i >= 0;i--){
            statement = new IfStmt(new BinaryExpression(expression, expressions.get(i), "=="),
                    statements.get(i), statement);
        }
        state.getExeStack().push(statement);
        return null;
    }


    public MyMap<String, IType> typecheck(MyMap<String, IType> typeDictionary) throws AppException {

        IType expressionType = null;
        try
        {
            expressionType = expression.typecheck(typeDictionary);
        } catch (ExpressionException e) {
            throw new RuntimeException(e);
        }
        for(IStmt iter_statement:this.statements.getAll()){
            try
            {
                iter_statement.typecheck(typeDictionary.copy());
            } catch (StatementException | ExpressionException e) {
                throw new RuntimeException(e);
            }
        }

        for(IExpression iter_expression:this.expressions.getAll()){
            try
            {
                if(!(iter_expression.typecheck(typeDictionary).equals(expressionType))) {
                    throw new AppException("Expression type mismatch in switch");
                }
            }
            catch (ExpressionException e) {
                throw new RuntimeException(e);
            }

        }
        return typeDictionary;
    }

    @Override
    public String toString(){
        StringBuilder answer = new StringBuilder("Switch(" + this.expression.toString() + ")");
        List<IExpression> expressions = this.expressions.getAll();
        List<IStmt> statements = this.statements.getAll();
        for(int i = 0;i < expressions.size();i++){
            answer.append("(Case ").append(expressions.get(i).toString()).append(": ").append(statements.get(i).toString()).append(")");
        }
        answer.append("(Default: ").append(statements.get(statements.size() - 1)).append(");");
        return answer.toString();
    }
}
