
import fibonacci.FibonacciNumber;
import fibonacci.FibonacciService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class FibonacciControllerTest extends PostgresTestContainerInitializer {

    @Autowired
    MockMvc mockMvc;

    @Mock
    FibonacciService service;

    @Test
    @DisplayName("Get fibonacci number and HTTP answer then index more than 1")
    void whenIndexMoreThan1_thenGetFibonacciNumberAndHTTPAnswer() throws Exception {
        int index = 2;
        int number = 1;
        when(service.fibonacciNumber(index)).thenReturn(new FibonacciNumber(index,number));
        mockMvc.perform(get("/fibonacci/" + index)).andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(number));
    }

    @Test
    @DisplayName("Get HTTP answer and throw exception then index less than 1")
    void whenIndexLessThan1_thenThrowExceptionAndHTTPAnswer() throws Exception {
        int index = 0;
        when(service.fibonacciNumber(index))
                .thenThrow(new IllegalArgumentException("Index should be greater or equal to 1"));
        mockMvc.perform(get("/fibonacci/" + index))
                .andExpect(status().isBadRequest());
    }
}
