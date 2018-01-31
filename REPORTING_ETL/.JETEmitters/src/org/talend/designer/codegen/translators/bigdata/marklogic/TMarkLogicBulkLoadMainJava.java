package org.talend.designer.codegen.translators.bigdata.marklogic;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.utils.NodeUtil;

public class TMarkLogicBulkLoadMainJava
{
  protected static String nl;
  public static synchronized TMarkLogicBulkLoadMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMarkLogicBulkLoadMainJava result = new TMarkLogicBulkLoadMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "           final String decryptedPassword_";
  protected final String TEXT_3 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_4 = ");";
  protected final String TEXT_5 = NL + "           final String decryptedPassword_";
  protected final String TEXT_6 = " = ";
  protected final String TEXT_7 = ";";
  protected final String TEXT_8 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_9 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_10 = ");";
  protected final String TEXT_11 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_12 = " = ";
  protected final String TEXT_13 = "; ";
  protected final String TEXT_14 = NL + "    String runtimeFileSep_";
  protected final String TEXT_15 = " = System.getProperty(\"file.separator\");" + NL + "    boolean runOnWindows_";
  protected final String TEXT_16 = " = \"\\\\\".equals(runtimeFileSep_";
  protected final String TEXT_17 = ");" + NL + "" + NL + "    StringBuilder mlcpCmd_";
  protected final String TEXT_18 = " = new StringBuilder();" + NL + "    String fmtLoadPath_";
  protected final String TEXT_19 = " = ";
  protected final String TEXT_20 = ";" + NL + "    if(runOnWindows_";
  protected final String TEXT_21 = "){" + NL + "        mlcpCmd_";
  protected final String TEXT_22 = ".append(\"cmd /c mlcp.bat\");" + NL + "    }else{" + NL + "        mlcpCmd_";
  protected final String TEXT_23 = ".append(\"mlcp.sh\");" + NL + "    }" + NL + "    if(fmtLoadPath_";
  protected final String TEXT_24 = ".contains(\":\")){" + NL + "       fmtLoadPath_";
  protected final String TEXT_25 = " = \"/\" + fmtLoadPath_";
  protected final String TEXT_26 = ";" + NL + "    }" + NL + "    fmtLoadPath_";
  protected final String TEXT_27 = " = (fmtLoadPath_";
  protected final String TEXT_28 = ").replace(\"\\\\\",\"/\");" + NL + "" + NL + "    mlcpCmd_";
  protected final String TEXT_29 = ".append(\" import -username \");" + NL + "    mlcpCmd_";
  protected final String TEXT_30 = ".append(";
  protected final String TEXT_31 = ");" + NL + "    mlcpCmd_";
  protected final String TEXT_32 = ".append(\" -password \");" + NL + "    mlcpCmd_";
  protected final String TEXT_33 = ".append(decryptedPassword_";
  protected final String TEXT_34 = ");" + NL + "    mlcpCmd_";
  protected final String TEXT_35 = ".append(\" -host \");" + NL + "    mlcpCmd_";
  protected final String TEXT_36 = ".append(";
  protected final String TEXT_37 = ");" + NL + "    mlcpCmd_";
  protected final String TEXT_38 = ".append(\" -port \");" + NL + "    mlcpCmd_";
  protected final String TEXT_39 = ".append(Integer.valueOf(";
  protected final String TEXT_40 = "));";
  protected final String TEXT_41 = NL + "     mlcpCmd_";
  protected final String TEXT_42 = ".append(\" -database \");" + NL + "     mlcpCmd_";
  protected final String TEXT_43 = ".append(";
  protected final String TEXT_44 = ");";
  protected final String TEXT_45 = NL + "       String handledDocIdPrefix_";
  protected final String TEXT_46 = " = ";
  protected final String TEXT_47 = ";" + NL + "       if(handledDocIdPrefix_";
  protected final String TEXT_48 = ".endsWith(\"/\") || handledDocIdPrefix_";
  protected final String TEXT_49 = ".endsWith(\"\\\\\")){" + NL + "          handledDocIdPrefix_";
  protected final String TEXT_50 = " = handledDocIdPrefix_";
  protected final String TEXT_51 = ".substring(0, handledDocIdPrefix_";
  protected final String TEXT_52 = ".length() - 1);" + NL + "       }" + NL + "       mlcpCmd_";
  protected final String TEXT_53 = ".append(\" -output_uri_replace \\\"\");" + NL + "       mlcpCmd_";
  protected final String TEXT_54 = ".append(fmtLoadPath_";
  protected final String TEXT_55 = ");" + NL + "       mlcpCmd_";
  protected final String TEXT_56 = ".append(\",'\");" + NL + "       mlcpCmd_";
  protected final String TEXT_57 = ".append(handledDocIdPrefix_";
  protected final String TEXT_58 = ".replace(\"\\\\\", \"/\"));" + NL + "       mlcpCmd_";
  protected final String TEXT_59 = ".append(\"'\\\"\");";
  protected final String TEXT_60 = NL + "    mlcpCmd_";
  protected final String TEXT_61 = ".append(\" -input_file_path \");" + NL + "    mlcpCmd_";
  protected final String TEXT_62 = ".append(fmtLoadPath_";
  protected final String TEXT_63 = ");";
  protected final String TEXT_64 = NL + "    mlcpCmd_";
  protected final String TEXT_65 = ".append(\" \");" + NL + "    mlcpCmd_";
  protected final String TEXT_66 = ".append(";
  protected final String TEXT_67 = ");";
  protected final String TEXT_68 = NL + NL + "    Process process_";
  protected final String TEXT_69 = " = Runtime.getRuntime().exec(mlcpCmd_";
  protected final String TEXT_70 = ".toString());" + NL + "    final java.io.InputStream inputStream_";
  protected final String TEXT_71 = " = process_";
  protected final String TEXT_72 = ".getInputStream();" + NL + "    final java.io.InputStream errStream_";
  protected final String TEXT_73 = " = process_";
  protected final String TEXT_74 = ".getErrorStream();" + NL + "    Thread normal_";
  protected final String TEXT_75 = " = new Thread() {" + NL + "        public void run() {" + NL + "            try {" + NL + "                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream_";
  protected final String TEXT_76 = "));" + NL + "                String line = \"\";" + NL + "                try {" + NL + "                    while((line = reader.readLine()) != null) {" + NL + "                       System.out.println(line);" + NL + "                    }" + NL + "                } finally {" + NL + "                     reader.close();" + NL + "                }" + NL + "            }catch(java.io.IOException ioe) {" + NL + "                ioe.printStackTrace();" + NL + "            }" + NL + "        }" + NL + "    };" + NL + "    normal_";
  protected final String TEXT_77 = ".start();" + NL + "" + NL + "    Thread error_";
  protected final String TEXT_78 = " = new Thread() {" + NL + "        public void run() {" + NL + "            try {" + NL + "                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(errStream_";
  protected final String TEXT_79 = "));" + NL + "                String line = \"\";" + NL + "                try {" + NL + "                    while((line = reader.readLine()) != null) {" + NL + "                        System.err.println(line);" + NL + "                    }" + NL + "                } finally {" + NL + "                    reader.close();" + NL + "                }" + NL + "            } catch(java.io.IOException ioe) {" + NL + "               ioe.printStackTrace();" + NL + "            }" + NL + "        }" + NL + "    };" + NL + "    error_";
  protected final String TEXT_80 = ".start();" + NL + "    process_";
  protected final String TEXT_81 = ".waitFor();" + NL + "    normal_";
  protected final String TEXT_82 = ".interrupt();" + NL + "    error_";
  protected final String TEXT_83 = ".interrupt();";
  protected final String TEXT_84 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
     CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
     INode node = (INode)codeGenArgument.getArgument();
     String cid = node.getUniqueName();

     boolean reuseConn = "true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"));
     String host = ElementParameterParser.getValue(node,"__HOST__");
     String port = ElementParameterParser.getValue(node,"__PORT__");
     String userName = ElementParameterParser.getValue(node,"__USER_NAME__");
     String database = ElementParameterParser.getValue(node,"__DATABASE__");
     String passwordFieldName = "__PASSWORD__";

     if(reuseConn){
       String reusedConn = ElementParameterParser.getValue(node,"__CONNECTION__");
       INode reusedNode = NodeUtil.getNodeByUniqueName(node.getProcess(), reusedConn);
       host = ElementParameterParser.getValue(reusedNode, "__HOST__");
       port = ElementParameterParser.getValue(reusedNode, "__PORT__");
       userName = ElementParameterParser.getValue(reusedNode, "__USER_NAME__");
       database = ElementParameterParser.getValue(reusedNode, "__DATABASE__");
       
    if(ElementParameterParser.canEncrypt(reusedNode, passwordFieldName)){
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(reusedNode, passwordFieldName));
    stringBuffer.append(TEXT_4);
    }else{
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    stringBuffer.append( ElementParameterParser.getValue(reusedNode, passwordFieldName));
    stringBuffer.append(TEXT_7);
    }
    
     }else{
     
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_10);
    } else {
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_13);
    }
    
     }

     String loadFromDir = ElementParameterParser.getValue(node,"__LOAD_FOLDER__");
     String outputURIReplaceTo = ElementParameterParser.getValue(node,"__OUTPUT_URI_REPLACE__");
     boolean replaceOutputURI = false;
     if(outputURIReplaceTo != null && !"".equals(outputURIReplaceTo) && !"\"\"".equals(outputURIReplaceTo)){
        replaceOutputURI = true;
     }
     String extraMCLPParameters = ElementParameterParser.getValue(node,"__MCLP_EXTRA_PARAMETERS__");
  
    stringBuffer.append(TEXT_14);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( loadFromDir );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( userName );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_32);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( host );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( port );
    stringBuffer.append(TEXT_40);
    
    if(database != null && !"".equals(database) && !"\"\"".equals(database)){
    
    stringBuffer.append(TEXT_41);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( database );
    stringBuffer.append(TEXT_44);
    
     }
    
    
    if(replaceOutputURI){
    
    stringBuffer.append(TEXT_45);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( outputURIReplaceTo );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_52);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_53);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_54);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_55);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_56);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_57);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_59);
    
    }
     
    stringBuffer.append(TEXT_60);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_61);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_62);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_63);
    
    if(extraMCLPParameters != null && !"".equals(extraMCLPParameters) && !"\"\"".equals(extraMCLPParameters)){
    
    stringBuffer.append(TEXT_64);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_66);
    stringBuffer.append( extraMCLPParameters );
    stringBuffer.append(TEXT_67);
    
    }
     
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_69);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_70);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_72);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_75);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_78);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_83);
    stringBuffer.append(TEXT_84);
    return stringBuffer.toString();
  }
}
