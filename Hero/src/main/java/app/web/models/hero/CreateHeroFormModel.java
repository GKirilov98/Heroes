package app.web.models.hero;

public class CreateHeroFormModel {
    private String name;
    private String gender;

    public CreateHeroFormModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
