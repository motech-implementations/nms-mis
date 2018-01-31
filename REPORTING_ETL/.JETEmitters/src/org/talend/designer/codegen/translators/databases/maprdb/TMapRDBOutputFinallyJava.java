package org.talend.designer.codegen.translators.databases.maprdb;

import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.ElementParameterParser;

public class TMapRDBOutputFinallyJava
{
  protected static String nl;
  public static synchronized TMapRDBOutputFinallyJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMapRDBOutputFinallyJava result = new TMapRDBOutputFinallyJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL + "\t\tif(resourceMap.get(\"finish_";
  protected final String TEXT_3 = "\") == null){" + NL + "\t\t\tif(resourceMap.get(\"conn_";
  protected final String TEXT_4 = "\")!=null){" + NL + "\t\t\t\torg.apache.hadoop.conf.Configuration conn_";
  protected final String TEXT_5 = " = (org.apache.hadoop.conf.Configuration)resourceMap.get(\"conn_";
  protected final String TEXT_6 = "\");" + NL + "\t\t\t\t";
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = NL + "        org.apache.hadoop.hbase.client.HConnection hConnection_";
  protected final String TEXT_9 = " = org.apache.hadoop.hbase.client.HConnectionManager.getConnection(conn_";
  protected final String TEXT_10 = ");" + NL + "        if ((hConnection_";
  protected final String TEXT_11 = " != null) && (!hConnection_";
  protected final String TEXT_12 = ".isClosed())) {" + NL + "            hConnection_";
  protected final String TEXT_13 = ".close();" + NL + "        }";
  protected final String TEXT_14 = NL + "\t\torg.apache.hadoop.hbase.client.HConnectionManager.deleteConnection(conn_";
  protected final String TEXT_15 = ",true);";
  protected final String TEXT_16 = NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t";
  protected final String TEXT_17 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();
String dieOnError = ElementParameterParser.getValue(node, "__DIE_ON_ERROR__");
String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");

    stringBuffer.append(TEXT_1);
    
	//use existing connection
	if(!"true".equals(useExistingConn)){
		String distribution = ElementParameterParser.getValue(node,"__DISTRIBUTION__");
		String hbaseVersion = ElementParameterParser.getValue(node,"__HBASE_VERSION__");
        boolean isHadoop2 = "HADOOP_2".equals(ElementParameterParser.getValue(node, "__HADOOP_CUSTOM_VERSION__"));
		org.talend.hadoop.distribution.component.HBaseComponent hbaseDistrib = null;
		try {
			hbaseDistrib = (org.talend.hadoop.distribution.component.HBaseComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hbaseVersion);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			return "";
		}
		boolean isCustom = hbaseDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
		
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    

if((isCustom && isHadoop2) || (!isCustom && hbaseDistrib != null && hbaseDistrib.doSupportNewHBaseAPI())) {
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_13);
    } else {
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    }
    stringBuffer.append(TEXT_16);
    
	}
	
    stringBuffer.append(TEXT_17);
    return stringBuffer.toString();
  }
}
