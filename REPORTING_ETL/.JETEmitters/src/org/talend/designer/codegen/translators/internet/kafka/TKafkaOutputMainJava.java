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

public class TKafkaOutputMainJava
{
  protected static String nl;
  public static synchronized TKafkaOutputMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TKafkaOutputMainJava result = new TKafkaOutputMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "_kafkaProducer.send(new org.apache.kafka.clients.producer.ProducerRecord<byte[], byte[]>(";
  protected final String TEXT_7 = ", ";
  protected final String TEXT_8 = ".";
  protected final String TEXT_9 = "));" + NL + NL;
  protected final String TEXT_10 = NL;

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
final TKafkaOutputUtil tKafkaOutputUtil = new TKafkaOutputUtil(node);

final String cid = node.getUniqueName();

    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(tKafkaOutputUtil.getKafkaTopic());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(tKafkaOutputUtil.getIncomingConnection().getName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(tKafkaOutputUtil.getIncomingColumnName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}
