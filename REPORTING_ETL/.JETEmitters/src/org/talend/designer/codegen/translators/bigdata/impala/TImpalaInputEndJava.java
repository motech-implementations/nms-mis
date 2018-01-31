package org.talend.designer.codegen.translators.bigdata.impala;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import java.util.List;
import java.util.Map;

public class TImpalaInputEndJava
{
  protected static String nl;
  public static synchronized TImpalaInputEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TImpalaInputEndJava result = new TImpalaInputEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t}" + NL + "\t} finally {" + NL + "\t}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	CodeGeneratorArgument inputCodeGenArgument = (CodeGeneratorArgument) argument;
	INode inputNode = (INode)inputCodeGenArgument.getArgument();
	String inputCid = inputNode.getUniqueName();

    stringBuffer.append(TEXT_1);
    return stringBuffer.toString();
  }
}
