package com.gustavo.sakila.entity;

import java.time.LocalDateTime;

public abstract class BaseEntity<ID> {
    public abstract ID getId();
    public abstract void setId(ID id);
    protected LocalDateTime lastUpdate;
    public LocalDateTime getLastUpdate(){ return lastUpdate; }
    public void setLastUpdate(LocalDateTime lu){ this.lastUpdate = lu; }
}
