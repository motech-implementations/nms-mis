package org.talend.designer.codegen.translators.databases.mongodb;

import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.ElementParameterParser;
import java.util.List;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.utils.NodeUtil;
import org.talend.core.model.process.EConnectionType;

public class TMongoDBWriteConfEndJava
{
  protected static String nl;
  public static synchronized TMongoDBWriteConfEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMongoDBWriteConfEndJava result = new TMongoDBWriteConfEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_2 = NL + "\t\t\t\ttxf_";
  protected final String TEXT_3 = ".join();" + NL + "\t\t\t\tif(txf_";
  protected final String TEXT_4 = ".getLastException()!=null) {" + NL + "\t\t\t\t\tcurrentComponent = txf_";
  protected final String TEXT_5 = ".getCurrentComponent();" + NL + "\t\t\t\t\tthrow txf_";
  protected final String TEXT_6 = ".getLastException();" + NL + "\t\t\t\t}" + NL + "\t\t\t\tglobalMap.remove(\"queue_";
  protected final String TEXT_7 = "\");" + NL + "\t\t\t";
  protected final String TEXT_8 = NL + "\t\t\t\tString readFinishWithExceptionMarkWithPipeId_";
  protected final String TEXT_9 = " = \"";
  protected final String TEXT_10 = "_FINISH_WITH_EXCEPTION\"+(queue_";
  protected final String TEXT_11 = "==null?\"\":queue_";
  protected final String TEXT_12 = ".hashCode());" + NL + "\t\t\t\tif(globalMap.containsKey(readFinishWithExceptionMarkWithPipeId_";
  protected final String TEXT_13 = ")){" + NL + "\t\t\t\t\tglobalMap.put(readFinishWithExceptionMarkWithPipeId_";
  protected final String TEXT_14 = ", null);// syn" + NL + "\t\t\t\t\tglobalMap.remove(readFinishWithExceptionMarkWithPipeId_";
  protected final String TEXT_15 = ");" + NL + "\t\t\t\t\treturn;" + NL + "\t\t\t\t}" + NL + "\t\t\t\tglobalMap.remove(\"queue_";
  protected final String TEXT_16 = "\"); " + NL + "\t\t\t";
  protected final String TEXT_17 = NL + "\t\t        // if bulkWriteOperationCounter_";
  protected final String TEXT_18 = " == 1 the ulkWriteOperation_";
  protected final String TEXT_19 = " is empty, do not execute." + NL + "\t\t        if (bulkWriteOperationCounter_";
  protected final String TEXT_20 = " != 1) {" + NL + "\t\t            bulkWriteOperation_";
  protected final String TEXT_21 = ".execute();" + NL + "\t\t        }" + NL + "\t\t        ";
  protected final String TEXT_22 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_23 = " - Has been ";
  protected final String TEXT_24 = " \" + nb_line_";
  protected final String TEXT_25 = " + \" records.\");" + NL + "\t\t\t";
  protected final String TEXT_26 = NL + "\t\t\t\tif(mongo_";
  protected final String TEXT_27 = " != null ){" + NL + "\t\t\t\t\t";
  protected final String TEXT_28 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_29 = " - Closing the connection \" + mongo_";
  protected final String TEXT_30 = ".getServerAddressList() + \".\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_31 = NL + "\t\t\t\t\t\tmongo_";
  protected final String TEXT_32 = ".close();" + NL + "\t\t\t\t\t";
  protected final String TEXT_33 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_34 = " - The connection was closed successfully.\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_35 = NL + "\t\t\t\t}" + NL + "\t\t\t\tresourceMap.put(\"finish_";
  protected final String TEXT_36 = "\", true); " + NL + "\t\t\t";
  protected final String TEXT_37 = NL + "\t\t\tglobalMap.put(readFinishMarkWithPipeId_";
  protected final String TEXT_38 = ",null);//syn" + NL + "\t\t\tglobalMap.remove(readFinishMarkWithPipeId_";
  protected final String TEXT_39 = ");" + NL + "\t\t";
  protected final String TEXT_40 = NL + "\tglobalMap.put(\"";
  protected final String TEXT_41 = "_NB_LINE\",nb_line_";
  protected final String TEXT_42 = ");" + NL + "\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
	String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");
	String strJobCid=cid.replace("_Out","");
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");

	// BulkWrite is only supportted on Mongo 2.6+ drivers
	String dbversion = ElementParameterParser.getValue(node, "__DB_VERSION__");
	boolean setBulkWrite = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__SET_BULK_WRITE__"));
	final boolean bulkWrite = setBulkWrite & (!"MONGODB_2_5_X".equalsIgnoreCase(dbversion));
	
	List<IMetadataTable> metadatas = node.getMetadataList();
	if ((metadatas!=null)&&(metadatas.size()>0)) {
    	IMetadataTable metadata = metadatas.get(0);
		if (metadata!=null) {
		
    stringBuffer.append(TEXT_1);
    
			INode sourceNode = node.getIncomingConnections(EConnectionType.ON_COMPONENT_OK).get(0).getSource();
			String virtualSourceCid = sourceNode.getUniqueName();
			INode startNode = NodeUtil.getSpecificStartNode(sourceNode);
			String startNodeCid = null; 
			if(startNode != null){
				startNodeCid = startNode.getUniqueName();
			} 
			IConnection nextMergeConn = NodeUtil.getNextMergeConnection(node);
			if(nextMergeConn != null && nextMergeConn.getInputId()>1 && startNodeCid != null){
   			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(virtualSourceCid);
    stringBuffer.append(TEXT_7);
    
			}else{
			
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
			}

			// BulkWrite 
			// Last write if number of rows added is less than bulksize
			if(bulkWrite){
		        
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    
			}

			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(dataAction.toLowerCase()+(dataAction.endsWith("E")?"d":"ed"));
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    
			}
			if(!"true".equals(useExistingConn)){
			
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    
					if(isLog4jEnabled){
					
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    
					}
					
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    
					if(isLog4jEnabled){
					
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    
					}
					
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    
			}
			
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    
		}
	}
	
    stringBuffer.append(TEXT_40);
    stringBuffer.append(strJobCid );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_42);
    return stringBuffer.toString();
  }
}
