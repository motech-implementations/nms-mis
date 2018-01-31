package org.talend.designer.codegen.translators.databases.couchdb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.utils.NodeUtil;
import java.util.Map;
import java.util.List;

public class TCouchDBOutputBeginJava
{
  protected static String nl;
  public static synchronized TCouchDBOutputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchDBOutputBeginJava result = new TCouchDBOutputBeginJava();
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
  protected final String TEXT_11 = " = null;" + NL + "\tjava.util.Map<Object,Object> map_";
  protected final String TEXT_12 = "=null;" + NL + "\tcom.google.gson.JsonParser jsonParse_";
  protected final String TEXT_13 = " = new com.google.gson.JsonParser();" + NL + "\tint nb_line_";
  protected final String TEXT_14 = " = 0;" + NL + "\tint nb_line_updated_";
  protected final String TEXT_15 = " = 0;" + NL + "\tint nb_line_inserted_";
  protected final String TEXT_16 = " = 0;" + NL + "\tint nb_line_upserted_";
  protected final String TEXT_17 = " = 0;" + NL + "\tint nb_line_deleted_";
  protected final String TEXT_18 = " = 0;" + NL + "\t";
  protected final String TEXT_19 = NL + "\t\tclient_";
  protected final String TEXT_20 = " = (org.lightcouch.CouchDbClient)globalMap.get(\"client_";
  protected final String TEXT_21 = "\");" + NL + "\t\t";
  protected final String TEXT_22 = "\t" + NL + "\t\t\tif(client_";
  protected final String TEXT_23 = " != null) {" + NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_24 = " - Get an existing client from \" + \"";
  protected final String TEXT_25 = "\" + \".\");" + NL + "\t\t\t}" + NL + "\t\t";
  protected final String TEXT_26 = NL + "\t\tclient_";
  protected final String TEXT_27 = " = new org.lightcouch.CouchDbClient(";
  protected final String TEXT_28 = ", true, \"http\", ";
  protected final String TEXT_29 = ", ";
  protected final String TEXT_30 = ", ";
  protected final String TEXT_31 = ", decryptedPassword_";
  protected final String TEXT_32 = ");" + NL + "\t\t";
  protected final String TEXT_33 = NL + "\t\t\torg.lightcouch.ReplicationResult result_";
  protected final String TEXT_34 = " = null;" + NL + "\t\t\tList<org.lightcouch.Replication> replications_";
  protected final String TEXT_35 = " = new java.util.ArrayList<org.lightcouch.Replication>();" + NL + "\t\t\t";
  protected final String TEXT_36 = NL + "\t\t\t\tresult_";
  protected final String TEXT_37 = " = client_";
  protected final String TEXT_38 = ".replication()" + NL + "\t\t\t\t  .source(";
  protected final String TEXT_39 = ")" + NL + "\t\t\t\t  .target(";
  protected final String TEXT_40 = ") " + NL + "\t\t\t\t  .createTarget(";
  protected final String TEXT_41 = ")" + NL + "\t\t\t\t  .continuous(";
  protected final String TEXT_42 = ")" + NL + "\t\t\t\t  .trigger();" + NL + "\t\t\t\t";
  protected final String TEXT_43 = NL + "\t\t\t\t\tif(result_";
  protected final String TEXT_44 = ".isOk()){" + NL + "\t\t\t\t\t\torg.lightcouch.Replication replication_";
  protected final String TEXT_45 = "_";
  protected final String TEXT_46 = "=" + NL + "\t\t\t\t\t\tclient_";
  protected final String TEXT_47 = ".replication()" + NL + "\t\t\t\t\t\t  .source(";
  protected final String TEXT_48 = ")" + NL + "\t\t\t\t\t\t  .target(";
  protected final String TEXT_49 = ")" + NL + "\t\t\t\t\t\t  .createTarget(";
  protected final String TEXT_50 = ")" + NL + "\t\t\t\t\t\t  .continuous(";
  protected final String TEXT_51 = ")" + NL + "\t\t\t\t\t\t  .cancel(";
  protected final String TEXT_52 = ");" + NL + "\t\t\t\t\t\treplications_";
  protected final String TEXT_53 = ".add(replication_";
  protected final String TEXT_54 = "_";
  protected final String TEXT_55 = ");" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_56 = NL + "\t\tlog.info(\"";
  protected final String TEXT_57 = " - Start to manipulate data in database [\" +";
  protected final String TEXT_58 = "+ \"].\");" + NL + "\t";

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
    boolean useExistingConn = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
    String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
    
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    
	if(useExistingConn){
	 	INode connectionNode=NodeUtil.getNodeByUniqueName(node.getProcess(),connection);
	 	dbname = ElementParameterParser.getValue(connectionNode, "__DATABASE__");
	
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_21);
    
		if(isLog4jEnabled){
		
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_25);
    
		}
	}else{
	
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    
		if(useTriggerReplicate){
		
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    
			int index= 0;
			for(Map<String,String> targetDB:replicaTargetDB){
				String targetDBName=targetDB.get("TARGET_DB_NAME");
				boolean isCreateTargetDB = "true".equalsIgnoreCase(targetDB.get("CREATE_TARGET"));
				boolean isContinuous = "true".equalsIgnoreCase(targetDB.get("CONTINUOUS"));
				boolean isCancel = "true".equalsIgnoreCase(targetDB.get("CANCEL_REPLICATE"));
				
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(targetDBName);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(isCreateTargetDB);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(isContinuous);
    stringBuffer.append(TEXT_42);
    
				if(isContinuous && isCancel){
				
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(targetDBName);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(isCreateTargetDB);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(isContinuous);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(isCancel);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_55);
    
					index++;
				}
			}
		}
	}
	if (isLog4jEnabled) {
	
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_58);
    
	}
	
    return stringBuffer.toString();
  }
}
