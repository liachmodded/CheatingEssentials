package com.luna.lib.interfaces.updaters;

/**
 * Like {@link ITickable}, but this has a <code>delta</code> passed in
 *
 * @author godshawk
 */
public interface IDTickable {
    void tick(int delta);
}
