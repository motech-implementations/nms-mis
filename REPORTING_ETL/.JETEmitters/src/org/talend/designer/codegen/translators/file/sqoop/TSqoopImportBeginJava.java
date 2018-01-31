package org.talend.designer.codegen.translators.file.sqoop;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import java.util.List;
import java.util.Map;

public class TSqoopImportBeginJava
{
  protected static String nl;
  public static synchronized TSqoopImportBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TSqoopImportBeginJava result = new TSqoopImportBeginJava();
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
  protected final String TEXT_30 = NL + "\t\t";
  protected final String TEXT_31 = NL;
  protected final String TEXT_32 = NL + NL + "class SqoopAdditionalArgumentParser_";
  protected final String TEXT_33 = " {" + NL + "" + NL + "    public String[] parse(String command) {" + NL + "        final char QUOTE = '\\\"';" + NL + "        final char SINGLEQUOTE = '\\'';" + NL + "        final char SPACE = ' ';" + NL + "        final char BACKSLASH = '\\\\';" + NL + "        java.util.List<String> list = new java.util.ArrayList<String>();" + NL + "        if (command == null) {" + NL + "            return null;" + NL + "        }" + NL + "" + NL + "        StringBuilder sb = new StringBuilder();" + NL + "        char[] chars = command.trim().toCharArray();" + NL + "        boolean backslash = true;" + NL + "        boolean withinString = false;" + NL + "        for (char c : chars) {" + NL + "            switch (c) {" + NL + "            case SPACE:" + NL + "                if (!withinString) {" + NL + "                    if (sb.toString().trim().length() > 0) {" + NL + "                        list.add(sb.toString());" + NL + "                        sb = new StringBuilder();" + NL + "                    }" + NL + "                } else {" + NL + "                    sb.append(c);" + NL + "                }" + NL + "                break;" + NL + "            case SINGLEQUOTE:" + NL + "                if (!withinString) {" + NL + "                    withinString = true;" + NL + "                } else {" + NL + "                    withinString = false;" + NL + "                }" + NL + "                break;" + NL + "            case QUOTE:" + NL + "                if (backslash && !withinString) {" + NL + "                    withinString = true;" + NL + "                }" + NL + "                if (backslash && withinString) {" + NL + "                    withinString = false;" + NL + "                }" + NL + "                break;" + NL + "            case BACKSLASH:" + NL + "                if (!backslash) {" + NL + "                    backslash = true;" + NL + "                } else {" + NL + "                    backslash = false;" + NL + "                }" + NL + "                break;" + NL + "            default:" + NL + "                sb.append(c);" + NL + "            }" + NL + "        }" + NL + "        if (sb.toString().trim().length() > 0) {" + NL + "            list.add(sb.toString());" + NL + "        }" + NL + "        return list.toArray(new String[list.size()]);" + NL + "    }" + NL + "}" + NL + "SqoopAdditionalArgumentParser_";
  protected final String TEXT_34 = " parser_";
  protected final String TEXT_35 = " = new SqoopAdditionalArgumentParser_";
  protected final String TEXT_36 = "();" + NL;
  protected final String TEXT_37 = " " + NL + "" + NL + "" + NL + "//call the cmd part" + NL + "Runtime runtime_";
  protected final String TEXT_38 = " = Runtime.getRuntime();" + NL;
  protected final String TEXT_39 = NL;
  protected final String TEXT_40 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_41 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_42 = ");";
  protected final String TEXT_43 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_44 = " = ";
  protected final String TEXT_45 = "; ";
  protected final String TEXT_46 = NL + "\t" + NL + "" + NL + "String[] cmd_";
  protected final String TEXT_47 = " = new String[] {\"sqoop\", \"import\"";
  protected final String TEXT_48 = " " + NL + "\t\t\t,\"-D";
  protected final String TEXT_49 = "=\"+";
  protected final String TEXT_50 = NL + ",";
  protected final String TEXT_51 = NL + NL + ", \"--connect\", ";
  protected final String TEXT_52 = ", \"--username\", ";
  protected final String TEXT_53 = NL + "\t, \"--password-file\", ";
  protected final String TEXT_54 = NL + "\t, \"--password\", decryptedPassword_";
  protected final String TEXT_55 = ",\"--connection-manager\",\"org.apache.sqoop.teradata.TeradataConnManager\"";
  protected final String TEXT_56 = ", \"--query\", ";
  protected final String TEXT_57 = NL + "\t, \"--table\", ";
  protected final String TEXT_58 = ", \"--columns\", \"";
  protected final String TEXT_59 = "\"";
  protected final String TEXT_60 = ", \"--where\", ";
  protected final String TEXT_61 = ", \"--verbose\"";
  protected final String TEXT_62 = ", \"--append\"";
  protected final String TEXT_63 = ", \"--delete-target-dir\"";
  protected final String TEXT_64 = NL + "\t, \"--direct\"";
  protected final String TEXT_65 = NL + "\t, \"--direct-split-size\", ";
  protected final String TEXT_66 = NL + "\t, \"--compress\"";
  protected final String TEXT_67 = NL + "\t, \"--compression-codec\", ";
  protected final String TEXT_68 = ", \"--target-dir\", ";
  protected final String TEXT_69 = ", \"--as-sequencefile\"";
  protected final String TEXT_70 = ", \"--as-avrodatafile\"";
  protected final String TEXT_71 = ", \"--as-parquetfile\"";
  protected final String TEXT_72 = ", \"--mysql-delimiters\"";
  protected final String TEXT_73 = ", \"--num-mappers\", ";
  protected final String TEXT_74 = ", \"--split-by\", ";
  protected final String TEXT_75 = " " + NL + "\t\t\t,\"";
  protected final String TEXT_76 = "\", ";
  protected final String TEXT_77 = " ";
  protected final String TEXT_78 = " " + NL + "\t\t\t,\"";
  protected final String TEXT_79 = "\" ";
  protected final String TEXT_80 = NL + "\t\t,\"--map-column-java\"";
  protected final String TEXT_81 = NL + "\t\t\t\t,";
  protected final String TEXT_82 = " + \"=\" + ";
  protected final String TEXT_83 = NL + "\t\t\t\t+\",\" + ";
  protected final String TEXT_84 = " + \"=\" + ";
  protected final String TEXT_85 = NL + "\t\t,\"--map-column-hive\"";
  protected final String TEXT_86 = NL + "\t\t\t\t,";
  protected final String TEXT_87 = " + \"=\" + ";
  protected final String TEXT_88 = NL + "\t\t\t\t+\",\" + ";
  protected final String TEXT_89 = " + \"=\" + ";
  protected final String TEXT_90 = NL + "};" + NL + "" + NL + "String[] additionalArgs_";
  protected final String TEXT_91 = " = parser_";
  protected final String TEXT_92 = ".parse(";
  protected final String TEXT_93 = ");" + NL + "cmd_";
  protected final String TEXT_94 = " = (String[]) org.apache.commons.lang.ArrayUtils.addAll(cmd_";
  protected final String TEXT_95 = ", additionalArgs_";
  protected final String TEXT_96 = ");" + NL;
  protected final String TEXT_97 = NL + "java.lang.StringBuilder stringBuilder_";
  protected final String TEXT_98 = " = new java.lang.StringBuilder();" + NL + "" + NL + "for(String parameter_";
  protected final String TEXT_99 = " : cmd_";
  protected final String TEXT_100 = ") {" + NL + "\tstringBuilder_";
  protected final String TEXT_101 = ".append(parameter_";
  protected final String TEXT_102 = ");" + NL + "\tstringBuilder_";
  protected final String TEXT_103 = ".append(\" \");" + NL + "}" + NL + "log.info(\"";
  protected final String TEXT_104 = " - execute sqoop command: \" + stringBuilder_";
  protected final String TEXT_105 = ".toString());";
  protected final String TEXT_106 = NL + NL + "Process ps_";
  protected final String TEXT_107 = " = null;" + NL + "java.lang.StringBuilder sb_";
  protected final String TEXT_108 = " = null;" + NL + "" + NL + "try {" + NL + "\tps_";
  protected final String TEXT_109 = " = runtime_";
  protected final String TEXT_110 = ".exec(cmd_";
  protected final String TEXT_111 = ");" + NL + "\tbyte[] byteArray_";
  protected final String TEXT_112 = " = new byte[1024];" + NL + "\tint len_";
  protected final String TEXT_113 = " = 0;" + NL + "" + NL + "\tjava.io.InputStream errorStream_";
  protected final String TEXT_114 = " = new java.io.BufferedInputStream(ps_";
  protected final String TEXT_115 = ".getErrorStream());" + NL + "\tsb_";
  protected final String TEXT_116 = " = new java.lang.StringBuilder();" + NL + "\twhile ((len_";
  protected final String TEXT_117 = " = errorStream_";
  protected final String TEXT_118 = ".read(byteArray_";
  protected final String TEXT_119 = ")) != -1) {" + NL + "\t\tString line_";
  protected final String TEXT_120 = " = new String(byteArray_";
  protected final String TEXT_121 = ", 0, len_";
  protected final String TEXT_122 = ", \"ISO-8859-1\");";
  protected final String TEXT_123 = NL + "\t    \tSystem.err.println(line_";
  protected final String TEXT_124 = ");";
  protected final String TEXT_125 = NL + "\t\tsb_";
  protected final String TEXT_126 = ".append(line_";
  protected final String TEXT_127 = ");" + NL + "\t}" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_128 = "_ERROR_MESSAGE\", sb_";
  protected final String TEXT_129 = ".toString());" + NL + "" + NL + "\t";
  protected final String TEXT_130 = NL + "\t\tlog.error(\"";
  protected final String TEXT_131 = " - \" + sb_";
  protected final String TEXT_132 = ".toString());" + NL + "\t";
  protected final String TEXT_133 = NL + NL + "\tbyteArray_";
  protected final String TEXT_134 = " = new byte[1024];" + NL + "\tlen_";
  protected final String TEXT_135 = " = 0;" + NL + "\tjava.io.InputStream inputStream_";
  protected final String TEXT_136 = " = new java.io.BufferedInputStream(ps_";
  protected final String TEXT_137 = ".getInputStream());" + NL + "\tsb_";
  protected final String TEXT_138 = " = new java.lang.StringBuilder();" + NL + "\twhile ((len_";
  protected final String TEXT_139 = " = inputStream_";
  protected final String TEXT_140 = ".read(byteArray_";
  protected final String TEXT_141 = ")) != -1) {" + NL + "\t\tString line_";
  protected final String TEXT_142 = " = new String(byteArray_";
  protected final String TEXT_143 = ", 0, len_";
  protected final String TEXT_144 = ", \"ISO-8859-1\");";
  protected final String TEXT_145 = NL + "\t    \tSystem.out.println(line_";
  protected final String TEXT_146 = ");";
  protected final String TEXT_147 = NL + "\t\tsb_";
  protected final String TEXT_148 = ".append(line_";
  protected final String TEXT_149 = ");" + NL + "\t}" + NL + "} catch (Exception e) {";
  protected final String TEXT_150 = NL + "\t\tthrow e;";
  protected final String TEXT_151 = NL + "\t\tSystem.err.println(e.getMessage());" + NL + "\t\t";
  protected final String TEXT_152 = NL + "\t\tlog.error(\"";
  protected final String TEXT_153 = " - \" + e.getMessage());" + NL + "\t\t";
  protected final String TEXT_154 = "\t" + NL + "}" + NL + "" + NL + "\tint result_";
  protected final String TEXT_155 = " = ps_";
  protected final String TEXT_156 = ".waitFor();" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_157 = "_EXIT_CODE\", result_";
  protected final String TEXT_158 = ");" + NL;
  protected final String TEXT_159 = NL + "\t\tif(ps_";
  protected final String TEXT_160 = ".exitValue()>0) {" + NL + "\t\t\tthrow new Exception(\"The Sqoop import has failed. Please check the logs.\");" + NL + "\t\t}";
  protected final String TEXT_161 = NL + NL + "if(sb_";
  protected final String TEXT_162 = "!=null) {" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_163 = "_OUTPUT_MESSAGE\", sb_";
  protected final String TEXT_164 = ".toString());" + NL + "\t";
  protected final String TEXT_165 = NL + "\t\tlog.info(\"";
  protected final String TEXT_166 = " - \" + sb_";
  protected final String TEXT_167 = ".toString());" + NL + "\t";
  protected final String TEXT_168 = NL + "}";
  protected final String TEXT_169 = NL + "\t\t";
  protected final String TEXT_170 = NL + "\t";
  protected final String TEXT_171 = NL + "\tglobalMap.put(\"current_client_path_separator\", System.getProperty(\"path.separator\"));" + NL + "\tSystem.setProperty(\"path.separator\", ";
  protected final String TEXT_172 = ");" + NL + "" + NL + "\tglobalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_173 = "\", System.getProperty(\"HADOOP_USER_NAME\"));";
  protected final String TEXT_174 = NL + "\t\tString username_";
  protected final String TEXT_175 = " = ";
  protected final String TEXT_176 = ";" + NL + "\t\tif(username_";
  protected final String TEXT_177 = "!=null && !\"\".equals(username_";
  protected final String TEXT_178 = ".trim())) {" + NL + "\t\t\tSystem.setProperty(\"HADOOP_USER_NAME\",username_";
  protected final String TEXT_179 = ");" + NL + "\t\t}";
  protected final String TEXT_180 = NL + NL + "\torg.apache.hadoop.conf.Configuration configuration_";
  protected final String TEXT_181 = " = new org.apache.hadoop.conf.Configuration();" + NL + "\tconfiguration_";
  protected final String TEXT_182 = ".set(\"";
  protected final String TEXT_183 = "\", ";
  protected final String TEXT_184 = ");";
  protected final String TEXT_185 = NL + "\t\tconfiguration_";
  protected final String TEXT_186 = ".set(\"mapreduce.job.map.output.collector.class\", \"org.apache.hadoop.mapred.MapRFsOutputBuffer\");" + NL + "\t    configuration_";
  protected final String TEXT_187 = ".set(\"mapreduce.job.reduce.shuffle.consumer.plugin.class\", \"org.apache.hadoop.mapreduce.task.reduce.DirectShuffle\");";
  protected final String TEXT_188 = NL + "    \tconfiguration_";
  protected final String TEXT_189 = ".set(\"mapreduce.framework.name\", \"yarn\");" + NL + "    \tconfiguration_";
  protected final String TEXT_190 = ".set(\"yarn.resourcemanager.address\", ";
  protected final String TEXT_191 = ");";
  protected final String TEXT_192 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_193 = ".set(\"mapreduce.jobhistory.address\", ";
  protected final String TEXT_194 = ");" + NL + "\t\t\t";
  protected final String TEXT_195 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_196 = ".set(\"yarn.resourcemanager.scheduler.address\", ";
  protected final String TEXT_197 = ");";
  protected final String TEXT_198 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_199 = ".set(\"yarn.app.mapreduce.am.staging-dir\", ";
  protected final String TEXT_200 = ");";
  protected final String TEXT_201 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_202 = ".set(\"mapreduce.app-submission.cross-platform\",\"true\");";
  protected final String TEXT_203 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_204 = ".set(\"mapreduce.application.classpath\", \"";
  protected final String TEXT_205 = "\");";
  protected final String TEXT_206 = NL + "\t\tconfiguration_";
  protected final String TEXT_207 = ".set(\"yarn.application.classpath\", \"";
  protected final String TEXT_208 = "\");";
  protected final String TEXT_209 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_210 = ".set(\"mapreduce.map.memory.mb\", ";
  protected final String TEXT_211 = ");" + NL + "\t\t\tconfiguration_";
  protected final String TEXT_212 = ".set(\"mapreduce.reduce.memory.mb\", ";
  protected final String TEXT_213 = ");" + NL + "\t\t\tconfiguration_";
  protected final String TEXT_214 = ".set(\"yarn.app.mapreduce.am.resource.mb\", ";
  protected final String TEXT_215 = ");";
  protected final String TEXT_216 = NL + "\t\tconfiguration_";
  protected final String TEXT_217 = ".set(\"mapred.job.tracker\", ";
  protected final String TEXT_218 = ");";
  protected final String TEXT_219 = NL + "        configuration_";
  protected final String TEXT_220 = ".set(\"mapred.job.map.memory.mb\", ";
  protected final String TEXT_221 = ");" + NL + "        configuration_";
  protected final String TEXT_222 = ".set(\"mapred.job.reduce.memory.mb\", ";
  protected final String TEXT_223 = ");";
  protected final String TEXT_224 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_225 = ".set(";
  protected final String TEXT_226 = " ,";
  protected final String TEXT_227 = ");";
  protected final String TEXT_228 = NL + "\t\tconfiguration_";
  protected final String TEXT_229 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_230 = ");";
  protected final String TEXT_231 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_232 = ".set(\"mapreduce.jobtracker.kerberos.principal\", ";
  protected final String TEXT_233 = ");";
  protected final String TEXT_234 = NL + "\t\t\tconfiguration_";
  protected final String TEXT_235 = ".set(\"yarn.resourcemanager.principal\", ";
  protected final String TEXT_236 = ");" + NL + "\t\t\tconfiguration_";
  protected final String TEXT_237 = ".set(\"mapreduce.jobhistory.principal\", ";
  protected final String TEXT_238 = ");";
  protected final String TEXT_239 = NL + "            System.setProperty(\"pname\", \"MapRLogin\");" + NL + "            System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "            System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_240 = ");" + NL + "            System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_241 = ");";
  protected final String TEXT_242 = NL + "\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_243 = ", ";
  protected final String TEXT_244 = ");";
  protected final String TEXT_245 = NL + "            com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_246 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "            maprLogin_";
  protected final String TEXT_247 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_248 = ", ";
  protected final String TEXT_249 = ");";
  protected final String TEXT_250 = NL + "            System.setProperty(\"pname\", \"MapRLogin\");" + NL + "            System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "            System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_251 = ");" + NL + "            com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_252 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_253 = NL + "                System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_254 = ");";
  protected final String TEXT_255 = NL + "                maprLogin_";
  protected final String TEXT_256 = ".setCheckUGI(false);";
  protected final String TEXT_257 = " " + NL + "                final String decryptedMapRPassword_";
  protected final String TEXT_258 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_259 = ");";
  protected final String TEXT_260 = NL + "                final String decryptedMapRPassword_";
  protected final String TEXT_261 = " = ";
  protected final String TEXT_262 = "; ";
  protected final String TEXT_263 = NL;
  protected final String TEXT_264 = NL + "            \tmaprLogin_";
  protected final String TEXT_265 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_266 = ", ";
  protected final String TEXT_267 = ", decryptedMapRPassword_";
  protected final String TEXT_268 = ", ";
  protected final String TEXT_269 = ", \"\");" + NL + "            \t";
  protected final String TEXT_270 = NL + "            \tmaprLogin_";
  protected final String TEXT_271 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_272 = ", ";
  protected final String TEXT_273 = ", decryptedMapRPassword_";
  protected final String TEXT_274 = ", ";
  protected final String TEXT_275 = ");" + NL + "            \t";
  protected final String TEXT_276 = NL + "        configuration_";
  protected final String TEXT_277 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_278 = NL + "\t//check whether we can connect to the fs?" + NL + "\torg.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_279 = " = org.apache.hadoop.fs.FileSystem.get(configuration_";
  protected final String TEXT_280 = ");" + NL + "\tjava.util.List<String> lColumns_";
  protected final String TEXT_281 = " = new java.util.ArrayList<String>();";
  protected final String TEXT_282 = NL + "\t\tlColumns_";
  protected final String TEXT_283 = ".add(";
  protected final String TEXT_284 = ");";
  protected final String TEXT_285 = NL + NL + "\tcom.cloudera.sqoop.tool.SqoopTool sqoopTool_";
  protected final String TEXT_286 = " = com.cloudera.sqoop.tool.SqoopTool.getTool(\"import\");" + NL + "" + NL + "\tcom.cloudera.sqoop.SqoopOptions sqoopOptions_";
  protected final String TEXT_287 = " = new com.cloudera.sqoop.SqoopOptions(configuration_";
  protected final String TEXT_288 = ");" + NL + "\tsqoopOptions_";
  protected final String TEXT_289 = ".setConnectString(";
  protected final String TEXT_290 = "); // __CONNECTION__" + NL + "\tsqoopOptions_";
  protected final String TEXT_291 = ".setUsername(";
  protected final String TEXT_292 = "); // __USERNAME__" + NL + "\t";
  protected final String TEXT_293 = NL + "\t\t";
  protected final String TEXT_294 = NL + "\t\t";
  protected final String TEXT_295 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_296 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_297 = ");";
  protected final String TEXT_298 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_299 = " = ";
  protected final String TEXT_300 = "; ";
  protected final String TEXT_301 = NL + "\t\tsqoopOptions_";
  protected final String TEXT_302 = ".setPassword(decryptedPassword_";
  protected final String TEXT_303 = "); // __PASSWORD__" + NL + "\t";
  protected final String TEXT_304 = NL + "\t\tsqoopOptions_";
  protected final String TEXT_305 = ".setPasswordFilePath(";
  protected final String TEXT_306 = ");" + NL + "\t\tsqoopOptions_";
  protected final String TEXT_307 = ".setPassword(org.apache.sqoop.util.CredentialsUtil.fetchPassword";
  protected final String TEXT_308 = "(sqoopOptions_";
  protected final String TEXT_309 = "));" + NL + "\t";
  protected final String TEXT_310 = NL + "\t" + NL + "\t";
  protected final String TEXT_311 = NL + "\t\tsqoopOptions_";
  protected final String TEXT_312 = ".setSqlQuery(";
  protected final String TEXT_313 = ");" + NL + "\t";
  protected final String TEXT_314 = NL + "\t\tsqoopOptions_";
  protected final String TEXT_315 = ".setTableName(";
  protected final String TEXT_316 = "); // __TABLE__" + NL + "\t\t";
  protected final String TEXT_317 = "sqoopOptions_";
  protected final String TEXT_318 = ".setColumns(lColumns_";
  protected final String TEXT_319 = ".toArray(new String[lColumns_";
  protected final String TEXT_320 = ".size()]));";
  protected final String TEXT_321 = NL + "\t\t";
  protected final String TEXT_322 = "sqoopOptions_";
  protected final String TEXT_323 = ".setWhereClause(";
  protected final String TEXT_324 = "); // __WHERE__";
  protected final String TEXT_325 = NL + "\t";
  protected final String TEXT_326 = NL + "\t";
  protected final String TEXT_327 = "sqoopOptions_";
  protected final String TEXT_328 = ".setAppendMode(true);";
  protected final String TEXT_329 = NL + "\t";
  protected final String TEXT_330 = NL + "\t\tsqoopOptions_";
  protected final String TEXT_331 = ".setDirectMode(true);" + NL + "\t\t";
  protected final String TEXT_332 = NL + "\t\t\tsqoopOptions_";
  protected final String TEXT_333 = ".setDirectSplitSize(Long.parseLong(";
  protected final String TEXT_334 = "));" + NL + "\t\t";
  protected final String TEXT_335 = NL + "\t";
  protected final String TEXT_336 = NL + "\t\tsqoopOptions_";
  protected final String TEXT_337 = ".setUseCompression(true);" + NL + "\t\t";
  protected final String TEXT_338 = NL + "\t\t\tsqoopOptions_";
  protected final String TEXT_339 = ".setCompressionCodec(";
  protected final String TEXT_340 = ");" + NL + "\t\t";
  protected final String TEXT_341 = NL + "\t";
  protected final String TEXT_342 = "sqoopOptions_";
  protected final String TEXT_343 = ".setTargetDir(";
  protected final String TEXT_344 = ");";
  protected final String TEXT_345 = NL + "\t";
  protected final String TEXT_346 = "sqoopOptions_";
  protected final String TEXT_347 = ".setFileLayout(com.cloudera.sqoop.SqoopOptions.FileLayout.SequenceFile);";
  protected final String TEXT_348 = "sqoopOptions_";
  protected final String TEXT_349 = ".setFileLayout(com.cloudera.sqoop.SqoopOptions.FileLayout.TextFile);";
  protected final String TEXT_350 = "sqoopOptions_";
  protected final String TEXT_351 = ".setFileLayout(com.cloudera.sqoop.SqoopOptions.FileLayout.ParquetFile);";
  protected final String TEXT_352 = "sqoopOptions_";
  protected final String TEXT_353 = ".setFileLayout(com.cloudera.sqoop.SqoopOptions.FileLayout.AvroDataFile);";
  protected final String TEXT_354 = NL + "\t";
  protected final String TEXT_355 = "sqoopOptions_";
  protected final String TEXT_356 = ".setNumMappers(Integer.valueOf(";
  protected final String TEXT_357 = "));";
  protected final String TEXT_358 = NL + "\t";
  protected final String TEXT_359 = "sqoopOptions_";
  protected final String TEXT_360 = ".setSplitByCol(";
  protected final String TEXT_361 = ");";
  protected final String TEXT_362 = NL + "\t";
  protected final String TEXT_363 = NL + "\t\t";
  protected final String TEXT_364 = NL + "\t\t\tsqoopOptions_";
  protected final String TEXT_365 = ".setDeleteMode(true);" + NL + "\t\t";
  protected final String TEXT_366 = NL + "\t";
  protected final String TEXT_367 = NL + "\t" + NL + "\t";
  protected final String TEXT_368 = NL + "\t\t\tStringBuilder sb_java_";
  protected final String TEXT_369 = " = new StringBuilder();" + NL + "\t";
  protected final String TEXT_370 = NL + "\t\t\t\t\tsb_java_";
  protected final String TEXT_371 = ".append(";
  protected final String TEXT_372 = " + \"=\" + ";
  protected final String TEXT_373 = ");" + NL + "\t";
  protected final String TEXT_374 = NL + "\t\t\t\t\tsb_java_";
  protected final String TEXT_375 = ".append(\",\" + ";
  protected final String TEXT_376 = " + \"=\" + ";
  protected final String TEXT_377 = ");" + NL + "\t";
  protected final String TEXT_378 = NL + "\t\t\tsqoopOptions_";
  protected final String TEXT_379 = ".setMapColumnJava(sb_java_";
  protected final String TEXT_380 = ".toString());" + NL + "\t";
  protected final String TEXT_381 = NL + "\t\t\tStringBuilder sb_hive_";
  protected final String TEXT_382 = " = new StringBuilder();" + NL + "\t";
  protected final String TEXT_383 = NL + "\t\t\t\t\tsb_hive_";
  protected final String TEXT_384 = ".append(";
  protected final String TEXT_385 = " + \"=\" + ";
  protected final String TEXT_386 = ");" + NL + "\t";
  protected final String TEXT_387 = NL + "\t\t\t\t\tsb_hive_";
  protected final String TEXT_388 = ".append(\",\" + ";
  protected final String TEXT_389 = " + \"=\" + ";
  protected final String TEXT_390 = ");" + NL + "\t";
  protected final String TEXT_391 = NL + "\t\t\tsqoopOptions_";
  protected final String TEXT_392 = ".setMapColumnHive(sb_hive_";
  protected final String TEXT_393 = ".toString());" + NL + "\t";
  protected final String TEXT_394 = NL + "\t" + NL + "\tjava.util.Properties additionalProperties_";
  protected final String TEXT_395 = " = new java.util.Properties();" + NL + "\t";
  protected final String TEXT_396 = NL + "\t\tadditionalProperties_";
  protected final String TEXT_397 = ".put(\"customtool.options.jsonmap\", \"{\\\"\\\":\\\"\\\"}\");" + NL + "\t";
  protected final String TEXT_398 = " " + NL + "\t\t\t\tadditionalProperties_";
  protected final String TEXT_399 = ".put(";
  protected final String TEXT_400 = ", \"\"+";
  protected final String TEXT_401 = ".codePointAt(0)); ";
  protected final String TEXT_402 = " " + NL + "\t\t\t\tadditionalProperties_";
  protected final String TEXT_403 = ".put(";
  protected final String TEXT_404 = ", ";
  protected final String TEXT_405 = "); ";
  protected final String TEXT_406 = NL + "\tsqoopOptions_";
  protected final String TEXT_407 = ".loadProperties(additionalProperties_";
  protected final String TEXT_408 = ");" + NL + "\torg.apache.sqoop.Sqoop sqoop_";
  protected final String TEXT_409 = " = new org.apache.sqoop.Sqoop(sqoopTool_";
  protected final String TEXT_410 = ", configuration_";
  protected final String TEXT_411 = ", sqoopOptions_";
  protected final String TEXT_412 = ");" + NL + "\t";
  protected final String TEXT_413 = NL + "\t\troutines.system.GetJarsToRegister getJarsToRegister_";
  protected final String TEXT_414 = " = new routines.system.GetJarsToRegister();" + NL + "\t\tjava.lang.StringBuilder sb_";
  protected final String TEXT_415 = " = new java.lang.StringBuilder();";
  protected final String TEXT_416 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_417 = ".append(getJarsToRegister_";
  protected final String TEXT_418 = ".replaceJarPaths(\"";
  protected final String TEXT_419 = "\", \"file:///\") + \",\");";
  protected final String TEXT_420 = NL + "\t";
  protected final String TEXT_421 = NL + "\t\troutines.system.GetJarsToRegister getJarsToRegister_";
  protected final String TEXT_422 = " = new routines.system.GetJarsToRegister();" + NL + "\t\tjava.lang.StringBuilder sb_";
  protected final String TEXT_423 = " = new java.lang.StringBuilder();";
  protected final String TEXT_424 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_425 = ".append(getJarsToRegister_";
  protected final String TEXT_426 = ".replaceJarPaths(\"";
  protected final String TEXT_427 = "\", \"file:///\") + \",\");";
  protected final String TEXT_428 = NL + "\ttry {";
  protected final String TEXT_429 = NL + "\t\t\tint result_";
  protected final String TEXT_430 = " = org.apache.sqoop.Sqoop.runSqoop(sqoop_";
  protected final String TEXT_431 = ", new String[] {\"-libjars\", sb_";
  protected final String TEXT_432 = ".toString()});";
  protected final String TEXT_433 = NL + "\t\t\tint result_";
  protected final String TEXT_434 = " = org.apache.sqoop.Sqoop.runSqoop(sqoop_";
  protected final String TEXT_435 = ", new String[] {});";
  protected final String TEXT_436 = NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_437 = "_EXIT_CODE\", result_";
  protected final String TEXT_438 = ");" + NL + "\t\tif(result_";
  protected final String TEXT_439 = " != 0) {";
  protected final String TEXT_440 = NL + "\t\t\t\tthrow new Exception(\"The Sqoop import job has failed. Please check the logs.\");";
  protected final String TEXT_441 = NL + "\t\t\t\tSystem.err.println(\"The Sqoop import job has failed. Please check the logs.\");" + NL + "\t\t\t\t";
  protected final String TEXT_442 = NL + "\t\t\t\tlog.error(\"";
  protected final String TEXT_443 = " - The Sqoop import job has failed. Please check the logs.\");" + NL + "\t\t\t\t";
  protected final String TEXT_444 = NL + "\t\t}" + NL + "\t} catch (Exception e) {";
  protected final String TEXT_445 = NL + "\t\t\tthrow e;";
  protected final String TEXT_446 = NL + "\t\t\tSystem.err.println(e.getMessage());" + NL + "\t\t\t";
  protected final String TEXT_447 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_448 = " - \" + e.getMessage());" + NL + "\t\t\t";
  protected final String TEXT_449 = NL + "\t}" + NL + "" + NL + "\t";
  protected final String TEXT_450 = NL + "String currentClientPathSeparator_";
  protected final String TEXT_451 = " = (String)globalMap.get(\"current_client_path_separator\");" + NL + "if(currentClientPathSeparator_";
  protected final String TEXT_452 = "!=null) {" + NL + "\tSystem.setProperty(\"path.separator\", currentClientPathSeparator_";
  protected final String TEXT_453 = ");" + NL + "\tglobalMap.put(\"current_client_path_separator\", null);" + NL + "}" + NL + "" + NL + "String originalHadoopUsername_";
  protected final String TEXT_454 = " = (String)globalMap.get(\"HADOOP_USER_NAME_";
  protected final String TEXT_455 = "\");" + NL + "if(originalHadoopUsername_";
  protected final String TEXT_456 = "!=null) {" + NL + "\tSystem.setProperty(\"HADOOP_USER_NAME\", originalHadoopUsername_";
  protected final String TEXT_457 = ");" + NL + "\tglobalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_458 = "\", null);" + NL + "} else {" + NL + "\tSystem.clearProperty(\"HADOOP_USER_NAME\");" + NL + "}";

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
	
	boolean useCommandLine = "true".equals(ElementParameterParser.getValue(node,"__USE_COMMANDLINE__"));
	
	if(useCommandLine) {

    stringBuffer.append(TEXT_30);
    
	boolean useColumns = "true".equals(ElementParameterParser.getValue(node,"__USE_COLUMNS__"));
	List<Map<String, String>> columns = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__COLUMNS__");
	String connection = ElementParameterParser.getValue(node,"__CONNECTION__");
	String table = ElementParameterParser.getValue(node,"__TABLE__");
	String username = ElementParameterParser.getValue(node,"__USERNAME__");
	boolean passwordStoredInFile = "true".equals(ElementParameterParser.getValue(node, "__PASSWORD_STORED_IN_FILE__"));
	boolean printLog = "true".equals(ElementParameterParser.getValue(node,"__PRINT_LOG__"));
	boolean verbose = "true".equals(ElementParameterParser.getValue(node,"__VERBOSE__"));
	boolean append = "true".equals(ElementParameterParser.getValue(node,"__APPEND__"));
	boolean direct = "true".equals(ElementParameterParser.getValue(node,"__DIRECT__"));
	
	boolean compress = "true".equals(ElementParameterParser.getValue(node,"__COMPRESS__"));
	boolean useHadoopCodec = "true".equals(ElementParameterParser.getValue(node,"__DEFINE_HADOOP_CODEC__"));
	String hadoopCodec = ElementParameterParser.getValue(node,"__HADOOP_CODEC__");
	
	boolean deleteTargetDirectory = "true".equals(ElementParameterParser.getValue(node, "__DELETE_TARGET_DIR__"));
	boolean splitInputStreamDirect = "true".equals(ElementParameterParser.getValue(node, "__DEFINE_DIRECT_SPLIT_SIZE__"));
	String directSplitSize = ElementParameterParser.getValue(node, "__DIRECT_SPLIT_SIZE__");
	boolean mysqlDelimiters = "true".equals(ElementParameterParser.getValue(node,"__MYSQL_DELIMITERS__"));
	String fileFormat = ElementParameterParser.getValue(node,"__FILE_FORMAT__");
	boolean useTarget = "true".equals(ElementParameterParser.getValue(node,"__USE_TARGET__"));
	String target = ElementParameterParser.getValue(node,"__TARGET__");
	boolean useMappers = "true".equals(ElementParameterParser.getValue(node,"__USE_MAPPERS__"));
	String mappers = ElementParameterParser.getValue(node,"__MAPPERS__");
	List<Map<String,String>> additionalList = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node,"__ADDITIONAL_ARGUMENTS__");
	boolean isUseSpeedParallel = "true".equals(ElementParameterParser.getValue(node,"__USE_SPEED_PARALLEL__"));
	List<Map<String,String>> specificParams= (List<Map<String, String>>)ElementParameterParser.getObjectValue(node,"__SPECIFIC_PARAMS__");
	boolean overrideJavaMapping = "true".equals(ElementParameterParser.getValue(node, "__DEFINE_JAVA_MAPPING__"));
	boolean overrideHiveMapping = "true".equals(ElementParameterParser.getValue(node, "__DEFINE_HIVE_MAPPING__"));
	boolean isUseAdditionParams = "true".equals(ElementParameterParser.getValue(node,"__USE_ADDITION_PARAM__"));
	String specificAdditionParams=ElementParameterParser.getValue(node,"__SPECIFIC_ADDITION_PARAM__");
	String additionalCommandLineArguments = ElementParameterParser.getValue(node,"__ADDITIONAL_COMMANDLINE_ARGUMENTS__");
	
	boolean useWhere = "true".equals(ElementParameterParser.getValue(node,"__USE_WHERE__"));
	String where = ElementParameterParser.getValue(node,"__WHERE__");
	where = where.substring(1, where.length()-1);
	where = "\"\\\""+where+"\\\"\"";
	
	boolean useQuery = "true".equals(ElementParameterParser.getValue(node,"__USE_QUERY__"));
	String query = ElementParameterParser.getValue(node,"__QUERY__");
	query = query.replaceAll("\n"," ");
	query = query.replaceAll("\r"," ");
	
	boolean useSplit = "true".equals(ElementParameterParser.getValue(node,"__USE_SPLIT__"));
	String split = ElementParameterParser.getValue(node,"__SPLIT__");
	
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));

    stringBuffer.append(TEXT_31);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    
String sColumns = "\\\"";
int size = columns.size();
int current = 0;
for (Map<String, String> row : columns) {
    String column = row.get("COLUMN");
    column = column.substring(1, column.length()-1);
    if (size-1 == current) {
        sColumns += column;
    } else {
        sColumns += column + ",";
    }
    current++;
}
sColumns += "\\\"";

    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_38);
    
String passwordFieldName = "__PASSWORD__";

    stringBuffer.append(TEXT_39);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_42);
    } else {
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_45);
    }
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_47);
    
boolean isNull=false; 
if(isUseSpeedParallel && specificParams!=null){
	boolean isFirstParam=true;
	for(Map<String,String> specificArg : specificParams){ 
		isNull=false; 
	 	if(specificArg.get("SPECIFIC_PARAM_VALUE")==null || "".equals(specificArg.get("SPECIFIC_PARAM_VALUE"))) { 
	 		isNull=true; 
	 	} 
	 	if(!isNull) { 

    stringBuffer.append(TEXT_48);
    stringBuffer.append(specificArg.get("SPECIFIC_PARAM"));
    stringBuffer.append(TEXT_49);
    stringBuffer.append(specificArg.get("SPECIFIC_PARAM_VALUE"));
     
		} 
	} 
} 
if(isUseSpeedParallel && isUseAdditionParams){

    stringBuffer.append(TEXT_50);
    stringBuffer.append(specificAdditionParams);
    
}

    stringBuffer.append(TEXT_51);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(username);
    if(passwordStoredInFile) {
    stringBuffer.append(TEXT_53);
    stringBuffer.append((String)ElementParameterParser.getValue(node, "__PASSWORD_FILE__"));
    } else {
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    }
    if(isUseSpeedParallel){
    stringBuffer.append(TEXT_55);
    }
    if(useQuery){
    stringBuffer.append(TEXT_56);
    stringBuffer.append(query);
    } else {
    stringBuffer.append(TEXT_57);
    stringBuffer.append(table);
    if(useColumns){
    stringBuffer.append(TEXT_58);
    stringBuffer.append(sColumns);
    stringBuffer.append(TEXT_59);
    }
    if(useWhere){
    stringBuffer.append(TEXT_60);
    stringBuffer.append(where);
    }
    }
    if(printLog && verbose){
    stringBuffer.append(TEXT_61);
    }
    if(append){
    stringBuffer.append(TEXT_62);
    }
    if(deleteTargetDirectory){
    stringBuffer.append(TEXT_63);
    }
    if(direct){
    stringBuffer.append(TEXT_64);
    
	if(splitInputStreamDirect) {

    stringBuffer.append(TEXT_65);
    stringBuffer.append(directSplitSize);
    
	}
}
    if(compress){
    stringBuffer.append(TEXT_66);
    
	if(useHadoopCodec) {

    stringBuffer.append(TEXT_67);
    stringBuffer.append(hadoopCodec);
    
	}
}
    if(useTarget || useQuery){
    stringBuffer.append(TEXT_68);
    stringBuffer.append(target);
    }
    if(fileFormat.equals("sequencefile")){
    stringBuffer.append(TEXT_69);
    }
    if(fileFormat.equals("avrofile")){
    stringBuffer.append(TEXT_70);
    }
    if(fileFormat.equals("parquetfile")){
    stringBuffer.append(TEXT_71);
    }
    if(mysqlDelimiters){
    stringBuffer.append(TEXT_72);
    }
    if(useMappers){
    stringBuffer.append(TEXT_73);
    stringBuffer.append(mappers);
    }
    if(useSplit){
    stringBuffer.append(TEXT_74);
    stringBuffer.append(split);
    }
    
if(additionalList != null){ 
	for(Map<String,String> additionalMap : additionalList){ 
		isNull=false; 
	 	if(additionalMap.get("ADDITIONAL_VALUE")==null || "".equals(additionalMap.get("ADDITIONAL_VALUE"))) { 
	 		isNull=true; 
	 	} 
	 	if(!isNull) { 

    stringBuffer.append(TEXT_75);
    stringBuffer.append(additionalMap.get("ADDITIONAL_ARGUMENT"));
    stringBuffer.append(TEXT_76);
    stringBuffer.append(additionalMap.get("ADDITIONAL_VALUE"));
    stringBuffer.append(TEXT_77);
     
		} else { 

    stringBuffer.append(TEXT_78);
    stringBuffer.append(additionalMap.get("ADDITIONAL_ARGUMENT"));
    stringBuffer.append(TEXT_79);
     
		} 
	} 
} 

if(overrideJavaMapping) {
	List<Map<String,String>> javaMappings = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node,"__JAVA_TYPE_MAPPING__");
	if(javaMappings!=null && javaMappings.size()>0) {

    stringBuffer.append(TEXT_80);
    
		boolean first = true;	
		for(Map<String,String> javaMapping : javaMappings) {
			if(first) {
				first = false;

    stringBuffer.append(TEXT_81);
    stringBuffer.append(javaMapping.get("COLUMN_NAME"));
    stringBuffer.append(TEXT_82);
    stringBuffer.append(javaMapping.get("JAVA_TYPE"));
    
			} else {

    stringBuffer.append(TEXT_83);
    stringBuffer.append(javaMapping.get("COLUMN_NAME"));
    stringBuffer.append(TEXT_84);
    stringBuffer.append(javaMapping.get("JAVA_TYPE"));
    
			}	
		}
	}
}

if(overrideHiveMapping) {
	List<Map<String,String>> hiveMappings = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node,"__HIVE_TYPE_MAPPING__");
	if(hiveMappings!=null && hiveMappings.size()>0) {

    stringBuffer.append(TEXT_85);
    	
		boolean first = true;	
		for(Map<String,String> hiveMapping : hiveMappings) {
			if(first) {
				first = false;

    stringBuffer.append(TEXT_86);
    stringBuffer.append(hiveMapping.get("COLUMN_NAME"));
    stringBuffer.append(TEXT_87);
    stringBuffer.append(hiveMapping.get("HIVE_TYPE"));
    
			} else {

    stringBuffer.append(TEXT_88);
    stringBuffer.append(hiveMapping.get("COLUMN_NAME"));
    stringBuffer.append(TEXT_89);
    stringBuffer.append(hiveMapping.get("HIVE_TYPE"));
    
			}	
		}
	}
}

    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(additionalCommandLineArguments);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid );
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
    }
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
    
		if(printLog){

    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    
		}

    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_129);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    }
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    
		if(printLog){

    stringBuffer.append(TEXT_145);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_146);
    
		}

    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    
	if(dieOnError) {

    stringBuffer.append(TEXT_150);
    		
	} else {

    stringBuffer.append(TEXT_151);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_153);
    }
	}

    stringBuffer.append(TEXT_154);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_157);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_158);
    
	if(dieOnError) {

    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    		
	}

    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_164);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    }
    stringBuffer.append(TEXT_168);
    
	} else {

    stringBuffer.append(TEXT_169);
    
	String distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
	String version = ElementParameterParser.getValue(node, "__DB_VERSION__");

	org.talend.hadoop.distribution.component.SqoopComponent sqoopDistrib = null;
	try {
		sqoopDistrib = (org.talend.hadoop.distribution.component.SqoopComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, version);
	} catch (java.lang.Exception e) {
		e.printStackTrace();
		return "";
	}

	boolean isCustom = sqoopDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

	String processId = node.getProcess().getId();
	
	List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");
	
	String namenode = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
	boolean passwordStoredInFile = "true".equals(ElementParameterParser.getValue(node, "__PASSWORD_STORED_IN_FILE__"));
	String jobtracker = ElementParameterParser.getValue(node, "__MAPRED_JOB_TRACKER__");
	boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
	boolean useColumns = "true".equals(ElementParameterParser.getValue(node,"__USE_COLUMNS__"));
	List<Map<String, String>> columns = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__COLUMNS__");
	String connection = ElementParameterParser.getValue(node,"__CONNECTION__");
	String table = ElementParameterParser.getValue(node,"__TABLE__");
	String username = ElementParameterParser.getValue(node,"__USERNAME__");
	boolean printLog = "true".equals(ElementParameterParser.getValue(node,"__PRINT_LOG__"));
	boolean verbose = "true".equals(ElementParameterParser.getValue(node,"__VERBOSE__"));
	boolean append = "true".equals(ElementParameterParser.getValue(node,"__APPEND__"));
	boolean direct = "true".equals(ElementParameterParser.getValue(node,"__DIRECT__"));
	boolean compress = "true".equals(ElementParameterParser.getValue(node,"__COMPRESS__"));
	boolean useHadoopCodec = "true".equals(ElementParameterParser.getValue(node,"__DEFINE_HADOOP_CODEC__"));
	String hadoopCodec = ElementParameterParser.getValue(node,"__HADOOP_CODEC__");
	String fileFormat = ElementParameterParser.getValue(node,"__FILE_FORMAT__");
	boolean useTarget = "true".equals(ElementParameterParser.getValue(node,"__USE_TARGET__"));
	String target = ElementParameterParser.getValue(node,"__TARGET__");
	boolean deleteTargetDirectory = "true".equals(ElementParameterParser.getValue(node, "__DELETE_TARGET_DIR__"));
	boolean splitInputStreamDirect = "true".equals(ElementParameterParser.getValue(node, "__DEFINE_DIRECT_SPLIT_SIZE__"));
	String directSplitSize = ElementParameterParser.getValue(node, "__DIRECT_SPLIT_SIZE__");
	boolean useMappers = "true".equals(ElementParameterParser.getValue(node,"__USE_MAPPERS__"));
	String mappers = ElementParameterParser.getValue(node,"__MAPPERS__");
	List<Map<String,String>> additionalList = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node,"__ADDITIONAL_JAVA__");
	boolean overrideJavaMapping = "true".equals(ElementParameterParser.getValue(node, "__DEFINE_JAVA_MAPPING__"));
	boolean overrideHiveMapping = "true".equals(ElementParameterParser.getValue(node, "__DEFINE_HIVE_MAPPING__"));
    boolean useDatanodeHostname = "true".equals(ElementParameterParser.getValue(node, "__USE_DATANODE_HOSTNAME__"));

    boolean useMapRTicket = ElementParameterParser.getBooleanValue(node, "__USE_MAPRTICKET__");
    String mapRTicketUsername = ElementParameterParser.getValue(node, "__MAPRTICKET_USERNAME__");
    String mapRTicketCluster = ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
    String mapRTicketDuration = ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");

    boolean setMapRHomeDir = ElementParameterParser.getBooleanValue(node, "__SET_MAPR_HOME_DIR__");
    String mapRHomeDir = ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");

    boolean setMapRHadoopLogin = ElementParameterParser.getBooleanValue(node, "__SET_HADOOP_LOGIN__");
    String mapRHadoopLogin = ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");

	boolean useWhere = "true".equals(ElementParameterParser.getValue(node,"__USE_WHERE__"));
	String where = ElementParameterParser.getValue(node,"__WHERE__");
	//where = where.substring(1, where.length()-1);
	//where = "\"\\\""+where+"\\\"\"";
	
	boolean useQuery = "true".equals(ElementParameterParser.getValue(node,"__USE_QUERY__"));
	String query = ElementParameterParser.getValue(node,"__QUERY__");
	query = query.replaceAll("\n"," ");
	query = query.replaceAll("\r"," ");
	
	boolean useSplit = "true".equals(ElementParameterParser.getValue(node,"__USE_SPLIT__"));
	String split = ElementParameterParser.getValue(node,"__SPLIT__");
	
	String fsDefalutName = "fs.default.name";
	
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	String passwordFieldName = "";

    stringBuffer.append(TEXT_170);
    
	boolean useYarn = "true".equals(ElementParameterParser.getValue(node, "__USE_YARN__"));
	String resourceManager = ElementParameterParser.getValue(node, "__RESOURCE_MANAGER__");

	String yarnClasspathSeparator = ElementParameterParser.getValue(node, "__CLASSPATH_SEPARATOR__");

    stringBuffer.append(TEXT_171);
    stringBuffer.append(yarnClasspathSeparator);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_173);
    
	if(isCustom || (!isCustom && sqoopDistrib.doSupportImpersonation())) {
		String hadoopUser = ElementParameterParser.getValue(node, "__HADOOP_USER__");

    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_175);
    stringBuffer.append(hadoopUser);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_178);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_179);
    
	}

    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    stringBuffer.append(fsDefalutName);
    stringBuffer.append(TEXT_183);
    stringBuffer.append(namenode);
    stringBuffer.append(TEXT_184);
    

	if(!isCustom && ("MAPR401".equals(version) || "MAPR410".equals(version))) {//set the default properties

    stringBuffer.append(TEXT_185);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_186);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_187);
    
	}

	boolean isKerberosAvailableHadoop2 = !isCustom && sqoopDistrib.isHadoop2() && sqoopDistrib.doSupportKerberos();
	boolean isHadoop2 = !isCustom && sqoopDistrib.isHadoop2();
	boolean isKerberosAvailableHadoop1 = !isCustom && sqoopDistrib.isHadoop1() && sqoopDistrib.doSupportKerberos();

	if((isCustom && useYarn) || (!isCustom && isHadoop2)) {

    stringBuffer.append(TEXT_188);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    stringBuffer.append(resourceManager);
    stringBuffer.append(TEXT_191);
    
		boolean setJobHistoryAddress = "true".equals(ElementParameterParser.getValue(node, "__SET_JOBHISTORY_ADDRESS__"));
		if(setJobHistoryAddress) {
			String jobHistoryAddress = ElementParameterParser.getValue(node,"__JOBHISTORY_ADDRESS__");
			
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_193);
    stringBuffer.append(jobHistoryAddress);
    stringBuffer.append(TEXT_194);
    
		}

		boolean setSchedulerAddress = "true".equals(ElementParameterParser.getValue(node, "__SET_SCHEDULER_ADDRESS__"));
		if(setSchedulerAddress) {
			String schedulerAddress = ElementParameterParser.getValue(node,"__RESOURCEMANAGER_SCHEDULER_ADDRESS__");

    stringBuffer.append(TEXT_195);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_196);
    stringBuffer.append(schedulerAddress);
    stringBuffer.append(TEXT_197);
    
		}
		boolean setStagingDirectory = "true".equals(ElementParameterParser.getValue(node, "__SET_STAGING_DIRECTORY__"));
		if(setStagingDirectory) {
			String stagingDirectory = ElementParameterParser.getValue(node, "__STAGING_DIRECTORY__");

    stringBuffer.append(TEXT_198);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(stagingDirectory);
    stringBuffer.append(TEXT_200);
    
		}

		boolean crossPlatformSubmission = "true".equals(ElementParameterParser.getValue(node, "__CROSS_PLATFORM_SUBMISSION__"));
		if((!isCustom && sqoopDistrib.doSupportCrossPlatformSubmission()) || (isCustom && useYarn && crossPlatformSubmission)) {

    stringBuffer.append(TEXT_201);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_202);
    
		}

		if(sqoopDistrib.doSupportCustomMRApplicationCP()){

    stringBuffer.append(TEXT_203);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_204);
    stringBuffer.append(sqoopDistrib.getCustomMRApplicationCP());
    stringBuffer.append(TEXT_205);
    
		}


    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(sqoopDistrib.getYarnApplicationClasspath());
    stringBuffer.append(TEXT_208);
    

		boolean setMemory = "true".equals(ElementParameterParser.getValue(node, "__SET_MEMORY__"));
		if(setMemory) {
			String mapMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_MAP_MEMORY_MB__");
			String reduceMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_REDUCE_MEMORY_MB__");
			String amMemory = ElementParameterParser.getValue(node,"__YARN_APP_MAPREDUCE_AM_RESOURCE_MB__");

    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_210);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_211);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_212);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_213);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(amMemory);
    stringBuffer.append(TEXT_215);
    
		}
	} else {

    stringBuffer.append(TEXT_216);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_217);
    stringBuffer.append(jobtracker);
    stringBuffer.append(TEXT_218);
    
	}

    if(!isCustom && ("HDP_1_2".equals(version) || "HDP_1_3".equals(version))) {
        String mapMemory = ElementParameterParser.getValue(node,"__MAPRED_JOB_MAP_MEMORY_MB__");
        String reduceMemory = ElementParameterParser.getValue(node,"__MAPRED_JOB_REDUCE_MEMORY_MB__");

    stringBuffer.append(TEXT_219);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_220);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_222);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_223);
    
    }
	if(hadoopProps!=null && hadoopProps.size() > 0){
		for(Map<String, String> item : hadoopProps){

    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_226);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_227);
    
		}
	}
	if(useKrb) {
		String namenodePrincipal = ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");
		boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
		String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
		String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");

    stringBuffer.append(TEXT_228);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    stringBuffer.append(namenodePrincipal);
    stringBuffer.append(TEXT_230);
    
		if(isKerberosAvailableHadoop1 || (isCustom && !useYarn)) {
			String jobTrackerPrincipal = ElementParameterParser.getValue(node, "__JOBTRACKER_PRINCIPAL__");

    stringBuffer.append(TEXT_231);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(jobTrackerPrincipal);
    stringBuffer.append(TEXT_233);
    
		}

		if(isKerberosAvailableHadoop2 || (isCustom && useYarn)) {
			String resourceManagerPrincipal = ElementParameterParser.getValue(node, "__RESOURCEMANAGER_PRINCIPAL__");
			String jobHistoryPrincipal = ElementParameterParser.getValue(node, "__JOBHISTORY_PRINCIPAL__");

    stringBuffer.append(TEXT_234);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_235);
    stringBuffer.append(resourceManagerPrincipal);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(jobHistoryPrincipal);
    stringBuffer.append(TEXT_238);
    
		}
        if ((isCustom || sqoopDistrib.doSupportMapRTicket()) && useMapRTicket) {
            
    stringBuffer.append(TEXT_239);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_240);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_241);
    
        }
		if(useKeytab) {

    stringBuffer.append(TEXT_242);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_243);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_244);
    
		}
		if ((isCustom || sqoopDistrib.doSupportMapRTicket()) && useMapRTicket) {
            
    stringBuffer.append(TEXT_245);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_246);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_247);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_248);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_249);
    
        }
	} else {
	    // MapR Ticket
	    if ((isCustom || sqoopDistrib.doSupportMapRTicket()) && useMapRTicket) {
            passwordFieldName = "__MAPRTICKET_PASSWORD__";
            
    stringBuffer.append(TEXT_250);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_252);
    
            if (setMapRHadoopLogin) {
                
    stringBuffer.append(TEXT_253);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_254);
    
            } else {
                
    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    
            }
            if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_257);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_258);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_259);
    } else {
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_262);
    }
    stringBuffer.append(TEXT_263);
    
            if(sqoopDistrib.doSupportMaprTicketV52API()){
				
    stringBuffer.append(TEXT_264);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_265);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_266);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_268);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_269);
    
            } else {
				
    stringBuffer.append(TEXT_270);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_271);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_272);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_273);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_274);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_275);
    
            }
        }
	}

    if (useDatanodeHostname) {
        
    stringBuffer.append(TEXT_276);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_277);
    
    }

    stringBuffer.append(TEXT_278);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_279);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_280);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_281);
    
	for (Map<String, String> row : columns) {
		String column = row.get("COLUMN");

    stringBuffer.append(TEXT_282);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_283);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_284);
    
	}

    stringBuffer.append(TEXT_285);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_286);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_287);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_288);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_289);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_290);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_291);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_292);
    if(!passwordStoredInFile || !sqoopDistrib.doJavaAPISupportStorePasswordInFile()) {
    stringBuffer.append(TEXT_293);
    
    		passwordFieldName = "__PASSWORD__";
    	
    stringBuffer.append(TEXT_294);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_295);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_296);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_297);
    } else {
    stringBuffer.append(TEXT_298);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_300);
    }
    stringBuffer.append(TEXT_301);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_302);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_303);
    } else {
    stringBuffer.append(TEXT_304);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_305);
    stringBuffer.append((String)ElementParameterParser.getValue(node, "__PASSWORD_FILE__"));
    stringBuffer.append(TEXT_306);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_307);
    stringBuffer.append(sqoopDistrib.doSupportFetchPasswordFromFile()?"FromFile":"");
    stringBuffer.append(TEXT_308);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_309);
    }
    stringBuffer.append(TEXT_310);
    if(useQuery){
    stringBuffer.append(TEXT_311);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_312);
    stringBuffer.append(query);
    stringBuffer.append(TEXT_313);
    } else {
    stringBuffer.append(TEXT_314);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_315);
    stringBuffer.append(table);
    stringBuffer.append(TEXT_316);
    if(useColumns){
    stringBuffer.append(TEXT_317);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_318);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_319);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_320);
    }
    stringBuffer.append(TEXT_321);
    if(useWhere){
    stringBuffer.append(TEXT_322);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(where);
    stringBuffer.append(TEXT_324);
    }
    stringBuffer.append(TEXT_325);
    }
    stringBuffer.append(TEXT_326);
    if(append){
    stringBuffer.append(TEXT_327);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_328);
    }
    stringBuffer.append(TEXT_329);
    if(direct){
    stringBuffer.append(TEXT_330);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_331);
    if(splitInputStreamDirect) {
    stringBuffer.append(TEXT_332);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_333);
    stringBuffer.append(directSplitSize);
    stringBuffer.append(TEXT_334);
    }
	}
    stringBuffer.append(TEXT_335);
    if(compress){
    stringBuffer.append(TEXT_336);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_337);
    if(useHadoopCodec) {
    stringBuffer.append(TEXT_338);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_339);
    stringBuffer.append(hadoopCodec);
    stringBuffer.append(TEXT_340);
    } 
	}
    stringBuffer.append(TEXT_341);
    if(useTarget || useQuery){
    stringBuffer.append(TEXT_342);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_343);
    stringBuffer.append(target);
    stringBuffer.append(TEXT_344);
    }
    stringBuffer.append(TEXT_345);
    if(fileFormat.equals("sequencefile")){
    stringBuffer.append(TEXT_346);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_347);
    } else if(fileFormat.equals("textfile")){
    stringBuffer.append(TEXT_348);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_349);
    }
	else if(fileFormat.equals("parquetfile")){
    stringBuffer.append(TEXT_350);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_351);
    }
	else {
    stringBuffer.append(TEXT_352);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_353);
    }
    stringBuffer.append(TEXT_354);
    if(useMappers){
    stringBuffer.append(TEXT_355);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_356);
    stringBuffer.append(mappers);
    stringBuffer.append(TEXT_357);
    }
    stringBuffer.append(TEXT_358);
    if(useSplit){
    stringBuffer.append(TEXT_359);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_360);
    stringBuffer.append(split);
    stringBuffer.append(TEXT_361);
    }
    stringBuffer.append(TEXT_362);
    if(!isCustom && sqoopDistrib.doJavaAPISqoopImportSupportDeleteTargetDir()) {
    stringBuffer.append(TEXT_363);
    if(deleteTargetDirectory){
    stringBuffer.append(TEXT_364);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_365);
    }
    stringBuffer.append(TEXT_366);
    }
    stringBuffer.append(TEXT_367);
    
	
	if(overrideJavaMapping) {
		List<Map<String,String>> javaMappings = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node,"__JAVA_TYPE_MAPPING__");
		if(javaMappings!=null && javaMappings.size()>0) {
	
    stringBuffer.append(TEXT_368);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_369);
    
			boolean first = true;	
			for(Map<String,String> javaMapping : javaMappings) {
				if(first) {
					first = false;
	
    stringBuffer.append(TEXT_370);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_371);
    stringBuffer.append(javaMapping.get("COLUMN_NAME"));
    stringBuffer.append(TEXT_372);
    stringBuffer.append(javaMapping.get("JAVA_TYPE"));
    stringBuffer.append(TEXT_373);
    
				} else {
	
    stringBuffer.append(TEXT_374);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_375);
    stringBuffer.append(javaMapping.get("COLUMN_NAME"));
    stringBuffer.append(TEXT_376);
    stringBuffer.append(javaMapping.get("JAVA_TYPE"));
    stringBuffer.append(TEXT_377);
    
				}	
			}
	
    stringBuffer.append(TEXT_378);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_379);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_380);
    
		}
	}

	if(overrideHiveMapping) {
		List<Map<String,String>> hiveMappings = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node,"__HIVE_TYPE_MAPPING__");
		if(hiveMappings!=null && hiveMappings.size()>0) {
	
    stringBuffer.append(TEXT_381);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    	
			boolean first = true;	
			for(Map<String,String> hiveMapping : hiveMappings) {
				if(first) {
					first = false;
	
    stringBuffer.append(TEXT_383);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_384);
    stringBuffer.append(hiveMapping.get("COLUMN_NAME"));
    stringBuffer.append(TEXT_385);
    stringBuffer.append(hiveMapping.get("HIVE_TYPE"));
    stringBuffer.append(TEXT_386);
    
				} else {
	
    stringBuffer.append(TEXT_387);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_388);
    stringBuffer.append(hiveMapping.get("COLUMN_NAME"));
    stringBuffer.append(TEXT_389);
    stringBuffer.append(hiveMapping.get("HIVE_TYPE"));
    stringBuffer.append(TEXT_390);
    
				}	
			}
	
    stringBuffer.append(TEXT_391);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_392);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_393);
    
		}
	}
	
    stringBuffer.append(TEXT_394);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_395);
    
	if(sqoopDistrib.isImpactedBySqoop2995()){
	//TODO, relate with https://issues.apache.org/jira/browse/SQOOP-2995, have to set a init value to the new map type key "customtool.options.jsonmap". But the key hasn't been used
	
    stringBuffer.append(TEXT_396);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_397);
    
	}
	if(additionalList != null){ 
		for(Map<String,String> additionalMap : additionalList){
			if(additionalMap.get("ADDITIONAL_VALUE")==null || "".equals(additionalMap.get("ADDITIONAL_VALUE"))) { 
				break; 
			}
            		String currentArgument = additionalMap.get("ADDITIONAL_ARGUMENT");
		        if(currentArgument.contains("delimiters") && !currentArgument.contains("required")) {

    stringBuffer.append(TEXT_398);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_399);
    stringBuffer.append(currentArgument);
    stringBuffer.append(TEXT_400);
    stringBuffer.append(additionalMap.get("ADDITIONAL_VALUE"));
    stringBuffer.append(TEXT_401);
    
			} else {

    stringBuffer.append(TEXT_402);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_403);
    stringBuffer.append(currentArgument);
    stringBuffer.append(TEXT_404);
    stringBuffer.append(additionalMap.get("ADDITIONAL_VALUE"));
    stringBuffer.append(TEXT_405);
    
			}
		} 
	} 

    stringBuffer.append(TEXT_406);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_407);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_408);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_409);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_410);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_411);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_412);
    
	// Register jars to handle the Avro format.
	
	boolean generateAddJarCodeForAll = "avrofile".equals(fileFormat);
	boolean emptyStringBuilder = true; // true when we don't import any dependency in the libjars.
		
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
		jarsToRegister.add("avro-");
	
		for (int j = 0; j < commandLine.length; j++) {
			if(commandLine[j].contains("jar")) {
				jars = java.util.Arrays.asList(commandLine[j].split(";"));
				break;
			}
		}
	}
		
	if(jarsToRegister!=null && jars!=null) {

    stringBuffer.append(TEXT_413);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_414);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_415);
    
		
		for(int i=0; i<jarsToRegister.size(); i++) {
			String jarToRegister = jarsToRegister.get(i);
			for(int j=0; j<jars.size(); j++) {
				if(jars.get(j).contains(jarToRegister)) {
					emptyStringBuilder = false;

    stringBuffer.append(TEXT_416);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_417);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_418);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_419);
    
				}
			}
		}
	}

    stringBuffer.append(TEXT_420);
    
	// Register jars to handle the Parquet format.
	
	boolean parquetGenerateAddJarCodeForAll = "parquetfile".equals(fileFormat);
	boolean parquetEmptyStringBuilder = true; // true when we don't import any dependency in the libjars.
		
	java.util.List<String> parquetJarsToRegister = null;
	java.util.List<String> parquetJars = null;
	if(parquetGenerateAddJarCodeForAll) {
		String[] commandLine = new String[] {"<command>"};
		try {
			commandLine = ProcessorUtilities.getCommandLine("win32",true, processId, "",org.talend.designer.runprocess.IProcessor.NO_STATISTICS,org.talend.designer.runprocess.IProcessor.NO_TRACES, new String[]{});
		} catch (ProcessorException e) {
			e.printStackTrace();
		}
	
		parquetJarsToRegister = new java.util.ArrayList();
		parquetJarsToRegister.add("kite-data-core-");
		parquetJarsToRegister.add("kite-data-mapreduce-");
		parquetJarsToRegister.add("kite-hadoop-compatibility-");
		parquetJarsToRegister.add("jackson-databind-");
		parquetJarsToRegister.add("jackson-core-");
		parquetJarsToRegister.add("parquet-");
	
		for (int j = 0; j < commandLine.length; j++) {
			if(commandLine[j].contains("jar")) {
				parquetJars = java.util.Arrays.asList(commandLine[j].split(";"));
				break;
			}
		}
	}
		
	if(parquetJarsToRegister!=null && parquetJars!=null) {

    stringBuffer.append(TEXT_421);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_422);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_423);
    
		
		for(int i=0; i<parquetJarsToRegister.size(); i++) {
			String parquetJarToRegister = parquetJarsToRegister.get(i);
			for(int j=0; j<parquetJars.size(); j++) {
				if(parquetJars.get(j).contains(parquetJarToRegister)) {
					emptyStringBuilder = false;

    stringBuffer.append(TEXT_424);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_425);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_426);
    stringBuffer.append(parquetJars.get(j));
    stringBuffer.append(TEXT_427);
    
				}
			}
		}
	}

    stringBuffer.append(TEXT_428);
    
		if(!emptyStringBuilder) {

    stringBuffer.append(TEXT_429);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_430);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_431);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_432);
    
		} else {

    stringBuffer.append(TEXT_433);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_434);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_435);
    
		}

    stringBuffer.append(TEXT_436);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_437);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_438);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_439);
    
			if(dieOnError) {

    stringBuffer.append(TEXT_440);
    
			} else {

    stringBuffer.append(TEXT_441);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_442);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_443);
    }
			}

    stringBuffer.append(TEXT_444);
    
		if(dieOnError) {

    stringBuffer.append(TEXT_445);
    
		} else {

    stringBuffer.append(TEXT_446);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_447);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_448);
    }
		}

    stringBuffer.append(TEXT_449);
    stringBuffer.append(TEXT_450);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_451);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_452);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_453);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_454);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_455);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_456);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_457);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_458);
    
	}
	

    return stringBuffer.toString();
  }
}
