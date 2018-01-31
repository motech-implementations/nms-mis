package org.talend.designer.codegen.translators.processing.hadoop;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.types.Java2STLangTypesHelper;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.designer.runprocess.ProcessorException;
import java.util.List;
import java.util.Map;

public class TPigLoadBeginJava
{
  protected static String nl;
  public static synchronized TPigLoadBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TPigLoadBeginJava result = new TPigLoadBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\t";
  protected final String TEXT_3 = NL + "\t";
  protected final String TEXT_4 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_5 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_6 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_7 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_8 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_10 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_11 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_12 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_13 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_14 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_15 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_16 = " - Written records count: \" + nb_line_";
  protected final String TEXT_17 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_18 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_19 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_21 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_22 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_23 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_24 = " - Writing the record \" + nb_line_";
  protected final String TEXT_25 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_26 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_27 = " - Processing the record \" + nb_line_";
  protected final String TEXT_28 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_29 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_30 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_31 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_32 = NL + "\t\troutines.system.GetJarsToRegister getJarsToRegister_";
  protected final String TEXT_33 = " = new routines.system.GetJarsToRegister();";
  protected final String TEXT_34 = NL + "\t\tjava.util.Properties props_";
  protected final String TEXT_35 = " = new java.util.Properties();";
  protected final String TEXT_36 = NL + "\t\tprops_";
  protected final String TEXT_37 = ".put(org.apache.pig.impl.PigContext.JOB_NAME, projectName + \"_\" + jobName + \"_\" + jobVersion + \"_\" + \"";
  protected final String TEXT_38 = "\");";
  protected final String TEXT_39 = NL + "\t\t// Add HADOOP_CONF_DIR to the classpath if it's present" + NL + "\t\tString hadoopConfDir_";
  protected final String TEXT_40 = " = System.getenv(\"HADOOP_CONF_DIR\");" + NL + "\t\tif(hadoopConfDir_";
  protected final String TEXT_41 = " != null){" + NL + "\t\t\tjava.net.URLClassLoader sysloader = (java.net.URLClassLoader) ClassLoader.getSystemClassLoader();" + NL + "\t\t\tjava.lang.reflect.Method method = java.net.URLClassLoader.class.getDeclaredMethod(\"addURL\", new Class[] { java.net.URL.class });" + NL + "\t\t\tmethod.setAccessible(true);" + NL + "\t\t\tmethod.invoke(sysloader,new Object[] { new java.io.File(hadoopConfDir_";
  protected final String TEXT_42 = ").toURI().toURL() });" + NL + "\t\t}" + NL + "\t\torg.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_43 = " = new org.apache.hadoop.conf.Configuration();" + NL + "" + NL + "\t\t// Adding any hdfs-site.xml file" + NL + "\t\tconf_";
  protected final String TEXT_44 = ".addResource(\"hdfs-site.xml\");" + NL + "" + NL + "\t\t// Adding any yarn-site.xml file" + NL + "\t\tconf_";
  protected final String TEXT_45 = ".addResource(\"yarn-site.xml\");" + NL + "" + NL + "\t\t// Adding any mapred-site.xml" + NL + "\t\tconf_";
  protected final String TEXT_46 = ".addResource(\"mapred-site.xml\");" + NL + "" + NL + "\t\t// Adding any core-site.xml" + NL + "\t\tconf_";
  protected final String TEXT_47 = ".addResource(\"core-site.xml\");" + NL + "" + NL + "\t\t// Adding any talend-site.xml" + NL + "\t\tconf_";
  protected final String TEXT_48 = ".addResource(\"talend-site.xml\");" + NL + "\t\tString tldKerberosAuth = conf_";
  protected final String TEXT_49 = ".get(\"talend.kerberos.authentication\", \"\");" + NL + "\t\tString tldKerberosKtPrincipal = conf_";
  protected final String TEXT_50 = ".get(\"talend.kerberos.keytab.principal\", \"\");" + NL + "\t\tString tldKerberosKtPath = conf_";
  protected final String TEXT_51 = ".get(\"talend.kerberos.keytab.path\", \"\");" + NL;
  protected final String TEXT_52 = NL + "\t\t\t// Adding any hbase-site.xml" + NL + "\t\t\tconf_";
  protected final String TEXT_53 = ".addResource(\"hbase-site.xml\");";
  protected final String TEXT_54 = NL + NL + "\t\t// Add hadoop configurations to pig_context properties" + NL + "\t\tjava.util.Iterator<java.util.Map.Entry<String,String>> iterator_";
  protected final String TEXT_55 = " = conf_";
  protected final String TEXT_56 = ".iterator();" + NL + "\t\twhile(iterator_";
  protected final String TEXT_57 = ".hasNext()){" + NL + "\t\t\tjava.util.Map.Entry<String,String> property = iterator_";
  protected final String TEXT_58 = ".next();" + NL + "\t\t\tprops_";
  protected final String TEXT_59 = ".put(property.getKey(), property.getValue());" + NL + "\t\t}";
  protected final String TEXT_60 = NL + "\t\t\tlog.debug(\"CLASSPATH_CONFIGURATION_";
  protected final String TEXT_61 = "\" + \" Loaded : \" + conf_";
  protected final String TEXT_62 = ");" + NL + "\t\t\tlog.debug(\"CLASSPATH_CONFIGURATION_";
  protected final String TEXT_63 = "\" + \" key value pairs : \");" + NL + "\t\t\tjava.util.Iterator<java.util.Map.Entry<String,String>> iterator_log_";
  protected final String TEXT_64 = " = conf_";
  protected final String TEXT_65 = ".iterator();" + NL + "\t\t\twhile(iterator_log_";
  protected final String TEXT_66 = ".hasNext()){" + NL + "\t\t\t\tjava.util.Map.Entry<String,String> property = iterator_log_";
  protected final String TEXT_67 = ".next();" + NL + "\t\t\t\tlog.debug(\"CLASSPATH_CONFIGURATION_";
  protected final String TEXT_68 = " \" + property.getKey() + \" : \" + property.getValue());" + NL + "\t\t\t}" + NL + "\t\t";
  protected final String TEXT_69 = NL + NL + "\t\tif(org.apache.hadoop.security.UserGroupInformation.isSecurityEnabled()){" + NL + "\t\t\t// Keytab configurations" + NL + "\t\t\tif(tldKerberosAuth.equalsIgnoreCase(\"keytab\")){" + NL + "\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(tldKerberosKtPrincipal, tldKerberosKtPath);" + NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_70 = NL + "\ttry {";
  protected final String TEXT_71 = NL + "\t\t\t\tfinal String hdInsightPassword_";
  protected final String TEXT_72 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_73 = ");";
  protected final String TEXT_74 = NL + "\t\t\t\tfinal String hdInsightPassword_";
  protected final String TEXT_75 = " = ";
  protected final String TEXT_76 = ";";
  protected final String TEXT_77 = NL + "\t\t\t\tfinal String wasbPassword_";
  protected final String TEXT_78 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_79 = ");";
  protected final String TEXT_80 = NL + "\t\t\t\tfinal String wasbPassword_";
  protected final String TEXT_81 = " = ";
  protected final String TEXT_82 = ";";
  protected final String TEXT_83 = NL + NL + "\t\t\tjava.io.File localPigLatin_";
  protected final String TEXT_84 = " = new java.io.File(projectName + \"_\" + jobName + \"_\" + Thread.currentThread().getId() +\".pig\");" + NL + "\t\t\tjava.io.FileWriter fw_";
  protected final String TEXT_85 = " = new java.io.FileWriter(localPigLatin_";
  protected final String TEXT_86 = ".getAbsoluteFile());" + NL + "\t\t\tjava.io.BufferedWriter bw_";
  protected final String TEXT_87 = " = new java.io.BufferedWriter(fw_";
  protected final String TEXT_88 = ");" + NL + "" + NL + "\t\t\tjava.lang.StringBuilder libjars_";
  protected final String TEXT_89 = " = new StringBuilder();" + NL + "" + NL + "\t\t\torg.talend.bigdata.launcher.fs.FileSystem azureFs_";
  protected final String TEXT_90 = " = new org.talend.bigdata.launcher.fs.AzureFileSystem(\"DefaultEndpointsProtocol=https;\"" + NL + "\t\t\t\t+ \"AccountName=\"" + NL + "\t\t\t\t+ ";
  protected final String TEXT_91 = NL + "\t\t\t\t+ \";\"" + NL + "\t\t\t\t+ \"AccountKey=\" + wasbPassword_";
  protected final String TEXT_92 = ", ";
  protected final String TEXT_93 = ");" + NL + "" + NL + "\t\t\torg.talend.bigdata.launcher.webhcat.WebHCatJob instance_";
  protected final String TEXT_94 = " = new org.talend.bigdata.launcher.webhcat.QueryJob(azureFs_";
  protected final String TEXT_95 = ", org.talend.bigdata.launcher.utils.JobType.PIG);" + NL + "\t\t\tinstance_";
  protected final String TEXT_96 = ".setCredentials(new org.talend.bigdata.launcher.security.HDInsightCredentials(";
  protected final String TEXT_97 = ", hdInsightPassword_";
  protected final String TEXT_98 = "));" + NL + "\t\t\tinstance_";
  protected final String TEXT_99 = ".setUsername(";
  protected final String TEXT_100 = ");" + NL + "\t\t\tinstance_";
  protected final String TEXT_101 = ".setWebhcatEndpoint(\"https\", ";
  protected final String TEXT_102 = " + \":\" + ";
  protected final String TEXT_103 = ");" + NL + "\t\t\tinstance_";
  protected final String TEXT_104 = ".setStatusFolder(org.talend.bigdata.launcher.utils.Utils.removeFirstSlash(";
  protected final String TEXT_105 = "));" + NL + "\t\t\tinstance_";
  protected final String TEXT_106 = ".setRemoteFolder(org.talend.bigdata.launcher.utils.Utils.removeFirstSlash(";
  protected final String TEXT_107 = "));" + NL + "\t\t\t((org.talend.bigdata.launcher.webhcat.QueryJob) instance_";
  protected final String TEXT_108 = ").setFileToExecute(projectName + \"_\" + jobName + \"_\" + Thread.currentThread().getId() +\".pig\");" + NL + "" + NL + "\t\t\tString wasbPath_";
  protected final String TEXT_109 = " = azureFs_";
  protected final String TEXT_110 = ".getFileSystemPrefix() + \"/\"\t+ instance_";
  protected final String TEXT_111 = ".getRemoteFolder()\t+ \"/libjars/\";";
  protected final String TEXT_112 = NL + "\t\t\torg.apache.pig.impl.PigContext context_";
  protected final String TEXT_113 = " = new org.apache.pig.impl.PigContext(org.apache.pig.ExecType.LOCAL, props_";
  protected final String TEXT_114 = ");";
  protected final String TEXT_115 = NL + "\t\t\t\tglobalMap.put(\"current_client_path_separator\", System.getProperty(\"path.separator\"));" + NL + "\t\t\t\tSystem.setProperty(\"path.separator\", ";
  protected final String TEXT_116 = ");" + NL + "" + NL + "\t\t\t\tglobalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_117 = "\", System.getProperty(\"HADOOP_USER_NAME\"));";
  protected final String TEXT_118 = NL + "\t\t\t\t\tString username_";
  protected final String TEXT_119 = " = ";
  protected final String TEXT_120 = ";" + NL + "\t\t\t\t\tif(username_";
  protected final String TEXT_121 = "!=null && !\"\".equals(username_";
  protected final String TEXT_122 = ".trim())) {" + NL + "\t\t\t\t\t\tSystem.setProperty(\"HADOOP_USER_NAME\",username_";
  protected final String TEXT_123 = ");" + NL + "\t\t\t\t\t}";
  protected final String TEXT_124 = NL + "\t\t\t        props_";
  protected final String TEXT_125 = ".put(\"mapreduce.job.map.output.collector.class\", \"org.apache.hadoop.mapred.MapRFsOutputBuffer\");" + NL + "\t\t\t        props_";
  protected final String TEXT_126 = ".put(\"mapreduce.job.reduce.shuffle.consumer.plugin.class\", \"org.apache.hadoop.mapreduce.task.reduce.DirectShuffle\");";
  protected final String TEXT_127 = NL + "\t                props_";
  protected final String TEXT_128 = ".put(\"fs.defaultFS\", ";
  protected final String TEXT_129 = ");" + NL + "\t                org.apache.hadoop.security.UserGroupInformation.setLoginUser(org.apache.hadoop.security.UserGroupInformation.createRemoteUser(username_";
  protected final String TEXT_130 = "));" + NL + "\t                ";
  protected final String TEXT_131 = NL + "\t                props_";
  protected final String TEXT_132 = ".put(\"fs.default.name\", ";
  protected final String TEXT_133 = ");" + NL + "\t                ";
  protected final String TEXT_134 = NL + "\t\t\t\t\tprops_";
  protected final String TEXT_135 = ".put(\"mapreduce.framework.name\", \"yarn\");" + NL + "\t\t\t\t\tprops_";
  protected final String TEXT_136 = ".put(\"yarn.resourcemanager.address\", ";
  protected final String TEXT_137 = ");";
  protected final String TEXT_138 = NL + "\t\t\t\t\t\tprops_";
  protected final String TEXT_139 = ".put(\"yarn.resourcemanager.scheduler.address\", ";
  protected final String TEXT_140 = ");";
  protected final String TEXT_141 = NL + "\t\t\t\t\t\tprops_";
  protected final String TEXT_142 = ".put(\"yarn.app.mapreduce.am.staging-dir\", ";
  protected final String TEXT_143 = ");";
  protected final String TEXT_144 = NL + "\t\t\t\t\t\tprops_";
  protected final String TEXT_145 = ".put(\"mapreduce.app-submission.cross-platform\",\"true\");";
  protected final String TEXT_146 = NL + "\t\t\t\t\t\tprops_";
  protected final String TEXT_147 = ".put(\"mapreduce.application.classpath\", \"";
  protected final String TEXT_148 = "\");";
  protected final String TEXT_149 = NL + "\t\t\t\t\tprops_";
  protected final String TEXT_150 = ".put(\"yarn.application.classpath\", \"";
  protected final String TEXT_151 = "\");";
  protected final String TEXT_152 = NL + "\t\t\t\t\t\tprops_";
  protected final String TEXT_153 = ".put(\"mapreduce.jobhistory.address\", ";
  protected final String TEXT_154 = ");";
  protected final String TEXT_155 = NL + "\t\t\t\t\tprops_";
  protected final String TEXT_156 = ".put(\"mapred.job.tracker\", ";
  protected final String TEXT_157 = ");";
  protected final String TEXT_158 = NL + "\t\t\t\t\tprops_";
  protected final String TEXT_159 = ".put(\"hcat.metastore.uri\", ";
  protected final String TEXT_160 = ");";
  protected final String TEXT_161 = NL + "                        props_";
  protected final String TEXT_162 = ".put(\"hive.metastore.sasl.enabled\", \"true\");";
  protected final String TEXT_163 = NL + "\t\t\t\t\tprops_";
  protected final String TEXT_164 = ".put(\"hbase.zookeeper.quorum\", ";
  protected final String TEXT_165 = ");" + NL + "\t\t\t\t\tprops_";
  protected final String TEXT_166 = ".put(\"hbase.zookeeper.property.clientPort\", ";
  protected final String TEXT_167 = ");";
  protected final String TEXT_168 = NL + "\t\t\t\t\tprops_";
  protected final String TEXT_169 = ".put(\"zookeeper.znode.parent\",";
  protected final String TEXT_170 = ");";
  protected final String TEXT_171 = NL + "\t\t\t\t\tprops_";
  protected final String TEXT_172 = ".put(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_173 = ");" + NL + "\t\t\t\t\tprops_";
  protected final String TEXT_174 = ".put(\"hbase.security.authentication\",\"kerberos\");" + NL + "\t\t\t\t\tprops_";
  protected final String TEXT_175 = ".put(\"hbase.regionserver.kerberos.principal\",";
  protected final String TEXT_176 = ");" + NL + "\t\t\t\t\tprops_";
  protected final String TEXT_177 = ".put(\"hbase.master.kerberos.principal\",";
  protected final String TEXT_178 = ");";
  protected final String TEXT_179 = NL + "\t\t\t\t\t\tprops_";
  protected final String TEXT_180 = ".put(\"mapreduce.jobtracker.kerberos.principal\", ";
  protected final String TEXT_181 = ");";
  protected final String TEXT_182 = NL + "\t\t\t\t\t\tprops_";
  protected final String TEXT_183 = ".put(\"yarn.resourcemanager.principal\", ";
  protected final String TEXT_184 = ");" + NL + "\t\t\t\t\t\tprops_";
  protected final String TEXT_185 = ".put(\"mapreduce.jobhistory.principal\", ";
  protected final String TEXT_186 = ");";
  protected final String TEXT_187 = NL + "                        System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                        System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                        System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_188 = ");" + NL + "                        System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_189 = ");";
  protected final String TEXT_190 = NL + "\t\t\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_191 = ", ";
  protected final String TEXT_192 = ");";
  protected final String TEXT_193 = NL + "                        com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_194 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "                        maprLogin_";
  protected final String TEXT_195 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_196 = ", ";
  protected final String TEXT_197 = ");";
  protected final String TEXT_198 = NL + "                        System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                        System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                        System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_199 = ");" + NL + "                        com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_200 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_201 = NL + "                            System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_202 = ");";
  protected final String TEXT_203 = NL + "                            maprLogin_";
  protected final String TEXT_204 = ".setCheckUGI(false);";
  protected final String TEXT_205 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_206 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_207 = ");";
  protected final String TEXT_208 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_209 = " = ";
  protected final String TEXT_210 = "; ";
  protected final String TEXT_211 = NL + "                        \tmaprLogin_";
  protected final String TEXT_212 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_213 = ", ";
  protected final String TEXT_214 = ", decryptedPassword_";
  protected final String TEXT_215 = ", ";
  protected final String TEXT_216 = ", \"\");" + NL + "                        \t";
  protected final String TEXT_217 = NL + "                        \tmaprLogin_";
  protected final String TEXT_218 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_219 = ", ";
  protected final String TEXT_220 = ", decryptedPassword_";
  protected final String TEXT_221 = ", ";
  protected final String TEXT_222 = ");" + NL + "                        \t";
  protected final String TEXT_223 = NL + "\t\t        props_";
  protected final String TEXT_224 = ".put(\"mapred.job.map.memory.mb\", ";
  protected final String TEXT_225 = ");" + NL + "\t\t        props_";
  protected final String TEXT_226 = ".put(\"mapred.job.reduce.memory.mb\", ";
  protected final String TEXT_227 = ");";
  protected final String TEXT_228 = NL + "\t\t\t\t\tprops_";
  protected final String TEXT_229 = ".put(";
  protected final String TEXT_230 = " ,";
  protected final String TEXT_231 = ");";
  protected final String TEXT_232 = NL + "\t    \t\tprops_";
  protected final String TEXT_233 = ".put(\"mapreduce.map.memory.mb\", ";
  protected final String TEXT_234 = ");" + NL + "\t    \t\tprops_";
  protected final String TEXT_235 = ".put(\"mapreduce.reduce.memory.mb\", ";
  protected final String TEXT_236 = ");" + NL + "\t    \t\tprops_";
  protected final String TEXT_237 = ".put(\"yarn.app.mapreduce.am.resource.mb\", ";
  protected final String TEXT_238 = ");";
  protected final String TEXT_239 = NL + "                props_";
  protected final String TEXT_240 = ".put(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_241 = NL + "\t\t\t\torg.apache.pig.impl.PigContext context_";
  protected final String TEXT_242 = " = new org.apache.pig.impl.PigContext(new org.apache.pig.backend.hadoop.executionengine.tez.TezExecType(), props_";
  protected final String TEXT_243 = ");";
  protected final String TEXT_244 = NL + "\t\t\t\torg.apache.pig.impl.PigContext context_";
  protected final String TEXT_245 = " = new org.apache.pig.impl.PigContext(org.apache.pig.ExecType.MAPREDUCE, props_";
  protected final String TEXT_246 = ");";
  protected final String TEXT_247 = NL + "                String decryptedS3Password_";
  protected final String TEXT_248 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_249 = ");";
  protected final String TEXT_250 = NL + "                String decryptedS3Password_";
  protected final String TEXT_251 = " = ";
  protected final String TEXT_252 = ";";
  protected final String TEXT_253 = NL + "\t\t\t\torg.apache.pig.PigServer pigServer_";
  protected final String TEXT_254 = " = new org.apache.pig.PigServer(context_";
  protected final String TEXT_255 = ");" + NL + "\t\t\t\tpigServer_";
  protected final String TEXT_256 = ".setBatchOn();";
  protected final String TEXT_257 = NL + "\t\t\t//store the pig script in the lookup process" + NL + "\t\t\tjava.util.List<String[]> pigScript_";
  protected final String TEXT_258 = " = new java.util.ArrayList<String[]>();";
  protected final String TEXT_259 = NL + "\t\troutines.system.PigHelper helper_";
  protected final String TEXT_260 = " = new routines.system.PigHelper();" + NL;
  protected final String TEXT_261 = NL + "\t\tStringBuilder script_";
  protected final String TEXT_262 = "=new StringBuilder();";
  protected final String TEXT_263 = NL + "\t\tscript_";
  protected final String TEXT_264 = ".append(\"SET \"+";
  protected final String TEXT_265 = "+\" \"+";
  protected final String TEXT_266 = "+\";\");";
  protected final String TEXT_267 = NL + "\t\tscript_";
  protected final String TEXT_268 = ".append(\"SET output.compression.enabled true;\");";
  protected final String TEXT_269 = NL + "\t\tscript_";
  protected final String TEXT_270 = ".append(\"SET output.compression.codec org.apache.hadoop.io.compress.GzipCodec;\");";
  protected final String TEXT_271 = NL + "\t\tscript_";
  protected final String TEXT_272 = ".append(\"SET output.compression.codec org.apache.hadoop.io.compress.BZip2Codec;\");";
  protected final String TEXT_273 = NL + "\t\tscript_";
  protected final String TEXT_274 = ".append(\"SET hbase.zookeeper.quorum \").append(";
  protected final String TEXT_275 = ").append(\";\");" + NL + "\t\tscript_";
  protected final String TEXT_276 = ".append(\"SET hbase.zookeeper.property.clientPort \").append(";
  protected final String TEXT_277 = ").append(\";\");";
  protected final String TEXT_278 = NL + "\t\t\tscript_";
  protected final String TEXT_279 = ".append(\"SET zookeeper.znode.parent \").append(";
  protected final String TEXT_280 = ").append(\";\");";
  protected final String TEXT_281 = NL + "    \t\t\t\t\tSystem.err.println(\"Please set the path of Tez lib in 'Tez lib path'!\");" + NL + "    \t\t\t\t";
  protected final String TEXT_282 = NL + "\t\t\t\t";
  protected final String TEXT_283 = NL;
  protected final String TEXT_284 = NL + "        String hdfs_username_";
  protected final String TEXT_285 = " = (";
  protected final String TEXT_286 = " != null && !\"\".equals(";
  protected final String TEXT_287 = ")) ? ";
  protected final String TEXT_288 = " : System.getProperty(\"user.name\");;" + NL + "        org.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_289 = " = null;";
  protected final String TEXT_290 = NL + "        org.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_291 = " = new org.apache.hadoop.conf.Configuration(); " + NL + "        conf_";
  protected final String TEXT_292 = ".set(\"fs.default.name\", ";
  protected final String TEXT_293 = ");" + NL + "        ";
  protected final String TEXT_294 = NL + "            conf_";
  protected final String TEXT_295 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_296 = NL + "        \t\tconf_";
  protected final String TEXT_297 = ".set(";
  protected final String TEXT_298 = " ,";
  protected final String TEXT_299 = ");" + NL + "        \t";
  protected final String TEXT_300 = NL + "    \t\tconf_";
  protected final String TEXT_301 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_302 = ");" + NL + "    \t\t";
  protected final String TEXT_303 = NL + "    \t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_304 = ", ";
  protected final String TEXT_305 = ");" + NL + "    \t\t";
  protected final String TEXT_306 = NL + "\t\t\tconf_";
  protected final String TEXT_307 = ".set(\"hadoop.job.ugi\",hdfs_username_";
  protected final String TEXT_308 = "+\",\"+";
  protected final String TEXT_309 = ");" + NL + "        \tfs_";
  protected final String TEXT_310 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_311 = ");";
  protected final String TEXT_312 = NL + "        \t" + NL + "        \tif(hdfs_username_";
  protected final String TEXT_313 = " == null || \"\".equals(hdfs_username_";
  protected final String TEXT_314 = ")){" + NL + "        \t\tfs_";
  protected final String TEXT_315 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_316 = ");" + NL + "        \t}else{" + NL + "        \t\tfs_";
  protected final String TEXT_317 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_318 = ".get(\"fs.default.name\")),conf_";
  protected final String TEXT_319 = ",hdfs_username_";
  protected final String TEXT_320 = ");" + NL + "        \t}\t";
  protected final String TEXT_321 = NL + "\t\t\t\t";
  protected final String TEXT_322 = NL + "\t\t\t\t\tString hdfsUserName_";
  protected final String TEXT_323 = " = (";
  protected final String TEXT_324 = " != null && !\"\".equals(";
  protected final String TEXT_325 = ")) ? ";
  protected final String TEXT_326 = " : System.getProperty(\"user.name\");" + NL + "\t    \t\t\tString tezLibPath_";
  protected final String TEXT_327 = " = \"/tmp/\" + hdfsUserName_";
  protected final String TEXT_328 = " + \"/talend_tez_libs/";
  protected final String TEXT_329 = "\";" + NL + "\t\t\t\t";
  protected final String TEXT_330 = NL + "\t\t\t\t\tString tezLibPath_";
  protected final String TEXT_331 = " = ";
  protected final String TEXT_332 = ";" + NL + "\t\t\t\t";
  protected final String TEXT_333 = NL + "\t\t\t\tfs_";
  protected final String TEXT_334 = ".mkdirs(new org.apache.hadoop.fs.Path(tezLibPath_";
  protected final String TEXT_335 = "));" + NL + "\t\t\t\t";
  protected final String TEXT_336 = NL + "\t\t\t\tString[] classPaths_";
  protected final String TEXT_337 = " = System.getProperty(\"java.class.path\").split(";
  protected final String TEXT_338 = "String.valueOf(globalMap.get(\"current_client_path_separator\"))";
  protected final String TEXT_339 = "System.getProperty(\"path.separator\")";
  protected final String TEXT_340 = ");" + NL + "\t\t\t\tfor(String classPath_";
  protected final String TEXT_341 = " : classPaths_";
  protected final String TEXT_342 = "){" + NL + "\t\t\t\t\t";
  protected final String TEXT_343 = NL + "\t\t\t\t\t\tif(classPath_";
  protected final String TEXT_344 = ".endsWith(\"";
  protected final String TEXT_345 = "\")){" + NL + "\t\t\t\t\t\t\torg.apache.hadoop.fs.Path tezJarPath_";
  protected final String TEXT_346 = " = new org.apache.hadoop.fs.Path(tezLibPath_";
  protected final String TEXT_347 = " + \"/";
  protected final String TEXT_348 = "\");" + NL + "\t\t\t\t\t\t\tif(!fs_";
  protected final String TEXT_349 = ".exists(tezJarPath_";
  protected final String TEXT_350 = ")){" + NL + "\t\t\t\t\t\t\t\tfs_";
  protected final String TEXT_351 = ".copyFromLocalFile(false, false, new org.apache.hadoop.fs.Path(classPath_";
  protected final String TEXT_352 = "), tezJarPath_";
  protected final String TEXT_353 = ");" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_354 = NL + "\t    \t\t}" + NL + "\t\t\t";
  protected final String TEXT_355 = NL + "\t\t\t\tString tezLibPath_";
  protected final String TEXT_356 = " = ";
  protected final String TEXT_357 = ";" + NL + "\t\t\t";
  protected final String TEXT_358 = NL + "\t\t\tString[] tezLibPaths_";
  protected final String TEXT_359 = " = tezLibPath_";
  protected final String TEXT_360 = ".split(\",\");" + NL + "\t\t\tscript_";
  protected final String TEXT_361 = ".append(\"SET tez.lib.uris \");" + NL + "\t\t\tint tezLibPathCount_";
  protected final String TEXT_362 = " = 1;" + NL + "\t\t\tfor(String tezLibPathKey_";
  protected final String TEXT_363 = " : tezLibPaths_";
  protected final String TEXT_364 = "){" + NL + "\t\t\t\t script_";
  protected final String TEXT_365 = ".append(";
  protected final String TEXT_366 = " + \"/\" + tezLibPathKey_";
  protected final String TEXT_367 = ");" + NL + "\t\t\t\t if(tezLibPathCount_";
  protected final String TEXT_368 = " < tezLibPaths_";
  protected final String TEXT_369 = ".length){" + NL + "\t\t\t\t \tscript_";
  protected final String TEXT_370 = ".append(\",\");" + NL + "\t\t\t\t }" + NL + "\t\t\t\t tezLibPathCount_";
  protected final String TEXT_371 = "++;" + NL + "\t\t\t}" + NL + "\t\t\tscript_";
  protected final String TEXT_372 = ".append(\";\");" + NL + "\t\t";
  protected final String TEXT_373 = NL + "\t\thelper_";
  protected final String TEXT_374 = ".add(\"script\",script_";
  protected final String TEXT_375 = ".toString());";
  protected final String TEXT_376 = NL + NL + "\t\tStringBuilder sb_";
  protected final String TEXT_377 = " = new StringBuilder();" + NL;
  protected final String TEXT_378 = NL + "\t\t\t\t\t\thelper_";
  protected final String TEXT_379 = ".add(\"jar\",getJarsToRegister_";
  protected final String TEXT_380 = ".replaceJarPaths(\"";
  protected final String TEXT_381 = "\"));";
  protected final String TEXT_382 = NL + "\t\t\t\thelper_";
  protected final String TEXT_383 = ".add(\"function\", ";
  protected final String TEXT_384 = " ,";
  protected final String TEXT_385 = ");";
  protected final String TEXT_386 = NL + "\t\t\t\t\t\thelper_";
  protected final String TEXT_387 = ".add(\"jar\",";
  protected final String TEXT_388 = ");";
  protected final String TEXT_389 = NL + NL + "\t\t\tSystem.setProperty(\"hive.metastore.local\", \"false\");" + NL + "\t\t\tSystem.setProperty(\"hive.metastore.uris\", ";
  protected final String TEXT_390 = ");" + NL + "\t\t\tsb_";
  protected final String TEXT_391 = ".append(\"";
  protected final String TEXT_392 = "_";
  protected final String TEXT_393 = "_RESULT";
  protected final String TEXT_394 = " = LOAD '\"+";
  protected final String TEXT_395 = "+\".\"+";
  protected final String TEXT_396 = "+\"' using ";
  protected final String TEXT_397 = ".";
  protected final String TEXT_398 = "()\");";
  protected final String TEXT_399 = NL + "\t            sb_";
  protected final String TEXT_400 = ".append(\"";
  protected final String TEXT_401 = "_";
  protected final String TEXT_402 = "_RESULT = LOAD '\" + \"s3n://\" + ";
  protected final String TEXT_403 = " +\":\" + decryptedS3Password_";
  protected final String TEXT_404 = " + \"@\" + ";
  protected final String TEXT_405 = NL + "\t                + \"' using com.twitter.elephantbird.pig.load.SequenceFileLoader('-c ";
  protected final String TEXT_406 = "','-c ";
  protected final String TEXT_407 = "')\");" + NL + "\t            ";
  protected final String TEXT_408 = NL + "\t            sb_";
  protected final String TEXT_409 = ".append(\"";
  protected final String TEXT_410 = "_";
  protected final String TEXT_411 = "_RESULT = LOAD '\" + ";
  protected final String TEXT_412 = " +" + NL + "\t                \"' using com.twitter.elephantbird.pig.load.SequenceFileLoader('-c ";
  protected final String TEXT_413 = "','-c ";
  protected final String TEXT_414 = "')\");" + NL + "\t            ";
  protected final String TEXT_415 = NL + "\t\t\t\tsb_";
  protected final String TEXT_416 = ".append(\" AS (\");" + NL + "\t\t\t\tsb_";
  protected final String TEXT_417 = ".append(\"";
  protected final String TEXT_418 = ":";
  protected final String TEXT_419 = ", ";
  protected final String TEXT_420 = ":";
  protected final String TEXT_421 = "\");" + NL + "\t\t\t\tsb_";
  protected final String TEXT_422 = ".append(\")\");";
  protected final String TEXT_423 = NL + "\t                sb_";
  protected final String TEXT_424 = ".append(\"";
  protected final String TEXT_425 = "_";
  protected final String TEXT_426 = "_RESULT = LOAD '\" + \"s3n://\" + ";
  protected final String TEXT_427 = " +\":\" + decryptedS3Password_";
  protected final String TEXT_428 = " + \"@\" + ";
  protected final String TEXT_429 = NL + "\t                    + \"' using \" + ";
  protected final String TEXT_430 = ");" + NL + "\t                ";
  protected final String TEXT_431 = NL + "\t                sb_";
  protected final String TEXT_432 = ".append(\"";
  protected final String TEXT_433 = "_";
  protected final String TEXT_434 = "_RESULT = LOAD '\"+";
  protected final String TEXT_435 = "+\"' using \" + ";
  protected final String TEXT_436 = ");" + NL + "\t                ";
  protected final String TEXT_437 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_438 = ".append(\"";
  protected final String TEXT_439 = "_";
  protected final String TEXT_440 = "_RESULT = LOAD 'hbase://\").append(";
  protected final String TEXT_441 = ").append(\"' using org.apache.pig.backend.hadoop.hbase.HBaseStorage('\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_442 = NL + "\t\t\t\t\t\t\tsb_";
  protected final String TEXT_443 = ".append(";
  protected final String TEXT_444 = ");";
  protected final String TEXT_445 = NL + "\t\t\t\t\t\t\tsb_";
  protected final String TEXT_446 = ".append(\" \");";
  protected final String TEXT_447 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_448 = ".append(\"'\");";
  protected final String TEXT_449 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_450 = ".append(\",'-loadKey true\");";
  protected final String TEXT_451 = NL + "\t\t\t\t\t\t\t\tsb_";
  protected final String TEXT_452 = ".append(\",'\");";
  protected final String TEXT_453 = NL + "\t\t\t\t\t\t\t\tsb_";
  protected final String TEXT_454 = ".append(\" \");";
  protected final String TEXT_455 = NL + "\t\t\t\t\t\t\tsb_";
  protected final String TEXT_456 = ".append(\"-";
  protected final String TEXT_457 = " \").append(";
  protected final String TEXT_458 = ");";
  protected final String TEXT_459 = NL + "\t\t\t\t\t\t\tsb_";
  protected final String TEXT_460 = ".append(\" \");";
  protected final String TEXT_461 = NL + "\t\t\t\t\t\t\tsb_";
  protected final String TEXT_462 = ".append(\"'\");";
  protected final String TEXT_463 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_464 = ".append(\"'\");";
  protected final String TEXT_465 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_466 = ".append(\")\");";
  protected final String TEXT_467 = NL + "\t                    sb_";
  protected final String TEXT_468 = ".append(\"";
  protected final String TEXT_469 = "_";
  protected final String TEXT_470 = "_RESULT = LOAD '\" + \"s3n://\" + ";
  protected final String TEXT_471 = " +\":\" + decryptedS3Password_";
  protected final String TEXT_472 = " + \"@\" + ";
  protected final String TEXT_473 = NL + "\t                        + \"' using parquet.pig.ParquetLoader\");" + NL + "\t                    ";
  protected final String TEXT_474 = NL + "\t                    sb_";
  protected final String TEXT_475 = ".append(\"";
  protected final String TEXT_476 = "_";
  protected final String TEXT_477 = "_RESULT = LOAD '\"+";
  protected final String TEXT_478 = "+\"' using parquet.pig.ParquetLoader\");" + NL + "\t                    ";
  protected final String TEXT_479 = NL + "                        sb_";
  protected final String TEXT_480 = ".append(\"";
  protected final String TEXT_481 = "_";
  protected final String TEXT_482 = "_RESULT = LOAD '\" + \"s3n://\" + ";
  protected final String TEXT_483 = " +\":\" + decryptedS3Password_";
  protected final String TEXT_484 = " + \"@\" + ";
  protected final String TEXT_485 = NL + "                            + \"' using ";
  protected final String TEXT_486 = "(";
  protected final String TEXT_487 = ")\");";
  protected final String TEXT_488 = NL + "                        sb_";
  protected final String TEXT_489 = ".append(\"";
  protected final String TEXT_490 = "_";
  protected final String TEXT_491 = "_RESULT = LOAD '\"+";
  protected final String TEXT_492 = "+\"' using ";
  protected final String TEXT_493 = "(";
  protected final String TEXT_494 = ")\");";
  protected final String TEXT_495 = NL + "\t\t\t\t\t\tsb_";
  protected final String TEXT_496 = ".append(\" AS (\");";
  protected final String TEXT_497 = NL + "\t\t\t\t\t\t\tsb_";
  protected final String TEXT_498 = ".append(\"";
  protected final String TEXT_499 = ":";
  protected final String TEXT_500 = "\");";
  protected final String TEXT_501 = NL + "\t\t\t\t\t\tsb_";
  protected final String TEXT_502 = ".append(\")\");";
  protected final String TEXT_503 = NL + "\t\tsb_";
  protected final String TEXT_504 = ".append(\";\");" + NL + "" + NL + "\t\thelper_";
  protected final String TEXT_505 = ".add(\"query\",sb_";
  protected final String TEXT_506 = ".toString());";
  protected final String TEXT_507 = NL + "\t\t\t\tsb_";
  protected final String TEXT_508 = " = new StringBuilder();" + NL + "\t\t\t\tsb_";
  protected final String TEXT_509 = ".append(\"";
  protected final String TEXT_510 = "_";
  protected final String TEXT_511 = "_RESULT = FILTER ";
  protected final String TEXT_512 = "_";
  protected final String TEXT_513 = "_RESULT_TEMP BY \");";
  protected final String TEXT_514 = NL + "\t\t\t\t\t\tsb_";
  protected final String TEXT_515 = ".append(\" AND \");";
  protected final String TEXT_516 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_517 = ".append(\"(\" + ";
  protected final String TEXT_518 = ");" + NL + "\t\t\t\t\tsb_";
  protected final String TEXT_519 = ".append(\" == \");" + NL + "\t\t\t\t\tsb_";
  protected final String TEXT_520 = ".append(";
  protected final String TEXT_521 = " + \")\");";
  protected final String TEXT_522 = NL + "\t\t\t\tsb_";
  protected final String TEXT_523 = ".append(\";\");" + NL + "" + NL + "\t\t\t\thelper_";
  protected final String TEXT_524 = ".add(\"query\",sb_";
  protected final String TEXT_525 = ".toString());";
  protected final String TEXT_526 = NL + NL + "\t\t";
  protected final String TEXT_527 = NL + "\tjava.util.List<String[]> pigLatins_";
  protected final String TEXT_528 = " = helper_";
  protected final String TEXT_529 = ".getPigLatins();";
  protected final String TEXT_530 = NL + "\t\tfor(String[] pigLatin_";
  protected final String TEXT_531 = " : pigLatins_";
  protected final String TEXT_532 = ") {" + NL + "\t\t\tString type_";
  protected final String TEXT_533 = " = pigLatin_";
  protected final String TEXT_534 = "[0];" + NL + "\t\t\t";
  protected final String TEXT_535 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_536 = " - register \" + type_";
  protected final String TEXT_537 = " + \" : \" + pigLatin_";
  protected final String TEXT_538 = "[1]);" + NL + "\t\t\t";
  protected final String TEXT_539 = NL + "\t\t\tif(\"query\".equals(type_";
  protected final String TEXT_540 = ")) {";
  protected final String TEXT_541 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_542 = ".write(pigLatin_";
  protected final String TEXT_543 = "[1]);";
  protected final String TEXT_544 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_545 = ".registerQuery(pigLatin_";
  protected final String TEXT_546 = "[1]);";
  protected final String TEXT_547 = NL + "\t\t\t} else if(\"jar\".equals(type_";
  protected final String TEXT_548 = ")) {";
  protected final String TEXT_549 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_550 = ".write(\"REGISTER \" + wasbPath_";
  protected final String TEXT_551 = " + new java.io.File(pigLatin_";
  protected final String TEXT_552 = "[1]).getName() + \";\");" + NL + "\t\t\t\t\tlibjars_";
  protected final String TEXT_553 = ".append(pigLatin_";
  protected final String TEXT_554 = "[1] + \",\");";
  protected final String TEXT_555 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_556 = ".registerJar(pigLatin_";
  protected final String TEXT_557 = "[1]);";
  protected final String TEXT_558 = NL + "\t\t\t} else if(\"script\".equals(type_";
  protected final String TEXT_559 = ")) {";
  protected final String TEXT_560 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_561 = ".write(pigLatin_";
  protected final String TEXT_562 = "[1]);";
  protected final String TEXT_563 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_564 = ".registerScript(new java.io.ByteArrayInputStream(pigLatin_";
  protected final String TEXT_565 = "[1].getBytes()));";
  protected final String TEXT_566 = NL + "\t\t\t} else if(\"function\".equals(type_";
  protected final String TEXT_567 = ")) {";
  protected final String TEXT_568 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_569 = ".write(\"DEFINE \" + pigLatin_";
  protected final String TEXT_570 = "[1] + \" \" + pigLatin_";
  protected final String TEXT_571 = "[2] + \";\");";
  protected final String TEXT_572 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_573 = ".registerFunction(pigLatin_";
  protected final String TEXT_574 = "[1], new org.apache.pig.FuncSpec(pigLatin_";
  protected final String TEXT_575 = "[2]));";
  protected final String TEXT_576 = NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_577 = NL + "    \tpigScript_";
  protected final String TEXT_578 = ".addAll(pigLatins_";
  protected final String TEXT_579 = ");";
  protected final String TEXT_580 = NL + "\tpigLatins_";
  protected final String TEXT_581 = ".clear();";
  protected final String TEXT_582 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();
	String cid = node.getUniqueName();
	String processId = node.getProcess().getId();

	String distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
	String pigVersion = ElementParameterParser.getValue(node, "__PIG_VERSION__");
	
	boolean isLocal = "true".equals(ElementParameterParser.getValue(node, "__LOCAL__"));
	boolean isCustom = false;

	org.talend.hadoop.distribution.component.PigComponent pigDistrib = null;
	if(!isLocal) {
    	try {
    		pigDistrib = (org.talend.hadoop.distribution.component.PigComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, pigVersion);
    	} catch (java.lang.Exception e) {
    		e.printStackTrace();
    		return "";
    	}
    	isCustom = pigDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
    }

	boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
	String inputFilename = ElementParameterParser.getValue(node, "__INPUT_FILENAME__");
	boolean isS3Location = "true".equals(ElementParameterParser.getValue(node, "__S3_LOCATION_LOAD__"));
    String s3bucket = ElementParameterParser.getValue(node, "__S3_BUCKET_LOAD__");
    String s3username = ElementParameterParser.getValue(node, "__S3_USERNAME_LOAD__");
	String databaseName = ElementParameterParser.getValue(node, "__DATABASE_NAME__");
	String tableName = ElementParameterParser.getValue(node, "__TABLE_NAME__");
	String fieldSeparator = ElementParameterParser.getValue(node, "__FIELD_SEPARATOR_CHAR__");
	String function = ElementParameterParser.getValue(node, "__LOAD__");

	String fsDefaultName = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
	String mapredJobTracker = ElementParameterParser.getValue(node, "__MAPRED_JOB_TRACKER__");
	String thriftServer = ElementParameterParser.getValue(node, "__THRIFT_SERVER__");

	List<Map<String, String>> advProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ADVANCED_PROPERTIES__");
	List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");

	List<Map<String, String>> partitionFilter = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__PARTITION_FILTER__");

	boolean compress = "true".equals(ElementParameterParser.getValue(node, "__COMPRESS__"));
	String compression = ElementParameterParser.getValue(node, "__COMPRESSION__");

	String zookeeper_quorum = ElementParameterParser.getValue(node, "__ZOOKEEPER_QUORUM__");
	String zookeeper_client_port = ElementParameterParser.getValue(node, "__ZOOKEEPER_CLIENT_PORT__");

	boolean setZNodeParent = "true".equals(ElementParameterParser.getValue(node, "__SET_ZNODE_PARENT__"));
	String zNodeParent = ElementParameterParser.getValue(node, "__ZNODE_PARENT__");

	String resourceManager = ElementParameterParser.getValue(node, "__RESOURCE_MANAGER__");
	boolean useYarn = "true".equals(ElementParameterParser.getValue(node, "__USE_YARN__"));
	boolean setJobHistoryAddress = "true".equals(ElementParameterParser.getValue(node, "__SET_JOBHISTORY_ADDRESS__"));
    boolean useDatanodeHostname = "true".equals(ElementParameterParser.getValue(node, "__USE_DATANODE_HOSTNAME__"));

	boolean isParquet = !isLocal && "ParquetLoader".equals(function);

	boolean defineJarsToRegister = "true".equals(ElementParameterParser.getValue(node, "__DEFINE_REGISTER_JAR__"));
	List<Map<String, String>> registerJarForHCatalog = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__REGISTER_JAR__");

	List<Map<String, String>> registerJar = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__DRIVER_JAR__");
	List<Map<String, String>> defineFunctions = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__DEFINE_FUNCTION__");

    boolean useMapRTicket = ElementParameterParser.getBooleanValue(node, "__USE_MAPRTICKET__");
    String mapRTicketUsername = ElementParameterParser.getValue(node, "__MAPRTICKET_USERNAME__");
    String mapRTicketCluster = ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
    String mapRTicketDuration = ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");

    boolean setMapRHomeDir = ElementParameterParser.getBooleanValue(node, "__SET_MAPR_HOME_DIR__");
    String mapRHomeDir = ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");

    boolean setMapRHadoopLogin = ElementParameterParser.getBooleanValue(node, "__SET_HADOOP_LOGIN__");
    String mapRHadoopLogin = ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");
	
	boolean isExecutedThroughWebHCat = !isLocal && (pigDistrib != null && pigDistrib.isExecutedThroughWebHCat());

	String hcatPackage = (pigDistrib != null && pigDistrib.pigVersionPriorTo_0_12() && !isCustom) ? "org.apache.hcatalog.pig" : "org.apache.hive.hcatalog.pig";

	boolean generateRegisterJarCodeForHCatalog = (!isLocal && (isCustom || (pigDistrib != null && pigDistrib.doSupportHCatalog())) && "HCatLoader".equals(function) && !defineJarsToRegister);
	boolean generateRegisterJarCode = registerJar.size() > 0;

	java.util.List<String> jarsToRegister = null;
	java.util.List<String> jars = null;

	boolean generateRegisterJarCodeForHBase = !isLocal && "HBaseStorage".equals(function) && pigDistrib!= null && pigDistrib.doSupportHBase();

	boolean generateRegisterJarCodeForSequenceFile = !isLocal && "SequenceFileStorage".equals(function);

	boolean generateRegisterJarCodeForRCFile = !isLocal && "RCFilePigStorage".equals(function);

	boolean generateRegisterJarCodeForAvroFile = !isLocal && "AvroStorage".equals(function);

	boolean generateRegisterJarCodeForParquetFile = isParquet;

	boolean generateRegisterJarCodeForAll = true;

	boolean isHadoop2 = !isCustom && pigDistrib!=null && pigDistrib.isHadoop2();

	boolean configureFromClassPath = "true".equals(ElementParameterParser.getValue(node, "__CONFIGURATIONS_FROM_CLASSPATH__"));

	String passwordFieldName = "";

	if(generateRegisterJarCodeForAll) {
		String[] commandLine = new String[] {"<command>"};
		try {
			commandLine = ProcessorUtilities.getCommandLine("win32",true, processId, "",org.talend.designer.runprocess.IProcessor.NO_STATISTICS,org.talend.designer.runprocess.IProcessor.NO_TRACES, new String[]{});
		} catch (ProcessorException e) {
			e.printStackTrace();
		}

		jarsToRegister = new java.util.ArrayList();

		jarsToRegister.add("datafu");

		if(generateRegisterJarCode) {
			for(Map<String, String> jar : registerJar){
				jarsToRegister.add(jar.get("JAR_NAME"));
			}
		}

		if(generateRegisterJarCodeForHCatalog) {
			jarsToRegister.add("hcatalog");

			jarsToRegister.add("hcatalog-core");

			jarsToRegister.add("hive-hcatalog-core");

			jarsToRegister.add("hive-exec");
			jarsToRegister.add("hive-metastore");
			jarsToRegister.add("jdo2-api");
			jarsToRegister.add("libfb303");
			jarsToRegister.add("libthrift");
		}

		if(generateRegisterJarCodeForHBase) {
			jarsToRegister.add("protobuf-java");
			jarsToRegister.add("hbase");
			jarsToRegister.add("hbase-client");
			jarsToRegister.add("hbase-common");
			jarsToRegister.add("hbase-hadoop-compat");
			jarsToRegister.add("hbase-protocol");
			jarsToRegister.add("hbase-server");
			jarsToRegister.add("zookeeper");
			jarsToRegister.add("guava");
			jarsToRegister.add("htrace-core");
		}

		if(generateRegisterJarCodeForSequenceFile) {
			jarsToRegister.add("elephant-bird-core");
			jarsToRegister.add("elephant-bird-hadoop-compat");
			jarsToRegister.add("elephant-bird-pig");
			jarsToRegister.add("pigutil");
		}

		if(generateRegisterJarCodeForRCFile) {
			jarsToRegister.add("elephant-bird-core");
			jarsToRegister.add("elephant-bird-hadoop-compat");
			jarsToRegister.add("elephant-bird-rcfile");
			jarsToRegister.add("hive-serde");
			jarsToRegister.add("hive-common");
			jarsToRegister.add("hive-exec");
		}

		if(generateRegisterJarCodeForAvroFile) {
			jarsToRegister.add("piggybank");
			jarsToRegister.add("avro");
			jarsToRegister.add("json_simple");
		}

		if(generateRegisterJarCodeForParquetFile) {
			jarsToRegister.add("parquet-pig-bundle");
			jarsToRegister.add("snappy-java");
		}

		for (int j = 0; j < commandLine.length; j++) {
			if(commandLine[j].contains("jar")) {
				jars = java.util.Arrays.asList(commandLine[j].split(";"));
				break;
			}
		}
	}

	String start_node=cid;

	boolean inMain = true;

	if(node.getIncomingConnections()!=null && node.getIncomingConnections().size()>0) {
		INode loadNode = node.getDesignSubjobStartNode();
		inMain = loadNode.isSubtreeStart();
		start_node = loadNode.getUniqueName();
	}

	String outputConnectionName = "";
	List<IConnection> outputConnections = (List<IConnection>)node.getOutgoingConnections();
	if(outputConnections!=null && outputConnections.size()>0) {
		outputConnectionName = outputConnections.get(0).getName();
	}

	boolean useTez = !isLocal && pigDistrib != null && pigDistrib.doSupportTezForPig() && "TEZ".equals(ElementParameterParser.getValue(node, "__ENGINE__"));

    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    
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
			
    stringBuffer.append(TEXT_4);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_6);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_9);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_11);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_12);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_15);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_17);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_20);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_22);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_23);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_25);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_26);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_29);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_31);
    
			}
		}
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    
	log4jFileUtil.componentStartInfo(node);
	final boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

    
	if(generateRegisterJarCodeForAll) {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_33);
    
	}

	if(!isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    
	}

	if(inMain && !isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    
	}

	// BEGIN IF01
	if(configureFromClassPath){

    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
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
    
		if("HBaseStorage".equals(function) && pigDistrib.doSupportHBase()){

    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    
		}

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
    
		// log all loaded xxx-site.xml files and all of the key value pairs for debugging
		if(isLog4jEnabled){
		
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    
		}

    stringBuffer.append(TEXT_69);
    
	} // END IF01

    stringBuffer.append(TEXT_70);
    
		// BEGIN IF02
		if(inMain && isExecutedThroughWebHCat) {
			passwordFieldName = "__HDINSIGHT_PASSWORD__";
			if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_73);
    
			} else {

    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_76);
    
			}

			passwordFieldName = "__WASB_PASSWORD__";
			if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {

    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_79);
    
			} else {

    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_82);
    
			}

    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WASB_USERNAME__"));
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WASB_CONTAINER__"));
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HDINSIGHT_USERNAME__"));
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_USERNAME__"));
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_HOST__"));
    stringBuffer.append(TEXT_102);
    stringBuffer.append(ElementParameterParser.getValue(node, "__WEBHCAT_PORT__"));
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(ElementParameterParser.getValue(node, "__STATUSDIR__"));
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(ElementParameterParser.getValue(node, "__REMOTE_FOLDER__"));
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    
		} // END IF02

		// BEGIN IF03
		if(isLocal) { // LOCAL mode

    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    
		// BEGIN ELSE03
		} else if(!isLocal && !isExecutedThroughWebHCat && pigDistrib != null) { // MAPREDUCE mode
			// BEGIn IF04
			if(inMain) {
				String yarnClasspathSeparator = ElementParameterParser.getValue(node, "__CLASSPATH_SEPARATOR__");

    stringBuffer.append(TEXT_115);
    stringBuffer.append(yarnClasspathSeparator);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    
				if(isCustom || (!isCustom && pigDistrib.doSupportImpersonation())) {
					String username = ElementParameterParser.getValue(node, "__USERNAME__");

    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_119);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_123);
    
				}

				// Do not set these configurations if we're using the classpath configuration
				if(!configureFromClassPath && !isCustom && ("MAPR401".equals(pigVersion) || "MAPR410".equals(pigVersion))) {//set the default properties

    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    
				}
			} // END IF04

			// BEGIN IF05
			// All of these configurations are not set when we're using the classpath for configurations
			if(!configureFromClassPath){

				if(!isCustom && "APACHE_2_4_0_EMR".equals(pigVersion)) {
	                
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_130);
    
	            } else {
	                
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_133);
    
	            }

				boolean isKerberosAvailableHadoop2 = !isCustom && pigDistrib.isHadoop2() && pigDistrib.doSupportKerberos();
				boolean isKerberosAvailableHadoop1 = !isCustom && pigDistrib.isHadoop1() && pigDistrib.doSupportKerberos();

				if((isCustom && useYarn) || (!isCustom && isHadoop2)) {

    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    stringBuffer.append(resourceManager);
    stringBuffer.append(TEXT_137);
    
					boolean setSchedulerAddress = "true".equals(ElementParameterParser.getValue(node, "__SET_SCHEDULER_ADDRESS__"));
					if(setSchedulerAddress) {
						String schedulerAddress = ElementParameterParser.getValue(node,"__RESOURCEMANAGER_SCHEDULER_ADDRESS__");

    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(schedulerAddress);
    stringBuffer.append(TEXT_140);
    
					}
					boolean setStagingDirectory = "true".equals(ElementParameterParser.getValue(node, "__SET_STAGING_DIRECTORY__"));
					if(setStagingDirectory) {
						String stagingDirectory = ElementParameterParser.getValue(node, "__STAGING_DIRECTORY__");

    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(stagingDirectory);
    stringBuffer.append(TEXT_143);
    
					}

					boolean crossPlatformSubmission = "true".equals(ElementParameterParser.getValue(node, "__CROSS_PLATFORM_SUBMISSION__"));
					if((!isCustom && pigDistrib.doSupportCrossPlatformSubmission()) || (isCustom && useYarn && crossPlatformSubmission)) {

    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    
					}

					if(pigDistrib.doSupportCustomMRApplicationCP()){

    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(pigDistrib.getCustomMRApplicationCP());
    stringBuffer.append(TEXT_148);
    
					}


    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_150);
    stringBuffer.append(pigDistrib.getYarnApplicationClasspath());
    stringBuffer.append(TEXT_151);
    
					if(setJobHistoryAddress) {
						String jobHistoryAddress = ElementParameterParser.getValue(node, "__JOBHISTORY_ADDRESS__");

    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_153);
    stringBuffer.append(jobHistoryAddress);
    stringBuffer.append(TEXT_154);
    
					}
				} else {

    stringBuffer.append(TEXT_155);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_156);
    stringBuffer.append(mapredJobTracker);
    stringBuffer.append(TEXT_157);
    
				}

				if("HCatLoader".equals(function) && pigDistrib.doSupportHCatalog()) { // If the HCatalog loader is used, we need to add the HCat metastore in the pig context

    stringBuffer.append(TEXT_158);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(thriftServer);
    stringBuffer.append(TEXT_160);
    
                    // activate sasl if we using a security
                    if ((isCustom || pigDistrib.doSupportMapRTicket()) && useMapRTicket) {

    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    
                    }
				}

				if("HBaseStorage".equals(function) && pigDistrib.doSupportHBase()) {

    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_164);
    stringBuffer.append(zookeeper_quorum);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(zookeeper_client_port);
    stringBuffer.append(TEXT_167);
    
					if(setZNodeParent) {

    stringBuffer.append(TEXT_168);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(zNodeParent);
    stringBuffer.append(TEXT_170);
    
					}
				}

				if(useKrb) {
					String namenodePrincipal = ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");
					boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
					String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
					String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
					String hbaseMasterPrincipal = ElementParameterParser.getValue(node, "__HBASEMASTER_PRINCIPAL__");
					String hbaseReigonServerPrincipal = ElementParameterParser.getValue(node, "__HBASEREIGONSERVER_PRINCIPAL__");

    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(namenodePrincipal);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(hbaseReigonServerPrincipal);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(hbaseMasterPrincipal);
    stringBuffer.append(TEXT_178);
    
					if(isKerberosAvailableHadoop1 || (isCustom && !useYarn)) {
						String jobTrackerPrincipal = ElementParameterParser.getValue(node, "__JOBTRACKER_PRINCIPAL__");

    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(jobTrackerPrincipal);
    stringBuffer.append(TEXT_181);
    
					}
					if(isKerberosAvailableHadoop2 || (isCustom && useYarn)) {
						String resourceManagerPrincipal = ElementParameterParser.getValue(node, "__RESOURCEMANAGER_PRINCIPAL__");
						String jobHistoryPrincipal = ElementParameterParser.getValue(node, "__JOBHISTORY_PRINCIPAL__");

    stringBuffer.append(TEXT_182);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_183);
    stringBuffer.append(resourceManagerPrincipal);
    stringBuffer.append(TEXT_184);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(jobHistoryPrincipal);
    stringBuffer.append(TEXT_186);
    
					}
					if ((isCustom || pigDistrib.doSupportMapRTicket()) && useMapRTicket) {
                        
    stringBuffer.append(TEXT_187);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_188);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_189);
    
                    }
					if(useKeytab) {

    stringBuffer.append(TEXT_190);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_191);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_192);
    
					}
					if ((isCustom || pigDistrib.doSupportMapRTicket()) && useMapRTicket) {
                        
    stringBuffer.append(TEXT_193);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_194);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_195);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_196);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_197);
    
                    }
				} else {
                    // Mapr ticket
				    if ((isCustom || pigDistrib.doSupportMapRTicket()) && useMapRTicket) {
                        passwordFieldName = "__MAPRTICKET_PASSWORD__";
                        
    stringBuffer.append(TEXT_198);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_200);
    
                        if (setMapRHadoopLogin) {
                            
    stringBuffer.append(TEXT_201);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_202);
    
                        } else {
                            
    stringBuffer.append(TEXT_203);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_204);
    
                        }
                        
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_205);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_207);
    } else {
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_210);
    }
    
                        if(pigDistrib.doSupportMaprTicketV52API()){
							
    stringBuffer.append(TEXT_211);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_212);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_213);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_215);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_216);
    
                        } else {
                        	
    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_219);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_222);
    
                        }
                    }
				}

			} // END IF05

			// set Memory regardless of configuration option (classpath of component)
		    if(!isCustom && ("HDP_1_2".equals(pigVersion) || "HDP_1_3".equals(pigVersion))) {
		        String mapMemory = ElementParameterParser.getValue(node,"__MAPRED_JOB_MAP_MEMORY_MB__");
		        String reduceMemory = ElementParameterParser.getValue(node,"__MAPRED_JOB_REDUCE_MEMORY_MB__");

    stringBuffer.append(TEXT_223);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_224);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_225);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_226);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_227);
    
    		}

    		// set adv hadoop properties regardless of configuration option (classpath of component)
			if(hadoopProps.size() > 0){
				for(Map<String, String> item : hadoopProps){

    stringBuffer.append(TEXT_228);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_230);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_231);
    
				}
			}

			// set Memory regardless of configuration option (classpath of component)
			boolean setMemory = "true".equals(ElementParameterParser.getValue(node, "__SET_MEMORY__"));
			if(setMemory && (isCustom && useYarn) || (!isCustom && isHadoop2)) {
				String mapMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_MAP_MEMORY_MB__");
				String reduceMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_REDUCE_MEMORY_MB__");
				String amMemory = ElementParameterParser.getValue(node,"__YARN_APP_MAPREDUCE_AM_RESOURCE_MB__");

    stringBuffer.append(TEXT_232);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_233);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_234);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_235);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(amMemory);
    stringBuffer.append(TEXT_238);
    
			}

			// set useDatanodeHostname regardless of configuration option (classpath of component)
            if (useDatanodeHostname) {
                
    stringBuffer.append(TEXT_239);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_240);
    
            }

            // create PigContext with/out tez regardless of configuration option (classpath of component)
            if(useTez){

    stringBuffer.append(TEXT_241);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_242);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_243);
    
            }else{

    stringBuffer.append(TEXT_244);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_245);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_246);
    
			}
		} // END ELSE03

        if (isS3Location) {
            passwordFieldName = "__S3_PASSWORD_LOAD__";
            // Get the decrypted password under the variable decryptedS3Password

            if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
                
    stringBuffer.append(TEXT_247);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_248);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_249);
    
            } else {
                
    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_252);
    
            }
        }

		if(inMain) {//only main process create the server object,lookup process use the one in main process
			if(!isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_253);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_254);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_255);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_256);
    
			}
		} else {

    stringBuffer.append(TEXT_257);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_258);
    
		}

    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_260);
    
	if((advProps.size() > 0) || compress || (isLocal && "HBaseStorage".equals(function)) || useTez){

    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_262);
    
		for(Map<String, String> item : advProps){

    stringBuffer.append(TEXT_263);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_264);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_265);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_266);
    
		}

		if(compress) {

    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_268);
    
			if("GZIP".equals(compression)) {

    stringBuffer.append(TEXT_269);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_270);
    
			} else if("BZIP2".equals(compression)) {

    stringBuffer.append(TEXT_271);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_272);
    
			}
		}

		if(isLocal && "HBaseStorage".equals(function)) {

    stringBuffer.append(TEXT_273);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_274);
    stringBuffer.append(zookeeper_quorum);
    stringBuffer.append(TEXT_275);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_276);
    stringBuffer.append(zookeeper_client_port);
    stringBuffer.append(TEXT_277);
    
			if(setZNodeParent) {

    stringBuffer.append(TEXT_278);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_279);
    stringBuffer.append(zNodeParent);
    stringBuffer.append(TEXT_280);
    
			}
		}

		if(useTez){
			if(advProps != null){
    			for(Map<String, String> item : advProps){
    				if("\"tez.lib.uris\"".equals(item.get("PROPERTY"))){
    				
    stringBuffer.append(TEXT_281);
    
    				}
    			}
    		}
			boolean installTez = "INSTALL".equals(ElementParameterParser.getValue(node, "__TEZ_LIB__"));
			if(installTez){
				//prepare the folder
				
    stringBuffer.append(TEXT_282);
    stringBuffer.append(TEXT_283);
    
class GetFileSystem{
	public void invoke(INode node){
        String cid = node.getUniqueName();
        
        String fsDefaultName = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
        List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");
        String user = null;
        
        String username = ElementParameterParser.getValue(node, "__USERNAME__");
        
    stringBuffer.append(TEXT_284);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_285);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_286);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_287);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_288);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_289);
    
        String distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
        String hadoopVersion = ElementParameterParser.getValue(node, "__PIG_VERSION__");
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
            return;
        }

        boolean isCustom = hdfsDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
        
        
    stringBuffer.append(TEXT_290);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_291);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_293);
    
        if(mrUseDatanodeHostname && hdfsDistrib.doSupportUseDatanodeHostname()){
        
    stringBuffer.append(TEXT_294);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_295);
    
        }
        if(hadoopProps!=null && hadoopProps.size() > 0){
        	for(Map<String, String> item : hadoopProps){
        	
    stringBuffer.append(TEXT_296);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_297);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_298);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_299);
     
    		}
    	}
        	
    	if((hdfsDistrib.doSupportKerberos() && useKrb && !isCustom) || (isCustom && useKrb)){
    	
    stringBuffer.append(TEXT_300);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_301);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_302);
    
    		if(useKeytab){
    		
    stringBuffer.append(TEXT_303);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_304);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_305);
    
    		}
    	}
    	
    	if(hdfsDistrib.doSupportGroup()) {
    		String group = ElementParameterParser.getValue(node, "__GROUP__");
    		
    stringBuffer.append(TEXT_306);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_307);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_308);
    stringBuffer.append(group);
    stringBuffer.append(TEXT_309);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_310);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_311);
    
        }else{
        
    stringBuffer.append(TEXT_312);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_313);
    stringBuffer.append(cid);
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
    
        }
    }
}

    stringBuffer.append(TEXT_321);
    
				(new GetFileSystem()).invoke(node);
				String tezLibFolder = ElementParameterParser.getValue(node, "__TEZ_LIB_FOLDER__");
				boolean useDefaultTezLibFolder = "\"/tmp/{USERNAME}/talend_tez_libs/{custom|PIG_VERSION}\"".equals(tezLibFolder);
				if(useDefaultTezLibFolder){
					String username = ElementParameterParser.getValue(node, "__USERNAME__");
					
    stringBuffer.append(TEXT_322);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_324);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_325);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_326);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_327);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_328);
    stringBuffer.append(isCustom?"custom":pigVersion);
    stringBuffer.append(TEXT_329);
    
				}else{
				
    stringBuffer.append(TEXT_330);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_331);
    stringBuffer.append(tezLibFolder);
    stringBuffer.append(TEXT_332);
    
				}
				
    stringBuffer.append(TEXT_333);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_334);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_335);
    
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
				
    stringBuffer.append(TEXT_336);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_337);
    if(!isLocal && !isExecutedThroughWebHCat && inMain){
    stringBuffer.append(TEXT_338);
    }else{
    stringBuffer.append(TEXT_339);
    }
    stringBuffer.append(TEXT_340);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_341);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_342);
    
					for(String jarName : tezLibJarsName){
					
    stringBuffer.append(TEXT_343);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_344);
    stringBuffer.append(jarName);
    stringBuffer.append(TEXT_345);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_346);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_347);
    stringBuffer.append(jarName);
    stringBuffer.append(TEXT_348);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_349);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_350);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_351);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_352);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_353);
    
					}
					
    stringBuffer.append(TEXT_354);
    
			}else{
			
    stringBuffer.append(TEXT_355);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_356);
    stringBuffer.append(ElementParameterParser.getValue(node, "__TEZ_LIB_PATH__"));
    stringBuffer.append(TEXT_357);
    
			}
    		//define the location of tez lib
			
    stringBuffer.append(TEXT_358);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_359);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_360);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_361);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_362);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_363);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_364);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_365);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_366);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_367);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_368);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_369);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_370);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_371);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_372);
    
		}
		
    stringBuffer.append(TEXT_373);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_374);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_375);
    
	}

    stringBuffer.append(TEXT_376);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_377);
    
		if(generateRegisterJarCodeForAll) {
			for(int i=0; i<jarsToRegister.size(); i++) {
				String jarToRegister = jarsToRegister.get(i);
				for(int j=0; j<jars.size(); j++) {
					if(jars.get(j).contains(jarToRegister)) {

    stringBuffer.append(TEXT_378);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_379);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_380);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_381);
    
					}
				}
			}
		}

		if(defineFunctions.size() > 0){
			for(Map<String, String> item : defineFunctions){

    stringBuffer.append(TEXT_382);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_383);
    stringBuffer.append(item.get("FUNCTION_ALIAS") );
    stringBuffer.append(TEXT_384);
    stringBuffer.append(item.get("UDF_FUNCTION") );
    stringBuffer.append(TEXT_385);
    
			}
		}

		if(!isLocal && "HCatLoader".equals(function) && pigDistrib != null && pigDistrib.doSupportHCatalog()) { // If the HCatalog loader is used, we don't need a filename anymore, but a database name and a table name.
			if(!generateRegisterJarCodeForHCatalog) {
				if(defineJarsToRegister && registerJarForHCatalog.size() > 0){
					for(Map<String, String> item : registerJarForHCatalog){

    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_387);
    stringBuffer.append(item.get("JAR_PATH") );
    stringBuffer.append(TEXT_388);
    
					}
				}
			}

    stringBuffer.append(TEXT_389);
    stringBuffer.append(thriftServer);
    stringBuffer.append(TEXT_390);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_391);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_392);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_393);
    stringBuffer.append(partitionFilter.size()>0?"_TEMP":"");
    stringBuffer.append(TEXT_394);
    stringBuffer.append(databaseName);
    stringBuffer.append(TEXT_395);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_396);
    stringBuffer.append(hcatPackage);
    stringBuffer.append(TEXT_397);
    stringBuffer.append(function);
    stringBuffer.append(TEXT_398);
    
		} else if(!isLocal && "SequenceFileStorage".equals(function)) {
			List<IMetadataTable> metadatas = node.getMetadataList();
			IMetadataTable metadata = null;
			if(metadatas != null && metadatas.size() > 0) {
				metadata = metadatas.get(0);
			}

			String keyColumn = ElementParameterParser.getValue(node,"__KEYCOLUMN__");
			String valueColumn = ElementParameterParser.getValue(node,"__VALUECOLUMN__");

			String talendKeyClass = "";
			String talendValueClass = "";

			if(metadata!=null) {
    			List<IMetadataColumn> listColumns = metadata.getListColumns();

    			for (IMetadataColumn column : listColumns) {
    				if (column.getLabel().equals(keyColumn)) {
    					talendKeyClass = column.getTalendType();
    				}
    				if (column.getLabel().equals(valueColumn)) {
    					talendValueClass = column.getTalendType();
    				}
    			}
			}

			String keyConverterClass="com.twitter.elephantbird.pig.util.TextConverter";
			if (talendKeyClass.equals("id_Boolean")) keyConverterClass="com.twitter.elephantbird.pig.util.TextConverter";
			if (talendKeyClass.equals("id_Byte")) keyConverterClass="com.twitter.elephantbird.pig.util.IntWritableConverter";
			if (talendKeyClass.equals("id_byte[]")) keyConverterClass="com.twitter.elephantbird.pig.util.TextConverter";
			if (talendKeyClass.equals("id_Double")) keyConverterClass="com.talend.pig.util.DoubleWritableConverter";
			if (talendKeyClass.equals("id_Float")) keyConverterClass="com.talend.pig.util.FloatWritableConverter";
			if (talendKeyClass.equals("id_Integer")) keyConverterClass="com.twitter.elephantbird.pig.util.IntWritableConverter";
			if (talendKeyClass.equals("id_Long")) keyConverterClass="com.twitter.elephantbird.pig.util.LongWritableConverter";
			if (talendKeyClass.equals("id_Short")) keyConverterClass="com.twitter.elephantbird.pig.util.IntWritableConverter";
			if (talendKeyClass.equals("id_String")) keyConverterClass="com.twitter.elephantbird.pig.util.TextConverter";

			String valueConverterClass="com.twitter.elephantbird.pig.util.TextConverter";
			if (talendValueClass.equals("id_Boolean")) valueConverterClass="com.twitter.elephantbird.pig.util.TextConverter";
			if (talendValueClass.equals("id_Byte")) valueConverterClass="com.twitter.elephantbird.pig.util.IntWritableConverter";
			if (talendValueClass.equals("id_byte[]")) valueConverterClass="com.twitter.elephantbird.pig.util.TextConverter";
			if (talendValueClass.equals("id_Double")) valueConverterClass="com.talend.pig.util.DoubleWritableConverter";
			if (talendValueClass.equals("id_Float")) valueConverterClass="com.talend.pig.util.FloatWritableConverter";
			if (talendValueClass.equals("id_Integer")) valueConverterClass="com.twitter.elephantbird.pig.util.IntWritableConverter";
			if (talendValueClass.equals("id_Long")) valueConverterClass="com.twitter.elephantbird.pig.util.LongWritableConverter";
			if (talendValueClass.equals("id_Short")) valueConverterClass="com.twitter.elephantbird.pig.util.IntWritableConverter";
			if (talendValueClass.equals("id_String")) valueConverterClass="com.twitter.elephantbird.pig.util.TextConverter";

			if (isS3Location) {
			    
    stringBuffer.append(TEXT_399);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_400);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_401);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_402);
    stringBuffer.append(s3username);
    stringBuffer.append(TEXT_403);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_404);
    stringBuffer.append(s3bucket);
    stringBuffer.append(TEXT_405);
    stringBuffer.append(keyConverterClass);
    stringBuffer.append(TEXT_406);
    stringBuffer.append(valueConverterClass);
    stringBuffer.append(TEXT_407);
    
	        } else {
	            
    stringBuffer.append(TEXT_408);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_409);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_410);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_411);
    stringBuffer.append(inputFilename);
    stringBuffer.append(TEXT_412);
    stringBuffer.append(keyConverterClass);
    stringBuffer.append(TEXT_413);
    stringBuffer.append(valueConverterClass);
    stringBuffer.append(TEXT_414);
    
	        }

			if(metadata!=null) {

    stringBuffer.append(TEXT_415);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_416);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_417);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_418);
    stringBuffer.append(Java2STLangTypesHelper.getPigType(metadata, keyColumn));
    stringBuffer.append(TEXT_419);
    stringBuffer.append(valueColumn);
    stringBuffer.append(TEXT_420);
    stringBuffer.append(Java2STLangTypesHelper.getPigType(metadata, valueColumn));
    stringBuffer.append(TEXT_421);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_422);
    
			}
		} else {
			if("CustomLoader".equals(function)) {
				String customLoader = ElementParameterParser.getValue(node, "__CUSTOM_LOADER__");
	            if (isS3Location) {
	                
    stringBuffer.append(TEXT_423);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_424);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_425);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_426);
    stringBuffer.append(s3username);
    stringBuffer.append(TEXT_427);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_428);
    stringBuffer.append(s3bucket);
    stringBuffer.append(TEXT_429);
    stringBuffer.append(customLoader);
    stringBuffer.append(TEXT_430);
    
	            } else {
	                
    stringBuffer.append(TEXT_431);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_432);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_433);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_434);
    stringBuffer.append(inputFilename);
    stringBuffer.append(TEXT_435);
    stringBuffer.append(customLoader);
    stringBuffer.append(TEXT_436);
    
	            }
			} else {
				List<IMetadataTable> metadatas = node.getMetadataList();
				IMetadataTable metadata = null;
				if(metadatas != null && metadatas.size() > 0) {
					metadata = metadatas.get(0);
				}

				if("HBaseStorage".equals(function)){
					String hbasetable = ElementParameterParser.getValue(node, "__HBASE_TABLE__");
					boolean loadkey = "true".equals(ElementParameterParser.getValue(node, "__LOAD_KEY__"));//if load key,the first column is hbase row key
					List<Map<String, String>> mapping = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node,"__MAPPING__");
					
    stringBuffer.append(TEXT_437);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_438);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_439);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_440);
    stringBuffer.append(hbasetable);
    stringBuffer.append(TEXT_441);
    

					if(metadata!=null && mapping!=null) {
						for(int i=0;i<mapping.size();i++){
							if(loadkey && (i == 0)) {
								continue;
							}
                			Map<String, String> map = mapping.get(i);
                			String family_column= map.get("FAMILY_COLUMN");

    stringBuffer.append(TEXT_442);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_443);
    stringBuffer.append(family_column);
    stringBuffer.append(TEXT_444);
    
							if(i < mapping.size()-1) {

    stringBuffer.append(TEXT_445);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_446);
    
							}
            			}
					}

    stringBuffer.append(TEXT_447);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_448);
    
					List<Map<String, String>> hbasestorageParams = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__HBASESTORAGE_PARAMETER__");

					if(loadkey) {

    stringBuffer.append(TEXT_449);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_450);
    
					}

					if(hbasestorageParams!=null) {
						for(int i=0;i<hbasestorageParams.size();i++) {
							if(i == 0) {
								if(!loadkey) {

    stringBuffer.append(TEXT_451);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_452);
    
								} else {

    stringBuffer.append(TEXT_453);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_454);
    
								}
							}
							Map<String, String> param = hbasestorageParams.get(i);
							String name = param.get("PARAM_NAME");
							String value = param.get("PARAM_VALUE");

    stringBuffer.append(TEXT_455);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_456);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_457);
    stringBuffer.append(value);
    stringBuffer.append(TEXT_458);
    
							if(i < hbasestorageParams.size()-1) {

    stringBuffer.append(TEXT_459);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_460);
    
							} else if(!loadkey){

    stringBuffer.append(TEXT_461);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_462);
    
							}
						}
					}

					if(loadkey) {

    stringBuffer.append(TEXT_463);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_464);
    
					}

    stringBuffer.append(TEXT_465);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_466);
    
				} else if(isParquet) {
	                if (isS3Location) {
	                    
    stringBuffer.append(TEXT_467);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_468);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_469);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_470);
    stringBuffer.append(s3username);
    stringBuffer.append(TEXT_471);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_472);
    stringBuffer.append(s3bucket);
    stringBuffer.append(TEXT_473);
    
	                } else {
	                    
    stringBuffer.append(TEXT_474);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_475);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_476);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_477);
    stringBuffer.append(inputFilename);
    stringBuffer.append(TEXT_478);
    
	                }
				} else {
					if("RCFilePigStorage".equals(function)) {
						function = "com.twitter.elephantbird.pig.store.RCFilePigStorage";
					}

					if("AvroStorage".equals(function)) {
						function = "org.apache.pig.piggybank.storage.avro.AvroStorage";
					}
					if (isS3Location) {
                        
    stringBuffer.append(TEXT_479);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_480);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_481);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_482);
    stringBuffer.append(s3username);
    stringBuffer.append(TEXT_483);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_484);
    stringBuffer.append(s3bucket);
    stringBuffer.append(TEXT_485);
    stringBuffer.append(function);
    stringBuffer.append(TEXT_486);
    stringBuffer.append("PigStorage".equals(function)?"'\"+"+fieldSeparator+"+\"'":"");
    stringBuffer.append(TEXT_487);
    
                    } else {
                        
    stringBuffer.append(TEXT_488);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_489);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_490);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_491);
    stringBuffer.append(inputFilename);
    stringBuffer.append(TEXT_492);
    stringBuffer.append(function);
    stringBuffer.append(TEXT_493);
    stringBuffer.append("PigStorage".equals(function)?"'\"+"+fieldSeparator+"+\"'":"");
    stringBuffer.append(TEXT_494);
    
                    }
				}

				if(metadata!=null) {
					if(metadata.getListColumns() != null && metadata.getListColumns().size() > 0) {

    stringBuffer.append(TEXT_495);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_496);
    
						for(int i=0; i<metadata.getListColumns().size(); i++) {

    stringBuffer.append(TEXT_497);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_498);
    stringBuffer.append(i!=0?", ":"");
    stringBuffer.append(metadata.getListColumns().get(i).getLabel());
    stringBuffer.append(TEXT_499);
    stringBuffer.append(Java2STLangTypesHelper.getPigType(metadata, metadata.getListColumns().get(i).getLabel()));
    stringBuffer.append(TEXT_500);
    

						}

    stringBuffer.append(TEXT_501);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_502);
    
					}
				}
			}
		}

    stringBuffer.append(TEXT_503);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_504);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_505);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_506);
    
		if(!isLocal && "HCatLoader".equals(function) && pigDistrib != null && pigDistrib.doSupportHCatalog()) { // If the HCatalog loader is used, we will have to consider the partition filter
			if(partitionFilter.size() > 0){
				int i = 0;

    stringBuffer.append(TEXT_507);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_508);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_509);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_510);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_511);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_512);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_513);
    
				for(Map<String, String> item : partitionFilter){
					if(i++ != 0) {

    stringBuffer.append(TEXT_514);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_515);
    
					}

    stringBuffer.append(TEXT_516);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_517);
    stringBuffer.append(item.get("PART_NAME") );
    stringBuffer.append(TEXT_518);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_519);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_520);
    stringBuffer.append(item.get("PART_VALUE") );
    stringBuffer.append(TEXT_521);
    
				}

    stringBuffer.append(TEXT_522);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_523);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_524);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_525);
    
			}
		}

    stringBuffer.append(TEXT_526);
    stringBuffer.append(TEXT_527);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_528);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_529);
    
	if(inMain) {

    stringBuffer.append(TEXT_530);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_531);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_532);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_533);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_534);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_535);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_536);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_537);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_538);
    }
    stringBuffer.append(TEXT_539);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_540);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_541);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_542);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_543);
    					
				} else {

    stringBuffer.append(TEXT_544);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_545);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_546);
    
				}

    stringBuffer.append(TEXT_547);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_548);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_549);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_550);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_551);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_552);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_553);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_554);
    					
				} else {

    stringBuffer.append(TEXT_555);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_556);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_557);
    
				}

    stringBuffer.append(TEXT_558);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_559);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_560);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_561);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_562);
    					
				} else {

    stringBuffer.append(TEXT_563);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_564);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_565);
    
				}

    stringBuffer.append(TEXT_566);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_567);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_568);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_569);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_570);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_571);
    					
				} else {

    stringBuffer.append(TEXT_572);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_573);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_574);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_575);
    
				}

    stringBuffer.append(TEXT_576);
    
	} else {

    stringBuffer.append(TEXT_577);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_578);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_579);
    
	}

    stringBuffer.append(TEXT_580);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_581);
    stringBuffer.append(TEXT_582);
    return stringBuffer.toString();
  }
}
