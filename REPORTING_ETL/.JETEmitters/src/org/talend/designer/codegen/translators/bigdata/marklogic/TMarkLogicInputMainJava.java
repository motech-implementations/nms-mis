package org.talend.designer.codegen.translators.bigdata.marklogic;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.metadata.IMetadataColumn;
import java.util.List;

public class TMarkLogicInputMainJava
{
  protected static String nl;
  public static synchronized TMarkLogicInputMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMarkLogicInputMainJava result = new TMarkLogicInputMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "\t";
  protected final String TEXT_3 = NL + "\t\tString docId_";
  protected final String TEXT_4 = " = ";
  protected final String TEXT_5 = ".";
  protected final String TEXT_6 = ";" + NL + "\t\tnb_line_";
  protected final String TEXT_7 = "++;" + NL + "\t";
  protected final String TEXT_8 = NL + "    for(com.marklogic.client.query.MatchDocumentSummary matchDocSummary: matchDocSummary_";
  protected final String TEXT_9 = "){//TagF1 }" + NL + "       String docId_";
  protected final String TEXT_10 = " = matchDocSummary.getUri();";
  protected final String TEXT_11 = NL + "\t" + NL + "\t";
  protected final String TEXT_12 = ".docId = docId_";
  protected final String TEXT_13 = ";" + NL + "\t";
  protected final String TEXT_14 = NL + "       Compile Err: can not return ";
  protected final String TEXT_15 = ", try to set the type to String Document Object byte[].";
  protected final String TEXT_16 = NL + "       routines.system.Document docment_";
  protected final String TEXT_17 = " = new routines.system.Document();" + NL + "       docment_";
  protected final String TEXT_18 = ".setDocument(" + NL + "                                     new org.dom4j.io.SAXReader().read(" + NL + "                                                                      (";
  protected final String TEXT_19 = ")docMgr_";
  protected final String TEXT_20 = ".readAs(docId_";
  protected final String TEXT_21 = ",";
  protected final String TEXT_22 = NL + "                                                                                                                    ";
  protected final String TEXT_23 = NL + "                                                                                                                    )" + NL + "                                                                      )" + NL + "                                     );";
  protected final String TEXT_24 = NL + "      ";
  protected final String TEXT_25 = ".";
  protected final String TEXT_26 = " = docment_";
  protected final String TEXT_27 = ";";
  protected final String TEXT_28 = NL + "        ";
  protected final String TEXT_29 = ".";
  protected final String TEXT_30 = " = ((com.marklogic.client.io.BytesHandle)docMgr_";
  protected final String TEXT_31 = ".read(docId_";
  protected final String TEXT_32 = ", new com.marklogic.client.io.BytesHandle())).get();";
  protected final String TEXT_33 = NL + "       ";
  protected final String TEXT_34 = ".";
  protected final String TEXT_35 = " = (";
  protected final String TEXT_36 = ")docMgr_";
  protected final String TEXT_37 = ".readAs(docId_";
  protected final String TEXT_38 = ",";
  protected final String TEXT_39 = NL + "                                                        ";
  protected final String TEXT_40 = NL + "                                                       );";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    
    boolean inputMode = false;
    IConnection inputConn = null;
	List<? extends IConnection> incomingConns = node.getIncomingConnections();
	if(incomingConns!=null) {
		for(IConnection inputConnection : incomingConns) {
			if(inputConnection!=null && inputConnection.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
				inputMode = true;
				inputConn = inputConnection;
			}
		}
	}
	
    List<IMetadataTable> metadataTableList = node.getMetadataList();
    if((metadataTableList == null) || metadataTableList.isEmpty()){
    	return stringBuffer.toString();
    }
    
    IMetadataTable metadata = metadataTableList.get(0);
    if(metadata == null){
    	return stringBuffer.toString();
    }
    
    List<IMetadataColumn> columnList = metadata.getListColumns();
    List<? extends IConnection> outgoingConns = node.getOutgoingSortedConnections();
    
    if(columnList.isEmpty() || (outgoingConns == null || outgoingConns.isEmpty())){
    	return stringBuffer.toString();
    }
    
    IConnection outputConn = outgoingConns.get(0);
    if(!outputConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)){
    	return stringBuffer.toString();
    }

    stringBuffer.append(TEXT_2);
    
	if(inputMode) {
		String asDocIdColumn = ElementParameterParser.getValue(node,"__AS_DOCID_COLUMN__");
	
    stringBuffer.append(TEXT_3);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( inputConn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( asDocIdColumn );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    
	} else {
	
    stringBuffer.append(TEXT_8);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_10);
    }
    stringBuffer.append(TEXT_11);
    stringBuffer.append( outputConn.getName() );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_13);
    
    if(columnList.size() < 2){
    	return stringBuffer.toString();
    }
    
    IMetadataColumn column = columnList.get(1);//treat the second column as docContent
    String columnName = column.getLabel();
    String javaTypeId = column.getTalendType();
    String returnIOClazz = null;
    if("id_String".equalsIgnoreCase(javaTypeId)){
      returnIOClazz = "java.lang.String";
    }else if("id_Document".equalsIgnoreCase(javaTypeId)){
      returnIOClazz = "java.io.File";
    }else if("id_Object".equalsIgnoreCase(javaTypeId)){
      returnIOClazz = "java.io.InputStream";
    }else if("id_byte[]".equalsIgnoreCase(javaTypeId)){
      // Do Nothing
    }else{
    
    stringBuffer.append(TEXT_14);
    stringBuffer.append( javaTypeId );
    stringBuffer.append(TEXT_15);
    
    }
    if("id_Document".equalsIgnoreCase(javaTypeId)){
    
    stringBuffer.append(TEXT_16);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( returnIOClazz );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    stringBuffer.append( (returnIOClazz + ".class") );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(TEXT_24);
    stringBuffer.append( outputConn.getName() );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( columnName );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_27);
    
    }else if("id_byte[]".equalsIgnoreCase(javaTypeId)){
    
    stringBuffer.append(TEXT_28);
    stringBuffer.append( outputConn.getName() );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( columnName );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_32);
    
    }else{
    
    stringBuffer.append(TEXT_33);
    stringBuffer.append( outputConn.getName() );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( columnName );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( returnIOClazz );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_38);
    stringBuffer.append(TEXT_39);
    stringBuffer.append( (returnIOClazz + ".class") );
    stringBuffer.append(TEXT_40);
    
  	}
  	
    return stringBuffer.toString();
  }
}
