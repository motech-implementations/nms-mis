package org.talend.designer.codegen.translators.bigdata.marklogic;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.metadata.IMetadataColumn;
import java.util.List;

public class TMarkLogicInputBeginJava
{
  protected static String nl;
  public static synchronized TMarkLogicInputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMarkLogicInputBeginJava result = new TMarkLogicInputBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "    Compile Err: QUERY OPTION NAME CAN'T BE NULL OR EMPTY STRING.";
  protected final String TEXT_3 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_4 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_5 = ");";
  protected final String TEXT_6 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_7 = " = ";
  protected final String TEXT_8 = "; ";
  protected final String TEXT_9 = NL + NL + "      long nb_line_";
  protected final String TEXT_10 = " = 0;" + NL + "      com.marklogic.client.DatabaseClient client_";
  protected final String TEXT_11 = " = null;";
  protected final String TEXT_12 = NL + "        client_";
  protected final String TEXT_13 = " = (com.marklogic.client.DatabaseClient)globalMap.get(\"";
  protected final String TEXT_14 = "\");";
  protected final String TEXT_15 = NL + "        com.marklogic.client.DatabaseClientFactory.Authentication authType_";
  protected final String TEXT_16 = " = null;";
  protected final String TEXT_17 = NL + "                   authType_";
  protected final String TEXT_18 = " = com.marklogic.client.DatabaseClientFactory.Authentication.DIGEST;";
  protected final String TEXT_19 = NL + "                   authType_";
  protected final String TEXT_20 = " = com.marklogic.client.DatabaseClientFactory.Authentication.BASIC;";
  protected final String TEXT_21 = NL + "                   Compile Err: UNKNOWN_AUTHENTICATION_TYPE: ";
  protected final String TEXT_22 = NL + "                   client_";
  protected final String TEXT_23 = " = com.marklogic.client.DatabaseClientFactory.newClient(";
  protected final String TEXT_24 = NL + "                          ";
  protected final String TEXT_25 = ", Integer.valueOf(";
  protected final String TEXT_26 = "), ";
  protected final String TEXT_27 = ", ";
  protected final String TEXT_28 = ", decryptedPassword_";
  protected final String TEXT_29 = ", authType_";
  protected final String TEXT_30 = NL + "                  );";
  protected final String TEXT_31 = NL + " " + NL + " \tcom.marklogic.client.document.DocumentManager docMgr_";
  protected final String TEXT_32 = " = client_";
  protected final String TEXT_33 = ".newDocumentManager();" + NL + " \t";
  protected final String TEXT_34 = NL;
  protected final String TEXT_35 = NL + "        com.marklogic.client.admin.QueryOptionsManager qryOptMgr_";
  protected final String TEXT_36 = " = client_";
  protected final String TEXT_37 = ".newServerConfigManager().newQueryOptionsManager();";
  protected final String TEXT_38 = NL + "            com.marklogic.client.io.StringHandle strHandle = new com.marklogic.client.io.StringHandle();" + NL + "            strHandle.withFormat(";
  protected final String TEXT_39 = NL + "                                                    com.marklogic.client.io.Format.XML";
  protected final String TEXT_40 = NL + "                                                    com.marklogic.client.io.Format.JSON";
  protected final String TEXT_41 = NL + "                                                    Compile Err: UNSUPPORTED QUERY OPTION TYPE ";
  protected final String TEXT_42 = ".";
  protected final String TEXT_43 = NL + "                                                 ).set(\"";
  protected final String TEXT_44 = "\");" + NL + "            qryOptMgr_";
  protected final String TEXT_45 = ".writeOptions(";
  protected final String TEXT_46 = ", strHandle);";
  protected final String TEXT_47 = NL + " com.marklogic.client.query.QueryManager qryMgr_";
  protected final String TEXT_48 = " = client_";
  protected final String TEXT_49 = ".newQueryManager();" + NL + " com.marklogic.client.query.StringQueryDefinition strQryDef_";
  protected final String TEXT_50 = " = qryMgr_";
  protected final String TEXT_51 = ".newStringDefinition(";
  protected final String TEXT_52 = NL + "                                                                                                               ";
  protected final String TEXT_53 = NL + "                                                                                                              );" + NL + " strQryDef_";
  protected final String TEXT_54 = ".setCriteria(";
  protected final String TEXT_55 = ");" + NL + " com.marklogic.client.io.SearchHandle searchHdl_";
  protected final String TEXT_56 = " = new com.marklogic.client.io.SearchHandle();" + NL + " qryMgr_";
  protected final String TEXT_57 = ".search(strQryDef_";
  protected final String TEXT_58 = ", searchHdl_";
  protected final String TEXT_59 = ");" + NL + " long matchedDocNumber_";
  protected final String TEXT_60 = " = searchHdl_";
  protected final String TEXT_61 = ".getTotalResults();" + NL + " long max_retrieve_num_";
  protected final String TEXT_62 = " = Long.valueOf(";
  protected final String TEXT_63 = ");" + NL + "      if(max_retrieve_num_";
  protected final String TEXT_64 = " <= 0){" + NL + "        nb_line_";
  protected final String TEXT_65 = " = matchedDocNumber_";
  protected final String TEXT_66 = ";" + NL + "      }else if(max_retrieve_num_";
  protected final String TEXT_67 = " <= matchedDocNumber_";
  protected final String TEXT_68 = "){" + NL + "        nb_line_";
  protected final String TEXT_69 = " = max_retrieve_num_";
  protected final String TEXT_70 = ";" + NL + "      }else{" + NL + "        nb_line_";
  protected final String TEXT_71 = " = matchedDocNumber_";
  protected final String TEXT_72 = ";" + NL + "      }" + NL + " int DEFAULT_PAGE_SIZE_";
  protected final String TEXT_73 = " = Integer.valueOf(";
  protected final String TEXT_74 = ");" + NL + " if(DEFAULT_PAGE_SIZE_";
  protected final String TEXT_75 = " <= 0){" + NL + " \tDEFAULT_PAGE_SIZE_";
  protected final String TEXT_76 = " = 10;" + NL + " }" + NL + " qryMgr_";
  protected final String TEXT_77 = ".setPageLength(DEFAULT_PAGE_SIZE_";
  protected final String TEXT_78 = " * 1L);" + NL + " final long page_number_";
  protected final String TEXT_79 = " = (long)Math.ceil(nb_line_";
  protected final String TEXT_80 = " / (DEFAULT_PAGE_SIZE_";
  protected final String TEXT_81 = " * 1.0));" + NL + " long start_";
  protected final String TEXT_82 = " = 1;" + NL + " long retrieved_doc_number_";
  protected final String TEXT_83 = " = 0;" + NL + " " + NL + " for(long i = 1; i < page_number_";
  protected final String TEXT_84 = " + 1; i++){//TagF2" + NL + " qryMgr_";
  protected final String TEXT_85 = ".search(strQryDef_";
  protected final String TEXT_86 = ", searchHdl_";
  protected final String TEXT_87 = ", start_";
  protected final String TEXT_88 = ");" + NL + " com.marklogic.client.query.MatchDocumentSummary[] matchDocSummary_";
  protected final String TEXT_89 = " = searchHdl_";
  protected final String TEXT_90 = ".getMatchResults();";
  protected final String TEXT_91 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();

    String host = ElementParameterParser.getValue(node,"__HOST__");
    String port = ElementParameterParser.getValue(node,"__PORT__");
    String userName = ElementParameterParser.getValue(node,"__USER_NAME__");
    String database = ElementParameterParser.getValue(node,"__DATABASE__");
    String authType = ElementParameterParser.getValue(node,"__AUTHENTICATION_TYPE__");
    
    String pageSize = ElementParameterParser.getValue(node,"__PAGE_SIZE__");

    final String MAXIMAL_RETRIEVE_NUMBER = ElementParameterParser.getValue(node, "__MAXIMAL_RETRIEVE_NUMBER__");
    boolean reuseConn = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
    boolean useQueryOption = "true".equals(ElementParameterParser.getValue(node,"__USE_QUERY_OPTION__"));
    String queryOptionName = ElementParameterParser.getValue(node, "__QUERY_OPTION_NAME__");
    boolean isValidQueryOptionName = false;
    if(queryOptionName != null && !"".equals(queryOptionName) && !"\"\"".equals(queryOptionName)){
        isValidQueryOptionName = true;
    }
    if(useQueryOption && !isValidQueryOptionName){
    
    stringBuffer.append(TEXT_2);
    
    }
    String queryOptionType = ElementParameterParser.getValue(node,"__QUERY_OPTION_TYPE__");
    String queryOptionLiterals = ElementParameterParser.getValue(node,"__QUERY_OPTION_LITERALS__");
    boolean isQueryOptionLiteralsEmpty = true;
    if(queryOptionLiterals != null && !"".equals(queryOptionLiterals) && !"\"\"".equals(queryOptionLiterals)){
        isQueryOptionLiteralsEmpty = false;
    }
    if(useQueryOption && !isQueryOptionLiteralsEmpty){
            if(queryOptionLiterals.startsWith("\"") && queryOptionLiterals.endsWith("\"")){
               queryOptionLiterals = queryOptionLiterals.substring(1, queryOptionLiterals.length()-1);
            }
               queryOptionLiterals = queryOptionLiterals.replace("\r","").replace("\n","").replace("\\\"", "\"").replace("\\", "\\\\").replace("\"", "\\\"");
    }
    String queryCriteria = ElementParameterParser.getValue(node,"__QUERY_CRITERIA__");

    String passwordFieldName = "__PASSWORD__";
 
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_5);
    } else {
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_8);
    }
    stringBuffer.append(TEXT_9);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_11);
    
    if(reuseConn){
      String connection = ElementParameterParser.getValue(node,"__CONNECTION__");
      String conn = "conn_" + connection;
     
    stringBuffer.append(TEXT_12);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_13);
    stringBuffer.append(conn);
    stringBuffer.append(TEXT_14);
    
    }else{
     
    stringBuffer.append(TEXT_15);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_16);
    
            if("DIGEST".equals(authType)){
                
    stringBuffer.append(TEXT_17);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_18);
    
            }else if("BASIC".equals(authType)){
                
    stringBuffer.append(TEXT_19);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_20);
    
            }else{
                
    stringBuffer.append(TEXT_21);
    stringBuffer.append( authType );
    
            }
        
    stringBuffer.append(TEXT_22);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(TEXT_24);
    stringBuffer.append( host );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( port );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( database );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( userName );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_30);
    
    }
 
    stringBuffer.append(TEXT_31);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_32);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_33);
    
	boolean inputMode = false;
	List<? extends IConnection> incomingConns = node.getIncomingConnections();
	if(incomingConns!=null) {
		for(IConnection inputConnection : incomingConns) {
			if(inputConnection!=null && inputConnection.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
				inputMode = true;
			}
		}
	}
	
	if(inputMode) {
		return stringBuffer.toString();
	}

    stringBuffer.append(TEXT_34);
    
 if(useQueryOption && isValidQueryOptionName){
 
    stringBuffer.append(TEXT_35);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_37);
    
        if(!isQueryOptionLiteralsEmpty){
        
    stringBuffer.append(TEXT_38);
    
                                                 if("XML".equals(queryOptionType)){
                                                 
    stringBuffer.append(TEXT_39);
    
                                                 }else if("JSON".equals(queryOptionType)){
                                                 
    stringBuffer.append(TEXT_40);
    
                                                 }else{
                                                 
    stringBuffer.append(TEXT_41);
    stringBuffer.append( queryOptionType );
    stringBuffer.append(TEXT_42);
    
                                                 }
                                                 
    stringBuffer.append(TEXT_43);
    stringBuffer.append( queryOptionLiterals );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_45);
    stringBuffer.append( queryOptionName );
    stringBuffer.append(TEXT_46);
    
        }
         
    
 }
  
    stringBuffer.append(TEXT_47);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_51);
     if(useQueryOption && isValidQueryOptionName){ 
    stringBuffer.append(TEXT_52);
    stringBuffer.append( queryOptionName );
     } 
    stringBuffer.append(TEXT_53);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_54);
    stringBuffer.append( queryCriteria );
    stringBuffer.append(TEXT_55);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_56);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_57);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_59);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_60);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_61);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_62);
    stringBuffer.append( MAXIMAL_RETRIEVE_NUMBER );
    stringBuffer.append(TEXT_63);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_64);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_66);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_68);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_69);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_70);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_71);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_72);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_73);
    stringBuffer.append( pageSize );
    stringBuffer.append(TEXT_74);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_75);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_76);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_77);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_78);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_79);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_80);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_81);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_82);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_83);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_84);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_85);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_86);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_87);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_88);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_89);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_90);
    stringBuffer.append(TEXT_91);
    return stringBuffer.toString();
  }
}
