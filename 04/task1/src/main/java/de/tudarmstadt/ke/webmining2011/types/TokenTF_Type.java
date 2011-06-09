
/* First created by JCasGen Mon Jun 06 18:43:33 CEST 2011 */
package de.tudarmstadt.ke.webmining2011.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Term frequency of a token
 * Updated by JCasGen Mon Jun 06 18:43:33 CEST 2011
 * @generated */
public class TokenTF_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TokenTF_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TokenTF_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TokenTF(addr, TokenTF_Type.this);
  			   TokenTF_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TokenTF(addr, TokenTF_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = TokenTF.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.tudarmstadt.ke.webmining2011.types.TokenTF");
 
  /** @generated */
  final Feature casFeat_stopword;
  /** @generated */
  final int     casFeatCode_stopword;
  /** @generated */ 
  public boolean getStopword(int addr) {
        if (featOkTst && casFeat_stopword == null)
      jcas.throwFeatMissing("stopword", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_stopword);
  }
  /** @generated */    
  public void setStopword(int addr, boolean v) {
        if (featOkTst && casFeat_stopword == null)
      jcas.throwFeatMissing("stopword", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_stopword, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tfidf;
  /** @generated */
  final int     casFeatCode_tfidf;
  /** @generated */ 
  public float getTfidf(int addr) {
        if (featOkTst && casFeat_tfidf == null)
      jcas.throwFeatMissing("tfidf", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_tfidf);
  }
  /** @generated */    
  public void setTfidf(int addr, float v) {
        if (featOkTst && casFeat_tfidf == null)
      jcas.throwFeatMissing("tfidf", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    ll_cas.ll_setFloatValue(addr, casFeatCode_tfidf, v);}
    
  
 
  /** @generated */
  final Feature casFeat_propability;
  /** @generated */
  final int     casFeatCode_propability;
  /** @generated */ 
  public float getPropability(int addr) {
        if (featOkTst && casFeat_propability == null)
      jcas.throwFeatMissing("propability", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_propability);
  }
  /** @generated */    
  public void setPropability(int addr, float v) {
        if (featOkTst && casFeat_propability == null)
      jcas.throwFeatMissing("propability", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    ll_cas.ll_setFloatValue(addr, casFeatCode_propability, v);}
    
  
 
  /** @generated */
  final Feature casFeat_count;
  /** @generated */
  final int     casFeatCode_count;
  /** @generated */ 
  public int getCount(int addr) {
        if (featOkTst && casFeat_count == null)
      jcas.throwFeatMissing("count", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return ll_cas.ll_getIntValue(addr, casFeatCode_count);
  }
  /** @generated */    
  public void setCount(int addr, int v) {
        if (featOkTst && casFeat_count == null)
      jcas.throwFeatMissing("count", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    ll_cas.ll_setIntValue(addr, casFeatCode_count, v);}
    
  
 
  /** @generated */
  final Feature casFeat_token;
  /** @generated */
  final int     casFeatCode_token;
  /** @generated */ 
  public String getToken(int addr) {
        if (featOkTst && casFeat_token == null)
      jcas.throwFeatMissing("token", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    return ll_cas.ll_getStringValue(addr, casFeatCode_token);
  }
  /** @generated */    
  public void setToken(int addr, String v) {
        if (featOkTst && casFeat_token == null)
      jcas.throwFeatMissing("token", "de.tudarmstadt.ke.webmining2011.types.TokenTF");
    ll_cas.ll_setStringValue(addr, casFeatCode_token, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public TokenTF_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_stopword = jcas.getRequiredFeatureDE(casType, "stopword", "uima.cas.Boolean", featOkTst);
    casFeatCode_stopword  = (null == casFeat_stopword) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_stopword).getCode();

 
    casFeat_tfidf = jcas.getRequiredFeatureDE(casType, "tfidf", "uima.cas.Float", featOkTst);
    casFeatCode_tfidf  = (null == casFeat_tfidf) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tfidf).getCode();

 
    casFeat_propability = jcas.getRequiredFeatureDE(casType, "propability", "uima.cas.Float", featOkTst);
    casFeatCode_propability  = (null == casFeat_propability) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_propability).getCode();

 
    casFeat_count = jcas.getRequiredFeatureDE(casType, "count", "uima.cas.Integer", featOkTst);
    casFeatCode_count  = (null == casFeat_count) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_count).getCode();

 
    casFeat_token = jcas.getRequiredFeatureDE(casType, "token", "uima.cas.String", featOkTst);
    casFeatCode_token  = (null == casFeat_token) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_token).getCode();

  }
}



    