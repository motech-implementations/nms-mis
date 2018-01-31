package org.talend.designer.codegen.translators.file.hadoop;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;

public class THDFSOutputRawMainJava
{
  protected static String nl;
  public static synchronized THDFSOutputRawMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    THDFSOutputRawMainJava result = new THDFSOutputRawMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\t\t\t\torg.apache.hadoop.fs.FSDataOutputStream fsDataOutputStream_";
  protected final String TEXT_3 = " = null;";
  protected final String TEXT_4 = NL + "\t\t\t\t\torg.apache.hadoop.io.compress.CompressionOutputStream out_";
  protected final String TEXT_5 = " = null;";
  protected final String TEXT_6 = NL + "\t\t\t\ttry {" + NL + "\t\t\t\t\tObject content_";
  protected final String TEXT_7 = " = ";
  protected final String TEXT_8 = ".";
  protected final String TEXT_9 = ";" + NL + "\t\t\t\t\tfsDataOutputStream_";
  protected final String TEXT_10 = " = fs_";
  protected final String TEXT_11 = ".create(path_";
  protected final String TEXT_12 = ", ";
  protected final String TEXT_13 = ");";
  protected final String TEXT_14 = NL + "\t\t\t\t\t\t\torg.apache.hadoop.io.compress.GzipCodec codec_";
  protected final String TEXT_15 = " = new org.apache.hadoop.io.compress.GzipCodec();" + NL + "\t\t\t\t\t\t\tcodec_";
  protected final String TEXT_16 = ".setConf(conf_";
  protected final String TEXT_17 = ");";
  protected final String TEXT_18 = NL + "\t\t\t\t\t\t\torg.apache.hadoop.io.compress.CompressionCodec codec_";
  protected final String TEXT_19 = " = org.apache.hadoop.util.ReflectionUtils.newInstance(org.apache.hadoop.io.compress.BZip2Codec.class, conf_";
  protected final String TEXT_20 = ");";
  protected final String TEXT_21 = NL + "\t\t\t\t\t\tout_";
  protected final String TEXT_22 = " = codec_";
  protected final String TEXT_23 = ".createOutputStream(fsDataOutputStream_";
  protected final String TEXT_24 = ");";
  protected final String TEXT_25 = NL + "\t\t\t\t\tif(content_";
  protected final String TEXT_26 = " != null) {" + NL + "\t\t\t\t\t\tif (content_";
  protected final String TEXT_27 = " instanceof String) {" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_28 = " out_";
  protected final String TEXT_29 = ".write(content_";
  protected final String TEXT_30 = ".toString().getBytes(";
  protected final String TEXT_31 = "));" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_32 = " fsDataOutputStream_";
  protected final String TEXT_33 = ".write(content_";
  protected final String TEXT_34 = ".toString().getBytes(";
  protected final String TEXT_35 = "));" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_36 = NL + "\t\t\t\t\t\t} else if(content_";
  protected final String TEXT_37 = " instanceof byte[]) {" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_38 = " out_";
  protected final String TEXT_39 = ".write((byte[])content_";
  protected final String TEXT_40 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_41 = " fsDataOutputStream_";
  protected final String TEXT_42 = ".write((byte[])content_";
  protected final String TEXT_43 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_44 = NL + "\t\t\t\t\t\t} else if(content_";
  protected final String TEXT_45 = " instanceof java.io.InputStream) {" + NL + "\t\t\t\t\t\t\tjava.io.InputStream fis_";
  protected final String TEXT_46 = " = (java.io.InputStream) content_";
  protected final String TEXT_47 = ";" + NL + "\t\t\t\t\t\t\tbyte[] buffer_";
  protected final String TEXT_48 = " = new byte[65536];" + NL + "\t\t\t\t\t\t\tint nb_";
  protected final String TEXT_49 = " = 0;" + NL + "\t\t\t\t\t\t\twhile (true) {" + NL + "\t\t\t\t\t\t\t\tnb_";
  protected final String TEXT_50 = " = fis_";
  protected final String TEXT_51 = ".read(buffer_";
  protected final String TEXT_52 = ");" + NL + "\t\t\t\t\t\t\t\tif (nb_";
  protected final String TEXT_53 = " == -1) {" + NL + "\t\t\t\t\t\t\t\t\tbreak;" + NL + "\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_54 = " out_";
  protected final String TEXT_55 = ".write(buffer_";
  protected final String TEXT_56 = ", 0, nb_";
  protected final String TEXT_57 = ");" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_58 = " fsDataOutputStream_";
  protected final String TEXT_59 = ".write(buffer_";
  protected final String TEXT_60 = ", 0, nb_";
  protected final String TEXT_61 = ");" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_62 = NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\tfis_";
  protected final String TEXT_63 = ".close();" + NL + "\t\t\t\t\t\t} else if (content_";
  protected final String TEXT_64 = " instanceof routines.system.Document) {" + NL + "\t\t\t\t\t\t\torg.dom4j.io.OutputFormat format_";
  protected final String TEXT_65 = " = new org.dom4j.io.OutputFormat();" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_66 = "format_";
  protected final String TEXT_67 = ".setEncoding(";
  protected final String TEXT_68 = ");";
  protected final String TEXT_69 = NL + "\t\t\t\t\t\t\torg.dom4j.io.XMLWriter writer_";
  protected final String TEXT_70 = " = new org.dom4j.io.XMLWriter(";
  protected final String TEXT_71 = "out_";
  protected final String TEXT_72 = " fsDataOutputStream_";
  protected final String TEXT_73 = ", format_";
  protected final String TEXT_74 = ");" + NL + "\t\t\t\t\t\t\twriter_";
  protected final String TEXT_75 = ".write(((routines.system.Document) content_";
  protected final String TEXT_76 = ").getDocument());" + NL + "\t\t\t\t\t\t\twriter_";
  protected final String TEXT_77 = ".close();" + NL + "\t\t\t\t\t\t} else if (content_";
  protected final String TEXT_78 = " != null) {" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_79 = " out_";
  protected final String TEXT_80 = ".write(content_";
  protected final String TEXT_81 = ".toString().getBytes(";
  protected final String TEXT_82 = "));" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_83 = " fsDataOutputStream_";
  protected final String TEXT_84 = ".write(content_";
  protected final String TEXT_85 = ".toString().getBytes(";
  protected final String TEXT_86 = "));" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_87 = NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t} catch (java.lang.Exception e_";
  protected final String TEXT_88 = ") {";
  protected final String TEXT_89 = NL + "\t\t\t\t\t\tthrow e_";
  protected final String TEXT_90 = ";";
  protected final String TEXT_91 = NL + "\t\t\t\t\t\tSystem.err.println(e_";
  protected final String TEXT_92 = ");";
  protected final String TEXT_93 = NL + "\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_94 = " - \" + e_";
  protected final String TEXT_95 = ".getMessage());";
  protected final String TEXT_96 = NL + "\t\t\t\t} finally {";
  protected final String TEXT_97 = NL + "\t\t\t\t\t\ttry {" + NL + "\t\t\t\t\t\t\tif(out_";
  protected final String TEXT_98 = " != null) {" + NL + "\t\t\t\t\t\t\t\tout_";
  protected final String TEXT_99 = ".close();" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t} catch(java.nio.channels.ClosedChannelException e_";
  protected final String TEXT_100 = ") {" + NL + "\t\t\t\t\t\t\t// The stream is closed, continue." + NL + "\t\t\t\t\t\t}";
  protected final String TEXT_101 = NL + "\t\t\t\t\ttry {" + NL + "\t\t\t\t\t\tif(fsDataOutputStream_";
  protected final String TEXT_102 = " != null) {" + NL + "\t\t\t\t\t\t\tfsDataOutputStream_";
  protected final String TEXT_103 = ".close();" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t} catch(java.nio.channels.ClosedChannelException e_";
  protected final String TEXT_104 = ") {" + NL + "\t\t\t\t\t\t// The stream is closed, continue." + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();
	String cid = node.getUniqueName();

	boolean customEncoding = "true".equals(ElementParameterParser.getValue(node, "__CUSTOM_ENCODING__"));
	String encoding = ElementParameterParser.getValue(node, "__ENCODING__");

	String fileAction = ElementParameterParser.getValue(node, "__FILE_ACTION__");

	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));

    boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

	boolean compress = "true".equals(ElementParameterParser.getValue(node, "__COMPRESS__"));
	String compression = ElementParameterParser.getValue(node, "__COMPRESSION__");

	java.util.List<IMetadataColumn> columnList = null;
	java.util.List<IMetadataTable> metadatas = node.getMetadataList();
	if(metadatas != null && metadatas.size() > 0) {
		IMetadataTable metadata = metadatas.get(0);
		if(metadata != null) {
		    columnList = metadata.getListColumns();
		}
	}
	java.util.List< ? extends IConnection> incomingConns = node.getIncomingConnections();
	if(columnList!=null && incomingConns!=null && incomingConns.size()>0) {
		for(IConnection incomingConn : incomingConns) {
			if(incomingConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {

    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    
				if(compress) {

    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
				}

    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(incomingConn.getName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(columnList.get(0).getLabel());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append("CREATE".equals(fileAction)?false:true);
    stringBuffer.append(TEXT_13);
    
					if(compress) {
						if("GZIP".equals(compression)) {

    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    
						} else if("BZIP2".equals(compression)) {

    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    
						}

    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    
					}

    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    if(compress) { 
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(customEncoding?encoding:"");
    stringBuffer.append(TEXT_31);
     } else { 
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(customEncoding?encoding:"");
    stringBuffer.append(TEXT_35);
     } 
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    if(compress) { 
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
     } else { 
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
     } 
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
     if (compress) { 
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
     } else { 
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
     } 
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
     if(customEncoding) { 
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_68);
     } 
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
     if(compress) { 
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
     } else { 
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
     } 
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    if(compress) { 
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(customEncoding?encoding:"");
    stringBuffer.append(TEXT_82);
     } else { 
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(customEncoding?encoding:"");
    stringBuffer.append(TEXT_86);
     } 
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    
					if(dieOnError) {

    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_90);
    
					} else {

    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    
						if(isLog4jEnabled) {

    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    
						}
					}

    stringBuffer.append(TEXT_96);
    
					if(compress) {

    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_100);
    
					}

    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    
			}
		}
	}

    return stringBuffer.toString();
  }
}
