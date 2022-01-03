package bgu.spl.net.api.bidi;

import java.io.Serializable;

public interface Command<T> extends Serializable {

    void execute(T args, int connectionId);
}
