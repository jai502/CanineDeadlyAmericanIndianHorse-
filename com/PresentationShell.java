/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones, Peter Mills, ALexander cramb
 * Date of first version: 17/05/2016
 * 
 * Last version by: Peter Mills, Alexander cramb
 * Date of last update: 30/05/2016
 * Version number: 1.1.0
 * 
 * Commit date: 18/06/2015
 * Description: 
 * a Simple class to contain presentation meta-data
 */

package com;

import java.io.Serializable;

public class PresentationShell implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String title, language, author, tagOne, tagTwo, tagThree, tagFour, tagFive;
    private Integer id, rating;
    
    /**
     * @return the title
     */
    public String getTitle() 
    {
        return title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer votes) {
        this.rating = votes;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) 
    {
        this.title = title;
    }

    /**
     * @return the language
     */
    public String getLanguage() 
    {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) 
    {
        this.language = language;
    }

    /**
     * @return the author
     */
    public String getAuthor() 
    {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    /**
     * @return the tagOne
     */
    public String getTagOne() 
    {
        return tagOne;
    }

    /**
     * @param tagOne the tagOne to set
     */
    public void setTagOne(String tagOne) 
    {
        this.tagOne = tagOne;
    }

    /**
     * @return the tagTwo
     */
    public String getTagTwo() 
    {
        return tagTwo;
    }

    /**
     * @param tagTwo the tagTwo to set
     */
    public void setTagTwo(String tagTwo) 
    {
        this.tagTwo = tagTwo;
    }

    /**
     * @return the tagThree
     */
    public String getTagThree() 
    {
        return tagThree;
    }

    /**
     * @param tagThree the tagThree to set
     */
    public void setTagThree(String tagThree) 
    {
        this.tagThree = tagThree;
    }

    /**
     * @return the tagFour
     */
    public String getTagFour() 
    {
        return tagFour;
    }

    /**
     * @param tagFour the tagFour to set
     */
    public void setTagFour(String tagFour) 
    {
        this.tagFour = tagFour;
    }

    /**
     * @return the tagFive
     */
    public String getTagFive() 
    {
        return tagFive;
    }

    /**
     * @param tagFive the tagFive to set
     */
    public void setTagFive(String tagFive) 
    {
        this.tagFive = tagFive;
    }
    
    // returns true
    public boolean validForUpload()
    {
        if (this.author == null) return false;
        if (this.title == null) return false;
        if (this.language == null) return false;
        
        return true;
    }
}
