package assignment4;
import java.util.*;
import assignment4.Predictor;
import java.lang.*;
public class EAGERPredictor extends Predictor
{
    String input;
    int index = -1, i = 0;
    List History = new Vector();
    List Repeat = new Vector();

    EAGERPredictor(Calc calculator)
    {
        super(calculator);
        reset();
    }
//------------- clear all the recorded information in the history list -------------
    void reset()
    {
        History.clear();
	Repeat = null;
    }
//----------------------- record actions to history list ----------------------------
    void record(String realaction)
    {   
        input = realaction;
        index = History.lastIndexOf(realaction);
        History.add(realaction); 
    }
//-------------------------------- make a prediction ---------------------------------
    String predict()
    {
        if (Repeat == null)
        {
            if (index != -1)
            {
                Repeat = new Vector();
                for(int i =index; i<=(History.size()-2);i++){
                Repeat.add(History.get(i));
             }
                i = 1;
                log("Find an iteration in action history:"+input+"\nswitch to confirming mode");
            }
            else
                log("Waiting mode: No iteration found!");
                return null;
         }                
        else
        {
            if(i < (Repeat.size() - 1))
            {
                log("Confirming mode:\ncheck if the action "+input+" is from the iteration");
                if(((String)Repeat.get(i)).equals((String)input))
                {
                    log("Comfirming in progress....");
                    i++;
                }
                else 
                {
                    Repeat = null;
                    log("Warning! This "+input+" has broke the iteration. \nWaiting for another iteration");
                }
                return null;
            }
            else if(i == (Repeat.size() - 1))
            {
                if(((String)Repeat.get(i)).equals(input))
                {
                    i++;
                   log("Iteration comfirmed! Start to make prediction.");
                   return (String)Repeat.get(0);
                }
                else 
                {
                    Repeat = null;
                    log("Warning! This "+input+" has broke the iteration. \nWaiting for another iteration");
                }
                return null;
            }
            else
            {
                if(((String)Repeat.get((i - Repeat.size())%Repeat.size())).equals(input))
                {  
                    i++;
                    log("Executing mode: Successfully predicted!");
                    return (String)Repeat.get((i - Repeat.size())%Repeat.size());
                }
                else 
                {
                    Repeat = null;
                    log("Warning! This "+input+" has broke the iteration. \nWaiting for another iteration");
                }
                return null;
            }
        }       
}
}

