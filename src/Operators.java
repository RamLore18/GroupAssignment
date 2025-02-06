import java.awt.event.ActionListener;

public abstract class Operators extends BinaryOperators  implements C, D, E{
    public double factorial(double y) {
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
    public ActionListener power() {
        return null;
    }
    public ActionListener sqrt() {
        return null;
    }
    public ActionListener cbrt() {
        return null;
    }
    public ActionListener root() {
        return null;
    }
    public ActionListener pow10() {
        return null;
    }
    public ActionListener sin() {
        return null;
    }
    public ActionListener cos() {
        return null;
    }
    public ActionListener tan() {
        return null;
    }
    public ActionListener sinh() {
        return null;
    }
    public ActionListener cosh() {
        return null;
    }
    public ActionListener tanh() {
        return null;
    }
    public ActionListener rand(){
        return null;
    }
    public ActionListener log() {
        return null;
    }
    public ActionListener ln() {
        return null;
    }
    public ActionListener exp() {
        return null;
    }
    public ActionListener rad() {
        return null;
    }
    public ActionListener EE() {
        return null;
    }
    public ActionListener C() {
        return null;
    }
    public ActionListener Del() {
        return null;
    }

}
