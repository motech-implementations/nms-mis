package org.talend.designer.codegen.translators.bigdata.hcatalog;

import org.talend.core.model.process.INode;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnection;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.process.ElementParameterParser;
import java.util.Map;
import java.util.List;

public class THCatalogInputBeginJava
{
  protected static String nl;
  public static synchronized THCatalogInputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THCatalogInputBeginJava result = new THCatalogInputBeginJava();
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
  protected final String TEXT_30 = NL + "\t\tclass KerberosPolicyConfig_";
  protected final String TEXT_31 = " extends javax.security.auth.login.Configuration {" + NL + "\t\t\tprivate java.util.Map<String, String> properties;" + NL + "" + NL + "\t\t\tpublic KerberosPolicyConfig_";
  protected final String TEXT_32 = "() {" + NL + "\t\t\t\tproperties = java.util.Collections" + NL + "\t\t\t\t\t\t.<String, String> emptyMap();" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\tpublic KerberosPolicyConfig_";
  protected final String TEXT_33 = "(" + NL + "\t\t\t\t\tjava.util.Map<String, String> properties) {" + NL + "\t\t\t\tthis.properties = properties;" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\tpublic javax.security.auth.login.AppConfigurationEntry[] getAppConfigurationEntry(" + NL + "\t\t\t\t\tString name) {" + NL + "\t\t\t\treturn new javax.security.auth.login.AppConfigurationEntry[] { new javax.security.auth.login.AppConfigurationEntry(" + NL + "\t\t\t\t\t\tcom.sun.security.auth.module.Krb5LoginModule.class" + NL + "\t\t\t\t\t\t\t\t.getName()," + NL + "\t\t\t\t\t\tjavax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag.REQUIRED," + NL + "\t\t\t\t\t\tproperties) };" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "" + NL + "\t\torg.apache.cxf.jaxrs.security.KerberosAuthOutInterceptor kbInterceptor_";
  protected final String TEXT_34 = " = new org.apache.cxf.jaxrs.security.KerberosAuthOutInterceptor();" + NL + "" + NL + "\t\torg.apache.cxf.configuration.security.AuthorizationPolicy policy_";
  protected final String TEXT_35 = " = new org.apache.cxf.configuration.security.AuthorizationPolicy();" + NL + "\t\tpolicy_";
  protected final String TEXT_36 = ".setAuthorizationType(org.apache.cxf.transport.http.auth.HttpAuthHeader.AUTH_TYPE_NEGOTIATE);" + NL + "" + NL + "\t\tkbInterceptor_";
  protected final String TEXT_37 = ".setPolicy(policy_";
  protected final String TEXT_38 = ");" + NL + "\t\tjava.util.Map<String, String> properties_";
  protected final String TEXT_39 = " = new java.util.HashMap<String, String>();" + NL + "\t\tkbInterceptor_";
  protected final String TEXT_40 = ".setServicePrincipalName(";
  protected final String TEXT_41 = ");" + NL + "\t\tkbInterceptor_";
  protected final String TEXT_42 = ".setRealm(";
  protected final String TEXT_43 = ");" + NL + "\t\tproperties_";
  protected final String TEXT_44 = ".put(\"useTicketCache\", \"true\");" + NL + "\t\tproperties_";
  protected final String TEXT_45 = ".put(\"refreshKrb5Config\", \"true\");" + NL + "\t\tproperties_";
  protected final String TEXT_46 = ".put(\"renewTGT\", \"true\");";
  protected final String TEXT_47 = NL + "\t\t\t\tproperties_";
  protected final String TEXT_48 = ".put(\"useKeyTab\", \"true\");" + NL + "\t\t\t\tproperties_";
  protected final String TEXT_49 = ".put(\"keyTab\", ";
  protected final String TEXT_50 = ");" + NL + "\t\t\t\tproperties_";
  protected final String TEXT_51 = ".put(\"principal\", ";
  protected final String TEXT_52 = ");";
  protected final String TEXT_53 = NL + "\t\tkbInterceptor_";
  protected final String TEXT_54 = ".setLoginConfig(new KerberosPolicyConfig_";
  protected final String TEXT_55 = "(properties_";
  protected final String TEXT_56 = "));";
  protected final String TEXT_57 = NL + "\tString credentials_";
  protected final String TEXT_58 = " = \"?user.name=\" + ";
  protected final String TEXT_59 = " + \"&format=extended\";" + NL + "\torg.apache.cxf.jaxrs.client.WebClient client_";
  protected final String TEXT_60 = " = org.apache.cxf.jaxrs.client.WebClient.create(\"http://\" + ";
  protected final String TEXT_61 = " + \":\" + ";
  protected final String TEXT_62 = " + credentials_";
  protected final String TEXT_63 = ");";
  protected final String TEXT_64 = NL + "\t\torg.apache.cxf.jaxrs.client.WebClient.getConfig(client_";
  protected final String TEXT_65 = ").getOutInterceptors().add(kbInterceptor_";
  protected final String TEXT_66 = ");";
  protected final String TEXT_67 = NL + "\t        final String decryptedPassword_client_";
  protected final String TEXT_68 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_69 = ");";
  protected final String TEXT_70 = NL + "\t        final String decryptedPassword_client_";
  protected final String TEXT_71 = " = ";
  protected final String TEXT_72 = "; ";
  protected final String TEXT_73 = NL + "        String authorizationHeader_drop_";
  protected final String TEXT_74 = " = \"Basic \"" + NL + "                + org.apache.cxf.common.util.Base64Utility.encode((";
  protected final String TEXT_75 = " + \":\" + decryptedPassword_client_";
  protected final String TEXT_76 = ").getBytes());" + NL + "        client_";
  protected final String TEXT_77 = ".header(\"Authorization\", authorizationHeader_drop_";
  protected final String TEXT_78 = ");";
  protected final String TEXT_79 = NL + "\tString pathEndpoint_";
  protected final String TEXT_80 = " = \"/templeton/v1/ddl/database/\" + ";
  protected final String TEXT_81 = " + \"/table/\" + ";
  protected final String TEXT_82 = ";" + NL + "\tif(!\"\".equals(";
  protected final String TEXT_83 = ")) {" + NL + "\t\tpathEndpoint_";
  protected final String TEXT_84 = " += \"/partition/\" + ";
  protected final String TEXT_85 = ";" + NL + "\t}" + NL + "\t" + NL + "\tString location_";
  protected final String TEXT_86 = " = \"\";" + NL + "\t";
  protected final String TEXT_87 = NL + "\t\t\tString err_";
  protected final String TEXT_88 = " = \"\";" + NL + "\t\t\torg.json.simple.JSONObject jsonRow_";
  protected final String TEXT_89 = " = new org.json.simple.JSONObject();" + NL + "\t\t\tjava.io.InputStream is_";
  protected final String TEXT_90 = ";" + NL + "\t\t\tObject obj_";
  protected final String TEXT_91 = ";" + NL + "\t\t\torg.json.simple.JSONObject object_";
  protected final String TEXT_92 = ";" + NL + "\t\t\tObject error_";
  protected final String TEXT_93 = ";";
  protected final String TEXT_94 = NL + "\t\t\tis_";
  protected final String TEXT_95 = " = (java.io.InputStream) response_";
  protected final String TEXT_96 = ".getEntity();" + NL + "\t\t\tString result_";
  protected final String TEXT_97 = " = org.apache.cxf.helpers.IOUtils.toString(is_";
  protected final String TEXT_98 = ");" + NL + "\t\t\tobj_";
  protected final String TEXT_99 = "=org.json.simple.JSONValue.parse(result_";
  protected final String TEXT_100 = ");" + NL + "\t\t\tobject_";
  protected final String TEXT_101 = "=(org.json.simple.JSONObject)obj_";
  protected final String TEXT_102 = ";";
  protected final String TEXT_103 = NL + "\t\t\terror_";
  protected final String TEXT_104 = " = object_";
  protected final String TEXT_105 = ".get(\"error\");" + NL + "\t\t\tif(error_";
  protected final String TEXT_106 = "!=null) {" + NL + "\t\t\t\terr_";
  protected final String TEXT_107 = " = error_";
  protected final String TEXT_108 = ".toString();" + NL + "\t\t\t}";
  protected final String TEXT_109 = NL + "\t\t\t\tif(((org.json.simple.JSONObject)object_";
  protected final String TEXT_110 = ".get(\"exec\"))!=null) {" + NL + "\t\t\t\t\tString time_";
  protected final String TEXT_111 = " = TalendDate.formatDate(\"yyyyMMddHHmmss\", TalendDate.getCurrentDate());" + NL + "\t\t\t\t\tObject stdout_";
  protected final String TEXT_112 = " = ((org.json.simple.JSONObject)object_";
  protected final String TEXT_113 = ".get(\"exec\")).get(\"stdout\");" + NL + "\t\t\t\t\tObject stderr_";
  protected final String TEXT_114 = " = ((org.json.simple.JSONObject)object_";
  protected final String TEXT_115 = ".get(\"exec\")).get(\"stderr\");" + NL + "\t\t\t\t\tString stdout_filename_";
  protected final String TEXT_116 = " = \"/\" + projectName + \"_\" + jobName + \"_\" + jobVersion + \"_\" + time_";
  protected final String TEXT_117 = " + \"_\" + \"";
  protected final String TEXT_118 = "\" + \"_stdout.log\";" + NL + "\t\t\t\t\tString stderr_filename_";
  protected final String TEXT_119 = " = \"/\" + projectName + \"_\" + jobName + \"_\" + jobVersion + \"_\" + time_";
  protected final String TEXT_120 = " + \"_\" + \"";
  protected final String TEXT_121 = "\" + \"_stderr.log\";" + NL + "\t\t\t\t\tjava.io.FileWriter fw_out_";
  protected final String TEXT_122 = " = new java.io.FileWriter(new java.io.File(";
  protected final String TEXT_123 = "+stdout_filename_";
  protected final String TEXT_124 = "));" + NL + "\t\t\t\t\tjava.io.FileWriter fw_err_";
  protected final String TEXT_125 = " = new java.io.FileWriter(new java.io.File(";
  protected final String TEXT_126 = "+stderr_filename_";
  protected final String TEXT_127 = "));" + NL + "\t\t\t\t\tfw_out_";
  protected final String TEXT_128 = ".write(stdout_";
  protected final String TEXT_129 = "!=null?stdout_";
  protected final String TEXT_130 = ".toString():\"\");" + NL + "\t\t\t\t\tfw_err_";
  protected final String TEXT_131 = ".write(stderr_";
  protected final String TEXT_132 = "!=null?stderr_";
  protected final String TEXT_133 = ".toString():\"\");" + NL + "\t\t\t\t\tfw_out_";
  protected final String TEXT_134 = ".close();" + NL + "\t\t\t\t\tfw_err_";
  protected final String TEXT_135 = ".close();" + NL + "\t\t\t\t}";
  protected final String TEXT_136 = NL + "\t\t\tif(response_";
  protected final String TEXT_137 = ".getStatus()!=200) {" + NL + "\t\t\t\tif(response_";
  protected final String TEXT_138 = ".getStatus()==400) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 400 (Bad request): The request was invalid.\\n--->\" + err_";
  protected final String TEXT_139 = ");" + NL + "\t\t\t\t} else if(response_";
  protected final String TEXT_140 = ".getStatus()==401) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 401 (Unauthorized): Credentials were missing or incorrect.\\n--->\" + err_";
  protected final String TEXT_141 = ");" + NL + "\t\t\t\t} else if(response_";
  protected final String TEXT_142 = ".getStatus()==404) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 404 (Not found): The URI requested is invalid or the resource requested does not exist.\\n--->\" + err_";
  protected final String TEXT_143 = ");" + NL + "\t\t\t\t} else if(response_";
  protected final String TEXT_144 = ".getStatus()==500) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 500 (Internal Server Error): We received an unexpected result.\\n--->\" + err_";
  protected final String TEXT_145 = ");" + NL + "\t\t\t\t} else if(response_";
  protected final String TEXT_146 = ".getStatus()==503) {" + NL + "\t\t\t\t\tthrow new Exception(\"Error 503 (Busy, please retry): The server is busy.\\n--->\" + err_";
  protected final String TEXT_147 = ");" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tthrow new Exception(object_";
  protected final String TEXT_148 = ".get(\"errorCode\") + \":\" + object_";
  protected final String TEXT_149 = ".get(\"error\") + object_";
  protected final String TEXT_150 = ".get(\"errorDetail\"));" + NL + "\t\t\t\t}" + NL + "\t\t\t}";
  protected final String TEXT_151 = NL + "\ttry {" + NL + "\t\tclient_";
  protected final String TEXT_152 = ".path(pathEndpoint_";
  protected final String TEXT_153 = ");" + NL + "\t\tclient_";
  protected final String TEXT_154 = ".accept(\"application/json\");" + NL + "\t\t" + NL + "\t\tjavax.ws.rs.core.Response response_";
  protected final String TEXT_155 = " = client_";
  protected final String TEXT_156 = ".get();" + NL;
  protected final String TEXT_157 = "\t\t" + NL + "\t\tObject loc_";
  protected final String TEXT_158 = " = object_";
  protected final String TEXT_159 = ".get(\"location\");" + NL + "\t\tif(loc_";
  protected final String TEXT_160 = "!=null) {" + NL + "\t\t\tlocation_";
  protected final String TEXT_161 = " = loc_";
  protected final String TEXT_162 = ".toString();" + NL + "\t\t}" + NL + "\t} catch (Exception e_";
  protected final String TEXT_163 = ") {";
  protected final String TEXT_164 = NL + "\t\t\tthrow e_";
  protected final String TEXT_165 = ";";
  protected final String TEXT_166 = NL + "\t\t\tSystem.err.println(e_";
  protected final String TEXT_167 = ".getMessage());";
  protected final String TEXT_168 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_169 = " - \" + e_";
  protected final String TEXT_170 = ".getMessage());";
  protected final String TEXT_171 = NL + "\t}" + NL + "\t" + NL + "\tif(location_";
  protected final String TEXT_172 = " == null || \"\".equals(location_";
  protected final String TEXT_173 = ") || location_";
  protected final String TEXT_174 = ".length() <= 0) {";
  protected final String TEXT_175 = NL + "\t\t\tthrow new Exception(\"Error during the response parsing. The Templeton server has returned a bad value or the location isn't available\");";
  protected final String TEXT_176 = NL + "\t\t\tSystem.err.println(\"Error during the response parsing. The Templeton server has returned a bad value or the location isn't available\");";
  protected final String TEXT_177 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_178 = " - Error during the response parsing. The Templeton server has returned a bad value or the location isn't available\");";
  protected final String TEXT_179 = NL + "\t}" + NL + "\t\t" + NL + "\tString pathHDFS_";
  protected final String TEXT_180 = " = \"\";" + NL + "\tString namenode_";
  protected final String TEXT_181 = " = \"\";" + NL + "\t" + NL + "\tif(location_";
  protected final String TEXT_182 = ".contains(\":\")) {";
  protected final String TEXT_183 = NL + "\t\tpathHDFS_";
  protected final String TEXT_184 = " = location_";
  protected final String TEXT_185 = ";" + NL + "\t\tnamenode_";
  protected final String TEXT_186 = " = \"maprfs:///\";";
  protected final String TEXT_187 = NL + "\t\tjava.net.URI uri_location_";
  protected final String TEXT_188 = " = java.net.URI.create(location_";
  protected final String TEXT_189 = ");" + NL + "\t\tpathHDFS_";
  protected final String TEXT_190 = " = uri_location_";
  protected final String TEXT_191 = ".getPath();" + NL + "\t\tnamenode_";
  protected final String TEXT_192 = " = uri_location_";
  protected final String TEXT_193 = ".getScheme() + \"://\" + uri_location_";
  protected final String TEXT_194 = ".getAuthority();";
  protected final String TEXT_195 = NL + "\t}";
  protected final String TEXT_196 = NL + "\t\tString username_";
  protected final String TEXT_197 = " = \"\";" + NL + "\t\torg.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_198 = " = null;";
  protected final String TEXT_199 = NL + "\t\torg.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_200 = " = new org.apache.hadoop.conf.Configuration();" + NL + "\t\tconf_";
  protected final String TEXT_201 = ".set(\"fs.default.name\", namenode_";
  protected final String TEXT_202 = ");";
  protected final String TEXT_203 = NL + "                System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_204 = ");" + NL + "                System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_205 = ");";
  protected final String TEXT_206 = NL + "\t\t\tconf_";
  protected final String TEXT_207 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_208 = ");";
  protected final String TEXT_209 = NL + "\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_210 = ", ";
  protected final String TEXT_211 = ");";
  protected final String TEXT_212 = NL + "                com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_213 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "                maprLogin_";
  protected final String TEXT_214 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_215 = ", ";
  protected final String TEXT_216 = ");";
  protected final String TEXT_217 = NL + "                System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_218 = ");" + NL + "                com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_219 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_220 = NL + "                    System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_221 = ");";
  protected final String TEXT_222 = NL + "                    maprLogin_";
  protected final String TEXT_223 = ".setCheckUGI(false);";
  protected final String TEXT_224 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_225 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_226 = ");";
  protected final String TEXT_227 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_228 = " = ";
  protected final String TEXT_229 = "; ";
  protected final String TEXT_230 = NL;
  protected final String TEXT_231 = NL + "                \tmaprLogin_";
  protected final String TEXT_232 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_233 = ", ";
  protected final String TEXT_234 = ", decryptedPassword_";
  protected final String TEXT_235 = ", ";
  protected final String TEXT_236 = ", \"\");" + NL + "                \t";
  protected final String TEXT_237 = NL + "                \tmaprLogin_";
  protected final String TEXT_238 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_239 = ", ";
  protected final String TEXT_240 = ", decryptedPassword_";
  protected final String TEXT_241 = ", ";
  protected final String TEXT_242 = ");" + NL + "                \t";
  protected final String TEXT_243 = NL + "\t\t\tusername_";
  protected final String TEXT_244 = " = ";
  protected final String TEXT_245 = ";";
  protected final String TEXT_246 = NL + "\t\t\t\tconf_";
  protected final String TEXT_247 = ".set(";
  protected final String TEXT_248 = " ,";
  protected final String TEXT_249 = ");";
  protected final String TEXT_250 = NL + "\t\tif(username_";
  protected final String TEXT_251 = " == null || \"\".equals(username_";
  protected final String TEXT_252 = ")){" + NL + "\t\t\tfs_";
  protected final String TEXT_253 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_254 = ");" + NL + "\t\t}else{" + NL + "\t\t\tfs_";
  protected final String TEXT_255 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_256 = ".get(\"fs.default.name\")),conf_";
  protected final String TEXT_257 = ",username_";
  protected final String TEXT_258 = ");" + NL + "\t\t}\t" + NL + "" + NL + "\t\torg.apache.hadoop.fs.FileStatus [] fileStatus_";
  protected final String TEXT_259 = "= fs_";
  protected final String TEXT_260 = ".listStatus(new org.apache.hadoop.fs.Path(pathHDFS_";
  protected final String TEXT_261 = "));";
  protected final String TEXT_262 = NL + "\t\tfor(int i_";
  protected final String TEXT_263 = "=0; i_";
  protected final String TEXT_264 = "<fileStatus_";
  protected final String TEXT_265 = ".length; i_";
  protected final String TEXT_266 = "++) {" + NL + "\t\t\tif(fileStatus_";
  protected final String TEXT_267 = "[i_";
  protected final String TEXT_268 = "].isDir()) {" + NL + "\t\t\t\tcontinue;" + NL + "\t\t\t}" + NL + "\t\t\torg.apache.hadoop.fs.Path path_";
  protected final String TEXT_269 = " = fileStatus_";
  protected final String TEXT_270 = "[i_";
  protected final String TEXT_271 = "].getPath();" + NL + "\t\t\torg.apache.hadoop.fs.FSDataInputStream fsDataInputStream_";
  protected final String TEXT_272 = " = fs_";
  protected final String TEXT_273 = ".open(path_";
  protected final String TEXT_274 = ");" + NL + "\t\t\torg.talend.fileprocess.FileInputDelimited fid_";
  protected final String TEXT_275 = " = new org.talend.fileprocess.FileInputDelimited(fsDataInputStream_";
  protected final String TEXT_276 = ", ";
  protected final String TEXT_277 = ",";
  protected final String TEXT_278 = ",";
  protected final String TEXT_279 = ",false,0,0,-1,-1, false);" + NL + "\t\t\twhile (fid_";
  protected final String TEXT_280 = ".nextRecord()) {";
  protected final String TEXT_281 = NL + NL + "\t";

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
	
	String rowSeparator = ElementParameterParser.getValue(node, "__ROWSEPARATOR__");
	String fieldSeparator = ElementParameterParser.getValue(node, "__FIELDSEPARATOR__");
	boolean customEncoding="true".equals( ElementParameterParser.getValue(node,"__CUSTOM_ENCODING__"));
	String encoding = ElementParameterParser.getValue(node,"__ENCODING__");
	
	String templetonHost = ElementParameterParser.getValue(node, "__TEMPLETON_HOST__");
	String templetonPort = ElementParameterParser.getValue(node, "__TEMPLETON_PORT__");
	String database = ElementParameterParser.getValue(node, "__DATABASE_NAME__");
	String table = ElementParameterParser.getValue(node, "__TABLE_NAME__");
	String partition = ElementParameterParser.getValue(node, "__PARTITION_NAME__");
	String user = ElementParameterParser.getValue(node, "__USERNAME__");
	
	String fsDefaultName = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
	List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");
		
	String fileName = ElementParameterParser.getValue(node, "__FILENAME__");
	
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	boolean activateLog = "true".equals(ElementParameterParser.getValue(node, "__ACTIVATE_LOG__"));
	
	String stdout = ElementParameterParser.getValue(node, "__STDOUT__");
	String stderr = ElementParameterParser.getValue(node, "__STDERR__");
	
	IConnection outConn = null;
	IMetadataTable metadata = null;
	
	boolean useKerberos = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
	String krb_realm = ElementParameterParser.getValue(node, "__KRB_REALM__");
	String krb_princ = ElementParameterParser.getValue(node, "__KRB_PRINC__");
	String kerberosPrincipal = ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");
	boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
	String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
	String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
	
	boolean useMapRTicket = ElementParameterParser.getBooleanValue(node, "__USE_MAPRTICKET__");
    String passwordFieldName = "__MAPRTICKET_PASSWORD__";
    String mapRTicketCluster = ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
    String mapRTicketDuration = ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");

    boolean setMapRHomeDir = ElementParameterParser.getBooleanValue(node, "__SET_MAPR_HOME_DIR__");
    String mapRHomeDir = ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");

    boolean setMapRHadoopLogin = ElementParameterParser.getBooleanValue(node, "__SET_HADOOP_LOGIN__");
    String mapRHadoopLogin = ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");
    String distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
    String hcatVersion = ElementParameterParser.getValue(node, "__HCAT_VERSION__");
    boolean isCustom = "CUSTOM".equals(ElementParameterParser.getValue(node, "__DISTRIBUTION__"));
	
    org.talend.hadoop.distribution.component.HDFSComponent hcatDistrib = null;
    try {
        hcatDistrib = (org.talend.hadoop.distribution.component.HDFSComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hcatVersion);
    } catch (java.lang.Exception e) {
        e.printStackTrace();
        return "";
    }
	if(useKerberos) {

    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(krb_princ);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(krb_realm);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
			if(useKeytab) {

    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_52);
    
			}

    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    
	}	

    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(templetonHost);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(templetonPort);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    
	if(useKerberos) {

    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    
	} else if ((isCustom || hcatDistrib.doSupportMapRTicket()) && useMapRTicket) {
	    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_69);
    
	    } else {

    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_72);
    
        }

    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    
	}

    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(database);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(table);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(partition);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(partition);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    
	class ParseRestResult {
	
		protected String cid;
		protected boolean activateLog;
		protected String stdout;
		protected String stderr;
	
	
		public void declareVariable(INode node) {
			this.cid = node.getUniqueName();

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
    
		}
	
		public void getJSONObject(INode node, String context) {
			this.cid = node.getUniqueName();

    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    
		}
		
		public void manageError(INode node, boolean activateLog) {
			this.cid = node.getUniqueName();
			this.activateLog = activateLog;

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
    
			if(this.activateLog) {
				this.stdout = ElementParameterParser.getValue(node, "__STDOUT__");
				this.stderr = ElementParameterParser.getValue(node, "__STDERR__");

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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(this.stdout);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(this.stderr);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid);
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
    
			}
		}
		
		public void manageResponseStatus(INode node, String context) {
			this.cid = node.getUniqueName();

    stringBuffer.append(TEXT_136);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(context==null?"":context+"_");
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_150);
    
		}
	}

    
		ParseRestResult parserResult = new ParseRestResult();
		parserResult.declareVariable(node);

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
    
			parserResult.getJSONObject(node, null);
			parserResult.manageError(node, activateLog);
			parserResult.manageResponseStatus(node, null);

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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    
		if(dieOnError) {

    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_165);
    
		} else {

    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_168);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_170);
    
			}
		}

    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_174);
    
		if(dieOnError) {

    stringBuffer.append(TEXT_175);
    
		} else {

    stringBuffer.append(TEXT_176);
    
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_178);
    
			}
		}

    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    
        if(!isCustom && hcatVersion!=null && hcatVersion.contains("MAPR")) {

    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_186);
    
		} else {

    stringBuffer.append(TEXT_187);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_188);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_191);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_193);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_194);
    
		}

    stringBuffer.append(TEXT_195);
    
	List<IMetadataTable> metadatas = node.getMetadataList();
	if ((metadatas!=null)&&(metadatas.size()>0)) {
		metadata = metadatas.get(0);
		if (metadata!=null) { 

    stringBuffer.append(TEXT_196);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_197);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_198);
       
		String hadoopVersion = ElementParameterParser.getValue(node, "__DB_VERSION__");

    stringBuffer.append(TEXT_199);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_200);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_201);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_202);
    
		if(useKerberos) {
		    if ((isCustom || hcatDistrib.doSupportMapRTicket()) && useMapRTicket) {
                
    stringBuffer.append(TEXT_203);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_204);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_205);
    
            }
		    
    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_208);
    
			if(useKeytab) {

    stringBuffer.append(TEXT_209);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_210);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_211);
    
			}
            if ((isCustom || hcatDistrib.doSupportMapRTicket()) && useMapRTicket) {
                
    stringBuffer.append(TEXT_212);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_213);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_215);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_216);
    
            }
		} else {
		    // MapR Ticket
		    if ((isCustom || hcatDistrib.doSupportMapRTicket()) && useMapRTicket) {
                
    stringBuffer.append(TEXT_217);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_218);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_219);
    
                if (setMapRHadoopLogin) {
                    
    stringBuffer.append(TEXT_220);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_221);
    
                } else {
                    
    stringBuffer.append(TEXT_222);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_223);
    
                }
                
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_226);
    } else {
    stringBuffer.append(TEXT_227);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_228);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_229);
    }
    stringBuffer.append(TEXT_230);
    
                if(hcatDistrib.doSupportMaprTicketV52API()) {
					
    stringBuffer.append(TEXT_231);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_233);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_234);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_235);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_236);
    
                } else {
                	
    stringBuffer.append(TEXT_237);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_238);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_239);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_240);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_241);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_242);
    
                }
            }

    stringBuffer.append(TEXT_243);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_244);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_245);
    
		}
		if(hadoopProps.size() > 0){
			for(Map<String, String> item : hadoopProps){

    stringBuffer.append(TEXT_246);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_247);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_248);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_249);
     
			} 
		}

    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_253);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_254);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_257);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_258);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    
		log4jFileUtil.startRetriveDataInfo();

    stringBuffer.append(TEXT_262);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_263);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_264);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_265);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_266);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_268);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_269);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_270);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_271);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_272);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_273);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_274);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_275);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_276);
    stringBuffer.append((customEncoding?encoding:null) );
    stringBuffer.append(TEXT_277);
    stringBuffer.append(fieldSeparator );
    stringBuffer.append(TEXT_278);
    stringBuffer.append(rowSeparator );
    stringBuffer.append(TEXT_279);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_280);
    
		}
	}

    stringBuffer.append(TEXT_281);
    return stringBuffer.toString();
  }
}
