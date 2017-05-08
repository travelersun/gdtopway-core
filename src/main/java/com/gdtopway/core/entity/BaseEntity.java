/**
 * Copyright (c) 2012
 */
package com.gdtopway.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.gdtopway.core.annotation.MetaData;
import com.gdtopway.core.audit.DefaultAuditable;
import com.gdtopway.core.audit.SaveUpdateAuditListener;
import com.gdtopway.core.web.json.JsonViews;

@Getter
@Setter
@Access(AccessType.FIELD)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "javassistLazyInitializer", "revisionEntity", "handler" }, ignoreUnknown = true)
@MappedSuperclass
@EntityListeners({ SaveUpdateAuditListener.class })
@AuditOverrides({ @AuditOverride(forClass = BaseEntity.class) })
public abstract class BaseEntity<ID extends Serializable> extends PersistableEntity<ID> implements DefaultAuditable<String, ID> {

    private static final long serialVersionUID = 2476761516236455260L;

    @MetaData(value = "乐观锁版本")
    @Version
    @Column(name = "optlock", nullable = false)
    @JsonProperty
    @JsonView(JsonViews.Admin.class)
    private Integer version = 0;

    @Column(length = 100)
    @JsonProperty
    @JsonView(JsonViews.Admin.class)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    protected Date createdDate;

    @Column(length = 100)
    @JsonProperty
    @JsonView(JsonViews.Admin.class)
    private String lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    @JsonView(JsonViews.Admin.class)
    private Date lastModifiedDate;

    @MetaData(value = "数据分组记录", comments = "标识数据来源，比如不同工厂，结构代码，APP名称等")
    @Column(length = 100)
    @JsonView(JsonViews.Admin.class)
    private String dataGroup;

    private static final String[] PROPERTY_LIST = new String[] { "id", "version" };

    public String[] retriveCommonProperties() {
        return PROPERTY_LIST;
    }

    @Override
    @Transient
    @JsonProperty
    @JsonView(JsonViews.Admin.class)
    public String getDisplay() {
        return "[" + getId() + "]" + this.getClass().getSimpleName();
    }
}
