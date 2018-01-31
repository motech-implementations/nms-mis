package org.talend.designer.codegen.translators.bigdata.hcatalog;

import java.util.List;
import java.util.Map;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.types.Java2STLangTypesHelper;

public class THCatalogOperationBeginJava
{
  protected static String nl;
  public static synchronized THCatalogOperationBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THCatalogOperationBeginJava result = new THCatalogOperationBeginJava();
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
  protected final String TEXT_31 = NL + "\t\t\tString err_";
  protected final String TEXT_32 = " = \"\";" + NL + "\t\t\torg.json.simple.JSONObject jsonRow_";
  protected final String TEXT_33 = " = new org.json.simple.JSONObject();" + NL + "\t\t\tjava.io.InputStream is_";
  protected final String TEXT_34 = ";" + NL + "\t\t\tObject obj_";
  protected final String TEXT_35 = ";" + NL + "\t\t\torg.json.simple.JSONObject object_";
  protected final String TEXT_36 = ";" + NL + "\t\t\tObject error_";
  protected final String TEXT_37 = ";";
  protected final String TEXT_38 = NL + "\t\t\tis_";
  protected final String TEXT_39 = " = (java.io.InputStream) response_";
  protected final String TEXT_40 = ".getEntity();" + NL + "\t\t\tString result_";
  protected final String TEXT_41 = " = org.apache.cxf.helpers.IOUtils.toString(is_";
  protected final String TEXT_42 = ");" + NL + "\t\t\tobj_";
  protected final String TEXT_43 = "=org.json.simple.JSONValue.parse(result_";
  protected final String TEXT_44 = ");" + NL + "\t\t\tobject_";
  protected final String TEXT_45 = "=(org.json.simple.JSONObject)obj_";
  protected final String TEXT_46 = ";";
  protected final String TEXT_47 = NL + "\t\t\terror_";
  protected final String TEXT_48 = " = object_";
  protected final String TEXT_49 = ".get(\"error\");" + NL + "\t\t\tif(error_";
  protected final String TEXT_50 = "!=null) {" + NL + "\t\t\t\terr_";
  protected final String TEXT_51 = " = error_";
  protected final String TEXT_52 = ".toString();" + NL + "\t\t\t}";
  protected final String TEXT_53 = NL + "\t\t\t\tif(((org.json.simple.JSONObject)object_";
  protected final String TEXT_54 = ".get(\"exec\"))!=null) {" + NL + "\t\t\t\t\tString time_";
  protected final String TEXT_55 = " = TalendDate.formatDate(\"yyyyMMddHHmmss\", TalendDate.getCurrentDate());" + NL + "\t\t\t\t\tObject stdout_";
  protected final String TEXT_56 = " = ((org.json.simple.JSONObject)object_";
  protected final String TEXT_57 = ".get(\"exec\")).get(\"stdout\");" + NL + "\t\t\t\t\tObject stderr_";
  protected final String TEXT_58 = " = ((org.json.simple.JSONObject)object_";
  protected final String TEXT_59 = ".get(\"exec\")).get(\"stderr\");" + NL + "\t\t\t\t\tString stdout_filename_";
  protected final String TEXT_60 = " = \"/\" + projectName + \"_\" + jobName + \"_\" + jobVersion + \"_\" + time_";
  protected final String TEXT_61 = " + \"_\" + \"";
  protected final String TEXT_62 = "\" + \"_stdout.log\";" + NL + "\t\t\t\t\tString stderr_filename_";
  protected final String TEXT_63 = " = \"/\" + projectName + \"_\" + jobName + \"_\" + jobVersion + \"_\" + time_";
  protected final String TEXT_64 = " + \"_\" + \"";
  protected final String TEXT_65 = "\" + \"_stderr.log\";" + NL + "\t\t\t\t\tjava.io.FileWriter fw_out_";
  protected final String TEXT_66 = " = new java.io.FileWriter(new java.io.File(";
  protected final String TEXT_67 = "+stdout_filename_";
  protected final String TEXT_68 = "));" + NL + "\t\t\t\t\tjava.io.FileWriter fw_err_";
  protected final String TEXT_69 = " = new java.io.FileWriter(new java.io.File(";
  protected final String TEXT_70 = "+stderr_filename_";
  protected final String TEXT_71 = "));" + NL + "\t\t\t\t\tfw_out_";
  protected final String TEXT_72 = ".write(stdout_";
  protected final String TEXT_73 = "!=null?stdout_";
  protected final String TEXT_74 = ".toString():\"\");" + NL + "\t\t\t\t\tfw_err_";
  protected final String TEXT_75 = ".write(stderr_";
  protected final String TEXT_76 = "!=null?stderr_";
  protected final String TEXT_77 = ".toString():\"\");" + NL + "\t\t\t\t\tfw_out_";
  protected final String TEXT_78 = ".close();" + NL + "\t\t\t\t\tfw_err_";
  protected final String TEXT_79 = ".close();" + NL + "\t\t\t\t}";
  protected final String TEXT_80 = NL + "\t\t\tif(response_";
  protected final String TEXT_81 = ".getStatus()!=200) {" + NL + "\t\t\t\tif(response_";
  protected final String TEXT_82 = ".getStatus()==400) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 400 (Bad request): The request was invalid.\\n--->\" + err_";
  protected final String TEXT_83 = ");" + NL + "\t\t\t\t} else if(response_";
  protected final String TEXT_84 = ".getStatus()==401) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 401 (Unauthorized): Credentials were missing or incorrect.\\n--->\" + err_";
  protected final String TEXT_85 = ");" + NL + "\t\t\t\t} else if(response_";
  protected final String TEXT_86 = ".getStatus()==404) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 404 (Not found): The URI requested is invalid or the resource requested does not exist.\\n--->\" + err_";
  protected final String TEXT_87 = ");" + NL + "\t\t\t\t} else if(response_";
  protected final String TEXT_88 = ".getStatus()==500) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 500 (Internal Server Error): We received an unexpected result.\\n--->\" + err_";
  protected final String TEXT_89 = ");" + NL + "\t\t\t\t} else if(response_";
  protected final String TEXT_90 = ".getStatus()==503) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 503 (Busy, please retry): The server is busy.\\n--->\" + err_";
  protected final String TEXT_91 = ");" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tthrow new Exception(object_";
  protected final String TEXT_92 = ".get(\"errorCode\") + \":\" + object_";
  protected final String TEXT_93 = ".get(\"error\") + object_";
  protected final String TEXT_94 = ".get(\"errorDetail\"));" + NL + "\t\t\t\t}" + NL + "\t\t\t}";
  protected final String TEXT_95 = NL + "\ttry {";
  protected final String TEXT_96 = NL + "\t\t\tclass KerberosPolicyConfig_";
  protected final String TEXT_97 = " extends javax.security.auth.login.Configuration {" + NL + "\t\t\t\tprivate java.util.Map<String, String> properties;" + NL + "" + NL + "\t\t\t\tpublic KerberosPolicyConfig_";
  protected final String TEXT_98 = "() {" + NL + "\t\t\t\t\tproperties = java.util.Collections" + NL + "\t\t\t\t\t\t\t.<String, String> emptyMap();" + NL + "\t\t\t\t}" + NL + "" + NL + "\t\t\t\tpublic KerberosPolicyConfig_";
  protected final String TEXT_99 = "(" + NL + "\t\t\t\t\t\tjava.util.Map<String, String> properties) {" + NL + "\t\t\t\t\tthis.properties = properties;" + NL + "\t\t\t\t}" + NL + "" + NL + "\t\t\t\tpublic javax.security.auth.login.AppConfigurationEntry[] getAppConfigurationEntry(" + NL + "\t\t\t\t\t\tString name) {" + NL + "\t\t\t\t\treturn new javax.security.auth.login.AppConfigurationEntry[] { new javax.security.auth.login.AppConfigurationEntry(" + NL + "\t\t\t\t\t\t\tcom.sun.security.auth.module.Krb5LoginModule.class" + NL + "\t\t\t\t\t\t\t\t\t.getName()," + NL + "\t\t\t\t\t\t\tjavax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag.REQUIRED," + NL + "\t\t\t\t\t\t\tproperties) };" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\torg.apache.cxf.jaxrs.security.KerberosAuthOutInterceptor kbInterceptor_";
  protected final String TEXT_100 = " = new org.apache.cxf.jaxrs.security.KerberosAuthOutInterceptor();" + NL + "" + NL + "\t\t\torg.apache.cxf.configuration.security.AuthorizationPolicy policy_";
  protected final String TEXT_101 = " = new org.apache.cxf.configuration.security.AuthorizationPolicy();" + NL + "\t\t\tpolicy_";
  protected final String TEXT_102 = ".setAuthorizationType(org.apache.cxf.transport.http.auth.HttpAuthHeader.AUTH_TYPE_NEGOTIATE);" + NL + "" + NL + "\t\t\tkbInterceptor_";
  protected final String TEXT_103 = ".setPolicy(policy_";
  protected final String TEXT_104 = ");" + NL + "\t\t\tjava.util.Map<String, String> properties_";
  protected final String TEXT_105 = " = new java.util.HashMap<String, String>();" + NL + "\t\t\tkbInterceptor_";
  protected final String TEXT_106 = ".setServicePrincipalName(";
  protected final String TEXT_107 = ");" + NL + "\t\t\tkbInterceptor_";
  protected final String TEXT_108 = ".setRealm(";
  protected final String TEXT_109 = ");" + NL + "\t\t\tproperties_";
  protected final String TEXT_110 = ".put(\"useTicketCache\", \"true\");" + NL + "\t\t\tproperties_";
  protected final String TEXT_111 = ".put(\"refreshKrb5Config\", \"true\");" + NL + "\t\t\tproperties_";
  protected final String TEXT_112 = ".put(\"renewTGT\", \"true\");" + NL + "\t\t\t";
  protected final String TEXT_113 = NL + "\t\t\t\t\tproperties_";
  protected final String TEXT_114 = ".put(\"useKeyTab\", \"true\");" + NL + "\t\t\t\t\tproperties_";
  protected final String TEXT_115 = ".put(\"keyTab\", ";
  protected final String TEXT_116 = ");" + NL + "\t\t\t\t\tproperties_";
  protected final String TEXT_117 = ".put(\"principal\", ";
  protected final String TEXT_118 = ");" + NL + "\t\t\t";
  protected final String TEXT_119 = NL + "\t\t\tkbInterceptor_";
  protected final String TEXT_120 = ".setLoginConfig(new KerberosPolicyConfig_";
  protected final String TEXT_121 = "(properties_";
  protected final String TEXT_122 = "));";
  protected final String TEXT_123 = NL;
  protected final String TEXT_124 = NL + "\t\t\t\torg.apache.cxf.jaxrs.client.WebClient client_drop_";
  protected final String TEXT_125 = " = org.apache.cxf.jaxrs.client.WebClient.create(\"http://\" + ";
  protected final String TEXT_126 = " + \":\" + ";
  protected final String TEXT_127 = " + \"?user.name=\" + ";
  protected final String TEXT_128 = ");";
  protected final String TEXT_129 = NL + "\t\t\t\t\torg.apache.cxf.jaxrs.client.WebClient.getConfig(client_drop_";
  protected final String TEXT_130 = ").getOutInterceptors().add(kbInterceptor_";
  protected final String TEXT_131 = ");";
  protected final String TEXT_132 = NL + "                        final String decryptedPassword_drop_";
  protected final String TEXT_133 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_134 = ");";
  protected final String TEXT_135 = NL + "                        final String decryptedPassword_drop_";
  protected final String TEXT_136 = " = ";
  protected final String TEXT_137 = "; ";
  protected final String TEXT_138 = "                  " + NL + "                    String authorizationHeader_drop_";
  protected final String TEXT_139 = " = \"Basic \"" + NL + "                        + org.apache.cxf.common.util.Base64Utility.encode((";
  protected final String TEXT_140 = " + \":\" + decryptedPassword_drop_";
  protected final String TEXT_141 = ").getBytes());" + NL + "                    client_drop_";
  protected final String TEXT_142 = ".header(\"Authorization\", authorizationHeader_drop_";
  protected final String TEXT_143 = ");";
  protected final String TEXT_144 = NL + "\t\t\t\t" + NL + "\t\t\t\tclient_drop_";
  protected final String TEXT_145 = ".path(\"templeton/v1/ddl/database/\" + ";
  protected final String TEXT_146 = ");" + NL + "\t\t\t\tclient_drop_";
  protected final String TEXT_147 = ".accept(\"application/json\");" + NL + "\t\t" + NL + "\t\t\t\tjavax.ws.rs.core.Response response_drop_";
  protected final String TEXT_148 = " = client_drop_";
  protected final String TEXT_149 = ".delete();";
  protected final String TEXT_150 = NL + "\t\t\t\t\torg.json.simple.JSONObject columnPropertiesJSON_";
  protected final String TEXT_151 = " = new org.json.simple.JSONObject();";
  protected final String TEXT_152 = NL + "\t\t\t\t\t\tcolumnPropertiesJSON_";
  protected final String TEXT_153 = ".put(";
  protected final String TEXT_154 = ", ";
  protected final String TEXT_155 = ");";
  protected final String TEXT_156 = NL + "\t\t\t\t\tjsonRow_";
  protected final String TEXT_157 = ".put(\"properties\", columnPropertiesJSON_";
  protected final String TEXT_158 = ");";
  protected final String TEXT_159 = NL + "\t\t\t\tjsonRow_";
  protected final String TEXT_160 = ".put(\"comment\", ";
  protected final String TEXT_161 = ");" + NL + "\t\t\t\tjsonRow_";
  protected final String TEXT_162 = ".put(\"location\", ";
  protected final String TEXT_163 = ");" + NL + "\t\t\t\torg.apache.cxf.jaxrs.client.WebClient client_create_";
  protected final String TEXT_164 = " = org.apache.cxf.jaxrs.client.WebClient.create(\"http://\" + ";
  protected final String TEXT_165 = " + \":\" + ";
  protected final String TEXT_166 = " + \"?user.name=\" + ";
  protected final String TEXT_167 = ");";
  protected final String TEXT_168 = NL + "\t\t\t\t\torg.apache.cxf.jaxrs.client.WebClient.getConfig(client_create_";
  protected final String TEXT_169 = ").getOutInterceptors().add(kbInterceptor_";
  protected final String TEXT_170 = ");";
  protected final String TEXT_171 = NL + "                        final String decryptedPassword_create_";
  protected final String TEXT_172 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_173 = ");";
  protected final String TEXT_174 = NL + "                        final String decryptedPassword_create_";
  protected final String TEXT_175 = " = ";
  protected final String TEXT_176 = "; ";
  protected final String TEXT_177 = "                  " + NL + "                    String authorizationHeader_create_";
  protected final String TEXT_178 = " = \"Basic \"" + NL + "                        + org.apache.cxf.common.util.Base64Utility.encode((";
  protected final String TEXT_179 = " + \":\" + decryptedPassword_create_";
  protected final String TEXT_180 = ").getBytes());" + NL + "                    client_create_";
  protected final String TEXT_181 = ".header(\"Authorization\", authorizationHeader_create_";
  protected final String TEXT_182 = ");";
  protected final String TEXT_183 = NL + "\t\t\t\t" + NL + "\t\t\t\tclient_create_";
  protected final String TEXT_184 = ".path(\"templeton/v1/ddl/database/\" + ";
  protected final String TEXT_185 = ");" + NL + "\t\t\t\tclient_create_";
  protected final String TEXT_186 = ".type(\"application/json\").accept(\"application/json\");" + NL + "\t\t\t\t" + NL + "\t\t\t\tjavax.ws.rs.core.Response response_create_";
  protected final String TEXT_187 = " = client_create_";
  protected final String TEXT_188 = ".put(jsonRow_";
  protected final String TEXT_189 = ".toJSONString());";
  protected final String TEXT_190 = NL + "\t\t\t\torg.apache.cxf.jaxrs.client.WebClient client_drop_";
  protected final String TEXT_191 = " = org.apache.cxf.jaxrs.client.WebClient.create(\"http://\" + ";
  protected final String TEXT_192 = " + \":\" + ";
  protected final String TEXT_193 = " + \"?user.name=\" + ";
  protected final String TEXT_194 = ");";
  protected final String TEXT_195 = NL + "\t\t\t\t\torg.apache.cxf.jaxrs.client.WebClient.getConfig(client_drop_";
  protected final String TEXT_196 = ").getOutInterceptors().add(kbInterceptor_";
  protected final String TEXT_197 = ");";
  protected final String TEXT_198 = NL + "                        final String decryptedPassword_drop_";
  protected final String TEXT_199 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_200 = ");";
  protected final String TEXT_201 = NL + "                        final String decryptedPassword_drop_";
  protected final String TEXT_202 = " = ";
  protected final String TEXT_203 = "; ";
  protected final String TEXT_204 = "                  " + NL + "                    String authorizationHeader_drop_";
  protected final String TEXT_205 = " = \"Basic \"" + NL + "                        + org.apache.cxf.common.util.Base64Utility.encode((";
  protected final String TEXT_206 = " + \":\" + decryptedPassword_drop_";
  protected final String TEXT_207 = ").getBytes());" + NL + "                    client_drop_";
  protected final String TEXT_208 = ".header(\"Authorization\", authorizationHeader_drop_";
  protected final String TEXT_209 = ");";
  protected final String TEXT_210 = NL + "\t\t\t\t" + NL + "\t\t\t\tclient_drop_";
  protected final String TEXT_211 = ".path(\"templeton/v1/ddl/database/\" + ";
  protected final String TEXT_212 = " + \"/table/\" + ";
  protected final String TEXT_213 = ");" + NL + "\t\t\t\tclient_drop_";
  protected final String TEXT_214 = ".accept(\"application/json\");" + NL + "\t\t\t\t" + NL + "\t\t\t\tjavax.ws.rs.core.Response response_drop_";
  protected final String TEXT_215 = " = client_drop_";
  protected final String TEXT_216 = ".delete();";
  protected final String TEXT_217 = NL + "\t\t\t\t\torg.json.simple.JSONObject columnPropertiesJSON_";
  protected final String TEXT_218 = " = new org.json.simple.JSONObject();";
  protected final String TEXT_219 = NL + "\t\t\t\t\t\tcolumnPropertiesJSON_";
  protected final String TEXT_220 = ".put(";
  protected final String TEXT_221 = ", ";
  protected final String TEXT_222 = ");";
  protected final String TEXT_223 = NL + "\t\t\t\t\tjsonRow_";
  protected final String TEXT_224 = ".put(\"properties\", columnPropertiesJSON_";
  protected final String TEXT_225 = ");";
  protected final String TEXT_226 = NL + "\t\t\t\t\tjsonRow_";
  protected final String TEXT_227 = ".put(\"ifNotExists\", \"true\");";
  protected final String TEXT_228 = NL + "\t\t\t\t\tjsonRow_";
  protected final String TEXT_229 = ".put(\"ifNotExists\", \"false\");";
  protected final String TEXT_230 = NL + "\t\t\t\t\tjsonRow_";
  protected final String TEXT_231 = ".put(\"external\", \"true\");";
  protected final String TEXT_232 = NL + "\t\t\t\t\tjsonRow_";
  protected final String TEXT_233 = ".put(\"location\", ";
  protected final String TEXT_234 = ");";
  protected final String TEXT_235 = NL + "\t\t\t\tjsonRow_";
  protected final String TEXT_236 = ".put(\"comment\", ";
  protected final String TEXT_237 = ");" + NL + "\t\t\t\torg.json.simple.JSONObject storage_";
  protected final String TEXT_238 = " = new org.json.simple.JSONObject();" + NL + "\t\t\t\tstorage_";
  protected final String TEXT_239 = ".put(\"storedAs\", \"";
  protected final String TEXT_240 = "\");";
  protected final String TEXT_241 = NL + "\t\t\t\t\torg.json.simple.JSONObject rowFormat_";
  protected final String TEXT_242 = " = new org.json.simple.JSONObject();" + NL + "\t\t\t\t\t";
  protected final String TEXT_243 = "rowFormat_";
  protected final String TEXT_244 = ".put(\"fieldsTerminatedBy\", ";
  protected final String TEXT_245 = ");";
  protected final String TEXT_246 = NL + "\t\t\t\t\t";
  protected final String TEXT_247 = "rowFormat_";
  protected final String TEXT_248 = ".put(\"collectionItemsTerminatedBy\", ";
  protected final String TEXT_249 = ");";
  protected final String TEXT_250 = NL + "\t\t\t\t\t";
  protected final String TEXT_251 = "rowFormat_";
  protected final String TEXT_252 = ".put(\"mapKeysTerminatedBy\", ";
  protected final String TEXT_253 = ");";
  protected final String TEXT_254 = NL + "\t\t\t\t\t";
  protected final String TEXT_255 = "rowFormat_";
  protected final String TEXT_256 = ".put(\"linesTerminatedBy\", ";
  protected final String TEXT_257 = ");";
  protected final String TEXT_258 = NL + "\t\t\t\t\torg.json.simple.JSONObject serde_";
  protected final String TEXT_259 = " = new org.json.simple.JSONObject();" + NL + "\t\t\t\t\tserde_";
  protected final String TEXT_260 = ".put(\"name\", \"org.apache.hadoop.hive.serde2.columnar.ColumnarSerDe\");" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\trowFormat_";
  protected final String TEXT_261 = ".put(\"serde\", serde_";
  protected final String TEXT_262 = ");" + NL + "\t\t\t\t\tstorage_";
  protected final String TEXT_263 = ".put(\"rowFormat\", rowFormat_";
  protected final String TEXT_264 = ");";
  protected final String TEXT_265 = NL + "\t\t\t\tjsonRow_";
  protected final String TEXT_266 = ".put(\"format\", storage_";
  protected final String TEXT_267 = ");";
  protected final String TEXT_268 = NL + "\t\t\t\t\t\t\torg.json.simple.JSONArray columnListForJson_";
  protected final String TEXT_269 = " = new org.json.simple.JSONArray();" + NL + "\t\t\t\t\t\t\torg.json.simple.JSONObject columnObjJson_";
  protected final String TEXT_270 = ";";
  protected final String TEXT_271 = NL + "\t\t\t\t\t\t\t\tcolumnObjJson_";
  protected final String TEXT_272 = " = new org.json.simple.JSONObject();" + NL + "\t\t\t\t\t\t\t\tcolumnObjJson_";
  protected final String TEXT_273 = ".put(\"name\", \"";
  protected final String TEXT_274 = "\");" + NL + "\t\t\t\t\t\t\t\tcolumnObjJson_";
  protected final String TEXT_275 = ".put(\"type\", \"";
  protected final String TEXT_276 = "\");" + NL + "\t\t\t\t\t\t\t\tcolumnObjJson_";
  protected final String TEXT_277 = ".put(\"comment\", \"";
  protected final String TEXT_278 = "\");" + NL + "\t\t\t\t\t\t\t\tcolumnListForJson_";
  protected final String TEXT_279 = ".add(columnObjJson_";
  protected final String TEXT_280 = ");";
  protected final String TEXT_281 = "\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\tjsonRow_";
  protected final String TEXT_282 = ".put(\"columns\", columnListForJson_";
  protected final String TEXT_283 = ");";
  protected final String TEXT_284 = NL + "\t\t\t\t\t\t\t if(true){" + NL + "\t\t\t\t\t\t\t \tthrow new Exception(\"Schema must be defined for ";
  protected final String TEXT_285 = "\");" + NL + "\t\t\t\t\t\t\t }";
  protected final String TEXT_286 = NL + "\t\t\t\t\t\t\tSystem.err.println(\"Schema must be defined for ";
  protected final String TEXT_287 = "\");";
  protected final String TEXT_288 = NL + "\t\t\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_289 = " - Schema must be defined for ";
  protected final String TEXT_290 = "\");";
  protected final String TEXT_291 = NL + "\t\t\t\t\t\t\t\torg.json.simple.JSONArray partitionListForJson_";
  protected final String TEXT_292 = " = new org.json.simple.JSONArray();" + NL + "\t\t\t\t\t\t\t\torg.json.simple.JSONObject partitionObjJson_";
  protected final String TEXT_293 = ";";
  protected final String TEXT_294 = NL + "\t\t\t\t\t\t\t\t\tpartitionObjJson_";
  protected final String TEXT_295 = " = new org.json.simple.JSONObject();" + NL + "\t\t\t\t\t\t\t\t\tpartitionObjJson_";
  protected final String TEXT_296 = ".put(\"name\", \"";
  protected final String TEXT_297 = "\");" + NL + "\t\t\t\t\t\t\t\t\tpartitionObjJson_";
  protected final String TEXT_298 = ".put(\"type\", \"";
  protected final String TEXT_299 = "\");" + NL + "\t\t\t\t\t\t\t\t\tpartitionObjJson_";
  protected final String TEXT_300 = ".put(\"comment\", \"";
  protected final String TEXT_301 = "\");" + NL + "\t\t\t\t\t\t\t\t\tpartitionListForJson_";
  protected final String TEXT_302 = ".add(partitionObjJson_";
  protected final String TEXT_303 = ");";
  protected final String TEXT_304 = "\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\tjsonRow_";
  protected final String TEXT_305 = ".put(\"partitionedBy\", partitionListForJson_";
  protected final String TEXT_306 = ");";
  protected final String TEXT_307 = NL + "\t\t\t\t" + NL + "\t\t\t\torg.apache.cxf.jaxrs.client.WebClient client_create_";
  protected final String TEXT_308 = " = org.apache.cxf.jaxrs.client.WebClient.create(\"http://\" + ";
  protected final String TEXT_309 = " + \":\" + ";
  protected final String TEXT_310 = " + \"?user.name=\" + ";
  protected final String TEXT_311 = ");";
  protected final String TEXT_312 = NL + "\t\t\t\t\torg.apache.cxf.jaxrs.client.WebClient.getConfig(client_create_";
  protected final String TEXT_313 = ").getOutInterceptors().add(kbInterceptor_";
  protected final String TEXT_314 = ");";
  protected final String TEXT_315 = NL + "                        final String decryptedPassword_create_";
  protected final String TEXT_316 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_317 = ");";
  protected final String TEXT_318 = NL + "                        final String decryptedPassword_create_";
  protected final String TEXT_319 = " = ";
  protected final String TEXT_320 = "; ";
  protected final String TEXT_321 = "                  " + NL + "                    String authorizationHeader_create_";
  protected final String TEXT_322 = " = \"Basic \"" + NL + "                        + org.apache.cxf.common.util.Base64Utility.encode((";
  protected final String TEXT_323 = " + \":\" + decryptedPassword_create_";
  protected final String TEXT_324 = ").getBytes());" + NL + "" + NL + "                    client_create_";
  protected final String TEXT_325 = ".header(\"Authorization\", authorizationHeader_create_";
  protected final String TEXT_326 = ");";
  protected final String TEXT_327 = NL + "\t\t\t\t" + NL + "\t\t\t\tclient_create_";
  protected final String TEXT_328 = ".path(\"templeton/v1/ddl/database/\" + ";
  protected final String TEXT_329 = " + \"/table/\" + ";
  protected final String TEXT_330 = ");" + NL + "\t\t\t\tclient_create_";
  protected final String TEXT_331 = ".type(\"application/json\").accept(\"application/json\");" + NL + "\t\t\t\t" + NL + "\t\t\t\tjavax.ws.rs.core.Response response_create_";
  protected final String TEXT_332 = " = client_create_";
  protected final String TEXT_333 = ".put(jsonRow_";
  protected final String TEXT_334 = ".toJSONString());";
  protected final String TEXT_335 = NL + "\t\t\t\torg.apache.cxf.jaxrs.client.WebClient client_drop_";
  protected final String TEXT_336 = " = org.apache.cxf.jaxrs.client.WebClient.create(\"http://\" + ";
  protected final String TEXT_337 = " + \":\" + ";
  protected final String TEXT_338 = " + \"?user.name=\" + ";
  protected final String TEXT_339 = ");";
  protected final String TEXT_340 = NL + "\t\t\t\t\torg.apache.cxf.jaxrs.client.WebClient.getConfig(client_drop_";
  protected final String TEXT_341 = ").getOutInterceptors().add(kbInterceptor_";
  protected final String TEXT_342 = ");";
  protected final String TEXT_343 = NL + "                        final String decryptedPassword_drop_";
  protected final String TEXT_344 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_345 = ");";
  protected final String TEXT_346 = NL + "                        final String decryptedPassword_drop_";
  protected final String TEXT_347 = " = ";
  protected final String TEXT_348 = "; ";
  protected final String TEXT_349 = "                  " + NL + "                    String authorizationHeader_drop_";
  protected final String TEXT_350 = " = \"Basic \"" + NL + "                        + org.apache.cxf.common.util.Base64Utility.encode((";
  protected final String TEXT_351 = " + \":\" + decryptedPassword_drop_";
  protected final String TEXT_352 = ").getBytes());" + NL + "                    client_drop_";
  protected final String TEXT_353 = ".header(\"Authorization\", authorizationHeader_drop_";
  protected final String TEXT_354 = ");";
  protected final String TEXT_355 = "\t\t\t\t" + NL + "" + NL + "\t\t\t\tclient_drop_";
  protected final String TEXT_356 = ".path(\"templeton/v1/ddl/database/\" + ";
  protected final String TEXT_357 = " +\"/table/\" + ";
  protected final String TEXT_358 = " + \"/partition/\" + ";
  protected final String TEXT_359 = ");" + NL + "\t\t\t\tclient_drop_";
  protected final String TEXT_360 = ".accept(\"application/json\");" + NL + "\t\t\t\t" + NL + "\t\t\t\tjavax.ws.rs.core.Response response_drop_";
  protected final String TEXT_361 = " = client_drop_";
  protected final String TEXT_362 = ".delete();";
  protected final String TEXT_363 = NL + "\t\t\t\t\tjsonRow_";
  protected final String TEXT_364 = ".put(\"location\", ";
  protected final String TEXT_365 = ");";
  protected final String TEXT_366 = NL + "\t\t\t\torg.apache.cxf.jaxrs.client.WebClient client_create_";
  protected final String TEXT_367 = " = org.apache.cxf.jaxrs.client.WebClient.create(\"http://\" + ";
  protected final String TEXT_368 = " + \":\" + ";
  protected final String TEXT_369 = " + \"?user.name=\" + ";
  protected final String TEXT_370 = ");";
  protected final String TEXT_371 = NL + "\t\t\t\t\torg.apache.cxf.jaxrs.client.WebClient.getConfig(client_create_";
  protected final String TEXT_372 = ").getOutInterceptors().add(kbInterceptor_";
  protected final String TEXT_373 = ");";
  protected final String TEXT_374 = NL + "                        final String decryptedPassword_create_";
  protected final String TEXT_375 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_376 = ");";
  protected final String TEXT_377 = NL + "                        final String decryptedPassword_create_";
  protected final String TEXT_378 = " = ";
  protected final String TEXT_379 = "; ";
  protected final String TEXT_380 = "                  " + NL + "                    String authorizationHeader_create_";
  protected final String TEXT_381 = " = \"Basic \"" + NL + "                        + org.apache.cxf.common.util.Base64Utility.encode((";
  protected final String TEXT_382 = " + \":\" + decryptedPassword_create_";
  protected final String TEXT_383 = ").getBytes());" + NL + "                    client_create_";
  protected final String TEXT_384 = ".header(\"Authorization\", authorizationHeader_create_";
  protected final String TEXT_385 = ");";
  protected final String TEXT_386 = NL + "\t\t\t\t" + NL + "\t\t\t\tclient_create_";
  protected final String TEXT_387 = ".path(\"templeton/v1/ddl/database/\" + ";
  protected final String TEXT_388 = "+\"/table/\" + ";
  protected final String TEXT_389 = " + \"/partition/\" + ";
  protected final String TEXT_390 = ");" + NL + "\t\t\t\tclient_create_";
  protected final String TEXT_391 = ".type(\"application/json\").accept(\"application/json\");" + NL + "\t\t\t\t" + NL + "\t\t\t\tjavax.ws.rs.core.Response response_create_";
  protected final String TEXT_392 = " = client_create_";
  protected final String TEXT_393 = ".put(jsonRow_";
  protected final String TEXT_394 = ".toJSONString());";
  protected final String TEXT_395 = "\t\t" + NL + "\t} catch (Exception e_";
  protected final String TEXT_396 = ") {";
  protected final String TEXT_397 = NL + "\t\t\tthrow e_";
  protected final String TEXT_398 = ";";
  protected final String TEXT_399 = NL + "\t\t\tSystem.err.println(e_";
  protected final String TEXT_400 = ".getMessage());";
  protected final String TEXT_401 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_402 = " - \" + e_";
  protected final String TEXT_403 = ".getMessage());";
  protected final String TEXT_404 = NL + "\t}" + NL + "\t";
  protected final String TEXT_405 = NL;

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
	
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    
	log4jFileUtil.componentStartInfo(node);
	final boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

	String templeton_host = ElementParameterParser.getValue(node, "__TEMPLETON_HOST__");
	String templeton_port = ElementParameterParser.getValue(node, "__TEMPLETON_PORT__");
	
	String database = ElementParameterParser.getValue(node, "__DATABASE_NAME__");
	String table = ElementParameterParser.getValue(node, "__TABLE_NAME__");
	String partition = ElementParameterParser.getValue(node, "__PARTITION_NAME__");
	String username = ElementParameterParser.getValue(node, "__USERNAME__");
	boolean setFileLocation = "true".equals(ElementParameterParser.getValue(node, "__SET_FILE_LOCATION__"));
	String fileLocation = ElementParameterParser.getValue(node, "__FILE_LOCATION__");
	
	String operationOn = ElementParameterParser.getValue(node, "__OPERATION_ON__");
	String operation = ElementParameterParser.getValue(node, "__OPERATION__");
	String option = ElementParameterParser.getValue(node, "__OPTION__");
	
	boolean setUserGroup = "true".equals(ElementParameterParser.getValue(node, "__SET_USER_GROUP__"));
	boolean setPermissions = "true".equals(ElementParameterParser.getValue(node, "__SET_PERMISSIONS__"));
	String userGroup = ElementParameterParser.getValue(node, "__USER_GROUP__");
	String permissions = ElementParameterParser.getValue(node, "__PERMISSIONS__");
	
	String dbLocation = ElementParameterParser.getValue(node, "__DB_LOCATION__");
	String dbDescription = ElementParameterParser.getValue(node, "__DB_DESCRIPTION__");
	
	boolean createExternal = "true".equals(ElementParameterParser.getValue(node, "__CREATE_EXTERNAL__"));
	boolean setPartitions = "true".equals(ElementParameterParser.getValue(node, "__SET_PARTITIONS__"));
	boolean setHDFSLocation = "true".equals(ElementParameterParser.getValue(node, "__SET_HDFS_LOCATION__"));
	String hdfsLocation = ElementParameterParser.getValue(node, "__HDFS_LOCATION__");
	String tableComment = ElementParameterParser.getValue(node, "__TABLE_COMMENT__");
	String storageFormat = ElementParameterParser.getValue(node, "__STORAGE_FORMAT__");
	
	boolean setRowFormat = "true".equals(ElementParameterParser.getValue(node, "__SET_ROW_FORMAT__"));
	String fieldsTerminatedBy = ElementParameterParser.getValue(node, "__FIELD_TERMINATED_BY__");
	String collectionItemsTerminatedBy = ElementParameterParser.getValue(node, "__COLLECTION_ITEM_TERMINATED_BY__");
	String mapKeysTerminatedBy = ElementParameterParser.getValue(node, "__MAP_KEY_TERMINATED_BY__");
	String linesTerminatedBy = ElementParameterParser.getValue(node, "__LINES_TERMINATED_BY__");
	
	boolean setFieldsTerminatedBy = "true".equals(ElementParameterParser.getValue(node, "__SET_FIELD_TERMINATED_BY__"));
	boolean setCollectionItemsTerminatedBy = "true".equals(ElementParameterParser.getValue(node, "__SET_COLLECTION_ITEM_TERMINATED_BY__"));
	boolean setMapKeysTerminatedBy = "true".equals(ElementParameterParser.getValue(node, "__SET_MAP_KEY_TERMINATED_BY__"));
	boolean setLinesTerminatedBy = "true".equals(ElementParameterParser.getValue(node, "__SET_LINES_TERMINATED_BY__"));
	
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	boolean activateLog = "true".equals(ElementParameterParser.getValue(node, "__ACTIVATE_LOG__"));
	
	String stdout = ElementParameterParser.getValue(node, "__STDOUT__");
	String stderr = ElementParameterParser.getValue(node, "__STDERR__");
	
	List<Map<String, String>> templetonProperties = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__TEMPLETON_PROPERTIES__");
	
	boolean useKerberos = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
	String krb_realm = ElementParameterParser.getValue(node, "__KRB_REALM__");
	String krb_princ = ElementParameterParser.getValue(node, "__KRB_PRINC__");
	
	boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
	String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
	String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
	
	
    boolean setPassword = ElementParameterParser.getBooleanValue(node, "__SET_PASSWORD__");
    String passwordFieldName = "__PASSWORD__";
	
	boolean drop=false;
	boolean dropIfExist=false;
	boolean create=false;
	boolean createIfNotExist=false;
	boolean dropIfExistAndCreate=false;
	boolean dropAndCreate=false;
	
	if("DROP".equals(operation))
		drop=true;
	if("DROP_IF_EXIST".equals(operation))
		dropIfExist=true;
	if("CREATE".equals(operation))
		create=true;
	if("DROP_IF_EXIST_CREATE".equals(operation))
		dropIfExistAndCreate=true;
	if("DROP_CREATE".equals(operation))
		dropAndCreate=true;
		
	boolean checkIfExist = "true".equals(ElementParameterParser.getValue(node, "__CREATE_TABLE_IF_NOT_EXIST__"));
		
	StringBuilder parameters=new StringBuilder();

    stringBuffer.append(TEXT_30);
    
	class ParseRestResult {
	
		protected String cid;
		protected boolean activateLog;
		protected String stdout;
		protected String stderr;
	
	
		public void declareVariable(INode node) {
			this.cid = node.getUniqueName();

    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    
		}
	
		public void getJSONObject(INode node, String context) {
			this.cid = node.getUniqueName();

    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
		}
		
		public void manageError(INode node, boolean activateLog) {
			this.cid = node.getUniqueName();
			this.activateLog = activateLog;

    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    
			if(this.activateLog) {
				this.stdout = ElementParameterParser.getValue(node, "__STDOUT__");
				this.stderr = ElementParameterParser.getValue(node, "__STDERR__");

    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(this.stdout);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(this.stderr);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    
			}
		}
		
		public void manageResponseStatus(INode node, String context) {
			this.cid = node.getUniqueName();

    stringBuffer.append(TEXT_80);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(context==null?"":context+"_");
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
    
		}
	}

    
	ParseRestResult parserResult = new ParseRestResult();

    stringBuffer.append(TEXT_95);
    
		parserResult.declareVariable(node);
		if(useKerberos) {

    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(krb_princ);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(krb_realm);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    
				if(useKeytab) {
			
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_118);
    
				}
			
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    
		}

    stringBuffer.append(TEXT_123);
    
		if("OP_DATABASE".equals(operationOn)) {
		
			/* ---------------------------------------------------------------- 
			   ------------DROP DATABASE or DROP DATABASE IF EXIST ------------
			   ---------------------------------------------------------------- */
			if(drop || dropIfExist || dropIfExistAndCreate || dropAndCreate) { // DROP DATABASE or DROP DATABASE IF EXIST
				if(drop || dropAndCreate)
					parameters.append(" + \"&ifExists=false" + "\"");
				if(dropIfExist || dropIfExistAndCreate)
					parameters.append(" + \"&ifExists=true" + "\"");
				if(!("none".equals(option)))
					parameters.append(" + \"&option=\" + \"" + option + "\"");
				if(setUserGroup) {
					parameters.append(" + \"&group=\" + " + userGroup);
				}
				if(setPermissions) {
					parameters.append(" + \"&permissions=\" + " + permissions);
				}

    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(templeton_host);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(templeton_port);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(username);
    stringBuffer.append(parameters);
    stringBuffer.append(TEXT_128);
    
				if(useKerberos) {

    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    
				} else if (setPassword) {
                    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_134);
    
                    } else {

    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_137);
    
                    }

    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_143);
    
                }

    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(database);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    
				parserResult.getJSONObject(node, "drop");
				parserResult.manageError(node, activateLog);
				parserResult.manageResponseStatus(node, "drop");
			}
			
			/* ----------------------------------------------------------------- 
			   ---------------------- CREATE DATABASE --------------------------
			   ----------------------------------------------------------------- */
			if(create ||  dropIfExistAndCreate ||  dropAndCreate) {
				if(templetonProperties.size()>0) {

    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_151);
    
					for(Map<String, String> item : templetonProperties){

    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_153);
    stringBuffer.append(item.get("KEY"));
    stringBuffer.append(TEXT_154);
    stringBuffer.append(item.get("VALUE"));
    stringBuffer.append(TEXT_155);
    
					}

    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_157);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_158);
    
				}

    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(dbDescription);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(dbLocation);
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_164);
    stringBuffer.append(templeton_host);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(templeton_port);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(username);
    stringBuffer.append(parameters);
    stringBuffer.append(TEXT_167);
    
				if(useKerberos) {

    stringBuffer.append(TEXT_168);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_170);
    
				} else if (setPassword) {
                    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_173);
    
                    } else {

    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_175);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_176);
    
                    }

    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    
                }

    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    stringBuffer.append(database);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_186);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_187);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_188);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_189);
    
				parserResult.getJSONObject(node, "create");
				parserResult.manageError(node, activateLog);
				parserResult.manageResponseStatus(node, "create");
			}
			// -------------------------------------------------------
		} else if("OP_TABLE".equals(operationOn)) {

			/* ---------------------------------------------------------------- 
			   -------------- DROP TABLE or DROP TABLE IF EXIST ---------------
			   ---------------------------------------------------------------- */
			if(drop || dropIfExist || dropIfExistAndCreate || dropAndCreate) {
				if(drop || dropAndCreate)
					parameters.append(" + \"&ifExists=false" + "\"");
				if(dropIfExist || dropIfExistAndCreate)
					parameters.append(" + \"&ifExists=true" + "\"");
				if(!("none".equals(option)))
					parameters.append(" + \"&option=\" + \"" + option + "\"");
				if(setUserGroup) {
					parameters.append(" + \"&group=\" + " + userGroup );
				}
				if(setPermissions) {
					parameters.append(" + \"&permissions=\" + " + permissions);
				}

    stringBuffer.append(TEXT_190);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_191);
    stringBuffer.append(templeton_host);
    stringBuffer.append(TEXT_192);
    stringBuffer.append(templeton_port);
    stringBuffer.append(TEXT_193);
    stringBuffer.append(username);
    stringBuffer.append(parameters);
    stringBuffer.append(TEXT_194);
    
				if(useKerberos) {

    stringBuffer.append(TEXT_195);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_196);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_197);
    
				} else if (setPassword) {
                    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_198);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_200);
    
                    } else {

    stringBuffer.append(TEXT_201);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_202);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_203);
    
                    }

    stringBuffer.append(TEXT_204);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_205);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    
                }

    stringBuffer.append(TEXT_210);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_211);
    stringBuffer.append(database);
    stringBuffer.append(TEXT_212);
    stringBuffer.append(table);
    stringBuffer.append(TEXT_213);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_215);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_216);
    
				parserResult.getJSONObject(node, "drop");
				parserResult.manageError(node, activateLog);
				parserResult.manageResponseStatus(node, "drop");
			}
			
			/* ---------------------------------------------------------------- 
			   ------------------------ CREATE TABLE --------------------------
			   ---------------------------------------------------------------- */
			if(create ||  dropIfExistAndCreate ||  dropAndCreate) {
				if(templetonProperties.size()>0) {

    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_218);
    
					for(Map<String, String> item : templetonProperties){

    stringBuffer.append(TEXT_219);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_220);
    stringBuffer.append(item.get("KEY"));
    stringBuffer.append(TEXT_221);
    stringBuffer.append(item.get("VALUE"));
    stringBuffer.append(TEXT_222);
    
					}

    stringBuffer.append(TEXT_223);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    
				}
				
				if(checkIfExist) {

    stringBuffer.append(TEXT_226);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_227);
    
				} else {

    stringBuffer.append(TEXT_228);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    
				}
				
				if(createExternal) {

    stringBuffer.append(TEXT_230);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_231);
    
				}
				
				if(setHDFSLocation) {

    stringBuffer.append(TEXT_232);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_233);
    stringBuffer.append(hdfsLocation);
    stringBuffer.append(TEXT_234);
    
				}

    stringBuffer.append(TEXT_235);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(dbDescription);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_238);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_239);
    stringBuffer.append(storageFormat);
    stringBuffer.append(TEXT_240);
    
				if(setRowFormat) {

    stringBuffer.append(TEXT_241);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_242);
    if(setFieldsTerminatedBy){
    stringBuffer.append(TEXT_243);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_244);
    stringBuffer.append(fieldsTerminatedBy);
    stringBuffer.append(TEXT_245);
    }
    stringBuffer.append(TEXT_246);
    if(setCollectionItemsTerminatedBy){
    stringBuffer.append(TEXT_247);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_248);
    stringBuffer.append(collectionItemsTerminatedBy);
    stringBuffer.append(TEXT_249);
    }
    stringBuffer.append(TEXT_250);
    if(setMapKeysTerminatedBy){
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(mapKeysTerminatedBy);
    stringBuffer.append(TEXT_253);
    }
    stringBuffer.append(TEXT_254);
    if(setLinesTerminatedBy){
    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    stringBuffer.append(linesTerminatedBy);
    stringBuffer.append(TEXT_257);
    }
    stringBuffer.append(TEXT_258);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_262);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_263);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_264);
    
				}

    stringBuffer.append(TEXT_265);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_266);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_267);
    
				List<IMetadataTable> metadatas = node.getMetadataList();
				if ((metadatas!=null)&&(metadatas.size()>0)) {
					IMetadataTable metadata = metadatas.get(0);
					if (metadata!=null) {
						List<IMetadataColumn> columnList = metadata.getListColumns();
						if(columnList != null && columnList.size() > 0) {

    stringBuffer.append(TEXT_268);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_269);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_270);
    
							for(int i=0; i<columnList.size(); i++) {

    stringBuffer.append(TEXT_271);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_272);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_273);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_274);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_275);
    stringBuffer.append(Java2STLangTypesHelper.getHCatType(metadata, columnList.get(i).getLabel()));
    stringBuffer.append(TEXT_276);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_277);
    stringBuffer.append(columnList.get(i).getComment());
    stringBuffer.append(TEXT_278);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_279);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_280);
    
							}

    stringBuffer.append(TEXT_281);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_282);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_283);
    
						} else {
							// Undefined schema
							if(dieOnError) {

    stringBuffer.append(TEXT_284);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_285);
    
							}else{

    stringBuffer.append(TEXT_286);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_287);
    
								if(isLog4jEnabled) {

    stringBuffer.append(TEXT_288);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_289);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_290);
    
								}
							}
						}
					}
				}
				if(setPartitions) {
					metadatas = node.getMetadataList();
					if ((metadatas!=null)&&(metadatas.size()>0)) {
						IMetadataTable metadata = metadatas.get(1);
						if (metadata!=null) {
							List<IMetadataColumn> columnList = metadata.getListColumns();
							if(columnList != null && columnList.size() > 0) {

    stringBuffer.append(TEXT_291);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_293);
    
								for(int i=0; i<columnList.size(); i++) {

    stringBuffer.append(TEXT_294);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_295);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_296);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_297);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_298);
    stringBuffer.append(Java2STLangTypesHelper.getHCatType(metadata, columnList.get(i).getLabel()));
    stringBuffer.append(TEXT_299);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_300);
    stringBuffer.append(columnList.get(i).getComment());
    stringBuffer.append(TEXT_301);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_302);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_303);
    
								}

    stringBuffer.append(TEXT_304);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_305);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_306);
    
							}
						}
					}
				}

    stringBuffer.append(TEXT_307);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_308);
    stringBuffer.append(templeton_host);
    stringBuffer.append(TEXT_309);
    stringBuffer.append(templeton_port);
    stringBuffer.append(TEXT_310);
    stringBuffer.append(username);
    stringBuffer.append(parameters);
    stringBuffer.append(TEXT_311);
    
				if(useKerberos) {

    stringBuffer.append(TEXT_312);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_313);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_314);
    
				} else if (setPassword) {
                    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_315);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_316);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_317);
    
                    } else {

    stringBuffer.append(TEXT_318);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_319);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_320);
    
                    }

    stringBuffer.append(TEXT_321);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_322);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_324);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_325);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_326);
    
                }

    stringBuffer.append(TEXT_327);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_328);
    stringBuffer.append(database);
    stringBuffer.append(TEXT_329);
    stringBuffer.append(table);
    stringBuffer.append(TEXT_330);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_331);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_332);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_333);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_334);
    
				parserResult.getJSONObject(node, "create");
				parserResult.manageError(node, activateLog);
				parserResult.manageResponseStatus(node, "create");
			}
			
		} else if("OP_PARTITION".equals(operationOn)) {		
			/* ---------------------------------------------------------------- 
			   ------------DROP PARTITION or DROP PARTITION IF EXIST ------------
			   ---------------------------------------------------------------- */
			if(drop || dropIfExist || dropIfExistAndCreate || dropAndCreate) {
				if(drop || dropAndCreate)
					parameters.append(" + \"&ifExists=false" + "\"");
				if(dropIfExist || dropIfExistAndCreate)
					parameters.append(" + \"&ifExists=true" + "\"");
				if(!("none".equals(option)))
					parameters.append(" + \"&option=\" + \"" + option + "\"");
				if(setUserGroup) {
					parameters.append(" + \"&group=\" + " + userGroup );
				}
				if(setPermissions) {
					parameters.append(" + \"&permissions=\" + " + permissions);
				}

    stringBuffer.append(TEXT_335);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_336);
    stringBuffer.append(templeton_host);
    stringBuffer.append(TEXT_337);
    stringBuffer.append(templeton_port);
    stringBuffer.append(TEXT_338);
    stringBuffer.append(username);
    stringBuffer.append(parameters);
    stringBuffer.append(TEXT_339);
    
				if(useKerberos) {

    stringBuffer.append(TEXT_340);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_341);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_342);
    
				} else if (setPassword) {
                    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_343);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_344);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_345);
    
                    } else {

    stringBuffer.append(TEXT_346);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_347);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_348);
    
                    }

    stringBuffer.append(TEXT_349);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_350);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_351);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_352);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_353);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_354);
    
                }

    stringBuffer.append(TEXT_355);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_356);
    stringBuffer.append(database);
    stringBuffer.append(TEXT_357);
    stringBuffer.append(table);
    stringBuffer.append(TEXT_358);
    stringBuffer.append(partition);
    stringBuffer.append(TEXT_359);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_360);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_361);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_362);
    
				parserResult.getJSONObject(node, "drop");
				parserResult.manageError(node, activateLog);
				parserResult.manageResponseStatus(node, "drop");
			}
			
			/* ----------------------------------------------------------------- 
			   ---------------------- CREATE PARTITION --------------------------
			   ----------------------------------------------------------------- */
			if(create ||  dropIfExistAndCreate ||  dropAndCreate) {
				if(setFileLocation) {

    stringBuffer.append(TEXT_363);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_364);
    stringBuffer.append(fileLocation);
    stringBuffer.append(TEXT_365);
    
				}

    stringBuffer.append(TEXT_366);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_367);
    stringBuffer.append(templeton_host);
    stringBuffer.append(TEXT_368);
    stringBuffer.append(templeton_port);
    stringBuffer.append(TEXT_369);
    stringBuffer.append(username);
    stringBuffer.append(parameters);
    stringBuffer.append(TEXT_370);
    
				if(useKerberos) {

    stringBuffer.append(TEXT_371);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_372);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_373);
    
				} else if (setPassword) {
                    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_374);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_375);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_376);
    
                    } else {

    stringBuffer.append(TEXT_377);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_378);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_379);
    
                    }

    stringBuffer.append(TEXT_380);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_381);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_383);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_384);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_385);
    
                }

    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_387);
    stringBuffer.append(database);
    stringBuffer.append(TEXT_388);
    stringBuffer.append(table);
    stringBuffer.append(TEXT_389);
    stringBuffer.append(partition);
    stringBuffer.append(TEXT_390);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_391);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_392);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_393);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_394);
    
				parserResult.getJSONObject(node, "create");
				parserResult.manageError(node, activateLog);
				parserResult.manageResponseStatus(node, "create");
			}
			// -------------------------------------------------------
		}

    stringBuffer.append(TEXT_395);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_396);
    
		if(dieOnError) {

    stringBuffer.append(TEXT_397);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_398);
    
		} else {

    stringBuffer.append(TEXT_399);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_400);
    
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_401);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_402);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_403);
    
			}
		}


    stringBuffer.append(TEXT_404);
    stringBuffer.append(TEXT_405);
    return stringBuffer.toString();
  }
}
