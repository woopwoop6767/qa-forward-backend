package rf.subscribe.tests.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rf.subscribe.logic.TextGenerator;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.services.BasketOnlineService;
import rf.subscribe.logic.services.CategoriesService;
import rf.subscribe.logic.services.LeasingApplicationService;
import rf.subscribe.logic.services.ModelsService;

public class test005CheckOrderIphoneMissingPassportAndSelfie implements Specification, TextGenerator {

    private String categoryCode;
    private String modelCode;
    private String goodCode;
    private String basketId;
    private String typeApplication;
    private String applicationID;
    private String authToken;
    private String countModel = "1";
    private String smsCode = "1111";


    private CategoriesService categoriesService;
    private ModelsService modelsService;
    private BasketOnlineService basketOnlineService;
    private LeasingApplicationService leasingApplicationService;

    @BeforeMethod
    public void setUp() {
        categoriesService = new CategoriesService();
        modelsService = new ModelsService();
        basketOnlineService = new BasketOnlineService();
        leasingApplicationService = new LeasingApplicationService();
    }

    @Test(description = "Making an application for an iPhone without passport and selfie")
    void test() {
        categoryCode = categoriesService
                .getCodeCategoryIphone();

        modelCode = modelsService
                .getRandomModelCode(categoryCode);

        goodCode = modelsService
                .getRandomModelGoodCode(modelCode);

        basketId = basketOnlineService
                .getBasketId(countModel, goodCode);

        typeApplication = basketOnlineService
                .getTypeApplication(basketId);

        applicationID = leasingApplicationService
                .getApplicationId(basketId, typeApplication, generateEmail(), generateMobilePhone());

        authToken = leasingApplicationService
                .getAuthToken(applicationID, smsCode);

        leasingApplicationService
                .postLeasingAppClientDataWithoutPassportAndSelfie(applicationID, authToken)
        ;
    }
}

