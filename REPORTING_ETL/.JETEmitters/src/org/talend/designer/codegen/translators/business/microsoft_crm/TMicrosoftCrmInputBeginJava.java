package org.talend.designer.codegen.translators.business.microsoft_crm;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TMicrosoftCrmInputBeginJava
{
  protected static String nl;
  public static synchronized TMicrosoftCrmInputBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TMicrosoftCrmInputBeginJava result = new TMicrosoftCrmInputBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\tint nb_line_";
  protected final String TEXT_3 = " = 0;";
  protected final String TEXT_4 = NL + "class CrmClass2JavaClassHelper{" + NL + "    public Object extractValue(Object obj){" + NL + "        Object value = null;" + NL + "        if(obj instanceof com.microsoft.schemas.xrm._2011.contracts.OptionSetValue){" + NL + "            //Integer" + NL + "            value = ((com.microsoft.schemas.xrm._2011.contracts.OptionSetValue)obj).getValue();" + NL + "        }else if(obj instanceof com.microsoft.schemas._2003._10.serialization.Guid){" + NL + "            //String" + NL + "            value = ((com.microsoft.schemas._2003._10.serialization.Guid)obj).getValue();" + NL + "        }else if(obj instanceof com.microsoft.schemas.xrm._2011.contracts.Money){" + NL + "            //BigDecimal" + NL + "            value = ((com.microsoft.schemas.xrm._2011.contracts.Money)obj).getValue();" + NL + "        }else if(obj instanceof com.microsoft.schemas.xrm._2011.contracts.EntityReference){" + NL + "            //JOSN String" + NL + "            com.microsoft.schemas.xrm._2011.contracts.EntityReference entityRef = (com.microsoft.schemas.xrm._2011.contracts.EntityReference)obj;" + NL + "            StringBuilder strBuilder = new StringBuilder(\"{\\\"guid\\\":\\\"\");" + NL + "                          strBuilder.append(entityRef.getId().getValue());" + NL + "                          strBuilder.append(\"\\\",\\\"logical_name\\\":\\\"\");" + NL + "                          strBuilder.append(entityRef.getLogicalName());" + NL + "                          strBuilder.append(\"\\\",\\\"name\\\":\\\"\");" + NL + "                          strBuilder.append(entityRef.getName());" + NL + "                          strBuilder.append(\"\\\"}\");" + NL + "           value = strBuilder.toString();" + NL + "        }else if(obj instanceof com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl){" + NL + "            //java.util.Date" + NL + "            value = ((com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl)obj).toGregorianCalendar().getTime();" + NL + "        }else{" + NL + "           //Object (EntityCollection)" + NL + "           value = obj;" + NL + "        }" + NL + "        return value;" + NL + "    }" + NL + "}" + NL + "" + NL + "CrmClass2JavaClassHelper crmClazzHelper_";
  protected final String TEXT_5 = " = new CrmClass2JavaClassHelper();" + NL;
  protected final String TEXT_6 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_7 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_8 = ");";
  protected final String TEXT_9 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_10 = " = ";
  protected final String TEXT_11 = "; ";
  protected final String TEXT_12 = NL + "                    System.setProperty(\"javax.net.ssl.trustStore\", ";
  protected final String TEXT_13 = ");";
  protected final String TEXT_14 = NL + "                    com.talend.microsoft.crm._2015_.utils.MsCrmWsdl" + NL + "                                         msCrmWsdl_";
  protected final String TEXT_15 = " = new com.talend.microsoft.crm._2015_.utils.MsCrmWsdl(";
  protected final String TEXT_16 = NL + "                                         ";
  protected final String TEXT_17 = ", ";
  protected final String TEXT_18 = NL + "                                         );" + NL + "                    com.talend.microsoft.crm._2015_.utils.DynamicsCRMConnector" + NL + "                                        crmConnector_";
  protected final String TEXT_19 = " = new com.talend.microsoft.crm._2015_.utils.DynamicsCRMConnector(";
  protected final String TEXT_20 = NL + "                                        ";
  protected final String TEXT_21 = ", decryptedPassword_";
  protected final String TEXT_22 = ", msCrmWsdl_";
  protected final String TEXT_23 = NL + "                                        );" + NL + "                   com.microsoft.schemas.xrm._2011.contracts.services.IOrganizationService" + NL + "                                                   iorgService_";
  protected final String TEXT_24 = " = crmConnector_";
  protected final String TEXT_25 = ".getOrganizationService();" + NL;
  protected final String TEXT_26 = NL + "                    com.microsoft.schemas.xrm._2011.contracts.QueryExpression" + NL + "                                                    queryExp_";
  protected final String TEXT_27 = " = new com.microsoft.schemas.xrm._2011.contracts.QueryExpression();" + NL + "                    com.microsoft.schemas.xrm._2011.contracts.ColumnSet" + NL + "                                                    columnSet_";
  protected final String TEXT_28 = " = new com.microsoft.schemas.xrm._2011.contracts.ColumnSet();" + NL + "                    com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring" + NL + "                                                    arrOfStr_";
  protected final String TEXT_29 = " = new com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring();" + NL;
  protected final String TEXT_30 = NL + "                       com.microsoft.schemas.xrm._2011.contracts.FilterExpression" + NL + "                                     filterExp_";
  protected final String TEXT_31 = " = new com.microsoft.schemas.xrm._2011.contracts.FilterExpression();" + NL + "                       com.microsoft.schemas.xrm._2011.contracts.ArrayOfConditionExpression" + NL + "                                     arrOfAnyConditionExp_";
  protected final String TEXT_32 = " = new com.microsoft.schemas.xrm._2011.contracts.ArrayOfConditionExpression();" + NL;
  protected final String TEXT_33 = NL + "                        com.microsoft.schemas.xrm._2011.contracts.ConditionExpression" + NL + "                                      conditionExp_";
  protected final String TEXT_34 = " = new com.microsoft.schemas.xrm._2011.contracts.ConditionExpression();" + NL + "                                      conditionExp_";
  protected final String TEXT_35 = ".setAttributeName(\"";
  protected final String TEXT_36 = "\");" + NL + "                                      conditionExp_";
  protected final String TEXT_37 = ".setOperator(" + NL + "                                            com.microsoft.schemas.xrm._2011.contracts.ConditionOperator.fromValue(" + NL + "                                            \"";
  protected final String TEXT_38 = "\"));" + NL + "                        com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfanyType" + NL + "                                      arrOfAnyType_";
  protected final String TEXT_39 = " = new com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfanyType();" + NL + "                                      arrOfAnyType_";
  protected final String TEXT_40 = ".getAnyTypes().add(";
  protected final String TEXT_41 = ");" + NL + "                                      conditionExp_";
  protected final String TEXT_42 = ".setValues(arrOfAnyType_";
  protected final String TEXT_43 = ");" + NL + "                                      arrOfAnyConditionExp_";
  protected final String TEXT_44 = ".getConditionExpressions().add(conditionExp_";
  protected final String TEXT_45 = ");" + NL;
  protected final String TEXT_46 = NL + "                                      filterExp_";
  protected final String TEXT_47 = ".setConditions(arrOfAnyConditionExp_";
  protected final String TEXT_48 = ");" + NL + "                                      filterExp_";
  protected final String TEXT_49 = ".setFilterOperator(" + NL + "                                                    com.microsoft.schemas.xrm._2011.contracts.LogicalOperator.fromValue(\"";
  protected final String TEXT_50 = "\")" + NL + "                                                    );" + NL + "                                      queryExp_";
  protected final String TEXT_51 = ".setCriteria(filterExp_";
  protected final String TEXT_52 = ");";
  protected final String TEXT_53 = NL + "                                                    arrOfStr_";
  protected final String TEXT_54 = ".getStrings().addAll(" + NL + "                                                            java.util.Arrays.asList(";
  protected final String TEXT_55 = ")" + NL + "                                                    );" + NL + "                                      columnSet_";
  protected final String TEXT_56 = ".setColumns(arrOfStr_";
  protected final String TEXT_57 = ");" + NL + "                                      queryExp_";
  protected final String TEXT_58 = ".setColumnSet(columnSet_";
  protected final String TEXT_59 = ");" + NL + "                                      queryExp_";
  protected final String TEXT_60 = ".setEntityName(\"";
  protected final String TEXT_61 = "\");" + NL;
  protected final String TEXT_62 = NL + "                \tcom.microsoft.schemas.xrm._2011.contracts.PagingInfo pagingInfo_";
  protected final String TEXT_63 = " = new com.microsoft.schemas.xrm._2011.contracts.PagingInfo();" + NL + "                \tint pageNumber_";
  protected final String TEXT_64 = " = 1;" + NL + "                \tpagingInfo_";
  protected final String TEXT_65 = ".setPageNumber(pageNumber_";
  protected final String TEXT_66 = ");" + NL + "" + NL + "                \tqueryExp_";
  protected final String TEXT_67 = ".setPageInfo(pagingInfo_";
  protected final String TEXT_68 = ");" + NL + "                \t" + NL + "                \tboolean fetchMore_";
  protected final String TEXT_69 = " = true;" + NL + "                \twhile (fetchMore_";
  protected final String TEXT_70 = ") {" + NL + "                \t" + NL + "                    com.microsoft.schemas.xrm._2011.contracts.EntityCollection" + NL + "                                  entityCollection_";
  protected final String TEXT_71 = " = iorgService_";
  protected final String TEXT_72 = ".retrieveMultiple(queryExp_";
  protected final String TEXT_73 = ");" + NL + "                                  " + NL + "                    String pagingCookie_";
  protected final String TEXT_74 = " = entityCollection_";
  protected final String TEXT_75 = ".getPagingCookie();" + NL + "                    fetchMore_";
  protected final String TEXT_76 = " = entityCollection_";
  protected final String TEXT_77 = ".isMoreRecords() != null" + NL + "                            ? entityCollection_";
  protected final String TEXT_78 = ".isMoreRecords() : false;" + NL + "                    List<com.microsoft.schemas.xrm._2011.contracts.Entity>" + NL + "                                  entityList_";
  protected final String TEXT_79 = " = entityCollection_";
  protected final String TEXT_80 = ".getEntities().getEntities();" + NL + "                    java.util.Map<String,Object> map_";
  protected final String TEXT_81 = " = new java.util.HashMap<String,Object>();" + NL + "                    //Entity For Loop Begin" + NL + "                    for(com.microsoft.schemas.xrm._2011.contracts.Entity entity: entityList_";
  protected final String TEXT_82 = "){" + NL + "                            nb_line_";
  protected final String TEXT_83 = " ++;" + NL + "                            map_";
  protected final String TEXT_84 = ".clear();" + NL + "                            List<org.datacontract.schemas._2004._07.system_collections.KeyValuePairOfstringanyType>" + NL + "                                     props = entity.getAttributes().getKeyValuePairOfstringanyTypes();" + NL + "                            for(org.datacontract.schemas._2004._07.system_collections.KeyValuePairOfstringanyType prop: props){" + NL + "                                String key = prop.getKey(); Object value = prop.getValue();" + NL + "                                map_";
  protected final String TEXT_85 = ".put(key,value);" + NL + "                            }";
  protected final String TEXT_86 = NL + "                                    if(map_";
  protected final String TEXT_87 = ".get(\"";
  protected final String TEXT_88 = "\") != null){" + NL + "                                        Object obj_";
  protected final String TEXT_89 = " = crmClazzHelper_";
  protected final String TEXT_90 = ".extractValue(map_";
  protected final String TEXT_91 = ".get(\"";
  protected final String TEXT_92 = "\"));";
  protected final String TEXT_93 = NL + "                                            ";
  protected final String TEXT_94 = ".";
  protected final String TEXT_95 = " = obj_";
  protected final String TEXT_96 = ".toString();";
  protected final String TEXT_97 = NL + "                                           ";
  protected final String TEXT_98 = ".";
  protected final String TEXT_99 = " = map_";
  protected final String TEXT_100 = ".get(\"";
  protected final String TEXT_101 = "\");";
  protected final String TEXT_102 = NL + "                                            if(obj_";
  protected final String TEXT_103 = " instanceof java.util.Date){";
  protected final String TEXT_104 = NL + "                                                 ";
  protected final String TEXT_105 = ".";
  protected final String TEXT_106 = " = (java.util.Date)obj_";
  protected final String TEXT_107 = ";" + NL + "                                            }else{";
  protected final String TEXT_108 = NL + "                                                 ";
  protected final String TEXT_109 = ".";
  protected final String TEXT_110 = " = ParserUtils.parseTo_Date(obj_";
  protected final String TEXT_111 = ".toString(), ";
  protected final String TEXT_112 = ");" + NL + "                                            }";
  protected final String TEXT_113 = NL + "                                            if(obj_";
  protected final String TEXT_114 = " instanceof java.lang.Integer){";
  protected final String TEXT_115 = NL + "                                                ";
  protected final String TEXT_116 = ".";
  protected final String TEXT_117 = " = (java.lang.Integer)obj_";
  protected final String TEXT_118 = ";" + NL + "                                            }else{";
  protected final String TEXT_119 = NL + "                                                ";
  protected final String TEXT_120 = ".";
  protected final String TEXT_121 = " = ParserUtils.parseTo_Integer(obj_";
  protected final String TEXT_122 = ".toString());" + NL + "                                            }";
  protected final String TEXT_123 = NL + "                                           if(obj_";
  protected final String TEXT_124 = " instanceof java.math.BigDecimal){";
  protected final String TEXT_125 = NL + "                                             ";
  protected final String TEXT_126 = ".";
  protected final String TEXT_127 = " = (java.math.BigDecimal)obj_";
  protected final String TEXT_128 = ";" + NL + "                                           }else{";
  protected final String TEXT_129 = NL + "                                             ";
  protected final String TEXT_130 = ".";
  protected final String TEXT_131 = " = ParserUtils.parseTo_BigDecimal(obj_";
  protected final String TEXT_132 = ".toString());" + NL + "                                           }";
  protected final String TEXT_133 = NL + "                                            ";
  protected final String TEXT_134 = ".";
  protected final String TEXT_135 = " = ParserUtils.parseTo_";
  protected final String TEXT_136 = "(obj_";
  protected final String TEXT_137 = ".toString());";
  protected final String TEXT_138 = NL + "                                    }else{";
  protected final String TEXT_139 = NL + "                                        ";
  protected final String TEXT_140 = ".";
  protected final String TEXT_141 = " = ";
  protected final String TEXT_142 = ";" + NL + "                                    }";
  protected final String TEXT_143 = NL + "\t\t\tSystem.setProperty(\"org.apache.commons.logging.Log\", \"org.apache.commons.logging.impl.SimpleLog\");" + NL + "\t\t    System.setProperty(\"org.apache.commons.logging.simplelog.showdatetime\", \"true\");" + NL + "\t\t    System.setProperty(\"org.apache.commons.logging.simplelog.log.httpclient.wire\", \"debug\");" + NL + "\t\t    System.setProperty(\"org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient\", \"debug\");" + NL + "\t\t\t";
  protected final String TEXT_144 = NL + "\t\t\tSystem.setProperty(\"org.apache.commons.logging.Log\", \"org.apache.commons.logging.impl.NoOpLog\");" + NL + "\t\t\t";
  protected final String TEXT_145 = NL + NL + "\t\t\t";
  protected final String TEXT_146 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_147 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_148 = ");";
  protected final String TEXT_149 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_150 = " = ";
  protected final String TEXT_151 = "; ";
  protected final String TEXT_152 = NL + NL + "\t\t\t";
  protected final String TEXT_153 = NL + "\t\t\t\t\torg.apache.commons.httpclient.auth.AuthPolicy.registerAuthScheme(org.apache.commons.httpclient.auth.AuthPolicy.NTLM, org.talend.mscrm.login.ntlm.JCIFS_NTLMScheme.class);" + NL + "" + NL + "\t\t\t\t\tcom.microsoft.crm4.webserviceTest.CrmServiceStub service_";
  protected final String TEXT_154 = " = new com.microsoft.crm4.webserviceTest.CrmServiceStub(";
  protected final String TEXT_155 = ");" + NL + "\t\t\t\t\torg.apache.axis2.client.Options options_";
  protected final String TEXT_156 = " = service_";
  protected final String TEXT_157 = "._getServiceClient().getOptions();" + NL + "\t\t\t\t\torg.apache.axis2.transport.http.HttpTransportProperties.Authenticator auth_";
  protected final String TEXT_158 = " = new org.apache.axis2.transport.http.HttpTransportProperties.Authenticator();" + NL + "" + NL + "\t\t\t\t\tList<String> authSchemes_";
  protected final String TEXT_159 = " = new java.util.ArrayList<String>();" + NL + "\t\t\t\t\tauthSchemes_";
  protected final String TEXT_160 = ".add(org.apache.axis2.transport.http.HttpTransportProperties.Authenticator.NTLM);" + NL + "\t\t\t\t\tauth_";
  protected final String TEXT_161 = " .setAuthSchemes(authSchemes_";
  protected final String TEXT_162 = ");" + NL + "" + NL + "\t\t            auth_";
  protected final String TEXT_163 = " .setUsername(";
  protected final String TEXT_164 = ");" + NL + "\t\t            auth_";
  protected final String TEXT_165 = " .setPassword(decryptedPassword_";
  protected final String TEXT_166 = ");" + NL + "\t\t            auth_";
  protected final String TEXT_167 = " .setHost(";
  protected final String TEXT_168 = ");" + NL + "\t\t            auth_";
  protected final String TEXT_169 = " .setPort(";
  protected final String TEXT_170 = ");" + NL + "\t\t            auth_";
  protected final String TEXT_171 = " .setDomain(";
  protected final String TEXT_172 = ");" + NL + "\t\t            auth_";
  protected final String TEXT_173 = " .setPreemptiveAuthentication(false);" + NL + "" + NL + "\t\t            options_";
  protected final String TEXT_174 = " .setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth_";
  protected final String TEXT_175 = ");" + NL + "\t\t           \toptions_";
  protected final String TEXT_176 = " .setProperty(org.apache.axis2.transport.http.HTTPConstants.REUSE_HTTP_CLIENT, \"";
  protected final String TEXT_177 = "\");" + NL + "" + NL + "" + NL + "\t\t            options_";
  protected final String TEXT_178 = " .setUserName(";
  protected final String TEXT_179 = ");" + NL + "\t\t            options_";
  protected final String TEXT_180 = " .setPassword(decryptedPassword_";
  protected final String TEXT_181 = ");" + NL + "\t\t            options_";
  protected final String TEXT_182 = " .setTimeOutInMilliSeconds(Long.valueOf(";
  protected final String TEXT_183 = "));" + NL + "" + NL + "\t\t            options_";
  protected final String TEXT_184 = " .setProperty(org.apache.axis2.transport.http.HTTPConstants.SO_TIMEOUT,new Integer(";
  protected final String TEXT_185 = "));" + NL + "\t\t            options_";
  protected final String TEXT_186 = " .setProperty(org.apache.axis2.transport.http.HTTPConstants.CONNECTION_TIMEOUT, new Integer(";
  protected final String TEXT_187 = "));" + NL + "" + NL + "\t\t            com.microsoft.schemas.crm._2007.webservices.CrmAuthenticationTokenDocument catd_";
  protected final String TEXT_188 = " = com.microsoft.schemas.crm._2007.webservices.CrmAuthenticationTokenDocument.Factory.newInstance();" + NL + "\t\t            com.microsoft.schemas.crm._2007.coretypes.CrmAuthenticationToken token_";
  protected final String TEXT_189 = " = com.microsoft.schemas.crm._2007.coretypes.CrmAuthenticationToken.Factory.newInstance();" + NL + "\t\t            token_";
  protected final String TEXT_190 = ".setAuthenticationType(0);" + NL + "\t\t            token_";
  protected final String TEXT_191 = ".setOrganizationName(";
  protected final String TEXT_192 = ");" + NL + "\t\t            catd_";
  protected final String TEXT_193 = ".setCrmAuthenticationToken(token_";
  protected final String TEXT_194 = ");" + NL + "\t\t        ";
  protected final String TEXT_195 = NL + "\t\t        \tcom.microsoft.crm4.webserviceTest.CrmServiceStub service_";
  protected final String TEXT_196 = " = new com.microsoft.crm4.webserviceTest.CrmServiceStub(\"https://\" + ";
  protected final String TEXT_197 = " + \"/MSCrmServices/2007/CrmService.asmx\");" + NL + "\t\t\t\t\torg.apache.axis2.client.Options options_";
  protected final String TEXT_198 = " = service_";
  protected final String TEXT_199 = "._getServiceClient().getOptions();" + NL + "\t\t        \torg.talend.mscrm.login.passport.MsDynamicsWrapper msDynamicsWrapper_";
  protected final String TEXT_200 = " = new org.talend.mscrm.login.passport.MsDynamicsWrapper(";
  protected final String TEXT_201 = ",";
  protected final String TEXT_202 = ",";
  protected final String TEXT_203 = ",decryptedPassword_";
  protected final String TEXT_204 = ");" + NL + "\t\t\t\t\tmsDynamicsWrapper_";
  protected final String TEXT_205 = ".connect();" + NL + "\t\t\t\t\tString crmTicket_";
  protected final String TEXT_206 = " = msDynamicsWrapper_";
  protected final String TEXT_207 = ".getCrmTicket();" + NL + "" + NL + "\t\t        \toptions_";
  protected final String TEXT_208 = " .setProperty(org.apache.axis2.transport.http.HTTPConstants.REUSE_HTTP_CLIENT, \"";
  protected final String TEXT_209 = "\");" + NL + "\t\t        \toptions_";
  protected final String TEXT_210 = " .setTimeOutInMilliSeconds(Long.valueOf(";
  protected final String TEXT_211 = "));" + NL + "" + NL + "\t\t            options_";
  protected final String TEXT_212 = " .setProperty(org.apache.axis2.transport.http.HTTPConstants.SO_TIMEOUT,new Integer(";
  protected final String TEXT_213 = "));" + NL + "\t\t            options_";
  protected final String TEXT_214 = " .setProperty(org.apache.axis2.transport.http.HTTPConstants.CONNECTION_TIMEOUT, new Integer(";
  protected final String TEXT_215 = "));" + NL + "" + NL + "\t\t        \tcom.microsoft.schemas.crm._2007.webservices.CrmAuthenticationTokenDocument catd_";
  protected final String TEXT_216 = " = com.microsoft.schemas.crm._2007.webservices.CrmAuthenticationTokenDocument.Factory.newInstance();" + NL + "\t\t            com.microsoft.schemas.crm._2007.coretypes.CrmAuthenticationToken token_";
  protected final String TEXT_217 = " = com.microsoft.schemas.crm._2007.coretypes.CrmAuthenticationToken.Factory.newInstance();" + NL + "\t\t            token_";
  protected final String TEXT_218 = ".setAuthenticationType(1);" + NL + "\t\t            token_";
  protected final String TEXT_219 = ".setOrganizationName(";
  protected final String TEXT_220 = ");" + NL + "\t\t            token_";
  protected final String TEXT_221 = ".setCrmTicket(crmTicket_";
  protected final String TEXT_222 = ");" + NL + "\t\t            catd_";
  protected final String TEXT_223 = ".setCrmAuthenticationToken(token_";
  protected final String TEXT_224 = ");" + NL + "\t\t        ";
  protected final String TEXT_225 = NL + NL + "\t     \t\t";
  protected final String TEXT_226 = NL + "\t        \tcom.microsoft.schemas.crm._2006.query.QueryExpression query_";
  protected final String TEXT_227 = " = com.microsoft.schemas.crm._2006.query.QueryExpression.Factory.newInstance();" + NL + "\t        \tcom.microsoft.schemas.crm._2006.query.ColumnSet cols_";
  protected final String TEXT_228 = " = com.microsoft.schemas.crm._2006.query.ColumnSet.Factory.newInstance();" + NL + "\t        \tcom.microsoft.schemas.crm._2006.query.ArrayOfString aos_";
  protected final String TEXT_229 = " = com.microsoft.schemas.crm._2006.query.ArrayOfString.Factory.newInstance();" + NL + "\t       \t\t";
  protected final String TEXT_230 = NL + "\t            \t\tcom.microsoft.schemas.crm._2006.query.ConditionExpression condition_";
  protected final String TEXT_231 = " = com.microsoft.schemas.crm._2006.query.ConditionExpression.Factory.newInstance();" + NL + "\t            \t\tcondition_";
  protected final String TEXT_232 = ".setAttributeName(\"";
  protected final String TEXT_233 = "\");" + NL + "\t            \t\tcondition_";
  protected final String TEXT_234 = ".setOperator(com.microsoft.schemas.crm._2006.query.ConditionOperator.Enum.forString(\"";
  protected final String TEXT_235 = "\"));" + NL + "\t        \t\t\t";
  protected final String TEXT_236 = NL + "\t            \t\t\tcom.microsoft.schemas.crm._2006.query.ArrayOfAnyType values_";
  protected final String TEXT_237 = " = com.microsoft.schemas.crm._2006.query.ArrayOfAnyType.Factory.newInstance();" + NL + "\t            \t\t\torg.xmlsoap.schemas.soap.encoding.String StringValue_";
  protected final String TEXT_238 = " = org.xmlsoap.schemas.soap.encoding.String.Factory.newInstance();" + NL + "\t            \t\t\tStringValue_";
  protected final String TEXT_239 = ".setStringValue(";
  protected final String TEXT_240 = ");" + NL + "\t            \t\t\tvalues_";
  protected final String TEXT_241 = ".setValueArray(new org.xmlsoap.schemas.soap.encoding.String[] { StringValue_";
  protected final String TEXT_242 = " });" + NL + "\t            \t\t\tcondition_";
  protected final String TEXT_243 = ".setValues(values_";
  protected final String TEXT_244 = ");" + NL + "\t       \t\t\t\t";
  protected final String TEXT_245 = NL + "\t        \t\tcom.microsoft.schemas.crm._2006.query.ArrayOfConditionExpression conditions_";
  protected final String TEXT_246 = " = com.microsoft.schemas.crm._2006.query.ArrayOfConditionExpression.Factory.newInstance();" + NL + "\t        \t\tconditions_";
  protected final String TEXT_247 = ".setConditionArray(new com.microsoft.schemas.crm._2006.query.ConditionExpression[] { ";
  protected final String TEXT_248 = " });" + NL + "\t        \t\t";
  protected final String TEXT_249 = NL + "\t        \t\t\tcom.microsoft.schemas.crm._2006.query.FilterExpression filter_";
  protected final String TEXT_250 = " = com.microsoft.schemas.crm._2006.query.FilterExpression.Factory.newInstance();" + NL + "\t        \t\t\tfilter_";
  protected final String TEXT_251 = ".setFilterOperator(com.microsoft.schemas.crm._2006.query.LogicalOperator.Enum.forString(\"";
  protected final String TEXT_252 = "\"));" + NL + "\t        \t\t\tfilter_";
  protected final String TEXT_253 = ".setConditions(conditions_";
  protected final String TEXT_254 = ");" + NL + "\t        \t\t\tquery_";
  protected final String TEXT_255 = ".setCriteria(filter_";
  protected final String TEXT_256 = ");" + NL + "\t       \t\t\t";
  protected final String TEXT_257 = NL + NL + "\t        \t";
  protected final String TEXT_258 = NL + "\t        \taos_";
  protected final String TEXT_259 = ".setAttributeArray(new String[]{";
  protected final String TEXT_260 = "});" + NL + "\t        \tcols_";
  protected final String TEXT_261 = ".setAttributes(aos_";
  protected final String TEXT_262 = ");" + NL + "\t        \tquery_";
  protected final String TEXT_263 = ".setColumnSet(cols_";
  protected final String TEXT_264 = ");" + NL + "\t        \tquery_";
  protected final String TEXT_265 = ".setEntityName(\"";
  protected final String TEXT_266 = "\");" + NL + "" + NL + "\t        \tcom.microsoft.schemas.crm._2006.query.PagingInfo pagingInfo_";
  protected final String TEXT_267 = " = com.microsoft.schemas.crm._2006.query.PagingInfo.Factory.newInstance();" + NL + "\t        \tint pageNumber_";
  protected final String TEXT_268 = " = 1;" + NL + "\t        \tpagingInfo_";
  protected final String TEXT_269 = ".setPageNumber(pageNumber_";
  protected final String TEXT_270 = ");" + NL + "\t        \t";
  protected final String TEXT_271 = NL + "\t        \tpagingInfo_";
  protected final String TEXT_272 = ".setCount(5);" + NL + "\t        \t";
  protected final String TEXT_273 = NL + "\t        \tquery_";
  protected final String TEXT_274 = ".setPageInfo(pagingInfo_";
  protected final String TEXT_275 = ");" + NL + "" + NL + "\t        \tcom.microsoft.schemas.crm._2007.webservices.RetrieveMultipleRequest rmr_";
  protected final String TEXT_276 = " = com.microsoft.schemas.crm._2007.webservices.RetrieveMultipleRequest.Factory.newInstance();" + NL + "\t        \tcom.microsoft.schemas.crm._2007.webservices.ExecuteDocument.Execute execute_";
  protected final String TEXT_277 = " = com.microsoft.schemas.crm._2007.webservices.ExecuteDocument.Execute.Factory.newInstance();" + NL + "\t        \tcom.microsoft.schemas.crm._2007.webservices.ExecuteDocument executeDoc_";
  protected final String TEXT_278 = " = com.microsoft.schemas.crm._2007.webservices.ExecuteDocument.Factory.newInstance();" + NL + "\t        \trmr_";
  protected final String TEXT_279 = ".setReturnDynamicEntities(true);" + NL + "\t        \trmr_";
  protected final String TEXT_280 = ".setQuery(query_";
  protected final String TEXT_281 = ");" + NL + "\t        \texecute_";
  protected final String TEXT_282 = ".setRequest(rmr_";
  protected final String TEXT_283 = ");" + NL + "\t        \texecuteDoc_";
  protected final String TEXT_284 = ".setExecute(execute_";
  protected final String TEXT_285 = ");" + NL + "\t        \texecuteDoc_";
  protected final String TEXT_286 = " = com.microsoft.schemas.crm._2007.webservices.ExecuteDocument.Factory.parse(executeDoc_";
  protected final String TEXT_287 = ".toString());" + NL + "\t        \tcom.microsoft.schemas.crm._2007.webservices.ExecuteResponseDocument result_";
  protected final String TEXT_288 = ";" + NL + "\t        \tboolean fetchMore_";
  protected final String TEXT_289 = " = true;" + NL + "\t        \twhile (fetchMore_";
  protected final String TEXT_290 = ") {" + NL + "\t        \t\tresult_";
  protected final String TEXT_291 = " = service_";
  protected final String TEXT_292 = ".execute(executeDoc_";
  protected final String TEXT_293 = ", catd_";
  protected final String TEXT_294 = ", null, null);" + NL + "\t        \t\tcom.microsoft.schemas.crm._2007.webservices.ExecuteResponseDocument.ExecuteResponse executeResponse_";
  protected final String TEXT_295 = " =  result_";
  protected final String TEXT_296 = ".getExecuteResponse();" + NL + "\t           \t\tcom.microsoft.schemas.crm._2007.webservices.Response response_";
  protected final String TEXT_297 = " = executeResponse_";
  protected final String TEXT_298 = ".getResponse();" + NL + "\t        \t\tcom.microsoft.schemas.crm._2007.webservices.RetrieveMultipleResponse rmp_";
  protected final String TEXT_299 = " = (com.microsoft.schemas.crm._2007.webservices.RetrieveMultipleResponse)response_";
  protected final String TEXT_300 = ";" + NL + "\t        \t\tcom.microsoft.schemas.crm._2006.webservices.BusinessEntityCollection bec_";
  protected final String TEXT_301 = " = rmp_";
  protected final String TEXT_302 = ".getBusinessEntityCollection();" + NL + "\t        \t\tString pagingCookie_";
  protected final String TEXT_303 = " = bec_";
  protected final String TEXT_304 = ".getPagingCookie();" + NL + "\t        \t\tfetchMore_";
  protected final String TEXT_305 = " = bec_";
  protected final String TEXT_306 = ".getMoreRecords();" + NL + "\t        \t\tcom.microsoft.schemas.crm._2006.webservices.ArrayOfBusinessEntity aobe_";
  protected final String TEXT_307 = " = bec_";
  protected final String TEXT_308 = ".getBusinessEntities();" + NL + "\t        \t\tcom.microsoft.schemas.crm._2006.webservices.BusinessEntity[] entities_";
  protected final String TEXT_309 = " = aobe_";
  protected final String TEXT_310 = ".getBusinessEntityArray();" + NL + "" + NL + "\t        \t\tfor (int i_";
  protected final String TEXT_311 = " = 0; i_";
  protected final String TEXT_312 = " < entities_";
  protected final String TEXT_313 = ".length; i_";
  protected final String TEXT_314 = "++) {" + NL + "\t                    com.microsoft.schemas.crm._2006.webservices.DynamicEntity dynamicEntity_";
  protected final String TEXT_315 = " = (com.microsoft.schemas.crm._2006.webservices.DynamicEntity)entities_";
  protected final String TEXT_316 = "[i_";
  protected final String TEXT_317 = "];" + NL + "\t                    com.microsoft.schemas.crm._2006.webservices.DynamicEntity.Properties properties_";
  protected final String TEXT_318 = " = dynamicEntity_";
  protected final String TEXT_319 = ".getProperties();" + NL + "\t                    com.microsoft.schemas.crm._2006.webservices.Property[] propertyArray_";
  protected final String TEXT_320 = " = properties_";
  protected final String TEXT_321 = ".getPropertyArray();" + NL + "\t                   \tnb_line_";
  protected final String TEXT_322 = " ++;" + NL + "\t                    String[] propertyValue_";
  protected final String TEXT_323 = " = new String[propertyArray_";
  protected final String TEXT_324 = ".length];" + NL + "\t                    String propertyType_";
  protected final String TEXT_325 = ";" + NL + "\t                    java.util.Map<String,String> propertyMap_";
  protected final String TEXT_326 = " = new java.util.HashMap<String,String>();" + NL + "" + NL + "\t                    for(int j_";
  protected final String TEXT_327 = " = 0; j_";
  protected final String TEXT_328 = " < propertyArray_";
  protected final String TEXT_329 = ".length; j_";
  protected final String TEXT_330 = "++){" + NL + "" + NL + "\t                    \tpropertyType_";
  protected final String TEXT_331 = " = propertyArray_";
  protected final String TEXT_332 = "[j_";
  protected final String TEXT_333 = "].schemaType().getShortJavaName();" + NL + "\t                    \tif(\"CrmBooleanProperty\".equals(propertyType_";
  protected final String TEXT_334 = ")){" + NL + "\t                    \t\tcom.microsoft.schemas.crm._2006.webservices.CrmBooleanProperty tempProperty_";
  protected final String TEXT_335 = " = (com.microsoft.schemas.crm._2006.webservices.CrmBooleanProperty)propertyArray_";
  protected final String TEXT_336 = "[j_";
  protected final String TEXT_337 = "];" + NL + "\t                    \t\tpropertyValue_";
  protected final String TEXT_338 = "[j_";
  protected final String TEXT_339 = "] = tempProperty_";
  protected final String TEXT_340 = ".getValue().getStringValue();" + NL + "\t                    \t\tpropertyMap_";
  protected final String TEXT_341 = ".put(tempProperty_";
  protected final String TEXT_342 = ".getName(),propertyValue_";
  protected final String TEXT_343 = "[j_";
  protected final String TEXT_344 = "]);" + NL + "\t                    \t}else if(\"CrmDateTimeProperty\".equals(propertyType_";
  protected final String TEXT_345 = ")){" + NL + "\t                    \t\tcom.microsoft.schemas.crm._2006.webservices.CrmDateTimeProperty tempProperty_";
  protected final String TEXT_346 = " = (com.microsoft.schemas.crm._2006.webservices.CrmDateTimeProperty)propertyArray_";
  protected final String TEXT_347 = "[j_";
  protected final String TEXT_348 = "];" + NL + "\t\t\t\t\t\t\t\tString tempDateString_";
  protected final String TEXT_349 = " = tempProperty_";
  protected final String TEXT_350 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_351 = NL + "\t\t\t\t\t\t\t\ttempDateString_";
  protected final String TEXT_352 = " = tempDateString_";
  protected final String TEXT_353 = ".substring(0, tempDateString_";
  protected final String TEXT_354 = ".lastIndexOf(\":\")) + tempDateString_";
  protected final String TEXT_355 = ".substring(tempDateString_";
  protected final String TEXT_356 = ".lastIndexOf(\":\") + 1);" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_357 = "[j_";
  protected final String TEXT_358 = "] = tempDateString_";
  protected final String TEXT_359 = ";" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_360 = ".put(tempProperty_";
  protected final String TEXT_361 = ".getName(),propertyValue_";
  protected final String TEXT_362 = "[j_";
  protected final String TEXT_363 = "]);" + NL + "\t                    \t}else if(\"CrmDecimalProperty\".equals(propertyType_";
  protected final String TEXT_364 = ")){" + NL + "\t                    \t\tcom.microsoft.schemas.crm._2006.webservices.CrmDecimalProperty tempProperty_";
  protected final String TEXT_365 = " = (com.microsoft.schemas.crm._2006.webservices.CrmDecimalProperty)propertyArray_";
  protected final String TEXT_366 = "[j_";
  protected final String TEXT_367 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_368 = "[j_";
  protected final String TEXT_369 = "] = tempProperty_";
  protected final String TEXT_370 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_371 = ".put(tempProperty_";
  protected final String TEXT_372 = ".getName(),propertyValue_";
  protected final String TEXT_373 = "[j_";
  protected final String TEXT_374 = "]);" + NL + "\t                    \t}else if(\"CrmFloatProperty\".equals(propertyType_";
  protected final String TEXT_375 = ")){" + NL + "\t                    \t\tcom.microsoft.schemas.crm._2006.webservices.CrmFloatProperty tempProperty_";
  protected final String TEXT_376 = " = (com.microsoft.schemas.crm._2006.webservices.CrmFloatProperty)propertyArray_";
  protected final String TEXT_377 = "[j_";
  protected final String TEXT_378 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_379 = "[j_";
  protected final String TEXT_380 = "] = tempProperty_";
  protected final String TEXT_381 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_382 = ".put(tempProperty_";
  protected final String TEXT_383 = ".getName(),propertyValue_";
  protected final String TEXT_384 = "[j_";
  protected final String TEXT_385 = "]);" + NL + "\t                    \t}else if(\"CrmMoneyProperty\".equals(propertyType_";
  protected final String TEXT_386 = ")){" + NL + "\t                    \t\tcom.microsoft.schemas.crm._2006.webservices.CrmMoneyProperty tempProperty_";
  protected final String TEXT_387 = " = (com.microsoft.schemas.crm._2006.webservices.CrmMoneyProperty)propertyArray_";
  protected final String TEXT_388 = "[j_";
  protected final String TEXT_389 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_390 = "[j_";
  protected final String TEXT_391 = "] = tempProperty_";
  protected final String TEXT_392 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_393 = ".put(tempProperty_";
  protected final String TEXT_394 = ".getName(),propertyValue_";
  protected final String TEXT_395 = "[j_";
  protected final String TEXT_396 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"CrmNumberProperty\".equals(propertyType_";
  protected final String TEXT_397 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.CrmNumberProperty tempProperty_";
  protected final String TEXT_398 = " = (com.microsoft.schemas.crm._2006.webservices.CrmNumberProperty)propertyArray_";
  protected final String TEXT_399 = "[j_";
  protected final String TEXT_400 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_401 = "[j_";
  protected final String TEXT_402 = "] = tempProperty_";
  protected final String TEXT_403 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_404 = ".put(tempProperty_";
  protected final String TEXT_405 = ".getName(),propertyValue_";
  protected final String TEXT_406 = "[j_";
  protected final String TEXT_407 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"CustomerProperty\".equals(propertyType_";
  protected final String TEXT_408 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.CustomerProperty tempProperty_";
  protected final String TEXT_409 = " = (com.microsoft.schemas.crm._2006.webservices.CustomerProperty)propertyArray_";
  protected final String TEXT_410 = "[j_";
  protected final String TEXT_411 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_412 = "[j_";
  protected final String TEXT_413 = "] = tempProperty_";
  protected final String TEXT_414 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_415 = ".put(tempProperty_";
  protected final String TEXT_416 = ".getName(),propertyValue_";
  protected final String TEXT_417 = "[j_";
  protected final String TEXT_418 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"KeyProperty\".equals(propertyType_";
  protected final String TEXT_419 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.KeyProperty tempProperty_";
  protected final String TEXT_420 = " = (com.microsoft.schemas.crm._2006.webservices.KeyProperty)propertyArray_";
  protected final String TEXT_421 = "[j_";
  protected final String TEXT_422 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_423 = "[j_";
  protected final String TEXT_424 = "] = tempProperty_";
  protected final String TEXT_425 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_426 = ".put(tempProperty_";
  protected final String TEXT_427 = ".getName(),propertyValue_";
  protected final String TEXT_428 = "[j_";
  protected final String TEXT_429 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"LookupProperty\".equals(propertyType_";
  protected final String TEXT_430 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.LookupProperty tempProperty_";
  protected final String TEXT_431 = " = (com.microsoft.schemas.crm._2006.webservices.LookupProperty)propertyArray_";
  protected final String TEXT_432 = "[j_";
  protected final String TEXT_433 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_434 = "[j_";
  protected final String TEXT_435 = "] = tempProperty_";
  protected final String TEXT_436 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_437 = ".put(tempProperty_";
  protected final String TEXT_438 = ".getName(),propertyValue_";
  protected final String TEXT_439 = "[j_";
  protected final String TEXT_440 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"OwnerProperty\".equals(propertyType_";
  protected final String TEXT_441 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.OwnerProperty tempProperty_";
  protected final String TEXT_442 = " = (com.microsoft.schemas.crm._2006.webservices.OwnerProperty)propertyArray_";
  protected final String TEXT_443 = "[j_";
  protected final String TEXT_444 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_445 = "[j_";
  protected final String TEXT_446 = "] = tempProperty_";
  protected final String TEXT_447 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_448 = ".put(tempProperty_";
  protected final String TEXT_449 = ".getName(),propertyValue_";
  protected final String TEXT_450 = "[j_";
  protected final String TEXT_451 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"PicklistProperty\".equals(propertyType_";
  protected final String TEXT_452 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.PicklistProperty tempProperty_";
  protected final String TEXT_453 = " = (com.microsoft.schemas.crm._2006.webservices.PicklistProperty)propertyArray_";
  protected final String TEXT_454 = "[j_";
  protected final String TEXT_455 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_456 = "[j_";
  protected final String TEXT_457 = "] = tempProperty_";
  protected final String TEXT_458 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_459 = ".put(tempProperty_";
  protected final String TEXT_460 = ".getName(),propertyValue_";
  protected final String TEXT_461 = "[j_";
  protected final String TEXT_462 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"StateProperty\".equals(propertyType_";
  protected final String TEXT_463 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.StateProperty tempProperty_";
  protected final String TEXT_464 = " = (com.microsoft.schemas.crm._2006.webservices.StateProperty)propertyArray_";
  protected final String TEXT_465 = "[j_";
  protected final String TEXT_466 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_467 = "[j_";
  protected final String TEXT_468 = "] = tempProperty_";
  protected final String TEXT_469 = ".getValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_470 = ".put(tempProperty_";
  protected final String TEXT_471 = ".getName(),propertyValue_";
  protected final String TEXT_472 = "[j_";
  protected final String TEXT_473 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"StatusProperty\".equals(propertyType_";
  protected final String TEXT_474 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.StatusProperty tempProperty_";
  protected final String TEXT_475 = " = (com.microsoft.schemas.crm._2006.webservices.StatusProperty)propertyArray_";
  protected final String TEXT_476 = "[j_";
  protected final String TEXT_477 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_478 = "[j_";
  protected final String TEXT_479 = "] = tempProperty_";
  protected final String TEXT_480 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_481 = ".put(tempProperty_";
  protected final String TEXT_482 = ".getName(),propertyValue_";
  protected final String TEXT_483 = "[j_";
  protected final String TEXT_484 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"StringProperty\".equals(propertyType_";
  protected final String TEXT_485 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.StringProperty tempProperty_";
  protected final String TEXT_486 = " = (com.microsoft.schemas.crm._2006.webservices.StringProperty)propertyArray_";
  protected final String TEXT_487 = "[j_";
  protected final String TEXT_488 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_489 = "[j_";
  protected final String TEXT_490 = "] = tempProperty_";
  protected final String TEXT_491 = ".getValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_492 = ".put(tempProperty_";
  protected final String TEXT_493 = ".getName(),propertyValue_";
  protected final String TEXT_494 = "[j_";
  protected final String TEXT_495 = "]);" + NL + "\t\t\t\t\t\t\t}else if(\"UniqueIdentifierProperty\".equals(propertyType_";
  protected final String TEXT_496 = ")){" + NL + "\t\t\t\t\t\t\t\tcom.microsoft.schemas.crm._2006.webservices.UniqueIdentifierProperty tempProperty_";
  protected final String TEXT_497 = " = (com.microsoft.schemas.crm._2006.webservices.UniqueIdentifierProperty)propertyArray_";
  protected final String TEXT_498 = "[j_";
  protected final String TEXT_499 = "];" + NL + "\t\t\t\t\t\t\t\tpropertyValue_";
  protected final String TEXT_500 = "[j_";
  protected final String TEXT_501 = "] = tempProperty_";
  protected final String TEXT_502 = ".getValue().getStringValue();" + NL + "\t\t\t\t\t\t\t\tpropertyMap_";
  protected final String TEXT_503 = ".put(tempProperty_";
  protected final String TEXT_504 = ".getName(),propertyValue_";
  protected final String TEXT_505 = "[j_";
  protected final String TEXT_506 = "]);" + NL + "\t\t\t\t\t\t\t}" + NL + "\t                    }" + NL + "\t                   \t\t";
  protected final String TEXT_507 = NL + NL + "\t\t\t\t\t\t\t\tif(propertyMap_";
  protected final String TEXT_508 = ".get(\"";
  protected final String TEXT_509 = "\")!=null){" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_510 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_511 = ".";
  protected final String TEXT_512 = " = propertyMap_";
  protected final String TEXT_513 = ".get(\"";
  protected final String TEXT_514 = "\");" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_515 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_516 = ".";
  protected final String TEXT_517 = " = ParserUtils.parseTo_Date(propertyMap_";
  protected final String TEXT_518 = ".get(\"";
  protected final String TEXT_519 = "\"), ";
  protected final String TEXT_520 = ");" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_521 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_522 = ".";
  protected final String TEXT_523 = " = ParserUtils.parseTo_Double(propertyMap_";
  protected final String TEXT_524 = ".get(\"";
  protected final String TEXT_525 = "\"));" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_526 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_527 = ".";
  protected final String TEXT_528 = " = ParserUtils.parseTo_BigDecimal(propertyMap_";
  protected final String TEXT_529 = ".get(\"";
  protected final String TEXT_530 = "\"));" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_531 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_532 = ".";
  protected final String TEXT_533 = " = ParserUtils.parseTo_Float(propertyMap_";
  protected final String TEXT_534 = ".get(\"";
  protected final String TEXT_535 = "\"));" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_536 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_537 = ".";
  protected final String TEXT_538 = " = ParserUtils.parseTo_Integer(propertyMap_";
  protected final String TEXT_539 = ".get(\"";
  protected final String TEXT_540 = "\"));" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_541 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_542 = ".";
  protected final String TEXT_543 = " = ParserUtils.parseTo_Boolean(propertyMap_";
  protected final String TEXT_544 = ".get(\"";
  protected final String TEXT_545 = "\"));" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_546 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_547 = ".";
  protected final String TEXT_548 = " = propertyMap_";
  protected final String TEXT_549 = ".get(\"";
  protected final String TEXT_550 = "\");" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_551 = NL + "\t        \t\t\t\t\t\t\t";
  protected final String TEXT_552 = ".";
  protected final String TEXT_553 = " = ParserUtils.parseTo_";
  protected final String TEXT_554 = "(propertyMap_";
  protected final String TEXT_555 = ".get(\"";
  protected final String TEXT_556 = "\"));" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_557 = NL + "\t        \t\t\t\t\t}else{" + NL + "\t        \t\t\t\t\t\t";
  protected final String TEXT_558 = ".";
  protected final String TEXT_559 = " = ";
  protected final String TEXT_560 = ";" + NL + "\t        \t\t\t\t\t}" + NL + "\t        \t\t\t\t";
  protected final String TEXT_561 = NL + NL + NL + "\t\t\t\t";
  protected final String TEXT_562 = NL + "\t\t\t\torg.talend.ms.crm.MSCRMClient client_";
  protected final String TEXT_563 = " = new org.talend.ms.crm.MSCRMClient(";
  protected final String TEXT_564 = ", decryptedPassword_";
  protected final String TEXT_565 = ", ";
  protected final String TEXT_566 = ");" + NL + "\t\t\t\tclient_";
  protected final String TEXT_567 = ".setTimeout(";
  protected final String TEXT_568 = ");" + NL + "\t\t\t\tclient_";
  protected final String TEXT_569 = ".setReuseHttpClient(";
  protected final String TEXT_570 = ");" + NL + "\t\t\t\tcom.microsoft.schemas.xrm._2011.contracts.OrganizationServiceStub serviceStub_";
  protected final String TEXT_571 = " = client_";
  protected final String TEXT_572 = ".getOnlineConnection(";
  protected final String TEXT_573 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_574 = NL + "\t        \tcom.microsoft.schemas.xrm._2011.contracts.QueryExpression query_";
  protected final String TEXT_575 = " = com.microsoft.schemas.xrm._2011.contracts.QueryExpression.Factory.newInstance();" + NL + "\t        \tcom.microsoft.schemas.xrm._2011.contracts.ColumnSet cols_";
  protected final String TEXT_576 = " = com.microsoft.schemas.xrm._2011.contracts.ColumnSet.Factory.newInstance();" + NL + "\t        \tcom.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring aos_";
  protected final String TEXT_577 = " = com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring.Factory.newInstance();" + NL + "\t       \t\t";
  protected final String TEXT_578 = NL + "\t        \t\tcom.microsoft.schemas.xrm._2011.contracts.ArrayOfConditionExpression conditions_";
  protected final String TEXT_579 = " = com.microsoft.schemas.xrm._2011.contracts.ArrayOfConditionExpression.Factory.newInstance();" + NL + "\t        \t\tcom.microsoft.schemas.xrm._2011.contracts.ConditionExpression condition_";
  protected final String TEXT_580 = " = null;" + NL + "\t        \t\tcom.microsoft.schemas._2003._10.serialization.arrays.ArrayOfanyType values_";
  protected final String TEXT_581 = " = null;" + NL + "\t        \t\torg.apache.xmlbeans.XmlString conditionValue_";
  protected final String TEXT_582 = " = null;" + NL + "\t            \t";
  protected final String TEXT_583 = NL + "\t\t            \t\tcondition_";
  protected final String TEXT_584 = " = conditions_";
  protected final String TEXT_585 = ".addNewConditionExpression();" + NL + "\t\t            \t\tcondition_";
  protected final String TEXT_586 = ".setAttributeName(\"";
  protected final String TEXT_587 = "\");" + NL + "\t\t            \t\tcondition_";
  protected final String TEXT_588 = ".setOperator(com.microsoft.schemas.xrm._2011.contracts.ConditionOperator.Enum.forString(\"";
  protected final String TEXT_589 = "\"));" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_590 = NL + "\t            \t\t\tvalues_";
  protected final String TEXT_591 = " = com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfanyType.Factory.newInstance();" + NL + "\t            \t\t\tconditionValue_";
  protected final String TEXT_592 = " = org.apache.xmlbeans.XmlString.Factory.newInstance();" + NL + "\t            \t\t\tconditionValue_";
  protected final String TEXT_593 = ".setStringValue(";
  protected final String TEXT_594 = ");" + NL + "\t            \t\t\tvalues_";
  protected final String TEXT_595 = ".setAnyTypeArray(new org.apache.xmlbeans.XmlString[]{conditionValue_";
  protected final String TEXT_596 = "});" + NL + "\t            \t\t\tcondition_";
  protected final String TEXT_597 = ".setValues(values_";
  protected final String TEXT_598 = ");" + NL + "\t       \t\t\t\t";
  protected final String TEXT_599 = NL + "\t        \t\t";
  protected final String TEXT_600 = NL + "\t        \t\t\tcom.microsoft.schemas.xrm._2011.contracts.FilterExpression filter_";
  protected final String TEXT_601 = " = com.microsoft.schemas.xrm._2011.contracts.FilterExpression.Factory.newInstance();" + NL + "\t        \t\t\tfilter_";
  protected final String TEXT_602 = ".setFilterOperator(com.microsoft.schemas.xrm._2011.contracts.LogicalOperator.Enum.forString(\"";
  protected final String TEXT_603 = "\"));" + NL + "\t        \t\t\tfilter_";
  protected final String TEXT_604 = ".setConditions(conditions_";
  protected final String TEXT_605 = ");" + NL + "\t        \t\t\tquery_";
  protected final String TEXT_606 = ".setCriteria(filter_";
  protected final String TEXT_607 = ");" + NL + "\t       \t\t\t";
  protected final String TEXT_608 = NL + NL + "\t        \t";
  protected final String TEXT_609 = NL + "\t        \taos_";
  protected final String TEXT_610 = ".setStringArray(new String[]{";
  protected final String TEXT_611 = "});" + NL + "\t        \tcols_";
  protected final String TEXT_612 = ".setColumns(aos_";
  protected final String TEXT_613 = ");" + NL + "\t        \tquery_";
  protected final String TEXT_614 = ".setColumnSet(cols_";
  protected final String TEXT_615 = ");" + NL + "\t        \tquery_";
  protected final String TEXT_616 = ".setEntityName(\"";
  protected final String TEXT_617 = "\");" + NL + "" + NL + "\t        \tcom.microsoft.schemas.xrm._2011.contracts.PagingInfo pagingInfo_";
  protected final String TEXT_618 = " = com.microsoft.schemas.xrm._2011.contracts.PagingInfo.Factory.newInstance();" + NL + "\t        \tint pageNumber_";
  protected final String TEXT_619 = " = 1;" + NL + "\t        \tpagingInfo_";
  protected final String TEXT_620 = ".setPageNumber(pageNumber_";
  protected final String TEXT_621 = ");" + NL + "\t        \t";
  protected final String TEXT_622 = NL + "\t        \tpagingInfo_";
  protected final String TEXT_623 = ".setCount(5);" + NL + "\t        \t";
  protected final String TEXT_624 = NL + "\t        \tquery_";
  protected final String TEXT_625 = ".setPageInfo(pagingInfo_";
  protected final String TEXT_626 = ");" + NL + "" + NL + "\t        \tcom.microsoft.schemas.xrm._2011.contracts.services.RetrieveMultipleDocument.RetrieveMultiple rmr_";
  protected final String TEXT_627 = " = com.microsoft.schemas.xrm._2011.contracts.services.RetrieveMultipleDocument.RetrieveMultiple.Factory.newInstance();" + NL + "\t        \tcom.microsoft.schemas.xrm._2011.contracts.services.RetrieveMultipleResponseDocument.RetrieveMultipleResponse retrieveMultipleResp_";
  protected final String TEXT_628 = " = null;" + NL + "\t        \twhile(true){" + NL + "\t        \t\trmr_";
  protected final String TEXT_629 = ".setQuery(query_";
  protected final String TEXT_630 = ");" + NL + "\t        \t\tcom.microsoft.schemas.xrm._2011.contracts.services.RetrieveMultipleDocument rmrdoc_";
  protected final String TEXT_631 = " = com.microsoft.schemas.xrm._2011.contracts.services.RetrieveMultipleDocument.Factory.newInstance();" + NL + "\t        \t\trmrdoc_";
  protected final String TEXT_632 = ".setRetrieveMultiple(rmr_";
  protected final String TEXT_633 = ");" + NL + "\t        \t\ttry{" + NL + "\t        \t\t\tretrieveMultipleResp_";
  protected final String TEXT_634 = " = serviceStub_";
  protected final String TEXT_635 = ".retrieveMultiple(rmrdoc_";
  protected final String TEXT_636 = ").getRetrieveMultipleResponse();" + NL + "\t        \t\t}catch(com.microsoft.schemas.xrm._2011.contracts.IOrganizationService_RetrieveMultiple_OrganizationServiceFaultFault_FaultMessage ex_";
  protected final String TEXT_637 = "){" + NL + "\t        \t\t\tthrow new Exception(ex_";
  protected final String TEXT_638 = ".getFaultMessage().getOrganizationServiceFault().getMessage());" + NL + "\t        \t\t}" + NL + "\t        \t\tcom.microsoft.schemas.xrm._2011.contracts.EntityCollection retrieveMultipleResult_";
  protected final String TEXT_639 = " = retrieveMultipleResp_";
  protected final String TEXT_640 = ".getRetrieveMultipleResult();" + NL + "\t        \t\tcom.microsoft.schemas.xrm._2011.contracts.Entity[] entities_";
  protected final String TEXT_641 = " = retrieveMultipleResult_";
  protected final String TEXT_642 = ".getEntities().getEntityArray();" + NL + "\t        \t\tfor(com.microsoft.schemas.xrm._2011.contracts.Entity entity_";
  protected final String TEXT_643 = " : entities_";
  protected final String TEXT_644 = "){" + NL + "\t                   \tnb_line_";
  protected final String TEXT_645 = "++;" + NL + "\t                    org.datacontract.schemas._2004._07.system_collections_generic.KeyValuePairOfstringanyType[] properties_";
  protected final String TEXT_646 = " = entity_";
  protected final String TEXT_647 = ".getAttributes().getKeyValuePairOfstringanyTypeArray();" + NL + "" + NL + "\t                    java.util.Map<String, String> propertyMap_";
  protected final String TEXT_648 = " = new java.util.HashMap<String, String>();" + NL + "\t                    String propertyValue_";
  protected final String TEXT_649 = " = null;" + NL + "\t                    org.apache.xmlbeans.XmlObject tempPropertyValue_";
  protected final String TEXT_650 = " = null;" + NL + "\t                    for(org.datacontract.schemas._2004._07.system_collections_generic.KeyValuePairOfstringanyType property_";
  protected final String TEXT_651 = " : properties_";
  protected final String TEXT_652 = "){" + NL + "\t                    \ttempPropertyValue_";
  protected final String TEXT_653 = " = property_";
  protected final String TEXT_654 = ".getValue();" + NL + "\t                    \tif(tempPropertyValue_";
  protected final String TEXT_655 = " instanceof org.apache.xmlbeans.XmlAnySimpleType){" + NL + "\t                    \t\tpropertyValue_";
  protected final String TEXT_656 = " = ((org.apache.xmlbeans.XmlAnySimpleType)tempPropertyValue_";
  protected final String TEXT_657 = ").getStringValue();" + NL + "\t                    \t}else if(tempPropertyValue_";
  protected final String TEXT_658 = " instanceof com.microsoft.schemas.xrm._2011.contracts.OptionSetValue){" + NL + "\t                    \t\tpropertyValue_";
  protected final String TEXT_659 = " = ((com.microsoft.schemas.xrm._2011.contracts.OptionSetValue)tempPropertyValue_";
  protected final String TEXT_660 = ").xgetValue().getStringValue();" + NL + "\t                    \t}else if(tempPropertyValue_";
  protected final String TEXT_661 = " instanceof com.microsoft.schemas.xrm._2011.contracts.Money){" + NL + "\t                    \t\tpropertyValue_";
  protected final String TEXT_662 = " = ((com.microsoft.schemas.xrm._2011.contracts.Money)tempPropertyValue_";
  protected final String TEXT_663 = ").xgetValue().getStringValue();" + NL + "\t                    \t}else if(tempPropertyValue_";
  protected final String TEXT_664 = " instanceof com.microsoft.schemas.xrm._2011.contracts.EntityReference){" + NL + "\t                    \t\tcom.microsoft.schemas.xrm._2011.contracts.EntityReference entityReference_";
  protected final String TEXT_665 = " = (com.microsoft.schemas.xrm._2011.contracts.EntityReference)tempPropertyValue_";
  protected final String TEXT_666 = ";" + NL + "                        \t\tpropertyValue_";
  protected final String TEXT_667 = " = \"{\\\"id\\\":\\\"\"+entityReference_";
  protected final String TEXT_668 = ".getId() + \"\\\",\\\"logicalName\\\":\\\"\" + entityReference_";
  protected final String TEXT_669 = ".getLogicalName() + \"\\\",\\\"name\\\":\\\"\" + entityReference_";
  protected final String TEXT_670 = ".getName() + \"\\\"}\";" + NL + "\t                    \t}else if(tempPropertyValue_";
  protected final String TEXT_671 = " instanceof com.microsoft.schemas.xrm._2011.contracts.BooleanManagedProperty){" + NL + "\t                    \t\tcom.microsoft.schemas.xrm._2011.contracts.BooleanManagedProperty booleanManagedProperty_";
  protected final String TEXT_672 = " = (com.microsoft.schemas.xrm._2011.contracts.BooleanManagedProperty)tempPropertyValue_";
  protected final String TEXT_673 = ";" + NL + "\t                    \t\tpropertyValue_";
  protected final String TEXT_674 = " = \"{\\\"canBeChanged\\\":\\\"\"+booleanManagedProperty_";
  protected final String TEXT_675 = ".getCanBeChanged() + \"\\\",\\\"logicalName\\\":\\\"\" + booleanManagedProperty_";
  protected final String TEXT_676 = ".getManagedPropertyLogicalName() + \"\\\",\\\"value\\\":\\\"\" + booleanManagedProperty_";
  protected final String TEXT_677 = ".getValue() + \"\\\"}\";" + NL + "\t                    \t}else{" + NL + "\t                    \t\tSystem.err.println(\"don't support the type of \" + property_";
  protected final String TEXT_678 = ");" + NL + "\t                    \t\tpropertyValue_";
  protected final String TEXT_679 = " = null;" + NL + "\t                    \t}" + NL + "                    \t\tpropertyMap_";
  protected final String TEXT_680 = ".put(property_";
  protected final String TEXT_681 = ".getKey(), propertyValue_";
  protected final String TEXT_682 = ");" + NL + "\t                    }" + NL + "                   \t\t";
  protected final String TEXT_683 = NL + NL + "\t\t\t\t\t\t\tif(propertyMap_";
  protected final String TEXT_684 = ".get(\"";
  protected final String TEXT_685 = "\")!=null){" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_686 = NL + "\t        \t\t\t\t\t\t";
  protected final String TEXT_687 = ".";
  protected final String TEXT_688 = " = propertyMap_";
  protected final String TEXT_689 = ".get(\"";
  protected final String TEXT_690 = "\");" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_691 = NL + "\t        \t\t\t\t\t\t";
  protected final String TEXT_692 = ".";
  protected final String TEXT_693 = " = ParserUtils.parseTo_Date(propertyMap_";
  protected final String TEXT_694 = ".get(\"";
  protected final String TEXT_695 = "\"), ";
  protected final String TEXT_696 = ");" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_697 = NL + "\t        \t\t\t\t\t\t";
  protected final String TEXT_698 = ".";
  protected final String TEXT_699 = " = ParserUtils.parseTo_";
  protected final String TEXT_700 = "(propertyMap_";
  protected final String TEXT_701 = ".get(\"";
  protected final String TEXT_702 = "\"));" + NL + "\t        \t\t\t\t\t";
  protected final String TEXT_703 = NL + "        \t\t\t\t\t}else{" + NL + "        \t\t\t\t\t\t";
  protected final String TEXT_704 = ".";
  protected final String TEXT_705 = " = ";
  protected final String TEXT_706 = ";" + NL + "        \t\t\t\t\t}" + NL + "        \t\t\t\t";
  protected final String TEXT_707 = NL + "\t\t\t\t";
  protected final String TEXT_708 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
    CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
    INode node = (INode)codeGenArgument.getArgument();
    String cid = node.getUniqueName();

    String authenticationType = ElementParameterParser.getValue(node, "__AUTH_TYPE__");
    String crmVersion = ElementParameterParser.getValue(node, "__MS_CRM_VERSION__");

    boolean isMsCrm2015OnPremise = "ON_PREMISE".equals(authenticationType) && "CRM_2015".equals(crmVersion);

    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    
    if(isMsCrm2015OnPremise){
       
    stringBuffer.append(TEXT_4);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_5);
    
   List<IMetadataTable> metadataTableList = node.getMetadataList();
   if((metadataTableList != null) && (metadataTableList.size() > 0)){
        IMetadataTable metadata = metadataTableList.get(0);
        if(metadata != null){
            List<IMetadataColumn> columnList = metadata.getListColumns();
            int columnSize = columnList.size();
            //Concatenate schemas as string
            String schemasAsString = null;
            StringBuilder strBuilder4Schema = new StringBuilder();
            for(int i = 0; i < columnSize; i++){
                if(i > 0){
                    strBuilder4Schema.append(", ");
                }
                   strBuilder4Schema.append("\"");
                   strBuilder4Schema.append(columnList.get(i).getLabel());
                   strBuilder4Schema.append("\"");
            }
            schemasAsString = strBuilder4Schema.toString();

            List<? extends IConnection> outgoingConns = node.getOutgoingSortedConnections();
            if(columnSize > 0 && outgoingConns != null && outgoingConns.size() > 0){
                 String domainUserName =  ElementParameterParser.getValue(node, "__USERNAME__");
                 String passwordFieldName = "__PASSWORD__";
                 
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_8);
    } else {
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_11);
    }
    
                 String timeout = ElementParameterParser.getValue(node, "__TIMEOUT__");
                 int timeoutInSeconds = Integer.valueOf((timeout != null && !"".equals(timeout)) ? timeout : "1").intValue() * 1000;

                 String organizationWsdl = ElementParameterParser.getValue(node, "__ORGANIZATION_WSDL__");
                 String securityServiceWsdl = ElementParameterParser.getValue(node, "__SECURITY_SERVICE_URL__");
                 String certificatePath = ElementParameterParser.getValue(node, "__CERTIFICATE_PATH__");
                 if(certificatePath != null && !"".equals(certificatePath) && !"\"\"".equals(certificatePath)){
                 
    stringBuffer.append(TEXT_12);
    stringBuffer.append( certificatePath );
    stringBuffer.append(TEXT_13);
    
                 }
                 
    stringBuffer.append(TEXT_14);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    stringBuffer.append( organizationWsdl );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( securityServiceWsdl );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_19);
    stringBuffer.append(TEXT_20);
    stringBuffer.append( domainUserName );
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_25);
    
                   String entityname = ElementParameterParser.getValue(node, "__ENTITYNAME__").trim();
                   String customEntityname = ElementParameterParser.getValue(node, "__CUSTOM_ENTITY_NAME__");
                   if("CustomEntity".equals(entityname)){
                       entityname = customEntityname.replaceAll("\"","");
                   }
                       entityname = entityname.toLowerCase();
                   //Logical Operator for FilterExpression
                   String logicalOperator = ElementParameterParser.getValue(node,"__LOGICAL_OP__");
                   //Logical Operator for ConditionExpression
                   List<Map<String, String>> keyColumns = (List<Map<String, String>>)ElementParameterParser.getObjectValue(node, "__CONDITIONS__");
                 
    stringBuffer.append(TEXT_26);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_29);
    
                //Use Query Criteria
                if(keyColumns.size()>0){
                    
    stringBuffer.append(TEXT_30);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_32);
    
                    StringBuilder strBuilder = new StringBuilder("");
                    int conditionIndex = 0;
                    for(Map<String, String> keyColumn: keyColumns){
                        String conditionId = cid + "_" + String.valueOf(conditionIndex);
                        
    stringBuffer.append(TEXT_33);
    stringBuffer.append( conditionId );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( conditionId );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( keyColumn.get("INPUT_COLUMN") );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( conditionId );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(keyColumn.get("OPERATOR"));
    stringBuffer.append(TEXT_38);
    stringBuffer.append( conditionId );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( conditionId );
    stringBuffer.append(TEXT_40);
    stringBuffer.append(keyColumn.get("RVALUE"));
    stringBuffer.append(TEXT_41);
    stringBuffer.append( conditionId );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( conditionId );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( conditionId );
    stringBuffer.append(TEXT_45);
    
                        conditionIndex ++;
                    }
                        
    stringBuffer.append(TEXT_46);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( logicalOperator );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_52);
    
                }
                
    stringBuffer.append(TEXT_53);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_54);
    stringBuffer.append( schemasAsString );
    stringBuffer.append(TEXT_55);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_56);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_57);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_59);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_60);
    stringBuffer.append( entityname );
    stringBuffer.append(TEXT_61);
    
                //Begin to Transfer Data
                IConnection outgoingConn = outgoingConns.get(0);
                if(outgoingConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)){
                
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
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_71);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_72);
    stringBuffer.append( cid );
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
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_79);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_80);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_81);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_82);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_83);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_84);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_85);
    
                                for(int i = 0; i < columnList.size(); i++){
                                    IMetadataColumn column = columnList.get(i);
                                    String typeToGenerate = JavaTypesManager.getTypeToGenerate(column.getTalendType(), column.isNullable());
                                    JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
                                    String patternValue = column.getPattern() == null || column.getPattern().trim().length() == 0 ? null : column.getPattern();
                                    
    stringBuffer.append(TEXT_86);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_87);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_88);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_89);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_90);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_91);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_92);
    
                                        if(javaType == JavaTypesManager.STRING){
                                        
    stringBuffer.append(TEXT_93);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_94);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_95);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_96);
    
                                        }else if(javaType == JavaTypesManager.OBJECT){
                                        
    stringBuffer.append(TEXT_97);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_98);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_99);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_100);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_101);
    
                                        }
                                        else if(javaType == JavaTypesManager.DATE){
                                        
    stringBuffer.append(TEXT_102);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_103);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_105);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_106);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_107);
    stringBuffer.append(TEXT_108);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_109);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_110);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_111);
    stringBuffer.append( patternValue );
    stringBuffer.append(TEXT_112);
    
                                        }else if(javaType == JavaTypesManager.INTEGER){
                                         
    stringBuffer.append(TEXT_113);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_114);
    stringBuffer.append(TEXT_115);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_116);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_117);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_118);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_120);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_121);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_122);
    
                                        }else if(javaType == JavaTypesManager.BIGDECIMAL){
                                        
    stringBuffer.append(TEXT_123);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_124);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_126);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_127);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_128);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_130);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_131);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_132);
    
                                        }else{
                                        
    stringBuffer.append(TEXT_133);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_134);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_135);
    stringBuffer.append(typeToGenerate);
    stringBuffer.append(TEXT_136);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_137);
    
                                        }
                                        
    stringBuffer.append(TEXT_138);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_140);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_141);
    stringBuffer.append(JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate));
    stringBuffer.append(TEXT_142);
    
                                }
                            
     //}// Entity For Loop end but moved to the end part 
    
                }
            }
        }
    }
 
    
    }else{
        
    
List<IMetadataTable> metadatas = node.getMetadataList();
if ((metadatas != null) && (metadatas.size() > 0)) {
	IMetadataTable metadata = metadatas.get(0);

	if (metadata != null) {
		List<IMetadataColumn> columnList = metadata.getListColumns();
		int nbSchemaColumns = columnList.size();
		List<? extends IConnection> outgoingConns = node.getOutgoingSortedConnections();

		// if output columns are defined
		if(nbSchemaColumns > 0 && outgoingConns != null && outgoingConns.size() > 0){
			String authType = ElementParameterParser.getValue(node, "__AUTH_TYPE__");
			String endpointURL = ElementParameterParser.getValue(node, "__ENDPOINTURL__");
			String orgName = ElementParameterParser.getValue(node, "__ORGNAME__");
			String username = ElementParameterParser.getValue(node, "__USERNAME__");

			String domain = ElementParameterParser.getValue(node, "__DOMAIN__");
			String host = ElementParameterParser.getValue(node, "__HOST__");
			String port = ElementParameterParser.getValue(node, "__PORT__");
			String timeoutSecTemp = ElementParameterParser.getValue(node, "__TIMEOUT__");
			String timeoutSec = (timeoutSecTemp!=null&&!("").equals(timeoutSecTemp))?timeoutSecTemp:"2";
			int timeout = (int)(Double.valueOf(timeoutSec) * 1000);
			boolean reuseHttpClient = ("true").equals(ElementParameterParser.getValue(node,"__REUSE_HTTP_CLIENT__"));
			boolean debug = ("true").equals(ElementParameterParser.getValue(node,"__DEBUG__"));
			boolean isAPI2011 = ("API_2011").equals(ElementParameterParser.getValue(node,"__API_VERSION__"));
			String discWSDL = ElementParameterParser.getValue(node, "__DISC_WSDL__");
			if(debug){
    stringBuffer.append(TEXT_143);
    }else{
    stringBuffer.append(TEXT_144);
    }

			String passwordFieldName = "__PASSWORD__";
			
    stringBuffer.append(TEXT_145);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_148);
    } else {
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_150);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_151);
    }
    stringBuffer.append(TEXT_152);
    
			if(!isAPI2011 || ("ON_PREMISE").equals(authType)){
				if(("ON_PREMISE").equals(authType)){
    stringBuffer.append(TEXT_153);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_154);
    stringBuffer.append(endpointURL);
    stringBuffer.append(TEXT_155);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_157);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_158);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_165);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_166);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_168);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_169);
    stringBuffer.append(port);
    stringBuffer.append(TEXT_170);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_171);
    stringBuffer.append(domain);
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_173);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_174);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_176);
    stringBuffer.append(reuseHttpClient);
    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_178);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    stringBuffer.append(timeout);
    stringBuffer.append(TEXT_183);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    stringBuffer.append(timeout);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_186);
    stringBuffer.append(timeout);
    stringBuffer.append(TEXT_187);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_188);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_191);
    stringBuffer.append(orgName);
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_193);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_194);
    }else if(("ONLINE").equals(authType)){
    stringBuffer.append(TEXT_195);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_196);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_197);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_198);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_200);
    stringBuffer.append(host);
    stringBuffer.append(TEXT_201);
    stringBuffer.append(orgName);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_204);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_205);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_208);
    stringBuffer.append(reuseHttpClient);
    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_210);
    stringBuffer.append(timeout);
    stringBuffer.append(TEXT_211);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_212);
    stringBuffer.append(timeout);
    stringBuffer.append(TEXT_213);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(timeout);
    stringBuffer.append(TEXT_215);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_216);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_218);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_219);
    stringBuffer.append(orgName);
    stringBuffer.append(TEXT_220);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_221);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_222);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_223);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_224);
    }
    stringBuffer.append(TEXT_225);
    
	     		String entityname = ElementParameterParser.getValue(node, "__ENTITYNAME__").trim();
	     		String customEntityname = ElementParameterParser.getValue(node, "__CUSTOM_ENTITY_NAME__");
	     		if("CustomEntity".equals(entityname)){
					entityname = customEntityname.replaceAll("\"","");
				}
	     		String entitynamealllower = entityname.toLowerCase();
	     		String logical = ElementParameterParser.getValue(node,"__LOGICAL_OP__");
	     		List<Map<String, String>> keyColumns = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__CONDITIONS__");
	     		
    stringBuffer.append(TEXT_226);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_227);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_228);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    
	        	if(keyColumns.size()>0 ){
	            	StringBuffer conditionList = new StringBuffer("");
	            	int nbCondition = 0;
	            	for(Map<String, String> keyColumn:keyColumns){
	            		nbCondition++;
	            		String conditionID = cid + "_" + String.valueOf(nbCondition);
	        			
    stringBuffer.append(TEXT_230);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_231);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_232);
    stringBuffer.append(keyColumn.get("INPUT_COLUMN"));
    stringBuffer.append(TEXT_233);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_234);
    stringBuffer.append(keyColumn.get("OPERATOR"));
    stringBuffer.append(TEXT_235);
    
	        			if(!("").equals(keyColumn.get("RVALUE"))){
	        			
    stringBuffer.append(TEXT_236);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_238);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_239);
    stringBuffer.append(keyColumn.get("RVALUE"));
    stringBuffer.append(TEXT_240);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_241);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_242);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_243);
    stringBuffer.append(conditionID);
    stringBuffer.append(TEXT_244);
    
	            		}
	            		conditionList.append("condition_"+conditionID+",");
	        		}
	        		conditionList.deleteCharAt(conditionList.lastIndexOf(","));
	        		
    stringBuffer.append(TEXT_245);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_246);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_247);
    stringBuffer.append(conditionList);
    stringBuffer.append(TEXT_248);
    
	        		if(!("").equals(logical)){
	        		
    stringBuffer.append(TEXT_249);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(logical);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_253);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_254);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    
	        		}
	        	}
	        	
    stringBuffer.append(TEXT_257);
    
	        	StringBuilder sb = new StringBuilder("");
	        	for(IMetadataColumn column: columnList){
	  				sb.append("\"");
	  				sb.append(column.getLabel());
	  				sb.append("\",");
	        	}
	        	sb.deleteCharAt(sb.lastIndexOf(","));
	        	IConnection outgoingConn = outgoingConns.get(0);
				if(outgoingConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
	        	
    stringBuffer.append(TEXT_258);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_259);
    stringBuffer.append(sb);
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_262);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_263);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_264);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_265);
    stringBuffer.append(entitynamealllower);
    stringBuffer.append(TEXT_266);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_268);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_269);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_270);
    boolean debugPaging=false;
	        	if(debugPaging){
    stringBuffer.append(TEXT_271);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_272);
    }
    stringBuffer.append(TEXT_273);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_274);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_275);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_276);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_277);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_278);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_279);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_280);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_281);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_282);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_283);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_284);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_285);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_286);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_287);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_288);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_289);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_290);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_291);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_293);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_294);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_295);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_296);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_297);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_298);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_300);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_301);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_302);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_303);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_304);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_305);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_306);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_307);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_308);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_309);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_310);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_311);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_312);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_313);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_314);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_315);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_316);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_317);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_318);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_319);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_320);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_321);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_322);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_324);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_325);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_326);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_327);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_328);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_329);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_330);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_331);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_332);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_333);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_334);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_335);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_336);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_337);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_338);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_339);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_340);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_341);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_342);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_343);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_344);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_345);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_346);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_347);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_348);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_349);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_350);
    //2009-04-14T10:09:42-07:00 ---> 2009-04-14T10:09:42-0700
    stringBuffer.append(TEXT_351);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_352);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_353);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_354);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_355);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_356);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_357);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_358);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_359);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_360);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_361);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_362);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_363);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_364);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_365);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_366);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_367);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_368);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_369);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_370);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_371);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_372);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_373);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_374);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_375);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_376);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_377);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_378);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_379);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_380);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_381);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_383);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_384);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_385);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_387);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_388);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_389);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_390);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_391);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_392);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_393);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_394);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_395);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_396);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_397);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_398);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_399);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_400);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_401);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_402);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_403);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_404);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_405);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_406);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_407);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_408);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_409);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_410);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_411);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_412);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_413);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_414);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_415);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_416);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_417);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_418);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_419);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_420);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_421);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_422);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_423);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_424);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_425);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_426);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_427);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_428);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_429);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_430);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_431);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_432);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_433);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_434);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_435);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_436);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_437);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_438);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_439);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_440);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_441);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_442);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_443);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_444);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_445);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_446);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_447);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_448);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_449);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_450);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_451);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_452);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_453);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_454);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_455);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_456);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_457);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_458);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_459);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_460);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_461);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_462);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_463);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_464);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_465);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_466);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_467);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_468);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_469);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_470);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_471);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_472);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_473);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_474);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_475);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_476);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_477);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_478);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_479);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_480);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_481);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_482);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_483);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_484);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_485);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_486);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_487);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_488);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_489);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_490);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_491);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_492);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_493);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_494);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_495);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_496);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_497);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_498);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_499);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_500);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_501);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_502);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_503);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_504);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_505);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_506);
    
							for( int i = 0; i < columnList.size(); i++) {

								IMetadataColumn column = columnList.get(i);

								String typeToGenerate = JavaTypesManager.getTypeToGenerate(column.getTalendType(), column.isNullable());

								JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());

								String patternValue = column.getPattern() == null || column.getPattern().trim().length() == 0 ? null : column.getPattern();
								
    stringBuffer.append(TEXT_507);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_508);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_509);
    
	        						if (javaType == JavaTypesManager.STRING) {
	        					
    stringBuffer.append(TEXT_510);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_511);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_512);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_513);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_514);
    
	        						} else if(javaType == JavaTypesManager.DATE) { // Date
	        					
    stringBuffer.append(TEXT_515);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_516);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_517);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_518);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_519);
    stringBuffer.append( patternValue );
    stringBuffer.append(TEXT_520);
    
	        						} else if(javaType == JavaTypesManager.DOUBLE) { // Double
	        					
    stringBuffer.append(TEXT_521);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_522);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_523);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_524);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_525);
    
	        						} else if(javaType == JavaTypesManager.BIGDECIMAL) { // BigDecimal
	        					
    stringBuffer.append(TEXT_526);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_527);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_528);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_529);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_530);
    
	        						} else if(javaType == JavaTypesManager.FLOAT) { // Float
	        					
    stringBuffer.append(TEXT_531);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_532);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_533);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_534);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_535);
    
	        						} else if(javaType == JavaTypesManager.INTEGER) { // Integer
	        					
    stringBuffer.append(TEXT_536);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_537);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_538);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_539);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_540);
    
	        						} else if(javaType == JavaTypesManager.BOOLEAN) { // Boolean
	        					
    stringBuffer.append(TEXT_541);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_542);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_543);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_544);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_545);
    
	        						} else if(javaType == JavaTypesManager.OBJECT) { // OBJECT
	        					
    stringBuffer.append(TEXT_546);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_547);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_548);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_549);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_550);
    
	        						} else  { // other
	        					
    stringBuffer.append(TEXT_551);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_552);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_553);
    stringBuffer.append( typeToGenerate );
    stringBuffer.append(TEXT_554);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_555);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_556);
    
	        						}
	        					
    stringBuffer.append(TEXT_557);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_558);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_559);
    stringBuffer.append(JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate));
    stringBuffer.append(TEXT_560);
    
							}
							
    stringBuffer.append(TEXT_561);
    
				}

			}else{//api 2011
			
    stringBuffer.append(TEXT_562);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_563);
    stringBuffer.append(username);
    stringBuffer.append(TEXT_564);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_565);
    stringBuffer.append(orgName);
    stringBuffer.append(TEXT_566);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_567);
    stringBuffer.append(timeout);
    stringBuffer.append(TEXT_568);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_569);
    stringBuffer.append(reuseHttpClient);
    stringBuffer.append(TEXT_570);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_571);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_572);
    stringBuffer.append(discWSDL);
    stringBuffer.append(TEXT_573);
    
	     		String entityname = ElementParameterParser.getValue(node, "__ENTITYNAME__").trim();
	     		String customEntityname = ElementParameterParser.getValue(node, "__CUSTOM_ENTITY_NAME__");
	     		if("CustomEntity".equals(entityname)){
					entityname = customEntityname.replaceAll("\"","");
				}
	     		String entitynamealllower = entityname.toLowerCase();
	     		String logical = ElementParameterParser.getValue(node,"__LOGICAL_OP__");
	     		List<Map<String, String>> keyColumns = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__CONDITIONS__");
	     		
    stringBuffer.append(TEXT_574);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_575);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_576);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_577);
    
	        	if(keyColumns.size()>0 ){
				
    stringBuffer.append(TEXT_578);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_579);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_580);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_581);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_582);
    
	            	for(Map<String, String> keyColumn:keyColumns){
	        		
    stringBuffer.append(TEXT_583);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_584);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_585);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_586);
    stringBuffer.append(keyColumn.get("INPUT_COLUMN"));
    stringBuffer.append(TEXT_587);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_588);
    stringBuffer.append(keyColumn.get("OPERATOR"));
    stringBuffer.append(TEXT_589);
    
	        			if(!("").equals(keyColumn.get("RVALUE"))){
	        			
    stringBuffer.append(TEXT_590);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_591);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_592);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_593);
    stringBuffer.append(keyColumn.get("RVALUE"));
    stringBuffer.append(TEXT_594);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_595);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_596);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_597);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_598);
    
	            		}
	        		}
	        		
    stringBuffer.append(TEXT_599);
    
	        		if(!("").equals(logical)){
	        		
    stringBuffer.append(TEXT_600);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_601);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_602);
    stringBuffer.append(logical);
    stringBuffer.append(TEXT_603);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_604);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_605);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_606);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_607);
    
	        		}
	        	}
	        	
    stringBuffer.append(TEXT_608);
    
	        	StringBuilder sb = new StringBuilder("");
	        	for(IMetadataColumn column: columnList){
	  				sb.append("\"");
	  				sb.append(column.getLabel());
	  				sb.append("\",");
	        	}
	        	sb.deleteCharAt(sb.lastIndexOf(","));
	        	IConnection outgoingConn = outgoingConns.get(0);
				if(outgoingConn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
	        	
    stringBuffer.append(TEXT_609);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_610);
    stringBuffer.append(sb);
    stringBuffer.append(TEXT_611);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_612);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_613);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_614);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_615);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_616);
    stringBuffer.append(entitynamealllower);
    stringBuffer.append(TEXT_617);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_618);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_619);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_620);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_621);
    boolean debugPaging=false;
	        	if(debugPaging){
    stringBuffer.append(TEXT_622);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_623);
    }
    stringBuffer.append(TEXT_624);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_625);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_626);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_627);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_628);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_629);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_630);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_631);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_632);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_633);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_634);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_635);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_636);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_637);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_638);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_639);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_640);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_641);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_642);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_643);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_644);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_645);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_646);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_647);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_648);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_649);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_650);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_651);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_652);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_653);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_654);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_655);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_656);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_657);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_658);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_659);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_660);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_661);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_662);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_663);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_664);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_665);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_666);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_667);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_668);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_669);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_670);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_671);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_672);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_673);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_674);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_675);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_676);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_677);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_678);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_679);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_680);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_681);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_682);
    
						for(int i = 0; i < columnList.size(); i++){//for begin

							IMetadataColumn column = columnList.get(i);

							String typeToGenerate = JavaTypesManager.getTypeToGenerate(column.getTalendType(), column.isNullable());

							JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());

							String patternValue = column.getPattern() == null || column.getPattern().trim().length() == 0 ? null : column.getPattern();
							
    stringBuffer.append(TEXT_683);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_684);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_685);
    
	       						if(javaType == JavaTypesManager.STRING || javaType == JavaTypesManager.OBJECT){
	        					
    stringBuffer.append(TEXT_686);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_687);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_688);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_689);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_690);
    
	        					}else if(javaType == JavaTypesManager.DATE){ // Date
	        					
    stringBuffer.append(TEXT_691);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_692);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_693);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_694);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_695);
    stringBuffer.append( patternValue );
    stringBuffer.append(TEXT_696);
    
	        					}else{ // other
	        					
    stringBuffer.append(TEXT_697);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_698);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_699);
    stringBuffer.append(typeToGenerate);
    stringBuffer.append(TEXT_700);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_701);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_702);
    
	        					}
	        					
    stringBuffer.append(TEXT_703);
    stringBuffer.append(outgoingConn.getName());
    stringBuffer.append(TEXT_704);
    stringBuffer.append(columnList.get(i).getLabel());
    stringBuffer.append(TEXT_705);
    stringBuffer.append(JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate));
    stringBuffer.append(TEXT_706);
    
						}//for end
						
    stringBuffer.append(TEXT_707);
    
				}
			}
		}
	}
}

    
    }

    stringBuffer.append(TEXT_708);
    return stringBuffer.toString();
  }
}
