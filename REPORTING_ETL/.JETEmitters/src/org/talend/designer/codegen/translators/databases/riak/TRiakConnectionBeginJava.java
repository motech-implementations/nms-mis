package org.talend.designer.codegen.translators.databases.riak;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;

public class TRiakConnectionBeginJava
{
  protected static String nl;
  public static synchronized TRiakConnectionBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TRiakConnectionBeginJava result = new TRiakConnectionBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\tcom.basho.riak.client.IRiakClient client_";
  protected final String TEXT_2 = " = null;" + NL + "\t";
  protected final String TEXT_3 = NL + NL + "\t";
  protected final String TEXT_4 = NL + "\t\tcom.basho.riak.client.raw.http.HTTPClusterConfig  clusterConfig_";
  protected final String TEXT_5 = " = new com.basho.riak.client.raw.http.HTTPClusterConfig(com.basho.riak.client.raw.config.ClusterConfig.UNLIMITED_CONNECTIONS);" + NL + "\t\t";
  protected final String TEXT_6 = NL + "\t\t\t\tcom.basho.riak.client.raw.http.HTTPClientConfig clientConfig_";
  protected final String TEXT_7 = "_";
  protected final String TEXT_8 = " = new com.basho.riak.client.raw.http.HTTPClientConfig.Builder()" + NL + "\t\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\t\t.withPort(";
  protected final String TEXT_10 = ")" + NL + "\t\t\t\t";
  protected final String TEXT_11 = NL + "\t\t\t\t\t.withRiakPath(";
  protected final String TEXT_12 = ")" + NL + "\t\t\t\t";
  protected final String TEXT_13 = NL + "\t\t\t\t\t.build();" + NL + "\t\t\t\tclusterConfig_";
  protected final String TEXT_14 = ".addHosts(clientConfig_";
  protected final String TEXT_15 = "_";
  protected final String TEXT_16 = ", ";
  protected final String TEXT_17 = ");" + NL + "\t\t\t";
  protected final String TEXT_18 = NL + "\t\tclient_";
  protected final String TEXT_19 = " =com.basho.riak.client.RiakFactory.newClient(clusterConfig_";
  protected final String TEXT_20 = ");" + NL + "\t\tclient_";
  protected final String TEXT_21 = ".ping();" + NL + "\t";
  protected final String TEXT_22 = NL + "\tglobalMap.put(\"client_\" + \"";
  protected final String TEXT_23 = "\",client_";
  protected final String TEXT_24 = ");" + NL + "\t";
  protected final String TEXT_25 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
	
    String cid = node.getUniqueName();
	
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    
    List<Map<String, String>> riakNodes = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__NODES__");
    if(riakNodes.size()>0){
	
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
    	for(int i=0;i<riakNodes.size();i++){
    		Map<String, String> riakNode = riakNodes.get(i);
			String host =riakNode.get("HOST");
			String port =riakNode.get("PORT");
			String riakPath =riakNode.get("RIAK_PATH");
			if(host!=null && host.trim().length()>0 && !"\"\"".equals(host)){
			
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_8);
    
				if(port!=null && port.trim().length()>0){
				
    stringBuffer.append(TEXT_9);
    stringBuffer.append(port);
    stringBuffer.append(TEXT_10);
    
				}
				if(riakPath!=null && riakPath.trim().length()>0 && !"\"\"".equals(riakPath)){
				
    stringBuffer.append(TEXT_11);
    stringBuffer.append(riakPath);
    stringBuffer.append(TEXT_12);
    
				}
				
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_17);
    
			}
		}
		
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    
    }
	
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(TEXT_25);
    return stringBuffer.toString();
  }
}
