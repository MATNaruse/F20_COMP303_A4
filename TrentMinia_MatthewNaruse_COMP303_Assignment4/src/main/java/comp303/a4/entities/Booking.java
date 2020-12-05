package comp303.a4.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Booking {
	/* 
	 * Removed "date" and "time" due to ambiguity
	 * -> Replaced with "purchaseDate" and "viewingDate"
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bookingId;
	
	@NotBlank(message="Must pick a Movie")
	private String movieName;
	
	// May remove this to simplify things
	@OneToOne(cascade=CascadeType.ALL)
	private Customer custId;
	
	private double amountPaid;
	private Date purchaseDate;
	private Date viewingDate;
	private String venue;
	
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public Customer getCustId() {
		return custId;
	}
	public void setCustId(Customer custId) {
		this.custId = custId;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getViewingDate() {
		return viewingDate;
	}
	public void setViewingDate(Date viewingDate) {
		this.viewingDate = viewingDate;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
}
