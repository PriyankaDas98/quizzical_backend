package com.exam.model.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="category")


public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;
    @Column(nullable = false)
    @NotEmpty(message = "Title can not be blank")
    private String title;

    @Column(length = 1000, nullable = false)
    @NotEmpty(message = "Title can not be blank")
    @Size(min=6, message = "Description length must be greater than six.")
    private String description;
//fetch = FetchType.EAGER, this is replace in belwo code because of mapping issue
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Quiz> set = new LinkedHashSet<>();
    

  public Category() {
   }

    public Category(Long cid, String title, String description, Set<Quiz> set) {
        this.cid = cid;
       this.title = title;
       this.description = description;
      this.set = set;
    }


    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Quiz> getSet() {
        return set;
    }

    public void setSet(Set<Quiz> set) {
        this.set = set;
    }
    
    
}
