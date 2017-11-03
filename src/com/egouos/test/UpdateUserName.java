package com.egouos.test;

import com.egouos.pojo.User;
import com.egouos.service.UserService;
import java.io.PrintStream;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
@Repository
public class UpdateUserName
{
  @Autowired
  private UserService userService;
  
  @Test
  public void go()
  {
    List<User> userList = this.userService.loadAll();
    int updateCount = 0;
    for (User user : userList)
    {
      String userName = user.getUserName();
      if (userName.indexOf("888") != -1)
      {
        userName = userName.replaceAll("888", "***");
        user.setUserName(userName);
        this.userService.add(user);
        updateCount++;
      }
    }
    System.err.println("updateCount:" + updateCount);
  }
}
