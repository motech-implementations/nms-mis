package org.talend.designer.codegen.translators.bigdata.marklogic;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import java.util.List;

public class TMarkLogicInputEndJava
{
  protected static String nl;
  public static synchronized TMarkLogicInputEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMarkLogicInputEndJava result = new TMarkLogicInputEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL + "                retrieved_doc_number_";
  protected final String TEXT_4 = " ++;" + NL + "                if(retrieved_doc_number_";
  protected final String TEXT_5 = " >= nb_line_";
  protected final String TEXT_6 = "){" + NL + "                    break;" + NL + "                }" + NL + "                }" + NL + "                start_";
  protected final String TEXT_7 = " = i * DEFAULT_PAGE_SIZE_";
  protected final String TEXT_8 = " + 1;" + NL + "                }";
  protected final String TEXT_9 = NL + NL + "    globalMap.put(\"";
  protected final String TEXT_10 = "_NB_LINE\", nb_line_";
  protected final String TEXT_11 = ");" + NL;
  protected final String TEXT_12 = NL + "       client_";
  protected final String TEXT_13 = ".release();";
  protected final String TEXT_14 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();

	boolean inputMode = false;
	List<? extends IConnection> incomingConns = node.getIncomingConnections();
	if(incomingConns!=null) {
		for(IConnection inputConnection : incomingConns) {
			if(inputConnection!=null && inputConnection.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
				inputMode = true;
			}
		}
	}
	
    boolean reuseConn = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));

    stringBuffer.append(TEXT_2);
    
    List<IMetadataTable> metadataTableList = node.getMetadataList();
    if(!inputMode && (metadataTableList != null) && (metadataTableList.size() > 0)){//TagA
        IMetadataTable metadata = metadataTableList.get(0);
        if(metadata != null){//TagB
            List<IMetadataColumn> columnList = metadata.getListColumns();
            List<? extends IConnection> outgoingConns = node.getOutgoingSortedConnections();
            if(columnList.size() > 0 && outgoingConns != null && outgoingConns.size() > 0){//TagC
                IConnection outputConn = outgoingConns.get(0);
                if(outputConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)){//TagD
                
    stringBuffer.append(TEXT_3);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_8);
    
                }//TagD
            }//TagC
    	}//TagB
	}//TagA
    
    stringBuffer.append(TEXT_9);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    
    if(!reuseConn){
    
    stringBuffer.append(TEXT_12);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_13);
    
    }

    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
}
