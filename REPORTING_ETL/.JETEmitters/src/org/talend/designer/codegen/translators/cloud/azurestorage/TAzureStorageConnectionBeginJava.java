package org.talend.designer.codegen.translators.cloud.azurestorage;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TAzureStorageConnectionBeginJava
{
  protected static String nl;
  public static synchronized TAzureStorageConnectionBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TAzureStorageConnectionBeginJava result = new TAzureStorageConnectionBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\tString myAzureStorageConn_";
  protected final String TEXT_3 = " = \"DefaultEndpointsProtocol=";
  protected final String TEXT_4 = ";\" + \"AccountName=\" + ";
  protected final String TEXT_5 = " + \";\" + \"AccountKey=\" + ";
  protected final String TEXT_6 = ";" + NL + "\ttry {" + NL + "\t\t// Retrieve storage account from connection-string" + NL + "\t\tcom.microsoft.windowsazure.services.core.storage.CloudStorageAccount myAzureStorageAccount_";
  protected final String TEXT_7 = " = com.microsoft.windowsazure.services.core.storage.CloudStorageAccount.parse(myAzureStorageConn_";
  protected final String TEXT_8 = ");" + NL + "\t\tglobalMap.put(\"connection_\" + \"";
  protected final String TEXT_9 = "\", myAzureStorageAccount_";
  protected final String TEXT_10 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_11 = "_ACCOUNT_NAME\", ";
  protected final String TEXT_12 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_13 = "_ACCOUNT_KEY\", ";
  protected final String TEXT_14 = ");";
  protected final String TEXT_15 = NL + "\t\tlog.info(\"";
  protected final String TEXT_16 = " - Create the cloud storage account object successfully.\");";
  protected final String TEXT_17 = NL + "\t} catch (java.lang.Exception e_";
  protected final String TEXT_18 = ") {";
  protected final String TEXT_19 = NL + "\t\t\tthrow e_";
  protected final String TEXT_20 = ";";
  protected final String TEXT_21 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_22 = " - \" + e_";
  protected final String TEXT_23 = ".getMessage());";
  protected final String TEXT_24 = NL + "\t\t\tSystem.err.println(e_";
  protected final String TEXT_25 = ".getMessage());";
  protected final String TEXT_26 = NL + "\t}";
  protected final String TEXT_27 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();

	String cid = node.getUniqueName();
	String accountName = ElementParameterParser.getValue(node, "__ACCESS_KEY__");
	String accountSecret = ElementParameterParser.getValue(node, "__SECRET_KEY__");
	String protocol = ElementParameterParser.getValue(node, "__PROTOCOL__");
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	boolean dieOnError = ("true").equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));

    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(protocol.toLowerCase());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(accountName);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(accountSecret);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(accountName);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(accountSecret);
    stringBuffer.append(TEXT_14);
    
	if(isLog4jEnabled) {

    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
	}

    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    
		if(dieOnError) {

    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    
		} else {
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    
			}

    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    
		}

    stringBuffer.append(TEXT_26);
    stringBuffer.append(TEXT_27);
    return stringBuffer.toString();
  }
}
