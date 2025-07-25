# 📝 Project Change Log

**Date**: 21-July-2025

---

## 1. Added Font JAR Files for iReport

- Fonts added:
   - `Arial`
   - `Arial Narrow`
- Location:
   - `resources/fonts`
- 2 jar file added in pom.xml

---

## 2. Organization ID Auto-Assignment

- Implemented using:
   - `OrganizationContextFilter`
   - `SessionContext`
- Modified `BaseEntity`:

```java
@PrePersist
public void prePersist() {
    this.organizationId = SessionContext.getOrganizationId();
}
```

---

## 3. Auto-Audit Fields

- Auto-managed fields:
  - `createdBy`
  - `createdOn`
  - `updatedBy`
  - `updatedOn`
- Configured using:
  - `AuditorAwareImpl`
  - `JpaConfig`
