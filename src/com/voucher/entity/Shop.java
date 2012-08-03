/**
 * 
 */
package com.voucher.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.voucher.entity.sys.SysUser;

/**
 *
 */
@Entity
@Table(name="T_SHOP")
public class Shop {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="SHOP_NAME")
	private String shopName;
	@Column(name="SHOP_ADDRESS")
	private String shopAddress;
	private String image;
	private String description;
	@ManyToOne
	@JoinColumn(name="MERCHANT_ID")
	private SysUser merchant;
	@Column(name="TEL_NO")
	private String telNo;
	@OneToOne
	@JoinColumn(name="SHOP_TYPE_ID")
	private ShopType shopType;
	@Column(name="CREATE_DATE")
	private Date createDate;
	@OneToOne(mappedBy="shop")
	private Position position;
	@OneToMany(mappedBy="shop")
	private Set<Voucher> vouchers = new HashSet<Voucher>();
	@OneToOne
	@JoinColumn(name="CITY_ID")
	private Region city;
	@OneToOne
	@JoinColumn(name="AREA_ID")
	private Region area;
	
	public Shop(){
		
	}
	/**
	 * @param shopName
	 * @param shopAddress
	 * @param image
	 * @param description
	 * @param merchant
	 * @param shopType
	 * @param createDate
	 */
	public Shop(String shopName, String shopAddress, String telNo,
			String description, ShopType shopType) {
		this.shopName = shopName;
		this.shopAddress = shopAddress;
		this.telNo = telNo;
		this.description = description;
		this.shopType = shopType;
		this.position = null;
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
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}
	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * @return the shopAddress
	 */
	public String getShopAddress() {
		return shopAddress;
	}
	/**
	 * @param shopAddress the shopAddress to set
	 */
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
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
	/**
	 * @return the merchant
	 */
	public SysUser getMerchant() {
		return merchant;
	}
	/**
	 * @param merchant the merchant to set
	 */
	public void setMerchant(SysUser merchant) {
		this.merchant = merchant;
	}
	/**
	 * @return the shopType
	 */
	public ShopType getShopType() {
		return shopType;
	}
	/**
	 * @param shopType the shopType to set
	 */
	public void setShopType(ShopType shopType) {
		this.shopType = shopType;
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
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result
				+ ((merchant == null) ? 0 : merchant.hashCode());
		result = prime * result
				+ ((shopAddress == null) ? 0 : shopAddress.hashCode());
		result = prime * result
				+ ((shopName == null) ? 0 : shopName.hashCode());
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
		Shop other = (Shop) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (merchant == null) {
			if (other.merchant != null)
				return false;
		} else if (!merchant.equals(other.merchant))
			return false;
		if (shopAddress == null) {
			if (other.shopAddress != null)
				return false;
		} else if (!shopAddress.equals(other.shopAddress))
			return false;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		return true;
	}
	/**
	 * @return the vouchers
	 */
	public Set<Voucher> getVouchers() {
		return vouchers;
	}
	/**
	 * @param vouchers the vouchers to set
	 */
	public void setVouchers(Set<Voucher> vouchers) {
		this.vouchers = vouchers;
	}
	/**
	 * @return the city
	 */
	public Region getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(Region city) {
		this.city = city;
	}
	/**
	 * @return the area
	 */
	public Region getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(Region area) {
		this.area = area;
	}
	/**
	 * @return the telNo
	 */
	public String getTelNo() {
		return telNo;
	}
	/**
	 * @param telNo the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
}
