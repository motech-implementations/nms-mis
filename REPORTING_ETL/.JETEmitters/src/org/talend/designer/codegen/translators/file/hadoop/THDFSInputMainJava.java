package org.talend.designer.codegen.translators.file.hadoop;

import org.talend.core.model.process.INode;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.process.IConnectionCategory;
import java.util.List;

public class THDFSInputMainJava
{
  protected static String nl;
  public static synchronized THDFSInputMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THDFSInputMainJava result = new THDFSInputMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_3 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_4 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_5 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_6 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_7 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_8 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_9 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_10 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_11 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_12 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_13 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_14 = " - Written records count: \" + nb_line_";
  protected final String TEXT_15 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_16 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_17 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_18 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_19 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_20 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_21 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_22 = " - Writing the record \" + nb_line_";
  protected final String TEXT_23 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_24 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_25 = " - Processing the record \" + nb_line_";
  protected final String TEXT_26 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_27 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_28 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_29 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_30 = NL;
  protected final String TEXT_31 = NL;
  protected final String TEXT_32 = " = new ";
  protected final String TEXT_33 = "Struct();";
  protected final String TEXT_34 = NL + "\t\t\t";
  protected final String TEXT_35 = NL + "\t\t" + NL + "\t\t";
  protected final String TEXT_36 = ".";
  protected final String TEXT_37 = " = ";
  protected final String TEXT_38 = "key_";
  protected final String TEXT_39 = ".";
  protected final String TEXT_40 = ";" + NL + "\t\t";
  protected final String TEXT_41 = ".";
  protected final String TEXT_42 = " = ";
  protected final String TEXT_43 = "value_";
  protected final String TEXT_44 = ".";
  protected final String TEXT_45 = ";";
  protected final String TEXT_46 = NL;
  protected final String TEXT_47 = ".";
  protected final String TEXT_48 = " = fid_";
  protected final String TEXT_49 = ".get(";
  protected final String TEXT_50 = ");";
  protected final String TEXT_51 = NL + "if(fid_";
  protected final String TEXT_52 = ".get(";
  protected final String TEXT_53 = ")!=null && fid_";
  protected final String TEXT_54 = ".get(";
  protected final String TEXT_55 = ").length() > 0) {";
  protected final String TEXT_56 = NL + "\t\t";
  protected final String TEXT_57 = ".";
  protected final String TEXT_58 = " = ParserUtils.parseTo_Date(fid_";
  protected final String TEXT_59 = ".get(";
  protected final String TEXT_60 = "), ";
  protected final String TEXT_61 = ");";
  protected final String TEXT_62 = NL + "\t\t";
  protected final String TEXT_63 = ".";
  protected final String TEXT_64 = " = fid_";
  protected final String TEXT_65 = ".get(";
  protected final String TEXT_66 = ").getBytes(";
  protected final String TEXT_67 = ");";
  protected final String TEXT_68 = NL + "\t\t";
  protected final String TEXT_69 = ".";
  protected final String TEXT_70 = " = ParserUtils.parseTo_";
  protected final String TEXT_71 = "(fid_";
  protected final String TEXT_72 = ".get(";
  protected final String TEXT_73 = "));";
  protected final String TEXT_74 = NL + "}else{" + NL + "\t";
  protected final String TEXT_75 = ".";
  protected final String TEXT_76 = " = ";
  protected final String TEXT_77 = ";" + NL + "}";
  protected final String TEXT_78 = NL + "\t\tnb_line_";
  protected final String TEXT_79 = "++;";
  protected final String TEXT_80 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	//this util class use by set log4j debug paramters
	class DefaultLog4jFileUtil {
	
		INode node = null;
	    String cid = null;
 		boolean isLog4jEnabled = false;
 		String label = null;
 		
 		public DefaultLog4jFileUtil(){
 		}
 		public DefaultLog4jFileUtil(INode node) {
 			this.node = node;
 			this.cid = node.getUniqueName();
 			this.label = cid;
			this.isLog4jEnabled = ("true").equals(org.talend.core.model.process.ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
 		}
 		
 		public void setCid(String cid) {
 			this.cid = cid;
 		}
 		
		//for all tFileinput* components 
		public void startRetriveDataInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_3);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_4);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_7);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_9);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_10);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_13);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_15);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_18);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_20);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_21);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_24);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_26);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_27);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_29);
    
			}
		}
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    stringBuffer.append(TEXT_30);
     
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();

boolean customEncoding="true".equals( ElementParameterParser.getValue(node,"__CUSTOM_ENCODING__"));
String encoding = ElementParameterParser.getValue(node,"__ENCODING__");

List< ? extends IConnection> conns = node.getOutgoingSortedConnections();
String firstConnName = "";
if (conns!=null) {
	if (conns.size()>0) {
		IConnection conn = conns.get(0);
		if(conn!=null && conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)){
			firstConnName = conn.getName();

    stringBuffer.append(TEXT_31);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_33);
    
			}
		}
	}
	
	List<IMetadataTable> metadatas = node.getMetadataList();
	if ((metadatas!=null)&&(metadatas.size()>0) && firstConnName.length()>0) {
		IMetadataTable metadata = metadatas.get(0);
	
		String typeFile = ElementParameterParser.getValue(node,"__TYPEFILE__");
		if (typeFile.equals("SEQUENCE")) {
		
			String keyColumn = ElementParameterParser.getValue(node,"__KEYCOLUMN__");
			String valueColumn = ElementParameterParser.getValue(node,"__VALUECOLUMN__");
			
			List<IMetadataColumn> listColumns = metadata.getListColumns();
			String talendKeyClass = "";
			String talendValueClass = "";
			for (IMetadataColumn column : listColumns) {
				if (column.getLabel().equals(keyColumn)) {
					talendKeyClass = column.getTalendType();
				}
				if (column.getLabel().equals(valueColumn)) {
					talendValueClass = column.getTalendType();
				}
			}
			
			String hadoopVersion = null;
			String distribution = null;
			boolean useExistingConnection = "true".equals(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"));
			if(!useExistingConnection) {
				distribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
				hadoopVersion = ElementParameterParser.getValue(node, "__DB_VERSION__");
			} else {
				String connectionSid = ElementParameterParser.getValue(node, "__CONNECTION__");
				List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
				for(INode targetNode : nodes){
					if (targetNode.getUniqueName().equals(connectionSid)) {
						hadoopVersion = ElementParameterParser.getValue(targetNode, "__DB_VERSION__"); 
						distribution = ElementParameterParser.getValue(targetNode, "__DISTRIBUTION__");
						break;
					}
				}
			}

			org.talend.hadoop.distribution.component.HDFSComponent hdfsDistrib = null;
			try {
				hdfsDistrib = (org.talend.hadoop.distribution.component.HDFSComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(distribution, hadoopVersion);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
				return "";
			}
			boolean isCustom = hdfsDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

    stringBuffer.append(TEXT_34);
    
    String keyClass="org.apache.hadoop.io.Text";
    if (talendKeyClass.equals("id_Boolean")) keyClass="org.apache.hadoop.io.BooleanWritable";
    if (talendKeyClass.equals("id_Byte")) keyClass="org.apache.hadoop.io.ByteWritable";
    if (talendKeyClass.equals("id_byte[]")) keyClass="org.apache.hadoop.io.BytesWritable";
    if (talendKeyClass.equals("id_Double")) keyClass="org.apache.hadoop.io.DoubleWritable";
    if (talendKeyClass.equals("id_Float")) keyClass="org.apache.hadoop.io.FloatWritable";
    if (talendKeyClass.equals("id_Integer")) keyClass="org.apache.hadoop.io.IntWritable";
    if (talendKeyClass.equals("id_Long")) keyClass="org.apache.hadoop.io.LongWritable";
    if (talendKeyClass.equals("id_String")) keyClass="org.apache.hadoop.io.Text";
    if (talendKeyClass.equals("id_Short")) {
    	if(isCustom || hdfsDistrib.doSupportSequenceFileShortType()) {
    		keyClass="org.apache.hadoop.io.ShortWritable";
    	} else {
    		keyClass="org.apache.hadoop.io.IntWritable";
    	}
    }
    
    String valueClass="org.apache.hadoop.io.Text";
    if (talendValueClass.equals("id_Boolean")) valueClass="org.apache.hadoop.io.BooleanWritable";
    if (talendValueClass.equals("id_Byte")) valueClass="org.apache.hadoop.io.ByteWritable";
    if (talendValueClass.equals("id_byte[]")) valueClass="org.apache.hadoop.io.BytesWritable";
    if (talendValueClass.equals("id_Double")) valueClass="org.apache.hadoop.io.DoubleWritable";
    if (talendValueClass.equals("id_Float")) valueClass="org.apache.hadoop.io.FloatWritable";
    if (talendValueClass.equals("id_Integer")) valueClass="org.apache.hadoop.io.IntWritable";
    if (talendValueClass.equals("id_Long")) valueClass="org.apache.hadoop.io.LongWritable";
    if (talendValueClass.equals("id_String")) valueClass="org.apache.hadoop.io.Text";
    if (talendValueClass.equals("id_Short")) {
    	if(isCustom || hdfsDistrib.doSupportSequenceFileShortType()) {
    		valueClass="org.apache.hadoop.io.ShortWritable";
    	} else {
    		valueClass="org.apache.hadoop.io.IntWritable";
    	}
    }

    stringBuffer.append(TEXT_35);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_36);
    stringBuffer.append(keyColumn );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(talendKeyClass.equals("id_Short")?"(short)":"");
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(((keyClass.equals("org.apache.hadoop.io.Text"))?"toString()":"get()") );
    stringBuffer.append(TEXT_40);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(valueColumn );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(talendValueClass.equals("id_Short")?"(short)":"");
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(((valueClass.equals("org.apache.hadoop.io.Text"))?"toString()":"get()") );
    stringBuffer.append(TEXT_45);
    
		} else {
			if (metadata!=null) {
				List<IMetadataColumn> listColumns = metadata.getListColumns();
				for (int valueN = 0; valueN < listColumns.size(); valueN++) {
					IMetadataColumn column = listColumns.get(valueN);

					String typeToGenerate = JavaTypesManager.getTypeToGenerate(column.getTalendType(), column.isNullable());
					JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
					String patternValue = column.getPattern() == null || column.getPattern().trim().length() == 0 ? null : column.getPattern();
					if(javaType == JavaTypesManager.STRING || javaType == JavaTypesManager.OBJECT) {

    stringBuffer.append(TEXT_46);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_47);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_49);
    stringBuffer.append(valueN );
    stringBuffer.append(TEXT_50);
    
					} else {

    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_52);
    stringBuffer.append(valueN );
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_54);
    stringBuffer.append(valueN );
    stringBuffer.append(TEXT_55);
    
						if (javaType == JavaTypesManager.DATE) {

    stringBuffer.append(TEXT_56);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_57);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_59);
    stringBuffer.append(valueN );
    stringBuffer.append(TEXT_60);
    stringBuffer.append( patternValue );
    stringBuffer.append(TEXT_61);
    
						} else if(javaType == JavaTypesManager.BYTE_ARRAY) {

    stringBuffer.append(TEXT_62);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_63);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_65);
    stringBuffer.append(valueN );
    stringBuffer.append(TEXT_66);
    stringBuffer.append((customEncoding?encoding:"utf8Charset"));
    stringBuffer.append(TEXT_67);
    
						} else {

    stringBuffer.append(TEXT_68);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_69);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_70);
    stringBuffer.append(typeToGenerate );
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_72);
    stringBuffer.append(valueN );
    stringBuffer.append(TEXT_73);
    
						}

    stringBuffer.append(TEXT_74);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_75);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate));
    stringBuffer.append(TEXT_77);
    
					}
				} // for
			}
		}
		

    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_79);
    
		log4jFileUtil.debugRetriveData(node);
	}

    stringBuffer.append(TEXT_80);
    return stringBuffer.toString();
  }
}
