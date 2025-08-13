package com.rony.erpsoft.configuration.security;

public class SessionContext {

    private static final ThreadLocal<Long> ORGANIZATION_ID = new ThreadLocal<>();

    public static void setOrganizationId(Long organizationId) {
        ORGANIZATION_ID.set(organizationId);
    }

    public static Long getOrganizationId() {
        return ORGANIZATION_ID.get();
    }

    public static void clear() {
        ORGANIZATION_ID.remove();
    }
}
