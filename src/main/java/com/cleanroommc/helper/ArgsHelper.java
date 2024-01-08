package com.cleanroommc.helper;

import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

public class ArgsHelper extends Args {
    private Object[] args;
    /**
     * Ctor.
     *
     * @param values argument values
     */
    protected ArgsHelper(Object[] values) {
        super(values);
        args = values;
    }
    
    @Override
    public <T> void set(int index, T value) {
        args[index] = value;
    }

    @Override
    public void setAll(Object... values) {
        args = values;
    }

    @Override
    public int size() {
        return this.args.length;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        return (T)this.args[index];
    }
    
    public static ArgsHelper of(float a, float b, float c, float d) {
        return new ArgsHelper(new Object[]{a,b,c,d});
    }
}
