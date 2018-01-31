package org.talend.designer.codegen.translators.databases.hbase;

import java.util.List;
import java.util.Map;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class THBaseConnectionBeginJava
{
  protected static String nl;
  public static synchronized THBaseConnectionBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THBaseConnectionBeginJava result = new THBaseConnectionBeginJava();
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
  protected final String TEXT_31 = NL + "\t\t// Add HADOOP_CONF_DIR to the classpath if it's present" + NL + "\t\tString hadoopConfDir_";
  protected final String TEXT_32 = " = System.getenv(\"HADOOP_CONF_DIR\");" + NL + "\t\tif(hadoopConfDir_";
  protected final String TEXT_33 = " != null){" + NL + "\t\t\tjava.net.URLClassLoader sysloader = (java.net.URLClassLoader) ClassLoader.getSystemClassLoader();" + NL + "\t\t\tjava.lang.reflect.Method method = java.net.URLClassLoader.class.getDeclaredMethod(\"addURL\", new Class[] { java.net.URL.class });" + NL + "\t\t\tmethod.setAccessible(true);" + NL + "\t\t\tmethod.invoke(sysloader,new Object[] { new java.io.File(hadoopConfDir_";
  protected final String TEXT_34 = ").toURI().toURL() });" + NL + "\t\t}" + NL + "\t\torg.apache.hadoop.conf.Configuration config";
  protected final String TEXT_35 = " = org.apache.hadoop.hbase.HBaseConfiguration.create();" + NL + "" + NL + "\t\t// Adding any talend-site.xml files on the classpath" + NL + "\t\tconfig";
  protected final String TEXT_36 = ".addResource(\"talend-site.xml\");" + NL + "\t\tString tldKerberosAuth_";
  protected final String TEXT_37 = " = config";
  protected final String TEXT_38 = ".get(\"talend.kerberos.authentication\", \"\");" + NL + "\t\tString tldKerberosKtPrincipal_";
  protected final String TEXT_39 = " = config";
  protected final String TEXT_40 = ".get(\"talend.kerberos.keytab.principal\", \"\");" + NL + "\t\tString tldKerberosKtPath_";
  protected final String TEXT_41 = " = config";
  protected final String TEXT_42 = ".get(\"talend.kerberos.keytab.path\", \"\");" + NL;
  protected final String TEXT_43 = NL + "\t\t\tlog.debug(\"CLASSPATH_CONFIGURATION_";
  protected final String TEXT_44 = "\" + \" Loaded : \" + config";
  protected final String TEXT_45 = ");" + NL + "\t\t\tlog.debug(\"CLASSPATH_CONFIGURATION_";
  protected final String TEXT_46 = "\" + \" key value pairs : \");" + NL + "\t\t\tjava.util.Iterator<java.util.Map.Entry<String,String>> iterator_log_";
  protected final String TEXT_47 = " = config";
  protected final String TEXT_48 = ".iterator();" + NL + "\t\t\twhile(iterator_log_";
  protected final String TEXT_49 = ".hasNext()){" + NL + "\t\t\t\tjava.util.Map.Entry<String,String> property = iterator_log_";
  protected final String TEXT_50 = ".next();" + NL + "\t\t\t\tlog.debug(\"CLASSPATH_CONFIGURATION_";
  protected final String TEXT_51 = " \" + property.getKey() + \" : \" + property.getValue());" + NL + "\t\t\t}" + NL + "\t\t";
  protected final String TEXT_52 = NL + "\t\tif(org.apache.hadoop.security.UserGroupInformation.isSecurityEnabled()){" + NL + "\t\t\t// Keytab configurations" + NL + "\t\t\tif(tldKerberosAuth_";
  protected final String TEXT_53 = ".equalsIgnoreCase(\"keytab\")){" + NL + "\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(tldKerberosKtPrincipal_";
  protected final String TEXT_54 = ", tldKerberosKtPath_";
  protected final String TEXT_55 = ");" + NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_56 = NL + "\t\torg.apache.hadoop.conf.Configuration config";
  protected final String TEXT_57 = " = org.apache.hadoop.hbase.HBaseConfiguration.create();" + NL + "\t\tconfig";
  protected final String TEXT_58 = ".clear();" + NL + "\t\tconfig";
  protected final String TEXT_59 = ".set(\"hbase.zookeeper.quorum\", ";
  protected final String TEXT_60 = "); " + NL + "\t\tconfig";
  protected final String TEXT_61 = ".set(\"hbase.zookeeper.property.clientPort\",";
  protected final String TEXT_62 = "); " + NL + "\t\tconfig";
  protected final String TEXT_63 = ".set(\"hbase.cluster.distributed\",\"true\"); ";
  protected final String TEXT_64 = NL + "\t\t\tconfig";
  protected final String TEXT_65 = ".set(\"zookeeper.znode.parent\",";
  protected final String TEXT_66 = "); ";
  protected final String TEXT_67 = NL + "                System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_68 = ");" + NL + "                System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_69 = ");";
  protected final String TEXT_70 = NL + "\t\t\tconfig";
  protected final String TEXT_71 = ".set(\"hbase.master.kerberos.principal\",";
  protected final String TEXT_72 = ");" + NL + "\t\t\tconfig";
  protected final String TEXT_73 = ".set(\"hbase.regionserver.kerberos.principal\",";
  protected final String TEXT_74 = ");" + NL + "\t\t\tconfig";
  protected final String TEXT_75 = ".set(\"hbase.security.authorization\",\"true\");" + NL + "\t\t\tconfig";
  protected final String TEXT_76 = ".set(\"hbase.security.authentication\",\"kerberos\");" + NL;
  protected final String TEXT_77 = NL + "\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_78 = ", ";
  protected final String TEXT_79 = ");";
  protected final String TEXT_80 = NL + "                com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_81 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "                maprLogin_";
  protected final String TEXT_82 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_83 = ", ";
  protected final String TEXT_84 = ");";
  protected final String TEXT_85 = NL + "                System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_86 = ");" + NL + "                com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_87 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_88 = NL + "                    System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_89 = ");";
  protected final String TEXT_90 = NL + "                    maprLogin_";
  protected final String TEXT_91 = ".setCheckUGI(false);";
  protected final String TEXT_92 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_93 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_94 = ");";
  protected final String TEXT_95 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_96 = " = ";
  protected final String TEXT_97 = "; ";
  protected final String TEXT_98 = NL;
  protected final String TEXT_99 = NL + "                \tmaprLogin_";
  protected final String TEXT_100 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_101 = ", ";
  protected final String TEXT_102 = ", decryptedPassword_";
  protected final String TEXT_103 = ", ";
  protected final String TEXT_104 = ", \"\");" + NL + "                \t";
  protected final String TEXT_105 = NL + NL + "                \tmaprLogin_";
  protected final String TEXT_106 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_107 = ", ";
  protected final String TEXT_108 = ", decryptedPassword_";
  protected final String TEXT_109 = ", ";
  protected final String TEXT_110 = ");" + NL + "                \t";
  protected final String TEXT_111 = NL + "\t\tconfig";
  protected final String TEXT_112 = ".set(";
  protected final String TEXT_113 = ",";
  protected final String TEXT_114 = ");";
  protected final String TEXT_115 = NL + "\tglobalMap.put(\"conn_";
  protected final String TEXT_116 = "\",config";
  protected final String TEXT_117 = ");";

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
	String zookeeper_quorum = ElementParameterParser.getValue(node, "__ZOOKEEPER_QUORUM__");
	String zookeeper_client_port = ElementParameterParser.getValue(node, "__ZOOKEEPER_CLIENT_PORT__");
	
	boolean setZNodeParent = "true".equals(ElementParameterParser.getValue(node, "__SET_ZNODE_PARENT__"));
	String zNodeParent = ElementParameterParser.getValue(node, "__ZNODE_PARENT__");

	String distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
	String hbaseVersion = ElementParameterParser.getValue(node, "__HBASE_VERSION__");
	boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
	boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
	String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
	String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
	String hbaseMasterPrincipal = ElementParameterParser.getValue(node, "__HBASE_MASTER_PRINCIPAL__");
	String hbaseRegionServerPrincipal = ElementParameterParser.getValue(node, "__HBASE_REGIONSERVER_PRINCIPAL__");
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	boolean configureFromClassPath = "true".equals(ElementParameterParser.getValue(node, "__CONFIGURATIONS_FROM_CLASSPATH__"));

    boolean useMapRTicket = ElementParameterParser.getBooleanValue(node, "__USE_MAPRTICKET__");
    String username = ElementParameterParser.getValue(node, "__USERNAME__");
    String mapRTicketCluster = ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
    String mapRTicketDuration = ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");

    boolean setMapRHomeDir = ElementParameterParser.getBooleanValue(node, "__SET_MAPR_HOME_DIR__");
    String mapRHomeDir = ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");

    boolean setMapRHadoopLogin = ElementParameterParser.getBooleanValue(node, "__SET_HADOOP_LOGIN__");
    String mapRHadoopLogin = ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");
    

	
	org.talend.hadoop.distribution.component.HBaseComponent hbaseDistrib = null;
	try {
		hbaseDistrib = (org.talend.hadoop.distribution.component.HBaseComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hbaseVersion);
	} catch (java.lang.Exception e) {
		e.printStackTrace();
		return "";
	}

    boolean isCustom = hbaseDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

	log4jFileUtil.componentStartInfo(node);
	// BEGIN IF01
	if(configureFromClassPath){

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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    
		// log all loaded xxx-site.xml files and all of the key value pairs for debugging
		if(isLog4jEnabled){
		
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
    
		}

    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    
	}
	// END IF01
	else{

    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(zookeeper_quorum);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(zookeeper_client_port);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    
		if(setZNodeParent) {

    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(zNodeParent);
    stringBuffer.append(TEXT_66);
    
		}

		if(hbaseDistrib.doSupportKerberos() && useKrb){
            if ((isCustom || hbaseDistrib.doSupportMapRTicket()) && useMapRTicket) {
                
    stringBuffer.append(TEXT_67);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_68);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_69);
    
            }

    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(hbaseMasterPrincipal);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(hbaseRegionServerPrincipal);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    
			if(useKeytab){

    stringBuffer.append(TEXT_77);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_79);
    
			}
            if ((isCustom || hbaseDistrib.doSupportMapRTicket()) && useMapRTicket) {
                
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_84);
    
            }
		} else {
		    // MapR ticket
		    if ((isCustom || hbaseDistrib.doSupportMapRTicket()) && useMapRTicket) {
                String passwordFieldName = "__MAPRTICKET_PASSWORD__";
                
    stringBuffer.append(TEXT_85);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    
                if (setMapRHadoopLogin) {
                    
    stringBuffer.append(TEXT_88);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_89);
    
                } else {
                    
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    
                }
                
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_94);
    } else {
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_97);
    }
    stringBuffer.append(TEXT_98);
    
                if(hbaseDistrib.doSupportMaprTicketV52API()){
                	
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_104);
    
                } else {
                	
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_110);
    
                }
            }
		}
	}

	List<Map<String, String>> properties =
        (List<Map<String,String>>)ElementParameterParser.getObjectValue(node,"__HBASE_PARAMETERS__");
   for(int i=0;i<properties.size();i++){
   		Map<String, String> map = properties.get(i);
   		String property = map.get("PROPERTY");
   		String value= map.get("VALUE");

    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(property);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(value);
    stringBuffer.append(TEXT_114);
    
   }

    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    return stringBuffer.toString();
  }
}
