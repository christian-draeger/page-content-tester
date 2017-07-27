package pagecontenttester.configurations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TypedPropertiesTest {

    private TypedProperties pacoProperties = new TypedProperties("/pagecontent.properties");

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_for_unknown_property_file() {
        new TypedProperties("/wrongName.properties");
    }

    @Test
    public void can_load_existing_property_files() {
        new TypedProperties("/pagecontent.properties");
    }

    @Test(expected = ConfigurationException.class)
    public void throws_exception_if_trying_to_load_non_existing_property() {
        pacoProperties.getBooleanValue("non.valid.property");
    }

    @Test
    public void should_return_false_if_property_not_found() throws Exception {
        assertThat(pacoProperties.hasProperty("non.valid.property")).isFalse();
    }

    @Test
    public void should_return_true_if_property_found() throws Exception {
        assertThat(pacoProperties.hasProperty("timeout")).isTrue();
    }

    @Test
    public void should_return_property_value_of_type_int() throws Exception {
        assertThat(pacoProperties.getIntValue("timeout")).isInstanceOf(Integer.class);
    }

    @Test
    public void should_return_property_value_of_type_String() throws Exception {
        assertThat(pacoProperties.getStringValue("referrer")).isInstanceOf(String.class);
    }

    @Test
    public void should_return_property_value_of_type_boolean() throws Exception {
        assertThat(pacoProperties.getBooleanValue("cache.duplicates")).isInstanceOf(Boolean.class);
    }

    @Test(expected = NumberFormatException.class)
    public void should_throw_number_format_exception() throws Exception {
        pacoProperties.getIntValue("cache.duplicates");
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_runtime_exception() throws Exception {
        pacoProperties.getBooleanValue("referrer");
    }
}
