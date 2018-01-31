package org.talend.designer.codegen.translators.databases.riak;

import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.IConnectionCategory;
import java.util.List;

public class TRiakOutputEndJava
{
  protected static String nl;
  public static synchronized TRiakOutputEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TRiakOutputEndJava result = new TRiakOutputEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\t\t\tif(client_";
  protected final String TEXT_2 = " !=null){" + NL + "\t\t\t\t\t\tclient_";
  protected final String TEXT_3 = ".shutdown();" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\tresourceMap.put(\"finish_";
  protected final String TEXT_4 = "\", true); " + NL + "\t\t\t\t";
  protected final String TEXT_5 = NL + "\t\t\t\tglobalMap.put(\"";
  protected final String TEXT_6 = "_UPSERT_NB_LINE\", upsert_nb_line_";
  protected final String TEXT_7 = ");" + NL + "\t\t\t\tglobalMap.put(\"";
  protected final String TEXT_8 = "_DELETE_NB_LINE\", delete_nb_line_";
  protected final String TEXT_9 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_10 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_11 = " - Has deleted \" + delete_nb_line_";
  protected final String TEXT_12 = " + \" records.\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_13 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_14 = " - Has inserted or updated \" + upsert_nb_line_";
  protected final String TEXT_15 = " + \" records.\");" + NL + "\t\t\t\t\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    
	boolean useExistingConn = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
	List<? extends IConnection> inConns = node.getIncomingConnections(EConnectionType.FLOW_MAIN);
	String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	
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
				
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    
				}
				
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    
				if(isLog4jEnabled){
					if("DELETE".equals(dataAction)){
					
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    
					}else{
					
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    
					}
				}
			}
		}
	}
	
    return stringBuffer.toString();
  }
}
