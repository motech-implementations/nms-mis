package org.talend.designer.codegen.translators.internet.maprstreams;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class TMapRStreamsCreateStreamBeginJava
{
  protected static String nl;
  public static synchronized TMapRStreamsCreateStreamBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMapRStreamsCreateStreamBeginJava result = new TMapRStreamsCreateStreamBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + NL + "org.apache.hadoop.conf.Configuration conf_";
  protected final String TEXT_5 = " = (org.apache.hadoop.conf.Configuration) globalMap.get(\"conf_";
  protected final String TEXT_6 = "\");" + NL + "com.mapr.streams.Admin admin_";
  protected final String TEXT_7 = " = com.mapr.streams.Streams.newAdmin(conf_";
  protected final String TEXT_8 = ");" + NL;
  protected final String TEXT_9 = NL + "\t";
  protected final String TEXT_10 = "try {" + NL + "\tcom.mapr.streams.StreamDescriptor descriptor_";
  protected final String TEXT_11 = " = com.mapr.streams.Streams.newStreamDescriptor();" + NL + "\tdescriptor_";
  protected final String TEXT_12 = ".setAdminPerms(";
  protected final String TEXT_13 = ");" + NL + "\tdescriptor_";
  protected final String TEXT_14 = ".setConsumePerms(";
  protected final String TEXT_15 = ");" + NL + "\tdescriptor_";
  protected final String TEXT_16 = ".setCopyPerms(";
  protected final String TEXT_17 = ");" + NL + "\tdescriptor_";
  protected final String TEXT_18 = ".setProducePerms(";
  protected final String TEXT_19 = ");" + NL + "\tdescriptor_";
  protected final String TEXT_20 = ".setTopicPerms(";
  protected final String TEXT_21 = ");" + NL + "\tdescriptor_";
  protected final String TEXT_22 = ".setCompressionAlgo(\"";
  protected final String TEXT_23 = "\");" + NL + "\tdescriptor_";
  protected final String TEXT_24 = ".setDefaultPartitions(";
  protected final String TEXT_25 = ");" + NL + "\tdescriptor_";
  protected final String TEXT_26 = ".setTimeToLiveSec(";
  protected final String TEXT_27 = ");" + NL + "\tdescriptor_";
  protected final String TEXT_28 = ".setAutoCreateTopics(";
  protected final String TEXT_29 = ");\t" + NL + "\tadmin_";
  protected final String TEXT_30 = ".createStream(";
  protected final String TEXT_31 = ", descriptor_";
  protected final String TEXT_32 = ");" + NL + "} catch (com.mapr.db.exceptions.TableExistsException e) {";
  protected final String TEXT_33 = NL + "\t\t// nothing to do even through the stream already exists, continue the job";
  protected final String TEXT_34 = NL + "\t\t// stream already exists, stop the job" + NL + "\t\tthrow e;";
  protected final String TEXT_35 = NL + "} finally {" + NL + "\tadmin_";
  protected final String TEXT_36 = ".close();" + NL + "}";
  protected final String TEXT_37 = NL + "\t";
  protected final String TEXT_38 = "try {" + NL + "\tcom.mapr.streams.StreamDescriptor descriptor_";
  protected final String TEXT_39 = " = admin_";
  protected final String TEXT_40 = ".getStreamDescriptor(";
  protected final String TEXT_41 = ");";
  protected final String TEXT_42 = NL + "\t\tdescriptor_";
  protected final String TEXT_43 = ".setAdminPerms(";
  protected final String TEXT_44 = ");";
  protected final String TEXT_45 = NL + "\t\tdescriptor_";
  protected final String TEXT_46 = ".setConsumePerms(";
  protected final String TEXT_47 = ");";
  protected final String TEXT_48 = NL + "\t\tdescriptor_";
  protected final String TEXT_49 = ".setCopyPerms(";
  protected final String TEXT_50 = ");";
  protected final String TEXT_51 = NL + "\t\tdescriptor_";
  protected final String TEXT_52 = ".setProducePerms(";
  protected final String TEXT_53 = ");";
  protected final String TEXT_54 = NL + "\t\tdescriptor_";
  protected final String TEXT_55 = ".setTopicPerms(";
  protected final String TEXT_56 = ");";
  protected final String TEXT_57 = NL + "\t\tdescriptor_";
  protected final String TEXT_58 = ".setCompressionAlgo(\"";
  protected final String TEXT_59 = "\");";
  protected final String TEXT_60 = NL + "\t\tdescriptor_";
  protected final String TEXT_61 = ".setDefaultPartitions(";
  protected final String TEXT_62 = ");";
  protected final String TEXT_63 = NL + "\t\tdescriptor_";
  protected final String TEXT_64 = ".setTimeToLiveSec(";
  protected final String TEXT_65 = ");";
  protected final String TEXT_66 = NL + "\t\tdescriptor_";
  protected final String TEXT_67 = ".setAutoCreateTopics(";
  protected final String TEXT_68 = ");";
  protected final String TEXT_69 = NL + "\tadmin_";
  protected final String TEXT_70 = ".editStream(";
  protected final String TEXT_71 = ", descriptor_";
  protected final String TEXT_72 = ");" + NL + "}  finally {" + NL + "\tadmin_";
  protected final String TEXT_73 = ".close();" + NL + "}";
  protected final String TEXT_74 = NL + "\t";
  protected final String TEXT_75 = "try {";
  protected final String TEXT_76 = NL + "\t\tadmin_";
  protected final String TEXT_77 = ".createTopic(";
  protected final String TEXT_78 = ", ";
  protected final String TEXT_79 = ", ";
  protected final String TEXT_80 = ");";
  protected final String TEXT_81 = NL + "\t\tadmin_";
  protected final String TEXT_82 = ".createTopic(";
  protected final String TEXT_83 = ", ";
  protected final String TEXT_84 = ");";
  protected final String TEXT_85 = NL + "} catch (java.io.IOException e) {";
  protected final String TEXT_86 = NL + "\t\t// nothing to do even through the topic already exists, continue the job";
  protected final String TEXT_87 = NL + "\t\t// topic already exists, stop the job" + NL + "\t\tthrow e;";
  protected final String TEXT_88 = NL + "} finally {" + NL + "\tadmin_";
  protected final String TEXT_89 = ".close();" + NL + "}";
  protected final String TEXT_90 = NL + "\t";
  protected final String TEXT_91 = "try {" + NL + "\tadmin_";
  protected final String TEXT_92 = ".editTopic(";
  protected final String TEXT_93 = ", ";
  protected final String TEXT_94 = ", ";
  protected final String TEXT_95 = ");" + NL + "} finally {" + NL + "\tadmin_";
  protected final String TEXT_96 = ".close();" + NL + "}";
  protected final String TEXT_97 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    
class TMapRStreamsCreateStreamUtil {

	private INode node;
	
	private INode connNode;

	public TMapRStreamsCreateStreamUtil(INode node) {
		this.node = node;
		this.connNode = ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__");
	}
	
	public String getConnectionCid() {
		return this.connNode.getUniqueName();
	}

	public boolean isCreateStream() {
		return "CREATE_STREAM".equals(ElementParameterParser.getValue(node, "__ACTION__")) || isCreateStreamIfNotExists();
	}

	public boolean isCreateStreamIfNotExists() {
		return "CREATE_STREAM_IF_NOT_EXISTS".equals(ElementParameterParser.getValue(node, "__ACTION__"));
	}

	public boolean isAlterStream() {
		return "ALTER_STREAM".equals(ElementParameterParser.getValue(node, "__ACTION__"));
	}
	
	public boolean isCreateTopic() {
		return "CREATE_TOPIC".equals(ElementParameterParser.getValue(node, "__ACTION__")) || isCreateTopicIfNotExists();
	}

	public boolean isCreateTopicIfNotExists() {
		return "CREATE_TOPIC_IF_NOT_EXISTS".equals(ElementParameterParser.getValue(node, "__ACTION__"));
	}

	public boolean isAlterTopic() {
		return "ALTER_TOPIC".equals(ElementParameterParser.getValue(node, "__ACTION__"));
	}
	
	public String getStream() {
		return ElementParameterParser.getValue(node, "__STREAM__");
	}

	public boolean isAlterAdminPerms() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_ADMIN_PERMS__"));
	}
	
	public boolean isAlterConsumePerms() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_CONSUME_PERMS__"));
	}
	
	public boolean isAlterProducePerms() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_PRODUCE_PERMS__"));
	}
	
	public boolean isAlterCopyPerms() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_COPY_PERMS__"));
	}
	
	public boolean isAlterTopicPerms() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_TOPIC_PERMS__"));
	}
	
	public boolean isAlterAutoCreateTopics() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_AUTO_CREATE_TOPICS__"));
	}
	
	public boolean isAlterDefaultPartitions() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_DEFAULT_PARTITIONS__"));
	}
	
	public boolean isAlterTimeToLive() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_TIME_TO_LIVE__"));
	}
	
	public boolean isAlterCompression() {
		return "true".equals(ElementParameterParser.getValue(node, "__ALTER_COMPRESSION__"));
	}
	
	public String getAdminPerms() {
		return ElementParameterParser.getValue(node, "__STREAM_ADMIN_PERMS__");
	}
	
	public String getConsumePerms() {
		return ElementParameterParser.getValue(node, "__STREAM_CONSUME_PERMS__");
	}
	
	public String getProducePerms() {
		return ElementParameterParser.getValue(node, "__STREAM_PRODUCE_PERMS__");
	}
	
	public String getCopyPerms() {
		return ElementParameterParser.getValue(node, "__STREAM_COPY_PERMS__");
	}
	
	public String getTopicPerms() {
		return ElementParameterParser.getValue(node, "__STREAM_TOPIC_PERMS__");
	}
	
	public boolean isAutoCreateTopics() {
		return "true".equals(ElementParameterParser.getValue(node, "__STREAM_AUTO_CREATE_TOPICS__"));
	}
	
	public String getDefaultPartitions() {
		return ElementParameterParser.getValue(node, "__STREAM_DEFAULT_PARTITIONS__");
	}
	
	public String getTimeToLive() {
		return ElementParameterParser.getValue(node, "__STREAM_TIME_TO_LIVE__");
	}
	
	public String getCompression() {
		return ElementParameterParser.getValue(node, "__STREAM_COMPRESSION__").toLowerCase();
	}
	
	public String getTopic() {
		return ElementParameterParser.getValue(node, "__TOPIC__");
	}
	
	public boolean isSetNbPartitions() {
		return "true".equals(ElementParameterParser.getValue(node, "__SET_NB_PARTITIONS__"));
	}
	
	public String getNbPartitions() {
		return ElementParameterParser.getValue(node, "__TOPIC_NB_PARTITIONS__");
	}
}

    stringBuffer.append(TEXT_3);
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();

TMapRStreamsCreateStreamUtil tMapRStreamsCreateStreamUtil = new TMapRStreamsCreateStreamUtil(node);

    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getConnectionCid());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    
if (tMapRStreamsCreateStreamUtil.isCreateStream()) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getAdminPerms());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getConsumePerms());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getCopyPerms());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getProducePerms());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getTopicPerms());
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getCompression());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getDefaultPartitions());
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getTimeToLive());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.isAutoCreateTopics());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getStream());
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    
	if (tMapRStreamsCreateStreamUtil.isCreateStreamIfNotExists()) {

    stringBuffer.append(TEXT_33);
    
	} else {

    stringBuffer.append(TEXT_34);
    
	}

    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    
} else if (tMapRStreamsCreateStreamUtil.isAlterStream()) {

    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getStream());
    stringBuffer.append(TEXT_41);
    
	if (tMapRStreamsCreateStreamUtil.isAlterAdminPerms()) {

    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getAdminPerms());
    stringBuffer.append(TEXT_44);
    
	}
	if (tMapRStreamsCreateStreamUtil.isAlterConsumePerms()) {

    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getConsumePerms());
    stringBuffer.append(TEXT_47);
    
	}
	if (tMapRStreamsCreateStreamUtil.isAlterCopyPerms()) {

    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getCopyPerms());
    stringBuffer.append(TEXT_50);
    
	}
	if (tMapRStreamsCreateStreamUtil.isAlterProducePerms()) {

    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getProducePerms());
    stringBuffer.append(TEXT_53);
    
	}
	if (tMapRStreamsCreateStreamUtil.isAlterTopicPerms()) {

    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getTopicPerms());
    stringBuffer.append(TEXT_56);
    
	}
	if (tMapRStreamsCreateStreamUtil.isAlterCompression()) {

    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getCompression());
    stringBuffer.append(TEXT_59);
    
	}
	if (tMapRStreamsCreateStreamUtil.isAlterDefaultPartitions()) {

    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getDefaultPartitions());
    stringBuffer.append(TEXT_62);
    
	}
	if (tMapRStreamsCreateStreamUtil.isAlterTimeToLive()) {

    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getTimeToLive());
    stringBuffer.append(TEXT_65);
    
	}
	if (tMapRStreamsCreateStreamUtil.isAlterAutoCreateTopics()) {

    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.isAutoCreateTopics());
    stringBuffer.append(TEXT_68);
    
	}

    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getStream());
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    
} else if (tMapRStreamsCreateStreamUtil.isCreateTopic()) {

    stringBuffer.append(TEXT_74);
    stringBuffer.append(TEXT_75);
    
	if (tMapRStreamsCreateStreamUtil.isSetNbPartitions()) {

    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getStream());
    stringBuffer.append(TEXT_78);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getTopic());
    stringBuffer.append(TEXT_79);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getNbPartitions());
    stringBuffer.append(TEXT_80);
    
	} else {

    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getStream());
    stringBuffer.append(TEXT_83);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getTopic());
    stringBuffer.append(TEXT_84);
    
	}

    stringBuffer.append(TEXT_85);
    
	if (tMapRStreamsCreateStreamUtil.isCreateTopicIfNotExists()) {

    stringBuffer.append(TEXT_86);
    
	} else {

    stringBuffer.append(TEXT_87);
    
	}

    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    
} else if (tMapRStreamsCreateStreamUtil.isAlterTopic()) {

    stringBuffer.append(TEXT_90);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getStream());
    stringBuffer.append(TEXT_93);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getTopic());
    stringBuffer.append(TEXT_94);
    stringBuffer.append(tMapRStreamsCreateStreamUtil.getNbPartitions());
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    
}

    stringBuffer.append(TEXT_97);
    return stringBuffer.toString();
  }
}
