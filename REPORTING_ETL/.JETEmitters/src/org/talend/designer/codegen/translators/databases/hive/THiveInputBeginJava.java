package org.talend.designer.codegen.translators.databases.hive;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import java.util.List;
import java.util.Map;

public class THiveInputBeginJava
{
  protected static String nl;
  public static synchronized THiveInputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THiveInputBeginJava result = new THiveInputBeginJava();
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
  protected final String TEXT_30 = NL + "\t\t\t\tif(conn_";
  protected final String TEXT_31 = " != null) {" + NL + "\t\t\t\t\tif(conn_";
  protected final String TEXT_32 = ".getMetaData() != null) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_33 = NL + "\t\t\t\t\t\tlog.debug(\"";
  protected final String TEXT_34 = " - Uses an existing connection ";
  protected final String TEXT_35 = ".\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_36 = NL + "\t\t\t\t\t\tlog.debug(\"";
  protected final String TEXT_37 = " - Uses an existing connection with username '\" + conn_";
  protected final String TEXT_38 = ".getMetaData().getUserName() + \"'. Connection URL: \" + conn_";
  protected final String TEXT_39 = ".getMetaData().getURL() + \".\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_40 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_41 = NL + "\t\t\tconn_";
  protected final String TEXT_42 = " = java.sql.DriverManager.getConnection(url_";
  protected final String TEXT_43 = ", dbUser_";
  protected final String TEXT_44 = ", dbPwd_";
  protected final String TEXT_45 = ");" + NL + "\t\t\t";
  protected final String TEXT_46 = NL + "\t\t\tconn_";
  protected final String TEXT_47 = ".rollback();" + NL + "\t\t\t";
  protected final String TEXT_48 = NL + "\t\t\tconn_";
  protected final String TEXT_49 = ".commit();" + NL + "\t\t\t";
  protected final String TEXT_50 = NL + "\t\t\tconn_";
  protected final String TEXT_51 = ".close();" + NL + "\t\t\t";
  protected final String TEXT_52 = NL + "\t\t\t\tconn_";
  protected final String TEXT_53 = ".setAutoCommit(";
  protected final String TEXT_54 = ");" + NL + "\t\t\t";
  protected final String TEXT_55 = NL + "\t\t\t\tlog.";
  protected final String TEXT_56 = "(\"";
  protected final String TEXT_57 = " - \" + ";
  protected final String TEXT_58 = ".getMessage());" + NL + "\t\t\t";
  protected final String TEXT_59 = NL + "\t    \t\tlog.";
  protected final String TEXT_60 = "(\"";
  protected final String TEXT_61 = "\");" + NL + "\t\t\t";
  protected final String TEXT_62 = NL + "\t\t\t\tpstmt_";
  protected final String TEXT_63 = ".executeBatch();" + NL + "\t\t\t";
  protected final String TEXT_64 = NL + "\t\t\t\tint countSum_";
  protected final String TEXT_65 = " = 0;" + NL + "\t\t\t\tfor(int countEach_";
  protected final String TEXT_66 = ": pstmt_";
  protected final String TEXT_67 = ".executeBatch()) {" + NL + "\t\t\t\t\tcountSum_";
  protected final String TEXT_68 = " += (countEach_";
  protected final String TEXT_69 = " < 0 ? 0 : ";
  protected final String TEXT_70 = ");" + NL + "\t\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_71 = NL + "            java.util.Map<String, routines.system.TalendDataSource> dataSources_";
  protected final String TEXT_72 = " = (java.util.Map<String, routines.system.TalendDataSource>) globalMap.get(KEY_DB_DATASOURCES);" + NL + "            if (null != dataSources_";
  protected final String TEXT_73 = ") {";
  protected final String TEXT_74 = NL + "    \t\t\tString dsAlias_";
  protected final String TEXT_75 = " = ";
  protected final String TEXT_76 = ";" + NL + "        \t\tif (dataSources_";
  protected final String TEXT_77 = ".get(dsAlias_";
  protected final String TEXT_78 = ") == null) {" + NL + "           \t\t\tthrow new RuntimeException(\"No DataSource with alias: \" + dsAlias_";
  protected final String TEXT_79 = " + \" available!\");" + NL + "              \t}" + NL + "        \t\tconn_";
  protected final String TEXT_80 = " = dataSources_";
  protected final String TEXT_81 = ".get(dsAlias_";
  protected final String TEXT_82 = ").getConnection();" + NL + "            } else {";
  protected final String TEXT_83 = NL + "\t\t\t\tString driverClass_";
  protected final String TEXT_84 = " = \"";
  protected final String TEXT_85 = "\";" + NL + "\t\t\t    java.lang.Class.forName(driverClass_";
  protected final String TEXT_86 = ");" + NL + "\t\t\t   \tString dbUser_";
  protected final String TEXT_87 = " = ";
  protected final String TEXT_88 = ";" + NL + "\t\t\t   \t" + NL + "        \t\t";
  protected final String TEXT_89 = NL + "        \t\t" + NL + "        \t\t";
  protected final String TEXT_90 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_91 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_92 = ");";
  protected final String TEXT_93 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_94 = " = ";
  protected final String TEXT_95 = "; ";
  protected final String TEXT_96 = NL + "\t\t\t   \t" + NL + "\t\t        String dbPwd_";
  protected final String TEXT_97 = " = decryptedPassword_";
  protected final String TEXT_98 = ";" + NL + "\t\t        " + NL + "\t\t\t\t";
  protected final String TEXT_99 = NL + "\t\t\t\t";
  protected final String TEXT_100 = NL + "\t\t\t\tconn_";
  protected final String TEXT_101 = " = java.sql.DriverManager.getConnection(url_";
  protected final String TEXT_102 = ",dbUser_";
  protected final String TEXT_103 = ",dbPwd_";
  protected final String TEXT_104 = ");";
  protected final String TEXT_105 = NL + "\t\t\t\tconn_";
  protected final String TEXT_106 = " = java.sql.DriverManager.getConnection(url_";
  protected final String TEXT_107 = ");";
  protected final String TEXT_108 = NL + "\t\t        ";
  protected final String TEXT_109 = NL + "\t\t    }";
  protected final String TEXT_110 = NL + "\t\t\tjava.sql.Statement stmt_";
  protected final String TEXT_111 = " = conn_";
  protected final String TEXT_112 = ".createStatement();";
  protected final String TEXT_113 = NL + "\t\t\tjava.sql.Statement stmt_";
  protected final String TEXT_114 = " = conn_";
  protected final String TEXT_115 = ".createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY," + NL + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tjava.sql.ResultSet.CONCUR_READ_ONLY);" + NL + "\t\t\t";
  protected final String TEXT_116 = NL + "\t\t\t\t\ttmpContent_";
  protected final String TEXT_117 = " = rs_";
  protected final String TEXT_118 = ".getString(";
  protected final String TEXT_119 = "column_index_";
  protected final String TEXT_120 = ");";
  protected final String TEXT_121 = NL + "                        if(tmpContent_";
  protected final String TEXT_122 = " != null) {" + NL + "                            tmpContent_";
  protected final String TEXT_123 = " = tmpContent_";
  protected final String TEXT_124 = ";" + NL + "                        }";
  protected final String TEXT_125 = NL + "                    if(tmpContent_";
  protected final String TEXT_126 = " != null && tmpContent_";
  protected final String TEXT_127 = ".length() > 0) {\t\t\t  \t";
  protected final String TEXT_128 = NL + "                        ";
  protected final String TEXT_129 = ".";
  protected final String TEXT_130 = " = tmpContent_";
  protected final String TEXT_131 = ".charAt(0);\t\t\t  \t\t" + NL + "                    } else {\t\t\t  \t";
  protected final String TEXT_132 = "\t\t\t  \t    " + NL + "                            if(tmpContent_";
  protected final String TEXT_133 = " == null) {\t\t\t  \t   \t";
  protected final String TEXT_134 = NL + "                                ";
  protected final String TEXT_135 = ".";
  protected final String TEXT_136 = " = null;\t\t\t  \t\t\t" + NL + "                            } else {\t\t\t  \t\t";
  protected final String TEXT_137 = NL + "                                ";
  protected final String TEXT_138 = ".";
  protected final String TEXT_139 = " = '\\0';\t\t\t  \t\t\t" + NL + "                            }";
  protected final String TEXT_140 = "\t\t\t  \t\t" + NL + "                            if((\"\").equals(tmpContent_";
  protected final String TEXT_141 = ")) {\t\t\t  \t\t";
  protected final String TEXT_142 = NL + "                                ";
  protected final String TEXT_143 = ".";
  protected final String TEXT_144 = " = '\\0';\t\t\t  \t\t\t" + NL + "                            } else {" + NL + "        \t\t\t  \t\t\tthrow new RuntimeException(" + NL + "        \t\t\t\t\t\t\t\"Value is empty for column : '";
  protected final String TEXT_145 = "' in '";
  protected final String TEXT_146 = "' connection, value is invalid or this column should be nullable or have a default value.\");\t\t\t\t\t\t\t" + NL + "                            }\t\t\t  \t\t";
  protected final String TEXT_147 = NL + "                    }";
  protected final String TEXT_148 = NL + "\t\t\t";
  protected final String TEXT_149 = ".";
  protected final String TEXT_150 = " = routines.system.JDBCUtil.getDate(rs_";
  protected final String TEXT_151 = ", ";
  protected final String TEXT_152 = "column_index_";
  protected final String TEXT_153 = ");";
  protected final String TEXT_154 = NL + "        \t";
  protected final String TEXT_155 = ".";
  protected final String TEXT_156 = " = routines.system.JDBCUtil.getString(rs_";
  protected final String TEXT_157 = ", ";
  protected final String TEXT_158 = "column_index_";
  protected final String TEXT_159 = ", ";
  protected final String TEXT_160 = "false";
  protected final String TEXT_161 = "true";
  protected final String TEXT_162 = ");";
  protected final String TEXT_163 = NL + "            if(rs_";
  protected final String TEXT_164 = ".getObject(";
  protected final String TEXT_165 = "column_index_";
  protected final String TEXT_166 = ") != null) {";
  protected final String TEXT_167 = NL + "                ";
  protected final String TEXT_168 = ".";
  protected final String TEXT_169 = " = rs_";
  protected final String TEXT_170 = ".get";
  protected final String TEXT_171 = "(";
  protected final String TEXT_172 = "column_index_";
  protected final String TEXT_173 = ");" + NL + "            } else {";
  protected final String TEXT_174 = NL + "                    ";
  protected final String TEXT_175 = ".";
  protected final String TEXT_176 = " = null;";
  protected final String TEXT_177 = NL + "                    throw new RuntimeException(\"Null value in non-Nullable column\");";
  protected final String TEXT_178 = NL + "            }";
  protected final String TEXT_179 = NL + NL + "\t";
  protected final String TEXT_180 = NL + "\t\t\tString dbname_";
  protected final String TEXT_181 = " = (String)globalMap.get(\"";
  protected final String TEXT_182 = "\");" + NL + "        \tif(dbname_";
  protected final String TEXT_183 = "!=null && !\"\".equals(dbname_";
  protected final String TEXT_184 = ".trim()) && !\"default\".equals(dbname_";
  protected final String TEXT_185 = ".trim())) {" + NL + "            \tjava.sql.Statement goToDatabase_";
  protected final String TEXT_186 = " = conn_";
  protected final String TEXT_187 = ".createStatement();" + NL + "            \tgoToDatabase_";
  protected final String TEXT_188 = ".execute(\"use \" + dbname_";
  protected final String TEXT_189 = ");" + NL + "            \tgoToDatabase_";
  protected final String TEXT_190 = ".close();" + NL + "        \t}" + NL + "" + NL + "        \tString dbUser_";
  protected final String TEXT_191 = " = (String)globalMap.get(\"";
  protected final String TEXT_192 = "\");" + NL + "        \tglobalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_193 = "\", System.getProperty(\"HADOOP_USER_NAME\"));" + NL + "    \t\tif(dbUser_";
  protected final String TEXT_194 = "!=null && !\"\".equals(dbUser_";
  protected final String TEXT_195 = ".trim())) {" + NL + "    \t\t\tSystem.setProperty(\"HADOOP_USER_NAME\",dbUser_";
  protected final String TEXT_196 = ");" + NL + "    \t\t\t//make relative file path work for hive" + NL + "    \t\t\tglobalMap.put(\"current_client_user_name\", System.getProperty(\"user.name\"));" + NL + "    \t\t\tSystem.setProperty(\"user.name\",dbUser_";
  protected final String TEXT_197 = ");" + NL + "    \t\t}";
  protected final String TEXT_198 = NL + "\t\t\t\tif(true) {" + NL + "\t\t\t\t\tthrow new java.lang.Exception(\"The distribution ";
  protected final String TEXT_199 = " does not support this version of Hive . Please check your component configuration.\");" + NL + "\t\t\t\t}";
  protected final String TEXT_200 = NL + "\t\t\t\tif(true) {" + NL + "\t\t\t\t\tthrow new java.lang.Exception(\"The distribution ";
  protected final String TEXT_201 = " does not support this connection mode . Please check your component configuration.\");" + NL + "\t\t\t\t}";
  protected final String TEXT_202 = NL + "\t\t\t\tif(true) {" + NL + "\t\t\t\t\tthrow new java.lang.Exception(\"The Hive version and the connection mode are not compatible together. Please check your component configuration.\");" + NL + "\t\t\t\t}";
  protected final String TEXT_203 = NL + "\t\t\tjava.sql.Statement init_";
  protected final String TEXT_204 = " = conn_";
  protected final String TEXT_205 = ".createStatement();";
  protected final String TEXT_206 = NL + "\t        \tinit_";
  protected final String TEXT_207 = ".execute(\"SET mapred.job.map.memory.mb=\" + ";
  protected final String TEXT_208 = ");" + NL + "\t\t    \tinit_";
  protected final String TEXT_209 = ".execute(\"SET mapred.job.reduce.memory.mb=\" + ";
  protected final String TEXT_210 = ");";
  protected final String TEXT_211 = NL + "\t\t\t\tinit_";
  protected final String TEXT_212 = ".execute(\"SET dfs.namenode.kerberos.principal=\" + ";
  protected final String TEXT_213 = ");";
  protected final String TEXT_214 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_215 = ".execute(\"SET mapreduce.jobtracker.kerberos.principal=\" + ";
  protected final String TEXT_216 = ");";
  protected final String TEXT_217 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_218 = ".execute(\"SET yarn.resourcemanager.principal=\" + ";
  protected final String TEXT_219 = ");";
  protected final String TEXT_220 = NL + "            \t\tinit_";
  protected final String TEXT_221 = ".execute(\"SET mapreduce.framework.name=yarn\");" + NL + "            \t\tinit_";
  protected final String TEXT_222 = ".execute(\"SET yarn.resourcemanager.address=\" + ";
  protected final String TEXT_223 = ");";
  protected final String TEXT_224 = NL + "        \t\t\tinit_";
  protected final String TEXT_225 = ".execute(\"SET mapreduce.jobhistory.address=\" + ";
  protected final String TEXT_226 = ");" + NL + "        \t\t\t";
  protected final String TEXT_227 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_228 = ".execute(\"SET yarn.resourcemanager.scheduler.address=\" + ";
  protected final String TEXT_229 = ");";
  protected final String TEXT_230 = NL + "\t                init_";
  protected final String TEXT_231 = ".execute(\"SET dfs.client.use.datanode.hostname=true\");" + NL + "\t                init_";
  protected final String TEXT_232 = ".execute(\"SET dfs.datanode.use.datanode.hostname=true\");" + NL + "\t                ";
  protected final String TEXT_233 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_234 = ".execute(\"SET fs.default.name=\" + ";
  protected final String TEXT_235 = ");";
  protected final String TEXT_236 = NL + "\t\t\t\t\t\tinit_";
  protected final String TEXT_237 = ".execute(\"SET mapreduce.app-submission.cross-platform=true\");";
  protected final String TEXT_238 = NL + "\t\t\t\t\t\tinit_";
  protected final String TEXT_239 = ".execute(\"SET mapreduce.job.map.output.collector.class=org.apache.hadoop.mapred.MapRFsOutputBuffer\");" + NL + "\t\t\t\t\t\tinit_";
  protected final String TEXT_240 = ".execute(\"SET mapreduce.job.reduce.shuffle.consumer.plugin.class=org.apache.hadoop.mapreduce.task.reduce.DirectShuffle\");";
  protected final String TEXT_241 = NL + "\t\t\t\t\t\tinit_";
  protected final String TEXT_242 = ".execute(\"SET mapreduce.application.classpath=";
  protected final String TEXT_243 = "\");";
  protected final String TEXT_244 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_245 = ".execute(\"SET yarn.application.classpath=";
  protected final String TEXT_246 = "\");";
  protected final String TEXT_247 = NL + "        \t\t\tinit_";
  protected final String TEXT_248 = ".execute(\"SET mapreduce.map.memory.mb=\" + ";
  protected final String TEXT_249 = ");" + NL + "        \t\t\tinit_";
  protected final String TEXT_250 = ".execute(\"SET mapreduce.reduce.memory.mb=\" + ";
  protected final String TEXT_251 = ");" + NL + "        \t\t\tinit_";
  protected final String TEXT_252 = ".execute(\"SET yarn.app.mapreduce.am.resource.mb=\" + ";
  protected final String TEXT_253 = ");";
  protected final String TEXT_254 = NL + "\t\t\t\t\tinit_";
  protected final String TEXT_255 = ".execute(\"SET \"+";
  protected final String TEXT_256 = "+\"=\"+";
  protected final String TEXT_257 = ");";
  protected final String TEXT_258 = NL + "\t\t\t";
  protected final String TEXT_259 = NL + "            \t";
  protected final String TEXT_260 = NL;
  protected final String TEXT_261 = NL + "    \t\tinit_";
  protected final String TEXT_262 = ".execute(\"SET hive.execution.engine=tez\");";
  protected final String TEXT_263 = NL + "                        System.err.println(\"Please set the path of Tez lib in 'Tez lib path'!\");";
  protected final String TEXT_264 = NL;
  protected final String TEXT_265 = NL + "        String username_";
  protected final String TEXT_266 = " = (";
  protected final String TEXT_267 = " != null && !\"\".equals(";
  protected final String TEXT_268 = ")) ? ";
  protected final String TEXT_269 = " : System.getProperty(\"user.name\");;" + NL + "        org.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_270 = " = null;";
  protected final String TEXT_271 = NL + "        org.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_272 = " = new org.apache.hadoop.conf.Configuration(); " + NL + "        conf_";
  protected final String TEXT_273 = ".set(\"";
  protected final String TEXT_274 = "\", ";
  protected final String TEXT_275 = ");" + NL + "        ";
  protected final String TEXT_276 = NL + "            conf_";
  protected final String TEXT_277 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_278 = NL + "        \t\tconf_";
  protected final String TEXT_279 = ".set(";
  protected final String TEXT_280 = " ,";
  protected final String TEXT_281 = ");" + NL + "        \t";
  protected final String TEXT_282 = NL + "            username_";
  protected final String TEXT_283 = " = null;" + NL + "    \t\tconf_";
  protected final String TEXT_284 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_285 = ");" + NL + "    \t\t";
  protected final String TEXT_286 = NL + "    \t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_287 = ", ";
  protected final String TEXT_288 = ");" + NL + "    \t\t";
  protected final String TEXT_289 = NL + "\t\t\tconf_";
  protected final String TEXT_290 = ".set(\"hadoop.job.ugi\",username_";
  protected final String TEXT_291 = "+\",\"+username_";
  protected final String TEXT_292 = ");" + NL + "        \tfs_";
  protected final String TEXT_293 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_294 = ");";
  protected final String TEXT_295 = NL + "        \t" + NL + "        \tif(username_";
  protected final String TEXT_296 = " == null || \"\".equals(username_";
  protected final String TEXT_297 = ")){" + NL + "        \t\tfs_";
  protected final String TEXT_298 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_299 = ");" + NL + "        \t}else{" + NL + "        \t\tfs_";
  protected final String TEXT_300 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_301 = ".get(\"";
  protected final String TEXT_302 = "\")),conf_";
  protected final String TEXT_303 = ",username_";
  protected final String TEXT_304 = ");" + NL + "        \t}\t";
  protected final String TEXT_305 = NL + "                    String hdfsUserName_";
  protected final String TEXT_306 = " = (";
  protected final String TEXT_307 = " != null && !\"\".equals(";
  protected final String TEXT_308 = ")) ? ";
  protected final String TEXT_309 = " : System.getProperty(\"user.name\");" + NL + "                    String tezLibPath_";
  protected final String TEXT_310 = " = \"/tmp/\" + hdfsUserName_";
  protected final String TEXT_311 = " + \"/talend_tez_libs/";
  protected final String TEXT_312 = "\";";
  protected final String TEXT_313 = NL + "                    String tezLibPath_";
  protected final String TEXT_314 = " = ";
  protected final String TEXT_315 = ";";
  protected final String TEXT_316 = NL + "                fs_";
  protected final String TEXT_317 = ".mkdirs(new org.apache.hadoop.fs.Path(tezLibPath_";
  protected final String TEXT_318 = "));";
  protected final String TEXT_319 = NL + "                String[] classPaths_";
  protected final String TEXT_320 = " = System.getProperty(\"java.class.path\").split(";
  protected final String TEXT_321 = "String.valueOf(globalMap.get(\"current_client_path_separator\"))";
  protected final String TEXT_322 = "System.getProperty(\"path.separator\")";
  protected final String TEXT_323 = ");" + NL + "                String tezLibLocalPath_";
  protected final String TEXT_324 = " = null;" + NL + "                for(String classPath_";
  protected final String TEXT_325 = " : classPaths_";
  protected final String TEXT_326 = "){";
  protected final String TEXT_327 = NL + "                        if(classPath_";
  protected final String TEXT_328 = ".endsWith(\"";
  protected final String TEXT_329 = "\")){" + NL + "                            org.apache.hadoop.fs.Path tezJarPath_";
  protected final String TEXT_330 = " = new org.apache.hadoop.fs.Path(tezLibPath_";
  protected final String TEXT_331 = " + \"/";
  protected final String TEXT_332 = "\");" + NL + "                            if(!fs_";
  protected final String TEXT_333 = ".exists(tezJarPath_";
  protected final String TEXT_334 = ")){" + NL + "                                fs_";
  protected final String TEXT_335 = ".copyFromLocalFile(false, false, new org.apache.hadoop.fs.Path(classPath_";
  protected final String TEXT_336 = "), tezJarPath_";
  protected final String TEXT_337 = ");" + NL + "                            }" + NL + "                        }";
  protected final String TEXT_338 = NL + "                }";
  protected final String TEXT_339 = NL + "                String tezLibPath_";
  protected final String TEXT_340 = " = ";
  protected final String TEXT_341 = ";";
  protected final String TEXT_342 = NL + "\t\t\tStringBuilder script_";
  protected final String TEXT_343 = " = new StringBuilder();" + NL + "\t\t\tString[] tezLibPaths_";
  protected final String TEXT_344 = " = tezLibPath_";
  protected final String TEXT_345 = ".split(\",\");" + NL + "\t\t\tscript_";
  protected final String TEXT_346 = ".append(\"SET tez.lib.uris=\");" + NL + "\t\t\tint tezLibPathCount_";
  protected final String TEXT_347 = " = 1;" + NL + "\t\t\tfor(String tezLibPathKey_";
  protected final String TEXT_348 = " : tezLibPaths_";
  protected final String TEXT_349 = "){" + NL + "\t\t\t\t script_";
  protected final String TEXT_350 = ".append(";
  protected final String TEXT_351 = " + \"/\" + tezLibPathKey_";
  protected final String TEXT_352 = ");" + NL + "\t\t\t\t if(tezLibPathCount_";
  protected final String TEXT_353 = " < tezLibPaths_";
  protected final String TEXT_354 = ".length){" + NL + "\t\t\t\t \tscript_";
  protected final String TEXT_355 = ".append(\",\");" + NL + "\t\t\t\t }" + NL + "\t\t\t\t tezLibPathCount_";
  protected final String TEXT_356 = "++;" + NL + "\t\t\t}" + NL + "\t\t\tinit_";
  protected final String TEXT_357 = ".execute(script_";
  protected final String TEXT_358 = ".toString());" + NL + "\t\t";
  protected final String TEXT_359 = NL + "            \t";
  protected final String TEXT_360 = NL + NL + "\t\t\tinit_";
  protected final String TEXT_361 = ".close();" + NL + "" + NL + "        \tString dbname_";
  protected final String TEXT_362 = " = ";
  protected final String TEXT_363 = ";" + NL + "        \tif(dbname_";
  protected final String TEXT_364 = "!=null && !\"\".equals(dbname_";
  protected final String TEXT_365 = ".trim()) && !\"default\".equals(dbname_";
  protected final String TEXT_366 = ".trim())) {" + NL + "            \tjava.sql.Statement goToDatabase_";
  protected final String TEXT_367 = " = conn_";
  protected final String TEXT_368 = ".createStatement();" + NL + "            \tgoToDatabase_";
  protected final String TEXT_369 = ".execute(\"use \" + dbname_";
  protected final String TEXT_370 = ");" + NL + "            \tgoToDatabase_";
  protected final String TEXT_371 = ".close();" + NL + "        \t}";
  protected final String TEXT_372 = NL + "    \t\t\tjava.sql.Statement statement_";
  protected final String TEXT_373 = " = conn_";
  protected final String TEXT_374 = ".createStatement();" + NL + "        \t\t";
  protected final String TEXT_375 = NL + "        \t\t\tstatement_";
  protected final String TEXT_376 = ".execute(\"SET hbase.zookeeper.quorum=\"+";
  protected final String TEXT_377 = ");" + NL + "        \t\t";
  protected final String TEXT_378 = NL + NL + "        \t\t";
  protected final String TEXT_379 = NL + "        \t\t\tstatement_";
  protected final String TEXT_380 = ".execute(\"SET hbase.zookeeper.property.clientPort=\"+";
  protected final String TEXT_381 = ");" + NL + "        \t\t";
  protected final String TEXT_382 = NL + NL + "\t\t\t\t";
  protected final String TEXT_383 = NL + "\t\t\t\t\tstatement_";
  protected final String TEXT_384 = ".execute(\"SET zookeeper.znode.parent=\"+";
  protected final String TEXT_385 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_386 = NL + NL + "\t\t\t\t";
  protected final String TEXT_387 = NL + "\t\t\t\t\tstatement_";
  protected final String TEXT_388 = ".execute(\"SET hbase.security.authentication=kerberos\");" + NL + "\t\t\t\t\tstatement_";
  protected final String TEXT_389 = ".execute(\"SET hbase.rpc.engine=org.apache.hadoop.hbase.ipc.SecureRpcEngine\");" + NL + "" + NL + "\t\t\t\t\t";
  protected final String TEXT_390 = NL + "\t\t\t\t\t\tstatement_";
  protected final String TEXT_391 = ".execute(\"SET hbase.master.kerberos.principal=\"+";
  protected final String TEXT_392 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_393 = NL + "\t\t\t\t\t";
  protected final String TEXT_394 = NL + "\t\t\t\t\t\tstatement_";
  protected final String TEXT_395 = ".execute(\"SET hbase.regionserver.kerberos.principal=\"+";
  protected final String TEXT_396 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_397 = NL + "\t\t\t\t";
  protected final String TEXT_398 = NL + NL + "        \t\t";
  protected final String TEXT_399 = NL + "    \t\t\t\tstatement_";
  protected final String TEXT_400 = ".execute(\"add jar \"+";
  protected final String TEXT_401 = ");" + NL + "        \t\t";
  protected final String TEXT_402 = NL + "    \t\t\tstatement_";
  protected final String TEXT_403 = ".close();";
  protected final String TEXT_404 = NL + "\t\t\t\tSystem.setProperty(";
  protected final String TEXT_405 = " ,";
  protected final String TEXT_406 = ");";
  protected final String TEXT_407 = NL + "\t\t\t\tSystem.setProperty(\"mapred.job.tracker\", ";
  protected final String TEXT_408 = ");";
  protected final String TEXT_409 = NL + "\t\t\t\tSystem.setProperty(\"";
  protected final String TEXT_410 = "\", ";
  protected final String TEXT_411 = ");";
  protected final String TEXT_412 = NL + "\t\t\t\tSystem.setProperty(\"hive.metastore.sasl.enabled\", \"true\");" + NL + "\t\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionDriverName\", ";
  protected final String TEXT_413 = ");" + NL + "" + NL + "\t\t\t\t";
  protected final String TEXT_414 = NL + "                    System.setProperty(\"hive.security.authorization.enabled\", \"false\");";
  protected final String TEXT_415 = NL + "                    System.setProperty(\"hive.security.authorization.enabled\", \"true\");";
  protected final String TEXT_416 = NL + "\t\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionURL\", ";
  protected final String TEXT_417 = ");" + NL + "\t\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionUserName\", ";
  protected final String TEXT_418 = ");" + NL + "" + NL + "        \t\t";
  protected final String TEXT_419 = NL + NL + "        \t\t";
  protected final String TEXT_420 = NL + "            \tString decryptedMetastorePassword_";
  protected final String TEXT_421 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_422 = ");" + NL + "       \t\t \t";
  protected final String TEXT_423 = NL + "            \tString decryptedMetastorePassword_";
  protected final String TEXT_424 = " = ";
  protected final String TEXT_425 = ";" + NL + "    \t\t\t";
  protected final String TEXT_426 = NL + NL + "\t\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionPassword\", decryptedMetastorePassword_";
  protected final String TEXT_427 = ");" + NL + "\t\t\t\tSystem.setProperty(\"hive.metastore.kerberos.principal\", ";
  protected final String TEXT_428 = ");";
  protected final String TEXT_429 = NL + "                \tSystem.setProperty(\"hive.server2.authentication.kerberos.principal\", ";
  protected final String TEXT_430 = ");" + NL + "               \t\tSystem.setProperty(\"hive.server2.authentication.kerberos.keytab\", ";
  protected final String TEXT_431 = ");";
  protected final String TEXT_432 = NL + "\t\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_433 = ", ";
  protected final String TEXT_434 = ");";
  protected final String TEXT_435 = NL + "\t            String username_";
  protected final String TEXT_436 = " = ";
  protected final String TEXT_437 = ";" + NL + "\t            if(username_";
  protected final String TEXT_438 = "!=null && !\"\".equals(username_";
  protected final String TEXT_439 = ".trim())) {" + NL + "\t                System.setProperty(\"HADOOP_USER_NAME\",username_";
  protected final String TEXT_440 = ");" + NL + "\t            }" + NL + "\t            ";
  protected final String TEXT_441 = NL + "\t\t\tglobalMap.put(\"HADOOP_USER_NAME_";
  protected final String TEXT_442 = "\", System.getProperty(\"HADOOP_USER_NAME\"));";
  protected final String TEXT_443 = NL + "\t\t\t\tSystem.setProperty(\"hive.metastore.local\", \"false\");" + NL + "\t\t\t\tSystem.setProperty(\"hive.metastore.uris\", \"thrift://\" + ";
  protected final String TEXT_444 = " + \":\" + ";
  protected final String TEXT_445 = ");" + NL + "\t\t\t\tSystem.setProperty(\"hive.metastore.execute.setugi\", \"true\");" + NL + "\t\t\t\tString url_";
  protected final String TEXT_446 = " = \"jdbc:";
  protected final String TEXT_447 = "://\";";
  protected final String TEXT_448 = NL + "    \t\t\t\tif(dbUser_";
  protected final String TEXT_449 = "!=null && !\"\".equals(dbUser_";
  protected final String TEXT_450 = ".trim())) {" + NL + "    \t\t\t\t\tSystem.setProperty(\"HADOOP_USER_NAME\",dbUser_";
  protected final String TEXT_451 = ");" + NL + "    \t\t\t\t\t//make relative file path work for hive" + NL + "    \t\t\t\t\tglobalMap.put(\"current_client_user_name\", System.getProperty(\"user.name\"));" + NL + "    \t\t\t\t\tSystem.setProperty(\"user.name\",dbUser_";
  protected final String TEXT_452 = ");" + NL + "    \t\t\t\t}";
  protected final String TEXT_453 = NL + "\t\t\t            System.setProperty(\"pname\", \"MapRLogin\");" + NL + "\t\t\t            System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "\t\t\t            System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_454 = ");" + NL + "\t\t\t            System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_455 = ");" + NL + "\t\t\t            ";
  protected final String TEXT_456 = NL + "\t\t\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_457 = ", ";
  protected final String TEXT_458 = ");";
  protected final String TEXT_459 = NL + "\t\t\t            com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_460 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "\t\t\t            maprLogin_";
  protected final String TEXT_461 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_462 = ", ";
  protected final String TEXT_463 = ");" + NL + "\t\t\t            ";
  protected final String TEXT_464 = NL + "\t\t\t\t\t\t\t\tString decryptedSslStorePassword_";
  protected final String TEXT_465 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_466 = ");";
  protected final String TEXT_467 = NL + "\t\t\t\t\t\t\t\tString decryptedSslStorePassword_";
  protected final String TEXT_468 = " = ";
  protected final String TEXT_469 = ";";
  protected final String TEXT_470 = NL + "\t\t\t\t\t\t\tString url_";
  protected final String TEXT_471 = " = \"jdbc:";
  protected final String TEXT_472 = "://\" + ";
  protected final String TEXT_473 = " + \":\" + ";
  protected final String TEXT_474 = " + \"/\" + ";
  protected final String TEXT_475 = " + \";principal=\" + ";
  protected final String TEXT_476 = "+\";ssl=true\" +\";sslTrustStore=\" + ";
  protected final String TEXT_477 = " + \";trustStorePassword=\" + decryptedSslStorePassword_";
  protected final String TEXT_478 = ";";
  protected final String TEXT_479 = NL + "\t\t\t\t\t\t\tString url_";
  protected final String TEXT_480 = " = \"jdbc:";
  protected final String TEXT_481 = "://\" + ";
  protected final String TEXT_482 = " + \":\" + ";
  protected final String TEXT_483 = " + \"/\" + ";
  protected final String TEXT_484 = " + \";principal=\" + ";
  protected final String TEXT_485 = "+\";sasl.qop=auth-conf\";";
  protected final String TEXT_486 = NL + "\t\t\t\t\t\tString url_";
  protected final String TEXT_487 = " = \"jdbc:";
  protected final String TEXT_488 = "://\" + ";
  protected final String TEXT_489 = " + \":\" + ";
  protected final String TEXT_490 = " + \"/\" + ";
  protected final String TEXT_491 = " + \";principal=\" + ";
  protected final String TEXT_492 = ";";
  protected final String TEXT_493 = NL + "                        System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                        System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                        System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_494 = ");" + NL + "                        com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_495 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_496 = NL + "                            System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_497 = ");";
  protected final String TEXT_498 = NL + "                            maprLogin_";
  protected final String TEXT_499 = ".setCheckUGI(false);";
  protected final String TEXT_500 = NL + "                            String decryptedMaprPassword_";
  protected final String TEXT_501 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_502 = ");";
  protected final String TEXT_503 = NL + "                            String decryptedMaprPassword_";
  protected final String TEXT_504 = " = ";
  protected final String TEXT_505 = ";";
  protected final String TEXT_506 = NL + "                        \tmaprLogin_";
  protected final String TEXT_507 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_508 = ", ";
  protected final String TEXT_509 = ", decryptedMaprPassword_";
  protected final String TEXT_510 = ", ";
  protected final String TEXT_511 = ", \"\");" + NL + "                        \t";
  protected final String TEXT_512 = NL + "                        \tmaprLogin_";
  protected final String TEXT_513 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_514 = ", ";
  protected final String TEXT_515 = ", decryptedMaprPassword_";
  protected final String TEXT_516 = ", ";
  protected final String TEXT_517 = ");" + NL + "                        \t";
  protected final String TEXT_518 = NL + "\t\t\t\t\t\t\tString decryptedSslStorePassword_";
  protected final String TEXT_519 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_520 = ");";
  protected final String TEXT_521 = NL + "\t\t\t\t\t\t\tString decryptedSslStorePassword_";
  protected final String TEXT_522 = " = ";
  protected final String TEXT_523 = ";";
  protected final String TEXT_524 = NL + "\t\t\t\t\t\tString url_";
  protected final String TEXT_525 = " = \"jdbc:";
  protected final String TEXT_526 = "://\" + ";
  protected final String TEXT_527 = " + \":\" + ";
  protected final String TEXT_528 = " + \"/\" + ";
  protected final String TEXT_529 = "+ \";ssl=true\" +\";sslTrustStore=\" + ";
  protected final String TEXT_530 = " + \";trustStorePassword=\" + decryptedSslStorePassword_";
  protected final String TEXT_531 = ";";
  protected final String TEXT_532 = NL + "\t\t\t\t\t\tString url_";
  protected final String TEXT_533 = " = \"jdbc:";
  protected final String TEXT_534 = "://\" + ";
  protected final String TEXT_535 = " + \":\" + ";
  protected final String TEXT_536 = " + \"/\" + ";
  protected final String TEXT_537 = ";";
  protected final String TEXT_538 = NL + "\t\t\t\tString additionalJdbcSettings_";
  protected final String TEXT_539 = " = ";
  protected final String TEXT_540 = ";" + NL + "\t\t\t\tif(!\"\".equals(additionalJdbcSettings_";
  protected final String TEXT_541 = ".trim())) {" + NL + "\t\t\t\t\tif(!additionalJdbcSettings_";
  protected final String TEXT_542 = ".startsWith(\";\")) {" + NL + "\t\t\t\t\t\tadditionalJdbcSettings_";
  protected final String TEXT_543 = " = \";\" + additionalJdbcSettings_";
  protected final String TEXT_544 = ";" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\turl_";
  protected final String TEXT_545 = " += additionalJdbcSettings_";
  protected final String TEXT_546 = ";" + NL + "\t\t\t\t}";
  protected final String TEXT_547 = NL + "\t\t\t\tif(true) {" + NL + "\t\t\t\t\tthrow new java.lang.UnsupportedOperationException(\"Parquet is only supported if the distribution uses embedded Hive version 0.10 or later.\");" + NL + "\t\t\t\t}";
  protected final String TEXT_548 = NL + "\t\t\t\t\troutines.system.GetJarsToRegister getJarsToRegister_";
  protected final String TEXT_549 = " = new routines.system.GetJarsToRegister();";
  protected final String TEXT_550 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_551 = NL + "\tclass GetHiveJarsToRegister_";
  protected final String TEXT_552 = " extends routines.system.GetJarsToRegister {" + NL + "\t\t" + NL + "\t\tprivate String uploadJarToHDFS(String jar) throws Exception {" + NL + "\t\t\t";
  protected final String TEXT_553 = NL;
  protected final String TEXT_554 = NL + "        String username_";
  protected final String TEXT_555 = " = (";
  protected final String TEXT_556 = " != null && !\"\".equals(";
  protected final String TEXT_557 = ")) ? ";
  protected final String TEXT_558 = " : System.getProperty(\"user.name\");;" + NL + "        org.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_559 = " = null;";
  protected final String TEXT_560 = NL + "        org.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_561 = " = new org.apache.hadoop.conf.Configuration(); " + NL + "        conf_";
  protected final String TEXT_562 = ".set(\"";
  protected final String TEXT_563 = "\", ";
  protected final String TEXT_564 = ");" + NL + "        ";
  protected final String TEXT_565 = NL + "            conf_";
  protected final String TEXT_566 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_567 = NL + "        \t\tconf_";
  protected final String TEXT_568 = ".set(";
  protected final String TEXT_569 = " ,";
  protected final String TEXT_570 = ");" + NL + "        \t";
  protected final String TEXT_571 = NL + "            username_";
  protected final String TEXT_572 = " = null;" + NL + "    \t\tconf_";
  protected final String TEXT_573 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_574 = ");" + NL + "    \t\t";
  protected final String TEXT_575 = NL + "    \t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_576 = ", ";
  protected final String TEXT_577 = ");" + NL + "    \t\t";
  protected final String TEXT_578 = NL + "\t\t\tconf_";
  protected final String TEXT_579 = ".set(\"hadoop.job.ugi\",username_";
  protected final String TEXT_580 = "+\",\"+username_";
  protected final String TEXT_581 = ");" + NL + "        \tfs_";
  protected final String TEXT_582 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_583 = ");";
  protected final String TEXT_584 = NL + "        \t" + NL + "        \tif(username_";
  protected final String TEXT_585 = " == null || \"\".equals(username_";
  protected final String TEXT_586 = ")){" + NL + "        \t\tfs_";
  protected final String TEXT_587 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_588 = ");" + NL + "        \t}else{" + NL + "        \t\tfs_";
  protected final String TEXT_589 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_590 = ".get(\"";
  protected final String TEXT_591 = "\")),conf_";
  protected final String TEXT_592 = ",username_";
  protected final String TEXT_593 = ");" + NL + "        \t}\t";
  protected final String TEXT_594 = NL + "\t\t\t";
  protected final String TEXT_595 = NL + "\t\t\t" + NL + "\t\t\tString pathUsername_";
  protected final String TEXT_596 = " = username_";
  protected final String TEXT_597 = " == null ? org.apache.hadoop.security.UserGroupInformation.getLoginUser().getShortUserName() : username_";
  protected final String TEXT_598 = ";" + NL + "\t\t\tfs_";
  protected final String TEXT_599 = ".mkdirs(new org.apache.hadoop.fs.Path(\"/user/\" + pathUsername_";
  protected final String TEXT_600 = " + \"/tmp\"), new org.apache.hadoop.fs.permission.FsPermission(org.apache.hadoop.fs.permission.FsAction.ALL, org.apache.hadoop.fs.permission.FsAction.ALL, org.apache.hadoop.fs.permission.FsAction.ALL));" + NL + "\t\t\tfs_";
  protected final String TEXT_601 = ".copyFromLocalFile(false, true, new org.apache.hadoop.fs.Path(jar), new org.apache.hadoop.fs.Path(\"/user/\" + pathUsername_";
  protected final String TEXT_602 = " + \"/tmp\"));" + NL + "\t\t\treturn ";
  protected final String TEXT_603 = " + \"/user/\" + pathUsername_";
  protected final String TEXT_604 = " + \"/tmp/\" + new java.io.File(jar).getName();" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tpublic String replaceJarPaths(String originalClassPathLine) throws Exception {" + NL + "\t\t\tString classPathLine = super.replaceJarPaths(originalClassPathLine);" + NL + "\t\t\tString hdfsPath = uploadJarToHDFS(classPathLine);" + NL + "\t\t\treturn hdfsPath;" + NL + "\t\t}" + NL + "\t}" + NL + "\t\t\t\t\t\tGetHiveJarsToRegister_";
  protected final String TEXT_605 = " getJarsToRegister_";
  protected final String TEXT_606 = " = new GetHiveJarsToRegister_";
  protected final String TEXT_607 = "();";
  protected final String TEXT_608 = NL + "\t\t\t\t\tjava.sql.Statement addJar_";
  protected final String TEXT_609 = " = null;";
  protected final String TEXT_610 = NL + "\t\t\t\t\t\t\t\taddJar_";
  protected final String TEXT_611 = " = conn_";
  protected final String TEXT_612 = ".createStatement();" + NL + "\t\t\t\t\t\t\t\ttry {" + NL + "\t\t\t\t\t\t\t\t\taddJar_";
  protected final String TEXT_613 = ".execute(\"add jar \" + getJarsToRegister_";
  protected final String TEXT_614 = ".replaceJarPaths(\"";
  protected final String TEXT_615 = "\"));" + NL + "\t\t\t\t\t\t\t\t} catch (Exception e) {" + NL + "\t\t\t\t\t\t\t\t\te.printStackTrace();" + NL + "\t\t\t\t\t\t\t\t} finally {" + NL + "\t\t\t\t\t\t\t\t\taddJar_";
  protected final String TEXT_616 = ".close();" + NL + "\t\t\t\t\t\t\t\t}";
  protected final String TEXT_617 = NL + "\tSystem.setProperty(\"org.apache.commons.logging.Log\", \"org.apache.commons.logging.impl.NoOpLog\");";
  protected final String TEXT_618 = NL + "\t\tSystem.setProperty(\"java.io.tmpdir\", ";
  protected final String TEXT_619 = ");";
  protected final String TEXT_620 = NL + "\tglobalMap.put(\"current_client_path_separator\", System.getProperty(\"path.separator\"));" + NL + "\tSystem.setProperty(\"path.separator\", ";
  protected final String TEXT_621 = ");" + NL + "" + NL + "\t";
  protected final String TEXT_622 = NL + "\t\t    int nb_line_";
  protected final String TEXT_623 = " = 0;" + NL + "\t\t    java.sql.Connection conn_";
  protected final String TEXT_624 = " = null;";
  protected final String TEXT_625 = NL + "\t\t        conn_";
  protected final String TEXT_626 = " = (java.sql.Connection)globalMap.get(\"";
  protected final String TEXT_627 = "\");" + NL + "\t\t\t\t";
  protected final String TEXT_628 = NL + "\t\t\t\t";
  protected final String TEXT_629 = NL + "             boolean currentAutoCommitMode";
  protected final String TEXT_630 = " =  conn_";
  protected final String TEXT_631 = ".getAutoCommit();" + NL + "             conn_";
  protected final String TEXT_632 = ".setAutoCommit(true);" + NL + "             try{";
  protected final String TEXT_633 = NL + "                           try{" + NL + "                               conn_";
  protected final String TEXT_634 = ".createStatement().execute(\"BEGIN QUERY LOGGING WITH SQL,OBJECTS ON \" + ";
  protected final String TEXT_635 = ");" + NL + "                           }catch(Exception e){" + NL + "                                 System.err.println(e.getMessage());" + NL + "                           }";
  protected final String TEXT_636 = NL + "                 conn_";
  protected final String TEXT_637 = ".createStatement().execute(" + NL + "              \"SET QUERY_BAND='\" +";
  protected final String TEXT_638 = NL + "               ";
  protected final String TEXT_639 = " + \"=\" + ";
  protected final String TEXT_640 = " + \";\" +";
  protected final String TEXT_641 = " \"' FOR SESSION\"" + NL + "           );" + NL + "              }catch(Exception e){" + NL + "               System.err.println(e.getMessage());" + NL + "      }" + NL + "      conn_";
  protected final String TEXT_642 = ".setAutoCommit(currentAutoCommitMode";
  protected final String TEXT_643 = ");";
  protected final String TEXT_644 = NL + "\t\t    ";
  protected final String TEXT_645 = NL + NL + "\t\t    String dbquery_";
  protected final String TEXT_646 = " = ";
  protected final String TEXT_647 = ";" + NL + "\t\t\t";
  protected final String TEXT_648 = NL + "                log.debug(\"";
  protected final String TEXT_649 = " - Executing the query: '\"+dbquery_";
  protected final String TEXT_650 = "+\"'.\");" + NL + "\t\t\t";
  protected final String TEXT_651 = NL;
  protected final String TEXT_652 = NL + "                       globalMap.put(\"";
  protected final String TEXT_653 = "_QUERY\",dbquery_";
  protected final String TEXT_654 = ");";
  protected final String TEXT_655 = NL + "                       globalMap.put(\"";
  protected final String TEXT_656 = "_QUERY\",dbquery_";
  protected final String TEXT_657 = ");";
  protected final String TEXT_658 = NL + NL + "\t\t    java.sql.ResultSet rs_";
  protected final String TEXT_659 = " = null;" + NL + "\t\ttry{" + NL + "\t\t    rs_";
  protected final String TEXT_660 = " = stmt_";
  protected final String TEXT_661 = ".executeQuery(dbquery_";
  protected final String TEXT_662 = ");" + NL + "\t\t    java.sql.ResultSetMetaData rsmd_";
  protected final String TEXT_663 = " = rs_";
  protected final String TEXT_664 = ".getMetaData();" + NL + "\t\t    int colQtyInRs_";
  protected final String TEXT_665 = " = rsmd_";
  protected final String TEXT_666 = ".getColumnCount();" + NL;
  protected final String TEXT_667 = NL + "\t\t    routines.system.Dynamic dcg_";
  protected final String TEXT_668 = " =  new routines.system.Dynamic();" + NL + "\t\t    dcg_";
  protected final String TEXT_669 = ".setDbmsId(\"";
  protected final String TEXT_670 = "\");" + NL + "\t\t    List<String> listSchema_";
  protected final String TEXT_671 = "=new java.util.ArrayList<String>();" + NL + "\t\t    ";
  protected final String TEXT_672 = NL + "\t\t    \t    listSchema_";
  protected final String TEXT_673 = ".add(\"";
  protected final String TEXT_674 = "\");" + NL + "\t    \t    ";
  protected final String TEXT_675 = NL + NL + "\t\t\tint fixedColumnCount_";
  protected final String TEXT_676 = " = ";
  protected final String TEXT_677 = ";" + NL + "" + NL + "            for (int i = ";
  protected final String TEXT_678 = "; i <= rsmd_";
  protected final String TEXT_679 = ".getColumnCount()-";
  protected final String TEXT_680 = "; i++) {" + NL + "                if (!(listSchema_";
  protected final String TEXT_681 = ".contains(rsmd_";
  protected final String TEXT_682 = ".getColumnLabel(i).toUpperCase()) )) {" + NL + "                \troutines.system.DynamicMetadata dcm_";
  protected final String TEXT_683 = "=new routines.system.DynamicMetadata();" + NL + "                \tdcm_";
  protected final String TEXT_684 = ".setName(rsmd_";
  protected final String TEXT_685 = ".getColumnLabel(i));" + NL + "                \tdcm_";
  protected final String TEXT_686 = ".setDbName(rsmd_";
  protected final String TEXT_687 = ".getColumnName(i));" + NL + "                \tdcm_";
  protected final String TEXT_688 = ".setType(routines.system.Dynamic.getTalendTypeFromDBType(\"";
  protected final String TEXT_689 = "\", rsmd_";
  protected final String TEXT_690 = ".getColumnTypeName(i).toUpperCase(), rsmd_";
  protected final String TEXT_691 = ".getPrecision(i), rsmd_";
  protected final String TEXT_692 = ".getScale(i)));" + NL + "                \tdcm_";
  protected final String TEXT_693 = ".setDbType(rsmd_";
  protected final String TEXT_694 = ".getColumnTypeName(i));" + NL + "                \tdcm_";
  protected final String TEXT_695 = ".setDbTypeId(rsmd_";
  protected final String TEXT_696 = ".getColumnType(i));";
  protected final String TEXT_697 = NL + "                \tdcm_";
  protected final String TEXT_698 = ".setFormat(";
  protected final String TEXT_699 = ");";
  protected final String TEXT_700 = NL + "\t\t\tif(\"LONG\".equals(rsmd_";
  protected final String TEXT_701 = ".getColumnTypeName(i).toUpperCase())) {" + NL + "\t\t\t\tString length = MetadataTalendType.getDefaultDBTypes(\"oracle_id\", \"LONG\", MetadataTalendType.DEFAULT_LENGTH);" + NL + "\t\t\t\tif(length!=null && !(\"\".equals(length))) {" + NL + "\t\t\t\t\tdcm_";
  protected final String TEXT_702 = ".setLength(Integer.parseInt(length));" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tdcm_";
  protected final String TEXT_703 = ".setLength(rsmd_";
  protected final String TEXT_704 = ".getPrecision(i));" + NL + "\t\t\t\t}" + NL + "\t\t\t} else {" + NL + "\t\t\t\tdcm_";
  protected final String TEXT_705 = ".setLength(rsmd_";
  protected final String TEXT_706 = ".getPrecision(i));" + NL + "\t\t\t}";
  protected final String TEXT_707 = NL + "\t\t\tdcm_";
  protected final String TEXT_708 = ".setLength(rsmd_";
  protected final String TEXT_709 = ".getPrecision(i));";
  protected final String TEXT_710 = NL + "                \tdcm_";
  protected final String TEXT_711 = ".setPrecision(rsmd_";
  protected final String TEXT_712 = ".getScale(i));" + NL + "                \tdcm_";
  protected final String TEXT_713 = ".setNullable(rsmd_";
  protected final String TEXT_714 = ".isNullable(i) == 0 ? false : true);" + NL + "                \tdcm_";
  protected final String TEXT_715 = ".setKey(false);" + NL + "                \tdcm_";
  protected final String TEXT_716 = ".setSourceType(DynamicMetadata.sourceTypes.database);" + NL + "                \tdcm_";
  protected final String TEXT_717 = ".setColumnPosition(i);" + NL + "                \tdcg_";
  protected final String TEXT_718 = ".metadatas.add(dcm_";
  protected final String TEXT_719 = ");" + NL + "                }" + NL + "            }";
  protected final String TEXT_720 = NL + "\t\t    String tmpContent_";
  protected final String TEXT_721 = " = null;" + NL + "\t\t    ";
  protected final String TEXT_722 = NL + "\t\t    \tint column_index_";
  protected final String TEXT_723 = " =1;" + NL + "\t\t    ";
  protected final String TEXT_724 = NL + "\t\t    ";
  protected final String TEXT_725 = NL + "\t\t    \tlog.debug(\"";
  protected final String TEXT_726 = " - Retrieving records from the database.\");" + NL + "\t\t    ";
  protected final String TEXT_727 = NL + "\t\t    while (rs_";
  protected final String TEXT_728 = ".next()) {" + NL + "\t\t        nb_line_";
  protected final String TEXT_729 = "++;" + NL + "\t\t        ";
  protected final String TEXT_730 = NL + "\t\t\t\t\t\t\t\t\tcolumn_index_";
  protected final String TEXT_731 = " = ";
  protected final String TEXT_732 = " + dcg_";
  protected final String TEXT_733 = ".getColumnCount();" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_734 = NL + "\t\t\t\t\t\t\t\t\tcolumn_index_";
  protected final String TEXT_735 = " = ";
  protected final String TEXT_736 = ";" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_737 = NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_738 = NL + "\t\t\t\t\t\t\tif(colQtyInRs_";
  protected final String TEXT_739 = " < ";
  protected final String TEXT_740 = "column_index_";
  protected final String TEXT_741 = ") {" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_742 = ".";
  protected final String TEXT_743 = " = ";
  protected final String TEXT_744 = ";" + NL + "\t\t\t\t\t\t\t} else {";
  protected final String TEXT_745 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_746 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_747 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_748 = NL + "\t\t                            ";
  protected final String TEXT_749 = ".";
  protected final String TEXT_750 = " = (List)rs_";
  protected final String TEXT_751 = ".getObject(";
  protected final String TEXT_752 = "column_index_";
  protected final String TEXT_753 = ");";
  protected final String TEXT_754 = NL + "\t                         \t\t";
  protected final String TEXT_755 = NL + "\t                         \t\t";
  protected final String TEXT_756 = NL + "\t                         \t\t";
  protected final String TEXT_757 = NL + "                                    oracle.sql.STRUCT jGeomStruct = (oracle.sql.STRUCT) rs_";
  protected final String TEXT_758 = ".getObject(";
  protected final String TEXT_759 = "column_index_";
  protected final String TEXT_760 = ");" + NL + "                                    if (jGeomStruct != null) {" + NL + "                                    oracle.spatial.geometry.JGeometry jGeom = oracle.spatial.geometry.JGeometry.load(jGeomStruct);" + NL + "                                    oracle.spatial.util.WKT wkt = new oracle.spatial.util.WKT();" + NL + "                                    String wktValue = new String(wkt.fromJGeometry(jGeom));" + NL;
  protected final String TEXT_761 = NL + "                                    ";
  protected final String TEXT_762 = ".";
  protected final String TEXT_763 = " = new Geometry(wktValue);" + NL + "                                    } else {";
  protected final String TEXT_764 = NL + "                                      ";
  protected final String TEXT_765 = ".";
  protected final String TEXT_766 = " = null;" + NL + "                                    }";
  protected final String TEXT_767 = NL + "                                        if (";
  protected final String TEXT_768 = ".";
  protected final String TEXT_769 = " != null) {";
  protected final String TEXT_770 = NL + "                                        ";
  protected final String TEXT_771 = ".";
  protected final String TEXT_772 = ".setEPSG(";
  protected final String TEXT_773 = ");" + NL + "                                        }";
  protected final String TEXT_774 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_775 = NL + "                                  ";
  protected final String TEXT_776 = ".";
  protected final String TEXT_777 = "=dcg_";
  protected final String TEXT_778 = ";";
  protected final String TEXT_779 = NL + "\t\t\t\t\t\t\t\t\t\tList<String> list_";
  protected final String TEXT_780 = " = new java.util.ArrayList<String>();" + NL + "\t\t\t\t\t    \t\t\t\tfor(int i_";
  protected final String TEXT_781 = " = ";
  protected final String TEXT_782 = "; i_";
  protected final String TEXT_783 = "  <= rsmd_";
  protected final String TEXT_784 = ".getColumnCount(); i_";
  protected final String TEXT_785 = " ++){" + NL + "\t\t\t\t\t    \t\t\t\t\t";
  protected final String TEXT_786 = NL + "\t\t\t\t\t\t \t\t\t\t\tif (\"NTEXT\".equals(rsmd_";
  protected final String TEXT_787 = ".getColumnTypeName(i_";
  protected final String TEXT_788 = " ).toUpperCase())) {" + NL + "\t\t\t\t\t\t\t\t\t\t\t\tnet.sourceforge.jtds.jdbc.ClobImpl clob_";
  protected final String TEXT_789 = " = (net.sourceforge.jtds.jdbc.ClobImpl) rs_";
  protected final String TEXT_790 = ".getClob(i_";
  protected final String TEXT_791 = ");" + NL + "\t\t\t\t\t\t\t\t\t\t\t\tif(clob_";
  protected final String TEXT_792 = "!=null){" + NL + "\t\t\t\t\t\t\t\t\t\t\t\t\tnet.sourceforge.jtds.jdbc.TalendNTextImpl tNTextImpl_";
  protected final String TEXT_793 = " = new net.sourceforge.jtds.jdbc.TalendNTextImpl(clob_";
  protected final String TEXT_794 = ");" + NL + "\t\t\t\t\t\t\t  \t\t\t\t\t\tlist_";
  protected final String TEXT_795 = ".add(tNTextImpl_";
  protected final String TEXT_796 = ".getValue());" + NL + "\t\t\t\t\t\t\t\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\t\t\t\t\t\t\t\tlist_";
  protected final String TEXT_797 = ".add(null);" + NL + "\t\t\t\t\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t   \t\t\t\t\t}" + NL + "\t\t\t\t\t\t   \t\t\t\t\t";
  protected final String TEXT_798 = " " + NL + "\t\t\t\t\t\t   \t\t\t\t\tif(\"NTEXT\".equals(rsmd_";
  protected final String TEXT_799 = ".getColumnTypeName(i_";
  protected final String TEXT_800 = " ).toUpperCase())) {" + NL + "\t\t\t\t\t\t   \t\t\t\t\t\tlist_";
  protected final String TEXT_801 = ".add(rs_";
  protected final String TEXT_802 = ".getString(i_";
  protected final String TEXT_803 = "));" + NL + "\t\t\t\t\t\t   \t\t\t\t\t}" + NL + "\t\t\t\t\t\t   \t\t\t\t\t";
  protected final String TEXT_804 = NL + "   \t \t\t\t\t    \t\t\t\t}" + NL + "                                 \t\t routines.system.DynamicUtils.readColumnsFromDatabase_Mssql(";
  protected final String TEXT_805 = ".";
  protected final String TEXT_806 = ", rs_";
  protected final String TEXT_807 = ", fixedColumnCount_";
  protected final String TEXT_808 = ",list_";
  protected final String TEXT_809 = ",";
  protected final String TEXT_810 = ");";
  protected final String TEXT_811 = NL + "\t\t\t\t\t\t\t\t\t\troutines.system.DynamicUtils.readColumnsFromDatabase_Access(";
  protected final String TEXT_812 = ".";
  protected final String TEXT_813 = ", rs_";
  protected final String TEXT_814 = ", fixedColumnCount_";
  protected final String TEXT_815 = ",";
  protected final String TEXT_816 = ");";
  protected final String TEXT_817 = NL + "                                \t\t routines.system.DynamicUtils.readColumnsFromDatabase(";
  protected final String TEXT_818 = ".";
  protected final String TEXT_819 = ", rs_";
  protected final String TEXT_820 = ", fixedColumnCount_";
  protected final String TEXT_821 = ",";
  protected final String TEXT_822 = ");";
  protected final String TEXT_823 = NL + "                                  Object geom = rs_";
  protected final String TEXT_824 = ".getObject(";
  protected final String TEXT_825 = "column_index_";
  protected final String TEXT_826 = ");" + NL + "                                  if (geom != null) {" + NL + "                                  \torg.postgis.Geometry o =" + NL + "                                      org.postgis.PGgeometry.geomFromString(geom.toString());" + NL + "                                \tStringBuffer sb = new StringBuffer();" + NL + "                                \to.outerWKT(sb, false);" + NL + "                                \t";
  protected final String TEXT_827 = ".";
  protected final String TEXT_828 = " = new Geometry(sb.toString());" + NL + "                                  } else {" + NL + "                                  \t";
  protected final String TEXT_829 = ".";
  protected final String TEXT_830 = " = null;" + NL + "                                  }";
  protected final String TEXT_831 = NL + "\t\t                          ";
  protected final String TEXT_832 = NL + "\t\t                    }";
  protected final String TEXT_833 = NL + "\t\t\t\t\t";
  protected final String TEXT_834 = NL + "\t\t\t\t\t\tlog.debug(\"";
  protected final String TEXT_835 = " - Retrieving the record \" + nb_line_";
  protected final String TEXT_836 = " + \".\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_837 = NL + "\t\t                            ";
  protected final String TEXT_838 = ".";
  protected final String TEXT_839 = " = ";
  protected final String TEXT_840 = ".";
  protected final String TEXT_841 = ";" + NL + "\t\t                            ";
  protected final String TEXT_842 = NL;

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
			
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    if (cid.startsWith("tImpala") || cid.startsWith("tHive")) {
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(connection );
    stringBuffer.append(TEXT_35);
    } else {
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    }
    stringBuffer.append(TEXT_40);
    
			}
		}

		public void connect(INode node){
			beforeComponentProcess(node);
			connect();
		}

		public void connect(){
			connect_begin();
			
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    
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
			
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    
			logInfo(node,"debug",cid+" - Connection "+connection+"rollback has succeeded.");
		}

		public void commit(INode node){
			beforeComponentProcess(node);
			commit();
		}

		private void commit(){
			commit_begin();
			
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    
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
			
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    
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
			
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(autoCommit);
    stringBuffer.append(TEXT_54);
    
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
	    	
    stringBuffer.append(TEXT_55);
    stringBuffer.append(logLevel);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(exception);
    stringBuffer.append(TEXT_58);
    
			}
	    }

	    public void logError(INode node,String logLevel){
	    	logError(node,logLevel,"e");
	    }
	    
	    public void logInfo(INode node,String logLevel,String message){
	    	beforeComponentProcess(node);
	    	if(isLog4jEnabled){
	    	
    stringBuffer.append(TEXT_59);
    stringBuffer.append(logLevel);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(message);
    stringBuffer.append(TEXT_61);
    
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
			
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_63);
    
			}else if(batchType==2){
				boolean isMysqlBatchInsert = false;
				if ((node.getUniqueName().contains("tMysqlOutput")||node.getUniqueName().contains("tAmazonMysqlOutput")) && ("INSERT").equals(dataAction)) {
					isMysqlBatchInsert = true;
				}
			
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(isMysqlBatchInsert? "1" : "countEach_"+cid );
    stringBuffer.append(TEXT_70);
    
			}
			if(logBatch){
				logInfo(node,"debug",cid+" - The "+dataAction+" batch execution has succeeded.");
			}
		}
	}

	DefaultLog4jCodeGenerateUtil log4jCodeGenerateUtil = new DefaultLog4jCodeGenerateUtil();

    
	//this util class use by connection component
	class DefaultDBInputUtil {
	
		protected String cid ;
		protected String dbproperties ;
		protected String dbhost;
		protected String dbport;
		protected String dbname;
		protected String dbuser;
		protected String dbpwd ;
		protected boolean isLog4jEnabled;
		protected boolean isDynamic;
		protected DefaultLog4jCodeGenerateUtil log4jCodeGenerateUtil;

		public void beforeComponentProcess(INode node){
			cid = node.getUniqueName();
			List<IMetadataTable> metadatas = node.getMetadataList();
			if ((metadatas != null) && (metadatas.size()>0)) {
				IMetadataTable metadata = metadatas.get(0);
				if (metadata != null) {
					isDynamic = metadata.isDynamicSchema();
				}
			}
			log4jCodeGenerateUtil = new DefaultLog4jCodeGenerateUtil();
		}

		public void afterUseExistConnection(INode node) {
		}
		
		public String getDirverClassName(INode node){
			return "";
		}
	    
	    public void afterUseNewConnection(INode node) {
	    }
	    
	    public void setURL(INode node) {
	    }
	    
		public void createConnection(INode node) {
			this.createConnection(node, true);
		}

		public void createConnection(INode node, boolean needUserAndPassword) {
			cid = node.getUniqueName();
			dbproperties = ElementParameterParser.getValue(node, "__PROPERTIES__");
			dbhost = ElementParameterParser.getValue(node, "__HOST__");
	    	dbport = ElementParameterParser.getValue(node, "__PORT__");
	    	dbname = ElementParameterParser.getValue(node, "__DBNAME__");
	    	dbuser = ElementParameterParser.getValue(node, "__USER__");

	 		boolean specify_alias = "true".equals(ElementParameterParser.getValue(node, "__SPECIFY_DATASOURCE_ALIAS__"));
 		if(specify_alias){

    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    
                String alias = ElementParameterParser.getValue(node, "__DATASOURCE_ALIAS__");

    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append((null != alias && !("".equals(alias)))?alias:"\"\"");
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    
		}

    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(this.getDirverClassName(node) );
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_87);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_88);
    
        		String passwordFieldName = "__PASS__";
        		
    stringBuffer.append(TEXT_89);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_92);
    } else {
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_95);
    }
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    this.setURL(node);
    stringBuffer.append(TEXT_99);
    
				log4jCodeGenerateUtil.debugConnectionParams(node);
				log4jCodeGenerateUtil.connect_begin();
				
    
			if(needUserAndPassword) {

    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    
			} else {

    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_107);
    
			}
			log4jCodeGenerateUtil.connect_end();

    stringBuffer.append(TEXT_108);
    this.afterUseNewConnection(node);
    
			if(specify_alias){

    stringBuffer.append(TEXT_109);
    
			}
		}
		
		public String getQueryString(INode node) {
			String dbquery= ElementParameterParser.getValue(node, "__QUERY__");
			dbquery = org.talend.core.model.utils.NodeUtil.replaceCRLFInMEMO_SQL(dbquery);
			
			return dbquery;
		}
		
		public void createStatement(INode node) {

    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    
		}
		public void createMinValueStatement(INode node){

    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    }
		public String mappingType(String typeToGenerate) {
		
            if(("byte[]").equals(typeToGenerate)) {
                return "Bytes";
            } else if(("java.util.Date").equals(typeToGenerate)) {
                return "Timestamp";
            } else if(("Integer").equals(typeToGenerate)) {
               return "Int";
            } else {
                return typeToGenerate.substring(0,1).toUpperCase()+typeToGenerate.substring(1);
            }
		}
		//-----------according schema type to generate ResultSet
		public void generateStringCharAndCharacterSet(String firstConnName, IMetadataColumn column, int currentColNo,
					String trimMethod, String typeToGenerate, boolean whetherTrimAllCol, boolean whetherTrimCol) {

    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_118);
    if(isDynamic){
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_120);
    
                    if(whetherTrimAllCol || whetherTrimCol) {

    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(trimMethod);
    stringBuffer.append(TEXT_124);
    
                    }

    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    
                        if(("Character").equals(typeToGenerate)) {

    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(TEXT_134);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_136);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_139);
    
                        } else {

    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_144);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_145);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_146);
    
                        }

    stringBuffer.append(TEXT_147);
    
		}
		
	    public void generateTimestampResultSet(String firstConnName, IMetadataColumn column, int currentColNo) {

    stringBuffer.append(TEXT_148);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_151);
    if(isDynamic){
    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_153);
    
	    }
	    
	    public void generateStringResultSet(String firstConnName, IMetadataColumn column, int currentColNo, String trimMethod) {

    stringBuffer.append(TEXT_154);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_157);
    if(isDynamic){
    stringBuffer.append(TEXT_158);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_159);
    if(trimMethod==null||trimMethod.isEmpty()){
    stringBuffer.append(TEXT_160);
    } else {
    stringBuffer.append(TEXT_161);
    }
    stringBuffer.append(TEXT_162);
    
	    }
	    
	    public void generateDoubleResultSet(String firstConnName, IMetadataColumn column, int currentColNo) {
	    	generateOthersResultSet(firstConnName, column, currentColNo, "Double");
	    }
	    
	    public void generateBooleanResultSet(String firstConnName, IMetadataColumn column, int currentColNo) {
	    	generateOthersResultSet(firstConnName, column, currentColNo, "Boolean");
	    }
	    
	    public void generateBytesResultSet(String firstConnName, IMetadataColumn column, int currentColNo) {
	    }
	    
	    public void generateOthersResultSet(String firstConnName, IMetadataColumn column, int currentColNo, String typeToGenerate) {

    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_164);
    if(isDynamic){
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_166);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_168);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_169);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_170);
    stringBuffer.append(typeToGenerate);
    stringBuffer.append(TEXT_171);
    if(isDynamic){
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_173);
    
                if(column.isNullable()) {
                    
    stringBuffer.append(TEXT_174);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_176);
    
                } else {
                    
    stringBuffer.append(TEXT_177);
        
                }
                
    stringBuffer.append(TEXT_178);
    
	    }
	    //---------end according schema type to generate ResultSet
	    
		public void afterComponentProcess(INode node){
	    }
	}//end DefaultDBInputUtil class
	
	DefaultDBInputUtil dbInputBeginUtil = new DefaultDBInputUtil();
	
	

    stringBuffer.append(TEXT_179);
    
	class DBInputBeginUtil extends DefaultDBInputUtil{
		private String javaDbDriver = "org.apache.hadoop.hive.jdbc.HiveDriver";
		private String hiveServer = "hive";
		private org.talend.hadoop.distribution.component.HiveComponent hiveDistrib;
		private boolean isCustom;


		public void createConnection(INode node) {
			String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");
			String hiveVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");
			String hiveServer = ElementParameterParser.getValue(node, "__HIVE_SERVER__");

			boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
			boolean securityIsEnabled = useKrb && (this.isCustom || (this.hiveDistrib.doSupportKerberos() && (("HIVE".equalsIgnoreCase(hiveServer) && "EMBEDDED".equalsIgnoreCase(connectionMode)) || "HIVE2".equalsIgnoreCase(hiveServer))));
			boolean securedStandaloneHive2 = securityIsEnabled && "HIVE2".equalsIgnoreCase(hiveServer) && "STANDALONE".equalsIgnoreCase(connectionMode);
			if(securedStandaloneHive2) {
				super.createConnection(node, false);
			} else {
				super.createConnection(node);
			}
		}

		public void afterUseExistConnection(INode node) {
			String connection = ElementParameterParser.getValue(node,"__CONNECTION__");
	        String db = "db_" + connection;
	        String dbUser = "dbUser_" + connection;

    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(db);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_191);
    stringBuffer.append(dbUser);
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_193);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_194);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_195);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_196);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_197);
    
	    }

	    public void beforeComponentProcess(INode node){
	    	super.beforeComponentProcess(node);
	    	this.hiveServer = ElementParameterParser.getValue(node, "__HIVE_SERVER__");

			String theDistribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
			String theVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");
			String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");

		    if("true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"))) {
		        String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
		        for (INode pNode : node.getProcess().getNodesOfType("tHiveConnection")) {
		            if(connection!=null && connection.equals(pNode.getUniqueName())) {
		                theDistribution = ElementParameterParser.getValue(pNode, "__DISTRIBUTION__");
		                theVersion = ElementParameterParser.getValue(pNode, "__HIVE_VERSION__");
		                this.hiveServer = ElementParameterParser.getValue(pNode, "__HIVE_SERVER__");
		                connectionMode = ElementParameterParser.getValue(pNode, "__CONNECTION_MODE__");
		            }
		        }
		    }

			try {
				this.hiveDistrib = (org.talend.hadoop.distribution.component.HiveComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(theDistribution, theVersion);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
				return;
			}
			this.isCustom = this.hiveDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

			if(hiveServer!=null && !"".equals(hiveServer.trim()) && (isCustom || hiveDistrib.doSupportHive2())) {
				hiveServer = hiveServer.toLowerCase();
				if ("hive2".equals(hiveServer)) {
					javaDbDriver = "org.apache.hive.jdbc.HiveDriver";
				}
			} else {
				hiveServer = "hive";
			}

			if(("hive".equals(this.hiveServer) && !hiveDistrib.doSupportHive1()) || ("hive2".equals(this.hiveServer) && !hiveDistrib.doSupportHive2())) {

    stringBuffer.append(TEXT_198);
    stringBuffer.append(hiveDistrib.getVersion());
    stringBuffer.append(TEXT_199);
    
			}

			if(("STANDALONE".equals(connectionMode) && !hiveDistrib.doSupportStandaloneMode()) || ("EMBEDDED".equals(connectionMode) && !hiveDistrib.doSupportEmbeddedMode())) {

    stringBuffer.append(TEXT_200);
    stringBuffer.append(hiveDistrib.getVersion());
    stringBuffer.append(TEXT_201);
    
			}

			if("STANDALONE".equals(connectionMode) && "hive".equals(this.hiveServer) && !hiveDistrib.doSupportHive1Standalone()) {

    stringBuffer.append(TEXT_202);
    
			}
	    }

		public void afterUseNewConnection(INode node) {

        	String storeByHBase = ElementParameterParser.getValue(node, "__STORE_BY_HBASE__");
			String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");
			String hiveVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");

        	String zookeeperQuorumForHBase = ElementParameterParser.getValue(node, "__ZOOKEEPER_QUORUM__");
        	String zookeeperClientPortForHBase = ElementParameterParser.getValue(node, "__ZOOKEEPER_CLIENT_PORT__");
        	boolean setZNodeParent = "true".equals(ElementParameterParser.getValue(node, "__SET_ZNODE_PARENT__"));
			String zNodeParent = ElementParameterParser.getValue(node, "__ZNODE_PARENT__");
			String hbaseMasterPrincipal = ElementParameterParser.getValue(node, "__HBASE_MASTER_PRINCIPAL__");
			String hbaseReigonServerPrincipal = ElementParameterParser.getValue(node, "__HBASE_REIGONSERVER_PRINCIPAL__");
        	String defineRegisterJar = ElementParameterParser.getValue(node, "__DEFINE_REGISTER_JAR__");
        	List<Map<String, String>> registerJarForHBase = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__REGISTER_JAR__");

        	boolean useYarn = "true".equals(ElementParameterParser.getValue(node, "__USE_YARN__"));
        	boolean setResourceManager = "true".equals(ElementParameterParser.getValue(node, "__SET_RESOURCE_MANAGER__"));

			boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
			boolean securityIsEnabled = useKrb && (this.isCustom || (this.hiveDistrib.doSupportKerberos() && (("HIVE".equalsIgnoreCase(hiveServer) && "EMBEDDED".equalsIgnoreCase(connectionMode)) || "HIVE2".equalsIgnoreCase(hiveServer))));
			boolean securedStandaloneHive2 = securityIsEnabled && "HIVE2".equalsIgnoreCase(hiveServer) && "STANDALONE".equalsIgnoreCase(connectionMode);
			boolean securedEmbedded = securityIsEnabled && "EMBEDDED".equalsIgnoreCase(connectionMode);
			boolean securedEmbeddedHive2 = securedEmbedded && "HIVE2".equalsIgnoreCase(hiveServer);

    stringBuffer.append(TEXT_203);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_204);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_205);
    

		    if(!isCustom && ("HDP_1_2".equals(hiveVersion) || "HDP_1_3".equals(hiveVersion))) {
		        String mapMemory = ElementParameterParser.getValue(node,"__MAPRED_JOB_MAP_MEMORY_MB__");
		        String reduceMemory = ElementParameterParser.getValue(node,"__MAPRED_JOB_REDUCE_MEMORY_MB__");

    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_210);
    
    		}

			boolean isKerberosAvailableHadoop2 = !this.isCustom && this.hiveDistrib.isHadoop2() && this.hiveDistrib.doSupportKerberos();
			boolean isHadoop2 = !this.isCustom && this.hiveDistrib.isHadoop2();
			boolean isKerberosAvailableHadoop1 = !this.isCustom && this.hiveDistrib.isHadoop1() && this.hiveDistrib.doSupportKerberos();

			if(securedEmbedded) {
				String namenodePrincipal = ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");

    stringBuffer.append(TEXT_211);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_212);
    stringBuffer.append(namenodePrincipal);
    stringBuffer.append(TEXT_213);
    
				if(isKerberosAvailableHadoop1 || (isCustom && !useYarn)) {
					String jobtrackerPrincipal = ElementParameterParser.getValue(node, "__JOBTRACKER_PRINCIPAL__");

    stringBuffer.append(TEXT_214);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_215);
    stringBuffer.append(jobtrackerPrincipal);
    stringBuffer.append(TEXT_216);
    
				}
				if(isKerberosAvailableHadoop2 || (isCustom && useYarn)) {
					String resourceManagerPrincipal = ElementParameterParser.getValue(node, "__RESOURCEMANAGER_PRINCIPAL__");

    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(resourceManagerPrincipal);
    stringBuffer.append(TEXT_219);
    
				}

			}

        	if((this.isCustom && useYarn) || (!this.isCustom && isHadoop2)) {
        		if(setResourceManager) {
        			String resourceManager = ElementParameterParser.getValue(node, "__RESOURCE_MANAGER__");

    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_222);
    stringBuffer.append(resourceManager);
    stringBuffer.append(TEXT_223);
    
				}

        		boolean setJobHistoryAddress = "true".equals(ElementParameterParser.getValue(node, "__SET_JOBHISTORY_ADDRESS__"));
        		if(setJobHistoryAddress) {
        			String jobHistoryAddress = ElementParameterParser.getValue(node,"__JOBHISTORY_ADDRESS__");
        			
    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    stringBuffer.append(jobHistoryAddress);
    stringBuffer.append(TEXT_226);
    
        		}

				boolean setSchedulerAddress = "true".equals(ElementParameterParser.getValue(node, "__SET_SCHEDULER_ADDRESS__"));
				if(setSchedulerAddress) {
					String schedulerAddress = ElementParameterParser.getValue(node,"__RESOURCEMANAGER_SCHEDULER_ADDRESS__");

    stringBuffer.append(TEXT_227);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_228);
    stringBuffer.append(schedulerAddress);
    stringBuffer.append(TEXT_229);
    
				}

				if ("true".equals(ElementParameterParser.getValue(node, "__USE_DATANODE_HOSTNAME__"))) {
				    
    stringBuffer.append(TEXT_230);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_231);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_232);
    
				}

				if("true".equals(ElementParameterParser.getValue(node, "__SET_FS_DEFAULT_NAME__"))) {
        			String namenode = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");

    stringBuffer.append(TEXT_233);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_234);
    stringBuffer.append(namenode);
    stringBuffer.append(TEXT_235);
    
				}

				if("EMBEDDED".equals(connectionMode) && (this.isCustom || this.hiveDistrib.doSupportEmbeddedMode())) {
					boolean crossPlatformSubmission = "true".equals(ElementParameterParser.getValue(node, "__CROSS_PLATFORM_SUBMISSION__"));
					if((this.isCustom && useYarn && crossPlatformSubmission) || (!this.isCustom && this.hiveDistrib.doSupportCrossPlatformSubmission())) {

    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    
					}

					if("MAPR410".equals(hiveVersion)){

    stringBuffer.append(TEXT_238);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_239);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_240);
    
					}

					if(this.hiveDistrib.doSupportCustomMRApplicationCP()){

    stringBuffer.append(TEXT_241);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_242);
    stringBuffer.append(this.hiveDistrib.getCustomMRApplicationCP());
    stringBuffer.append(TEXT_243);
    
					}

    stringBuffer.append(TEXT_244);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_245);
    stringBuffer.append(this.hiveDistrib.getYarnApplicationClasspath());
    stringBuffer.append(TEXT_246);
    
    			}

    			boolean setMemory = "true".equals(ElementParameterParser.getValue(node, "__SET_MEMORY__"));
    			if(setMemory) {
        			String mapMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_MAP_MEMORY_MB__");
        			String reduceMemory = ElementParameterParser.getValue(node,"__MAPREDUCE_REDUCE_MEMORY_MB__");
        			String amMemory = ElementParameterParser.getValue(node,"__YARN_APP_MAPREDUCE_AM_RESOURCE_MB__");

    stringBuffer.append(TEXT_247);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_248);
    stringBuffer.append(mapMemory);
    stringBuffer.append(TEXT_249);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_250);
    stringBuffer.append(reduceMemory);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(amMemory);
    stringBuffer.append(TEXT_253);
    
				}
			}

            List<Map<String, String>> advProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ADVANCED_PROPERTIES__");
        	if(advProps!=null) {
				for(Map<String, String> item : advProps){

    stringBuffer.append(TEXT_254);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_255);
    stringBuffer.append(item.get("PROPERTY"));
    stringBuffer.append(TEXT_256);
    stringBuffer.append(item.get("VALUE"));
    stringBuffer.append(TEXT_257);
    
				}
			}

    stringBuffer.append(TEXT_258);
    
			boolean useExistingConnection = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
			if(!useExistingConnection){
			
    stringBuffer.append(TEXT_259);
    stringBuffer.append(TEXT_260);
    
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
    	
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_262);
    
            if(advProps != null){
                for(Map<String, String> item : advProps){
                    if("\"tez.lib.uris\"".equals(item.get("PROPERTY"))){
                    
    stringBuffer.append(TEXT_263);
      
                    }
                }
            }
            boolean installTez = "INSTALL".equals(ElementParameterParser.getValue(node, "__TEZ_LIB__"));
            if(installTez){
                //prepare the folder
                
    stringBuffer.append(TEXT_264);
    
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
        
    stringBuffer.append(TEXT_265);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_266);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_267);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_268);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_269);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_270);
    
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
        
        
    stringBuffer.append(TEXT_271);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_272);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_273);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_274);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_275);
    
        if(mrUseDatanodeHostname && (isCustom || hdfsDistrib.doSupportUseDatanodeHostname())){
        
    stringBuffer.append(TEXT_276);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_277);
    
        }
        if(hadoopProps!=null && hadoopProps.size() > 0){
        	for(Map<String, String> item : hadoopProps){
        	
    stringBuffer.append(TEXT_278);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_279);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_280);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_281);
     
    		}
    	}
        	
    	if((hdfsDistrib.doSupportKerberos() && useKrb && !isCustom) || (isCustom && useKrb)){
    	
    stringBuffer.append(TEXT_282);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_283);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_284);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_285);
    
    		if(useKeytab){
    		
    stringBuffer.append(TEXT_286);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_287);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_288);
    
    		}
    	}
    	
    	if(hdfsDistrib.doSupportGroup()){
    		
    stringBuffer.append(TEXT_289);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_290);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_291);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_293);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_294);
    
        }else{
        
    stringBuffer.append(TEXT_295);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_296);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_297);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_298);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_300);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_301);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_302);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_303);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_304);
    
        }
    }
}

      
                (new GetFileSystem()).invoke(node, cid);
                String tezLibFolder = ElementParameterParser.getValue(node, "__TEZ_LIB_FOLDER__");
                boolean useDefaultTezLibFolder = "\"/tmp/{USERNAME}/talend_tez_libs/{custom|HIVE_VERSION}\"".equals(tezLibFolder);
                if(useDefaultTezLibFolder){
                
    stringBuffer.append(TEXT_305);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_306);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_307);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_308);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_309);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_310);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_311);
    stringBuffer.append(isCustom?"custom":hiveVersion);
    stringBuffer.append(TEXT_312);
    
                }else{
                
    stringBuffer.append(TEXT_313);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_314);
    stringBuffer.append(tezLibFolder);
    stringBuffer.append(TEXT_315);
    
                }
                
    stringBuffer.append(TEXT_316);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_317);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_318);
    
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
                
    stringBuffer.append(TEXT_319);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_320);
    if(changePathSeparator){
    stringBuffer.append(TEXT_321);
    }else{
    stringBuffer.append(TEXT_322);
    }
    stringBuffer.append(TEXT_323);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_324);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_325);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_326);
    
                    for(String jarName : tezLibJarsName){
                    
    stringBuffer.append(TEXT_327);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_328);
    stringBuffer.append(jarName);
    stringBuffer.append(TEXT_329);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_330);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_331);
    stringBuffer.append(jarName);
    stringBuffer.append(TEXT_332);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_333);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_334);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_335);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_336);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_337);
    
                    }
                    
    stringBuffer.append(TEXT_338);
    
            }else{
            
    stringBuffer.append(TEXT_339);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_340);
    stringBuffer.append(ElementParameterParser.getValue(node, "__TEZ_LIB_PATH__"));
    stringBuffer.append(TEXT_341);
    
            }
            //define the location of tez lib    
            
    stringBuffer.append(TEXT_342);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_343);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_344);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_345);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_346);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_347);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_348);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_349);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_350);
    stringBuffer.append(ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__"));
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_357);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_358);
    
    	}
    }
}

    stringBuffer.append(TEXT_359);
    
            	(new PrepareTez()).invoke(node, cid);
            }
            
    stringBuffer.append(TEXT_360);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_361);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_362);
    stringBuffer.append(dbname);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_370);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_371);
    
    		if("true".equalsIgnoreCase(storeByHBase) && (this.isCustom || this.hiveDistrib.doSupportHBaseForHive())) {
    stringBuffer.append(TEXT_372);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_373);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_374);
    if(zookeeperQuorumForHBase!=null && !"".equals(zookeeperQuorumForHBase) && !"\"\"".equals(zookeeperQuorumForHBase)) {
    stringBuffer.append(TEXT_375);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_376);
    stringBuffer.append(zookeeperQuorumForHBase);
    stringBuffer.append(TEXT_377);
    }
    stringBuffer.append(TEXT_378);
    if(zookeeperClientPortForHBase!=null && !"".equals(zookeeperClientPortForHBase) && !"\"\"".equals(zookeeperClientPortForHBase)) {
    stringBuffer.append(TEXT_379);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_380);
    stringBuffer.append(zookeeperClientPortForHBase);
    stringBuffer.append(TEXT_381);
    }
    stringBuffer.append(TEXT_382);
    if(setZNodeParent && zNodeParent!=null && !"".equals(zNodeParent) && !"\"\"".equals(zNodeParent)) {
    stringBuffer.append(TEXT_383);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_384);
    stringBuffer.append(zNodeParent);
    stringBuffer.append(TEXT_385);
    }
    stringBuffer.append(TEXT_386);
    if(useKrb){
    stringBuffer.append(TEXT_387);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_388);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_389);
    if(hbaseMasterPrincipal!=null && !"".equals(hbaseMasterPrincipal) && !"\"\"".equals(hbaseMasterPrincipal)){
    stringBuffer.append(TEXT_390);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_391);
    stringBuffer.append(hbaseMasterPrincipal);
    stringBuffer.append(TEXT_392);
    }
    stringBuffer.append(TEXT_393);
    if(hbaseReigonServerPrincipal!=null && !"".equals(hbaseReigonServerPrincipal) && !"\"\"".equals(hbaseReigonServerPrincipal)){
    stringBuffer.append(TEXT_394);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_395);
    stringBuffer.append(hbaseReigonServerPrincipal);
    stringBuffer.append(TEXT_396);
    }
    stringBuffer.append(TEXT_397);
    }
    stringBuffer.append(TEXT_398);
    if("true".equalsIgnoreCase(defineRegisterJar) && registerJarForHBase!=null && registerJarForHBase.size()>0) {
        			for(Map<String, String> jar : registerJarForHBase){
        				String path = jar.get("JAR_PATH");
        				if(path == null || "".equals(path) || "\"\"".equals(path)) {
        					continue;
        				}
        		
    stringBuffer.append(TEXT_399);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_400);
    stringBuffer.append(path);
    stringBuffer.append(TEXT_401);
    
        			}
        		}
    stringBuffer.append(TEXT_402);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_403);
    
			}
    	}

		public void setURL(INode node) {
			String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");
			String hiveVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");
			String fsDefalutName = "fs.default.name";

			boolean setMapredJT = "true".equals(ElementParameterParser.getValue(node, "__SET_MAPRED_JT__"));
			boolean setNamenode = "true".equals(ElementParameterParser.getValue(node, "__SET_FS_DEFAULT_NAME__"));
			List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");

			boolean useYarn = "true".equals(ElementParameterParser.getValue(node, "__USE_YARN__"));

			boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
			boolean securityIsEnabled = useKrb && (this.isCustom || (this.hiveDistrib.doSupportKerberos() && (("HIVE".equalsIgnoreCase(hiveServer) && "EMBEDDED".equalsIgnoreCase(connectionMode)) || "HIVE2".equalsIgnoreCase(hiveServer))));
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
	        if(hadoopProps.size() > 0){
				for(Map<String, String> item : hadoopProps){

    stringBuffer.append(TEXT_404);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_405);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_406);
    
				}
			}

			if(((this.isCustom && !useYarn) || (!this.isCustom && this.hiveDistrib.isHadoop1())) && setMapredJT) {
				String mapredJT = ElementParameterParser.getValue(node, "__MAPRED_JT__");

    stringBuffer.append(TEXT_407);
    stringBuffer.append(mapredJT);
    stringBuffer.append(TEXT_408);
    
			}

			if(setNamenode) {
				String namenode = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");

    stringBuffer.append(TEXT_409);
    stringBuffer.append(fsDefalutName);
    stringBuffer.append(TEXT_410);
    stringBuffer.append(namenode);
    stringBuffer.append(TEXT_411);
    
			}

			if(securedEmbedded) {
				String metastoreUrl = ElementParameterParser.getValue(node, "__METASTORE_JDBC_URL__");
				String driverClass = ElementParameterParser.getValue(node, "__METASTORE_CLASSNAME__");
				String metastoreUsername = ElementParameterParser.getValue(node, "__METASTORE_USERNAME__");
				boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
				String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
				String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");

    stringBuffer.append(TEXT_412);
    stringBuffer.append(driverClass);
    stringBuffer.append(TEXT_413);
    if(securedEmbeddedHive2){
					// Disable authorization when using local HiveServer2 in secure mode
					
    stringBuffer.append(TEXT_414);
    
                }else{
                    
    stringBuffer.append(TEXT_415);
    
                 }
                
    stringBuffer.append(TEXT_416);
    stringBuffer.append(metastoreUrl);
    stringBuffer.append(TEXT_417);
    stringBuffer.append(metastoreUsername);
    stringBuffer.append(TEXT_418);
    
        		passwordFieldName = "__METASTORE_PASSWORD__";
        		
    stringBuffer.append(TEXT_419);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_420);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_421);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_422);
    } else {
    stringBuffer.append(TEXT_423);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_424);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_425);
    }
    stringBuffer.append(TEXT_426);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_427);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_428);
    
               	if(securedEmbeddedHive2){
                
    stringBuffer.append(TEXT_429);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HIVESERVER2_LOCAL_PRINCIPAL__"));
    stringBuffer.append(TEXT_430);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HIVESERVER2_LOCAL_KEYTAB__"));
    stringBuffer.append(TEXT_431);
    
                }
                
    
				if(useKeytab) {

    stringBuffer.append(TEXT_432);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_433);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_434);
    
				}
			}

	        boolean setHadoopUser = "true".equals(ElementParameterParser.getValue(node, "__SET_HADOOP_USER__"));
	        if (setHadoopUser) {
	            String hadoopUser = ElementParameterParser.getValue(node, "__HADOOP_USER__");
	            
    stringBuffer.append(TEXT_435);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_436);
    stringBuffer.append(hadoopUser);
    stringBuffer.append(TEXT_437);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_438);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_439);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_440);
    
	        }

    stringBuffer.append(TEXT_441);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_442);
    
			if("EMBEDDED".equals(connectionMode) && (this.isCustom || this.hiveDistrib.doSupportEmbeddedMode())) {

    stringBuffer.append(TEXT_443);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_444);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_445);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_446);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_447);
    
				if(this.isCustom || (!this.isCustom && this.hiveDistrib.doSupportImpersonation())) {

    stringBuffer.append(TEXT_448);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_449);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_450);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_451);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_452);
    
				}
			} else if("STANDALONE".equals(connectionMode) && (this.isCustom || this.hiveDistrib.doSupportStandaloneMode())) {
				if(securedStandaloneHive2) {
					// using keytab with HiveServer2 in standalone mode
					boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
					String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
					String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");

			        if ((isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket) {
			            
    stringBuffer.append(TEXT_453);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_454);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_455);
    
			        }
					if(useKeytab) {

    stringBuffer.append(TEXT_456);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_457);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_458);
    
					}

					if ((isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket) {
			            
    stringBuffer.append(TEXT_459);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_460);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_461);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_462);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_463);
    
			        }

					// Using SSL in Secure Mode
					if(useSsl && this.hiveDistrib.doSupportSSL()){

						// Does the distrib support SSL + KERBEROS
						if(this.hiveDistrib.doSupportSSLwithKerberos()){
							if (ElementParameterParser.canEncrypt(node, sslStorepasswordFieldName)) {

    stringBuffer.append(TEXT_464);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_465);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_466);
    
							}else{

    stringBuffer.append(TEXT_467);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_468);
    stringBuffer.append( ElementParameterParser.getValue(node, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_469);
    
							}

    stringBuffer.append(TEXT_470);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_471);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_472);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_473);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_474);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_475);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_476);
    stringBuffer.append(sslTrustStore);
    stringBuffer.append(TEXT_477);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_478);
    
						// Does the distrib support only SASL-QOP + KERBEROS
						} else {
						

    stringBuffer.append(TEXT_479);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_480);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_481);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_482);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_483);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_484);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_485);
    
						}
					}else{

    stringBuffer.append(TEXT_486);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_487);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_488);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_489);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_490);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_491);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_492);
    
					}
				} else {
				    // Mapr ticket
				    if ((isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket) {
                        passwordFieldName = "__MAPRTICKET_PASSWORD__";
                        
    stringBuffer.append(TEXT_493);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_494);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_495);
    
                        if (setMapRHadoopLogin) {
                            
    stringBuffer.append(TEXT_496);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_497);
    
                        } else {
                            
    stringBuffer.append(TEXT_498);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_499);
    
                        }
                        if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_500);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_501);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_502);
    } else {
    stringBuffer.append(TEXT_503);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_504);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_505);
    }
    
                        if(hiveDistrib.doSupportMaprTicketV52API()){
							
    stringBuffer.append(TEXT_506);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_507);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_508);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_509);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_510);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_511);
    
                        } else {
                        	
    stringBuffer.append(TEXT_512);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_513);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_514);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_515);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_516);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_517);
    
                        }
                    }
                    // Using SSL in non Secure Mode
					if(useSsl && this.hiveDistrib.doSupportSSL()){
						if (ElementParameterParser.canEncrypt(node, sslStorepasswordFieldName)) {

    stringBuffer.append(TEXT_518);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_519);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_520);
    
						}else{

    stringBuffer.append(TEXT_521);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_522);
    stringBuffer.append( ElementParameterParser.getValue(node, sslStorepasswordFieldName));
    stringBuffer.append(TEXT_523);
    
						}

    stringBuffer.append(TEXT_524);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_525);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_526);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_527);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_528);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_529);
    stringBuffer.append(sslTrustStore);
    stringBuffer.append(TEXT_530);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_531);
    
					}else{

    stringBuffer.append(TEXT_532);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_533);
    stringBuffer.append(hiveServer);
    stringBuffer.append(TEXT_534);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_535);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_536);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_537);
    
					}
				}
				String additionalJdbcSettings = ElementParameterParser.getValue(node, "__HIVE_ADDITIONAL_JDBC__");

    stringBuffer.append(TEXT_538);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_539);
    stringBuffer.append(additionalJdbcSettings);
    stringBuffer.append(TEXT_540);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_541);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_542);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_543);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_544);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_545);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_546);
    
			}
		}

		public String getDirverClassName(INode node){
			return javaDbDriver;
		}

		public void createStatement(INode node) {
			boolean useParquet = "true".equals(ElementParameterParser.getValue(node,"__USE_PARQUET__"));
			String processId = node.getProcess().getId();

			String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");
			boolean setFsDefaultName = "true".equals(ElementParameterParser.getValue(node, "__SET_FS_DEFAULT_NAME__"));
			String fsDefaultName = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
			INode connectionInformationNode = node;

			boolean useExistingConnection = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));

			if(useExistingConnection) {
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

			boolean isParquetSupported = this.isCustom || this.hiveDistrib.doSupportParquetFormat();;
			if(useParquet && !isParquetSupported) {

    stringBuffer.append(TEXT_547);
    
			}

			boolean generateAddJarCodeForAll = useParquet;

			// Register jars to handle the parquet format.

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
				jarsToRegister.add("parquet-hive-bundle");
				//jarsToRegister.add("parquet-hadoop-bundle");

				for (int j = 0; j < commandLine.length; j++) {
					if(commandLine[j].contains("jar")) {
						jars = java.util.Arrays.asList(commandLine[j].split(";"));
						break;
					}
				}
			}


			if(jarsToRegister!=null && jars!=null) {
				if("EMBEDDED".equalsIgnoreCase(connectionMode)) {

    stringBuffer.append(TEXT_548);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_549);
    
				} else {
					generateAddJarCodeForAll = false;
					if(setFsDefaultName) {
						generateAddJarCodeForAll = true;

    stringBuffer.append(TEXT_550);
    stringBuffer.append(TEXT_551);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_552);
    stringBuffer.append(TEXT_553);
    
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
        
    stringBuffer.append(TEXT_554);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_555);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_556);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_557);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_558);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_559);
    
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
        
        
    stringBuffer.append(TEXT_560);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_561);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_562);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_563);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_564);
    
        if(mrUseDatanodeHostname && (isCustom || hdfsDistrib.doSupportUseDatanodeHostname())){
        
    stringBuffer.append(TEXT_565);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_566);
    
        }
        if(hadoopProps!=null && hadoopProps.size() > 0){
        	for(Map<String, String> item : hadoopProps){
        	
    stringBuffer.append(TEXT_567);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_568);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_569);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_570);
     
    		}
    	}
        	
    	if((hdfsDistrib.doSupportKerberos() && useKrb && !isCustom) || (isCustom && useKrb)){
    	
    stringBuffer.append(TEXT_571);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_572);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_573);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_574);
    
    		if(useKeytab){
    		
    stringBuffer.append(TEXT_575);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_576);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_577);
    
    		}
    	}
    	
    	if(hdfsDistrib.doSupportGroup()){
    		
    stringBuffer.append(TEXT_578);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_579);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_580);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_581);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_582);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_583);
    
        }else{
        
    stringBuffer.append(TEXT_584);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_585);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_586);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_587);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_588);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_589);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_590);
    stringBuffer.append(fsDefaultNameKey);
    stringBuffer.append(TEXT_591);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_592);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_593);
    
        }
    }
}

    stringBuffer.append(TEXT_594);
    
			(new GetFileSystem()).invoke(connectionInformationNode, cid);
			
    stringBuffer.append(TEXT_595);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_596);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_597);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_598);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_599);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_600);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_601);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_602);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_603);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_604);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_605);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_606);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_607);
    
					}
				}

				if(generateAddJarCodeForAll) {

    stringBuffer.append(TEXT_608);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_609);
    
					for(int i=0; i<jarsToRegister.size(); i++) {
						String jarToRegister = jarsToRegister.get(i);
						for(int j=0; j<jars.size(); j++) {
							if(jars.get(j).contains(jarToRegister)) {

    stringBuffer.append(TEXT_610);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_611);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_612);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_613);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_614);
    stringBuffer.append(jars.get(j));
    stringBuffer.append(TEXT_615);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_616);
    
							}
						}
					}
				}
			}

			// End of parquet format handling.

			super.createStatement(node);
		}

	}//end class

	dbInputBeginUtil = new DBInputBeginUtil();
	
    stringBuffer.append(TEXT_617);
    
	CodeGeneratorArgument codeGenArg = (CodeGeneratorArgument) argument;
	INode hiveNode = (INode)codeGenArg.getArgument();
	boolean setTempPath = "true".equals(ElementParameterParser.getValue(hiveNode, "__SET_TEMP_PATH__"));
	if(setTempPath) {
		String tempPath = ElementParameterParser.getValue(hiveNode, "__TEMP_PATH__");

    stringBuffer.append(TEXT_618);
    stringBuffer.append(tempPath);
    stringBuffer.append(TEXT_619);
    
	}

	String yarnClasspathSeparator = ElementParameterParser.getValue(hiveNode, "__CLASSPATH_SEPARATOR__");

    stringBuffer.append(TEXT_620);
    stringBuffer.append(yarnClasspathSeparator);
    stringBuffer.append(TEXT_621);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();
	String cid = node.getUniqueName();
	String type = ElementParameterParser.getValue(node, "__TYPE__");
	String dbhost = ElementParameterParser.getValue(node, "__HOST__");
	String dbport = ElementParameterParser.getValue(node, "__PORT__");
	String dbname = ElementParameterParser.getValue(node, "__DBNAME__");
	String dbproperties = ElementParameterParser.getValue(node, "__PROPERTIES__");
	String dbuser = ElementParameterParser.getValue(node, "__USER__");

	String dbencoding = ElementParameterParser.getValue(node, "__ENCODING__");
	String enableStream = ElementParameterParser.getValue(node, "__ENABLE_STREAM__");
	String dbms=ElementParameterParser.getValue(node, "__MAPPING__");
	if("tJDBCInput".equals(node.getComponent().getName())) {
		boolean isEnableMapping = ("true").equals(ElementParameterParser.getValue(node, "__ENABLE_MAPPING__"));
		if (!isEnableMapping) {
			dbms = "";
		}
	}
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

    boolean whetherTrimAllCol = ("true").equals(ElementParameterParser.getValue(node, "__TRIM_ALL_COLUMN__"));
    List<Map<String, String>> trimColumnList = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__TRIM_COLUMN__");

    dbInputBeginUtil.beforeComponentProcess(node);
    int dynamic_index=-1;
	List<IMetadataTable> metadatas = node.getMetadataList();
	if ((metadatas != null) && (metadatas.size()>0)) {
		IMetadataTable metadata = metadatas.get(0);
		if (metadata != null) {

    stringBuffer.append(TEXT_622);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_623);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_624);
    
		    String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");
		    if(("true").equals(useExistingConn)) {
		        String connection = ElementParameterParser.getValue(node,"__CONNECTION__");
		        String conn = "conn_" + connection;
                if(connection.matches("^.*?tAmazonAuroraConnection_\\d+$")){
                      conn += "_in";
                }

    stringBuffer.append(TEXT_625);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_626);
    stringBuffer.append(conn);
    stringBuffer.append(TEXT_627);
    log4jCodeGenerateUtil.useExistConnection(node);
    
				dbInputBeginUtil.afterUseExistConnection(node);
		    } else {
				dbInputBeginUtil.createConnection(node);
				if ("teradata_id".equalsIgnoreCase(dbms)) {

    stringBuffer.append(TEXT_628);
    
      boolean queryBand = "true".equals(ElementParameterParser.getValue(node, "__QUERY_BAND__"));
      boolean activateQueryLogging = "true".equals(ElementParameterParser.getValue(node, "__ACTIVATE_QUERY_LOGGING__"));
      String usrName = ElementParameterParser.getValue(node, "__USER__");
      List<Map<String, String>> queryBandList = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__QUERY_BAND_PARAMETERS__");
      if(queryBand){
    stringBuffer.append(TEXT_629);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_630);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_631);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_632);
    
                    if(activateQueryLogging){
                        
    stringBuffer.append(TEXT_633);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_634);
    stringBuffer.append( usrName );
    stringBuffer.append(TEXT_635);
    
                    }
                  
    stringBuffer.append(TEXT_636);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_637);
    
            for(Map<String, String> map : queryBandList) {

    stringBuffer.append(TEXT_638);
    stringBuffer.append( map.get("KEY") );
    stringBuffer.append(TEXT_639);
    stringBuffer.append( map.get("VALUE") );
    stringBuffer.append(TEXT_640);
    
            }

    stringBuffer.append(TEXT_641);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_642);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_643);
    }
    
				}
		    }

    stringBuffer.append(TEXT_644);
    dbInputBeginUtil.createStatement(node);
    stringBuffer.append(TEXT_645);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_646);
    stringBuffer.append(dbInputBeginUtil.getQueryString(node));
    stringBuffer.append(TEXT_647);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_648);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_649);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_650);
    }
    stringBuffer.append(TEXT_651);
     
                if(cid.matches("^.*?tAmazonAuroraInput_\\d+_in$")){
                    // why 3: ==> "_in".length()
                    
    stringBuffer.append(TEXT_652);
    stringBuffer.append( cid.substring(0,cid.length() - 3) );
    stringBuffer.append(TEXT_653);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_654);
    
                }else{
                    
    stringBuffer.append(TEXT_655);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_656);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_657);
    
            }
             
    stringBuffer.append(TEXT_658);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_659);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_660);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_661);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_662);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_663);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_664);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_665);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_666);
    
		    List< ? extends IConnection> conns = node.getOutgoingSortedConnections();
		    List<IMetadataColumn> columnList = metadata.getListColumns();
		    boolean isDynamic = metadata.isDynamicSchema();
		    if(isDynamic){
		    	String DynamicDatePattern = "\"dd-MM-yyyy\"";
		    	for(IMetadataColumn column : columnList) {
		    		if("id_Dynamic".equals(column.getTalendType())) {
		    			DynamicDatePattern = column.getPattern();
		    			break;
		    		}
		    	}
		    
    stringBuffer.append(TEXT_667);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_668);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_669);
    stringBuffer.append(dbms );
    stringBuffer.append(TEXT_670);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_671);
    

		    for(int i=0; i< columnList.size(); i++) {
		        if(columnList.get(i).getTalendType().equals("id_Dynamic")){
		            dynamic_index = i+1;
		        }else{
		            IMetadataColumn column = columnList.get(i);
		            String columName = column.getOriginalDbColumnName();
		            if(columName==null || "".equals(columName)){
		                columName = column.getLabel();
		            }
	            
    stringBuffer.append(TEXT_672);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_673);
    stringBuffer.append(columName.toUpperCase());
    stringBuffer.append(TEXT_674);
    
		    	}
			}
		    
    stringBuffer.append(TEXT_675);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_676);
    stringBuffer.append(dynamic_index-1);
    stringBuffer.append(TEXT_677);
    stringBuffer.append(dynamic_index);
    stringBuffer.append(TEXT_678);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_679);
    stringBuffer.append(columnList.size()-dynamic_index);
    stringBuffer.append(TEXT_680);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_681);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_682);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_683);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_684);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_685);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_686);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_687);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_688);
    stringBuffer.append(dbms );
    stringBuffer.append(TEXT_689);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_690);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_691);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_692);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_693);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_694);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_695);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_696);
     if((DynamicDatePattern!=null) && (!"".equals(DynamicDatePattern)) && (!"\"\"".equals(DynamicDatePattern))) {
    stringBuffer.append(TEXT_697);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_698);
    stringBuffer.append(DynamicDatePattern);
    stringBuffer.append(TEXT_699);
     } 
    
		if (("oracle_id".equalsIgnoreCase(dbms))) {

    stringBuffer.append(TEXT_700);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_701);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_702);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_703);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_704);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_705);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_706);
    
		} else {

    stringBuffer.append(TEXT_707);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_708);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_709);
    
		}

    stringBuffer.append(TEXT_710);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_711);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_712);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_713);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_714);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_715);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_716);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_717);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_718);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_719);
    
		    }
		    
    stringBuffer.append(TEXT_720);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_721);
    if(isDynamic){
    stringBuffer.append(TEXT_722);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_723);
    }
    stringBuffer.append(TEXT_724);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_725);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_726);
    }
    stringBuffer.append(TEXT_727);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_728);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_729);
    
		        if(conns != null && conns.size() > 0) {
		            IConnection conn = conns.get(0);
		            String firstConnName = conn.getName();
		            int currentColNo = 1;
		            for(IMetadataColumn column : columnList) {
		                boolean whetherTrimCol = false;
		                if((trimColumnList != null && trimColumnList.size() > 0) && !whetherTrimAllCol) {
		                    for(Map<String, String> trimColumn : trimColumnList) {
		                        if(column.getLabel().equals(trimColumn.get("SCHEMA_COLUMN"))) {
		                            if(("true").equals(trimColumn.get("TRIM"))) {
		                                whetherTrimCol = true;
		                                break;
		                            }
		                        }
		                    }
		                }
		                String trimMethod = "";
		                if(whetherTrimAllCol || whetherTrimCol) {
		                    trimMethod = ".trim()";
		                }
		                String columnType = column.getType();

		                String typeToGenerate = JavaTypesManager.getTypeToGenerate(column.getTalendType(), column.isNullable());
		                String defVal = JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate);
		                if(conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
							if(isDynamic){
								if(!("Dynamic").equals(typeToGenerate) && dynamic_index < currentColNo) {
								
    stringBuffer.append(TEXT_730);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_731);
    stringBuffer.append(currentColNo-1);
    stringBuffer.append(TEXT_732);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_733);
    }else{
    stringBuffer.append(TEXT_734);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_735);
    stringBuffer.append(currentColNo);
    stringBuffer.append(TEXT_736);
    }
    stringBuffer.append(TEXT_737);
    
							}
							
    stringBuffer.append(TEXT_738);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_739);
    if(isDynamic){
    stringBuffer.append(TEXT_740);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_741);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_742);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_743);
    stringBuffer.append(defVal);
    stringBuffer.append(TEXT_744);
    
								typeToGenerate = dbInputBeginUtil.mappingType(typeToGenerate);

								if(("Char").equals(typeToGenerate) || ("Character").equals(typeToGenerate)) {

    stringBuffer.append(TEXT_745);
    dbInputBeginUtil.generateStringCharAndCharacterSet(firstConnName, column,currentColNo, trimMethod, typeToGenerate, whetherTrimAllCol, whetherTrimCol);
    
								} else if(("Timestamp").equals(typeToGenerate)) {
									if("vertica_id".equalsIgnoreCase(dbms) && "DATE".equalsIgnoreCase(column.getType())){

    stringBuffer.append(TEXT_746);
    dbInputBeginUtil.generateOthersResultSet(firstConnName, column,  currentColNo,  "Date");
    
									}else{

    stringBuffer.append(TEXT_747);
    dbInputBeginUtil.generateTimestampResultSet(firstConnName, column, currentColNo);
    
									}
		                         } else if (("List").equals(typeToGenerate)) {

    stringBuffer.append(TEXT_748);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_749);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_750);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_751);
    if(isDynamic){
    stringBuffer.append(TEXT_752);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_753);
    
		                        } else if(("String").equals(typeToGenerate)) {

    stringBuffer.append(TEXT_754);
    dbInputBeginUtil.generateStringResultSet(firstConnName, column, currentColNo,trimMethod);
    
								} else if(("Double").equals(typeToGenerate)) {

    stringBuffer.append(TEXT_755);
    dbInputBeginUtil.generateDoubleResultSet(firstConnName, column, currentColNo);
    
								} else if(("Boolean").equals(typeToGenerate)) {

    stringBuffer.append(TEXT_756);
    dbInputBeginUtil.generateBooleanResultSet(firstConnName, column, currentColNo);
    
								} else if("Geometry".equals(typeToGenerate) && type.indexOf("ORACLE") >= 0) {

    stringBuffer.append(TEXT_757);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_758);
    if(isDynamic){
    stringBuffer.append(TEXT_759);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_760);
    stringBuffer.append(TEXT_761);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_762);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_763);
    stringBuffer.append(TEXT_764);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_765);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_766);
    
                                    String sourceCRS = ElementParameterParser.getValue(node,"__CRS__");
                                    String forceCRS = ElementParameterParser.getValue(node,"__FORCE_CRS__");
                                    if (forceCRS.equals("true")) {

    stringBuffer.append(TEXT_767);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_768);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_769);
    stringBuffer.append(TEXT_770);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_771);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_772);
    stringBuffer.append(sourceCRS);
    stringBuffer.append(TEXT_773);
    
                                    }
								} else if(("Bytes").equals(typeToGenerate) && (columnType != null && (("LONG RAW").equals(columnType) || ("RAW").equals(columnType)))) {//oracle

    stringBuffer.append(TEXT_774);
    dbInputBeginUtil.generateBytesResultSet(firstConnName, column, currentColNo);
    
								} else if(("Dynamic").equals(typeToGenerate)) {

    stringBuffer.append(TEXT_775);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_776);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_777);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_778);
     //for bug TDI-20886
									boolean trim = whetherTrimAllCol || whetherTrimCol;
									if ("id_MSSQL".equalsIgnoreCase(dbms)) {
										String driver = "";
										if(("true").equals(useExistingConn)) {
											String connection = ElementParameterParser.getValue(node,"__CONNECTION__");
											INode connectionNode = null; 
											for (INode processNode : node.getProcess().getGeneratingNodes()) { 
												if(connection.equals(processNode.getUniqueName())) { 
													connectionNode = processNode; 
													break; 
												} 
											}
											driver = ElementParameterParser.getValue(connectionNode,"__DRIVER__");
										} else {
											driver = ElementParameterParser.getValue(node,"__DRIVER__");
										}

    stringBuffer.append(TEXT_779);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_780);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_781);
    stringBuffer.append(metadata.getListColumns().size());
    stringBuffer.append(TEXT_782);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_783);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_784);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_785);
     if("JTDS".equals(driver)) { 
    stringBuffer.append(TEXT_786);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_787);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_788);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_789);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_790);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_791);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_792);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_793);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_794);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_795);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_796);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_797);
     } else {
    stringBuffer.append(TEXT_798);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_799);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_800);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_801);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_802);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_803);
     } 
    stringBuffer.append(TEXT_804);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_805);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_806);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_807);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_808);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_809);
    stringBuffer.append(trim);
    stringBuffer.append(TEXT_810);
    
									}else if("access_id".equalsIgnoreCase(dbms)){

    stringBuffer.append(TEXT_811);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_812);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_813);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_814);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_815);
    stringBuffer.append(trim);
    stringBuffer.append(TEXT_816);
    
									}else{

    stringBuffer.append(TEXT_817);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_818);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_819);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_820);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_821);
    stringBuffer.append(trim);
    stringBuffer.append(TEXT_822);
    
									}
								} else if(typeToGenerate.equals("Geometry")) {

    stringBuffer.append(TEXT_823);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_824);
    if(isDynamic){
    stringBuffer.append(TEXT_825);
    stringBuffer.append(cid);
    }else{
    stringBuffer.append(currentColNo);
    }
    stringBuffer.append(TEXT_826);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_827);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_828);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_829);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_830);
    
                 	            } else {

    stringBuffer.append(TEXT_831);
    dbInputBeginUtil.generateOthersResultSet( firstConnName, column,  currentColNo,  typeToGenerate);
    
		                        }

    stringBuffer.append(TEXT_832);
    
		                    currentColNo++;
		                }
		            }

    stringBuffer.append(TEXT_833);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_834);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_835);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_836);
    }
    
		            if(conns.size() > 1) {
		                for(int connNO = 1 ; connNO < conns.size() ; connNO++) {
		                    IConnection conn2 = conns.get(connNO);
		                    if((conn2.getName().compareTo(firstConnName) != 0) && (conn2.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA))) {
		                        for(IMetadataColumn column:columnList){
		                            
    stringBuffer.append(TEXT_837);
    stringBuffer.append(conn2.getName());
    stringBuffer.append(TEXT_838);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_839);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_840);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_841);
    
		                        }
		                    }
		                }
		            }
		        }
		}
	}

    stringBuffer.append(TEXT_842);
    return stringBuffer.toString();
  }
}
