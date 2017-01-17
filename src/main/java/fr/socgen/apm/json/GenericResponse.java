package fr.socgen.apm.json;

/**
 * Created by baudoin on 17/01/17.
 */
public class GenericResponse {
    private static final String TITLE="Generic response";
    private final String description;
    public GenericResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
