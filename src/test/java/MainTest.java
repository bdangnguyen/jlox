import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MainTest {
    
    @Test
    public void sampleTest() {
        final int two = 1 + 1;
        assertThat(two).isEqualTo(2);
    }
    
}
