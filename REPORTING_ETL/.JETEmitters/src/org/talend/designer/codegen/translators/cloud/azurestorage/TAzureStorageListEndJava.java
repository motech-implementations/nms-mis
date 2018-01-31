package org.talend.designer.codegen.translators.cloud.azurestorage;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TAzureStorageListEndJava
{
  protected static String nl;
  public static synchronized TAzureStorageListEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TAzureStorageListEndJava result = new TAzureStorageListEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t} catch (java.lang.Exception e_";
  protected final String TEXT_3 = ") {";
  protected final String TEXT_4 = NL + "\t\t\tthrow(e_";
  protected final String TEXT_5 = ");";
  protected final String TEXT_6 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_7 = " - \" + e_";
  protected final String TEXT_8 = ".getMessage());";
  protected final String TEXT_9 = NL + "\t\t\tSystem.err.println(e_";
  protected final String TEXT_10 = ".getMessage());";
  protected final String TEXT_11 = NL + "\t}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();

	String cid = node.getUniqueName();
	boolean useExistingConnection = "true".equals(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"));
	String accountName = ElementParameterParser.getValue(node, "__ACCESS_KEY__");
	String accountSecret = ElementParameterParser.getValue(node, "__SECRET_KEY__");
	String protocol = ElementParameterParser.getValue(node, "__PROTOCOL__");
	
	String container = ElementParameterParser.getValue(node, "__CONTAINER__");
	String blobName = ElementParameterParser.getValue(node, "__BLOB_NAME__");
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	

    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    
		if (dieOnError) {

    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
		} else {
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    
			}

    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    
		}

    stringBuffer.append(TEXT_11);
    return stringBuffer.toString();
  }
}
