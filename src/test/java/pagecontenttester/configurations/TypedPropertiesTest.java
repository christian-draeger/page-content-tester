package pagecontenttester.configurations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import pagecontenttester.configurations.TypedProperties;

public class TypedPropertiesTest {

    private TypedProperties defaultProperties = new TypedProperties("/default.properties");

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
    public void should_return_false_if_property_not_found() throws Exception {
        assertThat(defaultProperties.hasProperty("non.valid.property"), is(false));
    }

    @Test
    public void should_return_true_if_property_found() throws Exception {
        assertThat(defaultProperties.hasProperty("timeout"), is(true));
    }

    @Test
    public void should_return_property_value_of_type_int() throws Exception {
        assertThat(defaultProperties.getIntValue("timeout"), instanceOf(int.class));
    }

    @Test
    public void should_return_property_value_of_type_String() throws Exception {
        assertThat(defaultProperties.getStringValue("referrer"), instanceOf(String.class));
    }

    @Test
    public void should_return_property_value_of_type_boolean() throws Exception {
        assertThat(defaultProperties.getBooleanValue("cache.duplicates"), instanceOf(boolean.class));
    }

    @Test(expected = NumberFormatException.class)
    public void should_throw_number_format_exception() throws Exception {
        defaultProperties.getIntValue("cache.duplicates");
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_number_format_exception2() throws Exception {
        defaultProperties.getBooleanValue("referrer");
    }
}