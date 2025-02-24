package hardcoded;
import model.statements.*;

import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.*;
import model.expressions.*;
import model.state.*;
import java.util.List;
import java.util.ArrayList;

public class HardcodedPrograms {

    public static final IStmt ex1= new CompositeStmt(new VariableDeclarationStmt("v",new BoolType()),
            new CompositeStmt(new AssignStmt("v",new ValueExpression(new IntValue(2))), new PrintStmt(new
                    VariableExpression("v"))));

    public static final IStmt ex2 = new CompositeStmt( new VariableDeclarationStmt("a",new IntType()),
            new CompositeStmt(new VariableDeclarationStmt("b",new IntType()),
                    new CompositeStmt(new AssignStmt("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                            ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                            new CompositeStmt(new AssignStmt("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new
                                    IntValue(1)))), new PrintStmt(new VariableExpression("b"))))));

    public final static IStmt ex3 = new CompositeStmt(new VariableDeclarationStmt("a",new BoolType()),
            new CompositeStmt(new VariableDeclarationStmt("v", new IntType()),
                    new CompositeStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
                            new CompositeStmt(new IfStmt(new VariableExpression("a"),new AssignStmt("v",new ValueExpression(new
                                    IntValue(2))), new AssignStmt("v", new ValueExpression(new IntValue(3)))), new PrintStmt(new
                                    VariableExpression("v"))))));

    public final static IStmt ex4 = new CompositeStmt(new VariableDeclarationStmt("a", new IntType()),
            new CompositeStmt(new VariableDeclarationStmt("b", new IntType()),
                    new CompositeStmt(new AssignStmt("a", new ValueExpression(new IntValue(5))),
                            new CompositeStmt(new AssignStmt("b", new ValueExpression(new IntValue(7))),
                                    new IfStmt(new RelationalExpression(new VariableExpression("a"),
                                            new VariableExpression("b"), ">"),new PrintStmt(new VariableExpression("a")),
                                            new PrintStmt(new VariableExpression("b")))))));

    public static final IStmt ex5 = new CompositeStmt(new VariableDeclarationStmt("varf", new StringType()),
            new CompositeStmt(new AssignStmt("varf", new ValueExpression(
                    new StringValue("test.in"))),
                    new CompositeStmt(new OpenFileStmt(new VariableExpression("varf")),
                            new CompositeStmt(new VariableDeclarationStmt("varc", new IntType()),
                                    new CompositeStmt(new ReadFileStmt(
                                            new VariableExpression("varf"), "varc"),
                                            new CompositeStmt(new PrintStmt(new VariableExpression("varc")),
                                                    new CompositeStmt(new ReadFileStmt(
                                                            new VariableExpression("varf"), "varc"),
                                                            new CompositeStmt(
                                                                    new PrintStmt(
                                                                            new VariableExpression("varc")),
                                                                    new CloseFileStmt(
                                                                            new VariableExpression("varf"))))))))));
    /* Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a) */
    public final static IStmt ex6 = new CompositeStmt(new VariableDeclarationStmt("v", new ReferenceType(new IntType())),
            new CompositeStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(20))),
                    new CompositeStmt(new VariableDeclarationStmt("a",
                            new ReferenceType(new ReferenceType(new IntType()))),
                            new CompositeStmt(new HeapAllocationStmt("a", new VariableExpression("v")),
                                    new CompositeStmt(new PrintStmt(new VariableExpression("v")),
                                            new PrintStmt(new VariableExpression("a")))))));

    /* Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a))) */
    public final static IStmt ex7 = new CompositeStmt(new VariableDeclarationStmt("v", new ReferenceType(new IntType())),
            new CompositeStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(20))),
                    new CompositeStmt(new VariableDeclarationStmt("a", new ReferenceType(new ReferenceType(new IntType()))),
                            new CompositeStmt(new HeapAllocationStmt("a", new VariableExpression("v")),
                                    new CompositeStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(30))),
                                            new PrintStmt(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))))))));

    /* Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5); */
    public final static IStmt ex8 =  new CompositeStmt(new VariableDeclarationStmt("v", new ReferenceType(new IntType())),
            new CompositeStmt(new HeapAllocationStmt("v", new ValueExpression(new IntValue(20))),
                    new CompositeStmt( new PrintStmt(new HeapReadExpression(new VariableExpression("a"))),
                            new CompositeStmt(new HeapWriteStmt("v", new ValueExpression(new BoolValue(true))),
                                    new PrintStmt(new ArithmeticExpression('+', new HeapReadExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

    /* int a; int b; a=5; b=7; (While( a>b) Then Print(a) Else Print(b)) */
    public final static IStmt ex9 = new CompositeStmt(new VariableDeclarationStmt("a", new IntType()),
            new CompositeStmt(new VariableDeclarationStmt("b", new IntType()),
            new CompositeStmt(new AssignStmt("a", new ValueExpression(new IntValue(5))),
            new CompositeStmt(new AssignStmt("b", new ValueExpression(new IntValue(7))),
            new WhileStmt(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"), "<"),
                    new AssignStmt("a",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new
                            IntValue(1)))))))));

    /* int v; Reference int a; v=10; new(a,22); fork(WriteHeap(a,30); v=32; print(v); print(ReadHeap(a))); print(v); print(ReadHeao(a)) */
    public final static IStmt ex10 = new CompositeStmt(new VariableDeclarationStmt("v", new IntType()),
            new CompositeStmt(new VariableDeclarationStmt("a", new ReferenceType(new IntType())),
                    new CompositeStmt(new AssignStmt("v", new ValueExpression(new IntValue(10))),
                            new CompositeStmt(new HeapAllocationStmt("a", new ValueExpression(new IntValue(22))),
                                    new CompositeStmt(new ForkStmt(new CompositeStmt(new HeapWriteStmt("a", new ValueExpression(new IntValue(30))),
                                            new CompositeStmt(new AssignStmt("v", new ValueExpression(new IntValue(32))),
                                                    new CompositeStmt(new PrintStmt(new VariableExpression("v")), new PrintStmt(new HeapReadExpression(new VariableExpression("a"))))))),
                                            new CompositeStmt(new PrintStmt(new VariableExpression("v")), new PrintStmt(new HeapReadExpression(new VariableExpression("a")))))))));

 public final static IStmt ex11 = new CompositeStmt(new VariableDeclarationStmt("v", new IntType()),
            new CompositeStmt(new VariableDeclarationStmt("a", new ReferenceType(new IntType())),
                    new CompositeStmt(new AssignStmt("v", new ValueExpression(new IntValue(10))),
                            new CompositeStmt(new HeapAllocationStmt("a", new ValueExpression(new IntValue(22))),
                                    new CompositeStmt(new ForkStmt(new CompositeStmt(
                                            new HeapWriteStmt("a", new ValueExpression(new IntValue(30))),
                                            new CompositeStmt(new AssignStmt("v", new ValueExpression(new IntValue(32))),
                                                    new CompositeStmt(new PrintStmt(new VariableExpression("v")),
                                                            new CompositeStmt(
                                                                    new ForkStmt(new CompositeStmt(
                                                                            new AssignStmt("v", new ValueExpression(new IntValue(100))),
                                                                            new PrintStmt(new VariableExpression("v"))
                                                                    )),
                                                                    new PrintStmt(new HeapReadExpression(new VariableExpression("a")))
                                                            ))))),
                                            new CompositeStmt(new PrintStmt(new VariableExpression("v")), new PrintStmt(new HeapReadExpression(new VariableExpression("a")))))))));



    public final static IStmt ex12 = new CompositeStmt(new VariableDeclarationStmt("v", new IntType()),
            new CompositeStmt(new VariableDeclarationStmt("a", new ReferenceType(new IntType())),
                    new CompositeStmt(new AssignStmt("v", new ValueExpression(new IntValue(10))),
                            new CompositeStmt(new HeapAllocationStmt("a", new ValueExpression(new IntValue(22))),
                                    new CompositeStmt(new ForkStmt(new CompositeStmt(
                                            new HeapWriteStmt("a", new ValueExpression(new IntValue(30))),
                                            new CompositeStmt(new AssignStmt("v", new ValueExpression(new IntValue(32))),
                                                    new CompositeStmt(new PrintStmt(new VariableExpression("v")),
                                                            new PrintStmt(new HeapReadExpression(new VariableExpression("a"))))))),
                                            new CompositeStmt(new ForkStmt(new CompositeStmt(
                                                    new AssignStmt("v", new ValueExpression(new IntValue(100))),
                                                    new CompositeStmt(new HeapWriteStmt("a", new ValueExpression(new IntValue(200))),
                                                            new CompositeStmt(new PrintStmt(new VariableExpression("v")),
                                                                    new PrintStmt(new HeapReadExpression(new VariableExpression("a"))))))),
                                                    new CompositeStmt(new PrintStmt(new VariableExpression("v")),
                                                            new PrintStmt(new HeapReadExpression(new VariableExpression("a"))))))))));

    public final static IStmt ex13 = new CompositeStmt(
            new VariableDeclarationStmt("v", new IntType()),
            new CompositeStmt(
                    new VariableDeclarationStmt("x", new IntType()),
                    new CompositeStmt(
                            new VariableDeclarationStmt("y", new IntType()),
                            new CompositeStmt(
                                    new AssignStmt("v", new ValueExpression(new IntValue(0))),
                                    new CompositeStmt(
                                            new RepeatUntilStmt(
                                                    new CompositeStmt(
                                                            new ForkStmt(
                                                                    new CompositeStmt(
                                                                            new PrintStmt(new VariableExpression("v")), new AssignStmt("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))), new AssignStmt("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))), new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)),"==")),
                                            new CompositeStmt(
                                                    new AssignStmt("x", new ValueExpression(new IntValue(1))),
                                                    new CompositeStmt(
                                                            new NopStmt(),
                                                            new CompositeStmt(
                                                                    new AssignStmt("y", new ValueExpression(new IntValue(3))),
                                                                    new CompositeStmt(
                                                                            new NopStmt(),
                                                                            new PrintStmt(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))))))))));

    public final static IStmt ex14 = new CompositeStmt(
            new VariableDeclarationStmt("v1", new ReferenceType(new IntType())),
            new CompositeStmt(
                    new VariableDeclarationStmt("v2", new ReferenceType(new IntType())),
                    new CompositeStmt(
                            new VariableDeclarationStmt("v3", new ReferenceType(new IntType())),
                            new CompositeStmt(
                                    new VariableDeclarationStmt("cnt", new IntType()),
                                    new CompositeStmt(
                                            new HeapAllocationStmt("v1", new ValueExpression(new IntValue(2))),
                                            new CompositeStmt(
                                                    new HeapAllocationStmt("v2", new ValueExpression(new IntValue(3))),
                                                    new CompositeStmt(
                                                            new HeapAllocationStmt("v3", new ValueExpression(new IntValue(4))),
                                                            new CompositeStmt(
                                                                    new NewBarrierStmt("cnt", new HeapReadExpression(new VariableExpression("v2"))),
                                                                    new CompositeStmt(
                                                                            new ForkStmt(
                                                                                    new CompositeStmt(
                                                                                            new AwaitStmt("cnt"),
                                                                                            new CompositeStmt(
                                                                                                    new HeapWriteStmt("v1", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                                    new PrintStmt(new HeapReadExpression(new VariableExpression("v1")))
                                                                                            )
                                                                                    )
                                                                            ),
                                                                            new CompositeStmt(
                                                                                    new ForkStmt(
                                                                                            new CompositeStmt(
                                                                                                    new AwaitStmt("cnt"),
                                                                                                    new CompositeStmt(
                                                                                                            new HeapWriteStmt("v2", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                            new CompositeStmt(
                                                                                                                    new HeapWriteStmt("v2", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                                    new PrintStmt(new HeapReadExpression(new VariableExpression("v2")))
                                                                                                            )
                                                                                                    )
                                                                                            )
                                                                                    ),
                                                                                    new CompositeStmt(
                                                                                            new AwaitStmt("cnt"),
                                                                                            new PrintStmt(new HeapReadExpression(new VariableExpression("v3")))
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );





    public static final  List<IStmt> hardCodedPrograms = new ArrayList<>(List.of(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12, ex13, ex14));

    public List<IStmt> getHardCodedPrograms()
    {
        return hardCodedPrograms;
    }



}
