package org.talend.designer.codegen.translators.internet.kafka;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class TKafkaCreateTopicBeginJava
{
  protected static String nl;
  public static synchronized TKafkaCreateTopicBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TKafkaCreateTopicBeginJava result = new TKafkaCreateTopicBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + "\t";
  protected final String TEXT_5 = NL + NL + "// Topic configuration" + NL + "java.util.Properties ";
  protected final String TEXT_6 = "_topicProperties = new java.util.Properties();";
  protected final String TEXT_7 = NL + "\t\t";
  protected final String TEXT_8 = "_topicProperties.put(\"retention.ms\", \"";
  protected final String TEXT_9 = "\");";
  protected final String TEXT_10 = NL + "\t\t";
  protected final String TEXT_11 = "_topicProperties.put(";
  protected final String TEXT_12 = ", ";
  protected final String TEXT_13 = ");";
  protected final String TEXT_14 = NL + NL + "org.I0Itec.zkclient.ZkClient ";
  protected final String TEXT_15 = "_zkClient = new org.I0Itec.zkclient.ZkClient(";
  protected final String TEXT_16 = ", 10000, 10000, kafka.utils.ZKStringSerializer$.MODULE$);" + NL;
  protected final String TEXT_17 = NL + "\t\ttry{" + NL + "\t\t\tkafka.admin.AdminUtils.createTopic(";
  protected final String TEXT_18 = "_zkClient, ";
  protected final String TEXT_19 = ", Integer.valueOf(";
  protected final String TEXT_20 = "), Integer.valueOf(";
  protected final String TEXT_21 = "), ";
  protected final String TEXT_22 = "_topicProperties);" + NL + "\t\t} catch (kafka.common.TopicExistsException e) {";
  protected final String TEXT_23 = NL + "\t\t\t// nothing to do even through the topic already exists, continue the job";
  protected final String TEXT_24 = NL + "\t\t\t// topic already exists, stop the job" + NL + "\t\t\tthrow e;";
  protected final String TEXT_25 = NL + "\t\t}";
  protected final String TEXT_26 = NL + "\t\t// Existing topic configuration" + NL + "\t\tjava.util.Properties ";
  protected final String TEXT_27 = "_initialTopicProperties = kafka.admin.AdminUtils.fetchTopicConfig(";
  protected final String TEXT_28 = "_zkClient, ";
  protected final String TEXT_29 = ");" + NL + "\t\t// Merge existing configuration with the new one" + NL + "\t\tjava.util.Properties ";
  protected final String TEXT_30 = "_newTopicProperties = new java.util.Properties();" + NL + "\t\t";
  protected final String TEXT_31 = "_newTopicProperties.putAll(";
  protected final String TEXT_32 = "_initialTopicProperties);" + NL + "\t\t";
  protected final String TEXT_33 = "_newTopicProperties.putAll(";
  protected final String TEXT_34 = "_topicProperties);" + NL + "\t\t// Push the configuration changes into ZK" + NL + "\t\tkafka.admin.AdminUtils.changeTopicConfig(";
  protected final String TEXT_35 = "_zkClient, ";
  protected final String TEXT_36 = ", ";
  protected final String TEXT_37 = "_newTopicProperties);";
  protected final String TEXT_38 = NL;
  protected final String TEXT_39 = NL + "\t";
  protected final String TEXT_40 = NL + NL + "// Topic configuration" + NL + "java.util.Properties ";
  protected final String TEXT_41 = "_topicProperties = new java.util.Properties();";
  protected final String TEXT_42 = NL + "\t\t";
  protected final String TEXT_43 = "_topicProperties.put(\"retention.ms\", \"";
  protected final String TEXT_44 = "\");";
  protected final String TEXT_45 = NL + "\t\t";
  protected final String TEXT_46 = "_topicProperties.put(";
  protected final String TEXT_47 = ", ";
  protected final String TEXT_48 = ");";
  protected final String TEXT_49 = NL + "boolean isZkSecurityEnabled = ";
  protected final String TEXT_50 = "true";
  protected final String TEXT_51 = "false";
  protected final String TEXT_52 = ";" + NL + "kafka.utils.ZkUtils ";
  protected final String TEXT_53 = "_zkUtils =  kafka.utils.ZkUtils.apply(";
  protected final String TEXT_54 = ", 10000, 10000, isZkSecurityEnabled);";
  protected final String TEXT_55 = NL + "\t\ttry{" + NL + "\t\t\tkafka.admin.AdminUtils.createTopic(";
  protected final String TEXT_56 = "_zkUtils, ";
  protected final String TEXT_57 = ", Integer.valueOf(";
  protected final String TEXT_58 = "), Integer.valueOf(";
  protected final String TEXT_59 = "), ";
  protected final String TEXT_60 = "_topicProperties);" + NL + "\t\t} catch (kafka.common.TopicExistsException e) {";
  protected final String TEXT_61 = NL + "\t\t\t// nothing to do even through the topic already exists, continue the job";
  protected final String TEXT_62 = NL + "\t\t\t// topic already exists, stop the job" + NL + "\t\t\tthrow e;";
  protected final String TEXT_63 = NL + "\t\t}";
  protected final String TEXT_64 = NL + "\t\t// Existing topic configuration" + NL + "\t\tjava.util.Properties ";
  protected final String TEXT_65 = "_initialTopicProperties = kafka.admin.AdminUtils.fetchEntityConfig(";
  protected final String TEXT_66 = "_zkUtils, kafka.server.ConfigType.Topic(), ";
  protected final String TEXT_67 = ");" + NL + "" + NL + "\t\t// Merge existing configuration with the new one" + NL + "\t\tjava.util.Properties ";
  protected final String TEXT_68 = "_newTopicProperties = new java.util.Properties();" + NL + "\t\t";
  protected final String TEXT_69 = "_newTopicProperties.putAll(";
  protected final String TEXT_70 = "_initialTopicProperties);" + NL + "\t\t";
  protected final String TEXT_71 = "_newTopicProperties.putAll(";
  protected final String TEXT_72 = "_topicProperties);" + NL + "\t\t// Push the configuration changes into ZK" + NL + "\t\tkafka.admin.AdminUtils.changeTopicConfig(";
  protected final String TEXT_73 = "_zkUtils, ";
  protected final String TEXT_74 = ", ";
  protected final String TEXT_75 = "_newTopicProperties);";
  protected final String TEXT_76 = NL + NL;
  protected final String TEXT_77 = NL + "\t";
  protected final String TEXT_78 = NL + NL + "// Topic configuration" + NL + "java.util.Properties ";
  protected final String TEXT_79 = "_topicProperties = new java.util.Properties();";
  protected final String TEXT_80 = NL + "\t\t";
  protected final String TEXT_81 = "_topicProperties.put(\"retention.ms\", \"";
  protected final String TEXT_82 = "\");";
  protected final String TEXT_83 = NL + "\t\t";
  protected final String TEXT_84 = "_topicProperties.put(";
  protected final String TEXT_85 = ", ";
  protected final String TEXT_86 = ");";
  protected final String TEXT_87 = NL + "boolean isZkSecurityEnabled = ";
  protected final String TEXT_88 = "true";
  protected final String TEXT_89 = "false";
  protected final String TEXT_90 = ";" + NL + "kafka.utils.ZkUtils ";
  protected final String TEXT_91 = "_zkUtils =  kafka.utils.ZkUtils.apply(";
  protected final String TEXT_92 = ", 10000, 10000, isZkSecurityEnabled);";
  protected final String TEXT_93 = NL + "\t\ttry{" + NL + "\t\t\tkafka.admin.AdminUtils.createTopic(";
  protected final String TEXT_94 = "_zkUtils, ";
  protected final String TEXT_95 = ", Integer.valueOf(";
  protected final String TEXT_96 = "), Integer.valueOf(";
  protected final String TEXT_97 = "), ";
  protected final String TEXT_98 = "_topicProperties, kafka.admin.RackAwareMode.Safe$.MODULE$);" + NL + "\t\t} catch (kafka.common.TopicExistsException e) {";
  protected final String TEXT_99 = NL + "\t\t\t// nothing to do even through the topic already exists, continue the job";
  protected final String TEXT_100 = NL + "\t\t\t// topic already exists, stop the job" + NL + "\t\t\tthrow e;";
  protected final String TEXT_101 = NL + "\t\t}";
  protected final String TEXT_102 = NL + "\t\t// Existing topic configuration" + NL + "\t\tjava.util.Properties ";
  protected final String TEXT_103 = "_initialTopicProperties = kafka.admin.AdminUtils.fetchEntityConfig(";
  protected final String TEXT_104 = "_zkUtils, kafka.server.ConfigType.Topic(), ";
  protected final String TEXT_105 = ");" + NL + "" + NL + "\t\t// Merge existing configuration with the new one" + NL + "\t\tjava.util.Properties ";
  protected final String TEXT_106 = "_newTopicProperties = new java.util.Properties();" + NL + "\t\t";
  protected final String TEXT_107 = "_newTopicProperties.putAll(";
  protected final String TEXT_108 = "_initialTopicProperties);" + NL + "\t\t";
  protected final String TEXT_109 = "_newTopicProperties.putAll(";
  protected final String TEXT_110 = "_topicProperties);" + NL + "\t\t// Push the configuration changes into ZK" + NL + "\t\tkafka.admin.AdminUtils.changeTopicConfig(";
  protected final String TEXT_111 = "_zkUtils, ";
  protected final String TEXT_112 = ", ";
  protected final String TEXT_113 = "_newTopicProperties);";
  protected final String TEXT_114 = NL + NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    
class TKafkaCreateTopicUtil {

	private INode node;

	private INode connNode;

	private Map<String, String> kafkaTopicProperties;

	public TKafkaCreateTopicUtil(INode node) {
		this.node = node;
		this.kafkaTopicProperties = findKafkaTopicProperties();
		this.connNode = Boolean.valueOf(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"))
			? ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__")
			: node;
	}

	public boolean isCreate() {
		return "CREATE_TOPIC".equals(ElementParameterParser.getValue(node, "__TOPICACTION__")) || isCreateIfNotExists();
	}

	public boolean isCreateIfNotExists() {
		return "CREATE_IF_NOT_EXISTS".equals(ElementParameterParser.getValue(node, "__TOPICACTION__"));
	}

	public boolean isAlter() {
		return "ALTER_TOPIC".equals(ElementParameterParser.getValue(node, "__TOPICACTION__"));
	}

	public String getZookeeperConnect() {
		return ElementParameterParser.getValue(connNode, "__ZOOKEEPER_CONNECT__");
	}

	public boolean isRetentionSet() {
		return "true".equals(ElementParameterParser.getValue(node, "__KAFKA_RETENTION_CHECK__"));
	}
	
	public boolean isZookeeperSecurityEnabled() {
		return "true".equals(ElementParameterParser.getValue(node, "__ZOOKEEPER_SECURITY_IS_ENABLED__"));
	}

	public String getRetention() {
		return ElementParameterParser.getValue(node, "__KAFKA_RETENTION__");
	}

	public String getTopic() {
		return ElementParameterParser.getValue(node, "__KAFKA_TOPIC__");
	}

	public String getReplicationFactor() {
		return ElementParameterParser.getValue(node, "__KAFKA_REPLICATION__");
	}

	public String getNbPartitions() {
		return ElementParameterParser.getValue(node, "__KAFKA_PARTITION__");
	}

	public Map<String, String> getKafkaTopicProperties(){
		return this.kafkaTopicProperties;
	}
	
	public boolean isKafkaVersion(String version) {
		return (version != null) ? version.equals(ElementParameterParser.getValue(connNode, "__KAFKA_VERSION__")) : false;
	}

	private Map<String, String> findKafkaTopicProperties() {
		Map<String, String> result = new HashMap<String, String>();
		List<Map<String, String>> topicPropertiesFromStudio = (List<Map<String, String>>) ElementParameterParser
		        .getObjectValue(node, "__KAFKA_TOPIC_PROPERTIES__");
		for (Map<String, String> topicPropertyFromStudio : topicPropertiesFromStudio) {
		    result.put(topicPropertyFromStudio.get("PROPERTY"), topicPropertyFromStudio.get("VALUE"));
		}
		return result;
   }
}

    stringBuffer.append(TEXT_3);
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();

TKafkaCreateTopicUtil tKafkaCreateTopicUtil = new TKafkaCreateTopicUtil(node);

if(tKafkaCreateTopicUtil.isKafkaVersion("KAFKA_0_8_2_0")) {

    stringBuffer.append(TEXT_4);
    
	// This is the tKafkaCreateTopic_begin javajet part for Kafka 0.8.2.x
	
	// Since kafka.admin.AdminUtils methods have been changed in Kafka 0.9.0.x, we must split the javajets
	// in order to generate the relevant code depending of the current Kafka version.

    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    
	if(tKafkaCreateTopicUtil.isRetentionSet()) {

    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(tKafkaCreateTopicUtil.getRetention());
    stringBuffer.append(TEXT_9);
    
	}

    
	for(Entry<String, String> topicProperty : tKafkaCreateTopicUtil.getKafkaTopicProperties().entrySet()) {

    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(topicProperty.getKey());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(topicProperty.getValue());
    stringBuffer.append(TEXT_13);
    
	}

    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(tKafkaCreateTopicUtil.getZookeeperConnect());
    stringBuffer.append(TEXT_16);
    
	if(tKafkaCreateTopicUtil.isCreate()) {

    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(tKafkaCreateTopicUtil.getNbPartitions());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(tKafkaCreateTopicUtil.getReplicationFactor());
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    
		if (tKafkaCreateTopicUtil.isCreateIfNotExists()) {

    stringBuffer.append(TEXT_23);
    
		} else {

    stringBuffer.append(TEXT_24);
    
		}

    stringBuffer.append(TEXT_25);
    
	} else if(tKafkaCreateTopicUtil.isAlter()) {

    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
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
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    
	}

    stringBuffer.append(TEXT_38);
    
} else if(tKafkaCreateTopicUtil.isKafkaVersion("KAFKA_0_9_0_1")){

    stringBuffer.append(TEXT_39);
    
	// This is the tKafkaCreateTopic_begin javajet part for Kafka 0.9.0.x
	
	// Since kafka.admin.AdminUtils methods have been changed in Kafka 0.9.0.x, we must split the javajets
	// in order to generate the relevant code depending of the current Kafka version.

    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    
	if(tKafkaCreateTopicUtil.isRetentionSet()) {

    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(tKafkaCreateTopicUtil.getRetention());
    stringBuffer.append(TEXT_44);
    
	}

    
	for(Entry<String, String> topicProperty : tKafkaCreateTopicUtil.getKafkaTopicProperties().entrySet()) {

    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(topicProperty.getKey());
    stringBuffer.append(TEXT_47);
    stringBuffer.append(topicProperty.getValue());
    stringBuffer.append(TEXT_48);
    
	}

    stringBuffer.append(TEXT_49);
    if(tKafkaCreateTopicUtil.isZookeeperSecurityEnabled()) {
    stringBuffer.append(TEXT_50);
    } else {
    stringBuffer.append(TEXT_51);
    }
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(tKafkaCreateTopicUtil.getZookeeperConnect());
    stringBuffer.append(TEXT_54);
    
	if(tKafkaCreateTopicUtil.isCreate()) {

    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_57);
    stringBuffer.append(tKafkaCreateTopicUtil.getNbPartitions());
    stringBuffer.append(TEXT_58);
    stringBuffer.append(tKafkaCreateTopicUtil.getReplicationFactor());
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    
		if (tKafkaCreateTopicUtil.isCreateIfNotExists()) {

    stringBuffer.append(TEXT_61);
    
		} else {

    stringBuffer.append(TEXT_62);
    
		}

    stringBuffer.append(TEXT_63);
    
	} else if(tKafkaCreateTopicUtil.isAlter()) {

    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    
	}

    stringBuffer.append(TEXT_76);
    
} else if(tKafkaCreateTopicUtil.isKafkaVersion("KAFKA_0_10_0_1")){

    stringBuffer.append(TEXT_77);
    
	// This is the tKafkaCreateTopic_begin javajet part for Kafka 0.10.0.x
	
	// Since kafka.admin.AdminUtils methods have been changed in Kafka 0.10.0.x, we must split the javajets
	// in order to generate the relevant code depending of the current Kafka version.

    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    
	if(tKafkaCreateTopicUtil.isRetentionSet()) {

    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(tKafkaCreateTopicUtil.getRetention());
    stringBuffer.append(TEXT_82);
    
	}

    
	for(Entry<String, String> topicProperty : tKafkaCreateTopicUtil.getKafkaTopicProperties().entrySet()) {

    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(topicProperty.getKey());
    stringBuffer.append(TEXT_85);
    stringBuffer.append(topicProperty.getValue());
    stringBuffer.append(TEXT_86);
    
	}

    stringBuffer.append(TEXT_87);
    if(tKafkaCreateTopicUtil.isZookeeperSecurityEnabled()) {
    stringBuffer.append(TEXT_88);
    } else {
    stringBuffer.append(TEXT_89);
    }
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(tKafkaCreateTopicUtil.getZookeeperConnect());
    stringBuffer.append(TEXT_92);
    
	if(tKafkaCreateTopicUtil.isCreate()) {

    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_95);
    stringBuffer.append(tKafkaCreateTopicUtil.getNbPartitions());
    stringBuffer.append(TEXT_96);
    stringBuffer.append(tKafkaCreateTopicUtil.getReplicationFactor());
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    
		if (tKafkaCreateTopicUtil.isCreateIfNotExists()) {

    stringBuffer.append(TEXT_99);
    
		} else {

    stringBuffer.append(TEXT_100);
    
		}

    stringBuffer.append(TEXT_101);
    
	} else if(tKafkaCreateTopicUtil.isAlter()) {

    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(tKafkaCreateTopicUtil.getTopic());
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_113);
    
	}

    stringBuffer.append(TEXT_114);
    
}

    return stringBuffer.toString();
  }
}
