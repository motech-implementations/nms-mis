package org.talend.designer.codegen.translators.bigdata.marklogic;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TMarkLogicConnectionBeginJava
{
  protected static String nl;
  public static synchronized TMarkLogicConnectionBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMarkLogicConnectionBeginJava result = new TMarkLogicConnectionBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_3 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_4 = ");";
  protected final String TEXT_5 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_6 = " = ";
  protected final String TEXT_7 = "; ";
  protected final String TEXT_8 = NL + NL + "com.marklogic.client.DatabaseClientFactory.Authentication authType_";
  protected final String TEXT_9 = " = null;";
  protected final String TEXT_10 = NL + "           authType_";
  protected final String TEXT_11 = " = com.marklogic.client.DatabaseClientFactory.Authentication.DIGEST;";
  protected final String TEXT_12 = NL + "           authType_";
  protected final String TEXT_13 = " = com.marklogic.client.DatabaseClientFactory.Authentication.BASIC;";
  protected final String TEXT_14 = NL + "           Compile Err: UNKNOWN_AUTHENTICATION_TYPE: ";
  protected final String TEXT_15 = NL + "com.marklogic.client.DatabaseClient" + NL + "           client_";
  protected final String TEXT_16 = " = com.marklogic.client.DatabaseClientFactory.newClient(";
  protected final String TEXT_17 = NL + "                  ";
  protected final String TEXT_18 = ", Integer.valueOf(";
  protected final String TEXT_19 = "), ";
  protected final String TEXT_20 = ", ";
  protected final String TEXT_21 = ", decryptedPassword_";
  protected final String TEXT_22 = ", authType_";
  protected final String TEXT_23 = NL + "          );" + NL + "globalMap.put(\"conn_";
  protected final String TEXT_24 = "\", client_";
  protected final String TEXT_25 = ");";
  protected final String TEXT_26 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();

    String host = ElementParameterParser.getValue(node,"__HOST__");
    String port = ElementParameterParser.getValue(node,"__PORT__");
    String userName = ElementParameterParser.getValue(node,"__USER_NAME__");
    String database = ElementParameterParser.getValue(node,"__DATABASE__");
    String authType = ElementParameterParser.getValue(node,"__AUTHENTICATION_TYPE__");
    String passwordFieldName = "__PASSWORD__";
 
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_4);
    } else {
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_7);
    }
    stringBuffer.append(TEXT_8);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_9);
    
    if("DIGEST".equals(authType)){
        
    stringBuffer.append(TEXT_10);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_11);
    
    }else if("BASIC".equals(authType)){
        
    stringBuffer.append(TEXT_12);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_13);
    
    }else{
        
    stringBuffer.append(TEXT_14);
    stringBuffer.append( authType );
    
    }

    stringBuffer.append(TEXT_15);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append( host );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( port );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( database );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( userName );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_25);
    stringBuffer.append(TEXT_26);
    return stringBuffer.toString();
  }
}
