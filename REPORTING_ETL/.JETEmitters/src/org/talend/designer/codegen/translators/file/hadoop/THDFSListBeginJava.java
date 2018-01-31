package org.talend.designer.codegen.translators.file.hadoop;

import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.ElementParameterParser;
import java.util.Map;
import java.util.List;

public class THDFSListBeginJava
{
  protected static String nl;
  public static synchronized THDFSListBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THDFSListBeginJava result = new THDFSListBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "\t";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_4 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_5 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_6 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_7 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_8 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_9 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_10 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_11 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_12 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_13 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_14 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_15 = " - Written records count: \" + nb_line_";
  protected final String TEXT_16 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_17 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_18 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_19 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_20 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_21 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_22 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_23 = " - Writing the record \" + nb_line_";
  protected final String TEXT_24 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_25 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_26 = " - Processing the record \" + nb_line_";
  protected final String TEXT_27 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_28 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_29 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_30 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_31 = NL;
  protected final String TEXT_32 = NL + "String username_";
  protected final String TEXT_33 = " = \"\";" + NL + "org.apache.hadoop.fs.FileSystem fs_";
  protected final String TEXT_34 = " = null;";
  protected final String TEXT_35 = NL + "\torg.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_36 = " = new org.apache.hadoop.conf.Configuration();" + NL + "\tconf_";
  protected final String TEXT_37 = ".set(\"";
  protected final String TEXT_38 = "\", ";
  protected final String TEXT_39 = ");" + NL + "" + NL + "\t";
  protected final String TEXT_40 = NL + "        conf_";
  protected final String TEXT_41 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_42 = NL + "\t\t\tconf_";
  protected final String TEXT_43 = ".set(";
  protected final String TEXT_44 = " ,";
  protected final String TEXT_45 = ");" + NL + "\t\t";
  protected final String TEXT_46 = NL + "            System.setProperty(\"pname\", \"MapRLogin\");" + NL + "            System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "            System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_47 = ");" + NL + "            com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_48 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_49 = NL + "                System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_50 = ");";
  protected final String TEXT_51 = NL + "                maprLogin_";
  protected final String TEXT_52 = ".setCheckUGI(false);";
  protected final String TEXT_53 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_54 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_55 = ");";
  protected final String TEXT_56 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_57 = " = ";
  protected final String TEXT_58 = "; ";
  protected final String TEXT_59 = NL;
  protected final String TEXT_60 = NL + "            \tmaprLogin_";
  protected final String TEXT_61 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_62 = ", ";
  protected final String TEXT_63 = ", decryptedPassword_";
  protected final String TEXT_64 = ", ";
  protected final String TEXT_65 = ", \"\");" + NL + "            \t";
  protected final String TEXT_66 = NL + "            \tmaprLogin_";
  protected final String TEXT_67 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_68 = ", ";
  protected final String TEXT_69 = ", decryptedPassword_";
  protected final String TEXT_70 = ", ";
  protected final String TEXT_71 = ");" + NL + "            \t";
  protected final String TEXT_72 = NL + "            System.setProperty(\"pname\", \"MapRLogin\");" + NL + "            System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "            System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_73 = ");" + NL + "            System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_74 = ");";
  protected final String TEXT_75 = NL + "\t\tconf_";
  protected final String TEXT_76 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_77 = ");";
  protected final String TEXT_78 = NL + "\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_79 = ", ";
  protected final String TEXT_80 = ");";
  protected final String TEXT_81 = NL + "            com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_82 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "            maprLogin_";
  protected final String TEXT_83 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_84 = ", ";
  protected final String TEXT_85 = ");";
  protected final String TEXT_86 = NL + "       org.apache.hadoop.security.UserGroupInformation.setConfiguration(conf_";
  protected final String TEXT_87 = ");";
  protected final String TEXT_88 = NL + "    \tconf_";
  protected final String TEXT_89 = ".set(\"hadoop.job.ugi\",";
  protected final String TEXT_90 = "+\",\"+";
  protected final String TEXT_91 = ");" + NL + "    \tfs_";
  protected final String TEXT_92 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_93 = ");" + NL + "\t";
  protected final String TEXT_94 = NL + "\t\tusername_";
  protected final String TEXT_95 = " = ";
  protected final String TEXT_96 = ";" + NL + "\t\tif(username_";
  protected final String TEXT_97 = " == null || \"\".equals(username_";
  protected final String TEXT_98 = ")){" + NL + "\t\t\tfs_";
  protected final String TEXT_99 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_100 = ");" + NL + "\t\t}else{" + NL + "\t\t\tSystem.setProperty(\"HADOOP_USER_NAME\", username_";
  protected final String TEXT_101 = ");" + NL + "\t\t\tfs_";
  protected final String TEXT_102 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_103 = ".get(\"";
  protected final String TEXT_104 = "\")),conf_";
  protected final String TEXT_105 = ",username_";
  protected final String TEXT_106 = ");" + NL + "\t\t}\t" + NL + "\t";
  protected final String TEXT_107 = NL + "\torg.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_108 = " = (org.apache.hadoop.conf.Configuration)globalMap.get(\"conn_";
  protected final String TEXT_109 = "\");" + NL + "\t";
  protected final String TEXT_110 = NL + "\t\t    \tfs_";
  protected final String TEXT_111 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_112 = ");" + NL + "\t\t\t";
  protected final String TEXT_113 = NL + "\t\t\t\t\t\tconf_";
  protected final String TEXT_114 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_115 = ");";
  protected final String TEXT_116 = "\t\t\t\t\t" + NL + "\t\t\t\t\tusername_";
  protected final String TEXT_117 = " = ";
  protected final String TEXT_118 = ";";
  protected final String TEXT_119 = NL + "\t\t\t\t\tif(!org.apache.hadoop.security.UserGroupInformation.isSecurityEnabled()) {" + NL + "\t\t\t\t\t\tusername_";
  protected final String TEXT_120 = " = conf_";
  protected final String TEXT_121 = ".get(\"talend.hadoop.user.name\", \"\");" + NL + "\t\t\t\t\t}";
  protected final String TEXT_122 = NL + "\t\t\t\tif(username_";
  protected final String TEXT_123 = " == null || \"\".equals(username_";
  protected final String TEXT_124 = ")){" + NL + "\t\t\t\t\tfs_";
  protected final String TEXT_125 = " = org.apache.hadoop.fs.FileSystem.get(conf_";
  protected final String TEXT_126 = ");" + NL + "\t\t\t\t}else{" + NL + "\t\t\t\t\tSystem.setProperty(\"HADOOP_USER_NAME\", username_";
  protected final String TEXT_127 = ");" + NL + "\t\t\t\t\tfs_";
  protected final String TEXT_128 = " = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(conf_";
  protected final String TEXT_129 = ".get(\"";
  protected final String TEXT_130 = "\")),conf_";
  protected final String TEXT_131 = ",username_";
  protected final String TEXT_132 = ");" + NL + "\t\t\t\t}\t\t\t  \t\t" + NL + "\t\t  \t";
  protected final String TEXT_133 = NL + "\t";
  protected final String TEXT_134 = NL + "\tjava.util.List<String> maskList_";
  protected final String TEXT_135 = " = new java.util.ArrayList<String>();" + NL + "\t";
  protected final String TEXT_136 = NL + "\t    maskList_";
  protected final String TEXT_137 = ".add(\"*\");" + NL + "\t    ";
  protected final String TEXT_138 = NL + "\t    maskList_";
  protected final String TEXT_139 = ".add(\".*\");" + NL + "\t    ";
  protected final String TEXT_140 = " " + NL + "    \tmaskList_";
  protected final String TEXT_141 = ".add(";
  protected final String TEXT_142 = ");" + NL + "  \t";
  protected final String TEXT_143 = NL + "  \t" + NL + "  \tint NB_FILE";
  protected final String TEXT_144 = " = 0;" + NL + "  \t" + NL + "  \tfor (String filemask_";
  protected final String TEXT_145 = " : maskList_";
  protected final String TEXT_146 = ") {//TD1024" + NL + "\t";
  protected final String TEXT_147 = NL + "\t\tfilemask_";
  protected final String TEXT_148 = " = org.apache.oro.text.GlobCompiler.globToPerl5(filemask_";
  protected final String TEXT_149 = ".toCharArray(), org.apache.oro.text.GlobCompiler.DEFAULT_MASK);" + NL + "\t";
  protected final String TEXT_150 = "   " + NL + "\t\tString excludefilemask_";
  protected final String TEXT_151 = " = ";
  protected final String TEXT_152 = ";" + NL + "    \tList<java.util.regex.Pattern> excludefileNameEachPattern_";
  protected final String TEXT_153 = " = new java.util.ArrayList<java.util.regex.Pattern>();" + NL + "\t   \tif(excludefilemask_";
  protected final String TEXT_154 = "!=null && !\"\".equals(excludefilemask_";
  protected final String TEXT_155 = ")) {" + NL + "\t\t  \tfor(String excludefilemaskEach_";
  protected final String TEXT_156 = " : excludefilemask_";
  protected final String TEXT_157 = ".split(\",\")) {" + NL + "\t\t  \t";
  protected final String TEXT_158 = NL + "\t\t  \t\texcludefilemaskEach_";
  protected final String TEXT_159 = " = org.apache.oro.text.GlobCompiler.globToPerl5(excludefilemaskEach_";
  protected final String TEXT_160 = ".toCharArray(), org.apache.oro.text.GlobCompiler.DEFAULT_MASK);" + NL + "\t\t  \t";
  protected final String TEXT_161 = NL + "\t\t\t\texcludefileNameEachPattern_";
  protected final String TEXT_162 = ".add(java.util.regex.Pattern.compile(excludefilemaskEach_";
  protected final String TEXT_163 = ",java.util.regex.Pattern.CASE_INSENSITIVE));" + NL + "\t\t\t";
  protected final String TEXT_164 = NL + "\t\t\t\texcludefileNameEachPattern_";
  protected final String TEXT_165 = ".add(java.util.regex.Pattern.compile(excludefilemaskEach_";
  protected final String TEXT_166 = "));" + NL + "\t\t\t";
  protected final String TEXT_167 = "\t  \t \t\t" + NL + "\t\t\t}" + NL + "\t  }";
  protected final String TEXT_168 = NL + "\tjava.util.regex.Pattern fileNamePattern_";
  protected final String TEXT_169 = " = java.util.regex.Pattern.compile(filemask_";
  protected final String TEXT_170 = ");" + NL + "\t";
  protected final String TEXT_171 = NL + "\tjava.util.regex.Pattern fileNamePattern_";
  protected final String TEXT_172 = " = java.util.regex.Pattern.compile(filemask_";
  protected final String TEXT_173 = ", java.util.regex.Pattern.CASE_INSENSITIVE);" + NL + "\t";
  protected final String TEXT_174 = NL + "\t" + NL + "\torg.apache.hadoop.fs.Path hdfsdir_";
  protected final String TEXT_175 = " = new org.apache.hadoop.fs.Path(";
  protected final String TEXT_176 = ");" + NL + "\tfinal java.util.List<org.apache.hadoop.fs.FileStatus> status_";
  protected final String TEXT_177 = " = new java.util.ArrayList<org.apache.hadoop.fs.FileStatus>();" + NL + "\tfinal org.apache.hadoop.fs.FileSystem filesystem_";
  protected final String TEXT_178 = " = fs_";
  protected final String TEXT_179 = ";" + NL + "\tfilesystem_";
  protected final String TEXT_180 = ".listStatus(hdfsdir_";
  protected final String TEXT_181 = ",new org.apache.hadoop.fs.PathFilter() {" + NL + "\t" + NL + "\t\tpublic boolean accept(org.apache.hadoop.fs.Path path) {" + NL + "\t\t\ttry {" + NL + "\t\t\t\torg.apache.hadoop.fs.FileStatus statu = filesystem_";
  protected final String TEXT_182 = ".getFileStatus(path);" + NL + "\t\t\t\tif(statu.isDir()) {" + NL + "\t\t\t\t\t";
  protected final String TEXT_183 = NL + "\t\t\t\t\tstatus_";
  protected final String TEXT_184 = ".add(statu);" + NL + "\t\t\t\t\t";
  protected final String TEXT_185 = NL + "\t\t\t\t\t" + NL + "\t\t\t\t\t";
  protected final String TEXT_186 = NL + "\t\t\t\t\tfilesystem_";
  protected final String TEXT_187 = ".listStatus(path, this);" + NL + "\t\t\t\t\t";
  protected final String TEXT_188 = NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\t";
  protected final String TEXT_189 = NL + "\t\t\t\t\tstatus_";
  protected final String TEXT_190 = ".add(statu);" + NL + "\t\t\t\t\t";
  protected final String TEXT_191 = NL + "\t\t\t\t}" + NL + "\t\t\t} catch (java.io.FileNotFoundException e) {" + NL + "\t\t\t\te.printStackTrace();" + NL + "\t\t\t\t" + NL + "        \t\t";
  protected final String TEXT_192 = NL + "               \tlog.error(\"";
  protected final String TEXT_193 = " - \" + e.getMessage());";
  protected final String TEXT_194 = NL + "\t\t\t} catch (java.io.IOException e) {" + NL + "\t\t\t\te.printStackTrace();" + NL + "\t\t\t\t" + NL + "\t\t\t    ";
  protected final String TEXT_195 = NL + "               \tlog.error(\"";
  protected final String TEXT_196 = " - \" + e.getMessage());";
  protected final String TEXT_197 = NL + "\t\t\t}" + NL + "\t\t\treturn false;" + NL + "\t\t}" + NL + "" + NL + "\t});" + NL + "\t" + NL + "\t";
  protected final String TEXT_198 = NL + "\tjava.util.Collections.sort(status_";
  protected final String TEXT_199 = ");" + NL + "\t";
  protected final String TEXT_200 = NL + "\tjava.util.Collections.sort(status_";
  protected final String TEXT_201 = ",new java.util.Comparator<org.apache.hadoop.fs.FileStatus>() {" + NL + "\t" + NL + "\t\tpublic int compare(org.apache.hadoop.fs.FileStatus f1, org.apache.hadoop.fs.FileStatus f2) {" + NL + "\t\t\tint result = 0;" + NL + "\t\t\tboolean f1IsFile = !f1.isDir(); " + NL + "\t\t\tboolean f2IsFile = !f2.isDir();" + NL + "\t\t\t" + NL + "\t\t\tif((f1IsFile && f2IsFile) || (!f1IsFile && !f2IsFile)) {" + NL + "\t\t\t\tresult = f1.getPath().getName().compareTo(f2.getPath().getName());" + NL + "\t\t\t} else if(f1IsFile && !f2IsFile) {" + NL + "\t\t\t\tresult = 1;" + NL + "\t\t\t} else if(!f1IsFile && f2IsFile) {" + NL + "\t\t\t\tresult = -1;" + NL + "\t\t\t}" + NL + "\t\t\t" + NL + "\t\t\treturn ";
  protected final String TEXT_202 = "-";
  protected final String TEXT_203 = "result;" + NL + "\t\t}" + NL + "\t\t\t\t" + NL + "\t});" + NL + "\t";
  protected final String TEXT_204 = NL + "\tjava.util.Collections.sort(status_";
  protected final String TEXT_205 = ",new java.util.Comparator<org.apache.hadoop.fs.FileStatus>() {" + NL + "\t" + NL + "\t\tpublic int compare(org.apache.hadoop.fs.FileStatus f1, org.apache.hadoop.fs.FileStatus f2) {" + NL + "\t\t\tint result = 0;" + NL + "\t\t\tboolean f1IsFile = !f1.isDir(); " + NL + "\t\t\tboolean f2IsFile = !f2.isDir();" + NL + "\t\t\t" + NL + "\t\t\tif(f1IsFile && f2IsFile) {" + NL + "\t\t\t\tlong size_1 = f1.getLen();" + NL + "            \tlong size_2 = f2.getLen();" + NL + "                if (size_1 == size_2) {" + NL + "                    result = f1.getPath().getName().compareTo(f2.getPath().getName());" + NL + "                } else if (size_1 > size_2) {" + NL + "                    result = 1;" + NL + "                } else {" + NL + "                    result = -1;" + NL + "                }" + NL + "\t\t\t} else if(f1IsFile && !f2IsFile) {" + NL + "\t\t\t\tresult = 1;" + NL + "\t\t\t} else if(!f1IsFile && f2IsFile) {" + NL + "\t\t\t\tresult = -1;" + NL + "\t\t\t} else if(!f1IsFile && !f2IsFile) {" + NL + "\t\t\t\tresult = f1.getPath().getName().compareTo(f2.getPath().getName());" + NL + "\t\t\t}" + NL + "\t\t\t" + NL + "\t\t\treturn ";
  protected final String TEXT_206 = "-";
  protected final String TEXT_207 = "result;" + NL + "\t\t}" + NL + "\t\t\t\t" + NL + "\t});" + NL + "\t";
  protected final String TEXT_208 = NL + "\tjava.util.Collections.sort(status_";
  protected final String TEXT_209 = ",new java.util.Comparator<org.apache.hadoop.fs.FileStatus>() {" + NL + "\t" + NL + "\t\tpublic int compare(org.apache.hadoop.fs.FileStatus f1, org.apache.hadoop.fs.FileStatus f2) {" + NL + "\t\t\tint result = 0;" + NL + "\t\t\tboolean f1IsFile = !f1.isDir(); " + NL + "\t\t\tboolean f2IsFile = !f2.isDir();" + NL + "\t\t\t" + NL + "\t\t\tif((f1IsFile && f2IsFile) || (!f1IsFile && !f2IsFile)) {" + NL + "\t\t\t\tif(f1.getModificationTime() == f2.getModificationTime()) {" + NL + "\t\t\t\t\tresult = f1.getPath().getName().compareTo(f2.getPath().getName());" + NL + "\t\t\t\t} else if(f1.getModificationTime() > f2.getModificationTime()) {" + NL + "\t\t\t\t\tresult = 1;" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tresult = -1;" + NL + "\t\t\t\t}" + NL + "\t\t\t} else if(f1IsFile && !f2IsFile) {" + NL + "\t\t\t\tresult = 1;" + NL + "\t\t\t} else if(!f1IsFile && f2IsFile) {" + NL + "\t\t\t\tresult = -1;" + NL + "\t\t\t}" + NL + "\t\t\t" + NL + "\t\t\treturn ";
  protected final String TEXT_210 = "-";
  protected final String TEXT_211 = "result;" + NL + "\t\t}" + NL + "\t\t\t\t" + NL + "\t});" + NL + "\t";
  protected final String TEXT_212 = NL + "\t" + NL + "\t";
  protected final String TEXT_213 = NL + "\t\tlog.info(\"";
  protected final String TEXT_214 = " - Start to list files\");" + NL + "\t";
  protected final String TEXT_215 = NL + "\tfor(int i_";
  protected final String TEXT_216 = "=0;i_";
  protected final String TEXT_217 = "<status_";
  protected final String TEXT_218 = ".size();i_";
  protected final String TEXT_219 = "++) {//TD512" + NL + "\t\torg.apache.hadoop.fs.FileStatus file_";
  protected final String TEXT_220 = " = status_";
  protected final String TEXT_221 = ".get(i_";
  protected final String TEXT_222 = ");" + NL + "\t\torg.apache.hadoop.fs.Path path_";
  protected final String TEXT_223 = " = file_";
  protected final String TEXT_224 = ".getPath();" + NL + "\t\tString fileName_";
  protected final String TEXT_225 = " = path_";
  protected final String TEXT_226 = ".getName();" + NL + "\t\t" + NL + "\t\tif (!fileNamePattern_";
  protected final String TEXT_227 = ".matcher(fileName_";
  protected final String TEXT_228 = ").matches()){" + NL + "          continue;" + NL + "        }" + NL + "        " + NL + "\t\t";
  protected final String TEXT_229 = " " + NL + "        boolean isExclude_";
  protected final String TEXT_230 = " = false;" + NL + "        for(java.util.regex.Pattern pattern : excludefileNameEachPattern_";
  protected final String TEXT_231 = ") {" + NL + "        \tif(pattern.matcher(fileName_";
  protected final String TEXT_232 = ").matches()) {" + NL + "        \t\tisExclude_";
  protected final String TEXT_233 = " = true;" + NL + "        \t\tbreak;" + NL + "        \t}" + NL + "        }" + NL + "        if(isExclude_";
  protected final String TEXT_234 = ") {" + NL + "          continue;" + NL + "        }" + NL + "\t\t";
  protected final String TEXT_235 = NL + "\t\t" + NL + "\t\tString currentFileName_";
  protected final String TEXT_236 = " = fileName_";
  protected final String TEXT_237 = ";" + NL + "\t\tString currentFilePath_";
  protected final String TEXT_238 = " = path_";
  protected final String TEXT_239 = ".toString();" + NL + "\t\tString currentFileDirectory_";
  protected final String TEXT_240 = " = path_";
  protected final String TEXT_241 = ".getParent().toString();" + NL + "\t\tString currentFileExtension_";
  protected final String TEXT_242 = " = \"\";" + NL + "\t\tif(!file_";
  protected final String TEXT_243 = ".isDir() && fileName_";
  protected final String TEXT_244 = ".contains(\".\")) {" + NL + "\t\t\tcurrentFileExtension_";
  protected final String TEXT_245 = " = fileName_";
  protected final String TEXT_246 = ".substring(fileName_";
  protected final String TEXT_247 = ".lastIndexOf(\".\")+1);" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tNB_FILE";
  protected final String TEXT_248 = " ++;" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_249 = "_CURRENT_FILE\", currentFileName_";
  protected final String TEXT_250 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_251 = "_CURRENT_FILEPATH\", currentFilePath_";
  protected final String TEXT_252 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_253 = "_CURRENT_FILEDIRECTORY\", currentFileDirectory_";
  protected final String TEXT_254 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_255 = "_CURRENT_FILEEXTENSION\", currentFileExtension_";
  protected final String TEXT_256 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_257 = "_NB_FILE\", NB_FILE";
  protected final String TEXT_258 = ");" + NL + "\t\t" + NL + "\t\t";
  protected final String TEXT_259 = NL + "\t\tlog.info(\"";
  protected final String TEXT_260 = " - Current file or directory path : \" + currentFilePath_";
  protected final String TEXT_261 = ");" + NL + "\t  \t";
  protected final String TEXT_262 = NL + "\t\t";
  protected final String TEXT_263 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    
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
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_4);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_5);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_8);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_10);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_11);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_14);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_16);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_19);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_21);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_22);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_24);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_25);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_27);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_28);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_30);
    
			}
		}
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    stringBuffer.append(TEXT_31);
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();

String fsDefaultName = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
boolean useExistingConnection = "true".equals(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"));
List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");
String user = null;

String fsDefalutName = "fs.default.name";

String distribution = null;
String hadoopVersion = null;
boolean isCustom = false;
org.talend.hadoop.distribution.component.HDFSComponent hdfsDistrib = null;

boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

log4jFileUtil.componentStartInfo(node);


    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    
if(!useExistingConnection) { // if we don't use an existing connection, we create a new hadoop configuration
	distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
	hadoopVersion = ElementParameterParser.getValue(node, "__DB_VERSION__");

	boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
	String kerberosPrincipal = ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");
	boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
	String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
	String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
    boolean mrUseDatanodeHostname = "true".equals(ElementParameterParser.getValue(node, "__USE_DATANODE_HOSTNAME__"));
    
    boolean useMapRTicket = ElementParameterParser.getBooleanValue(node, "__USE_MAPRTICKET__");
    String mapRTicketCluster = ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
    String mapRTicketDuration = ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");

    boolean setMapRHomeDir = ElementParameterParser.getBooleanValue(node, "__SET_MAPR_HOME_DIR__");
    String mapRHomeDir = ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");

    boolean setMapRHadoopLogin = ElementParameterParser.getBooleanValue(node, "__SET_HADOOP_LOGIN__");
    String mapRHadoopLogin = ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");
    
	try {
		hdfsDistrib = (org.talend.hadoop.distribution.component.HDFSComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hadoopVersion);
	} catch (java.lang.Exception e) {
		e.printStackTrace();
		return "";
	}
    isCustom = hdfsDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

	String auth = ElementParameterParser.getValue(node, "__AUTHENTICATION_MODE__");
	
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(fsDefalutName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(fsDefaultName);
    stringBuffer.append(TEXT_39);
    
	if (hdfsDistrib.doSupportUseDatanodeHostname() && mrUseDatanodeHostname) {
        
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    
    }
	if(hadoopProps!=null && hadoopProps.size() > 0){
		for(Map<String, String> item : hadoopProps){
		
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_44);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_45);
     
		}
	}
	
	if(!((!isCustom && hdfsDistrib.doSupportKerberos() && useKrb) || (isCustom && "KRB".equals(auth)))) {
		user = ElementParameterParser.getValue(node, "__USERNAME__");
		if ((isCustom || hdfsDistrib.doSupportMapRTicket()) && useMapRTicket) {
            String passwordFieldName = "__MAPRTICKET_PASSWORD__";
            
    stringBuffer.append(TEXT_46);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    
            if (setMapRHadoopLogin) {
                
    stringBuffer.append(TEXT_49);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_50);
    
            } else {
                
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    
            }
            
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_55);
    } else {
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_58);
    }
    stringBuffer.append(TEXT_59);
    
            if(hdfsDistrib.doSupportMaprTicketV52API()){
				
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_65);
    
            }else{
            	
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_71);
    
            }
        }
	} else {
	    if ((isCustom || hdfsDistrib.doSupportMapRTicket()) && useMapRTicket) {
            
    stringBuffer.append(TEXT_72);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_74);
    
        }

    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_77);
    
		if(useKeytab) {

    stringBuffer.append(TEXT_78);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_80);
    
		}
        if ((isCustom || hdfsDistrib.doSupportMapRTicket()) && useMapRTicket) {
            
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_85);
    
        }
	}


    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    
	
	if((!isCustom && hdfsDistrib.doSupportGroup()) || (isCustom && "UGI".equals(auth))){
		String group = ElementParameterParser.getValue(node, "__GROUP__");
	
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(group);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    
	}else{
	
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(user);
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
    stringBuffer.append(fsDefalutName);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    
	}
	
} else { // We re use the existing connection, coming from the component learned.
	String connectionSid = ElementParameterParser.getValue(node, "__CONNECTION__");
	
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(connectionSid);
    stringBuffer.append(TEXT_109);
    
	List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
    for(INode targetNode : nodes){
		if (targetNode.getUniqueName().equals(connectionSid)) {
        	distribution = ElementParameterParser.getValue(targetNode, "__DISTRIBUTION__");
        	hadoopVersion = ElementParameterParser.getValue(targetNode, "__DB_VERSION__"); 

    		boolean useKrb = "true".equals(ElementParameterParser.getValue(targetNode, "__USE_KRB__"));
    		String kerberosPrincipal = ElementParameterParser.getValue(targetNode, "__NAMENODE_PRINCIPAL__");

			try {
				hdfsDistrib = (org.talend.hadoop.distribution.component.HDFSComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hadoopVersion);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
				return "";
			}
    		isCustom = hdfsDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

    		String auth = ElementParameterParser.getValue(targetNode, "__AUTHENTICATION_MODE__");
		
	      	if((!isCustom && hdfsDistrib.doSupportGroup()) || (isCustom && "UGI".equals(auth))){
		    
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    
		  	}else{
		  		boolean configureFromClassPath = "true".equals(ElementParameterParser.getValue(targetNode, "__CONFIGURATIONS_FROM_CLASSPATH__"));
		  		if(!configureFromClassPath) {
					if(!((!isCustom && hdfsDistrib.doSupportKerberos() && useKrb) || (isCustom && "KRB".equals(auth)))) {
						user = ElementParameterParser.getValue(targetNode, "__USERNAME__");
					} else {

    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(kerberosPrincipal);
    stringBuffer.append(TEXT_115);
    
					}

    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(user);
    stringBuffer.append(TEXT_118);
    
				} else {
					// If the configuration is inspected from the classpath

    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    				
				}
			  	
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
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
    stringBuffer.append(fsDefalutName);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    
		  	}
	      	break;
	    }
    }
}

    stringBuffer.append(TEXT_133);
    
	String path = ElementParameterParser.getValue(node, "__PATH__");
	String hdfsdir = ElementParameterParser.getValue(node, "__DIRECTORY__");
	boolean incldSubdir = ("true").equals(ElementParameterParser.getValue(node, "__INCLUDSUBDIR__"));
  	boolean ifexclude = ("true").equals(ElementParameterParser.getValue(node, "__IFEXCLUDE__"));
  	String filelistType = ElementParameterParser.getValue(node, "__LIST_MODE__");
  	boolean useGlob = ("true").equals(ElementParameterParser.getValue(node, "__GLOBEXPRESSIONS__"));
  	List<Map<String, String>> files = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__FILES__");
  	boolean caseSensitive = ("YES").equals(ElementParameterParser.getValue(node, "__CASE_SENSITIVE__"));
  	String excludefilemask = ElementParameterParser.getValue(node, "__EXCLUDEFILEMASK__");
  	
	boolean bOrdByDefault = "true".equals(ElementParameterParser.getValue(node, "__ORDER_BY_NOTHING__"));
    boolean bOrdByFileName = "true".equals(ElementParameterParser.getValue(node, "__ORDER_BY_FILENAME__"));
    boolean bOrdByFileSize = "true".equals(ElementParameterParser.getValue(node, "__ORDER_BY_FILESIZE__"));
    boolean bOrdByModifiedTime = "true".equals(ElementParameterParser.getValue(node, "__ORDER_BY_MODIFIEDDATE__"));
    
    boolean bOrdASC = "true".equals(ElementParameterParser.getValue(node, "__ORDER_ACTION_ASC__"));

    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_135);
    
    if (files.size() == 0){
		if (useGlob){
    	
    stringBuffer.append(TEXT_136);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_137);
    
	    } else {
	    
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_139);
    
	    }
	}
  	
  	for (int i = 0; i < files.size(); i++) {
    	Map<String, String> line = files.get(i);
    
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_141);
    stringBuffer.append( line.get("FILEMASK") );
    stringBuffer.append(TEXT_142);
    }
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_146);
    if(useGlob) {
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    }
	
	if (ifexclude){
    
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_151);
    stringBuffer.append(excludefilemask);
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
    if (useGlob){
    stringBuffer.append(TEXT_158);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    }
  	 		if(!caseSensitive){
		  	
    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    } else {
    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_166);
    }
    stringBuffer.append(TEXT_167);
    }
    
    if(caseSensitive) {
    
    stringBuffer.append(TEXT_168);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_170);
    } else {
    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_173);
     } 
    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(hdfsdir);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    if(("DIRECTORIES OR BOTH").contains(filelistType)) {
    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    }
    stringBuffer.append(TEXT_185);
    if(incldSubdir) {
    stringBuffer.append(TEXT_186);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_187);
    }
    stringBuffer.append(TEXT_188);
    if(("FILES OR BOTH").contains(filelistType)) {
    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    }
    stringBuffer.append(TEXT_191);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_193);
    }
    stringBuffer.append(TEXT_194);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_195);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_196);
    }
    stringBuffer.append(TEXT_197);
    if(bOrdByDefault){
    stringBuffer.append(TEXT_198);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_199);
    } else if(bOrdByFileName) {
    stringBuffer.append(TEXT_200);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_201);
    if(!bOrdASC) {
    stringBuffer.append(TEXT_202);
    }
    stringBuffer.append(TEXT_203);
    } else if(bOrdByFileSize) {
    stringBuffer.append(TEXT_204);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_205);
    if(!bOrdASC) {
    stringBuffer.append(TEXT_206);
    }
    stringBuffer.append(TEXT_207);
    } else if(bOrdByModifiedTime) {
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    if(!bOrdASC) {
    stringBuffer.append(TEXT_210);
    }
    stringBuffer.append(TEXT_211);
    }
    stringBuffer.append(TEXT_212);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_213);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    }
    stringBuffer.append(TEXT_215);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_216);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_219);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_222);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_223);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_226);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_227);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_228);
    if (ifexclude){
    stringBuffer.append(TEXT_229);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_230);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_231);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_233);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_234);
    }
    stringBuffer.append(TEXT_235);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_238);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_239);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_240);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_241);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_242);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_243);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_244);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_245);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_246);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_247);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_248);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_249);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_253);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_254);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_257);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_258);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    }
    stringBuffer.append(TEXT_262);
    stringBuffer.append(TEXT_263);
    return stringBuffer.toString();
  }
}
