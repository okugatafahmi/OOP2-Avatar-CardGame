package com.avatarduel.gameplay;

public interface Observer {
    /**
     * Do the update when there is new notify from subject
     */
    public void update();

    /**
     * Set the subject (gameplay)
     * @param sub subject
     */
    public void setSubject(Subject sub);
}