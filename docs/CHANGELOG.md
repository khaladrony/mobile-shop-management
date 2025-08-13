# 📝 Project Change Log

**Date**: 21-July-2025

---

## 1. Added Font JAR Files for iReport

- Fonts added:
   - `Arial`
   - `Arial Narrow`
- Location:
   - `resources/fonts`
- Two jar file added in pom.xml

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

**Date**: 30-July-2025

---
## 1. Inventory: added three report

- Inventory Material Receive/Issue report
- Item Ledger
- Item Ledger(Summary)

## 2. Added warehouse dropdown in Receive/Issue UI

## 3. Accounts: two financial report added
- Balance sheet
- Income statement

## 4. In chart of accounts add new AccountsType `Equity`

## 5. Activate Accounts sub-type. Revenue & Balance Sheet
- Revenue => Income & Expenditure 
- Balance Sheet => Assets, Liability & Equity


---