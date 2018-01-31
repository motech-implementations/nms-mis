package org.talend.designer.codegen.translators.internet.kafka;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

public class TKafkaOutputBeginJava
{
  protected static String nl;
  public static synchronized TKafkaOutputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TKafkaOutputBeginJava result = new TKafkaOutputBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + NL + "// Producer configuration" + NL + "java.util.Properties ";
  protected final String TEXT_5 = "_kafkaProperties = new java.util.Properties();";
  protected final String TEXT_6 = NL + "\t\tif(true){" + NL + "\t\t\tthrow new Exception(\"A broker list must be provided.\");" + NL + "\t\t}";
  protected final String TEXT_7 = NL + "\t\t";
  protected final String TEXT_8 = "_kafkaProperties.setProperty(\"bootstrap.servers\", ";
  protected final String TEXT_9 = ");" + NL + "\t\t";
  protected final String TEXT_10 = "_kafkaProperties.setProperty(\"compression.type\", \"";
  protected final String TEXT_11 = "\");" + NL + "\t\t";
  protected final String TEXT_12 = "_kafkaProperties.setProperty(\"security.protocol\", \"";
  protected final String TEXT_13 = "\");";
  protected final String TEXT_14 = NL + "\t\t\t";
  protected final String TEXT_15 = "_kafkaProperties.setProperty(";
  protected final String TEXT_16 = ", ";
  protected final String TEXT_17 = ");";
  protected final String TEXT_18 = NL + "\t\t\t";
  protected final String TEXT_19 = "_kafkaProperties.setProperty(\"ssl.truststore.type\", ";
  protected final String TEXT_20 = ");" + NL + "\t\t\t";
  protected final String TEXT_21 = "_kafkaProperties.setProperty(\"ssl.truststore.location\", ";
  protected final String TEXT_22 = ");" + NL + "\t\t\t";
  protected final String TEXT_23 = "_kafkaProperties.setProperty(\"ssl.truststore.password\", ";
  protected final String TEXT_24 = ");";
  protected final String TEXT_25 = NL + "\t\t\t \t";
  protected final String TEXT_26 = "_kafkaProperties.setProperty(\"ssl.keystore.type\", ";
  protected final String TEXT_27 = ");" + NL + "\t\t \t \t";
  protected final String TEXT_28 = "_kafkaProperties.setProperty(\"ssl.keystore.location\", ";
  protected final String TEXT_29 = ");" + NL + "\t\t \t \t";
  protected final String TEXT_30 = "_kafkaProperties.setProperty(\"ssl.keystore.password\", ";
  protected final String TEXT_31 = ");";
  protected final String TEXT_32 = NL + "\t\t\t// Make sure the new security information is picked up." + NL + "\t\t\tjavax.security.auth.login.Configuration.setConfiguration(null);" + NL + "\t\t\t";
  protected final String TEXT_33 = "_kafkaProperties.put(\"sasl.kerberos.service.name\", ";
  protected final String TEXT_34 = ");";
  protected final String TEXT_35 = NL + "\t\t\t\t";
  protected final String TEXT_36 = "_kafkaProperties.put(\"sasl.kerberos.kinit.cmd\", ";
  protected final String TEXT_37 = ");";
  protected final String TEXT_38 = NL + NL + "\t\t\tSystem.setProperty(\"java.security.auth.login.config\", ";
  protected final String TEXT_39 = ");";
  protected final String TEXT_40 = NL + "\t\t\t\tSystem.setProperty(\"java.security.krb5.conf\", ";
  protected final String TEXT_41 = ");";
  protected final String TEXT_42 = NL + NL + "org.apache.kafka.clients.producer.KafkaProducer<byte[], byte[]> ";
  protected final String TEXT_43 = "_kafkaProducer = (org.apache.kafka.clients.producer.KafkaProducer<byte[], byte[]>)globalMap.get(\"";
  protected final String TEXT_44 = "_kafkaProducer\");" + NL + "if(null == ";
  protected final String TEXT_45 = "_kafkaProducer) {";
  protected final String TEXT_46 = NL + "    ";
  protected final String TEXT_47 = "_kafkaProducer = new org.apache.kafka.clients.producer.KafkaProducer<byte[], byte[]>(";
  protected final String TEXT_48 = "_kafkaProperties, new org.apache.kafka.common.serialization.ByteArraySerializer(), new org.apache.kafka.common.serialization.ByteArraySerializer());" + NL + "    globalMap.put(\"";
  protected final String TEXT_49 = "_kafkaProducer\", ";
  protected final String TEXT_50 = "_kafkaProducer);" + NL + "}";
  protected final String TEXT_51 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
class TSetKeystoreUtil {

    private static final String USE_HTTPS = "__USE_HTTPS__"; 

    private static final String HTTPS_SETTING = "__HTTPS_SETTING__"; 

    private static final String TRUSTSTORE_TYPE = "__TRUSTSTORE_TYPE__"; 

    private static final String SSL_TRUSTSERVER_TRUSTSTORE = "__SSL_TRUSTSERVER_TRUSTSTORE__"; 

    private static final String VERIFY_NAME = "__VERIFY_NAME__"; 

    private static final String SSL_KEYSTORE_PASSWORD = "__SSL_KEYSTORE_PASSWORD__"; 

    private static final String SSL_KEYSTORE = "__SSL_KEYSTORE__"; 

    private static final String KEYSTORE_TYPE = "__KEYSTORE_TYPE__"; 

    private static final String NEED_CLIENT_AUTH = "__NEED_CLIENT_AUTH__"; 

    private static final String SSL_TRUSTSERVER_PASSWORD = "__SSL_TRUSTSERVER_PASSWORD__"; 

    private INode node;

    public TSetKeystoreUtil(INode node) {
        if (ElementParameterParser.getBooleanValue(node, USE_HTTPS)) {
            this.node = ElementParameterParser.getLinkedNodeValue(node, HTTPS_SETTING);
        }
    }

    public boolean useHTTPS() {
        return node != null;
    }

    public String getTrustStoreType() {
        return ElementParameterParser.getWrapDoubleQuoteValue(node, TRUSTSTORE_TYPE);
    }

    public String getTrustStorePath() {
        return ElementParameterParser.getValue(node, SSL_TRUSTSERVER_TRUSTSTORE);
    }

    public String getTrustStorePassword() {
        return ElementParameterParser.getPasswordValue(node, SSL_TRUSTSERVER_PASSWORD);
    }

    public boolean needClientAuth() {
        return ElementParameterParser.getBooleanValue(node, NEED_CLIENT_AUTH);
    }

    public String getKeyStoreType() {
        return ElementParameterParser.getWrapDoubleQuoteValue(node, KEYSTORE_TYPE);
    }

    public String getKeyStorePath() {
        return ElementParameterParser.getValue(node, SSL_KEYSTORE);
    }

    public String getKeyStorePassword() {
        return ElementParameterParser.getPasswordValue(node, SSL_KEYSTORE_PASSWORD);
    }

    public boolean needVerifyName() {
        return ElementParameterParser.getBooleanValue(node, VERIFY_NAME);
    }
}

    stringBuffer.append(TEXT_2);
    
class TKafkaOutputUtil {
	
	 private INode node;
	 
	 private INode connNode;
	 
	 private TSetKeystoreUtil tSetKeystoreUtil;

    private IConnection incomingConnection;

    private Map<String, String> kafkaProducerProperties;

    public TKafkaOutputUtil(INode node) {
        this.node = node;
        this.connNode = Boolean.valueOf(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"))
            ? ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__")
            : node;
		  this.tSetKeystoreUtil = new TSetKeystoreUtil(connNode);
        this.incomingConnection = findIncomingConnection();
        this.kafkaProducerProperties = findKafkaProducerProperties();
    }
    
    public String getInStructName() {
		return this.incomingConnection.getName() + "Struct";
	 }

    public String getEncoding() {
        return ElementParameterParser.getValue(node, "__ENCODING__");
    }

    public String getBrokerList() {
        return ElementParameterParser.getValue(connNode, "__BROKER_LIST__");
    }

    public String getCompression() {
        String compression = "none";
        if ("true".equals(ElementParameterParser.getValue(node, "__COMPRESS__"))) {
            compression = ElementParameterParser.getValue(node, "__COMPRESSION__").toLowerCase();
        }
        return compression;
    }

    public IConnection getIncomingConnection() {
        return this.incomingConnection;
    }

    public Map<String, String> getKafkaProducerProperties() {
        return this.kafkaProducerProperties;
    }

    public String getKafkaTopic() {
        return ElementParameterParser.getValue(node, "__KAFKA_TOPIC__");
    }

    public String getIncomingColumnName() {
        return this.incomingConnection.getMetadataTable().getListColumns().get(0).getLabel();
    }
    
    public TSetKeystoreUtil getTSetKeystoreUtil() {
        return tSetKeystoreUtil;
    }
    
    public String getSecurityProtocol() {
			if (tSetKeystoreUtil.useHTTPS()) {
				return useKrb() ? "SASL_SSL" : "SSL";
			} else {
				return useKrb() ? "SASL_PLAINTEXT" : "PLAINTEXT";
			}
    }
    
    public boolean useKrb() {
			return "true".equals(ElementParameterParser.getValue(connNode, "__USE_KRB__"));
    }
    
    public String getJAASConf() {
    		return ElementParameterParser.getValue(connNode, "__JAAS_CONF__");
    }
    
    public String getKrbServiceName() {
    		return ElementParameterParser.getValue(connNode, "__KRB_SERVICE_NAME__");
    }
    
    public boolean isSetKinitPath() {
			return "true".equals(ElementParameterParser.getValue(connNode, "__SET_KINIT_PATH__"));
    }
    
    public String getKinitPath() {
    		return ElementParameterParser.getValue(connNode, "__KINIT_PATH__");
    }
    
    public boolean isSetKrb5Conf() {
			return "true".equals(ElementParameterParser.getValue(connNode, "__SET_KRB5_CONF__"));
    }
    
    public String getKrb5Conf() {
    		return ElementParameterParser.getValue(connNode, "__KRB5_CONF__");
    }
    
    private IConnection findIncomingConnection() {
        IConnection result = null;
        if (node.getIncomingConnections() != null) {
            for (IConnection in : node.getIncomingConnections()) {
                if (in.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
                    result = in;
                    break;
                }
            }
        }
        return result;
    }

    private Map<String, String> findKafkaProducerProperties() {
        Map<String, String> result = new HashMap<String, String>();
        List<Map<String, String>> producerPropertiesFromStudio = (List<Map<String, String>>) ElementParameterParser
                .getObjectValue(node, "__KAFKA_PRODUCER_PROPERTIES__");
        if(producerPropertiesFromStudio != null) {
	        for (Map<String, String> producerPropertyFromStudio : producerPropertiesFromStudio) {
	            result.put(producerPropertyFromStudio.get("PROPERTY"), producerPropertyFromStudio.get("VALUE"));
	        }
        }
        return result;
    }
}

    stringBuffer.append(TEXT_3);
    
// Parse the inputs to this javajet generator.
final CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
final INode node = (INode)codeGenArgument.getArgument();
final String cid = node.getUniqueName();

TKafkaOutputUtil tKafkaOutputUtil = new TKafkaOutputUtil(node);
TSetKeystoreUtil tSetKeystoreUtil = tKafkaOutputUtil.getTSetKeystoreUtil();

    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
	if(tKafkaOutputUtil.getBrokerList() == null || "".equals(tKafkaOutputUtil.getBrokerList())){

    stringBuffer.append(TEXT_6);
    
	} else {
		// Basic configuration

    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(tKafkaOutputUtil.getBrokerList());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(tKafkaOutputUtil.getCompression());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(tKafkaOutputUtil.getSecurityProtocol());
    stringBuffer.append(TEXT_13);
    
		// Advanced configuration
		for(Entry<String, String> kafkaProperty : tKafkaOutputUtil.getKafkaProducerProperties().entrySet()) {

    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(kafkaProperty.getKey());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(kafkaProperty.getValue());
    stringBuffer.append(TEXT_17);
    
		} // end for

		// SSL configuration
		if (tSetKeystoreUtil.useHTTPS()) {

    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(tSetKeystoreUtil.getTrustStoreType());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(tSetKeystoreUtil.getTrustStorePath());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(tSetKeystoreUtil.getTrustStorePassword());
    stringBuffer.append(TEXT_24);
    
			if (tSetKeystoreUtil.needClientAuth()) {

    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(tSetKeystoreUtil.getKeyStoreType());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(tSetKeystoreUtil.getKeyStorePath());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(tSetKeystoreUtil.getKeyStorePassword());
    stringBuffer.append(TEXT_31);
    
			}
		} // end SSL configuration

		// Kerberos configuration
		if (tKafkaOutputUtil.useKrb()) {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(tKafkaOutputUtil.getKrbServiceName());
    stringBuffer.append(TEXT_34);
    
			if (tKafkaOutputUtil.isSetKinitPath()) {

    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(tKafkaOutputUtil.getKinitPath());
    stringBuffer.append(TEXT_37);
    
			}

    stringBuffer.append(TEXT_38);
    stringBuffer.append(tKafkaOutputUtil.getJAASConf());
    stringBuffer.append(TEXT_39);
    
			if (tKafkaOutputUtil.isSetKrb5Conf()) {

    stringBuffer.append(TEXT_40);
    stringBuffer.append(tKafkaOutputUtil.getKrb5Conf());
    stringBuffer.append(TEXT_41);
    
			}
		}	// end Kerberos configuration
	} // end else -> (brokerList is provided)

    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(TEXT_51);
    return stringBuffer.toString();
  }
}
