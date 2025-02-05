import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

public class Calculator {
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
    private JLabel exprlabel;
    private JPanel CalculatorP;


    private String expression="";
    private boolean num=false;
    private boolean dot=false;

    private ArrayList<String> token=new ArrayList<String>();
    private String result="";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Calculator().CalculatorP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


    // Button colors and styles
    Color buttonBackground = new Color(50, 50, 50);
    Color specialBackground = new Color(255, 140, 0);


    int precedence(String x)
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
        if(x.equals("+") || x.equals("-") || x.equals("x") || x.equals("/") || x.equals("sqrt") || x.equals("^") || x.equals("!") || x.equals("sin") || x.equals("cos") || x.equals("tan") || x.equals("ln") || x.equals("log"))
            return true;
        else
            return false;
    }

    private String infixTopostfix()
    {
        Stack<String> s=new Stack<String>();
        String y;
        int flag;
        String p="";
        token.add(")");
        s.push("(");
        for(String i: token) {
            if(i.equals("(")){
                s.push(i);
            }else if(i.equals(")")){
                y=s.pop();
                while(!y.equals("("))
                {
                    p=p+y+",";
                    y=s.pop();
                }
            }else if(isoperator(i)){
                y=s.pop();
                flag=0;
                if(isoperator(y) && precedence(y)>precedence(i)){
                    p=p+y+",";
                    flag=1;
                }
                if(flag==0)
                    s.push(y);

                s.push(i);
            }else{
                p=p+i+",";
            }
        }
        while(!s.empty()) {
            y=s.pop();
            if(!y.equals("(") && !y.equals(")")) {
                p+=y+",";
            }
        }
        return p;
    }

    //factorial method
    private double factorial(double y) {
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
                res= Math.sin(y);
                break;
            case "cos":
                res = Math.cos(y);
                break;
            case "tan":
                res =Math.tan(y);
                break;
            case "ln":
                res= Math.log(y);
                break;
            case "sqrt":
                res= Math.sqrt(y);
                break;
            case "!":
                res=factorial(y);
                break;
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
                if(i.equals("sin") ||i.equals("cos") ||i.equals("tan") ||i.equals("log") || i.equals("ln") || i.equals("sqrt") || i.equals("!")) {
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

    public Calculator() {

        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("0");
                exprlabel.setText("");
                expression ="";
                token.clear();
                result="";
                num=false;
                dot=false;
            }
        });
        πButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+Character.toString((char)960));
                }else {
                    textField1.setText(Character.toString((char)960));
                }
                expression += ",pi";
                num=false;
                dot=false;
            }
        });

        xyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"^");
                    expression+=",^";
                }else {
                    textField1.setText("0^");
                    expression += ",0,^";
                }
                num=false;
                dot=false;
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
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"sin(");
                }else {
                    textField1.setText("sin(");
                }
                expression+=",sin,(";
                num=false;
                dot=false;
            }
        });

        button18.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"(");
                }else {
                    textField1.setText("(");
                }
                expression+=",(";
                num=false;
                dot=false;
            }
        });

        button19.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+")");
                }else {
                    textField1.setText(")");
                }
                expression+=",)";
                num=false;
                dot=false;
            }
        });

        eButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"e");
                }else {
                    textField1.setText("e");
                }
                expression+=",e";
                num=false;
                dot=false;
            }
        });

        xButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+Character.toString((char)8730));
                }else {
                    textField1.setText(Character.toString((char)8730));
                }
                expression+=",sqrt";
                num=false;
                dot=false;
            }
        });
        cosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"cos(");
                }else {
                    textField1.setText("cos(");
                }
                expression+=",cos,(";
                num=false;
                dot=false;
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        tanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"tan(");
                }else {
                    textField1.setText("tan(");
                }
                expression+=",tan,(";
                num=false;
                dot=false;
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
            }
        });
        lnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"ln(");
                }else {
                    textField1.setText("ln(");
                }
                expression+=",ln,(";
                num=false;
                dot=false;
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        log10Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! "0".equals(textField1.getText())) {
                    textField1.setText(textField1.getText()+"log(");
                }else {
                    textField1.setText("log(");
                }
                expression+=",log,(";
                num=false;
                dot=false;
            }
        });
        button16.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                exprlabel.setText(s+"=");
                textField1.setText(result);

                expression = result;
                dot=true;
                num=true;
                token.clear();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }
}
