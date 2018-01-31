package org.talend.designer.codegen.translators.databases.cassandra;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.utils.NodeUtil;

public class TCassandraCloseMainJava
{
  protected static String nl;
  public static synchronized TCassandraCloseMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCassandraCloseMainJava result = new TCassandraCloseMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL + "    \t";
  protected final String TEXT_4 = NL + "\tcom.datastax.driver.core.Cluster cluster_";
  protected final String TEXT_5 = " = (com.datastax.driver.core.Cluster)globalMap.get(\"cluster_";
  protected final String TEXT_6 = "\");" + NL + "\tcom.datastax.driver.core.Session connection_";
  protected final String TEXT_7 = " = (com.datastax.driver.core.Session)globalMap.get(\"connection_";
  protected final String TEXT_8 = "\");" + NL + "\tif(connection_";
  protected final String TEXT_9 = " != null){" + NL + "\t\tconnection_";
  protected final String TEXT_10 = ".close();" + NL + "\t}" + NL + "\tif(cluster_";
  protected final String TEXT_11 = " != null){" + NL + "\t\tcluster_";
  protected final String TEXT_12 = ".close();" + NL + "\t}" + NL + "\t";
  protected final String TEXT_13 = NL + "\t\t";
  protected final String TEXT_14 = NL + "\tme.prettyprint.hector.api.Cluster cluster_";
  protected final String TEXT_15 = "=(me.prettyprint.hector.api.Cluster)globalMap.get(\"cluster_";
  protected final String TEXT_16 = "\");" + NL + "\tif(cluster_";
  protected final String TEXT_17 = "!=null){" + NL + "\t\tcluster_";
  protected final String TEXT_18 = ".getConnectionManager().shutdown();" + NL + "\t}" + NL + "\t" + NL + "\t" + NL + "\t";
  protected final String TEXT_19 = NL + "\t" + NL + "\t";
  protected final String TEXT_20 = NL;

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

    
    String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
    INode connectionNode = NodeUtil.getNodeByUniqueName(node.getProcess(), connection);
    
    stringBuffer.append(TEXT_2);
    
//TODO: use API_SELECTED instead of this
class API_selector{
	public boolean useDatastax(INode node){
		String dbVersion = ElementParameterParser.getValue(node, "__DB_VERSION__");
        String apiType = ElementParameterParser.getValue(node, "__API_TYPE__");
        return "CASSANDRA_2_2".equals(dbVersion) || "CASSANDRA_3_0".equals(dbVersion) || ("CASSANDRA_2_0_0".equals(dbVersion) && "DATASTAX".equals(apiType)); 
	}
}	

    
	if((new API_selector()).useDatastax(connectionNode)){
    
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    
    }else{
	
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    
	}	
	
    stringBuffer.append(TEXT_19);
    stringBuffer.append(TEXT_20);
    return stringBuffer.toString();
  }
}
