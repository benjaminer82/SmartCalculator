package assignment4;
class RandomPredictor extends Predictor {

    RandomPredictor(Calc _calc) {
	super(_calc);
    }

    void record(String action) {
	log("* RandomPredictor: ignoring action " + action);
    }

    void reset() {
	log("* RandomPredictor: nothing to reset!");
    }

    String predict() {
	String result = calc.Action[(int)(Math.random()*calc.Action.length)];
	log("* RandomPredictor: predicting action " + result);
	return result;
    }

}
