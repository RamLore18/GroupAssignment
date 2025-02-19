import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class Calculator2{

    public static JTextField textField = new JTextField();
    private static String expression = "";
    private static boolean num = false;
    private static boolean dot = false;

    private static ArrayList<String> token = new ArrayList<String>();
    private static String result = "";

    //factorial calculation
    public static double factorial(double y) {
        double fact=1;
        if(y==0 || y==1) {
            fact=1;
        }else {
            for(int i=2; i<=y; i++) {
                fact*=i;
            }
        }
        return fact;
    }

    static int precedence(String x) {
        int p = 10;
        switch (x) {
            case "+":
                p = 1;
                break;
            case "-":
                p = 2;
                break;
            case "x":
                p = 3;
                break;
            case "/":
                p = 4;
                break;
            case "^":
                p = 6;
                break;
            case "!":
                p = 7;
                break;
        }

        return p;
    }

    //operator checking
    private static boolean isoperator(String x) {
        if (List.of("+", "-", "x", "/", "sqrt", "^", "!", "sin", "cos", "tan", "ln", "log", "sinh", "cosh", "tanh", "%", "Rad", "Mod", "cbrt", "aroot", "exp", "pow", "10^", "Del", "1/", "^2", "^3").contains(x))
            return true;
        else
            return false;
    }

    //infix to postfix conversion
    private static String infixTopostfix() {
        Stack<String> s = new Stack<String>();
        String y;
        int flag;
        StringBuilder p = new StringBuilder();
        token.add(")");
        s.push("(");
        for (String i : token) {
            if (i.equals("(")) {
                s.push(i);
            } else if (i.equals(")")) {
                y = s.pop();
                while (!y.equals("(")) {
                    p.append(y).append(",");
                    y = s.pop();
                }
            } else if (isoperator(i)) {
                y = s.pop();
                flag = 0;
                if (isoperator(y) && precedence(y) > precedence(i)) {
                    p.append(y).append(",");
                    flag = 1;
                }
                if (flag == 0)
                    s.push(y);

                s.push(i);
            } else {
                p.append(i).append(",");
            }
        }
        while (!s.empty()) {
            y = s.pop();
            if (!y.equals("(") && !y.equals(")")) {
                p.append(y).append(",");
            }
        }
        return p.toString();
    }


    //for actual calculation with binary operators
    private static double calculate(double x, double y, String c) {
        double res = 0;
        switch (c) {
            case "-":
                res = x - y;
                break;
            case "+":
                res = x + y;
                break;
            case "x":
                res = x * y;
                break;
            case "/":
                res = x / y;
                break;
            case "^":
                res = Math.pow(x, y);
                break;
            case "aroot":
                res = Math.pow(y, 1 / x);
                break;
            case "Mod":
                res = Math.IEEEremainder(x, y);
                break;
            default:
                res = 0;
        }
        return res;
    }

    //calculation with unary operators
    private static double calculate(double y, String c) {
        double res = 0;
        switch (c) {
            case "log":
                res = Math.log10(y);
                break;
            case "sin":
                res = Math.sin(Math.toRadians(y));
                break;
            case "cos":
                res = Math.cos(Math.toRadians(y));
                break;
            case "tan":
                res = Math.tan(Math.toRadians(y));
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
                res = y / 100;
                break;
            case "Rad":
                res = Math.toRadians(y);
                break;
            case "ln":
                res = Math.log(y);
                break;
            case "sqrt":
                res = Math.sqrt(y);
                break;
            case "cbrt":
                res = Math.cbrt(y);
                break;
            case "exp":
                res = Math.exp(y);
                break;
            case "10^":
                res = Math.pow(10, y);
                break;
            case "^2":
                res = Math.pow(y, 2);
                break;
            case "^3":
                res = Math.pow(y, 3);
                break;
            case "1/":
                res = 1 / y;
                break;
            case "!":
                res = factorial(y);
                break;
        }
        return res;
    }

    private static double Eval(String p) {
        String tokens[] = p.split(",");
        ArrayList<String> token2 = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].equals("") && !tokens[i].equals(" ") && !tokens[i].equals("\n") && !tokens[i].equals("  ")) {
                token2.add(tokens[i]);  // tokens from post fix form p actual tokens for calculation
            }
        }

        Stack<Double> s = new Stack<Double>();
        double x, y;
        for (String i : token2) {
            if (isoperator(i)) {
                //if it is unary operator or function
                if (i.equals("sin") || i.equals("cos") || i.equals("tan") || i.equals("log") || i.equals("ln") || i.equals("sqrt") || i.equals("!") || i.equals("sinh") || i.equals("cosh") || i.equals("tanh") || i.equals("%") || i.equals("Rand") || i.equals("Rad") || i.equals("cbrt") || i.equals("exp") || i.equals("10^") || i.equals("1/") || i.equals("^2") || i.equals("^3")) {
                    y = s.pop();
                    s.push(calculate(y, i));
                } else {
                    //for binary operators
                    y = s.pop();
                    x = s.pop();
                    s.push(calculate(x, y, i));
                }
            } else {
                if (i.equals("pi"))
                    s.push(Math.PI);
                else if (i.equals("e"))
                    s.push(Math.E);
                else
                    s.push(Double.valueOf(i));
            }
        }
        double res = 1;
        while (!s.empty()) {
            res *= s.pop();
        }
        return res;  //final result
    }

    //actual combined method for calculation
    private static void calculateMain() {
        String tokens[] = expression.split(",");
        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].equals("") && !tokens[i].equals(" ") && !tokens[i].equals("\n") && !tokens[i].equals("  ")) {
                token.add(tokens[i]);  //adding token to token array list from expression
            }
        }
        try {
            double res = Eval(infixTopostfix());
            result = Double.toString(res);
        } catch (Exception e) {
        }
    }

    public static ActionListener zero() {
        if (!"0".equals(textField.getText())) {
            textField.setText(textField.getText() + "0");
        } else {
            textField.setText("0");
        }
        if (num) {
            expression += "0";
        } else {
            expression += ",0";
        }
        num = true;
        return null;
    }
    public static ActionListener one(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"1");
        }else {
            textField.setText("1");
        }
        if(num) {
            expression+="1";
        }else {
            expression+=",1";
        }
        num=true;
        return null;
    }
    public static ActionListener two(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"2");
        }else {
            textField.setText("2");
        }
        if(num) {
            expression+="2";
        }else {
            expression+=",2";
        }
        num=true;
        return null;
    }
    public static ActionListener three(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"3");
        }else {
            textField.setText("3");
        }
        if(num) {
            expression+="3";
        }else {
            expression+=",3";
        }
        num=true;
        return null;
    }
    public static ActionListener four(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"4");
        }else {
            textField.setText("4");
        }
        if(num) {
            expression+="4";
        }else {
            expression+=",4";
        }
        num=true;
        return null;
    }
    public static ActionListener five(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"5");
        }else {
            textField.setText("5");
        }
        if(num) {
            expression+="5";
        }else {
            expression+=",5";
        }
        num=true;
        return null;
    }
    public static ActionListener six(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"6");
        }else {
            textField.setText("6");
        }
        if(num) {
            expression+="6";
        }else {
            expression+=",6";
        }
        num=true;
        return null;
    }
    public static ActionListener seven(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"7");
        }else {
            textField.setText("7");
        }
        if(num) {
            expression+="7";
        }else {
            expression+=",7";
        }
        num=true;
        return null;
    }
    public static ActionListener eight(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"8");
        }else {
            textField.setText("8");
        }
        if(num) {
            expression+="8";
        }else {
            expression+=",8";
        }
        num=true;
        return null;
    }
    public static ActionListener nine(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"9");
        }else {
            textField.setText("9");
        }
        if(num) {
            expression+="9";
        }else {
            expression+=",9";
        }
        num=true;
        return null;
    }
    public static ActionListener point(){
        String s=textField.getText();
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
        textField.setText(s);
        return null;
    }
    public static ActionListener openParen(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"(");
        }else {
            textField.setText("(");
        }
        expression+=",(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener closeParen(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+")");
        }else {
            textField.setText(")");
        }
        expression+=",)";
        num=false;
        dot=false;
        return null;
    }
    //Interface B methods.
    // All functional
    public static ActionListener addition() {
        String s=textField.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1) == (char)(247)) {
            String newString = s.substring(0,s.length()-1);
            newString += "+";
            textField.setText(newString);
            expression = expression.substring(0,expression.length()-1);
            expression += "+";
        }else if(s.charAt(s.length()-1)!= '+') {
            s += "+";
            textField.setText(s);
            expression+=",+";
        }else {
            textField.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener subtract() {
        String s=textField.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '+') {
            String newString = s.substring(0,s.length()-1);
            newString += "-";
            expression = expression.substring(0,expression.length()-1);
            expression += "-";
            textField.setText(newString);
        }else if(s.charAt(s.length()-1)!= '-') {
            s += "-";
            textField.setText(s);
            expression += ",-";
        }else {
            textField.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener multiply() {

        String s=textField.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== '+' || s.charAt(s.length()-1) == (char)(247)) {
            String newString = s.substring(0,s.length()-1);
            newString += "x";
            textField.setText(newString);
            expression = expression.substring(0,expression.length()-1);
            expression += "x";
        }else if(s.charAt(s.length()-1)!= 'x') {
            s += "x";
            textField.setText(s);
            expression+=",x";
        }else {
            textField.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener divide() {
        String s=textField.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1) == '+') {
            String newString = s.substring(0,s.length()-1);
            textField.setText(newString+Character.toString((char)247));
            expression = expression.substring(0,expression.length()-1);
            expression += "/";
        }else if(s.charAt(s.length()-1)!= (char)247) {
            textField.setText(s+Character.toString((char)247));
            expression+=",/";
        }else {
            textField.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener percent() {
        String s=textField.getText();
        if(s.equals("0")) {
            expression+="0";
        }
        if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1) == (char)(247)) {
            String newString = s.substring(0,s.length()-1);
            newString += "%";
            textField.setText(newString);
            expression = expression.substring(0,expression.length()-1);
            expression += "%";
        }else if(s.charAt(s.length()-1)!= '%') {
            s += "%";
            textField.setText(s);
            expression+=",%";
        }else {
            textField.setText(s);
        }
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener equals() {
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
        textField.setText(result);

        expression = result;
        dot=true;
        num=true;
        token.clear();
        return null;
    }

    // Interface C methods.
    // Will have to fix the trig functions in time.Done
    //Hyperbolic Trig functions are functional.
    public static ActionListener sin(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"sin(");
        }else {
            textField.setText("sin(");
        }
        expression+=",sin,(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener cos(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"cos(");
        }else {
            textField.setText("cos(");
        }
        expression+=",cos,(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener tan(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"tan(");
        }else {
            textField.setText("tan(");
        }
        expression+=",tan,(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener sinh(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"sinh(");
        }else {
            textField.setText("sinh(");
        }
        expression+=",sinh,(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener tanh(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"tanh(");
        }else {
            textField.setText("tanh(");
        }
        expression+=",tanh,(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener cosh(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"cosh(");
        }else {
            textField.setText("cosh(");
        }
        expression+=",cosh,(";
        num=false;
        dot=false;
        return null;
    }
    //Interface D methods. All functional
    //COMPLETED
    public static ActionListener power() {
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"^");
            expression+=",^";
        }else {
            textField.setText("0^");
            expression += ",0,^";
        }
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener pi(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+Character.toString((char)960));
        }else {
            textField.setText(Character.toString((char)960));
        }
        expression += ",pi";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener e(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"e");
        }else {
            textField.setText("e");
        }
        expression+=",e";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener sqrt(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+Character.toString((char)8730));
        }else {
            textField.setText(Character.toString((char)8730));
        }
        expression+=",sqrt";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener cbrt(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"∛");
        }else {
            textField.setText("∛");
        }
        expression+=",cbrt";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener root(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+Character.toString((char)8730));
        }else {
            textField.setText(Character.toString((char)8730));
        }
        expression+=",aroot";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener pow10(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"10^");
        }else {
            textField.setText("10^");
        }
        expression+=",10^";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener square(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"^2");
        }else {
            textField.setText("^2");
        }
        expression+=",^2";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener cube(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"^3");
        }else {
            textField.setText("^3");
        }
        expression+=",^3";
        num=false;
        dot=false;
        return null;
    }
    //Interface E methods.
    // All functional
    public static ActionListener rand(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"Rand");
        }else {
            textField.setText("Rand");
        }
        expression+= Math.random();
        return null;
    }
    public static ActionListener log(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"log(");
        }else {
            textField.setText("log(");
        }
        expression+=",log,(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener ln(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"ln(");
        }else {
            textField.setText("ln(");
        }
        expression+=",ln,(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener exp(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"exp(");
        }else {
            textField.setText("exp(");
        }
        expression+=",exp,(";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener rad(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"Rad(");
        }else {
            textField.setText("Rad(");
        }
        expression+=",Rad,(";
        num=false;
        dot=false;
        return null;
    }
    public ActionListener EE(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"Mod");
        }else {
            textField.setText("Mod");
        }
        expression+=",Mod";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener C(){
        textField.setText("");
        expression ="";
        token.clear();
        result="";
        num=false;
        dot=false;
        return null;
    }
    public static ActionListener Del(){
        String s=textField.getText();
        if(s.length()>0) {
            if(s.charAt(s.length()-1)=='.') {
                dot=false;
            }
            if(s.charAt(s.length()-1)== '+' || s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1)== (char)(247)) {
                num=false;
            }
            textField.setText(s.substring(0,s.length()-1));
            expression = expression.substring(0,expression.length()-1);
        }
        return null;
    }
    public static ActionListener Rec(){
        if(! "0".equals(textField.getText())) {
            textField.setText(textField.getText()+"1/");
        }else {
            textField.setText("1/x");
        }
        expression+=",1/";
        num=false;
        dot=false;
        return null;
    }


    public static void main(String[] args) {
        //Create the frame
        JFrame frame = new JFrame("Calculator GROUP 1 Handmade GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setSize(800, 500);


        //Create the display field
        textField.setSize(150, 123);
        textField.setPreferredSize(new Dimension(150, 170));
        textField.setFont(new Font("Arial", Font.PLAIN, 80));
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setForeground(Color.WHITE);
        textField.setBackground(new Color(32, 33, 34));
        textField.enable();
        textField.setEditable(true);
        frame.add(textField, BorderLayout.NORTH);

        //Creating button layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 10, 2, 2));
        buttonPanel.setBackground(new Color(32, 33, 34));

        JPanel scPanel = new JPanel();
        scPanel.setLayout(new GridLayout(5, 5, 6, 6));
        scPanel.setBackground(new Color(52, 54, 57));

        JPanel numPanel = new JPanel();
        numPanel.setLayout(new GridLayout(5, 5, 3, 3));
        numPanel.setBackground(new Color(52, 54, 57));

        JPanel bodPanel = new JPanel();
        bodPanel.setLayout(new GridLayout(5, 5, 1, 1));
        bodPanel.setBackground(Color.GRAY);


        //Create the buttons

        String[] buttons = {"(", ")", "mc", "m+", "m-", "mr", "C", "+/-", "%", "÷",
                "2nd", "x²", "x³", "xʸ", "eˣ", "10ˣ", "7", "8", "9", "X",
                "1/x", "²√x", "³√x", "ʸ√x", "ln", "log", "4", "5", "6", "-",
                "x!", "sin", "cos", "tan", "e", "EE", "1", "2", "3", "+",
                "Rad", "sinh", "cosh", "tanh", "π", "Rand", "0", ".", "DEL", "="};

        Color specialBackground1 = new Color(95, 96, 98);
        Color specialBackground2 = new Color(205, 149, 33);
        Color specialBackground3 = new Color(52, 54, 57);

        for (String buttonlbl : buttons) {
            JButton button = new JButton(buttonlbl);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setBackground(new Color(52, 54, 57));
            button.setForeground(Color.WHITE);
            if (buttonlbl.equals("+") || buttonlbl.equals("-") || buttonlbl.equals("÷") || buttonlbl.equals("X") || buttonlbl.equals("=")) {
                button.setBackground(specialBackground2);
            } else if (buttonlbl.equals("0") || buttonlbl.equals(".") || buttonlbl.equals("1") || buttonlbl.equals("2") || buttonlbl.equals("3") || buttonlbl.equals("4") || buttonlbl.equals("5") || buttonlbl.equals("6") || buttonlbl.equals("7") || buttonlbl.equals("8") || buttonlbl.equals("9") || buttonlbl.equals("DEL")) {
                button.setBackground(specialBackground1);

            } else {
                button.setBackground(specialBackground3);
            }
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(buttonlbl);
                }
            });

                buttonPanel.add(button);
            }

            frame.add(buttonPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        }

    private static void handleButtonClick(String buttonlbl) {
        switch (buttonlbl) {
            case "0":
                zero();
                break;
            case "1":
                one();
                break;
            case "2":
                two();
                break;
            case "3":
                three();
                break;
            case "4":
                four();
                break;
            case "5":
                five();
                break;
            case "6":
                six();
                break;
            case "7":
                seven();
                break;
            case "8":
                eight();
                break;
            case "9":
                nine();
                break;
            case ".":
                point();
                break;
            case "-":
                subtract();
                break;
            case "X":
                multiply();
                break;
            case "÷":
                divide();
                break;
            case "=":
                equals();
                break;
            case "C":
                C();
                break;
            case "DEL":
                Del();
                break;
            case "sin":
                sin();
                break;
            case "cos":
                cos();
                break;
            case "tan":
                tan();
                break;
            case "sinh":
                sinh();
                break;
            case "cosh":
                cosh();
                break;
            case "tanh":
                tanh();
                break;
            case "(":
                openParen();
                break;
            case ")":
                closeParen();
                break;
            case "x²":
                square();
                break;
            case "x³":
                cube();
                break;
            case "xʸ":
                power();
                break;
            case "eˣ":
                exp();
                break;
            case "10ˣ":
                pow10();
                break;
            case "1/x":
                Rec();
                break;
            case "²√x":
                sqrt();
                break;
            case "³√x":
                cbrt();
                break;
            case "ʸ√x":
                root();
                break;
            case "ln":
                ln();
                break;
            case "log":
                log();
                break;
            case "π":
                pi();
                break;
            case "Rand":
                rand();
                break;
            case "Rad":
                rad();
                break;
            case "+":
                addition();
                break;
            case "%":
                percent();
                break;
            case "e":
                e();
                break;
            case "x!":
                if(! "0".equals(textField.getText())) {
                    textField.setText(textField.getText()+"!");
                    expression+=",!";
                }else {
                    textField.setText("0!");
                    expression+=",0,!";
                }
                num=false;
                dot=false;

                break;}

                // Add other cases for different buttons
        }
    }

