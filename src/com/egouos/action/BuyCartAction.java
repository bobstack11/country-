package com.egouos.action;

import com.egouos.pojo.Product;
import com.egouos.service.ProductService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BuyCartAction")
public class BuyCartAction
  extends ActionSupport
{
  private static final long serialVersionUID = -4695580454502546044L;
  private String id;
  @Autowired
  private ProductService productService;
  private Product product;
  
  public String index()
  {
    this.product = ((Product)this.productService.findById(this.id));
    
    return "index";
  }
}
