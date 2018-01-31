package org.talend.designer.codegen.translators.databases.mongodb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import java.util.List;
import java.util.Map;

public class TMongoDBBulkLoadMainJava
{
  protected static String nl;
  public static synchronized TMongoDBBulkLoadMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMongoDBBulkLoadMainJava result = new TMongoDBBulkLoadMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + NL + "\tStringBuffer comand_";
  protected final String TEXT_2 = "=new StringBuffer();" + NL + "\t";
  protected final String TEXT_3 = NL + "\t\tcomand_";
  protected final String TEXT_4 = ".append(";
  protected final String TEXT_5 = ");" + NL + "\t";
  protected final String TEXT_6 = NL + "\tcomand_";
  protected final String TEXT_7 = ".append(\"/bin/mongoimport\");" + NL + " \tList<String> args_";
  protected final String TEXT_8 = " = new java.util.ArrayList<String>();" + NL + " \targs_";
  protected final String TEXT_9 = ".add(comand_";
  protected final String TEXT_10 = ".toString());" + NL + " \tProcessBuilder runtime_";
  protected final String TEXT_11 = " = new ProcessBuilder(args_";
  protected final String TEXT_12 = ");" + NL + "\t";
  protected final String TEXT_13 = NL + "\t\targs_";
  protected final String TEXT_14 = ".add(\"--dbpath\");" + NL + "\t\targs_";
  protected final String TEXT_15 = ".add(";
  protected final String TEXT_16 = ");" + NL + "\t";
  protected final String TEXT_17 = NL + "\t\t\t\targs_";
  protected final String TEXT_18 = ".add(\"--host\");" + NL + "\t\t\t\targs_";
  protected final String TEXT_19 = ".add(";
  protected final String TEXT_20 = ");" + NL + "\t\t\t";
  protected final String TEXT_21 = NL + "\t\t\t\targs_";
  protected final String TEXT_22 = ".add(\"--port\");" + NL + "\t\t\t\targs_";
  protected final String TEXT_23 = ".add(\"\" + ";
  protected final String TEXT_24 = ");" + NL + "\t\t\t";
  protected final String TEXT_25 = NL + "\t\t    StringBuffer repHosts_";
  protected final String TEXT_26 = "=new StringBuffer();";
  protected final String TEXT_27 = NL + "                repHosts_";
  protected final String TEXT_28 = ".append(";
  protected final String TEXT_29 = "+\"/\");";
  protected final String TEXT_30 = NL + "\t\t\t\t\t\trepHosts_";
  protected final String TEXT_31 = ".append(\",\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_32 = NL + "\t\t\t\t\t\trepHosts_";
  protected final String TEXT_33 = ".append(";
  protected final String TEXT_34 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_35 = NL + "\t\t\t\t\t\trepHosts_";
  protected final String TEXT_36 = ".append(\":\"+";
  protected final String TEXT_37 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_38 = NL + "\t\t\targs_";
  protected final String TEXT_39 = ".add(\"--host\");" + NL + "\t\t\targs_";
  protected final String TEXT_40 = ".add(repHosts_";
  protected final String TEXT_41 = ".toString());" + NL + "\t\t";
  protected final String TEXT_42 = NL + "            args_";
  protected final String TEXT_43 = ".add(\"--ssl\");";
  protected final String TEXT_44 = NL + "\t\targs_";
  protected final String TEXT_45 = ".add(\"--db\");" + NL + "\t\targs_";
  protected final String TEXT_46 = ".add(";
  protected final String TEXT_47 = ");" + NL + "\t";
  protected final String TEXT_48 = NL + "\t\targs_";
  protected final String TEXT_49 = ".add(\"--collection\");" + NL + "\t\targs_";
  protected final String TEXT_50 = ".add(";
  protected final String TEXT_51 = ");" + NL + "\t";
  protected final String TEXT_52 = NL + "                args_";
  protected final String TEXT_53 = ".add(\"--username\"); " + NL + "                args_";
  protected final String TEXT_54 = ".add(";
  protected final String TEXT_55 = "); ";
  protected final String TEXT_56 = NL;
  protected final String TEXT_57 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_58 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_59 = ");";
  protected final String TEXT_60 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_61 = " = ";
  protected final String TEXT_62 = "; ";
  protected final String TEXT_63 = NL + NL + "                args_";
  protected final String TEXT_64 = ".add(\"--password\"); " + NL + "                args_";
  protected final String TEXT_65 = ".add(decryptedPassword_";
  protected final String TEXT_66 = "); ";
  protected final String TEXT_67 = NL + "                args_";
  protected final String TEXT_68 = ".add(\"--authenticationMechanism\");" + NL + "                args_";
  protected final String TEXT_69 = ".add(\"SCRAM-SHA-1\");" + NL + "                args_";
  protected final String TEXT_70 = ".add(\"--authenticationDatabase\");" + NL + "                args_";
  protected final String TEXT_71 = ".add(";
  protected final String TEXT_72 = ");";
  protected final String TEXT_73 = NL + "                args_";
  protected final String TEXT_74 = ".add(\"--authenticationMechanism\");" + NL + "                args_";
  protected final String TEXT_75 = ".add(\"MONGODB-X509\");" + NL + "                args_";
  protected final String TEXT_76 = ".add(\"--authenticationDatabase\");" + NL + "                args_";
  protected final String TEXT_77 = ".add(";
  protected final String TEXT_78 = ");";
  protected final String TEXT_79 = NL + "                args_";
  protected final String TEXT_80 = ".add(\"--authenticationMechanism\");" + NL + "                args_";
  protected final String TEXT_81 = ".add(\"MONGODB-CR\");" + NL + "                args_";
  protected final String TEXT_82 = ".add(\"--authenticationDatabase\");" + NL + "                args_";
  protected final String TEXT_83 = ".add(";
  protected final String TEXT_84 = ");";
  protected final String TEXT_85 = NL + "                args_";
  protected final String TEXT_86 = ".add(\"--authenticationMechanism\");" + NL + "                args_";
  protected final String TEXT_87 = ".add(\"PLAIN\");" + NL + "                args_";
  protected final String TEXT_88 = ".add(\"--authenticationDatabase\");" + NL + "                args_";
  protected final String TEXT_89 = ".add(\"'$external'\");";
  protected final String TEXT_90 = NL + "\t            args_";
  protected final String TEXT_91 = ".add(\"--authenticationMechanism\");" + NL + "\t            args_";
  protected final String TEXT_92 = ".add(\"GSSAPI\");" + NL + "\t            ";
  protected final String TEXT_93 = NL + "\t                args_";
  protected final String TEXT_94 = ".add(\"--gssapiServiceName\");" + NL + "\t                args_";
  protected final String TEXT_95 = ".add(";
  protected final String TEXT_96 = ");" + NL + "\t                ";
  protected final String TEXT_97 = NL + "\t                args_";
  protected final String TEXT_98 = ".add(\"--gssapiHostName\");" + NL + "\t                args_";
  protected final String TEXT_99 = ".add(";
  protected final String TEXT_100 = ");" + NL + "\t                ";
  protected final String TEXT_101 = NL + "\t                args_";
  protected final String TEXT_102 = ".add(\"--username\");" + NL + "\t                args_";
  protected final String TEXT_103 = ".add(";
  protected final String TEXT_104 = ");" + NL + "\t                ";
  protected final String TEXT_105 = NL + "\t                args_";
  protected final String TEXT_106 = ".add(\"--authenticationDatabase\");" + NL + "\t                args_";
  protected final String TEXT_107 = ".add(";
  protected final String TEXT_108 = ");" + NL + "\t                ";
  protected final String TEXT_109 = NL + "\t\targs_";
  protected final String TEXT_110 = ".add(\"--type\");" + NL + "\t\targs_";
  protected final String TEXT_111 = ".add(\"";
  protected final String TEXT_112 = "\");" + NL + "\t";
  protected final String TEXT_113 = NL + "\t\targs_";
  protected final String TEXT_114 = ".add(\"--file\");" + NL + "\t\targs_";
  protected final String TEXT_115 = ".add(";
  protected final String TEXT_116 = ");" + NL + "\t";
  protected final String TEXT_117 = NL + "\t\targs_";
  protected final String TEXT_118 = ".add(\"--drop\");" + NL + "\t";
  protected final String TEXT_119 = NL + "\t\targs_";
  protected final String TEXT_120 = ".add(\"--upsert\");" + NL + "\t\t";
  protected final String TEXT_121 = NL + "\t\t\targs_";
  protected final String TEXT_122 = ".add(\"--upsertFields\");" + NL + "\t\t\targs_";
  protected final String TEXT_123 = ".add(\"";
  protected final String TEXT_124 = "\");" + NL + "\t\t";
  protected final String TEXT_125 = NL + "\t\t\targs_";
  protected final String TEXT_126 = ".add(\"--ignoreBlanks\");" + NL + "\t\t";
  protected final String TEXT_127 = NL + "\t\t    args_";
  protected final String TEXT_128 = ".add(\"--headerline\");" + NL + "\t\t    ";
  protected final String TEXT_129 = NL + "\t\t\targs_";
  protected final String TEXT_130 = ".add(\"--fields\");" + NL + "\t        args_";
  protected final String TEXT_131 = ".add(\"";
  protected final String TEXT_132 = "\");" + NL + "\t        ";
  protected final String TEXT_133 = NL + "\t            args_";
  protected final String TEXT_134 = ".add(\"--jsonArray\");" + NL + "\t        ";
  protected final String TEXT_135 = NL + "\t\t\t\targs_";
  protected final String TEXT_136 = ".add(\"\" + ";
  protected final String TEXT_137 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_138 = NL + "\t\t\t\t\targs_";
  protected final String TEXT_139 = ".add(\"\"+";
  protected final String TEXT_140 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_141 = "\t" + NL + "\t\tStringBuilder sb = new StringBuilder();" + NL + "\t\tfor (String s : args_";
  protected final String TEXT_142 = ") {" + NL + "\t\t\tsb.append(s + \" \");" + NL + "\t\t}" + NL + "\t\tlog.info(\"";
  protected final String TEXT_143 = " - Execute command \" + sb.toString() + \".\");" + NL + "\t\t";
  protected final String TEXT_144 = NL + "\t        log.info(\"";
  protected final String TEXT_145 = " - Start to import the data\");" + NL + "\t\t    ";
  protected final String TEXT_146 = NL + "\t        log.info(\"";
  protected final String TEXT_147 = " - Start to import the data into [\"+";
  protected final String TEXT_148 = "+\":\"+\"";
  protected final String TEXT_149 = "\"+\"/\"+";
  protected final String TEXT_150 = "+\"]\");" + NL + "\t\t    ";
  protected final String TEXT_151 = NL + "\t// Redirect stderr to stdout" + NL + "\truntime_";
  protected final String TEXT_152 = ".redirectErrorStream(true);" + NL + "" + NL + "\tfinal Process process_";
  protected final String TEXT_153 = " = runtime_";
  protected final String TEXT_154 = ".start();" + NL + "" + NL + "\tThread normal_";
  protected final String TEXT_155 = " = new Thread() {" + NL + "\t\tpublic void run() {" + NL + "\t\t\ttry {" + NL + "\t\t\t\tjava.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process_";
  protected final String TEXT_156 = ".getInputStream()));" + NL + "\t\t\t\tString line = \"\";" + NL + "\t\t\t\ttry {" + NL + "\t\t\t\t\twhile((line = reader.readLine()) != null) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_157 = NL + "\t\t\t\t\t\t\tSystem.out.println(line);" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_158 = "\t\t\t\t" + NL + "\t\t\t\t\t\tint im=line.indexOf(\"imported\");" + NL + "\t\t\t\t\t\tint obj=line.indexOf(\"object\");" + NL + "\t\t\t\t\t\tint doc=line.indexOf(\"document\");" + NL + "\t\t\t\t\t\tif(im > 0 && obj > 0){" + NL + "\t\t\t\t\t\t\tline = line.substring(im+8, obj).trim();" + NL + "\t\t\t\t\t\t} else if(im > 0 && doc > 0){" + NL + "\t\t\t\t\t\t\tline = line.substring(im+8, doc).trim();" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\tif(line.matches(\"^[\\\\d]+$\")){" + NL + "\t\t\t\t\t\t\tglobalMap.put(\"";
  protected final String TEXT_159 = "_NB_LINE\", Integer.parseInt(line));" + NL + "\t\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\t\tglobalMap.put(\"";
  protected final String TEXT_160 = "_NB_LINE\", 0);" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t} finally {" + NL + "\t\t\t\t\treader.close();" + NL + "\t\t\t\t}" + NL + "\t\t\t} catch(java.io.IOException ioe) {" + NL + "\t\t\t\tioe.printStackTrace();" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t};" + NL + "\tnormal_";
  protected final String TEXT_161 = ".start();" + NL + "\t";
  protected final String TEXT_162 = NL + "\t\tThread error_";
  protected final String TEXT_163 = " = new Thread() {" + NL + "\t\t\tpublic void run() {" + NL + "\t\t\t\ttry {" + NL + "\t\t\t\t\tjava.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process_";
  protected final String TEXT_164 = ".getErrorStream()));" + NL + "\t\t\t\t\tString line = \"\";" + NL + "\t\t\t\t\ttry {" + NL + "\t\t\t\t\t\twhile((line = reader.readLine()) != null) {" + NL + "\t\t\t\t\t\t\tSystem.err.println(line);" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t} finally {" + NL + "\t\t\t\t\t\treader.close();" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t} catch(java.io.IOException ioe) {" + NL + "\t\t\t\t\tioe.printStackTrace();" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t};" + NL + "\t\terror_";
  protected final String TEXT_165 = ".start();" + NL + "\t";
  protected final String TEXT_166 = "\t\t\t" + NL + "\tprocess_";
  protected final String TEXT_167 = ".waitFor();" + NL + "\tnormal_";
  protected final String TEXT_168 = ".interrupt();" + NL + "\t";
  protected final String TEXT_169 = NL + "\t\terror_";
  protected final String TEXT_170 = ".interrupt();" + NL + "\t";
  protected final String TEXT_171 = "\t" + NL + "\t\tlog.info(\"";
  protected final String TEXT_172 = " - Has been imported \"+globalMap.get(\"";
  protected final String TEXT_173 = "_NB_LINE\")+\" objects.\");" + NL + "\t\tlog.info(\"";
  protected final String TEXT_174 = " - Finish imported.\");" + NL + "\t";
  protected final String TEXT_175 = "\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
  	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
  	INode node = (INode)codeGenArgument.getArgument();
  	String cid = node.getUniqueName();

    String dbhost   = ElementParameterParser.getValue(node, "__HOST__");
    String dbname   = ElementParameterParser.getValue(node, "__DATABASE__");
    String collection = ElementParameterParser.getValue(node,"__COLLECTION__");
    boolean useSSL ="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__USE_SSL__"));
    boolean authentication="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REQUIRED_AUTHENTICATION__"));
    String authenticationMechanism = ElementParameterParser.getValue(node, "__AUTHENTICATION_MECHANISM__");
    String gssapiServiceName = ElementParameterParser.getValue(node, "__KRB_SERVICE_NAME__");
    String gssapiHostName = ElementParameterParser.getValue(node, "__KRB_HOST_NAME__");
    String userPrincipal = ElementParameterParser.getValue(node, "__KRB_USER_PRINCIPAL__");
    String authenticationDatabase = ElementParameterParser.getValue(node, "__KRB_AUTH_DATABASE__");
    String dbuser   = ElementParameterParser.getValue(node, "__USERNAME__");
    String dbpass   = ElementParameterParser.getValue(node, "__PASSWORD__");
    String dbport  	= ElementParameterParser.getValue(node, "__PORT__");
  	String mongoBinFolder = ElementParameterParser.getValue(node, "__MONGODB_DIR__");
  	String dataFileName = ElementParameterParser.getValue(node, "__DATA__");
  	String fileType = ElementParameterParser.getValue(node, "__FILE_TYPE__");
  	boolean isFirstLineHeader = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__HEADERLINE__"));
  	boolean useReplicaSet="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__USE_REPLICA_SET__"));
  	boolean useLocalDBpath="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__USE_LOCAL_DBPATH__"));
  	String localDBpath=ElementParameterParser.getValue(node, "__LOCAL_DB_PATH__");
  	boolean printLog="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__PRINT_LOG__"));
  	boolean dropCreateColl="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__DROP_COLLECTION_CREATE__"));
    boolean ignoreBlanks="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__IGNORE_BLANKS__"));
    boolean jsonArray = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__JSON_ARRAY__"));
    boolean useAuthDB = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__SET_AUTHENTICATION_DATABASE__"));
    String authDB = ElementParameterParser.getValue(node, "__AUTHENTICATION_DATABASE__");
    String usedAuthenticationDB = useAuthDB ? authDB : dbname;
  	
  	String dataAction = ElementParameterParser.getValue(node,"__DATA_ACTION__");
  	List<Map<String, String>> additionalParams = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__ADDITIONAL_ARGS__");
  	
  	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
  	
  	List<IMetadataTable> metadatas = node.getMetadataList();
  	String fileds="";
  	if(!"JSON".equalsIgnoreCase(fileType) && metadatas != null && metadatas.size() > 0){
      	IMetadataTable metadata = metadatas.get(0);
      	if(metadata != null){
      		List<IMetadataColumn> columnList = metadata.getListColumns();
      		boolean isFirstColumn=true;
      		for (IMetadataColumn column:columnList) {
      			if(isFirstColumn){
      				isFirstColumn=false;
      			}else{
      				fileds=fileds+",";
      			}
      			fileds=fileds + column.getOriginalDbColumnName();
			}
		}
	}
  	
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    	
	if(mongoBinFolder!= null && !"".equals(mongoBinFolder)&& !"\"\"".equals(mongoBinFolder)){
	
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(mongoBinFolder);
    stringBuffer.append(TEXT_5);
    
	}
	
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    	

 
	if(useLocalDBpath){
	
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(localDBpath);
    stringBuffer.append(TEXT_16);
    
	}else{
		if(!useReplicaSet){
			if(dbhost!= null && !"".equals(dbhost)&& !"\"\"".equals(dbhost)){
			
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_20);
    	
			}
			if(dbport!= null && !"".equals(dbport)&& !"\"\"".equals(dbport)){
			
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_24);
    
			}
		}else{
		    
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    
            boolean specifyReplicaSet ="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__SPECIFY_REPLICA_SET__"));
            if (specifyReplicaSet) {
                String replicaSetName = ElementParameterParser.getValue(node, "__REPLICA_NAME__");
                
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(replicaSetName);
    stringBuffer.append(TEXT_29);
    
            }
		  	List<Map<String,String>> replicaAddrs= (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__REPLICA_SET__");
		  	boolean isFirstHost=true;
		  	for(Map<String,String> replicaAddr:replicaAddrs){
		  		String repHost = replicaAddr.get("REPLICA_HOST");
		  		String repPort = replicaAddr.get("REPLICA_PORT");
				if(repHost!= null && !"".equals(repHost)&& !"\"\"".equals(repHost)){
					if(isFirstHost){
						isFirstHost=false;
					}else{
					
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    
					}
					
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(repHost);
    stringBuffer.append(TEXT_34);
    
					if(repPort!= null && !"".equals(repPort)&& !"\"\"".equals(repPort)){
					
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(repPort);
    stringBuffer.append(TEXT_37);
    
					}
				}else{
					break;
				}
		  	}
			
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    
		}
		// SSL is not compatible with Kerberos in mongoimport
		if (useSSL && !(authenticationMechanism.equals("KERBEROS_MEC"))) {
		    
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    
		}
	}
	
	if(dbname!= null && !"".equals(dbname)&& !"\"\"".equals(dbname)){
	
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_47);
    
	}
	if(collection!= null && !"".equals(collection)&& !"\"\"".equals(collection)){
	
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(collection);
    stringBuffer.append(TEXT_51);
    
	}

	if (authentication){
        if((authenticationMechanism.equals("SCRAMSHA1_MEC")) || (authenticationMechanism.equals("MONGODBX509_MEC"))
                || (authenticationMechanism.equals("MONGODBCR_MEC") || (authenticationMechanism.equals("PLAIN_MEC")))) {
            if(dbuser!= null && !"".equals(dbuser)&& !"\"\"".equals(dbuser)){
            
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(dbuser);
    stringBuffer.append(TEXT_55);
    
            }
            if(dbpass!= null && !"".equals(dbpass)&& !"\"\"".equals(dbpass)){
                String passwordFieldName = "__PASSWORD__";
                
    stringBuffer.append(TEXT_56);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_59);
    } else {
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_62);
    }
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    
            }

            if(authenticationMechanism.equals("SCRAMSHA1_MEC")){
                
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(usedAuthenticationDB);
    stringBuffer.append(TEXT_72);
    
                // X509 can't work today without adding a property to locate the x509 certificate
            } else if(authenticationMechanism.equals("MONGODBX509_MEC")){
                
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(usedAuthenticationDB);
    stringBuffer.append(TEXT_78);
    
            } else if(authenticationMechanism.equals("MONGODBCR_MEC")){
                
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(usedAuthenticationDB);
    stringBuffer.append(TEXT_84);
    
            } else if(authenticationMechanism.equals("PLAIN_MEC")){
                
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
        } else { // GSSAPI SASL (KERBEROS)
        	// SSL is not compatible with Kerberos in mongoimport
        	if(!useSSL){
	        	
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    
	            if(gssapiServiceName!= null && !"".equals(gssapiServiceName)&& !"\"\"".equals(gssapiServiceName)){
	                
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(gssapiServiceName);
    stringBuffer.append(TEXT_96);
    
	            }
	            if(gssapiHostName!= null && !"".equals(gssapiHostName)&& !"\"\"".equals(gssapiHostName)){
	                
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(gssapiHostName);
    stringBuffer.append(TEXT_100);
    
	            }
	            if(userPrincipal!= null && !"".equals(userPrincipal)&& !"\"\"".equals(userPrincipal)){
	                
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_104);
    
	            }
	            if(authenticationDatabase!= null && !"".equals(authenticationDatabase)&& !"\"\"".equals(authenticationDatabase)){
	                
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(authenticationDatabase);
    stringBuffer.append(TEXT_108);
    
	            }
        	}
        }
	} // authentication

	if(fileType!= null && !"".equals(fileType)&& !"\"\"".equals(fileType)){
	
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(fileType);
    stringBuffer.append(TEXT_112);
    
	}
	if(dataFileName!= null && !"".equals(dataFileName)&& !"\"\"".equals(dataFileName)){
	
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(dataFileName);
    stringBuffer.append(TEXT_116);
    
	}
	if(dropCreateColl){
	
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    
	}
	if("UPSERT".equalsIgnoreCase(dataAction)){
		List<Map<String, String>> upsertFieldList = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__UPSERT_FIELDS__");
		String upsertFields = "";
		if(upsertFieldList!=null && upsertFieldList.size()>0){
			upsertFields = upsertFieldList.get(0).get("COLNAME");
			for(int i=1;i<upsertFieldList.size();i++){
				Map<String, String> field = upsertFieldList.get(i);
				upsertFields=upsertFields+","+field.get("COLNAME");
			}
		}
		
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    
		if(!"".equals(upsertFields)){
		
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(upsertFields);
    stringBuffer.append(TEXT_124);
    
		}
	}
	if(!"JSON".equalsIgnoreCase(fileType)){
		if(ignoreBlanks){
		
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    
		}
		if(isFirstLineHeader){
		    
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    
		} else if(!"".equals(fileds)){
		    
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(fileds);
    stringBuffer.append(TEXT_132);
    
		}

	} else {
	    if (jsonArray) {
	        
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    
	    }
	}

	if(additionalParams != null && additionalParams.size()>0){
		for(Map<String, String> param : additionalParams){
			String arg = param.get("ARGUMENT");
			String value = param.get("VALUE");
			if(arg!=null && !"".equals(arg)){
			
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    stringBuffer.append(arg);
    stringBuffer.append(TEXT_137);
    
				if(value!=null && !"".equals(value)){
				
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(value);
    stringBuffer.append(TEXT_140);
    
				}
			}
		}
	}
	if(isLog4jEnabled){
	
    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_143);
    
		if (useReplicaSet) {
		    
    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    
		} else {
		    
    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(dbhost);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(dbport);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(dbname);
    stringBuffer.append(TEXT_150);
    
		}
	}
	
    stringBuffer.append(TEXT_151);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_153);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_155);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_156);
    
					    if(printLog){
						
    stringBuffer.append(TEXT_157);
    
					    }
						
    stringBuffer.append(TEXT_158);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    
    if(printLog){
	
    stringBuffer.append(TEXT_162);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_165);
    
    }
	
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_167);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_168);
    
    if(printLog){
	
    stringBuffer.append(TEXT_169);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_170);
    
    }
    if(isLog4jEnabled){
	
    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_173);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_174);
    
	}
	
    stringBuffer.append(TEXT_175);
    return stringBuffer.toString();
  }
}
