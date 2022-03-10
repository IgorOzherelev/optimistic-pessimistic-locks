package sber.ru.dss.dsshomeworks.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Account {

    @Id
    @Column(name  = "id")
    private Long id;

    @Column(name = "sum")
    private Integer sum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
