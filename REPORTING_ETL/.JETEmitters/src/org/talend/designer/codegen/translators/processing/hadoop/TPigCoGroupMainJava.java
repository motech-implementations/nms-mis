package org.talend.designer.codegen.translators.processing.hadoop;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TPigCoGroupMainJava
{
  protected static String nl;
  public static synchronized TPigCoGroupMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TPigCoGroupMainJava result = new TPigCoGroupMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "\t";
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
  protected final String TEXT_32 = "\t" + NL + "\troutines.system.PigHelper helper_";
  protected final String TEXT_33 = " = new routines.system.PigHelper();" + NL + "\t";
  protected final String TEXT_34 = NL + "\t\t\tStringBuilder sb_";
  protected final String TEXT_35 = " = new StringBuilder();" + NL + "\t\t\t//the bag name of main input bag" + NL + "\t\t\thelper_";
  protected final String TEXT_36 = ".add(\"query\",\"";
  protected final String TEXT_37 = "_";
  protected final String TEXT_38 = "_RESULT = FOREACH ";
  protected final String TEXT_39 = "_";
  protected final String TEXT_40 = "_RESULT GENERATE *;\");";
  protected final String TEXT_41 = NL + "\t\t\t//get the scripts in the lookup process" + NL + "\t\t\tjava.util.List<String[]> ps_";
  protected final String TEXT_42 = " = (java.util.List<String[]>)globalMap.get(\"";
  protected final String TEXT_43 = "\");" + NL + "\t\t\t" + NL + "\t\t\tfor(int i_";
  protected final String TEXT_44 = "=0; i_";
  protected final String TEXT_45 = "<ps_";
  protected final String TEXT_46 = ".size(); i_";
  protected final String TEXT_47 = "++) {" + NL + "\t\t\t\tString[] o_";
  protected final String TEXT_48 = " = ps_";
  protected final String TEXT_49 = ".get(i_";
  protected final String TEXT_50 = ");" + NL + "\t\t\t\tif(o_";
  protected final String TEXT_51 = ".length > 2 && o_";
  protected final String TEXT_52 = "[2] != null) {" + NL + "\t\t\t\t\thelper_";
  protected final String TEXT_53 = ".add(o_";
  protected final String TEXT_54 = "[0],o_";
  protected final String TEXT_55 = "[1],o_";
  protected final String TEXT_56 = "[2]);" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\thelper_";
  protected final String TEXT_57 = ".add(o_";
  protected final String TEXT_58 = "[0],o_";
  protected final String TEXT_59 = "[1]);" + NL + "\t\t\t\t}" + NL + "\t\t\t\t" + NL + "\t\t\t\tif(i_";
  protected final String TEXT_60 = " == (ps_";
  protected final String TEXT_61 = ".size() - 1)) {//get the bag name of current lookup bag" + NL + "\t\t\t\t\tString bagName_";
  protected final String TEXT_62 = " = o_";
  protected final String TEXT_63 = "[1];" + NL + "\t\t\t\t\tbagName_";
  protected final String TEXT_64 = " = bagName_";
  protected final String TEXT_65 = ".substring(0,bagName_";
  protected final String TEXT_66 = ".indexOf(\"_RESULT\")+7);" + NL + "\t\t\t\t\thelper_";
  protected final String TEXT_67 = ".add(\"query\",\"";
  protected final String TEXT_68 = "_";
  protected final String TEXT_69 = "_RESULT = FOREACH \" + bagName_";
  protected final String TEXT_70 = " + \" GENERATE *;\");" + NL + "\t\t\t\t}" + NL + "\t\t\t}";
  protected final String TEXT_71 = NL + "\tsb_";
  protected final String TEXT_72 = ".append(\"";
  protected final String TEXT_73 = "_";
  protected final String TEXT_74 = "_RESULT = COGROUP\");";
  protected final String TEXT_75 = NL + "\t\t\tsb_";
  protected final String TEXT_76 = ".append(\" ";
  protected final String TEXT_77 = "_";
  protected final String TEXT_78 = "_RESULT \");";
  protected final String TEXT_79 = NL + "\t\t\tsb_";
  protected final String TEXT_80 = ".append(\"ALL \");";
  protected final String TEXT_81 = NL + "\t\t\tsb_";
  protected final String TEXT_82 = ".append(\"BY (\");";
  protected final String TEXT_83 = NL + "\t\t\t\tsb_";
  protected final String TEXT_84 = ".append(\"";
  protected final String TEXT_85 = "\");";
  protected final String TEXT_86 = NL + "\t\t\t\t\tsb_";
  protected final String TEXT_87 = ".append(\",\");";
  protected final String TEXT_88 = NL + "\t\t\tsb_";
  protected final String TEXT_89 = ".append(\")\");";
  protected final String TEXT_90 = NL + "\t\t\tsb_";
  protected final String TEXT_91 = ".append(\",\");";
  protected final String TEXT_92 = NL + "\t\tsb_";
  protected final String TEXT_93 = ".append(\" USING '";
  protected final String TEXT_94 = "'\");";
  protected final String TEXT_95 = NL + "\t\tsb_";
  protected final String TEXT_96 = ".append(\" PARTITION BY \").append(";
  protected final String TEXT_97 = ");";
  protected final String TEXT_98 = NL + "\t\tsb_";
  protected final String TEXT_99 = ".append(\" PARALLEL \").append(";
  protected final String TEXT_100 = ");";
  protected final String TEXT_101 = NL + "\tsb_";
  protected final String TEXT_102 = ".append(\";\");" + NL + "\thelper_";
  protected final String TEXT_103 = ".add(\"query\",sb_";
  protected final String TEXT_104 = ".toString());" + NL + "\tsb_";
  protected final String TEXT_105 = ".setLength(0);" + NL + "\t" + NL + "\tsb_";
  protected final String TEXT_106 = ".append(\"";
  protected final String TEXT_107 = "_";
  protected final String TEXT_108 = "_RESULT = FOREACH ";
  protected final String TEXT_109 = "_";
  protected final String TEXT_110 = "_RESULT GENERATE\");";
  protected final String TEXT_111 = NL + "\t\tsb_";
  protected final String TEXT_112 = ".append(\" ";
  protected final String TEXT_113 = "(";
  protected final String TEXT_114 = ") AS ";
  protected final String TEXT_115 = " \");";
  protected final String TEXT_116 = NL + "\t\t\tsb_";
  protected final String TEXT_117 = ".append(\",\");";
  protected final String TEXT_118 = NL + "\tsb_";
  protected final String TEXT_119 = ".append(\";\");" + NL + "\thelper_";
  protected final String TEXT_120 = ".add(\"query\",sb_";
  protected final String TEXT_121 = ".toString());" + NL + "\tsb_";
  protected final String TEXT_122 = ".setLength(0);\t" + NL + "\t" + NL + "\t";
  protected final String TEXT_123 = NL + "\tjava.util.List<String[]> pigLatins_";
  protected final String TEXT_124 = " = helper_";
  protected final String TEXT_125 = ".getPigLatins();";
  protected final String TEXT_126 = NL + "\t\tfor(String[] pigLatin_";
  protected final String TEXT_127 = " : pigLatins_";
  protected final String TEXT_128 = ") {" + NL + "\t\t\tString type_";
  protected final String TEXT_129 = " = pigLatin_";
  protected final String TEXT_130 = "[0];" + NL + "\t\t\t";
  protected final String TEXT_131 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_132 = " - register \" + type_";
  protected final String TEXT_133 = " + \" : \" + pigLatin_";
  protected final String TEXT_134 = "[1]);" + NL + "\t\t\t";
  protected final String TEXT_135 = NL + "\t\t\tif(\"query\".equals(type_";
  protected final String TEXT_136 = ")) {";
  protected final String TEXT_137 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_138 = ".write(pigLatin_";
  protected final String TEXT_139 = "[1]);";
  protected final String TEXT_140 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_141 = ".registerQuery(pigLatin_";
  protected final String TEXT_142 = "[1]);";
  protected final String TEXT_143 = NL + "\t\t\t} else if(\"jar\".equals(type_";
  protected final String TEXT_144 = ")) {";
  protected final String TEXT_145 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_146 = ".write(\"REGISTER \" + wasbPath_";
  protected final String TEXT_147 = " + new java.io.File(pigLatin_";
  protected final String TEXT_148 = "[1]).getName() + \";\");" + NL + "\t\t\t\t\tlibjars_";
  protected final String TEXT_149 = ".append(pigLatin_";
  protected final String TEXT_150 = "[1] + \",\");";
  protected final String TEXT_151 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_152 = ".registerJar(pigLatin_";
  protected final String TEXT_153 = "[1]);";
  protected final String TEXT_154 = NL + "\t\t\t} else if(\"script\".equals(type_";
  protected final String TEXT_155 = ")) {";
  protected final String TEXT_156 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_157 = ".write(pigLatin_";
  protected final String TEXT_158 = "[1]);";
  protected final String TEXT_159 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_160 = ".registerScript(new java.io.ByteArrayInputStream(pigLatin_";
  protected final String TEXT_161 = "[1].getBytes()));";
  protected final String TEXT_162 = NL + "\t\t\t} else if(\"function\".equals(type_";
  protected final String TEXT_163 = ")) {";
  protected final String TEXT_164 = NL + "\t\t\t\t\tbw_";
  protected final String TEXT_165 = ".write(\"DEFINE \" + pigLatin_";
  protected final String TEXT_166 = "[1] + \" \" + pigLatin_";
  protected final String TEXT_167 = "[2] + \";\");";
  protected final String TEXT_168 = NL + "\t\t\t\t\tpigServer_";
  protected final String TEXT_169 = ".registerFunction(pigLatin_";
  protected final String TEXT_170 = "[1], new org.apache.pig.FuncSpec(pigLatin_";
  protected final String TEXT_171 = "[2]));";
  protected final String TEXT_172 = NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_173 = NL + "    \tpigScript_";
  protected final String TEXT_174 = ".addAll(pigLatins_";
  protected final String TEXT_175 = ");";
  protected final String TEXT_176 = NL + "\tpigLatins_";
  protected final String TEXT_177 = ".clear();";
  protected final String TEXT_178 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();
	String cid = node.getUniqueName();
	
	List<IConnection> inputConnections = (List<IConnection>)node.getIncomingConnections();
	List<IConnection> outputConnections = (List<IConnection>)node.getOutgoingConnections();
	if((inputConnections==null) || (inputConnections.size()==0) || (outputConnections == null) || (outputConnections.size()==0)) {
		return "";
	}
	
	String previous_node="";
	String start_node="";
	
	boolean inMain = true;

	String previousOutputConnectionName = null;
	
	IConnection mainConnnection = inputConnections.get(0);
	previous_node = mainConnnection.getSource().getUniqueName();
	INode loadNode = node.getDesignSubjobStartNode();
	start_node = loadNode.getUniqueName();
	
	boolean isLocal = "true".equals(ElementParameterParser.getValue(loadNode, "__LOCAL__"));
	boolean isExecutedThroughWebHCat = false;
    
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
	previousOutputConnectionName = mainConnnection.getName();
	
	String outputConnectionName = outputConnections.get(0).getName();
	
	List<Map<String, String>> groupByTable = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__GROUP_BY__");
	List<Map<String, String>> outputMapping = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__OUTPUT_MAPPING__");
	
	if((outputMapping == null) || (outputMapping.size() == 0)) {
		return "";
	}

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
    
	for(int i=0;i<inputConnections.size();i++) {
		IConnection inputConnection = inputConnections.get(i);
		String inputConnectionName = inputConnection.getName();
		boolean isMainInput = (i == 0);
		if(isMainInput) {

    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(previous_node);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_40);
    
		} else {

    stringBuffer.append(TEXT_41);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_70);
    
		}
	}

    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_74);
    
	for(int i=0;i<inputConnections.size();i++) {
		IConnection inputConnection = inputConnections.get(i);
		String inputConnectionName = inputConnection.getName();

    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(inputConnectionName);
    stringBuffer.append(TEXT_78);
    
		if((groupByTable==null) || (groupByTable.size()==0)) {//COGROUP ALL

    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    
		} else {

    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    
			for(int j=0;j<groupByTable.size();j++) {
				Map<String,String> groupBy = groupByTable.get(j);
				String expression = groupBy.get(inputConnectionName);

    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(expression);
    stringBuffer.append(TEXT_85);
    
				if(j<groupByTable.size()-1) {

    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    
				}
			}

    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    
		}
		
		if(i<inputConnections.size()-1) {

    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    
		}
	}
	
	String optimization = ElementParameterParser.getValue(node, "__GROUP_OPTIMIZATION__");
	
	boolean usePartition = "true".equals(ElementParameterParser.getValue(node, "__USE_PARTITION__"));
	String partitioner = ElementParameterParser.getValue(node, "__CUSTOM_PARTITIONER__");
	
	boolean increaseParallelism = "true".equals(ElementParameterParser.getValue(node, "__USE_INCREASE_PARALLELISM__"));
	String parallelism = ElementParameterParser.getValue(node, "__INCREASE_PARALLELISM__");
	
	boolean useOptimization = false;
	String optimizationModel = "";
	if("NONE".equalsIgnoreCase(optimization)) {
		//do nothing
	} else if("COLLECTED".equalsIgnoreCase(optimization)) {
		optimizationModel = "collected";
		useOptimization = true;
	} else if("MERGE".equalsIgnoreCase(optimization)) {
		optimizationModel = "merge";
		useOptimization = true;
	}
	
	if(useOptimization) {

    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(optimizationModel);
    stringBuffer.append(TEXT_94);
    
	}
	
	if(usePartition && partitioner!=null && !"".equals(partitioner) && !"\"\"".equals(partitioner)) {

    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(partitioner);
    stringBuffer.append(TEXT_97);
    
	}
	
	if(increaseParallelism && parallelism!=null && !"".equals(parallelism) && !"\"\"".equals(parallelism)) {

    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(parallelism);
    stringBuffer.append(TEXT_100);
    
	}

    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_107);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(outputConnectionName);
    stringBuffer.append(TEXT_110);
    
	//case1 : input expression is the same(now only use string equals to compare) with any group expression and no function
	//we convert like that : (id+1) ==> group or group.$0 
	//case2 : other any cases
	//we convert like that : (id+1)(have to come from row1) ==> (row1bagname.id+1) 
	
	//case1
	Map<String,Map<String,String>> groupExpressionConvertMap = new HashMap<String,Map<String,String>>();
	if(groupByTable!=null && groupByTable.size()!=0) {
		for(int i=0;i<inputConnections.size();i++) {
			IConnection inputConnection = inputConnections.get(i);
			String inputConnectionName = inputConnection.getName();
			Map<String,String> currentConnectionGroupExpressionMap = new HashMap<String,String>();
			for(int j=0;j<groupByTable.size();j++) {
				Map<String,String> groupBy = groupByTable.get(j);
				String expression = groupBy.get(inputConnectionName);
				if(groupByTable.size() != 1) {
					currentConnectionGroupExpressionMap.put(expression,"group.$" + j);
				} else {
					currentConnectionGroupExpressionMap.put(expression,"group");
				}
			}
			groupExpressionConvertMap.put(inputConnectionName,currentConnectionGroupExpressionMap);
		}
	}
	
	//case2
	Map<String,Map<String,String>> inputExpressionConvertMap = new HashMap<String,Map<String,String>>();
	StringBuilder sb = new StringBuilder();
	for(int i=0;i<inputConnections.size();i++) {
		IConnection inputConnection = inputConnections.get(i);
		String inputConnectionName = inputConnection.getName();
		IMetadataTable currentMetadata = inputConnection.getMetadataTable();
		List<IMetadataColumn> columns = currentMetadata.getListColumns();
		Map<String,String> currentConnectionInputExpressionMap = new HashMap<String,String>();
		for(IMetadataColumn column : columns) {
			currentConnectionInputExpressionMap.put(column.getLabel(), sb.append(cid).append("_").append(inputConnectionName).append("_RESULT").append(".").append(column.getLabel()).toString());
			sb.setLength(0);
		}
		inputExpressionConvertMap.put(inputConnectionName,currentConnectionInputExpressionMap);
	}
	
	class ExpressionUtil {
		
		private Map<String,Pattern> cache = new HashMap<String,Pattern>();
		
		String convertExpression(Map<String,String> expressionConvertMap, String expression) {// (id+1) ==> (row1bagname.id+1)
			if(expression == null || "".equals(expression.trim())) {
				return "";
			}
			
			for(Map.Entry<String, String> entry : expressionConvertMap.entrySet()) {
				String key = entry.getKey();//like id
				String value = entry.getValue();//like row1.id
				
	            Pattern p = cache.get(key);
	            
	            if(p == null) {
	                p = Pattern.compile(key,Pattern.LITERAL);
	                cache.put(key, p);
	            }
	            
	            Matcher m = p.matcher(expression);
	            
	            StringBuffer sb = new StringBuffer();
	            
	            if (m.find()) {
	                do {
	                    if (m.start() > 0) {
	                        char c = expression.charAt(m.start() - 1);
	                        if (Character.isLetterOrDigit(c) || (c == '_')) {
	                            continue;
	                        }
	                    }
	                    
	                    if (m.end() < expression.length()) {
	                        char c = expression.charAt(m.end());
	                        if (Character.isLetterOrDigit(c) || (c == '_')) {
	                            continue;
	                        }
	                    }
	                    
	                    m.appendReplacement(sb,value);
	                } while (m.find());
	                
	                m.appendTail(sb);
	                expression = sb.toString();
	            }
			}
			
			return expression;
		}
	}
	
	ExpressionUtil expressionUtil = new ExpressionUtil();
	
	for(int i=0;i<outputMapping.size();i++) {
		Map<String,String> item = outputMapping.get(i);
		String outputColumn = item.get("SCHEMA_COLUMN");
		String function = item.get("FUNCTION");
		String sourceConnectionOfInputExpression = item.get("PARENT_ROW");
		String inputExpression = item.get("REFCOL");
		
		String finalExpression = inputExpression;
		Map<String,String> currentConnectionGroupExpressionMap = groupExpressionConvertMap.get(sourceConnectionOfInputExpression);
		if("EMPTY".equals(function) && currentConnectionGroupExpressionMap!=null && currentConnectionGroupExpressionMap.get(inputExpression)!=null) {//case1
			finalExpression = currentConnectionGroupExpressionMap.get(inputExpression);
		} else {//case2
			Map<String,String> currentConnectionInputExpressionMap = inputExpressionConvertMap.get(sourceConnectionOfInputExpression);// id ==> row1bagname.id
			finalExpression = expressionUtil.convertExpression(currentConnectionInputExpressionMap, inputExpression);
		}
		
		if("EMPTY".equals(function)) {
			function = "";
		}

    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(function);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(finalExpression);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(outputColumn);
    stringBuffer.append(TEXT_115);
    
		if(i<outputMapping.size()-1) {

    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    
		}
	}

    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_125);
    
	if(inMain) {

    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    }
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_137);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    					
				} else {

    stringBuffer.append(TEXT_140);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_141);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_142);
    
				}

    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_145);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_150);
    					
				} else {

    stringBuffer.append(TEXT_151);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_153);
    
				}

    stringBuffer.append(TEXT_154);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_155);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_156);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_157);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_158);
    					
				} else {

    stringBuffer.append(TEXT_159);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    
				}

    stringBuffer.append(TEXT_162);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    
				if(isExecutedThroughWebHCat) {

    stringBuffer.append(TEXT_164);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    					
				} else {

    stringBuffer.append(TEXT_168);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_170);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_171);
    
				}

    stringBuffer.append(TEXT_172);
    
	} else {

    stringBuffer.append(TEXT_173);
    stringBuffer.append(start_node);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_175);
    
	}

    stringBuffer.append(TEXT_176);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(TEXT_178);
    return stringBuffer.toString();
  }
}
