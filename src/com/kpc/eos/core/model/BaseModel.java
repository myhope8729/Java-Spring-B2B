package com.kpc.eos.core.model;

import java.io.Serializable;

import lombok.Data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Data
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 8167258303349145136L;
    
    private String reloadPage;
    private String userId;
    private String lang;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
