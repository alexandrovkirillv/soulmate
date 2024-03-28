package com.findsoulmate.models;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Data
@EntityScan
public class Post implements Comparable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String anons;
    private String text;
    @Column(nullable = false)
    private String time;
    private int views;
    @Column(name = "user_name")
    private String userName;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss 'Томск'");
    public static final String ASIA_NOVOSIBIRSK = "Asia/Novosibirsk";

    public Post() {
    }

    public Post(String title, String anons, String text, String userName) {
        this.title = title;
        this.anons = anons;
        this.text = text;
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ASIA_NOVOSIBIRSK));
        this.time = DATE_FORMAT.format(new Date());
        this.userName = userName;
    }

    @Override
    public int compareTo(Post o) {
        return time.compareTo(o.getTime());
    }
}
