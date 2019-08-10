package com.gk.screenshot.dal;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the screen database table.
 * 
 */
@Entity
@NamedQuery(name="Screen.findAll", query="SELECT s FROM Screen s")
public class Screen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date created;

	@Column(name="customer_id")
	private String customerId;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="request_url")
	private String requestUrl;

	@Column(name="request_url_domain")
	private String requestUrlDomain;

	private byte[] screenshot;

	@Column(name = "status" , columnDefinition = "ENUM")
	private String status;

	public Screen() {
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequestUrl() {
		return this.requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestUrlDomain() {
		return this.requestUrlDomain;
	}

	public void setRequestUrlDomain(String requestUrlDomain) {
		this.requestUrlDomain = requestUrlDomain;
	}

	public byte[] getScreenshot() {
		return this.screenshot;
	}

	public void setScreenshot(byte[] screenshot) {
		this.screenshot = screenshot;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}