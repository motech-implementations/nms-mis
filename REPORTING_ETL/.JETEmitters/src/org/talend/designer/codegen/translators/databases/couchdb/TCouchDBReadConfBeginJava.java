package org.talend.designer.codegen.translators.databases.couchdb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import java.util.Map;
import java.util.List;

public class TCouchDBReadConfBeginJava
{
  protected static String nl;
  public static synchronized TCouchDBReadConfBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchDBReadConfBeginJava result = new TCouchDBReadConfBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_3 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_4 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_5 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_6 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_7 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_8 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_9 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_10 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_11 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_12 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_13 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_14 = " - Written records count: \" + nb_line_";
  protected final String TEXT_15 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_16 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_17 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_18 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_19 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_20 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_21 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_22 = " - Writing the record \" + nb_line_";
  protected final String TEXT_23 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_24 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_25 = " - Processing the record \" + nb_line_";
  protected final String TEXT_26 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_27 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_28 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_29 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_30 = NL + "\t";
  protected final String TEXT_31 = NL + " \tString decryptedPassword_";
  protected final String TEXT_32 = " = null;" + NL + " \t";
  protected final String TEXT_33 = " " + NL + "    \tdecryptedPassword_";
  protected final String TEXT_34 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_35 = ");";
  protected final String TEXT_36 = NL + "    \tdecryptedPassword_";
  protected final String TEXT_37 = " = ";
  protected final String TEXT_38 = "; ";
  protected final String TEXT_39 = NL + "\torg.lightcouch.CouchDbClient client_";
  protected final String TEXT_40 = " = null;" + NL + "\tcom.google.gson.JsonParser jsonParse_";
  protected final String TEXT_41 = " = new com.google.gson.JsonParser();" + NL + "\t" + NL + "\t";
  protected final String TEXT_42 = NL + "\t\tclient_";
  protected final String TEXT_43 = " = (org.lightcouch.CouchDbClient)globalMap.get(\"client_";
  protected final String TEXT_44 = "\");" + NL + "\t\t";
  protected final String TEXT_45 = "\t" + NL + "\t\t\tif(client_";
  protected final String TEXT_46 = " != null) {" + NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_47 = " - Get an existing client from \" + \"";
  protected final String TEXT_48 = "\" + \".\");" + NL + "\t\t\t}" + NL + "\t\t";
  protected final String TEXT_49 = NL + "\t\tclient_";
  protected final String TEXT_50 = " = new org.lightcouch.CouchDbClient(";
  protected final String TEXT_51 = ", true, \"http\", ";
  protected final String TEXT_52 = ", ";
  protected final String TEXT_53 = ", ";
  protected final String TEXT_54 = ", decryptedPassword_";
  protected final String TEXT_55 = ");" + NL + "\t\t";
  protected final String TEXT_56 = NL + "\t\t\torg.lightcouch.ReplicationResult result_";
  protected final String TEXT_57 = " = null;" + NL + "\t\t\tList<org.lightcouch.Replication> replications_";
  protected final String TEXT_58 = " = new java.util.ArrayList<org.lightcouch.Replication>();" + NL + "\t\t\t";
  protected final String TEXT_59 = NL + "\t\t\t\tresult_";
  protected final String TEXT_60 = " = client_";
  protected final String TEXT_61 = ".replication()" + NL + "\t\t\t\t  .source(";
  protected final String TEXT_62 = ")" + NL + "\t\t\t\t  .target(";
  protected final String TEXT_63 = ") " + NL + "\t\t\t\t  .createTarget(";
  protected final String TEXT_64 = ")" + NL + "\t\t\t\t  .continuous(";
  protected final String TEXT_65 = ")" + NL + "\t\t\t\t  .trigger();" + NL + "\t\t\t\t";
  protected final String TEXT_66 = NL + "\t\t\t\t\tif(result_";
  protected final String TEXT_67 = ".isOk()){" + NL + "\t\t\t\t\t\torg.lightcouch.Replication replication_";
  protected final String TEXT_68 = "_";
  protected final String TEXT_69 = "=" + NL + "\t\t\t\t\t\tclient_";
  protected final String TEXT_70 = ".replication()" + NL + "\t\t\t\t\t\t  .source(";
  protected final String TEXT_71 = ")" + NL + "\t\t\t\t\t\t  .target(";
  protected final String TEXT_72 = ")" + NL + "\t\t\t\t\t\t  .createTarget(";
  protected final String TEXT_73 = ")" + NL + "\t\t\t\t\t\t  .continuous(";
  protected final String TEXT_74 = ")" + NL + "\t\t\t\t\t\t  .cancel(";
  protected final String TEXT_75 = ");" + NL + "\t\t\t\t\t\treplications_";
  protected final String TEXT_76 = ".add(replication_";
  protected final String TEXT_77 = "_";
  protected final String TEXT_78 = ");" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_79 = NL + "\t\tint nb_line_";
  protected final String TEXT_80 = " = 0;" + NL + "\t";
  protected final String TEXT_81 = "\t" + NL + "      \t\tjava.util.Map<String,Object> options_";
  protected final String TEXT_82 = "=new java.util.HashMap<String,Object>();" + NL + "      \t\tString viewPath_";
  protected final String TEXT_83 = " = \"_all_docs\";" + NL + "      \t\t";
  protected final String TEXT_84 = NL + "\t\t\t\tviewPath_";
  protected final String TEXT_85 = " = ";
  protected final String TEXT_86 = "+\"/\"+";
  protected final String TEXT_87 = ";" + NL + "\t\t\t\tString desDocId_";
  protected final String TEXT_88 = " = \"_design/\"+";
  protected final String TEXT_89 = ";" + NL + "\t\t\t\torg.lightcouch.DesignDocument designDoc_";
  protected final String TEXT_90 = "=null;" + NL + "\t\t\t\ttry{" + NL + "\t\t\t\t\tdesignDoc_";
  protected final String TEXT_91 = " = client_";
  protected final String TEXT_92 = ".find(org.lightcouch.DesignDocument.class,desDocId_";
  protected final String TEXT_93 = ");" + NL + "\t\t\t\t}catch(java.lang.Exception e_";
  protected final String TEXT_94 = "){" + NL + "\t\t\t\t}" + NL + "\t\t\t\tjava.util.Map<String, org.lightcouch.DesignDocument.MapReduce> views_";
  protected final String TEXT_95 = " = null;" + NL + "\t\t\t\torg.lightcouch.DesignDocument.MapReduce mapReduce_";
  protected final String TEXT_96 = " = new org.lightcouch.DesignDocument.MapReduce();" + NL + "\t\t\t\tmapReduce_";
  protected final String TEXT_97 = ".setMap(";
  protected final String TEXT_98 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_99 = NL + "\t\t\t\t\tmapReduce_";
  protected final String TEXT_100 = ".setReduce(";
  protected final String TEXT_101 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_102 = NL + "\t\t\t\t\t\tclient_";
  protected final String TEXT_103 = ".remove(designDoc_";
  protected final String TEXT_104 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_105 = NL + "\t\t\t\t\t\tif(designDoc_";
  protected final String TEXT_106 = " != null){" + NL + "\t\t\t\t\t\t\tclient_";
  protected final String TEXT_107 = ".remove(designDoc_";
  protected final String TEXT_108 = ");" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_109 = NL + "\t\t\t\t\tdesignDoc_";
  protected final String TEXT_110 = " = new org.lightcouch.DesignDocument();" + NL + "\t\t\t\t\tdesignDoc_";
  protected final String TEXT_111 = ".setId(desDocId_";
  protected final String TEXT_112 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_113 = NL + "\t\t\t\t\t\tviews_";
  protected final String TEXT_114 = " = new java.util.HashMap<String, org.lightcouch.DesignDocument.MapReduce>();" + NL + "\t\t\t\t\t\tviews_";
  protected final String TEXT_115 = ".put(";
  protected final String TEXT_116 = ",mapReduce_";
  protected final String TEXT_117 = ");" + NL + "\t\t\t\t\t\tdesignDoc_";
  protected final String TEXT_118 = ".setViews(views_";
  protected final String TEXT_119 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_120 = NL + "\t\t\t\t\t\tSystem.err.println(\"You should create a new view for the design document.\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_121 = NL + "\t\t\t\t\tclient_";
  protected final String TEXT_122 = ".save(designDoc_";
  protected final String TEXT_123 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_124 = NL + "\t\t\t\t\t\tif(designDoc_";
  protected final String TEXT_125 = " != null){" + NL + "\t\t\t\t\t\t\tif(designDoc_";
  protected final String TEXT_126 = ".getViews()!=null){" + NL + "\t\t\t\t\t\t\t\tviews_";
  protected final String TEXT_127 = " = designDoc_";
  protected final String TEXT_128 = ".getViews();" + NL + "\t\t\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\t\t\tviews_";
  protected final String TEXT_129 = " = new java.util.HashMap<String, org.lightcouch.DesignDocument.MapReduce>();" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\tif(!views_";
  protected final String TEXT_130 = ".containsKey(";
  protected final String TEXT_131 = ")){" + NL + "\t\t\t\t\t\t\t\tviews_";
  protected final String TEXT_132 = ".put(";
  protected final String TEXT_133 = ",mapReduce_";
  protected final String TEXT_134 = ");" + NL + "\t\t\t\t\t\t\t\tdesignDoc_";
  protected final String TEXT_135 = ".setViews(views_";
  protected final String TEXT_136 = ");" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_137 = NL + "\t\t\t\t\t\t\t\tSystem.err.println(\"Design Document named \" + ";
  protected final String TEXT_138 = " + \"not exist\");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_139 = NL + "\t\t\t\t\t\t\t\tdesignDoc_";
  protected final String TEXT_140 = " = new org.lightcouch.DesignDocument();" + NL + "\t\t\t\t\t\t\t\tdesignDoc_";
  protected final String TEXT_141 = ".setId(desDocId_";
  protected final String TEXT_142 = ");" + NL + "\t\t\t\t\t\t\t\tviews_";
  protected final String TEXT_143 = " = new java.util.HashMap<String, org.lightcouch.DesignDocument.MapReduce>();" + NL + "\t\t\t\t\t\t\t\tviews_";
  protected final String TEXT_144 = ".put(";
  protected final String TEXT_145 = ",mapReduce_";
  protected final String TEXT_146 = ");" + NL + "\t\t\t\t\t\t\t\tdesignDoc_";
  protected final String TEXT_147 = ".setViews(views_";
  protected final String TEXT_148 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_149 = NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\tclient_";
  protected final String TEXT_150 = ".update(designDoc_";
  protected final String TEXT_151 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_152 = NL + "\t\t\t\t\t\tif(designDoc_";
  protected final String TEXT_153 = " == null){" + NL + "\t\t\t\t\t\t\tdesignDoc_";
  protected final String TEXT_154 = " = new org.lightcouch.DesignDocument();" + NL + "\t\t\t\t\t\t\tdesignDoc_";
  protected final String TEXT_155 = ".setId(desDocId_";
  protected final String TEXT_156 = ");" + NL + "\t\t\t\t\t\t\tclient_";
  protected final String TEXT_157 = ".save(designDoc_";
  protected final String TEXT_158 = ");" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_159 = NL + "\t\t\torg.lightcouch.View view_";
  protected final String TEXT_160 = " = client_";
  protected final String TEXT_161 = ".view(viewPath_";
  protected final String TEXT_162 = ");" + NL + "\t\t\t";
  protected final String TEXT_163 = NL + "\t\t\t\tview_";
  protected final String TEXT_164 = ".startKey(";
  protected final String TEXT_165 = ");" + NL + "\t\t\t";
  protected final String TEXT_166 = NL + "\t\t\t\tview_";
  protected final String TEXT_167 = ".endKey(";
  protected final String TEXT_168 = ");" + NL + "\t\t\t";
  protected final String TEXT_169 = NL + "\t\t\t\tview_";
  protected final String TEXT_170 = ".startKeyDocId(";
  protected final String TEXT_171 = ");" + NL + "\t\t\t";
  protected final String TEXT_172 = NL + "\t\t\t\tview_";
  protected final String TEXT_173 = ".endKeyDocId(";
  protected final String TEXT_174 = ");" + NL + "\t\t\t";
  protected final String TEXT_175 = NL + "\t\t\t\tview_";
  protected final String TEXT_176 = ".limit(";
  protected final String TEXT_177 = ");" + NL + "\t\t\t";
  protected final String TEXT_178 = NL + "\t\t\t\tview_";
  protected final String TEXT_179 = ".includeDocs(";
  protected final String TEXT_180 = ");" + NL + "\t\t\t";
  protected final String TEXT_181 = NL + "\t\t\t\tview_";
  protected final String TEXT_182 = ".descending(";
  protected final String TEXT_183 = ");" + NL + "\t\t\t";
  protected final String TEXT_184 = NL + "\t\t\t\t\t\toptions_";
  protected final String TEXT_185 = ".put(";
  protected final String TEXT_186 = ",";
  protected final String TEXT_187 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_188 = NL + "\t\t\t\torg.talend.couchdb.CouchDBUtil.initView(view_";
  protected final String TEXT_189 = ", options_";
  protected final String TEXT_190 = ");" + NL + "\t      \t";
  protected final String TEXT_191 = NL + "\t\t\t\tview_";
  protected final String TEXT_192 = ".reduce(";
  protected final String TEXT_193 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_194 = NL + "\t\t\t\t\tview_";
  protected final String TEXT_195 = ".groupLevel(";
  protected final String TEXT_196 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_197 = NL + "\t\t\torg.lightcouch.ViewResult<com.google.gson.JsonElement,com.google.gson.JsonElement,com.google.gson.JsonObject> results_";
  protected final String TEXT_198 = " = null;" + NL + "\t\t\ttry{" + NL + "\t\t\t\tresults_";
  protected final String TEXT_199 = " = view_";
  protected final String TEXT_200 = ".queryView(com.google.gson.JsonElement.class, com.google.gson.JsonElement.class, com.google.gson.JsonObject.class);" + NL + "\t\t\t}catch(org.lightcouch.NoDocumentException e_";
  protected final String TEXT_201 = "){" + NL + "\t\t\t\tSystem.err.println(e_";
  protected final String TEXT_202 = ".getMessage());" + NL + "\t\t\t}" + NL + "\t\t\tif(results_";
  protected final String TEXT_203 = " != null){" + NL + "\t\t\t\tfor(org.lightcouch.ViewResult<com.google.gson.JsonElement, com.google.gson.JsonElement, com.google.gson.JsonObject>.Rows valueRow_";
  protected final String TEXT_204 = ":results_";
  protected final String TEXT_205 = ".getRows()){" + NL + "\t\t\t\t\tnb_line_";
  protected final String TEXT_206 = " ++;" + NL + "\t\t\t\t  \t";
  protected final String TEXT_207 = NL + "\t\t\t\t \t\t\t";
  protected final String TEXT_208 = ".id=valueRow_";
  protected final String TEXT_209 = ".getId();" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_210 = NL + "\t\t\t\t\t\t\tif(valueRow_";
  protected final String TEXT_211 = ".getKey()!=null){" + NL + "\t\t\t\t\t\t\t\tcom.google.gson.JsonElement key_";
  protected final String TEXT_212 = " = (com.google.gson.JsonElement)valueRow_";
  protected final String TEXT_213 = ".getKey();" + NL + "\t\t\t\t\t\t\t\tif(key_";
  protected final String TEXT_214 = ".isJsonPrimitive() &&(key_";
  protected final String TEXT_215 = ".getAsJsonPrimitive().isNumber()||(!key_";
  protected final String TEXT_216 = ".isJsonArray()&&key_";
  protected final String TEXT_217 = NL + "\t\t\t\t\t\t\t\t\t\t\t.getAsJsonPrimitive().isString()))){" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_218 = ".key = key_";
  protected final String TEXT_219 = ".getAsString();" + NL + "\t\t\t\t\t\t\t\t}else if(!key_";
  protected final String TEXT_220 = ".isJsonNull()){" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_221 = ".key = client_";
  protected final String TEXT_222 = ".getGson().toJson(key_";
  protected final String TEXT_223 = ");" + NL + "\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_224 = NL + "\t\t\t\t\t\t\tif(valueRow_";
  protected final String TEXT_225 = ".getValue()!=null){" + NL + "\t\t\t\t\t\t\t\tcom.google.gson.JsonElement value_";
  protected final String TEXT_226 = " = (com.google.gson.JsonElement)valueRow_";
  protected final String TEXT_227 = ".getValue();" + NL + "\t\t\t\t\t\t\t\tif(value_";
  protected final String TEXT_228 = ".isJsonPrimitive() &&(value_";
  protected final String TEXT_229 = ".getAsJsonPrimitive().isNumber()||(!value_";
  protected final String TEXT_230 = ".isJsonArray()&&value_";
  protected final String TEXT_231 = NL + "\t\t\t\t\t\t\t\t\t\t\t.getAsJsonPrimitive().isString()))){" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_232 = ".value = value_";
  protected final String TEXT_233 = ".getAsString();" + NL + "\t\t\t\t\t\t\t\t}else if(!value_";
  protected final String TEXT_234 = ".isJsonNull()){" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_235 = ".value = client_";
  protected final String TEXT_236 = ".getGson().toJson(value_";
  protected final String TEXT_237 = ");" + NL + "\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_238 = NL + "\t\t\t\t \t\t\t";
  protected final String TEXT_239 = ".jsonDoc = valueRow_";
  protected final String TEXT_240 = ".getDoc()";
  protected final String TEXT_241 = ";" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_242 = NL + "\t";
  protected final String TEXT_243 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	//this util class use by set log4j debug paramters
	class DefaultLog4jFileUtil {
	
		INode node = null;
	    String cid = null;
 		boolean isLog4jEnabled = false;
 		String label = null;
 		
 		public DefaultLog4jFileUtil(){
 		}
 		public DefaultLog4jFileUtil(INode node) {
 			this.node = node;
 			this.cid = node.getUniqueName();
 			this.label = cid;
			this.isLog4jEnabled = ("true").equals(org.talend.core.model.process.ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
 		}
 		
 		public void setCid(String cid) {
 			this.cid = cid;
 		}
 		
		//for all tFileinput* components 
		public void startRetriveDataInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_3);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_4);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_7);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_9);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_10);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_13);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_15);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_18);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_20);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_21);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_24);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_26);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_27);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_29);
    
			}
		}
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    stringBuffer.append(TEXT_30);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;

    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    String dbhost = ElementParameterParser.getValue(node, "__HOST__");
    String dbname = ElementParameterParser.getValue(node, "__DATABASE__");
    String dbuser = ElementParameterParser.getValue(node, "__USERNAME__");
    String dbpass = ElementParameterParser.getValue(node, "__PASSWORD__");
    boolean authentication="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REQUIRED_AUTHENTICATION__"));
    if (!authentication){
    	dbuser = null;
    	dbpass = null;
    }
    
    String passwordFieldName = "__PASSWORD__";
    
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    if(!authentication) {
 	   //do nothing
    } else if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_35);
    } else {
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_38);
    }    
    
    
    boolean useTriggerReplicate="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__USE_TRIGGER_REPLICATE__"));
    String dbport  	= ElementParameterParser.getValue(node, "__PORT__");
    List<Map<String,String>> replicaTargetDB= (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__REPLICATE_TARGET_DB__");
    boolean useExistingConn = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
    String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
    
   
    boolean isQueryByView = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__QUERY_BY_VIEW__"));
    boolean include_docs = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__INCLUDE_DOCS__"));
    
    Object startKey = ElementParameterParser.getValue(node,"__STARTKEY__");
    Object endKey = ElementParameterParser.getValue(node,"__ENDKEY__");
    String startKeyDocId = ElementParameterParser.getValue(node,"__STARTKEY_DOCID__");
    String endKeyDocId = ElementParameterParser.getValue(node,"__ENDKEY_DOCID__");
    boolean descending = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__DESCENDING__"));
    boolean isAddOptions = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__ADD_OPTION__"));
    
    String docName = ElementParameterParser.getValue(node, "__DOC__");
    String viewName = ElementParameterParser.getValue(node, "__VIEW__");
    String doc_action = ElementParameterParser.getValue(node,"__DOC_ACTION__");
    String view_action = ElementParameterParser.getValue(node,"__VIEW_ACTION__");
    String map = ElementParameterParser.getValue(node,"__MAP__");
    map = map.replaceAll("\n"," ");
    map = map.replaceAll("\r"," ");
    boolean isReduce = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__ISREDUCE__"));
    String reduce = ElementParameterParser.getValue(node,"__REDUCE__");
    reduce = reduce.replaceAll("\n"," ");
    reduce = reduce.replaceAll("\r"," ");
    boolean isGroup = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__GROUP__"));
    String group_level = ElementParameterParser.getValue(node,"__GROUP_LEVEL__");
    
    String limit = ElementParameterParser.getValue(node, "__LIMIT__");
    
    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
    
    List<IMetadataTable> metadatas = node.getMetadataList();
  	List<? extends IConnection> outputs = node.getOutgoingSortedConnections();
  	String firstConnName = "";
  	if (outputs.size() > 0){
		IConnection out = outputs.get(0);
		if(out!=null && out.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)){
			firstConnName = out.getName();
		}
    }
	
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    
	if(useExistingConn){
	
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_44);
    
		if(isLog4jEnabled){
		
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_48);
    
		}
	}else{
	
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    
		if(useTriggerReplicate){
		
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    
			int index= 0;
			for(Map<String,String> targetDB:replicaTargetDB){
				String targetDBName=targetDB.get("TARGET_DB_NAME");
				boolean isCreateTargetDB = "true".equalsIgnoreCase(targetDB.get("CREATE_TARGET"));
				boolean isContinuous = "true".equalsIgnoreCase(targetDB.get("CONTINUOUS"));
				boolean isCancel = "true".equalsIgnoreCase(targetDB.get("CANCEL_REPLICATE"));
				
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(targetDBName);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(isCreateTargetDB);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(isContinuous);
    stringBuffer.append(TEXT_65);
    
				if(isContinuous && isCancel){
				
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(targetDBName);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(isCreateTargetDB);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(isContinuous);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(isCancel);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_78);
    
					index++;
				}
			}
		}
	}
	
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_80);
    
	log4jFileUtil.startRetriveDataInfo();
  	if(metadatas != null && metadatas.size() > 0){
		IMetadataTable metadata = metadatas.get(0);
      	if(metadata != null){
      	
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_83);
    
  			if(isQueryByView){
			
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(docName);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(viewName);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(docName);
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
    stringBuffer.append(map);
    stringBuffer.append(TEXT_98);
    
				if(isReduce){
				
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(reduce);
    stringBuffer.append(TEXT_101);
    
				}
				//A1:create design document or view
				if(!"NONE".equals(doc_action) && !"CREATE_IF_NOT_EXISTS".equals(doc_action)){
					if("DROP_CREATE".equals(doc_action)){
					
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    
					}else if("DROP_IF_EXISTS_AND_CREATE".equals(doc_action)){
					
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_108);
    
					}
					
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    
					if("CREATE".equals(view_action)){
					
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(viewName);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    
					}else{
					
    stringBuffer.append(TEXT_120);
    
					}
					
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_123);
    
				}else{
					if("CREATE".equals(view_action)){
					
    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(viewName);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(viewName);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    
							if("NONE".equals(doc_action)){
							
    stringBuffer.append(TEXT_137);
    stringBuffer.append(docName);
    stringBuffer.append(TEXT_138);
    
							}else{
							
    stringBuffer.append(TEXT_139);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(viewName);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    
							}
							
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_151);
    
					}else{
					
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
    
					}
				}//A1:end
			}
			
    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    
			if(startKey!=null && !"".equals(startKey)){
			
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_164);
    stringBuffer.append(startKey);
    stringBuffer.append(TEXT_165);
    
			}	
			if(endKey!=null && !"".equals(endKey)){
			
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(endKey);
    stringBuffer.append(TEXT_168);
    
			}	
			if(startKeyDocId!=null && !"".equals(startKeyDocId)){
			
    stringBuffer.append(TEXT_169);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_170);
    stringBuffer.append(startKeyDocId);
    stringBuffer.append(TEXT_171);
    
			}	
			if(endKeyDocId!=null && !"".equals(endKeyDocId)){
			
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(endKeyDocId);
    stringBuffer.append(TEXT_174);
    
			}	
			if (limit != null && limit.length() > 0){
			
    stringBuffer.append(TEXT_175);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(limit);
    stringBuffer.append(TEXT_177);
    
			}
			if(!isReduce){
			
    stringBuffer.append(TEXT_178);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(include_docs);
    stringBuffer.append(TEXT_180);
    
			}
			
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    stringBuffer.append(descending);
    stringBuffer.append(TEXT_183);
    
			if(isAddOptions){
				List<Map<String, String>> options = (List<Map<String,String>>)ElementParameterParser.getObjectValueXML(node, "__OPTIONS__");
				for(Map<String,String>option:options){
					if(option.get("PARAMETER")!=null && option.get("VALUE")!=null){
					
    stringBuffer.append(TEXT_184);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(option.get("PARAMETER"));
    stringBuffer.append(TEXT_186);
    stringBuffer.append(option.get("VALUE"));
    stringBuffer.append(TEXT_187);
    
					}
				}
				
    stringBuffer.append(TEXT_188);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    
			}
			if(isQueryByView){
			
    stringBuffer.append(TEXT_191);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_192);
    stringBuffer.append(isReduce);
    stringBuffer.append(TEXT_193);
    
      			if(isReduce && isGroup){
				
    stringBuffer.append(TEXT_194);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_195);
    stringBuffer.append(group_level);
    stringBuffer.append(TEXT_196);
    
	      		}
			}
			
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
    
				  	if(firstConnName!=null && firstConnName.length()>0){
						if(metadata.getColumn("id")!=null){
						
    stringBuffer.append(TEXT_207);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    
						}
						if(metadata.getColumn("key")!=null){
						
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
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_219);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_220);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_222);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_223);
    
						}
						if(metadata.getColumn("value")!=null){
						
    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_226);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_227);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_228);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_230);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_231);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_233);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_234);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_235);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    
						}
						if(metadata.getColumn("jsonDoc")!=null){
						
    stringBuffer.append(TEXT_238);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_239);
    stringBuffer.append(cid);
    if(include_docs && !isReduce){
    stringBuffer.append(TEXT_240);
    }
    stringBuffer.append(TEXT_241);
    
						}
					}
					log4jFileUtil.debugRetriveData(node);
		}
	}
	
    stringBuffer.append(TEXT_242);
    stringBuffer.append(TEXT_243);
    return stringBuffer.toString();
  }
}
