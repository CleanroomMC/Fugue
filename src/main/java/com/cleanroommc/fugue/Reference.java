package com.cleanroommc.fugue;

/**
 * Tags storage class, you can change at will
 */
public class Reference {
    private Reference() {}

    public static final String MOD_ID = "@{MOD_ID}";
    public static final String MOD_NAME = "@{MOD_NAME}";
    public static final String VERSION = "@{MOD_VERSION}";

    // Dependencies
    public static final String MOD_DEPENDENCIES =
            "required-after:forge@[14.23.5.2864,);required-after:cleanroom@[@{CLEANROOM_VERSION},);";
}
