import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public class MultiThread extends Thread
{
    private List<String> subIds;
    public MultiThread(List<String> subIds){
        this.subIds=subIds;
    }
    @Override
    public void run()    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1, d2 ;
        int w1, w2;
        long listenedDuration, targetDuration, userlistenedDuration, usertargetDuration;
        int counter;
        boolean terminate;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection e = DriverManager.getConnection("jdbc:mysql://192.168.200.67:3306/Reporting", "etluser", "etl@2015!");
            Statement stmt2 = e.createStatement();
            String ids;
            for(String s: subIds) {
                d2 = null;
                counter=0;
                listenedDuration =0;
                targetDuration =0;
                terminate = false;
                w2=0;
                ResultSet rs2 = stmt2.executeQuery("select * from Reporting.continuous_6_calls_answered_call_details where subscription_id = " +s);

                while(rs2.next()) {
                    if(!terminate) {
                        d1=d2;
                        w1=w2;
                        d2=sdf.parse(rs2.getString(4));
                        if(rs2.getString(5) == null) {
                            userlistenedDuration=0;
                        }
                        else {
                            userlistenedDuration = Long.parseLong(rs2.getString(5));
                        }
                        usertargetDuration = Long.parseLong(rs2.getString(6));
                        w2= Integer.parseInt(rs2.getString(8));
                        if(d1 == null && w1 ==0) {
                            listenedDuration += userlistenedDuration;
                            targetDuration  += usertargetDuration;
                            counter++;
                        }
                        if(d1 != null && w1 !=0) {
                            long difference = d2.getTime() - d1.getTime();
                            long daysBetween = (difference / (1000*60*60*24));
                            if (w1==w2 && daysBetween <=3) { //If multiple success calls on the same week
                                listenedDuration += userlistenedDuration;
                                targetDuration  += usertargetDuration;
                            }else {
                                w1+=1;
                                if(daysBetween>= 4 && daysBetween <= 10 && w1==w2) {
                                    counter++;
                                    listenedDuration += userlistenedDuration;
                                    targetDuration  += usertargetDuration;
                                } else {
                                    if(counter>=6) { 		 	          	    		//Terminate the current result set
                                        terminate=true;
                                    }
                                    else {
                                        counter=0;
                                        listenedDuration=userlistenedDuration;
                                        targetDuration=usertargetDuration;
//                                        w2=0;
//                                        d2=null;
                                        counter++;
                                    }
                                }// days between 4 & 10
                            }//else
                        }
                    }//terminate if loop
                }// rs2
                if(counter>=6) {
                    System.out.println( s + "," +  listenedDuration + "," + targetDuration+ ",success");
                }
                else {
                    System.out.println(s+",0,0,failure");
                }
            }//



        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
    }
}
