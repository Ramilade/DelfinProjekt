package logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionCalculatorTest {

    @ParameterizedTest
    @CsvSource(value={"10:true:1000","20:false:500","30:true:1600","75:true:1200"},delimiter = ':')
    void subscribeCalEquals(int age, boolean active, double expected) {
        //arrange
        SubscriptionCalculator subscriptionCalculator = new SubscriptionCalculator();

        //act
        double actual = subscriptionCalculator.subscribeCal(age,active);

        //assert
        assertEquals(expected,actual);
    }

    @ParameterizedTest
    @CsvSource(value={"10:false:1000","18:true:-1000","-5:false:1000","18:true:1000"},delimiter = ':')
    void subscribeCalNotEquals(int age, boolean active, double expected) {
        //arrange
        SubscriptionCalculator subscriptionCalculator = new SubscriptionCalculator();

        //act
        double actual = subscriptionCalculator.subscribeCal(age,active);

        //assert
        assertNotEquals(expected,actual);
    }
}