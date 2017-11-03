package com.egouos.pojo;

import java.io.Serializable;

public class Productimage
  implements Serializable
{
  private Integer productImageId;
  private Integer piProductId;
  private String image;
  private String imageType;
  private String attribute75;
  
  public Productimage() {}
  
  public Productimage(Integer productImageId, Integer piProductId, String image, String imageType, String attribute75)
  {
    this.productImageId = productImageId;
    this.piProductId = piProductId;
    this.image = image;
    this.imageType = imageType;
    this.attribute75 = attribute75;
  }
  
  public Integer getProductImageId()
  {
    return this.productImageId;
  }
  
  public void setProductImageId(Integer productImageId)
  {
    this.productImageId = productImageId;
  }
  
  public Integer getPiProductId()
  {
    return this.piProductId;
  }
  
  public void setPiProductId(Integer piProductId)
  {
    this.piProductId = piProductId;
  }
  
  public String getImage()
  {
    return this.image;
  }
  
  public void setImage(String image)
  {
    this.image = image;
  }
  
  public String getAttribute75()
  {
    return this.attribute75;
  }
  
  public void setAttribute75(String attribute75)
  {
    this.attribute75 = attribute75;
  }
  
  public String getImageType()
  {
    return this.imageType;
  }
  
  public void setImageType(String imageType)
  {
    this.imageType = imageType;
  }
}
