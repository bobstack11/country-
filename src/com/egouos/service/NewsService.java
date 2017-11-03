package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.News;
import java.util.List;

public abstract interface NewsService
  extends TService<News, Integer>
{
  public abstract Pagination newsList(int paramInt1, int paramInt2);
  
  public abstract List indexNews();
}
