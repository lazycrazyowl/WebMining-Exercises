

/* First created by JCasGen Sun May 22 12:34:12 CEST 2011 */
package de.tudarmstadt.ke.webmining2011.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Term frequency of a token
 * Updated by JCasGen Sun May 22 12:34:12 CEST 2011
 * XML source: ./src/main/resources/desc/types/typ-system.xml
 * @generated */
public class TF extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(TF.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected TF() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public TF(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public TF(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public TF(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: propability

  /** getter for propability - gets 
   * @generated */
  public float getPropability() {
    if (TF_Type.featOkTst && ((TF_Type)jcasType).casFeat_propability == null)
      jcasType.jcas.throwFeatMissing("propability", "de.tudarmstadt.ke.webmining2011.types.TF");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((TF_Type)jcasType).casFeatCode_propability);}
    
  /** setter for propability - sets  
   * @generated */
  public void setPropability(float v) {
    if (TF_Type.featOkTst && ((TF_Type)jcasType).casFeat_propability == null)
      jcasType.jcas.throwFeatMissing("propability", "de.tudarmstadt.ke.webmining2011.types.TF");
    jcasType.ll_cas.ll_setFloatValue(addr, ((TF_Type)jcasType).casFeatCode_propability, v);}    
   
    
  //*--------------*
  //* Feature: count

  /** getter for count - gets 
   * @generated */
  public int getCount() {
    if (TF_Type.featOkTst && ((TF_Type)jcasType).casFeat_count == null)
      jcasType.jcas.throwFeatMissing("count", "de.tudarmstadt.ke.webmining2011.types.TF");
    return jcasType.ll_cas.ll_getIntValue(addr, ((TF_Type)jcasType).casFeatCode_count);}
    
  /** setter for count - sets  
   * @generated */
  public void setCount(int v) {
    if (TF_Type.featOkTst && ((TF_Type)jcasType).casFeat_count == null)
      jcasType.jcas.throwFeatMissing("count", "de.tudarmstadt.ke.webmining2011.types.TF");
    jcasType.ll_cas.ll_setIntValue(addr, ((TF_Type)jcasType).casFeatCode_count, v);}    
   
    
  //*--------------*
  //* Feature: token

  /** getter for token - gets 
   * @generated */
  public String getToken() {
    if (TF_Type.featOkTst && ((TF_Type)jcasType).casFeat_token == null)
      jcasType.jcas.throwFeatMissing("token", "de.tudarmstadt.ke.webmining2011.types.TF");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TF_Type)jcasType).casFeatCode_token);}
    
  /** setter for token - sets  
   * @generated */
  public void setToken(String v) {
    if (TF_Type.featOkTst && ((TF_Type)jcasType).casFeat_token == null)
      jcasType.jcas.throwFeatMissing("token", "de.tudarmstadt.ke.webmining2011.types.TF");
    jcasType.ll_cas.ll_setStringValue(addr, ((TF_Type)jcasType).casFeatCode_token, v);}    
  }

    