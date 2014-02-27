package org.danisoft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Contract implements Serializable {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 404452082824427768L;

	/**
	 * Primary key from the DB.
	 */
	private int id;
	
	/**
	 * Concept of the contract.
	 */
	private String concept;
	
	/**
	 * Date in which the last payment was done.
	 */
	private Date lastPaymentDate;
	
	/**
	 * Period.
	 */
	private Period period;
	
	/**
	 * Is fixed amount
	 */
	private boolean fixedAmount;
	
	/**
	 * Amount.
	 */
	private double amount;
	
	/**
	 * Account to charge contract's movements.
	 */
	private Account account;
	
	/**
	 * 
	 */

	/**
	 * Primary constructor.
	 * 
	 * @param id
	 * @param concept
	 * @param lastPaymentDate
	 * @param periodicty
	 * @param amount
	 * @param account
	 */
	public Contract(int id, String concept, Date lastPaymentDate,
			Period periodicty, boolean fixedAmount, double amount, Account account) {
		super();
		this.id = id;
		this.concept = concept;
		this.lastPaymentDate = lastPaymentDate;
		this.period = periodicty;
		this.fixedAmount = fixedAmount;
		this.amount = amount;
		this.account = account;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "contract_id")
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
	 * @return the concept
	 */
	public String getConcept() {
		return concept;
	}

	/**
	 * @param concept the concept to set
	 */
	public void setConcept(String concept) {
		this.concept = concept;
	}

	/**
	 * @return the lastPaymentDate
	 */
	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	/**
	 * @param lastPaymentDate the lastPaymentDate to set
	 */
	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period.getCode();
	}

	/**
	 * @param periodicty the period to set
	 */
	public void setPeriod(String period) {
		this.period = Period.periodByCode(period);
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
	 * @return the account
	 */
	@ManyToOne
	@JoinColumn(name="account_id", nullable = false)
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	public boolean isFixedAmount() {
		return fixedAmount;
	}

	public void setFixedAmount(boolean fixedAmount) {
		this.fixedAmount = fixedAmount;
	}
	
}
