package comp303.a4.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

@Entity
public class Booking {
	/* 
	 * Removed "date" and "time" due to ambiguity
	 * -> Replaced with "purchaseDate" and "viewingDate"
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="book_gen")
	@SequenceGenerator(name="book_gen", sequenceName="book_seq")
	private int bookingId;
	
	@NotBlank(message="Must pick a Movie")
	private String movieName;

	private int custId;
	private int tickAdult;
	private int tickSenStu;
	private int tickChild;
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
	public int getTickAdult() {
		return tickAdult;
	}
	public void setTickAdult(int tickAdult) {
		this.tickAdult = tickAdult;
	}
	public int getTickSenStu() {
		return tickSenStu;
	}
	public void setTickSenStu(int tickSenStu) {
		this.tickSenStu = tickSenStu;
	}
	public int getTickChild() {
		return tickChild;
	}
	public void setTickChild(int tickChild) {
		this.tickChild = tickChild;
	}
	public double getAmountPaid() {
		//return amountPaid;
		return  (tickAdult * 12) + (tickSenStu * 8) + (tickChild * 6.50);
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
	
	public Booking() {};
	
	public Booking(String movieName, int custId, int tAdult, int tSenStu, int tChild, Date purchDate, Date viewDate, String venue) {
		this.movieName = movieName;
		this.custId = custId;
		this.tickAdult = tAdult;
		this.tickSenStu = tSenStu;
		this.tickChild = tChild;
		this.purchaseDate = purchDate;
		this.viewingDate = viewDate;
		this.venue = venue;
	}
}
