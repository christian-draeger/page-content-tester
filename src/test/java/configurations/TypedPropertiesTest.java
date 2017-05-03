package configurations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;

public class TypedPropertiesTest {

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_for_unknown_property_file() {
        new TypedProperties("/wrongName.properties");
    }

    @Test
    public void can_load_existing_property_files() {
        new TypedProperties("/default.properties");
        new TypedProperties("/pagecontent.properties");
    }

    @Test
    public void should_return_null_if_property_not_found() throws Exception {
        TypedProperties properties = new TypedProperties("/default.properties");
        assertThat(properties.hasProperty("non.valid.property"), is(false));
    }
}