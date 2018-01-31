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

public class TCassandraOutputFinallyJava
{
  protected static String nl;
  public static synchronized TCassandraOutputFinallyJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCassandraOutputFinallyJava result = new TCassandraOutputFinallyJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL + "    \t";
  protected final String TEXT_4 = NL + "\t";
  protected final String TEXT_5 = NL + "    \tif(resourceMap.get(\"finish_";
  protected final String TEXT_6 = "\") == null){" + NL + "    \t\tif(resourceMap.get(\"connection_";
  protected final String TEXT_7 = "\") != null){" + NL + "    \t\t\t((com.datastax.driver.core.Session)resourceMap.get(\"connection_";
  protected final String TEXT_8 = "\")).close();" + NL + "    \t\t}" + NL + "    \t\tif(resourceMap.get(\"cluster_";
  protected final String TEXT_9 = "\") != null){" + NL + "    \t\t\t((com.datastax.driver.core.Cluster)resourceMap.get(\"cluster_";
  protected final String TEXT_10 = "\")).close();" + NL + "    \t\t}" + NL + "    \t}";
  protected final String TEXT_11 = "    ";
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = NL + "\t";
  protected final String TEXT_14 = NL + "\t\tif(resourceMap.get(\"finish_";
  protected final String TEXT_15 = "\") == null){" + NL + "\t\t\tif(resourceMap.get(\"cluster_";
  protected final String TEXT_16 = "\")!=null){" + NL + "\t\t\t\t((me.prettyprint.hector.api.Cluster)resourceMap.get(\"cluster_";
  protected final String TEXT_17 = "\")).getConnectionManager().shutdown();" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t";
  protected final String TEXT_18 = NL + "    \t";
  protected final String TEXT_19 = NL;

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
    
     
    boolean useExistConn = "true".equals(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"));
    if(!useExistConn){
    
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    
    }
    
    
    }else{
    
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    
    boolean useExistingConnection = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
	
    stringBuffer.append(TEXT_13);
    
	if (!useExistingConnection){
	
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    
	}
	
    stringBuffer.append(TEXT_18);
    
    }
    
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
}
