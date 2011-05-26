

/* First created by JCasGen Mon May 23 10:09:44 CEST 2011 */
package de.tudarmstadt.ke.webmining2011.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Category of the annotation
 * Updated by JCasGen Mon May 23 10:09:44 CEST 2011
 * XML source: ./src/main/resources/desc/types/typ-system.xml
 * @generated */
public class Category extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Category.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Category() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Category(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Category(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Category(JCas jcas, int begin, int end) {
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
  //* Feature: category

  /** getter for category - gets 
   * @generated */
  public String getCategory() {
    if (Category_Type.featOkTst && ((Category_Type)jcasType).casFeat_category == null)
      jcasType.jcas.throwFeatMissing("category", "de.tudarmstadt.ke.webmining2011.types.Category");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Category_Type)jcasType).casFeatCode_category);}
    
  /** setter for category - sets  
   * @generated */
  public void setCategory(String v) {
    if (Category_Type.featOkTst && ((Category_Type)jcasType).casFeat_category == null)
      jcasType.jcas.throwFeatMissing("category", "de.tudarmstadt.ke.webmining2011.types.Category");
    jcasType.ll_cas.ll_setStringValue(addr, ((Category_Type)jcasType).casFeatCode_category, v);}    
  }

    