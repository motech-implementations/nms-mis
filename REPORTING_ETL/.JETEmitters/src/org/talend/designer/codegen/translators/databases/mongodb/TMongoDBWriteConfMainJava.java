package org.talend.designer.codegen.translators.databases.mongodb;

import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import java.util.List;
import java.util.Map;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;

public class TMongoDBWriteConfMainJava
{
  protected static String nl;
  public static synchronized TMongoDBWriteConfMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMongoDBWriteConfMainJava result = new TMongoDBWriteConfMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\tcom.mongodb.WriteResult resultMessage_";
  protected final String TEXT_2 = "=null;" + NL + "\t\t\t";
  protected final String TEXT_3 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_4 = " - Trying to parse a json string.\");" + NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_5 = " - The JSON string:\"+json_";
  protected final String TEXT_6 = ".toString());" + NL + "\t\t\t";
  protected final String TEXT_7 = NL + "\t\t\tObject data_";
  protected final String TEXT_8 = "=com.mongodb.util.JSON.parse(json_";
  protected final String TEXT_9 = ".toString());" + NL + "\t\t\t";
  protected final String TEXT_10 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_11 = " - Parsed Successfully.\");" + NL + "\t\t\t";
  protected final String TEXT_12 = NL + NL + "\t\t\t// Replace Date and Bytes by true mongoDB types." + NL + "            Object ";
  protected final String TEXT_13 = "_dbobject = data_";
  protected final String TEXT_14 = ";" + NL + "            String pathString_";
  protected final String TEXT_15 = " = \"\";" + NL + "            String[] path_";
  protected final String TEXT_16 = ";" + NL + "\t\t\t";
  protected final String TEXT_17 = NL + "                            // root";
  protected final String TEXT_18 = NL + "                            ";
  protected final String TEXT_19 = "_dbobject = (com.mongodb.BasicDBObject) data_";
  protected final String TEXT_20 = ";" + NL + "                            pathString_";
  protected final String TEXT_21 = " = \"";
  protected final String TEXT_22 = "\";";
  protected final String TEXT_23 = NL + "                                pathString_";
  protected final String TEXT_24 = " = pathString_";
  protected final String TEXT_25 = ".substring(pathString_";
  protected final String TEXT_26 = ".indexOf(\"/\") + 1);";
  protected final String TEXT_27 = NL + "                            path_";
  protected final String TEXT_28 = " = pathString_";
  protected final String TEXT_29 = ".split(\"/\");";
  protected final String TEXT_30 = NL + "                                mongoDBTreeConverter_";
  protected final String TEXT_31 = ".processTree(";
  protected final String TEXT_32 = "_dbobject, path_";
  protected final String TEXT_33 = ", ";
  protected final String TEXT_34 = ");";
  protected final String TEXT_35 = NL + "                                mongoDBTreeConverter_";
  protected final String TEXT_36 = ".processTree(";
  protected final String TEXT_37 = "_dbobject, path_";
  protected final String TEXT_38 = ", null);";
  protected final String TEXT_39 = NL + "                            // loop";
  protected final String TEXT_40 = NL + "                            ";
  protected final String TEXT_41 = "_dbobject = (com.mongodb.BasicDBObject) data_";
  protected final String TEXT_42 = ";" + NL + "                            pathString_";
  protected final String TEXT_43 = " = \"";
  protected final String TEXT_44 = "\";";
  protected final String TEXT_45 = NL + "                                pathString_";
  protected final String TEXT_46 = " = pathString_";
  protected final String TEXT_47 = ".substring(pathString_";
  protected final String TEXT_48 = ".indexOf(\"/\") + 1);";
  protected final String TEXT_49 = NL + "                            path_";
  protected final String TEXT_50 = " = pathString_";
  protected final String TEXT_51 = ".split(\"/\");";
  protected final String TEXT_52 = NL + "                                mongoDBTreeConverter_";
  protected final String TEXT_53 = ".processTree(";
  protected final String TEXT_54 = "_dbobject, path_";
  protected final String TEXT_55 = ", ";
  protected final String TEXT_56 = ");";
  protected final String TEXT_57 = NL + "                                mongoDBTreeConverter_";
  protected final String TEXT_58 = ".processTree(";
  protected final String TEXT_59 = "_dbobject, path_";
  protected final String TEXT_60 = ", null);";
  protected final String TEXT_61 = NL + "                            // group";
  protected final String TEXT_62 = NL + "                            ";
  protected final String TEXT_63 = "_dbobject = (com.mongodb.BasicDBObject) data_";
  protected final String TEXT_64 = ";" + NL + "                            pathString_";
  protected final String TEXT_65 = " = \"";
  protected final String TEXT_66 = "\";";
  protected final String TEXT_67 = NL + "                                pathString_";
  protected final String TEXT_68 = " = pathString_";
  protected final String TEXT_69 = ".substring(pathString_";
  protected final String TEXT_70 = ".indexOf(\"/\") + 1);";
  protected final String TEXT_71 = NL + "                            path_";
  protected final String TEXT_72 = " = pathString_";
  protected final String TEXT_73 = ".split(\"/\");";
  protected final String TEXT_74 = NL + "                                mongoDBTreeConverter_";
  protected final String TEXT_75 = ".processTree(";
  protected final String TEXT_76 = "_dbobject, path_";
  protected final String TEXT_77 = ", ";
  protected final String TEXT_78 = ");";
  protected final String TEXT_79 = NL + "                                mongoDBTreeConverter_";
  protected final String TEXT_80 = ".processTree(";
  protected final String TEXT_81 = "_dbobject, path_";
  protected final String TEXT_82 = ", null);";
  protected final String TEXT_83 = NL + "\t\t\t" + NL + "\t\t\tObject queryObj_";
  protected final String TEXT_84 = "=null;" + NL + "\t\t\tif(data_";
  protected final String TEXT_85 = "!=null){" + NL + "\t\t\t\t";
  protected final String TEXT_86 = NL + "\t\t\t\t\t\tif(bulkWriteOperationCounter_";
  protected final String TEXT_87 = " < bulkWriteOperationSize_";
  protected final String TEXT_88 = "){" + NL + "\t\t\t\t\t\t\tbulkWriteOperation_";
  protected final String TEXT_89 = ".insert((com.mongodb.DBObject)data_";
  protected final String TEXT_90 = ");" + NL + "\t\t\t\t\t\t\tbulkWriteOperationCounter_";
  protected final String TEXT_91 = "++;" + NL + "\t\t\t\t\t\t} else {" + NL + "                            bulkWriteOperation_";
  protected final String TEXT_92 = ".insert((com.mongodb.DBObject)data_";
  protected final String TEXT_93 = ");" + NL + "\t\t\t\t\t\t\tbulkWriteOperation_";
  protected final String TEXT_94 = ".execute();" + NL + "\t\t\t\t\t\t\tbulkWriteOperationCounter_";
  protected final String TEXT_95 = "=1;" + NL + "\t\t\t\t\t\t\tbulkWriteOperation_";
  protected final String TEXT_96 = " = coll_";
  protected final String TEXT_97 = ".initialize";
  protected final String TEXT_98 = "BulkOperation();" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_99 = NL + "\t\t\t\t\t\tresultMessage_";
  protected final String TEXT_100 = "=coll_";
  protected final String TEXT_101 = ".insert((com.mongodb.DBObject)data_";
  protected final String TEXT_102 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_103 = NL + "\t\t\t\t\t\tif(bulkWriteOperationCounter_";
  protected final String TEXT_104 = " < bulkWriteOperationSize_";
  protected final String TEXT_105 = "){" + NL + "\t\t\t\t\t\t\tbulkWriteOperation_";
  protected final String TEXT_106 = ".find((com.mongodb.DBObject)data_";
  protected final String TEXT_107 = ").remove();" + NL + "\t\t\t\t\t\t\tbulkWriteOperationCounter_";
  protected final String TEXT_108 = "++;" + NL + "\t\t\t\t\t\t}else{" + NL + "                            bulkWriteOperation_";
  protected final String TEXT_109 = ".find((com.mongodb.DBObject)data_";
  protected final String TEXT_110 = ").remove();" + NL + "\t\t\t\t\t\t\tbulkWriteOperation_";
  protected final String TEXT_111 = ".execute();" + NL + "\t\t\t\t\t\t\tbulkWriteOperationCounter_";
  protected final String TEXT_112 = "=1;" + NL + "\t\t\t\t\t\t\tbulkWriteOperation_";
  protected final String TEXT_113 = " = coll_";
  protected final String TEXT_114 = ".initialize";
  protected final String TEXT_115 = "BulkOperation();" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_116 = NL + "\t\t\t\t\t\tresultMessage_";
  protected final String TEXT_117 = "=coll_";
  protected final String TEXT_118 = ".remove((com.mongodb.DBObject)data_";
  protected final String TEXT_119 = ");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_120 = NL + "\t\t\t\t\tqueryObj_";
  protected final String TEXT_121 = " =((com.mongodb.DBObject)data_";
  protected final String TEXT_122 = ").get(";
  protected final String TEXT_123 = ");" + NL + "\t\t\t\t\tdata_";
  protected final String TEXT_124 = "=((com.mongodb.DBObject)data_";
  protected final String TEXT_125 = ").get(";
  protected final String TEXT_126 = ");" + NL + "\t\t\t\t\tif(data_";
  protected final String TEXT_127 = "!=null && queryObj_";
  protected final String TEXT_128 = "!=null){" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_129 = NL + "                                if(bulkWriteOperationCounter_";
  protected final String TEXT_130 = " < bulkWriteOperationSize_";
  protected final String TEXT_131 = "){" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_132 = ".find((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_133 = ").replaceOne((com.mongodb.DBObject)data_";
  protected final String TEXT_134 = ");" + NL + "                                    bulkWriteOperationCounter_";
  protected final String TEXT_135 = "++;" + NL + "                                }else{" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_136 = ".find((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_137 = ").replaceOne((com.mongodb.DBObject)data_";
  protected final String TEXT_138 = ");" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_139 = ".execute();" + NL + "                                    bulkWriteOperationCounter_";
  protected final String TEXT_140 = "=1;" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_141 = " = coll_";
  protected final String TEXT_142 = ".initialize";
  protected final String TEXT_143 = "BulkOperation();" + NL + "                                }";
  protected final String TEXT_144 = NL + "                                resultMessage_";
  protected final String TEXT_145 = "=coll_";
  protected final String TEXT_146 = ".update((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_147 = ",(com.mongodb.DBObject)data_";
  protected final String TEXT_148 = ");";
  protected final String TEXT_149 = NL + "                                if(bulkWriteOperationCounter_";
  protected final String TEXT_150 = " < bulkWriteOperationSize_";
  protected final String TEXT_151 = "){" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_152 = ".find((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_153 = ").update(new com.mongodb.BasicDBObject(\"$set\", (com.mongodb.DBObject)data_";
  protected final String TEXT_154 = "));" + NL + "                                    bulkWriteOperationCounter_";
  protected final String TEXT_155 = "++;" + NL + "                                }else{" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_156 = ".find((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_157 = ").update(new com.mongodb.BasicDBObject(\"$set\", (com.mongodb.DBObject)data_";
  protected final String TEXT_158 = "));" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_159 = ".execute();" + NL + "                                    bulkWriteOperationCounter_";
  protected final String TEXT_160 = "=1;" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_161 = " = coll_";
  protected final String TEXT_162 = ".initialize";
  protected final String TEXT_163 = "BulkOperation();" + NL + "                                }";
  protected final String TEXT_164 = NL + "                                resultMessage_";
  protected final String TEXT_165 = "=coll_";
  protected final String TEXT_166 = ".update((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_167 = ",new com.mongodb.BasicDBObject(\"$set\", (com.mongodb.DBObject)data_";
  protected final String TEXT_168 = "));";
  protected final String TEXT_169 = NL + "\t\t\t\t\t\t\t\tif(bulkWriteOperationCounter_";
  protected final String TEXT_170 = " < bulkWriteOperationSize_";
  protected final String TEXT_171 = "){" + NL + "\t\t\t\t\t\t\t\t\tcom.mongodb.BulkWriteRequestBuilder bulkWriteRequestBuilder_";
  protected final String TEXT_172 = " = bulkWriteOperation_";
  protected final String TEXT_173 = ".find((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_174 = ");" + NL + "\t\t\t\t\t\t\t\t\tcom.mongodb.BulkUpdateRequestBuilder bulkUpdateRequestBuilder_";
  protected final String TEXT_175 = " = bulkWriteRequestBuilder_";
  protected final String TEXT_176 = ".upsert();" + NL + "\t\t\t\t\t\t\t\t\tbulkUpdateRequestBuilder_";
  protected final String TEXT_177 = ".replaceOne((com.mongodb.DBObject)data_";
  protected final String TEXT_178 = ");" + NL + "\t\t\t\t\t\t\t\t\tbulkWriteOperationCounter_";
  protected final String TEXT_179 = "++;" + NL + "\t\t\t\t\t\t\t\t}else{" + NL + "                                    com.mongodb.BulkWriteRequestBuilder bulkWriteRequestBuilder_";
  protected final String TEXT_180 = " = bulkWriteOperation_";
  protected final String TEXT_181 = ".find((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_182 = ");" + NL + "                                    com.mongodb.BulkUpdateRequestBuilder bulkUpdateRequestBuilder_";
  protected final String TEXT_183 = " = bulkWriteRequestBuilder_";
  protected final String TEXT_184 = ".upsert();" + NL + "                                    bulkUpdateRequestBuilder_";
  protected final String TEXT_185 = ".replaceOne((com.mongodb.DBObject)data_";
  protected final String TEXT_186 = ");" + NL + "\t\t\t\t\t\t\t\t\tbulkWriteOperation_";
  protected final String TEXT_187 = ".execute();" + NL + "\t\t\t\t\t\t\t\t\tbulkWriteOperationCounter_";
  protected final String TEXT_188 = "=1;" + NL + "\t\t\t\t\t\t\t\t\tbulkWriteOperation_";
  protected final String TEXT_189 = " = coll_";
  protected final String TEXT_190 = ".initialize";
  protected final String TEXT_191 = "BulkOperation();" + NL + "\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_192 = NL + "\t\t\t\t\t\t\t\tresultMessage_";
  protected final String TEXT_193 = "=coll_";
  protected final String TEXT_194 = ".update((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_195 = ",(com.mongodb.DBObject)data_";
  protected final String TEXT_196 = ",true,false);" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_197 = NL + "                                if(bulkWriteOperationCounter_";
  protected final String TEXT_198 = " < bulkWriteOperationSize_";
  protected final String TEXT_199 = "){" + NL + "                                    com.mongodb.BulkWriteRequestBuilder bulkWriteRequestBuilder_";
  protected final String TEXT_200 = " = bulkWriteOperation_";
  protected final String TEXT_201 = ".find((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_202 = ");" + NL + "                                    com.mongodb.BulkUpdateRequestBuilder bulkUpdateRequestBuilder_";
  protected final String TEXT_203 = " = bulkWriteRequestBuilder_";
  protected final String TEXT_204 = ".upsert();" + NL + "                                    bulkUpdateRequestBuilder_";
  protected final String TEXT_205 = ".update(new com.mongodb.BasicDBObject(\"$set\", (com.mongodb.DBObject)data_";
  protected final String TEXT_206 = "));" + NL + "                                    bulkWriteOperationCounter_";
  protected final String TEXT_207 = "++;" + NL + "                                }else{" + NL + "                                    com.mongodb.BulkWriteRequestBuilder bulkWriteRequestBuilder_";
  protected final String TEXT_208 = " = bulkWriteOperation_";
  protected final String TEXT_209 = ".find((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_210 = ");" + NL + "                                    com.mongodb.BulkUpdateRequestBuilder bulkUpdateRequestBuilder_";
  protected final String TEXT_211 = " = bulkWriteRequestBuilder_";
  protected final String TEXT_212 = ".upsert();" + NL + "                                    bulkUpdateRequestBuilder_";
  protected final String TEXT_213 = ".update(new com.mongodb.BasicDBObject(\"$set\", (com.mongodb.DBObject)data_";
  protected final String TEXT_214 = "));" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_215 = ".execute();" + NL + "                                    bulkWriteOperationCounter_";
  protected final String TEXT_216 = "=1;" + NL + "                                    bulkWriteOperation_";
  protected final String TEXT_217 = " = coll_";
  protected final String TEXT_218 = ".initialize";
  protected final String TEXT_219 = "BulkOperation();" + NL + "                                }";
  protected final String TEXT_220 = NL + "                                resultMessage_";
  protected final String TEXT_221 = "=coll_";
  protected final String TEXT_222 = ".update((com.mongodb.DBObject)queryObj_";
  protected final String TEXT_223 = ",new com.mongodb.BasicDBObject(\"$set\", (com.mongodb.DBObject)data_";
  protected final String TEXT_224 = "),true,false);";
  protected final String TEXT_225 = "\t" + NL + "\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_226 = NL + "\t\t\t\t\t\t\tthrow new Exception(\"Data node or Query node configuration error! \");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_227 = NL + "\t\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_228 = " - Data node or Query node configuration error!\");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_229 = NL + "\t\t\t\t\t\t\tSystem.err.println(\"Data node or Query node configuration error!\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_230 = "\t" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_231 = NL + "    \t\t\t\tif(resultMessage_";
  protected final String TEXT_232 = "!=null && resultMessage_";
  protected final String TEXT_233 = ".getError()!=null){" + NL + "    \t\t\t\t\t";
  protected final String TEXT_234 = NL + "    \t\t\t\t\t\tthrow new Exception(resultMessage_";
  protected final String TEXT_235 = ".getError());" + NL + "    \t\t\t\t\t";
  protected final String TEXT_236 = NL + "    \t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_237 = " - \" + resultMessage_";
  protected final String TEXT_238 = ".getError());" + NL + "    \t\t\t\t\t\t";
  protected final String TEXT_239 = NL + "    \t\t\t\t\t\tSystem.err.println(resultMessage_";
  protected final String TEXT_240 = ".getError());" + NL + "    \t\t\t\t\t";
  protected final String TEXT_241 = NL + "    \t\t\t\t}" + NL + "    \t\t\t\t";
  protected final String TEXT_242 = NL + "\t\t\t}else{" + NL + "\t\t\t\t";
  protected final String TEXT_243 = NL + "\t\t\t\t\tthrow new Exception(\"There no data in json,Please check the json configuration\");" + NL + "\t\t\t\t";
  protected final String TEXT_244 = NL + "\t\t\t\t\tlog.error(\"";
  protected final String TEXT_245 = " - There no data in json,Please check the json configuration\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_246 = NL + "\t\t\t\t\tSystem.err.println(\"There no data in json,Please check the json configuration\");" + NL + "\t\t\t\t";
  protected final String TEXT_247 = "\t" + NL + "\t\t\t}\t\t" + NL + "\t\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();
	String cid = node.getUniqueName();
	
	String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");
	String dataNode = ElementParameterParser.getValue(node,"__DATA_NODE__");
	String queryNode = ElementParameterParser.getValue(node,"__QUERY_NODE__");
	boolean dieOnError = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
    String dbversion = ElementParameterParser.getValue(node, "__DB_VERSION__");

    boolean setBulkWrite = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__SET_BULK_WRITE__"));
    String bulkWriteOperationSize = ElementParameterParser.getValue(node, "__BULK_WRITE_SIZE__");
    String bulkWriteType = ElementParameterParser.getValue(node, "__BULK_WRITE_TYPE__");

    boolean removeRoot = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REMOVE_ROOT__"));
    List<Map<String, String>> rootTable = 
        (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ROOT__");
    List<Map<String, String>> loopTable = 
        (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__LOOP__");
    List<Map<String, String>> groupTable = 
            (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__GROUP__");

    // BulkWrite is only supportted on Mongo 2.6+ drivers
    final boolean bulkWrite = setBulkWrite & (!"MONGODB_2_5_X".equalsIgnoreCase(dbversion));
    
    List<IMetadataTable> metadatas = node.getMetadataList();
  	if(metadatas != null && metadatas.size() > 0){
      	IMetadataTable metadata = metadatas.get(0);
      	if(metadata != null){
		
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    
			}
			
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    
			}
			
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
            for (IMetadataColumn column: metadata.getListColumns()) {
                JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
                if ((javaType == JavaTypesManager.DATE) || (javaType == JavaTypesManager.BYTE_ARRAY)) {
                    for (Map<String, String> root: rootTable) {
                        if (root.get("COLUMN").equals(column.getLabel())) {
                            
    stringBuffer.append(TEXT_17);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(root.get("PATH"));
    stringBuffer.append(TEXT_22);
    
                            if (removeRoot) {
                                
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    
                            }
                            
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    
                            if (javaType == JavaTypesManager.DATE) {
                                
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(column.getPattern());
    stringBuffer.append(TEXT_34);
    
                            } else { // Byte Array
                                
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    
                            }
                            // note: we cannot break here, they may be multiple values
                        }
                    }

                    for (Map<String, String> loop: loopTable) {
                        if (loop.get("COLUMN").equals(column.getLabel())) {
                            
    stringBuffer.append(TEXT_39);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(loop.get("PATH"));
    stringBuffer.append(TEXT_44);
    
                            if (removeRoot) {
                                
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    
                            }
                            
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    
                            if (javaType == JavaTypesManager.DATE) {
                                
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(column.getPattern());
    stringBuffer.append(TEXT_56);
    
                            } else { // Byte Array
                                
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    
                            }
                            // note: we cannot break here, they may be multiple values
                        }
                    }

                    for (Map<String, String> group: groupTable) {
                        if (group.get("COLUMN").equals(column.getLabel())) {
                            
    stringBuffer.append(TEXT_61);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(group.get("PATH"));
    stringBuffer.append(TEXT_66);
    
                            if (removeRoot) {
                                
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    
                            }
                            
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    
                            if (javaType == JavaTypesManager.DATE) {
                                
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(column.getPattern());
    stringBuffer.append(TEXT_78);
    
                            } else { // Byte Array
                                
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    
                            }
                            // note: we cannot break here, they may be multiple values
                        }
                    }
                }
            }
			
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    
				if("Insert".equalsIgnoreCase(dataAction)){
					// Bulk Writes
					if(bulkWrite){
						// Add inserts untill bulksize is reached then execute and re-initialze the bulk operation
						
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(bulkWriteType);
    stringBuffer.append(TEXT_98);
    
					// Single Writes
					}else{
					
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    
					}
				// Other operations (UPDATE, UPSERT, DELETE)
				}else if("DELETE".equalsIgnoreCase(dataAction)){
				// Bulk Delete
					if(bulkWrite){
						
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
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
    stringBuffer.append(bulkWriteType);
    stringBuffer.append(TEXT_115);
    
					}else{
						
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    
					}
				}else{
				
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(queryNode);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(dataNode);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    
						if("UPDATE".equalsIgnoreCase(dataAction)){
	                        // Bulk Updates
                            if(bulkWrite){
                                
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(bulkWriteType);
    stringBuffer.append(TEXT_143);
    
                            }else{
                                
    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    
                            }
                        } else if("SET".equalsIgnoreCase(dataAction)){
                            // Bulk Updates
                            if(bulkWrite){
                                
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_151);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_153);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_157);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_158);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(bulkWriteType);
    stringBuffer.append(TEXT_163);
    
                            }else{
                                
    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_168);
    
                            }
                        } else if("UPSERT".equalsIgnoreCase(dataAction)){
                            // Bulk Upsert
							if(bulkWrite){
								
    stringBuffer.append(TEXT_169);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_170);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_186);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_187);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_188);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    stringBuffer.append(bulkWriteType);
    stringBuffer.append(TEXT_191);
    
							}else{
								
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_193);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_194);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_195);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_196);
    
							}
                        } else if("UPSERT_WITH_SET".equalsIgnoreCase(dataAction)){
                            // Bulk Upsert
                            if(bulkWrite){
                                
    stringBuffer.append(TEXT_197);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_198);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_200);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_201);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_204);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_205);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_210);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_211);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_212);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_213);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_215);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_216);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(bulkWriteType);
    stringBuffer.append(TEXT_219);
    
                            }else{
                                
    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_222);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_223);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_224);
    
                            }
                        }
						
    stringBuffer.append(TEXT_225);
    
						if(dieOnError){
						
    stringBuffer.append(TEXT_226);
    
						}else{
							if(isLog4jEnabled){
							
    stringBuffer.append(TEXT_227);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_228);
    
							}
							
    stringBuffer.append(TEXT_229);
    
						}
						
    stringBuffer.append(TEXT_230);
    
				}
				
                if (dbversion.equals("MONGODB_2_5_X")) {
                
    stringBuffer.append(TEXT_231);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_233);
    
    					if(dieOnError){
    					
    stringBuffer.append(TEXT_234);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_235);
    
    					}else{
    						if(isLog4jEnabled){
    						
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_238);
    
    						}
    						
    stringBuffer.append(TEXT_239);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_240);
    
    					}
    					
    stringBuffer.append(TEXT_241);
    
                }
    			
    stringBuffer.append(TEXT_242);
    
				if(dieOnError){
				
    stringBuffer.append(TEXT_243);
    
				}else{
					if(isLog4jEnabled){
					
    stringBuffer.append(TEXT_244);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_245);
    
					}
					
    stringBuffer.append(TEXT_246);
    
				}
				
    stringBuffer.append(TEXT_247);
    
		}
	}
	
    return stringBuffer.toString();
  }
}
