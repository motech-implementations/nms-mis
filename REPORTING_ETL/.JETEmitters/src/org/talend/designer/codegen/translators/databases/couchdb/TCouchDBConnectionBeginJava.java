package org.talend.designer.codegen.translators.databases.couchdb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.Map;
import java.util.List;

public class TCouchDBConnectionBeginJava
{
  protected static String nl;
  public static synchronized TCouchDBConnectionBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchDBConnectionBeginJava result = new TCouchDBConnectionBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL + " \tString decryptedPassword_";
  protected final String TEXT_3 = " = null;" + NL + " \t";
  protected final String TEXT_4 = " " + NL + "    \tdecryptedPassword_";
  protected final String TEXT_5 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_6 = ");";
  protected final String TEXT_7 = NL + "    \tdecryptedPassword_";
  protected final String TEXT_8 = " = ";
  protected final String TEXT_9 = "; ";
  protected final String TEXT_10 = NL + "\torg.lightcouch.CouchDbClient client_";
  protected final String TEXT_11 = " = new org.lightcouch.CouchDbClient(";
  protected final String TEXT_12 = ", true, \"http\", ";
  protected final String TEXT_13 = ", ";
  protected final String TEXT_14 = ", ";
  protected final String TEXT_15 = ", decryptedPassword_";
  protected final String TEXT_16 = ");" + NL + "\t";
  protected final String TEXT_17 = NL + "\t\torg.lightcouch.ReplicationResult result_";
  protected final String TEXT_18 = " = null;" + NL + "\t\tList<org.lightcouch.Replication> replications_";
  protected final String TEXT_19 = " = new java.util.ArrayList<org.lightcouch.Replication>();" + NL + "\t\t";
  protected final String TEXT_20 = NL + "\t\t\tresult_";
  protected final String TEXT_21 = " = client_";
  protected final String TEXT_22 = ".replication()" + NL + "\t\t\t  .source(";
  protected final String TEXT_23 = ")" + NL + "\t\t\t  .target(";
  protected final String TEXT_24 = ") " + NL + "\t\t\t  .createTarget(";
  protected final String TEXT_25 = ")" + NL + "\t\t\t  .continuous(";
  protected final String TEXT_26 = ")" + NL + "\t\t\t  .trigger();" + NL + "\t\t\t";
  protected final String TEXT_27 = NL + "\t\t\t\tif(result_";
  protected final String TEXT_28 = ".isOk()){" + NL + "\t\t\t\t\torg.lightcouch.Replication replication_";
  protected final String TEXT_29 = "_";
  protected final String TEXT_30 = "=" + NL + "\t\t\t\t\tclient_";
  protected final String TEXT_31 = ".replication()" + NL + "\t\t\t\t\t  .source(";
  protected final String TEXT_32 = ")" + NL + "\t\t\t\t\t  .target(";
  protected final String TEXT_33 = ")" + NL + "\t\t\t\t\t  .createTarget(";
  protected final String TEXT_34 = ")" + NL + "\t\t\t\t\t  .continuous(";
  protected final String TEXT_35 = ")" + NL + "\t\t\t\t\t  .cancel(";
  protected final String TEXT_36 = ");" + NL + "\t\t\t\t\treplications_";
  protected final String TEXT_37 = ".add(replication_";
  protected final String TEXT_38 = "_";
  protected final String TEXT_39 = ");" + NL + "\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_40 = NL + "\t\tglobalMap.put(\"replications_";
  protected final String TEXT_41 = "\", replications_";
  protected final String TEXT_42 = ");" + NL + "\t";
  protected final String TEXT_43 = NL + "\tglobalMap.put(\"client_";
  protected final String TEXT_44 = "\", client_";
  protected final String TEXT_45 = ");" + NL + "\t";
  protected final String TEXT_46 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;

    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    String dbhost = ElementParameterParser.getValue(node, "__HOST__");
    String dbname = ElementParameterParser.getValue(node, "__DATABASE__");
    String dbuser = ElementParameterParser.getValue(node, "__USERNAME__");
    String dbpass = ElementParameterParser.getValue(node, "__PASSWORD__");
    boolean authentication="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REQUIRED_AUTHENTICATION__"));
    if (!authentication){
    	dbuser = null;
    	dbpass = null;
    }
    
    String passwordFieldName = "__PASSWORD__";
    
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    if(!authentication) {
 	   //do nothing
    } else if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_6);
    } else {
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_9);
    }
    
    boolean useTriggerReplicate="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__USE_TRIGGER_REPLICATE__"));
    String dbport  	= ElementParameterParser.getValue(node, "__PORT__");
    List<Map<String,String>> replicaTargetDB= (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__REPLICATE_TARGET_DB__");

	
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
	if(useTriggerReplicate){
	
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
		int index= 0;
		for(Map<String,String> targetDB:replicaTargetDB){
			String targetDBName=targetDB.get("TARGET_DB_NAME");
			boolean isCreateTargetDB = "true".equalsIgnoreCase(targetDB.get("CREATE_TARGET"));
			boolean isContinuous = "true".equalsIgnoreCase(targetDB.get("CONTINUOUS"));
			boolean isCancel = "true".equalsIgnoreCase(targetDB.get("CANCEL_REPLICATE"));
			
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(targetDBName);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(isCreateTargetDB);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(isContinuous);
    stringBuffer.append(TEXT_26);
    
			if(isContinuous && isCancel){
			
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(targetDBName);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(isCreateTargetDB);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(isContinuous);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(isCancel);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_39);
    
				index++;
			}
		}
		
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    
	}
	
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(TEXT_46);
    return stringBuffer.toString();
  }
}
