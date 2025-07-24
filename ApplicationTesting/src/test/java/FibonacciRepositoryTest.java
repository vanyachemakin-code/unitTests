
import fibonacci.FibonacciNumber;
import fibonacci.FibonacciRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FibonacciRepositoryTest extends PostgresTestContainerInitializer {

    @Autowired
    FibonacciRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void clearDB() {
        repository.deleteAll();
        entityManager.clear();
        entityManager.close();
    }

    private List<FibonacciNumber> getNumberFromDB(int index, int number) {
        FibonacciNumber fibonacciNumber = new FibonacciNumber(index, number);
        repository.save(fibonacciNumber);
        entityManager.flush();
        entityManager.detach(fibonacciNumber);

        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number",
                (resultSet, rowNum) -> new FibonacciNumber(
                        resultSet.getInt("index"), resultSet.getInt("value")
                )
        );
        return actual;
    }

    @Test
    @DisplayName("New fibonacci number save to DB")
    void whenCreateFibonacciNumber_thenSaveToRepository() {
        int number = 1;
        int index = 1;
        assertEquals(1, getNumberFromDB(index, number).size());
    }

    @Test
    @DisplayName("Get by index from DB")
    void whenFindFibonacciNumberById_thenGetFromDB() {
        int number = 1;
        int index = 1;
        FibonacciNumber fibonacciNumber = getNumberFromDB(index, number).get(0);
        assertEquals(1, fibonacciNumber.getValue());
    }

    @Test
    @DisplayName("Add same number are not exceptions or duplicates")
    void whenAddSameNumber_thenThereAreNotExceptionOrDuplicate() {
        int number = 1;
        int index = 1;
        getNumberFromDB(index, number); //add fibonacci number
        getNumberFromDB(index, number); //add same number
        assertEquals(2, getNumberFromDB(++index, ++number).size()); //check List.size then add different number
    }
}
