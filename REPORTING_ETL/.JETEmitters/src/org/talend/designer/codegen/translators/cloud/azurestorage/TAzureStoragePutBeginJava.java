package org.talend.designer.codegen.translators.cloud.azurestorage;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;

public class TAzureStoragePutBeginJava
{
  protected static String nl;
  public static synchronized TAzureStoragePutBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TAzureStoragePutBeginJava result = new TAzureStoragePutBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\ttry {";
  protected final String TEXT_3 = NL + "\t\t\tcom.microsoft.windowsazure.services.core.storage.CloudStorageAccount myAzureStorageAccount_";
  protected final String TEXT_4 = " = (com.microsoft.windowsazure.services.core.storage.CloudStorageAccount)globalMap.get(\"connection_\" + \"";
  protected final String TEXT_5 = "\");";
  protected final String TEXT_6 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_7 = " - Get the existed cloud storage account object from ";
  protected final String TEXT_8 = ".\");";
  protected final String TEXT_9 = NL + "\t\t\tString myAzureStorageConn_";
  protected final String TEXT_10 = " = \"DefaultEndpointsProtocol=";
  protected final String TEXT_11 = ";\" + \"AccountName=\" + ";
  protected final String TEXT_12 = " + \";\" + \"AccountKey=\" + ";
  protected final String TEXT_13 = ";" + NL + "\t\t\t// Retrieve storage account from connection-string" + NL + "\t\t\tcom.microsoft.windowsazure.services.core.storage.CloudStorageAccount myAzureStorageAccount_";
  protected final String TEXT_14 = " = com.microsoft.windowsazure.services.core.storage.CloudStorageAccount.parse(myAzureStorageConn_";
  protected final String TEXT_15 = ");";
  protected final String TEXT_16 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_17 = " - Create the cloud storage account object successfully.\");";
  protected final String TEXT_18 = NL + "\t\t\tglobalMap.put(\"";
  protected final String TEXT_19 = "_ACCOUNT_NAME\", ";
  protected final String TEXT_20 = ");" + NL + "\t\t\tglobalMap.put(\"";
  protected final String TEXT_21 = "_ACCOUNT_KEY\", ";
  protected final String TEXT_22 = ");";
  protected final String TEXT_23 = NL + "\t\t// Create the blob client" + NL + "\t\tcom.microsoft.windowsazure.services.blob.client.CloudBlobClient blobClient_";
  protected final String TEXT_24 = " = myAzureStorageAccount_";
  protected final String TEXT_25 = ".createCloudBlobClient();" + NL + "" + NL + "\t\t// Retrieve reference to a previously created container" + NL + "\t\tcom.microsoft.windowsazure.services.blob.client.CloudBlobContainer container_";
  protected final String TEXT_26 = " = blobClient_";
  protected final String TEXT_27 = ".getContainerReference(";
  protected final String TEXT_28 = ");" + NL + "" + NL + "\t\torg.talend.azurestorage.utils.AzureStorageUtils azureStorageUtils_";
  protected final String TEXT_29 = " = new org.talend.azurestorage.utils.AzureStorageUtils();" + NL + "\t\t" + NL + "\t\tjava.io.File dataDir_";
  protected final String TEXT_30 = "=new java.io.File(";
  protected final String TEXT_31 = ");" + NL + "\t\tjava.util.List<com.microsoft.windowsazure.services.blob.client.CloudBlockBlob> objects_";
  protected final String TEXT_32 = " =new java.util.ArrayList<com.microsoft.windowsazure.services.blob.client.CloudBlockBlob>();" + NL + "\t\tjava.util.List<java.util.Map<String,String>> list_";
  protected final String TEXT_33 = " = new java.util.ArrayList<java.util.Map<String,String>>();";
  protected final String TEXT_34 = "    " + NL + "\t\t\t\tjava.util.Map<String,String> map_";
  protected final String TEXT_35 = "_";
  protected final String TEXT_36 = " = new java.util.HashMap<String,String>();" + NL + "\t\t\t\tmap_";
  protected final String TEXT_37 = "_";
  protected final String TEXT_38 = ".put(";
  protected final String TEXT_39 = ",";
  protected final String TEXT_40 = ");\t\t" + NL + "\t\t\t\tlist_";
  protected final String TEXT_41 = ".add(map_";
  protected final String TEXT_42 = "_";
  protected final String TEXT_43 = ");       ";
  protected final String TEXT_44 = NL + "\t\t\tjava.util.Map<String,String> fileMap_";
  protected final String TEXT_45 = "=azureStorageUtils_";
  protected final String TEXT_46 = ".genFileFilterList(list_";
  protected final String TEXT_47 = ", ";
  protected final String TEXT_48 = ", ";
  protected final String TEXT_49 = ");";
  protected final String TEXT_50 = NL + "\t\t\tjava.util.Map<String,String> fileMap_";
  protected final String TEXT_51 = "=azureStorageUtils_";
  protected final String TEXT_52 = ".genAzureObjectList(new java.io.File(";
  protected final String TEXT_53 = "), ";
  protected final String TEXT_54 = ");";
  protected final String TEXT_55 = NL + "\t\tfor(java.util.Map.Entry entry_";
  protected final String TEXT_56 = ":fileMap_";
  protected final String TEXT_57 = ".entrySet()) {" + NL + "\t\t\tcom.microsoft.windowsazure.services.blob.client.CloudBlockBlob object_";
  protected final String TEXT_58 = " = container_";
  protected final String TEXT_59 = ".getBlockBlobReference(entry_";
  protected final String TEXT_60 = ".getValue().toString());" + NL + "\t\t\tjava.io.File source_";
  protected final String TEXT_61 = " = new java.io.File(entry_";
  protected final String TEXT_62 = ".getKey().toString());" + NL + "\t\t\tjava.io.FileInputStream currentStream_";
  protected final String TEXT_63 = " = new java.io.FileInputStream(source_";
  protected final String TEXT_64 = ");" + NL + "\t\t\tresourceMap.put(\"stream_";
  protected final String TEXT_65 = "\", currentStream_";
  protected final String TEXT_66 = ");" + NL + "\t\t\tobject_";
  protected final String TEXT_67 = ".upload(currentStream_";
  protected final String TEXT_68 = ", source_";
  protected final String TEXT_69 = ".length());" + NL + "\t\t\tresourceMap.remove(\"stream_";
  protected final String TEXT_70 = "\");" + NL + "\t\t\tcurrentStream_";
  protected final String TEXT_71 = ".close();";
  protected final String TEXT_72 = NL + "\t\t\tlog.info(\"";
  protected final String TEXT_73 = " - Upload the file : \" + source_";
  protected final String TEXT_74 = ".getName() + \" successfully.\");";
  protected final String TEXT_75 = NL + "\t\t}" + NL + "\t\t" + NL + "\t\t" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_76 = "_CONTAINER\", ";
  protected final String TEXT_77 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_78 = "_REMOTE_FOLDER\", ";
  protected final String TEXT_79 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_80 = "_LOCAL_FOLDER\", ";
  protected final String TEXT_81 = ");" + NL + "\t} catch (java.lang.Exception e_";
  protected final String TEXT_82 = ") {";
  protected final String TEXT_83 = NL + "\t\t\tthrow e_";
  protected final String TEXT_84 = ";";
  protected final String TEXT_85 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_86 = " - \" + e_";
  protected final String TEXT_87 = ".getMessage());";
  protected final String TEXT_88 = NL + "\t\t\tSystem.err.println(e_";
  protected final String TEXT_89 = ".getMessage());";
  protected final String TEXT_90 = NL + "\t} finally {" + NL + "\t\tjava.io.FileInputStream lastStream_";
  protected final String TEXT_91 = " = (java.io.FileInputStream)resourceMap.remove(\"stream_";
  protected final String TEXT_92 = "\");" + NL + "\t\tif(lastStream_";
  protected final String TEXT_93 = " != null) {" + NL + "\t\t\ttry {" + NL + "\t\t\t\tlastStream_";
  protected final String TEXT_94 = ".close();" + NL + "\t\t\t} catch(java.io.IOException eStreamClose_";
  protected final String TEXT_95 = ") {";
  protected final String TEXT_96 = NL + "\t\t\t\tlog.error(\"";
  protected final String TEXT_97 = " - \" + eStreamClose_";
  protected final String TEXT_98 = ".getMessage());";
  protected final String TEXT_99 = NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t}";
  protected final String TEXT_100 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();

	String cid = node.getUniqueName();
	boolean useExistingConnection = "true".equals(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"));
	String accountName = ElementParameterParser.getValue(node, "__ACCESS_KEY__");
	String accountSecret = ElementParameterParser.getValue(node, "__SECRET_KEY__");
	String protocol = ElementParameterParser.getValue(node, "__PROTOCOL__");
	
	String container = ElementParameterParser.getValue(node, "__CONTAINER__");
	//String remoteFileName = ElementParameterParser.getValue(node, "__REMOTE_FILENAME__");
	//String localFileName = ElementParameterParser.getValue(node, "__LOCAL_FILENAME__");
	String remoteFolder = ElementParameterParser.getValue(node, "__REMOTE_FOLDER__");
	String localFolder = ElementParameterParser.getValue(node, "__LOCAL_FOLDER__");
	List<Map<String, String>> files = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__FILES__");	
	
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	boolean useFileList = "true".equals(ElementParameterParser.getValue(node, "__USE_FILES_LIST__"));
	

    stringBuffer.append(TEXT_2);
    
		if(useExistingConnection) {
			String connection = ElementParameterParser.getValue(node,"__CONNECTION__");

    stringBuffer.append(TEXT_3);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_5);
    
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(connection);
    stringBuffer.append(TEXT_8);
    
			}
		} else {

    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(protocol.toLowerCase());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(accountName);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(accountSecret);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_16);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_17);
    
			}

    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(accountName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(accountSecret);
    stringBuffer.append(TEXT_22);
    
		}

    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(container);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(localFolder);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_33);
    
		if(useFileList && files!=null && files.size()>0){
			for (int i=0; i<files.size(); i++) {
				Map<String, String> line = files.get(i);

    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_35);
    stringBuffer.append(i );
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(i );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( line.get("FILEMASK") );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( line.get("NEWNAME") );
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(i );
    stringBuffer.append(TEXT_43);
    
			}
		}

    
		if(useFileList) {

    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_47);
    stringBuffer.append(localFolder);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(remoteFolder);
    stringBuffer.append(TEXT_49);
    
		} else {

    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(localFolder);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(remoteFolder);
    stringBuffer.append(TEXT_54);
    			
		}

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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    
			}

    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_76);
    stringBuffer.append(container);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(remoteFolder);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(localFolder);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    
		if(dieOnError) {

    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    
		} else {
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_87);
    
			}

    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
    
		}

    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    
				if(isLog4jEnabled) {

    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    
				}

    stringBuffer.append(TEXT_99);
    stringBuffer.append(TEXT_100);
    return stringBuffer.toString();
  }
}
