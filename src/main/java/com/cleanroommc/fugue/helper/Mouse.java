package com.cleanroommc.fugue.helper;

public class Mouse {
    public static int getEventDWheel() {
        return org.lwjgl.input.Mouse.getEventDWheel() * 120;
    }

    public static int getDWheel() {
        return org.lwjgl.input.Mouse.getDWheel() * 120;
    }

    public static void create() {}
    public static void destroy() {}
    public static void setCursorPosition(int x, int y) {}
}
