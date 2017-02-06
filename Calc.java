// UCD -- COMP-UMS3 -- Assignment 4 -- main calculator class
// Nicholas Kushmerick (nick@ucd.ie) -- October 2004
package assignment4;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.math.*;

public class Calc implements ActionListener {
    
    // ** ** ** ** ** ** ** ** ** ** ** ** ** **
    // REGISTER YOUR PREDICTION ALGORITHM BELOW
    // ** ** ** ** ** ** ** ** ** ** ** ** ** **
    Predictor[] predictor = {
	new DoNothingPredictor(),
	new RandomPredictor(this),
	new NGramPredictor(this),
	new EAGERPredictor(this),
        new Generalization(this),
    };
    // ** ** ** ** ** ** ** ** ** ** ** ** ** **
    // REGISTER YOUR PREDICTION ALGORITHM ABOVE
    // ** ** ** ** ** ** ** ** ** ** ** ** ** **

    public static void main(String[] args) {
	Calc calc = new Calc();
    }
 
    // default predictor
    Predictor PREDICTOR = predictor[0];

    // calculator AWT code and layout borrowed from:
    /******************************************************************************
     * Program Name:		Java Calculator Applet
     * Author:				Jason Elias
     * Last Modified:		April 12, 2001
     *****************************************************************************/

    Label LabelMem = new Label("",Label.RIGHT);
    Label display = new Label("0",Label.RIGHT);
    
    Button button1 = new Button("1");
    Button button2 = new Button("2");
    Button button3 = new Button("3");
    Button button4 = new Button("4");
    Button button5 = new Button("5");
    Button button6 = new Button("6");
    Button button7 = new Button("7");
    Button button8 = new Button("8");
    Button button9 = new Button("9");
    Button button0 = new Button("0");
    Button buttonMinus    = new Button("-");
    Button buttonMultiply = new Button("x");
    Button buttonPlus     = new Button("+");
    Button buttonEquals   = new Button("=");
    Button buttonDivide   = new Button("�");
    Button buttonClear    = new Button("C");
    Button buttonDecimal  = new Button(".");
    Button buttonNegative = new Button("�");
    Button buttonMPlus    = new Button("M+");
    Button buttonMClear	  = new Button("MC");
    Button buttonMR	  = new Button("MR");
    Button buttonPercent  = new Button("%");
    Button buttonOneOverX = new Button("1/x");
    Button buttonSqr	  = new Button("x�");
    Button buttonSqrRoot  = new Button("sqrt");
    Button buttonExit     = new Button("exit");
    
    public final int
	OpNone = 10,
	DotButton = 11,
	OpPlus = 12,
	OpMinus = 13,
	OpMult = 14,
	OpDivide = 15,
	OpEquals = 16,
	OpMPlus = 17,
	OpMClear = 18,
	OpMR = 19,
	Button1overx = 20,
	ButtonPercent = 21,
	ButtonSquare = 22,
	ButtonSqrt = 23,
	ButtonNegate = 24,
	ButtonClear = 25;

    String Action[];

    CheckboxMenuItem[] predictormi;

    CheckboxMenuItem roundmi;
    
    TextArea logta;

    boolean ROUNDMODE = true;
    
    // the state of the calculator is captured in terms of the current value (display) and ...
    int pendingOperator = OpNone; // pending operator (or OpNone if none)
    double operand; // pending value (ignored in no pending operator)
    double memory = 0;
    boolean FIRSTDIG = false; // is this the first digit entered after an operator?
    boolean DBZ = false; // did we divide by zero (in which case only the Clear button works)

    Calc() {
	Frame frame = new Frame("Calc");
	Panel panel = new Panel();
	frame.add(panel);
	panel.setLayout(null);
	panel.setSize(420,368);
	panel.setFont(new Font("Helvetica", Font.PLAIN, 12));

	display.setBounds(42,15,308,30);
	display.setFont(new Font("Helvetica", Font.PLAIN, 20));
	display.setForeground(new Color(65280));
	display.setBackground(Color.black);
	panel.add(display);
		
	LabelMem.setBounds(350,15,20,30);
	LabelMem.setFont(new Font("Helvetica", Font.PLAIN, 16));
	LabelMem.setForeground(new Color(65280));
	LabelMem.setBackground(Color.black);
	panel.add(LabelMem);

	button1.addActionListener(this);
	button1.setBounds(42,65,60,34);
	button1.setFont(new Font("Dialog", Font.BOLD, 18));
	button1.setBackground(Color.gray);
	panel.add(button1);

	button2.addActionListener(this);
	button2.setBounds(106,65,60,34);
	button2.setFont(new Font("Dialog", Font.BOLD, 18));
	button2.setBackground(Color.gray);
	panel.add(button2);

	button3.addActionListener(this);
	button3.setBounds(170,65,60,34);
	button3.setFont(new Font("Dialog", Font.BOLD, 18));
	button3.setBackground(Color.gray);
	panel.add(button3);

	button4.addActionListener(this);
	button4.setBounds(42,104,60,34);
	button4.setFont(new Font("Dialog", Font.BOLD, 18));
	button4.setBackground(Color.gray);
	panel.add(button4);

	button5.addActionListener(this);
	button5.setBounds(106,104,60,34);
	button5.setFont(new Font("Dialog", Font.BOLD, 18));
	button5.setBackground(Color.gray);
	panel.add(button5);

	button6.addActionListener(this);
	button6.setBounds(170,104,60,34);
	button6.setFont(new Font("Dialog", Font.BOLD, 18));
	button6.setBackground(Color.gray);
	panel.add(button6);

	button7.addActionListener(this);
	button7.setBounds(42,142,60,34);
	button7.setFont(new Font("Dialog", Font.BOLD, 18));
	button7.setBackground(Color.gray);
	panel.add(button7);

	button8.addActionListener(this);
	button8.setBounds(106,142,60,34);
	button8.setFont(new Font("Dialog", Font.BOLD, 18));
	button8.setBackground(Color.gray);
	panel.add(button8);

	button9.addActionListener(this);
	button9.setBounds(170,142,60,34);
	button9.setFont(new Font("Dialog", Font.BOLD, 18));
	button9.setBackground(Color.gray);
	panel.add(button9);

	button0.addActionListener(this);
	button0.setBounds(42,180,60,34);
	button0.setFont(new Font("Dialog", Font.BOLD, 18));
	button0.setBackground(Color.gray);
	panel.add(button0);

	buttonDecimal.addActionListener(this);
	buttonDecimal.setBounds(106,180,60,34);
	buttonDecimal.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonDecimal.setBackground(Color.gray);
	panel.add(buttonDecimal);

	buttonNegative.addActionListener(this);
	buttonNegative.setBounds(170,180,60,34);
	buttonNegative.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonNegative.setBackground(Color.gray);
	panel.add(buttonNegative);

	buttonMinus.addActionListener(this);
	buttonMinus.setBounds(234,104,60,34);
	buttonMinus.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonMinus.setBackground(Color.gray);
	panel.add(buttonMinus);

	buttonMultiply.addActionListener(this);
	buttonMultiply.setBounds(234,142,60,34);
	buttonMultiply.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonMultiply.setBackground(Color.gray);
	panel.add(buttonMultiply);

	buttonPlus.addActionListener(this);
	buttonPlus.setBounds(234,65,60,34);
	buttonPlus.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonPlus.setBackground(Color.gray);
	panel.add(buttonPlus);

	buttonPercent.addActionListener(this);
	buttonPercent.setBounds(42,230,60,34);
	buttonPercent.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonPercent.setBackground(Color.gray);
	panel.add(buttonPercent);

	buttonOneOverX.addActionListener(this);
	buttonOneOverX.setBounds(106,230,60,34);
	buttonOneOverX.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonOneOverX.setBackground(Color.gray);
	panel.add(buttonOneOverX);

	buttonSqr.addActionListener(this);
	buttonSqr.setBounds(170,230,60,34);
	buttonSqr.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonSqr.setBackground(Color.gray);
	panel.add(buttonSqr);

	buttonSqrRoot.addActionListener(this);
	buttonSqrRoot.setBounds(234,230,60,34);
	buttonSqrRoot.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonSqrRoot.setBackground(Color.gray);
	panel.add(buttonSqrRoot);
		
	buttonEquals.addActionListener(this);
	buttonEquals.setBounds(309,230,60,34);
	buttonEquals.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonEquals.setBackground(Color.gray);
	panel.add(buttonEquals);

	buttonDivide.addActionListener(this);
	buttonDivide.setBounds(234,180,60,34);
	buttonDivide.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonDivide.setBackground(Color.gray);
	panel.add(buttonDivide);

	buttonClear.addActionListener(this);
	buttonClear.setBounds(309,65,60,34);
	buttonClear.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonClear.setForeground(Color.red);
	buttonClear.setBackground(Color.gray);
	panel.add(buttonClear);
	
	buttonMPlus.addActionListener(this);
	buttonMPlus.setBounds(309,104,60,34);
	buttonMPlus.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonMPlus.setForeground(Color.blue);
	buttonMPlus.setBackground(Color.gray);
	panel.add(buttonMPlus);

	buttonMClear.addActionListener(this);
	buttonMClear.setBounds(309,142,60,34);
	buttonMClear.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonMClear.setForeground(Color.blue);
	buttonMClear.setBackground(Color.gray);
	panel.add(buttonMClear);

	buttonMR.addActionListener(this);
	buttonMR.setBounds(309,180,60,34);
	buttonMR.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonMR.setForeground(Color.blue);
	buttonMR.setBackground(Color.gray);
	panel.add(buttonMR);

	buttonExit.addActionListener(this);
	buttonExit.setBounds(42,280,60,34);
	buttonExit.setFont(new Font("Dialog", Font.BOLD, 18));
	buttonExit.setForeground(Color.black);
	buttonExit.setBackground(Color.gray);
	panel.add(buttonExit);

	logta = new TextArea("", 10, 20, TextArea.SCROLLBARS_BOTH);
	logta.setFont(new Font("Monospaced", Font.PLAIN, 10));
	logta.setEditable(false);
	logta.setBounds(42,330,325,130);
	panel.add(logta);

	MenuBar menubar = new MenuBar();
	frame.setMenuBar(menubar);
	Menu omenu = new Menu("Options");
	Menu pmenu = new Menu("Predictor");
	menubar.add(omenu);
	menubar.add(pmenu);
	roundmi = new CheckboxMenuItem("Round");
	roundmi.setState(ROUNDMODE);
	roundmi.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    ROUNDMODE = !ROUNDMODE;
		    roundmi.setState(ROUNDMODE);
		}
	    });
	omenu.add(roundmi);
	predictormi = new CheckboxMenuItem[predictor.length];
	ItemListener il = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    for (int i = 0; i<predictor.length; i++) {
			predictormi[i].setState(false);
		    }
		    String choice = e.getItem().toString();
		    for (int i = 0; i<predictor.length; i++) {
			if (choice.equals(predictor[i].getClass().getName())) {
			    PREDICTOR = predictor[i];
			    log("* Switching to predictor " + choice);
			    PREDICTOR.reset(); // probably couldn't hurt?!
			    predictormi[i].setState(true);
			    reset();
			    break;
			}
		    }
		}
	    };
	for (int i = 0; i < predictor.length; i++) {
	    predictormi[i] = new CheckboxMenuItem(predictor[i].getClass().getName(), i==0);
	    predictormi[i].addItemListener(il);
	    pmenu.add(predictormi[i]);
	}		 
	pmenu.add(new MenuItem("-"));
	MenuItem resetmi = new MenuItem("Reset");
	resetmi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    PREDICTOR.reset();
		    reset();
		}
	    });
	pmenu.add(resetmi);

	Action = new String[ButtonClear+1];
	Action[0] = button0.getLabel();
	Action[1] = button1.getLabel();
	Action[2] = button2.getLabel();
	Action[3] = button3.getLabel();
	Action[4] = button4.getLabel();
	Action[5] = button5.getLabel();
	Action[6] = button6.getLabel();
	Action[7] = button7.getLabel();
	Action[8] = button8.getLabel();
	Action[9] = button9.getLabel();
	Action[DotButton] = buttonDecimal.getLabel();
	Action[OpMinus] = buttonMinus.getLabel();
	Action[OpPlus] = buttonPlus.getLabel();
	Action[OpMult] = buttonMultiply.getLabel();
	Action[OpDivide] = buttonDivide.getLabel();
	Action[OpEquals] = buttonEquals.getLabel();
	Action[OpMPlus] = buttonMPlus.getLabel();
	Action[OpMClear] = buttonMClear.getLabel();
	Action[OpMR] = buttonMR.getLabel();
	Action[Button1overx] = buttonOneOverX.getLabel();
	Action[ButtonPercent] = buttonPercent.getLabel();
	Action[ButtonSquare] = buttonSqr.getLabel();
	Action[ButtonSqrt] = buttonSqrRoot.getLabel();
	Action[ButtonNegate] = buttonNegative.getLabel();
	Action[ButtonClear] = buttonClear.getLabel();

	reset();

	frame.setBounds(20,20, 400+20,500+20);
	frame.show();

    }

    void log(String message) {
	StringBuffer sb = new StringBuffer(logta.getText());
	if (sb.length()>0) sb.append('\n');
	sb.append(message);
	logta.setText(sb.toString());
	logta.setCaretPosition(sb.length());
    }

    void reset() {
	clear();
	clearMemory();
	unhighlight();
    }

    void clear() {
	pendingOperator = OpNone;
	operand = 0;
	display(0);
	DBZ = false;
	FIRSTDIG = false;
    }

    void clearMemory() {
	memorize(0);
    }

    void display(double v) {
	String s = ""+v;
	if (s.endsWith(".0")) s = s.substring(0,s.length()-2);
	display.setText(s);
    }

    void memorize(double m) {
	memory = m;
	LabelMem.setText(m==0 ? "" : "M");
    }

    public void actionPerformed(ActionEvent event)  {
	Object src = event.getSource();
	if (src instanceof Button) {
	    if (src == buttonClear) {
		PREDICTOR.record(Action[ButtonClear]);
		clear();
	    }
	    if (!DBZ) {
		if (src == button0) {
		    addDigit(0);
		}
		if (src == button1) {
		    addDigit(1);
		}
		if (src == button2) {
		    addDigit(2);
		}
		if (src == button3) {
		    addDigit(3);
		}
		if (src == button4) {
		    addDigit(4);
		}
		if (src == button5) {
		    addDigit(5);
		}
		if (src == button6) {
		    addDigit(6);
		}
		if (src == button7) {
		    addDigit(7);
		}
		if (src == button8) {
		    addDigit(8);
		}
		if (src == button9) {
		    addDigit(9);
		}
		if (src == buttonDecimal) {
		    PREDICTOR.record(Action[DotButton]);
		    String d = display.getText();
		    if (FIRSTDIG) {
			display.setText("0.");
			FIRSTDIG = false;
		    } else {
			if (d.indexOf('.')<0) display.setText(d + '.');
		    }
		}
		if (src == buttonPercent) {
		    PREDICTOR.record(Action[ButtonPercent]);
		    display(value()/100);
		}
		if (src == buttonNegative) {
		    PREDICTOR.record(Action[ButtonNegate]);
		    display(-value());
		}
		if (src == buttonSqr) {
		    PREDICTOR.record(Action[ButtonSquare]);
		    double v = value();
		    display(v*v);
		}
		if (src == buttonSqrRoot) {
		    PREDICTOR.record(Action[ButtonSqrt]);
		    display(Math.sqrt(value()));
		}
		if (src == buttonOneOverX) {
		    PREDICTOR.record(Action[Button1overx]);
		    double v = value();
		    if (v==0) {
			dbz();
		    } else {
			display(1/v);
		    }
		}
		if (src == buttonMPlus) {
		    PREDICTOR.record(Action[OpMPlus]);
		    memorize(memory+value());
		}
		if (src == buttonMClear) {
		    PREDICTOR.record(Action[OpMClear]);
		    clearMemory();
		}
		if (src == buttonMR) {
		    PREDICTOR.record(Action[OpMR]);
		    display(memory);
		}
		if (src == buttonExit) {
		    System.exit(0);
		}
		if (src == buttonEquals || src == buttonPlus || src == buttonMinus ||
		    src == buttonMultiply || src == buttonDivide) {
		    double result = 0;
		    if (pendingOperator == OpNone) result = value();
		    if (pendingOperator == OpPlus) result = operand + value();
		    if (pendingOperator == OpMinus) result = operand - value();
		    if (pendingOperator == OpMult) result = operand * value();
		    if (pendingOperator == OpDivide) {
			double v = value();
			if (v==0) {
			    dbz();
			} else {
			    result = operand / value();
			}
		    }
		    if (!DBZ) {
			if (ROUNDMODE) {
			    result = Math.round(result*100000)/100000.0;
			}
			operand = result;
			display(result);
			FIRSTDIG = true;
			int j = -1;
			if (src == buttonEquals) {pendingOperator = OpNone; j =  OpEquals;}
			if (src == buttonPlus) {pendingOperator = OpPlus; j = OpPlus;}
			if (src == buttonMinus) {pendingOperator = OpMinus; j = OpMinus;}
			if (src == buttonMultiply) {pendingOperator = OpMult; j = OpMult;}
			if (src == buttonDivide) {pendingOperator = OpDivide; j = OpDivide;}
			PREDICTOR.record(Action[j]);
		    }
		}
	    }
	    unhighlight();
	    String prediction = PREDICTOR.predict();
	    if (prediction!=null) {
		highlight(prediction);
	    }
	}
    }

    void addDigit(int d) {
	PREDICTOR.record("" + d);
	String x = display.getText();
	if (x.equals(DBZmsg)) {
	    throw new RuntimeException("Yikes! Internal error: Should never be adding a digit during DBZ");
	} else {
	    if (FIRSTDIG) {
		display(d);
		FIRSTDIG = false;
	    } else {
		if (x.length()<15) {
		    if (x.equals("0")) {
			display(d);
		    } else {
			display.setText(x + d);
		    }
		}
	    }
	}
    }

    double value() {
	String x = display.getText();
	if (x.equals(DBZmsg)) {
	    throw new RuntimeException("Yikes! Internal error: Should never be asked for value during DBZ");
	} else {
	    return Double.parseDouble(x);
	}
    }

    final static String DBZmsg = "division by zero";

    void dbz() {
	DBZ = true;
	display.setText(DBZmsg);
    }
    
    void highlight(String prediction) {
	if (prediction==null) return;
	if (prediction.equals(Action[0])) highlight(button0);
	else if (prediction.equals(Action[1])) highlight(button1);
	else if (prediction.equals(Action[2])) highlight(button2);
	else if (prediction.equals(Action[3])) highlight(button3);
	else if (prediction.equals(Action[4])) highlight(button4);
	else if (prediction.equals(Action[5])) highlight(button5);
	else if (prediction.equals(Action[6])) highlight(button6);
	else if (prediction.equals(Action[7])) highlight(button7);
	else if (prediction.equals(Action[8])) highlight(button8);
	else if (prediction.equals(Action[9])) highlight(button9);
	else if (prediction.equals(Action[DotButton])) highlight(buttonDecimal);
	else if (prediction.equals(Action[OpMinus])) highlight(buttonMinus);
	else if (prediction.equals(Action[OpPlus])) highlight(buttonPlus);
	else if (prediction.equals(Action[OpMult])) highlight(buttonMultiply);
	else if (prediction.equals(Action[OpDivide])) highlight(buttonDivide);
	else if (prediction.equals(Action[OpEquals])) highlight(buttonEquals);
	else if (prediction.equals(Action[OpMPlus])) highlight(buttonMPlus);
	else if (prediction.equals(Action[OpMClear])) highlight(buttonMClear);
	else if (prediction.equals(Action[OpMR])) highlight(buttonMR);
	else if (prediction.equals(Action[Button1overx])) highlight(buttonOneOverX);
	else if (prediction.equals(Action[ButtonPercent])) highlight(buttonPercent);
	else if (prediction.equals(Action[ButtonSquare])) highlight(buttonSqr);
	else if (prediction.equals(Action[ButtonSqrt])) highlight(buttonSqrRoot);
	else if (prediction.equals(Action[ButtonNegate])) highlight(buttonNegative);
	else if (prediction.equals(Action[ButtonClear])) highlight(buttonClear);
    }
    
    Button currentlyhighlighted = null;
    
    void highlight(Button button) {
	button.setBackground(Color.yellow);
	currentlyhighlighted = button;
    }

    void unhighlight() {
	if (currentlyhighlighted!=null) {
	    currentlyhighlighted.setBackground(Color.gray);
	}
    }

}
