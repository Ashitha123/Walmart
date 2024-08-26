package walmart;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



public class AddToCart 
{	
	
	
  WebDriver driver ;
  
  
  //Calculate the total price. TotalPrice = Quantity * ProductPrice
  
  String productPriceText = driver.findElement(By.class("w_iUH7"));  
  String[] ar1 = 	productPriceText.split("$");

  double productPrice = Double.parseDouble(ar1[1]); 
  
  public double priceCalculator(int quantity, double productPrice);
  
  {
	  double result = productPrice * quantity;
	  return result;
  }
  
  
	
  @Test
  @Parameters({"productName", "quantity"})
  public void verifyAddToCartWorkflow(String productName, int quantity) 
  {
      
      driver.get("https://walmart.com");
  
      Assert.assertEquals(driver.getCurrentUrl(), "https://walmart.com"); 
      Assert.assertTrue(driver.getTitle().contains("Walmart"));
      
      WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/span/header/form/div/input")); 
      searchBox.sendKeys(productName);
      searchBox.submit();
      
      
      List<WebElement> productItems = driver.findElements(By.cssSelector("[data-testid='item-stack'] span[data-automation-id='product-title']")); 
      for (WebElement item : productItems) 
      {
          if (item.getText().contains(productName)) 
          Assert.assertTrue(true, productName);
      }
     
      WebElement nextPage = driver.findElement(By.cssSelector("[data-testid='NextPage'] "));
       if (nextPage != null && nextPage.isDisplayed()) 
       {
              nextPage.click();
          } 
       else 
       {
              break;
          }    
      
       WebElement lastItem = driver.findElement(By.cssSelector("[data-testid='item-stack'] span[data-automation-id='product-title']"));
       lastItem.click();
       WebElement addToCartButton = driver.findElement(By.title("add to cart"));
       addToCartButton.click();
       
       //Add more quantity
       WebElement cartIcon = driver.findElement(By.cssSelector(".cart-icon")); 
       cartIcon.click();
       WebElement quantityBox = driver.findElement(By.class("ld ld-Plus")); 
       quantityBox.clear();
       quantityBox.sendKeys(String.valueOf(quantity));
       WebElement updateButton = driver.findElement(By.cssSelector(".update-cart")); 
       updateButton.click();
       
       
       // Calculate total price
       WebElement totalPriceElement = driver.findElement(By.cssSelector("[data-testid='grand-total-label'])); 
       String totalPriceText = totalPriceElement.getText();
       double totalPrice = Double.parseDouble(totalPriceText.replaceAll("[^0-9.]", ""));
       Assert.assertEquals(totalPrice, priceCalculator(quantity,productPrice)); 
   
       
       
  
  @BeforeMethod
  public void beforeMethod() 
  {
	  System.setProperty("webdriver.chrome.driver", "Users/ashitha/eclipse-workspace/New/Walmart/src/");
      driver = new ChromeDriver();
  }
  

  @AfterMethod
  public void afterMethod() 
  {
  
	  driver.close();
		System.out.println("Finished Test On Chrome Browser");
  }

}
