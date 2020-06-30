public class App {
    public static void main(String[] args) throws Exception {
        
        InfixToPostfix a = new InfixToPostfix("10+10*20*10-((10+10)+10+10+(10-10))");
        a.clear();
        a.convert("10+10");
        System.out.println(a.get());
        
    }
}
