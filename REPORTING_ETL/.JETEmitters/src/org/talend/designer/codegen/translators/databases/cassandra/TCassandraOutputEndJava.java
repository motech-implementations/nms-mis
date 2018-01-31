package org.talend.designer.codegen.translators.databases.cassandra;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.process.EConnectionType;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.talend.core.model.metadata.MappingTypeRetriever;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.core.model.utils.NodeUtil;

public class TCassandraOutputEndJava
{
  protected static String nl;
  public static synchronized TCassandraOutputEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCassandraOutputEndJava result = new TCassandraOutputEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL + "    \t";
  protected final String TEXT_4 = NL + "\t";
  protected final String TEXT_5 = NL + "        cassandraBatchExec_";
  protected final String TEXT_6 = ".endBatch();";
  protected final String TEXT_7 = NL + "        connection_";
  protected final String TEXT_8 = ".close();" + NL + "        cluster_";
  protected final String TEXT_9 = ".close();" + NL + "        resourceMap.put(\"finish_";
  protected final String TEXT_10 = "\", true); ";
  protected final String TEXT_11 = NL + "    globalMap.put(\"";
  protected final String TEXT_12 = "_NB_LINE\", nb_line_";
  protected final String TEXT_13 = ");";
  protected final String TEXT_14 = "    ";
  protected final String TEXT_15 = NL + "\tif (batchCounter_";
  protected final String TEXT_16 = " != 0) {" + NL + "\t\t";
  protected final String TEXT_17 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_18 = " - Executing the batch.\");" + NL + "\t\t";
  protected final String TEXT_19 = NL + "\t\tmutator_";
  protected final String TEXT_20 = ".execute();" + NL + "\t\t";
  protected final String TEXT_21 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_22 = " - Executed the batch successfully.\");" + NL + "\t\t";
  protected final String TEXT_23 = NL + "\t}" + NL + "\t";
  protected final String TEXT_24 = NL + "\t\tlog.info(\"";
  protected final String TEXT_25 = " - Has been ";
  protected final String TEXT_26 = " \" + nb_line_";
  protected final String TEXT_27 = " + \" records.\");" + NL + "\t";
  protected final String TEXT_28 = NL + "\t\tif(cluster_";
  protected final String TEXT_29 = "!=null){" + NL + "\t\t\tcluster_";
  protected final String TEXT_30 = ".getConnectionManager().shutdown();" + NL + "\t\t}" + NL + "\t\tresourceMap.put(\"finish_";
  protected final String TEXT_31 = "\", true); " + NL + "\t";
  protected final String TEXT_32 = NL + "\tglobalMap.put(\"";
  protected final String TEXT_33 = "_NB_LINE\", nb_line_";
  protected final String TEXT_34 = ");" + NL + "\t    \t";
  protected final String TEXT_35 = NL + "\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    
    
// Check the presence of a tCassandraConnection
{
    boolean useExistingConnection = ElementParameterParser.getBooleanValue(node,"__USE_EXISTING_CONNECTION__");
    if (useExistingConnection) {
        String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
        if (connection.isEmpty()) {
            return "if (true) {throw new IOException(\"You have to selection a connection\");}";
        }
    }
}

    stringBuffer.append(TEXT_2);
    
//TODO: use API_SELECTED instead of this
class API_selector{
	public boolean useDatastax(INode node){
		String dbVersion = ElementParameterParser.getValue(node, "__DB_VERSION__");
        String apiType = ElementParameterParser.getValue(node, "__API_TYPE__");
        return "CASSANDRA_2_2".equals(dbVersion) || "CASSANDRA_3_0".equals(dbVersion) || ("CASSANDRA_2_0_0".equals(dbVersion) && "DATASTAX".equals(apiType)); 
	}
}	

    
	if((new API_selector()).useDatastax(ElementParameterParser.getBooleanValue(node, "__USE_EXISTING_CONNECTION__") ? ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__") : node)){
	
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    
    IMetadataTable metadata = null;
    IConnection conn = null;
    List<IMetadataColumn> columns = null;
    List<IMetadataTable> metadatas = node.getMetadataList();
    if((metadatas!=null) && (metadatas.size() > 0)){
        metadata = metadatas.get(0);
        if(metadata != null){
    		columns = metadata.getListColumns();		
    	}
    }
    List<? extends IConnection> conns = node.getIncomingConnections();
    if(conns != null && conns.size() > 0 && conns.get(0).getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)){
        conn = conns.get(0);
    }
    
    if(columns == null || columns.isEmpty() || conn == null){
    	return "";
    }
    
    
    boolean useBatch = ElementParameterParser.getBooleanValue(node, "__USE_UNLOGGED_BATCH__");
    if(useBatch){
    
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    
    }
    boolean useExistConn = ElementParameterParser.getBooleanValue(node, "__USE_EXISTING_CONNECTION__");
    if(!useExistConn){
    
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    
    }
    
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    
    }else{
    
    stringBuffer.append(TEXT_14);
    
    boolean useExistingConnection = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
    
   	String dataAction = ElementParameterParser.getValue(node,"__ACTION_ON_DATA__");
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
		if(isLog4jEnabled){
		
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    
		}
		
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    
		if(isLog4jEnabled){
		
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    
		}
		
    stringBuffer.append(TEXT_23);
    
	if(isLog4jEnabled){
	
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(dataAction.toLowerCase()+(dataAction.endsWith("E")?"d":"ed"));
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    
	}
	if (!useExistingConnection){
	
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    
	}
	
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    
    }
    
    stringBuffer.append(TEXT_35);
    return stringBuffer.toString();
  }
}
