package com.phope.realcalloutbackend.Shared.config.tenant;

import java.util.UUID;

public class TenantContext {
    private static final ThreadLocal<UUID> currentTenant = new ThreadLocal<>();

    private TenantContext() {}

    public static void setTenant(UUID orgId) {
        currentTenant.set(orgId);
    }

    public static UUID getTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove(); // Always call this in finally block
    }

    public static boolean hasTenant() {
        return currentTenant.get() != null;
    }
}
