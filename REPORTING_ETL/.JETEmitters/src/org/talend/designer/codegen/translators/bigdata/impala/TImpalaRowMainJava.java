package org.talend.designer.codegen.translators.bigdata.impala;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.metadata.MappingTypeRetriever;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class TImpalaRowMainJava
{
  protected static String nl;
  public static synchronized TImpalaRowMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TImpalaRowMainJava result = new TImpalaRowMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "    " + NL + "\t\t\t";
  protected final String TEXT_2 = " = null;            ";
  protected final String TEXT_3 = NL + "\tquery_";
  protected final String TEXT_4 = " = ";
  protected final String TEXT_5 = ";" + NL + "\twhetherReject_";
  protected final String TEXT_6 = " = false;" + NL + "" + NL + "\t";
  protected final String TEXT_7 = NL + "\tglobalMap.put(\"";
  protected final String TEXT_8 = "_QUERY\",query_";
  protected final String TEXT_9 = ");";
  protected final String TEXT_10 = NL + "\t\tjava.sql.ResultSet rs_";
  protected final String TEXT_11 = " = null;";
  protected final String TEXT_12 = NL + "\ttry {";
  protected final String TEXT_13 = NL + "\t\t\t\t\tpstmt_";
  protected final String TEXT_14 = ".setTimestamp(";
  protected final String TEXT_15 = ",new java.sql.Timestamp(";
  protected final String TEXT_16 = ".getTime()));";
  protected final String TEXT_17 = NL + "\t\t\t\t\tpstmt_";
  protected final String TEXT_18 = ".set";
  protected final String TEXT_19 = "(";
  protected final String TEXT_20 = ",";
  protected final String TEXT_21 = ");";
  protected final String TEXT_22 = NL + "\t\t\t\trs_";
  protected final String TEXT_23 = " = pstmt_";
  protected final String TEXT_24 = ".executeQuery();";
  protected final String TEXT_25 = NL + "\t    \t\tpstmt_";
  protected final String TEXT_26 = ".execute();";
  protected final String TEXT_27 = NL + "\t\t\t\trs_";
  protected final String TEXT_28 = " = stmt_";
  protected final String TEXT_29 = ".executeQuery(query_";
  protected final String TEXT_30 = ");";
  protected final String TEXT_31 = NL + "\t    \t\tstmt_";
  protected final String TEXT_32 = ".execute(query_";
  protected final String TEXT_33 = ");";
  protected final String TEXT_34 = NL + "\t} catch (java.lang.Exception e) {" + NL + "\t\twhetherReject_";
  protected final String TEXT_35 = " = true;";
  protected final String TEXT_36 = NL + "\t\t\t\tlog.fatal(\"";
  protected final String TEXT_37 = " - \" + e.getMessage());";
  protected final String TEXT_38 = NL + "\t\t\tthrow e;";
  protected final String TEXT_39 = NL + "\t\t\t\tlog.error(\"";
  protected final String TEXT_40 = " - \" + e.getMessage());";
  protected final String TEXT_41 = NL + "\t\t\t\t";
  protected final String TEXT_42 = " = new ";
  protected final String TEXT_43 = "Struct();";
  protected final String TEXT_44 = NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_45 = ".";
  protected final String TEXT_46 = " = ";
  protected final String TEXT_47 = ".";
  protected final String TEXT_48 = ";";
  protected final String TEXT_49 = NL + "\t\t\t\t";
  protected final String TEXT_50 = ".errorCode = ((java.sql.SQLException)e).getSQLState();" + NL + "\t\t\t\t";
  protected final String TEXT_51 = ".errorMessage = e.getMessage() + \" - Line: \" + tos_count_";
  protected final String TEXT_52 = ";";
  protected final String TEXT_53 = NL + "\t\t\t\tSystem.err.println(\"Exception in the component ";
  protected final String TEXT_54 = ":\" + e.getMessage());";
  protected final String TEXT_55 = NL + "    }";
  protected final String TEXT_56 = NL + "\t\tif(!whetherReject_";
  protected final String TEXT_57 = ") {";
  protected final String TEXT_58 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_59 = " = new ";
  protected final String TEXT_60 = "Struct();";
  protected final String TEXT_61 = NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_62 = ".";
  protected final String TEXT_63 = " = rs_";
  protected final String TEXT_64 = ";";
  protected final String TEXT_65 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_66 = ".";
  protected final String TEXT_67 = " = ";
  protected final String TEXT_68 = ".";
  protected final String TEXT_69 = ";";
  protected final String TEXT_70 = NL + "\t\t}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	CodeGeneratorArgument loadCodeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)loadCodeGenArgument.getArgument();
	final String cid = node.getUniqueName();

	boolean useExistingConn = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node,"__DIE_ON_ERROR__"));
	String propagateRecordset = ElementParameterParser.getValue(node,"__PROPAGATE_RECORD_SET__");
	String recordsetColumn = ElementParameterParser.getValue(node,"__RECORD_SET_COLUMN__");
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	boolean usePrepareStatement = "true".equals(ElementParameterParser.getValue(node,"__USE_PREPAREDSTATEMENT__"));
	List<Map<String, String>> prepareStatementParameters = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__SET_PREPAREDSTATEMENT_PARAMETERS__");
	 
	String dbquery= ElementParameterParser.getValue(node, "__QUERY__");
	dbquery = org.talend.core.model.utils.NodeUtil.replaceCRLFInMEMO_SQL(dbquery);
	
	String incomingConnName = null;
	
	Set<String> inputCols = new HashSet<String>();
	
	List<IMetadataColumn> columnList = null;
	
	String rejectConnName = null;
	List<? extends IConnection> rejectConns = node.getOutgoingConnections("REJECT");
	if(rejectConns != null && rejectConns.size() > 0) {
	    IConnection rejectConn = rejectConns.get(0);
	    rejectConnName = rejectConn.getName();
	}
	List<IMetadataColumn> rejectColumnList = null;
	IMetadataTable metadataTable = node.getMetadataFromConnector("REJECT");
	if(metadataTable != null) {
	    rejectColumnList = metadataTable.getListColumns();      
	}
	
	List<? extends IConnection> outgoingConns = node.getOutgoingSortedConnections();
	
	boolean hasOutgoingDataConnection = false;
	for(IConnection conn : outgoingConns) {
	    if (conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
			if(!hasOutgoingDataConnection){
				hasOutgoingDataConnection = true;
			}

    stringBuffer.append(TEXT_1);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_2);
          
	    }
	}

    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_4);
    stringBuffer.append(dbquery);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    
	List<IMetadataTable> metadatas = node.getMetadataList();
	if ((metadatas!=null)&&(metadatas.size()>0)) {
		IMetadataTable metadata = metadatas.get(0);
		if (metadata!=null) {
			List< ? extends IConnection> conns = node.getIncomingConnections();
			columnList = metadata.getListColumns();
			if(conns != null && conns.size()>0){
				IConnection conn = conns.get(0);
				if (conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
					incomingConnName = conn.getName();
					IMetadataTable inputMetadataTable = conn.getMetadataTable();
					for (IMetadataColumn inputCol : inputMetadataTable.getListColumns()) {
						inputCols.add(inputCol.getLabel());
					}
				}
			}//end of connection size.
		}//end of metadatas
	}
	if(!hasOutgoingDataConnection || columnList == null || columnList.size() < 1){
		propagateRecordset = "false";
	}

    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_9);
    
	if(("true").equals(propagateRecordset)) {

    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_11);
    
	}

    stringBuffer.append(TEXT_12);
    		
		if (usePrepareStatement) {
			for (Map<String, String> param : prepareStatementParameters) {
				if ("Date".equals(param.get("PARAMETER_TYPE"))) {

    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_14);
    stringBuffer.append(param.get("PARAMETER_INDEX"));
    stringBuffer.append(TEXT_15);
    stringBuffer.append(param.get("PARAMETER_VALUE"));
    stringBuffer.append(TEXT_16);
    
				} else {

    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_18);
    stringBuffer.append(param.get("PARAMETER_TYPE"));
    stringBuffer.append(TEXT_19);
    stringBuffer.append(param.get("PARAMETER_INDEX"));
    stringBuffer.append(TEXT_20);
    stringBuffer.append(param.get("PARAMETER_VALUE"));
    stringBuffer.append(TEXT_21);
    
				}
			}
			
			if(("true").equals(propagateRecordset)) {

    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_24);
    
			} else {

    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_26);
    
			}
		} else {
			if(("true").equals(propagateRecordset)) {

    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_30);
    
			} else {

    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_33);
    
			}
		}

    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    
		if (dieOnError) {

    
			if(isLog4jEnabled){

    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    
			}

    stringBuffer.append(TEXT_38);
    
		} else {
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    
			}
			if(rejectConnName != null && rejectColumnList != null && rejectColumnList.size() > 0) {

    stringBuffer.append(TEXT_41);
    stringBuffer.append(rejectConnName );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(rejectConnName );
    stringBuffer.append(TEXT_43);
    
            if(incomingConnName!=null) {
					for(IMetadataColumn column : columnList) {
                	if (inputCols.contains(column.getLabel())) {

    stringBuffer.append(TEXT_44);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_46);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_48);
    
						}
					}
				}

    stringBuffer.append(TEXT_49);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(node.getUniqueName() );
    stringBuffer.append(TEXT_52);
    
			} else {

    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    
			}
		} 

    stringBuffer.append(TEXT_55);
    
		
	if(outgoingConns != null && outgoingConns.size() > 0) {

    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    
			for(IConnection outgoingConn : outgoingConns) {
				if(rejectConnName == null || (rejectConnName != null && !outgoingConn.getName().equals(rejectConnName))) {
					if(outgoingConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {

    stringBuffer.append(TEXT_58);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_59);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_60);
    
						for(IMetadataColumn column : columnList) {
							if(("true").equals(propagateRecordset) && column.getLabel().equals(recordsetColumn)){

    stringBuffer.append(TEXT_61);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_62);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_64);
    
							} else {
								if(incomingConnName!=null){
									if (inputCols.contains(column.getLabel())) {

    stringBuffer.append(TEXT_65);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_66);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_67);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_69);
    
									}
								}
							}
						}
					}
				}
			}

    stringBuffer.append(TEXT_70);
    
	}

    return stringBuffer.toString();
  }
}
