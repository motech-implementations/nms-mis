package org.talend.designer.codegen.translators.elt.map.hive;

import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import java.util.List;
import java.util.Map;

public class TELTHiveOutputMainJava
{
  protected static String nl;
  public static synchronized TELTHiveOutputMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TELTHiveOutputMainJava result = new TELTHiveOutputMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "    String select_query_";
  protected final String TEXT_3 = " = null;" + NL + "    String tableName_";
  protected final String TEXT_4 = " = null;";
  protected final String TEXT_5 = NL + "        select_query_";
  protected final String TEXT_6 = " = (String) globalMap.get(\"";
  protected final String TEXT_7 = "\"+\"QUERY\"+\"";
  protected final String TEXT_8 = "\");" + NL;
  protected final String TEXT_9 = NL + "        String dbschema_";
  protected final String TEXT_10 = " = ";
  protected final String TEXT_11 = ";" + NL + "        if(dbschema_";
  protected final String TEXT_12 = " != null && dbschema_";
  protected final String TEXT_13 = ".trim().length() > 0) {" + NL + "             tableName_";
  protected final String TEXT_14 = " = ";
  protected final String TEXT_15 = " + \".\" + ";
  protected final String TEXT_16 = ";" + NL + "        } else {" + NL + "            tableName_";
  protected final String TEXT_17 = " = ";
  protected final String TEXT_18 = ";" + NL + "        }";
  protected final String TEXT_19 = NL + "\tjava.io.File localPigLatin_";
  protected final String TEXT_20 = " = new java.io.File(projectName + \"_\" + jobName + \"_\" + Thread.currentThread().getId() +\".hive\");" + NL + "\tjava.io.FileWriter fw_";
  protected final String TEXT_21 = " = new java.io.FileWriter(localPigLatin_";
  protected final String TEXT_22 = ".getAbsoluteFile());" + NL + "\tjava.io.BufferedWriter bw_";
  protected final String TEXT_23 = " = new java.io.BufferedWriter(fw_";
  protected final String TEXT_24 = ");" + NL + "\tjava.lang.StringBuilder libjars_";
  protected final String TEXT_25 = " = new StringBuilder();";
  protected final String TEXT_26 = " " + NL + "\t\t\tfinal String hdInsightPassword_";
  protected final String TEXT_27 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_28 = ");";
  protected final String TEXT_29 = NL + "\t\t\tfinal String hdInsightPassword_";
  protected final String TEXT_30 = " = ";
  protected final String TEXT_31 = "; ";
  protected final String TEXT_32 = " " + NL + "\t\t\tfinal String wasbPassword_";
  protected final String TEXT_33 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_34 = ");";
  protected final String TEXT_35 = NL + "\t\t\tfinal String wasbPassword_";
  protected final String TEXT_36 = " = ";
  protected final String TEXT_37 = "; ";
  protected final String TEXT_38 = NL + "\t\torg.talend.bigdata.launcher.fs.FileSystem azureFs_";
  protected final String TEXT_39 = " = new org.talend.bigdata.launcher.fs.AzureFileSystem(\"DefaultEndpointsProtocol=https;\"" + NL + "\t\t\t+ \"AccountName=\"" + NL + "\t\t\t+ ";
  protected final String TEXT_40 = NL + "\t\t\t+ \";\"" + NL + "\t\t\t+ \"AccountKey=\" + wasbPassword_";
  protected final String TEXT_41 = ", ";
  protected final String TEXT_42 = ");" + NL + "\t\t\t\t" + NL + "\t\torg.talend.bigdata.launcher.webhcat.WebHCatJob instance_";
  protected final String TEXT_43 = " = new org.talend.bigdata.launcher.webhcat.QueryJob(azureFs_";
  protected final String TEXT_44 = ", org.talend.bigdata.launcher.utils.JobType.HIVE);" + NL + "\t\t\t\t\t\t" + NL + "\t\tinstance_";
  protected final String TEXT_45 = ".setCredentials(new org.talend.bigdata.launcher.security.HDInsightCredentials(";
  protected final String TEXT_46 = ", hdInsightPassword_";
  protected final String TEXT_47 = "));" + NL + "\t\tinstance_";
  protected final String TEXT_48 = ".setUsername(";
  protected final String TEXT_49 = ");" + NL + "\t\tinstance_";
  protected final String TEXT_50 = ".setWebhcatEndpoint(\"https\", ";
  protected final String TEXT_51 = " + \":\" + ";
  protected final String TEXT_52 = ");" + NL + "\t\tinstance_";
  protected final String TEXT_53 = ".setStatusFolder(org.talend.bigdata.launcher.utils.Utils.removeFirstSlash(";
  protected final String TEXT_54 = "));" + NL + "\t\tinstance_";
  protected final String TEXT_55 = ".setRemoteFolder(org.talend.bigdata.launcher.utils.Utils.removeFirstSlash(";
  protected final String TEXT_56 = "));";
  protected final String TEXT_57 = NL + "\t\torg.talend.bigdata.launcher.webhcat.WebHCatJob instance_";
  protected final String TEXT_58 = " = (org.talend.bigdata.launcher.webhcat.WebHCatJob) globalMap.get(\"conn_";
  protected final String TEXT_59 = "\");" + NL + "\t\t" + NL + "\t\torg.talend.bigdata.launcher.fs.FileSystem azureFs_";
  protected final String TEXT_60 = " = instance_";
  protected final String TEXT_61 = ".getFileSystem();\t\t" + NL + "\t\t" + NL + "\t\tjava.util.List<String> connectionCommandList_";
  protected final String TEXT_62 = " = (java.util.List<String>)globalMap.get(\"commandList_";
  protected final String TEXT_63 = "\");" + NL + "\t\tfor(String command : connectionCommandList_";
  protected final String TEXT_64 = ") {" + NL + "\t\t\tbw_";
  protected final String TEXT_65 = ".write(command);" + NL + "\t\t}";
  protected final String TEXT_66 = NL + "\t((org.talend.bigdata.launcher.webhcat.QueryJob)instance_";
  protected final String TEXT_67 = ").setFileToExecute(projectName + \"_\" + jobName + \"_\" + Thread.currentThread().getId() +\".hive\");" + NL + "\tString wasbPath_";
  protected final String TEXT_68 = " = azureFs_";
  protected final String TEXT_69 = ".getFileSystemPrefix() + \"/\"\t+ instance_";
  protected final String TEXT_70 = ".getRemoteFolder()\t+ \"/libjars/\";";
  protected final String TEXT_71 = NL + "                bw_";
  protected final String TEXT_72 = ".write(\"SET mapreduce.map.memory.mb=\" + ";
  protected final String TEXT_73 = " + \";\");" + NL + "                bw_";
  protected final String TEXT_74 = ".write(\"SET mapreduce.reduce.memory.mb=\" + ";
  protected final String TEXT_75 = " + \";\");" + NL + "                bw_";
  protected final String TEXT_76 = ".write(\"SET yarn.app.mapreduce.am.resource.mb=\" + ";
  protected final String TEXT_77 = " + \";\");";
  protected final String TEXT_78 = NL + "                    bw_";
  protected final String TEXT_79 = ".write(\"SET \"+";
  protected final String TEXT_80 = "+\"=\"+";
  protected final String TEXT_81 = " + \";\");";
  protected final String TEXT_82 = NL + "            String dbname_";
  protected final String TEXT_83 = " = ";
  protected final String TEXT_84 = ";" + NL + "            if(dbname_";
  protected final String TEXT_85 = "!=null && !\"\".equals(dbname_";
  protected final String TEXT_86 = ".trim()) && !\"default\".equals(dbname_";
  protected final String TEXT_87 = ".trim())) {" + NL + "                bw_";
  protected final String TEXT_88 = ".write(\"use \" + dbname_";
  protected final String TEXT_89 = " + \";\");" + NL + "            }";
  protected final String TEXT_90 = NL + "        java.sql.Connection conn_";
  protected final String TEXT_91 = " = null;" + NL;
  protected final String TEXT_92 = NL + "        globalMap.put(\"current_client_path_separator\", System.getProperty(\"path.separator\"));" + NL + "        System.setProperty(\"path.separator\", ";
  protected final String TEXT_93 = ");";
  protected final String TEXT_94 = NL + "            conn_";
  protected final String TEXT_95 = " = (java.sql.Connection)globalMap.get(\"";
  protected final String TEXT_96 = "\");" + NL + "" + NL + "            String dbname_";
  protected final String TEXT_97 = " = (String)globalMap.get(\"";
  protected final String TEXT_98 = "\");" + NL + "            if(dbname_";
  protected final String TEXT_99 = "!=null && !\"\".equals(dbname_";
  protected final String TEXT_100 = ".trim()) && !\"default\".equals(dbname_";
  protected final String TEXT_101 = ".trim())) {" + NL + "                java.sql.Statement goToDatabase_";
  protected final String TEXT_102 = " = conn_";
  protected final String TEXT_103 = ".createStatement();" + NL + "                goToDatabase_";
  protected final String TEXT_104 = ".execute(\"use \" + dbname_";
  protected final String TEXT_105 = ");" + NL + "                goToDatabase_";
  protected final String TEXT_106 = ".close();" + NL + "            }" + NL + "" + NL + "            String dbUser_";
  protected final String TEXT_107 = " = (String)globalMap.get(\"";
  protected final String TEXT_108 = "\");" + NL + "" + NL + "" + NL + "            globalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_109 = "\", System.getProperty(\"HADOOP_USER_NAME\"));" + NL + "            if(dbUser_";
  protected final String TEXT_110 = "!=null && !\"\".equals(dbUser_";
  protected final String TEXT_111 = ".trim())) {" + NL + "                System.setProperty(\"HADOOP_USER_NAME\",dbUser_";
  protected final String TEXT_112 = ");" + NL + "                //make relative file path work for hive" + NL + "                globalMap.put(\"current_client_user_name\", System.getProperty(\"user.name\"));" + NL + "                System.setProperty(\"user.name\",dbUser_";
  protected final String TEXT_113 = ");" + NL + "            }";
  protected final String TEXT_114 = NL + "                if(true) {" + NL + "                    throw new java.lang.Exception(\"The distribution ";
  protected final String TEXT_115 = " does not support this version of Hive . Please check your component configuration.\");" + NL + "                }";
  protected final String TEXT_116 = NL + "                if(true) {" + NL + "                    throw new java.lang.Exception(\"The distribution ";
  protected final String TEXT_117 = " does not support this connection mode . Please check your component configuration.\");" + NL + "                }";
  protected final String TEXT_118 = NL + "                if(true) {" + NL + "                    throw new java.lang.Exception(\"The Hive version and the connection mode are not compatible together. Please check your component configuration.\");" + NL + "                }";
  protected final String TEXT_119 = NL + "            String dbUser_";
  protected final String TEXT_120 = " = ";
  protected final String TEXT_121 = ";" + NL;
  protected final String TEXT_122 = NL;
  protected final String TEXT_123 = NL + "            ";
  protected final String TEXT_124 = "String decryptedPassword_";
  protected final String TEXT_125 = " = null;";
  protected final String TEXT_126 = " " + NL + "\tdecryptedPassword_";
  protected final String TEXT_127 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_128 = ");";
  protected final String TEXT_129 = NL + "\tdecryptedPassword_";
  protected final String TEXT_130 = " = ";
  protected final String TEXT_131 = "; ";
  protected final String TEXT_132 = NL + NL + "            String dbPwd_";
  protected final String TEXT_133 = " = decryptedPassword_";
  protected final String TEXT_134 = ";" + NL;
  protected final String TEXT_135 = NL + "                String username_";
  protected final String TEXT_136 = " = ";
  protected final String TEXT_137 = ";" + NL + "                if(username_";
  protected final String TEXT_138 = "!=null && !\"\".equals(username_";
  protected final String TEXT_139 = ".trim())) {" + NL + "                    System.setProperty(\"HADOOP_USER_NAME\",username_";
  protected final String TEXT_140 = ");" + NL + "                }";
  protected final String TEXT_141 = NL + NL + "            globalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_142 = "\", System.getProperty(\"HADOOP_USER_NAME\"));";
  protected final String TEXT_143 = NL + "                System.setProperty(\"hive.metastore.local\", \"false\");" + NL + "                System.setProperty(\"hive.metastore.uris\", \"thrift://\" + ";
  protected final String TEXT_144 = " + \":\" + ";
  protected final String TEXT_145 = ");" + NL + "                System.setProperty(\"hive.metastore.execute.setugi\", \"true\");" + NL + "                String url_";
  protected final String TEXT_146 = " = \"jdbc:";
  protected final String TEXT_147 = "://\";";
  protected final String TEXT_148 = NL + "                    if(dbUser_";
  protected final String TEXT_149 = "!=null && !\"\".equals(dbUser_";
  protected final String TEXT_150 = ".trim())) {" + NL + "                        System.setProperty(\"HADOOP_USER_NAME\",dbUser_";
  protected final String TEXT_151 = ");" + NL + "                        //make relative file path work for hive" + NL + "                        globalMap.put(\"current_client_user_name\", System.getProperty(\"user.name\"));" + NL + "                        System.setProperty(\"user.name\",dbUser_";
  protected final String TEXT_152 = ");" + NL + "                    }";
  protected final String TEXT_153 = NL + "                                String decryptedSslStorePassword_";
  protected final String TEXT_154 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_155 = ");";
  protected final String TEXT_156 = NL + "                                String decryptedSslStorePassword_";
  protected final String TEXT_157 = " = ";
  protected final String TEXT_158 = ";";
  protected final String TEXT_159 = NL + "                            String url_";
  protected final String TEXT_160 = " = \"jdbc:";
  protected final String TEXT_161 = "://\" + ";
  protected final String TEXT_162 = " + \":\" + ";
  protected final String TEXT_163 = " + \"/\" + ";
  protected final String TEXT_164 = " + \";principal=\" + ";
  protected final String TEXT_165 = "+\";ssl=true\" +\";sslTrustStore=\" + ";
  protected final String TEXT_166 = " + \";trustStorePassword=\" + decryptedSslStorePassword_";
  protected final String TEXT_167 = ";";
  protected final String TEXT_168 = NL + "                            String url_";
  protected final String TEXT_169 = " = \"jdbc:";
  protected final String TEXT_170 = "://\" + ";
  protected final String TEXT_171 = " + \":\" + ";
  protected final String TEXT_172 = " + \"/\" + ";
  protected final String TEXT_173 = " + \";principal=\" + ";
  protected final String TEXT_174 = "+\";sasl.qop=auth-conf\";";
  protected final String TEXT_175 = NL + "                        String url_";
  protected final String TEXT_176 = " = \"jdbc:";
  protected final String TEXT_177 = "://\" + ";
  protected final String TEXT_178 = " + \":\" + ";
  protected final String TEXT_179 = " + \"/\" + ";
  protected final String TEXT_180 = " + \";principal=\" + ";
  protected final String TEXT_181 = ";";
  protected final String TEXT_182 = NL + "                                String decryptedSslStorePassword_";
  protected final String TEXT_183 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_184 = ");";
  protected final String TEXT_185 = NL + "                                String decryptedSslStorePassword_";
  protected final String TEXT_186 = " = ";
  protected final String TEXT_187 = ";";
  protected final String TEXT_188 = NL + "                            String decryptedSslStorePassword_";
  protected final String TEXT_189 = " = \"\";";
  protected final String TEXT_190 = NL + "                        String url_";
  protected final String TEXT_191 = " = \"jdbc:";
  protected final String TEXT_192 = "://\" + ";
  protected final String TEXT_193 = " + \":\" + ";
  protected final String TEXT_194 = " + \"/\" + ";
  protected final String TEXT_195 = "+ \";ssl=true\" +\";sslTrustStore=\" + ";
  protected final String TEXT_196 = " + \";trustStorePassword=\" + decryptedSslStorePassword_";
  protected final String TEXT_197 = ";";
  protected final String TEXT_198 = NL + "                        String url_";
  protected final String TEXT_199 = " = \"jdbc:";
  protected final String TEXT_200 = "://\" + ";
  protected final String TEXT_201 = " + \":\" + ";
  protected final String TEXT_202 = " + \"/\" + ";
  protected final String TEXT_203 = ";";
  protected final String TEXT_204 = NL + "                String additionalJdbcSettings_";
  protected final String TEXT_205 = " = ";
  protected final String TEXT_206 = ";" + NL + "                if(!\"\".equals(additionalJdbcSettings_";
  protected final String TEXT_207 = ".trim())) {" + NL + "                    if(!additionalJdbcSettings_";
  protected final String TEXT_208 = ".startsWith(\";\")) {" + NL + "                        additionalJdbcSettings_";
  protected final String TEXT_209 = " = \";\" + additionalJdbcSettings_";
  protected final String TEXT_210 = ";" + NL + "                    }" + NL + "                    url_";
  protected final String TEXT_211 = " += additionalJdbcSettings_";
  protected final String TEXT_212 = ";" + NL + "                }";
  protected final String TEXT_213 = NL + "            java.lang.Class.forName(\"";
  protected final String TEXT_214 = "\");";
  protected final String TEXT_215 = NL + "                conn_";
  protected final String TEXT_216 = " = java.sql.DriverManager.getConnection(url_";
  protected final String TEXT_217 = ");";
  protected final String TEXT_218 = NL + "                conn_";
  protected final String TEXT_219 = " = java.sql.DriverManager.getConnection(url_";
  protected final String TEXT_220 = ", dbUser_";
  protected final String TEXT_221 = ", dbPwd_";
  protected final String TEXT_222 = ");";
  protected final String TEXT_223 = NL + "            java.sql.Statement init_";
  protected final String TEXT_224 = " = conn_";
  protected final String TEXT_225 = ".createStatement();";
  protected final String TEXT_226 = NL + "                init_";
  protected final String TEXT_227 = ".execute(\"SET mapred.job.map.memory.mb=\" + ";
  protected final String TEXT_228 = ");" + NL + "                init_";
  protected final String TEXT_229 = ".execute(\"SET mapred.job.reduce.memory.mb=\" + ";
  protected final String TEXT_230 = ");";
  protected final String TEXT_231 = NL + "                init_";
  protected final String TEXT_232 = ".execute(\"SET dfs.namenode.kerberos.principal=\" + ";
  protected final String TEXT_233 = ");";
  protected final String TEXT_234 = NL + "                    init_";
  protected final String TEXT_235 = ".execute(\"SET mapreduce.jobtracker.kerberos.principal=\" + ";
  protected final String TEXT_236 = ");";
  protected final String TEXT_237 = NL + "                    init_";
  protected final String TEXT_238 = ".execute(\"SET yarn.resourcemanager.principal=\" + ";
  protected final String TEXT_239 = ");";
  protected final String TEXT_240 = NL + "                    init_";
  protected final String TEXT_241 = ".execute(\"SET mapreduce.framework.name=yarn\");" + NL + "                    init_";
  protected final String TEXT_242 = ".execute(\"SET yarn.resourcemanager.address=\" + ";
  protected final String TEXT_243 = ");";
  protected final String TEXT_244 = NL + "                    init_";
  protected final String TEXT_245 = ".execute(\"SET mapreduce.jobhistory.address=\" + ";
  protected final String TEXT_246 = ");";
  protected final String TEXT_247 = NL + "                    init_";
  protected final String TEXT_248 = ".execute(\"SET yarn.resourcemanager.scheduler.address=\" + ";
  protected final String TEXT_249 = ");";
  protected final String TEXT_250 = NL + "                    init_";
  protected final String TEXT_251 = ".execute(\"SET dfs.client.use.datanode.hostname=true\");";
  protected final String TEXT_252 = NL + "                    init_";
  protected final String TEXT_253 = ".execute(\"SET fs.default.name=\" + ";
  protected final String TEXT_254 = ");";
  protected final String TEXT_255 = NL + "                        init_";
  protected final String TEXT_256 = ".execute(\"SET mapreduce.app-submission.cross-platform=true\");";
  protected final String TEXT_257 = NL + "                        init_";
  protected final String TEXT_258 = ".execute(\"SET mapreduce.job.map.output.collector.class=org.apache.hadoop.mapred.MapRFsOutputBuffer\");" + NL + "                        init_";
  protected final String TEXT_259 = ".execute(\"SET mapreduce.job.reduce.shuffle.consumer.plugin.class=org.apache.hadoop.mapreduce.task.reduce.DirectShuffle\");";
  protected final String TEXT_260 = NL + "                        init_";
  protected final String TEXT_261 = ".execute(\"SET mapreduce.application.classpath=";
  protected final String TEXT_262 = "\");";
  protected final String TEXT_263 = NL + "                    init_";
  protected final String TEXT_264 = ".execute(\"SET yarn.application.classpath=";
  protected final String TEXT_265 = "\");";
  protected final String TEXT_266 = NL + "                    init_";
  protected final String TEXT_267 = ".execute(\"SET mapreduce.map.memory.mb=\" + ";
  protected final String TEXT_268 = ");" + NL + "                    init_";
  protected final String TEXT_269 = ".execute(\"SET mapreduce.reduce.memory.mb=\" + ";
  protected final String TEXT_270 = ");" + NL + "                    init_";
  protected final String TEXT_271 = ".execute(\"SET yarn.app.mapreduce.am.resource.mb=\" + ";
  protected final String TEXT_272 = ");";
  protected final String TEXT_273 = NL + "                    init_";
  protected final String TEXT_274 = ".execute(\"SET \"+";
  protected final String TEXT_275 = "+\"=\"+";
  protected final String TEXT_276 = ");";
  protected final String TEXT_277 = NL;
  protected final String TEXT_278 = NL + "        \t   ";
  protected final String TEXT_279 = NL;
  protected final String TEXT_280 = NL + "    \t\tinit_";
  protected final String TEXT_281 = ".execute(\"SET hive.execution.engine=tez\");";
  protected final String TEXT_282 = NL + "                        System.err.println(\"Please set the path of Tez lib in 'Tez lib path'!\");";
  protected final String TEXT_283 = NL;
  protected final String TEXT_284 = NL + "        String username_";
  protected final String TEXT_285 = " = (";
  protected final String TEXT_286 = " != null && !\"\".equals(";
  protected final String TEXT_287 = ")) ? ";
  protected final String TEXT_288 = " : System.getProperty(\"user.name\");;" + NL + "        org.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_289 = " = null;";
  protected final String TEXT_290 = NL + "        org.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_291 = " = new org.apache.hadoop.conf.Configuration(); " + NL + "        conf_";
  protected final String TEXT_292 = ".set(\"";
  protected final String TEXT_293 = "\", ";
  protected final String TEXT_294 = ");" + NL + "        ";
  protected final String TEXT_295 = NL + "            conf_";
  protected final String TEXT_296 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_297 = NL + "        \t\tconf_";
  protected final String TEXT_298 = ".set(";
  protected final String TEXT_299 = " ,";
  protected final String TEXT_300 = ");" + NL + "        \t";
  protected final String TEXT_301 = NL + "            username_";
  protected final String TEXT_302 = " = null;" + NL + "    \t\tconf_";
  protected final String TEXT_303 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_304 = ");" + NL + "    \t\t";
  protected final String TEXT_305 = NL + "    \t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_306 = ", ";
  protected final String TEXT_307 = ");" + NL + "    \t\t";
  protected final String TEXT_308 = NL + "\t\t\tconf_";
  protected final String TEXT_309 = ".set(\"hadoop.job.ugi\",username_";
  protected final String TEXT_310 = "+\",\"+username_";
  protected final String TEXT_311 = ");" + NL + "        \tfs_";
  protected final String TEXT_312 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_313 = ");";
  protected final String TEXT_314 = NL + "        \t" + NL + "        \tif(username_";
  protected final String TEXT_315 = " == null || \"\".equals(username_";
  protected final String TEXT_316 = ")){" + NL + "        \t\tfs_";
  protected final String TEXT_317 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_318 = ");" + NL + "        \t}else{" + NL + "        \t\tfs_";
  protected final String TEXT_319 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_320 = ".get(\"";
  protected final String TEXT_321 = "\")),conf_";
  protected final String TEXT_322 = ",username_";
  protected final String TEXT_323 = ");" + NL + "        \t}\t";
  protected final String TEXT_324 = NL + "                    String hdfsUserName_";
  protected final String TEXT_325 = " = (";
  protected final String TEXT_326 = " != null && !\"\".equals(";
  protected final String TEXT_327 = ")) ? ";
  protected final String TEXT_328 = " : System.getProperty(\"user.name\");" + NL + "                    String tezLibPath_";
  protected final String TEXT_329 = " = \"/tmp/\" + hdfsUserName_";
  protected final String TEXT_330 = " + \"/talend_tez_libs/";
  protected final String TEXT_331 = "\";";
  protected final String TEXT_332 = NL + "                    String tezLibPath_";
  protected final String TEXT_333 = " = ";
  protected final String TEXT_334 = ";";
  protected final String TEXT_335 = NL + "                fs_";
  protected final String TEXT_336 = ".mkdirs(new org.apache.hadoop.fs.Path(tezLibPath_";
  protected final String TEXT_337 = "));";
  protected final String TEXT_338 = NL + "                String[] classPaths_";
  protected final String TEXT_339 = " = System.getProperty(\"java.class.path\").split(";
  protected final String TEXT_340 = "String.valueOf(globalMap.get(\"current_client_path_separator\"))";
  protected final String TEXT_341 = "System.getProperty(\"path.separator\")";
  protected final String TEXT_342 = ");" + NL + "                String tezLibLocalPath_";
  protected final String TEXT_343 = " = null;" + NL + "                for(String classPath_";
  protected final String TEXT_344 = " : classPaths_";
  protected final String TEXT_345 = "){";
  protected final String TEXT_346 = NL + "                        if(classPath_";
  protected final String TEXT_347 = ".endsWith(\"";
  protected final String TEXT_348 = "\")){" + NL + "                            org.apache.hadoop.fs.Path tezJarPath_";
  protected final String TEXT_349 = " = new org.apache.hadoop.fs.Path(tezLibPath_";
  protected final String TEXT_350 = " + \"/";
  protected final String TEXT_351 = "\");" + NL + "                            if(!fs_";
  protected final String TEXT_352 = ".exists(tezJarPath_";
  protected final String TEXT_353 = ")){" + NL + "                                fs_";
  protected final String TEXT_354 = ".copyFromLocalFile(false, false, new org.apache.hadoop.fs.Path(classPath_";
  protected final String TEXT_355 = "), tezJarPath_";
  protected final String TEXT_356 = ");" + NL + "                            }" + NL + "                        }";
  protected final String TEXT_357 = NL + "                }";
  protected final String TEXT_358 = NL + "                String tezLibPath_";
  protected final String TEXT_359 = " = ";
  protected final String TEXT_360 = ";";
  protected final String TEXT_361 = NL + "\t\t\tStringBuilder script_";
  protected final String TEXT_362 = " = new StringBuilder();" + NL + "\t\t\tString[] tezLibPaths_";
  protected final String TEXT_363 = " = tezLibPath_";
  protected final String TEXT_364 = ".split(\",\");" + NL + "\t\t\tscript_";
  protected final String TEXT_365 = ".append(\"SET tez.lib.uris=\");" + NL + "\t\t\tint tezLibPathCount_";
  protected final String TEXT_366 = " = 1;" + NL + "\t\t\tfor(String tezLibPathKey_";
  protected final String TEXT_367 = " : tezLibPaths_";
  protected final String TEXT_368 = "){" + NL + "\t\t\t\t script_";
  protected final String TEXT_369 = ".append(";
  protected final String TEXT_370 = " + \"/\" + tezLibPathKey_";
  protected final String TEXT_371 = ");" + NL + "\t\t\t\t if(tezLibPathCount_";
  protected final String TEXT_372 = " < tezLibPaths_";
  protected final String TEXT_373 = ".length){" + NL + "\t\t\t\t \tscript_";
  protected final String TEXT_374 = ".append(\",\");" + NL + "\t\t\t\t }" + NL + "\t\t\t\t tezLibPathCount_";
  protected final String TEXT_375 = "++;" + NL + "\t\t\t}" + NL + "\t\t\tinit_";
  protected final String TEXT_376 = ".execute(script_";
  protected final String TEXT_377 = ".toString());" + NL + "\t\t";
  protected final String TEXT_378 = NL + "            init_";
  protected final String TEXT_379 = ".close();" + NL + "" + NL + "            String dbname_";
  protected final String TEXT_380 = " = ";
  protected final String TEXT_381 = ";" + NL + "            if(dbname_";
  protected final String TEXT_382 = "!=null && !\"\".equals(dbname_";
  protected final String TEXT_383 = ".trim()) && !\"default\".equals(dbname_";
  protected final String TEXT_384 = ".trim())) {" + NL + "                java.sql.Statement goToDatabase_";
  protected final String TEXT_385 = " = conn_";
  protected final String TEXT_386 = ".createStatement();" + NL + "                goToDatabase_";
  protected final String TEXT_387 = ".execute(\"use \" + dbname_";
  protected final String TEXT_388 = ");" + NL + "                goToDatabase_";
  protected final String TEXT_389 = ".close();" + NL + "            }";
  protected final String TEXT_390 = NL + "                java.sql.Statement statement_";
  protected final String TEXT_391 = " = conn_";
  protected final String TEXT_392 = ".createStatement();";
  protected final String TEXT_393 = NL + "                    statement_";
  protected final String TEXT_394 = ".execute(\"SET hbase.zookeeper.quorum=\"+";
  protected final String TEXT_395 = ");";
  protected final String TEXT_396 = NL;
  protected final String TEXT_397 = NL + "                    statement_";
  protected final String TEXT_398 = ".execute(\"SET hbase.zookeeper.property.clientPort=\"+";
  protected final String TEXT_399 = ");";
  protected final String TEXT_400 = NL;
  protected final String TEXT_401 = NL + "                    statement_";
  protected final String TEXT_402 = ".execute(\"SET zookeeper.znode.parent=\"+";
  protected final String TEXT_403 = ");";
  protected final String TEXT_404 = NL;
  protected final String TEXT_405 = NL + "                        statement_";
  protected final String TEXT_406 = ".execute(\"add jar \"+";
  protected final String TEXT_407 = ");";
  protected final String TEXT_408 = NL + "                statement_";
  protected final String TEXT_409 = ".close();";
  protected final String TEXT_410 = NL + "        if(true) {" + NL + "            throw new java.lang.UnsupportedOperationException(\"Parquet is only supported if the distribution uses embedded Hive version 0.10 or later.\");" + NL + "        }";
  protected final String TEXT_411 = NL + "                routines.system.GetJarsToRegister getJarsToRegister_";
  protected final String TEXT_412 = " = new GetJarsToRegister();";
  protected final String TEXT_413 = NL + "\tclass GetHiveJarsToRegister_";
  protected final String TEXT_414 = " extends routines.system.GetJarsToRegister {" + NL + "\t\t" + NL + "\t\tprivate String uploadJarToHDFS(String jar) throws Exception {" + NL + "\t\t\t";
  protected final String TEXT_415 = NL;
  protected final String TEXT_416 = NL + "        String username_";
  protected final String TEXT_417 = " = (";
  protected final String TEXT_418 = " != null && !\"\".equals(";
  protected final String TEXT_419 = ")) ? ";
  protected final String TEXT_420 = " : System.getProperty(\"user.name\");;" + NL + "        org.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_421 = " = null;";
  protected final String TEXT_422 = NL + "        org.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_423 = " = new org.apache.hadoop.conf.Configuration(); " + NL + "        conf_";
  protected final String TEXT_424 = ".set(\"";
  protected final String TEXT_425 = "\", ";
  protected final String TEXT_426 = ");" + NL + "        ";
  protected final String TEXT_427 = NL + "            conf_";
  protected final String TEXT_428 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_429 = NL + "        \t\tconf_";
  protected final String TEXT_430 = ".set(";
  protected final String TEXT_431 = " ,";
  protected final String TEXT_432 = ");" + NL + "        \t";
  protected final String TEXT_433 = NL + "            username_";
  protected final String TEXT_434 = " = null;" + NL + "    \t\tconf_";
  protected final String TEXT_435 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_436 = ");" + NL + "    \t\t";
  protected final String TEXT_437 = NL + "    \t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_438 = ", ";
  protected final String TEXT_439 = ");" + NL + "    \t\t";
  protected final String TEXT_440 = NL + "\t\t\tconf_";
  protected final String TEXT_441 = ".set(\"hadoop.job.ugi\",username_";
  protected final String TEXT_442 = "+\",\"+username_";
  protected final String TEXT_443 = ");" + NL + "        \tfs_";
  protected final String TEXT_444 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_445 = ");";
  protected final String TEXT_446 = NL + "        \t" + NL + "        \tif(username_";
  protected final String TEXT_447 = " == null || \"\".equals(username_";
  protected final String TEXT_448 = ")){" + NL + "        \t\tfs_";
  protected final String TEXT_449 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_450 = ");" + NL + "        \t}else{" + NL + "        \t\tfs_";
  protected final String TEXT_451 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_452 = ".get(\"";
  protected final String TEXT_453 = "\")),conf_";
  protected final String TEXT_454 = ",username_";
  protected final String TEXT_455 = ");" + NL + "        \t}\t";
  protected final String TEXT_456 = NL + "\t\t\t";
  protected final String TEXT_457 = NL + "\t\t\t" + NL + "\t\t\tString pathUsername_";
  protected final String TEXT_458 = " = username_";
  protected final String TEXT_459 = " == null ? org.apache.hadoop.security.UserGroupInformation.getLoginUser().getShortUserName() : username_";
  protected final String TEXT_460 = ";" + NL + "\t\t\tfs_";
  protected final String TEXT_461 = ".mkdirs(new org.apache.hadoop.fs.Path(\"/user/\" + pathUsername_";
  protected final String TEXT_462 = " + \"/tmp\"), new org.apache.hadoop.fs.permission.FsPermission(org.apache.hadoop.fs.permission.FsAction.ALL, org.apache.hadoop.fs.permission.FsAction.ALL, org.apache.hadoop.fs.permission.FsAction.ALL));" + NL + "\t\t\tfs_";
  protected final String TEXT_463 = ".copyFromLocalFile(false, true, new org.apache.hadoop.fs.Path(jar), new org.apache.hadoop.fs.Path(\"/user/\" + pathUsername_";
  protected final String TEXT_464 = " + \"/tmp\"));" + NL + "\t\t\treturn ";
  protected final String TEXT_465 = " + \"/user/\" + pathUsername_";
  protected final String TEXT_466 = " + \"/tmp/\" + new java.io.File(jar).getName();" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tpublic String replaceJarPaths(String originalClassPathLine) throws Exception {" + NL + "\t\t\tString classPathLine = super.replaceJarPaths(originalClassPathLine);" + NL + "\t\t\tString hdfsPath = uploadJarToHDFS(classPathLine);" + NL + "\t\t\treturn hdfsPath;" + NL + "\t\t}" + NL + "\t}" + NL + "                    GetHiveJarsToRegister_";
  protected final String TEXT_467 = " getJarsToRegister_";
  protected final String TEXT_468 = " = new GetHiveJarsToRegister_";
  protected final String TEXT_469 = "();";
  protected final String TEXT_470 = NL + "                java.sql.Statement addJar_";
  protected final String TEXT_471 = " = null;";
  protected final String TEXT_472 = NL + "                            addJar_";
  protected final String TEXT_473 = " = conn_";
  protected final String TEXT_474 = ".createStatement();" + NL + "                            try {" + NL + "                                addJar_";
  protected final String TEXT_475 = ".execute(\"add jar \" + getJarsToRegister_";
  protected final String TEXT_476 = ".replaceJarPaths(\"";
  protected final String TEXT_477 = "\"));" + NL + "                            } catch (Exception e) {" + NL + "                                e.printStackTrace();" + NL + "                            } finally {" + NL + "                                addJar_";
  protected final String TEXT_478 = ".close();" + NL + "                            }";
  protected final String TEXT_479 = NL + "                                bw_";
  protected final String TEXT_480 = ".write(\"ADD JAR \" + wasbPath_";
  protected final String TEXT_481 = " + new java.io.File(getJarsToRegister_";
  protected final String TEXT_482 = ".replaceJarPaths(\"";
  protected final String TEXT_483 = "\")).getName() + \";\");" + NL + "                                libjars_";
  protected final String TEXT_484 = ".append(getJarsToRegister_";
  protected final String TEXT_485 = ".replaceJarPaths(\"";
  protected final String TEXT_486 = "\") + \",\");";
  protected final String TEXT_487 = NL + "            java.sql.Statement setCompression_";
  protected final String TEXT_488 = " = conn_";
  protected final String TEXT_489 = ".createStatement();" + NL + "            try {" + NL + "                setCompression_";
  protected final String TEXT_490 = ".execute(\"SET parquet.compression=";
  protected final String TEXT_491 = "\");" + NL + "            } finally {" + NL + "                setCompression_";
  protected final String TEXT_492 = ".close();" + NL + "            }";
  protected final String TEXT_493 = NL + "            bw_";
  protected final String TEXT_494 = ".write(\"SET parquet.compression=";
  protected final String TEXT_495 = ";\");";
  protected final String TEXT_496 = NL + "        java.sql.Statement stmt_";
  protected final String TEXT_497 = " = conn_";
  protected final String TEXT_498 = ".createStatement();";
  protected final String TEXT_499 = NL + "    StringBuffer partitionSql_";
  protected final String TEXT_500 = " = new StringBuffer();" + NL + "    String startPartition_";
  protected final String TEXT_501 = " = \"\";" + NL + "    String endPartition_";
  protected final String TEXT_502 = " = \"\";" + NL + "    String bodyPartition_";
  protected final String TEXT_503 = " = \"\";";
  protected final String TEXT_504 = NL + "    startPartition_";
  protected final String TEXT_505 = " = \" PARTITION(\";" + NL + "    endPartition_";
  protected final String TEXT_506 = " = \")\";";
  protected final String TEXT_507 = NL + "                bodyPartition_";
  protected final String TEXT_508 = " = bodyPartition_";
  protected final String TEXT_509 = " + ";
  protected final String TEXT_510 = ";";
  protected final String TEXT_511 = NL + "                       bodyPartition_";
  protected final String TEXT_512 = " = bodyPartition_";
  protected final String TEXT_513 = " + \"=\";" + NL + "                       bodyPartition_";
  protected final String TEXT_514 = " = bodyPartition_";
  protected final String TEXT_515 = " + ";
  protected final String TEXT_516 = ";";
  protected final String TEXT_517 = NL + "                    bodyPartition_";
  protected final String TEXT_518 = " = bodyPartition_";
  protected final String TEXT_519 = " + \",\";";
  protected final String TEXT_520 = NL + "    partitionSql_";
  protected final String TEXT_521 = ".append(startPartition_";
  protected final String TEXT_522 = ").append(bodyPartition_";
  protected final String TEXT_523 = ").append(endPartition_";
  protected final String TEXT_524 = ");" + NL;
  protected final String TEXT_525 = NL + "    String insertQuery_";
  protected final String TEXT_526 = " = \"INSERT INTO TABLE \"+tableName_";
  protected final String TEXT_527 = " + partitionSql_";
  protected final String TEXT_528 = ".toString() + \" \"+select_query_";
  protected final String TEXT_529 = ";";
  protected final String TEXT_530 = NL + "    stmt_";
  protected final String TEXT_531 = ".execute(insertQuery_";
  protected final String TEXT_532 = ");";
  protected final String TEXT_533 = NL + "                bw_";
  protected final String TEXT_534 = ".write(insertQuery_";
  protected final String TEXT_535 = " + \";\");";
  protected final String TEXT_536 = NL + "    String overwriteQuery_";
  protected final String TEXT_537 = " = \"INSERT OVERWRITE TABLE \"+tableName_";
  protected final String TEXT_538 = "+ partitionSql_";
  protected final String TEXT_539 = ".toString() + \" \"+select_query_";
  protected final String TEXT_540 = ";";
  protected final String TEXT_541 = NL + "    stmt_";
  protected final String TEXT_542 = ".execute(overwriteQuery_";
  protected final String TEXT_543 = ");" + NL;
  protected final String TEXT_544 = NL + "                bw_";
  protected final String TEXT_545 = ".write(overwriteQuery_";
  protected final String TEXT_546 = " + \";\");";
  protected final String TEXT_547 = NL + "stmt_";
  protected final String TEXT_548 = ".close();" + NL;
  protected final String TEXT_549 = NL + "    if(conn_";
  protected final String TEXT_550 = " != null && !conn_";
  protected final String TEXT_551 = ".isClosed()) {" + NL + "        conn_";
  protected final String TEXT_552 = " .close();" + NL + "    }";
  protected final String TEXT_553 = NL + NL + "String currentClientPathSeparator_";
  protected final String TEXT_554 = " = (String)globalMap.get(\"current_client_path_separator\");" + NL + "if(currentClientPathSeparator_";
  protected final String TEXT_555 = "!=null) {" + NL + "    System.setProperty(\"path.separator\", currentClientPathSeparator_";
  protected final String TEXT_556 = ");" + NL + "    globalMap.put(\"current_client_path_separator\", null);" + NL + "}" + NL + "" + NL + "String currentClientUsername_";
  protected final String TEXT_557 = " = (String)globalMap.get(\"current_client_user_name\");" + NL + "if(currentClientUsername_";
  protected final String TEXT_558 = "!=null) {" + NL + "    System.setProperty(\"user.name\", currentClientUsername_";
  protected final String TEXT_559 = ");" + NL + "    globalMap.put(\"current_client_user_name\", null);" + NL + "}" + NL + "" + NL + "String originalHadoopUsername_";
  protected final String TEXT_560 = " = (String)globalMap.get(\"HADOOP_USER_NAME_";
  protected final String TEXT_561 = "\");" + NL + "if(originalHadoopUsername_";
  protected final String TEXT_562 = "!=null) {" + NL + "    System.setProperty(\"HADOOP_USER_NAME\", originalHadoopUsername_";
  protected final String TEXT_563 = ");" + NL + "    globalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_564 = "\", null);" + NL + "} else {" + NL + "    System.clearProperty(\"HADOOP_USER_NAME\");" + NL + "}";
  protected final String TEXT_565 = NL + "        bw_";
  protected final String TEXT_566 = ".close();" + NL + "" + NL + "        if(libjars_";
  protected final String TEXT_567 = ".length() > 0) {" + NL + "            instance_";
  protected final String TEXT_568 = ".setLibJars(libjars_";
  protected final String TEXT_569 = ".toString().substring(0, libjars_";
  protected final String TEXT_570 = ".length()-1));" + NL + "        }" + NL + "" + NL + "        instance_";
  protected final String TEXT_571 = ".callWS(instance_";
  protected final String TEXT_572 = ".sendFiles());" + NL + "        int exitCode_";
  protected final String TEXT_573 = " = instance_";
  protected final String TEXT_574 = ".execute();" + NL + "        if(exitCode_";
  protected final String TEXT_575 = " > 0) {" + NL + "            throw new Exception(\"The Hive job failed. Please read the logs for more details\");" + NL + "        }";
  protected final String TEXT_576 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();

    String cid = node.getUniqueName();
    String processId = node.getProcess().getId();

    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

    String dbtable = null;
    String uniqueNameConnection = null;
    INode previousNode = null;
    boolean setFsDefaultName=false;
    String connectionMode = "";
    String fsDefaultName = "";
    org.talend.hadoop.distribution.component.HiveComponent hiveDistrib = null;
    boolean isCustom = false;
    INode connectionInformationNode = node;

    
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    
    List<IConnection> connections = (List<IConnection>) node.getIncomingConnections();
    if(connections != null && connections.size() > 0 && connections.get(0) != null) {
        IConnection connection = connections.get(0);
        previousNode = connection.getSource();
        String previousComponentName = previousNode.getUniqueName();
        dbtable = connection.getName();
        uniqueNameConnection = connection.getUniqueName();

        
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(previousComponentName);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uniqueNameConnection);
    stringBuffer.append(TEXT_8);
    
    }

    String differenttable = ElementParameterParser.getValue(node, "__DIFFERENT_TABLE_NAME__");
    boolean useDifferentTable = "true".equals(ElementParameterParser.getValue(node, "__USE_DIFFERENT_TABLE__"));

       String dbschema = ElementParameterParser.getValue(node,"__ELT_SCHEMA_NAME__");
    
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(dbschema);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(dbschema);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(useDifferentTable? differenttable:"\""+dbtable +"\"");
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(useDifferentTable? differenttable:"\""+dbtable +"\"");
    stringBuffer.append(TEXT_18);
    

    String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");

    List<Map<String, String>> fieldPartitions = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__FIELD_PARTITION__");

    String dbhost = null;
    String dbport = null;
    String dbname = null;
    String dbuser = null;
    String hiveVersion = null;
    String distribution = null;

    //hbase settings
       String storeByHBase = null;
    String zookeeperQuorumForHBase = null;
    String zookeeperClientPortForHBase = null;

    boolean setZNodeParent = false;
    String zNodeParent = null;

    String sslTrustStore = null;
    String sslStorepasswordFieldName = null;

    boolean useSsl = false;
    String additionalJdbcSettings = "";

    String defineRegisterJar = null;
    List<Map<String, String>> registerJarForHBase = null;

    if(previousNode != null) {
        dbhost = ElementParameterParser.getValue(previousNode, "__HOST__");
        dbport = ElementParameterParser.getValue(previousNode, "__PORT__");
        dbname = ElementParameterParser.getValue(previousNode, "__DBNAME__");
        dbuser = ElementParameterParser.getValue(previousNode, "__USER__");
        connectionInformationNode = previousNode;
        hiveVersion = ElementParameterParser.getValue(previousNode, "__HIVE_VERSION__");
        distribution = ElementParameterParser.getValue(previousNode, "__DISTRIBUTION__");

        additionalJdbcSettings = ElementParameterParser.getValue(previousNode, "__HIVE_ADDITIONAL_JDBC__");
        useSsl = "true".equals(ElementParameterParser.getValue(previousNode, "__USE_SSL__"));
        sslTrustStore = ElementParameterParser.getValue(previousNode, "__SSL_TRUST_STORE__");
        sslStorepasswordFieldName = "__SSL_TRUST_STORE_PASSWORD__";

        storeByHBase = ElementParameterParser.getValue(previousNode, "__STORE_BY_HBASE__");
        zookeeperQuorumForHBase = ElementParameterParser.getValue(previousNode, "__ZOOKEEPER_QUORUM__");
        zookeeperClientPortForHBase = ElementParameterParser.getValue(previousNode, "__ZOOKEEPER_CLIENT_PORT__");

        setZNodeParent = "true".equals(ElementParameterParser.getValue(previousNode, "__SET_ZNODE_PARENT__"));
        zNodeParent = ElementParameterParser.getValue(previousNode, "__ZNODE_PARENT__");

        defineRegisterJar = ElementParameterParser.getValue(previousNode, "__DEFINE_REGISTER_JAR__");
        registerJarForHBase = (List<Map<String, String>>)ElementParameterParser.getObjectValue(previousNode, "__REGISTER_JAR__");

        String theDistribution = ElementParameterParser.getValue(previousNode, "__DISTRIBUTION__");
        String theVersion = ElementParameterParser.getValue(previousNode, "__HIVE_VERSION__");

        if("true".equals(ElementParameterParser.getValue(previousNode,"__USE_EXISTING_CONNECTION__"))) {
            String connection = ElementParameterParser.getValue(previousNode, "__CONNECTION__");
            for (INode pNode : previousNode.getProcess().getNodesOfType("tHiveConnection")) {
                if(connection!=null && connection.equals(pNode.getUniqueName())) {
                    theDistribution = ElementParameterParser.getValue(pNode, "__DISTRIBUTION__");
                    theVersion = ElementParameterParser.getValue(pNode, "__HIVE_VERSION__");
                    connectionInformationNode = pNode;
                }
            }
        }

        try {
            hiveDistrib = (org.talend.hadoop.distribution.component.HiveComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(theDistribution, theVersion);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return "";
        }
        isCustom = hiveDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
    }

    if(hiveDistrib.isExecutedThroughWebHCat()) {
        INode nodeBackup = node;
        node = previousNode;

    
	String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");

    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    
	if("false".equals(useExistingConn)) {
		String passwordFieldName = "__HDINSIGHT_PASSWORD__";
		if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_28);
    
		} else {

    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_31);
    
		}
			
		passwordFieldName = "__WASB_PASSWORD__";
		if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_34);
    
		} else {

    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_37);
    
		}

    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WASB_USERNAME__"));
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WASB_CONTAINER__"));
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HDINSIGHT_USERNAME__"));
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_USERNAME__"));
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_HOST__"));
    stringBuffer.append(TEXT_51);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_PORT__"));
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(ElementParameterParser.getValue(node, "__STATUSDIR__"));
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(ElementParameterParser.getValue(node, "__REMOTE_FOLDER__"));
    stringBuffer.append(TEXT_56);
    
	} else {
		String azureConnection = ElementParameterParser.getValue(node,"__CONNECTION__");

    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(azureConnection);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(azureConnection);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    
	}

    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    
        node = nodeBackup;
        if("false".equals(useExistingConn)) { // This variable is declared and initialized in the GetAzureConnection.javajet
            boolean setMemory = "true".equals(ElementParameterParser.getValue(previousNode, "__SET_MEMORY__"));
            if(setMemory) {
                String mapMemory = ElementParameterParser.getValue(previousNode,"__MAPREDUCE_MAP_MEMORY_MB__");
                String reduceMemory = ElementParameterParser.getValue(previousNode,"__MAPREDUCE_REDUCE_MEMORY_MB__");
                String amMemory = ElementParameterParser.getValue(previousNode,"__YARN_APP_MAPREDUCE_AM_RESOURCE_MB__");

    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(amMemory);
    stringBuffer.append(TEXT_77);
    
            }

            List<Map<String, String>> advProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(previousNode, "__ADVANCED_PROPERTIES__");
            if(advProps!=null) {
                for(Map<String, String> item : advProps){

    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(item.get("PROPERTY"));
    stringBuffer.append(TEXT_80);
    stringBuffer.append(item.get("VALUE"));
    stringBuffer.append(TEXT_81);
    
                }
            }

    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    
        }
    } else {
        boolean useExistingConn = ("true").equals(ElementParameterParser.getValue(previousNode, "__USE_EXISTING_CONNECTION__"));

    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    
        connectionMode = ElementParameterParser.getValue(previousNode, "__CONNECTION_MODE__");
        setFsDefaultName = "true".equals(ElementParameterParser.getValue(previousNode, "__SET_FS_DEFAULT_NAME__"));
        fsDefaultName = ElementParameterParser.getValue(previousNode, "__FS_DEFAULT_NAME__");
        connectionInformationNode = previousNode;

        String yarnClasspathSeparator = ElementParameterParser.getValue(previousNode, "__CLASSPATH_SEPARATOR__");

    stringBuffer.append(TEXT_92);
    stringBuffer.append(yarnClasspathSeparator);
    stringBuffer.append(TEXT_93);
    

        if(useExistingConn) {
             connectionMode = "";
             setFsDefaultName = false;
             fsDefaultName = "";
             dbuser = "";
             hiveVersion = "";
             distribution = "";
            String connection = ElementParameterParser.getValue(previousNode, "__CONNECTION__");
            for (INode pNode : node.getProcess().getNodesOfType("tHiveConnection")) {
                    if(connection!=null && connection.equals(pNode.getUniqueName())) {
                        connectionMode = ElementParameterParser.getValue(pNode, "__CONNECTION_MODE__");
                        setFsDefaultName = "true".equals(ElementParameterParser.getValue(pNode, "__SET_FS_DEFAULT_NAME__"));
                        fsDefaultName = ElementParameterParser.getValue(pNode, "__FS_DEFAULT_NAME__");
                        dbuser = ElementParameterParser.getValue(pNode, "__USER__");
                        hiveVersion = ElementParameterParser.getValue(pNode, "__HIVE_VERSION__");
                        distribution = ElementParameterParser.getValue(pNode, "__DISTRIBUTION__");
                        connectionInformationNode = pNode;
                        break;
                    }
             }

            String conn = "conn_" + connection;
            String db = "db_" + connection;
            String dbUser = "dbUser_" + connection;
            
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(conn);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(db);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(dbUser);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_113);
    
        } else {
            String javaDbDriver = "org.apache.hadoop.hive.jdbc.HiveDriver";
            String hiveServer = ElementParameterParser.getValue(previousNode, "__HIVE_SERVER__");

            boolean useKrb = "true".equals(ElementParameterParser.getValue(previousNode, "__USE_KRB__"));
            boolean securityIsEnabled = useKrb && (isCustom || (hiveDistrib.doSupportKerberos() && (("HIVE".equalsIgnoreCase(hiveServer) && "EMBEDDED".equalsIgnoreCase(connectionMode)) || "HIVE2".equalsIgnoreCase(hiveServer))));
            boolean securedStandaloneHive2 = securityIsEnabled && "HIVE2".equalsIgnoreCase(hiveServer) && "STANDALONE".equalsIgnoreCase(connectionMode);
            boolean securedEmbedded = securityIsEnabled && "EMBEDDED".equalsIgnoreCase(connectionMode);
            boolean securedEmbeddedHive2 = securedEmbedded && "HIVE2".equalsIgnoreCase(hiveServer);

            String hivePrincipal = ElementParameterParser.getValue(previousNode, "__HIVE_PRINCIPAL__");
            if(hiveServer!=null && !"".equals(hiveServer.trim()) && (isCustom || hiveDistrib.doSupportHive2())) {
                hiveServer = hiveServer.toLowerCase();
                if ("hive2".equals(hiveServer)) {
                    javaDbDriver = "org.apache.hive.jdbc.HiveDriver";
                }
            } else {
                hiveServer = "hive";
            }

            if(("hive".equals(hiveServer) && !hiveDistrib.doSupportHive1()) || ("hive2".equals(hiveServer) && !hiveDistrib.doSupportHive2())) {

    stringBuffer.append(TEXT_114);
    stringBuffer.append(hiveDistrib.getVersion());
    stringBuffer.append(TEXT_115);
    
            }

            if(("STANDALONE".equals(connectionMode) && !hiveDistrib.doSupportStandaloneMode()) || ("EMBEDDED".equals(connectionMode) && !hiveDistrib.doSupportEmbeddedMode())) {

    stringBuffer.append(TEXT_116);
    stringBuffer.append(hiveDistrib.getVersion());
    stringBuffer.append(TEXT_117);
    
            }

            if("STANDALONE".equals(connectionMode) && "hive".equals(hiveServer) && !hiveDistrib.doSupportHive1Standalone()) {

    stringBuffer.append(TEXT_118);
    
            }

    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_120);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_121);
    
            String passwordFieldName = "__PASS__";
            
    stringBuffer.append(TEXT_122);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_125);
    if (ElementParameterParser.canEncrypt(previousNode, passwordFieldName)) {
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(previousNode, passwordFieldName));
    stringBuffer.append(TEXT_128);
    } else {
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    stringBuffer.append( ElementParameterParser.getValue(previousNode, passwordFieldName));
    stringBuffer.append(TEXT_131);
    }
    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    
            boolean setHadoopUser = "true".equals(ElementParameterParser.getValue(previousNode, "__SET_HADOOP_USER__"));
            if (setHadoopUser) {
                String hadoopUser = ElementParameterParser.getValue(previousNode, "__HADOOP_USER__");

    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_136);
    stringBuffer.append(hadoopUser);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_139);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_140);
    
            }

    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    
            if("EMBEDDED".equals(connectionMode) && (isCustom || hiveDistrib.doSupportEmbeddedMode())) {

    stringBuffer.append(TEXT_143);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_147);
    
                if(isCustom || (!isCustom && hiveDistrib.doSupportImpersonation())) {

    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_151);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_152);
    
                }
            } else if("STANDALONE".equals(connectionMode) && (isCustom || hiveDistrib.doSupportStandaloneMode())) {
                if(securedStandaloneHive2) {

                    // Using SSL in Secure Mode
                    if(useSsl && hiveDistrib.doSupportSSL()) {
                        // Does the distrib support SSL + KERBEROS
                        if(hiveDistrib.doSupportSSLwithKerberos()){
                            if (ElementParameterParser.canEncrypt(node, sslStorepasswordFieldName)) {

    stringBuffer.append(TEXT_153);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(previousNode, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_155);
    
                            }else{

    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_157);
    stringBuffer.append( ElementParameterParser.getValue(previousNode, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_158);
    
                            }

    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_163);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_164);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(sslTrustStore);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    
                        // Does the distrib support only SASL-QOP + KERBEROS Or was it migrated from old SASL job
                        } else {

    stringBuffer.append(TEXT_168);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_170);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_171);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_174);
    
                        }
                    } else {

    stringBuffer.append(TEXT_175);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_181);
    
                    }
                } else {
                // Using SSL in non Secure Mode
                    if(useSsl && hiveDistrib.doSupportSSL()){
                        if(previousNode != null) {
                            if (ElementParameterParser.canEncrypt(previousNode, sslStorepasswordFieldName)) {

    stringBuffer.append(TEXT_182);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_183);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(previousNode, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_184);
    
                            } else {
    
    stringBuffer.append(TEXT_185);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_186);
    stringBuffer.append( ElementParameterParser.getValue(previousNode, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_187);
    
                            }
                        } else {

    stringBuffer.append(TEXT_188);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_189);
    
                        }

    stringBuffer.append(TEXT_190);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_191);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_192);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_193);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_194);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_195);
    stringBuffer.append(sslTrustStore);
    stringBuffer.append(TEXT_196);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_197);
    
                    } else {

    stringBuffer.append(TEXT_198);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_200);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_201);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_203);
    
                    }
                }

    stringBuffer.append(TEXT_204);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_205);
    stringBuffer.append(additionalJdbcSettings);
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
    
            }

    stringBuffer.append(TEXT_213);
    stringBuffer.append(javaDbDriver );
    stringBuffer.append(TEXT_214);
    
            if(securedStandaloneHive2) {

    stringBuffer.append(TEXT_215);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_216);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_217);
    
            } else {

    stringBuffer.append(TEXT_218);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_219);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_222);
    
            }


    stringBuffer.append(TEXT_223);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    
            if(!isCustom && ("HDP_1_2".equals(hiveVersion) || "HDP_1_3".equals(hiveVersion))) {
                String mapMemory = ElementParameterParser.getValue(previousNode,"__MAPRED_JOB_MAP_MEMORY_MB__");
                String reduceMemory = ElementParameterParser.getValue(previousNode,"__MAPRED_JOB_REDUCE_MEMORY_MB__");

    stringBuffer.append(TEXT_226);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_227);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_228);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_230);
    
            }

            boolean isKerberosAvailableHadoop2 = !isCustom && hiveDistrib.isHadoop2() && hiveDistrib.doSupportKerberos();
            boolean isHadoop2 = !isCustom && hiveDistrib.isHadoop2();
            boolean isKerberosAvailableHadoop1 = !isCustom && hiveDistrib.isHadoop1() && hiveDistrib.doSupportKerberos();

            boolean useYarn = "true".equals(ElementParameterParser.getValue(previousNode, "__USE_YARN__"));

            if(securedEmbedded) {
                String namenodePrincipal = ElementParameterParser.getValue(previousNode, "__NAMENODE_PRINCIPAL__");

    stringBuffer.append(TEXT_231);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(namenodePrincipal);
    stringBuffer.append(TEXT_233);
    
                if(isKerberosAvailableHadoop1 || (isCustom && !useYarn)) {
                    String jobtrackerPrincipal = ElementParameterParser.getValue(previousNode, "__JOBTRACKER_PRINCIPAL__");

    stringBuffer.append(TEXT_234);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_235);
    stringBuffer.append(jobtrackerPrincipal);
    stringBuffer.append(TEXT_236);
    
                }
                if(isKerberosAvailableHadoop2 || (isCustom && useYarn)) {
                    String resourceManagerPrincipal = ElementParameterParser.getValue(previousNode, "__RESOURCEMANAGER_PRINCIPAL__");

    stringBuffer.append(TEXT_237);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_238);
    stringBuffer.append(resourceManagerPrincipal);
    stringBuffer.append(TEXT_239);
    
                }

            }

            boolean setResourceManager = "true".equals(ElementParameterParser.getValue(previousNode, "__SET_RESOURCE_MANAGER__"));

            if((isCustom && useYarn) || (!isCustom && isHadoop2)) {
                if(setResourceManager) {
                    String resourceManager = ElementParameterParser.getValue(previousNode, "__RESOURCE_MANAGER__");

    stringBuffer.append(TEXT_240);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_241);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_242);
    stringBuffer.append(resourceManager);
    stringBuffer.append(TEXT_243);
    
                }

                boolean setJobHistoryAddress = "true".equals(ElementParameterParser.getValue(previousNode, "__SET_JOBHISTORY_ADDRESS__"));
                if(setJobHistoryAddress) {
                    String jobHistoryAddress = ElementParameterParser.getValue(previousNode,"__JOBHISTORY_ADDRESS__");
                    
    stringBuffer.append(TEXT_244);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_245);
    stringBuffer.append(jobHistoryAddress);
    stringBuffer.append(TEXT_246);
    
                }

                boolean setSchedulerAddress = "true".equals(ElementParameterParser.getValue(previousNode, "__SET_SCHEDULER_ADDRESS__"));
                if(setSchedulerAddress) {
                    String schedulerAddress = ElementParameterParser.getValue(previousNode,"__RESOURCEMANAGER_SCHEDULER_ADDRESS__");

    stringBuffer.append(TEXT_247);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_248);
    stringBuffer.append(schedulerAddress);
    stringBuffer.append(TEXT_249);
    
                }

                if ("true".equals(ElementParameterParser.getValue(previousNode, "__USE_DATANODE_HOSTNAME__"))) {

    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_251);
    
                }

                if(setFsDefaultName) {

    stringBuffer.append(TEXT_252);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_253);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_254);
    
                }

                if("EMBEDDED".equals(connectionMode) && (isCustom || hiveDistrib.doSupportEmbeddedMode())) {
                    boolean crossPlatformSubmission = "true".equals(ElementParameterParser.getValue(node, "__CROSS_PLATFORM_SUBMISSION__"));
                    if((isCustom && useYarn && crossPlatformSubmission) || (!isCustom && hiveDistrib.doSupportCrossPlatformSubmission())) {

    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    
                    }

                    if("MAPR410".equals(hiveVersion)){

    stringBuffer.append(TEXT_257);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_258);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_259);
    
                    }

                    if(hiveDistrib.doSupportCustomMRApplicationCP()){

    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(hiveDistrib.getCustomMRApplicationCP());
    stringBuffer.append(TEXT_262);
    
                    }

    stringBuffer.append(TEXT_263);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_264);
    stringBuffer.append(hiveDistrib.getYarnApplicationClasspath());
    stringBuffer.append(TEXT_265);
    
                }

                boolean setMemory = "true".equals(ElementParameterParser.getValue(previousNode, "__SET_MEMORY__"));
                if(setMemory) {
                    String mapMemory = ElementParameterParser.getValue(previousNode,"__MAPREDUCE_MAP_MEMORY_MB__");
                    String reduceMemory = ElementParameterParser.getValue(previousNode,"__MAPREDUCE_REDUCE_MEMORY_MB__");
                    String amMemory = ElementParameterParser.getValue(previousNode,"__YARN_APP_MAPREDUCE_AM_RESOURCE_MB__");

    stringBuffer.append(TEXT_266);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_267);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_268);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_269);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_270);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_271);
    stringBuffer.append(amMemory);
    stringBuffer.append(TEXT_272);
    
                }
            }

            List<Map<String, String>> advProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(previousNode, "__ADVANCED_PROPERTIES__");
            if(advProps!=null) {
                for(Map<String, String> item : advProps){

    stringBuffer.append(TEXT_273);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_274);
    stringBuffer.append(item.get("PROPERTY"));
    stringBuffer.append(TEXT_275);
    stringBuffer.append(item.get("VALUE"));
    stringBuffer.append(TEXT_276);
    
                }
            }

    stringBuffer.append(TEXT_277);
    
    		boolean useExistingConnection = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
    		if(!useExistingConnection){

    stringBuffer.append(TEXT_278);
    stringBuffer.append(TEXT_279);
    
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
    	
    stringBuffer.append(TEXT_280);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_281);
    
            if(advProps != null){
                for(Map<String, String> item : advProps){
                    if("\"tez.lib.uris\"".equals(item.get("PROPERTY"))){
                    
    stringBuffer.append(TEXT_282);
      
                    }
                }
            }
            boolean installTez = "INSTALL".equals(ElementParameterParser.getValue(node, "__TEZ_LIB__"));
            if(installTez){
                //prepare the folder
                
    stringBuffer.append(TEXT_283);
    
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
        
    stringBuffer.append(TEXT_284);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_285);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_286);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_287);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_288);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_289);
    
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
        
        
    stringBuffer.append(TEXT_290);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_291);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_293);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_294);
    
        if(mrUseDatanodeHostname && (isCustom || hdfsDistrib.doSupportUseDatanodeHostname())){
        
    stringBuffer.append(TEXT_295);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_296);
    
        }
        if(hadoopProps!=null && hadoopProps.size() > 0){
        	for(Map<String, String> item : hadoopProps){
        	
    stringBuffer.append(TEXT_297);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_298);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_299);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_300);
     
    		}
    	}
        	
    	if((hdfsDistrib.doSupportKerberos() && useKrb && !isCustom) || (isCustom && useKrb)){
    	
    stringBuffer.append(TEXT_301);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_302);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_303);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_304);
    
    		if(useKeytab){
    		
    stringBuffer.append(TEXT_305);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_306);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_307);
    
    		}
    	}
    	
    	if(hdfsDistrib.doSupportGroup()){
    		
    stringBuffer.append(TEXT_308);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_309);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_310);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_311);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_312);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_313);
    
        }else{
        
    stringBuffer.append(TEXT_314);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_315);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_316);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_317);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_318);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_319);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_320);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_321);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_322);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_323);
    
        }
    }
}

      
                (new GetFileSystem()).invoke(node, cid);
                String tezLibFolder = ElementParameterParser.getValue(node, "__TEZ_LIB_FOLDER__");
                boolean useDefaultTezLibFolder = "\"/tmp/{USERNAME}/talend_tez_libs/{custom|HIVE_VERSION}\"".equals(tezLibFolder);
                if(useDefaultTezLibFolder){
                
    stringBuffer.append(TEXT_324);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_325);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_326);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_327);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_328);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_329);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_330);
    stringBuffer.append(isCustom?"custom":hiveVersion);
    stringBuffer.append(TEXT_331);
    
                }else{
                
    stringBuffer.append(TEXT_332);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_333);
    stringBuffer.append(tezLibFolder);
    stringBuffer.append(TEXT_334);
    
                }
                
    stringBuffer.append(TEXT_335);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_336);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_337);
    
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
                
    stringBuffer.append(TEXT_338);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_339);
    if(changePathSeparator){
    stringBuffer.append(TEXT_340);
    }else{
    stringBuffer.append(TEXT_341);
    }
    stringBuffer.append(TEXT_342);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_343);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_344);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_345);
    
                    for(String jarName : tezLibJarsName){
                    
    stringBuffer.append(TEXT_346);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_347);
    stringBuffer.append(jarName);
    stringBuffer.append(TEXT_348);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_349);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_350);
    stringBuffer.append(jarName);
    stringBuffer.append(TEXT_351);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_352);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_353);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_354);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_355);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_356);
    
                    }
                    
    stringBuffer.append(TEXT_357);
    
            }else{
            
    stringBuffer.append(TEXT_358);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_359);
    stringBuffer.append(ElementParameterParser.getValue(node, "__TEZ_LIB_PATH__"));
    stringBuffer.append(TEXT_360);
    
            }
            //define the location of tez lib    
            
    stringBuffer.append(TEXT_361);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_362);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_363);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_364);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_365);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_366);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_367);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_368);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_369);
    stringBuffer.append(ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__"));
    stringBuffer.append(TEXT_370);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_371);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_372);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_373);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_374);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_375);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_376);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_377);
    
    	}
    }
}

    
    	       (new PrepareTez()).invoke(previousNode, cid);
            }

    stringBuffer.append(TEXT_378);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_379);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_380);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_381);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_383);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_384);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_385);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_387);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_388);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_389);
    
            if("true".equalsIgnoreCase(storeByHBase) && (isCustom || hiveDistrib.doSupportHBaseForHive())) {
    stringBuffer.append(TEXT_390);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_391);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_392);
    if(zookeeperQuorumForHBase!=null && !"".equals(zookeeperQuorumForHBase) && !"\"\"".equals(zookeeperQuorumForHBase)) {
    stringBuffer.append(TEXT_393);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_394);
    stringBuffer.append(zookeeperQuorumForHBase);
    stringBuffer.append(TEXT_395);
    }
    stringBuffer.append(TEXT_396);
    if(zookeeperClientPortForHBase!=null && !"".equals(zookeeperClientPortForHBase) && !"\"\"".equals(zookeeperClientPortForHBase)) {
    stringBuffer.append(TEXT_397);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_398);
    stringBuffer.append(zookeeperClientPortForHBase);
    stringBuffer.append(TEXT_399);
    }
    stringBuffer.append(TEXT_400);
    if(setZNodeParent && zNodeParent!=null && !"".equals(zNodeParent) && !"\"\"".equals(zNodeParent)) {
    stringBuffer.append(TEXT_401);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_402);
    stringBuffer.append(zNodeParent);
    stringBuffer.append(TEXT_403);
    }
    stringBuffer.append(TEXT_404);
    if("true".equalsIgnoreCase(defineRegisterJar) && registerJarForHBase!=null && registerJarForHBase.size()>0) {
                    for(Map<String, String> jar : registerJarForHBase){
                        String path = jar.get("JAR_PATH");
                        if(path == null || "".equals(path) || "\"\"".equals(path)) {
                            continue;
                        }

    stringBuffer.append(TEXT_405);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_406);
    stringBuffer.append(path);
    stringBuffer.append(TEXT_407);
    
                    }
                }

    stringBuffer.append(TEXT_408);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_409);
    
            }
        }
    }

 List<IMetadataColumn> columnList = null;

 List<IMetadataTable> metadatas = node.getMetadataList();
 if(metadatas !=null && metadatas.size()>0){
     IMetadataTable metadata = metadatas.get(0);
     if(metadata != null){
         columnList = metadata.getListColumns();
     }
}

    // Register jars to handle the parquet format.
    boolean targetTableUsesParquetFormat = "true".equals(ElementParameterParser.getValue(node, "__TARGET_TABLE_IS_A_PARQUET_TABLE__"));

    boolean isParquetSupported = isCustom || hiveDistrib.doSupportParquetFormat();
    if(targetTableUsesParquetFormat && !isParquetSupported) {

    stringBuffer.append(TEXT_410);
    
    }

    boolean generateAddJarCodeForAll = targetTableUsesParquetFormat;

    if(targetTableUsesParquetFormat) {
        String compression = ElementParameterParser.getValue(node, "__PARQUET_COMPRESSION__");
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

            jarsToRegister.add("snappy-java");
            //jarsToRegister.add("parquet-hadoop-bundle");
            jarsToRegister.add("parquet-hive-bundle");

            for (int j = 0; j < commandLine.length; j++) {
                if(commandLine[j].contains("jar")) {
                    jars = java.util.Arrays.asList(commandLine[j].split(";"));
                    break;
                }
            }
        }
        if(jarsToRegister!=null && jars!=null) {
            if("EMBEDDED".equalsIgnoreCase(connectionMode) || hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_411);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_412);
    
            } else {
                generateAddJarCodeForAll = false;
                if(setFsDefaultName) {
                    generateAddJarCodeForAll = true;

    stringBuffer.append(TEXT_413);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_414);
    stringBuffer.append(TEXT_415);
    
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
        
    stringBuffer.append(TEXT_416);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_417);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_418);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_419);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_420);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_421);
    
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
        
        
    stringBuffer.append(TEXT_422);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_423);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_424);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_425);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_426);
    
        if(mrUseDatanodeHostname && (isCustom || hdfsDistrib.doSupportUseDatanodeHostname())){
        
    stringBuffer.append(TEXT_427);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_428);
    
        }
        if(hadoopProps!=null && hadoopProps.size() > 0){
        	for(Map<String, String> item : hadoopProps){
        	
    stringBuffer.append(TEXT_429);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_430);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_431);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_432);
     
    		}
    	}
        	
    	if((hdfsDistrib.doSupportKerberos() && useKrb && !isCustom) || (isCustom && useKrb)){
    	
    stringBuffer.append(TEXT_433);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_434);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_435);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_436);
    
    		if(useKeytab){
    		
    stringBuffer.append(TEXT_437);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_438);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_439);
    
    		}
    	}
    	
    	if(hdfsDistrib.doSupportGroup()){
    		
    stringBuffer.append(TEXT_440);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_441);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_442);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_443);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_444);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_445);
    
        }else{
        
    stringBuffer.append(TEXT_446);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_447);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_448);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_449);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_450);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_451);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_452);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_453);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_454);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_455);
    
        }
    }
}

    stringBuffer.append(TEXT_456);
    
			(new GetFileSystem()).invoke(connectionInformationNode, cid);
			
    stringBuffer.append(TEXT_457);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_458);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_459);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_460);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_461);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_462);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_463);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_464);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_465);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_466);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_467);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_468);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_469);
    
                }
            }

            if(generateAddJarCodeForAll) {
                if(!hiveDistrib.isExecutedThroughWebHCat()) { // Then we create a SQL statement to add the jars.

    stringBuffer.append(TEXT_470);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_471);
    
                }
                for(int i=0; i<jarsToRegister.size(); i++) {
                    String jarToRegister = jarsToRegister.get(i);
                    for(int j=0; j<jars.size(); j++) {
                        if(jars.get(j).contains(jarToRegister)) {
                            if(!hiveDistrib.isExecutedThroughWebHCat()) { // Then we use the created SQL statement to add the jars.

    stringBuffer.append(TEXT_472);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_473);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_474);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_475);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_476);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_477);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_478);
    
                            } else {

    stringBuffer.append(TEXT_479);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_480);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_481);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_482);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_483);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_484);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_485);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_486);
    
                            }
                        }
                    }
                }
            }
        }

        if(!hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_487);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_488);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_489);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_490);
    stringBuffer.append(compression);
    stringBuffer.append(TEXT_491);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_492);
    
        } else {

    stringBuffer.append(TEXT_493);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_494);
    stringBuffer.append(compression);
    stringBuffer.append(TEXT_495);
    
        }
    }
        // End of parquet format handling.

    
    if(!hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_496);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_497);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_498);
    
    }

    stringBuffer.append(TEXT_499);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_500);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_501);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_502);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_503);
    
    //For Bug TDI-24105,support context variables
    if(fieldPartitions != null && !fieldPartitions.isEmpty()) {
        String columnName = null;
        String columnValue = null;
        int count = 0 ;

    stringBuffer.append(TEXT_504);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_505);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_506);
    
        for(Map<String, String> line : fieldPartitions ) {// search in the configuration table
             columnName = line.get("COLUMN_NAME");
             columnValue = line.get("COLUMN_VALUE");
            if (columnName!=null && !"".equals(columnName)) {
                count++;

    stringBuffer.append(TEXT_507);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_508);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_509);
    stringBuffer.append(columnName);
    stringBuffer.append(TEXT_510);
    
                   if (columnValue!=null && !"".equals(columnValue)) {

    stringBuffer.append(TEXT_511);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_512);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_513);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_514);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_515);
    stringBuffer.append(columnValue);
    stringBuffer.append(TEXT_516);
    
                }
                if(count < fieldPartitions.size()){

    stringBuffer.append(TEXT_517);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_518);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_519);
    
                }
            }
        }
    }

    stringBuffer.append(TEXT_520);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_521);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_522);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_523);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_524);
    

if(columnList != null && columnList.size()>0){
    if(("INSERT").equals(dataAction)){

    stringBuffer.append(TEXT_525);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_526);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_527);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_528);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_529);
    
            if(!hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_530);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_531);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_532);
    
            } else {

    stringBuffer.append(TEXT_533);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_534);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_535);
    
            }

    
    }else if (("OVERWRITE").equals(dataAction)){

    stringBuffer.append(TEXT_536);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_537);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_538);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_539);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_540);
    
            if(!hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_541);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_542);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_543);
    
            } else {

    stringBuffer.append(TEXT_544);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_545);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_546);
    
            }
    }
}

// END

    boolean useExistingConn = ("true").equals(ElementParameterParser.getValue(previousNode, "__USE_EXISTING_CONNECTION__"));

    if(!hiveDistrib.isExecutedThroughWebHCat()) {

    stringBuffer.append(TEXT_547);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_548);
    
if(!useExistingConn) {
    
    stringBuffer.append(TEXT_549);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_550);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_551);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_552);
    
}

    stringBuffer.append(TEXT_553);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_554);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_555);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_556);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_557);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_558);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_559);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_560);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_561);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_562);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_563);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_564);
    
    } else {

    stringBuffer.append(TEXT_565);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_566);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_567);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_568);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_569);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_570);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_571);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_572);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_573);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_574);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_575);
    
    }

    stringBuffer.append(TEXT_576);
    return stringBuffer.toString();
  }
}
