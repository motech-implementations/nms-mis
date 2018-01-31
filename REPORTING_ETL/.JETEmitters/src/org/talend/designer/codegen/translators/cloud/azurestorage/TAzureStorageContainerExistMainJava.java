package org.talend.designer.codegen.translators.cloud.azurestorage;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TAzureStorageContainerExistMainJava
{
  protected static String nl;
  public static synchronized TAzureStorageContainerExistMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TAzureStorageContainerExistMainJava result = new TAzureStorageContainerExistMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\tboolean containerExist_";
  protected final String TEXT_3 = " = false;" + NL + "\ttry {";
  protected final String TEXT_4 = NL + "\t\tcom.microsoft.windowsazure.services.core.storage.CloudStorageAccount azureStorageAccount_";
  protected final String TEXT_5 = " = (com.microsoft.windowsazure.services.core.storage.CloudStorageAccount)globalMap.get(\"connection_\" + \"";
  protected final String TEXT_6 = "\");";
  protected final String TEXT_7 = NL + "\t\tlog.info(\"";
  protected final String TEXT_8 = " - Get the existed cloud storage account object from ";
  protected final String TEXT_9 = ".\");";
  protected final String TEXT_10 = NL + "\t\tString myAzureStorageConn_";
  protected final String TEXT_11 = " = \"DefaultEndpointsProtocol=";
  protected final String TEXT_12 = ";\" + \"AccountName=\" + ";
  protected final String TEXT_13 = " + \";\" + \"AccountKey=\" + ";
  protected final String TEXT_14 = ";" + NL + "\t\tcom.microsoft.windowsazure.services.core.storage.CloudStorageAccount azureStorageAccount_";
  protected final String TEXT_15 = " = com.microsoft.windowsazure.services.core.storage.CloudStorageAccount.parse(myAzureStorageConn_";
  protected final String TEXT_16 = ");";
  protected final String TEXT_17 = NL + "\t\tlog.info(\"";
  protected final String TEXT_18 = " - Create the cloud storage account object successfully.\");";
  protected final String TEXT_19 = NL + "\tcom.microsoft.windowsazure.services.blob.client.CloudBlobClient blobClient_";
  protected final String TEXT_20 = " = azureStorageAccount_";
  protected final String TEXT_21 = ".createCloudBlobClient();" + NL + "\tcom.microsoft.windowsazure.services.blob.client.CloudBlobContainer blobContainer_";
  protected final String TEXT_22 = " = blobClient_";
  protected final String TEXT_23 = ".getContainerReference(";
  protected final String TEXT_24 = ");" + NL + "\t" + NL + "\tcontainerExist_";
  protected final String TEXT_25 = " = blobContainer_";
  protected final String TEXT_26 = ".exists();";
  protected final String TEXT_27 = NL + "\t\tif(containerExist_";
  protected final String TEXT_28 = ") {" + NL + "\t\t\tlog.info(\"";
  protected final String TEXT_29 = " - The container already exists.\");" + NL + "\t\t} else {" + NL + "\t\t\tlog.info(\"";
  protected final String TEXT_30 = " - The container doesn't exist.\");" + NL + "\t\t}";
  protected final String TEXT_31 = NL + NL + "\t}catch(com.microsoft.windowsazure.services.core.storage.StorageException e_";
  protected final String TEXT_32 = ") {";
  protected final String TEXT_33 = NL + "\t\tthrow(e_";
  protected final String TEXT_34 = ");";
  protected final String TEXT_35 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_36 = " - \" + e_";
  protected final String TEXT_37 = ".getMessage());";
  protected final String TEXT_38 = NL + "\t\tSystem.err.println(\"Exception in component ";
  protected final String TEXT_39 = ":\");" + NL + "\t\tSystem.err.println(e_";
  protected final String TEXT_40 = ".getMessage());" + NL + "\t\tif(e_";
  protected final String TEXT_41 = ".getErrorCode()!=null){" + NL + "\t\t\tSystem.err.println(e_";
  protected final String TEXT_42 = ".getErrorCode());" + NL + "\t\t}" + NL + "\t\tif(e_";
  protected final String TEXT_43 = ".getMessage()!=null){" + NL + "\t\t\tSystem.err.println(e_";
  protected final String TEXT_44 = ".getExtendedErrorInformation());" + NL + "\t\t}";
  protected final String TEXT_45 = NL + "\t}" + NL + "\t" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_46 = "_CONTAINER\", ";
  protected final String TEXT_47 = ");" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_48 = "_CONTAINER_EXIST\", containerExist_";
  protected final String TEXT_49 = ");";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();

    String cid = node.getUniqueName();
	boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");
	String accountName = ElementParameterParser.getValue(node, "__ACCESS_KEY__");
	String accountSecret = ElementParameterParser.getValue(node, "__SECRET_KEY__");
	String protocol = ElementParameterParser.getValue(node, "__PROTOCOL__");
	String containerName=ElementParameterParser.getValue(node,"__CONTAINER__");
	
	String dieOnError = ElementParameterParser.getValue(node, "__DIE_ON_ERROR__");

    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    
	if(("true").equals(useExistingConn)) {
		String azure_connection = ElementParameterParser.getValue(node,"__CONNECTION__");

    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(azure_connection);
    stringBuffer.append(TEXT_6);
    
		if(isLog4jEnabled) {

    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(azure_connection);
    stringBuffer.append(TEXT_9);
    
		}
	}else{

    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(protocol.toLowerCase());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(accountName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(accountSecret);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_16);
    
		if(isLog4jEnabled) {

    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    
		}
	}

    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(containerName);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    
	if(isLog4jEnabled) {

    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    
	}

    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    
		if (("true").equals(dieOnError)) {

    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    
		} else {
			if(isLog4jEnabled) {

    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    
			}

    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    
		}
		

    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(containerName);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    return stringBuffer.toString();
  }
}
