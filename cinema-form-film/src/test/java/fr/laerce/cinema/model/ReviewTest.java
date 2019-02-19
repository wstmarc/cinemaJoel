package fr.laerce.cinema.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {

    @Test
    public void setArticle() {
        Review commentaire = new Review();
        commentaire.setArticle("test commentaire");
        assertEquals("test commentaire", commentaire.getArticle());
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
            fail("Transition vers Publie non autorisee");
        } catch (IllegalTransitionStateException e) {
            assertEquals(Review.MUST_BE_MODIFIED, comment.getState());
        }
    }

    @Test
    public void goodTransitionToRejete(){
        Review comment = new Review();
        try {
            comment.rejectByModerator();
            assertEquals(Review.REJECTED, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }
/*mauvaise transition de attente de modification vers supprimer */
    @Test
    public void badTransitionToDelete(){
        Review comment = new Review();
        try {
            comment.keepForEditByModerator();
            comment.deleteByUser();
            fail("Transition vers supprimer non autorisee");
        } catch (IllegalTransitionStateException e) {
            assertEquals(Review.MUST_BE_MODIFIED, comment.getState());
        }
    }
    @Test
    public void goodTransitionToModeration(){
        Review comment = new Review();
        try {
            comment.editByUser();
            assertEquals(Review.WAITING_MODERATION, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }
    /*mauvaise transition de entre editer vers supprimer*/

    @Test
    public void badTransitionToRejected(){
        Review comment = new Review();
        try {
            comment.keepForEditByModerator();
            comment.rejectByModerator();
            fail("Transition vers rejeter non autorisee");
        } catch (IllegalTransitionStateException e) {
            assertEquals(Review.MUST_BE_MODIFIED, comment.getState());
        }
    }

    @Test
    public void goodTransitionToDeleted(){
        Review comment = new Review();
        try {
            comment.validByModerator();
            comment.deleteByUser();
            assertEquals(Review.DELETED, comment.getState());
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }

    @Test
    public void goodTransitionToAbandoned(){
        Review comment = new Review();
        try {
            comment.keepForEditByModerator();
            comment.abandonByUser();
            assertEquals(Review.ABANDONNED, comment.getState());
            System.out.println("ok mec");
        } catch (IllegalTransitionStateException e) {
            fail("Transition attendue");
        }
    }

}


