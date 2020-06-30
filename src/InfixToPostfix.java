import java.util.ArrayList;
import java.util.List;

public class InfixToPostfix {

    private List<String> infix = new ArrayList<>();
    private List<Integer> symbolFilter = new ArrayList<>();

    private List<String> postfix = new ArrayList<>();
    private List<String> stack = new ArrayList<>();
    private List<String> result = new ArrayList<>();
    
    public InfixToPostfix(String text){
        convert(text);
    }

    private boolean isSymbol(final char c) {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '%':
            case '^':
            case '/':
            case '(':
            case ')':
                return true;
            default:
                return false;
        }
    }

    private int operatorValue(String c) {
        switch (c) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "%":
            case "/":
                return 2;
            case "^":
                return 3;
            case "(":
                return 4;
            case ")":   
                return 0;
            default:
                return -1;
        }
    }

    //model
    private String calculate(String operator, String a, String b){
        double A = Double.parseDouble(a);
        double B = Double.parseDouble(b);
        switch (operator) {
            case "+": return ""+(A+B);
            case "-": return ""+(A-B);
            case "*": return ""+(A*B);
            case "%": return ""+(A%B);
            case "^": return ""+(Math.pow(A,B));
            case "/": return ""+(A/B);
            default: break;
        }
        return "";
    }
    //model


    private boolean isNumeric(String str) {
		return str != null && str.matches("[-+]?\\d*\\.?\\d+");
	}

    private void textToInfix(String text) {
        final String tmpInfix = text + ")";
        for(int i = 0 ; i < tmpInfix.length() ; i++){
            if(isSymbol(tmpInfix.charAt(i))) symbolFilter.add(i);
        }
        for(int i = 0, j = 0 , k = 0; i < symbolFilter.size() ;i++){
            j = symbolFilter.get(i);
            if(k != j ) {
                //for unary operator:
                //define " - " -> n
                if(tmpInfix.charAt(k) == 'n') {
                    infix.add(tmpInfix.substring(k, j).replace('n','-'));
                }
                else infix.add(tmpInfix.substring(k, j));
            }
            infix.add(""+tmpInfix.charAt(j));
            k = j+1;
        }
        infix.remove(infix.size()-1);
    }

    private void infixToPostfix(List<String> infix){
        for(int i = 0 ; i < infix.size() ; i++){
            if(isNumeric(infix.get(i))) postfix.add(infix.get(i));
            else if(stack.isEmpty()) stack.add(infix.get(i));
            else{
                for(int j = stack.size()-1 ; j >= 0 ; j--){
                    if(operatorValue(infix.get(i)) <= operatorValue(stack.get(j))){
                        
                        if(operatorValue(infix.get(i)) == 0) {
                            stack.remove("n");
                            j--;
                        }
                        else{
                            postfix.add(stack.get(j));
                            stack.remove(j);
                        }
                        
                    }
                    else{
                        if(operatorValue(infix.get(i)) == 4) stack.add("n");
                        else if(operatorValue(infix.get(i)) != 0) stack.add(infix.get(i));
                        break;
                    }
                }
                if(stack.isEmpty()) stack.add(infix.get(i));
            }
        }
        for(int j = stack.size()-1 ; j >= 0 ; j--){
            postfix.add(stack.get(j));
            stack.remove(j);
        }
    }

    private void calculateInPostfix(List<String> postfix){
        boolean turn = true;
        String tmp;
        for(int i = 0 ; i < postfix.size() ; i++){
            if(isNumeric(postfix.get(i))) result.add(postfix.get(i));
            else {
                if(turn){
                    tmp = calculate(postfix.get(i), result.get(result.size()-1), result.get(result.size()-2));
                    result.remove(result.size()-1);
                    result.remove(result.size()-1);
                    result.add(tmp);
                    turn = false;
                }else{
                    tmp = calculate(postfix.get(i), result.get(result.size()-2), result.get(result.size()-1));
                    result.remove(result.size()-1);
                    result.remove(result.size()-1);
                    result.add(tmp);
                }
            }
        }
    }
    public String get(){
        return result.get(result.size()-1);
    }
    public void clear(){
        infix.clear();
        postfix.clear();
        result.clear();
        stack.clear();
        symbolFilter.clear();
    }

    public String convert(String text){
        textToInfix(text);
        infixToPostfix(infix);
        calculateInPostfix(postfix);
        return get();
    }
}