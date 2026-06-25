package manager.hbm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactRecord {
    @Id
    @Column(name = "id")
    public int id;
    @Column(name = "firstname")
    public String firstname;
    @Column(name = "lastname")
    public String lastname;
    @Column(name = "address")
    public String address;
    @Column(name = "middlename")
    public String middlename;
    @Column(name = "nickname")
    public String nickname;
    @Column(name = "company")
    public String company;
    @Column(name = "title")
    public String title;
    @Column(name = "home")
    public String home;
    @Column(name = "mobile")
    public String mobile;
    @Column(name = "work")
    public String work;
    @Column(name = "fax")
    public String fax;
    @Column(name = "email")
    public String email;
    @Column(name = "email2")
    public String email2;
    @Column(name = "email3")
    public String email3;
    @Column(name = "homepage")
    public String homepage;



    public ContactRecord(){

    }
    public ContactRecord(int id, String firstname, String lastname, String address,
                         String middlename, String nickname, String company, String title,
                         String home, String mobile, String work, String fax,
                         String email, String email2, String email3, String homepage) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.middlename = middlename;
        this.nickname = nickname;
        this.company = company;
        this.title = title;
        this.home = home;
        this.mobile = mobile;
        this.work = work;
        this.fax = fax;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.homepage = homepage;
    }
}

