package org.talend.designer.codegen.translators.internet.maprstreams;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TMapRStreamsConnectionBeginJava
{
  protected static String nl;
  public static synchronized TMapRStreamsConnectionBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMapRStreamsConnectionBeginJava result = new TMapRStreamsConnectionBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL;
  protected final String TEXT_3 = NL + "\torg.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_4 = " = new org.apache.hadoop.conf.Configuration();" + NL + "\tconf_";
  protected final String TEXT_5 = ".set(\"fs.default.name\", ";
  protected final String TEXT_6 = ");";
  protected final String TEXT_7 = NL + "\t\tconf_";
  protected final String TEXT_8 = ".set(\"dfs.client.use.datanode.hostname\", \"true\");";
  protected final String TEXT_9 = NL + "\t\tconf_";
  protected final String TEXT_10 = ".set(";
  protected final String TEXT_11 = " ,";
  protected final String TEXT_12 = ");";
  protected final String TEXT_13 = NL + "\tSystem.setProperty(\"pname\", \"MapRLogin\");" + NL + "\tSystem.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "\tSystem.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_14 = ");" + NL + "\t" + NL + "\tcom.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_15 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL;
  protected final String TEXT_16 = NL + "\t\tSystem.setProperty(\"hadoop.login\", ";
  protected final String TEXT_17 = ");" + NL + "\t\tconf_";
  protected final String TEXT_18 = ".set(\"dfs.namenode.kerberos.principal\", ";
  protected final String TEXT_19 = ");" + NL + "\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_20 = ", ";
  protected final String TEXT_21 = ");" + NL + "\t\tmaprLogin_";
  protected final String TEXT_22 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_23 = ", ";
  protected final String TEXT_24 = ");";
  protected final String TEXT_25 = NL + "\t\tString decryptedPassword_";
  protected final String TEXT_26 = " = ";
  protected final String TEXT_27 = ";";
  protected final String TEXT_28 = NL + "\t\t\t\tmaprLogin_";
  protected final String TEXT_29 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_30 = ", ";
  protected final String TEXT_31 = ", decryptedPassword_";
  protected final String TEXT_32 = ", ";
  protected final String TEXT_33 = ", \"\");";
  protected final String TEXT_34 = NL + "\t\t\t\tmaprLogin_";
  protected final String TEXT_35 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_36 = ", ";
  protected final String TEXT_37 = ", decryptedPassword_";
  protected final String TEXT_38 = ", ";
  protected final String TEXT_39 = ");";
  protected final String TEXT_40 = NL + NL + "globalMap.put(\"conf_";
  protected final String TEXT_41 = "\", conf_";
  protected final String TEXT_42 = ");";
  protected final String TEXT_43 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    

class TMapRStreamsConnectionUtil {

    private INode node;

    public TMapRStreamsConnectionUtil(INode node) {
        this.node = node;
    }

    public boolean isUsingMapRTicket() {
        return "true".equals(ElementParameterParser.getValue(node, "__USE_MAPRTICKET__"));
    }
    
    public boolean isUsingKerberos() {
        return "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
    }

    public String getPrincipal() {
        return ElementParameterParser.getValue(node, "__PRINCIPAL__");
    }

    public String getKeytabPath() {
        return ElementParameterParser.getValue(node, "__KEYTAB_PATH__");
    }
    
    public String getUsername() {
        return ElementParameterParser.getValue(node, "__MAPRTICKET_USERNAME__");
    }
    
    public String getPassword() {
        return ElementParameterParser.getPasswordValue(node, "__MAPRTICKET_PASSWORD__");
    }
    
    public String getCluster() {
        return ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
    }
    
    public String getMapRTicketDuration() {
        return ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");
    }

    public boolean isSetMapRHomeDir() {
        return "true".equals(ElementParameterParser.getValue(node, "__SET_MAPR_HOME_DIR__"));
    }

    public String getMapRHomeDir() {
        return ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");
	 }
	 
	 public boolean isSetMapRHadoopLogin() {
        return "true".equals(ElementParameterParser.getValue(node, "__SET_HADOOP_LOGIN__"));
    }

    public String getMapRHadoopLogin() {
        return ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");
	 }
	 
	 public String getFsDefaultName() {
        return ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");
	 }
	 
 	 public String getNamenodePrincipal() {
        return ElementParameterParser.getValue(node, "__NAMENODE_PRINCIPAL__");
	 }
	 
 	 public boolean useDatanodeHostname() {
        return "true".equals(ElementParameterParser.getValue(node, "__USE_DATANODE_HOSTNAME__"));
    }
    
    public List<Map<String,String>> getHadoopProperties() {
		  return (List<Map<String,String>>) ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");
    }
}

    stringBuffer.append(TEXT_2);
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();

TMapRStreamsConnectionUtil tMapRStreamsConnectionUtil = new TMapRStreamsConnectionUtil(node);

// We need to know what the current distribution is since MapR Login API is subject to change between versions.
String distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
String hadoopVersion = ElementParameterParser.getValue(node, "__MAPRSTREAMS_VERSION__");
org.talend.hadoop.distribution.component.HDFSComponent hdfsDistrib = null;
try {
	hdfsDistrib = (org.talend.hadoop.distribution.component.HDFSComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hadoopVersion);
} catch (java.lang.Exception e) {
	e.printStackTrace();
	return "";
}

    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(tMapRStreamsConnectionUtil.getFsDefaultName());
    stringBuffer.append(TEXT_6);
    
if (tMapRStreamsConnectionUtil.useDatanodeHostname()) {

    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    
}

List<Map<String,String>> hadoopProperties = tMapRStreamsConnectionUtil.getHadoopProperties();
for(Map<String, String> item : hadoopProperties) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(item.get("PROPERTY"));
    stringBuffer.append(TEXT_11);
    stringBuffer.append(item.get("VALUE"));
    stringBuffer.append(TEXT_12);
     
}

if(tMapRStreamsConnectionUtil.isUsingMapRTicket()) {

    stringBuffer.append(TEXT_13);
    stringBuffer.append(tMapRStreamsConnectionUtil.isSetMapRHomeDir() ? tMapRStreamsConnectionUtil.getMapRHomeDir() : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    
	if(tMapRStreamsConnectionUtil.isUsingKerberos()) {

    stringBuffer.append(TEXT_16);
    stringBuffer.append(tMapRStreamsConnectionUtil.isSetMapRHadoopLogin() ? tMapRStreamsConnectionUtil.getMapRHadoopLogin() : "\"kerberos\"");
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(tMapRStreamsConnectionUtil.getNamenodePrincipal());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(tMapRStreamsConnectionUtil.getPrincipal());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(tMapRStreamsConnectionUtil.getKeytabPath());
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(tMapRStreamsConnectionUtil.getCluster());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(tMapRStreamsConnectionUtil.getMapRTicketDuration());
    stringBuffer.append(TEXT_24);
    
	} else {

    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(tMapRStreamsConnectionUtil.getPassword());
    stringBuffer.append(TEXT_27);
    
			if(hdfsDistrib.doSupportMaprTicketV52API()){

    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(tMapRStreamsConnectionUtil.getCluster());
    stringBuffer.append(TEXT_30);
    stringBuffer.append(tMapRStreamsConnectionUtil.getUsername());
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(tMapRStreamsConnectionUtil.getMapRTicketDuration());
    stringBuffer.append(TEXT_33);
    
			} else {

    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(tMapRStreamsConnectionUtil.getCluster());
    stringBuffer.append(TEXT_36);
    stringBuffer.append(tMapRStreamsConnectionUtil.getUsername());
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(tMapRStreamsConnectionUtil.getMapRTicketDuration());
    stringBuffer.append(TEXT_39);
    
			}
	}
} // end if(tMapRStreamsConnectionUtil.isUsingMapRTicket())

    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(TEXT_43);
    return stringBuffer.toString();
  }
}
