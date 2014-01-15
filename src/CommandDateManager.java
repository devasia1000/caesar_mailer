
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CommandDateManager implements Runnable{
    
    File filename;
    
    ArrayList<String> sentDates=new ArrayList<String>();
    
    public CommandDateManager(File temp){
        filename=temp;
    }

    @Override
    public void run() {
        while(true){
            try{
            BufferedReader rd=new BufferedReader(new FileReader(filename.getPath()));
            String date=rd.readLine();
            String command=rd.readLine();

            rd.close();
            
            System.out.println("In Loop");
            Date d=new Date();
            SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd/yy HH:mm");
            String currentString=format.format(d);
            System.out.println("Current Formatted Date: "+currentString);
            
            Pattern p = Pattern.compile(date);
            Matcher m = p.matcher(currentString);
            if(m.find()){
                String find=m.group();
                System.out.println("Pattern Matches");
                if(find.equals(currentString)){
                    
                        Date currentDate=format.parse(currentString);
                        System.out.println("Current Parsed Date: "+currentDate);
                        if(currentDate.before(new Date())){
                            if(!sentDates.contains(currentString)){
                                sentDates.add(currentString);
                                System.out.println("Command activated because "+currentDate+" is less than "+new Date().toString()); 
                                JavaRunCommand.run(command);
                            }
                           
                        }
                    
                    }
                    
                }
            
            } catch (ParseException e){
                JOptionPane.showMessageDialog(new JFrame(), "Date format in "+filename.getPath()+" is incorrect. Please correct it", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            
            catch (Exception e){
                JOptionPane.showMessageDialog(new JFrame(), "Unrecoverable exception. Please send stack trace to author", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            
            if(sentDates.size()>100){
                sentDates=new ArrayList<String>();
            }
        }
    }
}
