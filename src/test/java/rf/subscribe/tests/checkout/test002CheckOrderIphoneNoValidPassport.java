package rf.subscribe.tests.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.services.*;

public class test002CheckOrderIphoneNoValidPassport implements Specification {

    private String categoryCode;
    private String modelCode;
    private String goodCode;
    private String basketId;
    private String typeApplication;
    private String applicationID;
    private String authToken;

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

    @Test
    void test() {
        categoryCode = categoriesService
                .getCategories()
                .getResultData()
                .getCategories().get(0)
                .getChilds().get(0)
                .getCode()
        ;

        modelCode = modelsService
                .getModels(categoryCode)
                .getResultData()
                .getModels().get(0)
                .getCode()
        ;

        goodCode = modelsService
                .getGoodCode(modelCode)
                .getResultData()
                .getGoods().get(3)
                .getCode()
        ;

        basketId = basketOnlineService
                .postBasketOnlineWithGoodCode(goodCode)
                .getResultData()
                .getBasketId()
        ;

        typeApplication = basketOnlineService
                .getBasketOnlineWithBasketId(basketId)
                .getResultData()
                .getBasketInfo()
                .getStatus()
        ;

        applicationID = leasingApplicationService
                .postLeasingApplication(basketId, typeApplication)
                .getResultData()
                .getApplicationID()
        ;

        authToken = leasingApplicationService
                .postLeasingAppCheckConsentsOtp(applicationID)
                .getResultData()
                .getToken()
        ;

        uploadPhotoService
                .postUploadPassportNoValid(applicationID, authToken)
        ;

    }
}

