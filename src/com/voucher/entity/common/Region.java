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
