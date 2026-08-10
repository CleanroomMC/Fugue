package {{ package }};

/**
 * Tags storage class, you can change at will
 */
public class Reference {
    private Reference() {}

    public static final String MOD_ID = "{{ mod_id }}";
    public static final String MOD_NAME = "{{ mod_name }}";
    public static final String VERSION = "{{ mod_version }}";

    // Dependencies
    public static final String MOD_DEPENDENCIES =
            "required-after:forge@[14.23.5.2864,);required-after:cleanroom@[{{ crl_version }},);";
}
