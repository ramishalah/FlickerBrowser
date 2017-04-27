package org.example.rami.flickerbrowser;

import java.io.Serializable;

/**
 * Created by Rami on 3/15/2017.
 */

class Photo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLinks;
    private String mTags;
    private String mImage;

    public Photo(String title, String author, String authorId, String links, String tags, String image) {
        mTitle = title;
        mAuthor = author;
        mAuthorId = authorId;
        mLinks = links;
        mTags = tags;
        mImage = image;
    }

    String getTitle() {
        return mTitle;
    }

    String getAuthor() {
        return mAuthor;
    }

    String getAuthorId() {
        return mAuthorId;
    }

    String getLink() {
        return mLinks;
    }

    String getTags() {
        return mTags;
    }

    String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLinks='" + mLinks + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
