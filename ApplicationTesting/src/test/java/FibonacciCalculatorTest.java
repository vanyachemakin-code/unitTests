
import fibonacci.FibonacciCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FibonacciCalculatorTest {

    final FibonacciCalculator calculator = new FibonacciCalculator();

    @Test
    @DisplayName("Get fibonacci number by index")
    void whenIndex10_thenGetFibonacciNumber() {
        int index = 1;
        assertEquals(1, calculator.getFibonacciNumber(index));
    }

    @Test
    @DisplayName("When index less than 1 throw exception")
    void whenIndexLessThan1_thenThrowException() {
        int index = 0;
        assertThrows(Exception.class,
                () -> calculator.getFibonacciNumber(index)
        );
    }

    @Test
    @DisplayName("Get fibonacci number than index 1 or 2")
    void whenIndex1or2_thenFibonacciNumber1() {
        assertAll("",
                () -> assertEquals(1, calculator.getFibonacciNumber(1)),
                () -> assertEquals(1, calculator.getFibonacciNumber(2))
        );
    }
}
