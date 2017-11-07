package paco.configurations;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypedPropertiesTest {

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

    @Test
    public void system_properties_overrides_paco_properties() {
        System.setProperty("paco.foo", "override");
        assertThat(TypedProperties.getStringValue("paco.foo")).isEqualTo("override");
        System.setProperty("paco.foo", "42");
        assertThat(TypedProperties.getIntValue("paco.foo")).isEqualTo(42);
        System.setProperty("paco.foo", "false");
        assertThat(TypedProperties.getBooleanValue("paco.foo")).isEqualTo(false);
    }


    @Test
    public void paco_properties_overrides_default_properties() {
        assertThat(TypedProperties.getStringValue("paco.ascii")).isEqualTo("false");
        assertThat(TypedProperties.getBooleanValue("paco.ascii")).isEqualTo(false);
    }

}
