package org.talend.designer.codegen.translators.databases.neo4j;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import java.util.Map;

public class TNeo4jOutputRelationshipMainJava
{
  protected static String nl;
  public static synchronized TNeo4jOutputRelationshipMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TNeo4jOutputRelationshipMainJava result = new TNeo4jOutputRelationshipMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "            org.neo4j.graphdb.Transaction tx_";
  protected final String TEXT_2 = " = databaseService_";
  protected final String TEXT_3 = ".beginTx();";
  protected final String TEXT_4 = NL + "                org.neo4j.graphdb.Transaction tx_";
  protected final String TEXT_5 = " = databaseService_";
  protected final String TEXT_6 = ".beginTx();";
  protected final String TEXT_7 = NL + "            if (counter_";
  protected final String TEXT_8 = " % Long.parseLong(";
  protected final String TEXT_9 = ") == 0) {" + NL + "                tx_";
  protected final String TEXT_10 = ".success();" + NL + "                tx_";
  protected final String TEXT_11 = ".finish();" + NL + "                tx_";
  protected final String TEXT_12 = " = databaseService_";
  protected final String TEXT_13 = ".beginTx();" + NL + "            }";
  protected final String TEXT_14 = NL + "                if (counter_";
  protected final String TEXT_15 = " % Long.parseLong(";
  protected final String TEXT_16 = ") == 0) {" + NL + "                    tx_";
  protected final String TEXT_17 = ".success();" + NL + "                    tx_";
  protected final String TEXT_18 = ".finish();" + NL + "                    tx_";
  protected final String TEXT_19 = " = databaseService_";
  protected final String TEXT_20 = ".beginTx();" + NL + "                }";
  protected final String TEXT_21 = NL + "            if (counter_";
  protected final String TEXT_22 = " % Long.parseLong(";
  protected final String TEXT_23 = ") > 0) {" + NL + "                tx_";
  protected final String TEXT_24 = ".success();" + NL + "                tx_";
  protected final String TEXT_25 = ".finish();" + NL + "            }";
  protected final String TEXT_26 = NL + "                if (counter_";
  protected final String TEXT_27 = " % Long.parseLong(";
  protected final String TEXT_28 = ") > 0) {" + NL + "                    tx_";
  protected final String TEXT_29 = ".success();" + NL + "                    tx_";
  protected final String TEXT_30 = ".finish();" + NL + "                }";
  protected final String TEXT_31 = NL + "                org.neo4j.graphdb.Transaction tx_begin_";
  protected final String TEXT_32 = " = databaseService_";
  protected final String TEXT_33 = ".beginTx();" + NL + "                try" + NL + "                {";
  protected final String TEXT_34 = NL + "                tx_begin_";
  protected final String TEXT_35 = ".success();" + NL + "                } // end of Try" + NL + "                finally" + NL + "                {" + NL + "                    tx_begin_";
  protected final String TEXT_36 = ".close();" + NL + "                }";
  protected final String TEXT_37 = NL + "\t\t\torg.neo4j.graphdb.Node startNode_";
  protected final String TEXT_38 = " = startIndexNode_";
  protected final String TEXT_39 = ".get(";
  protected final String TEXT_40 = ", ";
  protected final String TEXT_41 = ".";
  protected final String TEXT_42 = ").getSingle();" + NL + "\t\t\torg.neo4j.graphdb.Node endNode_";
  protected final String TEXT_43 = " = endIndexNode_";
  protected final String TEXT_44 = ".get(";
  protected final String TEXT_45 = ", ";
  protected final String TEXT_46 = ".";
  protected final String TEXT_47 = ").getSingle();" + NL + "\t\t\tif (startNode_";
  protected final String TEXT_48 = " == null) {" + NL + "\t\t\t\tthrow new RuntimeException(\"Start node looked up by index with \" + ";
  protected final String TEXT_49 = " + \" as key and \" + ";
  protected final String TEXT_50 = ".";
  protected final String TEXT_51 = " + \" as value doesn't exist\");" + NL + "\t\t\t}" + NL + "\t\t\tif (endNode_";
  protected final String TEXT_52 = " == null) {" + NL + "\t\t\t\tthrow new RuntimeException(\"End node looked up by index with \" + ";
  protected final String TEXT_53 = " + \" as key and \" + ";
  protected final String TEXT_54 = ".";
  protected final String TEXT_55 = " + \" as value doesn't exist\");" + NL + "\t\t\t}" + NL + "\t\t\t";
  protected final String TEXT_56 = NL + "\t\t\t\torg.neo4j.graphdb.Relationship relationship_";
  protected final String TEXT_57 = " = startNode_";
  protected final String TEXT_58 = ".createRelationshipTo(endNode_";
  protected final String TEXT_59 = ", relationshipType_";
  protected final String TEXT_60 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_61 = NL + "\t\t\t\torg.neo4j.graphdb.Relationship relationship_";
  protected final String TEXT_62 = " = endNode_";
  protected final String TEXT_63 = ".createRelationshipTo(startNode_";
  protected final String TEXT_64 = ", relationshipType_";
  protected final String TEXT_65 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_66 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_67 = " != null) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_68 = NL + "\t\t\t\t\t\t\trelationship_";
  protected final String TEXT_69 = ".setProperty(";
  protected final String TEXT_70 = ", value_";
  protected final String TEXT_71 = "); " + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_72 = NL + "\t\t\t\t\t\t\trelationship_";
  protected final String TEXT_73 = ".setProperty(\"";
  protected final String TEXT_74 = "\", value_";
  protected final String TEXT_75 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_76 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_77 = NL + "\t\t\t\tObject value_";
  protected final String TEXT_78 = " = null;" + NL + "\t\t\t\tif (";
  protected final String TEXT_79 = ".";
  protected final String TEXT_80 = " != null) {" + NL + "\t\t\t\t\tjava.util.Date date_";
  protected final String TEXT_81 = " = (java.util.Date) ";
  protected final String TEXT_82 = ".";
  protected final String TEXT_83 = ";" + NL + "\t\t\t\t\tvalue_";
  protected final String TEXT_84 = " = date_";
  protected final String TEXT_85 = ".getTime();" + NL + "\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_86 = NL + "\t\t\t\tObject value_";
  protected final String TEXT_87 = " = ";
  protected final String TEXT_88 = ".";
  protected final String TEXT_89 = ";" + NL + "\t\t\t\t";
  protected final String TEXT_90 = NL + "    \t\tlong startNode_";
  protected final String TEXT_91 = " = startIndexInserter_";
  protected final String TEXT_92 = ".get(";
  protected final String TEXT_93 = ", ";
  protected final String TEXT_94 = ".";
  protected final String TEXT_95 = ").getSingle();" + NL + "    \t\tlong endNode_";
  protected final String TEXT_96 = " = endIndexInserter_";
  protected final String TEXT_97 = ".get(";
  protected final String TEXT_98 = ", ";
  protected final String TEXT_99 = ".";
  protected final String TEXT_100 = ").getSingle();" + NL + "    \t\tjava.util.Map<String, Object> properties_";
  protected final String TEXT_101 = " = new java.util.HashMap<String, Object>();" + NL + "    \t\t";
  protected final String TEXT_102 = NL + "\t\t\t\t\tif (value_";
  protected final String TEXT_103 = " != null) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_104 = NL + "\t\t\t\t\t\t\tproperties_";
  protected final String TEXT_105 = ".put(";
  protected final String TEXT_106 = ", value_";
  protected final String TEXT_107 = "); " + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_108 = NL + "\t\t\t\t\t\t\tproperties_";
  protected final String TEXT_109 = ".put(\"";
  protected final String TEXT_110 = "\", value_";
  protected final String TEXT_111 = ");" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_112 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\t";
  protected final String TEXT_113 = NL + "\t\t\t\tinserter_";
  protected final String TEXT_114 = ".createRelationship(startNode_";
  protected final String TEXT_115 = ", endNode_";
  protected final String TEXT_116 = ", relationshipType_";
  protected final String TEXT_117 = ", properties_";
  protected final String TEXT_118 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_119 = NL + "\t\t\t\tinserter_";
  protected final String TEXT_120 = ".createRelationship(endNode_";
  protected final String TEXT_121 = ", startNode_";
  protected final String TEXT_122 = ", relationshipType_";
  protected final String TEXT_123 = ", properties_";
  protected final String TEXT_124 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_125 = NL + "    \t\tif (";
  protected final String TEXT_126 = ".";
  protected final String TEXT_127 = " != null) {" + NL + "    \t\t";
  protected final String TEXT_128 = NL + "    \t\tif (";
  protected final String TEXT_129 = ".";
  protected final String TEXT_130 = " != null) {" + NL + "    \t\t";
  protected final String TEXT_131 = NL + "    \t\tif (";
  protected final String TEXT_132 = ".";
  protected final String TEXT_133 = " != null && ";
  protected final String TEXT_134 = ".";
  protected final String TEXT_135 = " != null) {" + NL + "    \t\t";
  protected final String TEXT_136 = NL + "    \t\t{" + NL + "    \t\t";
  protected final String TEXT_137 = NL + "\t    \t}" + NL + "\t    \t";
  protected final String TEXT_138 = NL + NL + "\tcounter_";
  protected final String TEXT_139 = "++;" + NL + "" + NL + "\t";
  protected final String TEXT_140 = NL + NL + "\tnb_line_";
  protected final String TEXT_141 = "++;";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
class Neo4JTransactionHelper {
    private INode node;
    private String cid;
    private String dbVersion;
    private boolean batchImport;
    private boolean remoteServer;
    private String commitEvery;

    public Neo4JTransactionHelper(INode node) {
        this.node = node;
        this.cid = node.getUniqueName();

        this.batchImport = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__BATCH_IMPORT__"));
        this.commitEvery = ElementParameterParser.getValue(node, "__COMMIT_EVERY__");
        boolean useExistingConnection = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__USE_EXISTING_CONNECTION__"));

        if (useExistingConnection) {
            String connection = ElementParameterParser.getValue(node, "__CONNECTION__");
            for(INode targetNode : node.getProcess().getNodesOfType("tNeo4jConnection")){
                if (targetNode.getUniqueName().equals(connection)) {
                    this.dbVersion = ElementParameterParser.getValue(targetNode, "__DB_VERSION__");
                    this.remoteServer = "true".equalsIgnoreCase(ElementParameterParser.getValue(targetNode, "__REMOTE_SERVER__"));
                    break;
                }
            }
        } else {
            this.dbVersion = ElementParameterParser.getValue(node,"__DB_VERSION__");
            this.remoteServer = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__REMOTE_SERVER__"));
        }

    }

    public String getDbVersion() {
        return this.dbVersion;
    }

    public void startTransaction() {
        if ("NEO4J_1_X_X".equals(this.dbVersion)) {
            
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    
        } else { // NEO4J_2_1_X or NEO4J_2_2_X
            if (!this.batchImport) {
                
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_6);
    
            }
        }
    }

    public void restartTransaction() {
        if ("NEO4J_1_X_X".equals(this.dbVersion)) {
            
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(commitEvery);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_13);
    
        } else { // NEO4J_2_1_X or NEO4J_2_2_X
            if (!this.batchImport) {
                
    stringBuffer.append(TEXT_14);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(commitEvery);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(this.cid);
    stringBuffer.append(TEXT_20);
    
            }
        }
    }

    public void closeTransaction() {
        if ("NEO4J_1_X_X".equals(this.dbVersion)) {
            
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(commitEvery);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    
        } else { // NEO4J_2_1_X or NEO4J_2_2_X
            if (!this.batchImport) {
                
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(commitEvery);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_30);
    
            }
        }
    }

    public void startEmbeddedTransaction() {
        if ("NEO4J_1_X_X".equals(this.dbVersion)) {
            // Nothing for neo4j 1.X.X
        } else { // NEO4J_2_1_X or NEO4J_2_2_X
            if (!remoteServer) {
                
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    
            }
        }
    }

    public void closeEmbeddedTransaction() {
        if ("NEO4J_1_X_X".equals(this.dbVersion)) {
            // Nothing for neo4j 1.X.X
        } else { // NEO4J_2_1_X or NEO4J_2_2_X
            if (!remoteServer) {
                
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    
            }
        }
    }
}

    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    final String cid = node.getUniqueName();

    Neo4JTransactionHelper neo4JTransactionHelper = new Neo4JTransactionHelper(node);

    List<IMetadataColumn> columnList = null;
    List<IMetadataTable> metadatas = node.getMetadataList();
    IMetadataTable metadata = null;
    
    if (metadatas != null && metadatas.size() > 0) {
    	metadata = metadatas.get(0);
    }	
    
    List<? extends IConnection> inputs = node.getIncomingConnections();
    List<? extends IConnection> outputs = node.getOutgoingSortedConnections();
    String incomingConnName = null;
    String outgoingConnName = null;
    
    if (inputs.size() > 0) {
    	IConnection in = inputs.get(0);
    	incomingConnName = in.getName();
    }
    
    if (outputs.size() > 0) {
    	IConnection out = outputs.get(0);
    	outgoingConnName = out.getName();
    }
    
    String commitEvery = ElementParameterParser.getValue(node, "__COMMIT_EVERY__");
    boolean batchImport = "true".equalsIgnoreCase(ElementParameterParser.getValue(node, "__BATCH_IMPORT__"));
    
    final String startIndexKey = ElementParameterParser.getValue(node, "__START_INDEX_KEY__");
    final String startIndexValueColumn = ElementParameterParser.getValue(node, "__START_INDEX_VALUE__");
    
    final String endIndexKey = ElementParameterParser.getValue(node, "__END_INDEX_KEY__");
    final String endIndexValueColumn = ElementParameterParser.getValue(node, "__END_INDEX_VALUE__");
    
    final String relationshipDirection = ElementParameterParser.getValue(node, "__RELATIONSHIP_DIRECTION__");
    
    final List<Map<String, String>> mappingTable = (List<Map<String,String>>) ElementParameterParser.getObjectValue(node, "__MAPPING__");
    
    final IMetadataColumn startColumn = metadata.getColumn(startIndexValueColumn);
    final IMetadataColumn endColumn = metadata.getColumn(endIndexValueColumn);

    
    class Neo4jRelationshipInserter {
    
    	private String incomingConnName;
    	private IMetadataTable metadata;
    	
    	public Neo4jRelationshipInserter(String incomingConnName, IMetadataTable metadata) {
    		this.incomingConnName = incomingConnName;
			this.metadata = metadata;
    	} 
    
    	public void insert() {
    		
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(startIndexKey);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(startColumn);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(endIndexKey);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(endColumn);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(startIndexKey);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(startColumn);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(startIndexKey);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(startColumn);
    stringBuffer.append(TEXT_55);
    
			if (relationshipDirection.equals("OUTGOING")) {
				
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    
			} else {
				
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_65);
    
			}
			for (Map<String, String> row : mappingTable) {
				String columnName = row.get("INPUT_COLUMN");
				String propertyName = row.get("PROPERTY_NAME");
				if (columnName != null && !columnName.isEmpty()) {
					IMetadataColumn column = metadata.getColumn(columnName);
					computeValue(column);
					
    stringBuffer.append(TEXT_66);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_67);
    
						if (propertyName != null && !propertyName.isEmpty()) {
							
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(propertyName);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_71);
    
						} else {
							
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_73);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_75);
    
						}
						
    stringBuffer.append(TEXT_76);
    
				}
			}
    	}
    
    	public void computeValue(IMetadataColumn column) {
    		if (column.getTalendType().equals("id_Date")) {
				
    stringBuffer.append(TEXT_77);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    
			} else {
				
    stringBuffer.append(TEXT_86);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_87);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_89);
    
			}
    	}
    
    	public void batchInsert() {
    		
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_92);
    stringBuffer.append(startIndexKey);
    stringBuffer.append(TEXT_93);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(startColumn);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(endIndexKey);
    stringBuffer.append(TEXT_98);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_99);
    stringBuffer.append(endColumn);
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_101);
    
    		for (Map<String, String> row : mappingTable) {
				String columnName = row.get("INPUT_COLUMN");
				String propertyName = row.get("PROPERTY_NAME");
				if (columnName != null && !columnName.isEmpty()) {
					IMetadataColumn column = metadata.getColumn(columnName);
					computeValue(column);
					
    stringBuffer.append(TEXT_102);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_103);
    
						if (propertyName != null && !propertyName.isEmpty()) {
							
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(propertyName);
    stringBuffer.append(TEXT_106);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_107);
    
						} else {
							
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(column);
    stringBuffer.append(TEXT_111);
    
						}
						
    stringBuffer.append(TEXT_112);
    
				}
			}
			if (relationshipDirection.equals("OUTGOING")) {
				
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    
			} else {
				
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_124);
    
			}
    	}
    }

    Neo4jRelationshipInserter relationshipInserter = new Neo4jRelationshipInserter(incomingConnName, metadata);

    if (startColumn != null && endColumn != null) {
    	// Create dynamique condition according to start and end columns state
    	if (startColumn.isNullable() && !endColumn.isNullable()) {
    		
    stringBuffer.append(TEXT_125);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(startColumn);
    stringBuffer.append(TEXT_127);
    
    	} else if (!startColumn.isNullable() && endColumn.isNullable()) {
    		
    stringBuffer.append(TEXT_128);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(endColumn);
    stringBuffer.append(TEXT_130);
    
    	} else if (startColumn.isNullable() && endColumn.isNullable()) {
    		
    stringBuffer.append(TEXT_131);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(startColumn);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(incomingConnName);
    stringBuffer.append(TEXT_134);
    stringBuffer.append(endColumn);
    stringBuffer.append(TEXT_135);
    
    	} else {
    		
    stringBuffer.append(TEXT_136);
    
    	}
    			if (batchImport) {
	    			relationshipInserter.batchInsert();
	    		} else {
	    			relationshipInserter.insert();
	    		} 
    stringBuffer.append(TEXT_137);
    
    }
    
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    
	neo4JTransactionHelper.restartTransaction();
	
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_141);
    return stringBuffer.toString();
  }
}
