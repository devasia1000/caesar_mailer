
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


public class RegexDateManager implements Runnable{

    File filename;
    
    ArrayList<String> sentDates=new ArrayList<String>();
    
    public RegexDateManager(File temp){
        filename=temp;
    }

    @Override
    public void run() {
        if(filename.exists()){
        while(true){
            try{
            BufferedReader rd=new BufferedReader(new FileReader(filename.getPath()));
            String sendlist="", subject="", message="", temp2=null, date="";
            date=rd.readLine();
            sendlist=rd.readLine();   
            subject=rd.readLine();
   
            while((temp2=rd.readLine())!=null){     
                message=message+temp2+"\n"; 
            }
            rd.close();
                    
            String[] sendList=sendlist.split(",");
            for(int i=0;i<sendList.length;i++){
                sendList[i].replaceAll(",", "");
                sendList[i]=sendList[i].trim();    
            }
            
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
                                System.out.println("Email Sent because "+currentDate+" is less than "+new Date().toString()); 
                                for(String send:sendList){
                                    System.err.println(send+"Size: "+sendList.length);
                                }
                                MessageSender.sendSSLMessage(sendList, subject, message, "Professor Caesar");
                            }
                           
                        }
                    
                    }
                    
                }
            
            } catch (ParseException e){
                JOptionPane.showMessageDialog(new JFrame(), "Date format in "+filename.getPath()+" is incorrect. Please correct it", "Automatic E-Mailer Errow", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            
            catch (Exception e){
                e.printStackTrace();
            }
            
            try{Thread.sleep(1000);} catch (Exception e){}
            
            if(sentDates.size()>100){
                sentDates=new ArrayList<String>();
            }
        }
        
    }
    }
}
