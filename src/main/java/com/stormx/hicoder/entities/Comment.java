package com.stormx.hicoder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stormx.hicoder.controllers.requests.CommentRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @Setter
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne
    @Setter
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

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
            name = "comment_likes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedByUsers = new ArrayList<>();

    public Comment(CommentRequest commentRequest) {
        this.content = commentRequest.getContent();
    }

    public boolean likeOperation(User user) {
        if (this.likedByUsers.contains(user)) {
            this.likedByUsers.remove(user);
            return false;
        }
        this.likedByUsers.add(user);
        return true;

    }

    public boolean isCommentedBy(User user) {
        return this.author.equals(user);
    }

    public boolean isCommentedOn(Post post) {
        return this.post.equals(post);
    }

}