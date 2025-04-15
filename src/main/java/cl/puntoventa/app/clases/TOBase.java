package cl.puntoventa.app.clases;


import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TOBase implements Serializable {

    protected static final long serialVersionUID = 1L;
    protected String excludedPropertyNames = "class,excludedPropertyNames";

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public int hashCode() {
	return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
	return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public Object clone() {
	return SerializationUtils.clone(this);
    }

    /**
     * @return the excludedPropertyNames
     */
    protected String getExcludedPropertyNames() {
	return excludedPropertyNames;
    }

    /**
     * @param excludedPropertyNames
     *            the excludedPropertyNames to set
     */
    protected void setExcludedPropertyNames(String excludedPropertyNames) {
	this.excludedPropertyNames = excludedPropertyNames;
    }
}

