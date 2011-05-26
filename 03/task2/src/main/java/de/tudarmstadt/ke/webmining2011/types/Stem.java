

/* First created by JCasGen Mon May 23 10:09:44 CEST 2011 */
package de.tudarmstadt.ke.webmining2011.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A stemming annotation
 * Updated by JCasGen Mon May 23 10:09:44 CEST 2011
 * XML source: ./src/main/resources/desc/types/typ-system.xml
 * @generated */
public class Stem extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Stem.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Stem() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Stem(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Stem(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Stem(JCas jcas, int begin, int end) {
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
     
}

    