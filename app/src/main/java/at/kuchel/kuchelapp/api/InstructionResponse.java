package at.kuchel.kuchelapp.api;

/**
 * Created by bernhard on 12.03.2018.
 */

public class InstructionResponse {

    private String step;
    private String description;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
