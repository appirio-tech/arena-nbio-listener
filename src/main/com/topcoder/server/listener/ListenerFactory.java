package com.topcoder.server.listener;

import java.util.Collection;

import com.topcoder.shared.netCommon.CSHandlerFactory;
import com.topcoder.server.listener.monitor.EmptyMonitor;
import com.topcoder.server.listener.monitor.MonitorInterface;

public abstract class ListenerFactory {

    public final ListenerInterface createListener(int port, ProcessorInterface processor, CSHandlerFactory csHandlerFactory) {
        return createListener(port, processor, new EmptyMonitor(), csHandlerFactory);
    }

    public final ListenerInterface createListener(int port, ProcessorInterface processor, MonitorInterface monitor, CSHandlerFactory csHandlerFactory) {
        return createListener(port, processor, monitor, 0, 0, 0, csHandlerFactory);
    }

    public final ListenerInterface createListener(int port, ProcessorInterface processor, MonitorInterface monitor,
            int numAcceptThreads, int numReadThreads, int numWriteThreads, CSHandlerFactory csHandlerFactory) {
        return createListener(port, processor, monitor, numAcceptThreads, numReadThreads, numWriteThreads,
                csHandlerFactory, null, false);
    }

    public final ListenerInterface createListener(int port, ProcessorInterface processor, MonitorInterface monitor,
            int numAcceptThreads, int numReadThreads, int numWriteThreads, CSHandlerFactory csHandlerFactory,
            Collection ips, boolean isAllowedSet) {
        return createListener(port, processor, monitor, numAcceptThreads, numReadThreads, numWriteThreads, csHandlerFactory,
                ips, isAllowedSet, 0, Integer.MAX_VALUE);
    }

    public final ListenerInterface createListener(int port, ProcessorInterface processor, MonitorInterface monitor,
            int numAcceptThreads, int numReadThreads, int numWriteThreads, CSHandlerFactory csHandlerFactory,
            Collection ips, boolean isAllowedSet, int minConnectionId, int maxConnectionId) {
        return createListener(null, port, processor, monitor, numAcceptThreads, numReadThreads,
                numWriteThreads, csHandlerFactory, ips, isAllowedSet, minConnectionId, maxConnectionId);
    }

    public abstract ListenerInterface createListener(String ipAddress, int port, ProcessorInterface processor, MonitorInterface monitor,
            int numAcceptThreads, int numReadThreads, int numWriteThreads, CSHandlerFactory csHandlerFactory,
            Collection ips, boolean isAllowedSet, int minConnectionId, int maxConnectionId);

}
