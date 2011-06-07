

/* First created by JCasGen Mon Jun 06 18:43:32 CEST 2011 */
package de.tudarmstadt.ke.webmining2011.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Term frequency of a token
 * Updated by JCasGen Mon Jun 06 18:43:32 CEST 2011
 * XML source: /home/jens/programming/scala/WebMining-Exercises/04/task1/src/main/resources/desc/types/webmining.xml
 * @generated */
public class TokenTF extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(TokenTF.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected TokenTF() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public TokenTF(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public TokenTF(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public TokenTF(JCas jcas, int begin, int end) {
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
  //* Feature: stopword

  /** getter for stopword - gets 
   * @generated */
  public boolean getStopword() {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_stopword == null)
      jcasType.jcas.throwFeatMissing("stopword", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((TokenTF_Type)jcasType).casFeatCode_stopword);}
    
  /** setter for stopword - sets  
   * @generated */
  public void setStopword(boolean v) {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_stopword == null)
      jcasType.jcas.throwFeatMissing("stopword", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((TokenTF_Type)jcasType).casFeatCode_stopword, v);}    
   
    
  //*--------------*
  //* Feature: tfidf

  /** getter for tfidf - gets 
   * @generated */
  public float getTfidf() {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_tfidf == null)
      jcasType.jcas.throwFeatMissing("tfidf", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((TokenTF_Type)jcasType).casFeatCode_tfidf);}
    
  /** setter for tfidf - sets  
   * @generated */
  public void setTfidf(float v) {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_tfidf == null)
      jcasType.jcas.throwFeatMissing("tfidf", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    jcasType.ll_cas.ll_setFloatValue(addr, ((TokenTF_Type)jcasType).casFeatCode_tfidf, v);}    
   
    
  //*--------------*
  //* Feature: propability

  /** getter for propability - gets 
   * @generated */
  public float getPropability() {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_propability == null)
      jcasType.jcas.throwFeatMissing("propability", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((TokenTF_Type)jcasType).casFeatCode_propability);}
    
  /** setter for propability - sets  
   * @generated */
  public void setPropability(float v) {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_propability == null)
      jcasType.jcas.throwFeatMissing("propability", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    jcasType.ll_cas.ll_setFloatValue(addr, ((TokenTF_Type)jcasType).casFeatCode_propability, v);}    
   
    
  //*--------------*
  //* Feature: count

  /** getter for count - gets 
   * @generated */
  public int getCount() {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_count == null)
      jcasType.jcas.throwFeatMissing("count", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return jcasType.ll_cas.ll_getIntValue(addr, ((TokenTF_Type)jcasType).casFeatCode_count);}
    
  /** setter for count - sets  
   * @generated */
  public void setCount(int v) {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_count == null)
      jcasType.jcas.throwFeatMissing("count", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    jcasType.ll_cas.ll_setIntValue(addr, ((TokenTF_Type)jcasType).casFeatCode_count, v);}    
   
    
  //*--------------*
  //* Feature: token

  /** getter for token - gets 
   * @generated */
  public String getToken() {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_token == null)
      jcasType.jcas.throwFeatMissing("token", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TokenTF_Type)jcasType).casFeatCode_token);}
    
  /** setter for token - sets  
   * @generated */
  public void setToken(String v) {
    if (TokenTF_Type.featOkTst && ((TokenTF_Type)jcasType).casFeat_token == null)
      jcasType.jcas.throwFeatMissing("token", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    jcasType.ll_cas.ll_setStringValue(addr, ((TokenTF_Type)jcasType).casFeatCode_token, v);}    
  }

    