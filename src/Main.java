
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Main {
    
    static ArrayList<String> regexList=new ArrayList<String>();
    static ArrayList<String> commandList=new ArrayList<String>();

    public static void main(String args[]) throws Exception{
        
        if(args.length>4){
            System.out.println("Usage #1: java -jar <nameofprogramfile>.jar <email_address> <password> <directory_to_find_textfiles> <directory_to_place_files_after_processing>");
            System.out.println("Usage #2: java -jar <nameofprogramfile>.jar regextest <regexExpression>");
            System.exit(0);
        }
        
        else if(args[0].toLowerCase().equals("regextest")){
            String regex=args[1]+" "+args[2]+" "+args[3];
            RegexTester.run(regex);
        }
        
        try{
            MessageSender.init(args[0], args[1]);
        } catch (Exception e){
            e.printStackTrace();
        }
        
        while(true){
        
        File f=new File(args[2]);
        File s[]=f.listFiles();
        
        System.out.println("Analyzing Files");
        for(File temp:s){
            if(temp.getPath().toLowerCase().endsWith(".txt")){
                try{
                    BufferedReader rd=new BufferedReader(new FileReader(temp.getPath()));
                    String sendlist="", subject="", message="", temp2=null, date="";
                    date=rd.readLine();
                    sendlist=rd.readLine();
                    subject=rd.readLine();
                    while((temp2=rd.readLine())!=null){
                        message=message+temp2+"\n";
                    }
                    rd.close();

                    
                    SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd/yy HH:mm");
                    try{
                        Date d1=format.parse(date);
                        System.out.println(date);
                        Date current=new Date();
                        System.out.println(d1+", "+current);
                        
                        if(d1.compareTo(current)<0){
                    
                            String[] sendTo=sendlist.split(",");
                            for(int i=0;i<sendTo.length;i++){
                                sendTo[i].replaceAll(",", "");
                                sendTo[i]=sendTo[i].trim();
                            }
                    
                            MessageSender.sendSSLMessage(sendTo, subject, message, "Professor Caesar");
                            
                            String t=temp.getName();
                     if(!args[3].endsWith("/") || !args[3].endsWith("\\")){
                                args[3]=args[3]+"/";
                            }
                            
                            
                            File moveDest=new File(args[3]+t);
                            
                            
                            BufferedReader rd2=new BufferedReader(new FileReader(temp));
                            BufferedWriter wt=new BufferedWriter(new FileWriter(moveDest));
                            String mess=null;
                            while((mess=rd2.readLine())!=null){
                                wt.write(mess+"\n");
                                wt.flush();
                            }
                            wt.close();
                            rd2.close();
                            boolean check=temp.delete();
                            if(!check){
                                temp.delete();
                                JOptionPane.showMessageDialog(new JFrame(), "Security Exception. Unable to move file. Please notify Devasia Manuel", "Automatic E-Mailer Error", JOptionPane.ERROR_MESSAGE);
                                throw new Exception();
                            }
                        }
                        
                    } catch (ParseException e){
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(new JFrame(), "Date format in "+temp.getPath()+" is incorrect. Please correct it", "Automatic E-Mailer Errow", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (Exception e){
                    e.printStackTrace();
                }
                
            }
            
            else if(temp.getPath().toLowerCase().endsWith(".regex")){
                
                if(!regexList.contains(temp.getPath())){
                    System.err.println("New Regex Email Thread Created!");
                    regexList.add(temp.getPath());
                    Runnable r = new RegexDateManager(temp);
                    new Thread(r).start();
                }
            }
            
            else if(temp.getPath().toLowerCase().endsWith(".command")){
                
                if(!commandList.contains(temp.getPath())){
                    System.err.println("New Command Regex Thread Created");
                    commandList.add(temp.getPath());
                    Runnable r=new CommandDateManager(temp);
                    new Thread(r).start();
                }
            }
        }
        
        System.out.println("Sleeping for 20 seconds");
        try{Thread.sleep(1000*20);}catch(Exception e){}
        }
    }
}

