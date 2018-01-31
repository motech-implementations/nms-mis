package org.talend.designer.codegen.translators.databases.mongodb;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.process.IConnectionCategory;
import java.util.List;
import java.util.Map;

public class TMongoDBGridFSPutMainJava
{
  protected static String nl;
  public static synchronized TMongoDBGridFSPutMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMongoDBGridFSPutMainJava result = new TMongoDBGridFSPutMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\t\tlog.debug(\"";
  protected final String TEXT_2 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_3 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_4 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_5 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_6 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_7 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_8 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_10 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_11 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_12 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_13 = " - Written records count: \" + nb_line_";
  protected final String TEXT_14 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_15 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_16 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_17 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_18 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_19 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_21 = " - Writing the record \" + nb_line_";
  protected final String TEXT_22 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_23 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_24 = " - Processing the record \" + nb_line_";
  protected final String TEXT_25 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_26 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_27 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_28 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_29 = NL + "\t\tjava.util.Set<String> keySet_";
  protected final String TEXT_30 = " = map_";
  protected final String TEXT_31 = ".keySet();" + NL + "\t\tfor (String key_";
  protected final String TEXT_32 = " : keySet_";
  protected final String TEXT_33 = "){" + NL + "\t\t\tString tempdir_";
  protected final String TEXT_34 = " =  localdir_";
  protected final String TEXT_35 = ";" + NL + "\t\t\tString filemask_";
  protected final String TEXT_36 = " = key_";
  protected final String TEXT_37 = "; " + NL + "\t\t\tString dir_";
  protected final String TEXT_38 = " = null;" + NL + "\t\t\t";
  protected final String TEXT_39 = NL + "\t\t\t\tString mask_";
  protected final String TEXT_40 = " = filemask_";
  protected final String TEXT_41 = ".replaceAll(\"\\\\\\\\\", \"/\") ;";
  protected final String TEXT_42 = NL + "\t\t\t\tString mask_";
  protected final String TEXT_43 = " = filemask_";
  protected final String TEXT_44 = ";";
  protected final String TEXT_45 = NL + "\t\t\tint i_";
  protected final String TEXT_46 = " = mask_";
  protected final String TEXT_47 = ".lastIndexOf('/');" + NL + "" + NL + "\t\t\tif (i_";
  protected final String TEXT_48 = "!=-1){" + NL + "\t\t\t\tdir_";
  protected final String TEXT_49 = " = mask_";
  protected final String TEXT_50 = ".substring(0, i_";
  protected final String TEXT_51 = ");" + NL + "\t\t\t\tmask_";
  protected final String TEXT_52 = " = mask_";
  protected final String TEXT_53 = ".substring(i_";
  protected final String TEXT_54 = "+1);" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\tif (dir_";
  protected final String TEXT_55 = "!=null && !\"\".equals(dir_";
  protected final String TEXT_56 = ")){" + NL + "\t\t\t\ttempdir_";
  protected final String TEXT_57 = " = tempdir_";
  protected final String TEXT_58 = " + \"/\" + dir_";
  protected final String TEXT_59 = ";" + NL + "\t\t\t}" + NL;
  protected final String TEXT_60 = NL + "\t\t\t\tmask_";
  protected final String TEXT_61 = " = mask_";
  protected final String TEXT_62 = ".replaceAll(\"\\\\.\", \"\\\\\\\\.\").replaceAll(\"\\\\*\", \".*\");";
  protected final String TEXT_63 = NL + "\t\t\tfinal String finalMask_";
  protected final String TEXT_64 = " = mask_";
  protected final String TEXT_65 = ";" + NL + "" + NL + "\t\t\tjava.io.File[] listings_";
  protected final String TEXT_66 = " = null;" + NL + "\t\t\tjava.io.File file_";
  protected final String TEXT_67 = " = new java.io.File(tempdir_";
  protected final String TEXT_68 = ");" + NL + "" + NL + "\t\t\tif (file_";
  protected final String TEXT_69 = ".isDirectory()) {" + NL + "\t\t\t\tlistings_";
  protected final String TEXT_70 = " = file_";
  protected final String TEXT_71 = ".listFiles(new java.io.FileFilter() {" + NL + "\t\t\t\t\tpublic boolean accept(java.io.File pathname) {" + NL + "\t\t\t\t\t\tboolean result = false;" + NL + "\t\t\t\t\t\tif (pathname != null && pathname.isFile()) {                      " + NL + "\t\t\t\t\t\t\tresult = java.util.regex.Pattern.compile(finalMask_";
  protected final String TEXT_72 = ").matcher(pathname.getName()).find(); " + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\treturn result;" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t});" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\tif(listings_";
  protected final String TEXT_73 = " == null || listings_";
  protected final String TEXT_74 = ".length <= 0){" + NL + "\t    \t\tSystem.err.println(\"No match file(\"+key_";
  protected final String TEXT_75 = "+\") exists!\");";
  protected final String TEXT_76 = NL + "\t\t\t\t\tlog.error(\"";
  protected final String TEXT_77 = " - No match file(\"+key_";
  protected final String TEXT_78 = "+\") exists!\");";
  protected final String TEXT_79 = NL + "\t\t\t}else{" + NL + "\t\t\t\tString localFilePath_";
  protected final String TEXT_80 = " = \"\";" + NL + "\t\t\t\tFileInfo_";
  protected final String TEXT_81 = " info_";
  protected final String TEXT_82 = " = map_";
  protected final String TEXT_83 = ".get(key_";
  protected final String TEXT_84 = ");" + NL + "\t\t\t\tfor (int m_";
  protected final String TEXT_85 = " = 0; m_";
  protected final String TEXT_86 = " < listings_";
  protected final String TEXT_87 = ".length; m_";
  protected final String TEXT_88 = "++){" + NL + "\t\t\t\t\tString gridfsFilename_";
  protected final String TEXT_89 = " = info_";
  protected final String TEXT_90 = ".newName;" + NL + "\t\t\t\t\tif((\"\").equals(gridfsFilename_";
  protected final String TEXT_91 = ")){" + NL + "\t\t\t\t\t\tgridfsFilename_";
  protected final String TEXT_92 = " = listings_";
  protected final String TEXT_93 = "[m_";
  protected final String TEXT_94 = "].getName();" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\tcom.mongodb.gridfs.GridFSInputFile gridfsFile_";
  protected final String TEXT_95 = " = gridfs_";
  protected final String TEXT_96 = ".createFile(listings_";
  protected final String TEXT_97 = "[m_";
  protected final String TEXT_98 = "]);" + NL + "\t\t\t\t\tgridfsFile_";
  protected final String TEXT_99 = ".setFilename(gridfsFilename_";
  protected final String TEXT_100 = ");" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\tif(info_";
  protected final String TEXT_101 = ".setMetadata) {" + NL + "\t\t\t\t\t\tcom.mongodb.DBObject metadata_";
  protected final String TEXT_102 = " = (com.mongodb.DBObject)com.mongodb.util.JSON.parse(info_";
  protected final String TEXT_103 = ".metadataJson);" + NL + "\t\t\t\t\t\tgridfsFile_";
  protected final String TEXT_104 = ".setMetaData(metadata_";
  protected final String TEXT_105 = ");" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\tgridfsFile_";
  protected final String TEXT_106 = ".save();" + NL + "\t\t\t\t\tnb_line_";
  protected final String TEXT_107 = "++;";
  protected final String TEXT_108 = NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_109 = " \" + \"file: \" + listings_";
  protected final String TEXT_110 = "[m_";
  protected final String TEXT_111 = "].getAbsolutePath() + \", size: \" + listings_";
  protected final String TEXT_112 = "[m_";
  protected final String TEXT_113 = "].length() + \" bytes upload successfully\");";
  protected final String TEXT_114 = NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
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
			
    stringBuffer.append(TEXT_1);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_2);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_6);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_8);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_9);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_12);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_14);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_17);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_19);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_20);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_22);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_23);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_25);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_26);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    
			}
		}
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    

	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
  	INode node = (INode)codeGenArgument.getArgument();
  	String cid = node.getUniqueName();
  	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	String localdir = ElementParameterParser.getValue(node, "__LOCALDIR__");
	boolean useGlob = ("true").equals(ElementParameterParser.getValue(node, "__PERL5_REGEX__"));


    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_38);
    
			// Clean the filemasks and prepare it for listing
			if(!useGlob) { //perl5 mode does not support windows (\) path separator in the mask string

    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_41);
    
			} else {

    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_44);
    
			}

    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_59);
    
			if(!useGlob) {

    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_62);
    
			}

    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_75);
    				if(isLog4jEnabled) {

    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_78);
    
				}

    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
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
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_107);
    					if(isLog4jEnabled) {

    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_113);
    
					}

    stringBuffer.append(TEXT_114);
    

    return stringBuffer.toString();
  }
}
