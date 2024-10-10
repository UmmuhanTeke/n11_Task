import Utility.BaseDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class POM {

    public POM() {
        PageFactory.initElements(BaseDriver.driver, this);
    }

    @FindBy(css = "[class='btnSignIn']")
    public WebElement loginButton;

    @FindBy(id = "email")
    public WebElement email;

    @FindBy(id = "password")
    public WebElement password;

    @FindBy(id = "loginButton")
    public WebElement loginClick;

    @FindBy(id = "searchData")
    public WebElement searchBox;

    @FindBy(css = "[class='iconsSearch']")
    public WebElement searchButton;

    @FindBy(css = "[class='resultView']")
    public WebElement productFound;

    @FindBy(xpath = "(//span[@class='followBtn']) [3]")
    public WebElement favouriteIcon;

    @FindBy(css = "[class='iconFavoritesWhite']")
    public WebElement favourites;

    @FindBy(css = "[id='favouriteList'] li")
    public List<WebElement> favouriteProductCheck;
}
