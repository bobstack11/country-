package com.egouos.pojo;

import java.io.Serializable;

public class Recommend
  implements Serializable
{
  private Integer recommendId;
  private Integer spellbuyProductId;
  private String date;
  
  public Recommend() {}
  
  public Recommend(Integer recommendId, Integer spellbuyProductId, String date)
  {
    this.recommendId = recommendId;
    this.spellbuyProductId = spellbuyProductId;
    this.date = date;
  }
  
  public Integer getRecommendId()
  {
    return this.recommendId;
  }
  
  public void setRecommendId(Integer recommendId)
  {
    this.recommendId = recommendId;
  }
  
  public Integer getSpellbuyProductId()
  {
    return this.spellbuyProductId;
  }
  
  public void setSpellbuyProductId(Integer spellbuyProductId)
  {
    this.spellbuyProductId = spellbuyProductId;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
}
