package assignment4;
abstract class Predictor {

    Predictor() {
	super();
    }

    Predictor(Calc _calc) {
	this();
	calc = _calc;
    }

    Calc calc;

    // your predictor can call this method if it wants to print out
    // any messages (eg, for debugging)
    void log(String message) {
	calc.log(message);
    }

    // your predictor needs to implement these three methods
    abstract void record(String action);
    abstract void reset();
    abstract String predict();

}
