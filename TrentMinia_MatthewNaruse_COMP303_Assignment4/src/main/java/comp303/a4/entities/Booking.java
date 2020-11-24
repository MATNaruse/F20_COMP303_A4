package comp303.a4.entities;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Booking {
	/* 
	 * Removed "date" and "time" due to ambiguity
	 * -> Replaced with "purchaseDate" and "viewingDate"
	 */
	private int bookingId;
	private String movieName;
	private int custId;
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
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
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
