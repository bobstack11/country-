package com.egouos.test;

import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.ProductService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.util.NewLotteryUtil;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext*.xml"})
@Repository
@Service("updateLatestlottery")
public class UpdateLatestlottery
{
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ProductService productService;
  @Autowired
  private NewLotteryUtil newLotteryUtil;
  private ProductCart productCart;
  private Product product;
  
  @Test
  public void go()
    throws InterruptedException
  {
    List<Spellbuyproduct> spellbuyproductList = this.spellbuyproductService.UpdateLatestlotteryGetList();
    for (Spellbuyproduct spellbuyproduct : spellbuyproductList)
    {
      this.productCart = new ProductCart();
      List<Object[]> proList = this.spellbuyproductService.findByProductId(spellbuyproduct.getSpellbuyProductId().intValue());
      this.product = ((Product)((Object[])proList.get(0))[0]);
      spellbuyproduct = (Spellbuyproduct)((Object[])proList.get(0))[1];
      this.productCart.setHeadImage(this.product.getHeadImage());
      this.productCart.setProductId(spellbuyproduct.getSpellbuyProductId());
      this.productCart.setProductName(this.product.getProductName());
      this.productCart.setProductPrice(this.product.getProductPrice());
      this.productCart.setProductTitle(this.product.getProductTitle());
      this.productCart.setCurrentBuyCount(spellbuyproduct.getSpellbuyCount());
      this.productCart.setProductPeriod(spellbuyproduct.getProductPeriod());
      
      this.newLotteryUtil.lottery(this.productCart);
    }
  }
}
