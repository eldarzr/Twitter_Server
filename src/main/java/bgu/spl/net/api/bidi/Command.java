package bgu.spl.net.api.bidi;

import java.io.Serializable;

public interface Command<T> extends Serializable {

    boolean execute(T args, int connectionId);
}
