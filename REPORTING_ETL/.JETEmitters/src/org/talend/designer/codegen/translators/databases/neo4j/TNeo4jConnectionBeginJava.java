package org.talend.designer.codegen.translators.databases.neo4j;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TNeo4jConnectionBeginJava
{
  protected static String nl;
  public static synchronized TNeo4jConnectionBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TNeo4jConnectionBeginJava result = new TNeo4jConnectionBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t        ";
  protected final String TEXT_2 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_3 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_4 = ");";
  protected final String TEXT_5 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_6 = " = ";
  protected final String TEXT_7 = "; ";
  protected final String TEXT_8 = NL + "\t\t    org.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_9 = " = new org.neo4j.rest.graphdb.RestGraphDatabase(";
  protected final String TEXT_10 = ", ";
  protected final String TEXT_11 = ", decryptedPassword_";
  protected final String TEXT_12 = ");" + NL + "\t\t    ";
  protected final String TEXT_13 = NL + "\t\t    org.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_14 = " = new org.neo4j.rest.graphdb.RestGraphDatabase(";
  protected final String TEXT_15 = ");" + NL + "\t\t    ";
  protected final String TEXT_16 = NL + "            java.util.Map config = new java.util.HashMap();" + NL + "//            config.put( \"read_only\", \"true\" );" + NL + "\t\t\torg.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_17 = " = new org.neo4j.graphdb.factory.GraphDatabaseFactory()" + NL + "\t\t\t        .newEmbeddedDatabaseBuilder(";
  protected final String TEXT_18 = ")" + NL + "\t\t\t        .setConfig(org.neo4j.graphdb.factory.GraphDatabaseSettings.read_only, \"true\").newGraphDatabase();" + NL + "//\t\t\t        .setConfig(config).newGraphDatabase();" + NL + "\t\t\t";
  protected final String TEXT_19 = NL + "\t\t\torg.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_20 = " = new org.neo4j.graphdb.factory.GraphDatabaseFactory().newEmbeddedDatabase(";
  protected final String TEXT_21 = ");" + NL + "\t\t\t";
  protected final String TEXT_22 = NL + "\tglobalMap.put(\"dbService_";
  protected final String TEXT_23 = "_path\", ";
  protected final String TEXT_24 = ");" + NL + "    globalMap.put(\"dbService_";
  protected final String TEXT_25 = "\", databaseService_";
  protected final String TEXT_26 = ");";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    String dbPath = ElementParameterParser.getValue(node, "__DATABASE_PATH__");

	boolean remoteServer = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REMOTE_SERVER__"));
	boolean readOnlyMode = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__READ_ONLY_MODE__"));
	
	
	if (remoteServer) {
		String serverUrl = ElementParameterParser.getValue(node, "__SERVER_URL__");
		String dbVersion = ElementParameterParser.getValue(node, "__DB_VERSION__");
        boolean setUsername = ElementParameterParser.getBooleanValue(node, "__SET_USERNAME__");
        String username = ElementParameterParser.getValue(node, "__USERNAME__");
        String passwordFieldName = "__PASSWORD__";
        if ((setUsername) || ((!"NEO4J_1_X_X".equals(dbVersion)) && (!"NEO4J_2_1_X".equals(dbVersion)))) {
		    
    stringBuffer.append(TEXT_1);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(serverUrl);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    
		} else {
		    
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(serverUrl);
    stringBuffer.append(TEXT_15);
    
		}
	} else {
		if (readOnlyMode) {
			
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(dbPath);
    stringBuffer.append(TEXT_18);
    
		} else {
			
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(dbPath);
    stringBuffer.append(TEXT_21);
    
		}
	}
	
	
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(dbPath);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    return stringBuffer.toString();
  }
}
