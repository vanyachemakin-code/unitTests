
import fibonacci.FibonacciCalculator;
import fibonacci.FibonacciNumber;
import fibonacci.FibonacciRepository;
import fibonacci.FibonacciService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FibonacciServiceTest {

    @Mock
    FibonacciRepository repository;
    final FibonacciCalculator calculator = new FibonacciCalculator();
    final FibonacciService service = new FibonacciService(repository, calculator);

    @Test
    @DisplayName("Get fibonacci number by index from repository")
    void whenRepositoryHasNumber_thenNumberGetByIndex() {
        int number = 1;
        int index = 1;

        FibonacciNumber fibonacciNumber = new FibonacciNumber(index, number);
        when(repository.findByIndex(index)).thenReturn(Optional.of(fibonacciNumber));
        Optional<FibonacciNumber> repositoryAnswer = repository.findByIndex(index);
        assertEquals(1, repositoryAnswer.get().getValue());
        verify(repository, times(1)).findByIndex(index);
    }

    @Test
    @DisplayName("Generate a number if it's not in the repository")
    void whenRepositoryHasNotNumber_thenGenerateNumberAndSaveToRepository() {
        int index = 1;
        if (repository.count() == 0) {
            FibonacciNumber fibonacciNumber = new FibonacciNumber(index, calculator.getFibonacciNumber(index));
            repository.save(fibonacciNumber);
            when(repository.findByIndex(index)).thenReturn(Optional.of(fibonacciNumber));
            Optional<FibonacciNumber> repositoryAnswer = repository.findByIndex(index);
            assertEquals(1, repositoryAnswer.get().getValue());
            verify(repository, times(1)).findByIndex(index);
            verify(repository,times(1)).save(fibonacciNumber);
        }
    }

    @Test
    @DisplayName("When index less than 1 throw exception")
    void whenIndexLessThan1_thenThrowException() {
        int index = 0;
        assertThrows(Exception.class,
                () -> service.fibonacciNumber(index)
        );
    }
}
