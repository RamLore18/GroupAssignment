import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculator extends Calc{

    private JTextField textField1;
    private JButton button1;
    private JButton button3;
    private JButton button4;
    private JButton xButton;
    private JButton button8;
    private JButton button2;
    private JButton button5;
    private JButton cButton;
    private JButton a9Button;
    private JButton a8Button;
    private JButton a5Button;
    private JButton a2Button;
    private JButton a0Button;
    private JButton a6Button;
    private JButton a3Button;
    private JButton button16;
    private JButton a7Button;
    private JButton a4Button;
    private JButton a1Button;
    private JButton a10xButton;
    private JButton exButton;
    private JButton xyButton;
    private JButton log10Button;
    private JButton EEButton;
    private JButton randButton;
    private JButton lnButton;
    private JButton eButton;
    private JButton πButton;
    private JButton ʸXButton;
    private JButton tanButton;
    private JButton tanhButton;
    private JButton x3Button;
    private JButton xButton2;
    private JButton cosButton;
    private JButton coshButton;
    private JButton x2Button;
    private JButton xButton3;
    private JButton sinButton;
    private JButton sinhButton;
    private JButton a2ndButton;
    private JButton a1XButton;
    private JButton xButton1;
    private JButton radButton;
    private JButton button18;
    private JButton button19;
    private JPanel Calculator;
    private JPanel CalculatorP;
    private JButton delButton;


    private String expression="";
    private boolean num=false;
    private boolean dot=false;

    private ArrayList<String> token=new ArrayList<String>();
    private String result="";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator GROUP 1 Plugin made GUI");
        frame.setContentPane(new Calculator().CalculatorP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    int precedence(@NotNull String x)
    {
        int p=10;
        switch(x) {
            case "+":
                p=1;
                break;
            case "-":
                p=2;
                break;
            case "x":
                p=3;
                break;
            case "/":
                p=4;
                break;
            case "^":
                p=6;
                break;
            case "!":
                p=7;
                break;
        }

        return p;
    }

    //operator checking
    private boolean isoperator(String x)
    {
        if(List.of("+", "-", "x", "/", "sqrt", "^", "!", "sin", "cos", "tan", "ln", "log", "sinh","cosh", "tanh", "%", "Rad", "Mod","cbrt","aroot","exp","pow","10^","Del","1/","^2","^3").contains(x))
            return true;
        else
            return false;
    }

    //infix to postfix conversion
    private String infixTopostfix()
    {
        Stack<String> s=new Stack<String>();
        String y;
        int flag;
        StringBuilder p= new StringBuilder();
        token.add(")");
        s.push("(");
        for(String i: token) {
            if(i.equals("(")){
                s.push(i);
            }else if(i.equals(")")){
                y=s.pop();
                while(!y.equals("("))
                {
                    p.append(y).append(",");
                    y=s.pop();
                }
            }else if(isoperator(i)){
                y=s.pop();
                flag=0;
                if(isoperator(y) && precedence(y)>precedence(i)){
                    p.append(y).append(",");
                    flag=1;
                }
                if(flag==0)
                    s.push(y);

                s.push(i);
            }else{
                p.append(i).append(",");
            }
        }
        while(!s.empty()) {
            y=s.pop();
            if(!y.equals("(") && !y.equals(")")) {
                p.append(y).append(",");
            }
        }
        return p.toString();
    }


    //for actual calculation with binary operators
    private double calculate(double x,double y,String c)
    {
        double res=0;
        switch(c)
        {
            case "-":
                res= x-y;
                break;
            case "+":
                res= x+y;
                break;
            case "x":
                res= x*y;
                break;
            case "/":
                res= x/y;
                break;
            case "^":
                res= Math.pow(x,y);
                break;
            case "aroot":
                res= Math.pow(y, 1/x);
                break;
            case "Mod":
                res = Math.IEEEremainder(x, y);
                break;
            default :
                res= 0;
        }
        return res;
    }

    //calculation with unary operators
    private double calculate(double y,String c) {
        double res=0;
        switch(c) {
            case "log":
                res = Math.log10(y);
                break;
            case "sin":
                res= Math.sin(Math.toRadians(y));
                break;
            case "cos":
                res = Math.cos(Math.toRadians(y));
                break;
            case "tan":
                res =Math.tan(Math.toRadians(y));
                break;
            case "sinh":
                res = Math.sinh(y);
                break;
            case "cosh":
                res = Math.cosh(y);
                break;
            case "tanh":
                res = Math.tanh(y);
                break;
            case "%":
                res = y/100;
                break;
            case "Rad":
                res = Math.toRadians(y);
                break;
            case "ln":
                res= Math.log(y);
                break;
            case "sqrt":
                res= Math.sqrt(y);
                break;
            case "cbrt":
                res= Math.cbrt(y);
                break;
            case "exp":
                res= Math.exp(y);
                break;
            case "10^":
                res= Math.pow(10, y);
                break;
            case "!":
                res=factorial(y);
                break;
            case "^2":
                res= Math.pow(y, 2);
                break;
            case "^3":
                res= Math.pow(y, 3);
                break;
            case "1/":
                res= 1/y;
        }
        return res;
    }

    private double Eval(String p)
    {
        String tokens[] = p.split(",");
        ArrayList<String> token2=new ArrayList<String>();
        for(int i=0; i<tokens.length; i++) {
            if(! tokens[i].equals("") && ! tokens[i].equals(" ") && ! tokens[i].equals("\n") && ! tokens[i].equals("  ")) {
                token2.add(tokens[i]);  // tokens from post fix form p actual tokens for calculation
            }
        }

        Stack<Double> s=new Stack<Double>();
        double x,y;
        for(String  i:token2) {
            if(isoperator(i)){
                //if it is unary operator or function
                if(i.equals("sin") ||i.equals("cos") ||i.equals("tan") ||i.equals("log") || i.equals("ln") || i.equals("sqrt") || i.equals("!") || i.equals("sinh") || i.equals("cosh") || i.equals("tanh") || i.equals("%") || i.equals("Rand") || i.equals("Rad") || i.equals("cbrt") || i.equals("exp") || i.equals("10^") || i.equals("1/")|| i.equals("^2") || i.equals("^3")) {
                    y=s.pop();
                    s.push(calculate(y,i));
                }else {
                    //for binary operators
                    y=s.pop();
                    x=s.pop();
                    s.push(calculate(x,y,i));
                }
            }else{
                if(i.equals("pi"))
                    s.push(Math.PI);
                else if(i.equals("e"))
                    s.push(Math.E);
                else
                    s.push(Double.valueOf(i));
            }
        }
        double res=1;
        while(!s.empty()) {
            res*=s.pop();
        }
        return res;  //final result
    }

    //actual combined method for calculation
    private void calculateMain() {
        String tokens[]=expression.split(",");
        for(int i=0; i<tokens.length; i++) {
            if(! tokens[i].equals("") && ! tokens[i].equals(" ") && ! tokens[i].equals("\n") && ! tokens[i].equals("  ")) {
                token.add(tokens[i]);  //adding token to token array list from expression
            }
        }
        try {
            double res = Eval(infixTopostfix());
            result= Double.toString(res);
        }catch(Exception e) {}
    }
    //Interface A methods.
    public ActionListener zero(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"0");
        }else {
            textField1.setText("0");
        }
        if(num) {
            expression+="0";
        }else {
            expression+=",0";
        }
        num=true;
        return null;
    }
    public ActionListener one(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"1");
        }else {
            textField1.setText("1");
        }
        if(num) {
            expression+="1";
        }else {
            expression+=",1";
        }
        num=true;
        return null;
    }
    public ActionListener two(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"2");
        }else {
            textField1.setText("2");
        }
        if(num) {
            expression+="2";
        }else {
            expression+=",2";
        }
        num=true;
        return null;
    }
    public ActionListener three(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"3");
        }else {
            textField1.setText("3");
        }
        if(num) {
            expression+="3";
        }else {
            expression+=",3";
        }
        num=true;
        return null;
    }
    public ActionListener four(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"4");
        }else {
            textField1.setText("4");
        }
        if(num) {
            expression+="4";
        }else {
            expression+=",4";
        }
        num=true;
        return null;
    }
    public ActionListener five(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"5");
        }else {
            textField1.setText("5");
        }
        if(num) {
            expression+="5";
        }else {
            expression+=",5";
        }
        num=true;
        return null;
    }
    public ActionListener six(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"6");
        }else {
            textField1.setText("6");
        }
        if(num) {
            expression+="6";
        }else {
            expression+=",6";
        }
        num=true;
        return null;
    }
    public ActionListener seven(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"7");
        }else {
            textField1.setText("7");
        }
        if(num) {
            expression+="7";
        }else {
            expression+=",7";
        }
        num=true;
        return null;
    }
    public ActionListener eight(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"8");
        }else {
            textField1.setText("8");
        }
        if(num) {
            expression+="8";
        }else {
            expression+=",8";
        }
        num=true;
        return null;
    }
    public ActionListener nine(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"9");
        }else {
            textField1.setText("9");
        }
        if(num) {
            expression+="9";
        }else {
            expression+=",9";
        }
        num=true;
        return null;
    }
    public ActionListener point(){
        String s=textField1.getText();
        if(s.charAt(s.length()-1)!= '.') {
            if(num && dot==false) {
                expression+=".";
                s += ".";
            }else if(num==false && dot ==false){
                expression+=",.";
                s += ".";
            }
        }
        num=true;
        dot=true;
        textField1.setText(s);
        return null;
    }
    public ActionListener openParen(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"(");
        }else {
            textField1.setText("(");
        }
        expression+=",(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener closeParen(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+")");
        }else {
            textField1.setText(")");
        }
        expression+=",)";
        num=false;
        dot=false;
        return null;
    }
    //Interface B methods.
    // All functional
    @Override
    public ActionListener addition() {
        String s=textField1.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1) == (char)(247)) {
            String newString = s.substring(0,s.length()-1);
            newString += "+";
            textField1.setText(newString);
            expression = expression.substring(0,expression.length()-1);
            expression += "+";
        }else if(s.charAt(s.length()-1)!= '+') {
            s += "+";
            textField1.setText(s);
            expression+=",+";
        }else {
            textField1.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    @Override
    public ActionListener subtract() {
        String s=textField1.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '+') {
            String newString = s.substring(0,s.length()-1);
            newString += "-";
            expression = expression.substring(0,expression.length()-1);
            expression += "-";
            textField1.setText(newString);
        }else if(s.charAt(s.length()-1)!= '-') {
            s += "-";
            textField1.setText(s);
            expression += ",-";
        }else {
            textField1.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public ActionListener multiply() {

        String s=textField1.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== '+' || s.charAt(s.length()-1) == (char)(247)) {
            String newString = s.substring(0,s.length()-1);
            newString += "x";
            textField1.setText(newString);
            expression = expression.substring(0,expression.length()-1);
            expression += "x";
        }else if(s.charAt(s.length()-1)!= 'x') {
            s += "x";
            textField1.setText(s);
            expression+=",x";
        }else {
            textField1.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public ActionListener divide() {
        String s=textField1.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1) == '+') {
            String newString = s.substring(0,s.length()-1);
            textField1.setText(newString+Character.toString((char)247));
            expression = expression.substring(0,expression.length()-1);
            expression += "/";
        }else if(s.charAt(s.length()-1)!= (char)247) {
            textField1.setText(s+Character.toString((char)247));
            expression+=",/";
        }else {
            textField1.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public ActionListener percent() {
        String s=textField1.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1) == (char)(247)) {
            String newString = s.substring(0,s.length()-1);
            newString += "%";
            textField1.setText(newString);
            expression = expression.substring(0,expression.length()-1);
            expression += "%";
        }else if(s.charAt(s.length()-1)!= '%') {
            s += "%";
            textField1.setText(s);
            expression+=",%";
        }else {
            textField1.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public ActionListener equals() {
        calculateMain();
        String s="";
        token.remove(token.size()-1);
        for(String i: token) {
            if(i.equals("/")) {
                s+=Character.toString((char)247);
            }else if(i.equals("sqrt")) {
                s+=Character.toString((char)8730);
            }else if(i.equals("pi")) {
                s+=Character.toString((char)960);
            }else {
                s+=i;
            }
        }
        textField1.setText(result);

        expression = result;
        dot=true;
        num=true;
        token.clear();
        return null;
    }

    // Interface C methods.
    // Will have to fix the trig functions in time.Done
    //Hyperbolic Trig functions are functional.
    public ActionListener sin(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"sin(");
        }else {
            textField1.setText("sin(");
        }
        expression+=",sin,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener cos(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"cos(");
        }else {
            textField1.setText("cos(");
        }
        expression+=",cos,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener tan(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"tan(");
        }else {
            textField1.setText("tan(");
        }
        expression+=",tan,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener sinh(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"sinh(");
        }else {
            textField1.setText("sinh(");
        }
        expression+=",sinh,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener tanh(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"tanh(");
        }else {
            textField1.setText("tanh(");
        }
        expression+=",tanh,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener cosh(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"cosh(");
        }else {
            textField1.setText("cosh(");
        }
        expression+=",cosh,(";
        num=false;
        dot=false;
        return null;
    }
    //Interface D methods. All functional
    //COMPLETED
    public ActionListener power() {
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"^");
            expression+=",^";
        }else {
            textField1.setText("0^");
            expression += ",0,^";
        }
        num=false;
        dot=false;
        return null;
    }
    public ActionListener pi(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+Character.toString((char)960));
        }else {
            textField1.setText(Character.toString((char)960));
        }
        expression += ",pi";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener e(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"e");
        }else {
            textField1.setText("e");
        }
        expression+=",e";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener sqrt(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+Character.toString((char)8730));
        }else {
            textField1.setText(Character.toString((char)8730));
        }
        expression+=",sqrt";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener cbrt(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"∛");
        }else {
            textField1.setText("³√");
        }
        expression+=",cbrt";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener root(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+Character.toString((char)8730));
        }else {
            textField1.setText(Character.toString((char)8730));
        }
        expression+=",aroot";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener pow10(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"10^");
        }else {
            textField1.setText("10^");
        }
        expression+=",10^";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener square(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"^2");
        }else {
            textField1.setText("^2");
        }
        expression+=",^2";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener cube(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"^3");
        }else {
            textField1.setText("^3");
        }
        expression+=",^3";
        num=false;
        dot=false;
        return null;
    }
    //Interface E methods.
    // All functional
    public ActionListener rand(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"Rand");
        }else {
            textField1.setText("Rand");
        }
        expression+= Math.random();
        return null;
    }
    public ActionListener log(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"log(");
        }else {
            textField1.setText("log(");
        }
        expression+=",log,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener ln(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"ln(");
        }else {
            textField1.setText("ln(");
        }
        expression+=",ln,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener exp(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"exp(");
        }else {
            textField1.setText("exp(");
        }
        expression+=",exp,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener rad(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"Rad(");
        }else {
            textField1.setText("Rad(");
        }
        expression+=",Rad,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener EE(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"Mod");
        }else {
            textField1.setText("Mod");
        }
        expression+=",Mod";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener C(){
        textField1.setText("");
        expression ="";
        token.clear();
        result="";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener Del(){
        String s=textField1.getText();
        if(s.length()>0) {
            if(s.charAt(s.length()-1)=='.') {
                dot=false;
            }
            if(s.charAt(s.length()-1)== '+' || s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1)== (char)(247)) {
                num=false;
            }
            textField1.setText(s.substring(0,s.length()-1));
            expression = expression.substring(0,expression.length()-1);
        }
        return null;
    }
    public ActionListener Rec(){
        if(! "0".equals(textField1.getText())) {
            textField1.setText(textField1.getText()+"1/");
        }else {
            textField1.setText("1/x");
        }
        expression+=",1/";
        num=false;
        dot=false;
        return null;
    }


    public Calculator() {

        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                C();
            }
        });
        πButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pi();
            }
        });

        xyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               power();
            }
        });

        xButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"!");
                    expression+=",!";
                }else {
                    textField1.setText("0!");
                    expression+=",0,!";
                }
                num=false;
                dot=false;

            }
        });

        sinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sin();
            }
        });

        button18.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openParen();
            }
        });

        button19.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               closeParen();
            }
        });

        eButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e();
            }
        });

        xButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               sqrt();
            }
        });
        cosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cos();
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seven();
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eight();
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nine();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                divide();
            }
        });
        tanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tan();
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                four();
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                five();
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              six();
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                multiply();
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               one();
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               two();
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              three();
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subtract();
            }
        });
        button16.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                point();
            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              zero();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calculator.this.equals();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addition();
            }
        });
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                percent();
            }
        });
        xButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbrt();
            }
        });
        ʸXButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                root();
            }
        });
        a10xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pow10();
            }
        });
        sinhButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sinh();
            }
        });
        coshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cosh();
            }
        });
        tanhButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tanh();
            }
        });
        randButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rand();
            }
        });
        log10Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log();
            }
        });
        lnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ln();
            }
        });
        exButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exp();
            }
        });
        radButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rad();
            }
        });
        EEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EE();
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Del();
            }
        });
        a1XButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rec();
            }
        });
        x3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cube();
            }
        });
        x2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                square();
            }
        });
    }
}