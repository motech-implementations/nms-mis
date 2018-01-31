package org.talend.designer.codegen.translators.databases.mongodb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TMongoDBCloseMainJava
{
  protected static String nl;
  public static synchronized TMongoDBCloseMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMongoDBCloseMainJava result = new TMongoDBCloseMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\tcom.mongodb.Mongo mongo_";
  protected final String TEXT_2 = "=(com.mongodb.Mongo)globalMap.get(\"mongo_";
  protected final String TEXT_3 = "\");" + NL + "\tif(mongo_";
  protected final String TEXT_4 = " != null )" + NL + "\t{" + NL + "\t\t";
  protected final String TEXT_5 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_6 = " - Closing the connection \" + mongo_";
  protected final String TEXT_7 = ".getServerAddressList() + \".\");" + NL + "\t\t";
  protected final String TEXT_8 = NL + "\t\t\tmongo_";
  protected final String TEXT_9 = ".close();" + NL + "\t\t";
  protected final String TEXT_10 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_11 = " - Connection closed.\");" + NL + "\t\t";
  protected final String TEXT_12 = NL + "\t}" + NL + "\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();

    String cid = node.getUniqueName();

   	String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    
		if(isLog4jEnabled){
		
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    
		}
		
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    
		if(isLog4jEnabled){
		
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    
		}
		
    stringBuffer.append(TEXT_12);
    return stringBuffer.toString();
  }
}
