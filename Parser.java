/**
 * A class for parsing arithmetic expressions
 */
public class Parser {

  /**
   * An exception that is thrown if the to-be-parsed expression is not
   * well-formed.
   */
  public static class ExpressionNotWellFormedException extends Exception {
    public ExpressionNotWellFormedException() {
    }
  }

  /**
   * Parses a given String, determines whether it is a well-formed expression,
   * and computes the expression.
   * 
   * @param expression
   *          the expression that is to be evaluated
   * @return the result of the evaluation / computation
   * @throws ExpressionNotWellFormedException
   *           if the expression is not well-formed, an exception is thrown
   */
  public static int parse(String expression) throws ExpressionNotWellFormedException {
	    if (expression.length() == 0) {
	        throw new ExpressionNotWellFormedException();
	    }

	    // this checks if the expression is a single number
	    if (expression.matches("\\d")) {
	        return Integer.parseInt(expression);
	    }
	    

	    //this just checks if the expression starts and ends with parentheses
	    if (expression.charAt(0) != '(' || expression.charAt(expression.length() - 1) != ')') {
	        throw new ExpressionNotWellFormedException();
	    }

	    // Remove the outer parentheses to make my life easier
	    expression = expression.substring(1, expression.length() - 1);

	    // Split the expression into left and right subexpressions based on the operator
	    int operatorIndex = findOperatorIndex(expression);
	    if (operatorIndex == -1) {
	        throw new ExpressionNotWellFormedException();
	    }

	    String leftExpression = expression.substring(0, operatorIndex);
	    String rightExpression = expression.substring(operatorIndex + 1);

	    // Parse the left and right subexpressions recursively
	    int leftResult = parse(leftExpression);
	    int rightResult = parse(rightExpression);

	    // Evaluate the expression based on the operator
	    char operator = expression.charAt(operatorIndex);
	    return evaluateExpression(leftResult, operator, rightResult);
	}

	private static int findOperatorIndex(String expression) throws ExpressionNotWellFormedException{
	    int parenthesesCount = 0;
	    for (int i = expression.length() - 1; i >= 0; i--) {
	        char c = expression.charAt(i);
	        if (c == ' ') {
	        	throw new ExpressionNotWellFormedException();
	        }
	        
	        if (c == '(') {
	            parenthesesCount++;
	        } else if (c == ')') {
	            parenthesesCount--;
	        } else if (parenthesesCount == 0 && isOperator(c)) {
	            return i;
	        }
	    }
	    return -1;
	}

	private static boolean isOperator(char c) {
	    return c == '+' || c == '-' || c == '*' || c == '/';
	}

	private static int evaluateExpression(int leftOperand, char operator, int rightOperand) {
	    switch (operator) {
	        case '+':
	            return leftOperand + rightOperand;
	        case '-':
	            return leftOperand - rightOperand;
	        case '*':
	            return leftOperand * rightOperand;
	        case '/':
	            return leftOperand / rightOperand;
	        default:
	            throw new IllegalArgumentException("Invalid operator: " + operator);
	    }
	}

  
  
  public static boolean number (char check) {
	  if (check >= '0' && check <= '9') {
		  return true;
	  }
	  return false;
  }
  
  public static int expcalc (char char1, char sym, char char2) {
	  int num1 = char1 - '0';
	  int num2 = char2 - '0';
	  
	  switch (sym) {
	  case '+':
          return num1 + num2;
      case '*':
          return num1 * num2;
      case '-':
          return num1 - num2;
      case '/':
          return num1 / num2;
	  }
	  return 0;
  }
  
 
  
  public static boolean symbol (char check) {
	  switch (check) {
      case '+':
          return true;
      case '*':
          return true;
      case '-':
          return true;
      case '/':
          return true;
      default:
          return false;
  }
  }

  /**
   * test cases
   */
  public static void main(String[] args) {
    {
      wellFormedCheck("((8+7)*2)", 30);
      wellFormedCheck("(4-(7-1))", -2);
      wellFormedCheck("8", 8);
      wellFormedCheck("((1+1)*(2*2))", 8);

      notWellFormedCheck(")8+)1(())");
      notWellFormedCheck("(8+())");
      notWellFormedCheck("-1");
      notWellFormedCheck("(   5    -7)");
      notWellFormedCheck("108");
      notWellFormedCheck("(8)");
      
    }
  }

  private static void checkAndPrint(String message, boolean correct) {
    System.out.println((correct ? "PASS:" : "FAIL:") + " " + message);
    assert (correct);
  }

  private static void notWellFormedCheck(String expression) {
    try {
      int returned = parse(expression);
      checkAndPrint("nicht wohlgeformter Ausdruck " + expression
          + " ausgewertet zu " + returned, false);
    } catch (ExpressionNotWellFormedException e) {
      checkAndPrint("Ausdruck " + expression
          + " als nicht wohlgeformt erkannt.", true);
    }
  }

  private static void wellFormedCheck(String expression, int expected) {
    try {
      int returned = parse(expression);
      checkAndPrint("Ausdruck " + expression + " ausgewertet zu " + returned
          + " (erwartet: " + expected + ")", returned == expected);
    } catch (ExpressionNotWellFormedException e) {
      checkAndPrint("Ausdruck " + expression
          + " fälschlicherweise als nicht wohlgeformt eingeschätzt.", false);
    }
  }
}