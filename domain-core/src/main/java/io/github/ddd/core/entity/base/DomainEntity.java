package io.github.ddd.core.entity.base;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class DomainEntity<ID> {
    protected ID id;
    protected long version;
    
    // Audit fields
    protected LocalDateTime createdAt;
    protected String createdBy;
    protected LocalDateTime lastModifiedAt;
    protected String lastModifiedBy;

    protected DomainEntity() {
        this.createdAt = LocalDateTime.now();
    }

    protected DomainEntity(ID id, long version) {
        this.id = id;
        this.version = version;
        this.createdAt = LocalDateTime.now();
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEntity<?> that = (DomainEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
