package hl7Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;

public class Executor {
	static String line;
	
    public static void main(String[] args) throws Exception {
    	 String hl7Message="";
    	File[] files =  new File("hl7_messages").listFiles((dir, name)->name.matches(".*.txt"));
    	if(files!=null && files.length>0) {
			for (File file:files) {
				
				 	hl7Message=readFile(file);
			
				 	System.out.println(hl7Message);
			        HapiContext hapiContext = new DefaultHapiContext();
			        Connection connection = hapiContext.newClient("127.0.0.1", 2575, false);
			        Initiator initiator = connection.getInitiator();
			        PipeParser pipeParser = new PipeParser();
			        Message SIU12 = pipeParser.parse(hl7Message);
			        
			        
			      /*  int timeout = 30000;
                    System.setProperty("ca.uhn.hl7v2.app.initiator.timeout",  Integer.toString(timeout));
                    Initiator init = connection.getInitiator();
                    init.setTimeoutMillis(timeout);  */
			         
			        Message response = initiator.sendAndReceive(SIU12);
			        System.out.println("Response: " + response.toString());
			        connection.close();
			}
       
         
    }
}
    private static String readFile(File file) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
             
            while ((line = bf.readLine()) != null) {
                content.append(line).append("\r");
            }
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }}




