package rf.subscribe.tests.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rf.subscribe.logic.TextGenerator;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.services.*;

public class test001CheckOrderIphone implements Specification, TextGenerator {

    private String categoryCode;
    private String modelCode;
    private String goodCode;
    private String basketId;
    private String typeApplication;
    private String applicationID;
    private String authToken;
    private String smsCode = "1111";
    private String countModel = "1";

    private CategoriesService categoriesService;
    private ModelsService modelsService;
    private BasketOnlineService basketOnlineService;
    private LeasingApplicationService leasingApplicationService;
    private UploadPhotoService uploadPhotoService;

    @BeforeMethod
    public void setUp() {
        categoriesService = new CategoriesService();
        modelsService = new ModelsService();
        basketOnlineService = new BasketOnlineService();
        leasingApplicationService = new LeasingApplicationService();
        uploadPhotoService = new UploadPhotoService();
    }

    @Test(description = "Correct checkout order of the iPhone application")
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

        uploadPhotoService
                .postUploadPassport(applicationID, authToken);

        uploadPhotoService
                .postUploadSelfie(applicationID, authToken);

        leasingApplicationService
                .postLeasingAppClientData(applicationID, authToken);
    }
}

