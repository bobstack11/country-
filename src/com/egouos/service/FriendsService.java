package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Friends;

public abstract interface FriendsService
  extends TService<Friends, Integer>
{
  public abstract Pagination getFriends(String paramString, int paramInt1, int paramInt2);
}
