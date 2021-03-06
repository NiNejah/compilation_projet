package fr.ubordeaux.arduinoCode.visitor;

import fr.ubordeaux.arduinoCode.ast.*;
import fr.ubordeaux.arduinoCode.type.TypeException;
import fr.ubordeaux.arduinoCode.type.TypeTree;
import fr.ubordeaux.arduinoCode.type.Type;

public class CheckTypeVisitor extends ConcreteVisitor {

	private String name;
	
	public CheckTypeVisitor(String name) {
		super();
		this.name = name;
	}

	@Override
	public void visit(StmAFF stm) throws TypeException {
		System.err.println("*** visit(Stm) with " + this);
		//stm.getLeft().getType().attestEquivalentTo(stm.getRight().getType());
		// stm.getRight().setType(stm.getLeft().getType());
 		if (stm.getLeft().getType() == null) stm.getLeft().setType(new TypeTree(Type.Tag.LIST)); /// C'est une liste.
		else stm.getRight().setType(stm.getLeft().getType());
		System.err.println("*** Types de stm:  " + stm.getRight().getType() + "   " + stm.getLeft().getType());
	}

	@Override
	public void visit(StmIF stm) throws TypeException {
		System.err.println("*** visit(StmIF) with " + this);
		stm.getExpr().getType().attestBoolean();
	}

	@Override
	public void visit(ExprFUNCTION expr) throws Exception {
		System.err.println("*** visit(ExprFUNCTION) with " + this);
		if (expr.getArguments() != null) {
			for (Expr arg : expr.getArguments()) {
				arg.accept(this);
			}
		}
	}

	@Override
	public void visit(ExprCONSTANT expr) throws Exception {
		System.err.println("*** visit(ExprCONSTANT) with " + this);
	}

	@Override
	public void visit(ExprUnary expr) throws Exception {
		System.err.println("*** visit(ExprUnary) with " + this);
	}

	@Override
	public void visit(StmWHILE stmWHILE) throws Exception {
		System.err.println("*** visit(StmIF) with " + this);
		stmWHILE.getExpr().getType().attestBoolean();
		stmWHILE.getExpr().accept(this);
	}


	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getPurpose() {
		return " Teste le type de l'objet et déclanche une exception TypeException en cas d'échec";
	}

	@Override
	public String getEffect() {
		return "Déclanche une exception de type TypeException";
	}

	


}
