package org.talend.designer.codegen.translators.databases.cassandra;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
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

public class TCassandraOutputMainJava
{
  protected static String nl;
  public static synchronized TCassandraOutputMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCassandraOutputMainJava result = new TCassandraOutputMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL + "    \t";
  protected final String TEXT_4 = NL + "\t";
  protected final String TEXT_5 = NL + "\t";
  protected final String TEXT_6 = NL + " ";
  protected final String TEXT_7 = NL + "        boundStmt_";
  protected final String TEXT_8 = " = new com.datastax.driver.core.BoundStatement(prepareStmt_";
  protected final String TEXT_9 = ");";
  protected final String TEXT_10 = NL + "    ";
  protected final String TEXT_11 = NL + "    ";
  protected final String TEXT_12 = NL + "\t\tcassandraBatchExec_";
  protected final String TEXT_13 = ".addOrExecBatch(boundStmt_";
  protected final String TEXT_14 = ");" + NL + "\t";
  protected final String TEXT_15 = NL + "        try{" + NL + "        \tconnection_";
  protected final String TEXT_16 = ".execute(boundStmt_";
  protected final String TEXT_17 = ");" + NL + "        }catch(java.lang.Exception e){" + NL + "        \t";
  protected final String TEXT_18 = NL + "        \t\tthrow(e);" + NL + "        \t";
  protected final String TEXT_19 = NL + "        \t\tSystem.err.println(e.getMessage());" + NL + "        \t";
  protected final String TEXT_20 = NL + "        }";
  protected final String TEXT_21 = NL + "    nb_line_";
  protected final String TEXT_22 = "++;";
  protected final String TEXT_23 = "    ";
  protected final String TEXT_24 = NL + "\t\t\t\ttry{" + NL + "\t\t\t\t\t";
  protected final String TEXT_25 = NL + "\t\t\t\t\t\tif (";
  protected final String TEXT_26 = ".";
  protected final String TEXT_27 = " != null) {" + NL + "\t\t\t\t\t";
  protected final String TEXT_28 = NL + "\t" + NL + "\t\t\t\t\t\tjava.util.List<me.prettyprint.hector.api.beans.HColumn<me.prettyprint.hector.api.Serializer,me.prettyprint.hector.api.Serializer>> innerColumn_";
  protected final String TEXT_29 = "=new " + NL + "\t\t\t    \t\tjava.util.ArrayList<me.prettyprint.hector.api.beans.HColumn<me.prettyprint.hector.api.Serializer,me.prettyprint.hector.api.Serializer>>();" + NL + "\t\t\t\t\t";
  protected final String TEXT_30 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_31 = "if(";
  protected final String TEXT_32 = ".";
  protected final String TEXT_33 = "!=null) { ";
  protected final String TEXT_34 = NL + "\t\t\t\t\t\t\t\t\t\tmutator_";
  protected final String TEXT_35 = ".addInsertion(" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_36 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_37 = ".";
  protected final String TEXT_38 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_39 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_40 = ".";
  protected final String TEXT_41 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_42 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_43 = ".";
  protected final String TEXT_44 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_45 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_46 = ".";
  protected final String TEXT_47 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_48 = NL + "\t\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\t\t\t\t, ";
  protected final String TEXT_49 = "," + NL + "\t\t\t\t\t\t\t\t\t\t\tme.prettyprint.hector.api.factory.HFactory.createColumn(" + NL + "\t\t\t\t\t\t\t\t\t\t\t\"";
  protected final String TEXT_50 = "\"," + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_51 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_52 = ".";
  protected final String TEXT_53 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_54 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_55 = ".";
  protected final String TEXT_56 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_57 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_58 = ".";
  protected final String TEXT_59 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_60 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_61 = ".";
  protected final String TEXT_62 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_63 = NL + "\t\t\t\t\t\t\t\t\t\t\t," + NL + "\t\t\t\t\t\t\t\t\t\t\tserializer_";
  protected final String TEXT_64 = "," + NL + "\t\t\t\t\t\t\t\t\t\t\tme.prettyprint.cassandra.serializers.";
  protected final String TEXT_65 = ".get()" + NL + "\t\t\t\t\t\t\t\t\t\t\t)" + NL + "\t\t\t\t\t\t\t\t\t\t);" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_66 = "} ";
  protected final String TEXT_67 = NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_68 = NL + "\t\t\t\t\t\t\t\t\t\tmutator_";
  protected final String TEXT_69 = ".addDeletion(" + NL + "\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_70 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_71 = ".";
  protected final String TEXT_72 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_73 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_74 = ".";
  protected final String TEXT_75 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_76 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_77 = ".";
  protected final String TEXT_78 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_79 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_80 = ".";
  protected final String TEXT_81 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_82 = NL + "\t\t\t\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\t\t\t, ";
  protected final String TEXT_83 = ",\"";
  protected final String TEXT_84 = "\",serializer_";
  protected final String TEXT_85 = ");" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_86 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_87 = "if(";
  protected final String TEXT_88 = ".";
  protected final String TEXT_89 = "!=null){ ";
  protected final String TEXT_90 = NL + "\t\t\t\t\t\t\t\t\t\tme.prettyprint.hector.api.beans.HColumn column_";
  protected final String TEXT_91 = "=" + NL + "\t\t\t\t\t\t\t\t\t\t\tme.prettyprint.hector.api.factory.HFactory.createColumn(" + NL + "\t\t\t\t\t\t\t\t\t\t\t\t\"";
  protected final String TEXT_92 = "\"," + NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_93 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_94 = ".";
  protected final String TEXT_95 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_96 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_97 = ".";
  protected final String TEXT_98 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_99 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_100 = ".";
  protected final String TEXT_101 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_102 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_103 = ".";
  protected final String TEXT_104 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_105 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t," + NL + "\t\t\t\t\t\t\t\t\t\t\t\tserializer_";
  protected final String TEXT_106 = "," + NL + "\t\t\t\t\t\t\t\t\t\t\t\tme.prettyprint.cassandra.serializers.";
  protected final String TEXT_107 = ".get()" + NL + "\t\t\t\t\t\t\t\t\t\t\t);" + NL + "\t\t\t\t\t\t\t\t\t\tinnerColumn_";
  protected final String TEXT_108 = ".add( column_";
  protected final String TEXT_109 = ");" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_110 = "} ";
  protected final String TEXT_111 = NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_112 = NL + "\t\t\t\t\t\t\t\t\t\tmutator_";
  protected final String TEXT_113 = ".addSubDelete(" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_114 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_115 = ".";
  protected final String TEXT_116 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_117 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_118 = ".";
  protected final String TEXT_119 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_120 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_121 = ".";
  protected final String TEXT_122 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_123 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_124 = ".";
  protected final String TEXT_125 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_126 = NL + "\t\t\t\t\t\t\t\t\t\t\t," + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_127 = NL + "\t\t\t\t\t\t\t\t\t\t\t," + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_128 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_129 = ".";
  protected final String TEXT_130 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_131 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_132 = ".";
  protected final String TEXT_133 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_134 = NL + "\t\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_135 = ".";
  protected final String TEXT_136 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_137 = NL + "\t\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_138 = ".";
  protected final String TEXT_139 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_140 = NL + "\t\t\t\t\t\t\t\t\t\t\t," + NL + "\t\t\t\t\t\t\t\t\t\t\t\"";
  protected final String TEXT_141 = "\",sNameSerializer_";
  protected final String TEXT_142 = ",serializer_";
  protected final String TEXT_143 = ");" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_144 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_145 = "if(";
  protected final String TEXT_146 = ".";
  protected final String TEXT_147 = "!=null){ ";
  protected final String TEXT_148 = NL + "\t\t\t\t\t\t\tmutator_";
  protected final String TEXT_149 = ".addInsertion(" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_150 = NL + "\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_151 = ".";
  protected final String TEXT_152 = NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_153 = NL + "\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_154 = ".";
  protected final String TEXT_155 = ")" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_156 = NL + "\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_157 = ".";
  protected final String TEXT_158 = ")" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_159 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_160 = ".";
  protected final String TEXT_161 = NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_162 = NL + "\t\t\t\t\t\t\t\t," + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_163 = "," + NL + "\t\t\t\t\t\t\t\tnew me.prettyprint.cassandra.model.HSuperColumnImpl(" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_164 = NL + "\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_165 = ".";
  protected final String TEXT_166 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_167 = NL + "\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_168 = ".";
  protected final String TEXT_169 = ")" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_170 = NL + "\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_171 = ".";
  protected final String TEXT_172 = ")" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_173 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_174 = ".";
  protected final String TEXT_175 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_176 = NL + "\t\t\t\t\t\t\t\t\t," + NL + "\t\t\t\t\t\t\t\t\tinnerColumn_";
  protected final String TEXT_177 = "," + NL + "\t\t\t\t\t\t\t\t\tme.prettyprint.hector.api.factory.HFactory.createClock(),sNameSerializer_";
  protected final String TEXT_178 = ",serializer_";
  protected final String TEXT_179 = ",serializer_";
  protected final String TEXT_180 = "));" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_181 = "} ";
  protected final String TEXT_182 = NL + "\t\t\t\t\t";
  protected final String TEXT_183 = NL + "\t\t\t\t\t\t\t\tmutator_";
  protected final String TEXT_184 = ".addDeletion(" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_185 = NL + "\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_186 = ".";
  protected final String TEXT_187 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_188 = NL + "\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_189 = ".";
  protected final String TEXT_190 = ")" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_191 = NL + "\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_192 = ".";
  protected final String TEXT_193 = ")" + NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_194 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_195 = ".";
  protected final String TEXT_196 = NL + "\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_197 = NL + "\t\t\t\t\t\t\t\t\t, ";
  protected final String TEXT_198 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_199 = NL + "\t\t\t\t\t\t\t\t\tmutator_";
  protected final String TEXT_200 = ".addDeletion(" + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_201 = NL + "\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_202 = ".";
  protected final String TEXT_203 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_204 = NL + "\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_205 = ".";
  protected final String TEXT_206 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_207 = NL + "\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_208 = ".";
  protected final String TEXT_209 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_210 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_211 = ".";
  protected final String TEXT_212 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_213 = NL + "\t\t\t\t\t\t\t\t\t\t, ";
  protected final String TEXT_214 = ");" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_215 = NL + "\t\t\t\t\t\t\t\t\tmutator_";
  protected final String TEXT_216 = ".addSuperDelete(" + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_217 = NL + "\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_218 = ".";
  protected final String TEXT_219 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_220 = NL + "\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_221 = ".";
  protected final String TEXT_222 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_223 = NL + "\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_224 = ".";
  protected final String TEXT_225 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_226 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_227 = ".";
  protected final String TEXT_228 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_229 = NL + "\t\t\t\t\t\t\t\t\t\t, ";
  protected final String TEXT_230 = "," + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_231 = NL + "\t\t\t\t\t\t\t\t\t\t\t(java.math.BigInteger)";
  protected final String TEXT_232 = ".";
  protected final String TEXT_233 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_234 = NL + "\t\t\t\t\t\t\t\t\t\t\tjava.util.UUID.fromString(";
  protected final String TEXT_235 = ".";
  protected final String TEXT_236 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_237 = NL + "\t\t\t\t\t\t\t\t\t\t\tnew com.eaio.uuid.UUID(";
  protected final String TEXT_238 = ".";
  protected final String TEXT_239 = ")" + NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_240 = NL + "\t\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_241 = ".";
  protected final String TEXT_242 = NL + "\t\t\t\t\t\t\t\t\t\t";
  protected final String TEXT_243 = NL + "\t\t\t\t\t\t\t\t\t\t,sNameSerializer_";
  protected final String TEXT_244 = ");" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_245 = NL + "\t\t\t\t\t";
  protected final String TEXT_246 = NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_247 = NL + "\t\t\t\t\tnb_line_";
  protected final String TEXT_248 = "++;" + NL + "\t\t\t\t\tbatchCounter_";
  protected final String TEXT_249 = "++;" + NL + "\t\t\t\t\t";
  protected final String TEXT_250 = NL + "\t\t\t\t\t\tlog.debug(\"";
  protected final String TEXT_251 = " - Adding the record \" + (nb_line_";
  protected final String TEXT_252 = ") + \" to the batch[size:\"+batchCounter_";
  protected final String TEXT_253 = "+\"].\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_254 = NL + "\t\t\t\t\tif (batchCounter_";
  protected final String TEXT_255 = " == ";
  protected final String TEXT_256 = ") {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_257 = NL + "\t\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_258 = " - Executing the batch.\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_259 = NL + "\t\t\t\t\t\tmutator_";
  protected final String TEXT_260 = ".execute();" + NL + "\t\t\t\t\t\tbatchCounter_";
  protected final String TEXT_261 = " = 0;" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_262 = NL + "\t\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_263 = " - ";
  protected final String TEXT_264 = " - Executed the batch successfully and clear the batch counter.\");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_265 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}catch(java.lang.Exception e_";
  protected final String TEXT_266 = "){" + NL + "\t\t\t\t\t";
  protected final String TEXT_267 = NL + "\t\t\t\t        throw(e_";
  protected final String TEXT_268 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_269 = NL + "\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_270 = " - \" + e_";
  protected final String TEXT_271 = ".getMessage());" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_272 = NL + "\t\t\t\t\t\tSystem.err.println(e_";
  protected final String TEXT_273 = ".getMessage());" + NL + "\t\t\t\t\t";
  protected final String TEXT_274 = "    " + NL + "\t\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_275 = NL + "    \t";
  protected final String TEXT_276 = NL;

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
    
    IMetadataTable metadata = null;
    IConnection conn = null;
    List<IMetadataColumn> columns = null;
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
    
    if(columns == null || columns.isEmpty() || conn == null){
    	return "";
    }
    
    stringBuffer.append(TEXT_5);
    
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
    
    stringBuffer.append(TEXT_6);
    
    Boolean dieOnError = ElementParameterParser.getBooleanValue(node, "__DIE_ON_ERROR__");
    CQLManager cqlManager = new CQLManager(node, columns);
    boolean useBatch = ElementParameterParser.getBooleanValue(node, "__USE_UNLOGGED_BATCH__");
    if(useBatch){
    
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    
    }
    
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cqlManager.generateStmt("boundStmt_" + cid, conn.getName()));
    
    if(useBatch){
	
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    
    }else{
    
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    
        	if(dieOnError){
        	
    stringBuffer.append(TEXT_18);
    
        	}else{
        	
    stringBuffer.append(TEXT_19);
    
        	}
        	
    stringBuffer.append(TEXT_20);
    
    }
    
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    
    }else{
    
    stringBuffer.append(TEXT_23);
    
    String batchSize = ElementParameterParser.getValue(node,"__BATCH_SIZE__");
	String columnFamilyType = ElementParameterParser.getValue(node,"__COLUMN_FAMILY_TYPE__");
	String superKeyColumn = ElementParameterParser.getValue(node,"__SUPER_KEY_COLUMN__");
    boolean incSuperKey = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__INC_SUPER_KEY__"));
    String keyColumn = ElementParameterParser.getValue(node,"__KEY_COLUMN__");
	String columnFamily = ElementParameterParser.getValue(node,"__COLUMN_FAMILY__");
    boolean incKey = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__INC_KEY__"));
    String actionOnData = ElementParameterParser.getValue(node,"__ACTION_ON_DATA__");
    List<Map<String, String>> listDeleteCols = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__DELETE_COLUMNS__");
    boolean deleteRow = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__DELETE_ROW__"));
    boolean delSuperColumn = "true".equalsIgnoreCase(ElementParameterParser.getValue(node,"__DELETE_SUPER_COLUMN__"));
    Map<String,String> deleteColumns=new java.util.HashMap<String,String>();
    boolean dieOnError = ("true").equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
    if(listDeleteCols != null) {
    	for(Map<String,String> delColumn:listDeleteCols){
    		deleteColumns.put(delColumn.get("DELETE_COLUMN"),delColumn.get("DELETE_COLUMN"));
    	}
    }
    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	
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
		for (IConnection incomingConn : node.getIncomingConnections()) {
			String conName = incomingConn.getName();
			if (incomingConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
				IMetadataTable inputMetadataTable = incomingConn.getMetadataTable();
				IMetadataColumn metaKeyColumn = inputMetadataTable.getColumn(keyColumn);
				String keyColumnType = JavaTypesManager.getTypeToGenerate(metaKeyColumn.getTalendType(), true);
				String keyCType = metaKeyColumn.getType();
				String supKeyColumnType = null;
				String supKeyCType = null;
				if(!"STANDARD".equals(columnFamilyType)){
					IMetadataColumn metaSupKeyColumn = inputMetadataTable.getColumn(superKeyColumn);
					supKeyColumnType = JavaTypesManager.getTypeToGenerate(metaSupKeyColumn.getTalendType(), true);
					supKeyCType = metaSupKeyColumn.getType();
				}
				boolean isKeyColumnJavaPrimitive = false;
				if (metaKeyColumn != null) {
					isKeyColumnJavaPrimitive = JavaTypesManager.isJavaPrimitiveType(metaKeyColumn.getTalendType(), metaKeyColumn.isNullable());
				}
				
    stringBuffer.append(TEXT_24);
    
					if (!isKeyColumnJavaPrimitive) {
					
    stringBuffer.append(TEXT_25);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_27);
    
					}
					if("SUPER".equals(columnFamilyType)){
					
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    
					}
		    		for (IMetadataColumn column : inputMetadataTable.getListColumns()) {
		    			JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
		    			String columnType=JavaTypesManager.getTypeToGenerate(column.getTalendType(),true);
						String cType = column.getType();
		    			boolean isJavaPrimitiveType = JavaTypesManager.isJavaPrimitiveType(column.getTalendType(), column.isNullable());
		    			if("STANDARD".equals(columnFamilyType)){
							if (incKey || !column.getLabel().equals(keyColumn)) {
								if("UPSERT".equals(actionOnData)){
								
    stringBuffer.append(TEXT_30);
    if (!isJavaPrimitiveType) {
    stringBuffer.append(TEXT_31);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_33);
    }
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    if("BigIntegerSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_36);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_38);
    }else if("UUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_39);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_41);
    }else if("TimeUUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_42);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_44);
    }else{
    stringBuffer.append(TEXT_45);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_47);
    }
    stringBuffer.append(TEXT_48);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_50);
    if("BigIntegerSerializer".equals(typeMap.get(columnType, cType))){
    stringBuffer.append(TEXT_51);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_53);
    }else if("UUIDSerializer".equals(typeMap.get(columnType, cType))){
    stringBuffer.append(TEXT_54);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_56);
    }else if("TimeUUIDSerializer".equals(typeMap.get(columnType, cType))){
    stringBuffer.append(TEXT_57);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_59);
    }else{
    stringBuffer.append(TEXT_60);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_62);
    }
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(typeMap.get(columnType, cType));
    stringBuffer.append(TEXT_65);
    if (!isJavaPrimitiveType) {
    stringBuffer.append(TEXT_66);
    }
    stringBuffer.append(TEXT_67);
    
								}else{
									if(!deleteRow && listDeleteCols.size()>0 && deleteColumns.get(column.getLabel())!=null){
									
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    if("BigIntegerSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_70);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_72);
    }else if("UUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_73);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_75);
    }else if("TimeUUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_76);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_78);
    }else{
    stringBuffer.append(TEXT_79);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_81);
    }
    stringBuffer.append(TEXT_82);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    
									}
								}
							}
						}else{
							if((incKey && column.getLabel().equals(keyColumn))||(incSuperKey && column.getLabel().equals(superKeyColumn))|| 
								(!column.getLabel().equals(keyColumn)&&!column.getLabel().equals(superKeyColumn)) ){
								if("UPSERT".equals(actionOnData)){
								
    stringBuffer.append(TEXT_86);
    if (!isJavaPrimitiveType) {
    stringBuffer.append(TEXT_87);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_89);
    }
    stringBuffer.append(TEXT_90);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_91);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_92);
    if("BigIntegerSerializer".equals(typeMap.get(columnType, cType))){
    stringBuffer.append(TEXT_93);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_95);
    }else if("UUIDSerializer".equals(typeMap.get(columnType, cType))){
    stringBuffer.append(TEXT_96);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_98);
    }else if("TimeUUIDSerializer".equals(typeMap.get(columnType, cType))){
    stringBuffer.append(TEXT_99);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_101);
    }else{
    stringBuffer.append(TEXT_102);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_104);
    }
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(typeMap.get(columnType, cType));
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_109);
    if (!isJavaPrimitiveType) {
    stringBuffer.append(TEXT_110);
    }
    stringBuffer.append(TEXT_111);
    
								}else{
									if(!deleteRow && listDeleteCols.size()>0 && deleteColumns.get(column.getLabel())!=null){
									
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_113);
    if("BigIntegerSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_114);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_116);
    }else if("UUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_117);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_119);
    }else if("TimeUUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_120);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_122);
    }else{
    stringBuffer.append(TEXT_123);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_125);
    }
    stringBuffer.append(TEXT_126);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_127);
    if("BigIntegerSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_128);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_130);
    }else if("UUIDSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_131);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_133);
    }else if("TimeUUIDSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_134);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_136);
    }else{
    stringBuffer.append(TEXT_137);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_139);
    }
    stringBuffer.append(TEXT_140);
    stringBuffer.append(column.getOriginalDbColumnName());
    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_143);
    
									}
								}
							}
						}
					}
		           	if("SUPER".equals(columnFamilyType) && "UPSERT".equals(actionOnData)){
						IMetadataColumn metaSuperKeyColumn = inputMetadataTable.getColumn(superKeyColumn);
						boolean isSuperKeyColumnJavaPrimitive = false;
						if (metaSuperKeyColumn != null) {
							isSuperKeyColumnJavaPrimitive = JavaTypesManager.isJavaPrimitiveType(metaSuperKeyColumn.getTalendType(), metaSuperKeyColumn.isNullable());
						}
						
    stringBuffer.append(TEXT_144);
    if (!isSuperKeyColumnJavaPrimitive) {
    stringBuffer.append(TEXT_145);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_147);
    }
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    if("BigIntegerSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_150);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_151);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_152);
    }else if("UUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_153);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_155);
    }else if("TimeUUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_156);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_157);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_158);
    }else{
    stringBuffer.append(TEXT_159);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_161);
    }
    stringBuffer.append(TEXT_162);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_163);
    if("BigIntegerSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_164);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_166);
    }else if("UUIDSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_167);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_168);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_169);
    }else if("TimeUUIDSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_170);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_171);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_172);
    }else{
    stringBuffer.append(TEXT_173);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_175);
    }
    stringBuffer.append(TEXT_176);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    if (!isSuperKeyColumnJavaPrimitive) {
    stringBuffer.append(TEXT_181);
    }
    stringBuffer.append(TEXT_182);
    
					}
					if("DELETE".equals(actionOnData)){
						if(deleteRow || listDeleteCols.size()<1){
							if("STANDARD".equals(columnFamilyType)){
							
    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    if("BigIntegerSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_185);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_186);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_187);
    }else if("UUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_188);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_189);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_190);
    }else if("TimeUUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_191);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_192);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_193);
    }else{
    stringBuffer.append(TEXT_194);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_195);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_196);
    }
    stringBuffer.append(TEXT_197);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_198);
                 		
							}else{
								if(deleteRow && !delSuperColumn){
								
    stringBuffer.append(TEXT_199);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_200);
    if("BigIntegerSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_201);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_203);
    }else if("UUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_204);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_205);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_206);
    }else if("TimeUUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_207);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_208);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_209);
    }else{
    stringBuffer.append(TEXT_210);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_211);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_212);
    }
    stringBuffer.append(TEXT_213);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_214);
    
								}else{
								
    stringBuffer.append(TEXT_215);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_216);
    if("BigIntegerSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_217);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_219);
    }else if("UUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_220);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_222);
    }else if("TimeUUIDSerializer".equals(typeMap.get(keyColumnType, keyCType))){
    stringBuffer.append(TEXT_223);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_224);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_225);
    }else{
    stringBuffer.append(TEXT_226);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_227);
    stringBuffer.append(keyColumn);
    stringBuffer.append(TEXT_228);
    }
    stringBuffer.append(TEXT_229);
    stringBuffer.append(columnFamily);
    stringBuffer.append(TEXT_230);
    if("BigIntegerSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_231);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_233);
    }else if("UUIDSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_234);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_235);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_236);
    }else if("TimeUUIDSerializer".equals(typeMap.get(supKeyColumnType, supKeyCType))){
    stringBuffer.append(TEXT_237);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_238);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_239);
    }else{
    stringBuffer.append(TEXT_240);
    stringBuffer.append(conName);
    stringBuffer.append(TEXT_241);
    stringBuffer.append(superKeyColumn);
    stringBuffer.append(TEXT_242);
    }
    stringBuffer.append(TEXT_243);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_244);
    
								}
							}
						}
		            }
					
    stringBuffer.append(TEXT_245);
    if (!isKeyColumnJavaPrimitive) {
    stringBuffer.append(TEXT_246);
    }
    stringBuffer.append(TEXT_247);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_248);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_249);
    
					if(isLog4jEnabled){
					
    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_253);
    
					}
					
    stringBuffer.append(TEXT_254);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_255);
    stringBuffer.append(batchSize);
    stringBuffer.append(TEXT_256);
    
						if(isLog4jEnabled){
						
    stringBuffer.append(TEXT_257);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_258);
    
						}
						
    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    
						if(isLog4jEnabled){
						
    stringBuffer.append(TEXT_262);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_263);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_264);
    
						}
						
    stringBuffer.append(TEXT_265);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_266);
    
					if(dieOnError) {
					
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_268);
    
					}else {
						if(isLog4jEnabled){
						
    stringBuffer.append(TEXT_269);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_270);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_271);
    
						}
						
    stringBuffer.append(TEXT_272);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_273);
    
					}
					
    stringBuffer.append(TEXT_274);
    
			}
      	}
    }

    stringBuffer.append(TEXT_275);
    
    }
    
    stringBuffer.append(TEXT_276);
    return stringBuffer.toString();
  }
}
