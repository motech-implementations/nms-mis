package org.talend.designer.codegen.translators.bigdata.couchbase;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.Map;
import java.util.List;

public class TCouchbaseConnectionBeginJava
{
  protected static String nl;
  public static synchronized TCouchbaseConnectionBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchbaseConnectionBeginJava result = new TCouchbaseConnectionBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL + "\t\tSystem.setProperty(\"net.spy.log.LoggerImpl\", \"net.spy.memcached.compat.log.Log4JLogger\");" + NL + "\t";
  protected final String TEXT_3 = NL + "\tcom.couchbase.client.CouchbaseClient client_";
  protected final String TEXT_4 = " = null;" + NL + "\tjava.util.ArrayList<java.net.URI> uris_";
  protected final String TEXT_5 = " = new java.util.ArrayList<java.net.URI>();" + NL + "\t";
  protected final String TEXT_6 = NL + "\t\turis_";
  protected final String TEXT_7 = ".add(java.net.URI.create(";
  protected final String TEXT_8 = "));" + NL + "\t";
  protected final String TEXT_9 = NL + "\t";
  protected final String TEXT_10 = NL + "    ";
  protected final String TEXT_11 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_12 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_13 = ");";
  protected final String TEXT_14 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_15 = " = ";
  protected final String TEXT_16 = "; ";
  protected final String TEXT_17 = NL + "\t" + NL + "\t" + NL + "    client_";
  protected final String TEXT_18 = " = new com.couchbase.client.CouchbaseClient(uris_";
  protected final String TEXT_19 = ", ";
  protected final String TEXT_20 = ",";
  protected final String TEXT_21 = ",";
  protected final String TEXT_22 = " decryptedPassword_";
  protected final String TEXT_23 = ");" + NL + "\tglobalMap.put(\"conn_";
  protected final String TEXT_24 = "\",client_";
  protected final String TEXT_25 = ");" + NL + "\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	
	INode node = (INode)codeGenArgument.getArgument();
	
	String cid = node.getUniqueName();
	String dbname = ElementParameterParser.getValue(node, "__DBNAME__");
	String username = ElementParameterParser.getValue(node, "__USER__");

	List<Map<String, String>> URIList = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__URIS__");
	
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	if(isLog4jEnabled){
	
    stringBuffer.append(TEXT_2);
    
	}
	
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
    for(java.util.Map<String,String> map:URIList){
	
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(map.get("URI"));
    stringBuffer.append(TEXT_8);
    
	}
	
    stringBuffer.append(TEXT_9);
    
    String passwordFieldName = "__PASS__";
    
    stringBuffer.append(TEXT_10);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_13);
    } else {
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_16);
    }
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_20);
    if(username!=null && !"".equals(username)){
    stringBuffer.append(username);
    stringBuffer.append(TEXT_21);
    }
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    return stringBuffer.toString();
  }
}
