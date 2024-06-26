package com.stormx.hicoder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stormx.hicoder.controllers.helpers.PostRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Setter
@Getter
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    private String mediaUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Timestamp createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Timestamp updatedAt;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedByUsers = new ArrayList<>();

    public Post(PostRequest post) {
        this.content = post.getContent();
        this.mediaUrl = post.getMediaUrl();
    }

    public boolean isPostedBy(User user) {
        return this.author.equals(user);
    }

    public boolean likeOperation(User user) {
        if (this.likedByUsers.contains(user)) {
            this.likedByUsers.remove(user);
            return false;
        }
        this.likedByUsers.add(user);
        return true;

    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }


    @Override
    public String toString() {

        return "Post{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author.getId() +
                ", createdAt=" + createdAt +
                '}';
    }

}