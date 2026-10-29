package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Update post request DTO for front-end API consumption")
public class UpdatePostRequest {

    @Schema(
            description = "Title of the post",
            example = "My Fishing Trip"
    )
    @Size(max = 100)
    private String title;

    @Schema(
            description = "Content/description of the post",
            example = "Went fishing at Lake Powell and caught a big bass!"
    )
    @Size(max = 2000)
    private String description;

    public UpdatePostRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}