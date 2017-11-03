package paco.configurations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TypedPropertiesTest {

    @Test
    public void can_load_existing_property_files() {
        new TypedProperties();
    }

    @Test
    public void should_return_property_value_of_type_int() throws Exception {
        assertThat(TypedProperties.getIntValue("paco.timeout")).isInstanceOf(Integer.class);
    }

    @Test
    public void should_return_property_value_of_type_String() throws Exception {
        assertThat(TypedProperties.getStringValue("paco.referrer")).isInstanceOf(String.class);
    }

    @Test
    public void should_return_property_value_of_type_boolean() throws Exception {
        assertThat(TypedProperties.getBooleanValue("paco.cacheDuplicates")).isInstanceOf(Boolean.class);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_runtime_exception() throws Exception {
        TypedProperties.getBooleanValue("paco.referrer");
    }
}
