package org.talend.designer.codegen.translators.processing.hadoop;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataColumn;
import java.util.List;
import java.util.Map;

public class TPigAggregateMainJava
{
  protected static String nl;
  public static synchronized TPigAggregateMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TPigAggregateMainJava result = new TPigAggregateMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\t";
  protected final String TEXT_3 = NL + "\t";
  protected final String TEXT_4 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_5 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_6 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_7 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_8 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_10 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_11 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_12 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_13 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_14 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_15 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_16 = " - Written records count: \" + nb_line_";
  protected final String TEXT_17 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_18 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_19 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_21 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_22 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_23 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_24 = " - Writing the record \" + nb_line_";
  protected final String TEXT_25 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_26 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_27 = " - Processing the record \" + nb_line_";
  protected final String TEXT_28 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_29 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_30 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_31 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_32 = NL + NL + "\tStringBuilder sb_";
  protected final String TEXT_33 = " = new StringBuilder();";
  protected final String TEXT_34 = NL + "\t\troutines.system.PigHelper helper_";
  protected final String TEXT_35 = " = new routines.system.PigHelper();" + NL + "\t" + NL + "\t\tsb_";
  protected final String TEXT_36 = ".append(\"";
  protected final String TEXT_37 = "_GROUP = GROUP ";
  protected final String TEXT_38 = "_";
  protected final String TEXT_39 = "_RESULT \");";
  protected final String TEXT_40 = NL + "\t\t\tsb_";
  protected final String TEXT_41 = ".append(\"BY (\");";
  protected final String TEXT_42 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_43 = ".append(\",\");";
  protected final String TEXT_44 = NL + "\t\t\t\tsb_";
  protected final String TEXT_45 = ".append(\"";
  protected final String TEXT_46 = "\");";
  protected final String TEXT_47 = NL + "\t\t\tsb_";
  protected final String TEXT_48 = ".append(\")\");";
  protected final String TEXT_49 = NL + "\t\t\tsb_";
  protected final String TEXT_50 = ".append(\"ALL\");";
  protected final String TEXT_51 = NL + "\t\t\tsb_";
  protected final String TEXT_52 = ".append(\" PARALLEL \"+";
  protected final String TEXT_53 = ");";
  protected final String TEXT_54 = "\t\t" + NL + "\t\tsb_";
  protected final String TEXT_55 = ".append(\";\");" + NL + "\t\t" + NL + "\t\thelper_";
  protected final String TEXT_56 = ".add(\"query\",sb_";
  protected final String TEXT_57 = ".toString());" + NL + "" + NL + "\t\tsb_";
  protected final String TEXT_58 = " = new StringBuilder();" + NL + "\t\tsb_";
  protected final String TEXT_59 = ".append(\"";
  protected final String TEXT_60 = "_";
  protected final String TEXT_61 = "_RESULT = FOREACH ";
  protected final String TEXT_62 = "_GROUP GENERATE \");" + NL + "" + NL + "\t\t";
  protected final String TEXT_63 = NL + "\t            sb_";
  protected final String TEXT_64 = ".append(\",\");" + NL + "\t            ";
  protected final String TEXT_65 = NL + "\t                sb_";
  protected final String TEXT_66 = ".append(\" ";
  protected final String TEXT_67 = "(";
  protected final String TEXT_68 = "_";
  protected final String TEXT_69 = "_RESULT.";
  protected final String TEXT_70 = ") AS ";
  protected final String TEXT_71 = "\");" + NL + "            \t\t";
  protected final String TEXT_72 = NL + "\t                    sb_";
  protected final String TEXT_73 = ".append(\"group";
  protected final String TEXT_74 = " AS ";
  protected final String TEXT_75 = "\");" + NL + "\t                    ";
  protected final String TEXT_76 = NL + "\t\tsb_";
  protected final String TEXT_77 = ".append(\";\");" + NL + "\t\t" + NL + "\t\thelper_";
  protected final String TEXT_78 = ".add(\"query\",sb_";
  protected final String TEXT_79 = ".toString());" + NL + "\t\t" + NL + "\t\t";
  protected final String TEXT_80 = NL + "\tjava.util.List<String[]> pigLatins_";
  protected final String TEXT_81 = " = helper_";
  protected final String TEXT_82 = ".getPigLatins();";
  protected final String TEXT_83 = NL + "\t\tfor(String[] pigLatin_";
  protected final String TEXT_84 = " : pigLatins_";
  protected final String TEXT_85 = ") {" + NL + "\t\t\tString type_";
  protected final String TEXT_86 = " = pigLatin_";
  protected final String TEXT_87 = "[0];" + NL + "\t\t\t";
  protected final String TEXT_88 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_89 = " - register \" + type_";
  protected final String TEXT_90 = " + \" : \" + pigLatin_";
  protected final String TEXT_91 = "[1]);" + NL + "\t\t\t";
  protected final String TEXT_92 = NL + "\t\t\tif(\"query\".equals(type_";
  protected final String TEXT_93 = ")) {";
  protected final String TEXT_94 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_95 = ".write(pigLatin_";
  protected final String TEXT_96 = "[1]);";
  protected final String TEXT_97 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_98 = ".registerQuery(pigLatin_";
  protected final String TEXT_99 = "[1]);";
  protected final String TEXT_100 = NL + "\t\t\t} else if(\"jar\".equals(type_";
  protected final String TEXT_101 = ")) {";
  protected final String TEXT_102 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_103 = ".write(\"REGISTER \" + wasbPath_";
  protected final String TEXT_104 = " + new java.io.File(pigLatin_";
  protected final String TEXT_105 = "[1]).getName() + \";\");" + NL + "\t\t\t\t\tlibjars_";
  protected final String TEXT_106 = ".append(pigLatin_";
  protected final String TEXT_107 = "[1] + \",\");";
  protected final String TEXT_108 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_109 = ".registerJar(pigLatin_";
  protected final String TEXT_110 = "[1]);";
  protected final String TEXT_111 = NL + "\t\t\t} else if(\"script\".equals(type_";
  protected final String TEXT_112 = ")) {";
  protected final String TEXT_113 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_114 = ".write(pigLatin_";
  protected final String TEXT_115 = "[1]);";
  protected final String TEXT_116 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_117 = ".registerScript(new java.io.ByteArrayInputStream(pigLatin_";
  protected final String TEXT_118 = "[1].getBytes()));";
  protected final String TEXT_119 = NL + "\t\t\t} else if(\"function\".equals(type_";
  protected final String TEXT_120 = ")) {";
  protected final String TEXT_121 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_122 = ".write(\"DEFINE \" + pigLatin_";
  protected final String TEXT_123 = "[1] + \" \" + pigLatin_";
  protected final String TEXT_124 = "[2] + \";\");";
  protected final String TEXT_125 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_126 = ".registerFunction(pigLatin_";
  protected final String TEXT_127 = "[1], new org.apache.pig.FuncSpec(pigLatin_";
  protected final String TEXT_128 = "[2]));";
  protected final String TEXT_129 = NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_130 = NL + "    \tpigScript_";
  protected final String TEXT_131 = ".addAll(pigLatins_";
  protected final String TEXT_132 = ");";
  protected final String TEXT_133 = NL + "\tpigLatins_";
  protected final String TEXT_134 = ".clear();";
  protected final String TEXT_135 = NL + "\t\t" + NL + "\t\t" + NL + "\t\t";
  protected final String TEXT_136 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();
	String cid = node.getUniqueName();
	
	String previous_node="";
	String start_node="";
	
	boolean inMain = true;
	boolean isExecutedThroughWebHCat = false;

	String previousOutputConnectionName = "";
	
	if(node.getIncomingConnections()!=null && node.getIncomingConnections().size()>0) {
		IConnection connection = node.getIncomingConnections().get(0);
		previous_node = connection.getSource().getUniqueName();
		INode loadNode = node.getDesignSubjobStartNode();
		start_node = loadNode.getUniqueName();
		
		boolean isLocal = "true".equals(ElementParameterParser.getValue(loadNode, "__LOCAL__"));

        if(!isLocal) {
            String loadDistribution = ElementParameterParser.getValue(loadNode, "__DISTRIBUTION__");
            String loadPigVersion = ElementParameterParser.getValue(loadNode, "__PIG_VERSION__");
            org.talend.hadoop.distribution.component.PigComponent pigDistrib = null;
            try {
                pigDistrib = (org.talend.hadoop.distribution.component.PigComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(loadDistribution, loadPigVersion);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
                return "";
            }

            boolean isCustom = pigDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;
            isExecutedThroughWebHCat = !isCustom && pigDistrib != null && pigDistrib.isExecutedThroughWebHCat();
        }
		
		inMain = loadNode.isSubtreeStart();
		previousOutputConnectionName = connection.getName();
	}
	
	String outputConnectionName = "";
	List<IMetadataColumn> outputMetadataColumns = null;
	List<IConnection> outputConnections = (List<IConnection>)node.getOutgoingConnections();
	if(outputConnections!=null && outputConnections.size()>0) {
		outputConnectionName = outputConnections.get(0).getName();
		outputMetadataColumns = outputConnections.get(0).getMetadataTable().getListColumns();
	}
	
	List<Map<String, String>> key_fields = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__KEY_FIELDS__");
	List<Map<String, String>> operations_config = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__OPERATIONS__");

	
	boolean useParallel = "true".equals(ElementParameterParser.getValue(node, "__INCREASE_PARALLELISM__"));
	String reduce_number = ElementParameterParser.getValue(node, "__REDUCE_NUMBER__");

    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    
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
			
    stringBuffer.append(TEXT_4);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_6);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_9);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_11);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_12);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_15);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_17);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_20);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_22);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_23);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_25);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_26);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_29);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_31);
    
			}
		}
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    
	log4jFileUtil.componentStartInfo(node);
	final boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    
	if(operations_config != null && operations_config.size()>0) {

    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(previous_node);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(previousOutputConnectionName);
    stringBuffer.append(TEXT_39);
    
		if(key_fields != null && key_fields.size()>0) {

    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    
			boolean isFirst = true;
    		for(Map<String, String> key_field : key_fields) {
    			if(!isFirst) {

    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    	
				}
				isFirst=false;

    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(key_field.get("INPUT_COLUMN"));
    stringBuffer.append(TEXT_46);
    
			}

    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    
		} else {

    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    
		}
		
		if(useParallel) {

    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(reduce_number);
    stringBuffer.append(TEXT_53);
    
		}
		

    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
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
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    
        Boolean notFirst = false;
		for (IMetadataColumn outputColumn: outputMetadataColumns) {
            boolean isOperationConfig = false;
		    if (notFirst) {
		        
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    
		    } else {
		        notFirst = true;
		    }
		    for (Map<String, String> operationConfig: operations_config) {
		        if (operationConfig.get("OUTPUT_COLUMN").equals(outputColumn.getLabel())) {
		            
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(operationConfig.get("FUNCTION"));
    stringBuffer.append(TEXT_67);
    stringBuffer.append(previous_node);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(previousOutputConnectionName);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(operationConfig.get("INPUT_COLUMN"));
    stringBuffer.append(TEXT_70);
    stringBuffer.append(operationConfig.get("OUTPUT_COLUMN"));
    stringBuffer.append(TEXT_71);
    
            		isOperationConfig = true;
		        }
		    }
		    if (!isOperationConfig) {
	            for(Map<String, String> keyField : key_fields) {
	                if (keyField.get("INPUT_COLUMN").equals(outputColumn.getLabel())) {
	                    
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(key_fields.size()>1?"."+keyField.get("INPUT_COLUMN"):"");
    stringBuffer.append(TEXT_74);
    stringBuffer.append(keyField.get("INPUT_COLUMN"));
    stringBuffer.append(TEXT_75);
    
	                }
                }
		    }
		}
	

    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    
	if(inMain) {

    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    }
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_94);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    					
				} else {

    stringBuffer.append(TEXT_97);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    
				}

    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_102);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    					
				} else {

    stringBuffer.append(TEXT_108);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    
				}

    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_113);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    					
				} else {

    stringBuffer.append(TEXT_116);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    
				}

    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_121);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    					
				} else {

    stringBuffer.append(TEXT_125);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    
				}

    stringBuffer.append(TEXT_129);
    
	} else {

    stringBuffer.append(TEXT_130);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    
	}

    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    
	}
	

    stringBuffer.append(TEXT_135);
    stringBuffer.append(TEXT_136);
    return stringBuffer.toString();
  }
}
