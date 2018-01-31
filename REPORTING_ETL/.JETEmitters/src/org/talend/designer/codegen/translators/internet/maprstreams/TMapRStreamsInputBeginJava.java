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

public class TMapRStreamsInputBeginJava
{
  protected static String nl;
  public static synchronized TMapRStreamsInputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMapRStreamsInputBeginJava result = new TMapRStreamsInputBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + NL + "class ";
  protected final String TEXT_6 = "_ValueDeserializer implements org.apache.kafka.common.serialization.Deserializer<";
  protected final String TEXT_7 = "> {" + NL + "" + NL + "\tprivate org.apache.kafka.common.serialization.StringDeserializer stringDeserializer;" + NL + "" + NL + "\tpublic void configure(java.util.Map<java.lang.String,?> configs, boolean isKey) {" + NL + "\t\tstringDeserializer = new org.apache.kafka.common.serialization.StringDeserializer();" + NL + "\t\tstringDeserializer.configure(configs, isKey);" + NL + "\t}" + NL + "" + NL + "\tpublic ";
  protected final String TEXT_8 = " deserialize(String topic, byte[] data) {" + NL + "\t\t";
  protected final String TEXT_9 = " result = new ";
  protected final String TEXT_10 = "();";
  protected final String TEXT_11 = NL + "\t\t    String line = stringDeserializer.deserialize(topic, data);" + NL + "\t\t    result.";
  protected final String TEXT_12 = " = line;";
  protected final String TEXT_13 = NL + "\t\t    result.";
  protected final String TEXT_14 = " = data;";
  protected final String TEXT_15 = NL + "\t\treturn result;" + NL + "    }" + NL + "" + NL + "\tpublic void close() {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "}" + NL + "" + NL + "class ";
  protected final String TEXT_16 = "_KeyDeserializer implements org.apache.kafka.common.serialization.Deserializer<byte[]> {" + NL + "" + NL + "\tpublic void configure(java.util.Map<java.lang.String,?> configs, boolean isKey) {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "" + NL + "\tpublic byte[] deserialize(String topic, byte[] data) {" + NL + "\t    return data;" + NL + "\t}" + NL + "" + NL + "\tpublic void close() {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "}" + NL + "" + NL + "// Consumer configuration" + NL + "java.util.Properties ";
  protected final String TEXT_17 = "_kafkaProperties = new java.util.Properties();";
  protected final String TEXT_18 = NL + "    ";
  protected final String TEXT_19 = "_kafkaProperties.put(";
  protected final String TEXT_20 = ", ";
  protected final String TEXT_21 = ");";
  protected final String TEXT_22 = NL + NL + "// Value deserializer configuration";
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = "_ValueDeserializer instance_";
  protected final String TEXT_25 = "_ValueDeserializer = new ";
  protected final String TEXT_26 = "_ValueDeserializer();" + NL + "java.util.Map<String, String> instance_";
  protected final String TEXT_27 = "_ValueDeserializer_configs = new java.util.HashMap<String, String>();" + NL + "instance_";
  protected final String TEXT_28 = "_ValueDeserializer_configs.put(\"serializer.encoding\", ";
  protected final String TEXT_29 = ");" + NL + "instance_";
  protected final String TEXT_30 = "_ValueDeserializer.configure(instance_";
  protected final String TEXT_31 = "_ValueDeserializer_configs, false);" + NL + "" + NL + "// Single-threaded consumer. Make sure the new security information is picked up." + NL + "javax.security.auth.login.Configuration.setConfiguration(null);" + NL + "org.apache.kafka.clients.consumer.KafkaConsumer<byte[], ";
  protected final String TEXT_32 = "> ";
  protected final String TEXT_33 = "_kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<byte[], ";
  protected final String TEXT_34 = ">(";
  protected final String TEXT_35 = "_kafkaProperties, new ";
  protected final String TEXT_36 = "_KeyDeserializer(), instance_";
  protected final String TEXT_37 = "_ValueDeserializer);";
  protected final String TEXT_38 = NL;
  protected final String TEXT_39 = "_kafkaConsumer.subscribe(java.util.Arrays.asList(";
  protected final String TEXT_40 = "));" + NL + "" + NL + "globalMap.put(\"";
  protected final String TEXT_41 = "_consumer\", ";
  protected final String TEXT_42 = "_kafkaConsumer);" + NL;
  protected final String TEXT_43 = NL + "\t// We first have to do a quick poll() in order to know which partitions are assigned to the consumer before" + NL + "\t// \"resetting\" offsets by seeking to the beginning/end of the current partitions." + NL + "\t// Those polled records are not processed since we wanted to reset the offsets first." + NL + "\t";
  protected final String TEXT_44 = "_kafkaConsumer.poll(0);" + NL + "\tjava.util.Set<org.apache.kafka.common.TopicPartition> ";
  protected final String TEXT_45 = "_assignedtopicPartitions = ";
  protected final String TEXT_46 = "_kafkaConsumer.assignment();";
  protected final String TEXT_47 = NL + "\t";
  protected final String TEXT_48 = "_kafkaConsumer.seekToBeginning(";
  protected final String TEXT_49 = "_assignedtopicPartitions.toArray(new org.apache.kafka.common.TopicPartition[";
  protected final String TEXT_50 = "_assignedtopicPartitions.size()]));";
  protected final String TEXT_51 = NL + "\t";
  protected final String TEXT_52 = "_kafkaConsumer.seekToEnd(";
  protected final String TEXT_53 = "_assignedtopicPartitions.toArray(new org.apache.kafka.common.TopicPartition[";
  protected final String TEXT_54 = "_assignedtopicPartitions.size()]));";
  protected final String TEXT_55 = NL + "\t\t// Commit the offsets to ensure that the next job(s) use the reset offset." + NL + "\t\t{" + NL + "\t\tjava.util.HashMap<org.apache.kafka.common.TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata> ";
  protected final String TEXT_56 = "_resetOffsets = new java.util.HashMap<>();" + NL + "\t\tfor (org.apache.kafka.common.TopicPartition topic : ";
  protected final String TEXT_57 = "_assignedtopicPartitions) {" + NL + "\t\t\t";
  protected final String TEXT_58 = "_resetOffsets.put(topic, new org.apache.kafka.clients.consumer.OffsetAndMetadata(" + NL + "\t\t\t\t";
  protected final String TEXT_59 = "_kafkaConsumer.position(topic)));" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_60 = "_kafkaConsumer.commitSync(";
  protected final String TEXT_61 = "_resetOffsets);" + NL + "\t\t}";
  protected final String TEXT_62 = NL + "    // Stop processing messages if the job has passed this time." + NL + "\t long initialTime_";
  protected final String TEXT_63 = " = System.currentTimeMillis();" + NL + "    long maxDurationDeadline_";
  protected final String TEXT_64 = " = System.currentTimeMillis() + ";
  protected final String TEXT_65 = ";";
  protected final String TEXT_66 = NL + NL + "// Start consumption" + NL + "while (true) {" + NL + "\ttry {";
  protected final String TEXT_67 = NL + "\t\t\t\t// When both max duration and timeout between two messages are set, the poll timeout should be the lowest one between" + NL + "\t\t\t\t// the provided timeout and the remaining execution time." + NL + "\t\t\t\tlong remainingTime_";
  protected final String TEXT_68 = " = maxDurationDeadline_";
  protected final String TEXT_69 = " - System.currentTimeMillis();" + NL + "\t\t\t\tif(remainingTime_";
  protected final String TEXT_70 = " < 0) {" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\t}" + NL + "\t\t\t\tlong ";
  protected final String TEXT_71 = "_timeout = java.lang.Math.min(";
  protected final String TEXT_72 = ", remainingTime_";
  protected final String TEXT_73 = ");" + NL + "\t\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_74 = "> ";
  protected final String TEXT_75 = "_consumerRecords = ";
  protected final String TEXT_76 = "_kafkaConsumer.poll(";
  protected final String TEXT_77 = "_timeout);";
  protected final String TEXT_78 = NL + "\t\t\t\t// Use the provided consumer timeout and the consumer has to be stopped if there is no record polled from the topic" + NL + "\t\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_79 = "> ";
  protected final String TEXT_80 = "_consumerRecords = ";
  protected final String TEXT_81 = "_kafkaConsumer.poll(";
  protected final String TEXT_82 = ");";
  protected final String TEXT_83 = NL + "\t\t\t// Poll timeout has to be recomputed for every single poll" + NL + "\t\t\tlong ";
  protected final String TEXT_84 = "_timeout = maxDurationDeadline_";
  protected final String TEXT_85 = " - System.currentTimeMillis();" + NL + "\t\t\tif(";
  protected final String TEXT_86 = "_timeout < 0) {" + NL + "\t\t\t\tbreak;" + NL + "\t\t\t}" + NL + "\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_87 = "> ";
  protected final String TEXT_88 = "_consumerRecords = ";
  protected final String TEXT_89 = "_kafkaConsumer.poll(";
  protected final String TEXT_90 = "_timeout);";
  protected final String TEXT_91 = NL + "\t\t\t// Either use a default poll timeout of 1s or the provided timeout precision. The consumer won't stop if there is no record polled from the topic." + NL + "\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_92 = "> ";
  protected final String TEXT_93 = "_consumerRecords = ";
  protected final String TEXT_94 = "_kafkaConsumer.poll(";
  protected final String TEXT_95 = ");";
  protected final String TEXT_96 = NL + "\t\t\t// If there is no record polled from the topic, we know that we exceeded the provided consumer timeout. So immediately stop processing." + NL + "\t\t\tif(";
  protected final String TEXT_97 = "_consumerRecords.isEmpty()) {" + NL + "\t\t\t\tbreak;" + NL + "\t\t\t}";
  protected final String TEXT_98 = NL + "\t\tif(";
  protected final String TEXT_99 = "_consumerRecords == null) {" + NL + "\t\t\t// Dummy condition to make sure we have a way to break the current loop, regardless of the tKafkaInput configuration (compilation matter)." + NL + "\t\t\tbreak;" + NL + "\t\t}" + NL + "" + NL + "\t\tfor(org.apache.kafka.clients.consumer.ConsumerRecord<byte[], ";
  protected final String TEXT_100 = "> ";
  protected final String TEXT_101 = "_consumerRecord : ";
  protected final String TEXT_102 = "_consumerRecords) {" + NL;
  protected final String TEXT_103 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    

class TMapRStreamsInputUtil {

    private INode node;
    
    private INode connNode;

    private IConnection outgoingConnection;

    private Map<String, String> consumerProperties;
    
    private boolean useExistingConn;

    public TMapRStreamsInputUtil(INode node) {
        this.node = node;
        this.connNode = Boolean.valueOf(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"))
				? ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__")
				: node;
        this.outgoingConnection = findOutgoingConnection();
        this.consumerProperties = findConsumerProperties();
    }

    public String getOutputType() {
        return ElementParameterParser.getValue(node, "__OUTPUT_TYPE__");
    }

    public String getTopic() {
        return ElementParameterParser.getValue(node, "__STREAM_TOPIC__");
    }

    public String getGroupId() {
        return ElementParameterParser.getValue(node, "__GROUP_ID__");
    }

    public boolean isResetNewConsumerGroup() {
        return "true".equals(ElementParameterParser.getValue(node, "__RESET_OFFSET__"));
    }

    public String getAutoOffsetReset() {
        return ElementParameterParser.getValue(node, "__AUTO_OFFSET_RESET__").toLowerCase();
	 }

    public boolean isAutoCommitOffset() {
        return "true".equals(ElementParameterParser.getValue(node, "__AUTO_COMMIT_OFFSET__"));
    }

    public String getAutoCommitInterval() {
        return ElementParameterParser.getValue(node, "__COMMIT_INTERVAL__");
    }

    public boolean isStopOnMaxDuration() {
        return "true".equals(ElementParameterParser.getValue(node, "__USE_BATCH_MAX_DURATION__"));
    }

    public String getMaxDuration() {
        return ElementParameterParser.getValue(node, "__BATCH_MAX_DURATION__");
    }

    public boolean isStopOnMaxSize() {
        return "true".equals(ElementParameterParser.getValue(node, "__USE_BATCH_MAX_SIZE__"));
    }

    public String getMaxSize() {
        return ElementParameterParser.getValue(node, "__BATCH_MAX_SIZE__");
    }

    public boolean isStopOnMaxMsgWait() {
        return "true".equals(ElementParameterParser.getValue(node, "__USE_BATCH_MESSAGE_TIMEOUT__"));
    }

    public String getMaxMsgWait() {
        return ElementParameterParser.getValue(node, "__BATCH_MESSAGE_TIMEOUT__");
    }

    public String getConsumerTimeout() {
        if (isStopOnMaxDuration()) {
            return getPrecision();
        } else if (isStopOnMaxMsgWait()) {
            return getMaxMsgWait();
        } else {
            return getPrecision();
        }
    }

    public String getEncoding() {
        String encoding = ElementParameterParser.getValue(node, "__ENCODING__");
        if (encoding == null || "".equals(encoding)) {
            encoding = "\"UTF-8\"";
        }
        return encoding;
   }

    public Map<String, String> getConsumerProperties() {
        return this.consumerProperties;
    }

    public String getOutStructName() {
        return this.outgoingConnection.getName() + "Struct";
    }

    public String getOutgoingConnectionName() {
        return this.outgoingConnection.getName();
    }

    public String getOutgoingColumnName() {
        return this.outgoingConnection.getMetadataTable().getListColumns().get(0).getLabel();
    }
    
    private String getPrecision() {
			String precision = ElementParameterParser.getValue(node, "__CONSUMER_TIMEOUT__");
			return "\"-1\"".equals(precision) || "-1".equals(precision) ? "1000" : precision;
    }

    private String getIntProperty(String inputMaybeNumeric) {
        if (inputMaybeNumeric.matches("-?\\d+"))
            return "\"" + inputMaybeNumeric + "\"";
        return inputMaybeNumeric;
    }

    public String getAsLong(String inputMaybeLong) {
        if (inputMaybeLong.matches("-?\\d+[lL]?"))
            return inputMaybeLong;
        if (inputMaybeLong.matches("\"-?\\d+[lL]?\""))
            return inputMaybeLong.substring(1, inputMaybeLong.length() - 1);
        return "Long.valueOf("+inputMaybeLong+")";
    }

    private Map<String, String> findConsumerProperties() {
    	java.util.Map<String, String> result = new java.util.TreeMap<String, String>();
		result.put("\"enable.auto.commit\"", "\"" + isAutoCommitOffset() + "\"");
		result.put("\"auto.commit.interval.ms\"", getIntProperty(getAutoCommitInterval()));
		result.put("\"auto.offset.reset\"", "\"" + getAutoOffsetReset() + "\"");
		result.put("\"group.id\"", getGroupId());
		fillWithCustomProps(result);
    	return result;
   }
   
   private void fillWithCustomProps(java.util.Map<String, String> result) {
   	java.util.List<java.util.Map<String, String>> customProps = (java.util.List<java.util.Map<String, String>>)
				        ElementParameterParser.getObjectValue(node, "__CONSUMER_PROPERTIES__");
		if(customProps != null) {
		    for (java.util.Map<String, String> prop : customProps) {
		        result.put(prop.get("PROPERTY"), prop.get("VALUE"));
		    }
		}
   }

    private IConnection findOutgoingConnection() {
        IConnection result = null;
        List<? extends IConnection> connections = node.getOutgoingConnections();
        for(IConnection connection : connections) {
            if(connection.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
                result = connection;
                break;
            }
        }
        return result;
    }
}

    stringBuffer.append(TEXT_2);
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();

TMapRStreamsInputUtil tMapRStreamsInputUtil = new TMapRStreamsInputUtil(node);

    stringBuffer.append(TEXT_3);
    
	// This is the tMapRStreamsInput_begin javajet part for any MapR Streams version based on Kafka 0.9.0.x

	// Since the new Consumer API was introduced in Kafka 0.9.0.x, we must split the javajets
	// in order to generate code with the relevant Consumer API depending of the current Kafka version.

    stringBuffer.append(TEXT_4);
    
String outStructName = tMapRStreamsInputUtil.getOutStructName();

    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_10);
    
		if("STRING".equals(tMapRStreamsInputUtil.getOutputType())) {

    stringBuffer.append(TEXT_11);
    stringBuffer.append(tMapRStreamsInputUtil.getOutgoingColumnName());
    stringBuffer.append(TEXT_12);
    
		} else if("BYTES".equals(tMapRStreamsInputUtil.getOutputType())) {

    stringBuffer.append(TEXT_13);
    stringBuffer.append(tMapRStreamsInputUtil.getOutgoingColumnName());
    stringBuffer.append(TEXT_14);
    
		}

    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    
// Basic and advanced configuration
for (java.util.Map.Entry<String, String> consumerProperty : tMapRStreamsInputUtil.getConsumerProperties().entrySet()) {

    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(consumerProperty.getKey());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(consumerProperty.getValue());
    stringBuffer.append(TEXT_21);
    
}

    stringBuffer.append(TEXT_22);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(tMapRStreamsInputUtil.getEncoding());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(tMapRStreamsInputUtil.getTopic());
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    
if (tMapRStreamsInputUtil.isResetNewConsumerGroup()) {

    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
	if ("earliest".equals(tMapRStreamsInputUtil.getAutoOffsetReset())) {

    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    
	} else if ("latest".equals(tMapRStreamsInputUtil.getAutoOffsetReset())) {

    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_54);
    
	}

	if (!tMapRStreamsInputUtil.isAutoCommitOffset()) {

    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    
	}
}

// Save the global deadline for all messages.
if (tMapRStreamsInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(tMapRStreamsInputUtil.getAsLong(tMapRStreamsInputUtil.getMaxDuration()));
    stringBuffer.append(TEXT_65);
    
}

    stringBuffer.append(TEXT_66);
    
		if (tMapRStreamsInputUtil.isStopOnMaxMsgWait()) {
			if (tMapRStreamsInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(tMapRStreamsInputUtil.getAsLong(tMapRStreamsInputUtil.getMaxMsgWait()));
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    
			} else {

    stringBuffer.append(TEXT_78);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(tMapRStreamsInputUtil.getAsLong(tMapRStreamsInputUtil.getMaxMsgWait()));
    stringBuffer.append(TEXT_82);
    
			}
		} else if (tMapRStreamsInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_90);
    
		} else {

    stringBuffer.append(TEXT_91);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(tMapRStreamsInputUtil.getConsumerTimeout());
    stringBuffer.append(TEXT_95);
    
		}

		if (tMapRStreamsInputUtil.isStopOnMaxMsgWait()) {

    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    
		}

    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(TEXT_103);
    return stringBuffer.toString();
  }
}
