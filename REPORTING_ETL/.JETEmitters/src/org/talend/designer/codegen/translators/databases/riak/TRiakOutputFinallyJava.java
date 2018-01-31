package org.talend.designer.codegen.translators.databases.riak;

import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.IConnectionCategory;
import java.util.List;

public class TRiakOutputFinallyJava
{
  protected static String nl;
  public static synchronized TRiakOutputFinallyJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TRiakOutputFinallyJava result = new TRiakOutputFinallyJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\t\t\t\t\tif(resourceMap.get(\"finish_";
  protected final String TEXT_3 = "\") == null){" + NL + "\t\t\t\t\t\tif(resourceMap.get(\"client_";
  protected final String TEXT_4 = "\") != null){" + NL + "\t\t\t\t\t\t\t((com.basho.riak.client.IRiakClient)resourceMap.get(\"client_";
  protected final String TEXT_5 = "\")).shutdown();" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    
	boolean useExistingConn = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
	List<? extends IConnection> inConns = node.getIncomingConnections(EConnectionType.FLOW_MAIN);
    IConnection inConn = null;
	IMetadataTable metadata = null;
	if(inConns!=null && inConns.size()> 0) {
		inConn = inConns.get(0);
		metadata = inConn.getMetadataTable();
	}
  	List< ? extends IConnection> conns = node.getIncomingConnections();
	for (IConnection conn : conns) {
		if (conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
	      	if(metadata != null){
				if(!useExistingConn){
				
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
				}
			}
		}
	}
	
    return stringBuffer.toString();
  }
}
