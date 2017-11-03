package com.egouos.service;

import com.egouos.pojo.Recommend;
import java.util.List;

public abstract interface RecommendService
  extends TService<Recommend, Integer>
{
  public abstract List getRecommend();
}
