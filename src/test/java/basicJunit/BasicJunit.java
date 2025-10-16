package basicJunit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class BasicJunit {

    // Inicialize / setup

    @BeforeEach
    public void init() {
        System.out.println("This method is executed before each test");
    }

    @AfterEach
    public void cleanup() {
        System.out.println("This method is executed After each test");
    }

    @Test
    @Order(1)
    public void verifySometing() {
        System.out.println("This is a test");
    }

    @Test
    @Order(2)
    public void verifySometing2() {
        System.out.println("This is a test 2");
    }
}
