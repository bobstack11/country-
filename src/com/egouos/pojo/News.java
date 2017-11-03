package com.egouos.pojo;

import java.io.Serializable;

public class News
  implements Serializable
{
  private Integer newsId;
  private String title;
  private String postDate;
  private String content;
  
  public News() {}
  
  public News(String title, String postDate, String content)
  {
    this.title = title;
    this.postDate = postDate;
    this.content = content;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getPostDate()
  {
    return this.postDate;
  }
  
  public void setPostDate(String postDate)
  {
    this.postDate = postDate;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public Integer getNewsId()
  {
    return this.newsId;
  }
  
  public void setNewsId(Integer newsId)
  {
    this.newsId = newsId;
  }
}
