package assignment4;
import java.util.*;
import assignment4.Predictor;
import java.lang.*;

class Generalization extends Predictor{
	List History = new Vector();
	List Generation = new Vector();
	List Repeat = new Vector();
        boolean confirm;
	boolean EAGEROUT = false, GenerationOUT = false;
	boolean DEOP = true;
        int index = - 1,i = 0, k = 0, l = 0, remainder = 0;
	String input,operator;
        
    Generalization(Calc calculator)
    {
        super(calculator);
        reset();
    }
//------------- clear all the recorded information in the history list -------------
    void reset()
    {
        History.clear();
        Generation.clear();
        Repeat = null;
        confirm = false;
        EAGEROUT = false;
        GenerationOUT = false;
        DEOP = true;
        index = -1;
        remainder = 0;
        i = 0;
        k = 0;
    }
//----------------------- record actions to history list ----------------------------
    void record(String realaction)
    {
	input = realaction;
	Generation.add(realaction);
        if ((String)Generation.get(0) == "+" || (String)Generation.get(0) == "-" || (String)Generation.get(0) == "*" || (String)Generation.get(0) == "/" || (String)Generation.get(0) == "%" || (String)Generation.get(0) == "1/x" || (String)Generation.get(0) == "x*x" || (String)Generation.get(0) == "sqrt")
        {  GenerationOUT = true;
		   Generation.clear();
	}
	if(Repeat == null)
	{
		index= History.lastIndexOf(realaction);
		if(index <0) History.add(realaction);		
	} 
    }
//-------------------------------- make a prediction ---------------------------------
    String predict()
    {  
//--------------------------------- Eager algorithm ----------------------------------
        if (EAGEROUT != true)
        {
            if(Repeat == null)
                {
                 if(index >= 0)
                 {
                       Repeat = History.subList(index,History.size());
                       log("found repetition in action history: "+input+"\nswitch to confirming mode");
                       if(index != History.size() - 1)
                       {
                               confirm = true;
                               i = 1;
                       }
                       else
                       {
                               confirm=false;
                               i = 0;
                       }
                     }
                    log("Waiting mode:No reptition found!");
                    return null;
                    }
	     else if(confirm)
	     {
                log("Confirming mode:check the action with "+Repeat);
                if(((String)Repeat.get(i)).equals(input))
                {
                  log("action "+input+" confirmed");
                  i++;
                  if(i == Repeat.size())
                  {
                      i=0;
                      confirm = false;
                      log("Confirming completed!Start make prediction.");
                      GenerationOUT = true;
                      return (String)Repeat.get(i);
                   }
                 log("Confiming in progress....");	 
                }
                else 
                {    
                    log("Warning! "+input+" is different from the repetition!");
                    log("Stop Eager!Begin Generation confiming!");
                    EAGEROUT = true;
                    GenerationOUT = false;
                    i = 0;
                    History.clear();
                    History.add(input);
                    Repeat = null;
                }
                return null;
	   }
	  else
	  {
                if(((String)(Repeat.get(i))).equals(input))
                {
                    log("Executing mode: prediction success!");
                    i++;
                    if(i == Repeat.size())
                        i= 0;
                    return (String)Repeat.get(i);
                }
                else
                {
                    log("Warning! "+input+" is different from the repetition!");
                    log("Predoctor reset!");
                    GenerationOUT = false;
                    i = 0;
                    History.clear();
                    Generation.clear();
                    History.add(input);
                    Generation.add(input);
                    Repeat = null;
                }
                return null;
	   }
	}
//-------------------------------- End of Eager algorithm ---------------------------------
//------------------------------- generalization algorithm --------------------------------
	  else if (GenerationOUT != true)
	  {   
              if(DEOP == true)
              {
                  if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/") || input.equals("1/x") || input.equals("%") || input.equals("x*x") || input.equals("sqrt"))
                  {  
                      operator = input;
                      DEOP = false;
                      k = Generation.indexOf(input);
                      Generation.remove(Generation.lastIndexOf(input));
                      i = Generation.lastIndexOf(input);
                      remainder = Integer.parseInt((String)Generation.get(k + 1)) - Integer.parseInt((String)Generation.get(k - 1));
                      l = Integer.parseInt((String)Generation.get(i + 1));
                      if(remainder == Integer.parseInt((String)Generation.get(i + 1)) - Integer.parseInt((String)Generation.get(i - 1)))
                      {    
                          log("Gen prediction-->" + String.valueOf(l + remainder));
                          Generation.add(input);
                          return String.valueOf(l + remainder);
                       }
                   }
                  else 
                  {   
                      Generation.add(input);
                      GenerationOUT = true;
                   }
              }
              else if(input.equals(String.valueOf(l + remainder)))
              {
                 DEOP = true;
                 log("Gen prediction-->" + operator);
                 return operator;
               }
              else GenerationOUT = true;
              return null;
	  }      
	  else
          {     
                reset();
                Generation.add(input);
                History.add(input);
           }
          return null;
//---------------------------- End of Generalization algorithm -----------------------------
    }
}
