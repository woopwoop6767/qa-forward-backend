package rf.subscribe.tests.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.services.*;

public class test001CheckOrderIphoneValid implements Specification {

    private static final int offset = 0;

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
                .getCategories().get(offset)
                .getChilds().get(offset)
                .getCode()
        ;

        modelCode = modelsService
                .getModels(categoryCode)
                .getResultData()
                .getModels().get(offset)
                .getCode()
        ;

        goodCode = modelsService
                .getGoodCode(modelCode)
                .getResultData()
                .getGoods().get(offset)
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
                .postUploadPassport(applicationID, authToken)
        ;

        uploadPhotoService
                .postUploadSelfie(applicationID, authToken)
        ;

        leasingApplicationService
                .postLeasingAppClientData(applicationID, authToken)
        ;
    }
}

