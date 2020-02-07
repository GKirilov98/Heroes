package app.services.models.user;

public class ProfileUserServiceModel {

    private String username;
    private String email;
    private String heroName;
    private String heroId;
    private String heroGender;

    public ProfileUserServiceModel() {
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroGender() {
        return heroGender;
    }

    public void setHeroGender(String heroGender) {
        this.heroGender = heroGender;
    }
}
