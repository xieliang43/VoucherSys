/**
 * 
 */
package com.voucher.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_VOUCHER")
public class Voucher {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double price;
	private int quantity;
	@Column(name="START_DATE")
	private Date startDate;
	@Column(name="END_DATE")
	private Date endDate;
	@Column(name="USE_RULE")
	private String useRule;
	private short enabled;
	private String image;
	@Column(name="VCH_KEY")
	private String vchKey;
	private String description;
	@Column(name="CREATE_DATE")
	private Date createDate;
	@ManyToOne
	@JoinColumn(name="SHOP_ID")
	private Shop shop;
	@OneToMany(mappedBy="voucher", cascade=CascadeType.ALL)
	private Set<VoucherInstance> instances = new HashSet<VoucherInstance>();
	
	public Voucher() {
		
	}
	/**
	 * @param name
	 * @param price
	 * @param quantity
	 * @param startDate
	 * @param endDate
	 * @param deadTime
	 * @param enabled
	 * @param image
	 * @param description
	 * @param createDate
	 */
	public Voucher(String name, double price, int quantity, Date startDate,
			Date endDate, String useRule, String vchKey, short enabled,
			String description) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.useRule = useRule;
		this.vchKey = vchKey;
		this.enabled = enabled;
		this.description = description;
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
	 * @return the domination
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param domination the domination to set
	 */
	public void setPrice(double price) {
		this.price = price;
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
	 * @return the useRule
	 */
	public String getUseRule() {
		return useRule;
	}
	/**
	 * @param useRule the useRule to set
	 */
	public void setUseRule(String useRule) {
		this.useRule = useRule;
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
	/**
	 * @return the shop
	 */
	public Shop getShop() {
		return shop;
	}
	/**
	 * @param shop the shop to set
	 */
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	/**
	 * @return the vchKey
	 */
	public String getVchKey() {
		return vchKey;
	}
	/**
	 * @param vchKey the vchKey to set
	 */
	public void setVchKey(String vchKey) {
		this.vchKey = vchKey;
	}
	
}
