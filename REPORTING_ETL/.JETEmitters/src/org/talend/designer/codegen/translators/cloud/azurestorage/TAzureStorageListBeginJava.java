package org.talend.designer.codegen.translators.cloud.azurestorage;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.Map;
import java.util.List;

public class TAzureStorageListBeginJava
{
  protected static String nl;
  public static synchronized TAzureStorageListBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TAzureStorageListBeginJava result = new TAzureStorageListBeginJava();
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
  protected final String TEXT_23 = NL + "\t\tjava.util.List<java.util.Map<String,Object>> list_";
  protected final String TEXT_24 = " = new java.util.ArrayList<java.util.Map<String,Object>>();";
  protected final String TEXT_25 = NL + "\t\t\tjava.util.Map<String,Object> map_";
  protected final String TEXT_26 = "_";
  protected final String TEXT_27 = " = new java.util.HashMap<String,Object>();" + NL + "\t\t\tmap_";
  protected final String TEXT_28 = "_";
  protected final String TEXT_29 = ".put(\"PREFIX\",";
  protected final String TEXT_30 = ");" + NL + "\t\t\tmap_";
  protected final String TEXT_31 = "_";
  protected final String TEXT_32 = ".put(\"INCLUDE\",";
  protected final String TEXT_33 = ");\t" + NL + "\t\t\tlist_";
  protected final String TEXT_34 = ".add(map_";
  protected final String TEXT_35 = "_";
  protected final String TEXT_36 = ");";
  protected final String TEXT_37 = NL + "\t\t// Create the blob client" + NL + "\t\tcom.microsoft.windowsazure.services.blob.client.CloudBlobClient blobClient_";
  protected final String TEXT_38 = " = myAzureStorageAccount_";
  protected final String TEXT_39 = ".createCloudBlobClient();" + NL + "\t" + NL + "\t\t// Retrieve reference to a previously created container" + NL + "\t\tcom.microsoft.windowsazure.services.blob.client.CloudBlobContainer container_";
  protected final String TEXT_40 = " = blobClient_";
  protected final String TEXT_41 = ".getContainerReference(";
  protected final String TEXT_42 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_43 = "_CONTAINER\", ";
  protected final String TEXT_44 = ");" + NL + "\t\tfor (java.util.Map<String, Object> map_";
  protected final String TEXT_45 = ": list_";
  protected final String TEXT_46 = ") {" + NL + "\t\t\tString currentPrefix_";
  protected final String TEXT_47 = " = (String)map_";
  protected final String TEXT_48 = ".get(\"PREFIX\");" + NL + "\t\t\tboolean currentDoesInclude_";
  protected final String TEXT_49 = " = (Boolean) map_";
  protected final String TEXT_50 = ".get(\"INCLUDE\");" + NL + "\t\t\tjava.lang.Iterable<com.microsoft.windowsazure.services.blob.client.ListBlobItem> blobList_";
  protected final String TEXT_51 = " = container_";
  protected final String TEXT_52 = ".listBlobs(currentPrefix_";
  protected final String TEXT_53 = ", currentDoesInclude_";
  protected final String TEXT_54 = ", null, null, null);" + NL + "\t\t\tfor (com.microsoft.windowsazure.services.blob.client.ListBlobItem blobItem_";
  protected final String TEXT_55 = " : blobList_";
  protected final String TEXT_56 = ") {" + NL + "\t\t\t\tif(blobItem_";
  protected final String TEXT_57 = " instanceof com.microsoft.windowsazure.services.blob.client.CloudBlob) {" + NL + "\t\t\t\t\tcom.microsoft.windowsazure.services.blob.client.CloudBlob blob_";
  protected final String TEXT_58 = " = (com.microsoft.windowsazure.services.blob.client.CloudBlob) blobItem_";
  protected final String TEXT_59 = ";" + NL + "\t\t\t\t\tglobalMap.put(\"";
  protected final String TEXT_60 = "_CURRENT_BLOB\", blob_";
  protected final String TEXT_61 = ".getName());" + NL + "\t\t\t\t\t";
  protected final String TEXT_62 = NL + "\t\t\t\t\tlog.info(\"";
  protected final String TEXT_63 = " - The current blob : \" + blob_";
  protected final String TEXT_64 = ".getName());" + NL + "\t  \t\t\t\t";
  protected final String TEXT_65 = NL + "\t\t\t\t} else {" + NL + "\t\t\t\t\tbreak;" + NL + "\t\t\t\t}";
  protected final String TEXT_66 = NL;

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
	String blobName = ElementParameterParser.getValue(node, "__BLOB_NAME__");
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	List<Map<String, String>> remoteBlobs = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__REMOTE_BLOBS__");
	
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

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
    	
		for(int i=0;i<remoteBlobs.size();i++){
			Map<String, String> map=remoteBlobs.get(i);
			String prefix =map.get("PREFIX");
			boolean include ="true".equals(map.get("INCLUDE"));

    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_26);
    stringBuffer.append(i );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    stringBuffer.append(i );
    stringBuffer.append(TEXT_29);
    stringBuffer.append(prefix);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_31);
    stringBuffer.append(i );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(include);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_35);
    stringBuffer.append(i );
    stringBuffer.append(TEXT_36);
    
		}

    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(container);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(container);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid );
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
    stringBuffer.append(cid);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_61);
    if(isLog4jEnabled) {
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    }
    stringBuffer.append(TEXT_65);
    stringBuffer.append(TEXT_66);
    return stringBuffer.toString();
  }
}
