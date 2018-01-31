package org.talend.designer.codegen.translators.databases.couchdb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import java.util.List;
import java.util.Map;

public class TCouchDBOutputMainJava
{
  protected static String nl;
  public static synchronized TCouchDBOutputMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchDBOutputMainJava result = new TCouchDBOutputMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL + "\t\t\t\tmap_";
  protected final String TEXT_3 = " = new java.util.HashMap<Object,Object>();" + NL + "\t\t\t\tnb_line_";
  protected final String TEXT_4 = " ++;" + NL + "\t\t\t\t";
  protected final String TEXT_5 = NL + "\t\t\t\t\tlog.debug(\"";
  protected final String TEXT_6 = " - ";
  protected final String TEXT_7 = " the record \" + (nb_line_";
  protected final String TEXT_8 = ") + \".\");" + NL + "\t\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\t\t\tif(";
  protected final String TEXT_10 = ".";
  protected final String TEXT_11 = "!=null){" + NL + "\t\t\t\t\t\t\tString columnValue_";
  protected final String TEXT_12 = "=";
  protected final String TEXT_13 = ".";
  protected final String TEXT_14 = ".toString();" + NL + "\t\t\t\t\t\t\ttry{" + NL + "\t\t\t\t\t\t\t\tmap_";
  protected final String TEXT_15 = ".put(\"";
  protected final String TEXT_16 = "\",jsonParse_";
  protected final String TEXT_17 = ".parse(columnValue_";
  protected final String TEXT_18 = "));" + NL + "\t\t\t\t\t\t\t}catch(java.lang.Exception e_";
  protected final String TEXT_19 = "){" + NL + "\t\t\t\t\t\t\t\tmap_";
  protected final String TEXT_20 = ".put(\"";
  protected final String TEXT_21 = "\",";
  protected final String TEXT_22 = ".";
  protected final String TEXT_23 = ");" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_24 = NL + "\t\t\t\tmap_";
  protected final String TEXT_25 = ".put(\"_id\",";
  protected final String TEXT_26 = ".";
  protected final String TEXT_27 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_28 = NL + "\t\t\t\t\ttry{" + NL + "\t\t\t\t\t\tclient_";
  protected final String TEXT_29 = ".save(map_";
  protected final String TEXT_30 = ");" + NL + "\t\t\t\t\t\tnb_line_inserted_";
  protected final String TEXT_31 = " ++;" + NL + "\t\t\t\t\t}catch(org.lightcouch.DocumentConflictException e_";
  protected final String TEXT_32 = "){" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_33 = NL + "\t\t\t\t\t\t\tthrow e_";
  protected final String TEXT_34 = ";" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_35 = NL + "\t\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_36 = " - \"+e_";
  protected final String TEXT_37 = ".getMessage());" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_38 = NL + "\t\t\t\t\t\t\tSystem.err.println(e_";
  protected final String TEXT_39 = ".getMessage());" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_40 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_41 = NL + "\t\t\t\t\tString rev_";
  protected final String TEXT_42 = "=null;" + NL + "\t\t\t\t\ttry{" + NL + "\t\t\t\t\t\torg.lightcouch.Document updateObject_";
  protected final String TEXT_43 = " = client_";
  protected final String TEXT_44 = ".find(org.lightcouch.Document.class, ";
  protected final String TEXT_45 = ".";
  protected final String TEXT_46 = ");" + NL + "\t\t\t\t\t\trev_";
  protected final String TEXT_47 = "=updateObject_";
  protected final String TEXT_48 = ".getRevision();" + NL + "\t\t\t\t\t\tmap_";
  protected final String TEXT_49 = ".put(\"_rev\",rev_";
  protected final String TEXT_50 = ");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_51 = NL + "\t\t\t\t\t\t\tclient_";
  protected final String TEXT_52 = ".update(map_";
  protected final String TEXT_53 = ");" + NL + "\t\t\t\t\t\t\tnb_line_updated_";
  protected final String TEXT_54 = " ++;" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_55 = NL + "\t\t\t\t\t\t\tclient_";
  protected final String TEXT_56 = ".remove(";
  protected final String TEXT_57 = ".";
  protected final String TEXT_58 = ",rev_";
  protected final String TEXT_59 = ");" + NL + "\t\t\t\t\t\t\tnb_line_deleted_";
  protected final String TEXT_60 = " ++;" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_61 = NL + "\t\t\t\t\t}catch(org.lightcouch.CouchDbException e_";
  protected final String TEXT_62 = "){" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_63 = NL + "\t\t\t\t\t\t\tclient_";
  protected final String TEXT_64 = ".save(map_";
  protected final String TEXT_65 = ");" + NL + "\t\t\t\t\t\t\tnb_line_inserted_";
  protected final String TEXT_66 = " ++;" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_67 = NL + "\t\t\t\t\t\t\t\tthrow e_";
  protected final String TEXT_68 = ";" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_69 = NL + "\t\t\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_70 = " - \"+e_";
  protected final String TEXT_71 = ".getMessage());" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_72 = NL + "\t\t\t\t\t\t\t\tSystem.err.println(e_";
  protected final String TEXT_73 = ".getMessage());" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_74 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_75 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;

    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
	String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");
	String key = ElementParameterParser.getValue(node,"__KEY__");
	boolean dieOnError = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	List<? extends IConnection> inConns = node.getIncomingConnections(EConnectionType.FLOW_MAIN);
	IConnection inConn = null;
	IMetadataTable metadata = null;
	
	if(inConns!=null && inConns.size()> 0) {
		inConn = inConns.get(0);
		metadata = inConn.getMetadataTable();
	}
	
	if (metadata!=null) {    
		List< ? extends IConnection> conns = node.getIncomingConnections();
		for (IConnection conn : conns) {
			if (conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
				List<IMetadataColumn> columnList = metadata.getListColumns();
				int sizeColumns = columnList.size();
				
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    
				Map<String,String> actionMap=new java.util.HashMap<String,String>();
				if (isLog4jEnabled) {
					actionMap.put("INSERT","Inserting");
					actionMap.put("UPDATE","Updating");
					actionMap.put("DELETE","Deleting");
					actionMap.put("UPSERT","Upserting");
					
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(actionMap.get(dataAction));
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    
				}
				for (int i = 0; i < sizeColumns; i++) {
					IMetadataColumn column = columnList.get(i);
					if(!column.getLabel().equals(key)){
					
    stringBuffer.append(TEXT_9);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_13);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_21);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_23);
    
					}
				}
				
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(key);
    stringBuffer.append(TEXT_27);
    
               	if("INSERT".equals(dataAction)){
				
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    
						if(dieOnError){
						
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    
						}else{
							if(isLog4jEnabled){
							
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    
							}
							
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    
						}
						
    stringBuffer.append(TEXT_40);
    
            	}else {
				
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_45);
    stringBuffer.append(key);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    
						if("UPDATE".equals(dataAction)||"UPSERT".equals(dataAction)){
						
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    
		               	}
						if("DELETE".equals(dataAction)){
						
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_57);
    stringBuffer.append(key);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    
						}
						
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    
						if("UPSERT".equals(dataAction)){
						
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    
						}else{
							if(dieOnError){
							
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    
							}else{
								if(isLog4jEnabled){
								
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    
								}
								
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    
							}
						}
						
    stringBuffer.append(TEXT_74);
    
				}
			}
		}
	}
	
    stringBuffer.append(TEXT_75);
    return stringBuffer.toString();
  }
}
