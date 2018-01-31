package org.talend.designer.codegen.translators.elt.map.hive;

import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import org.talend.core.model.process.IConnection;
import org.talend.designer.dbmap.external.data.ExternalDbMapData;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.dbmap.DbMapComponent;
import org.talend.core.model.process.INode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class TELTHiveMapMainJava
{
  protected static String nl;
  public static synchronized TELTHiveMapMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TELTHiveMapMainJava result = new TELTHiveMapMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = " " + NL + "\t\tSystem.setProperty(\"java.io.tmpdir\", ";
  protected final String TEXT_2 = "); " + NL + "\t";
  protected final String TEXT_3 = NL + "\t\t\tSystem.setProperty(";
  protected final String TEXT_4 = " ,";
  protected final String TEXT_5 = ");";
  protected final String TEXT_6 = NL + "\t\t\tSystem.setProperty(\"hive.metastore.sasl.enabled\", \"true\");" + NL + "\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionDriverName\", ";
  protected final String TEXT_7 = ");" + NL + "\t\t\t";
  protected final String TEXT_8 = NL + "\t\t\t\tSystem.setProperty(\"hive.security.authorization.enabled\", \"false\");" + NL + "\t\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\tSystem.setProperty(\"hive.security.authorization.enabled\", \"true\");" + NL + "\t\t\t\t";
  protected final String TEXT_10 = NL + "\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionURL\", ";
  protected final String TEXT_11 = ");" + NL + "\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionUserName\", ";
  protected final String TEXT_12 = ");" + NL + "\t\t\t" + NL + "    \t\t";
  protected final String TEXT_13 = NL + "    \t\t" + NL + "    \t\t";
  protected final String TEXT_14 = " " + NL + "        \tString decryptedMetastorePassword_";
  protected final String TEXT_15 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_16 = ");" + NL + "   \t\t \t";
  protected final String TEXT_17 = NL + "        \tString decryptedMetastorePassword_";
  protected final String TEXT_18 = " = ";
  protected final String TEXT_19 = "; " + NL + "\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t\tSystem.setProperty(\"hive.server2.authentication.kerberos.principal\", ";
  protected final String TEXT_21 = ");" + NL + "\t\t\t\tSystem.setProperty(\"hive.server2.authentication.kerberos.keytab\", ";
  protected final String TEXT_22 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_23 = NL + NL + "\t\t\tSystem.setProperty(\"javax.jdo.option.ConnectionPassword\", decryptedMetastorePassword_";
  protected final String TEXT_24 = ");" + NL + "\t\t\tSystem.setProperty(\"hive.metastore.kerberos.principal\", ";
  protected final String TEXT_25 = ");\t\t\t\t";
  protected final String TEXT_26 = NL + "\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_27 = ", ";
  protected final String TEXT_28 = ");";
  protected final String TEXT_29 = NL + "                System.setProperty(\"pname\", \"MapRLogin\");" + NL + "                System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "                System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_30 = ");" + NL + "                System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_31 = ");";
  protected final String TEXT_32 = NL + "\t\t\t\torg.apache.hadoop.security.UserGroupInformation.loginUserFromKeytab(";
  protected final String TEXT_33 = ", ";
  protected final String TEXT_34 = ");";
  protected final String TEXT_35 = NL + "                com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_36 = " = new com.mapr.login.client.MapRLoginHttpsClient();" + NL + "                maprLogin_";
  protected final String TEXT_37 = ".getMapRCredentialsViaKerberos(";
  protected final String TEXT_38 = ", ";
  protected final String TEXT_39 = ");";
  protected final String TEXT_40 = NL + "            System.setProperty(\"pname\", \"MapRLogin\");" + NL + "            System.setProperty(\"https.protocols\", \"TLSv1.2\");" + NL + "            System.setProperty(\"mapr.home.dir\", ";
  protected final String TEXT_41 = ");" + NL + "            com.mapr.login.client.MapRLoginHttpsClient maprLogin_";
  protected final String TEXT_42 = " = new com.mapr.login.client.MapRLoginHttpsClient();";
  protected final String TEXT_43 = NL + "                System.setProperty(\"hadoop.login\", ";
  protected final String TEXT_44 = ");";
  protected final String TEXT_45 = NL + "                maprLogin_";
  protected final String TEXT_46 = ".setCheckUGI(false);";
  protected final String TEXT_47 = NL + "                String decryptedMaprPassword_";
  protected final String TEXT_48 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_49 = ");";
  protected final String TEXT_50 = NL + "                String decryptedMaprPassword_";
  protected final String TEXT_51 = " = ";
  protected final String TEXT_52 = ";";
  protected final String TEXT_53 = NL + "            \tmaprLogin_";
  protected final String TEXT_54 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_55 = ", ";
  protected final String TEXT_56 = ", decryptedMaprPassword_";
  protected final String TEXT_57 = ", ";
  protected final String TEXT_58 = ", \"\");" + NL + "            \t";
  protected final String TEXT_59 = NL + "            \tmaprLogin_";
  protected final String TEXT_60 = ".getMapRCredentialsViaPassword(";
  protected final String TEXT_61 = ", ";
  protected final String TEXT_62 = ", decryptedMaprPassword_";
  protected final String TEXT_63 = ", ";
  protected final String TEXT_64 = ");" + NL + "            \t";
  protected final String TEXT_65 = NL + "\t\t\tSystem.setProperty(\"mapred.job.tracker\", ";
  protected final String TEXT_66 = ");";
  protected final String TEXT_67 = NL + "\t\t\tSystem.setProperty(\"";
  protected final String TEXT_68 = "\", ";
  protected final String TEXT_69 = ");";
  protected final String TEXT_70 = NL + NL + "\t\t\tglobalMap.put(" + NL + "\t\t\t\t\"";
  protected final String TEXT_71 = "\"+\"QUERY\" + \"";
  protected final String TEXT_72 = "\"," + NL + "\t\t\t\t";
  protected final String TEXT_73 = NL + "\t\t\t);" + NL + "\t\t\tglobalMap.put(" + NL + "\t\t\t\t\t\"";
  protected final String TEXT_74 = "\"+\"QUERY_COLUMNS_NAME\" + \"";
  protected final String TEXT_75 = "\"," + NL + "\t\t\t\t\t";
  protected final String TEXT_76 = NL + "\t\t\t);" + NL + "\t\t\t";
  protected final String TEXT_77 = "       " + NL + "                Object ";
  protected final String TEXT_78 = " = new Object();         ";
  protected final String TEXT_79 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    


	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	DbMapComponent node = (DbMapComponent) codeGenArgument.getArgument();

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    org.talend.designer.dbmap.language.hive.HiveGenerationManager gm = new org.talend.designer.dbmap.language.hive.HiveGenerationManager();
    String uniqueNameComponent = null;

    List<IConnection> outputConnections = (List<IConnection>) node.getOutgoingConnections();
    ExternalDbMapData data = (ExternalDbMapData) node.getExternalData();
    uniqueNameComponent = node.getUniqueName();

    String theDistribution = ElementParameterParser.getValue(node, "__DISTRIBUTION__");
	String theVersion = ElementParameterParser.getValue(node, "__HIVE_VERSION__");

	if("true".equals(ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__"))) {
	    String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
	    for (INode pNode : node.getProcess().getNodesOfType("tHiveConnection")) {
	        if(connection!=null && connection.equals(pNode.getUniqueName())) {
	            theDistribution = ElementParameterParser.getValue(pNode, "__DISTRIBUTION__");
	            theVersion = ElementParameterParser.getValue(pNode, "__HIVE_VERSION__");
	        }
	    }
	}

	org.talend.hadoop.distribution.component.HiveComponent hiveDistrib = null;
	try {
	    hiveDistrib = (org.talend.hadoop.distribution.component.HiveComponent) org.talend.hadoop.distribution.DistributionFactory.buildDistribution(theDistribution, theVersion);
	} catch (java.lang.Exception e) {
	    e.printStackTrace();
	    return "";
	}
	boolean isCustom = hiveDistrib instanceof org.talend.hadoop.distribution.custom.CustomDistribution;

	String hiveServer = ElementParameterParser.getValue(node, "__HIVE_SERVER__");
	String connectionMode = ElementParameterParser.getValue(node, "__CONNECTION_MODE__");
	String fsDefalutName = "fs.default.name";

	boolean setTempPath = "true".equals(ElementParameterParser.getValue(node, "__SET_TEMP_PATH__")); 
	if(setTempPath) { 
		String tempPath = ElementParameterParser.getValue(node, "__TEMP_PATH__"); 
	
    stringBuffer.append(TEXT_1);
    stringBuffer.append(tempPath);
    stringBuffer.append(TEXT_2);
     
	}
	String useExistingConn = ElementParameterParser.getValue(node,"__USE_EXISTING_CONNECTION__");
	if(("false").equals(useExistingConn) && !hiveDistrib.isExecutedThroughWebHCat()) {
		boolean setMapredJT = "true".equals(ElementParameterParser.getValue(node, "__SET_MAPRED_JT__"));
		boolean setNamenode = "true".equals(ElementParameterParser.getValue(node, "__SET_FS_DEFAULT_NAME__"));
		List<Map<String, String>> hadoopProps = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__HADOOP_ADVANCED_PROPERTIES__");

		boolean useYarn = "true".equals(ElementParameterParser.getValue(node, "__USE_YARN__"));

		boolean useKrb = "true".equals(ElementParameterParser.getValue(node, "__USE_KRB__"));
		boolean securityIsEnabled = useKrb && (isCustom || (hiveDistrib.doSupportKerberos() && (("HIVE".equalsIgnoreCase(hiveServer) && "EMBEDDED".equalsIgnoreCase(connectionMode)) || "HIVE2".equalsIgnoreCase(hiveServer))));
		boolean securedStandaloneHive2 = securityIsEnabled && "HIVE2".equalsIgnoreCase(hiveServer) && "STANDALONE".equalsIgnoreCase(connectionMode);
		boolean securedEmbedded = securityIsEnabled && "EMBEDDED".equalsIgnoreCase(connectionMode);
		boolean securedEmbeddedHive2 = securedEmbedded && "HIVE2".equalsIgnoreCase(hiveServer);

		String hivePrincipal = ElementParameterParser.getValue(node, "__HIVE_PRINCIPAL__");
		
        boolean useMapRTicket = ElementParameterParser.getBooleanValue(node, "__USE_MAPRTICKET__");
        String mapRTicketUsername = ElementParameterParser.getValue(node, "__MAPRTICKET_USERNAME__");
        String mapRTicketCluster = ElementParameterParser.getValue(node, "__MAPRTICKET_CLUSTER__");
        String mapRTicketDuration = ElementParameterParser.getValue(node, "__MAPRTICKET_DURATION__");

        boolean setMapRHomeDir = ElementParameterParser.getBooleanValue(node, "__SET_MAPR_HOME_DIR__");
        String mapRHomeDir = ElementParameterParser.getValue(node, "__MAPR_HOME_DIR__");

        boolean setMapRHadoopLogin = ElementParameterParser.getBooleanValue(node, "__SET_HADOOP_LOGIN__");
        String mapRHadoopLogin = ElementParameterParser.getValue(node, "__HADOOP_LOGIN__");

        String passwordFieldName = "";

		if(hadoopProps.size() > 0){
			for(Map<String, String> item : hadoopProps){

    stringBuffer.append(TEXT_3);
    stringBuffer.append(item.get("PROPERTY") );
    stringBuffer.append(TEXT_4);
    stringBuffer.append(item.get("VALUE") );
    stringBuffer.append(TEXT_5);
    
			} 
		}
	
		if(securedEmbedded) {
			String metastoreUrl = ElementParameterParser.getValue(node, "__METASTORE_JDBC_URL__");
			String driverClass = ElementParameterParser.getValue(node, "__METASTORE_CLASSNAME__");
			String metastoreUsername = ElementParameterParser.getValue(node, "__METASTORE_USERNAME__");
			boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
			String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
			String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");

    stringBuffer.append(TEXT_6);
    stringBuffer.append(driverClass);
    stringBuffer.append(TEXT_7);
    if(securedEmbeddedHive2){
				// Disable authorization when using local HiveServer2 in secure mode
				
    stringBuffer.append(TEXT_8);
    
			}else{
				
    stringBuffer.append(TEXT_9);
    
			}
			
    stringBuffer.append(TEXT_10);
    stringBuffer.append(metastoreUrl);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(metastoreUsername);
    stringBuffer.append(TEXT_12);
    
    		passwordFieldName = "__METASTORE_PASSWORD__";
    		
    stringBuffer.append(TEXT_13);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_14);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_16);
    } else {
    stringBuffer.append(TEXT_17);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_18);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_19);
    }
			
			if(securedEmbeddedHive2){
				
    stringBuffer.append(TEXT_20);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HIVESERVER2_LOCAL_PRINCIPAL__"));
    stringBuffer.append(TEXT_21);
    stringBuffer.append(ElementParameterParser.getValue(node, "__HIVESERVER2_LOCAL_KEYTAB__"));
    stringBuffer.append(TEXT_22);
    
			}
			
    stringBuffer.append(TEXT_23);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(hivePrincipal);
    stringBuffer.append(TEXT_25);
    
			if(useKeytab) {

    stringBuffer.append(TEXT_26);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_28);
    
			}
		}

		if(securedStandaloneHive2){
			boolean useKeytab = "true".equals(ElementParameterParser.getValue(node, "__USE_KEYTAB__"));
			String userPrincipal = ElementParameterParser.getValue(node, "__PRINCIPAL__");
			String keytabPath = ElementParameterParser.getValue(node, "__KEYTAB_PATH__");

            if ((isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket) {
                
    stringBuffer.append(TEXT_29);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_30);
    stringBuffer.append(setMapRHadoopLogin ? mapRHadoopLogin : "\"kerberos\"");
    stringBuffer.append(TEXT_31);
    
            }
			if(useKeytab) {

    stringBuffer.append(TEXT_32);
    stringBuffer.append(userPrincipal);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(keytabPath);
    stringBuffer.append(TEXT_34);
    
			}
			if ((isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket) {
                
    stringBuffer.append(TEXT_35);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_39);
    
            }
		}
		
		// Mapr ticket
        if((!securedStandaloneHive2 && !securedEmbedded && (isCustom || hiveDistrib.doSupportMapRTicket()) && useMapRTicket)) {
            passwordFieldName = "__MAPRTICKET_PASSWORD__";
            
    stringBuffer.append(TEXT_40);
    stringBuffer.append(setMapRHomeDir ? mapRHomeDir : "\"/opt/mapr\"" );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_42);
    
            if (setMapRHadoopLogin) {
                
    stringBuffer.append(TEXT_43);
    stringBuffer.append(mapRHadoopLogin);
    stringBuffer.append(TEXT_44);
    
            } else {
                
    stringBuffer.append(TEXT_45);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_46);
    
            }
            if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_47);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_49);
    } else {
    stringBuffer.append(TEXT_50);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_51);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_52);
    }
    
            if(hiveDistrib.doSupportMaprTicketV52API()){
            	
    stringBuffer.append(TEXT_53);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_58);
    
            } else {
            	
    stringBuffer.append(TEXT_59);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(mapRTicketCluster);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(mapRTicketUsername);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(uniqueNameComponent);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(mapRTicketDuration);
    stringBuffer.append(TEXT_64);
    
            }
        }

		if(((isCustom && !useYarn) || (!isCustom && hiveDistrib.isHadoop1())) && setMapredJT) {
			String mapredJT = ElementParameterParser.getValue(node, "__MAPRED_JT__");

    stringBuffer.append(TEXT_65);
    stringBuffer.append(mapredJT);
    stringBuffer.append(TEXT_66);
    
		}

		if(setNamenode) {
			String namenode = ElementParameterParser.getValue(node, "__FS_DEFAULT_NAME__");

    stringBuffer.append(TEXT_67);
    stringBuffer.append(fsDefalutName);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(namenode);
    stringBuffer.append(TEXT_69);
    
		}			
	}   			
	
    List<ExternalDbMapTable> outputTables = data.getOutputTables();

    Map<String, IConnection> nameToOutputConnection = new HashMap<String, IConnection>();
    for (IConnection connection : outputConnections) {
        nameToOutputConnection.put(connection.getUniqueName(), connection);
    }

	Set<String> tablesProcessed = new HashSet<String>();
    int lstOutputTablesSize = outputTables.size();
    for (int i = 0; i < lstOutputTablesSize; i++) {
        ExternalDbMapTable outputTable = outputTables.get(i);
        String outputTableName = outputTable.getName();

			IConnection connection = nameToOutputConnection.get(outputTableName);

        if (connection == null) {
        	continue;
        }

        String sqlQuery = gm.buildSqlSelect((DbMapComponent) node, outputTable.getName());
        
	
    stringBuffer.append(TEXT_70);
    stringBuffer.append(uniqueNameComponent );
    stringBuffer.append(TEXT_71);
    stringBuffer.append( connection.getUniqueName() );
    stringBuffer.append(TEXT_72);
    stringBuffer.append( sqlQuery.replaceAll("[\r\n]", " ") );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(uniqueNameComponent );
    stringBuffer.append(TEXT_74);
    stringBuffer.append( connection.getUniqueName() );
    stringBuffer.append(TEXT_75);
    stringBuffer.append( gm.getQueryColumnsName() );
    stringBuffer.append(TEXT_76);
    
            if(!tablesProcessed.contains(outputTable.getTableName())) {          
            
    stringBuffer.append(TEXT_77);
    stringBuffer.append( outputTable.getTableName() );
    stringBuffer.append(TEXT_78);
           
            } 
			tablesProcessed.add(outputTable.getTableName());
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    stringBuffer.append(TEXT_79);
    return stringBuffer.toString();
  }
}
