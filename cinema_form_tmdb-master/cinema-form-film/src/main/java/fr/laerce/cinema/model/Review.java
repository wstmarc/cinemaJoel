//package fr.laerce.cinema.model;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//
//@Entity
//public class Review {
//    public static final int WAITING_MODERATION = 1;
//    public static final int PUBLISHED = 2;
//    public static final int MUST_BE_MODIFIED = 3;
//    public static final int REJECTED = 4;
//    public static final int DELETED = 5;
//    public static final int ABANDONNED = 6;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private long id;
//    @Basic
//    @Column(name = "article", nullable = true, length = -1)
//    private String article;
//    @Basic
//    @Column(name = "datte", nullable = false)
//    private Timestamp date;
//    @ManyToOne
//    @JoinColumn(name = "film_id")
//    private Film film;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//    private int state = Review.WAITING_MODERATION; // Point de départ
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getArticle() {
//        return article;
//    }
//
//    public void setArticle(String article) {
//        this.article = article;
//    }
//
//
//    public Timestamp getDate() {
//        return date;
//    }
//
//    public void setDate(Timestamp datte) {
//        this.date = datte;
//    }
//
//    public Film getFilm() {
//        return film;
//    }
//
//    public void setFilm(Film film) {
//        this.film = film;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Review review = (Review) o;
//
//        if (id != review.id) return false;
//        if (article != null ? !article.equals(review.article) : review.article != null) return false;
//        if (date != null ? !date.equals(review.date) : review.date != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = (int) (id ^ (id >>> 32));
//        result = 31 * result + (article != null ? article.hashCode() : 0);
//        result = 31 * result + (date != null ? date.hashCode() : 0);
//        return result;
//    }
//
//    public int getState() {
//        return this.state;
//    }
//
//    private boolean canTransitTo(int targetState){
//
//        boolean result;
//        switch (targetState) {
//            case Review.REJECTED :
//                result = this.getState() == Review.WAITING_MODERATION;
//                break;
//            case Review.DELETED :
//                result = this.getState() == Review.PUBLISHED;
//                break;
//            case Review.WAITING_MODERATION :
//                result = this.getState() == Review.PUBLISHED
//                        || this.getState() == Review.WAITING_MODERATION
//                        || this.getState() == Review.MUST_BE_MODIFIED;
//                break;
//            case Review.PUBLISHED :
//                result = this.getState() == Review.WAITING_MODERATION;
//                break;
//            case Review.MUST_BE_MODIFIED :
//                result = this.getState() == Review.WAITING_MODERATION;
//                break;
//            case Review.ABANDONNED :
//                result = this.getState() == Review.MUST_BE_MODIFIED;
//                break;
//            default :
//                result = false;
//        }
//        return result;
//    }
//
//    private void transitTo(int target) throws IllegalTransitionStateException {
//        if (canTransitTo(target)){
//            this.state = target;
//        } else {
//            throw new IllegalTransitionStateException("Transition non autorisée");
//        }
//    }
//
//    public void validByModerator() throws IllegalTransitionStateException {
//        transitTo(PUBLISHED);
//    }
//
//    public void keepForEditByModerator() throws IllegalTransitionStateException {
//        transitTo(MUST_BE_MODIFIED);
//    }
//
//    public void rejectByModerator() throws IllegalTransitionStateException {
//        transitTo(REJECTED);
//    }
//
//    public void editByUser() throws IllegalTransitionStateException {
//        transitTo(WAITING_MODERATION);
//    }
//
//    public void deleteByUser() throws IllegalTransitionStateException {
//        transitTo(DELETED);
//    }
//
//    public void abandonByUser() throws IllegalTransitionStateException {
//        transitTo(ABANDONNED);
//    }
//}

package fr.laerce.cinema.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
public class Review {

    private static final Map<Integer, List<Integer>> transitions;
    private static final Map<Integer, List<Integer>> transitionsTo;

    static {
        transitionsTo = new HashMap<>();
        transitionsTo.put(Review.WAITING_MODERATION,
                Arrays.asList(new Integer[]{
                        Review.PUBLISHED,
                        Review.WAITING_MODERATION,
                        Review.MUST_BE_MODIFIED,
                        Review.REJECTED
                }));
        transitionsTo.put(Review.PUBLISHED,
                Arrays.asList(new Integer[]{
                        Review.WAITING_MODERATION,
                        Review.DELETED
                }));

        transitionsTo.put(Review.MUST_BE_MODIFIED,
                Arrays.asList(new Integer[]{
                        Review.WAITING_MODERATION,
                        Review.ABANDONED
                }));
        transitionsTo.put(Review.ABANDONED, new ArrayList<>());
        transitionsTo.put(Review.DELETED, new ArrayList<>());
        transitionsTo.put(Review.REJECTED, new ArrayList<>());

    }


    static {
        transitions = new HashMap<>();
        transitions.put(Review.REJECTED, Arrays.asList(new Integer[]{Review.WAITING_MODERATION}));
        transitions.put(Review.DELETED, Arrays.asList(new Integer[]{Review.PUBLISHED}));
        transitions.put(Review.WAITING_MODERATION,
                Arrays.asList(new Integer[]{Review.PUBLISHED,
                        Review.WAITING_MODERATION,
                        Review.MUST_BE_MODIFIED
                }));
        transitions.put(Review.PUBLISHED, Arrays.asList(new Integer[]{Review.WAITING_MODERATION}));
        transitions.put(Review.MUST_BE_MODIFIED, Arrays.asList(new Integer[]{Review.WAITING_MODERATION}));
        transitions.put(Review.ABANDONED, Arrays.asList(new Integer[]{Review.MUST_BE_MODIFIED}));
    }


    public static final int WAITING_MODERATION = 1;
    public static final int PUBLISHED = 2;
    public static final int MUST_BE_MODIFIED = 3;
    public static final int ABANDONED = 4;
    public static final int REJECTED = 5;
    private static final int DELETED = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "article", nullable = true, length = -1)
    private String article;
    @Basic
    @Column(name = "datte", nullable = false)
    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int state = Review.WAITING_MODERATION;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }


    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp datte) {
        this.date = datte;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != review.id) return false;
        if (article != null ? !article.equals(review.article) : review.article != null) return false;
        if (date != null ? !date.equals(review.date) : review.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (article != null ? article.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    public int getState() {
        return this.state;
    }

    public void validByModerator() throws IllegalTransitionStateException {
        transitTo(Review.PUBLISHED);
    }

    public void keepForEditByModerator() throws IllegalTransitionStateException {
        transitTo(Review.MUST_BE_MODIFIED);
    }

    public void abandonByUser() throws IllegalTransitionStateException {
        transitTo(Review.ABANDONED);
    }

    public void rejectByModerator() throws IllegalTransitionStateException {
        transitTo(Review.REJECTED);
    }

    public void editByUser() throws IllegalTransitionStateException {
        transitTo(Review.WAITING_MODERATION);
    }

    private void transitTo(int target) throws IllegalTransitionStateException {
        if (canTransitTo(target)) {
            this.state = target;
        } else {
            throw new IllegalTransitionStateException("Transition non autorisée");
        }
    }

    private boolean canTransitTo(int targetState) {

        //return Review.transitions.get(targetState).contains(this.getState());

        return Review.transitionsTo.get(this.getState()).contains(targetState);

    /*

    boolean result;
    switch (targetState) {
      case Review.REJECTED:
        result = this.getState() == Review.WAITING_MODERATION;
        break;
      case Review.DELETED:
        result = this.getState() == Review.PUBLISHED;
        break;
      case Review.WAITING_MODERATION:
        result = this.getState() == Review.PUBLISHED
                || this.getState() == Review.WAITING_MODERATION
                || this.getState() == Review.MUST_BE_MODIFIED;
        break;
      case Review.PUBLISHED:
        result = this.getState() == Review.WAITING_MODERATION;
        break;
      case Review.MUST_BE_MODIFIED:
        result = this.getState() == Review.WAITING_MODERATION;
        break;
      case Review.ABANDONED:
        result = this.getState() == Review.MUST_BE_MODIFIED;
        break;
      default:
        result = false;
    }
    return result;
    */
    }

}
