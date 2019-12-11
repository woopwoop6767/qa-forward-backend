package rf.subscribe.logic;

import org.apache.commons.lang3.RandomStringUtils;

public interface TextGenerator {

    default String generateEmail() {
        return RandomStringUtils.randomAlphabetic(10).concat("@test.test");
    }

    default String generateMobilePhone() {
        return "9".concat(RandomStringUtils.randomNumeric(9));
    }
}
