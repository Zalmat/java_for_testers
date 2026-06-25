package manager.hbm;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "group_list")
public class GroupRecord {

    //Названия соответствуют неймингу в столбцам БД
    //Добавлена аннотация
    @Id
    @Column(name = "group_id")
    public int id;
    @Column(name = "group_name")
    public String name;
    @Column(name = "group_header")
    public String header;
    @Column(name = "group_footer")
    public String footer;

    public Date deprecated = new Date(System.currentTimeMillis());

    @ManyToMany//(fetch = FetchType.EAGER) //Тип связи в БД
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    public List<ContactRecord> contacts;

    public GroupRecord(){

    }
    public GroupRecord(int id, String name, String header, String footer){

        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;
    }
}
