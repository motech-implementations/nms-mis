package org.talend.designer.codegen.translators.databases.hive;

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

public class THiveCreateTableMainJava
{
  protected static String nl;
  public static synchronized THiveCreateTableMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THiveCreateTableMainJava result = new THiveCreateTableMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\t\tlog.debug(\"";
  protected final String TEXT_2 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_3 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_4 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_5 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_6 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_7 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_8 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_10 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_11 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_12 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_13 = " - Written records count: \" + nb_line_";
  protected final String TEXT_14 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_15 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_16 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_17 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_18 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_19 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_21 = " - Writing the record \" + nb_line_";
  protected final String TEXT_22 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_23 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_24 = " - Processing the record \" + nb_line_";
  protected final String TEXT_25 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_26 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_27 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_28 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_29 = NL + "\t\t\t\tif(conn_";
  protected final String TEXT_30 = " != null) {" + NL + "\t\t\t\t\tif(conn_";
  protected final String TEXT_31 = ".getMetaData() != null) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_32 = NL + "\t\t\t\t\t\tlog.debug(\"";
  protected final String TEXT_33 = " - Uses an existing connection ";
  protected final String TEXT_34 = ".\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_35 = NL + "\t\t\t\t\t\tlog.debug(\"";
  protected final String TEXT_36 = " - Uses an existing connection with username '\" + conn_";
  protected final String TEXT_37 = ".getMetaData().getUserName() + \"'. Connection URL: \" + conn_";
  protected final String TEXT_38 = ".getMetaData().getURL() + \".\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_39 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_40 = NL + "\t\t\tconn_";
  protected final String TEXT_41 = " = java.sql.DriverManager.getConnection(url_";
  protected final String TEXT_42 = ", dbUser_";
  protected final String TEXT_43 = ", dbPwd_";
  protected final String TEXT_44 = ");" + NL + "\t\t\t";
  protected final String TEXT_45 = NL + "\t\t\tconn_";
  protected final String TEXT_46 = ".rollback();" + NL + "\t\t\t";
  protected final String TEXT_47 = NL + "\t\t\tconn_";
  protected final String TEXT_48 = ".commit();" + NL + "\t\t\t";
  protected final String TEXT_49 = NL + "\t\t\tconn_";
  protected final String TEXT_50 = ".close();" + NL + "\t\t\t";
  protected final String TEXT_51 = NL + "\t\t\t\tconn_";
  protected final String TEXT_52 = ".setAutoCommit(";
  protected final String TEXT_53 = ");" + NL + "\t\t\t";
  protected final String TEXT_54 = NL + "\t\t\t\tlog.";
  protected final String TEXT_55 = "(\"";
  protected final String TEXT_56 = " - \" + ";
  protected final String TEXT_57 = ".getMessage());" + NL + "\t\t\t";
  protected final String TEXT_58 = NL + "\t    \t\tlog.";
  protected final String TEXT_59 = "(\"";
  protected final String TEXT_60 = "\");" + NL + "\t\t\t";
  protected final String TEXT_61 = NL + "\t\t\t\tpstmt_";
  protected final String TEXT_62 = ".executeBatch();" + NL + "\t\t\t";
  protected final String TEXT_63 = NL + "\t\t\t\tint countSum_";
  protected final String TEXT_64 = " = 0;" + NL + "\t\t\t\tfor(int countEach_";
  protected final String TEXT_65 = ": pstmt_";
  protected final String TEXT_66 = ".executeBatch()) {" + NL + "\t\t\t\t\tcountSum_";
  protected final String TEXT_67 = " += (countEach_";
  protected final String TEXT_68 = " < 0 ? 0 : ";
  protected final String TEXT_69 = ");" + NL + "\t\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_70 = NL;
  protected final String TEXT_71 = NL + "\tjava.io.File localPigLatin_";
  protected final String TEXT_72 = " = new java.io.File(projectName + \"_\" + jobName + \"_\" + Thread.currentThread().getId() +\".hive\");" + NL + "\tjava.io.FileWriter fw_";
  protected final String TEXT_73 = " = new java.io.FileWriter(localPigLatin_";
  protected final String TEXT_74 = ".getAbsoluteFile());" + NL + "\tjava.io.BufferedWriter bw_";
  protected final String TEXT_75 = " = new java.io.BufferedWriter(fw_";
  protected final String TEXT_76 = ");" + NL + "\tjava.lang.StringBuilder libjars_";
  protected final String TEXT_77 = " = new StringBuilder();";
  protected final String TEXT_78 = " " + NL + "\t\t\tfinal String hdInsightPassword_";
  protected final String TEXT_79 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_80 = ");";
  protected final String TEXT_81 = NL + "\t\t\tfinal String hdInsightPassword_";
  protected final String TEXT_82 = " = ";
  protected final String TEXT_83 = "; ";
  protected final String TEXT_84 = " " + NL + "\t\t\tfinal String wasbPassword_";
  protected final String TEXT_85 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_86 = ");";
  protected final String TEXT_87 = NL + "\t\t\tfinal String wasbPassword_";
  protected final String TEXT_88 = " = ";
  protected final String TEXT_89 = "; ";
  protected final String TEXT_90 = NL + "\t\torg.talend.bigdata.launcher.fs.FileSystem azureFs_";
  protected final String TEXT_91 = " = new org.talend.bigdata.launcher.fs.AzureFileSystem(\"DefaultEndpointsProtocol=https;\"" + NL + "\t\t\t+ \"AccountName=\"" + NL + "\t\t\t+ ";
  protected final String TEXT_92 = NL + "\t\t\t+ \";\"" + NL + "\t\t\t+ \"AccountKey=\" + wasbPassword_";
  protected final String TEXT_93 = ", ";
  protected final String TEXT_94 = ");" + NL + "\t\t\t\t" + NL + "\t\torg.talend.bigdata.launcher.webhcat.WebHCatJob instance_";
  protected final String TEXT_95 = " = new org.talend.bigdata.launcher.webhcat.QueryJob(azureFs_";
  protected final String TEXT_96 = ", org.talend.bigdata.launcher.utils.JobType.HIVE);" + NL + "\t\t\t\t\t\t" + NL + "\t\tinstance_";
  protected final String TEXT_97 = ".setCredentials(new org.talend.bigdata.launcher.security.HDInsightCredentials(";
  protected final String TEXT_98 = ", hdInsightPassword_";
  protected final String TEXT_99 = "));" + NL + "\t\tinstance_";
  protected final String TEXT_100 = ".setUsername(";
  protected final String TEXT_101 = ");" + NL + "\t\tinstance_";
  protected final String TEXT_102 = ".setWebhcatEndpoint(\"https\", ";
  protected final String TEXT_103 = " + \":\" + ";
  protected final String TEXT_104 = ");" + NL + "\t\tinstance_";
  protected final String TEXT_105 = ".setStatusFolder(org.talend.bigdata.launcher.utils.Utils.removeFirstSlash(";
  protected final String TEXT_106 = "));" + NL + "\t\tinstance_";
  protected final String TEXT_107 = ".setRemoteFolder(org.talend.bigdata.launcher.utils.Utils.removeFirstSlash(";
  protected final String TEXT_108 = "));";
  protected final String TEXT_109 = NL + "\t\torg.talend.bigdata.launcher.webhcat.WebHCatJob instance_";
  protected final String TEXT_110 = " = (org.talend.bigdata.launcher.webhcat.WebHCatJob) globalMap.get(\"conn_";
  protected final String TEXT_111 = "\");" + NL + "\t\t" + NL + "\t\torg.talend.bigdata.launcher.fs.FileSystem azureFs_";
  protected final String TEXT_112 = " = instance_";
  protected final String TEXT_113 = ".getFileSystem();\t\t" + NL + "\t\t" + NL + "\t\tjava.util.List<String> connectionCommandList_";
  protected final String TEXT_114 = " = (java.util.List<String>)globalMap.get(\"commandList_";
  protected final String TEXT_115 = "\");" + NL + "\t\tfor(String command : connectionCommandList_";
  protected final String TEXT_116 = ") {" + NL + "\t\t\tbw_";
  protected final String TEXT_117 = ".write(command);" + NL + "\t\t}";
  protected final String TEXT_118 = NL + "\t((org.talend.bigdata.launcher.webhcat.QueryJob)instance_";
  protected final String TEXT_119 = ").setFileToExecute(projectName + \"_\" + jobName + \"_\" + Thread.currentThread().getId() +\".hive\");" + NL + "\tString wasbPath_";
  protected final String TEXT_120 = " = azureFs_";
  protected final String TEXT_121 = ".getFileSystemPrefix() + \"/\"\t+ instance_";
  protected final String TEXT_122 = ".getRemoteFolder()\t+ \"/libjars/\";";
  protected final String TEXT_123 = NL + "                bw_";
  protected final String TEXT_124 = ".write(\"SET mapreduce.map.memory.mb=\" + ";
  protected final String TEXT_125 = " + \";\");" + NL + "                bw_";
  protected final String TEXT_126 = ".write(\"SET mapreduce.reduce.memory.mb=\" + ";
  protected final String TEXT_127 = " + \";\");" + NL + "                bw_";
  protected final String TEXT_128 = ".write(\"SET yarn.app.mapreduce.am.resource.mb=\" + ";
  protected final String TEXT_129 = " + \";\");";
  protected final String TEXT_130 = NL + "                    bw_";
  protected final String TEXT_131 = ".write(\"SET \"+";
  protected final String TEXT_132 = "+\"=\"+";
  protected final String TEXT_133 = " + \";\");";
  protected final String TEXT_134 = NL + "            String dbname_";
  protected final String TEXT_135 = " = ";
  protected final String TEXT_136 = ";" + NL + "            if(dbname_";
  protected final String TEXT_137 = "!=null && !\"\".equals(dbname_";
  protected final String TEXT_138 = ".trim()) && !\"default\".equals(dbname_";
  protected final String TEXT_139 = ".trim())) {" + NL + "                bw_";
  protected final String TEXT_140 = ".write(\"use \" + dbname_";
  protected final String TEXT_141 = " + \";\");" + NL + "            }";
  protected final String TEXT_142 = NL + NL + "java.sql.Connection conn_";
  protected final String TEXT_143 = " = null;";
  protected final String TEXT_144 = NL + "\t\tSystem.setProperty(\"java.io.tmpdir\", ";
  protected final String TEXT_145 = ");";
  protected final String TEXT_146 = NL + "\tglobalMap.put(\"current_client_path_separator\", System.getProperty(\"path.separator\"));" + NL + "\tSystem.setProperty(\"path.separator\", ";
  protected final String TEXT_147 = ");";
  protected final String TEXT_148 = NL + "\t\tconn_";
  protected final String TEXT_149 = " = (java.sql.Connection)globalMap.get(\"";
  protected final String TEXT_150 = "\");" + NL + "" + NL + "\t\tString dbname_";
  protected final String TEXT_151 = " = (String)globalMap.get(\"";
  protected final String TEXT_152 = "\");" + NL + "    \tif(dbname_";
  protected final String TEXT_153 = "!=null && !\"\".equals(dbname_";
  protected final String TEXT_154 = ".trim()) && !\"default\".equals(dbname_";
  protected final String TEXT_155 = ".trim())) {" + NL + "        \tjava.sql.Statement goToDatabase_";
  protected final String TEXT_156 = " = conn_";
  protected final String TEXT_157 = ".createStatement();" + NL + "        \tgoToDatabase_";
  protected final String TEXT_158 = ".execute(\"use \" + dbname_";
  protected final String TEXT_159 = ");" + NL + "        \tgoToDatabase_";
  protected final String TEXT_160 = ".close();" + NL + "    \t}" + NL + "" + NL + "    \tString dbUser_";
  protected final String TEXT_161 = " = (String)globalMap.get(\"";
  protected final String TEXT_162 = "\");" + NL + "" + NL + "    \tglobalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_163 = "\", System.getProperty(\"HADOOP_USER_NAME\"));" + NL + "\t\tif(dbUser_";
  protected final String TEXT_164 = "!=null && !\"\".equals(dbUser_";
  protected final String TEXT_165 = ".trim())) {" + NL + "\t\t\tSystem.setProperty(\"HADOOP_USER_NAME\",dbUser_";
  protected final String TEXT_166 = ");" + NL + "\t\t\t//make relative file path work for hive" + NL + "\t\t\tglobalMap.put(\"current_client_user_name\", System.getProperty(\"user.name\"));" + NL + "\t\t\tSystem.setProperty(\"user.name\",dbUser_";
  protected final String TEXT_167 = ");" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_168 = NL + "\t\t\tif(true) {" + NL + "\t\t\t\tthrow new java.lang.Exception(\"The distribution ";
  protected final String TEXT_169 = " does not support this version of Hive . Please check your component configuration.\");" + NL + "\t\t\t}";
  protected final String TEXT_170 = NL + "\t\t\tif(true) {" + NL + "\t\t\t\tthrow new java.lang.Exception(\"The distribution ";
  protected final String TEXT_171 = " does not support this connection mode . Please check your component configuration.\");" + NL + "\t\t\t}";
  protected final String TEXT_172 = NL + "\t\t\tif(true) {" + NL + "\t\t\t\tthrow new java.lang.Exception(\"The Hive version and the connection mode are not compatible together. Please check your component configuration.\");" + NL + "\t\t\t}";
  protected final String TEXT_173 = NL + "\t\t\t\tSystem.setProperty(";
  protected final String TEXT_174 = " ,";
  protected final String TEXT_175 = ");";
  protected final String TEXT_176 = NL + "\t\t\tSystem.setProperty(\"hive.metastore.sasl.enabled\", \"true\");" + NL + "\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionDriverName\", ";
  protected final String TEXT_177 = ");" + NL + "\t\t\t";
  protected final String TEXT_178 = NL + "\t\t\t\tSystem.setProperty(\"hive.security.authorization.enabled\", \"false\");" + NL + "\t\t\t\t";
  protected final String TEXT_179 = NL + "\t\t\t\tSystem.setProperty(\"hive.security.authorization.enabled\", \"true\");" + NL + "\t\t\t\t";
  protected final String TEXT_180 = NL + "\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionURL\", ";
  protected final String TEXT_181 = ");" + NL + "\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionUserName\", ";
  protected final String TEXT_182 = ");" + NL + "" + NL + "    \t\t";
  protected final String TEXT_183 = NL + NL + "    \t\t";
  protected final String TEXT_184 = NL + "        \tString decryptedMetastorePassword_";
  protected final String TEXT_185 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_186 = ");" + NL + "   \t\t \t";
  protected final String TEXT_187 = NL + "        \tString decryptedMetastorePassword_";
  protected final String TEXT_188 = " = ";
  protected final String TEXT_189 = ";" + NL + "\t\t\t";
  protected final String TEXT_190 = NL + NL + "\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionPassword\", decryptedMetastorePassword_";
  protected final String TEXT_191 = ");" + NL + "\t\t\tSystem.setProperty(\"hive.metastore.kerberos.principal\", ";
  protected final String TEXT_192 = ");" + NL + "\t\t\t";
  protected final String TEXT_193 = NL + "\t\t\t\tSystem.setProperty(\"hive.server2.authentication.kerberos.principal\", ";
  protected final String TEXT_194 = ");" + NL + "\t\t\t\tSystem.setProperty(\"hive.server2.authentication.kerberos.keytab\", ";
  protected final String TEXT_195 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_196 = NL;
  protected final String TEXT_197 = NL + "\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_198 = ", ";
  protected final String TEXT_199 = ");";
  protected final String TEXT_200 = NL + "\t\t\tSystem.setProperty(\"mapred.job.tracker\", ";
  protected final String TEXT_201 = ");";
  protected final String TEXT_202 = NL + "\t\t\tSystem.setProperty(\"";
  protected final String TEXT_203 = "\", ";
  protected final String TEXT_204 = ");";
  protected final String TEXT_205 = NL + "\t\tString dbUser_";
  protected final String TEXT_206 = " = ";
  protected final String TEXT_207 = ";" + NL + "" + NL + "\t\t";
  protected final String TEXT_208 = NL + NL + "\t\t";
  protected final String TEXT_209 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_210 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_211 = ");";
  protected final String TEXT_212 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_213 = " = ";
  protected final String TEXT_214 = "; ";
  protected final String TEXT_215 = NL + NL + "\t\tString dbPwd_";
  protected final String TEXT_216 = " = decryptedPassword_";
  protected final String TEXT_217 = ";" + NL;
  protected final String TEXT_218 = NL + "            String username_";
  protected final String TEXT_219 = " = ";
  protected final String TEXT_220 = ";" + NL + "            if(username_";
  protected final String TEXT_221 = "!=null && !\"\".equals(username_";
  protected final String TEXT_222 = ".trim())) {" + NL + "                System.setProperty(\"HADOOP_USER_NAME\",username_";
  protected final String TEXT_223 = ");" + NL + "            }";
  protected final String TEXT_224 = NL + "        globalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_225 = "\", System.getProperty(\"HADOOP_USER_NAME\"));";
  protected final String TEXT_226 = NL + "\t\t\tSystem.setProperty(\"hive.metastore.local\", \"false\");" + NL + "\t\t\tSystem.setProperty(\"hive.metastore.uris\", \"thrift://\" + ";
  protected final String TEXT_227 = " + \":\" + ";
  protected final String TEXT_228 = ");" + NL + "\t\t\tSystem.setProperty(\"hive.metastore.execute.setugi\", \"true\");" + NL + "\t\t\tString url_";
  protected final String TEXT_229 = " = \"jdbc:";
  protected final String TEXT_230 = "://\";";
  protected final String TEXT_231 = NL + "\t\t\t\tif(dbUser_";
  protected final String TEXT_232 = "!=null && !\"\".equals(dbUser_";
  protected final String TEXT_233 = ".trim())) {" + NL + "\t\t\t\t\tSystem.setProperty(\"HADOOP_USER_NAME\",dbUser_";
  protected final String TEXT_234 = ");" + NL + "\t\t\t\t\t//make relative file path work for hive" + NL + "\t\t\t\t\tglobalMap.put(\"current_client_user_name\", System.getProperty(\"user.name\"));" + NL + "\t\t\t\t\tSystem.setProperty(\"user.name\",dbUser_";
  protected final String TEXT_235 = ");" + NL + "\t\t\t\t}";
  protected final String TEXT_236 = NL + "                        System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                        System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                        System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_237 = ");" + NL + "                        System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_238 = ");";
  protected final String TEXT_239 = NL + "\t\t\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_240 = ", ";
  protected final String TEXT_241 = ");";
  protected final String TEXT_242 = NL + "\t\t\t\t\t\t\t\tString decryptedSslStorePassword_";
  protected final String TEXT_243 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_244 = ");";
  protected final String TEXT_245 = NL + "\t\t\t\t\t\t\t\tString decryptedSslStorePassword_";
  protected final String TEXT_246 = " = ";
  protected final String TEXT_247 = ";";
  protected final String TEXT_248 = NL + "\t\t\t\t\t\t\tString url_";
  protected final String TEXT_249 = " = \"jdbc:";
  protected final String TEXT_250 = "://\" + ";
  protected final String TEXT_251 = " + \":\" + ";
  protected final String TEXT_252 = " + \"/\" + ";
  protected final String TEXT_253 = " + \";principal=\" + ";
  protected final String TEXT_254 = "+\";ssl=true\" +\";sslTrustStore=\" + ";
  protected final String TEXT_255 = " + \";trustStorePassword=\" + decryptedSslStorePassword_";
  protected final String TEXT_256 = ";";
  protected final String TEXT_257 = NL + "\t\t\t\t\t\t\tString url_";
  protected final String TEXT_258 = " = \"jdbc:";
  protected final String TEXT_259 = "://\" + ";
  protected final String TEXT_260 = " + \":\" + ";
  protected final String TEXT_261 = " + \"/\" + ";
  protected final String TEXT_262 = " + \";principal=\" + ";
  protected final String TEXT_263 = "+\";sasl.qop=auth-conf\";";
  protected final String TEXT_264 = NL + "\t\t\t\t\t\tString url_";
  protected final String TEXT_265 = " = \"jdbc:";
  protected final String TEXT_266 = "://\" + ";
  protected final String TEXT_267 = " + \":\" + ";
  protected final String TEXT_268 = " + \"/\" + ";
  protected final String TEXT_269 = " + \";principal=\" + ";
  protected final String TEXT_270 = ";";
  protected final String TEXT_271 = NL + "                        com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_272 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "                        maprLogin_";
  protected final String TEXT_273 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_274 = ", ";
  protected final String TEXT_275 = ");";
  protected final String TEXT_276 = NL + "                        System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                        System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                        System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_277 = ");" + NL + "                        com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_278 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_279 = NL + "                            System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_280 = ");";
  protected final String TEXT_281 = NL + "                            maprLogin_";
  protected final String TEXT_282 = ".setCheckUGI(false);";
  protected final String TEXT_283 = NL + "                            String decryptedMaprPassword_";
  protected final String TEXT_284 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_285 = ");";
  protected final String TEXT_286 = NL + "                            String decryptedMaprPassword_";
  protected final String TEXT_287 = " = ";
  protected final String TEXT_288 = ";";
  protected final String TEXT_289 = NL + "                \t\t\t\tmaprLogin_";
  protected final String TEXT_290 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_291 = ", ";
  protected final String TEXT_292 = ", decryptedMaprPassword_";
  protected final String TEXT_293 = ", ";
  protected final String TEXT_294 = ", \"\");" + NL + "                \t\t\t\t";
  protected final String TEXT_295 = NL + "                \t\t\t\tmaprLogin_";
  protected final String TEXT_296 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_297 = ", ";
  protected final String TEXT_298 = ", decryptedMaprPassword_";
  protected final String TEXT_299 = ", ";
  protected final String TEXT_300 = ");" + NL + "                \t\t\t\t";
  protected final String TEXT_301 = NL + "\t\t\t\t\t\t\tString decryptedSslStorePassword_";
  protected final String TEXT_302 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_303 = ");";
  protected final String TEXT_304 = NL + "\t\t\t\t\t\t\tString decryptedSslStorePassword_";
  protected final String TEXT_305 = " = ";
  protected final String TEXT_306 = ";";
  protected final String TEXT_307 = NL + "\t\t\t\t\t\tString url_";
  protected final String TEXT_308 = " = \"jdbc:";
  protected final String TEXT_309 = "://\" + ";
  protected final String TEXT_310 = " + \":\" + ";
  protected final String TEXT_311 = " + \"/\" + ";
  protected final String TEXT_312 = "+ \";ssl=true\" +\";sslTrustStore=\" + ";
  protected final String TEXT_313 = " + \";trustStorePassword=\" + decryptedSslStorePassword_";
  protected final String TEXT_314 = ";";
  protected final String TEXT_315 = NL + "\t\t\t\t\t\tString url_";
  protected final String TEXT_316 = " = \"jdbc:";
  protected final String TEXT_317 = "://\" + ";
  protected final String TEXT_318 = " + \":\" + ";
  protected final String TEXT_319 = " + \"/\" + ";
  protected final String TEXT_320 = ";";
  protected final String TEXT_321 = NL + "\t\t\t\tString additionalJdbcSettings_";
  protected final String TEXT_322 = " = ";
  protected final String TEXT_323 = ";" + NL + "\t\t\t\tif(!\"\".equals(additionalJdbcSettings_";
  protected final String TEXT_324 = ".trim())) {" + NL + "\t\t\t\t\tif(!additionalJdbcSettings_";
  protected final String TEXT_325 = ".startsWith(\";\")) {" + NL + "\t\t\t\t\t\tadditionalJdbcSettings_";
  protected final String TEXT_326 = " = \";\" + additionalJdbcSettings_";
  protected final String TEXT_327 = ";" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\turl_";
  protected final String TEXT_328 = " += additionalJdbcSettings_";
  protected final String TEXT_329 = ";" + NL + "\t\t\t\t}";
  protected final String TEXT_330 = NL + "\t\tString driverClass_";
  protected final String TEXT_331 = " = \"";
  protected final String TEXT_332 = "\";" + NL + "\t\tjava.lang.Class.forName(driverClass_";
  protected final String TEXT_333 = ");" + NL + "\t\t";
  protected final String TEXT_334 = NL + "\t\t";
  protected final String TEXT_335 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_336 = " - Connection attempt to '\" + url_";
  protected final String TEXT_337 = " + \"' with the username '\" + dbUser_";
  protected final String TEXT_338 = " + \"'.\");" + NL + "\t\t";
  protected final String TEXT_339 = NL + "\t\t\tconn_";
  protected final String TEXT_340 = " = java.sql.DriverManager.getConnection(url_";
  protected final String TEXT_341 = ");";
  protected final String TEXT_342 = NL + "\t\t\tconn_";
  protected final String TEXT_343 = " = java.sql.DriverManager.getConnection(url_";
  protected final String TEXT_344 = ", dbUser_";
  protected final String TEXT_345 = ", dbPwd_";
  protected final String TEXT_346 = ");";
  protected final String TEXT_347 = NL + "\t\t";
  protected final String TEXT_348 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_349 = " - Connection to '\" + url_";
  protected final String TEXT_350 = " + \"' has succeeded.\");" + NL + "\t\t";
  protected final String TEXT_351 = NL + NL + "\t\tjava.sql.Statement init_";
  protected final String TEXT_352 = " = conn_";
  protected final String TEXT_353 = ".createStatement();";
  protected final String TEXT_354 = NL + "        \tinit_";
  protected final String TEXT_355 = ".execute(\"SET mapred.job.map.memory.mb=\" + ";
  protected final String TEXT_356 = ");" + NL + "\t    \tinit_";
  protected final String TEXT_357 = ".execute(\"SET mapred.job.reduce.memory.mb=\" + ";
  protected final String TEXT_358 = ");";
  protected final String TEXT_359 = NL + "\t\tinit_";
  protected final String TEXT_360 = ".execute(\"SET dfs.namenode.kerberos.principal=\" + ";
  protected final String TEXT_361 = ");";
  protected final String TEXT_362 = NL + "\t\t\tinit_";
  protected final String TEXT_363 = ".execute(\"SET mapreduce.jobtracker.kerberos.principal=\" + ";
  protected final String TEXT_364 = ");";
  protected final String TEXT_365 = NL + "\t\t\tinit_";
  protected final String TEXT_366 = ".execute(\"SET yarn.resourcemanager.principal=\" + ";
  protected final String TEXT_367 = ");";
  protected final String TEXT_368 = NL + "        \t\tinit_";
  protected final String TEXT_369 = ".execute(\"SET mapreduce.framework.name=yarn\");" + NL + "        \t\tinit_";
  protected final String TEXT_370 = ".execute(\"SET yarn.resourcemanager.address=\" + ";
  protected final String TEXT_371 = ");";
  protected final String TEXT_372 = NL + "\t\t\t\tinit_";
  protected final String TEXT_373 = ".execute(\"SET mapreduce.jobhistory.address=\" + ";
  protected final String TEXT_374 = ");" + NL + "    \t\t\t";
  protected final String TEXT_375 = NL + "\t\t\t\tinit_";
  protected final String TEXT_376 = ".execute(\"SET yarn.resourcemanager.scheduler.address=\" + ";
  protected final String TEXT_377 = ");";
  protected final String TEXT_378 = NL + "                init_";
  protected final String TEXT_379 = ".execute(\"SET dfs.client.use.datanode.hostname=true\");";
  protected final String TEXT_380 = NL + "\t\t\t\tinit_";
  protected final String TEXT_381 = ".execute(\"SET fs.default.name=\" + ";
  protected final String TEXT_382 = ");";
  protected final String TEXT_383 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_384 = ".execute(\"SET mapreduce.app-submission.cross-platform=true\");";
  protected final String TEXT_385 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_386 = ".execute(\"SET mapreduce.job.map.output.collector.class=org.apache.hadoop.mapred.MapRFsOutputBuffer\");" + NL + "\t\t\t\t\tinit_";
  protected final String TEXT_387 = ".execute(\"SET mapreduce.job.reduce.shuffle.consumer.plugin.class=org.apache.hadoop.mapreduce.task.reduce.DirectShuffle\");";
  protected final String TEXT_388 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_389 = ".execute(\"SET mapreduce.application.classpath=";
  protected final String TEXT_390 = "\");";
  protected final String TEXT_391 = NL + "\t\t\t\tinit_";
  protected final String TEXT_392 = ".execute(\"SET yarn.application.classpath=";
  protected final String TEXT_393 = "\");";
  protected final String TEXT_394 = NL + "    \t\t\tinit_";
  protected final String TEXT_395 = ".execute(\"SET mapreduce.map.memory.mb=\" + ";
  protected final String TEXT_396 = ");" + NL + "    \t\t\tinit_";
  protected final String TEXT_397 = ".execute(\"SET mapreduce.reduce.memory.mb=\" + ";
  protected final String TEXT_398 = ");" + NL + "    \t\t\tinit_";
  protected final String TEXT_399 = ".execute(\"SET yarn.app.mapreduce.am.resource.mb=\" + ";
  protected final String TEXT_400 = ");";
  protected final String TEXT_401 = NL + "\t\t\t\tinit_";
  protected final String TEXT_402 = ".execute(\"SET \"+";
  protected final String TEXT_403 = "+\"=\"+";
  protected final String TEXT_404 = ");";
  protected final String TEXT_405 = NL + NL + "\t\t";
  protected final String TEXT_406 = NL + "        \t";
  protected final String TEXT_407 = NL;
  protected final String TEXT_408 = NL + "    \t\tinit_";
  protected final String TEXT_409 = ".execute(\"SET hive.execution.engine=tez\");";
  protected final String TEXT_410 = NL + "                        System.err.println(\"Please set the path of Tez lib in 'Tez lib path'!\");";
  protected final String TEXT_411 = NL;
  protected final String TEXT_412 = NL + "        String username_";
  protected final String TEXT_413 = " = (";
  protected final String TEXT_414 = " != null && !\"\".equals(";
  protected final String TEXT_415 = ")) ? ";
  protected final String TEXT_416 = " : System.getProperty(\"user.name\");;" + NL + "        org.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_417 = " = null;";
  protected final String TEXT_418 = NL + "        org.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_419 = " = new org.apache.hadoop.conf.Configuration(); " + NL + "        conf_";
  protected final String TEXT_420 = ".set(\"";
  protected final String TEXT_421 = "\", ";
  protected final String TEXT_422 = ");" + NL + "        ";
  protected final String TEXT_423 = NL + "            conf_";
  protected final String TEXT_424 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_425 = NL + "        \t\tconf_";
  protected final String TEXT_426 = ".set(";
  protected final String TEXT_427 = " ,";
  protected final String TEXT_428 = ");" + NL + "        \t";
  protected final String TEXT_429 = NL + "            username_";
  protected final String TEXT_430 = " = null;" + NL + "    \t\tconf_";
  protected final String TEXT_431 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_432 = ");" + NL + "    \t\t";
  protected final String TEXT_433 = NL + "    \t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_434 = ", ";
  protected final String TEXT_435 = ");" + NL + "    \t\t";
  protected final String TEXT_436 = NL + "\t\t\tconf_";
  protected final String TEXT_437 = ".set(\"hadoop.job.ugi\",username_";
  protected final String TEXT_438 = "+\",\"+username_";
  protected final String TEXT_439 = ");" + NL + "        \tfs_";
  protected final String TEXT_440 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_441 = ");";
  protected final String TEXT_442 = NL + "        \t" + NL + "        \tif(username_";
  protected final String TEXT_443 = " == null || \"\".equals(username_";
  protected final String TEXT_444 = ")){" + NL + "        \t\tfs_";
  protected final String TEXT_445 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_446 = ");" + NL + "        \t}else{" + NL + "        \t\tfs_";
  protected final String TEXT_447 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_448 = ".get(\"";
  protected final String TEXT_449 = "\")),conf_";
  protected final String TEXT_450 = ",username_";
  protected final String TEXT_451 = ");" + NL + "        \t}\t";
  protected final String TEXT_452 = NL + "                    String hdfsUserName_";
  protected final String TEXT_453 = " = (";
  protected final String TEXT_454 = " != null && !\"\".equals(";
  protected final String TEXT_455 = ")) ? ";
  protected final String TEXT_456 = " : System.getProperty(\"user.name\");" + NL + "                    String tezLibPath_";
  protected final String TEXT_457 = " = \"/tmp/\" + hdfsUserName_";
  protected final String TEXT_458 = " + \"/talend_tez_libs/";
  protected final String TEXT_459 = "\";";
  protected final String TEXT_460 = NL + "                    String tezLibPath_";
  protected final String TEXT_461 = " = ";
  protected final String TEXT_462 = ";";
  protected final String TEXT_463 = NL + "                fs_";
  protected final String TEXT_464 = ".mkdirs(new org.apache.hadoop.fs.Path(tezLibPath_";
  protected final String TEXT_465 = "));";
  protected final String TEXT_466 = NL + "                String[] classPaths_";
  protected final String TEXT_467 = " = System.getProperty(\"java.class.path\").split(";
  protected final String TEXT_468 = "String.valueOf(globalMap.get(\"current_client_path_separator\"))";
  protected final String TEXT_469 = "System.getProperty(\"path.separator\")";
  protected final String TEXT_470 = ");" + NL + "                String tezLibLocalPath_";
  protected final String TEXT_471 = " = null;" + NL + "                for(String classPath_";
  protected final String TEXT_472 = " : classPaths_";
  protected final String TEXT_473 = "){";
  protected final String TEXT_474 = NL + "                        if(classPath_";
  protected final String TEXT_475 = ".endsWith(\"";
  protected final String TEXT_476 = "\")){" + NL + "                            org.apache.hadoop.fs.Path tezJarPath_";
  protected final String TEXT_477 = " = new org.apache.hadoop.fs.Path(tezLibPath_";
  protected final String TEXT_478 = " + \"/";
  protected final String TEXT_479 = "\");" + NL + "                            if(!fs_";
  protected final String TEXT_480 = ".exists(tezJarPath_";
  protected final String TEXT_481 = ")){" + NL + "                                fs_";
  protected final String TEXT_482 = ".copyFromLocalFile(false, false, new org.apache.hadoop.fs.Path(classPath_";
  protected final String TEXT_483 = "), tezJarPath_";
  protected final String TEXT_484 = ");" + NL + "                            }" + NL + "                        }";
  protected final String TEXT_485 = NL + "                }";
  protected final String TEXT_486 = NL + "                String tezLibPath_";
  protected final String TEXT_487 = " = ";
  protected final String TEXT_488 = ";";
  protected final String TEXT_489 = NL + "\t\t\tStringBuilder script_";
  protected final String TEXT_490 = " = new StringBuilder();" + NL + "\t\t\tString[] tezLibPaths_";
  protected final String TEXT_491 = " = tezLibPath_";
  protected final String TEXT_492 = ".split(\",\");" + NL + "\t\t\tscript_";
  protected final String TEXT_493 = ".append(\"SET tez.lib.uris=\");" + NL + "\t\t\tint tezLibPathCount_";
  protected final String TEXT_494 = " = 1;" + NL + "\t\t\tfor(String tezLibPathKey_";
  protected final String TEXT_495 = " : tezLibPaths_";
  protected final String TEXT_496 = "){" + NL + "\t\t\t\t script_";
  protected final String TEXT_497 = ".append(";
  protected final String TEXT_498 = " + \"/\" + tezLibPathKey_";
  protected final String TEXT_499 = ");" + NL + "\t\t\t\t if(tezLibPathCount_";
  protected final String TEXT_500 = " < tezLibPaths_";
  protected final String TEXT_501 = ".length){" + NL + "\t\t\t\t \tscript_";
  protected final String TEXT_502 = ".append(\",\");" + NL + "\t\t\t\t }" + NL + "\t\t\t\t tezLibPathCount_";
  protected final String TEXT_503 = "++;" + NL + "\t\t\t}" + NL + "\t\t\tinit_";
  protected final String TEXT_504 = ".execute(script_";
  protected final String TEXT_505 = ".toString());" + NL + "\t\t";
  protected final String TEXT_506 = NL + "            \t";
  protected final String TEXT_507 = NL + "\t\tinit_";
  protected final String TEXT_508 = ".close();" + NL + "" + NL + "    \tString dbname_";
  protected final String TEXT_509 = " = ";
  protected final String TEXT_510 = ";" + NL + "    \tif(dbname_";
  protected final String TEXT_511 = "!=null && !\"\".equals(dbname_";
  protected final String TEXT_512 = ".trim()) && !\"default\".equals(dbname_";
  protected final String TEXT_513 = ".trim())) {" + NL + "        \tjava.sql.Statement goToDatabase_";
  protected final String TEXT_514 = " = conn_";
  protected final String TEXT_515 = ".createStatement();" + NL + "        \tgoToDatabase_";
  protected final String TEXT_516 = ".execute(\"use \" + dbname_";
  protected final String TEXT_517 = ");" + NL + "        \tgoToDatabase_";
  protected final String TEXT_518 = ".close();" + NL + "    \t}" + NL + "" + NL + "\t\t";
  protected final String TEXT_519 = NL + "    \t\tjava.sql.Statement statement_";
  protected final String TEXT_520 = " = conn_";
  protected final String TEXT_521 = ".createStatement();" + NL + "    \t\t";
  protected final String TEXT_522 = NL + "    \t\t\tstatement_";
  protected final String TEXT_523 = ".execute(\"SET hbase.zookeeper.quorum=\"+";
  protected final String TEXT_524 = ");" + NL + "    \t\t";
  protected final String TEXT_525 = NL + NL + "        \t";
  protected final String TEXT_526 = NL + "        \t\tstatement_";
  protected final String TEXT_527 = ".execute(\"SET hbase.zookeeper.property.clientPort=\"+";
  protected final String TEXT_528 = ");" + NL + "        \t";
  protected final String TEXT_529 = NL + NL + "\t\t\t";
  protected final String TEXT_530 = NL + "\t\t\t\tstatement_";
  protected final String TEXT_531 = ".execute(\"SET zookeeper.znode.parent=\"+";
  protected final String TEXT_532 = ");" + NL + "\t\t\t";
  protected final String TEXT_533 = NL + NL + "\t\t\t";
  protected final String TEXT_534 = NL + "\t\t\t\tstatement_";
  protected final String TEXT_535 = ".execute(\"SET hbase.security.authentication=kerberos\");" + NL + "\t\t\t\tstatement_";
  protected final String TEXT_536 = ".execute(\"SET hbase.rpc.engine=org.apache.hadoop.hbase.ipc.SecureRpcEngine\");" + NL + "" + NL + "\t\t\t\t";
  protected final String TEXT_537 = NL + "\t\t\t\t\tstatement_";
  protected final String TEXT_538 = ".execute(\"SET hbase.master.kerberos.principal=\"+";
  protected final String TEXT_539 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_540 = NL + "\t\t\t\t";
  protected final String TEXT_541 = NL + "\t\t\t\t\tstatement_";
  protected final String TEXT_542 = ".execute(\"SET hbase.regionserver.kerberos.principal=\"+";
  protected final String TEXT_543 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_544 = NL + "\t\t\t";
  protected final String TEXT_545 = NL + NL + "        \t";
  protected final String TEXT_546 = NL + "\t\t\t\tstatement_";
  protected final String TEXT_547 = ".execute(\"add jar \"+";
  protected final String TEXT_548 = ");" + NL + "    \t\t";
  protected final String TEXT_549 = NL + "    \t\tstatement_";
  protected final String TEXT_550 = ".close();";
  protected final String TEXT_551 = NL + "        if(true) {" + NL + "            throw new java.lang.UnsupportedOperationException(\"Parquet is only supported if the distribution uses embedded Hive version 0.10 or later.\");" + NL + "        }";
  protected final String TEXT_552 = NL + "            routines.system.GetJarsToRegister getJarsToRegister_";
  protected final String TEXT_553 = " = new routines.system.GetJarsToRegister();";
  protected final String TEXT_554 = NL + "\tclass GetHiveJarsToRegister_";
  protected final String TEXT_555 = " extends routines.system.GetJarsToRegister {" + NL + "\t\t" + NL + "\t\tprivate String uploadJarToHDFS(String jar) throws Exception {" + NL + "\t\t\t";
  protected final String TEXT_556 = NL;
  protected final String TEXT_557 = NL + "        String username_";
  protected final String TEXT_558 = " = (";
  protected final String TEXT_559 = " != null && !\"\".equals(";
  protected final String TEXT_560 = ")) ? ";
  protected final String TEXT_561 = " : System.getProperty(\"user.name\");;" + NL + "        org.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_562 = " = null;";
  protected final String TEXT_563 = NL + "        org.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_564 = " = new org.apache.hadoop.conf.Configuration(); " + NL + "        conf_";
  protected final String TEXT_565 = ".set(\"";
  protected final String TEXT_566 = "\", ";
  protected final String TEXT_567 = ");" + NL + "        ";
  protected final String TEXT_568 = NL + "            conf_";
  protected final String TEXT_569 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_570 = NL + "        \t\tconf_";
  protected final String TEXT_571 = ".set(";
  protected final String TEXT_572 = " ,";
  protected final String TEXT_573 = ");" + NL + "        \t";
  protected final String TEXT_574 = NL + "            username_";
  protected final String TEXT_575 = " = null;" + NL + "    \t\tconf_";
  protected final String TEXT_576 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_577 = ");" + NL + "    \t\t";
  protected final String TEXT_578 = NL + "    \t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_579 = ", ";
  protected final String TEXT_580 = ");" + NL + "    \t\t";
  protected final String TEXT_581 = NL + "\t\t\tconf_";
  protected final String TEXT_582 = ".set(\"hadoop.job.ugi\",username_";
  protected final String TEXT_583 = "+\",\"+username_";
  protected final String TEXT_584 = ");" + NL + "        \tfs_";
  protected final String TEXT_585 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_586 = ");";
  protected final String TEXT_587 = NL + "        \t" + NL + "        \tif(username_";
  protected final String TEXT_588 = " == null || \"\".equals(username_";
  protected final String TEXT_589 = ")){" + NL + "        \t\tfs_";
  protected final String TEXT_590 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_591 = ");" + NL + "        \t}else{" + NL + "        \t\tfs_";
  protected final String TEXT_592 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_593 = ".get(\"";
  protected final String TEXT_594 = "\")),conf_";
  protected final String TEXT_595 = ",username_";
  protected final String TEXT_596 = ");" + NL + "        \t}\t";
  protected final String TEXT_597 = NL + "\t\t\t";
  protected final String TEXT_598 = NL + "\t\t\t" + NL + "\t\t\tString pathUsername_";
  protected final String TEXT_599 = " = username_";
  protected final String TEXT_600 = " == null ? org.apache.hadoop.security.UserGroupInformation.getLoginUser().getShortUserName() : username_";
  protected final String TEXT_601 = ";" + NL + "\t\t\tfs_";
  protected final String TEXT_602 = ".mkdirs(new org.apache.hadoop.fs.Path(\"/user/\" + pathUsername_";
  protected final String TEXT_603 = " + \"/tmp\"), new org.apache.hadoop.fs.permission.FsPermission(org.apache.hadoop.fs.permission.FsAction.ALL, org.apache.hadoop.fs.permission.FsAction.ALL, org.apache.hadoop.fs.permission.FsAction.ALL));" + NL + "\t\t\tfs_";
  protected final String TEXT_604 = ".copyFromLocalFile(false, true, new org.apache.hadoop.fs.Path(jar), new org.apache.hadoop.fs.Path(\"/user/\" + pathUsername_";
  protected final String TEXT_605 = " + \"/tmp\"));" + NL + "\t\t\treturn ";
  protected final String TEXT_606 = " + \"/user/\" + pathUsername_";
  protected final String TEXT_607 = " + \"/tmp/\" + new java.io.File(jar).getName();" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tpublic String replaceJarPaths(String originalClassPathLine) throws Exception {" + NL + "\t\t\tString classPathLine = super.replaceJarPaths(originalClassPathLine);" + NL + "\t\t\tString hdfsPath = uploadJarToHDFS(classPathLine);" + NL + "\t\t\treturn hdfsPath;" + NL + "\t\t}" + NL + "\t}" + NL + "                GetHiveJarsToRegister_";
  protected final String TEXT_608 = " getJarsToRegister_";
  protected final String TEXT_609 = " = new GetHiveJarsToRegister_";
  protected final String TEXT_610 = "();";
  protected final String TEXT_611 = NL + "            java.sql.Statement addJar_";
  protected final String TEXT_612 = " = null;";
  protected final String TEXT_613 = NL + "                        addJar_";
  protected final String TEXT_614 = " = conn_";
  protected final String TEXT_615 = ".createStatement();" + NL + "                        try {" + NL + "                            addJar_";
  protected final String TEXT_616 = ".execute(\"add jar \" + getJarsToRegister_";
  protected final String TEXT_617 = ".replaceJarPaths(\"";
  protected final String TEXT_618 = "\"));" + NL + "                        } catch (Exception e) {" + NL + "                            e.printStackTrace();" + NL + "                        } finally {" + NL + "                            addJar_";
  protected final String TEXT_619 = ".close();" + NL + "                        }";
  protected final String TEXT_620 = NL + "                            bw_";
  protected final String TEXT_621 = ".write(\"ADD JAR \" + wasbPath_";
  protected final String TEXT_622 = " + new java.io.File(getJarsToRegister_";
  protected final String TEXT_623 = ".replaceJarPaths(\"";
  protected final String TEXT_624 = "\")).getName() + \";\");" + NL + "                            libjars_";
  protected final String TEXT_625 = ".append(getJarsToRegister_";
  protected final String TEXT_626 = ".replaceJarPaths(\"";
  protected final String TEXT_627 = "\") + \",\");";
  protected final String TEXT_628 = NL + "    java.sql.Statement stmt_";
  protected final String TEXT_629 = " = conn_";
  protected final String TEXT_630 = ".createStatement();";
  protected final String TEXT_631 = NL + "    String query_";
  protected final String TEXT_632 = " = \"\";" + NL + "    String tableName_";
  protected final String TEXT_633 = " = ";
  protected final String TEXT_634 = ";";
  protected final String TEXT_635 = NL + "    String likeTableName_";
  protected final String TEXT_636 = " = ";
  protected final String TEXT_637 = ";";
  protected final String TEXT_638 = NL + "        String location_";
  protected final String TEXT_639 = " = ";
  protected final String TEXT_640 = ";";
  protected final String TEXT_641 = NL + "    String querySQL_";
  protected final String TEXT_642 = " = \"";
  protected final String TEXT_643 = "\";";
  protected final String TEXT_644 = NL + "    try {" + NL + "        stmt_";
  protected final String TEXT_645 = ".execute(querySQL_";
  protected final String TEXT_646 = ");" + NL + "    } catch(java.sql.SQLException e_";
  protected final String TEXT_647 = ") {";
  protected final String TEXT_648 = NL + "            throw(e_";
  protected final String TEXT_649 = ");";
  protected final String TEXT_650 = NL + "            System.err.println(e_";
  protected final String TEXT_651 = ".getMessage());";
  protected final String TEXT_652 = NL + "    }" + NL + "    stmt_";
  protected final String TEXT_653 = ".close();" + NL + "    globalMap.put(\"";
  protected final String TEXT_654 = "_QUERY\", querySQL_";
  protected final String TEXT_655 = ");" + NL + "" + NL + "    String currentClientPathSeparator_";
  protected final String TEXT_656 = " = (String)globalMap.get(\"current_client_path_separator\");" + NL + "    if(currentClientPathSeparator_";
  protected final String TEXT_657 = "!=null) {" + NL + "        System.setProperty(\"path.separator\", currentClientPathSeparator_";
  protected final String TEXT_658 = ");" + NL + "        globalMap.put(\"current_client_path_separator\", null);" + NL + "    }";
  protected final String TEXT_659 = NL + "        conn_";
  protected final String TEXT_660 = ".close();";
  protected final String TEXT_661 = NL + "            bw_";
  protected final String TEXT_662 = ".write(querySQL_";
  protected final String TEXT_663 = " + \";\");" + NL + "            globalMap.put(\"";
  protected final String TEXT_664 = "_QUERY\", querySQL_";
  protected final String TEXT_665 = ");" + NL + "" + NL + "            bw_";
  protected final String TEXT_666 = ".close();" + NL + "" + NL + "            if(libjars_";
  protected final String TEXT_667 = ".length() > 0) {" + NL + "                instance_";
  protected final String TEXT_668 = ".setLibJars(libjars_";
  protected final String TEXT_669 = ".toString().substring(0, libjars_";
  protected final String TEXT_670 = ".length()-1));" + NL + "            }" + NL + "            instance_";
  protected final String TEXT_671 = ".callWS(instance_";
  protected final String TEXT_672 = ".sendFiles());" + NL + "            int exitCode_";
  protected final String TEXT_673 = " = instance_";
  protected final String TEXT_674 = ".execute();" + NL + "            if(exitCode_";
  protected final String TEXT_675 = " > 0) {" + NL;
  protected final String TEXT_676 = NL + "                    throw new Exception(\"The Hive job failed. Please read the logs for more details\");";
  protected final String TEXT_677 = NL + "                    System.err.println(\"The Hive job failed. Please read the logs for more details\");";
  protected final String TEXT_678 = NL + "                        log.error(\"";
  protected final String TEXT_679 = " - The Hive job failed. Please read the logs for more details\");";
  protected final String TEXT_680 = NL + "            }";
  protected final String TEXT_681 = NL + "\t\t\t\t\tString comment_";
  protected final String TEXT_682 = "_";
  protected final String TEXT_683 = "_";
  protected final String TEXT_684 = " = ";
  protected final String TEXT_685 = ";";
  protected final String TEXT_686 = NL + "\t\t\t\t\tString comment_";
  protected final String TEXT_687 = "_";
  protected final String TEXT_688 = "_";
  protected final String TEXT_689 = " = ";
  protected final String TEXT_690 = ";";
  protected final String TEXT_691 = NL + "\t\t\t\t\tString key_";
  protected final String TEXT_692 = "_";
  protected final String TEXT_693 = "_";
  protected final String TEXT_694 = " = ";
  protected final String TEXT_695 = ";" + NL + "\t\t\t\t\tString value_";
  protected final String TEXT_696 = "_";
  protected final String TEXT_697 = "_";
  protected final String TEXT_698 = " = ";
  protected final String TEXT_699 = ";";
  protected final String TEXT_700 = NL + "    String tableComment_";
  protected final String TEXT_701 = " = ";
  protected final String TEXT_702 = ";";
  protected final String TEXT_703 = NL + "    String clustededOrSkewed_";
  protected final String TEXT_704 = " = ";
  protected final String TEXT_705 = ";";
  protected final String TEXT_706 = NL + "            String fieldChar_";
  protected final String TEXT_707 = " = ";
  protected final String TEXT_708 = ";";
  protected final String TEXT_709 = NL + "                String escapeChar_";
  protected final String TEXT_710 = " = ";
  protected final String TEXT_711 = ";";
  protected final String TEXT_712 = NL + "            String collectionChar_";
  protected final String TEXT_713 = " = ";
  protected final String TEXT_714 = ";";
  protected final String TEXT_715 = NL + "            String mapChar_";
  protected final String TEXT_716 = " = ";
  protected final String TEXT_717 = ";";
  protected final String TEXT_718 = NL + "            String lineChar_";
  protected final String TEXT_719 = " = ";
  protected final String TEXT_720 = ";";
  protected final String TEXT_721 = NL + "        String serdeName_";
  protected final String TEXT_722 = " = ";
  protected final String TEXT_723 = ";";
  protected final String TEXT_724 = NL + "        String inputClass_";
  protected final String TEXT_725 = " = ";
  protected final String TEXT_726 = ";" + NL + "        String outputClass_";
  protected final String TEXT_727 = " = ";
  protected final String TEXT_728 = ";";
  protected final String TEXT_729 = NL + "    String storageClass_";
  protected final String TEXT_730 = " = ";
  protected final String TEXT_731 = ";";
  protected final String TEXT_732 = NL + "            String decryptedS3Password_";
  protected final String TEXT_733 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_734 = ");";
  protected final String TEXT_735 = NL + "            String decryptedS3Password_";
  protected final String TEXT_736 = " = ";
  protected final String TEXT_737 = ";";
  protected final String TEXT_738 = NL + "        String location_";
  protected final String TEXT_739 = " = \"s3n://\" + ";
  protected final String TEXT_740 = " +\":\" + decryptedS3Password_";
  protected final String TEXT_741 = " + \"@\" + ";
  protected final String TEXT_742 = ";";
  protected final String TEXT_743 = NL + "    String location_";
  protected final String TEXT_744 = " = ";
  protected final String TEXT_745 = ";";
  protected final String TEXT_746 = NL + "    String select_";
  protected final String TEXT_747 = " = ";
  protected final String TEXT_748 = ";";
  protected final String TEXT_749 = NL + "String querySQL_";
  protected final String TEXT_750 = " = \"";
  protected final String TEXT_751 = "\";";
  protected final String TEXT_752 = NL + "try {" + NL + "    stmt_";
  protected final String TEXT_753 = ".execute(querySQL_";
  protected final String TEXT_754 = ");" + NL + "} catch(java.sql.SQLException e_";
  protected final String TEXT_755 = ") {";
  protected final String TEXT_756 = NL + "        throw(e_";
  protected final String TEXT_757 = ");";
  protected final String TEXT_758 = NL + "        System.err.println(e_";
  protected final String TEXT_759 = ".getMessage());";
  protected final String TEXT_760 = NL + "        }" + NL + "stmt_";
  protected final String TEXT_761 = ".close();";
  protected final String TEXT_762 = NL + "    conn_";
  protected final String TEXT_763 = ".close();";
  protected final String TEXT_764 = NL + "globalMap.put(\"";
  protected final String TEXT_765 = "_QUERY\", querySQL_";
  protected final String TEXT_766 = ");" + NL + "" + NL + "String currentClientPathSeparator_";
  protected final String TEXT_767 = " = (String)globalMap.get(\"current_client_path_separator\");" + NL + "if(currentClientPathSeparator_";
  protected final String TEXT_768 = "!=null) {" + NL + "    System.setProperty(\"path.separator\", currentClientPathSeparator_";
  protected final String TEXT_769 = ");" + NL + "    globalMap.put(\"current_client_path_separator\", null);" + NL + "}" + NL + "" + NL + "String currentClientUsername_";
  protected final String TEXT_770 = " = (String)globalMap.get(\"current_client_user_name\");" + NL + "if(currentClientUsername_";
  protected final String TEXT_771 = "!=null) {" + NL + "    System.setProperty(\"user.name\", currentClientUsername_";
  protected final String TEXT_772 = ");" + NL + "    globalMap.put(\"current_client_user_name\", null);" + NL + "}" + NL + "" + NL + "String originalHadoopUsername_";
  protected final String TEXT_773 = " = (String)globalMap.get(\"HADOOP_USER_NAME_";
  protected final String TEXT_774 = "\");" + NL + "if(originalHadoopUsername_";
  protected final String TEXT_775 = "!=null) {" + NL + "    System.setProperty(\"HADOOP_USER_NAME\", originalHadoopUsername_";
  protected final String TEXT_776 = ");" + NL + "    globalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_777 = "\", null);" + NL + "} else {" + NL + "    System.clearProperty(\"HADOOP_USER_NAME\");" + NL + "}";
  protected final String TEXT_778 = NL + "        bw_";
  protected final String TEXT_779 = ".write(querySQL_";
  protected final String TEXT_780 = " + \";\");" + NL + "        globalMap.put(\"";
  protected final String TEXT_781 = "_QUERY\", querySQL_";
  protected final String TEXT_782 = ");" + NL + "" + NL + "        bw_";
  protected final String TEXT_783 = ".close();" + NL + "" + NL + "        if(libjars_";
  protected final String TEXT_784 = ".length() > 0) {" + NL + "            instance_";
  protected final String TEXT_785 = ".setLibJars(libjars_";
  protected final String TEXT_786 = ".toString().substring(0, libjars_";
  protected final String TEXT_787 = ".length()-1));" + NL + "        }" + NL + "        instance_";
  protected final String TEXT_788 = ".callWS(instance_";
  protected final String TEXT_789 = ".sendFiles());" + NL + "        int exitCode_";
  protected final String TEXT_790 = " = instance_";
  protected final String TEXT_791 = ".execute();" + NL + "        if(exitCode_";
  protected final String TEXT_792 = " > 0) {" + NL;
  protected final String TEXT_793 = NL + "                throw new Exception(\"The Hive job failed. Please read the logs for more details\");";
  protected final String TEXT_794 = NL + "                System.err.println(\"The Hive job failed. Please read the logs for more details\");";
  protected final String TEXT_795 = NL + "                    log.error(\"";
  protected final String TEXT_796 = " - The Hive job failed. Please read the logs for more details\");";
  protected final String TEXT_797 = NL + "        }";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
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
			
    stringBuffer.append(TEXT_1);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_2);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_6);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_8);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_9);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_12);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_14);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_17);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_19);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_20);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_22);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_23);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_25);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_26);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    
			}
		}
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    
	class DefaultLog4jCodeGenerateUtil extends DefaultLog4jFileUtil{

 		String connection = "";
 		boolean hasInit = false;
 		String dataAction ;
 		String dataOperationPrefix;
		String useBatchSize;
		String batchSize;
		String dbSchema;
 		boolean logCommitCounter = false;

		public DefaultLog4jCodeGenerateUtil(){
		}

		public DefaultLog4jCodeGenerateUtil(INode node) {
			super(node);
	    	init();
		}

	    public void beforeComponentProcess(INode node){
	    	this.node = node;
	    	init();
	    }

		private void init() {
			if(hasInit){
				return;
			}
 			this.cid = node.getUniqueName();
			this.isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
			String useConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");
			if(useConn == null || "".equals(useConn) || "true".equals(useConn)){
				connection = ElementParameterParser.getValue(node,"__CONNECTION__");
				if(!"".equals(connection)){
					connection = "'" + connection+"' ";
				}
			}
			//for output
			dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");
			if(dataAction != null && !("").equals(dataAction)){
				logCommitCounter=true;
			}
			useBatchSize = ElementParameterParser.getValue(node, "__USE_BATCH_SIZE__");
			batchSize =ElementParameterParser.getValue(node, "__BATCH_SIZE__");
			hasInit = true;
		}

		public void debugDriverClassName() {
			logInfo(node,"debug",cid+" - Driver ClassName: \"+driverClass_"+cid+"+\".");
		}

		public void debugConnectionParams(INode node) {
			beforeComponentProcess(node);
			debugDriverClassName();
		}

		public void useExistConnection(INode node){
			beforeComponentProcess(node);
			if(isLog4jEnabled) {
			
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    if (cid.startsWith("tImpala") || cid.startsWith("tHive")) {
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(connection );
    stringBuffer.append(TEXT_34);
    } else {
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    }
    stringBuffer.append(TEXT_39);
    
			}
		}

		public void connect(INode node){
			beforeComponentProcess(node);
			connect();
		}

		public void connect(){
			connect_begin();
			
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    
			connect_end();
		}

		public void connect_begin(){
			logInfo(node,"debug",cid+" - Connection attempt to '\" + url_"+cid+" + \"' with the username '\" + dbUser_"+cid+" + \"'.");
		}

		public void connect_begin_noUser(){
			logInfo(node,"debug",cid+" - Connection attempt to '\" + url_"+cid+" + \"'.");
		}

		public void connect_end(){
			logInfo(node,"debug",cid+" - Connection to '\" + url_"+cid+" + \"' has succeeded.");
		}

		public void rollback(INode node){
			beforeComponentProcess(node);
			logInfo(node,"debug",cid+" - Connection "+connection+"starting to rollback.");
			
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
			logInfo(node,"debug",cid+" - Connection "+connection+"rollback has succeeded.");
		}

		public void commit(INode node){
			beforeComponentProcess(node);
			commit();
		}

		private void commit(){
			commit_begin();
			
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    
			commit_end();
		}

		private void commit_begin(){
			if(logCommitCounter){
				logInfo(node,"debug",cid+" - Connection "+connection+"starting to commit \" + commitCounter_"+cid+"+ \" records.");
			}else{
				logInfo(node,"debug",cid+" - Connection "+connection+"starting to commit.");
			}
		}
		private void commit_end(){
			logInfo(node,"debug",cid+" - Connection "+connection+"commit has succeeded.");
		}

		public void close(INode node){
			beforeComponentProcess(node);
			close();
		}

		private void close(){
			close_begin();
			
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    
			close_end();
		}

		public void close_begin(){
			logInfo(node,"debug",cid+" - Closing the connection "+connection+"to the database.");
		}
		public void close_end(){
			logInfo(node,"debug",cid+" - Connection "+connection+"to the database closed.");
		}

		public void autoCommit(INode node,boolean autoCommit){
			beforeComponentProcess(node);
			logInfo(node,"debug",cid+" - Connection is set auto commit to '"+autoCommit+"'.");
			
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(autoCommit);
    stringBuffer.append(TEXT_53);
    
		}

		public void query(INode node){
			beforeComponentProcess(node);
			//for input
	 		String dbquery= ElementParameterParser.getValue(node, "__QUERY__");
			dbquery = dbquery.replaceAll("\n"," ");
			dbquery = dbquery.replaceAll("\r"," ");
			logInfo(node,"debug",cid+" - Executing the query: '\" + "+dbquery +" + \"'.");
		}

		public void retrieveRecordsCount(INode node){
			beforeComponentProcess(node);
			logInfo(node,"debug",cid+" - Retrieved records count: \"+nb_line_"+cid+" + \" .");
		}

		public void logError(INode node,String logLevel,String exception){
	    	beforeComponentProcess(node);
	    	if(isLog4jEnabled){
	    	
    stringBuffer.append(TEXT_54);
    stringBuffer.append(logLevel);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(exception);
    stringBuffer.append(TEXT_57);
    
			}
	    }

	    public void logError(INode node,String logLevel){
	    	logError(node,logLevel,"e");
	    }
	    
	    public void logInfo(INode node,String logLevel,String message){
	    	beforeComponentProcess(node);
	    	if(isLog4jEnabled){
	    	
    stringBuffer.append(TEXT_58);
    stringBuffer.append(logLevel);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(message);
    stringBuffer.append(TEXT_60);
    
			}
	    }
		/**
		*batchType :
		*			1: do not get return value of executeBatch();
		*			2: get return value of executeBatch();
		*
		*/
		public void executeBatch(INode node,int batchType){
			beforeComponentProcess(node);
			boolean logBatch = ("true").equals(useBatchSize) && !("").equals(batchSize) && !("0").equals(batchSize);
			if(logBatch){
				logInfo(node,"debug",cid+" - Executing the "+dataAction+" batch.");
			}
			if(batchType==1){
			
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_62);
    
			}else if(batchType==2){
				boolean isMysqlBatchInsert = false;
				if ((node.getUniqueName().contains("tMysqlOutput")||node.getUniqueName().contains("tAmazonMysqlOutput")) && ("INSERT").equals(dataAction)) {
					isMysqlBatchInsert = true;
				}
			
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(isMysqlBatchInsert? "1" : "countEach_"+cid );
    stringBuffer.append(TEXT_69);
    
			}
			if(logBatch){
				logInfo(node,"debug",cid+" - The "+dataAction+" batch execution has succeeded.");
			}
		}
	}

	DefaultLog4jCodeGenerateUtil log4jCodeGenerateUtil = new DefaultLog4jCodeGenerateUtil();

    stringBuffer.append(TEXT_70);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    final String cid = node.getUniqueName();
    String processId = node.getProcess().getId();

    String dbhost = ElementParameterParser.getValue(node, "__HOST__");
    String dbport = ElementParameterParser.getValue(node, "__PORT__");
    String dbname= ElementParameterParser.getValue(node, "__DBNAME__");
    String dbuser= ElementParameterParser.getValue(node, "__USER__");


    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
    String theDistribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
    String theVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");

    if("true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"))) {
        String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
        for (INode pNode : node.getProcess().getNodesOfType("tHiveConnection")) {
            if(connection!=null && connection.equals(pNode.getUniqueName())) {
                theDistribution = ElementParameterParser.getValue(pNode, "__DISTRIBUTION__");
                theVersion = ElementParameterParser.getValue(pNode, "__HIVE_VERSION__");
            }
        }
    }

    org.talend.hadoop.distribution.component.HiveComponent hiveDistrib = null;
    try {
        hiveDistrib = (org.talend.hadoop.distribution.component.HiveComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(theDistribution, theVersion);
    } catch (java.lang.Exception e) {
        e.printStackTrace();
        return "";
    }
    boolean isCustom = hiveDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

    if(hiveDistrib.isExecutedThroughWebHCat()) {

    
	String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");

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
    
	if("false".equals(useExistingConn)) {
		String passwordFieldName = "__HDINSIGHT_PASSWORD__";
		if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_80);
    
		} else {

    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_83);
    
		}
			
		passwordFieldName = "__WASB_PASSWORD__";
		if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_86);
    
		} else {

    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_89);
    
		}

    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WASB_USERNAME__"));
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WASB_CONTAINER__"));
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HDINSIGHT_USERNAME__"));
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_USERNAME__"));
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_HOST__"));
    stringBuffer.append(TEXT_103);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_PORT__"));
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(ElementParameterParser.getValue(node, "__STATUSDIR__"));
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(ElementParameterParser.getValue(node, "__REMOTE_FOLDER__"));
    stringBuffer.append(TEXT_108);
    
	} else {
		String azureConnection = ElementParameterParser.getValue(node,"__CONNECTION__");

    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(azureConnection);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(azureConnection);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    
	}

    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    
        if("false".equals(useExistingConn)) { // This variable is declared and initialized in the GetAzureConnection.javajet
            boolean setMemory = "true".equals(ElementParameterParser.getValue(node, "__SET_MEMORY__"));
            if(setMemory) {
                String mapMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_MAP_MEMORY_MB__");
                String reduceMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_REDUCE_MEMORY_MB__");
                String amMemory = ElementParameterParser.getValue(node,"__YARN_APP_MAPREDUCE_AM_RESOURCE_MB__");

    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(amMemory);
    stringBuffer.append(TEXT_129);
    
            }

            List<Map<String, String>> advProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ADVANCED_PROPERTIES__");
            if(advProps!=null) {
                for(Map<String, String> item : advProps){

    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(item.get("PROPERTY"));
    stringBuffer.append(TEXT_132);
    stringBuffer.append(item.get("VALUE"));
    stringBuffer.append(TEXT_133);
    
                }
            }

    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(dbname);
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
    
        }
    } else {

    stringBuffer.append(TEXT_142);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_143);
    
	boolean setTempPath = "true".equals(ElementParameterParser.getValue(node, "__SET_TEMP_PATH__"));
	if(setTempPath) {
		String tempPath = ElementParameterParser.getValue(node, "__TEMP_PATH__");

    stringBuffer.append(TEXT_144);
    stringBuffer.append(tempPath);
    stringBuffer.append(TEXT_145);
    
	}

	String yarnClasspathSeparator = ElementParameterParser.getValue(node, "__CLASSPATH_SEPARATOR__");

    stringBuffer.append(TEXT_146);
    stringBuffer.append(yarnClasspathSeparator);
    stringBuffer.append(TEXT_147);
    

	String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");
	if(("true").equals(useExistingConn)) {
		String connection = ElementParameterParser.getValue(node,"__CONNECTION__");
		String conn = "conn_" + connection;
		String db = "db_" + connection;
		String dbUser = "dbUser_" + connection;
		
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(conn);
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_151);
    stringBuffer.append(db);
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
    stringBuffer.append(dbUser);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_167);
    
	} else {
		String javaDbDriver = "org.apache.hadoop.hive.jdbc.HiveDriver";
		String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");
		String hiveVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");
		String fsDefalutName = "fs.default.name";
		String hiveServer = ElementParameterParser.getValue(node, "__HIVE_SERVER__");

		boolean setMapredJT = "true".equals(ElementParameterParser.getValue(node, "__SET_MAPRED_JT__"));
		boolean setNamenode = "true".equals(ElementParameterParser.getValue(node, "__SET_FS_DEFAULT_NAME__"));
		List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");

    	String storeByHBase = ElementParameterParser.getValue(node, "__STORE_BY_HBASE__");
    	String zookeeperQuorumForHBase = ElementParameterParser.getValue(node, "__ZOOKEEPER_QUORUM__");
    	String zookeeperClientPortForHBase = ElementParameterParser.getValue(node, "__ZOOKEEPER_CLIENT_PORT__");

    	boolean setZNodeParent = "true".equals(ElementParameterParser.getValue(node, "__SET_ZNODE_PARENT__"));
		String zNodeParent = ElementParameterParser.getValue(node, "__ZNODE_PARENT__");

		String hbaseMasterPrincipal = ElementParameterParser.getValue(node, "__HBASE_MASTER_PRINCIPAL__");
		String hbaseRegionServerPrincipal = ElementParameterParser.getValue(node, "__HBASE_REGIONSERVER_PRINCIPAL__");

    	String defineRegisterJar = ElementParameterParser.getValue(node, "__DEFINE_REGISTER_JAR__");
    	List<Map<String, String>> registerJarForHBase = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__REGISTER_JAR__");

		boolean useYarn = "true".equals(ElementParameterParser.getValue(node, "__USE_YARN__"));

		boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
		boolean securityIsEnabled = useKrb && (isCustom || (hiveDistrib.doSupportKerberos() && (("HIVE".equalsIgnoreCase(hiveServer) && "EMBEDDED".equalsIgnoreCase(connectionMode)) || "HIVE2".equalsIgnoreCase(hiveServer))));
		boolean securedStandaloneHive2 = securityIsEnabled && "HIVE2".equalsIgnoreCase(hiveServer) && "STANDALONE".equalsIgnoreCase(connectionMode);
		boolean securedEmbedded = securityIsEnabled && "EMBEDDED".equalsIgnoreCase(connectionMode);
		boolean securedEmbeddedHive2 = securedEmbedded && "HIVE2".equalsIgnoreCase(hiveServer);

		String hivePrincipal = ElementParameterParser.getValue(node, "__HIVE_PRINCIPAL__");
		boolean useSsl = "true".equals(ElementParameterParser.getValue(node, "__USE_SSL__"));
		String sslTrustStore = ElementParameterParser.getValue(node, "__SSL_TRUST_STORE__");
		String sslStorepasswordFieldName = "__SSL_TRUST_STORE_PASSWORD__";

        boolean useMapRTicket = ElementParameterParser.getBooleanValue(node, "__USE_MAPRTICKET__");
        String mapRTicketUsername = ElementParameterParser.getValue(node, "__MAPRTICKET_USERNAME__");
        String mapRTicketCluster = ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
        String mapRTicketDuration = ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");

        boolean setMapRHomeDir = ElementParameterParser.getBooleanValue(node, "__SET_MAPR_HOME_DIR__");
        String mapRHomeDir = ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");

        boolean setMapRHadoopLogin = ElementParameterParser.getBooleanValue(node, "__SET_HADOOP_LOGIN__");
        String mapRHadoopLogin = ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");

        String passwordFieldName = "";

		if(hiveServer!=null && !"".equals(hiveServer.trim()) && (isCustom || hiveDistrib.doSupportHive2())) {
			hiveServer = hiveServer.toLowerCase();
			if ("hive2".equals(hiveServer)) {
				javaDbDriver = "org.apache.hive.jdbc.HiveDriver";
			}
		} else {
			hiveServer = "hive";
		}

		if(("hive".equals(hiveServer) && !hiveDistrib.doSupportHive1()) || ("hive2".equals(hiveServer) && !hiveDistrib.doSupportHive2())) {

    stringBuffer.append(TEXT_168);
    stringBuffer.append(hiveDistrib.getVersion());
    stringBuffer.append(TEXT_169);
    
		}

		if(("STANDALONE".equals(connectionMode) && !hiveDistrib.doSupportStandaloneMode()) || ("EMBEDDED".equals(connectionMode) && !hiveDistrib.doSupportEmbeddedMode())) {

    stringBuffer.append(TEXT_170);
    stringBuffer.append(hiveDistrib.getVersion());
    stringBuffer.append(TEXT_171);
    
		}

		if("STANDALONE".equals(connectionMode) && "hive".equals(hiveServer) && !hiveDistrib.doSupportHive1Standalone()) {

    stringBuffer.append(TEXT_172);
    
		}

		if(hadoopProps.size() > 0){
			for(Map<String, String> item : hadoopProps){

    stringBuffer.append(TEXT_173);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_174);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_175);
    
			}
		}

		if(securedEmbedded) {
			String metastoreUrl = ElementParameterParser.getValue(node, "__METASTORE_JDBC_URL__");
			String driverClass = ElementParameterParser.getValue(node, "__METASTORE_CLASSNAME__");
			String metastoreUsername = ElementParameterParser.getValue(node, "__METASTORE_USERNAME__");
			boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
			String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
			String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");

    stringBuffer.append(TEXT_176);
    stringBuffer.append(driverClass);
    stringBuffer.append(TEXT_177);
    if(securedEmbeddedHive2){
				// Disable authorization when using local HiveServer2 in secure mode
				
    stringBuffer.append(TEXT_178);
    
			}else{
				
    stringBuffer.append(TEXT_179);
    
			}
			
    stringBuffer.append(TEXT_180);
    stringBuffer.append(metastoreUrl);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(metastoreUsername);
    stringBuffer.append(TEXT_182);
    
    		passwordFieldName = "__METASTORE_PASSWORD__";
    		
    stringBuffer.append(TEXT_183);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_184);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_186);
    } else {
    stringBuffer.append(TEXT_187);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_188);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_189);
    }
    stringBuffer.append(TEXT_190);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_191);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_192);
    
			if(securedEmbeddedHive2){
				
    stringBuffer.append(TEXT_193);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HIVESERVER2_LOCAL_PRINCIPAL__"));
    stringBuffer.append(TEXT_194);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HIVESERVER2_LOCAL_KEYTAB__"));
    stringBuffer.append(TEXT_195);
    
			}
			
    stringBuffer.append(TEXT_196);
    
			if(useKeytab) {

    stringBuffer.append(TEXT_197);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_198);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_199);
    
			}
		}

		if(((isCustom && !useYarn) || (!isCustom && hiveDistrib.isHadoop1())) && setMapredJT) {
			String mapredJT = ElementParameterParser.getValue(node, "__MAPRED_JT__");

    stringBuffer.append(TEXT_200);
    stringBuffer.append(mapredJT);
    stringBuffer.append(TEXT_201);
    
		}

		if(setNamenode) {
			String namenode = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");

    stringBuffer.append(TEXT_202);
    stringBuffer.append(fsDefalutName);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(namenode);
    stringBuffer.append(TEXT_204);
    
		}

    stringBuffer.append(TEXT_205);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_206);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_207);
    
		passwordFieldName = "__PASS__";
		
    stringBuffer.append(TEXT_208);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_210);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_211);
    } else {
    stringBuffer.append(TEXT_212);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_213);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_214);
    }
    stringBuffer.append(TEXT_215);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_216);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_217);
    
        boolean setHadoopUser = "true".equals(ElementParameterParser.getValue(node, "__SET_HADOOP_USER__"));
        if (setHadoopUser) {
            String hadoopUser = ElementParameterParser.getValue(node, "__HADOOP_USER__");
            
    stringBuffer.append(TEXT_218);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_219);
    stringBuffer.append(hadoopUser);
    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_221);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_222);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_223);
    
        }
        
    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    
		if("EMBEDDED".equals(connectionMode) && (isCustom || hiveDistrib.doSupportEmbeddedMode())) {

    stringBuffer.append(TEXT_226);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_227);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_228);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_230);
    
			if(isCustom || (!isCustom && hiveDistrib.doSupportImpersonation())) {

    stringBuffer.append(TEXT_231);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_232);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_233);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_234);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_235);
    
			}
		} else if("STANDALONE".equals(connectionMode) && (isCustom || hiveDistrib.doSupportStandaloneMode())) {
				if(securedStandaloneHive2) {
					// using keytab with HiveServer2 in standalone mode
					boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
					String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
					String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
                    
					if ((isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket) {
                        
    stringBuffer.append(TEXT_236);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_237);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_238);
    
                    }
					if(useKeytab) {

    stringBuffer.append(TEXT_239);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_240);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_241);
    
					}
					// Using SSL in Secure Mode
					if(useSsl && hiveDistrib.doSupportSSL()){
						// Does the distrib support SSL + KERBEROS
						if(hiveDistrib.doSupportSSLwithKerberos()){
							if (ElementParameterParser.canEncrypt(node, sslStorepasswordFieldName)) {

    stringBuffer.append(TEXT_242);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_243);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_244);
    
							}else{

    stringBuffer.append(TEXT_245);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_246);
    stringBuffer.append( ElementParameterParser.getValue(node, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_247);
    
							}

    stringBuffer.append(TEXT_248);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_249);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_250);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_253);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_254);
    stringBuffer.append(sslTrustStore);
    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    
						// Does the distrib support only SASL-QOP + KERBEROS
						} else {
						

    stringBuffer.append(TEXT_257);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_258);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_259);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_260);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_262);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_263);
    
						}
					}else{

    stringBuffer.append(TEXT_264);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_265);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_266);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_267);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_268);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_269);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_270);
    
					}
					if ((isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket) {
                        
    stringBuffer.append(TEXT_271);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_272);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_273);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_274);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_275);
    
                    }
				} else {
                    // Mapr ticket
				    if ((isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket) {
                        passwordFieldName = "__MAPRTICKET_PASSWORD__";
                        
    stringBuffer.append(TEXT_276);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_277);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_278);
    
                        if (setMapRHadoopLogin) {
                            
    stringBuffer.append(TEXT_279);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_280);
    
                        } else {
                            
    stringBuffer.append(TEXT_281);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_282);
    
                        }
                        if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_283);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_284);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_285);
    } else {
    stringBuffer.append(TEXT_286);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_287);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_288);
    }
                        	if(hiveDistrib.doSupportMaprTicketV52API()){
                				
    stringBuffer.append(TEXT_289);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_290);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_291);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_293);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_294);
    
                			} else {
                				
    stringBuffer.append(TEXT_295);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_296);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_297);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_298);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_300);
    
                			}
                    }

                    // Using SSL in non Secure Mode
					if(useSsl && hiveDistrib.doSupportSSL()){
						if (ElementParameterParser.canEncrypt(node, sslStorepasswordFieldName)) {

    stringBuffer.append(TEXT_301);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_302);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_303);
    
						}else{

    stringBuffer.append(TEXT_304);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_305);
    stringBuffer.append( ElementParameterParser.getValue(node, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_306);
    
						}

    stringBuffer.append(TEXT_307);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_308);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_309);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_310);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_311);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_312);
    stringBuffer.append(sslTrustStore);
    stringBuffer.append(TEXT_313);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_314);
    
					}else{

    stringBuffer.append(TEXT_315);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_316);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_317);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_318);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_319);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_320);
    
					}
				}
				String additionalJdbcSettings = ElementParameterParser.getValue(node, "__HIVE_ADDITIONAL_JDBC__");

    stringBuffer.append(TEXT_321);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_322);
    stringBuffer.append(additionalJdbcSettings);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_324);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_325);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_326);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_327);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_328);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_329);
    
			}

    stringBuffer.append(TEXT_330);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_331);
    stringBuffer.append(javaDbDriver );
    stringBuffer.append(TEXT_332);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_333);
    
	   		log4jCodeGenerateUtil.debugConnectionParams(node);
		
    stringBuffer.append(TEXT_334);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_335);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_336);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_337);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_338);
    }
    
		if(securedStandaloneHive2) {

    stringBuffer.append(TEXT_339);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_340);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_341);
    
		} else {

    stringBuffer.append(TEXT_342);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_343);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_344);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_345);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_346);
    
		}

    stringBuffer.append(TEXT_347);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_348);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_349);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_350);
    }
    stringBuffer.append(TEXT_351);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_352);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_353);
    
    	if(!isCustom && ("HDP_1_2".equals(hiveVersion) || "HDP_1_3".equals(hiveVersion))) {
        	String mapMemory = ElementParameterParser.getValue(node,"__MAPRED_JOB_MAP_MEMORY_MB__");
       		String reduceMemory = ElementParameterParser.getValue(node,"__MAPRED_JOB_REDUCE_MEMORY_MB__");

    stringBuffer.append(TEXT_354);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_355);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_356);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_357);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_358);
    
	}

	boolean isKerberosAvailableHadoop2 = !isCustom && hiveDistrib.isHadoop2() && hiveDistrib.doSupportKerberos();
	boolean isHadoop2 = !isCustom && hiveDistrib.isHadoop2();
	boolean isKerberosAvailableHadoop1 = !isCustom && hiveDistrib.isHadoop1() && hiveDistrib.doSupportKerberos();

	if(securedEmbedded) {
		String namenodePrincipal = ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");

    stringBuffer.append(TEXT_359);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_360);
    stringBuffer.append(namenodePrincipal);
    stringBuffer.append(TEXT_361);
    
		if(isKerberosAvailableHadoop1 || (isCustom && !useYarn)) {
			String jobtrackerPrincipal = ElementParameterParser.getValue(node, "__JOBTRACKER_PRINCIPAL__");

    stringBuffer.append(TEXT_362);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_363);
    stringBuffer.append(jobtrackerPrincipal);
    stringBuffer.append(TEXT_364);
    
		}
		if(isKerberosAvailableHadoop2 || (isCustom && useYarn)) {
			String resourceManagerPrincipal = ElementParameterParser.getValue(node, "__RESOURCEMANAGER_PRINCIPAL__");

    stringBuffer.append(TEXT_365);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_366);
    stringBuffer.append(resourceManagerPrincipal);
    stringBuffer.append(TEXT_367);
    
		}

	}

    	boolean setResourceManager = "true".equals(ElementParameterParser.getValue(node, "__SET_RESOURCE_MANAGER__"));

    	if((isCustom && useYarn) || (!isCustom && isHadoop2)) {
    		if(setResourceManager) {
    			String resourceManager = ElementParameterParser.getValue(node, "__RESOURCE_MANAGER__");

    stringBuffer.append(TEXT_368);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_369);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_370);
    stringBuffer.append(resourceManager);
    stringBuffer.append(TEXT_371);
    
			}

			boolean setJobHistoryAddress = "true".equals(ElementParameterParser.getValue(node, "__SET_JOBHISTORY_ADDRESS__"));
			if(setJobHistoryAddress) {
				String jobHistoryAddress = ElementParameterParser.getValue(node,"__JOBHISTORY_ADDRESS__");
    			
    stringBuffer.append(TEXT_372);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_373);
    stringBuffer.append(jobHistoryAddress);
    stringBuffer.append(TEXT_374);
    
			}

    		boolean setSchedulerAddress = "true".equals(ElementParameterParser.getValue(node, "__SET_SCHEDULER_ADDRESS__"));
    		if(setSchedulerAddress) {
    			String schedulerAddress = ElementParameterParser.getValue(node,"__RESOURCEMANAGER_SCHEDULER_ADDRESS__");

    stringBuffer.append(TEXT_375);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_376);
    stringBuffer.append(schedulerAddress);
    stringBuffer.append(TEXT_377);
    
			}

            if ("true".equals(ElementParameterParser.getValue(node, "__USE_DATANODE_HOSTNAME__"))) {
                
    stringBuffer.append(TEXT_378);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_379);
    
            }

    		if("true".equals(ElementParameterParser.getValue(node, "__SET_FS_DEFAULT_NAME__"))) {
    			String namenode = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");

    stringBuffer.append(TEXT_380);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_381);
    stringBuffer.append(namenode);
    stringBuffer.append(TEXT_382);
    
			}

			if("EMBEDDED".equals(connectionMode) && (isCustom || hiveDistrib.doSupportEmbeddedMode())) {
				boolean crossPlatformSubmission = "true".equals(ElementParameterParser.getValue(node, "__CROSS_PLATFORM_SUBMISSION__"));
				if((isCustom && useYarn && crossPlatformSubmission) || (!isCustom && hiveDistrib.doSupportCrossPlatformSubmission())) {

    stringBuffer.append(TEXT_383);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_384);
    
				}

				if("MAPR410".equals(hiveVersion)){

    stringBuffer.append(TEXT_385);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_387);
    
				}

				if(hiveDistrib.doSupportCustomMRApplicationCP()){

    stringBuffer.append(TEXT_388);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_389);
    stringBuffer.append(hiveDistrib.getCustomMRApplicationCP());
    stringBuffer.append(TEXT_390);
    
				}


    stringBuffer.append(TEXT_391);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_392);
    stringBuffer.append(hiveDistrib.getYarnApplicationClasspath());
    stringBuffer.append(TEXT_393);
    
			}

    		boolean setMemory = "true".equals(ElementParameterParser.getValue(node, "__SET_MEMORY__"));
    		if(setMemory) {
    			String mapMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_MAP_MEMORY_MB__");
    			String reduceMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_REDUCE_MEMORY_MB__");
    			String amMemory = ElementParameterParser.getValue(node,"__YARN_APP_MAPREDUCE_AM_RESOURCE_MB__");

    stringBuffer.append(TEXT_394);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_395);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_396);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_397);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_398);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_399);
    stringBuffer.append(amMemory);
    stringBuffer.append(TEXT_400);
    
			}
		}

		List<Map<String, String>> advProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ADVANCED_PROPERTIES__");
		if(advProps!=null) {
			for(Map<String, String> item : advProps){

    stringBuffer.append(TEXT_401);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_402);
    stringBuffer.append(item.get("PROPERTY"));
    stringBuffer.append(TEXT_403);
    stringBuffer.append(item.get("VALUE"));
    stringBuffer.append(TEXT_404);
    
			}
		}

    stringBuffer.append(TEXT_405);
    
		if(("false").equals(useExistingConn)){
		
    stringBuffer.append(TEXT_406);
    stringBuffer.append(TEXT_407);
    
class PrepareTez{
	public void invoke(INode node, String cid){
        String hiveDistribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
        String hiveVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");

        org.talend.hadoop.distribution.component.HiveComponent hiveDistrib = null;
        try {
            hiveDistrib = (org.talend.hadoop.distribution.component.HiveComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(hiveDistribution, hiveVersion);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

        boolean isCustom = hiveDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

        boolean changePathSeparator = !hiveDistrib.isExecutedThroughWebHCat();

        String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");
        List<Map<String, String>> advProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ADVANCED_PROPERTIES__");
        String dbuser = ElementParameterParser.getValue(node, "__USER__");
        
        boolean useTez = "tez".equals(ElementParameterParser.getValue(node, "__EXECUTION_ENGINE__"));
    	boolean supportTez = (isCustom || hiveDistrib.doSupportTezForHive()) && "EMBEDDED".equals(connectionMode);
    	if(supportTez && useTez){
    	
    stringBuffer.append(TEXT_408);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_409);
    
            if(advProps != null){
                for(Map<String, String> item : advProps){
                    if("\"tez.lib.uris\"".equals(item.get("PROPERTY"))){
                    
    stringBuffer.append(TEXT_410);
      
                    }
                }
            }
            boolean installTez = "INSTALL".equals(ElementParameterParser.getValue(node, "__TEZ_LIB__"));
            if(installTez){
                //prepare the folder
                
    stringBuffer.append(TEXT_411);
    
class GetFileSystem{
	public void invoke(INode node, String cid){
        String fsDefaultName = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
        List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");
        String user = null;
        
        String fsDefaultNameKey = "fs.default.name";
        
        String distribution = null;
        String hadoopVersion = null;
        boolean isCustom = false;

        String dbuser = ElementParameterParser.getValue(node, "__USER__");
        
    stringBuffer.append(TEXT_412);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_413);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_414);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_415);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_416);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_417);
    
        distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
        hadoopVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");
        boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
        String kerberosPrincipal = ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");
        boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
        String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
        String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
        boolean mrUseDatanodeHostname = "true".equals(ElementParameterParser.getValue(node, "__USE_DATANODE_HOSTNAME__"));

        org.talend.hadoop.distribution.component.HDFSComponent hdfsDistrib = null;
        try {
            hdfsDistrib = (org.talend.hadoop.distribution.component.HDFSComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hadoopVersion);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

        isCustom = hdfsDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
        
        
    stringBuffer.append(TEXT_418);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_419);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_420);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_421);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_422);
    
        if(mrUseDatanodeHostname && (isCustom || hdfsDistrib.doSupportUseDatanodeHostname())){
        
    stringBuffer.append(TEXT_423);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_424);
    
        }
        if(hadoopProps!=null && hadoopProps.size() > 0){
        	for(Map<String, String> item : hadoopProps){
        	
    stringBuffer.append(TEXT_425);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_426);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_427);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_428);
     
    		}
    	}
        	
    	if((hdfsDistrib.doSupportKerberos() && useKrb && !isCustom) || (isCustom && useKrb)){
    	
    stringBuffer.append(TEXT_429);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_430);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_431);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_432);
    
    		if(useKeytab){
    		
    stringBuffer.append(TEXT_433);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_434);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_435);
    
    		}
    	}
    	
    	if(hdfsDistrib.doSupportGroup()){
    		
    stringBuffer.append(TEXT_436);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_437);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_438);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_439);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_440);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_441);
    
        }else{
        
    stringBuffer.append(TEXT_442);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_443);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_444);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_445);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_446);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_447);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_448);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_449);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_450);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_451);
    
        }
    }
}

      
                (new GetFileSystem()).invoke(node, cid);
                String tezLibFolder = ElementParameterParser.getValue(node, "__TEZ_LIB_FOLDER__");
                boolean useDefaultTezLibFolder = "\"/tmp/{USERNAME}/talend_tez_libs/{custom|HIVE_VERSION}\"".equals(tezLibFolder);
                if(useDefaultTezLibFolder){
                
    stringBuffer.append(TEXT_452);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_453);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_454);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_455);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_456);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_457);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_458);
    stringBuffer.append(isCustom?"custom":hiveVersion);
    stringBuffer.append(TEXT_459);
    
                }else{
                
    stringBuffer.append(TEXT_460);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_461);
    stringBuffer.append(tezLibFolder);
    stringBuffer.append(TEXT_462);
    
                }
                
    stringBuffer.append(TEXT_463);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_464);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_465);
    
                List<String> tezLibJarsName = new java.util.ArrayList<String>();
                if(isCustom){
                    List<Map<String,String>> tezLibJarsNameValue = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node,"__TEZ_LIB_NAME__");
                    for(Map<String, String> tezLibJarsNameV : tezLibJarsNameValue){
                        tezLibJarsName.add(tezLibJarsNameV.get("JAR_NAME"));
                    }
                }else{
                    String tezLibJarsNameValue = ElementParameterParser.getValue(node, "__TEZ_JARS_NAME__");
                    if(tezLibJarsNameValue != null && !"".equals(tezLibJarsNameValue)){
                        tezLibJarsName = java.util.Arrays.asList(tezLibJarsNameValue.split(","));
                    }
                }
                
    stringBuffer.append(TEXT_466);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_467);
    if(changePathSeparator){
    stringBuffer.append(TEXT_468);
    }else{
    stringBuffer.append(TEXT_469);
    }
    stringBuffer.append(TEXT_470);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_471);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_472);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_473);
    
                    for(String jarName : tezLibJarsName){
                    
    stringBuffer.append(TEXT_474);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_475);
    stringBuffer.append(jarName);
    stringBuffer.append(TEXT_476);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_477);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_478);
    stringBuffer.append(jarName);
    stringBuffer.append(TEXT_479);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_480);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_481);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_482);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_483);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_484);
    
                    }
                    
    stringBuffer.append(TEXT_485);
    
            }else{
            
    stringBuffer.append(TEXT_486);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_487);
    stringBuffer.append(ElementParameterParser.getValue(node, "__TEZ_LIB_PATH__"));
    stringBuffer.append(TEXT_488);
    
            }
            //define the location of tez lib    
            
    stringBuffer.append(TEXT_489);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_490);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_491);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_492);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_493);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_494);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_495);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_496);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_497);
    stringBuffer.append(ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__"));
    stringBuffer.append(TEXT_498);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_499);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_500);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_501);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_502);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_503);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_504);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_505);
    
    	}
    }
}

    stringBuffer.append(TEXT_506);
    
            	(new PrepareTez()).invoke(node, cid);
        }
    	
    stringBuffer.append(TEXT_507);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_508);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_509);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_510);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_511);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_512);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_513);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_514);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_515);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_516);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_517);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_518);
    if("true".equalsIgnoreCase(storeByHBase) && (isCustom || hiveDistrib.doSupportHBaseForHive())) {
    stringBuffer.append(TEXT_519);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_520);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_521);
    if(zookeeperQuorumForHBase!=null && !"".equals(zookeeperQuorumForHBase) && !"\"\"".equals(zookeeperQuorumForHBase)) {
    stringBuffer.append(TEXT_522);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_523);
    stringBuffer.append(zookeeperQuorumForHBase);
    stringBuffer.append(TEXT_524);
    }
    stringBuffer.append(TEXT_525);
    if(zookeeperClientPortForHBase!=null && !"".equals(zookeeperClientPortForHBase) && !"\"\"".equals(zookeeperClientPortForHBase)) {
    stringBuffer.append(TEXT_526);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_527);
    stringBuffer.append(zookeeperClientPortForHBase);
    stringBuffer.append(TEXT_528);
    }
    stringBuffer.append(TEXT_529);
    if(setZNodeParent && zNodeParent!=null && !"".equals(zNodeParent) && !"\"\"".equals(zNodeParent)) {
    stringBuffer.append(TEXT_530);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_531);
    stringBuffer.append(zNodeParent);
    stringBuffer.append(TEXT_532);
    }
    stringBuffer.append(TEXT_533);
    if(useKrb){
    stringBuffer.append(TEXT_534);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_535);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_536);
    if(hbaseMasterPrincipal!=null && !"".equals(hbaseMasterPrincipal) && !"\"\"".equals(hbaseMasterPrincipal)){
    stringBuffer.append(TEXT_537);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_538);
    stringBuffer.append(hbaseMasterPrincipal);
    stringBuffer.append(TEXT_539);
    }
    stringBuffer.append(TEXT_540);
    if(hbaseRegionServerPrincipal!=null && !"".equals(hbaseRegionServerPrincipal) && !"\"\"".equals(hbaseRegionServerPrincipal)){
    stringBuffer.append(TEXT_541);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_542);
    stringBuffer.append(hbaseRegionServerPrincipal);
    stringBuffer.append(TEXT_543);
    }
    stringBuffer.append(TEXT_544);
    }
    stringBuffer.append(TEXT_545);
    if("true".equalsIgnoreCase(defineRegisterJar) && registerJarForHBase!=null && registerJarForHBase.size()>0) {
        		for(Map<String, String> jar : registerJarForHBase){
        			String path = jar.get("JAR_PATH");
        			if(path == null || "".equals(path) || "\"\"".equals(path)) {
        				continue;
        			}
        	
    stringBuffer.append(TEXT_546);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_547);
    stringBuffer.append(path);
    stringBuffer.append(TEXT_548);
    
    			}
    		}

    stringBuffer.append(TEXT_549);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_550);
    
	  	}
	}

    
    }

    String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");
    String tableName = ElementParameterParser.getValue(node, "__TABLE__");
    String dieOnError = ElementParameterParser.getValue(node, "__DIE_ON_ERROR__");
    boolean external = "true".equals(ElementParameterParser.getValue(node, "__CREATE_EXTERNAL__"));
    String tableAction = ElementParameterParser.getValue(node,"__TABLEACTION__");
    boolean createIfNotExist = "CREATE_IF_NOT_EXIST".equals(tableAction);
    boolean setPartitioned = "true".equals(ElementParameterParser.getValue(node, "__SET_PARTITIONS__"));
    boolean setClustered = false;
    boolean setSkewed = false;

    boolean useExistingConnection = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));

    String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");
    boolean setFsDefaultName = "true".equals(ElementParameterParser.getValue(node, "__SET_FS_DEFAULT_NAME__"));
    String fsDefaultName = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
    INode connectionInformationNode = node;

    if(useExistingConnection && !hiveDistrib.isExecutedThroughWebHCat()) {
        connectionMode = "";
        setFsDefaultName = false;
        fsDefaultName = "";
        dbuser = "";
        String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
        for (INode pNode : node.getProcess().getNodesOfType("tHiveConnection")) {
            if(connection!=null && connection.equals(pNode.getUniqueName())) {
                connectionMode = ElementParameterParser.getValue(pNode, "__CONNECTION_MODE__");
                setFsDefaultName = "true".equals(ElementParameterParser.getValue(pNode, "__SET_FS_DEFAULT_NAME__"));
                fsDefaultName = ElementParameterParser.getValue(pNode, "__FS_DEFAULT_NAME__");
                dbuser = ElementParameterParser.getValue(pNode, "__USER__");
                connectionInformationNode = pNode;
                break;
            }
        }
    }

    boolean setLocation = "true".equals(ElementParameterParser.getValue(node, "__SET_FILE_LOCATION__"));
    String location = ElementParameterParser.getValue(node, "__FILE_LOCATION__");
    boolean isS3Location = "true".equals(ElementParameterParser.getValue(node, "__S3_LOCATION__"));

    String storedFormat = ElementParameterParser.getValue(node, "__STORAGE_FORMAT__");

    List<Map<String, String>> serdeProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__SERDE_PROPERTIES__");

    List<Map<String, String>> tableProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__TABLE_PROPERTIES__");

    boolean setDelimitedRowFormat = "true".equals(ElementParameterParser.getValue(node, "__SET_ROW_FORMAT__"));
    boolean setSerdeRowFormat = "true".equals(ElementParameterParser.getValue(node, "__SET_SERDE_ROW_FORMAT__"));

    boolean setRowFormat = (setDelimitedRowFormat || setSerdeRowFormat) && !"STORAGE".equals(storedFormat);

    boolean storeAsAvro = "AVRO".equals(storedFormat);
    boolean storeAsParquet = "PARQUET".equals(storedFormat);

    boolean isParquetSupported = isCustom || hiveDistrib.doSupportParquetFormat();
    if(storeAsParquet && !isParquetSupported) {

    stringBuffer.append(TEXT_551);
    
    }

    // Register jars to handle the parquet format.

    boolean generateAddJarCodeForAll = storeAsParquet;
    java.util.List<String> jarsToRegister = null;
    java.util.List<String> jars = null;

    if(generateAddJarCodeForAll) {
        String[] commandLine = new String[] {"<command>"};
        try {
            commandLine = ProcessorUtilities.getCommandLine("win32",true, processId, "",org.talend.designer.runprocess.IProcessor.NO_STATISTICS,org.talend.designer.runprocess.IProcessor.NO_TRACES, new String[]{});
        } catch (ProcessorException e) {
            e.printStackTrace();
        }

        jarsToRegister = new java.util.ArrayList();

        jarsToRegister.add("parquet-hive-bundle");
        //jarsToRegister.add("parquet-hadoop-bundle");
        jarsToRegister.add("snappy-java");

        for (int j = 0; j < commandLine.length; j++) {
            if(commandLine[j].contains("jar")) {
                jars = java.util.Arrays.asList(commandLine[j].split(";"));
                break;
            }
        }
    }

    if(jarsToRegister!=null && jars!=null) {
        if("EMBEDDED".equalsIgnoreCase(connectionMode) || hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_552);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_553);
    
        } else {
            generateAddJarCodeForAll = false;
            if(setFsDefaultName) {
                generateAddJarCodeForAll = true;

    stringBuffer.append(TEXT_554);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_555);
    stringBuffer.append(TEXT_556);
    
class GetFileSystem{
	public void invoke(INode node, String cid){
        String fsDefaultName = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
        List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");
        String user = null;
        
        String fsDefaultNameKey = "fs.default.name";
        
        String distribution = null;
        String hadoopVersion = null;
        boolean isCustom = false;

        String dbuser = ElementParameterParser.getValue(node, "__USER__");
        
    stringBuffer.append(TEXT_557);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_558);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_559);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_560);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_561);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_562);
    
        distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
        hadoopVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");
        boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
        String kerberosPrincipal = ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");
        boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
        String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
        String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
        boolean mrUseDatanodeHostname = "true".equals(ElementParameterParser.getValue(node, "__USE_DATANODE_HOSTNAME__"));

        org.talend.hadoop.distribution.component.HDFSComponent hdfsDistrib = null;
        try {
            hdfsDistrib = (org.talend.hadoop.distribution.component.HDFSComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hadoopVersion);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

        isCustom = hdfsDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
        
        
    stringBuffer.append(TEXT_563);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_564);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_565);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_566);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_567);
    
        if(mrUseDatanodeHostname && (isCustom || hdfsDistrib.doSupportUseDatanodeHostname())){
        
    stringBuffer.append(TEXT_568);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_569);
    
        }
        if(hadoopProps!=null && hadoopProps.size() > 0){
        	for(Map<String, String> item : hadoopProps){
        	
    stringBuffer.append(TEXT_570);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_571);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_572);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_573);
     
    		}
    	}
        	
    	if((hdfsDistrib.doSupportKerberos() && useKrb && !isCustom) || (isCustom && useKrb)){
    	
    stringBuffer.append(TEXT_574);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_575);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_576);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_577);
    
    		if(useKeytab){
    		
    stringBuffer.append(TEXT_578);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_579);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_580);
    
    		}
    	}
    	
    	if(hdfsDistrib.doSupportGroup()){
    		
    stringBuffer.append(TEXT_581);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_582);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_583);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_584);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_585);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_586);
    
        }else{
        
    stringBuffer.append(TEXT_587);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_588);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_589);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_590);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_591);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_592);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_593);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_594);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_595);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_596);
    
        }
    }
}

    stringBuffer.append(TEXT_597);
    
			(new GetFileSystem()).invoke(connectionInformationNode, cid);
			
    stringBuffer.append(TEXT_598);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_599);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_600);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_601);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_602);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_603);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_604);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_605);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_606);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_607);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_608);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_609);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_610);
    
            }
        }

        if(generateAddJarCodeForAll) {
            if(!hiveDistrib.isExecutedThroughWebHCat()) { // Then we create a SQL statement to add the jars.

    stringBuffer.append(TEXT_611);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_612);
    
            }
            for(int i=0; i<jarsToRegister.size(); i++) {
                String jarToRegister = jarsToRegister.get(i);
                for(int j=0; j<jars.size(); j++) {
                    if(jars.get(j).contains(jarToRegister)) {
                        if(!hiveDistrib.isExecutedThroughWebHCat()) { // Then we use the created SQL statement to add the jars.

    stringBuffer.append(TEXT_613);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_614);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_615);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_616);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_617);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_618);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_619);
    
                        } else {

    stringBuffer.append(TEXT_620);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_621);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_622);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_623);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_624);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_625);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_626);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_627);
    
                        }
                    }
                }
            }
        }
    }

    // End of parquet format handling.
    if(!hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_628);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_629);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_630);
    
    }

    stringBuffer.append(TEXT_631);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_632);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_633);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_634);
    
List<IMetadataColumn> listColumn = null;
List<IMetadataTable> metadatas = node.getMetadataList();
if(metadatas != null && metadatas.size() > 0) {
    IMetadataTable metadata = metadatas.get(0);
    listColumn = metadata.getListColumns();
}

StringBuilder createTableSQL = new StringBuilder();

createTableSQL.append("CREATE ");
createTableSQL.append(external || isS3Location ?"EXTERNAL":"");
createTableSQL.append(" TABLE ");
createTableSQL.append(createIfNotExist?"IF NOT EXISTS":"");
createTableSQL.append(" \" + ");
createTableSQL.append("tableName_");
createTableSQL.append(cid);
createTableSQL.append(" + \"");

boolean likeTable = "true".equals(ElementParameterParser.getValue(node, "__LIKE_TABLE__"));
if(likeTable) {
    String likeTableName = ElementParameterParser.getValue(node, "__LIKE_TABLE_NAME__");

    stringBuffer.append(TEXT_635);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_636);
    stringBuffer.append(likeTableName);
    stringBuffer.append(TEXT_637);
    
    createTableSQL.append(" LIKE ");
    createTableSQL.append("\" + ");
    createTableSQL.append("likeTableName_");
    createTableSQL.append(cid);
    createTableSQL.append(" + \"");

    if(setLocation) {

    stringBuffer.append(TEXT_638);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_639);
    stringBuffer.append(location);
    stringBuffer.append(TEXT_640);
    
        createTableSQL.append(" LOCATION '");
        createTableSQL.append("\" + ");
        createTableSQL.append("location_");
        createTableSQL.append(cid);
        createTableSQL.append(" + \"'");
    }

    stringBuffer.append(TEXT_641);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_642);
    stringBuffer.append(createTableSQL.toString());
    stringBuffer.append(TEXT_643);
    
        if(!hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_644);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_645);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_646);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_647);
    if(("true").equals(dieOnError)) {
        
    stringBuffer.append(TEXT_648);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_649);
    
        }else {
        
    stringBuffer.append(TEXT_650);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_651);
    
        }
    stringBuffer.append(TEXT_652);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_653);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_654);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_655);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_656);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_657);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_658);
    
    if(!("true").equals(useExistingConn)) {

    stringBuffer.append(TEXT_659);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_660);
    
    }
        } else {

    stringBuffer.append(TEXT_661);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_662);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_663);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_664);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_665);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_666);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_667);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_668);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_669);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_670);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_671);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_672);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_673);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_674);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_675);
    
                if(("true").equals(dieOnError)) {

    stringBuffer.append(TEXT_676);
    
                } else {

    stringBuffer.append(TEXT_677);
    
                    if(isLog4jEnabled) {

    stringBuffer.append(TEXT_678);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_679);
    
                    }
                }

    stringBuffer.append(TEXT_680);
    
        }
    return stringBuffer.toString();
}

boolean asSelect = "true".equals(ElementParameterParser.getValue(node, "__AS_SELECT__"));

if(!storeAsAvro && !asSelect) {
    createTableSQL.append("(");
}

final MappingTypeRetriever mappingType = MetadataTalendType.getMappingTypeRetriever("hive_id");

    
	class Util {
		int index = 0;
		java.util.Map<String,String> hiveTypeToAvroType = null;
		String appendKeyValue = null;
	
		void generateColumnsSQL(List<IMetadataColumn> listColumn,StringBuilder createTableSQL) {
			index++;
			int count = 0;
			String ending = ",";
			for(IMetadataColumn metadataColumn : listColumn) {
				createTableSQL.append(metadataColumn.getOriginalDbColumnName());
				createTableSQL.append(" ");
            
				String hiveType = null;
				if(metadataColumn.getType() == null || metadataColumn.getType().trim().length() == 0) {
					hiveType = mappingType.getDefaultSelectedDbType(metadataColumn.getTalendType());
				} else {
					hiveType = metadataColumn.getType();
				}
        		createTableSQL.append(hiveType);
        		if(metadataColumn.getLength() != null && metadataColumn.getPrecision() != null && "DECIMAL".equals(hiveType)) {
        			createTableSQL.append("(" + metadataColumn.getLength() + ", " + metadataColumn.getPrecision() + ")");
        		}
        	
	        	String comment = metadataColumn.getComment();
	        	if(comment!=null && !"".equals(comment) && !"\"\"".equals(comment)) {

    stringBuffer.append(TEXT_681);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_682);
    stringBuffer.append(count);
    stringBuffer.append(TEXT_683);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_684);
    stringBuffer.append(comment);
    stringBuffer.append(TEXT_685);
    
	        		createTableSQL.append(" COMMENT '");
		    		createTableSQL.append("\" + ");
	    			createTableSQL.append("comment_");
	    			createTableSQL.append(index);
	    			createTableSQL.append("_");
	    			createTableSQL.append(count);
	    			createTableSQL.append("_");
	    			createTableSQL.append(cid);
	        		createTableSQL.append(" + \"'");
        		}
            
            if(count == listColumn.size() - 1) {
                ending = "";
            }
            createTableSQL.append(ending);
            count++;
			}
		}
	
		void generateAvroSchema(List<IMetadataColumn> listColumn,StringBuilder schemaBuilder, String quote) {
			if(hiveTypeToAvroType == null) {
				hiveTypeToAvroType = new java.util.HashMap<String,String>();
				hiveTypeToAvroType.put("SMALLINT","int");
				hiveTypeToAvroType.put("FLOAT","float");
				hiveTypeToAvroType.put("DOUBLE","double");
				hiveTypeToAvroType.put("BIGINT","long");
				hiveTypeToAvroType.put("INT","int");
				hiveTypeToAvroType.put("TINYINT","int");
				hiveTypeToAvroType.put("STRING","string");
				hiveTypeToAvroType.put("BOOLEAN","boolean");
				hiveTypeToAvroType.put("STRUCT","record");
				hiveTypeToAvroType.put("MAP","map");
				hiveTypeToAvroType.put("ARRAY","list");
				hiveTypeToAvroType.put("TIMESTAMP","long");
			}
		
			index++;
		
			int count = 0;
			String ending = ",";
			for(IMetadataColumn metadataColumn : listColumn) {
        		schemaBuilder.append("{");
        	
        		schemaBuilder.append(quote).append("name").append(quote);
            schemaBuilder.append(" : ");
            schemaBuilder.append(quote).append(metadataColumn.getOriginalDbColumnName()).append(quote);
            
            schemaBuilder.append(", ");
            
            String hiveType = null;
            if(metadataColumn.getType() == null || metadataColumn.getType().trim().length() == 0) {
                hiveType = mappingType.getDefaultSelectedDbType(metadataColumn.getTalendType());
            } else {
                hiveType = metadataColumn.getType();
            }
            
            String avroType = hiveTypeToAvroType.get(hiveType);
            
            schemaBuilder.append(quote).append("type").append(quote);
            schemaBuilder.append(" : ");
        		schemaBuilder.append(quote).append(avroType).append(quote);
        	
        		String comment = metadataColumn.getComment();
        		if(comment!=null && !"".equals(comment) && !"\"\"".equals(comment)) {
        			schemaBuilder.append(", ");

    stringBuffer.append(TEXT_686);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_687);
    stringBuffer.append(count);
    stringBuffer.append(TEXT_688);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_689);
    stringBuffer.append(comment);
    stringBuffer.append(TEXT_690);
    
	        		schemaBuilder.append(quote).append("doc").append(quote);
	        		schemaBuilder.append(" : ");
	        		schemaBuilder.append(quote);
	        		
		    		schemaBuilder.append("\" + ");
	    			schemaBuilder.append("comment_");
	    			schemaBuilder.append(index);
	    			schemaBuilder.append("_");
	    			schemaBuilder.append(count);
	    			schemaBuilder.append("_");
	    			schemaBuilder.append(cid);
	        		schemaBuilder.append(" + \"");
	        		
	        		schemaBuilder.append(quote);
        		}
            
            schemaBuilder.append("}");
            
            if(count == listColumn.size() - 1) {
                ending = "";
            }
            schemaBuilder.append(ending);
            count++;
			}
		}
	
		void generatePros(String prefix, List<Map<String, String>> pros, StringBuilder createTableSQL) {
			index++;
			
			int count = 0;
			String ending = ",";
			if(pros.size() > 0){
				createTableSQL.append(prefix);
				createTableSQL.append("(");
				for(Map<String, String> item : pros){

    stringBuffer.append(TEXT_691);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_692);
    stringBuffer.append(count);
    stringBuffer.append(TEXT_693);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_694);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_695);
    stringBuffer.append(index);
    stringBuffer.append(TEXT_696);
    stringBuffer.append(count);
    stringBuffer.append(TEXT_697);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_698);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_699);
    
		    		createTableSQL.append("'\" + ");
	    			createTableSQL.append("key_");
	    			createTableSQL.append(index);
	    			createTableSQL.append("_");
	    			createTableSQL.append(count);
	    			createTableSQL.append("_");
	    			createTableSQL.append(cid);
	        		createTableSQL.append(" + \"'");
	        		
	        		createTableSQL.append(" = ");
	        		
	        		createTableSQL.append("'\" + ");
	    			createTableSQL.append("value_");
	    			createTableSQL.append(index);
	    			createTableSQL.append("_");
	    			createTableSQL.append(count);
	    			createTableSQL.append("_");
	    			createTableSQL.append(cid);
	        		createTableSQL.append(" + \"'");
        		
        			if(count == pros.size() - 1) {
            		ending = "";
        			}
            	
	            createTableSQL.append(ending);
            	count++;
				}
			
				if(appendKeyValue!=null) {
					createTableSQL.append(",");
					createTableSQL.append(appendKeyValue);
					appendKeyValue = null;
				}
			
				createTableSQL.append(")");
			} else if(appendKeyValue!=null) {
				createTableSQL.append(prefix);
				createTableSQL.append("(");
				createTableSQL.append(appendKeyValue);
				createTableSQL.append(")");
				appendKeyValue = null;
			}
		}
	
		void appendKeyValue(String appendKeyValue) {
			this.appendKeyValue = appendKeyValue;
		}
	}

    

Util util = new Util();

if(!storeAsAvro && !asSelect) {
    util.generateColumnsSQL(listColumn,createTableSQL);
    createTableSQL.append(")");
}

String tableComment = ElementParameterParser.getValue(node, "__TABLE_COMMENT__");
if(tableComment!=null && !"".equals(tableComment) && !"\"\"".equals(tableComment)) {

    stringBuffer.append(TEXT_700);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_701);
    stringBuffer.append(tableComment);
    stringBuffer.append(TEXT_702);
    
    createTableSQL.append(" COMMENT '");
    createTableSQL.append("\" + ");
    createTableSQL.append("tableComment_");
    createTableSQL.append(cid);
    createTableSQL.append(" + \"'");
}

if(setPartitioned) {
    if ((metadatas!=null)&&(metadatas.size()>0)) {
        IMetadataTable metadata = metadatas.get(1);
        if (metadata!=null) {
            List<IMetadataColumn> columnList = metadata.getListColumns();
            if(columnList != null && columnList.size() > 0) {
                createTableSQL.append(" PARTITIONED BY (");
                util.generateColumnsSQL(columnList,createTableSQL);
                createTableSQL.append(")");
            }
        }
    }
}

boolean clustededOrSkewed = "true".equals(ElementParameterParser.getValue(node, "__SET_CLUSTERED_BY_AND_SKEWED_BY__"));
if(clustededOrSkewed) {
    String ddl = ElementParameterParser.getValue(node, "__CLUSTERED_BY_AND_SKEWED_BY__");
    ddl = ddl.replaceAll("\n"," ");
    ddl = ddl.replaceAll("\r"," ");

    stringBuffer.append(TEXT_703);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_704);
    stringBuffer.append(ddl);
    stringBuffer.append(TEXT_705);
    
    createTableSQL.append(" \" + ");
    createTableSQL.append("clustededOrSkewed_");
    createTableSQL.append(cid);
    createTableSQL.append(" + \"");
}

if(storeAsAvro || !hiveDistrib.doSupportStoreAsParquet() && storeAsParquet) {
    createTableSQL.append(" ROW FORMAT SERDE '" + (storeAsAvro?"org.apache.hadoop.hive.serde2.avro.AvroSerDe":"parquet.hive.serde.ParquetHiveSerDe") + "'");
    util.generatePros(" WITH SERDEPROPERTIES ", serdeProps, createTableSQL);
} else if(setRowFormat) {
    createTableSQL.append(" ROW FORMAT ");
    if(setDelimitedRowFormat) {
        createTableSQL.append("DELIMITED ");
        boolean setField = "true".equals(ElementParameterParser.getValue(node, "__SET_FIELD_TERMINATED_BY__"));
        if(setField) {
            String fieldChar = ElementParameterParser.getValue(node, "__FIELD_TERMINATED_BY__");

    stringBuffer.append(TEXT_706);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_707);
    stringBuffer.append(fieldChar);
    stringBuffer.append(TEXT_708);
    
            createTableSQL.append(" FIELDS TERMINATED BY '");
            createTableSQL.append("\" + ");
            createTableSQL.append("fieldChar_");
            createTableSQL.append(cid);
            createTableSQL.append(" + \"'");

            boolean setEscape = "true".equals(ElementParameterParser.getValue(node, "__SET_FIELD_ESCAPE_BY__"));
            if(setEscape) {
                String escapeChar = ElementParameterParser.getValue(node, "__FIELD_ESCAPE_BY__");

    stringBuffer.append(TEXT_709);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_710);
    stringBuffer.append(escapeChar);
    stringBuffer.append(TEXT_711);
    
                createTableSQL.append(" ESCAPED BY '");
                createTableSQL.append("\" + ");
                createTableSQL.append("escapeChar_");
                createTableSQL.append(cid);
                createTableSQL.append(" + \"'");
            }
        }

        boolean setCollection = "true".equals(ElementParameterParser.getValue(node, "__SET_COLLECTION_ITEM_TERMINATED_BY__"));
        if(setCollection) {
            String collectionChar = ElementParameterParser.getValue(node, "__COLLECTION_ITEM_TERMINATED_BY__");

    stringBuffer.append(TEXT_712);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_713);
    stringBuffer.append(collectionChar);
    stringBuffer.append(TEXT_714);
    
            createTableSQL.append(" COLLECTION ITEMS TERMINATED BY '");
            createTableSQL.append("\" + ");
            createTableSQL.append("collectionChar_");
            createTableSQL.append(cid);
            createTableSQL.append(" + \"'");
        }

        boolean setMap = "true".equals(ElementParameterParser.getValue(node, "__SET_MAP_KEY_TERMINATED_BY__"));
        if(setMap) {
            String mapChar = ElementParameterParser.getValue(node, "__MAP_KEY_TERMINATED_BY__");

    stringBuffer.append(TEXT_715);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_716);
    stringBuffer.append(mapChar);
    stringBuffer.append(TEXT_717);
    
            createTableSQL.append(" MAP KEYS TERMINATED BY '");
            createTableSQL.append("\" + ");
            createTableSQL.append("mapChar_");
            createTableSQL.append(cid);
            createTableSQL.append(" + \"'");
        }

        boolean setLine = "true".equals(ElementParameterParser.getValue(node, "__SET_LINES_TERMINATED_BY__"));
        if(setLine) {
            String lineChar = ElementParameterParser.getValue(node, "__LINES_TERMINATED_BY__");

    stringBuffer.append(TEXT_718);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_719);
    stringBuffer.append(lineChar);
    stringBuffer.append(TEXT_720);
    
            createTableSQL.append(" LINES TERMINATED BY '");
            createTableSQL.append("\" + ");
            createTableSQL.append("lineChar_");
            createTableSQL.append(cid);
            createTableSQL.append(" + \"'");
        }
    } else {
        createTableSQL.append("SERDE \\\"");
        createTableSQL.append("\" + ");
        String serdeName = ElementParameterParser.getValue(node, "__SERDE_NAME__");

    stringBuffer.append(TEXT_721);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_722);
    stringBuffer.append(serdeName);
    stringBuffer.append(TEXT_723);
    
        createTableSQL.append("serdeName_");
        createTableSQL.append(cid);
        createTableSQL.append(" + \"\\\"");

        util.generatePros(" WITH SERDEPROPERTIES ", serdeProps, createTableSQL);
    }
}

if (storeAsAvro || !hiveDistrib.doSupportStoreAsParquet() && storeAsParquet) {
    createTableSQL.append(" STORED AS INPUTFORMAT '" + (storeAsAvro?"org.apache.hadoop.hive.ql.io.avro.AvroContainerInputFormat":"parquet.hive.DeprecatedParquetInputFormat") + "'");
    createTableSQL.append(" OUTPUTFORMAT '" + (storeAsAvro?"org.apache.hadoop.hive.ql.io.avro.AvroContainerOutputFormat":"parquet.hive.DeprecatedParquetOutputFormat") + "'");
} else if(!"STORAGE".equals(storedFormat)) {
    createTableSQL.append(" STORED AS ");
    if("INPUTFORMAT_AND_OUTPUTFORMAT".equals(storedFormat)) {
        String inputClass = ElementParameterParser.getValue(node, "__INPUTFORMAT_CLASS__");
        String outputClass = ElementParameterParser.getValue(node, "__OUTPUTFORMAT_CLASS__");

    stringBuffer.append(TEXT_724);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_725);
    stringBuffer.append(inputClass);
    stringBuffer.append(TEXT_726);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_727);
    stringBuffer.append(outputClass);
    stringBuffer.append(TEXT_728);
    
        createTableSQL.append("INPUTFORMAT '");
        createTableSQL.append("\" + ");
        createTableSQL.append("inputClass_");
        createTableSQL.append(cid);
        createTableSQL.append(" + \"'");

        createTableSQL.append(" OUTPUTFORMAT '");
        createTableSQL.append("\" + ");
        createTableSQL.append("outputClass_");
        createTableSQL.append(cid);
        createTableSQL.append(" + \"'");
    } else {
        createTableSQL.append(storedFormat);
    }
} else {
    String storageClass = ElementParameterParser.getValue(node, "__STORAGE_CLASS__");

    stringBuffer.append(TEXT_729);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_730);
    stringBuffer.append(storageClass);
    stringBuffer.append(TEXT_731);
    
    createTableSQL.append(" STORED BY '");
    createTableSQL.append("\" + ");
    createTableSQL.append("storageClass_");
    createTableSQL.append(cid);
    createTableSQL.append(" + \"'");

    util.generatePros(" WITH SERDEPROPERTIES ", serdeProps, createTableSQL);
}

if(setLocation) {
    if (isS3Location) {
        String s3bucket = ElementParameterParser.getValue(node, "__S3_BUCKET__");
        String s3username = ElementParameterParser.getValue(node, "__S3_USERNAME__");

        String passwordFieldName = "__S3_PASSWORD__";
        // Get the decrypted password under the variable decryptedS3Password

        if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
            
    stringBuffer.append(TEXT_732);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_733);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_734);
    
        } else {
            
    stringBuffer.append(TEXT_735);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_736);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_737);
    
        }
        
    stringBuffer.append(TEXT_738);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_739);
    stringBuffer.append(s3username);
    stringBuffer.append(TEXT_740);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_741);
    stringBuffer.append(s3bucket);
    stringBuffer.append(TEXT_742);
    

    } else {

    stringBuffer.append(TEXT_743);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_744);
    stringBuffer.append(location);
    stringBuffer.append(TEXT_745);
    
    }
    createTableSQL.append(" LOCATION '");
    createTableSQL.append("\" + ");
    createTableSQL.append("location_");
    createTableSQL.append(cid);
    createTableSQL.append(" + \"'");
}

if(storeAsAvro) {
    StringBuilder avroSchemaBuilder = new StringBuilder();
    String quote = "\\\"";
    avroSchemaBuilder.append("'avro.schema.literal'='{").append(quote).append("name").append(quote).append(" : ").append(quote).append("row").append(quote)
    .append(", ").append(quote).append("type").append(quote).append(" : ").append(quote).append("record").append(quote)
    .append(", ").append(quote).append("fields").append(quote).append(" : [");
    util.generateAvroSchema(listColumn, avroSchemaBuilder, quote);
    avroSchemaBuilder.append("] }'");

    util.appendKeyValue(avroSchemaBuilder.toString());
}

util.generatePros(" TBLPROPERTIES ", tableProps, createTableSQL);

if(asSelect) {
    String sql = ElementParameterParser.getValue(node, "__SELECT__");
    sql = sql.replaceAll("\n"," ");
    sql = sql.replaceAll("\r"," ");

    stringBuffer.append(TEXT_746);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_747);
    stringBuffer.append(sql);
    stringBuffer.append(TEXT_748);
    
    createTableSQL.append(" AS ");
    createTableSQL.append("\" + ");
    createTableSQL.append("select_");
    createTableSQL.append(cid);
    createTableSQL.append(" + \"");
}

    stringBuffer.append(TEXT_749);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_750);
    stringBuffer.append(createTableSQL.toString());
    stringBuffer.append(TEXT_751);
    
    if(!hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_752);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_753);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_754);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_755);
    
            if(("true").equals(dieOnError)) {
    
    stringBuffer.append(TEXT_756);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_757);
    
    }else {
    
    stringBuffer.append(TEXT_758);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_759);
    
            }

    stringBuffer.append(TEXT_760);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_761);
    if(!("true").equals(useExistingConn)) {
    stringBuffer.append(TEXT_762);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_763);
    }
    stringBuffer.append(TEXT_764);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_765);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_766);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_767);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_768);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_769);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_770);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_771);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_772);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_773);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_774);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_775);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_776);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_777);
    
    } else {

    stringBuffer.append(TEXT_778);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_779);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_780);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_781);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_782);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_783);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_784);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_785);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_786);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_787);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_788);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_789);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_790);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_791);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_792);
    
            if(("true").equals(dieOnError)) {

    stringBuffer.append(TEXT_793);
    
            } else {

    stringBuffer.append(TEXT_794);
    
                if(isLog4jEnabled) {

    stringBuffer.append(TEXT_795);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_796);
    
                }
            }

    stringBuffer.append(TEXT_797);
    
    }

    return stringBuffer.toString();
  }
}
