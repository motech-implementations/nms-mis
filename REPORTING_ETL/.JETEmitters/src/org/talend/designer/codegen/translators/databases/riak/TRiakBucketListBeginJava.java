package org.talend.designer.codegen.translators.databases.riak;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;

public class TRiakBucketListBeginJava
{
  protected static String nl;
  public static synchronized TRiakBucketListBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TRiakBucketListBeginJava result = new TRiakBucketListBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\t\tlog.debug(\"";
  protected final String TEXT_2 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_3 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_4 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_5 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_6 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_7 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_8 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_10 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_11 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_12 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_13 = " - Written records count: \" + nb_line_";
  protected final String TEXT_14 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_15 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_16 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_17 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_18 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_19 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_21 = " - Writing the record \" + nb_line_";
  protected final String TEXT_22 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_23 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_24 = " - Processing the record \" + nb_line_";
  protected final String TEXT_25 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_26 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_27 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_28 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_29 = NL + "\tcom.basho.riak.client.IRiakClient client_";
  protected final String TEXT_30 = " = null;" + NL + "\tint nb_line_";
  protected final String TEXT_31 = " = 0;" + NL + "\t";
  protected final String TEXT_32 = NL + "\t\tclient_";
  protected final String TEXT_33 = " = (com.basho.riak.client.IRiakClient)globalMap.get(\"client_";
  protected final String TEXT_34 = "\");" + NL + "\t\t";
  protected final String TEXT_35 = "\t" + NL + "\t\t\tif(client_";
  protected final String TEXT_36 = " != null) {" + NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_37 = " - Get an existing client from \" + \"";
  protected final String TEXT_38 = "\" + \".\");" + NL + "\t\t\t}" + NL + "\t\t";
  protected final String TEXT_39 = NL + "\t\ttry{" + NL + "" + NL + "\t\t\t";
  protected final String TEXT_40 = NL + NL + "\t";
  protected final String TEXT_41 = NL + "\t\tcom.basho.riak.client.raw.http.HTTPClusterConfig  clusterConfig_";
  protected final String TEXT_42 = " = new com.basho.riak.client.raw.http.HTTPClusterConfig(com.basho.riak.client.raw.config.ClusterConfig.UNLIMITED_CONNECTIONS);" + NL + "\t\t";
  protected final String TEXT_43 = NL + "\t\t\t\tcom.basho.riak.client.raw.http.HTTPClientConfig clientConfig_";
  protected final String TEXT_44 = "_";
  protected final String TEXT_45 = " = new com.basho.riak.client.raw.http.HTTPClientConfig.Builder()" + NL + "\t\t\t\t";
  protected final String TEXT_46 = NL + "\t\t\t\t\t.withPort(";
  protected final String TEXT_47 = ")" + NL + "\t\t\t\t";
  protected final String TEXT_48 = NL + "\t\t\t\t\t.withRiakPath(";
  protected final String TEXT_49 = ")" + NL + "\t\t\t\t";
  protected final String TEXT_50 = NL + "\t\t\t\t\t.build();" + NL + "\t\t\t\tclusterConfig_";
  protected final String TEXT_51 = ".addHosts(clientConfig_";
  protected final String TEXT_52 = "_";
  protected final String TEXT_53 = ", ";
  protected final String TEXT_54 = ");" + NL + "\t\t\t";
  protected final String TEXT_55 = NL + "\t\tclient_";
  protected final String TEXT_56 = " =com.basho.riak.client.RiakFactory.newClient(clusterConfig_";
  protected final String TEXT_57 = ");" + NL + "\t\tclient_";
  protected final String TEXT_58 = ".ping();" + NL + "\t";
  protected final String TEXT_59 = NL + NL + "\t";
  protected final String TEXT_60 = NL + "\t\tcom.basho.riak.client.query.StreamingOperation<String> bucketsStreamOpts_";
  protected final String TEXT_61 = " = client_";
  protected final String TEXT_62 = ".listBucketsStreaming();" + NL + "\t\tjava.util.List<String> buckets_";
  protected final String TEXT_63 = " = bucketsStreamOpts_";
  protected final String TEXT_64 = ".getAll();" + NL + "\t\t";
  protected final String TEXT_65 = NL + "\t\tfor(String bucketName_";
  protected final String TEXT_66 = ":buckets_";
  protected final String TEXT_67 = "){" + NL + "\t\t\tnb_line_";
  protected final String TEXT_68 = "++;" + NL + "\t\t\tglobalMap.put(\"";
  protected final String TEXT_69 = "_CURRENT_BUCKET_NAME\", bucketName_";
  protected final String TEXT_70 = ");" + NL + "\t\t\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	//this util class use by set log4j debug paramters
	class DefaultLog4jFileUtil {
	
		INode node = null;
	    String cid = null;
 		boolean isLog4jEnabled = false;
 		String label = null;
 		
 		public DefaultLog4jFileUtil(){
 		}
 		public DefaultLog4jFileUtil(INode node) {
 			this.node = node;
 			this.cid = node.getUniqueName();
 			this.label = cid;
			this.isLog4jEnabled = ("true").equals(org.talend.core.model.process.ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
 		}
 		
 		public void setCid(String cid) {
 			this.cid = cid;
 		}
 		
		//for all tFileinput* components 
		public void startRetriveDataInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_1);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_2);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_6);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_8);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_9);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_12);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_14);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_17);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_19);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_20);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_22);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_23);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_25);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_26);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    
			}
		}
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
	
    String cid = node.getUniqueName();
    
    boolean useExistingConn = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	
	
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    
	if(useExistingConn){
		String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
		
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_34);
    
		if(isLog4jEnabled){
		
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_38);
    
		}
	}else{
	
    stringBuffer.append(TEXT_39);
    stringBuffer.append(TEXT_40);
    
    List<Map<String, String>> riakNodes = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__NODES__");
    if(riakNodes.size()>0){
	
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    
    	for(int i=0;i<riakNodes.size();i++){
    		Map<String, String> riakNode = riakNodes.get(i);
			String host =riakNode.get("HOST");
			String port =riakNode.get("PORT");
			String riakPath =riakNode.get("RIAK_PATH");
			if(host!=null && host.trim().length()>0 && !"\"\"".equals(host)){
			
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_45);
    
				if(port!=null && port.trim().length()>0){
				
    stringBuffer.append(TEXT_46);
    stringBuffer.append(port);
    stringBuffer.append(TEXT_47);
    
				}
				if(riakPath!=null && riakPath.trim().length()>0 && !"\"\"".equals(riakPath)){
				
    stringBuffer.append(TEXT_48);
    stringBuffer.append(riakPath);
    stringBuffer.append(TEXT_49);
    
				}
				
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_54);
    
			}
		}
		
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    
    }
	
    stringBuffer.append(TEXT_59);
    
    }
	
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    
		log4jFileUtil.startRetriveDataInfo();
		
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    
			log4jFileUtil.debugRetriveData(node);
			
    return stringBuffer.toString();
  }
}
