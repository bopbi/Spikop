package com.arjunalabs.android.spikop.data;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public interface SpikDataSource {

    List<Timeline> getAllSpiks();

    Timeline getSpikById(long id);

}
