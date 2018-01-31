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
import java.util.HashMap;

public class TKafkaInputBeginJava
{
  protected static String nl;
  public static synchronized TKafkaInputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TKafkaInputBeginJava result = new TKafkaInputBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL;
  protected final String TEXT_3 = NL + "\t";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + NL + "class ";
  protected final String TEXT_6 = "_ValueDecoder implements kafka.serializer.Decoder<";
  protected final String TEXT_7 = "> {" + NL + "" + NL + "    private final kafka.serializer.StringDecoder stringDecoder;" + NL + "" + NL + "    public ";
  protected final String TEXT_8 = "_ValueDecoder(kafka.utils.VerifiableProperties props){" + NL + "        this.stringDecoder = new kafka.serializer.StringDecoder(props);" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_9 = " fromBytes(byte[] bytes) {";
  protected final String TEXT_10 = NL + "        ";
  protected final String TEXT_11 = " result = new ";
  protected final String TEXT_12 = "();";
  protected final String TEXT_13 = NL + "            String line = this.stringDecoder.fromBytes(bytes);" + NL + "            result.";
  protected final String TEXT_14 = " = line;";
  protected final String TEXT_15 = NL + "            result.";
  protected final String TEXT_16 = " = bytes;";
  protected final String TEXT_17 = NL + "        return result;" + NL + "    }" + NL + "}" + NL + "" + NL + "class ";
  protected final String TEXT_18 = "_KeyDecoder implements kafka.serializer.Decoder<byte[]> {" + NL + "" + NL + "    public ";
  protected final String TEXT_19 = "_KeyDecoder(kafka.utils.VerifiableProperties props){" + NL + "        // nothing but Decoder implementations must define a constructor with VerifiableProperties" + NL + "    }" + NL + "" + NL + "    public byte[] fromBytes(byte[] bytes) {" + NL + "        return bytes;" + NL + "    }" + NL + "}" + NL + "" + NL + "// Consumer configuration" + NL + "java.util.Properties ";
  protected final String TEXT_20 = "_kafkaProperties = new java.util.Properties();";
  protected final String TEXT_21 = NL + "    ";
  protected final String TEXT_22 = "_kafkaProperties.put(";
  protected final String TEXT_23 = ", ";
  protected final String TEXT_24 = ");";
  protected final String TEXT_25 = NL + "kafka.utils.VerifiableProperties ";
  protected final String TEXT_26 = "_verifiableProperties = new kafka.utils.VerifiableProperties(";
  protected final String TEXT_27 = "_kafkaProperties);" + NL;
  protected final String TEXT_28 = NL + "    // Reset consumer offsets" + NL + "    kafka.utils.ZkUtils.maybeDeletePath(";
  protected final String TEXT_29 = ", \"/consumers/\"+";
  protected final String TEXT_30 = "_kafkaProperties.get(\"group.id\"));";
  protected final String TEXT_31 = NL + NL + "// Single-threaded consumer" + NL + "kafka.javaapi.consumer.ConsumerConnector ";
  protected final String TEXT_32 = "_consumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(new kafka.consumer.ConsumerConfig(";
  protected final String TEXT_33 = "_kafkaProperties));" + NL + "java.util.Map<String, Integer> ";
  protected final String TEXT_34 = "_topicCountMap = new java.util.HashMap<String, Integer>();";
  protected final String TEXT_35 = NL;
  protected final String TEXT_36 = "_topicCountMap.put(";
  protected final String TEXT_37 = ", 1);" + NL + "java.util.Map<String, List<kafka.consumer.KafkaStream<byte[], ";
  protected final String TEXT_38 = ">>> ";
  protected final String TEXT_39 = "_consumerMap = ";
  protected final String TEXT_40 = "_consumerConnector.createMessageStreams(";
  protected final String TEXT_41 = "_topicCountMap, new ";
  protected final String TEXT_42 = "_KeyDecoder(";
  protected final String TEXT_43 = "_verifiableProperties), new ";
  protected final String TEXT_44 = "_ValueDecoder(";
  protected final String TEXT_45 = "_verifiableProperties));" + NL + "java.util.List<kafka.consumer.KafkaStream<byte[], ";
  protected final String TEXT_46 = ">> ";
  protected final String TEXT_47 = "_streams = ";
  protected final String TEXT_48 = "_consumerMap.get(";
  protected final String TEXT_49 = ");" + NL + "" + NL + "globalMap.put(\"";
  protected final String TEXT_50 = "_consumerConnector\", ";
  protected final String TEXT_51 = "_consumerConnector);" + NL;
  protected final String TEXT_52 = NL + "    // Stop processing messages if the job has passed this time." + NL + "    long maxDurationDeadline_";
  protected final String TEXT_53 = " = System.currentTimeMillis() + ";
  protected final String TEXT_54 = ";";
  protected final String TEXT_55 = NL + "        long maxMsgWait_";
  protected final String TEXT_56 = " = ";
  protected final String TEXT_57 = ";" + NL + "        long maxMsgWaitDeadline_";
  protected final String TEXT_58 = " = 0;";
  protected final String TEXT_59 = NL + NL + "// Start consumption" + NL + "kafka.consumer.ConsumerIterator<byte[], ";
  protected final String TEXT_60 = "> ";
  protected final String TEXT_61 = "_consumerIterator = ";
  protected final String TEXT_62 = "_streams.get(0).iterator();" + NL + "while (true) {" + NL + "    try {" + NL;
  protected final String TEXT_63 = NL + "            // Immediately stop processing messagesDeadline if the max duration deadline has passed." + NL + "            if (System.currentTimeMillis() > maxDurationDeadline_";
  protected final String TEXT_64 = ")" + NL + "                break;";
  protected final String TEXT_65 = NL + "                maxMsgWaitDeadline_";
  protected final String TEXT_66 = " = System.currentTimeMillis() + maxMsgWait_";
  protected final String TEXT_67 = ";;";
  protected final String TEXT_68 = NL + NL + "        if (!";
  protected final String TEXT_69 = "_consumerIterator.hasNext())" + NL + "            break;";
  protected final String TEXT_70 = NL + "\t";
  protected final String TEXT_71 = NL;
  protected final String TEXT_72 = NL + NL + "class ";
  protected final String TEXT_73 = "_ValueDeserializer implements org.apache.kafka.common.serialization.Deserializer<";
  protected final String TEXT_74 = "> {" + NL + "" + NL + "\tprivate org.apache.kafka.common.serialization.StringDeserializer stringDeserializer;" + NL + "" + NL + "\tpublic void configure(java.util.Map<java.lang.String,?> configs, boolean isKey) {" + NL + "\t\tstringDeserializer = new org.apache.kafka.common.serialization.StringDeserializer();" + NL + "\t\tstringDeserializer.configure(configs, isKey);" + NL + "\t}" + NL + "" + NL + "\tpublic ";
  protected final String TEXT_75 = " deserialize(String topic, byte[] data) {" + NL + "\t\t";
  protected final String TEXT_76 = " result = new ";
  protected final String TEXT_77 = "();";
  protected final String TEXT_78 = NL + "\t\t    String line = stringDeserializer.deserialize(topic, data);" + NL + "\t\t    result.";
  protected final String TEXT_79 = " = line;";
  protected final String TEXT_80 = NL + "\t\t    result.";
  protected final String TEXT_81 = " = data;";
  protected final String TEXT_82 = NL + "\t\treturn result;" + NL + "    }" + NL + "" + NL + "\tpublic void close() {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "}" + NL + "" + NL + "class ";
  protected final String TEXT_83 = "_KeyDeserializer implements org.apache.kafka.common.serialization.Deserializer<byte[]> {" + NL + "" + NL + "\tpublic void configure(java.util.Map<java.lang.String,?> configs, boolean isKey) {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "" + NL + "\tpublic byte[] deserialize(String topic, byte[] data) {" + NL + "\t    return data;" + NL + "\t}" + NL + "" + NL + "\tpublic void close() {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "}" + NL + "" + NL + "// Consumer configuration" + NL + "java.util.Properties ";
  protected final String TEXT_84 = "_kafkaProperties = new java.util.Properties();";
  protected final String TEXT_85 = NL + "    ";
  protected final String TEXT_86 = "_kafkaProperties.put(";
  protected final String TEXT_87 = ", ";
  protected final String TEXT_88 = ");";
  protected final String TEXT_89 = NL + "\t ";
  protected final String TEXT_90 = "_kafkaProperties.put(\"ssl.truststore.type\", ";
  protected final String TEXT_91 = ");" + NL + " \t ";
  protected final String TEXT_92 = "_kafkaProperties.put(\"ssl.truststore.location\", ";
  protected final String TEXT_93 = ");" + NL + " \t ";
  protected final String TEXT_94 = "_kafkaProperties.put(\"ssl.truststore.password\", ";
  protected final String TEXT_95 = ");";
  protected final String TEXT_96 = NL + "\t \t";
  protected final String TEXT_97 = "_kafkaProperties.put(\"ssl.keystore.type\", ";
  protected final String TEXT_98 = ");" + NL + " \t \t";
  protected final String TEXT_99 = "_kafkaProperties.put(\"ssl.keystore.location\", ";
  protected final String TEXT_100 = ");" + NL + " \t \t";
  protected final String TEXT_101 = "_kafkaProperties.put(\"ssl.keystore.password\", ";
  protected final String TEXT_102 = ");";
  protected final String TEXT_103 = NL + "\t";
  protected final String TEXT_104 = "_kafkaProperties.put(\"sasl.kerberos.service.name\", ";
  protected final String TEXT_105 = ");";
  protected final String TEXT_106 = NL + "\t\t";
  protected final String TEXT_107 = "_kafkaProperties.put(\"sasl.kerberos.kinit.cmd\", ";
  protected final String TEXT_108 = ");";
  protected final String TEXT_109 = NL + NL + "\tSystem.setProperty(\"java.security.auth.login.config\", ";
  protected final String TEXT_110 = ");";
  protected final String TEXT_111 = NL + "\t\tSystem.setProperty(\"java.security.krb5.conf\", ";
  protected final String TEXT_112 = ");";
  protected final String TEXT_113 = NL + NL + "// Value deserializer configuration";
  protected final String TEXT_114 = NL;
  protected final String TEXT_115 = "_ValueDeserializer instance_";
  protected final String TEXT_116 = "_ValueDeserializer = new ";
  protected final String TEXT_117 = "_ValueDeserializer();" + NL + "java.util.Map<String, String> instance_";
  protected final String TEXT_118 = "_ValueDeserializer_configs = new java.util.HashMap<String, String>();" + NL + "instance_";
  protected final String TEXT_119 = "_ValueDeserializer_configs.put(\"serializer.encoding\", ";
  protected final String TEXT_120 = ");" + NL + "instance_";
  protected final String TEXT_121 = "_ValueDeserializer.configure(instance_";
  protected final String TEXT_122 = "_ValueDeserializer_configs, false);" + NL + "" + NL + "// Single-threaded consumer. Make sure the new security information is picked up." + NL + "javax.security.auth.login.Configuration.setConfiguration(null);" + NL + "org.apache.kafka.clients.consumer.KafkaConsumer<byte[], ";
  protected final String TEXT_123 = "> ";
  protected final String TEXT_124 = "_kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<byte[], ";
  protected final String TEXT_125 = ">(";
  protected final String TEXT_126 = "_kafkaProperties, new ";
  protected final String TEXT_127 = "_KeyDeserializer(), instance_";
  protected final String TEXT_128 = "_ValueDeserializer);";
  protected final String TEXT_129 = NL;
  protected final String TEXT_130 = "_kafkaConsumer.subscribe(java.util.Arrays.asList(";
  protected final String TEXT_131 = "));" + NL + "" + NL + "globalMap.put(\"";
  protected final String TEXT_132 = "_kafkaConsumer\", ";
  protected final String TEXT_133 = "_kafkaConsumer);" + NL;
  protected final String TEXT_134 = NL + "\t// We first have to do a quick poll() in order to know which partitions are assigned to the consumer before" + NL + "\t// \"resetting\" offsets by seeking to the beginning/end of the current partitions." + NL + "\t// Those polled records are not processed since we wanted to reset the offsets first." + NL + "\t";
  protected final String TEXT_135 = "_kafkaConsumer.poll(0);" + NL + "\tjava.util.Set<org.apache.kafka.common.TopicPartition> ";
  protected final String TEXT_136 = "_assignedtopicPartitions = ";
  protected final String TEXT_137 = "_kafkaConsumer.assignment();";
  protected final String TEXT_138 = NL + "\t";
  protected final String TEXT_139 = "_kafkaConsumer.seekToBeginning(";
  protected final String TEXT_140 = "_assignedtopicPartitions.toArray(new org.apache.kafka.common.TopicPartition[";
  protected final String TEXT_141 = "_assignedtopicPartitions.size()]));";
  protected final String TEXT_142 = NL + "\t";
  protected final String TEXT_143 = "_kafkaConsumer.seekToEnd(";
  protected final String TEXT_144 = "_assignedtopicPartitions.toArray(new org.apache.kafka.common.TopicPartition[";
  protected final String TEXT_145 = "_assignedtopicPartitions.size()]));";
  protected final String TEXT_146 = NL + "\t\t// Commit the offsets to ensure that the next job(s) use the reset offset." + NL + "\t\t{" + NL + "\t\tjava.util.HashMap<org.apache.kafka.common.TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata> ";
  protected final String TEXT_147 = "_resetOffsets = new java.util.HashMap<>();" + NL + "\t\tfor (org.apache.kafka.common.TopicPartition topic : ";
  protected final String TEXT_148 = "_assignedtopicPartitions) {" + NL + "\t\t\t";
  protected final String TEXT_149 = "_resetOffsets.put(topic, new org.apache.kafka.clients.consumer.OffsetAndMetadata(" + NL + "\t\t\t\t";
  protected final String TEXT_150 = "_kafkaConsumer.position(topic)));" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_151 = "_kafkaConsumer.commitSync(";
  protected final String TEXT_152 = "_resetOffsets);" + NL + "\t\t}";
  protected final String TEXT_153 = NL + "    // Stop processing messages if the job has passed this time." + NL + "\t long initialTime_";
  protected final String TEXT_154 = " = System.currentTimeMillis();" + NL + "    long maxDurationDeadline_";
  protected final String TEXT_155 = " = System.currentTimeMillis() + ";
  protected final String TEXT_156 = ";";
  protected final String TEXT_157 = NL + NL + "// Start consumption" + NL + "while (true) {" + NL + "\ttry {";
  protected final String TEXT_158 = NL + "\t\t\t\t// When both max duration and timeout between two messages are set, the poll timeout should be the lowest one between" + NL + "\t\t\t\t// the provided timeout and the remaining execution time." + NL + "\t\t\t\tlong remainingTime_";
  protected final String TEXT_159 = " = maxDurationDeadline_";
  protected final String TEXT_160 = " - System.currentTimeMillis();" + NL + "\t\t\t\tif(remainingTime_";
  protected final String TEXT_161 = " < 0) {" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\t}" + NL + "\t\t\t\tlong ";
  protected final String TEXT_162 = "_timeout = java.lang.Math.min(";
  protected final String TEXT_163 = ", remainingTime_";
  protected final String TEXT_164 = ");" + NL + "\t\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_165 = "> ";
  protected final String TEXT_166 = "_consumerRecords = ";
  protected final String TEXT_167 = "_kafkaConsumer.poll(";
  protected final String TEXT_168 = "_timeout);";
  protected final String TEXT_169 = NL + "\t\t\t\t// Use the provided consumer timeout and the consumer has to be stopped if there is no record polled from the topic" + NL + "\t\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_170 = "> ";
  protected final String TEXT_171 = "_consumerRecords = ";
  protected final String TEXT_172 = "_kafkaConsumer.poll(";
  protected final String TEXT_173 = ");";
  protected final String TEXT_174 = NL + "\t\t\t// Poll timeout has to be recomputed for every single poll" + NL + "\t\t\tlong ";
  protected final String TEXT_175 = "_timeout = maxDurationDeadline_";
  protected final String TEXT_176 = " - System.currentTimeMillis();" + NL + "\t\t\tif(";
  protected final String TEXT_177 = "_timeout < 0) {" + NL + "\t\t\t\tbreak;" + NL + "\t\t\t}" + NL + "\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_178 = "> ";
  protected final String TEXT_179 = "_consumerRecords = ";
  protected final String TEXT_180 = "_kafkaConsumer.poll(";
  protected final String TEXT_181 = "_timeout);";
  protected final String TEXT_182 = NL + "\t\t\t// Either use a default poll timeout of 1s or the provided timeout precision. The consumer won't stop if there is no record polled from the topic." + NL + "\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_183 = "> ";
  protected final String TEXT_184 = "_consumerRecords = ";
  protected final String TEXT_185 = "_kafkaConsumer.poll(";
  protected final String TEXT_186 = ");";
  protected final String TEXT_187 = NL + "\t\t\t// If there is no record polled from the topic, we know that we exceeded the provided consumer timeout. So immediately stop processing." + NL + "\t\t\tif(";
  protected final String TEXT_188 = "_consumerRecords.isEmpty()) {" + NL + "\t\t\t\tbreak;" + NL + "\t\t\t}";
  protected final String TEXT_189 = NL + "\t\tif(";
  protected final String TEXT_190 = "_consumerRecords == null) {" + NL + "\t\t\t// Dummy condition to make sure we have a way to break the current loop, regardless of the tKafkaInput configuration (compilation matter)." + NL + "\t\t\tbreak;" + NL + "\t\t}" + NL + "" + NL + "\t\tfor(org.apache.kafka.clients.consumer.ConsumerRecord<byte[], ";
  protected final String TEXT_191 = "> ";
  protected final String TEXT_192 = "_consumerRecord : ";
  protected final String TEXT_193 = "_consumerRecords) {";
  protected final String TEXT_194 = NL + "\t";
  protected final String TEXT_195 = NL;
  protected final String TEXT_196 = NL + NL + "class ";
  protected final String TEXT_197 = "_ValueDeserializer implements org.apache.kafka.common.serialization.Deserializer<";
  protected final String TEXT_198 = "> {" + NL + "" + NL + "\tprivate org.apache.kafka.common.serialization.StringDeserializer stringDeserializer;" + NL + "" + NL + "\tpublic void configure(java.util.Map<java.lang.String,?> configs, boolean isKey) {" + NL + "\t\tstringDeserializer = new org.apache.kafka.common.serialization.StringDeserializer();" + NL + "\t\tstringDeserializer.configure(configs, isKey);" + NL + "\t}" + NL + "" + NL + "\tpublic ";
  protected final String TEXT_199 = " deserialize(String topic, byte[] data) {" + NL + "\t\t";
  protected final String TEXT_200 = " result = new ";
  protected final String TEXT_201 = "();";
  protected final String TEXT_202 = NL + "\t\t    String line = stringDeserializer.deserialize(topic, data);" + NL + "\t\t    result.";
  protected final String TEXT_203 = " = line;";
  protected final String TEXT_204 = NL + "\t\t    result.";
  protected final String TEXT_205 = " = data;";
  protected final String TEXT_206 = NL + "\t\treturn result;" + NL + "    }" + NL + "" + NL + "\tpublic void close() {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "}" + NL + "" + NL + "class ";
  protected final String TEXT_207 = "_KeyDeserializer implements org.apache.kafka.common.serialization.Deserializer<byte[]> {" + NL + "" + NL + "\tpublic void configure(java.util.Map<java.lang.String,?> configs, boolean isKey) {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "" + NL + "\tpublic byte[] deserialize(String topic, byte[] data) {" + NL + "\t    return data;" + NL + "\t}" + NL + "" + NL + "\tpublic void close() {" + NL + "\t\t// nothing" + NL + "\t}" + NL + "}" + NL + "" + NL + "// Consumer configuration" + NL + "java.util.Properties ";
  protected final String TEXT_208 = "_kafkaProperties = new java.util.Properties();";
  protected final String TEXT_209 = NL + "    ";
  protected final String TEXT_210 = "_kafkaProperties.put(";
  protected final String TEXT_211 = ", ";
  protected final String TEXT_212 = ");";
  protected final String TEXT_213 = NL + "\t ";
  protected final String TEXT_214 = "_kafkaProperties.put(\"ssl.truststore.type\", ";
  protected final String TEXT_215 = ");" + NL + " \t ";
  protected final String TEXT_216 = "_kafkaProperties.put(\"ssl.truststore.location\", ";
  protected final String TEXT_217 = ");" + NL + " \t ";
  protected final String TEXT_218 = "_kafkaProperties.put(\"ssl.truststore.password\", ";
  protected final String TEXT_219 = ");";
  protected final String TEXT_220 = NL + "\t \t";
  protected final String TEXT_221 = "_kafkaProperties.put(\"ssl.keystore.type\", ";
  protected final String TEXT_222 = ");" + NL + " \t \t";
  protected final String TEXT_223 = "_kafkaProperties.put(\"ssl.keystore.location\", ";
  protected final String TEXT_224 = ");" + NL + " \t \t";
  protected final String TEXT_225 = "_kafkaProperties.put(\"ssl.keystore.password\", ";
  protected final String TEXT_226 = ");";
  protected final String TEXT_227 = NL + "\t";
  protected final String TEXT_228 = "_kafkaProperties.put(\"sasl.kerberos.service.name\", ";
  protected final String TEXT_229 = ");";
  protected final String TEXT_230 = NL + "\t\t";
  protected final String TEXT_231 = "_kafkaProperties.put(\"sasl.kerberos.kinit.cmd\", ";
  protected final String TEXT_232 = ");";
  protected final String TEXT_233 = NL + NL + "\tSystem.setProperty(\"java.security.auth.login.config\", ";
  protected final String TEXT_234 = ");";
  protected final String TEXT_235 = NL + "\t\tSystem.setProperty(\"java.security.krb5.conf\", ";
  protected final String TEXT_236 = ");";
  protected final String TEXT_237 = NL + NL + "// Value deserializer configuration";
  protected final String TEXT_238 = NL;
  protected final String TEXT_239 = "_ValueDeserializer instance_";
  protected final String TEXT_240 = "_ValueDeserializer = new ";
  protected final String TEXT_241 = "_ValueDeserializer();" + NL + "java.util.Map<String, String> instance_";
  protected final String TEXT_242 = "_ValueDeserializer_configs = new java.util.HashMap<String, String>();" + NL + "instance_";
  protected final String TEXT_243 = "_ValueDeserializer_configs.put(\"serializer.encoding\", ";
  protected final String TEXT_244 = ");" + NL + "instance_";
  protected final String TEXT_245 = "_ValueDeserializer.configure(instance_";
  protected final String TEXT_246 = "_ValueDeserializer_configs, false);" + NL + "" + NL + "// Single-threaded consumer. Make sure the new security information is picked up." + NL + "javax.security.auth.login.Configuration.setConfiguration(null);" + NL + "org.apache.kafka.clients.consumer.KafkaConsumer<byte[], ";
  protected final String TEXT_247 = "> ";
  protected final String TEXT_248 = "_kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<byte[], ";
  protected final String TEXT_249 = ">(";
  protected final String TEXT_250 = "_kafkaProperties, new ";
  protected final String TEXT_251 = "_KeyDeserializer(), instance_";
  protected final String TEXT_252 = "_ValueDeserializer);";
  protected final String TEXT_253 = NL;
  protected final String TEXT_254 = "_kafkaConsumer.subscribe(java.util.Arrays.asList(";
  protected final String TEXT_255 = "));" + NL + "" + NL + "globalMap.put(\"";
  protected final String TEXT_256 = "_kafkaConsumer\", ";
  protected final String TEXT_257 = "_kafkaConsumer);" + NL;
  protected final String TEXT_258 = NL + "\t// We first have to do a quick poll() in order to know which partitions are assigned to the consumer before" + NL + "\t// \"resetting\" offsets by seeking to the beginning/end of the current partitions." + NL + "\t// Those polled records are not processed since we wanted to reset the offsets first." + NL + "\t";
  protected final String TEXT_259 = "_kafkaConsumer.poll(0);" + NL + "\tjava.util.Set<org.apache.kafka.common.TopicPartition> ";
  protected final String TEXT_260 = "_assignedtopicPartitions = ";
  protected final String TEXT_261 = "_kafkaConsumer.assignment();";
  protected final String TEXT_262 = NL + "\t";
  protected final String TEXT_263 = "_kafkaConsumer.seekToBeginning(";
  protected final String TEXT_264 = "_assignedtopicPartitions);";
  protected final String TEXT_265 = NL + "\t";
  protected final String TEXT_266 = "_kafkaConsumer.seekToEnd(";
  protected final String TEXT_267 = "_assignedtopicPartitions);";
  protected final String TEXT_268 = NL + "\t\t// Commit the offsets to ensure that the next job(s) use the reset offset." + NL + "\t\t{" + NL + "\t\tjava.util.HashMap<org.apache.kafka.common.TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata> ";
  protected final String TEXT_269 = "_resetOffsets = new java.util.HashMap<>();" + NL + "\t\tfor (org.apache.kafka.common.TopicPartition topic : ";
  protected final String TEXT_270 = "_assignedtopicPartitions) {" + NL + "\t\t\t";
  protected final String TEXT_271 = "_resetOffsets.put(topic, new org.apache.kafka.clients.consumer.OffsetAndMetadata(" + NL + "\t\t\t\t";
  protected final String TEXT_272 = "_kafkaConsumer.position(topic)));" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_273 = "_kafkaConsumer.commitSync(";
  protected final String TEXT_274 = "_resetOffsets);" + NL + "\t\t}";
  protected final String TEXT_275 = NL + "    // Stop processing messages if the job has passed this time." + NL + "\t long initialTime_";
  protected final String TEXT_276 = " = System.currentTimeMillis();" + NL + "    long maxDurationDeadline_";
  protected final String TEXT_277 = " = System.currentTimeMillis() + ";
  protected final String TEXT_278 = ";";
  protected final String TEXT_279 = NL + NL + "// Start consumption" + NL + "while (true) {" + NL + "\ttry {";
  protected final String TEXT_280 = NL + "\t\t\t\t// When both max duration and timeout between two messages are set, the poll timeout should be the lowest one between" + NL + "\t\t\t\t// the provided timeout and the remaining execution time." + NL + "\t\t\t\tlong remainingTime_";
  protected final String TEXT_281 = " = maxDurationDeadline_";
  protected final String TEXT_282 = " - System.currentTimeMillis();" + NL + "\t\t\t\tif(remainingTime_";
  protected final String TEXT_283 = " < 0) {" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\t}" + NL + "\t\t\t\tlong ";
  protected final String TEXT_284 = "_timeout = java.lang.Math.min(";
  protected final String TEXT_285 = ", remainingTime_";
  protected final String TEXT_286 = ");" + NL + "\t\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_287 = "> ";
  protected final String TEXT_288 = "_consumerRecords = ";
  protected final String TEXT_289 = "_kafkaConsumer.poll(";
  protected final String TEXT_290 = "_timeout);";
  protected final String TEXT_291 = NL + "\t\t\t\t// Use the provided consumer timeout and the consumer has to be stopped if there is no record polled from the topic" + NL + "\t\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_292 = "> ";
  protected final String TEXT_293 = "_consumerRecords = ";
  protected final String TEXT_294 = "_kafkaConsumer.poll(";
  protected final String TEXT_295 = ");";
  protected final String TEXT_296 = NL + "\t\t\t// Poll timeout has to be recomputed for every single poll" + NL + "\t\t\tlong ";
  protected final String TEXT_297 = "_timeout = maxDurationDeadline_";
  protected final String TEXT_298 = " - System.currentTimeMillis();" + NL + "\t\t\tif(";
  protected final String TEXT_299 = "_timeout < 0) {" + NL + "\t\t\t\tbreak;" + NL + "\t\t\t}" + NL + "\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_300 = "> ";
  protected final String TEXT_301 = "_consumerRecords = ";
  protected final String TEXT_302 = "_kafkaConsumer.poll(";
  protected final String TEXT_303 = "_timeout);";
  protected final String TEXT_304 = NL + "\t\t\t// Either use a default poll timeout of 1s or the provided timeout precision. The consumer won't stop if there is no record polled from the topic." + NL + "\t\t\torg.apache.kafka.clients.consumer.ConsumerRecords<byte[], ";
  protected final String TEXT_305 = "> ";
  protected final String TEXT_306 = "_consumerRecords = ";
  protected final String TEXT_307 = "_kafkaConsumer.poll(";
  protected final String TEXT_308 = ");";
  protected final String TEXT_309 = NL + "\t\t\t// If there is no record polled from the topic, we know that we exceeded the provided consumer timeout. So immediately stop processing." + NL + "\t\t\tif(";
  protected final String TEXT_310 = "_consumerRecords.isEmpty()) {" + NL + "\t\t\t\tbreak;" + NL + "\t\t\t}";
  protected final String TEXT_311 = NL + "\t\tif(";
  protected final String TEXT_312 = "_consumerRecords == null) {" + NL + "\t\t\t// Dummy condition to make sure we have a way to break the current loop, regardless of the tKafkaInput configuration (compilation matter)." + NL + "\t\t\tbreak;" + NL + "\t\t}" + NL + "" + NL + "\t\tfor(org.apache.kafka.clients.consumer.ConsumerRecord<byte[], ";
  protected final String TEXT_313 = "> ";
  protected final String TEXT_314 = "_consumerRecord : ";
  protected final String TEXT_315 = "_consumerRecords) {";
  protected final String TEXT_316 = NL;

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

    

class TKafkaInputUtil {

    private INode node;

    private INode connNode;
    
    private TSetKeystoreUtil tSetKeystoreUtil;

    private IConnection outgoingConnection;

    private Map<String, String> kafkaConsumerProperties;

    private boolean useExistingConn;

    public TKafkaInputUtil(INode node) {
        this.node = node;
        this.connNode = Boolean.valueOf(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"))
            ? ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__")
            : node;
        this.tSetKeystoreUtil = new TSetKeystoreUtil(connNode);
        this.outgoingConnection = findOutgoingConnection();
        this.kafkaConsumerProperties = findKafkaConsumerProperties();
    }

    public String getOutputType() {
        return ElementParameterParser.getValue(node, "__OUTPUT_TYPE__");
    }

    public String getZookeeperConnect() {
        return ElementParameterParser.getValue(connNode, "__ZOOKEEPER_CONNECT__");
    }
    
    public String getBrokerList() {
        return ElementParameterParser.getValue(connNode, "__BROKER_LIST__");
    }

    public String getTopic() {
        return ElementParameterParser.getValue(node, "__KAFKA_TOPIC__");
    }

    public String getGroupId() {
        return ElementParameterParser.getValue(node, "__GROUP_ID__");
    }

    public boolean isResetNewConsumerGroup() {
        return "true".equals(ElementParameterParser.getValue(node, "__RESET_OFFSET__"));
    }

    public String getAutoOffsetResetNew() {
        return ElementParameterParser.getValue(node, "__AUTO_OFFSET_RESET_NEW__").toLowerCase();
	 }
	 
	 public String getAutoOffsetReset() {
        return ElementParameterParser.getValue(node, "__AUTO_OFFSET_RESET__").toLowerCase();
	 }

    public String getOffsetStorage() {
        return ElementParameterParser.getValue(node, "__KAFKA_OFFSET_STORAGE__").toLowerCase();
    }

    public boolean isDualCommit() {
        return "kafka".equals(getOffsetStorage()) && "true".equals(ElementParameterParser.getValue(node, "__KAFKA_DUAL_COMMIT_CHECK__"));
    }

    public boolean isAutoCommitOffset() {
        return "true".equals(ElementParameterParser.getValue(node, "__AUTO_COMMIT_OFFSET__"));
    }

    public String getAutoCommitInterval() {
        return ElementParameterParser.getValue(node, "__KAFKA_COMMIT_INTERVAL__");
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
        } else if (!isKafkaVersion("KAFKA_0_8_2_0")) {
            return getPrecision();
        }
        // If we're not stopping, then don't ever wait for consumer timeout.
        return "-1";
    }

    public String getEncoding() {
        String encoding = ElementParameterParser.getValue(node, "__ENCODING__");
        if (encoding == null || "".equals(encoding)) {
            encoding = "\"UTF-8\"";
        }
        return encoding;
   }

    public Map<String, String> getKafkaConsumerProperties() {
        return this.kafkaConsumerProperties;
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
			String precision = ElementParameterParser.getValue(node, "__KAFKA_CONSUMER_TIMEOUT__");
			return "\"-1\"".equals(precision) || "-1".equals(precision) ? "1000" : precision;
    }

    private String getIntProperty(String inputMaybeNumeric) {
        if (inputMaybeNumeric.matches("-?\\d+"))
            return "\"" + inputMaybeNumeric + "\"";
        return inputMaybeNumeric;
    }
    
    public boolean isKafkaVersion(String version) {
			return (version != null) ? version.equals(ElementParameterParser.getValue(connNode, "__KAFKA_VERSION__")) : false;
    }

    public String getAsLong(String inputMaybeLong) {
        if (inputMaybeLong.matches("-?\\d+[lL]?"))
            return inputMaybeLong;
        if (inputMaybeLong.matches("\"-?\\d+[lL]?\""))
            return inputMaybeLong.substring(1, inputMaybeLong.length() - 1);
        return "Long.valueOf("+inputMaybeLong+")";
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

    private Map<String, String> findKafkaConsumerProperties() {
        java.util.Map<String, String> result = new java.util.TreeMap<String, String>();
        if(isKafkaVersion("KAFKA_0_8_2_0")) {
				// Old consumer API configuration
	        result.put("\"auto.commit.enable\"", "\"" + isAutoCommitOffset() + "\"");
	        result.put("\"auto.commit.interval.ms\"", getIntProperty(getAutoCommitInterval()));
	        result.put("\"auto.offset.reset\"", "\"" + getAutoOffsetReset() + "\"");
	        result.put("\"consumer.timeout.ms\"", getIntProperty(getConsumerTimeout()));
	        result.put("\"dual.commit.enabled\"", "\"" + isDualCommit() + "\"");
	        result.put("\"group.id\"", getGroupId());
	        result.put("\"offsets.storage\"", "\"" + getOffsetStorage() + "\"");
	        result.put("\"serializer.encoding\"", getEncoding());
	        result.put("\"zookeeper.connect\"", getZookeeperConnect());
			  fillWithCustomProps(result);
        } else {
				// New consumer API configuration
				result.put("\"enable.auto.commit\"", "\"" + isAutoCommitOffset() + "\"");
	        	result.put("\"auto.commit.interval.ms\"", getIntProperty(getAutoCommitInterval()));
				result.put("\"auto.offset.reset\"", "\"" + getAutoOffsetResetNew() + "\"");
				result.put("\"group.id\"", getGroupId());
				result.put("\"bootstrap.servers\"", getBrokerList());
				result.put("\"security.protocol\"", "\"" + getSecurityProtocol() + "\"");
				fillWithCustomProps(result);
        }
        return result;
   }
   
   private void fillWithCustomProps(java.util.Map<String, String> result) {
   	java.util.List<java.util.Map<String, String>> customProps = (java.util.List<java.util.Map<String, String>>)
				        ElementParameterParser.getObjectValue(node, "__KAFKA_CONSUMER_PROPERTIES__");
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

TKafkaInputUtil tKafkaInputUtil = new TKafkaInputUtil(node);

if(tKafkaInputUtil.isKafkaVersion("KAFKA_0_8_2_0")) {

    stringBuffer.append(TEXT_3);
    
	// This is the tKafkaInput_begin javajet part for Kafka 0.8.2.x
	
	// Since the new Consumer API was introduced in Kafka 0.9.0.x, we must split the javajets
	// in order to generate code with the relevant Consumer API depending of the current Kafka version.

    stringBuffer.append(TEXT_4);
    
String outStructName = tKafkaInputUtil.getOutStructName();

    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_12);
    
        if("STRING".equals(tKafkaInputUtil.getOutputType())) {
            
    stringBuffer.append(TEXT_13);
    stringBuffer.append(tKafkaInputUtil.getOutgoingColumnName());
    stringBuffer.append(TEXT_14);
    
        } else if("BYTES".equals(tKafkaInputUtil.getOutputType())) {
            
    stringBuffer.append(TEXT_15);
    stringBuffer.append(tKafkaInputUtil.getOutgoingColumnName());
    stringBuffer.append(TEXT_16);
    
        }
        
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    
for (java.util.Map.Entry<String, String> kafkaProperty : tKafkaInputUtil.getKafkaConsumerProperties().entrySet()) {
    
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(kafkaProperty.getKey());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(kafkaProperty.getValue());
    stringBuffer.append(TEXT_24);
    
}

    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    
if (tKafkaInputUtil.isResetNewConsumerGroup()) {
    
    stringBuffer.append(TEXT_28);
    stringBuffer.append(tKafkaInputUtil.getZookeeperConnect());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    
}

    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(tKafkaInputUtil.getTopic());
    stringBuffer.append(TEXT_37);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(tKafkaInputUtil.getTopic());
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    
// Save the global deadline for all messages.
if (tKafkaInputUtil.isStopOnMaxDuration()) {
    
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(tKafkaInputUtil.getAsLong(tKafkaInputUtil.getMaxDuration()));
    stringBuffer.append(TEXT_54);
    
    // These only need to be set if both maxDuration and maxMsgWait are used.
    if (tKafkaInputUtil.isStopOnMaxMsgWait()) {
        
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(tKafkaInputUtil.getAsLong(tKafkaInputUtil.getMaxMsgWait()));
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    
    }
}

    stringBuffer.append(TEXT_59);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    
        // Save the global deadline for all messages.
        if (tKafkaInputUtil.isStopOnMaxDuration()) {
            
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    
            // These only need to be set if both maxDuration and maxMsgWait are used.
            if (tKafkaInputUtil.isStopOnMaxMsgWait()) {
                
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    
            }
        }
        
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    
} else if(tKafkaInputUtil.isKafkaVersion("KAFKA_0_9_0_1")) {

    stringBuffer.append(TEXT_70);
    
	// This is the tKafkaInput_begin javajet part for Kafka 0.9.0.x

	// Since the new Consumer API was introduced in Kafka 0.9.0.x, we must split the javajets
	// in order to generate code with the relevant Consumer API depending of the current Kafka version.

    stringBuffer.append(TEXT_71);
    
String outStructName = tKafkaInputUtil.getOutStructName();
TSetKeystoreUtil tSetKeystoreUtil = tKafkaInputUtil.getTSetKeystoreUtil();

    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_77);
    
		if("STRING".equals(tKafkaInputUtil.getOutputType())) {

    stringBuffer.append(TEXT_78);
    stringBuffer.append(tKafkaInputUtil.getOutgoingColumnName());
    stringBuffer.append(TEXT_79);
    
		} else if("BYTES".equals(tKafkaInputUtil.getOutputType())) {

    stringBuffer.append(TEXT_80);
    stringBuffer.append(tKafkaInputUtil.getOutgoingColumnName());
    stringBuffer.append(TEXT_81);
    
		}

    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    
// Basic and advanced configuration
for (java.util.Map.Entry<String, String> kafkaProperty : tKafkaInputUtil.getKafkaConsumerProperties().entrySet()) {

    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(kafkaProperty.getKey());
    stringBuffer.append(TEXT_87);
    stringBuffer.append(kafkaProperty.getValue());
    stringBuffer.append(TEXT_88);
    
}

// SSL configuration
if (tSetKeystoreUtil.useHTTPS()) {

    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(tSetKeystoreUtil.getTrustStoreType());
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(tSetKeystoreUtil.getTrustStorePath());
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(tSetKeystoreUtil.getTrustStorePassword());
    stringBuffer.append(TEXT_95);
    
	if (tSetKeystoreUtil.needClientAuth()) {

    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(tSetKeystoreUtil.getKeyStoreType());
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(tSetKeystoreUtil.getKeyStorePath());
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    stringBuffer.append(tSetKeystoreUtil.getKeyStorePassword());
    stringBuffer.append(TEXT_102);
    
	}
}

// Kerberos configuration
if (tKafkaInputUtil.useKrb()) {

    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(tKafkaInputUtil.getKrbServiceName());
    stringBuffer.append(TEXT_105);
    
	if (tKafkaInputUtil.isSetKinitPath()) {

    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(tKafkaInputUtil.getKinitPath());
    stringBuffer.append(TEXT_108);
    
	}

    stringBuffer.append(TEXT_109);
    stringBuffer.append(tKafkaInputUtil.getJAASConf());
    stringBuffer.append(TEXT_110);
    
	if (tKafkaInputUtil.isSetKrb5Conf()) {

    stringBuffer.append(TEXT_111);
    stringBuffer.append(tKafkaInputUtil.getKrb5Conf());
    stringBuffer.append(TEXT_112);
    
	}
}

    stringBuffer.append(TEXT_113);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(tKafkaInputUtil.getEncoding());
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(tKafkaInputUtil.getTopic());
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_133);
    
if (tKafkaInputUtil.isResetNewConsumerGroup()) {

    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_137);
    
	if ("earliest".equals(tKafkaInputUtil.getAutoOffsetResetNew())) {

    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    
	} else if ("latest".equals(tKafkaInputUtil.getAutoOffsetResetNew())) {

    stringBuffer.append(TEXT_142);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_145);
    
	}

	if (!tKafkaInputUtil.isAutoCommitOffset()) {

    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_151);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_152);
    
	}
}

// Save the global deadline for all messages.
if (tKafkaInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_153);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(tKafkaInputUtil.getAsLong(tKafkaInputUtil.getMaxDuration()));
    stringBuffer.append(TEXT_156);
    
}

    stringBuffer.append(TEXT_157);
    
		if (tKafkaInputUtil.isStopOnMaxMsgWait()) {
			if (tKafkaInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_158);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(tKafkaInputUtil.getAsLong(tKafkaInputUtil.getMaxMsgWait()));
    stringBuffer.append(TEXT_163);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_164);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_168);
    
			} else {

    stringBuffer.append(TEXT_169);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_170);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(tKafkaInputUtil.getAsLong(tKafkaInputUtil.getMaxMsgWait()));
    stringBuffer.append(TEXT_173);
    
			}
		} else if (tKafkaInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    
		} else {

    stringBuffer.append(TEXT_182);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(tKafkaInputUtil.getConsumerTimeout());
    stringBuffer.append(TEXT_186);
    
		}

		if (tKafkaInputUtil.isStopOnMaxMsgWait()) {

    stringBuffer.append(TEXT_187);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_188);
    
		}

    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_191);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_193);
    
} else if(tKafkaInputUtil.isKafkaVersion("KAFKA_0_10_0_1")) {

    stringBuffer.append(TEXT_194);
    
	// This is the tKafkaInput_begin javajet part for Kafka 0.9.0.x

	// Since the new Consumer API was introduced in Kafka 0.9.0.x, we must split the javajets
	// in order to generate code with the relevant Consumer API depending of the current Kafka version.

    stringBuffer.append(TEXT_195);
    
String outStructName = tKafkaInputUtil.getOutStructName();
TSetKeystoreUtil tSetKeystoreUtil = tKafkaInputUtil.getTSetKeystoreUtil();

    stringBuffer.append(TEXT_196);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_197);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_198);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_200);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_201);
    
		if("STRING".equals(tKafkaInputUtil.getOutputType())) {

    stringBuffer.append(TEXT_202);
    stringBuffer.append(tKafkaInputUtil.getOutgoingColumnName());
    stringBuffer.append(TEXT_203);
    
		} else if("BYTES".equals(tKafkaInputUtil.getOutputType())) {

    stringBuffer.append(TEXT_204);
    stringBuffer.append(tKafkaInputUtil.getOutgoingColumnName());
    stringBuffer.append(TEXT_205);
    
		}

    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_208);
    
// Basic and advanced configuration
for (java.util.Map.Entry<String, String> kafkaProperty : tKafkaInputUtil.getKafkaConsumerProperties().entrySet()) {

    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_210);
    stringBuffer.append(kafkaProperty.getKey());
    stringBuffer.append(TEXT_211);
    stringBuffer.append(kafkaProperty.getValue());
    stringBuffer.append(TEXT_212);
    
}

// SSL configuration
if (tSetKeystoreUtil.useHTTPS()) {

    stringBuffer.append(TEXT_213);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(tSetKeystoreUtil.getTrustStoreType());
    stringBuffer.append(TEXT_215);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_216);
    stringBuffer.append(tSetKeystoreUtil.getTrustStorePath());
    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(tSetKeystoreUtil.getTrustStorePassword());
    stringBuffer.append(TEXT_219);
    
	if (tSetKeystoreUtil.needClientAuth()) {

    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(tSetKeystoreUtil.getKeyStoreType());
    stringBuffer.append(TEXT_222);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_223);
    stringBuffer.append(tSetKeystoreUtil.getKeyStorePath());
    stringBuffer.append(TEXT_224);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_225);
    stringBuffer.append(tSetKeystoreUtil.getKeyStorePassword());
    stringBuffer.append(TEXT_226);
    
	}
}

// Kerberos configuration
if (tKafkaInputUtil.useKrb()) {

    stringBuffer.append(TEXT_227);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_228);
    stringBuffer.append(tKafkaInputUtil.getKrbServiceName());
    stringBuffer.append(TEXT_229);
    
	if (tKafkaInputUtil.isSetKinitPath()) {

    stringBuffer.append(TEXT_230);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_231);
    stringBuffer.append(tKafkaInputUtil.getKinitPath());
    stringBuffer.append(TEXT_232);
    
	}

    stringBuffer.append(TEXT_233);
    stringBuffer.append(tKafkaInputUtil.getJAASConf());
    stringBuffer.append(TEXT_234);
    
	if (tKafkaInputUtil.isSetKrb5Conf()) {

    stringBuffer.append(TEXT_235);
    stringBuffer.append(tKafkaInputUtil.getKrb5Conf());
    stringBuffer.append(TEXT_236);
    
	}
}

    stringBuffer.append(TEXT_237);
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
    stringBuffer.append(tKafkaInputUtil.getEncoding());
    stringBuffer.append(TEXT_244);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_245);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_246);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_247);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_248);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_249);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(TEXT_253);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_254);
    stringBuffer.append(tKafkaInputUtil.getTopic());
    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_257);
    
if (tKafkaInputUtil.isResetNewConsumerGroup()) {

    stringBuffer.append(TEXT_258);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    
	if ("earliest".equals(tKafkaInputUtil.getAutoOffsetResetNew())) {

    stringBuffer.append(TEXT_262);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_263);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_264);
    
	} else if ("latest".equals(tKafkaInputUtil.getAutoOffsetResetNew())) {

    stringBuffer.append(TEXT_265);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_266);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_267);
    
	}

	if (!tKafkaInputUtil.isAutoCommitOffset()) {

    stringBuffer.append(TEXT_268);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_269);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_270);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_271);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_272);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_273);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_274);
    
	}
}

// Save the global deadline for all messages.
if (tKafkaInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_275);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_276);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_277);
    stringBuffer.append(tKafkaInputUtil.getAsLong(tKafkaInputUtil.getMaxDuration()));
    stringBuffer.append(TEXT_278);
    
}

    stringBuffer.append(TEXT_279);
    
		if (tKafkaInputUtil.isStopOnMaxMsgWait()) {
			if (tKafkaInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_280);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_281);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_282);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_283);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_284);
    stringBuffer.append(tKafkaInputUtil.getAsLong(tKafkaInputUtil.getMaxMsgWait()));
    stringBuffer.append(TEXT_285);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_286);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_287);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_288);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_289);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_290);
    
			} else {

    stringBuffer.append(TEXT_291);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_293);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_294);
    stringBuffer.append(tKafkaInputUtil.getAsLong(tKafkaInputUtil.getMaxMsgWait()));
    stringBuffer.append(TEXT_295);
    
			}
		} else if (tKafkaInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_296);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_297);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_298);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_300);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_301);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_302);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_303);
    
		} else {

    stringBuffer.append(TEXT_304);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_305);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_306);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_307);
    stringBuffer.append(tKafkaInputUtil.getConsumerTimeout());
    stringBuffer.append(TEXT_308);
    
		}

		if (tKafkaInputUtil.isStopOnMaxMsgWait()) {

    stringBuffer.append(TEXT_309);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_310);
    
		}

    stringBuffer.append(TEXT_311);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_312);
    stringBuffer.append(outStructName);
    stringBuffer.append(TEXT_313);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_314);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_315);
    
}

    stringBuffer.append(TEXT_316);
    return stringBuffer.toString();
  }
}
