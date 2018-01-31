package org.talend.designer.codegen.translators.databases.neo4j;

import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TNeo4jInputMainJava
{
  protected static String nl;
  public static synchronized TNeo4jInputMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TNeo4jInputMainJava result = new TNeo4jInputMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\tjava.util.Map<String, Object> row_";
  protected final String TEXT_2 = " = resultIterator_";
  protected final String TEXT_3 = ".next();" + NL + "\tObject value_";
  protected final String TEXT_4 = " = null;";
  protected final String TEXT_5 = NL + "\t\texpression_";
  protected final String TEXT_6 = " = ";
  protected final String TEXT_7 = "!=null?";
  protected final String TEXT_8 = ":\"\";" + NL + "\t\tvalue_";
  protected final String TEXT_9 = " = row_";
  protected final String TEXT_10 = ".get(expression_";
  protected final String TEXT_11 = ");" + NL + "\t\tif (value_";
  protected final String TEXT_12 = " == null) {" + NL + "\t\t\tvalue_";
  protected final String TEXT_13 = " = neo4jUtil_";
  protected final String TEXT_14 = ".getPropertyValue(expression_";
  protected final String TEXT_15 = ",row_";
  protected final String TEXT_16 = ");" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_17 = NL + "\t\t\tif (value_";
  protected final String TEXT_18 = " == null) {" + NL + "\t\t\t\t";
  protected final String TEXT_19 = ".";
  protected final String TEXT_20 = " = ";
  protected final String TEXT_21 = ";" + NL + "\t\t\t} else {" + NL + "\t\t\t\t";
  protected final String TEXT_22 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_23 = " instanceof Number) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_24 = ".";
  protected final String TEXT_25 = " = new java.util.Date(((Number) value_";
  protected final String TEXT_26 = ").longValue());" + NL + "\t\t\t\t\t";
  protected final String TEXT_27 = NL + "\t\t\t\t\t\t} else if (value_";
  protected final String TEXT_28 = " instanceof String) {" + NL + "\t\t\t\t\t\t\tjava.text.SimpleDateFormat formater_";
  protected final String TEXT_29 = " = new SimpleDateFormat(";
  protected final String TEXT_30 = ");" + NL + "\t\t\t\t\t\t\ttry {" + NL + "            \t\t\t\t\t";
  protected final String TEXT_31 = ".";
  protected final String TEXT_32 = " = formater_";
  protected final String TEXT_33 = ".parse((String)value_";
  protected final String TEXT_34 = ");" + NL + "        \t\t\t\t\t} catch (ParseException e_";
  protected final String TEXT_35 = ") {" + NL + "            \t\t\t\t\tthrow new RuntimeException(\"Unable to parse date\");" + NL + "        \t\t\t\t\t}" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_36 = NL + "\t\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t\tthrow new RuntimeException(\"Unable to parse date, review your configuration\");" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_37 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_38 = " instanceof java.util.Collection<?>) {\t\t\t\t" + NL + "\t\t\t\t\t\tjava.util.Collection<?> collection = (java.util.Collection<?>) value_";
  protected final String TEXT_39 = ";\t\t\t\t" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_40 = ".";
  protected final String TEXT_41 = " = new java.util.ArrayList(collection);" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\tthrow new RuntimeException(\"The returned parameter is not a Collection\");" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_42 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_43 = " instanceof String) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_44 = ".";
  protected final String TEXT_45 = " = (String) value_";
  protected final String TEXT_46 = ";" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_47 = ".";
  protected final String TEXT_48 = " = value_";
  protected final String TEXT_49 = ".toString();" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_50 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_51 = " instanceof Long) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_52 = ".";
  protected final String TEXT_53 = " = (Long) value_";
  protected final String TEXT_54 = ";" + NL + "\t\t\t\t\t} else if (value_";
  protected final String TEXT_55 = " instanceof Double) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_56 = ".";
  protected final String TEXT_57 = " = (Long) Math.round((Double) value_";
  protected final String TEXT_58 = ");" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_59 = ".";
  protected final String TEXT_60 = " = new Long(value_";
  protected final String TEXT_61 = ".toString());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_62 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_63 = " instanceof Integer) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_64 = ".";
  protected final String TEXT_65 = " = (Integer) value_";
  protected final String TEXT_66 = ";" + NL + "\t\t\t\t\t} else if (value_";
  protected final String TEXT_67 = " instanceof Double) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_68 = ".";
  protected final String TEXT_69 = " = (int) Math.round((Double) value_";
  protected final String TEXT_70 = ");" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_71 = ".";
  protected final String TEXT_72 = " = new Integer(value_";
  protected final String TEXT_73 = ".toString());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_74 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_75 = " instanceof Float) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_76 = ".";
  protected final String TEXT_77 = " = (Float) value_";
  protected final String TEXT_78 = ";" + NL + "\t\t\t\t\t} else if (value_";
  protected final String TEXT_79 = " instanceof Double) {" + NL + "\t\t\t\t\t \t";
  protected final String TEXT_80 = ".";
  protected final String TEXT_81 = " = ((Double) value_";
  protected final String TEXT_82 = ").floatValue();" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_83 = ".";
  protected final String TEXT_84 = " = new Float(value_";
  protected final String TEXT_85 = ".toString());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_86 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_87 = " instanceof Boolean) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_88 = ".";
  protected final String TEXT_89 = " = (Boolean) value_";
  protected final String TEXT_90 = ";" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_91 = ".";
  protected final String TEXT_92 = " = new Boolean(value_";
  protected final String TEXT_93 = ".toString());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_94 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_95 = " instanceof Double) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_96 = ".";
  protected final String TEXT_97 = " = (Double) value_";
  protected final String TEXT_98 = ";" + NL + "\t\t\t\t\t} else if (value_";
  protected final String TEXT_99 = " instanceof Float) {" + NL + "\t\t\t\t\t \t";
  protected final String TEXT_100 = ".";
  protected final String TEXT_101 = " = ((Float) value_";
  protected final String TEXT_102 = ").doubleValue();" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_103 = ".";
  protected final String TEXT_104 = " = new Double(value_";
  protected final String TEXT_105 = ".toString());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_106 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_107 = " instanceof byte[]) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_108 = ".";
  protected final String TEXT_109 = " = (byte[]) value_";
  protected final String TEXT_110 = ";" + NL + "\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_111 = ".";
  protected final String TEXT_112 = "=(new sun.misc.BASE64Decoder()).decodeBuffer(value_";
  protected final String TEXT_113 = ".toString());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_114 = NL + "\t\t\t\t\t";
  protected final String TEXT_115 = ".";
  protected final String TEXT_116 = " = value_";
  protected final String TEXT_117 = ";" + NL + "\t\t\t\t\t";
  protected final String TEXT_118 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_119 = " instanceof ";
  protected final String TEXT_120 = ") {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_121 = ".";
  protected final String TEXT_122 = " = (";
  protected final String TEXT_123 = ") value_";
  protected final String TEXT_124 = ";" + NL + "\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_125 = ".";
  protected final String TEXT_126 = "=ParserUtils.parseTo_";
  protected final String TEXT_127 = "(value_";
  protected final String TEXT_128 = ".toString());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_129 = NL + "\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_130 = NL + "nb_line_";
  protected final String TEXT_131 = "++;";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode) codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    List<Map<String, String>> mapping = (List<Map<String, String>>) ElementParameterParser.getObjectValue(node, "__MAPPING__");
    
    // Retrieve internal schema
    List<IMetadataColumn> columns = null;
    List<IMetadataTable> metadatas = node.getMetadataList();    
   	Map<String, IMetadataColumn> columnsMapped = new HashMap<String, IMetadataColumn>();
    if (metadatas != null && metadatas.size() > 0) {
    	IMetadataTable metadata = metadatas.get(0);
    	if (metadata != null){
    		columns = metadata.getListColumns();
    	}
    	for (Map<String, String> rowMapping : mapping) {
	    	String returnParameter = rowMapping.get("RETURN_PARAMETER");
	    	IMetadataColumn column = metadata.getColumn(rowMapping.get("SCHEMA_COLUMN"));
	    	columnsMapped.put(returnParameter, column);
    	}
    }
    
    // Retrieve input and output Talend connections
    List<? extends IConnection> inputs = node.getIncomingConnections();
    List<? extends IConnection> outputs = node.getOutgoingSortedConnections();
    String incomingConnName = null;
    String outgoingConnName = null;
    
    if (inputs.size() > 0) {
    	IConnection in = inputs.get(0);
    	incomingConnName = in.getName();
   	}
   	
   	if (outputs.size() > 0) {
   		IConnection out = outputs.get(0);
   		outgoingConnName = out.getName();
   	}
    

    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    
	for (Map.Entry<String, IMetadataColumn> columnMapped : columnsMapped.entrySet()) {
		String returnParameter = columnMapped.getKey();	
		IMetadataColumn column = columnMapped.getValue();	
		
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(returnParameter);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(returnParameter);
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
    
		String cType = column.getTalendType();
		String typeToGenerate = JavaTypesManager.getTypeToGenerate(cType, column.isNullable());
		if (outgoingConnName != null) {
			
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate));
    stringBuffer.append(TEXT_21);
    
				if (cType.equals("id_Date")) {
					
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    
					if (column.getPattern() != null && !column.getPattern().isEmpty()) {
						
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(column.getPattern());
    stringBuffer.append(TEXT_30);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    
					} else {
						
    stringBuffer.append(TEXT_36);
    
					}
				} else if (cType.equals("id_List")) {
					
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_41);
    
				} else if (cType.equals("id_String")) {
					
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    
				} else if (cType.equals("id_Long")) {
					
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    
				} else if (cType.equals("id_Integer")) {
					
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    
				} else if (cType.equals("id_Float")) {
					
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    
				} else if (cType.equals("id_Boolean")) {
					
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    
				} else if (cType.equals("id_Double")) {
					
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    
				} else if (cType.equals("id_byte[]")) {
					
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_113);
    
				} else if (cType.equals("id_Object")) {
					
    stringBuffer.append(TEXT_114);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    
				} else{
					
    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(JavaTypesManager.getTypeToGenerate(cType,true));
    stringBuffer.append(TEXT_120);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(JavaTypesManager.getTypeToGenerate(cType,true));
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(outgoingConnName);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(typeToGenerate);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    
				}
			
    stringBuffer.append(TEXT_129);
    
		}
	}

    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    return stringBuffer.toString();
  }
}
