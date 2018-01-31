package org.talend.designer.codegen.translators.bigdata.impala;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.metadata.MappingTypeRetriever;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class TImpalaRowEndJava
{
  protected static String nl;
  public static synchronized TImpalaRowEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TImpalaRowEndJava result = new TImpalaRowEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\tpstmt_";
  protected final String TEXT_2 = ".close();\t";
  protected final String TEXT_3 = NL + "\t\tstmt_";
  protected final String TEXT_4 = ".close();\t";
  protected final String TEXT_5 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_6 = " - Closing the connection to the database.\");";
  protected final String TEXT_7 = NL + "\t\tconn_";
  protected final String TEXT_8 = " .close();";
  protected final String TEXT_9 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_10 = " - Connection to the database closed.\");";
  protected final String TEXT_11 = NL + "\t\tlog.info(\"";
  protected final String TEXT_12 = " - Done.\");";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	CodeGeneratorArgument loadCodeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)loadCodeGenArgument.getArgument();
	final String cid = node.getUniqueName();
	
	boolean isLog4jEnabled = "true".equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	boolean usePrepareStatement = "true".equals(ElementParameterParser.getValue(node,"__USE_PREPAREDSTATEMENT__"));
	boolean useExistingConn = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
	
	if (usePrepareStatement) {

    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_2);
    
	} else {

    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_4);
    
	}
	if(!useExistingConn) {
		if(isLog4jEnabled) {

    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    
		}

    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_8);
    
		if(isLog4jEnabled) {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    
		}
	}

	if(isLog4jEnabled) {

    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    
	}

    return stringBuffer.toString();
  }
}
