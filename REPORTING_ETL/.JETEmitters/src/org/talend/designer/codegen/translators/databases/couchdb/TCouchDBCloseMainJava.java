package org.talend.designer.codegen.translators.databases.couchdb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TCouchDBCloseMainJava
{
  protected static String nl;
  public static synchronized TCouchDBCloseMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TCouchDBCloseMainJava result = new TCouchDBCloseMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t";
  protected final String TEXT_2 = NL + "\torg.lightcouch.CouchDbClient client_";
  protected final String TEXT_3 = " = (org.lightcouch.CouchDbClient)globalMap.get(\"client_";
  protected final String TEXT_4 = "\");" + NL + "\tif(client_";
  protected final String TEXT_5 = " != null){" + NL + "\t\tList<org.lightcouch.Replication> replications_";
  protected final String TEXT_6 = " = (List<org.lightcouch.Replication>)globalMap.get(\"replications_";
  protected final String TEXT_7 = "\");" + NL + "\t\tif(replications_";
  protected final String TEXT_8 = " != null && replications_";
  protected final String TEXT_9 = ".size()>0){" + NL + "\t\t\tfor(org.lightcouch.Replication replication_";
  protected final String TEXT_10 = " : replications_";
  protected final String TEXT_11 = "){" + NL + "\t\t\t\treplication_";
  protected final String TEXT_12 = ".trigger();" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\tclient_";
  protected final String TEXT_13 = ".shutdown();" + NL + "\t}" + NL + "\t";
  protected final String TEXT_14 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();

    String cid = node.getUniqueName();

   	String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
	
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
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
    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
}
