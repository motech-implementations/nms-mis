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

public class TMapRStreamsInputEndJava
{
  protected static String nl;
  public static synchronized TMapRStreamsInputEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMapRStreamsInputEndJava result = new TMapRStreamsInputEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + "\t\t} // end for ConsumerRecord";
  protected final String TEXT_5 = NL + "\t\t// Stop reading if the maximum number of input messages has been reached." + NL + "\t\tif (tos_count_";
  protected final String TEXT_6 = " >= ";
  protected final String TEXT_7 = ") {" + NL + "\t\t    break;" + NL + "\t\t}";
  protected final String TEXT_8 = NL + "    // Immediately stop processing messagesDeadline if the max duration deadline has passed." + NL + "    if (System.currentTimeMillis() > maxDurationDeadline_";
  protected final String TEXT_9 = ")" + NL + "        break;";
  protected final String TEXT_10 = NL + "    } catch (org.apache.kafka.common.KafkaException e) {" + NL + "\t\t// Unexpected exception" + NL + "\t\te.printStackTrace();" + NL + "\t\tbreak;" + NL + "\t}" + NL + "} // end while(true)";
  protected final String TEXT_11 = NL;

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
    
	// This is the tMapRStreamsInput_end javajet part for any MapR Streams version based on Kafka 0.9.0.x
	
	// Since the new Consumer API was introduced in Kafka 0.9.0.x, we must split the javajets
	// in order to generate code with the relevant Consumer API depending of the current Kafka version.

    stringBuffer.append(TEXT_4);
    

	// Stopping conditions for the loop.
	if (tMapRStreamsInputUtil.isStopOnMaxSize()) {

    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(tMapRStreamsInputUtil.getMaxSize());
    stringBuffer.append(TEXT_7);
    
	}
	
	// Save the global deadline for all messages.
	if (tMapRStreamsInputUtil.isStopOnMaxDuration()) {

    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    
	}

// Close the loop scope.

    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    return stringBuffer.toString();
  }
}
