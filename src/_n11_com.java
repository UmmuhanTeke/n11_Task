import Utility.BaseDriver;
import Utility.ConfigReader;
import Utility.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class _n11_com extends BaseDriver {

    @Test(dataProvider = "userData")
    @Parameters({"searchText"})
    public void n11_com(String email, String password, String product) {
        POM locator = new POM();

        //1-http://www.n11.com sitesine gidecek ve anasayfayı açtığını doğrulayacak.
        wait.until(ExpectedConditions.urlToBe(ConfigReader.getProperty("URL")));
        Assert.assertEquals(ConfigReader.getProperty("URL"), "https://www.n11.com/");

        WebElement shadowParent = driver.findElement(By.cssSelector("efilli-layout-dynamic"));
        SearchContext shadowContent = shadowParent.getShadowRoot();
        WebElement rejectButton = shadowContent.findElement(By.cssSelector("[data-name='Accept Button']"));
        rejectButton.click();

        //2-Login ekranını açıp, bir kullanıcı ile login olacak.
        wait.until(ExpectedConditions.elementToBeClickable(locator.loginButton));
        locator.loginButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(locator.email));
        locator.email.sendKeys(email);

        wait.until(ExpectedConditions.elementToBeClickable(locator.password));
        locator.password.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(locator.loginClick));
        locator.loginClick.click();

        //3-Ekranın üzerindeki search alanına "samsung" yazıp ara butonuna tıklatacak.
        wait.until(ExpectedConditions.elementToBeClickable(locator.searchBox));
        locator.searchBox.sendKeys(product);

        wait.until(ExpectedConditions.elementToBeClickable(locator.searchButton));
        locator.searchButton.click();

        //4-Gelen sayfada 'samsung' için sonuç bulunduğunu onaylayacak.
        wait.until(ExpectedConditions.visibilityOf(locator.productFound));
        Assert.assertTrue(locator.productFound.getText().contains("sonuç bulundu"));

        //5-Üstten 3. ürünün içindeki 'favorilerine ekle' butonuna tıklayacak.
        wait.until(ExpectedConditions.elementToBeClickable(locator.favouriteIcon));
        locator.favouriteIcon.click();

        //6-Ekranın en üstündeki 'favorilerim' linkine tıklayacak.
        wait.until(ExpectedConditions.elementToBeClickable(locator.favourites));
        Tools.jsClick(locator.favourites);

        //7-Açılan sayfada bir önceki sayfada izlemeye alınmış ürünün bulunduğunu onaylayacak.
        boolean A9found = false;
        for (WebElement products : locator.favouriteProductCheck) {
            String productName = products.getText();
            if (productName.contains("A9")) {
                A9found = true;
                System.out.println(productName);
            }
        }

        if (A9found) {
            System.out.println("This product was found in favourites");
        } else {
            System.out.println("This product was not found in favourites");
        }

        //8-Favorilere alınan bu ürünün yanındaki 'Kaldır' butonuna basarak, favorilerimden çıkaracak.
        for (WebElement products : locator.favouriteProductCheck) {
            String productName = products.getText();
            if (productName.contains("A9")) {
                WebElement productDelete = products.findElement(By.cssSelector("[class='deleteProFromFavorites']"));
                productDelete.click();
                break;
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        wait.until(ExpectedConditions.elementToBeClickable(locator.favourites));
        Tools.jsClick(locator.favourites);

        //9-Sayfada bu ürünün artık favorilere alınmadığını onaylayacak.
        for (WebElement products : locator.favouriteProductCheck) {
            String productName = products.getText();
            System.out.println(productName);
            Assert.assertTrue(!productName.contains(" Tab A9 Wi-Fi SM-X110"), "The product is in favorites");
        }
    }

    @DataProvider
    public Object[][] userData() {
        return new Object[][] {
                {ConfigReader.getProperty("email"), ConfigReader.getProperty("password")}
        };
    }
}

