package org.talend.designer.codegen.translators.databases.dynamodb;

import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnectionCategory;
import java.util.List;
import java.util.Map;

public class TDynamoDBOutputMainJava
{
  protected static String nl;
  public static synchronized TDynamoDBOutputMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TDynamoDBOutputMainJava result = new TDynamoDBOutputMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\t\t\tint countKey_";
  protected final String TEXT_2 = "=0;" + NL + "\t\t\t\t\tint countValue_";
  protected final String TEXT_3 = "=0;" + NL + "\t\t\t\t";
  protected final String TEXT_4 = "\t" + NL + "\t\t\t\t\tObject hashKeyValue_";
  protected final String TEXT_5 = " = null;" + NL + "\t\t\t\t\tObject rangeKeyValue_";
  protected final String TEXT_6 = " = null;" + NL + "\t\t\t\t\titem_";
  protected final String TEXT_7 = " = new com.amazonaws.services.dynamodbv2.document.Item();" + NL + "\t\t\t\t\t";
  protected final String TEXT_8 = "\t\t\t\t" + NL + "\t\t\t\t\t\tif (";
  protected final String TEXT_9 = ".";
  protected final String TEXT_10 = " != null) {  \t\t\t\t" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_11 = "\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\tif(\"";
  protected final String TEXT_12 = "\".equalsIgnoreCase(";
  protected final String TEXT_13 = "))" + NL + "\t\t\t\t\t\t\t\t{\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\t\thashKeyValue_";
  protected final String TEXT_14 = " = ";
  protected final String TEXT_15 = ".";
  protected final String TEXT_16 = ";" + NL + "\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t\telse if(";
  protected final String TEXT_17 = "!=null && !";
  protected final String TEXT_18 = ".isEmpty() && \"";
  protected final String TEXT_19 = "\".equalsIgnoreCase(";
  protected final String TEXT_20 = "))" + NL + "\t\t\t\t\t\t\t\t{\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\t\trangeKeyValue_";
  protected final String TEXT_21 = " = ";
  protected final String TEXT_22 = ".";
  protected final String TEXT_23 = ";" + NL + "\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_24 = NL + "\t\t\t\t\t\t\t\titem_";
  protected final String TEXT_25 = " = item_";
  protected final String TEXT_26 = ".with(\"";
  protected final String TEXT_27 = "\", ";
  protected final String TEXT_28 = ".";
  protected final String TEXT_29 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_30 = NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t" + NL + "\t\t\t\t\t";
  protected final String TEXT_31 = NL + "\t\t\t\t\tif(";
  protected final String TEXT_32 = "!=null && !";
  protected final String TEXT_33 = ".isEmpty()){\t\t\t\t\t" + NL + "\t\t\t\t\t\titem_";
  protected final String TEXT_34 = " = item_";
  protected final String TEXT_35 = ".withPrimaryKey(";
  protected final String TEXT_36 = ", hashKeyValue_";
  protected final String TEXT_37 = ", ";
  protected final String TEXT_38 = ", rangeKeyValue_";
  protected final String TEXT_39 = ");\t\t\t\t" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\telse{" + NL + "\t\t\t\t\t\titem_";
  protected final String TEXT_40 = " = item_";
  protected final String TEXT_41 = ".withPrimaryKey(";
  protected final String TEXT_42 = ", hashKeyValue_";
  protected final String TEXT_43 = ");" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\tputItemSpec_";
  protected final String TEXT_44 = " = new com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec().withItem(item_";
  protected final String TEXT_45 = ");" + NL + "                    \t\t\t\t" + NL + "\t\t\t\t\t";
  protected final String TEXT_46 = NL + "\t\t\t\t\tObject hashKeyValue_";
  protected final String TEXT_47 = " = null;" + NL + "\t\t\t\t\tObject rangeKeyValue_";
  protected final String TEXT_48 = " = null;" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\tjava.util.HashMap<String, Object> valueMap_";
  protected final String TEXT_49 = " = new java.util.HashMap<String, Object>();\t" + NL + "\t\t\t\t\tjava.util.HashMap<String,String> nameMap_";
  protected final String TEXT_50 = " = new java.util.HashMap<String,String>();\t" + NL + "\t\t\t\t\tjava.util.HashMap<String,String> nameMapRevert_";
  protected final String TEXT_51 = " = new java.util.HashMap<String,String>();\t\t\t\t\t\t" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\t";
  protected final String TEXT_52 = NL + "\t\t\t\t\t\t\tnameMap_";
  protected final String TEXT_53 = ".put(\"";
  protected final String TEXT_54 = "\", \"";
  protected final String TEXT_55 = "\");" + NL + "\t\t\t\t\t\t\tnameMapRevert_";
  protected final String TEXT_56 = ".put(\"";
  protected final String TEXT_57 = "\",\"";
  protected final String TEXT_58 = "\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_59 = NL + "\t\t\t\t\t" + NL + "\t\t\t\t\tStringBuffer updateExpressionBuffer_";
  protected final String TEXT_60 = " = new StringBuffer();" + NL + "\t\t\t\t\tupdateExpressionBuffer_";
  protected final String TEXT_61 = ".append(\"set \");" + NL + "\t\t\t\t\t";
  protected final String TEXT_62 = "\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\tif(\"";
  protected final String TEXT_63 = "\".equalsIgnoreCase(";
  protected final String TEXT_64 = "))" + NL + "\t\t\t\t\t\t\t{\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\thashKeyValue_";
  protected final String TEXT_65 = " = ";
  protected final String TEXT_66 = ".";
  protected final String TEXT_67 = ";" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\telse if(";
  protected final String TEXT_68 = "!=null && !";
  protected final String TEXT_69 = ".isEmpty() && \"";
  protected final String TEXT_70 = "\".equalsIgnoreCase(";
  protected final String TEXT_71 = "))" + NL + "\t\t\t\t\t\t\t{\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\trangeKeyValue_";
  protected final String TEXT_72 = " = ";
  protected final String TEXT_73 = ".";
  protected final String TEXT_74 = ";" + NL + "\t\t\t\t\t\t\t}" + NL + "" + NL + "\t\t\t\t\t\t\tcountKey_";
  protected final String TEXT_75 = "++;" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_76 = NL + "\t\t\t\t\t\t\tif(countValue_";
  protected final String TEXT_77 = " > 0)" + NL + "\t\t\t\t\t\t\t{" + NL + "\t\t\t\t\t\t\t\tupdateExpressionBuffer_";
  protected final String TEXT_78 = ".append(\", \");" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\tif(nameMapRevert_";
  protected final String TEXT_79 = ".containsKey(\"";
  protected final String TEXT_80 = "\")){" + NL + "\t\t\t\t\t\t\t\tupdateExpressionBuffer_";
  protected final String TEXT_81 = ".append(nameMapRevert_";
  protected final String TEXT_82 = ".get(\"";
  protected final String TEXT_83 = "\"));" + NL + "\t\t\t\t\t\t\t\tupdateExpressionBuffer_";
  protected final String TEXT_84 = ".append(\" =:";
  protected final String TEXT_85 = "\");" + NL + "\t\t\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\t\t\tupdateExpressionBuffer_";
  protected final String TEXT_86 = ".append(\"";
  protected final String TEXT_87 = "  =:";
  protected final String TEXT_88 = "\");" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\tvalueMap_";
  protected final String TEXT_89 = ".put(\":";
  protected final String TEXT_90 = "\", ";
  protected final String TEXT_91 = ".";
  protected final String TEXT_92 = ");" + NL + "\t\t\t\t\t\t\tcountValue_";
  protected final String TEXT_93 = "++;" + NL + "\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_94 = NL + "\t\t\t\t\tif(";
  protected final String TEXT_95 = "!=null && !";
  protected final String TEXT_96 = ".isEmpty()){\t\t\t\t\t" + NL + "\t\t\t\t\t\tupdateItemSpec_";
  protected final String TEXT_97 = " = updateItemSpec_";
  protected final String TEXT_98 = ".withPrimaryKey(";
  protected final String TEXT_99 = ", hashKeyValue_";
  protected final String TEXT_100 = ", ";
  protected final String TEXT_101 = ", rangeKeyValue_";
  protected final String TEXT_102 = ");\t\t\t\t" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\telse{" + NL + "\t\t\t\t\t\tupdateItemSpec_";
  protected final String TEXT_103 = " = updateItemSpec_";
  protected final String TEXT_104 = ".withPrimaryKey(";
  protected final String TEXT_105 = ", hashKeyValue_";
  protected final String TEXT_106 = ");" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\tupdateItemSpec_";
  protected final String TEXT_107 = " = updateItemSpec_";
  protected final String TEXT_108 = ".withUpdateExpression(updateExpressionBuffer_";
  protected final String TEXT_109 = ".toString()).withValueMap(valueMap_";
  protected final String TEXT_110 = ");\t" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\tif(nameMap_";
  protected final String TEXT_111 = ".size()>0)" + NL + "\t\t\t\t\t{" + NL + "\t\t\t\t\t\tupdateItemSpec_";
  protected final String TEXT_112 = " = updateItemSpec_";
  protected final String TEXT_113 = ".withNameMap(nameMap_";
  protected final String TEXT_114 = ");" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_115 = NL + "\t\t\t\t\tObject hashKeyValue_";
  protected final String TEXT_116 = " = null;" + NL + "\t\t\t\t\tObject rangeKeyValue_";
  protected final String TEXT_117 = " = null;" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\t";
  protected final String TEXT_118 = NL + "\t\t\t\t\t\tif (";
  protected final String TEXT_119 = ".";
  protected final String TEXT_120 = " != null) {" + NL + "   " + NL + "\t\t\t\t\t\t";
  protected final String TEXT_121 = "\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\tif(\"";
  protected final String TEXT_122 = "\".equalsIgnoreCase(";
  protected final String TEXT_123 = "))" + NL + "\t\t\t\t\t\t\t\t{\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\t\thashKeyValue_";
  protected final String TEXT_124 = " = ";
  protected final String TEXT_125 = ".";
  protected final String TEXT_126 = ";" + NL + "\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t\telse if(";
  protected final String TEXT_127 = "!=null && !";
  protected final String TEXT_128 = ".isEmpty() && \"";
  protected final String TEXT_129 = "\".equalsIgnoreCase(";
  protected final String TEXT_130 = "))" + NL + "\t\t\t\t\t\t\t\t{\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\t\trangeKeyValue_";
  protected final String TEXT_131 = " = ";
  protected final String TEXT_132 = ".";
  protected final String TEXT_133 = ";" + NL + "\t\t\t\t\t\t\t\t}" + NL + "" + NL + "\t\t\t\t\t\t\t\tcountKey_";
  protected final String TEXT_134 = "++;" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_135 = NL + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t" + NL + "\t\t\t\t\t";
  protected final String TEXT_136 = NL + "\t\t\t\t\tif(";
  protected final String TEXT_137 = "!=null && !";
  protected final String TEXT_138 = ".isEmpty()){\t\t\t\t\t" + NL + "\t\t\t\t\t\tdeleteItemSpec_";
  protected final String TEXT_139 = " = deleteItemSpec_";
  protected final String TEXT_140 = ".withPrimaryKey(";
  protected final String TEXT_141 = ", hashKeyValue_";
  protected final String TEXT_142 = ", ";
  protected final String TEXT_143 = ", rangeKeyValue_";
  protected final String TEXT_144 = ");\t\t\t\t" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\telse{" + NL + "\t\t\t\t\t\tdeleteItemSpec_";
  protected final String TEXT_145 = " = deleteItemSpec_";
  protected final String TEXT_146 = ".withPrimaryKey(";
  protected final String TEXT_147 = ", hashKeyValue_";
  protected final String TEXT_148 = ");" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_149 = "\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\tputItemOutcome_";
  protected final String TEXT_150 = " = table_";
  protected final String TEXT_151 = ".putItem(putItemSpec_";
  protected final String TEXT_152 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_153 = NL + NL + "\t\t\t\t\t\t";
  protected final String TEXT_154 = NL + "\t\t\t\t\t\t\tupdateItemOutcome_";
  protected final String TEXT_155 = " = table_";
  protected final String TEXT_156 = ".updateItem(updateItemSpec_";
  protected final String TEXT_157 = ");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_158 = NL + "\t\t\t\t\t\t\tdeleteItemOutcome_";
  protected final String TEXT_159 = " = table_";
  protected final String TEXT_160 = ".deleteItem(deleteItemSpec_";
  protected final String TEXT_161 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_162 = NL + NL + "\t\t\t\t";
  protected final String TEXT_163 = NL + "\t\t\t\tnb_line_";
  protected final String TEXT_164 = " ++;" + NL + "\t\t\t\t" + NL + "\t\t\t\t";
  protected final String TEXT_165 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();
	String cid = node.getUniqueName();
	
	String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");

	List<? extends IConnection> inConns = node.getIncomingConnections(EConnectionType.FLOW_MAIN);

	String hashKeyName = ElementParameterParser.getValue(node, "__PARTITION_KEY__");
	String rangeKeyName = ElementParameterParser.getValue(node, "__SORT_KEY__");
	
	List<Map<String, String>> nameMap = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__NAME_MAPPING__");
	
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

				if(!"INSERT".equalsIgnoreCase(dataAction)){
				
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    
				}
				
				if ("INSERT".equalsIgnoreCase(dataAction)) {
					
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    
					for (int i = 0; i < sizeColumns; i++) {
						IMetadataColumn column = columnList.get(i);

						
    stringBuffer.append(TEXT_8);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_10);
    
							
							if(column.isKey())
							{	
								
    stringBuffer.append(TEXT_11);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_15);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_22);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_23);
    
							}
							else
							{
								
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_28);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_29);
    
							}	
							
    stringBuffer.append(TEXT_30);
    			
					}
					
					
    stringBuffer.append(TEXT_31);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    
					
  				}
				
				if ("UPDATE".equalsIgnoreCase(dataAction)) {
					
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    	
					for(Map<String,String> subNameMap : nameMap)
					{
						String placeholder = subNameMap.get("PLACEHOLDER");
						String name = subNameMap.get("NAME");
						
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(placeholder);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(placeholder);
    stringBuffer.append(TEXT_58);
    
					}
					
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    
					for (int i = 0; i < sizeColumns; i++) {
						IMetadataColumn column = columnList.get(i);
						
						if(column.isKey()) 
						{		
							
    stringBuffer.append(TEXT_62);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_63);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_66);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_70);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    
						
						}
						else
						{
							
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_87);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(column.getOriginalDbColumnName() );
    stringBuffer.append(TEXT_90);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_91);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    
						}				
					}
			
					
    stringBuffer.append(TEXT_94);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    
					
  				}
				
				if ("DELETE".equalsIgnoreCase(dataAction)) {
					
    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    
					for (int i = 0; i < sizeColumns; i++) {
						IMetadataColumn column = columnList.get(i);

						
    stringBuffer.append(TEXT_118);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_119);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_120);
    
						
							if(column.isKey()) 
							{	
								
    stringBuffer.append(TEXT_121);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_122);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_125);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_126);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_129);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_132);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    
							
							}
							
    stringBuffer.append(TEXT_135);
    			
					}
					
    stringBuffer.append(TEXT_136);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(rangeKeyName);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(hashKeyName);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    
  				}
				

				// INSERT operation
				if ("INSERT".equalsIgnoreCase(dataAction)) {
				
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_151);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_152);
    
				// Other operations (UPDATE, UPSERT, DELETE)
				}else{
				
    stringBuffer.append(TEXT_153);
    
						// UPDATE
						if("UPDATE".equalsIgnoreCase(dataAction)){
							
    stringBuffer.append(TEXT_154);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_157);
    							
                        // UPSERT
						} else if("UPSERT".equalsIgnoreCase(dataAction)){
                          

                        // DELETE
                        } else if("DELETE".equalsIgnoreCase(dataAction)){
							
    stringBuffer.append(TEXT_158);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    							
						}
						
    stringBuffer.append(TEXT_162);
    
				}

    			
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_164);
    
				
			}
		}
	}
	
    stringBuffer.append(TEXT_165);
    return stringBuffer.toString();
  }
}
