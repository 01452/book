package telran.java2022.book.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "name")
@Entity
@ToString
public class Author implements Serializable {
	private static final long serialVersionUID = 7224662755462564108L;

	@Id
	String name;
	LocalDate birthDate;
}
