package prot3ct.workit.view_models;

public class ProfileDetailsViewModel {
    private int userId;
    private String email;
    private String fullName;
    private String phone;
    private double ratingAsTasker;
    private double ratingAsSupervisor;
    private int numberOfReviewsAsTasker;
    private int getNumberOfReviewsAsSupervisor;
    private String pictureAsString;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getRatingAsTasker() {
        return ratingAsTasker;
    }

    public void setRatingAsTasker(double ratingAsTasker) {
        this.ratingAsTasker = ratingAsTasker;
    }

    public double getRatingAsSupervisor() {
        return ratingAsSupervisor;
    }

    public void setRatingAsSupervisor(double ratiingAsSupervisor) {
        this.ratingAsSupervisor = ratiingAsSupervisor;
    }

    public int getNumberOfReviewsAsTasker() {
        return numberOfReviewsAsTasker;
    }

    public void setNumberOfReviewsAsTasker(int numberOfReviewsAsTasker) {
        this.numberOfReviewsAsTasker = numberOfReviewsAsTasker;
    }

    public int getGetNumberOfReviewsAsSupervisor() {
        return getNumberOfReviewsAsSupervisor;
    }

    public void setGetNumberOfReviewsAsSupervisor(int getNumberOfReviewsAsSupervisor) {
        this.getNumberOfReviewsAsSupervisor = getNumberOfReviewsAsSupervisor;
    }

    public String getPictureAsString() {
        return pictureAsString;
    }

    public void setPictureAsString(String pictureAsString) {
        this.pictureAsString = pictureAsString;
    }
}
