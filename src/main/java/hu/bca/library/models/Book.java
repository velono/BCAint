package hu.bca.library.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String workId;
    
    @Column
    private Integer year;
     

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author"
            , joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    @JsonIgnoreProperties({"books"})
    private List<Author> authors;

    @JsonIgnoreProperties({"authors"})
    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<Rental> rentals;
}
