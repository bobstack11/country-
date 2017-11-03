package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Visitors;
import java.util.List;

public abstract interface VisitorsService
  extends TService<Visitors, Integer>
{
  public abstract Pagination getVisitors(String paramString, int paramInt1, int paramInt2);
  
  public abstract Visitors findVisitorsByUserIdAndVisitorsId(String paramString1, String paramString2);
  
  public abstract List findVisitors(String paramString1, String paramString2, String paramString3, String paramString4);
}
