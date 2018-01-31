package org.talend.designer.codegen.translators.databases.hbase;

import java.util.List;
import java.util.Map;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class THBaseOutputBeginJava
{
  protected static String nl;
  public static synchronized THBaseOutputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THBaseOutputBeginJava result = new THBaseOutputBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "\t";
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
  protected final String TEXT_30 = NL + "\t";
  protected final String TEXT_31 = NL + "\t\t\torg.apache.hadoop.hbase.HTableDescriptor tableDes_";
  protected final String TEXT_32 = " = new org.apache.hadoop.hbase.HTableDescriptor(";
  protected final String TEXT_33 = ");" + NL + "\t\t\torg.apache.hadoop.hbase.HColumnDescriptor family_";
  protected final String TEXT_34 = " = null;";
  protected final String TEXT_35 = NL + "\t\t\t\tfamily_";
  protected final String TEXT_36 = " = new org.apache.hadoop.hbase.HColumnDescriptor(";
  protected final String TEXT_37 = ");";
  protected final String TEXT_38 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_39 = ".setInMemory(";
  protected final String TEXT_40 = ");";
  protected final String TEXT_41 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_42 = ".setBlockCacheEnabled(";
  protected final String TEXT_43 = ");";
  protected final String TEXT_44 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_45 = ".setBloomFilterType(" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_46 = NL + "\t\t\t\t\t\torg.apache.hadoop.hbase.regionserver.BloomType.";
  protected final String TEXT_47 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_48 = NL + "\t\t\t\t\t\torg.apache.hadoop.hbase.regionserver.StoreFile.BloomType.";
  protected final String TEXT_49 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_50 = NL + "\t\t\t\t\t);";
  protected final String TEXT_51 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_52 = ".setBlocksize(";
  protected final String TEXT_53 = ");";
  protected final String TEXT_54 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_55 = ".setCompactionCompressionType(" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_56 = NL + "\t\t\t\t\t\torg.apache.hadoop.hbase.io.compress.Compression.Algorithm.";
  protected final String TEXT_57 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_58 = NL + "\t\t\t\t\t\torg.apache.hadoop.hbase.io.hfile.Compression.Algorithm.";
  protected final String TEXT_59 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_60 = NL + "\t\t\t\t\t);";
  protected final String TEXT_61 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_62 = ".setCompressionType(" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_63 = NL + "\t\t\t\t\t\torg.apache.hadoop.hbase.io.compress.Compression.Algorithm.";
  protected final String TEXT_64 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_65 = NL + "\t\t\t\t\t\torg.apache.hadoop.hbase.io.hfile.Compression.Algorithm.";
  protected final String TEXT_66 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_67 = NL + "\t\t\t\t\t);";
  protected final String TEXT_68 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_69 = ".setMaxVersions(";
  protected final String TEXT_70 = ");";
  protected final String TEXT_71 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_72 = ".setScope(";
  protected final String TEXT_73 = ");";
  protected final String TEXT_74 = NL + "\t\t\t\t\tfamily_";
  protected final String TEXT_75 = ".setTimeToLive(";
  protected final String TEXT_76 = ");";
  protected final String TEXT_77 = NL + "\t\t\t\ttableDes_";
  protected final String TEXT_78 = ".addFamily(family_";
  protected final String TEXT_79 = ");";
  protected final String TEXT_80 = NL + "\t\tadmin_";
  protected final String TEXT_81 = ".createTable(tableDes_";
  protected final String TEXT_82 = ");";
  protected final String TEXT_83 = NL + "\t\tif(!admin_";
  protected final String TEXT_84 = ".isTableDisabled(";
  protected final String TEXT_85 = ")){" + NL + "\t\t\tadmin_";
  protected final String TEXT_86 = ".disableTable(";
  protected final String TEXT_87 = ");" + NL + "\t\t}" + NL + "\t\t\tadmin_";
  protected final String TEXT_88 = ".deleteTable(";
  protected final String TEXT_89 = ");";
  protected final String TEXT_90 = NL + "int nb_line_";
  protected final String TEXT_91 = " = 0;" + NL + "org.apache.hadoop.conf.Configuration conn_";
  protected final String TEXT_92 = "=null;";
  protected final String TEXT_93 = NL + "\tconn_";
  protected final String TEXT_94 = " = org.apache.hadoop.hbase.HBaseConfiguration.create();" + NL + "\tresourceMap.put(\"conn_";
  protected final String TEXT_95 = "\", conn_";
  protected final String TEXT_96 = "); " + NL + "\tconn_";
  protected final String TEXT_97 = ".clear();" + NL + "\tconn_";
  protected final String TEXT_98 = ".set(\"hbase.zookeeper.quorum\", ";
  protected final String TEXT_99 = "); " + NL + "\tconn_";
  protected final String TEXT_100 = ".set(\"hbase.zookeeper.property.clientPort\",";
  protected final String TEXT_101 = "); " + NL + "\tconn_";
  protected final String TEXT_102 = ".set(\"hbase.cluster.distributed\",\"true\"); ";
  protected final String TEXT_103 = NL + "\tconn_";
  protected final String TEXT_104 = ".set(\"zookeeper.znode.parent\",";
  protected final String TEXT_105 = "); ";
  protected final String TEXT_106 = NL + "            System.setProperty(\"pname\", \"MapRLogin\");" + NL + "            System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "            System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_107 = ");" + NL + "            System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_108 = ");";
  protected final String TEXT_109 = NL + "\t\tconn_";
  protected final String TEXT_110 = ".set(\"hbase.master.kerberos.principal\",";
  protected final String TEXT_111 = ");" + NL + "\t\tconn_";
  protected final String TEXT_112 = ".set(\"hbase.regionserver.kerberos.principal\",";
  protected final String TEXT_113 = ");" + NL + "\t\tconn_";
  protected final String TEXT_114 = ".set(\"hbase.security.authorization\",\"true\");" + NL + "\t\tconn_";
  protected final String TEXT_115 = ".set(\"hbase.security.authentication\",\"kerberos\");";
  protected final String TEXT_116 = NL + "\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_117 = ", ";
  protected final String TEXT_118 = ");";
  protected final String TEXT_119 = NL + "            com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_120 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "            maprLogin_";
  protected final String TEXT_121 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_122 = ", ";
  protected final String TEXT_123 = ");";
  protected final String TEXT_124 = NL + "            System.setProperty(\"pname\", \"MapRLogin\");" + NL + "            System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "            System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_125 = ");" + NL + "            com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_126 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_127 = NL + "                System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_128 = ");";
  protected final String TEXT_129 = NL + "                maprLogin_";
  protected final String TEXT_130 = ".setCheckUGI(false);";
  protected final String TEXT_131 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_132 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_133 = ");";
  protected final String TEXT_134 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_135 = " = ";
  protected final String TEXT_136 = "; ";
  protected final String TEXT_137 = NL;
  protected final String TEXT_138 = NL + "            \tmaprLogin_";
  protected final String TEXT_139 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_140 = ", ";
  protected final String TEXT_141 = ", decryptedPassword_";
  protected final String TEXT_142 = ", ";
  protected final String TEXT_143 = ", \"\");" + NL + "            \t";
  protected final String TEXT_144 = NL + "            \tmaprLogin_";
  protected final String TEXT_145 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_146 = ", ";
  protected final String TEXT_147 = ", decryptedPassword_";
  protected final String TEXT_148 = ", ";
  protected final String TEXT_149 = ");" + NL + "            \t";
  protected final String TEXT_150 = NL + "\t\tconn_";
  protected final String TEXT_151 = ".set(";
  protected final String TEXT_152 = ",";
  protected final String TEXT_153 = ");";
  protected final String TEXT_154 = NL + "\tconn_";
  protected final String TEXT_155 = " = (org.apache.hadoop.conf.Configuration)globalMap.get(\"";
  protected final String TEXT_156 = "\");" + NL + "\tif(conn_";
  protected final String TEXT_157 = " == null){" + NL + "\t\tthrow new RuntimeException(\"";
  protected final String TEXT_158 = "'s connection is null!\");" + NL + "\t}";
  protected final String TEXT_159 = NL + "\tconn_";
  protected final String TEXT_160 = ".set(\"hbase.table.namespace.mappings\", ";
  protected final String TEXT_161 = ");";
  protected final String TEXT_162 = NL + "\torg.apache.hadoop.hbase.client.HBaseAdmin admin_";
  protected final String TEXT_163 = " = new org.apache.hadoop.hbase.client.HBaseAdmin(conn_";
  protected final String TEXT_164 = ");";
  protected final String TEXT_165 = NL + "\tboolean tableExist_";
  protected final String TEXT_166 = " = admin_";
  protected final String TEXT_167 = ".tableExists(";
  protected final String TEXT_168 = ");" + NL + "\tif(!tableExist_";
  protected final String TEXT_169 = "){";
  protected final String TEXT_170 = NL + "\t}";
  protected final String TEXT_171 = NL + "\tboolean tableExist_";
  protected final String TEXT_172 = " = admin_";
  protected final String TEXT_173 = ".tableExists(";
  protected final String TEXT_174 = ");" + NL + "\tif(tableExist_";
  protected final String TEXT_175 = "){";
  protected final String TEXT_176 = NL + "\t}";
  protected final String TEXT_177 = NL + "org.apache.hadoop.hbase.client.HTable table_";
  protected final String TEXT_178 = " = null;" + NL + "table_";
  protected final String TEXT_179 = " = new org.apache.hadoop.hbase.client.HTable(conn_";
  protected final String TEXT_180 = ",";
  protected final String TEXT_181 = ");" + NL + "byte[] temp_";
  protected final String TEXT_182 = " = null;" + NL;
  protected final String TEXT_183 = NL + "\tjava.util.ArrayList<org.apache.hadoop.hbase.client.Put> batchPuts_";
  protected final String TEXT_184 = " = new java.util.ArrayList<org.apache.hadoop.hbase.client.Put>();" + NL + "\t";

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
	
    stringBuffer.append(TEXT_30);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");
	String zookeeper_quorum = ElementParameterParser.getValue(node, "__ZOOKEEPER_QUORUM__");
	String zookeeper_client_port = ElementParameterParser.getValue(node, "__ZOOKEEPER_CLIENT_PORT__");
	String table_name = ElementParameterParser.getValue(node, "__TABLE__");

	boolean setTableNsMapping = "true".equals(ElementParameterParser.getValue(node, "__SET_TABLE_NS_MAPPING__"));
	String tableNsMapping = ElementParameterParser.getValue(node, "__TABLE_NS_MAPPING__");
	boolean useTableNsMapping = setTableNsMapping && ((tableNsMapping != null)&&(!tableNsMapping.equals("")));

	boolean useBatchMode = ElementParameterParser.getBooleanValue(node, "__USE_BATCH_MODE__");
	
	String distribution = null;
	String hbaseVersion = null;
	boolean isHadoop2 = false;

	if(!"true".equals(useExistingConn)) {
		distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
		hbaseVersion = ElementParameterParser.getValue(node, "__HBASE_VERSION__");
		isHadoop2 = "HADOOP_2".equals(ElementParameterParser.getValue(node, "__HADOOP_CUSTOM_VERSION__"));
	} else {
		String connectionSid = ElementParameterParser.getValue(node, "__CONNECTION__");
		List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
		for(INode targetNode : nodes){
			if (targetNode.getUniqueName().equals(connectionSid)) {
				distribution = ElementParameterParser.getValue(targetNode, "__DISTRIBUTION__");
				hbaseVersion = ElementParameterParser.getValue(targetNode, "__HBASE_VERSION__");
				isHadoop2 = "HADOOP_2".equals(ElementParameterParser.getValue(targetNode, "__HADOOP_CUSTOM_VERSION__"));
				break;
			}
		}
	}
	
	log4jFileUtil.componentStartInfo(node);
	
	org.talend.hadoop.distribution.component.HBaseComponent hbaseDistrib = null;
	try {
		hbaseDistrib = (org.talend.hadoop.distribution.component.HBaseComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hbaseVersion);
	} catch (java.lang.Exception e) {
		e.printStackTrace();
		return "";
	}
	boolean isCustom = hbaseDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

	class GenerateCode {

		private boolean isCustom;
		private boolean isHadoop2;
		private org.talend.hadoop.distribution.component.HBaseComponent hbaseDistrib;

		public GenerateCode(boolean isCustom, boolean isHadoop2, org.talend.hadoop.distribution.component.HBaseComponent hbaseDistrib) {
			this.isCustom = isCustom;
			this.isHadoop2 = isHadoop2;
			this.hbaseDistrib = hbaseDistrib;
		}
		
		public void generateCreateTable(INode node) {
			List<Map<String,String>> familyParameters = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node,"__FAMILY_PARAMETERS__");
			String table_name = ElementParameterParser.getValue(node, "__TABLE__");
			String cid = node.getUniqueName();

    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(table_name);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    
			for(int familyParamNum=0;familyParamNum<familyParameters.size();familyParamNum++){
				Map<String,String> mapParamLine = familyParameters.get(familyParamNum);
				String family_name = mapParamLine.get("FAMILY_NAME");

    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(family_name);
    stringBuffer.append(TEXT_37);
    
				String family_inmemory = mapParamLine.get("FAMILY_INMEMORY");
				String family_blockcacheenabled = mapParamLine.get("FAMILY_BLOCKCACHEENABLED");
				String family_bloomfiltertype = mapParamLine.get("FAMILY_BLOOMFILTERTYPE");
				String family_blocksize = mapParamLine.get("FAMILY_BLOCKSIZE");
				String family_compactioncompressiontype = mapParamLine.get("FAMILY_COMPACTIONCOMPRESSIONTYPE");
				String family_compressiontype = mapParamLine.get("FAMILY_COMPRESSIONTYPE");
				String family_maxversions = mapParamLine.get("FAMILY_MAXVERSIONS");
				String family_scope = mapParamLine.get("FAMILY_SCOPE");
				String family_timetolive = mapParamLine.get("FAMILY_TIMETOLIVE");
				
				if(family_inmemory!=null&&!"".equals(family_inmemory)){

    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(family_inmemory);
    stringBuffer.append(TEXT_40);
    
				}
				if(family_blockcacheenabled!=null&&!"".equals(family_blockcacheenabled)){

    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(family_blockcacheenabled);
    stringBuffer.append(TEXT_43);
    
				}
				if(family_bloomfiltertype!=null&&!"".equals(family_bloomfiltertype)){

    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    if((isCustom && isHadoop2) || (!isCustom && hbaseDistrib.doSupportNewHBaseAPI())) {
    stringBuffer.append(TEXT_46);
    stringBuffer.append(family_bloomfiltertype);
    stringBuffer.append(TEXT_47);
    } else {
    stringBuffer.append(TEXT_48);
    stringBuffer.append(family_bloomfiltertype);
    stringBuffer.append(TEXT_49);
    }
    stringBuffer.append(TEXT_50);
    
				}
				if(family_blocksize!=null&&!"".equals(family_blocksize)){

    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(family_blocksize);
    stringBuffer.append(TEXT_53);
    
				}
				if(family_compactioncompressiontype!=null&&!"".equals(family_compactioncompressiontype)){

    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    if((isCustom && isHadoop2) || (!isCustom && hbaseDistrib.doSupportNewHBaseAPI())) {
    stringBuffer.append(TEXT_56);
    stringBuffer.append(family_compactioncompressiontype);
    stringBuffer.append(TEXT_57);
    } else {
    stringBuffer.append(TEXT_58);
    stringBuffer.append(family_compactioncompressiontype);
    stringBuffer.append(TEXT_59);
    }
    stringBuffer.append(TEXT_60);
    
				}
				if(family_compressiontype!=null&&!"".equals(family_compressiontype)){

    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    if((isCustom && isHadoop2) || (!isCustom && hbaseDistrib.doSupportNewHBaseAPI())) {
    stringBuffer.append(TEXT_63);
    stringBuffer.append(family_compressiontype);
    stringBuffer.append(TEXT_64);
    } else {
    stringBuffer.append(TEXT_65);
    stringBuffer.append(family_compressiontype);
    stringBuffer.append(TEXT_66);
    }
    stringBuffer.append(TEXT_67);
    
				}
				if(family_maxversions!=null&&!"".equals(family_maxversions)){

    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(family_maxversions);
    stringBuffer.append(TEXT_70);
    
				}
				if(family_scope!=null&&!"".equals(family_scope)){

    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(family_scope);
    stringBuffer.append(TEXT_73);
    
				}
				if(family_timetolive!=null&&!"".equals(family_timetolive)){

    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(family_timetolive);
    stringBuffer.append(TEXT_76);
    
				}

    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    
			}//for(int familyParamNum=0;familyParamNum<familyParameters.size();familyParamNum++)

    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    
		}
		public void generateDropTable(INode node){
			String cid = node.getUniqueName();
			String table_name = ElementParameterParser.getValue(node, "__TABLE__");

    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_84);
    stringBuffer.append(table_name);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_86);
    stringBuffer.append(table_name);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_88);
    stringBuffer.append(table_name);
    stringBuffer.append(TEXT_89);
    
		}
	}
	GenerateCode generateCode = new GenerateCode(isCustom, isHadoop2, hbaseDistrib);

    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_92);
     
// not use existing connection
if(!"true".equals(useExistingConn)){
	boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
	boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
	String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
	String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
	String hbaseMasterPrincipal = ElementParameterParser.getValue(node, "__HBASE_MASTER_PRINCIPAL__");
	String hbaseRegionServerPrincipal = ElementParameterParser.getValue(node, "__HBASE_REGIONSERVER_PRINCIPAL__");

    boolean useMapRTicket = ElementParameterParser.getBooleanValue(node, "__USE_MAPRTICKET__");
    String username = ElementParameterParser.getValue(node, "__USERNAME__");
    String mapRTicketCluster = ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
    String mapRTicketDuration = ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");

    boolean setMapRHomeDir = ElementParameterParser.getBooleanValue(node, "__SET_MAPR_HOME_DIR__");
    String mapRHomeDir = ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");

    boolean setMapRHadoopLogin = ElementParameterParser.getBooleanValue(node, "__SET_HADOOP_LOGIN__");
    String mapRHadoopLogin = ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");


    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_98);
    stringBuffer.append(zookeeper_quorum);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_100);
    stringBuffer.append(zookeeper_client_port);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_102);
    
	boolean setZNodeParent = "true".equals(ElementParameterParser.getValue(node, "__SET_ZNODE_PARENT__"));
	String zNodeParent = ElementParameterParser.getValue(node, "__ZNODE_PARENT__");		
	if(setZNodeParent) {

    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(zNodeParent);
    stringBuffer.append(TEXT_105);
    
	}
	if(hbaseDistrib.doSupportKerberos() && useKrb){
        if((isCustom || hbaseDistrib.doSupportMapRTicket()) && useMapRTicket) {
            
    stringBuffer.append(TEXT_106);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_107);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_108);
    
        }

    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(hbaseMasterPrincipal);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(hbaseRegionServerPrincipal);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    
		if(useKeytab){

    stringBuffer.append(TEXT_116);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_118);
    
		}
        if((isCustom || hbaseDistrib.doSupportMapRTicket()) && useMapRTicket) {
            
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_123);
    
        }
	} else {
        // MapR ticket
	    if((isCustom || hbaseDistrib.doSupportMapRTicket()) && useMapRTicket) {
            String passwordFieldName = "__MAPRTICKET_PASSWORD__";
            
    stringBuffer.append(TEXT_124);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    
            if (setMapRHadoopLogin) {
                
    stringBuffer.append(TEXT_127);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_128);
    
            } else {
                
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    
            }
            
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_133);
    } else {
    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_135);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_136);
    }
    stringBuffer.append(TEXT_137);
    
            if(hbaseDistrib.doSupportMaprTicketV52API()){
				
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_143);
    
            } else {
            	
    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_149);
    
            }
        }
    }
	List<Map<String, String>> properties =
        (List<Map<String,String>>)ElementParameterParser.getObjectValue(node,"__HBASE_PARAMETERS__");
   	for(int i=0;i<properties.size();i++){
   		Map<String, String> map = properties.get(i);
   		String property = map.get("PROPERTY");
   		String value= map.get("VALUE");

    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_151);
    stringBuffer.append(property);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(value);
    stringBuffer.append(TEXT_153);
    
   }
}else{// use existing connection
	String connection = ElementParameterParser.getValue(node,"__CONNECTION__");
	String conn = "conn_" + connection;

    stringBuffer.append(TEXT_154);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_155);
    stringBuffer.append(conn);
    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_157);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_158);
    
}
if(hbaseDistrib.doSupportMapRDB() && useTableNsMapping){

    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_160);
    stringBuffer.append(tableNsMapping);
    stringBuffer.append(TEXT_161);
    
}

String table_action = ElementParameterParser.getValue(node, "__TABLE_ACTION__");
if(!"NONE".equals(table_action)){

    stringBuffer.append(TEXT_162);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_164);
    
}
if("NONE".equals(table_action)){
}else if("CREATE".equals(table_action)){
	generateCode.generateCreateTable(node);
}else if("DROP_CREATE".equals(table_action)){
	generateCode.generateDropTable(node);
	generateCode.generateCreateTable(node);
}else if("CREATE_IF_NOT_EXISTS".equals(table_action)){

    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_167);
    stringBuffer.append(table_name);
    stringBuffer.append(TEXT_168);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_169);
    
		generateCode.generateCreateTable(node);

    stringBuffer.append(TEXT_170);
    
}else if("DROP_IF_EXISTS_AND_CREATE".equals(table_action)){

    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_173);
    stringBuffer.append(table_name);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_175);
    
		generateCode.generateDropTable(node); 

    stringBuffer.append(TEXT_176);
    
	generateCode.generateCreateTable(node);
}

    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_180);
    stringBuffer.append(table_name);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_182);
    
if (useBatchMode) {
	
    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    
}

    return stringBuffer.toString();
  }
}
