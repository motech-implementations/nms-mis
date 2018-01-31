package org.talend.designer.codegen.translators.databases.couchdb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import java.util.List;
import java.util.Map;

public class TCouchDBWriteConfMainJava
{
  protected static String nl;
  public static synchronized TCouchDBWriteConfMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchDBWriteConfMainJava result = new TCouchDBWriteConfMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\t\t\tlog.debug(\"";
  protected final String TEXT_2 = " - ";
  protected final String TEXT_3 = " the record \" + (nb_line_";
  protected final String TEXT_4 = "+1) + \".\");" + NL + "\t\t\t\t";
  protected final String TEXT_5 = NL + "\t\t\t\tmap_";
  protected final String TEXT_6 = " = client_";
  protected final String TEXT_7 = ".getGson().fromJson(";
  protected final String TEXT_8 = ".";
  protected final String TEXT_9 = ".toString(),java.util.Map.class);" + NL + "\t\t\t\tmap_";
  protected final String TEXT_10 = ".put(\"_id\",";
  protected final String TEXT_11 = ".";
  protected final String TEXT_12 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_13 = NL + "\t\t\t\t\ttry{" + NL + "\t\t\t\t\t\tclient_";
  protected final String TEXT_14 = ".save(map_";
  protected final String TEXT_15 = ");" + NL + "\t\t\t\t\t\tnb_line_inserted_";
  protected final String TEXT_16 = " ++;" + NL + "\t\t\t\t\t}catch(org.lightcouch.DocumentConflictException e_";
  protected final String TEXT_17 = "){" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_18 = NL + "\t\t\t\t\t\t\tthrow e_";
  protected final String TEXT_19 = ";" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_21 = " - \"+e_";
  protected final String TEXT_22 = ".getMessage());" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_23 = NL + "\t\t\t\t\t\t\tSystem.err.println(e_";
  protected final String TEXT_24 = ".getMessage());" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_25 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_26 = NL + "\t\t\t\t\tString rev_";
  protected final String TEXT_27 = "=null;" + NL + "\t\t\t\t\ttry{" + NL + "\t\t\t\t\t\torg.lightcouch.Document updateObject_";
  protected final String TEXT_28 = " = client_";
  protected final String TEXT_29 = ".find(org.lightcouch.Document.class, ";
  protected final String TEXT_30 = ".";
  protected final String TEXT_31 = ");" + NL + "\t\t\t\t\t\trev_";
  protected final String TEXT_32 = "=updateObject_";
  protected final String TEXT_33 = ".getRevision();" + NL + "\t\t\t\t\t\tmap_";
  protected final String TEXT_34 = ".put(\"_rev\",rev_";
  protected final String TEXT_35 = ");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_36 = NL + "\t\t\t\t\t\t\tclient_";
  protected final String TEXT_37 = ".update(map_";
  protected final String TEXT_38 = ");" + NL + "\t\t\t\t\t\t\tnb_line_updated_";
  protected final String TEXT_39 = " ++;" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_40 = NL + "\t\t\t\t\t\t\tclient_";
  protected final String TEXT_41 = ".remove(";
  protected final String TEXT_42 = ".";
  protected final String TEXT_43 = ",rev_";
  protected final String TEXT_44 = ");" + NL + "\t\t\t\t\t\t\tnb_line_deleted_";
  protected final String TEXT_45 = " ++;" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_46 = NL + "\t\t\t\t\t}catch(java.lang.Exception e_";
  protected final String TEXT_47 = "){" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_48 = NL + "\t\t\t\t\t\t\tclient_";
  protected final String TEXT_49 = ".save(map_";
  protected final String TEXT_50 = ");" + NL + "\t\t\t\t\t\t\tnb_line_inserted_";
  protected final String TEXT_51 = " ++;" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_52 = NL + "\t\t\t\t\t\t\t\tthrow e_";
  protected final String TEXT_53 = ";" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_54 = NL + "\t\t\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_55 = " - \"+e_";
  protected final String TEXT_56 = ".getMessage());" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_57 = NL + "\t\t\t\t\t\t\t\tSystem.err.println(e_";
  protected final String TEXT_58 = ".getMessage());" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_59 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_60 = "\t" + NL + "\t\t\t\tnb_line_";
  protected final String TEXT_61 = " ++;" + NL + "\t\t\t";
  protected final String TEXT_62 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;

    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();

	String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");
	String jsonfield = ElementParameterParser.getValue(node,"__JSONFIELD__");
	String key = ElementParameterParser.getValue(node,"__KEY__");
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	boolean dieOnError = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
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
				Map<String,String> actionMap=new java.util.HashMap<String,String>();
				if(isLog4jEnabled){
					actionMap.put("INSERT","Inserting");
					actionMap.put("UPDATE","Updating");
					actionMap.put("DELETE","Deleting");
					actionMap.put("UPSERT","Upserting");
				
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(actionMap.get(dataAction));
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    
				}
				
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(jsonfield);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(key);
    stringBuffer.append(TEXT_12);
    
               	if("INSERT".equals(dataAction)){
				
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    
						if(dieOnError){
						
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
						}else{
							if(isLog4jEnabled){
							
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    
							}
							
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    
						}
						
    stringBuffer.append(TEXT_25);
    
            	}else {
				
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_30);
    stringBuffer.append(key);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    
						if("UPDATE".equals(dataAction)||"UPSERT".equals(dataAction)){
						
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    
		               	}
						if("DELETE".equals(dataAction)){
						
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(key);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    
						}
						
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    
						if("UPSERT".equals(dataAction)){
						
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    
						}else{
							if(dieOnError){
							
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    
							}else{
								if(isLog4jEnabled){
								
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    
								}
								
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    
							}
						}
						
    stringBuffer.append(TEXT_59);
    
				}
				
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    
			}
		}
	}
	
    stringBuffer.append(TEXT_62);
    return stringBuffer.toString();
  }
}
