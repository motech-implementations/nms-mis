package org.talend.designer.codegen.translators.databases.cassandra;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TCassandraConnectionMainJava
{
  protected static String nl;
  public static synchronized TCassandraConnectionMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCassandraConnectionMainJava result = new TCassandraConnectionMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL + "    \t";
  protected final String TEXT_4 = NL + "\t";
  protected final String TEXT_5 = NL + "\t";
  protected final String TEXT_6 = NL + "\t";
  protected final String TEXT_7 = NL + "\t\tjavax.net.ssl.KeyManagerFactory kmf_";
  protected final String TEXT_8 = " = null;" + NL + "\t\t";
  protected final String TEXT_9 = NL + "\t\t\tjava.security.KeyStore ks_";
  protected final String TEXT_10 = " = java.security.KeyStore.getInstance(\"";
  protected final String TEXT_11 = "\");" + NL + "\t\t\tks_";
  protected final String TEXT_12 = ".load(new java.io.FileInputStream(";
  protected final String TEXT_13 = "), ";
  protected final String TEXT_14 = ".toCharArray());" + NL + "\t\t\tkmf_";
  protected final String TEXT_15 = " = javax.net.ssl.KeyManagerFactory.getInstance(javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());" + NL + "\t\t\tkmf_";
  protected final String TEXT_16 = ".init(ks_";
  protected final String TEXT_17 = ", ";
  protected final String TEXT_18 = ".toCharArray());" + NL + "\t\t";
  protected final String TEXT_19 = NL + "\t\t" + NL + "\t\tjava.security.KeyStore ts_";
  protected final String TEXT_20 = " = java.security.KeyStore.getInstance(\"";
  protected final String TEXT_21 = "\");" + NL + "\t\tts_";
  protected final String TEXT_22 = ".load(new java.io.FileInputStream(";
  protected final String TEXT_23 = "), ";
  protected final String TEXT_24 = ".toCharArray());" + NL + "\t\tjavax.net.ssl.TrustManagerFactory tmf_";
  protected final String TEXT_25 = " = javax.net.ssl.TrustManagerFactory.getInstance(javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm());" + NL + "    \ttmf_";
  protected final String TEXT_26 = ".init(ts_";
  protected final String TEXT_27 = ");" + NL + "\t\t" + NL + "\t\tjavax.net.ssl.SSLContext sslContext_";
  protected final String TEXT_28 = " = javax.net.ssl.SSLContext.getInstance(\"TLS\");" + NL + "\t\tsslContext_";
  protected final String TEXT_29 = ".init(kmf_";
  protected final String TEXT_30 = " == null ? null : kmf_";
  protected final String TEXT_31 = ".getKeyManagers(), tmf_";
  protected final String TEXT_32 = ".getTrustManagers(), new java.security.SecureRandom());" + NL + "\t";
  protected final String TEXT_33 = NL + "    com.datastax.driver.core.Cluster cluster_";
  protected final String TEXT_34 = " = com.datastax.driver.core.Cluster.builder()" + NL + "    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t.addContactPoints(";
  protected final String TEXT_35 = ".split(\",\"))" + NL + "    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t.withPort(Integer.valueOf(";
  protected final String TEXT_36 = "))" + NL + "                                                            \t";
  protected final String TEXT_37 = NL + "                                                                \t.withCredentials(";
  protected final String TEXT_38 = ", ";
  protected final String TEXT_39 = ")" + NL + "                                                            \t";
  protected final String TEXT_40 = NL + "                                                            \t\t.withSSL(com.datastax.driver.core.JdkSSLOptions.builder()" + NL + "                                                            \t\t\t\t\t.withSSLContext(sslContext_";
  protected final String TEXT_41 = ").build())" + NL + "                                                            \t";
  protected final String TEXT_42 = NL + "    \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t.build();" + NL + "\t" + NL + "\tcom.datastax.driver.core.Session connection_";
  protected final String TEXT_43 = " = null;" + NL + "\tconnection_";
  protected final String TEXT_44 = " = cluster_";
  protected final String TEXT_45 = ".connect();" + NL + "\tglobalMap.put(\"cluster_";
  protected final String TEXT_46 = "\", cluster_";
  protected final String TEXT_47 = ");" + NL + "\tglobalMap.put(\"connection_";
  protected final String TEXT_48 = "\", connection_";
  protected final String TEXT_49 = ");";
  protected final String TEXT_50 = "    ";
  protected final String TEXT_51 = NL + "\t";
  protected final String TEXT_52 = NL + "    String hostIps_";
  protected final String TEXT_53 = "=";
  protected final String TEXT_54 = "+\":\"+";
  protected final String TEXT_55 = ";" + NL + "    me.prettyprint.cassandra.service.CassandraHostConfigurator hosts_";
  protected final String TEXT_56 = " = new me.prettyprint.cassandra.service.CassandraHostConfigurator(hostIps_";
  protected final String TEXT_57 = ");" + NL + "    hosts_";
  protected final String TEXT_58 = ".setAutoDiscoverHosts(true);" + NL + "    java.util.Map<String, String> credentials_";
  protected final String TEXT_59 = " =new java.util.HashMap<String,String>();" + NL + "\t";
  protected final String TEXT_60 = NL + "    \tcredentials_";
  protected final String TEXT_61 = ".put(\"username\",";
  protected final String TEXT_62 = ");" + NL + "    \t";
  protected final String TEXT_63 = NL + "        ";
  protected final String TEXT_64 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_65 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_66 = ");";
  protected final String TEXT_67 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_68 = " = ";
  protected final String TEXT_69 = "; ";
  protected final String TEXT_70 = NL + "    \t" + NL + "    \t" + NL + "    \tcredentials_";
  protected final String TEXT_71 = ".put(\"password\",decryptedPassword_";
  protected final String TEXT_72 = ");" + NL + "\t";
  protected final String TEXT_73 = NL + "\tme.prettyprint.hector.api.Cluster cluster_";
  protected final String TEXT_74 = " = me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster(\"cluster_";
  protected final String TEXT_75 = "_\"+pid,hosts_";
  protected final String TEXT_76 = ",credentials_";
  protected final String TEXT_77 = ");" + NL + "\tif (cluster_";
  protected final String TEXT_78 = NL + "            .getConnectionManager().getHosts().isEmpty()) {" + NL + "        throw new Exception(\"Cannot connect to the server: \" + hostIps_";
  protected final String TEXT_79 = ");" + NL + "    }" + NL + "" + NL + "\tglobalMap.put(\"cluster_";
  protected final String TEXT_80 = "\", cluster_";
  protected final String TEXT_81 = ");" + NL + "    \t";
  protected final String TEXT_82 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();
    
    stringBuffer.append(TEXT_2);
    
//TODO: use API_SELECTED instead of this
class API_selector{
	public boolean useDatastax(INode node){
		String dbVersion = ElementParameterParser.getValue(node, "__DB_VERSION__");
        String apiType = ElementParameterParser.getValue(node, "__API_TYPE__");
        return "CASSANDRA_2_2".equals(dbVersion) || "CASSANDRA_3_0".equals(dbVersion) || ("CASSANDRA_2_0_0".equals(dbVersion) && "DATASTAX".equals(apiType)); 
	}
}	

    
    if((new API_selector()).useDatastax(node)){
    
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    
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

    stringBuffer.append(TEXT_6);
    
    String host = ElementParameterParser.getValue(node,"__HOST__");
    String port = ElementParameterParser.getValue(node,"__PORT__");
    String userName = ElementParameterParser.getValue(node, "__USERNAME__");
    String passWord = ElementParameterParser.getPasswordValue(node, "__PASSWORD__");
	boolean authentication = ElementParameterParser.getBooleanValue(node, "__REQUIRED_AUTHENTICATION__");
	INode sslNode = ElementParameterParser.getLinkedNodeValue(node, "__HTTPS_SETTING__");
	SSL_HELPER sslHelper = new SSL_HELPER(sslNode);
	boolean useSSL = ElementParameterParser.getBooleanValue(node, "__USE_HTTPS__") && sslHelper.useHTTPS();
	if(useSSL){
	
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    if(sslHelper.needKS()){
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(sslHelper.getKSType());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(sslHelper.getKSPath());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(sslHelper.getKSPwd());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(sslHelper.getKSPwd());
    stringBuffer.append(TEXT_18);
    }
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(sslHelper.getTSType());
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(sslHelper.getTSPath());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(sslHelper.getTSPwd());
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    
	}
    
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(port);
    stringBuffer.append(TEXT_36);
    
                                                            	if(authentication){
                                                            	
    stringBuffer.append(TEXT_37);
    stringBuffer.append(userName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(passWord);
    stringBuffer.append(TEXT_39);
    
                                                            	}
                                                            	if(useSSL){
                                                            	
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    
                                                            	}
                                                            	
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
    
    }else{
    
    stringBuffer.append(TEXT_50);
    stringBuffer.append(TEXT_51);
    
    String host = ElementParameterParser.getValue(node,"__HOST__");
    String port = ElementParameterParser.getValue(node,"__PORT__");
    //String cluster= ElementParameterParser.getValue(node, "__CLUSTER__");
    String userName = ElementParameterParser.getValue(node, "__USERNAME__");
    String passWord = ElementParameterParser.getValue(node, "__PASSWORD__");
	boolean authentication="true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REQUIRED_AUTHENTICATION__"));
	
	
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(port);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    
	if (authentication){
	
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(userName);
    stringBuffer.append(TEXT_62);
    
        String passwordFieldName = "__PASSWORD__";
        
    stringBuffer.append(TEXT_63);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_66);
    } else {
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_69);
    }
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    
	}
	
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
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
    }
    stringBuffer.append(TEXT_82);
    return stringBuffer.toString();
  }
}
