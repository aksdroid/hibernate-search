package model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import model.impl.SetStringFieldBridge;

@Entity
@Indexed
@AnalyzerDef(name = "customAnalyzer",
		
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
							@Parameter(name = "language", value = "Turkish")
				})
		})
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	private Integer ogrenciId;
	
	@Field
	@Analyzer(definition = "customAnalyzer")
	private String firstName;
	
	@Field
	@Analyzer(definition = "customAnalyzer")
	private String lastName;

	@Field
	@Analyzer(definition = "customAnalyzer")
	private String email;
	
	@DateBridge(resolution = Resolution.DAY)
	@Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@IndexedEmbedded
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "students_lessons", joinColumns = @JoinColumn(name = "studentId"),
					inverseJoinColumns =  @JoinColumn(name = "lessonId"))
	@Analyzer(definition = "customAnalyzer")
	private Set<Lesson> lessons;
	
	
	@ElementCollection
	@Field
	@FieldBridge(impl = SetStringFieldBridge.class)
	@CollectionTable(name = "student_phonenumbers", joinColumns = @JoinColumn(name = "studentId"))
	@Analyzer(definition = "customAnalyzer")
	private	Set<String> phoneNumber;
	
	
	@OneToOne
	@JoinColumn(name = "addressId")
	@IndexedEmbedded(prefix = "adres_")
	private Address address;
	
	public Student() {

	}


	public Student(String firstName, String lastName, Date dateOfBirth, Set<Lesson> lessons, Set<String> phoneNumber, String email, Address address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.lessons = lessons;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
	}
	
	
	/*		Tokenize = Parcalama i�lemi gibi adland�r�labilir.
	 * 
	 * 		�rnekte StandardTokenizerFactory, ard�ndan iki filtre fabrikas� LowerCaseFilterFactory ve SnowballPorterFilterFactory taraf�ndan kullan�l�r. 
	 * 		Standart tokenizer, noktalama i�aretleri ve tire kullan�lan kelimeleri b�ler. Bu, genel ama�l� bir tokenizer i�in iyi bir y�ntemdir. 
	 * 		E-posta adreslerini veya internet ana makine adlar�n� indekslemek i�in, onlar� ay�rd��� i�in en uygun de�ildir. 
	 * 		Bu gibi durumlarda Lucene'nin ClassicTokenizerFactory'sini kullanabilir veya �zel tokenizer ve fabrika uygulayabilirsiniz.
	 *		K���k harf filtresi, her bir belirte�teki harfleri k���k harf haline d�n��t�r�rken, kartopu s�zgeci sonunda dil �zel uyarlama uygular.
	 *
	 *
	 *		Genellikle, ��z�mleyici �er�evesi'ni kullan�rken, tokenizer ve bunun ard�ndan keyfi say�da filtre ile ba�laman�z gerekir.
	 */

	public Integer getOgrenciId() {
		return ogrenciId;
	}


	public void setOgrenciId(Integer ogrenciId) {
		this.ogrenciId = ogrenciId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	

	public Set<Lesson> getLessons() {
		return lessons;
	}


	public void setLessons(Set<Lesson> lessons) {
		this.lessons = lessons;
	}


	public Set<String> getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(Set<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "Student [ogrenciId=" + ogrenciId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", dateOfBirth=" + dateOfBirth + ", lessons=" + lessons + ", phoneNumber=" + phoneNumber
				+ ", address=" + address + "]";
	}

	
}
