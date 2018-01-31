package org.talend.designer.codegen.translators.databases.couchdb;

import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.ElementParameterParser;
import java.util.Map;

public class TCouchDBOutputEndJava
{
  protected static String nl;
  public static synchronized TCouchDBOutputEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchDBOutputEndJava result = new TCouchDBOutputEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL + "\t\tif(client_";
  protected final String TEXT_3 = " != null){" + NL + "\t\t\t";
  protected final String TEXT_4 = NL + "\t\t\t\tif(replications_";
  protected final String TEXT_5 = " != null && replications_";
  protected final String TEXT_6 = ".size()>0){" + NL + "\t\t\t\t\tfor(org.lightcouch.Replication replication_";
  protected final String TEXT_7 = " : replications_";
  protected final String TEXT_8 = "){" + NL + "\t\t\t\t\t\treplication_";
  protected final String TEXT_9 = ".trigger();" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_10 = NL + "\t\t\tclient_";
  protected final String TEXT_11 = ".shutdown();" + NL + "\t\t}  " + NL + "\t";
  protected final String TEXT_12 = NL + "\tint nb_line_failed_";
  protected final String TEXT_13 = " = nb_line_";
  protected final String TEXT_14 = " - nb_line_updated_";
  protected final String TEXT_15 = " - nb_line_inserted_";
  protected final String TEXT_16 = " " + NL + "\t\t- nb_line_deleted_";
  protected final String TEXT_17 = ";" + NL + "\tnb_line_upserted_";
  protected final String TEXT_18 = " = nb_line_updated_";
  protected final String TEXT_19 = " + nb_line_inserted_";
  protected final String TEXT_20 = ";" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_21 = "_NB_LINE\",nb_line_";
  protected final String TEXT_22 = ");" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_23 = "_NB_LINE_UPDATED\",nb_line_updated_";
  protected final String TEXT_24 = ");" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_25 = "_NB_LINE_INSERTED\",nb_line_inserted_";
  protected final String TEXT_26 = ");" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_27 = "_NB_LINE_UPSERTED\",nb_line_upserted_";
  protected final String TEXT_28 = ");" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_29 = "_NB_LINE_DELETED\",nb_line_deleted_";
  protected final String TEXT_30 = ");" + NL + "\t";
  protected final String TEXT_31 = NL + "\t\tlog.info(\"";
  protected final String TEXT_32 = " - Has ";
  protected final String TEXT_33 = " \" + nb_line_";
  protected final String TEXT_34 = "_";
  protected final String TEXT_35 = " + \" records.\");" + NL + "\t\tlog.info(\"";
  protected final String TEXT_36 = " - \" + nb_line_failed_";
  protected final String TEXT_37 = " + \" records failed.\");" + NL + "\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
	String dest_cid = cid.replace("_Out", "");
    boolean useTriggerReplicate="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__USE_TRIGGER_REPLICATE__"));
    boolean useExistingConn = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");
    
	if(!useExistingConn){
	
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    
			if(useTriggerReplicate){
			
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    
			}
			
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    
	}
	
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(dest_cid );
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(dest_cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(dest_cid );
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(dest_cid );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(dest_cid );
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    
	if(isLog4jEnabled){
		Map<String,String> actionMap=new java.util.HashMap<String,String>();
		actionMap.put("INSERT","inserted");
		actionMap.put("UPDATE","updated");
		actionMap.put("DELETE","deleted");
		actionMap.put("UPSERT","upserted");
		
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(actionMap.get(dataAction));
    stringBuffer.append(TEXT_33);
    stringBuffer.append(actionMap.get(dataAction));
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    
	}
	
    return stringBuffer.toString();
  }
}
