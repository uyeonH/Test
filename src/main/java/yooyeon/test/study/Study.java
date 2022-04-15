package yooyeon.test.study;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yooyeon.test.member.Member;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Study {

    @Id @GeneratedValue
    private Long id;

    private StudyStatus status = StudyStatus.DRAFT;
    private int limitCount;
    private String name;
    private LocalDateTime openedDateTime;

    @ManyToOne
    private Member owner;

    public Study(int limit, String name) {
        this.limitCount = limit;
        this.name = name;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야함");
        }
        this.limitCount = limit;
    }

    public void publish() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limitCount +
                ", name='" + name + '\'' +
                '}';
    }
}
