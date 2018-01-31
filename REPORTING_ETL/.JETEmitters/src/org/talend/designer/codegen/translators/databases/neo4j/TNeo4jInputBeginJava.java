package org.talend.designer.codegen.translators.databases.neo4j;

import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import java.util.List;

public class TNeo4jInputBeginJava
{
  protected static String nl;
  public static synchronized TNeo4jInputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TNeo4jInputBeginJava result = new TNeo4jInputBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "        org.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_2 = " = (org.neo4j.graphdb.GraphDatabaseService) globalMap.get(\"";
  protected final String TEXT_3 = "\");";
  protected final String TEXT_4 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_5 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_6 = ");";
  protected final String TEXT_7 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_8 = " = ";
  protected final String TEXT_9 = "; ";
  protected final String TEXT_10 = NL + "                org.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_11 = " = new org.neo4j.rest.graphdb.RestGraphDatabase(";
  protected final String TEXT_12 = ", ";
  protected final String TEXT_13 = ", decryptedPassword_";
  protected final String TEXT_14 = ");";
  protected final String TEXT_15 = NL + "                org.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_16 = " = new org.neo4j.rest.graphdb.RestGraphDatabase(";
  protected final String TEXT_17 = ");";
  protected final String TEXT_18 = NL + "                org.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_19 = " = new org.neo4j.kernel.EmbeddedReadOnlyGraphDatabase(";
  protected final String TEXT_20 = ");";
  protected final String TEXT_21 = NL + "                org.neo4j.graphdb.GraphDatabaseService databaseService_";
  protected final String TEXT_22 = " = new org.neo4j.graphdb.factory.GraphDatabaseFactory().newEmbeddedDatabase(";
  protected final String TEXT_23 = ");";
  protected final String TEXT_24 = NL + NL + "java.util.Iterator<java.util.Map<String, Object>> resultIterator_";
  protected final String TEXT_25 = " = null;" + NL + "if (databaseService_";
  protected final String TEXT_26 = " instanceof org.neo4j.rest.graphdb.RestGraphDatabase) {" + NL + "\torg.neo4j.rest.graphdb.RestGraphDatabase restDatabase_";
  protected final String TEXT_27 = " = (org.neo4j.rest.graphdb.RestGraphDatabase) databaseService_";
  protected final String TEXT_28 = "; " + NL + "\torg.neo4j.rest.graphdb.query.QueryEngine<java.util.Map<String, Object>> queryEngine_";
  protected final String TEXT_29 = " = new org.neo4j.rest.graphdb.query.RestCypherQueryEngine(restDatabase_";
  protected final String TEXT_30 = ".getRestAPI());" + NL + "\tresultIterator_";
  protected final String TEXT_31 = " = queryEngine_";
  protected final String TEXT_32 = ".query(";
  protected final String TEXT_33 = ", null).iterator();" + NL + "} else {";
  protected final String TEXT_34 = NL + "        org.neo4j.cypher.ExecutionEngine engine_";
  protected final String TEXT_35 = " = new org.neo4j.cypher.ExecutionEngine(databaseService_";
  protected final String TEXT_36 = ");";
  protected final String TEXT_37 = NL + "        org.neo4j.cypher.ExecutionEngine engine_";
  protected final String TEXT_38 = " = new org.neo4j.cypher.ExecutionEngine(databaseService_";
  protected final String TEXT_39 = ", org.neo4j.kernel.impl.util.StringLogger.SYSTEM);";
  protected final String TEXT_40 = NL + "        org.neo4j.cypher.ExecutionEngine engine_";
  protected final String TEXT_41 = " = new org.neo4j.cypher.ExecutionEngine(databaseService_";
  protected final String TEXT_42 = ", org.neo4j.logging.FormattedLogProvider.toOutputStream(System.out));";
  protected final String TEXT_43 = NL + "\torg.neo4j.cypher.ExecutionResult result_";
  protected final String TEXT_44 = " = engine_";
  protected final String TEXT_45 = ".execute(";
  protected final String TEXT_46 = ");" + NL + "\tresultIterator_";
  protected final String TEXT_47 = " = result_";
  protected final String TEXT_48 = ".javaIterator();" + NL + "}" + NL + "" + NL + "int nb_line_";
  protected final String TEXT_49 = " = 0;" + NL + "String expression_";
  protected final String TEXT_50 = " = \"\";" + NL + "class Neo4jUtil_";
  protected final String TEXT_51 = "{" + NL + "\tpublic Object getPropertyValue(String expression,java.util.Map<String, Object> row){" + NL + "\t\tObject value=null;" + NL + "\t\tif(expression!=null){" + NL + "\t\t\ttry{" + NL + "\t\t\t\tif(expression.contains(\".\")) {" + NL + "\t\t\t\t\tString suffix=expression.split(\"\\\\.\")[0];" + NL + "\t\t\t\t\tString propertyName=expression.split(\"\\\\.\")[1];" + NL + "\t\t\t\t\tif (row.get(suffix) != null && row.get(suffix) instanceof org.neo4j.graphdb.PropertyContainer) {" + NL + "\t\t\t\t\t\torg.neo4j.graphdb.PropertyContainer propertyContainer = (org.neo4j.graphdb.PropertyContainer) row.get(suffix);" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_52 = NL + "\t\t\t                value=propertyContainer.getProperty(propertyName);" + NL + "\t\t\t                ";
  protected final String TEXT_53 = NL + "                            org.neo4j.graphdb.Transaction tx_begin_";
  protected final String TEXT_54 = " = propertyContainer.getGraphDatabase().beginTx();" + NL + "                            try" + NL + "                            {" + NL + "                                value=propertyContainer.getProperty(propertyName);" + NL + "                                tx_begin_";
  protected final String TEXT_55 = ".success();" + NL + "                            }" + NL + "                            finally" + NL + "                            {" + NL + "                                tx_begin_";
  protected final String TEXT_56 = ".close();" + NL + "                            }";
  protected final String TEXT_57 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t}catch(org.neo4j.graphdb.NotFoundException e_";
  protected final String TEXT_58 = "){" + NL + "\t\t\t\tSystem.err.println(e_";
  protected final String TEXT_59 = ".getMessage()+\" not found!!\");" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\treturn value;" + NL + "\t}" + NL + "}" + NL + "Neo4jUtil_";
  protected final String TEXT_60 = " neo4jUtil_";
  protected final String TEXT_61 = "=new Neo4jUtil_";
  protected final String TEXT_62 = "();";
  protected final String TEXT_63 = NL + "try{";
  protected final String TEXT_64 = NL + "while (resultIterator_";
  protected final String TEXT_65 = ".hasNext()) {";
  protected final String TEXT_66 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    
    boolean useExistingConnection = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"));
    boolean shutdownDb = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__SHUTDOWN_DB__"));
    boolean remoteServer = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REMOTE_SERVER__"));
    String query = ElementParameterParser.getValue(node, "__QUERY__");
    String dbVersion = ElementParameterParser.getValue(node,"__DB_VERSION__");
    query = query.replaceAll("\n"," ");
    query = query.replaceAll("\r"," ");

    if (useExistingConnection) {
    	String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
        List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
        for(INode targetNode : nodes){
            if (targetNode.getUniqueName().equals(connection)) {
                dbVersion = ElementParameterParser.getValue(targetNode, "__DB_VERSION__");
                remoteServer = "true".equalsIgnoreCase(ElementParameterParser.getValue(targetNode, "__REMOTE_SERVER__"));
                break;
            }
        }

        String dbconn = "dbService_" + connection;
		
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(dbconn);
    stringBuffer.append(TEXT_3);
    
    } else {
    	if (remoteServer) {
            String serverUrl = ElementParameterParser.getValue(node, "__SERVER_URL__");
            boolean setUsername = ElementParameterParser.getBooleanValue(node, "__SET_USERNAME__");
            String username = ElementParameterParser.getValue(node, "__USERNAME__");
            String passwordFieldName = "__PASSWORD__";
            if ((setUsername) || ((!"NEO4J_1_X_X".equals(dbVersion)) && (!"NEO4J_2_1_X".equals(dbVersion)))) {
                
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
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
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(serverUrl);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    
            } else {
                
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(serverUrl);
    stringBuffer.append(TEXT_17);
    
            }
    	} else {
	    	String databasePath = ElementParameterParser.getValue(node, "__DATABASE_PATH__");
	    	if ("NEO4J_1_X_X".equals(dbVersion)) {
    			
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(databasePath);
    stringBuffer.append(TEXT_20);
    
	    	} else { // NEO4J_2_1_X or NEO4J_2_2_X
                
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(databasePath);
    stringBuffer.append(TEXT_23);
    
	    	}
		}
    }

    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(query);
    stringBuffer.append(TEXT_33);
    
    if ("NEO4J_1_X_X".equals(dbVersion)) {
        
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    
    } else if (("NEO4J_2_1_X".equals(dbVersion)) || ("NEO4J_2_2_X".equals(dbVersion))) { // NEO4J_2_X before moving to spring framework
        
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    
    } else { // NEO4J_2_3 and more using spring framework
        
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
    stringBuffer.append(query);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    
			            if (("NEO4J_1_X_X".equals(dbVersion)) || (remoteServer)) {
			                
    stringBuffer.append(TEXT_52);
    
			            } else {
			                
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    
			            }
			            
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    
	if (!useExistingConnection||(useExistingConnection && shutdownDb)) {

    stringBuffer.append(TEXT_63);
    
	}

    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(TEXT_66);
    return stringBuffer.toString();
  }
}
