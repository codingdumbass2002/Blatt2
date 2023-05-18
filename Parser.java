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
  public static int parse(String expression)
      throws ExpressionNotWellFormedException {
	  int first;
	  int second;
	  if (expression.length() == 0) {
		  throw new ExpressionNotWellFormedException();
	  }
	  
	  if (expression.length() == 1 && !number(expression.charAt(0))) {
		  throw new ExpressionNotWellFormedException();
	  }
	  
	  if (expression.length() < 5) {
		  throw new ExpressionNotWellFormedException();
	  }
	  
	  if (symbol(expression.charAt(0))) {
		  throw new ExpressionNotWellFormedException();
	  }
	  
	  if (expression.charAt(0) == ')') {
		  throw new ExpressionNotWellFormedException();
	  }
	  
	  
	  if (expression.charAt(0) == '(') {
		  if (number(expression.charAt(1)) && symbol(expression.charAt(2)) && number(expression.charAt(3)) && expression.charAt(4) == ')') {
			  return expcalc(expression.charAt(1), expression.charAt(2), expression.charAt(4));
		  }
		  
		  else  if (expression.charAt(1) == '(') {
			  String new_String = expression.substring(1);
			  return parse(new_String);
		  }
		  
		  else if (number(expression.charAt(1)) && symbol(expression.charAt(2)) && expression.charAt(3) == '(') {
			  String new_String = expression.substring(3);
			  return parse(new_String);
		  }
		  throw new ExpressionNotWellFormedException();
	  }
	  
	 
	  				

    return 0;
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