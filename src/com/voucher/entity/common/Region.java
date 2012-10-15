/**
 * 
 */
package com.voucher.entity.common;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name="T_REGION")
public class Region {
	/**
	 * 
	 */
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int type;
	private int agencyId;
	@Column(name="REGION_PREFIX")
	private String regionPrefix;
	private short enabled;
	@ManyToOne
	@JoinColumn(name="PARENTID")
	private Region parent;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="parent", fetch=FetchType.LAZY)
	private Set<Region> children = new HashSet<Region>();
	@Column(name="CREATE_DATE")
	private Date createDate;
	/**
	 * 
	 */
	public Region() {
		
	}
	/**
	 * @param name
	 * @param type
	 * @param regionPrefix
	 * @param enabled
	 * @param parent
	 */
	public Region(String name, int type, String regionPrefix, short enabled,
			Region parent) {
		this.name = name;
		this.type = type;
		this.regionPrefix = regionPrefix;
		this.enabled = enabled;
		this.parent = parent;
		this.agencyId = 0;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the agencyId
	 */
	public int getAgencyId() {
		return agencyId;
	}
	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * @return the parent
	 */
	public Region getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Region parent) {
		this.parent = parent;
	}
	/**
	 * @return the children
	 */
	public Set<Region> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<Region> children) {
		this.children = children;
	}
	
	/**
	 * @return the regionPrefix
	 */
	public String getRegionPrefix() {
		return regionPrefix;
	}
	/**
	 * @param regionPrefix the regionPrefix to set
	 */
	public void setRegionPrefix(String regionPrefix) {
		this.regionPrefix = regionPrefix;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + agencyId;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((regionPrefix == null) ? 0 : regionPrefix.hashCode());
		result = prime * result + type;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Region other = (Region) obj;
		if (agencyId != other.agencyId)
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (regionPrefix == null) {
			if (other.regionPrefix != null)
				return false;
		} else if (!regionPrefix.equals(other.regionPrefix))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	/**
	 * @return the enabled
	 */
	public short getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}
}
