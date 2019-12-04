package rf.subscribe.logic;

import java.util.HashMap;

public class Data {
    protected String pathAppLeasing = "application/v2/leasing-application/";
    protected String pathLeasingBasket = "leasing-basket/v1/basket-online/";
    protected String pathJsonSchema = "src/main/resources/jsonSchema/";
    protected HashMap clientData = new HashMap() {{
        put("clientInfo", new HashMap() {{
            put("birthDate", "1972-07-23");
            put("birthPlace", "С. ФРОНТОВКА ОРАТОВСКОГО Р-НА ВИННИЦКОЙ ОБЛ.");
            put("documentType", "PASSPORT");
            put("firstName", "СЕРГЕЙ");
            put("hasMiddleName", true);
            put("issueDate", "2015-12-18");
            put("issuer", "ОТДЕЛ УФМС РОССИИ ПО РЕСПУБЛИКЕ КРЫМ В КИЕВСКОМ РАЙОНЕ Г. СИМФЕРОПОЛЯ");
            put("issuerCode", "910-003");
            put("lastName", "ЗДРИЛЮК");
            put("patronymicName", "АНАТОЛЬЕВИЧ");
            put("registrationAddress", new HashMap() {{
                put("blockNum", null);
                put("buildingNum", null);
                put("district", null);
                put("districtCode", "000");
                put("flatNum", "3");
                put("locality", "Авиационный");
                put("localityCode", "000");
                put("localityType", null);
                put("region", "Московская");
                put("regionCode", "50");
                put("street", "Королева");
                put("streetCode", "0784");
                put("streetNum", "10");
                put("taxCode", "142007");
                put("town", "Домодедово");
                put("townCode", "001");
            }});
            put("sex", "MALE");
        }});
        put("monthlySalary", "70000");
        put("occupation", "COMMERCIAL_EMPLOYEE");
    }};

}
