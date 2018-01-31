package org.talend.designer.codegen.translators.bigdata.couchbase;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TCouchbaseCloseMainJava
{
  protected static String nl;
  public static synchronized TCouchbaseCloseMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchbaseCloseMainJava result = new TCouchbaseCloseMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL + "\tcom.couchbase.client.CouchbaseClient client_";
  protected final String TEXT_3 = " = (com.couchbase.client.CouchbaseClient)globalMap.get(\"";
  protected final String TEXT_4 = "\");" + NL + "    if(client_";
  protected final String TEXT_5 = " != null){" + NL + "        client_";
  protected final String TEXT_6 = ".shutdown();" + NL + "    }" + NL + "\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();

    String cid = node.getUniqueName();

    String connection = ElementParameterParser.getValue(node,"__CONNECTION__");

    String conn = "conn_" + connection;
    
    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(conn);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
