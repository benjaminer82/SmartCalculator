package assignment4;
import java.util.*;
import assignment4.Predictor;
import java.lang.*;

public class NGramPredictor extends Predictor
{
        int n;
	List History = new Vector();
        
    NGramPredictor(Calc calculator)
    {
        super(calculator);
	reset();
        n=4;
    }
//----------------------- record actions to history list ----------------------------
    void record(String realaction)
    {
        History.add(realaction);
        log("Action " + realaction+" recorded!");
    }
//------------- clear all the recorded information in the history list --------------
    void reset()
    {
        History.clear();
    }
//-------------------------------- make a prediction ---------------------------------
    String predict()
    {
        if(History.size()<n)
        {
                log( (n - History.size() ) + " actions still needed to make a prediction");
                return null;
         }
        else
        {
            boolean confirm = true;
            for(int i = 0; i < History.size() - (n - 1); i++)
                {
                    while(confirm)
                    {
                        if((History.subList(i,i + (n - 2))).equals(History.subList(History.size() - (n - 1),(History.size() - 1))))
                        {
                            confirm=false;
                            log("Predicting from " + n + "-Gram " + History.subList(i,i + (n - 1)));
                            return (String)History.get(i + (n - 1));
                        }
                        else
                        break;
                     }
                  }
          }
     log("Can't predict yet with the information got so far");
     return null;
}
}
