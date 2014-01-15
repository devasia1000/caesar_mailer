
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexTester {

    public static void  run(String regex){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        while(true){
        c.add(Calendar.MINUTE, 1);
        Date newDate = c.getTime();
        
        SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd/yy HH:mm");
        String currentString=format.format(newDate);
        Pattern p = Pattern.compile(regex);     
        Matcher m = p.matcher(currentString);
   
        if(m.find()){
            String find=m.group();
            System.out.println(find);        
        }
    
    }
    }
}
