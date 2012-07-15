/**
 * 
 */
package com.voucher.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_VOUCHER")
public class Voucher {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double domination;
	private int quantity;
	@Column(name="START_DATE")
	private Date startDate;
	@Column(name="END_DATE")
	private Date endDate;
	private String deadTime;
	private short enabled;
	private String image;
	private String description;
	@Column(name="CREATE_DATE")
	private Date createDate;
	@OneToMany(mappedBy="voucher")
	private Set<VoucherInstance> instances = new HashSet<VoucherInstance>();
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
	 * @return the domination
	 */
	public double getDomination() {
		return domination;
	}
	/**
	 * @param domination the domination to set
	 */
	public void setDomination(double domination) {
		this.domination = domination;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the deadTime
	 */
	public String getDeadTime() {
		return deadTime;
	}
	/**
	 * @param deadTime the deadTime to set
	 */
	public void setDeadTime(String deadTime) {
		this.deadTime = deadTime;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	 * @return the instances
	 */
	public Set<VoucherInstance> getInstances() {
		return instances;
	}
	/**
	 * @param instances the instances to set
	 */
	public void setInstances(Set<VoucherInstance> instances) {
		this.instances = instances;
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
