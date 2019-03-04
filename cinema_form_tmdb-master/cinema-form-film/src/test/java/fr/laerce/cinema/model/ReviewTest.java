//package fr.laerce.cinema.model;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class ReviewTest {
//
//    @Test
//    public void setArticle() {
//        Review commentaire = new Review();
//        commentaire.setArticle("test commentaire");
//        assertEquals("test commentaire", commentaire.getArticle());
//    }
//
//    @Test
//    public void etatInitial() {
//        Review comment = new Review();
//        assertEquals(Review.WAITING_MODERATION, comment.getState());
//    }
//
//    @Test
//    public void goodTransitionToPublie() {
//        Review comment = new Review();
//        try {
//            comment.validByModerator();
//            assertEquals(Review.PUBLISHED, comment.getState());
//        } catch (IllegalTransitionStateException e) {
//            fail("Transition attendue");
//        }
//    }
//
//    @Test
//    public void badTransitionToPublie() {
//        Review comment = new Review();
//        try {
//            comment.keepForEditByModerator();
//            comment.validByModerator();
//            fail("Transition vers Publie non autorisee");
//        } catch (IllegalTransitionStateException e) {
//            assertEquals(Review.MUST_BE_MODIFIED, comment.getState());
//        }
//    }
//
//    @Test
//    public void goodTransitionToRejete(){
//        Review comment = new Review();
//        try {
//            comment.rejectByModerator();
//            assertEquals(Review.REJECTED, comment.getState());
//        } catch (IllegalTransitionStateException e) {
//            fail("Transition attendue");
//        }
//    }
///*mauvaise transition de attente de modification vers supprimer */
//    @Test
//    public void badTransitionToDelete(){
//        Review comment = new Review();
//        try {
//            comment.keepForEditByModerator();
//            comment.deleteByUser();
//            fail("Transition vers supprimer non autorisee");
//        } catch (IllegalTransitionStateException e) {
//            assertEquals(Review.MUST_BE_MODIFIED, comment.getState());
//        }
//    }
//    @Test
//    public void goodTransitionToModeration(){
//        Review comment = new Review();
//        try {
//            comment.editByUser();
//            assertEquals(Review.WAITING_MODERATION, comment.getState());
//        } catch (IllegalTransitionStateException e) {
//            fail("Transition attendue");
//        }
//    }
//    /*mauvaise transition de entre editer vers supprimer*/
//
//    @Test
//    public void badTransitionToRejected(){
//        Review comment = new Review();
//        try {
//            comment.keepForEditByModerator();
//            comment();
//            fail("Transition vers supprimer non autorisee");
//        } catch (IllegalTransitionStateException e) {
//            assertEquals(Review.MUST_BE_MODIFIED, comment.getState());
//        }
//    }
//
//    @Test
//    public void goodTransitionToDeleted(){
//        Review comment = new Review();
//        try {
//            comment.validByModerator();
//            comment.deleteByUser();
//            assertEquals(Review.DELETED, comment.getState());
//        } catch (IllegalTransitionStateException e) {
//            fail("Transition attendue");
//        }
//    }
//
//    @Test
//    public void goodTransitionToAbandoned(){
//        Review comment = new Review();
//        try {
//            comment.keepForEditByModerator();
//            comment.abandonByUser();
//            assertEquals(Review.ABANDONNED, comment.getState());
//            System.out.println("ok mec");
//        } catch (IllegalTransitionStateException e) {
//            fail("Transition attendue");
//        }
//    }
//
//}
package fr.laerce.cinema.model;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.*;

@DataJpaTest

public class ReviewTest {

    @Test
    public void setArticle() {
        Review comment = new Review();
        comment.setArticle("test commentaire");
        assertEquals("test commentaire", comment.getArticle());
    }

    @Test
    public void etatInitial() {
        Review comment = new Review();
        assertEquals(Review.WAITING_MODERATION, comment.getState());
    }

    @Test
    public void goodTransitionToPublie() {
        Review comment = new Review();
        try {
            comment.validByModerator();
            assertEquals(Review.PUBLISHED, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }

    @Test
    public void badTransitionToPublie() {
        Review comment = new Review();
        try {
            comment.keepForEditByModerator();
            comment.validByModerator();
            fail("Transition non autorisée");
        } catch (IllegalTransitionStateException e) {
            assertEquals(Review.MUST_BE_MODIFIED, comment.getState());
        }
    }

    @Test
    public void goodTransitionToAbandonne() {
        Review comment = new Review();
        try {
            comment.keepForEditByModerator();
            comment.abandonByUser();
            assertEquals(Review.ABANDONED, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }

    @Test
    public void badTransitionToAbandoned() {
        Review comment = new Review();
        try {
            comment.validByModerator();
            comment.abandonByUser();
            fail("Transition vers Abandonne non autorisée");
        } catch (IllegalTransitionStateException e) {
            assertEquals(Review.PUBLISHED, comment.getState());
        }
    }

    @Test
    public void goodTransitionReject() {
        Review comment = new Review();
        try {
            comment.rejectByModerator();
            assertEquals(Review.REJECTED, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }

    @Test
    public void badTransitionReject() {
        Review comment = new Review();
        try {
            comment.validByModerator();
            comment.rejectByModerator();
            fail("Transition vers Rejected non autorisée");
        } catch (IllegalTransitionStateException e) {
            assertEquals(Review.PUBLISHED, comment.getState());
        }
    }


    @Test
    public void goodTransitionEditByUserFromPublished() {
        Review comment = new Review();
        try {
            comment.validByModerator();
            comment.editByUser();
            assertEquals(Review.WAITING_MODERATION, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }


    @Test
    public void goodTransitionEditByUserFromWAITING_MODERATION() {
        Review comment = new Review();
        try {
            assertEquals(Review.WAITING_MODERATION, comment.getState());
            comment.editByUser();
            assertEquals(Review.WAITING_MODERATION, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Aucune transition attendue");
        }
    }


    @Test
    public void goodTransitionEditByUserFromMUST_BE_MODIFIED() {
        Review comment = new Review();
        try {
            comment.keepForEditByModerator();
            assertEquals(Review.MUST_BE_MODIFIED, comment.getState());
            comment.editByUser();
            assertEquals(Review.WAITING_MODERATION, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }

    @Test
    public void badTransitionEditByUserFromABANDONED() {
        Review comment = new Review();
        try {
            comment.keepForEditByModerator();
            comment.abandonByUser();
            comment.editByUser();
            fail("Transition non permise attendue");
        } catch (IllegalTransitionStateException e) {
            assertEquals(Review.ABANDONED, comment.getState());
        }
    }

}
