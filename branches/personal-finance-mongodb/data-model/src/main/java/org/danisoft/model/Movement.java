package org.danisoft.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Movement implements Serializable {

	/**
	 * Generated Serial Version. 
	 */
	private static final long serialVersionUID = -7003850171775135550L;
	
	/**
	 * Date.
	 */
	private Date date;
	
	/**
	 * Value Date.
	 */
	private Date valueDate;
	
	/**
	 * Description of the movement;
	 */
	private String description;
	
	/**
	 * Amount.
	 */
	private double amount;
	
	/**
	 * Contract related to the movement.
	 */
	@DBRef
	private Contract contract;

	/**
	 * Primary Constructor.
	 * 
	 * @param id
	 * @param date
	 * @param valueDate
	 * @param description
	 * @param amount
	 * @param account
	 * @param contract
	 */
	public Movement(Date date, Date valueDate, String description,
			double amount) {
		super();
		this.date = date;
		this.valueDate = valueDate;
		this.description = description;
		this.amount = amount;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the valueDate
	 */
	public Date getValueDate() {
		return valueDate;
	}

	/**
	 * @param valueDate the valueDate to set
	 */
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
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
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the contract
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract the contract to set
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	
}
