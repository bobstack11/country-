package com.egouos.pojo;

import java.io.Serializable;

public class Producttype
  implements Serializable
{
  private Integer typeId;
  private String typeName;
  private String ftypeId;
  private String stypeId;
  private String attribute70;
  
  public Producttype() {}
  
  public Producttype(String typeName, String ftypeId, String stypeId)
  {
    this.typeName = typeName;
    this.ftypeId = ftypeId;
    this.stypeId = stypeId;
  }
  
  public Producttype(String typeName, String ftypeId, String stypeId, String attribute70)
  {
    this.typeName = typeName;
    this.ftypeId = ftypeId;
    this.stypeId = stypeId;
    this.attribute70 = attribute70;
  }
  
  public Integer getTypeId()
  {
    return this.typeId;
  }
  
  public void setTypeId(Integer typeId)
  {
    this.typeId = typeId;
  }
  
  public String getTypeName()
  {
    return this.typeName;
  }
  
  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
  }
  
  public String getFtypeId()
  {
    return this.ftypeId;
  }
  
  public void setFtypeId(String ftypeId)
  {
    this.ftypeId = ftypeId;
  }
  
  public String getStypeId()
  {
    return this.stypeId;
  }
  
  public void setStypeId(String stypeId)
  {
    this.stypeId = stypeId;
  }
  
  public String getAttribute70()
  {
    return this.attribute70;
  }
  
  public void setAttribute70(String attribute70)
  {
    this.attribute70 = attribute70;
  }
}
