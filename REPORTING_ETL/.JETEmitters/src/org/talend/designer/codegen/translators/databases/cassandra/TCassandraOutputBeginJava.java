package org.talend.designer.codegen.translators.databases.cassandra;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.process.EConnectionType;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.talend.core.model.metadata.MappingTypeRetriever;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.core.model.utils.NodeUtil;

public class TCassandraOutputBeginJava
{
  protected static String nl;
  public static synchronized TCassandraOutputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCassandraOutputBeginJava result = new TCassandraOutputBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL + "    \t";
  protected final String TEXT_4 = NL + "\t";
  protected final String TEXT_5 = NL + "\t\t\tcom.datastax.driver.core.Cluster cluster_";
  protected final String TEXT_6 = " = (com.datastax.driver.core.Cluster)globalMap.get(\"cluster_";
  protected final String TEXT_7 = "\");" + NL + "        \tcom.datastax.driver.core.Session connection_";
  protected final String TEXT_8 = " = (com.datastax.driver.core.Session)globalMap.get(\"connection_";
  protected final String TEXT_9 = "\");";
  protected final String TEXT_10 = NL + "\t        ";
  protected final String TEXT_11 = NL + "\t";
  protected final String TEXT_12 = NL + "\t";
  protected final String TEXT_13 = NL + "\t\tjavax.net.ssl.KeyManagerFactory kmf_";
  protected final String TEXT_14 = " = null;" + NL + "\t\t";
  protected final String TEXT_15 = NL + "\t\t\tjava.security.KeyStore ks_";
  protected final String TEXT_16 = " = java.security.KeyStore.getInstance(\"";
  protected final String TEXT_17 = "\");" + NL + "\t\t\tks_";
  protected final String TEXT_18 = ".load(new java.io.FileInputStream(";
  protected final String TEXT_19 = "), ";
  protected final String TEXT_20 = ".toCharArray());" + NL + "\t\t\tkmf_";
  protected final String TEXT_21 = " = javax.net.ssl.KeyManagerFactory.getInstance(javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());" + NL + "\t\t\tkmf_";
  protected final String TEXT_22 = ".init(ks_";
  protected final String TEXT_23 = ", ";
  protected final String TEXT_24 = ".toCharArray());" + NL + "\t\t";
  protected final String TEXT_25 = NL + "\t\t" + NL + "\t\tjava.security.KeyStore ts_";
  protected final String TEXT_26 = " = java.security.KeyStore.getInstance(\"";
  protected final String TEXT_27 = "\");" + NL + "\t\tts_";
  protected final String TEXT_28 = ".load(new java.io.FileInputStream(";
  protected final String TEXT_29 = "), ";
  protected final String TEXT_30 = ".toCharArray());" + NL + "\t\tjavax.net.ssl.TrustManagerFactory tmf_";
  protected final String TEXT_31 = " = javax.net.ssl.TrustManagerFactory.getInstance(javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm());" + NL + "    \ttmf_";
  protected final String TEXT_32 = ".init(ts_";
  protected final String TEXT_33 = ");" + NL + "\t\t" + NL + "\t\tjavax.net.ssl.SSLContext sslContext_";
  protected final String TEXT_34 = " = javax.net.ssl.SSLContext.getInstance(\"TLS\");" + NL + "\t\tsslContext_";
  protected final String TEXT_35 = ".init(kmf_";
  protected final String TEXT_36 = " == null ? null : kmf_";
  protected final String TEXT_37 = ".getKeyManagers(), tmf_";
  protected final String TEXT_38 = ".getTrustManagers(), new java.security.SecureRandom());" + NL + "\t";
  protected final String TEXT_39 = NL + "    com.datastax.driver.core.Cluster cluster_";
  protected final String TEXT_40 = " = com.datastax.driver.core.Cluster.builder()" + NL + "    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t.addContactPoints(";
  protected final String TEXT_41 = ".split(\",\"))" + NL + "    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t.withPort(Integer.valueOf(";
  protected final String TEXT_42 = "))" + NL + "                                                            \t";
  protected final String TEXT_43 = NL + "                                                                \t.withCredentials(";
  protected final String TEXT_44 = ", ";
  protected final String TEXT_45 = ")" + NL + "                                                            \t";
  protected final String TEXT_46 = NL + "                                                            \t\t.withSSL(com.datastax.driver.core.JdkSSLOptions.builder()" + NL + "                                                            \t\t\t\t\t.withSSLContext(sslContext_";
  protected final String TEXT_47 = ").build())" + NL + "                                                            \t";
  protected final String TEXT_48 = NL + "    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t.build();" + NL + "\t" + NL + "\tcom.datastax.driver.core.Session connection_";
  protected final String TEXT_49 = " = null;" + NL + "\tconnection_";
  protected final String TEXT_50 = " = cluster_";
  protected final String TEXT_51 = ".connect();" + NL + "\t        resourceMap.put(\"cluster_";
  protected final String TEXT_52 = "\", cluster_";
  protected final String TEXT_53 = ");" + NL + "\t        resourceMap.put(\"connection_";
  protected final String TEXT_54 = "\", connection_";
  protected final String TEXT_55 = ");" + NL + "\t    ";
  protected final String TEXT_56 = NL + "\t";
  protected final String TEXT_57 = NL + "\t    \t\tconnection_";
  protected final String TEXT_58 = ".execute(";
  protected final String TEXT_59 = ");" + NL + "\t    \t\tconnection_";
  protected final String TEXT_60 = ".execute(";
  protected final String TEXT_61 = ");" + NL + "\t    \t";
  protected final String TEXT_62 = NL + "\t    \t\tconnection_";
  protected final String TEXT_63 = ".execute(";
  protected final String TEXT_64 = ");" + NL + "\t    \t";
  protected final String TEXT_65 = NL + "\t    \t\tconnection_";
  protected final String TEXT_66 = ".execute(";
  protected final String TEXT_67 = ");" + NL + "\t    \t";
  protected final String TEXT_68 = NL + "\t    \t\tconnection_";
  protected final String TEXT_69 = ".execute(";
  protected final String TEXT_70 = ");" + NL + "\t    \t\tconnection_";
  protected final String TEXT_71 = ".execute(";
  protected final String TEXT_72 = ");" + NL + "\t    \t";
  protected final String TEXT_73 = NL + "\t\t\t\tconnection_";
  protected final String TEXT_74 = ".execute(";
  protected final String TEXT_75 = ");" + NL + "\t            connection_";
  protected final String TEXT_76 = ".execute(";
  protected final String TEXT_77 = ");" + NL + "\t            ";
  protected final String TEXT_78 = NL + "\t                System.err.println(\"Don't support create table with set/list/map\");" + NL + "\t            ";
  protected final String TEXT_79 = NL + "\t\t\t";
  protected final String TEXT_80 = NL + "\t\t\t\tconnection_";
  protected final String TEXT_81 = ".execute(";
  protected final String TEXT_82 = ");" + NL + "\t            ";
  protected final String TEXT_83 = NL + "\t                System.err.println(\"Don't support create table with set/list/map\");" + NL + "\t            ";
  protected final String TEXT_84 = NL + "\t\t\t";
  protected final String TEXT_85 = NL + "\t            connection_";
  protected final String TEXT_86 = ".execute(";
  protected final String TEXT_87 = ");" + NL + "\t            connection_";
  protected final String TEXT_88 = ".execute(";
  protected final String TEXT_89 = ");" + NL + "\t            ";
  protected final String TEXT_90 = NL + "\t                System.err.println(\"Don't support create table with set/list/map\");" + NL + "\t            ";
  protected final String TEXT_91 = NL + "\t        ";
  protected final String TEXT_92 = NL + "\t            connection_";
  protected final String TEXT_93 = ".execute(";
  protected final String TEXT_94 = ");" + NL + "\t            ";
  protected final String TEXT_95 = NL + "\t                System.err.println(\"Don't support create table with set/list/map\");" + NL + "\t            ";
  protected final String TEXT_96 = NL + "\t        ";
  protected final String TEXT_97 = NL + "\t            connection_";
  protected final String TEXT_98 = ".execute(";
  protected final String TEXT_99 = ");" + NL + "\t        ";
  protected final String TEXT_100 = NL + "\t            connection_";
  protected final String TEXT_101 = ".execute(";
  protected final String TEXT_102 = ");" + NL + "\t        ";
  protected final String TEXT_103 = NL + "\t\tint nb_line_";
  protected final String TEXT_104 = " = 0;" + NL + "\t    com.datastax.driver.core.PreparedStatement prepareStmt_";
  protected final String TEXT_105 = " = null;" + NL + "\t    prepareStmt_";
  protected final String TEXT_106 = " = connection_";
  protected final String TEXT_107 = ".prepare(";
  protected final String TEXT_108 = ");" + NL + "\t    com.datastax.driver.core.BoundStatement boundStmt_";
  protected final String TEXT_109 = " = null;" + NL + "\t    ";
  protected final String TEXT_110 = NL + "\t    \tjava.util.List<String> columns_";
  protected final String TEXT_111 = " = new java.util.ArrayList<String>();\t" + NL + "\t    \t";
  protected final String TEXT_112 = NL + "\t    \t\tcolumns_";
  protected final String TEXT_113 = ".add(\"";
  protected final String TEXT_114 = "\");" + NL + "\t    \t";
  protected final String TEXT_115 = NL + "\t    \torg.talend.bigdata.cassandra.";
  protected final String TEXT_116 = " cassandraBatchExec_";
  protected final String TEXT_117 = " = new org.talend.bigdata.cassandra.";
  protected final String TEXT_118 = "(" + NL + "\t    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tconnection_";
  protected final String TEXT_119 = ", " + NL + "\t    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tStringHandling.DQUOTE(";
  protected final String TEXT_120 = ")," + NL + "\t    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tStringHandling.DQUOTE(";
  protected final String TEXT_121 = "), " + NL + "\t    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tcolumns_";
  protected final String TEXT_122 = "," + NL + "\t    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\torg.talend.bigdata.cassandra.BatchGroupingKey.fromString(\"";
  protected final String TEXT_123 = "\")," + NL + "\t    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_124 = "," + NL + "\t    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_125 = "," + NL + "\t    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_126 = ");" + NL + "    \t";
  protected final String TEXT_127 = NL + "\t        boundStmt_";
  protected final String TEXT_128 = " = new com.datastax.driver.core.BoundStatement(prepareStmt_";
  protected final String TEXT_129 = ");" + NL + "\t    ";
  protected final String TEXT_130 = NL + "    ";
  protected final String TEXT_131 = "    ";
  protected final String TEXT_132 = NL + "\tme.prettyprint.hector.api.Cluster cluster_";
  protected final String TEXT_133 = " =null;" + NL + "\tme.prettyprint.hector.api.Keyspace keyspace_";
  protected final String TEXT_134 = " =null;" + NL + "\tint batchCounter_";
  protected final String TEXT_135 = " = 0;" + NL + "\tint nb_line_";
  protected final String TEXT_136 = " = 0;" + NL + "\t";
  protected final String TEXT_137 = NL + "\t\tcluster_";
  protected final String TEXT_138 = "=(me.prettyprint.hector.api.Cluster)globalMap.get(\"cluster_";
  protected final String TEXT_139 = "\");" + NL + "\t\t";
  protected final String TEXT_140 = "\t" + NL + "\t\t\tlog.info(\"";
  protected final String TEXT_141 = " - Get an existing cluster instance from \" + \"";
  protected final String TEXT_142 = "\" + \".\");" + NL + "\t\t";
  protected final String TEXT_143 = NL + "\t\tString hostIps_";
  protected final String TEXT_144 = "=";
  protected final String TEXT_145 = "+\":\"+";
  protected final String TEXT_146 = ";" + NL + "\t\tme.prettyprint.cassandra.service.CassandraHostConfigurator hosts_";
  protected final String TEXT_147 = " = new me.prettyprint.cassandra.service.CassandraHostConfigurator(hostIps_";
  protected final String TEXT_148 = ");" + NL + "\t\tjava.util.Map<String, String> credentials_";
  protected final String TEXT_149 = " = new java.util.HashMap<String, String>();" + NL + "\t\t";
  protected final String TEXT_150 = NL + "\t\t\tcredentials_";
  protected final String TEXT_151 = ".put(\"username\",";
  protected final String TEXT_152 = ");" + NL + "\t\t\t";
  protected final String TEXT_153 = NL + "            ";
  protected final String TEXT_154 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_155 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_156 = ");";
  protected final String TEXT_157 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_158 = " = ";
  protected final String TEXT_159 = "; ";
  protected final String TEXT_160 = NL + "\t\t\t" + NL + "\t\t\t" + NL + "\t\t\tcredentials_";
  protected final String TEXT_161 = ".put(\"password\",decryptedPassword_";
  protected final String TEXT_162 = ");" + NL + "\t\t";
  protected final String TEXT_163 = NL + "    \tcluster_";
  protected final String TEXT_164 = " = me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster(\"cluster_";
  protected final String TEXT_165 = "_\"+pid,hosts_";
  protected final String TEXT_166 = ",credentials_";
  protected final String TEXT_167 = ");" + NL + "    \tresourceMap.put(\"cluster_";
  protected final String TEXT_168 = "\", cluster_";
  protected final String TEXT_169 = ");" + NL + "\t";
  protected final String TEXT_170 = NL + "\tme.prettyprint.cassandra.model.ConfigurableConsistencyLevel clpolicy_";
  protected final String TEXT_171 = " = new me.prettyprint.cassandra.model.ConfigurableConsistencyLevel();" + NL + "\tme.prettyprint.hector.api.HConsistencyLevel consistencyLevel_";
  protected final String TEXT_172 = " = me.prettyprint.hector.api.HConsistencyLevel.ONE;" + NL + "\tclpolicy_";
  protected final String TEXT_173 = ".setDefaultWriteConsistencyLevel(consistencyLevel_";
  protected final String TEXT_174 = ");" + NL + "\t";
  protected final String TEXT_175 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_176 = " - Dropping a  keyspace [\"+";
  protected final String TEXT_177 = "+\"].\");" + NL + "\t\t";
  protected final String TEXT_178 = NL + "\t\tcluster_";
  protected final String TEXT_179 = ".dropKeyspace(";
  protected final String TEXT_180 = ", true);" + NL + "\t\t";
  protected final String TEXT_181 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_182 = " - Dropped keyspace [\"+";
  protected final String TEXT_183 = "+\"] successfully.\");" + NL + "\t\t\tlog.info(\"";
  protected final String TEXT_184 = " - Adding a keyspace [\"+";
  protected final String TEXT_185 = "+\"].\");" + NL + "\t\t";
  protected final String TEXT_186 = NL + "\t\tcluster_";
  protected final String TEXT_187 = ".addKeyspace(new me.prettyprint.cassandra.service.ThriftKsDef(";
  protected final String TEXT_188 = "), true);" + NL + "\t\t";
  protected final String TEXT_189 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_190 = " - Added keyspace [\"+";
  protected final String TEXT_191 = "+\"] successfully.\");" + NL + "\t\t";
  protected final String TEXT_192 = NL + "\t\tcluster_";
  protected final String TEXT_193 = ".addKeyspace(new me.prettyprint.cassandra.service.ThriftKsDef(";
  protected final String TEXT_194 = "), true);" + NL + "\t";
  protected final String TEXT_195 = NL + "\t\tif (cluster_";
  protected final String TEXT_196 = ".describeKeyspace(";
  protected final String TEXT_197 = ") != null) {" + NL + "\t\t\t";
  protected final String TEXT_198 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_199 = " - Dropping a  keyspace [\"+";
  protected final String TEXT_200 = "+\"].\");" + NL + "\t\t\t";
  protected final String TEXT_201 = NL + "      \t\tcluster_";
  protected final String TEXT_202 = ".dropKeyspace(";
  protected final String TEXT_203 = ", true);" + NL + "      \t\t";
  protected final String TEXT_204 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_205 = " - Dropped keyspace [\"+";
  protected final String TEXT_206 = "+\"] successfully.\");" + NL + "\t\t\t";
  protected final String TEXT_207 = NL + "   \t\t}" + NL + "   \t\t";
  protected final String TEXT_208 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_209 = " - Adding a  keyspace [\"+";
  protected final String TEXT_210 = "+\"].\");" + NL + "\t\t";
  protected final String TEXT_211 = NL + "   \t\tcluster_";
  protected final String TEXT_212 = ".addKeyspace(new me.prettyprint.cassandra.service.ThriftKsDef(";
  protected final String TEXT_213 = "), true);" + NL + "\t\t";
  protected final String TEXT_214 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_215 = " - Added keyspace [\"+";
  protected final String TEXT_216 = "+\"] successfully.\");" + NL + "\t\t";
  protected final String TEXT_217 = NL + "\t\tif (cluster_";
  protected final String TEXT_218 = ".describeKeyspace(";
  protected final String TEXT_219 = ") == null) {" + NL + "\t\t\t";
  protected final String TEXT_220 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_221 = " - Adding a  keyspace [\"+";
  protected final String TEXT_222 = "+\"].\");" + NL + "\t\t\t";
  protected final String TEXT_223 = NL + "      \t\tcluster_";
  protected final String TEXT_224 = ".addKeyspace(new me.prettyprint.cassandra.service.ThriftKsDef(";
  protected final String TEXT_225 = "), true);" + NL + "      \t\t";
  protected final String TEXT_226 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_227 = " - Added keyspace [\"+";
  protected final String TEXT_228 = "+\"] successfully.\");" + NL + "\t\t\t";
  protected final String TEXT_229 = NL + "   \t\t}" + NL + "\t";
  protected final String TEXT_230 = NL + "    keyspace_";
  protected final String TEXT_231 = " = me.prettyprint.hector.api.factory.HFactory.createKeyspace(";
  protected final String TEXT_232 = ", cluster_";
  protected final String TEXT_233 = ",clpolicy_";
  protected final String TEXT_234 = ");" + NL + "\t";
  protected final String TEXT_235 = NL + "\t\tme.prettyprint.hector.api.ddl.ColumnFamilyDefinition cfDefinition_";
  protected final String TEXT_236 = " = me.prettyprint.hector.api.factory.HFactory.createColumnFamilyDefinition(";
  protected final String TEXT_237 = ",";
  protected final String TEXT_238 = "); " + NL + "\t\tcfDefinition_";
  protected final String TEXT_239 = ".setColumnType(me.prettyprint.hector.api.ddl.ColumnType.";
  protected final String TEXT_240 = ");" + NL + "\t\t";
  protected final String TEXT_241 = NL + "\t\t\t\tcfDefinition_";
  protected final String TEXT_242 = ".setSubComparatorType(me.prettyprint.hector.api.ddl.ComparatorType.getByClassName(\"";
  protected final String TEXT_243 = "\"));" + NL + "\t\t\t";
  protected final String TEXT_244 = NL + "\t\t\t\tcfDefinition_";
  protected final String TEXT_245 = ".setComparatorType(me.prettyprint.hector.api.ddl.ComparatorType.getByClassName(\"";
  protected final String TEXT_246 = "\"));" + NL + "\t\t\t";
  protected final String TEXT_247 = NL + "\t\t\t\t\t\t\tcfDefinition_";
  protected final String TEXT_248 = ".setKeyValidationClass(me.prettyprint.hector.api.ddl.ComparatorType.getByClassName(\"";
  protected final String TEXT_249 = "\").getClassName());" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_250 = NL + "\t\t\t\t\t\t\tcfDefinition_";
  protected final String TEXT_251 = ".setComparatorType(me.prettyprint.hector.api.ddl.ComparatorType.getByClassName(\"";
  protected final String TEXT_252 = "\"));" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_253 = NL + "\t\t\t\t\t\t\tme.prettyprint.cassandra.model.BasicColumnDefinition colDef_";
  protected final String TEXT_254 = "_";
  protected final String TEXT_255 = " = new me.prettyprint.cassandra.model.BasicColumnDefinition();" + NL + "\t\t\t\t\t\t\tcolDef_";
  protected final String TEXT_256 = "_";
  protected final String TEXT_257 = ".setName(me.prettyprint.cassandra.serializers.StringSerializer.get().toByteBuffer(\"";
  protected final String TEXT_258 = "\"));" + NL + "\t\t\t\t\t\t\tcolDef_";
  protected final String TEXT_259 = "_";
  protected final String TEXT_260 = ".setValidationClass(me.prettyprint.hector.api.ddl.ComparatorType.getByClassName(\"";
  protected final String TEXT_261 = "\").getClassName());" + NL + "\t\t\t\t\t\t\tcfDefinition_";
  protected final String TEXT_262 = ".addColumnDefinition(colDef_";
  protected final String TEXT_263 = "_";
  protected final String TEXT_264 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_265 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_266 = " - Dropping a ";
  protected final String TEXT_267 = " column family [\"+";
  protected final String TEXT_268 = "+\"] from keyspace [\"+";
  protected final String TEXT_269 = "+\"].\");" + NL + "\t\t\t";
  protected final String TEXT_270 = NL + "\t\t\tcluster_";
  protected final String TEXT_271 = ".dropColumnFamily(";
  protected final String TEXT_272 = ",";
  protected final String TEXT_273 = ", true);" + NL + "\t\t\t";
  protected final String TEXT_274 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_275 = " - Dropped column family [\"+";
  protected final String TEXT_276 = "+\"] successfully.\");" + NL + "\t\t\t";
  protected final String TEXT_277 = NL + "\t\t\ttry {" + NL + "\t\t\t\t";
  protected final String TEXT_278 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_279 = " - Dropping a ";
  protected final String TEXT_280 = " column family [\"+";
  protected final String TEXT_281 = "+\"] from keyspace [\"+";
  protected final String TEXT_282 = "+\"].\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_283 = NL + "\t\t\t\t\tcluster_";
  protected final String TEXT_284 = ".dropColumnFamily(";
  protected final String TEXT_285 = ",";
  protected final String TEXT_286 = ", true);" + NL + "\t\t\t\t\t";
  protected final String TEXT_287 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_288 = " - Dropped column family [\"+";
  protected final String TEXT_289 = "+\"] successfully.\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_290 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_291 = " - Adding a ";
  protected final String TEXT_292 = " column family [\"+";
  protected final String TEXT_293 = "+\"] in keyspace [\"+";
  protected final String TEXT_294 = "+\"].\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_295 = NL + "\t\t\t\t\tcluster_";
  protected final String TEXT_296 = ".addColumnFamily(cfDefinition_";
  protected final String TEXT_297 = ", true);" + NL + "\t\t\t\t\t";
  protected final String TEXT_298 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_299 = " - Added column family [\"+";
  protected final String TEXT_300 = "+\"] successfully.\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_301 = NL + "\t\t\t} catch (Exception e_";
  protected final String TEXT_302 = ") {" + NL + "\t\t\t\t";
  protected final String TEXT_303 = NL + "\t\t\t\t\t}finally{" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_304 = NL + "\t\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_305 = " - Adding a ";
  protected final String TEXT_306 = " column family [\"+";
  protected final String TEXT_307 = "+\"] in keyspace [\"+";
  protected final String TEXT_308 = "+\"].\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_309 = NL + "\t\t\t\t\t\tcluster_";
  protected final String TEXT_310 = ".addColumnFamily(cfDefinition_";
  protected final String TEXT_311 = ", true);" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_312 = NL + "\t\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_313 = " - Added column family [\"+";
  protected final String TEXT_314 = "+\"] successfully.\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_315 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_316 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_317 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_318 = " - Adding a ";
  protected final String TEXT_319 = " column family [\"+";
  protected final String TEXT_320 = "+\"] in keyspace [\"+";
  protected final String TEXT_321 = "+\"].\");" + NL + "\t\t\t";
  protected final String TEXT_322 = NL + "\t\t\tcluster_";
  protected final String TEXT_323 = ".addColumnFamily(cfDefinition_";
  protected final String TEXT_324 = ", true);" + NL + "\t\t\t";
  protected final String TEXT_325 = NL + "\t\t\t\tlog.info(\"";
  protected final String TEXT_326 = " - Added column family [\"+";
  protected final String TEXT_327 = "+\"] successfully.\");" + NL + "\t\t\t";
  protected final String TEXT_328 = NL + "\t\tme.prettyprint.hector.api.mutation.Mutator mutator_";
  protected final String TEXT_329 = " = null;" + NL + "\t\t";
  protected final String TEXT_330 = NL + "\t\t\t\t\t\t\tme.prettyprint.hector.api.Serializer keySerializer_";
  protected final String TEXT_331 = " =me.prettyprint.cassandra.serializers.";
  protected final String TEXT_332 = ".get();" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_333 = NL + "\t\t\t\t\t\t\tme.prettyprint.hector.api.Serializer sNameSerializer_";
  protected final String TEXT_334 = " =me.prettyprint.cassandra.serializers.";
  protected final String TEXT_335 = ".get();" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_336 = NL + "\t\t\t     \tme.prettyprint.cassandra.serializers.StringSerializer serializer_";
  protected final String TEXT_337 = " = me.prettyprint.cassandra.serializers.StringSerializer.get();" + NL + "\t\t\t    \tmutator_";
  protected final String TEXT_338 = " = me.prettyprint.hector.api.factory.HFactory.createMutator(keyspace_";
  protected final String TEXT_339 = ", keySerializer_";
  protected final String TEXT_340 = ");" + NL + "\t\t\t    \t";
  protected final String TEXT_341 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_342 = " - Start to write data into database .\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_343 = NL + "        \t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    
    
// Check the presence of a tCassandraConnection
{
    boolean useExistingConnection = ElementParameterParser.getBooleanValue(node,"__USE_EXISTING_CONNECTION__");
    if (useExistingConnection) {
        String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
        if (connection.isEmpty()) {
            return "if (true) {throw new IOException(\"You have to selection a connection\");}";
        }
    }
}

    stringBuffer.append(TEXT_2);
    
//TODO: use API_SELECTED instead of this
class API_selector{
	public boolean useDatastax(INode node){
		String dbVersion = ElementParameterParser.getValue(node, "__DB_VERSION__");
        String apiType = ElementParameterParser.getValue(node, "__API_TYPE__");
        return "CASSANDRA_2_2".equals(dbVersion) || "CASSANDRA_3_0".equals(dbVersion) || ("CASSANDRA_2_0_0".equals(dbVersion) && "DATASTAX".equals(apiType)); 
	}
}	

    
	if((new API_selector()).useDatastax(ElementParameterParser.getBooleanValue(node, "__USE_EXISTING_CONNECTION__") ? ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__") : node)){
	
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    
	class Column{

        IMetadataColumn column;
        private String mark = "?";
        private String assignmentOperation = "=";
        private Column assignmentKey;
        private boolean asColumnKey = false;
        public Column(IMetadataColumn column){
            this.column = column;
        }
        public String getName(){
            return column.getLabel();
        }
        public String getDBName(){
        	return column.getOriginalDbColumnName();
        }
        public String getTalendType(){
        	return column.getTalendType();
        }
        public String getDBType(){
        	return column.getType();
        }
        public JavaType getJavaType(){
        	return JavaTypesManager.getJavaTypeFromId(getTalendType());
        }
        public boolean isObject(){
        	return !JavaTypesManager.isJavaPrimitiveType(getJavaType(), column.isNullable());
        }
        public boolean isKey(){
        	return column.isKey();
        }
        public String getMark(){
        	return mark;
        }
        public void setMark(String mark){
        	this.mark = mark;
        }
        public void setAssignmentOperation(String op){
            this.assignmentOperation = op;
        }
        public String getAssignmentOperation(){
            return assignmentOperation;
        }
        public void setAssignmentKey(Column keyColumn){
            this.assignmentKey = keyColumn;
        }
        public Column getAssignmentKey(){
            return assignmentKey;
        }
        public void setAsColumnKey(boolean asColumnKey){
            this.asColumnKey = asColumnKey;
        }
        public boolean getAsColumnKey(){
            return asColumnKey;
        }
    }
    
	class CQLManager{
		private String CASSANDRA = "cassandra_datastax_id";
		private String CASSANDRA22 = "cassandra22_datastax_id";
        
        private String[] KeyWords = {"ADD","ALL","ALLOW","ALTER","AND","ANY","APPLY","AS","ASC","ASCII","AUTHORIZE","BATCH","BEGIN","BIGINT","BLOB","BOOLEAN","BY","CLUSTERING","COLUMNFAMILY","COMPACT","CONSISTENCY","COUNT","COUNTER","CREATE","CUSTOM","DECIMAL","DELETE","DESC","DISTINCT","DOUBLE","DROP","EACH_QUORUM","EXISTS","FILTERING","FLOAT","FROM","frozen","GRANT","IF","IN","INDEX","INET","INFINITY","INSERT","INT","INTO","KEY","KEYSPACE","KEYSPACES","LEVEL","LIMIT","LIST","LOCAL_ONE","LOCAL_QUORUM","MAP","MODIFY","NAN","NORECURSIVE","NOSUPERUSER","NOT","OF","ON","ONE","ORDER","PASSWORD","PERMISSION","PERMISSIONS","PRIMARY","QUORUM","RENAME","REVOKE","SCHEMA","SELECT","SET","STATIC","STORAGE","SUPERUSER","TABLE","TEXT","TIMESTAMP","TIMEUUID","THREE","TO","TOKEN","TRUNCATE","TTL","TWO","TYPE","UNLOGGED","UPDATE","USE","USER","USERS","USING","UUID","VALUES","VARCHAR","VARINT","WHERE","WITH","WRITETIME"};
        
        private INode node;
        private String action;
        private String keyspace;
        private String tableName;
        private Boolean useSpark = false;
        private Boolean version22 = true;
        private List<Column> valueColumns;
        private String apiPackageName = "com.datastax.driver.";

        public CQLManager(INode node, List<IMetadataColumn> columnList){
        	this.node = node;
            this.action = ElementParameterParser.getValue(node, "__DATA_ACTION__");
            this.keyspace = "StringHandling.DQUOTE(" + ElementParameterParser.getValue(node, "__KEY_SPACE__") + ")";
            this.tableName = this.keyspace + " + \".\" + " + "StringHandling.DQUOTE(" + ElementParameterParser.getValue(node, "__COLUMN_FAMILY__") + ")";
            createColumnList(columnList);
            this.valueColumns = collectValueColumns();
        }
        
        public CQLManager(INode node, List<IMetadataColumn> columnList, boolean version22, boolean useSpark){
            this(node, columnList);
            this.version22 = version22;
            this.useSpark = useSpark;
            this.apiPackageName = "shade.com.datastax.spark.connector.driver.";
        }

        private List<Column> all;
    	private List<Column> keys;
    	private List<Column> normals;
    	private List<Column> conditions;
    	private Column ttl;
    	private Column timestamp;

        private void createColumnList(List<IMetadataColumn> columnList){
            all = new ArrayList<Column>();            
            for(IMetadataColumn column : columnList){
                all.add(new Column(column));
            }
            keys = new ArrayList<Column>();
    		normals = new ArrayList<Column>();
    		conditions = new ArrayList<Column>();
			boolean usingTimestamp = "true".equals(ElementParameterParser.getValue(node, "__USING_TIMESTAMP__"));
			String timestampColName = ElementParameterParser.getValue(node, "__TIMESTAMP__");
			for(Column column : all){
    			if("INSERT".equals(action) || "UPDATE".equals(action)){
					boolean usingTTL = "true".equals(ElementParameterParser.getValue(node, "__USING_TTL__"));
					String ttlColName = ElementParameterParser.getValue(node, "__TTL__");
    				if(usingTTL && ttlColName.equals(column.getName())){
    					ttl = column;
    					ttl.setMark("TTL ?");
    					continue;
    				}
    			}
				if(usingTimestamp && timestampColName.equals(column.getName())){
					timestamp = column;
					timestamp.setMark("TIMESTAMP ?");
					continue;
				}
				if(column.isKey()){
    				keys.add(column);
    				continue;
    			}
    			if("UPDATE".equals(action) || ("DELETE".equals(action) && !"true".equals(ElementParameterParser.getValue(node, "__DELETE_IF_EXISTS__")))){
    				List<Map<String,String>> ifCoditions = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__IF_CONDITION__");
    				boolean matched = false;
    				for(Map<String, String> ifCodition : ifCoditions){
    					if(ifCodition.get("COLUMN_NAME").equals(column.getName())){
    						conditions.add(column);
    						matched = true;
    						continue;
    					}
    				}
    				if(matched){
    					continue;
    				}
    			}
    			normals.add(column);
			}
            if("UPDATE".equals(action)){
                List<Map<String,String>> assignOperations = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ASSIGNMENT_OPERATION__");
                List<Column> keyColumns = new ArrayList<Column>();
                for(Column column : normals){
                    for(Map<String, String> operation : assignOperations){
                        String updateColumnKeyName = operation.get("KEY_COLUMN");
                        String updateColumnOperation = operation.get("OPERATION");
                        if("p/k".equals(updateColumnOperation) && column.getName().equals(updateColumnKeyName)){
                            keyColumns.add(column);
                        }
                    }
                }
                normals.removeAll(keyColumns);
                for(Column column : normals){
                    for(Map<String, String> operation : assignOperations){
                        String updateColumnName = operation.get("COLUMN_NAME");
                        String updateColumnKeyName = operation.get("KEY_COLUMN");
                        String updateColumnOperation = operation.get("OPERATION");
                        if(updateColumnName.equals(column.getName())){
                            column.setAssignmentOperation(updateColumnOperation);
                            if("p/k".equals(updateColumnOperation)){
                                for(Column keyColumn : keyColumns){
                                    if(keyColumn.getName().equals(updateColumnKeyName)){
                                        column.setAssignmentKey(keyColumn);
                                    }
                                }
                            }
                            continue;
                        }
                    }
                }
            }
            if("DELETE".equals(action)){
                List<Map<String,String>> columnsKey = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__DELETE_COLUMN_BY_POSITION_KEY__");
                for(Column column : normals){
                    for(Map<String, String> columnKey : columnsKey){
                        if(column.getName().equals(columnKey.get("COLUMN_NAME"))){
                            column.setAsColumnKey(true);
                        }
                    }
                }
            }
        }
        private List<Column> collectValueColumns(){
        	List<Column> columns = new ArrayList<Column>();
        	if("INSERT".equals(action)){
	        	columns.addAll(keys);
	        	columns.addAll(normals);
	        	if(ttl != null)
	        		columns.add(ttl);
	        	if(timestamp != null)
	        		columns.add(timestamp);
        	}else if("UPDATE".equals(action)){
	        	if(ttl != null)
	        		columns.add(ttl);
	        	if(timestamp != null)
	        		columns.add(timestamp);
	            for(Column normal : normals){
	                if(normal.getAssignmentKey() != null){
	                    columns.add(normal.getAssignmentKey());
	                }
	                columns.add(normal);
	            }
	        	columns.addAll(keys);
	        	columns.addAll(conditions);
        	}else if("DELETE".equals(action)){
	            for(Column column : normals){
	                if(column.getAsColumnKey()){
	                    columns.add(column);
	                }
	            }
	        	if(timestamp != null)
	        		columns.add(timestamp);
	        	columns.addAll(keys);
	        	boolean ifExist = "true".equals(ElementParameterParser.getValue(node, "__DELETE_IF_EXISTS__"));
	            if(!ifExist){
		        	columns.addAll(conditions);
		        }
		    }
        	return columns;
        }
        protected String getDBMSId(){
            if(version22){
            	return CASSANDRA22;
            }else{
            	return CASSANDRA;
            }
        }
        private String getLProtectedChar(String keyword){
            return "\\\""; 
        }
        private String getRProtectedChar(String keyword){
            return "\\\"";
        }  
        private String wrapProtectedChar(String keyword){
        	if(keyword.matches("^[a-z]+$")){
        		return keyword;
        	}else{
        		return getLProtectedChar(keyword) + keyword + getRProtectedChar(keyword);
        	}
        }
        public List<String> getValueColumns(){
        	java.util.List<String> valueColumnsName = new java.util.ArrayList<String>();
        	for(Column col : valueColumns){
        		valueColumnsName.add(col.getName());
        	}
        	return valueColumnsName;
        }
        public String getDropKSSQL(boolean ifExists){
        	StringBuilder dropKSSQL = new StringBuilder();
        	dropKSSQL.append("\"DROP KEYSPACE ");
        	if(ifExists){
        		dropKSSQL.append("IF EXISTS ");
        	}
        	dropKSSQL.append("\" + ");
        	dropKSSQL.append(this.keyspace);
        	return dropKSSQL.toString();
        }
        public String getCreateKSSQL(boolean ifNotExists){
        	StringBuilder createKSSQL = new StringBuilder();
        	createKSSQL.append("\"CREATE KEYSPACE ");
        	if(ifNotExists){
        		createKSSQL.append("IF NOT EXISTS ");
        	}
        	createKSSQL.append("\" + ");
        	createKSSQL.append(this.keyspace);
        	createKSSQL.append(" + \"");
        	createKSSQL.append("WITH REPLICATION = {\'class\' : \'" + ElementParameterParser.getValue(this.node, "__REPLICA_STRATEGY__") + "\',");
        	if("SimpleStrategy".equals(ElementParameterParser.getValue(this.node, "__REPLICA_STRATEGY__"))){
        		createKSSQL.append("'replication_factor' : \" + " + ElementParameterParser.getValue(this.node, "__SIMEPLE_REPLICA_NUMBER__") + " + \"}\"");
        	}else{
        		List<Map<String, String>> replicas = ElementParameterParser.getTableValue(this.node, "__NETWORK_REPLICA_TABLE__");
        		int count = 1;
        		for(Map<String, String> replica : replicas){
        			createKSSQL.append("\'\" + " + replica.get("DATACENTER_NAME") + " + \"\' : \" + " + replica.get("REPLICA_NUMBER") + " + \"");
        			if(count < replicas.size()){
        				createKSSQL.append(",");
        			}
        			count++;
        		}
        		createKSSQL.append("}\"");
        	}
        	
        	return createKSSQL.toString();
        }
 	    public String getDropTableSQL(boolean ifExists){
 	    	StringBuilder dropTableSQL = new StringBuilder();
            dropTableSQL.append("\"DROP TABLE ");
        	if(ifExists){
	            dropTableSQL.append("IF EXISTS ");
        	}
            dropTableSQL.append("\" + " + tableName);
            return dropTableSQL.toString();
 	    }   
        public String getCreateTableSQL(boolean ifNotExists){
            StringBuilder createSQL = new StringBuilder();
            createSQL.append("\"CREATE TABLE ");
            if(ifNotExists){
            	createSQL.append("IF NOT EXISTS ");
            }
            createSQL.append("\" + " + tableName + " + \"(");
            List<Column> columns = new ArrayList<Column>();
            if(!"DELETE".equals(action)){
            	columns.addAll(keys);
            	columns.addAll(normals);
            	if("UPDATE".equals(action)){
            		columns.addAll(conditions);
            	}
            }
            int count = 1;
            for(Column column : columns){
                createSQL.append(wrapProtectedChar(column.getDBName()));
                createSQL.append(" ");
                createSQL.append(validateDBType(column));
				if(count < columns.size()){
                	createSQL.append(",");
                }
                count++;
            }
            if(keys.size() > 0){
                createSQL.append(",PRIMARY KEY(");
                int i = 1;
                for(Column column : keys){
                    createSQL.append(wrapProtectedChar(column.getDBName()));
                    if(i < keys.size()){
                        createSQL.append(",");
                    }
                    i++;
                }
                createSQL.append(")");
            }
            createSQL.append(")\"");
            return createSQL.toString();
        }
        public boolean containsUnsupportTypes(){
        	boolean unsupport = false;
        	List<String> unsupportTypes = java.util.Arrays.asList(new String[]{"set", "list", "map"});
        	List<Column> columns = new ArrayList<Column>();
            if("INSERT".equals(action)){
            	columns.addAll(keys);
            	columns.addAll(normals);
            }
            for(Column column : columns){
                if(unsupportTypes.contains(validateDBType(column))){
                	return true;
                }
            }
            return false;
        }
        public String getDeleteTableSQL(){
            StringBuilder deleteTableSQL = new StringBuilder();
            deleteTableSQL.append("\"DELETE FROM \" + " + tableName);
            return deleteTableSQL.toString();
        }
        public String getTruncateTableSQL(){
            StringBuilder truncateTableSQL = new StringBuilder();
            truncateTableSQL.append("\"TRUNCATE \" + " + tableName);
            return truncateTableSQL.toString();
        }
        public String generatePreActionSQL(){
        	if("INSERT".equals(action)){
        		return generatePreInsertSQL();
        	}else if("UPDATE".equals(action)){
        		return generatePreUpdateSQL();
        	}else if("DELETE".equals(action)){
        		return generatePreDeleteSQL();
        	}else{
        		return "";
        	}
        }
        public String generateStmt(String assignStmt, String inConnName){
        	if("INSERT".equals(action) || "UPDATE".equals(action) || "DELETE".equals(action)){
	        	StringBuilder stmt = new StringBuilder();
	        	int index = 0;
	        	for(Column column : valueColumns){
	        		stmt.append(generateSetStmt(assignStmt, column, inConnName, index));
	        		index++;
	        	}
	        	return stmt.toString();
        	}else{
        		return "";
        	}
        }
        /*INSERT INTO table_name
		 *( identifier, column_name...)
		 *VALUES ( value, value ... )
		 *USING option AND option
		 */
        private String generatePreInsertSQL(){
        	List<Column> columns = new ArrayList<Column>();
        	columns.addAll(keys);
        	columns.addAll(normals);
        	
        	int count = 1;
        	StringBuilder preInsertSQL = new StringBuilder();
        	preInsertSQL.append("\"INSERT INTO \" + " + tableName + " + \" (");
        	for(Column column : columns){
                preInsertSQL.append(wrapProtectedChar(column.getDBName()));
				if(count < columns.size()){
                	preInsertSQL.append(",");
                }
                count++;
            }
            preInsertSQL.append(") VALUES (");
            count = 1;
        	for(Column column : columns){
                preInsertSQL.append(column.getMark());
				if(count < columns.size()){
                	preInsertSQL.append(",");
                }
                count++;
            }
            preInsertSQL.append(")");
            boolean ifNotExist = "true".equals(ElementParameterParser.getValue(node, "__INSERT_IF_NOT_EXISTS__"));
            if(ifNotExist){
            	preInsertSQL.append(" IF NOT EXISTS");
            }
            if(ttl != null || timestamp != null){
            	preInsertSQL.append(" USING ");
            	if(ttl != null){
            		preInsertSQL.append(ttl.getMark());
            		if(timestamp != null){
            			preInsertSQL.append(" AND ");
            		}
            	}
            	if(timestamp != null){
            		preInsertSQL.append(timestamp.getMark());
            	}
            }
            preInsertSQL.append("\"");
            return preInsertSQL.toString();
        }
        private String generatePreUpdateSQL(){
        	StringBuilder preUpdateSQL = new StringBuilder();
        	preUpdateSQL.append("\"UPDATE \" + " + tableName + "+ \"");
        	if(ttl != null || timestamp != null){
            	preUpdateSQL.append(" USING ");
            	if(ttl != null){
            		preUpdateSQL.append(ttl.getMark());
            		if(timestamp != null){
            			preUpdateSQL.append(" AND ");
            		}
            	}
            	if(timestamp != null){
            		preUpdateSQL.append(timestamp.getMark());
            	}
            }
            preUpdateSQL.append(" SET ");
        	int count = 1;
        	for(Column column : normals){
                
                String assignment = wrapProtectedChar(column.getDBName()) + "=" + column.getMark();
                
                if("+v".equals(column.getAssignmentOperation())){
                    assignment = wrapProtectedChar(column.getDBName()) + "=" + wrapProtectedChar(column.getDBName()) + "+" + column.getMark();     
                }else if("v+".equals(column.getAssignmentOperation())){
                    assignment = wrapProtectedChar(column.getDBName()) + "=" + column.getMark() + "+" + wrapProtectedChar(column.getDBName());     
                }else if("-".equals(column.getAssignmentOperation())){
                    assignment = wrapProtectedChar(column.getDBName()) + "=" + wrapProtectedChar(column.getDBName()) + "-" + column.getMark();     
                }else if("p/k".equals(column.getAssignmentOperation())){
                    assignment = wrapProtectedChar(column.getDBName()) + "[?]=" + column.getMark(); 
                }

                preUpdateSQL.append(assignment);

				if(count < normals.size()){
                	preUpdateSQL.append(",");
                }
                count++;
            }
            preUpdateSQL.append(" WHERE ");
            count = 1;
        	for(Column column : keys){
                preUpdateSQL.append(wrapProtectedChar(column.getDBName()));
                preUpdateSQL.append(rowKeyInList(column) ? " IN " : "=");
                preUpdateSQL.append(column.getMark());
				if(count < keys.size()){
                	preUpdateSQL.append(" AND ");
                }
                count++;
            }
            if(conditions.size() > 0){
         	   	preUpdateSQL.append(" IF ");
	            count = 1;
	            for(Column column : conditions){
	            	preUpdateSQL.append(wrapProtectedChar(column.getDBName()));
	            	preUpdateSQL.append("=");
                	preUpdateSQL.append(column.getMark());
	            	if(count < conditions.size()){
	                	preUpdateSQL.append(" AND ");
	                }
	                count++;	
	            }
            }
	        // can't work actually, even it supported on office document
            // boolean ifExist = "true".equals(ElementParameterParser.getValue(node, "__UPDATE_IF_EXISTS__"));
            // if(ifExist){
            // 	preUpdateSQL.append(" IF EXISTS");
            // }
            
            preUpdateSQL.append("\"");
            return preUpdateSQL.toString();

        }
        private boolean rowKeyInList(Column column){
            List<Map<String,String>> rowKeyInList = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ROW_KEY_IN_LIST__");
            for(Map<String, String> rowKey : rowKeyInList){
                if(column.getName().equals(rowKey.get("COLUMN_NAME"))){
                    return true;
                }
            }
            return false;
        }
        private String generatePreDeleteSQL(){
        	StringBuilder preDeleteSQL = new StringBuilder();
        	preDeleteSQL.append("\"DELETE ");
        	int count = 1;
        	for(Column column : normals){
                preDeleteSQL.append(wrapProtectedChar(column.getDBName()));
                if(column.getAsColumnKey()){
                    preDeleteSQL.append("[?]");
                }
				if(count < normals.size()){
                	preDeleteSQL.append(",");
                }
                count++;
            }
            preDeleteSQL.append(" FROM \" + " + tableName + " + \"");
        	if(timestamp != null){
            	preDeleteSQL.append(" USING ");
        		preDeleteSQL.append(timestamp.getMark());
            }
            if(keys.size() > 0){
                preDeleteSQL.append(" WHERE ");
                count = 1;
            	for(Column column : keys){
            		preDeleteSQL.append(wrapProtectedChar(column.getDBName()));
            		preDeleteSQL.append(rowKeyInList(column) ? " IN " : "=");
                    preDeleteSQL.append(column.getMark());
    				if(count < keys.size()){
                    	preDeleteSQL.append(" AND ");
                    }
                    count++;
                }
            }
            boolean ifExist = "true".equals(ElementParameterParser.getValue(node, "__DELETE_IF_EXISTS__"));
            if(ifExist){
            	preDeleteSQL.append(" IF EXISTS");
            }else{
	            if(conditions.size() > 0){
	         	   	preDeleteSQL.append(" IF ");
		            count = 1;
		            for(Column column : conditions){
		            	preDeleteSQL.append(wrapProtectedChar(column.getDBName()));
		            	preDeleteSQL.append("=");
	                	preDeleteSQL.append(column.getMark());
		            	if(count < conditions.size()){
		                	preDeleteSQL.append(" AND ");
		                }
		                count++;	
		            }
		        }
		    }
            preDeleteSQL.append("\"");
            return preDeleteSQL.toString();
        }
        private String validateDBType(Column column){
        	String dbType = column.getDBType();
        	if(dbType == null || "".equals(dbType.trim())){//TODO: throw error or use default value?
        		MappingTypeRetriever mappingType = MetadataTalendType.getMappingTypeRetriever(getDBMSId());
                dbType = mappingType.getDefaultSelectedDbType(column.getTalendType());
            } 
            return dbType;
        }
        private String generateSetStmt(String assignStmt, Column column, String inConnName, int index){
            String dbType = validateDBType(column);
            String columnValue = inConnName + "." + column.getName();
            StringBuilder setStmt = new StringBuilder();
            if(column.isObject()){
                setStmt.append("if(" + columnValue + " == null){\r\n");
                setStmt.append(assignStmt + ".setToNull(" + index + ");\r\n");
                setStmt.append("} else {");
            }

            if("ascii".equals(dbType) || "text".equals(dbType) || "varchar".equals(dbType)){
            	if(JavaTypesManager.STRING == column.getJavaType()){
                    setStmt.append(assignStmt +".setString(" + index + ", " + columnValue + ");\r\n");
            	}else if(JavaTypesManager.CHARACTER == column.getJavaType()){
            		setStmt.append(assignStmt +".setString(" + index + ", String.valueOf(" + columnValue + "));\r\n");
            	}
            }else if("timeuuid".equals(dbType) || "uuid".equals(dbType)){
                setStmt.append(assignStmt +".setUUID(" + index + ", java.util.UUID.fromString(" + columnValue + "));\r\n");
            }else if("varint".equals(dbType)){
                setStmt.append(assignStmt +".setVarint(" + index + ", (java.math.BigInteger)" + columnValue + ");\r\n");
            }else if("inet".equals(dbType)){
                setStmt.append(assignStmt +".setInet(" + index + ", (java.net.InetAddress)" + columnValue + ");\r\n");
            }else if("map".equals(dbType)){
                setStmt.append(assignStmt +".setMap(" + index + ", (java.util.Map)" + columnValue + ");\r\n");
            }else if("set".equals(dbType)){
                setStmt.append(assignStmt +".setSet(" + index + ", (java.util.Set)" + columnValue + ");\r\n");
            }else if("list".equals(dbType)){
                setStmt.append(assignStmt +".setList(" + index + ", " + columnValue + ");\r\n");
            }else if("boolean".equals(dbType)){
                setStmt.append(assignStmt +".setBool(" + index + ", " + columnValue + ");\r\n");
            }else if("blob".equals(dbType)){
                if (useSpark) {
                    setStmt.append(assignStmt +".setBytes(" + index + ", " + columnValue + ");\r\n");
                } else {
                    setStmt.append(assignStmt +".setBytes(" + index + ", java.nio.ByteBuffer.wrap(" + columnValue + "));\r\n");
                }
            }else if("timestamp".equals(dbType)){
            	if(version22){
                	setStmt.append(assignStmt +".setTimestamp(" + index + ", " + columnValue + ");\r\n");
                }else{
                	setStmt.append(assignStmt +".setDate(" + index + ", " + columnValue + ");\r\n");
                }
            }else if("decimal".equals(dbType)){
                setStmt.append(assignStmt +".setDecimal(" + index + ", " + columnValue + ");\r\n");
            }else if("double".equals(dbType)){
                setStmt.append(assignStmt +".setDouble(" + index + ", " + columnValue + ");\r\n");
            }else if("float".equals(dbType)){
                setStmt.append(assignStmt +".setFloat(" + index + ", " + columnValue + ");\r\n");
            }else if("int".equals(dbType)){
            	setStmt.append(assignStmt +".setInt(" + index + ", " + columnValue + ");\r\n");
            }else if("bigint".equals(dbType) || "count".equals(dbType)){
                setStmt.append(assignStmt +".setLong(" + index + ", " + columnValue + ");\r\n");
            }else if(version22){
            	if("smallint".equals(dbType)){
            		setStmt.append(assignStmt +".setShort(" + index + ", " + columnValue + ");\r\n");
            	}else if("tinyint".equals(dbType)){
            		setStmt.append(assignStmt +".setByte(" + index + ", " + columnValue + ");\r\n");
            	}else if("time".equals(dbType)){
            		setStmt.append(assignStmt +".setTime(" + index + ", " + columnValue + ");\r\n");
            	}else if("date".equals(dbType)){
            		setStmt.append(assignStmt +".setDate(" + index + ", "+apiPackageName+"core.LocalDate.fromMillisSinceEpoch(" + columnValue + ".getTime()));\r\n");
            	}
            }

            if(column.isObject()){
                setStmt.append("}\r\n");
            }
            return setStmt.toString();
        }
    }
    
    
class CassandraOutputGenHelper{
	INode node;
	String cid;
    IMetadataTable metadata = null;
    IConnection conn = null;
    List<IMetadataColumn> columns = null;
    
	CQLManager cqlManager;
	public CassandraOutputGenHelper(INode node){
		this.node = node;
	}
	
	public void init(){
		cid = node.getUniqueName();
		List<IMetadataTable> metadatas = node.getMetadataList();
	    if((metadatas!=null) && (metadatas.size() > 0)){
	        metadata = metadatas.get(0);
	        if(metadata != null){
	    		columns = metadata.getListColumns();		
	    	}
	    }
	    List<? extends IConnection> conns = node.getIncomingConnections();
	    if(conns != null && conns.size() > 0 && conns.get(0).getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)){
	        conn = conns.get(0);
	    }
        cqlManager = new CQLManager(node, columns);
	}
	public String getCid(){
		return cid;
	}
	public IMetadataTable getMetadata(){
		return metadata;
	}
	public IConnection getConn(){
		return conn;
	}
	public List<IMetadataColumn> getColumns(){
		return columns;
	}
	public boolean validGen(){
		return columns == null || columns.isEmpty() || conn == null;
	}
	public CQLManager getCQLManager(){
		return cqlManager;
	}
	public void genConn(){
		boolean useExistConn = ElementParameterParser.getBooleanValue(node, "__USE_EXISTING_CONNECTION__");
    	String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
    	if(useExistConn){
		
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_9);
    
	    }else{  
	    
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    
class SSL_HELPER{
	INode node;
	
	public SSL_HELPER(INode node){
		this.node = node;
	}
	
	public boolean useHTTPS() {
        return node != null;
    }
	
	public String getTSType(){
		return ElementParameterParser.getValue(node, "__TRUSTSTORE_TYPE__");
	}
	
	public String getTSPath(){
		return ElementParameterParser.getValue(node,"__SSL_TRUSTSERVER_TRUSTSTORE__");
	}
	
	public String getTSPwd(){
		return ElementParameterParser.getPasswordValue(node,"__SSL_TRUSTSERVER_PASSWORD__");
	}
	
	public boolean needKS(){
		return ElementParameterParser.getBooleanValue(node,"__NEED_CLIENT_AUTH__");
	}
	
	public String getKSType(){
		return ElementParameterParser.getValue(node, "__KEYSTORE_TYPE__");
	}
	
	public String getKSPath(){
		return ElementParameterParser.getValue(node,"__SSL_KEYSTORE__");
	}
	
	public String getKSPwd(){
		return ElementParameterParser.getPasswordValue(node,"__SSL_KEYSTORE_PASSWORD__");
	}
	
	public boolean needVerifyHostname(){
		return ElementParameterParser.getBooleanValue(node,"__VERIFY_NAME__");
	}
}	

    stringBuffer.append(TEXT_12);
    
    String host = ElementParameterParser.getValue(node,"__HOST__");
    String port = ElementParameterParser.getValue(node,"__PORT__");
    String userName = ElementParameterParser.getValue(node, "__USERNAME__");
    String passWord = ElementParameterParser.getPasswordValue(node, "__PASSWORD__");
	boolean authentication = ElementParameterParser.getBooleanValue(node, "__REQUIRED_AUTHENTICATION__");
	INode sslNode = ElementParameterParser.getLinkedNodeValue(node, "__HTTPS_SETTING__");
	SSL_HELPER sslHelper = new SSL_HELPER(sslNode);
	boolean useSSL = ElementParameterParser.getBooleanValue(node, "__USE_HTTPS__") && sslHelper.useHTTPS();
	if(useSSL){
	
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    if(sslHelper.needKS()){
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(sslHelper.getKSType());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(sslHelper.getKSPath());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(sslHelper.getKSPwd());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(sslHelper.getKSPwd());
    stringBuffer.append(TEXT_24);
    }
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(sslHelper.getTSType());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(sslHelper.getTSPath());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(sslHelper.getTSPwd());
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
    
	}
    
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(port);
    stringBuffer.append(TEXT_42);
    
                                                            	if(authentication){
                                                            	
    stringBuffer.append(TEXT_43);
    stringBuffer.append(userName);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(passWord);
    stringBuffer.append(TEXT_45);
    
                                                            	}
                                                            	if(useSSL){
                                                            	
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    
                                                            	}
                                                            	
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    
	    }
	    
    stringBuffer.append(TEXT_56);
    
	}
	
	public void genKSAction(){
		String keyspaceAction = ElementParameterParser.getValue(node, "__ACTION_ON_KEYSPACE__");
		String dataAction = ElementParameterParser.getValue(node, "__DATA_ACTION__");
		if(!"NONE".equals(keyspaceAction) && !"DELETE".equals(dataAction)){
	    	if("DROP_CREATE".equals(keyspaceAction)){
	    	
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cqlManager.getDropKSSQL(false));
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cqlManager.getCreateKSSQL(false));
    stringBuffer.append(TEXT_61);
    
	    	}else if("CREATE".equals(keyspaceAction)){
	    	
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cqlManager.getCreateKSSQL(false));
    stringBuffer.append(TEXT_64);
    
	    	}else if("CREATE_IF_NOT_EXISTS".equals(keyspaceAction)){
	    	
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cqlManager.getCreateKSSQL(true));
    stringBuffer.append(TEXT_67);
    
	    	}else if("DROP_IF_EXISTS_AND_CREATE".equals(keyspaceAction)){
	    	
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cqlManager.getDropKSSQL(true));
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cqlManager.getCreateKSSQL(false));
    stringBuffer.append(TEXT_72);
    
	    	}
	    }
	}
	
	public void genCFAction(){
		String tableAction = ElementParameterParser.getValue(node, "__ACTION_ON_COLUMN_FAMILY__");
	    String dataAction = ElementParameterParser.getValue(node, "__DATA_ACTION__");
		if(!"NONE".equals(tableAction) && !"DELETE".equals(dataAction)){
			if("DROP_CREATE".equals(tableAction)){
			
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cqlManager.getDropTableSQL(false));
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cqlManager.getCreateTableSQL(false));
    stringBuffer.append(TEXT_77);
    
	            if(cqlManager.containsUnsupportTypes()){
	            
    stringBuffer.append(TEXT_78);
    
	            }
	            
    stringBuffer.append(TEXT_79);
    
			}else if("CREATE".equals(tableAction)){
			
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cqlManager.getCreateTableSQL(false));
    stringBuffer.append(TEXT_82);
    
	            if(cqlManager.containsUnsupportTypes()){
	            
    stringBuffer.append(TEXT_83);
    
	            }
	            
    stringBuffer.append(TEXT_84);
    
	        }else if("DROP_IF_EXISTS_AND_CREATE".equals(tableAction)){
	        
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cqlManager.getDropTableSQL(true));
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cqlManager.getCreateTableSQL(false));
    stringBuffer.append(TEXT_89);
    
	            if(cqlManager.containsUnsupportTypes()){
	            
    stringBuffer.append(TEXT_90);
    
	            }
	            
    stringBuffer.append(TEXT_91);
        
	        }else if("CREATE_IF_NOT_EXISTS".equals(tableAction)){
	        
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cqlManager.getCreateTableSQL(true));
    stringBuffer.append(TEXT_94);
    
	            if(cqlManager.containsUnsupportTypes()){
	            
    stringBuffer.append(TEXT_95);
    
	            }
	            
    stringBuffer.append(TEXT_96);
    
	        }else if("CLEAR".equals(tableAction)){
	        
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cqlManager.getDeleteTableSQL());
    stringBuffer.append(TEXT_99);
    
	        }else if("TRUNCATE".equals(tableAction)){
	        
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cqlManager.getTruncateTableSQL());
    stringBuffer.append(TEXT_102);
     
	        }
	    }
	}
	
	//DI only, better to move
	public void genInit(){
	
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cqlManager.generatePreActionSQL());
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    
	    if(ElementParameterParser.getBooleanValue(node, "__USE_UNLOGGED_BATCH__")){
    	
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    
	    	for(String col : cqlManager.getValueColumns()){
	    	
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(col);
    stringBuffer.append(TEXT_114);
    	
	    	}
	    	String batchClass = ElementParameterParser.getBooleanValue(node, "__GROUP_CACHE__") ? "BatchCacheExecutor" : "BatchExecutor";
	    	
    stringBuffer.append(TEXT_115);
    stringBuffer.append(batchClass);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(batchClass);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(ElementParameterParser.getValue(node, "__KEY_SPACE__"));
    stringBuffer.append(TEXT_120);
    stringBuffer.append(ElementParameterParser.getValue(node, "__COLUMN_FAMILY__"));
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(ElementParameterParser.getValue(node, "__GROUP_METHOD__"));
    stringBuffer.append(TEXT_123);
    stringBuffer.append(ElementParameterParser.getValue(node, "__BATCH_SIZE__"));
    stringBuffer.append(TEXT_124);
    stringBuffer.append(ElementParameterParser.getBooleanValue(node, "__ASYNC__"));
    stringBuffer.append(TEXT_125);
    stringBuffer.append(ElementParameterParser.getValue(node, "__CONCURRENT_TASKS__"));
    stringBuffer.append(TEXT_126);
    
	    }else{
	    
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_129);
    
	    }
	}
}

    
    CassandraOutputGenHelper outputGenHelper = new CassandraOutputGenHelper(node);
    outputGenHelper.init();
    if(outputGenHelper.validGen()){
    	return "";
    }
    outputGenHelper.genConn();
    outputGenHelper.genKSAction();
    outputGenHelper.genCFAction();
	outputGenHelper.genInit();
    
    stringBuffer.append(TEXT_130);
    
    }else{
    
    stringBuffer.append(TEXT_131);
    
	String host = ElementParameterParser.getValue(node,"__HOST__");
    String port = ElementParameterParser.getValue(node,"__PORT__");
    //String cluster= ElementParameterParser.getValue(node, "__CLUSTER__");
    String userName = ElementParameterParser.getValue(node, "__USERNAME__");
    String passWord = ElementParameterParser.getValue(node, "__PASSWORD__");
	String keySpace = ElementParameterParser.getValue(node,"__KEY_SPACE__");
	boolean authentication="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REQUIRED_AUTHENTICATION__"));
    boolean useExistingConnection = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
    String actionOnKeyspace= ElementParameterParser.getValue(node,"__ACTION_ON_KEYSPACE__");
    String actionOnColumnFamily= ElementParameterParser.getValue(node,"__ACTION_ON_COLUMN_FAMILY__");
    String columnFamily = ElementParameterParser.getValue(node,"__COLUMN_FAMILY__");
	String columnFamilyType =ElementParameterParser.getValue(node,"__COLUMN_FAMILY_TYPE__");
	
	String superKeyColumn = ElementParameterParser.getValue(node,"__SUPER_KEY_COLUMN__");
	String keyColumn = ElementParameterParser.getValue(node,"__KEY_COLUMN__");
	String comparator = ElementParameterParser.getValue(node,"__COMPARATOR__");
	boolean incKey = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__INC_KEY__"));
	boolean incSuperKey = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__INC_SUPER_KEY__"));
	boolean isDefine = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__DEFINE_CF_STRUCTS__"));
	
    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	
    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_136);
    
	if(useExistingConnection){
  		String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
		
    stringBuffer.append(TEXT_137);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_139);
    
		if(isLog4jEnabled){
		
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_142);
    
		}
	}else{
	
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(port);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    
		if (authentication){
		
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_151);
    stringBuffer.append(userName);
    stringBuffer.append(TEXT_152);
    
            String passwordFieldName = "__PASSWORD__";
            
    stringBuffer.append(TEXT_153);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_154);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_156);
    } else {
    stringBuffer.append(TEXT_157);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_158);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_159);
    }
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    
		}
		
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_168);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_169);
    
	}
	
    stringBuffer.append(TEXT_170);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_174);
    
	if("DROP_CREATE".equals(actionOnKeyspace)){
		if (isLog4jEnabled) {
		
    stringBuffer.append(TEXT_175);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_177);
    
		}
		
    stringBuffer.append(TEXT_178);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_180);
    
		if (isLog4jEnabled) {
		
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_185);
    
		}
		
    stringBuffer.append(TEXT_186);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_187);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_188);
    
		if (isLog4jEnabled) {
		
    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_191);
    
		}
	}
	if("CREATE".equals(actionOnKeyspace)){
	
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_193);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_194);
    
	}
	if("DROP_IF_EXISTS_AND_CREATE".equals(actionOnKeyspace)){
	
    stringBuffer.append(TEXT_195);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_196);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_197);
    
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_198);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_200);
    
			}
			
    stringBuffer.append(TEXT_201);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_203);
    
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_204);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_205);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_206);
    
			}
			
    stringBuffer.append(TEXT_207);
    
		if (isLog4jEnabled) {
		
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_210);
    
		}
		
    stringBuffer.append(TEXT_211);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_212);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_213);
    
		if (isLog4jEnabled) {
		
    stringBuffer.append(TEXT_214);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_215);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_216);
    
		}
	}
	if("CREATE_IF_NOT_EXISTS".equals(actionOnKeyspace)){
	
    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_219);
    
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_222);
    
			}
			
    stringBuffer.append(TEXT_223);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_224);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_225);
    
      		if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_226);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_227);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_228);
    
			}
      		
    stringBuffer.append(TEXT_229);
    
	}
	
    stringBuffer.append(TEXT_230);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_231);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_233);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_234);
    
    List<IMetadataTable> metadatas = node.getMetadataList();
	if(!"NONE".equals(actionOnColumnFamily)){
	
    stringBuffer.append(TEXT_235);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_238);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_239);
    stringBuffer.append(columnFamilyType);
    stringBuffer.append(TEXT_240);
    
		if(isDefine){
			if("SUPER".equals(columnFamilyType)){
			
    stringBuffer.append(TEXT_241);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_242);
    stringBuffer.append(comparator);
    stringBuffer.append(TEXT_243);
    
			}else{
			
    stringBuffer.append(TEXT_244);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_245);
    stringBuffer.append(comparator);
    stringBuffer.append(TEXT_246);
    
			}
			if(metadatas != null && metadatas.size() > 0){
	      		IMetadataTable metadata = metadatas.get(0);
	      		if(metadata != null){
	      			for (IMetadataColumn column : metadata.getListColumns()) {
	    				JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
	    				String columnType=JavaTypesManager.getTypeToGenerate(column.getTalendType(),true);
						String cType = column.getType();
						if("ObjectType".equalsIgnoreCase(cType)){
							cType="BytesType";
						}
	    				if(column.getLabel().equals(keyColumn)){
						
    stringBuffer.append(TEXT_247);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_248);
    stringBuffer.append(cType);
    stringBuffer.append(TEXT_249);
    
						}
						if("SUPER".equals(columnFamilyType) && column.getLabel().equals(superKeyColumn)){
						
    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cType);
    stringBuffer.append(TEXT_252);
    
						}
						if((!column.getLabel().equals(keyColumn) || incKey) || !("SUPER".equals(columnFamilyType) && column.getLabel().equals(superKeyColumn) && !incSuperKey)){
						
    stringBuffer.append(TEXT_253);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_254);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_255);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_256);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_257);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_258);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cType);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_262);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_263);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_264);
    
							}
						}
					}
				}
			}
		}
		if("DROP_CREATE".equals(actionOnColumnFamily)){
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_265);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_266);
    stringBuffer.append(columnFamilyType);
    stringBuffer.append(TEXT_267);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_268);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_269);
    
			}
			
    stringBuffer.append(TEXT_270);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_271);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_272);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_273);
    
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_274);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_275);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_276);
    
			}
		}
		if("DROP_IF_EXISTS_AND_CREATE".equals(actionOnColumnFamily) || "CREATE_IF_NOT_EXISTS".equals(actionOnColumnFamily)){
		
    stringBuffer.append(TEXT_277);
    
				if("DROP_IF_EXISTS_AND_CREATE".equals(actionOnColumnFamily)){
					if (isLog4jEnabled) {
					
    stringBuffer.append(TEXT_278);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_279);
    stringBuffer.append(columnFamilyType);
    stringBuffer.append(TEXT_280);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_281);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_282);
    
					}
					
    stringBuffer.append(TEXT_283);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_284);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_285);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_286);
    
					if (isLog4jEnabled) {
					
    stringBuffer.append(TEXT_287);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_288);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_289);
    
					}
				}else{
					if (isLog4jEnabled) {
					
    stringBuffer.append(TEXT_290);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_291);
    stringBuffer.append(columnFamilyType);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_293);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_294);
    
					}
					
    stringBuffer.append(TEXT_295);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_296);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_297);
    
					if (isLog4jEnabled) {
					
    stringBuffer.append(TEXT_298);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_300);
    
					}
 				}
				
    stringBuffer.append(TEXT_301);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_302);
    
				if("DROP_IF_EXISTS_AND_CREATE".equals(actionOnColumnFamily)){
				
    stringBuffer.append(TEXT_303);
    
						if (isLog4jEnabled) {
						
    stringBuffer.append(TEXT_304);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_305);
    stringBuffer.append(columnFamilyType);
    stringBuffer.append(TEXT_306);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_307);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_308);
    
						}
						
    stringBuffer.append(TEXT_309);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_310);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_311);
    
						if (isLog4jEnabled) {
						
    stringBuffer.append(TEXT_312);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_313);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_314);
    
						}
						
    stringBuffer.append(TEXT_315);
    
				}else{
				
    stringBuffer.append(TEXT_316);
    
				}
		}
		if("CREATE".equals(actionOnColumnFamily) || "DROP_CREATE".equals(actionOnColumnFamily)){
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_317);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_318);
    stringBuffer.append(columnFamilyType);
    stringBuffer.append(TEXT_319);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_320);
    stringBuffer.append(keySpace);
    stringBuffer.append(TEXT_321);
    
			}
			
    stringBuffer.append(TEXT_322);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_324);
    
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_325);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_326);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_327);
    
			}
		}
		
    stringBuffer.append(TEXT_328);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_329);
    
		if (node.getIncomingConnections() != null) {
			class TypeMap{
      			Map<String,String> typeMap=new java.util.HashMap<String,String>();
      			
      			public TypeMap(){
					typeMap.put("Boolean","BooleanSerializer");
					typeMap.put("byte[]","BytesArraySerializer");
					typeMap.put("java.util.Date","DateSerializer");
					typeMap.put("Double","DoubleSerializer");
					typeMap.put("Float","FloatSerializer");
					typeMap.put("Integer","IntegerSerializer");
					typeMap.put("Long","LongSerializer");
					typeMap.put("Object","ObjectSerializer");
					typeMap.put("Short","ShortSerializer");
					typeMap.put("String","StringSerializer");
					typeMap.put("Character","CharSerializer");
					typeMap.put("BigDecimal","BigDecimalSerializer");
      			}
      			public String get(String tType, String cType){
      				if("".equals(cType) || "Default".equals(cType)){
      					return typeMap.get(tType);
      				}else if("CounterColumnType".equals(cType)){
      					return "LongSerializer";
      				}else if("IntegerType".equals(cType)){
      					return "BigIntegerSerializer";
      				}else if("AsciiType".equals(cType)){
      					return "AsciiSerializer";
      				}else if("UUIDType".equals(cType)){
      					return "UUIDSerializer";
      				}else if("TimeUUIDType".equals(cType)){
      					return "TimeUUIDSerializer";
      				}else{
      					return typeMap.get(tType) == null ? "ObjectSerializer" : typeMap.get(tType);
      				}
      			}
      		}
     		TypeMap typeMap = new TypeMap();
			boolean rowKeyFlag=false;
			boolean superColFlag=false;
			for (IConnection incomingConn : node.getIncomingConnections()) {
				if (incomingConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
					IMetadataTable inputMetadataTable = incomingConn.getMetadataTable();
					for (IMetadataColumn column : inputMetadataTable.getListColumns()) {
	    				JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
	    				String columnType=JavaTypesManager.getTypeToGenerate(column.getTalendType(),true);
						String cType = column.getType();
	    				if(column.getLabel().equals(keyColumn)){
	    					rowKeyFlag=true;
	    					
    stringBuffer.append(TEXT_330);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_331);
    stringBuffer.append(typeMap.get(columnType, cType));
    stringBuffer.append(TEXT_332);
    
						}
						if("SUPER".equals(columnFamilyType) && column.getLabel().equals(superKeyColumn)){
							superColFlag=true;
							
    stringBuffer.append(TEXT_333);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_334);
    stringBuffer.append(typeMap.get(columnType, cType));
    stringBuffer.append(TEXT_335);
    
						}
						if(("STANDARD".equals(columnFamilyType) && rowKeyFlag)||("SUPER".equals(columnFamilyType) && rowKeyFlag && superColFlag)){
							break;
						}
					}
					
    stringBuffer.append(TEXT_336);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_337);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_338);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_339);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_340);
    
					if (isLog4jEnabled) {
					
    stringBuffer.append(TEXT_341);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_342);
    
					}
				}
			}
		}
		
    stringBuffer.append(TEXT_343);
    
    }
    
    return stringBuffer.toString();
  }
}
