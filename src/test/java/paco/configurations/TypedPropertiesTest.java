package paco.configurations;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypedPropertiesTest {

    @Test
    public void should_return_property_value_of_type_int() throws Exception {
        assertThat(TypedProperties.getIntValue("timeout")).isInstanceOf(Integer.class);
    }

    @Test
    public void should_return_property_value_of_type_String() throws Exception {
        assertThat(TypedProperties.getStringValue("referrer")).isInstanceOf(String.class);
    }

    @Test
    public void should_return_property_value_of_type_boolean() throws Exception {
        assertThat(TypedProperties.getBooleanValue("cacheDuplicates")).isInstanceOf(Boolean.class);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_runtime_exception() throws Exception {
        TypedProperties.getBooleanValue("referrer");
    }

    @Test
    public void system_properties_overrides_paco_properties() {
        System.setProperty("foo", "override");
        assertThat(TypedProperties.getStringValue("foo")).isEqualTo("override");
        System.setProperty("foo", "42");
        assertThat(TypedProperties.getIntValue("foo")).isEqualTo(42);
        System.setProperty("foo", "false");
        assertThat(TypedProperties.getBooleanValue("foo")).isEqualTo(false);
    }


    @Test
    public void paco_properties_overrides_default_properties() {
        assertThat(TypedProperties.getStringValue("bar")).isEqualTo("false");
        assertThat(TypedProperties.getBooleanValue("bar")).isEqualTo(false);
        assertThat(TypedProperties.getIntValue("foobar")).isEqualTo(4711);
    }

}
