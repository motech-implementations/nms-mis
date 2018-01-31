package org.talend.designer.codegen.translators.cloud.azurestorage;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;

public class TAzureStorageDeleteBeginJava
{
  protected static String nl;
  public static synchronized TAzureStorageDeleteBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TAzureStorageDeleteBeginJava result = new TAzureStorageDeleteBeginJava();
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
  protected final String TEXT_28 = ");";
  protected final String TEXT_29 = NL + "\t\t\tfor (com.microsoft.windowsazure.services.blob.client.ListBlobItem blobItem_";
  protected final String TEXT_30 = " : container_";
  protected final String TEXT_31 = ".listBlobs(";
  protected final String TEXT_32 = ", ";
  protected final String TEXT_33 = ", null, null, null)) {" + NL + "\t\t\t\tif (blobItem_";
  protected final String TEXT_34 = " instanceof com.microsoft.windowsazure.services.blob.client.CloudBlob) {" + NL + "\t\t\t\t\tcom.microsoft.windowsazure.services.blob.client.CloudBlob blob_";
  protected final String TEXT_35 = " = (com.microsoft.windowsazure.services.blob.client.CloudBlob) blobItem_";
  protected final String TEXT_36 = ";";
  protected final String TEXT_37 = NL + "\t\t\t\t\tboolean result_";
  protected final String TEXT_38 = " =";
  protected final String TEXT_39 = NL + "\t\t\t\t\tblob_";
  protected final String TEXT_40 = ".deleteIfExists();";
  protected final String TEXT_41 = NL + "\t\t\t\t\tif(result_";
  protected final String TEXT_42 = ") {" + NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_43 = " - Delete the blob : \" + blob_";
  protected final String TEXT_44 = ".getName() + \" successfully.\");" + NL + "\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\tlog.info(\"";
  protected final String TEXT_45 = " - The blob : \" + blob_";
  protected final String TEXT_46 = ".getName() + \" is not deleted successfully.\");" + NL + "\t\t\t\t\t}";
  protected final String TEXT_47 = NL + "\t\t\t\t}" + NL + "\t\t\t}";
  protected final String TEXT_48 = NL + "\t\t\t    \t" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_49 = "_CONTAINER\", ";
  protected final String TEXT_50 = ");" + NL + "\t} catch (java.lang.Exception e_";
  protected final String TEXT_51 = ") {";
  protected final String TEXT_52 = NL + "\t\t\tthrow e_";
  protected final String TEXT_53 = ";";
  protected final String TEXT_54 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_55 = " - \" + e_";
  protected final String TEXT_56 = ".getMessage());";
  protected final String TEXT_57 = NL + "\t\t\tSystem.err.println(e_";
  protected final String TEXT_58 = ".getMessage());";
  protected final String TEXT_59 = NL + "\t}";
  protected final String TEXT_60 = NL;

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
	List<Map<String, String>> remoteBlobs = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__REMOTE_BLOBS__");
	boolean dieOnError = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
	
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(container);
    stringBuffer.append(TEXT_28);
    
		for(int i=0;i<remoteBlobs.size();i++){
			Map<String, String> map=remoteBlobs.get(i);
			String blobs = map.get("PREFIX");
			boolean includeSubdirectories = "true".equals(map.get("INCLUDE"));

    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(blobs);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(includeSubdirectories);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    
					if(isLog4jEnabled) {

    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    
					}

    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    
					if(isLog4jEnabled) {

    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
					}

    stringBuffer.append(TEXT_47);
    
		}

    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(container);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    
		if(dieOnError) {

    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_53);
    
		} else {
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_56);
    
			}

    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    
		}

    stringBuffer.append(TEXT_59);
    stringBuffer.append(TEXT_60);
    return stringBuffer.toString();
  }
}
