import javax.xml.transform.Result;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        File file = new File("/home/grameen", "outputfile.csv");
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        writer.write("subscriptionId,listenedDuration,targetDuration,status");
        writer.write("\n");
        writer.close();
        Class.forName("com.mysql.jdbc.Driver");
        Connection e = DriverManager.getConnection("jdbc:mysql://192.168.200.68:3306/Reporting", "etluser", "etl@2015!");
        Statement stmt1 = e.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select * from Reporting.continuous_6_calls_answered");
        List<String> subscriptionIds = new ArrayList<>();
        int count = 0;
        while(rs1.next()) {
            subscriptionIds.add(rs1.getString(1));
        }
        List<List<String>> subscriptionIdsArray = new ArrayList<>();
        while(count<subscriptionIds.size()){
            List<String> subscriptionIdsPart = new ArrayList<>();
            while(subscriptionIdsPart.size()<50000 && count<subscriptionIds.size()){
                subscriptionIdsPart.add(subscriptionIds.get(count));
//        subscriptionIdsPart.add("224479502");
                count++;
            }
            subscriptionIdsArray.add(subscriptionIdsPart);
        }
        for (List<String> s : subscriptionIdsArray) {
            MultiThread object = new MultiThread(s);
            object.start();
        }
    }
}
